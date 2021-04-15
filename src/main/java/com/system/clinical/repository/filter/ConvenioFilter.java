package com.system.clinical.repository.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioFilter {
	
	private String nome;
	private String cnpj;
	private Boolean status;

}
