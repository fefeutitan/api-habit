package br.com.fe.hell.apihabit.controller.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.fe.hell.apihabit.model.SolicitacaoAuxilioMoradia;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitacaoAuxilioMoradiaDto {
	
	private Integer id;
	private Integer ideDeputado;
	private Integer tipoPagamento;	
	private String pontoSolicitacao;
	private Date dataRequerimento; 
	private Boolean ciencia;
	private Date dataCiencia;
			
	public SolicitacaoAuxilioMoradiaDto(SolicitacaoAuxilioMoradia solicitacaoAuxilio) {
		this.id = solicitacaoAuxilio.getIdentidade();
		this.ideDeputado = solicitacaoAuxilio.getIdeDeputado();
		this.tipoPagamento = solicitacaoAuxilio.getTipoPagamento();
		this.pontoSolicitacao = solicitacaoAuxilio.getPontoSolicitacao();
		this.setDataRequerimento(solicitacaoAuxilio.getDataRequerimento());
		this.ciencia = solicitacaoAuxilio.getCiencia();
		this.dataCiencia = solicitacaoAuxilio.getDataCiencia();
	}
	
	public static List<SolicitacaoAuxilioMoradiaDto> converter(List<SolicitacaoAuxilioMoradia> solicitacaoAuxilios) {
		return solicitacaoAuxilios.stream().map(SolicitacaoAuxilioMoradiaDto::new).collect(Collectors.toList());
	}

	public SolicitacaoAuxilioMoradia to() {
		SolicitacaoAuxilioMoradia s = new SolicitacaoAuxilioMoradia();
		s.setIdentidade(id);
		s.setIdeDeputado(ideDeputado);
		s.setTipoPagamento(tipoPagamento);
		s.setPontoSolicitacao(pontoSolicitacao);
		s.setDataRequerimento(dataRequerimento);
		s.setCiencia(ciencia);
		s.setDataCiencia(dataCiencia);
		
		return s;
	}
}