package br.com.adblock.cadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adblock.cadastro.model.Funcionario;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
