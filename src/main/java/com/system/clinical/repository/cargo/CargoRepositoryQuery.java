package com.system.clinical.repository.cargo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.system.clinical.model.Cargo;

public interface CargoRepositoryQuery {
	
	public Page<Cargo> filtrar(String descricao, Pageable pageable);
	public List<Cargo> listaFiltrada(String descricao);
	
}
