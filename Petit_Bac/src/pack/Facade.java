package pack;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/")
public interface Facade {

    @GET
    @Path("/démarrer_partie")
    @Consumes("application/json")
    void demarrer_partie(@QueryParam("nb_tours") int nb_tours, @QueryParam("temps") int temps);

    @GET
    @Path("/ajout_surnom")
    @Consumes("application/json")
    void ajout_surnom(@QueryParam("surnom") String surnom);

    @GET
    @Path("/creer_partie")
    @Consumes("application/json")
    Partie creer_partie(@QueryParam("joueur") Joueur joueur);

    @GET
    @Path("/rejoindre_partie")
    @Consumes("application/json")
    void rejoindre_partie(@QueryParam("joueur") Joueur joueur, @QueryParam("partie") Partie partie);

    @GET
    @Path("/Enregistrer_reponse")
    @Produces("application/json")
    void enregistrerReponse(@QueryParam("Pays") String Pays, @QueryParam("Ville") String Ville, @QueryParam("Prenom") String Prenom, @QueryParam("Couleur") String Couleur, @QueryParam("Fruit") String Fruit, @QueryParam("Animal") String Animal, @QueryParam("Metier") String Metier);
    
    @GET
    @Path("/calculerPoints")
    @Consumes("application/json")
    @GetMapping("/calculerPoints")
    int calculerPoints(@QueryParam("reponse") String reponse);

    @GET
    @Path("/redemarrer_partie")
    @Consumes("application/json")
    void redemarrer_partie(@QueryParam("nb_tours") int nb_tours, @QueryParam("temps") int temps);
}
