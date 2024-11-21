package com.dexadocs.model;

import javax.persistence.*;




@Entity
public class Pasta{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_pasta;
	
	@Column
	private String nome_pasta;
	
	@Column
	private String senha_pasta;
	
	@Column
	private String ano_pasta;
	
	
	
	
	public Pasta(){
		
	}




	public Pasta(Long id_pasta, String nome_pasta, String senha_pasta, String ano_pasta) {
		super();
		this.id_pasta = id_pasta;
		this.nome_pasta = nome_pasta;
		this.senha_pasta = senha_pasta;
		this.ano_pasta = ano_pasta;
	}




	public Long getId_pasta() {
		return id_pasta;
	}




	public void setId_pasta(Long id_pasta) {
		this.id_pasta = id_pasta;
	}




	public String getNome_pasta() {
		return nome_pasta;
	}




	public void setNome_pasta(String nome_pasta) {
		this.nome_pasta = nome_pasta;
	}




	public String getSenha_pasta() {
		return senha_pasta;
	}




	public void setSenha_pasta(String senha_pasta) {
		this.senha_pasta = senha_pasta;
	}




	




	public String getAno_pasta() {
		return ano_pasta;
	}




	public void setAno_pasta(String ano_pasta) {
		this.ano_pasta = ano_pasta;
	}




	@Override
	public String toString() {
		return "Pasta [id_pasta=" + id_pasta + ", nome_pasta=" + nome_pasta + ", senha_pasta=" + senha_pasta
				+ ", ano_pasta=" + ano_pasta + "]";
	}

	


	
	
	
	
}


