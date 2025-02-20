package br.com.adblock.cadastro.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Endereco {
	
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String numero;
	private String cep;

}
