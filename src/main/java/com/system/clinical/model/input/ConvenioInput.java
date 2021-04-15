package com.system.clinical.model.input;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioInput {
	

	@NotNull
	private String nome;
	@NotNull
	private String cnpj;


}
