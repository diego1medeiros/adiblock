package br.com.adblock.cadastro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.adblock.cadastro.dto.EmpresaDto;
import br.com.adblock.cadastro.dto.ListaVendaDto;
import br.com.adblock.cadastro.dto.VendaDto;
import br.com.adblock.cadastro.service.VendaService;


@RestController
@RequestMapping("venda/adiblock")
public class VendaController {

	@Autowired
	private VendaService vendaService;

	@PostMapping
	public void cadastrarVenda(@RequestBody ListaVendaDto dto, UriComponentsBuilder uriComponentsBuilder) {
		vendaService.cadastrarVenda(dto);
	}

	@GetMapping
	public ResponseEntity<List<VendaDto>> listarVendas() {
		return ResponseEntity.ok(vendaService.listarVendas());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		vendaService.removerVenda(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> imprimirPedido(@PathVariable Long id) {
		return ResponseEntity.ok(vendaService.imprimirPedido(id));
	}
	
	@GetMapping("buscar/{id}")
	public ResponseEntity<List<VendaDto>> listarEmpresasComUsuariosLogado(@PathVariable Long id) {
		return ResponseEntity.ok(vendaService.listarPropostaComUsuariosLogado(id));
	}

}
