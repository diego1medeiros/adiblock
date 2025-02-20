package br.com.adblock.cadastro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto {

	private Long id;
	private FuncionarioDto funcionario;
	private String produto;
	private String embalagem;
	private int qtdLitro;
	private double preco;
	private String risco;

}
