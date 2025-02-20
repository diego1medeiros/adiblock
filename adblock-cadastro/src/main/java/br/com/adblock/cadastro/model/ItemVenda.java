package br.com.adblock.cadastro.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "itemvendas")
public class ItemVenda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int qtde;
	@Column(name = "qtdlitros")
	private int qtdLitros;
	@Column(name = "valordesconto")
	private double valorDesconto;
	@Column(name = "valor_total")
	private double valorTotal;
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto = new Produto();
	@ManyToOne
	@JoinColumn(name = "id_venda")
	private Venda venda = new Venda();

}
