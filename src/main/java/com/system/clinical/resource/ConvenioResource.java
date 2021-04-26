package com.system.clinical.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.system.clinical.model.Convenio;
import com.system.clinical.model.input.ConvenioInput;
import com.system.clinical.repository.ConvenioRepository;
import com.system.clinical.repository.filter.ConvenioFilter;
import com.system.clinical.service.ConvenioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/convenios")
public class ConvenioResource {
	

	@Autowired
	private ConvenioRepository convenioRepository;

	@Autowired
	private ConvenioService convenioService;
	
	@GetMapping
	@ApiOperation(value = "Retorna convênios filtrados paginados")
	public Page<Convenio> pesquisar(ConvenioFilter filter, Pageable pageable) {
		return convenioRepository.filtrar(filter, pageable);
	}
	
	
	@GetMapping(value = "/listarAtivos")
	@ApiOperation(value = "Retorna lista de convênios ativos - ideal para dropbox")
	public ResponseEntity<List<Convenio>> listarAtivos()  {
		return ResponseEntity.ok(convenioRepository.findByStatusTrue());
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna convênio por id")
	public ResponseEntity<Convenio> buscarPorId(@PathVariable Long id) {
		Optional<Convenio> optional = convenioRepository.findById(id);
		if (optional.isPresent())
			return ResponseEntity.ok(optional.get());

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ApiOperation(value = "Cria um novo convênio")
	public ResponseEntity<Convenio> novo(@Valid @RequestBody ConvenioInput convenio, HttpServletResponse response) {
		Convenio convenioSalvo = convenioService.salvar(convenio);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(convenioSalvo.getId())
				.toUri();

		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(convenioSalvo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Exclui convênio por id")
	public void remover(@PathVariable Long id) {
		convenioRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar dados de convênio")
	public ResponseEntity<Convenio> atualizar(@PathVariable Long id, @Valid @RequestBody Convenio convenio) {
		Convenio convenioSalvo = convenioService.atualizar(id, convenio);
		return ResponseEntity.ok(convenioSalvo);
	}
	
	@PutMapping("/{id}/status")
	@ApiOperation(value = "Atualizar status do convênio")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatus(@PathVariable Long id, @RequestBody Boolean status) {
		convenioService.atualizarStatus(id, status);
	}

}
