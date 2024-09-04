package br.com.fe.hell.apihabit.controller.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fe.hell.apihabit.model.SolicitacaoImovelFuncional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitacaoImovelFuncionalDto {

	private Integer id;
	private Integer ideDeputado;
	private Integer numeroOcupantes;
	private Integer ocupanteNecessidadesEspeciais;
	private Integer localizacao;
	private String observacoes; 
	private String pontoSolicitacao; 
	private Date dataRequerimento; 
	private String cpf; 
	private Boolean ocupantesDificuldadeLocomocao;
	private Boolean ocupanteNecessidadeFisica;
	private Boolean ocupanteNecessidadeVisual;
	private Boolean ocupanteNecessidadeAuditiva;
	private Boolean ocupanteNecessidadeIntelectual;
	private Boolean ocupanteNecessidadeMental;

	public SolicitacaoImovelFuncionalDto(SolicitacaoImovelFuncional solicitacaoOcupacao) {
		this.id = solicitacaoOcupacao.getIdentidade();
		this.ideDeputado = solicitacaoOcupacao.getIdeDeputado();
		this.numeroOcupantes = solicitacaoOcupacao.getNumeroOcupantes();
		this.ocupanteNecessidadesEspeciais = solicitacaoOcupacao.getOcupanteNecessidadesEspeciais();
		this.localizacao = solicitacaoOcupacao.getLocalizacao();
		this.pontoSolicitacao = solicitacaoOcupacao.getPontoSolicitacao();
		this.dataRequerimento = solicitacaoOcupacao.getDataRequerimento();
		this.cpf = solicitacaoOcupacao.getCpf();
		this.observacoes = solicitacaoOcupacao.getObservacoes();
		this.ocupantesDificuldadeLocomocao = solicitacaoOcupacao.getOcupantesDificuldadeLocomocao();
		this.ocupanteNecessidadeAuditiva = solicitacaoOcupacao.getOcupanteNecessidadeAuditiva();
		this.ocupanteNecessidadeFisica = solicitacaoOcupacao.getOcupanteNecessidadeFisica();
		this.ocupanteNecessidadeVisual = solicitacaoOcupacao.getOcupanteNecessidadeVisual();
		this.ocupanteNecessidadeIntelectual = solicitacaoOcupacao.getOcupanteNecessidadeIntelectual();
		this.ocupanteNecessidadeMental = solicitacaoOcupacao.getOcupanteNecessidadeMental();
	}
	
	public static List<SolicitacaoImovelFuncionalDto> converter(List<SolicitacaoImovelFuncional> solicitacaoOcupacaos) {
		return solicitacaoOcupacaos.stream().map(SolicitacaoImovelFuncionalDto::new).collect(Collectors.toList());
	}

	public SolicitacaoImovelFuncional to() {
		SolicitacaoImovelFuncional s = new SolicitacaoImovelFuncional();
		s.setIdentidade(id);
		s.setLocalizacao(localizacao);
		s.setNumeroOcupantes(numeroOcupantes);
		s.setObservacoes(observacoes);
		s.setPontoSolicitacao(pontoSolicitacao);
		s.setOcupanteNecessidadeAuditiva(ocupanteNecessidadeAuditiva);
		s.setOcupanteNecessidadeFisica(ocupanteNecessidadeFisica);
		s.setOcupanteNecessidadeAuditiva(ocupanteNecessidadeAuditiva);
		s.setOcupanteNecessidadeIntelectual(ocupanteNecessidadeIntelectual);
		s.setOcupanteNecessidadeMental(ocupanteNecessidadeMental);
		s.setOcupanteNecessidadesEspeciais(ocupanteNecessidadesEspeciais);
		s.setOcupanteNecessidadeVisual(ocupanteNecessidadeVisual);
		s.setOcupantesDificuldadeLocomocao(ocupantesDificuldadeLocomocao);
		
		return s;
	}

}