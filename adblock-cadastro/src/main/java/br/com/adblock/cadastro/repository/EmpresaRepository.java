package br.com.adblock.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.adblock.cadastro.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	List<Empresa> findByFuncionarioId(Long id);

	

	
}
