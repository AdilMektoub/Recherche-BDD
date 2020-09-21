package app.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.repositories.ClientRepository;
import app.repositories.CommandeRepository;
import app.entities.*;

@Controller
public class Control {

	@Autowired
	private ClientRepository cr;
	@Autowired
	private CommandeRepository cmdR;
	
//	Client client = null ;
	
	@RequestMapping(value = "/index" , method = RequestMethod.POST)
	public String index(Model model , 
	Long idConnectedClient,
	@RequestParam(name = "mc") String mc) {
		
		System.out.println(idConnectedClient);
		System.out.println(mc);
		Client cl = cr.getOne(idConnectedClient);
		List<Commande> commandsCC = cl.getCommandes();
		if (mc.length() == 0 || mc.equals(null)) {
			System.out.println(commandsCC.size());
			model.addAttribute("listCmd", commandsCC);
		}
		else {
			List<Commande> cmdTrouves = cmdR.chercher("%"+mc+"%" , idConnectedClient);
			System.out.println(commandsCC.size());
			model.addAttribute("listCmd", cmdTrouves);
			System.out.println(mc);
			System.out.println(cmdTrouves.size());
		}
		
		model.addAttribute("mc", mc);
		model.addAttribute("idConnectedClient", idConnectedClient);
		return "login22";
	}

	@RequestMapping(value = "/login2", method = RequestMethod.GET)
	public String login2() {
		return "login2";
	}

	@RequestMapping(value = "/")
	public String home() {
		return "redirect:/login2";
	}

	@RequestMapping(value = "/login22", method = RequestMethod.GET)
	public String accueil(Model model,
		@RequestParam(name = "idConnectedClient") Long idConnectedClient) {
		Client connectedClient = cr.getOne(idConnectedClient);
		if (connectedClient != null)
			return login22(model, connectedClient.getNom(), connectedClient.getPassword());
		else
			return home();
	}

	@RequestMapping(value = "/login22", method = RequestMethod.POST)
	public String login22(Model model, @RequestParam(name = "username", defaultValue = "") String username,
			@RequestParam(name = "password", defaultValue = "") String pwd) {

		model.addAttribute("username", username);
		model.addAttribute("password", pwd);
//		model.addAttribute("mc", "");
		List<Client> clients = cr.getClientByName(username);
		boolean clientExist = false;
		for (Client client : clients) {
			if (client.getNom().equals(username) && client.getPassword().equals(pwd)) {
				clientExist = true;
				model.addAttribute("idConnectedClient", client.getId_client());
				model.addAttribute("listCmd", cr.getCommandesClient(client.getId_client()));
				if (client.isAdmin()) {
					model.addAttribute("isAdmin", true);
					List<Client> autresCli = cr.getAutresClients(client.getId_client());

					for (Client autreCli : autresCli) {
						autreCli.setCommandes(cr.getCommandesClient(autreCli.getId_client()));
					}
				}
				model.addAttribute("listClientsAutres", cr.getAutresClients(client.getId_client()));
				// model.addAttribute("listCmdAutres",
				// cr.getCommandesAutresClients(client.getId_client()));
			}
		}
		if (clientExist)
			return "login22";
		else
			return "erreur";
	}

	@RequestMapping(value = "/editCmd", method = RequestMethod.GET)
	public String editCmd(Model model, 
			@RequestParam(name = "idConnectedClient") Long idConnectedClient,
			@RequestParam(name = "id_commande") Long id_commande) {

		Client connectedClient = cr.findById(idConnectedClient).get();
		if (connectedClient != null) {
			Commande cmdToEdit = cmdR.findById(id_commande).get();
			if (cmdToEdit != null) {

				model.addAttribute("idConnectedClient", connectedClient.getId_client());
				model.addAttribute("cmd", cmdToEdit);
				return "viewEditCmd";
			} else
				return login22(model, connectedClient.getNom(), connectedClient.getPassword());

		} else
			return home();
	}

	@RequestMapping(value = "/addCmd")
	public String frmNewCmd(Model model,
			@RequestParam(name = "idConnectedClient") Long idClient) {

		Client connectedClient = cr.findById(idClient).get();
		if (connectedClient != null) {

			model.addAttribute("idConnectedClient", connectedClient.getId_client());

			Commande cmd = new Commande(connectedClient);
			cmd.setNombre(1);

			model.addAttribute("cmd", cmd);			

			return "viewEditCmd";
		} else
			return home();
	}

	@RequestMapping(value = "/saveCmd", method = RequestMethod.POST)
	public String saveCmd(Model model, @Valid Commande cmd,
			@RequestParam(name = "idConnectedClient") Long idConnectedClient, BindingResult bindingRes) {

		System.out.println("dans le post saveCmd");
		System.out.println(bindingRes.toString());

		if (bindingRes.hasErrors()) {
			System.out.println(" les erreurs dans la saisie");
			return "viewEditCmd";
		}
		Client connectedClient = cr.findById(idConnectedClient).get();

		if (connectedClient != null) {

			cmd.setClient(connectedClient);
			System.out.println(cmd.toString());
			cmdR.save(cmd);
			model.addAttribute("idConnectedClient", cmd.getClient().getId_client());
			model.addAttribute("cmd", cmd);
			return "viewConfirmationCmd";
			//return login22(model, connectedClient.getNom(), connectedClient.getPassword());
		} else
			return home();
	}

	@RequestMapping(value = "/deleteCmd", method = RequestMethod.GET)
	public String delete(Model model, Long id_commande,
			@RequestParam(name = "idConnectedClient") Long idConnectedClient) {

		cmdR.deleteById(id_commande);
		Client connectedClient = cr.findById(idConnectedClient).get();
		if (connectedClient != null) {
			model.addAttribute("idConnectedClient", connectedClient.getId_client());
			return login22(model, connectedClient.getNom(), connectedClient.getPassword());
		} else
			return home();
	}

}
