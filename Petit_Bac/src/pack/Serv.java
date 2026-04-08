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
                case "demarrer_partie":
                    int nb_tours = Integer.parseInt(request.getParameter("nb_tours"));
                    int temps = Integer.parseInt(request.getParameter("temps"));
                    //facade.demarrer_partie(nb_tours, temps);
                    request.getRequestDispatcher("FormulaireReponse.html").forward(request, response);
                break;
                case "ajout_surnom":
                    String surnom = request.getParameter("surnom");
                    //facade.ajout_surnom(surnom);
                    request.getRequestDispatcher("lobby.html").forward(request, response);
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