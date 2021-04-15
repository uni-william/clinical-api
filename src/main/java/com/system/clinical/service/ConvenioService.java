package com.system.clinical.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.system.clinical.model.Convenio;
import com.system.clinical.model.input.ConvenioInput;
import com.system.clinical.repository.ConvenioRepository;
import com.system.clinical.service.exception.NegocioException;

@Service
public class ConvenioService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ConvenioRepository convenioRepository;
	
	public Convenio salvar(ConvenioInput convenioInput) {
		Convenio convenioSalvo = convenioRepository.findByCnpj(convenioInput.getCnpj());
		if (convenioSalvo != null)
			throw new NegocioException("Cnpj já cadastrado para outro convênio");
		Convenio convenio = toEntity(convenioInput);
		convenio.setDataCadastro(LocalDateTime.now());
		convenio.setStatus(true);
		return convenioRepository.save(convenio);
	}
	
	public Convenio atualizar(Long id, ConvenioInput convenioInput) {
		Convenio convenio = toEntity(convenioInput);
		Convenio convenioSalvo = buscarConvenioPeloId(id);
		Convenio converioPorCnpj = convenioRepository.findByCnpj(convenioInput.getCnpj());
		if (converioPorCnpj != null && !convenioSalvo.equals(converioPorCnpj))
			throw new NegocioException("Cnpj já cadastrado para outro convênio");
		BeanUtils.copyProperties(convenio, convenioSalvo, "id");
		return convenioRepository.save(convenioSalvo);
	}
	
	public void atualizarStatus(Long id, Boolean status) {
		Convenio convenioSalvo = buscarConvenioPeloId(id);
		convenioSalvo.setStatus(status);
		convenioSalvo.setDataCancelamento(status == Boolean.FALSE ? LocalDateTime.now() : null);
		convenioRepository.save(convenioSalvo);
	}

	private Convenio buscarConvenioPeloId(Long id) {
		Convenio convenioSalvo = this.convenioRepository.findById(id)
			      .orElseThrow(() -> new EmptyResultDataAccessException(1));
		return convenioSalvo;
	}	
	
	private Convenio toEntity(ConvenioInput convenioInput) {
		return modelMapper.map(convenioInput, Convenio.class);
	}


}
