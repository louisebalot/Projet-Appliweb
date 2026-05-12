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
        Round round;
        int id_round;
        String lettre;

        switch (request.getParameter("op")) {

            case "creer_joueur":
                surnom = request.getParameter("surnom");
                id_joueur = facade.creer_joueur(surnom, joueurs.size() + 1);
                request.setAttribute("joueur", id_joueur);
                request.getRequestDispatcher("ChoixPartie.jsp").forward(request, response);
                break;

            case "creer_partie":
                id_admin = Integer.parseInt(request.getParameter("joueur"));
                id_partie = facade.creer_partie(id_admin);
                request.setAttribute("partie", id_partie);
                request.setAttribute("joueur", id_admin);
                request.getRequestDispatcher("Lobby.jsp").forward(request, response);
                break;

            case "rejoindre_partie":
                id_invite = Integer.parseInt(request.getParameter("joueur"));
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                facade.rejoindre_partie(id_invite, id_partie);
                request.setAttribute("partie", id_partie);
                request.setAttribute("joueur", id_invite);
                request.getRequestDispatcher("AttenteInvite.jsp").forward(request, response);
                break;

            case "valider_params":
                int nb_tours = Integer.parseInt(request.getParameter("nb_tours"));
                int temps = Integer.parseInt(request.getParameter("temps"));
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                id_joueur = Integer.parseInt(request.getParameter("id_joueur"));
                id_partie = facade.setParametres(id_partie, temps, nb_tours);
                request.setAttribute("partie", id_partie);
                request.setAttribute("joueur", id_joueur);
                request.getRequestDispatcher("AttenteAdmin.jsp").forward(request, response);
                break;

            case "demarrer_round":
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                id_joueur = Integer.parseInt(request.getParameter("joueur"));
                id_round = facade.demarrer_round(id_partie, id_joueur);
                lettre = facade.getLettreRound(id_partie, id_round);
                request.setAttribute("lettre", lettre);
                request.setAttribute("round", id_round);
                request.setAttribute("partie", id_partie);
                request.setAttribute("joueur", id_joueur);
                request.getRequestDispatcher("FormulaireReponse.jsp").forward(request, response);
                break;

            case "Enregistrer_reponse":
                int id_partie_actuelle = Integer.parseInt(request.getParameter("id_partie"));
                int id_joueur_actuel = Integer.parseInt(request.getParameter("joueur"));
                int id_round_actuel = Integer.parseInt(request.getParameter("round"));

                String Pays = request.getParameter("Pays");
                String Ville = request.getParameter("Ville");
                String Prenom = request.getParameter("Prenom");
                String Couleur = request.getParameter("Couleur");
                String Fruit = request.getParameter("Fruit");
                String Animal = request.getParameter("Animal");
                String Metier = request.getParameter("Metier");

                facade.enregistrerReponse(Pays, Ville, Prenom, Couleur, Fruit, Animal, Metier, id_partie_actuelle, id_joueur_actuel, id_round_actuel);
                break;
               
            case "Calculer_points":
                
                break;
            
            case "next_round":
                // if (partie_actuelle.getNumeroRoundActuel() > partie_actuelle.getNombreRounds()){
                //     request.getRequestDispatcher("Gagnant.html").forward(request, response);
                //     break; 

                // } else {
                //     facade.nextRound(partie_actuelle);
                //     request.getRequestDispatcher("FormulaireReponse.jsp").forward(request, response);
                //     break;
                // }
                break;
            
            case "redemarrer_partie":
                request.getRequestDispatcher("Lobby.jsp").forward(request, response);
                break;
        }
    }
}