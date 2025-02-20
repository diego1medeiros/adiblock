package br.com.adblock.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adblock.cadastro.model.ItemVenda;


public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long>{

    List<ItemVenda> findByVendaId(Long id);

}
