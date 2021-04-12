package com.system.clinical.enums;

public enum Sexo {
	
	MASCULINO("Masculino"),
	FEMININO("Feminino");
	
	Sexo(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

}
