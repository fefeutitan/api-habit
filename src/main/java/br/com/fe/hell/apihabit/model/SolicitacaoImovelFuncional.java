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
@Table(name = "dsolicitacaoimovel")
public class SolicitacaoImovelFuncional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer identidade;  
	
	private Integer ideDeputado;  
	private Date dataRequerimento; 
	private String cpf; 
	private Integer numeroOcupantes; 
	private Integer ocupanteNecessidadesEspeciais;  
	private Integer localizacao;  
	private String observacoes;
	private String contato;
	private String telefone;
	private String pontoSolicitacao;
	private Integer ideImovel; 
	private Date previsaoOcupacao;  
	private String comentarioQuarta;
	private String solicitante;
	private Boolean ocupantesDificuldadeLocomocao;
	private Boolean ocupanteNecessidadeFisica;
	private Boolean ocupanteNecessidadeVisual;
	private Boolean ocupanteNecessidadeAuditiva;
	private Boolean ocupanteNecessidadeIntelectual;
	private Boolean ocupanteNecessidadeMental;
	
	@Transient
	private String info;
	
	public SolicitacaoImovelFuncional(Integer id, Integer ideDeputado, Integer numeroOcupantes,
			Integer ocupanteNecessidadesEspeciais, Integer localizacao, String pontoSolicitacao,
			Date dataRequerimento, String cpf,Boolean ocupantesDificuldadeLocomocao,
			Boolean ocupanteNecessidadeAuditiva, Boolean ocupanteNecessidadeVisual,
			Boolean ocupanteNecessidadeMental, Boolean ocupanteNecessidadeIntelectual, 
			Boolean ocupanteNecessidadeFisica, String observacoes) {
		
		this.identidade = id;
		this.ideDeputado = ideDeputado;
		this.numeroOcupantes = numeroOcupantes;
		this.ocupanteNecessidadesEspeciais = ocupanteNecessidadesEspeciais;
		this.localizacao = localizacao;
		this.pontoSolicitacao = pontoSolicitacao;
		this.dataRequerimento = dataRequerimento;
		this.cpf = cpf;
		this.observacoes = observacoes;
		this.ocupantesDificuldadeLocomocao = ocupantesDificuldadeLocomocao;
		this.ocupanteNecessidadeAuditiva = ocupanteNecessidadeAuditiva;
		this.ocupanteNecessidadeVisual = ocupanteNecessidadeVisual;
		this.ocupanteNecessidadeMental = ocupanteNecessidadeMental;
		this.ocupanteNecessidadeIntelectual = ocupanteNecessidadeIntelectual;
		this.ocupanteNecessidadeFisica = ocupanteNecessidadeFisica;
	}
	
}