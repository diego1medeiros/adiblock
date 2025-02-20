package br.com.adblock.cadastro.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@Table(name = "vendas")
public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date data;
	@Column(name = "valor_total")
	private double valorTotal;
	private String observacao;
	@Column(name = "qtde_total")
	private int qtdeTotal;
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa = new Empresa() ;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_condicoescomercias")
	private CondicoesComercias condicoesDto = new CondicoesComercias();
	@Transient
    private List<ItemVenda> itensVenda;

	
}

