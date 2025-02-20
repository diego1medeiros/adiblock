package br.com.adiblock.dto;


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
public class CondicoesComerciasDto {

	private Long id; 
	private String pagamento;
	private String frete;
	private String entrega;
	
}
