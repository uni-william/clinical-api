package com.system.clinical.repository.especialidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.system.clinical.model.Especialidade;

public interface EspecialidadeRepositoryQuery {
	
	public Page<Especialidade> filtrar(String descricao, Pageable pageable);
	
}
