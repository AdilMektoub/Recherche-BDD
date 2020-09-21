package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import app.entities.Commande;

@CrossOrigin("*")
@RepositoryRestResource
public interface CommandeRepository extends JpaRepository<Commande, Long> {

	@Query("select c from Commande c where c.produit like :x and c.client.id_client like :y")
	public List<Commande> chercher(@Param("x")String mc ,
			@Param("y")Long id_client);
}
