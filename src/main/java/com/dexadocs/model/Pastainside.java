package com.dexadocs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Pastainside {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_pastainside;
	
	@Column
	private String nome_pastainside;
	
	@Column
	private String senha_pastainside;
	
	@Column
	private String ano_pastainside;
	
	@Column
	private Long pasta_mae;
	
	public Pastainside() {
		
	}
	
	
	
	public Pastainside(Long id_pastainside, String nome_pastainside, String senha_pastainside, String ano_pastainside, Long pasta_mae) {
		super();
		this.id_pastainside = id_pastainside;
		this.nome_pastainside = nome_pastainside;
		this.senha_pastainside = senha_pastainside;
		this.pasta_mae = pasta_mae;
		this.ano_pastainside = ano_pastainside;
	}



	public Long getId_pastainside() {
		return id_pastainside;
	}

	public void setId_pastainside(Long id_pastainside) {
		this.id_pastainside = id_pastainside;
	}

	public String getNome_pastainside() {
		return nome_pastainside;
	}

	public void setNome_pastainside(String nome_pastainside) {
		this.nome_pastainside = nome_pastainside;
	}

	public String getSenha_pastainside() {
		return senha_pastainside;
	}

	public void setSenha_pastainside(String senha_pastainside) {
		this.senha_pastainside = senha_pastainside;
	}

	public String getAno_pastainside() {
		return ano_pastainside;
	}

	public void setAno_pastainside(String ano_pastainside) {
		this.ano_pastainside = ano_pastainside;
	}



	public Long getPasta_mae() {
		return pasta_mae;
	}



	public void setPasta_mae(Long pasta_mae) {
		this.pasta_mae = pasta_mae;
	}



	@Override
	public String toString() {
		return "Pastainside [id_pastainside=" + id_pastainside + ", nome_pastainside=" + nome_pastainside
				+ ", senha_pastainside=" + senha_pastainside + ", ano_pastainside=" + ano_pastainside + ", pasta_mae="
				+ pasta_mae + "]";
	}
	
	
	
}
