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
        switch (request.getParameter("op")) {

            case "creer_joueur":
                String surnom = request.getParameter("surnom");
                Joueur joueur = facade.creer_joueur(surnom, joueurs.size() + 1);
                joueurs.add(joueur);
                request.setAttribute("joueur", joueur);
                request.getRequestDispatcher("CreerPartie.jsp").forward(request, response);
                break;

            case "creer_partie":
                int id_admin = Integer.parseInt(request.getParameter("joueur"));
                Joueur admin = joueurs.get(id_admin - 1);
                Partie partie = facade.creer_partie(admin);
                parties.add(partie);
                request.setAttribute("partie", partie);
                request.setAttribute("joueur", admin);
                request.getRequestDispatcher("Lobby.jsp").forward(request, response);
                break;

            case "rejoindre_partie":
                int id_invite = Integer.parseInt(request.getParameter("joueur"));
                Joueur invite = joueurs.get(id_invite - 1);
                int id_partie = Integer.parseInt(request.getParameter("id_partie"));
                Partie partie2 = parties.get(id_partie - 1);
                partie2.ajouterJoueur(invite);
                request.setAttribute("partie", partie2);
                request.setAttribute("joueur",invite);
                request.getRequestDispatcher("Attente.html").forward(request, response);
                break;

            case "demarrer_round":
                int nb_tours = Integer.parseInt(request.getParameter("nb_tours"));
                int temps = Integer.parseInt(request.getParameter("temps"));
                int id_partie2 = Integer.parseInt(request.getParameter("id_partie"));
                Partie partie3 = parties.get(id_partie2 - 1);
                int id_joueur = Integer.parseInt(request.getParameter("joueur"));
                Joueur joueur2 = joueurs.get(id_joueur - 1);
                request.setAttribute("partie", partie3);
                request.setAttribute("joueur",joueur2);
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
                formulaireReponse.ajouterJoueurs(partie.getJoueurs());

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