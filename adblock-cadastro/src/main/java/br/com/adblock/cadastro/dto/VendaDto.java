package br.com.adblock.cadastro.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendaDto {

	private Long id;
	private Date data;
	private double valorTotal;
	private String observacao;
	private int qtdeTotal;
	private List<ItemVendaDto> itensVenda;
	private EmpresaDto empresa;
	private CondicoesComerciasDto comerciasDto; 
		
	
}
