package com.dexadocs.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;




@Entity
public class Usuario {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String cargo;
	
	@Column
	private String email;
	
	@Column
	private String palavrapasse;
	
	@Column
	private String telemovel;
	
	@Column
	private String imagem;
	
	@Column
	private String prev_image;
	
	//@Column(nullable=false)
	@Column
	//@NotNull
	private String todas_permissoes;
		
	
	@Column()
	private String editar_perfil;
	
	@Column()
	private String editarcriar_utilizadores;
	
	@Column()
	private String criar_documento;
	
	@Column()
	private String editarapagar_documento; 
	
	@Column()
	private String partilhar;
	
	@Column()
	private String verlog;
	
	/*public Usuario(){
		
	}*/


	/*public Usuario(Long id, String nome, String cargo, String email, String palavrapasse, String telemovel, String imagem, String prev_imagem) {
		super();
		this.id =id;
		this.nome = nome;
		this.cargo = cargo;
		this.email = email;
		this.palavrapasse = palavrapasse;
		this.telemovel = telemovel;
		this.imagem = imagem;
		this.prev_image = prev_image;
	}*/

	
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



	public String getCargo() {
		return cargo;
	}



	public void setCargo(String cargo) {
		this.cargo = cargo;
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



	public String getTelemovel() {
		return telemovel;
	}



	public void setTelemovel(String telemovel) {
		this.telemovel = telemovel;
	}



	public String getImagem() {
		return imagem;
	}



	public void setImagem(String imagem) {
		this.imagem = imagem;
	}



	public String getPrev_image() {
		return prev_image;
	}



	public void setPrev_image(String prev_image) {
		this.prev_image = prev_image;
	}



	public String getTodas_permissoes() {
		return todas_permissoes;
	}



	public void setTodas_permissoes(String todas_permissoes) {
		this.todas_permissoes = todas_permissoes;
	}



	public String getEditar_perfil() {
		return editar_perfil;
	}



	public void setEditar_perfil(String editar_perfil) {
		this.editar_perfil = editar_perfil;
	}



	public String getEditarcriar_utilizadores() {
		return editarcriar_utilizadores;
	}



	public void setEditarcriar_utilizadores(String editarcriar_utilizadores) {
		this.editarcriar_utilizadores = editarcriar_utilizadores;
	}



	public String getCriar_documento() {
		return criar_documento;
	}



	public void setCriar_documento(String criar_documento) {
		this.criar_documento = criar_documento;
	}



	public String getEditarapagar_documento() {
		return editarapagar_documento;
	}



	public void setEditarapagar_documento(String editarapagar_documento) {
		this.editarapagar_documento = editarapagar_documento;
	}



	public String getPartilhar() {
		return partilhar;
	}



	public void setPartilhar(String partilhar) {
		this.partilhar = partilhar;
	}



	public String getVerlog() {
		return verlog;
	}



	public void setVerlog(String verlog) {
		this.verlog = verlog;
	}
	
	
	
	

	/*@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", cargo=" + cargo + ", email=" + email + ", palavrapasse="
				+ palavrapasse + ", telemovel=" + telemovel + ", imagem=" + imagem + ", prev_image=" + prev_image
				+ "]";
	}*/


	
	

	
	
}


