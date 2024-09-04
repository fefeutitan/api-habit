package br.com.fe.hell.apihabit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.fe.hell.apihabit.controller.dto.SituacaoSolicitacaoAuxilioEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dHistoricoSolicitacao")
public class HistoricoSolicitacaoAuxilioMoradia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer identidade;
	
	private Integer ideSolicitacao;
	private String pontoOperacao;
	private Date data;
	private SituacaoSolicitacaoAuxilioEnum codOperacao;
	private String observacoes;
	
	public HistoricoSolicitacaoAuxilioMoradia(SolicitacaoAuxilioMoradia solicitacao, SituacaoSolicitacaoAuxilioEnum situacao) {
	     this.setIdeSolicitacao(solicitacao.getIdentidade());
	     this.setPontoOperacao(solicitacao.getPontoSolicitacao());
	     this.setData(new Date());
	     this.setObservacoes(solicitacao.getObservacoes());
	     this.setCodOperacao(situacao);
	}
}