package br.com.adblock.cadastro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adblock.cadastro.dto.ProdutoDto;
import br.com.adblock.cadastro.model.Produto;
import br.com.adblock.cadastro.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoDto cadastrarProduto(ProdutoDto dto) {
		Produto produto = modelMapper.map(dto, Produto.class);
		produtoRepository.save(produto);
		return modelMapper.map(produto, ProdutoDto.class);
	}

	public List<ProdutoDto> listarProdutos() {
		List<Produto> produtos = produtoRepository.findAll();
		return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDto.class))
				.collect(Collectors.toList());
	}

	public void excluirProduto(Long id) {
		produtoRepository.deleteById(id);
	}

	public ProdutoDto atualizarProduto(ProdutoDto dto) {
		Produto produto = modelMapper.map(dto, Produto.class);
		produto.setId(dto.getId());
		produto = produtoRepository.save(produto);
		return modelMapper.map(produto, ProdutoDto.class);

	}

	public ProdutoDto buscarProdutos(Long id) {
		Produto produto = produtoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(produto, ProdutoDto.class);
	}

	public  List<ProdutoDto> listarProdutosComUsuariosLogado(Long id) {
		List<Produto> produtos = produtoRepository.findByFuncionarioId(id);
		return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDto.class))
				.collect(Collectors.toList());
	
	}
	
	

}
