package pack;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/")
public interface Facade {

    @POST
    @Path("/creer_joueur")
    @Consumes("application/json")
    int creer_joueur(@QueryParam("surnom") String surnom);

    @POST
    @Path("/creer_partie")
    @Consumes("application/json")
    int creer_partie(@QueryParam("id_admin") int id_admin);

    @POST
    @Path("/getListePseudos")
    @Consumes("application/json")
    List<String> getListePseudos(@QueryParam("id_partie") int id_partie);

    @POST
    @Path("/setParametres")
    @Consumes("application/json")
    int setParametres(@QueryParam("id_partie") int id_partie, @QueryParam("temps") int temps,
                       @QueryParam("nb_tours") int nb_tours);

    @POST
    @Path("/rejoindre_partie")
    @Consumes("application/json")
    void rejoindre_partie(@QueryParam("id_joueur") int id_joueur, @QueryParam("id_partie") int id_partie);

    @POST
    @Path("/demarrer_round")
    @Consumes("application/json")
    int demarrer_round(@QueryParam("id_partie") int id_partie, @QueryParam("id_joueur") int id_joueur);

    @POST
    @Path("/getTempsRound")
    @Consumes("application/json")
    int getTempsRound(@QueryParam("id_partie") int id_partie);

    @POST
    @Path("/getLettreRound")
    @Consumes("application/json")
    String getLettreRound(@QueryParam("id_partie") int id_partie, @QueryParam("id_round") int id_round);

    @POST
    @Path("/Enregistrer_reponse")
    @Consumes("application/json")
    void enregistrerReponse(@QueryParam("Pays") String pays, @QueryParam("Ville") String ville,
                            @QueryParam("Prenom") String prenom, @QueryParam("Couleur") String couleur,
                            @QueryParam("Fruit") String fruit, @QueryParam("Animal") String animal,
                            @QueryParam("Metier") String metier, @QueryParam("id_partie") int id_partie, @QueryParam("id_joueur") int id_joueur, @QueryParam("id_round") int id_round);
    
    @POST
    @Path("/next_round")
    @Consumes("application/json")
    boolean nextRound(@QueryParam("id_partie") int id_partie);

    @POST
    @Path("/Redemarrer_partie")
    @Consumes("application/json")
    void redemarrer_partie(@QueryParam("nb_tours") int nb_tours, @QueryParam("temps") int temps);

    @POST
    @Path("/Mise_a_jour_score")
    @Consumes("application/json")
    void miseAJourScore(@QueryParam("id_partie") int id_partie);
}
