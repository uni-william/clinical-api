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

import com.system.clinical.model.Especialidade;
import com.system.clinical.model.input.EspecialidadeInput;
import com.system.clinical.repository.EspecialidadeRepository;
import com.system.clinical.service.EspecialidadeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadeResource {
	

	@Autowired
	private EspecialidadeRepository especialidadeRepository;

	@Autowired
	private EspecialidadeService especialidadeService;
	
	@GetMapping
	@ApiOperation(value = "Retorna especialidades filtradas paginado")
	public Page<Especialidade> pesquisar(String descricao, Pageable pageable) {
		return especialidadeRepository.filtrar(descricao, pageable);
	}
	
	
	@GetMapping(value = "/listarAtivos")
	@ApiOperation(value = "Retorna lista de especialidades ativas - ideal para dropbox")
	public ResponseEntity<List<Especialidade>> listarAtivos()  {
		return ResponseEntity.ok(especialidadeRepository.findByStatusTrue());
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retorna especialidade por id")
	public ResponseEntity<Especialidade> buscarPorId(@PathVariable Long id) {
		Optional<Especialidade> optional = especialidadeRepository.findById(id);
		if (optional.isPresent())
			return ResponseEntity.ok(optional.get());

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ApiOperation(value = "Cria uma nova especialidade")
	public ResponseEntity<Especialidade> novo(@Valid @RequestBody EspecialidadeInput especialidade, HttpServletResponse response) {
		Especialidade especialidadeSalvo = especialidadeService.salvar(especialidade);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(especialidadeSalvo.getId())
				.toUri();

		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(especialidadeSalvo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Exclui especialidade por id")
	public void remover(@PathVariable Long id) {
		especialidadeRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Atualizar dados de especialidade")
	public ResponseEntity<Especialidade> atualizar(@PathVariable Long id, @Valid @RequestBody Especialidade especialidade) {
		Especialidade especialidadeSalvo = especialidadeService.atualizar(id, especialidade);
		return ResponseEntity.ok(especialidadeSalvo);
	}
	
	@PutMapping("/{id}/status")
	@ApiOperation(value = "Atualizar status da especialidade")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatus(@PathVariable Long id, @RequestBody Boolean status) {
		especialidadeService.atualizarStatus(id, status);
	}

}
