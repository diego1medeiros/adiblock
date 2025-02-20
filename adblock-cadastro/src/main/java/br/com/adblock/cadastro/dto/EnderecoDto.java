package br.com.adblock.cadastro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDto {

	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String numero;
	private String cep;


}
