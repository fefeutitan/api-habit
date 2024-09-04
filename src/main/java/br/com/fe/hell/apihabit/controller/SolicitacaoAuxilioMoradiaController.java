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

import br.com.fe.hell.apihabit.controller.dto.SolicitacaoAuxilioMoradiaDto;
import br.com.fe.hell.apihabit.model.HistoricoSolicitacaoAuxilioMoradia;
import br.com.fe.hell.apihabit.model.SolicitacaoAuxilioMoradia;
import br.com.fe.hell.apihabit.repository.HistoricoSolicitacaoAuxilioMoradiaRepository;
import br.com.fe.hell.apihabit.repository.SolicitacaoAuxilioMoradiaRepository;


@RestController
@RequestMapping("/deputado")
public class SolicitacaoAuxilioMoradiaController {
	
	@Autowired
	SolicitacaoAuxilioMoradiaRepository repository; 

	@Autowired
	HistoricoSolicitacaoAuxilioMoradiaRepository historicoRepository;
	
	@Autowired
	IntegracaoParlamentar parlamentarService;

	@GetMapping("/{ideDeputado}/auxiliomoradia/solicitacoes")
	public List<SolicitacaoAuxilioMoradiaDto> list(@PathVariable Integer ideDeputado){
		List<SolicitacaoAuxilioMoradia> solicitacaoAuxilios = repository.findAllByIdeDeputado(ideDeputado);
		return SolicitacaoAuxilioMoradiaDto.converter(solicitacaoAuxilios);
	}
	
	@GetMapping("/{ideDeputado}/auxiliomoradia/solicitacoes/{id}")
	public ResponseEntity<SolicitacaoAuxilioMoradiaDto> find(@PathVariable Integer ideDeputado, @PathVariable Integer id){
		Optional<SolicitacaoAuxilioMoradia> solicitacaoAuxilio = repository.findByIdeDeputadoAndIdentidade(ideDeputado, id);
		if(solicitacaoAuxilio.isPresent()) {
			return ResponseEntity.ok(new SolicitacaoAuxilioMoradiaDto(solicitacaoAuxilio.get()));	    		
		}
		return null;
	}

	@GetMapping("/auxiliomoradia/solicitacoes/{id}")
	public ResponseEntity<SolicitacaoAuxilioMoradiaDto> findGenerico(@PathVariable Long id){
		Optional<SolicitacaoAuxilioMoradia> solicitacaoAuxilio = repository.findById(id);
		if(solicitacaoAuxilio.isPresent()) {
			return ResponseEntity.ok(new SolicitacaoAuxilioMoradiaDto(solicitacaoAuxilio.get()));	    		
		}
		return null;
	}
	
	@GetMapping("/{ideDeputado}/auxiliomoradia/solicitacoes/ativa")
	public ResponseEntity<SolicitacaoAuxilioMoradiaDto> find(@PathVariable Integer ideDeputado){
		Optional<SolicitacaoAuxilioMoradia> solicitacaoAuxilio = repository.carregarUltimoPorIdeDeputado(ideDeputado);
		if(solicitacaoAuxilio.isPresent()) {
			return ResponseEntity.ok(new SolicitacaoAuxilioMoradiaDto(solicitacaoAuxilio.get()));	    		
		}
		return null;
	}

	 @PostMapping("/{ideDeputado}/auxiliomoradia/solicitacoes")
	 @Transactional	
	 public ResponseEntity<SolicitacaoAuxilioMoradiaDto> save(@PathVariable Integer ideDeputado, @RequestBody SolicitacaoAuxilioMoradiaDto form, 
			 												  UriComponentsBuilder uriBuilder, Principal usuario) throws Exception {
		 SolicitacaoAuxilioMoradia solicitacao = form.to();
		 
		 solicitacao.setDataRequerimento(new Date());
		 solicitacao.setIdeDeputado(ideDeputado);
		 
		 DeputadoEleitoDto deputado = parlamentarService.getNovoDeputadoEleitoPorIdeCadastro(ideDeputado);
		 solicitacao.setSolicitante(deputado.getNomParlamentar() != null ? deputado.getNomParlamentar().trim() : deputado.getNomCivil().trim());
		 solicitacao.setCpf(deputado.getNumCPF());
//		 solicitacaoAuxilio.setPontoSolicitacao(usuario.getName().toUpperCase());
		 
		 Integer id = solicitacao.getIdentidade();
		 solicitacao = repository.save(solicitacao);
		 
		 if (id == null) {
			 criarHistoricoInicial(solicitacao);
		 }
		 
		 URI uri = uriBuilder
			 		.path("/deputado/" + ideDeputado + "/imovelfuncional/solicitacoes/{identidade}")
			 		.buildAndExpand(solicitacao.getIdentidade())
			 		.toUri();
		 
		 return ResponseEntity.created(uri).body(new SolicitacaoAuxilioMoradiaDto(solicitacao));
	 }
   
   @DeleteMapping("/{ideDeputado}/auxiliomoradia/solicitacoes/{id}")
   @Transactional	
   public ResponseEntity<?> delete(@PathVariable Integer ideDeputado, @PathVariable Integer id){
   	Optional<SolicitacaoAuxilioMoradia> optional = repository.findByIdeDeputadoAndIdentidade(ideDeputado, id);
   	if(optional.isPresent()) {
   		repository.delete(optional.get());
   		return ResponseEntity.ok().build();
   	}
   	return null;
   }

	@GetMapping("/auxiliomoradia/solicitacoes/novas")
	public Collection<SolicitacaoAuxilioMoradia> listarSolicitacoesNovas() throws Exception {
		return repository.listarSolicitacoesCriadasAPartirDe(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("16/10/2022 23:59"));
	}
	
	@GetMapping("/auxiliomoradia/solicitacoes/saneamento/{idSolicitacao}")
	public Collection<SolicitacaoAuxilioMoradia> sanearCriadas(@PathVariable Integer idSolicitacao) throws Exception {
		Map<Integer, SolicitacaoAuxilioMoradia> mapSolicitacoes = new HashMap<>(); 
		Collection<SolicitacaoAuxilioMoradia> lista = this.listarSolicitacoesNovas();
				
		for (SolicitacaoAuxilioMoradia sol : lista) {
			if (idSolicitacao != null && !sol.getIdentidade().equals(idSolicitacao))
				continue;
			
			SolicitacaoAuxilioMoradia s = mapSolicitacoes.get(sol.getIdeDeputado());
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
			
			sol.setTipoPagamento(sol.getTipoPagamento().equals(1) ? 2 : 1); // 1-especie, 2-reembolso
			repository.save(sol);
			
			sol.setInfo("Alterado");
		}
		
		return mapSolicitacoes.values();
	}
   
   private void criarHistoricoInicial(SolicitacaoAuxilioMoradia solicitacao) {
	   historicoRepository.save(new HistoricoSolicitacaoAuxilioMoradia(solicitacao, SituacaoSolicitacaoAuxilioEnum.CADASTRADO));
	   historicoRepository.save(new HistoricoSolicitacaoAuxilioMoradia(solicitacao, SituacaoSolicitacaoAuxilioEnum.AGUARDANDO_AUTORIZACAO));
   }
   
}