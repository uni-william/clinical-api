package com.system.clinical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.clinical.model.Especialidade;
import com.system.clinical.repository.especialidade.EspecialidadeRepositoryQuery;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long>, EspecialidadeRepositoryQuery {
	
	public List<Especialidade> findByStatusTrue();

}
