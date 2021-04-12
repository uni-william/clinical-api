package com.system.clinical.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.system.clinical.model.Cargo;
import com.system.clinical.repository.CargoRepository;

@Service
public class CargoService {
	
	@Autowired
	private CargoRepository cargoRepository;
	
	public Cargo salvar(Cargo cargo) {
		return cargoRepository.save(cargo);
	}
	
	public Cargo atualizar(Long id, Cargo empresa) {
		Cargo cargoSalvo = buscarEmpresaPeloId(id);	
		BeanUtils.copyProperties(empresa, cargoSalvo, "id");
		return cargoRepository.save(cargoSalvo);
	}

	private Cargo buscarEmpresaPeloId(Long id) {
		Cargo cargoSalvo = this.cargoRepository.findById(id)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		return cargoSalvo;
	}	

}
