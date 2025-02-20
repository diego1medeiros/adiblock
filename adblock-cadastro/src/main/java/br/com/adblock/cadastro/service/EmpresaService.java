package br.com.adblock.cadastro.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adblock.cadastro.dto.EmpresaDto;
import br.com.adblock.cadastro.model.Empresa;
import br.com.adblock.cadastro.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private ModelMapper modelMapper;

	public EmpresaDto cadastrarEmpresa(EmpresaDto dto) {
		Empresa empresa = modelMapper.map(dto, Empresa.class);
		empresaRepository.save(empresa);
		return modelMapper.map(empresa, EmpresaDto.class);
	}

	public List<EmpresaDto> listarEmpresas() {
		List<Empresa> empresas = empresaRepository.findAll();
		return empresas.stream().map(empresa -> modelMapper.map(empresa, EmpresaDto.class))
				.collect(Collectors.toList());
	}

	public void excluirEmpresa(Long id) {
		empresaRepository.deleteById(id);
	}

	public EmpresaDto atualizarEmpresa(EmpresaDto dto) {
		Empresa empresa = modelMapper.map(dto, Empresa.class);
		empresa.setId(dto.getId());
		empresa = empresaRepository.save(empresa);
		return modelMapper.map(empresa, EmpresaDto.class);

	}

	public EmpresaDto buscarEmpresas(Long id) {
		Empresa empresa = empresaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(empresa, EmpresaDto.class);

	}

	public List<EmpresaDto> listarEmpresasComUsuariosLogado(Long id) {
		List<Empresa> empresas = empresaRepository.findByFuncionarioId(id);
		return empresas.stream().map(empresa -> modelMapper.map(empresa, EmpresaDto.class))
				.collect(Collectors.toList());
	}

}
