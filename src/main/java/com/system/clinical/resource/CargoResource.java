package com.system.clinical.resource;

import java.net.URI;
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

import com.system.clinical.model.Cargo;
import com.system.clinical.model.input.CargoInput;
import com.system.clinical.repository.CargoRepository;
import com.system.clinical.service.CargoService;

@RestController
@RequestMapping("/cargos")
public class CargoResource {
	

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private CargoService cargoService;
	
	@GetMapping
	public Page<Cargo> pesquisar(String descricao, Pageable pageable) {
		return cargoRepository.filtrar(descricao, pageable);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cargo> buscarPorId(@PathVariable Long id) {
		Optional<Cargo> optional = cargoRepository.findById(id);
		if (optional.isPresent())
			return ResponseEntity.ok(optional.get());

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Cargo> novo(@Valid @RequestBody CargoInput cargo, HttpServletResponse response) {
		Cargo cargoSalvo = cargoService.salvar(cargo);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(cargoSalvo.getId())
				.toUri();

		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(cargoSalvo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cargoRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cargo> atualizar(@PathVariable Long id, @Valid @RequestBody CargoInput cargo) {
		Cargo cargoSalvo = cargoService.atualizar(id, cargo);
		return ResponseEntity.ok(cargoSalvo);
	}	

}
