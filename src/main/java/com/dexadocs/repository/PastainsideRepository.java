package com.dexadocs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import com.dexadocs.model.Pastainside;
import com.dexadocs.model.Usuario;

public interface PastainsideRepository extends JpaRepository<Pastainside, Long> {

	/*@Query(value= "select nome_pasta from pasta where upper(trim(u.nome_pasta))like %?1%")
	List<Pasta>buscarPorNome(String nome_pasta);*/
	
	
	@Query(value = "select * FROM pastainside", nativeQuery = true)
	Iterable<Pastainside> vertodos();
	
	@Query(value = "select * from pastainside  where id_pastainside=?", nativeQuery = true)
	Iterable <Pastainside> fidById(Long id_pastainside);
	
	@Query(value ="SELECT  pastainside.nome_pastainside, pasta.nome_pasta FROM pastainside JOIN pasta ON pasta.id_pasta = pastainside.pasta_mae where id_pastainside = ?", nativeQuery = true)
	List <List> esp(Long id_pastainside);
	
	@Query(value = "SELECT * FROM pastainside WHERE pasta_mae =? ", nativeQuery = true)
	Iterable<Pastainside> espec();
	
	@Query(value = "SELECT * FROM pastainside WHERE pasta_mae =? ", nativeQuery = true)
	Iterable<Pastainside> espec2(Long pasta_mae);
}
