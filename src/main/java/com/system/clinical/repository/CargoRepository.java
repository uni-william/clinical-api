package com.system.clinical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.clinical.model.Cargo;
import com.system.clinical.repository.cargo.CargoRepositoryQuery;

public interface CargoRepository extends JpaRepository<Cargo, Long>, CargoRepositoryQuery {

}
