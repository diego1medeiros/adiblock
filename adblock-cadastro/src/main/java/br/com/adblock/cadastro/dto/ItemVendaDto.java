package br.com.adblock.cadastro.dto;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemVendaDto {

	private Long id;
	private int qtde;
	private int qtdLitros;
	private double valorDesconto;

	private double valorTotal;
	private List<?> itensVenda;
	private VendaDto venda;
	private ProdutoDto produto;
}
