package br.com.adblock.cadastro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adblock.cadastro.dto.EmpresaDto;
import br.com.adblock.cadastro.dto.ItemVendaDto;
import br.com.adblock.cadastro.dto.ListaVendaDto;
import br.com.adblock.cadastro.dto.ProdutoDto;
import br.com.adblock.cadastro.dto.VendaDto;
import br.com.adblock.cadastro.model.Empresa;
import br.com.adblock.cadastro.model.ItemVenda;
import br.com.adblock.cadastro.model.Venda;
import br.com.adblock.cadastro.repository.ItemVendaRepository;
import br.com.adblock.cadastro.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ItemVendaRepository itemVendaRepository;

	public void cadastrarVenda(ListaVendaDto dto) {
		Venda venda = modelMapper.map(dto, Venda.class);
		vendaRepository.save(venda);
		List<ItemVenda> itensVenda = venda.getItensVenda();
		for (ItemVenda itemVenda : itensVenda) {
			itemVenda.setVenda(venda);
			itemVendaRepository.save(itemVenda);
		}
	}

	public List<VendaDto> listarVendas() {
		List<Venda> vendas = vendaRepository.findAll();
		return vendas.stream().map(venda -> modelMapper.map(venda, VendaDto.class)).collect(Collectors.toList());
	}

	public void removerVenda(Long id) {
		vendaRepository.deleteById(id);
		Venda venda = vendaRepository.getReferenceById(id);
		List<ItemVenda> ItensVenda = itemVendaRepository.findByVendaId(id);
		for (ItemVenda itemVenda : ItensVenda) {
			itemVenda.setVenda(venda);
			itemVendaRepository.delete(itemVenda);
		}
	}

	public List<ItemVendaDto> imprimirPedido(Long id) {
		List<ItemVenda> itemVendas = itemVendaRepository.findByVendaId(id);
		return itemVendas.stream().map(itemVenda -> modelMapper.map(itemVenda, ItemVendaDto.class))
				.collect(Collectors.toList());
	}

	public  List<VendaDto> listarPropostaComUsuariosLogado(Long id) {
		List<Venda> vendas = vendaRepository.findByEmpresaFuncionarioId(id);
		return vendas.stream().map(venda -> modelMapper.map(venda, VendaDto.class))
				.collect(Collectors.toList());	}

}
