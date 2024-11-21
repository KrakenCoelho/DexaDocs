package com.dexadocs.repository;


import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dexadocs.model.Admin;
import com.dexadocs.model.Usuario;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	

	
	@Query(value = "SELECT * FROM Admin where email =:email and palavrapasse =:palavrapasse LIMIT 1", nativeQuery = true)
	Iterable <Admin> login(String email,String palavrapasse);
	
	@Query(value = "select * from Admin where email =:email and palavrapasse =:palavrapasse", nativeQuery = true)
	Iterable <Admin> verify(Object email,Object palavrapasse);
	
	
	
	@Query(value = "select u from Admin  where email =:email and palavrapasse =:palavrapasse", nativeQuery = true)
	Iterable <Admin> verifica(Object email,Object palavrapasse);
	
	@Query(value = "select * from Admin  where id=?", nativeQuery = true)
	Iterable <Admin> fidById(Long id);
	
	
	
	

	
	@Query(value = "select * FROM admin ", nativeQuery = true)
	Iterable<Admin> vertodos();
	
	@Modifying
	@Query(value = " UPDATE admin SET  nome= ?, email= ?, palavrapasse= ?, imagem=? where  id = ?", nativeQuery = true)
	int update( Long id);
	
	
	
}
