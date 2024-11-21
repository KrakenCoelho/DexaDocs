package com.dexadocs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dexadocs.model.Ficheiro;

@Repository
public interface FicheiroRepository extends JpaRepository<Ficheiro, Long> {
	
	@Query(value= "select nome_ficheiro from Ficheiro where nome_ficheiro like '?%'", nativeQuery = true)
	List<Ficheiro>buscarPorNome(String nome_ficheiro);
	
	@Query(value =  "select * from Ficheiro where nome_ficheiro LIKE %:search%", nativeQuery = true)
	Iterable <Ficheiro> buscaPornome(@Param("search") String search);
	//List<List> buscaPornome(@Param("search") String search);
	
	@Query(value = "select * FROM Ficheiro", nativeQuery = true)
	Iterable<Ficheiro> vertodos();
	
	@Query(value = "select * from ficheiro  where id_ficheiro=?", nativeQuery = true)
	Iterable <Ficheiro> fidById(Long id_ficheiro);
	
	@Query(value = "SELECT ficheiro.id_ficheiro, ficheiro.nome_ficheiro, ficheiro.arquivo, ficheiro.ano_ficheiro FROM ficheiro JOIN pasta on ficheiro.pasta_mae = pasta.id_pasta where id_pasta = ?", nativeQuery = true)
	List <List> contemP(Long id_pasta);
	
	//List<Ficheiro> findByNome_ficheiroContains (String search); //estudar este tipo de query
	
	@Query(value = "SELECT ficheiro.id_ficheiro, ficheiro.nome_ficheiro, ficheiro.arquivo, ficheiro.ano_ficheiro FROM ficheiro JOIN pastainside on ficheiro.sub_pasta = pastainside.id_pastainside where id_pastainside = ?", nativeQuery = true)
	List <List> contem(Long id_pastainside);

	@Query(value = "select id_pasta,nome_pasta from pasta join ficheiro on pasta_mae=id_pasta where id_ficheiro=?", nativeQuery = true)
	List <List> pm(Long id_fiheiro);
	
	@Query(value ="select id_pastainside,nome_pastainside from pastainside join ficheiro on sub_pasta=id_pastainside where id_ficheiro=?", nativeQuery = true)
	List <List> sp(Long id_fiheiro);
	
	@Query(value ="select * from ficheiro where id_ficheiro=?", nativeQuery = true)
	Iterable <Ficheiro> tipo(Long id_fiheiro);
	
	@Query(value = "SELECT ficheiro.id_ficheiro, nome_ficheiro FROM ficheiro JOIN pasta on ficheiro.pasta_mae = pasta.id_pasta where id_pasta = ?", nativeQuery = true)
	List <List> con(Long id_pasta);
	
	@Query(value = "SELECT nome_ficheiro,arquivo,ano_ficheiro FROM ficheiro WHERE nome_ficheiro = ?", nativeQuery = true)
	String en(String nome_ficheiro);
}
