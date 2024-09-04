package br.com.fe.hell.apihabit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dsolicitacaoauxilio")
public class SolicitacaoAuxilioMoradia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer identidade;	
	private Integer ideDeputado;
	private Integer tipoPagamento;	
	private Date dataRequerimento;	
	private Date dataInicioBeneficio;	
	private String observacoes;	
	private String pontoSolicitacao;	
	private Integer ideRequerimento;	
	private String autorizacaoBeneficio;	
	private String cpf;	
	private String solicitante;	
//	private String contato;
//	private String telefone;
	private Date dataFimBeneficio;	
	private Boolean ciencia;
	private Date dataCiencia;
	
	@Transient
	private String info;
	
	
	public SolicitacaoAuxilioMoradia(Integer identidade, Integer ideDeputado, String solicitante, Integer tipoPagamento, String pontoSolicitacao, 
									 Date dataRequerimento, Boolean ciencia, Date dataCiencia) {
		this.identidade = identidade;
		this.ideDeputado = ideDeputado;
		this.solicitante = solicitante;
		this.tipoPagamento = tipoPagamento;
		this.pontoSolicitacao = pontoSolicitacao;
		this.dataRequerimento = dataRequerimento;
		this.ciencia = ciencia;
		this.dataCiencia = dataCiencia;
	}
		
}