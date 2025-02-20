package br.com.adblock.cadastro.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDto {
	
	private Long id;
	private String razaoSocial;
	private String cnpj;
	private String telefone;
	private String email;
	private String inscricao;
	private EnderecoDto endereco ;
	private String contato;
	private FuncionarioDto funcionario;


}
