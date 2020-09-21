package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.repositories.ClientRepository;
import app.repositories.CommandeRepository;



@SpringBootApplication
public class SpringClientCommandeAdminManagementApplication {
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CommandeRepository commandeRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringClientCommandeAdminManagementApplication.class, args);
	}

	public void run(String... args) throws Exception {
		System.out.println("Application started");
	}
}
