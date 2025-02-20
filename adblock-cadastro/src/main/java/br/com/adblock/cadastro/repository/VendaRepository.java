package br.com.adblock.cadastro.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.adblock.cadastro.model.Venda;


public interface VendaRepository extends JpaRepository<Venda, Long>{

    @Query("SELECT SUM(v.valorTotal) FROM Venda v WHERE Date(v.data) = :data")
	Double sumValorTotalByData(@Param("data")Date data);

    List<Venda> findByEmpresaFuncionarioId(Long funcionarioId);
}
