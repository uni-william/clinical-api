package com.system.clinical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.clinical.model.Convenio;
import com.system.clinical.repository.convenio.ConvenioRepositoryQuery;

public interface ConvenioRepository extends JpaRepository<Convenio, Long>, ConvenioRepositoryQuery {
	
	public Convenio findByCnpj(String cnpj);
	public List<Convenio> findByStatusTrue();

}
