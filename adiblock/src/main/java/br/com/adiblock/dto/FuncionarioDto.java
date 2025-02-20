package br.com.adiblock.dto;


import java.io.Serializable;

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
public class FuncionarioDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String login;
	private String senha;
	private String nomeFuncionario;

}
