package br.com.fe.hell.apihabit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.fe.hell.apihabit.controller.dto.SituacaoSolicitacaoImovelEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dHistoricoSolicitacaoOcupacao")
public class HistoricoSolicitacaoImovelFuncional {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer identidade;
	
	private Integer ideSolicitacao;
	private String pontoOperacao;
	private Date data;
	private SituacaoSolicitacaoImovelEnum codOperacao;
	private String observacoes;
	
	public HistoricoSolicitacaoImovelFuncional(SolicitacaoImovelFuncional solicitacao, SituacaoSolicitacaoImovelEnum situacao) {
	     this.setIdeSolicitacao(solicitacao.getIdentidade());
	     this.setPontoOperacao(solicitacao.getPontoSolicitacao());
	     this.setData(new Date());
	     this.setObservacoes(solicitacao.getObservacoes());
	     this.setCodOperacao(situacao);
	}

}