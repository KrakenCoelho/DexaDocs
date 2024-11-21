package com.dexadocs.model;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ActiUsua {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @GenericGenerator(name = "hy", strategy = "native")
	private Long id_act;

	// @Column por ser tabela com chave estrangeira não pode usar esta anotação
	@ManyToOne(optional = true)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "biju")) // para criar chave estrangeira sem usar o
																				// mysql
	private Usuario ceusuario;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ficheiro_id", foreignKey = @ForeignKey(name = "dama")) // para criar chave estrangeira sem usar
																				// o mysql
	private Ficheiro ficheiro_id;

	@Column
	private String data;

	@Column
	private String acao;

	@Column
	private String hora;

	@Column
	private String usa;

	public ActiUsua() {

	}

	

	public ActiUsua(Long id_act, Usuario ceusuario, Ficheiro ficheiro_id, String data, String acao, String hora,
			String usa) {
		super();
		this.id_act = id_act;
		this.ceusuario = ceusuario;
		this.ficheiro_id = ficheiro_id;
		this.data = data;
		this.acao = acao;
		this.hora = hora;
		this.usa = usa;
	}



	public Usuario getCeusuario() {
		return ceusuario;
	}

	public void setCeusuario(Usuario ceusuario) {
		this.ceusuario = ceusuario;
	}

	public Long getId_act() {
		return id_act;
	}

	public void setId_act(Long id) {
		this.id_act = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Ficheiro getFicheiro_id() {
		return ficheiro_id;
	}

	public void setFicheiro_id(Ficheiro ficheiro_id) {
		this.ficheiro_id = ficheiro_id;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getUsa() {
		return usa;
	}

	public void setUsa(String usa) {
		this.usa = usa;
	}

}
