package br.com.adblock.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adblock.cadastro.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    List<Produto> findByFuncionarioId(Long id);

}
