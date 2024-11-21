package com.dexadocs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dexadocs.model.ActiUsua;

@Repository
public interface ActusuaRepository extends JpaRepository<ActiUsua, Long> {
	
	/*@Query(value= "select u from Usuario like %?1%")
	List<Usuario>buscarPorNome(String nome);*/

	
	
	
	@Query(value = "select * from acti_usua  where ficheiro_id=?", nativeQuery = true)
	Iterable <ActiUsua> fidById2(Long id_usuario);
	
	
	@Query(value = "select * from acti_usua  where id_usuario=?", nativeQuery = true)
	Iterable <ActiUsua> fidById(Long id_usuario);
	
	@Query(value = "select acti_usua.data, acti_usua.acao from acti_usua  where id_usuario=?", nativeQuery = true)
	List <List> fid(Long id_usuario);
	
	/*@Transactional()
	@Modifying
	@Query(value = "delete from Usuario where id=?", nativeQuery = true)
	int delete(int id);*/
	
	
	
	
	

	
	
	
	/*@Query(value ="")
	Usuario save(String name, String email, String username, String password, int id);
	*/
}
