package br.com.adiblock.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private FuncionarioDto funcionario = new FuncionarioDto();
	private String produto;
	private String embalagem;
	private int qtdLitro;
	private double preco;
	private double precoDesconto;
	private int qtd;
	private String risco;
	
}
