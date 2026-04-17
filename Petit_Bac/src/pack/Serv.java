package pack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Serv")
public class Serv extends HttpServlet {

    final String path = "http://localhost:8080/facade";
    Facade facade;
    java.util.List<Partie> parties = new java.util.ArrayList<>();
    java.util.List<Joueur> joueurs = new java.util.ArrayList<>();

    public Serv() {
        super();
    }

    @Override
    public void init() throws ServletException {
        try {
            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target(UriBuilder.fromPath(path));
            facade = target.proxy(Facade.class);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la connexion à la Facade REST", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String surnom;
        int id_joueur;
        Joueur joueur;
        int id_admin;
        Joueur admin;
        int id_invite;
        Joueur invite;
        int id_partie;
        Partie partie;

        switch (request.getParameter("op")) {

            case "creer_joueur":
                surnom = request.getParameter("surnom");
                joueur = facade.creer_joueur(surnom, joueurs.size() + 1);
                joueurs.add(joueur);
                request.setAttribute("joueur", joueur);
                request.getRequestDispatcher("ChoixPartie.jsp").forward(request, response);
                break;

            case "creer_partie":
                id_admin = Integer.parseInt(request.getParameter("joueur"));
                admin = joueurs.get(id_admin - 1);
                id_partie = parties.size() + 1;
                partie = facade.creer_partie(admin, id_partie);
                parties.add(partie);
                request.setAttribute("partie", partie);
                request.setAttribute("joueur", admin);
                request.getRequestDispatcher("Lobby.jsp").forward(request, response);
                break;

            case "rejoindre_partie":
                id_invite = Integer.parseInt(request.getParameter("joueur"));
                invite = joueurs.get(id_invite - 1);
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                partie = parties.get(id_partie - 1);
                partie.ajouterJoueur(invite);
                request.setAttribute("partie", partie);
                request.setAttribute("joueur",invite);
                request.getRequestDispatcher("AttenteInvite.jsp").forward(request, response);
                break;

            case "valider_params":
                int nb_tours = Integer.parseInt(request.getParameter("nb_tours"));
                int temps = Integer.parseInt(request.getParameter("temps"));
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                partie = parties.get(id_partie - 1);
                id_joueur = Integer.parseInt(request.getParameter("id_joueur"));
                joueur = joueurs.get(id_joueur - 1);
                facade.setParametres(partie, temps, nb_tours);
                request.setAttribute("partie", partie);
                request.setAttribute("joueur",joueur);
                request.getRequestDispatcher("AttenteAdmin.jsp").forward(request, response);
                break;

            case "demarrer_round":
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                partie = parties.get(id_partie - 1);
                id_joueur = Integer.parseInt(request.getParameter("joueur"));
                joueur = joueurs.get(id_joueur - 1);
                request.setAttribute("partie", partie);
                request.setAttribute("joueur",joueur);
                request.getRequestDispatcher("FormulaireReponse.jsp").forward(request, response);
                break;

            case "Enregistrer_reponse":
                int id_partie_actuelle = Integer.parseInt(request.getParameter("id_partie"));
                int id_joueur_actuel = Integer.parseInt(request.getParameter("joueur"));

                String Pays = request.getParameter("Pays");
                String Ville = request.getParameter("Ville");
                String Prenom = request.getParameter("Prenom");
                String Couleur = request.getParameter("Couleur");
                String Fruit = request.getParameter("Fruit");
                String Animal = request.getParameter("Animal");
                String Metier = request.getParameter("Metier");

                Partie partie_actuelle = parties.get(id_partie_actuelle - 1);

                Formulaire formulaireReponse = new Formulaire();
                formulaireReponse.ajouterJoueurs(partie_actuelle.getJoueurs());

                Joueur joueur_actuel = joueurs.get(id_joueur_actuel - 1);

                facade.enregistrerReponse(Pays, Ville, Prenom, Couleur, Fruit, Animal, Metier);

                partie_actuelle.prochainRound();

                if (partie_actuelle.getNumeroRoundActuel() > partie_actuelle.getNombreRounds()){
                    request.getRequestDispatcher("Gagnant.html").forward(request, response);
                    break; 

                } else {
                    request.getRequestDispatcher("FormulaireReponse.jsp").forward(request, response);
                    break;
                }

            case "redemarrer_partie":
                request.getRequestDispatcher("Lobby.jsp").forward(request, response);
                break;
        }
    }
}