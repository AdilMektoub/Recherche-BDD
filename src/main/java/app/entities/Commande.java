package app.entities;

import java.io.Serializable;
//import java.util.Date;
import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "cmd")
public class Commande implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_commande;

	@NotNull
	@Size(min = 2, max = 15)
	private String produit;

	@Min(value = 0)
	private int nombre;

	private int prix;

	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "id_client")
	private Client client;

	public Long getId_commande() {
		return id_commande;
	}

	public String getProduit() {
		return produit;
	}

	public int getNombre() {
		return nombre;
	}

	public int getPrix() {
		return prix;
	}

	public Date getDate() {
		return date;
	}

	public Client getClient() {
		return client;
	}

	public void setId_commande(Long id_commande) {
		this.id_commande = id_commande;
	}

	public void setProduit(String produit) {
		this.produit = produit;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setClient(Client client) {
		this.client = client;
//		if (this.client!=null) {
//			this.client.getCommandes().add(this);
//		}
	}

	public Commande(String produit, int nombre, int prix, Date date) {
		this.produit = produit;
		this.nombre = nombre;
		this.prix = prix;
		this.date = date;
	}

	public Commande() {
		super();		
	}

	public Commande(Client client) {
		this.date =new Date(System.currentTimeMillis());		
		this.setClient(client);
		if (this.client != null)
			this.client.getCommandes().add(this);
	}

	public int getTotal() {
		return this.getNombre() * this.getPrix();
	}

	@Override
	public String toString() {
		return "Commande [" + (id_commande != null ? "id_commande=" + id_commande + ", " : "")
				+ (produit != null ? "produit=" + produit + ", " : "") + "nombre=" + nombre + ", prix=" + prix + ", "
				+ (date != null ? "date=" + date + ", " : "")  + ", "
				+ (client != null ? "client=" + client.getNom() : "") + "]";
	}

}
