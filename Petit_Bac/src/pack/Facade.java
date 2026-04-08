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
    @Path("/Enregistrer_reponse")
    @Produces("application/json")
    void enregistrerReponse(@QueryParam("Pays") String Pays, @QueryParam("Ville") String Ville, @QueryParam("Prenom") String Prenom, @QueryParam("Couleur") String Couleur, @QueryParam("Fruit") String Fruit, @QueryParam("Animal") String Animal, @QueryParam("Metier") String Metier);
}
