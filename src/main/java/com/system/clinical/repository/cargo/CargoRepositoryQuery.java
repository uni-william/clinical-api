package com.system.clinical.repository.cargo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.system.clinical.model.Cargo;

public interface CargoRepositoryQuery {
	
	public Page<Cargo> filtrar(String descricao, Pageable pageable);
	
}
