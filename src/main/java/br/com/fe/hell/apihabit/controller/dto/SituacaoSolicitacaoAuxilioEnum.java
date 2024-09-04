package br.com.fe.hell.apihabit.controller.dto;

public enum SituacaoSolicitacaoAuxilioEnum {
	CADASTRADO/* 			  0 */("Cadastrado"),
    AGUARDANDO_AUTORIZACAO/*  1 */("Aguardando Autorização"),
    AGUARDANDO_ANALISE/*   	  2 */("Aguardando Análise"),
    DEFERIDO/*  			  3 */("Deferido"),
    INDEFERIDO/*  			  4 */("Indeferido"),
    DEVOLVIDO/* 			  5 */("Devolvido"),
    CANCELADO/* 			  6 */("Cancelado"),
    ARQUIVADO/* 			  7 */("Arquivado"),
    AUTORIZACAO_CANCELAMENTO/*8 */("Aguardando Autorização de Cancelamento"),
    AGUARDANDO_CANCELAMENTO/* 9 */("Aguardando Cancelamento"),
    RECEBIMENTO_CANCELADO/*   10*/("Recebimento Cancelado (COHAB)")
    ;
    
    private final String descricao;
    
    private SituacaoSolicitacaoAuxilioEnum(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public String toString() {
        return this.descricao;
    }

} 