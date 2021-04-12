package com.system.clinical.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.system.clinical.model.Cargo;
import com.system.clinical.model.input.CargoInput;
import com.system.clinical.repository.CargoRepository;

@Service
public class CargoService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CargoRepository cargoRepository;
	
	public Cargo salvar(CargoInput cargoInput) {
		Cargo cargo = toEntity(cargoInput);
		return cargoRepository.save(cargo);
	}
	
	public Cargo atualizar(Long id, CargoInput cargoInput) {
		Cargo cargo = toEntity(cargoInput);
		Cargo cargoSalvo = buscarEmpresaPeloId(id);
		BeanUtils.copyProperties(cargo, cargoSalvo, "id");
		return cargoRepository.save(cargoSalvo);
	}

	private Cargo buscarEmpresaPeloId(Long id) {
		Cargo cargoSalvo = this.cargoRepository.findById(id)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		return cargoSalvo;
	}	
	
	private Cargo toEntity(CargoInput cargoInput) {
		return modelMapper.map(cargoInput, Cargo.class);
	}

}
