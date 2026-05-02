package pack;

import javax.ws.rs.*;

@Path("/")
public interface Facade {

    @POST
    @Path("/creer_joueur")
    @Consumes("application/json")
    Joueur creer_joueur(@QueryParam("surnom") String surnom, @QueryParam("id") int id);

    @POST
    @Path("/creer_partie")
    @Consumes("application/json")
    Partie creer_partie(@QueryParam("joueur") Joueur joueur, @QueryParam("id") int id);

    @POST
    @Path("/setParametres")
    @Consumes("application/json")
    void setParametres(@QueryParam("partie") Partie partie, @QueryParam("temps") int temps,
                       @QueryParam("nb_tours") int nb_tours);

    @POST
    @Path("/rejoindre_partie")
    @Consumes("application/json")
    void rejoindre_partie(@QueryParam("joueur") Joueur joueur, @QueryParam("partie") Partie partie);

    @POST
    @Path("/Enregistrer_reponse")
    @Consumes("application/json")
    void enregistrerReponse(@QueryParam("pays") String pays, @QueryParam("ville") String ville,
                            @QueryParam("prenom") String prenom, @QueryParam("couleur") String couleur,
                            @QueryParam("fruit") String fruit, @QueryParam("animal") String animal,
                            @QueryParam("metier") String metier);

    @POST
    @Path("/Calculer_points")
    @Consumes("application/json")
    int calculerPoints(@QueryParam("reponse") String reponse);

    @POST
    @Path("/Redemarrer_partie")
    @Consumes("application/json")
    void redemarrer_partie(@QueryParam("nb_tours") int nb_tours, @QueryParam("temps") int temps);
}
