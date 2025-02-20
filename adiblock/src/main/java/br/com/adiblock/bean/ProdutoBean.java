package br.com.adiblock.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import com.google.gson.Gson;

import br.com.adiblock.dto.FuncionarioDto;
import br.com.adiblock.dto.ItemVendaDto;
import br.com.adiblock.dto.ProdutoDto;
import br.com.adiblock.dto.VendaDto;
import br.com.adiblock.enumeradores.TipoEmbalagem;
import br.com.adiblock.service.ProdutoService;
import br.com.adiblock.utils.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Named(value = "produtobean")
@RequestScoped

public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private FuncionarioBean bean = new FuncionarioBean();

	private VendaDto venda = new VendaDto();
	private ItemVendaDto itemVenda = new ItemVendaDto();
	private List<ItemVendaDto> itensVenda;
	private List<VendaDto> listaDaVenda;
	private ProdutoDto[] listaDeProduto;

	@Inject
	private ProdutoDto produtoDto;

	@Inject
	private ProdutoService produtoService;
	private FuncionarioDto funcionario;

	public List<String> getTipoEmbalagem() {
		return Arrays.asList(TipoEmbalagem.getEmbalagmeComboBox());
	}

	public String cadastrarProduto() {
		try {
			if (produtoDto.getId() == null) {
				FuncionarioDto funcionario = bean.getUsuarioLogado();
				produtoDto.getFuncionario().setId(funcionario.getId());
				;
				produtoService.cadastrarProdutoNoBancoDeDados(produtoDto);
				Message.info("Cadastro", "Produto  cadastrado com Sucesso!!!");

			} else {
				produtoService.atualizarrProdutoNoBancoDeDados(produtoDto);
				Message.info("Atualização", "Produto  atualizado com Sucesso!!!");
			}
			listarProdutos();
			limpar();
		} catch (Exception e) {
		}
		return null;
	}

	// lista produtos do banco de dados
	@PostConstruct
	public void listarProdutos() {

		funcionario = bean.getUsuarioLogado();
		if (funcionario.getLogin().equals("adiblock")) {
			String json = produtoService.listarProduto();
			Gson gson = new Gson();
			listaDeProduto = gson.fromJson(json, ProdutoDto[].class);
		} else {
			String json = produtoService.buscarProdutosPeloLogin(funcionario.getId());
			Gson gson = new Gson();
			listaDeProduto = gson.fromJson(json, ProdutoDto[].class);
		}
		venda = new VendaDto();
		venda.setData(new Date());
		venda.setValorTotal(0);
		venda.setQtdeTotal(0);
		itensVenda = new ArrayList<>();
	}

	public void excluirProduto(ProdutoDto produtoDto) {
		try {
			produtoService.excluirProdutoNoBancoDeDados(produtoDto.getId());
			Message.info("Excluir", "Produto excluido com Sucesso!!!");
			listarProdutos();
			limpar();
		} catch (Exception e) {
		}
	}

	// limpar dados da tela
	public void limpar() {
		produtoDto = new ProdutoDto();
	}

	public List<SelectItem> getListaNome() {
		List<SelectItem> lista = new ArrayList<>();
		FuncionarioDto funcionario = bean.getUsuarioLogado();
		String json = produtoService.buscarProdutosPeloLogin(funcionario.getId());
		Gson gson = new Gson();
		ProdutoDto[] listaDeProdutos = gson.fromJson(json, ProdutoDto[].class);
		for (ProdutoDto produtoDto : listaDeProdutos) {
			lista.add(new SelectItem(produtoDto.getId(), produtoDto.getProduto()));
		}
		return lista;
	}

	public void encontraProduto() {
		String json = produtoService.buscarProduto(produtoDto.getId());
		Gson gson = new Gson();
		ProdutoDto produto = gson.fromJson(json, ProdutoDto.class);
		produtoDto = new ProdutoDto(produto.getId(), produto.getFuncionario(), produto.getProduto(),
				produto.getEmbalagem(), produto.getQtdLitro(), produto.getPreco(), 0, 0, produto.getRisco());

	}

	public void adicionarItens() {
		ProdutoDto produto = produtoDto;
		int posicaoEncontrada = -1;
		for (int posicao = 0; posicao < itensVenda.size() && posicaoEncontrada < 0; posicao++) {
			ItemVendaDto itemVenda = itensVenda.get(posicao);
			if (itemVenda.getProduto().getId().equals(produto.getId())) {
				posicaoEncontrada = posicao;
			}
		}
		if (posicaoEncontrada < 0) {
			ItemVendaDto itemVenda = new ItemVendaDto();
			itemVenda.setProduto(produto);
			itemVenda.setQtde(produto.getQtd());
			itemVenda.setQtdLitros(produto.getQtdLitro());
			itemVenda.setValorTotal(produto.getPrecoDesconto());
			itemVenda.setValorTotal(produto.getPrecoDesconto() * itemVenda.getQtde());

			Message.info(itemVenda.getProduto().getProduto() + " adicionado com sucesso!!!", "");
			itensVenda.add(itemVenda);

		} else {
			ItemVendaDto itemVenda = itensVenda.get(posicaoEncontrada);
			itemVenda.setQtde(itemVenda.getQtde() + produto.getQtd());
			itemVenda.setQtdLitros(itemVenda.getQtdLitros() + produto.getQtdLitro());
			itemVenda.setValorTotal(produto.getPrecoDesconto() * itemVenda.getQtde());
			Message.info(itemVenda.getProduto().getProduto() + " adicionado com sucesso!!!", "");

		}
		calcularTotalDaVenda();
	}

	public void calcularTotalDaVenda() {
		venda.setValorTotal(0);
		venda.setQtdeTotal(0);
		for (ItemVendaDto itemVenda : itensVenda) {
			venda.setValorTotal(venda.getValorTotal() + itemVenda.getValorTotal());
			venda.setQtdeTotal(venda.getQtdeTotal() + itemVenda.getQtde());
		}
	}

}
