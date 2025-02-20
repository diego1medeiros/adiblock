package br.com.adblock.cadastro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDto {


	private Long id;
	private String login;
	private String senha;
	private String nomeFuncionario;
}
