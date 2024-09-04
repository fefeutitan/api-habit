package br.com.fe.hell.apihabit.controller;

import java.net.URI;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fe.hell.apihabit.controller.dto.SolicitacaoImovelFuncionalDto;
import br.com.fe.hell.apihabit.model.HistoricoSolicitacaoImovelFuncional;
import br.com.fe.hell.apihabit.model.SolicitacaoImovelFuncional;
import br.com.fe.hell.apihabit.repository.HistoricoSolicitacaoImovelFuncionalRepository;
import br.com.fe.hell.apihabit.repository.SolicitacaoImovelFuncionalRepository;

@RestController
@RequestMapping("deputado")
public class SolicitacaoImovelFuncionalController {
	
	@Autowired
	private SolicitacaoImovelFuncionalRepository repository;
	
	@Autowired
	HistoricoSolicitacaoImovelFuncionalRepository historicoRepository; 	
	
	@Autowired
	IntegracaoParlamentar parlamentarService;
	
	@GetMapping("/{ideDeputado}/imovelfuncional/solicitacoes")
	public List<SolicitacaoImovelFuncionalDto> list(@PathVariable Integer ideDeputado){
		System.out.println("passou");
		List<SolicitacaoImovelFuncional> solicitacaoOcupacoes = repository.findAllByIdeDeputado(ideDeputado);
		return SolicitacaoImovelFuncionalDto.converter(solicitacaoOcupacoes);
	}
	
	@GetMapping("/{ideDeputado}/imovelfuncional/solicitacoes/{id}")
	public ResponseEntity<SolicitacaoImovelFuncionalDto> find(@PathVariable Integer ideDeputado, @PathVariable Integer id){
		Optional<SolicitacaoImovelFuncional> solicitacaoOcupacao = repository.findByIdeDeputadoAndIdentidade(ideDeputado, id);
		if(solicitacaoOcupacao.isPresent()) {
			return ResponseEntity.ok(new SolicitacaoImovelFuncionalDto(solicitacaoOcupacao.get()));	    		
		}
		return null;
	}
	
	@GetMapping("/{ideDeputado}/imovelfuncional/solicitacoes/ativa")
	public ResponseEntity<SolicitacaoImovelFuncionalDto> find(@PathVariable Integer ideDeputado){
		Optional<SolicitacaoImovelFuncional> solicitacaoOcupacao = repository.carregarUltimoPorIdeDeputado(ideDeputado);
		if(solicitacaoOcupacao.isPresent()) {
			return ResponseEntity.ok(new SolicitacaoImovelFuncionalDto(solicitacaoOcupacao.get()));	    		
		}
		return null;
	}

	 @PostMapping("/{ideDeputado}/imovelfuncional/solicitacoes")
	 @Transactional	
	 public ResponseEntity<SolicitacaoImovelFuncionalDto> save(@PathVariable Integer ideDeputado, @RequestBody SolicitacaoImovelFuncionalDto form, 
			 												   UriComponentsBuilder uriBuilder, Principal usuario) throws Exception {
		 SolicitacaoImovelFuncional solicitacao = form.to();
		 
		 solicitacao.setDataRequerimento(new Date());
		 solicitacao.setIdeDeputado(ideDeputado);
		 
		 DeputadoEleitoDto deputado = parlamentarService.getNovoDeputadoEleitoPorIdeCadastro(ideDeputado);
		 solicitacao.setSolicitante((deputado.getNomParlamentar() != null ? deputado.getNomParlamentar().trim() : deputado.getNomCivil().trim()));
		 solicitacao.setCpf(deputado.getNumCPF());
		 
		 
		 Integer id = solicitacao.getIdentidade();
		 solicitacao = repository.save(solicitacao);
		 
		 if (id == null) {
			 criarHistoricoInicial(solicitacao);
		 }
		 
		 URI uri = uriBuilder
				 		.path("/deputado/" + ideDeputado + "/imovelfuncional/solicitacoes/{identidade}")
				 		.buildAndExpand(solicitacao.getIdentidade())
				 		.toUri();
		 
		 return ResponseEntity.created(uri).body(new SolicitacaoImovelFuncionalDto(solicitacao));
	 }
   
   @DeleteMapping("/{ideDeputado}/imovelfuncional/solicitacoes/{id}")
   @Transactional	
   public ResponseEntity<?> delete(@PathVariable Integer ideDeputado, @PathVariable Integer id){
   	Optional<SolicitacaoImovelFuncional> optional = repository.findByIdeDeputadoAndIdentidade(ideDeputado, id);
   	if(optional.isPresent()) {
   		repository.delete(optional.get());
   		return ResponseEntity.ok().build();
   	}
   	return null;
   }

	@GetMapping("/imovelfuncional/solicitacoes/{id}")
	public ResponseEntity<SolicitacaoImovelFuncional> findGenerico(@PathVariable Long id){
		Optional<SolicitacaoImovelFuncional> solicitacao = repository.findById(id);
		if(solicitacao.isPresent()) {
			return ResponseEntity.ok(solicitacao.get());	    		
		}
		return null;
	}

	@GetMapping("/imovelfuncional/solicitacoes/novas")
	public Collection<SolicitacaoImovelFuncional> listarSolicitacoesNovas() throws Exception {
		return repository.listarSolicitacoesCriadasAPartirDe(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("16/10/2022 23:59"));
	}
	
	@GetMapping("/imovelfuncional/solicitacoes/saneamento/{idSolicitacao}")
	public Collection<SolicitacaoImovelFuncional> sanearCriadas(@PathVariable Integer idSolicitacao) throws Exception {
		Map<Integer, SolicitacaoImovelFuncional> mapSolicitacoes = new HashMap<>(); 
		Collection<SolicitacaoImovelFuncional> lista = this.listarSolicitacoesNovas();
				
		for (SolicitacaoImovelFuncional sol : lista) {
			if (idSolicitacao != null && !sol.getIdentidade().equals(idSolicitacao))
				continue;
			
			SolicitacaoImovelFuncional s = mapSolicitacoes.get(sol.getIdeDeputado());
			if (s == null) {
				mapSolicitacoes.put(sol.getIdeDeputado(), sol);
			}
			else {
				if (sol.getDataRequerimento().after(s.getDataRequerimento())) {
					mapSolicitacoes.put(sol.getIdeDeputado(), sol);
					repository.delete(s);
				} else {
					repository.delete(sol);
				}
			}

			sol.setLocalizacao(sol.getLocalizacao().equals(1) ? 0 : 1); // 0-asa sul, 1-asa norte
			repository.save(sol);
			
			sol.setInfo("Alterado");
		}
		
		return mapSolicitacoes.values();
	}
   
   private void criarHistoricoInicial(SolicitacaoImovelFuncional solicitacao) {
	   historicoRepository.save(new HistoricoSolicitacaoImovelFuncional(solicitacao, SituacaoSolicitacaoImovelEnum.CADASTRADO));
	   historicoRepository.save(new HistoricoSolicitacaoImovelFuncional(solicitacao, SituacaoSolicitacaoImovelEnum.ENVIADO));
   }
   
}