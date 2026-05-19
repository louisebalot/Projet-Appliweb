package pack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Serv")
public class Serv extends HttpServlet {

    private Facade facade;

    public Serv() {
        super();
    }

    @Override
    public void init() throws ServletException {
        this.facade = RestClientManager.getProxy();
    }

    private String transformerEnJson(int id_partie) {

        List<Integer> joueurs = facade.getListeJoueurs(id_partie);
        String json = "{\"status\": \"UPDATE_PLAYERS\", \"joueurs\": [";
        for (int i = 0; i < joueurs.size(); i++) {
            String surnom = facade.getSurnom(id_partie, joueurs.get(i));
            json += "\"" + surnom + "\"";
            if (i < joueurs.size() - 1) {
                json += ",";
            }
        }
        json += "]}";
        return json;
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String surnom;
        int id_joueur;
        int id_admin;
        int id_invite;
        int id_partie;
        int id_round;
        String lettre;
        int temps;

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

                String json = transformerEnJson(id_partie);
                GameWebSocket.broadcast(String.valueOf(id_partie), json);

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
                id_round = facade.demarrer_round(id_partie);
                nb_tours = facade.getNombreRounds(id_partie);
                temps = facade.getTempsRound(id_partie) * 60;
                lettre = facade.getLettreRound(id_partie);
                request.setAttribute("temps", temps);
                request.setAttribute("lettre", lettre);
                request.setAttribute("nb_rounds", nb_tours);
                request.setAttribute("round", id_round);
                request.setAttribute("partie", id_partie);
                request.setAttribute("joueur", id_joueur);
                request.getRequestDispatcher("FormulaireReponse.jsp").forward(request, response);
                break;

            case "Enregistrer_reponse":
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                id_joueur = Integer.parseInt(request.getParameter("joueur"));
                id_round = Integer.parseInt(request.getParameter("round"));

                String pays = request.getParameter("Pays");
                String ville = request.getParameter("Ville");
                String prenom = request.getParameter("Prenom");
                String couleur = request.getParameter("Couleur");
                String vegetal = request.getParameter("Vegetal");
                String animal = request.getParameter("Animal");
                String metier = request.getParameter("Metier");
                String sport = request.getParameter("Sport");

                facade.enregistrerReponse(pays, ville, prenom, couleur, vegetal, animal, metier, sport, id_partie, id_joueur,
                        id_round);

                request.setAttribute("partie", id_partie);
                request.setAttribute("joueur", id_joueur);
                request.setAttribute("round", id_round);
                
                boolean isAdmin = facade.isJoueurAdmin(id_joueur, id_partie); 

                if (isAdmin) {
                    request.getRequestDispatcher("AttenteRoundSuivantAdmin.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("AttenteRoundSuivantInvite.jsp").forward(request, response);
                }
                break;


            case "prochain_round":
                id_partie = Integer.parseInt(request.getParameter("id_partie"));
                id_joueur = Integer.parseInt(request.getParameter("joueur"));
                id_round = Integer.parseInt(request.getParameter("round"));

                boolean joueurEstAdmin = facade.isJoueurAdmin(id_joueur, id_partie);

                int codeRound;
                if (joueurEstAdmin) {
                    facade.miseAJourScore(id_partie);
                    codeRound = facade.nextRound(id_partie, id_joueur);

                } else {
                    try { Thread.sleep(400); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    int maxRounds = facade.getNombreRounds(id_partie);

                    if (id_round >= maxRounds - 1) {
                        codeRound = 2; 
                    } else {
                        codeRound = 1;
                    }
                }

                if (codeRound == 0 || codeRound == 1) {
                    lettre = facade.getLettreRound(id_partie);
                    temps = facade.getTempsRound(id_partie) * 60;
                    
                    int roundActuel = facade.getNumeroRoundActuel(id_partie); 

                    request.setAttribute("temps", temps);
                    request.setAttribute("lettre", lettre);
                    request.setAttribute("round", roundActuel);
                    request.setAttribute("nb_rounds", facade.getNombreRounds(id_partie));
                    request.setAttribute("partie", id_partie);
                    request.setAttribute("joueur", id_joueur);
                    request.getRequestDispatcher("FormulaireReponse.jsp").forward(request, response);

                } else if (codeRound == 2) {

                    if (joueurEstAdmin) {
                        facade.miseAJourScore(id_partie);
                    } else {
                        try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                    }

                    List<ScoreLigne> classement = facade.getClassementTrie(id_partie);

                    String pseudoVainqueur = classement.isEmpty() ? "Personne" : classement.get(0).getPseudo();
                    int score = facade.getScore(id_partie, id_joueur);

                    request.setAttribute("classement", classement);
                    request.setAttribute("pseudo_vainqueur", pseudoVainqueur);
                    request.setAttribute("score", score);
                    request.setAttribute("partie", id_partie);
                    request.setAttribute("joueur", id_joueur);
                    request.getRequestDispatcher("Gagnant.jsp").forward(request, response);
                }
                break;

            case "redemarrer_partie":
                id_joueur = Integer.parseInt(request.getParameter("joueur"));
                facade.resetScore(id_joueur);
                request.setAttribute("joueur", id_joueur);
                request.getRequestDispatcher("ChoixPartie.jsp").forward(request, response);
                break;
        }
    }
}