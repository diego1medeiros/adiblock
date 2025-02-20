package br.com.adiblock.dto;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemVendaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private int qtde;
	private int qtdLitros;
	private double valorTotal;
	private double valorDesconto;
	private ProdutoDto produto = new ProdutoDto();
	private List<?> itensVenda;
	private VendaDto venda = new VendaDto();
	
		
	
}
