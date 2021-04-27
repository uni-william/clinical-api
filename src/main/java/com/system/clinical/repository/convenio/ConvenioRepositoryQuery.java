package com.system.clinical.repository.convenio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.system.clinical.model.Convenio;
import com.system.clinical.repository.filter.ConvenioFilter;

public interface ConvenioRepositoryQuery {
	
	public Page<Convenio> filtrar(ConvenioFilter filter, Pageable pageable);
	public List<Convenio> listaFiltrada(ConvenioFilter filter);
	
}
