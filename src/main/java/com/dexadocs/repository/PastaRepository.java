package com.dexadocs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.dexadocs.model.Pasta;
import com.dexadocs.model.Usuario;

public interface PastaRepository extends JpaRepository<Pasta, Long> {

	/*@Query(value= "select nome_pasta from pasta where upper(trim(u.nome_pasta))like %?1%")
	List<Pasta>buscarPorNome(String nome_pasta);*/
	
	
	@Query(value = "select * FROM pasta ORDER BY pasta.nome_pasta", nativeQuery = true)
	Iterable<Pasta> vertodos();
	
	@Query(value = "select * from pasta  where id_pasta=?", nativeQuery = true)
	Iterable <Pasta> fidById(Long id_pasta);
	
	@Query(value = "SELECT pasta.nome_pasta, pasta.id_pasta from pasta where ano_pasta = ?", nativeQuery = true)
	List<List> porano(String ano_pasta);
	
	@Query(value = "SELECT pasta.nome_pasta, pasta.ano_pasta from pasta where id_pasta = ?", nativeQuery = true)
	List<List> caminho(Long id_pasta);
	
	@Query(value = "SELECT pastainside.id_pastainside, pastainside.nome_pastainside   from pasta JOIN pastainside on pasta.id_pasta = pastainside.pasta_mae where id_pasta = ?", nativeQuery = true)
	List <List> esp(Long id_pasta);

}
