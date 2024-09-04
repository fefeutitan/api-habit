package br.com.fe.hell.apihabit.controller.dto;

public enum SituacaoSolicitacaoImovelEnum {
	CADASTRADO/* 			 0 */("Cadastrada"),
    ENVIADO/* 				 1 */("Enviada"),
    DEFERIDO/*  			 2 */("Deferida"),
    INDEFERIDO/*  			 3 */("Indeferida"),
    RESERVADO/* 			 4 */("Imovel Reservado"),
    CANCELADO/* 			 5 */("Cancelada")
    ;
    
    private final String descricao;
    
    private SituacaoSolicitacaoImovelEnum(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public String toString() {
        return this.descricao;
    }

} 