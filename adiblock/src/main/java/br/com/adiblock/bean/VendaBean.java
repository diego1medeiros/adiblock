package br.com.adiblock.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.MessagingException;

import com.google.gson.Gson;

import br.com.adiblock.dto.EmpresaDto;
import br.com.adiblock.dto.FuncionarioDto;
import br.com.adiblock.dto.ItemVendaDto;
import br.com.adiblock.dto.ProdutoDto;
import br.com.adiblock.dto.VendaDto;
import br.com.adiblock.enumeradores.TipoDeFrete;
import br.com.adiblock.service.EmpresaService;
import br.com.adiblock.service.VendaService;
import br.com.adiblock.utils.EnviarEmail;
import br.com.adiblock.utils.Message;
import br.com.adiblock.utils.Relatorio;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

@Getter
@Setter
@Named(value = "vendaBean")
@ViewScoped
public class VendaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private VendaDto venda;
	private VendaService vendaService = new VendaService();
	private EmpresaService empresaService;
	private FuncionarioBean bean = new FuncionarioBean();

	private List<ItemVendaDto> listaPedidos;

	private List<VendaDto> listaDaVenda;

	private List<ItemVendaDto> itensVenda;

	private ItemVendaDto itemVenda = new ItemVendaDto();

	private EmpresaDto empresa = new EmpresaDto();
	private FuncionarioDto funcionario;
	private List<VendaDto> listaVenda;

	// lista imformações dos itens da venda vindo do banco de dados
	@PostConstruct
	public void listaDadosDosItensDoCarrinho() {
		venda = new VendaDto();
		venda.setData(new Date());
		venda.setValorTotal(0);
		venda.setQtdeTotal(0);
		itensVenda = new ArrayList<>();
		listaDaVenda = listaDeProposta();

	}

	// adicioanr itens da venda no carrinho
	public void adicionarItens(ActionEvent evento) {
		ProdutoDto produto = (ProdutoDto) evento.getComponent().getAttributes().get("produtoSelecionado");

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
			itemVenda.setValorDesconto(produto.getPrecoDesconto());
			itemVenda.setValorTotal(produto.getPrecoDesconto() * itemVenda.getQtde());

			itemVenda.setQtdLitros(produto.getQtdLitro() * produto.getQtd());

			Message.info(itemVenda.getProduto().getProduto() + " adicionado com sucesso!!!", "");
			itensVenda.add(itemVenda);

		} else {
			ItemVendaDto itemVenda = itensVenda.get(posicaoEncontrada);
			itemVenda.setQtde(itemVenda.getQtde() + produto.getQtd());
			itemVenda.setValorDesconto(produto.getPrecoDesconto());

			itemVenda.setQtdLitros(itemVenda.getQtdLitros() + produto.getQtdLitro());
			itemVenda.setValorTotal(produto.getPrecoDesconto() * itemVenda.getQtde());

			itemVenda.setQtdLitros(produto.getQtdLitro() * itemVenda.getQtde());

			Message.info(itemVenda.getProduto().getProduto() + " adicionado com sucesso!!!", "");
		}
		limpar();
		calcularTotalDaVenda();
	}

	// calcular o valor da venda

	public void calcularTotalDaVenda() {
		venda.setValorTotal(0);
		venda.setQtdeTotal(0);
		for (ItemVendaDto itemVenda : itensVenda) {
			venda.setValorTotal(venda.getValorTotal() + itemVenda.getValorTotal());
			venda.setQtdeTotal(venda.getQtdeTotal() + itemVenda.getQtde());
		}
	}

	public void excluirItem(ActionEvent evento) {

		ItemVendaDto itemVenda = (ItemVendaDto) evento.getComponent().getAttributes().get("itemSelecionado");
		int posicaoEncontrada = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				posicaoEncontrada = posicao;
			}
		}
		if (posicaoEncontrada > -1) {
			itensVenda.remove(posicaoEncontrada);
		}
		Message.info("Item da venda excluido com sucesso!!!", "");
		calcularTotalDaVenda();
	}

	// finalizar o proposta da venda

	public void finalizarVenda(ActionEvent evento) {
		EmpresaDto empresa = (EmpresaDto) evento.getComponent().getAttributes().get("empresaSelecionado");
		try {
			venda.setItensVenda(itensVenda);
			venda.setEmpresa(empresa);
			vendaService.cadastrarVendaNoSpring(venda);
			Message.info("Venda concluida com sucesso!!!", "");
			listaDadosDosItensDoCarrinho();
		} catch (Exception e) {

		}
	}

	// excluir proposta
	public void excluirProposta(VendaDto venda) {
		vendaService.excluirVendaNoSpring(venda.getId());
		Message.info("O pedido numero " + venda.getId() + " foi excluido com sucesso!!!", "");
		listaDadosDosItensDoCarrinho();

	}

	// // imrimir os proposta
	public void getImprimirProposta(VendaDto venda)
			throws JRException, IOException, URISyntaxException, MessagingException {
		Relatorio<ItemVendaDto> report = new Relatorio<ItemVendaDto>();
		listaPedidos = listaProposta(venda.getId());
		if (listaPedidos.size() > 0) {
			report.getRelatorio(listaPedidos);
		} else {

		}
	}

	public void getEnviarEmailProposta(VendaDto venda)
			throws JRException, IOException, URISyntaxException, MessagingException {
		EnviarEmail<ItemVendaDto> report = new EnviarEmail<ItemVendaDto>();
		listaPedidos = listaProposta(venda.getId());
		if (listaPedidos.size() > 0) {
			report.getEnviarEmail(listaPedidos, venda);
			Message.info("Foi enviado o email para o Cliente " + venda.getEmpresa().getContato(), "");

		} else {

		}
	}

	public List<ItemVendaDto> listaProposta(Long id) {
		String json = vendaService.imprimirPedido(id);
		Gson gson = new Gson();
		ItemVendaDto[] lista = gson.fromJson(json, ItemVendaDto[].class);
		List<ItemVendaDto> listaPedidos = Arrays.asList(lista);
		return listaPedidos;
	}

	public List<VendaDto> listaVenda() {
		Gson gson = new Gson();
		String json = vendaService.listaVendas();
		VendaDto[] listaDaVenda = gson.fromJson(json, VendaDto[].class);
		List<VendaDto> listaVenda = Arrays.asList(listaDaVenda);
		return listaVenda;
	}

	public List<VendaDto> listaDeProposta() {
		funcionario = bean.getUsuarioLogado();
		if (funcionario.getLogin().equals("adiblock")) {
			Gson gson = new Gson();
			String json = vendaService.listaVendas();
			VendaDto[] listaDaVenda = gson.fromJson(json, VendaDto[].class);
			listaVenda = Arrays.asList(listaDaVenda);

		} else {
			String json = vendaService.listarPropostaComFuncionarioLogado(funcionario.getId());
			Gson gson = new Gson();
			VendaDto[] listaDaVenda = gson.fromJson(json, VendaDto[].class);
			listaVenda = Arrays.asList(listaDaVenda);
		}
		return listaVenda;
	}

	public String[] getTipoDeFrete() {
		return TipoDeFrete.gettipoFreteComboBox();
	}

	public void limpar() {
		venda = new VendaDto();
	}

}
