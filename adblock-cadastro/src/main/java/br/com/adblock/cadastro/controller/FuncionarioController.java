package br.com.adblock.cadastro.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adblock.cadastro.dto.FuncionarioDto;
import br.com.adblock.cadastro.service.FuncionarioService;



@RestController
@RequestMapping("funcionario/adiblock")
public class FuncionarioController {

	@Autowired
	private FuncionarioService service;


	@GetMapping
	public ResponseEntity<List<FuncionarioDto>> listarFuncionarios() {
		return ResponseEntity.ok(service.listarFuncionarios());
	}

	@PostMapping
	public ResponseEntity<FuncionarioDto> cadastrarFuncionario(@RequestBody FuncionarioDto dto,
			UriComponentsBuilder uriComponentsBuilder) {
		FuncionarioDto funcionarioDto = service.cadastrarFuncionario(dto);
		URI uri = uriComponentsBuilder.path("/listarfuncionario/{id}").buildAndExpand(funcionarioDto.getId()).toUri();
		return ResponseEntity.created(uri).body(funcionarioDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<FuncionarioDto> excluir(@PathVariable Long id) {
		service.excluirFuncionario(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public ResponseEntity<FuncionarioDto> atualizar(@RequestBody FuncionarioDto dto) {
		FuncionarioDto atualizado = service.atualizarFuncionario(dto);
		return ResponseEntity.ok(atualizado);
	}

}
