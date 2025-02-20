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

import br.com.adblock.cadastro.dto.EmpresaDto;
import br.com.adblock.cadastro.service.EmpresaService;

@RestController
@RequestMapping("empresa/adiblock")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping
	public ResponseEntity<List<EmpresaDto>> listarEmpresas() {
		return ResponseEntity.ok(empresaService.listarEmpresas());
	}
	
	@PostMapping
	public ResponseEntity<EmpresaDto> cadastrarEmpresa(@RequestBody EmpresaDto dto,
			UriComponentsBuilder uriComponentsBuilder) {
		EmpresaDto empresaDto = empresaService.cadastrarEmpresa(dto);
		URI uri = uriComponentsBuilder.path("/listarempresa/{id}").buildAndExpand(empresaDto.getId()).toUri();
		return ResponseEntity.created(uri).body(empresaDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<EmpresaDto> excluir(@PathVariable Long id) {
		empresaService.excluirEmpresa(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody EmpresaDto dto) {
		EmpresaDto atualizado = empresaService.atualizarEmpresa(dto);
		return ResponseEntity.ok(atualizado);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarEmpresas(@PathVariable Long id) {
		EmpresaDto empresaDados = empresaService.buscarEmpresas(id);
		return ResponseEntity.ok(empresaDados);
	}
	
	@GetMapping("buscar/{id}")
	public ResponseEntity<List<EmpresaDto>> listarEmpresasComUsuariosLogado(@PathVariable Long id) {
		return ResponseEntity.ok(empresaService.listarEmpresasComUsuariosLogado(id));
	}
	
}
