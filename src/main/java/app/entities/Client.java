package app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Client implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id_client")
	private Long id_client;
	private String nom;
	private String prenom;
	private int age;
	private String password;
	private String 	admin;
	
	@OneToMany (mappedBy = "client", fetch = FetchType.EAGER)//, fetch = FetchType.EAGER
	List<Commande> commandes = new ArrayList<Commande>();
	
	
	public Long getId_client() {
		return id_client;
	}


	public String getNom() {
		return nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public int getAge() {
		return age;
	}


	public String getPassword() {
		return password;
	}


	public List<Commande> getCommandes() {
		return commandes;
	}


	public void setId_client(Long id_client) {
		this.id_client = id_client;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}


	public Client(String nom, String prenom, int age, String password) {
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.password = password;
	}


	public Client() {
		super();		
	}


	public String getAdmin() {
		return admin;
	}


	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	public boolean isAdmin() {
		return this.admin.equals("admin");
	}

}
