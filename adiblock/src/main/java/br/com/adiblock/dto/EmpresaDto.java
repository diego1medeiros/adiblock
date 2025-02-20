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
public class EmpresaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String razaoSocial;
	private String contato;
	private String cnpj;
	private String telefone;
	private String email;
	private String inscricao;
	private FuncionarioDto funcionario = new FuncionarioDto();
	private EnderecoDto endereco = new EnderecoDto();

	

}
