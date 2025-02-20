package br.com.adblock.cadastro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adblock.cadastro.dto.FuncionarioDto;
import br.com.adblock.cadastro.model.Funcionario;
import br.com.adblock.cadastro.repository.FuncionarioRepository;


@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	public FuncionarioDto cadastrarFuncionario(FuncionarioDto dto) {
		Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
		repository.save(funcionario);
		return modelMapper.map(funcionario, FuncionarioDto.class);
	}

	public List<FuncionarioDto> listarFuncionarios() {
		List<Funcionario> funcionarios = repository.findAll();
		return funcionarios.stream().map(funcionario -> modelMapper.map(funcionario, FuncionarioDto.class))
				.collect(Collectors.toList());

	}

	public void excluirFuncionario(Long id) {
		repository.deleteById(id);
	}

	public FuncionarioDto atualizarFuncionario(FuncionarioDto dto) {
		Funcionario funcionario = modelMapper.map(dto, Funcionario.class);
		funcionario.setId(dto.getId());
		funcionario = repository.save(funcionario);
		return modelMapper.map(funcionario, FuncionarioDto.class);

	}

}
