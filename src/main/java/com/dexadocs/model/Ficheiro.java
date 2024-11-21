package com.dexadocs.model;

import javax.persistence.*;




@Entity
public class Ficheiro{

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_ficheiro;
	
	@Column
	private String nome_ficheiro;
	
	@Column
	private String senha_ficheiro;
	
	@Column
	private String Ano_ficheiro;
	
	@Column
	private Long pasta_mae;
	
	@Column
	private Long sub_pasta;
	
	@Column
	private String arquivo;
	
	public Ficheiro(){
		
	}



	public Ficheiro(Long id_ficheiro, String nome_ficheiro, String senha_ficheiro, String ano_ficheiro,
			Long pasta_mae, Long sub_pasta, String arquivo) {
		super();
		this.id_ficheiro = id_ficheiro;
		this.nome_ficheiro = nome_ficheiro;
		this.senha_ficheiro = senha_ficheiro;
		Ano_ficheiro = ano_ficheiro;
		this.pasta_mae = pasta_mae;
		this.sub_pasta = sub_pasta;
		this.arquivo = arquivo;
	}



	public Long getId_ficheiro() {
		return id_ficheiro;
	}

	public void setId_ficheiro(Long id_ficheiro) {
		this.id_ficheiro = id_ficheiro;
	}








	public String getNome_ficheiro() {
		return nome_ficheiro;
	}

	public void setNome_ficheiro(String nome_ficheiro) {
		this.nome_ficheiro = nome_ficheiro;
	}








	public String getSenha_ficheiro() {
		return senha_ficheiro;
	}

	public void setSenha_ficheiro(String senha_ficheiro) {
		this.senha_ficheiro = senha_ficheiro;
	}








	public String getAno_ficheiro() {
		return Ano_ficheiro;
	}

	public void setAno_ficheiro(String ano_ficheiro) {
		Ano_ficheiro = ano_ficheiro;
	}








	public Long getPasta_mae() {
		return pasta_mae;
	}

	public void setPasta_mae(Long pasta_mae) {
		this.pasta_mae = pasta_mae;
	}








	public Long getSub_pasta() {
		return sub_pasta;
	}

	public void setSub_pasta(Long sub_pasta) {
		this.sub_pasta = sub_pasta;
	}

	
	


	public String getArquivo() {
		return arquivo;
	}



	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}



	@Override
	public String toString() {
		return "Ficheiro [id_ficheiro=" + id_ficheiro + ", nome_ficheiro=" + nome_ficheiro + ", senha_ficheiro="
				+ senha_ficheiro + ", Ano_ficheiro=" + Ano_ficheiro + ", pasta_mae=" + pasta_mae + ", sub_pasta="
				+ sub_pasta + "]";
	}

	
	
	
}


