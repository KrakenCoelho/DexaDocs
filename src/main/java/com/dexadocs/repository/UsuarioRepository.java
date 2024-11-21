package com.dexadocs.repository;


import javax.validation.Valid;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dexadocs.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	/*@Query(value= "select u from Usuario like %?1%")
	List<Usuario>buscarPorNome(String nome);*/

	
	@Query(value = "select * from Usuario where email =:email and palavrapasse =:palavrapasse LIMIT 1", nativeQuery = true)
	Iterable <Usuario> login(String email,String palavrapasse);
	
	@Query(value = "select * from usuario where email =:email and palavrapasse =:palavrapasse", nativeQuery = true)
	Iterable <Usuario> verify(Object email,Object palavrapasse);
	
	
	
	@Query(value = "select u from usuario  where email =:email and palavrapasse =:palavrapasse", nativeQuery = true)
	Iterable <Usuario> verifica(Object email,Object palavrapasse);
	
	@Query(value = "select * from usuario  where id=?", nativeQuery = true)
	Iterable <Usuario> fidById(Long id);
	
	/*@Transactional()
	@Modifying
	@Query(value = "delete from Usuario where id=?", nativeQuery = true)
	int delete(int id);*/
	
	
	
	
	@Query(value = "select * FROM usuario ", nativeQuery = true)
	Iterable<Usuario> vertodos();
	
	@Modifying
	@Query(value = " UPDATE Usuario SET  `nome`= ?, `email`= ?, `cargo`= ?, `palavrapasse`= ?, 'telemovel'=? where  id = ?", nativeQuery = true)
	int update( Long id);
    
	
	@Query(value= "DELETE * FROM usuario where id=?", nativeQuery = true)
	Iterable <Usuario> apagar(Long id);

	
	
	
	/*@Query(value ="")
	Usuario save(String name, String email, String username, String password, int id);
	*/
}
