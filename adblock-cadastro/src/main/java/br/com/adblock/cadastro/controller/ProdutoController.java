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

import br.com.adblock.cadastro.dto.ProdutoDto;
import br.com.adblock.cadastro.service.ProdutoService;

@RestController
@RequestMapping("produto/adiblock")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<ProdutoDto>> listarProdutos() {
		return ResponseEntity.ok(produtoService.listarProdutos());
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDto> cadastrarProduto(@RequestBody ProdutoDto produto,
			UriComponentsBuilder uriComponentsBuilder) {
		ProdutoDto produtoDto = produtoService.cadastrarProduto(produto);
		URI uri = uriComponentsBuilder.path("/listarproduto/{id}").buildAndExpand(produtoDto.getId()).toUri();
		return ResponseEntity.created(uri).body(produtoDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ProdutoDto> excluir(@PathVariable Long id) {
		produtoService.excluirProduto(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	public ResponseEntity<?> atualizar(@RequestBody ProdutoDto produto) {
		ProdutoDto atualizado = produtoService.atualizarProduto(produto);
		return ResponseEntity.ok(atualizado);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarProduto(@PathVariable Long id) {
		ProdutoDto produtoDados = produtoService.buscarProdutos(id);
		return ResponseEntity.ok(produtoDados);
	}

	@GetMapping("buscar/{id}")
	public ResponseEntity<List<ProdutoDto>> listarProdutosComUsuariosLogado(@PathVariable Long id) {
		return ResponseEntity.ok(produtoService.listarProdutosComUsuariosLogado(id));
	}
	
}
