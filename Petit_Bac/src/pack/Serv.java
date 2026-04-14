package pack;

import java.io.IOException;

import java.sql.SQLException;

import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Serv")
public class Serv extends HttpServlet {
    
    final String path ="http://localhost:8080/facade";
    Facade facade;
    java.util.List<Partie> parties = new java.util.ArrayList<>();

    public Serv() throws ClassNotFoundException, SQLException {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(UriBuilder.fromPath(path));
        facade = target.proxy(Facade.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            switch(request.getParameter("op")){
                
                case "creer_joueur":
                    String surnom = request.getParameter("surnom");
                    Joueur joueur = facade.creer_joueur(surnom);
                    request.setAttribute("joueur", joueur);
                    request.getRequestDispatcher("CreerPartie.jsp").forward(request, response);
                break;
                
                case "creer_partie":
                    Joueur admin = request.getParameter("joueur");
                    Partie partie = facade.creer_partie(admin);
                    parties.add(partie);
                    partie.setId(parties.size());
                    request.setAttribute("partie", partie);
                    request.getRequestDispatcher("lobby.html").forward(request, response);
                break;

                case "rejoindre_partie":
                        Joueur joueur2 = request.getParameter("joueur");
                        int id_partie = Integer.parseInt(request.getParameter("id_partie"));
                        Partie partie2 = parties.get(id_partie - 1);
                        partie2.ajouterJoueur(joueur2);
                        request.getRequestDispatcher("lobby.html").forward(request, response);
                break;

                case "demarrer_partie":
                    int nb_tours = Integer.parseInt(request.getParameter("nb_tours"));
                    int temps = Integer.parseInt(request.getParameter("temps"));
                    //facade.demarrer_partie(nb_tours, temps);
                    request.getRequestDispatcher("FormulaireReponse.html").forward(request, response);
                break;

                case "Enregistrer_reponse":
                    String Pays = request.getParameter("Pays");
                    String Ville = request.getParameter("Ville");
                    String Prenom = request.getParameter("Prenom");
                    String Couleur = request.getParameter("Couleur");
                    String Fruit = request.getParameter("Fruit");
                    String Animal = request.getParameter("Animal");
                    String Metier = request.getParameter("Metier");
                    //facade.enregistrerReponse(Pays, Ville, Prenom, Couleur, Fruit, Animal, Metier);
                    request.getRequestDispatcher("lobby.html").forward(request, response);
                break;
                case "redemarrer_partie":
                    //facade.redemarrer_partie(nb_tours, temps);
                    request.getRequestDispatcher("FormulaireReponse.html").forward(request, response);
                break;
            }
    }
}