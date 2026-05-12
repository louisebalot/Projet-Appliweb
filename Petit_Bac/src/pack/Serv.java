package pack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Serv")
public class Serv extends HttpServlet {

    public Serv() {
        super();
    }

    private Facade facade;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String surnom;
        int id_joueur;
        int id_admin;
        int id_invite;
        int id_partie;
        int id_round;
        String lettre;
        int temps;

        this.facade = RestClientManager.getProxy();

        switch (request.getParameter("op")) {

            case "creer_joueur":
                surnom = request.getParameter("surnom");
                id_joueur = facade.creer_joueur(surnom);
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
                temps = Integer.parseInt(request.getParameter("temps"));
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
                temps = facade.getTempsRound(id_partie);
                temps *= 60;
                lettre = facade.getLettreRound(id_partie, id_round);
                request.setAttribute("temps", temps);
                request.setAttribute("lettre", lettre);
                request.setAttribute("round", id_round);
                request.setAttribute("partie", id_partie);
                request.setAttribute("joueur", id_joueur);
                request.getRequestDispatcher("FormulaireReponse.jsp").forward(request, response);
                break;


            case "Enregistrer_reponse":
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                id_joueur = Integer.parseInt(request.getParameter("joueur"));
                id_round = Integer.parseInt(request.getParameter("round"));

                String Pays = request.getParameter("Pays");
                String Ville = request.getParameter("Ville");
                String Prenom = request.getParameter("Prenom");
                String Couleur = request.getParameter("Couleur");
                String Fruit = request.getParameter("Fruit");
                String Animal = request.getParameter("Animal");
                String Metier = request.getParameter("Metier");

                facade.enregistrerReponse(Pays, Ville, Prenom, Couleur, Fruit, Animal, Metier, id_partie, id_joueur, id_round);

                // Calcul des points
                //facade.miseAJourScore(id_partie);
                
                // Renvoyer soit vers le prochain round, soit vers l'écran des résultats selon le nombre de rounds restants
                if (facade.nextRound(id_partie)) {

                    lettre = facade.getLettreRound(id_partie, id_round);
                    request.setAttribute("lettre", lettre);
                    request.setAttribute("round", id_round + 1);
                    request.setAttribute("partie", id_partie);
                    request.setAttribute("joueur", id_joueur);
                    request.getRequestDispatcher("FormulaireReponse.jsp").forward(request, response);
                } else {

                    // TODO Il faut surement mettre à jour des paramètres mais je ne sais pas lesquels
                    request.getRequestDispatcher("Gagnant.jsp").forward(request, response);
                }

                break;
            
            case "redemarrer_partie":
                request.getRequestDispatcher("Lobby.jsp").forward(request, response);
                break;
        }
    }
}