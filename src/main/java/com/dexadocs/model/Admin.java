package com.dexadocs.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




@Entity
public class Admin {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nome;
	
	
	@Column
	private String email; 
	
	@Column
	private String palavrapasse;
	
	@Column
	private String imagem;
	
	@Column
	private String prev_imagem;
	
	@Column
	private String funcao;
	
	public Admin(){
		
	}


	
	
	public Admin(Long id, String nome, String email, String palavrapasse, String imagem, String funcao, String prev_imagem) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.palavrapasse = palavrapasse;
		this.imagem = imagem;
		this.funcao = funcao;
		this.prev_imagem = prev_imagem;
	}




	


	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getNome() {
		return nome;
	}




	public void setNome(String nome) {
		this.nome = nome;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getPalavrapasse() {
		return palavrapasse;
	}




	public void setPalavrapasse(String palavrapasse) {
		this.palavrapasse = palavrapasse;
	}




	public String getImagem() {
		return imagem;
	}




	public void setImagem(String imagem) {
		this.imagem = imagem;
	}




	public String getFuncao() {
		return funcao;
	}




	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}


	

	public String getPrev_imagem() {
		return prev_imagem;
	}




	public void setPrev_imagem(String prev_imagem) {
		this.prev_imagem = prev_imagem;
	}




	@Override
	public String toString() {
		return "Admin [id=" + id + ", nome=" + nome + ", email=" + email + ", palavrapasse=" + palavrapasse
				+ ", imagem=" + imagem + ", prev_imagem=" + prev_imagem + ", funcao=" + funcao + "]";
	}



	
}


