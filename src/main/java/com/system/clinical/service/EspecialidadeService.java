package com.system.clinical.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.system.clinical.model.Especialidade;
import com.system.clinical.model.input.EspecialidadeInput;
import com.system.clinical.repository.EspecialidadeRepository;

@Service
public class EspecialidadeService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EspecialidadeRepository especialidadeRepository;
	
	public Especialidade salvar(EspecialidadeInput especialidadeInput) {
		Especialidade especialidade = toEntity(especialidadeInput);
		especialidade.setStatus(true);
		return especialidadeRepository.save(especialidade);
	}
	
	public Especialidade atualizar(Long id, EspecialidadeInput especialidadeInput) {
		Especialidade especialidade = toEntity(especialidadeInput);
		Especialidade especialidadeSalvo = buscarEspecialidadePeloId(id);
		BeanUtils.copyProperties(especialidade, especialidadeSalvo, "id");
		return especialidadeRepository.save(especialidadeSalvo);
	}
	
	public void atualizarStatus(Long id, Boolean status) {
		Especialidade especialidadeSalva = buscarEspecialidadePeloId(id);
		especialidadeSalva.setStatus(status);
		especialidadeRepository.save(especialidadeSalva);
	}

	private Especialidade buscarEspecialidadePeloId(Long id) {
		Especialidade especialidadeSalvo = this.especialidadeRepository.findById(id)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		return especialidadeSalvo;
	}	
	
	private Especialidade toEntity(EspecialidadeInput especialidadeInput) {
		return modelMapper.map(especialidadeInput, Especialidade.class);
	}

}
