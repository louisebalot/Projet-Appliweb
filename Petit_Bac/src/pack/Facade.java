package pack;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/")
public interface Facade {

    /**
     * Créer un joueur à partir de son surnom (pseudo).
     *
     * @param surnom surnom du joueur
     * @return id du joueur
     */
    @POST
    @Path("/creer_joueur")
    @Consumes("application/json")
    int creer_joueur(@QueryParam("surnom") String surnom);

    /**
     * Créer une partie à partir de l'id d'un joueur.
     *
     * @param id_admin id de l'admin
     * @return id de la partie
     */
    @POST
    @Path("/creer_partie")
    @Consumes("application/json")
    int creer_partie(@QueryParam("id_admin") int id_admin);

    @POST
    @Path("/getListePseudos")
    @Consumes("application/json")
    List<String> getListePseudos(@QueryParam("id_partie") int id_partie);

    /**
     * Mets en place les paramètres de la partie avant son départ.
     *
     * @param id_partie id de la partie
     * @param temps     temps d'un round
     * @param nb_tours  nombre de rounds
     * @return id de la partie
     */
    @POST
    @Path("/setParametres")
    @Consumes("application/json")
    int setParametres(@QueryParam("id_partie") int id_partie, @QueryParam("temps") int temps,
                      @QueryParam("nb_tours") int nb_tours);

    /**
     * Ajoute un joueur à la partie.
     *
     * @param id_joueur id du joueur à ajouter
     * @param id_partie id de la partie
     */
    @POST
    @Path("/rejoindre_partie")
    @Consumes("application/json")
    void rejoindre_partie(@QueryParam("id_joueur") int id_joueur, @QueryParam("id_partie") int id_partie);

    /**
     * "Démarre" le round
     *
     * @param id_partie id de la partie
     * @return numéro du round actuel
     */
    @POST
    @Path("/demarrer_round")
    @Consumes("application/json")
    int demarrer_round(@QueryParam("id_partie") int id_partie);

    @POST
    @Path("/getTempsRound")
    @Consumes("application/json")
    int getTempsRound(@QueryParam("id_partie") int id_partie);


    @POST
    @Path("/getNumeroRoundActuel")
    @Consumes("application/json")
    int getNumeroRoundActuel(@QueryParam("id_partie") int id_partie);


    @POST
    @Path("/getNombreRounds")
    @Consumes("application/json")
    int getNombreRounds(@QueryParam("id_partie") int id_partie);

    /**
     * Obtenir la lettre du round actuel
     *
     * @param id_partie id de la partie
     * @return lettre du round actuel
     */
    @POST
    @Path("/getLettreRound")
    @Consumes("application/json")
    String getLettreRound(@QueryParam("id_partie") int id_partie);

    /**
     * Enregistrer les réponses d'un joueur
     *
     * @param pays      réponse de la catégorie {@code PAYS}
     * @param ville     réponse de la catégorie {@code VILLE}
     * @param prenom    réponse de la catégorie {@code PRENOM}
     * @param couleur   réponse de la catégorie {@code COULEUR}
     * @param vegetal   réponse de la catégorie {@code VEGETAL}
     * @param animal    réponse de la catégorie {@code ANIMAL}
     * @param metier    réponse de la catégorie {@code METIER}
     * @param id_partie id de la partie
     * @param id_joueur id du joueur
     * @param id_round  id du round
     */
    @POST
    @Path("/enregistrer_reponse")
    @Consumes("application/json")
    void enregistrerReponse(@QueryParam("pays") String pays, @QueryParam("ville") String ville,
                            @QueryParam("prenom") String prenom, @QueryParam("couleur") String couleur,
                            @QueryParam("vegetal") String vegetal, @QueryParam("animal") String animal,
                            @QueryParam("metier") String metier, @QueryParam("sport") String sport,
                            @QueryParam("id_partie") int id_partie, @QueryParam("id_joueur") int id_joueur, 
                            @QueryParam("id_round") int id_round);

    /**
     * Passe au prochain round si possible
     *
     * @param id_partie id de la partie
     * @return envoie {@code true} s'il y a un prochain round, {@code false} sinon
     */
    @POST
    @Path("/next_round")
    @Consumes("application/json")
    int nextRound(@QueryParam("id_partie") int id_partie, @QueryParam("id_joueur") int id_joueur);

    /**
     * Incrémente les scores des joueurs de la partie en fonction du formulaire du
     * round.
     * <p>
     * !! Peut nécessiter que les bases de données des catégories soient lancées
     *
     * @param id_partie
     */
    @POST
    @Path("/mise_a_jour_score")
    @Consumes("application/json")
    void miseAJourScore(@QueryParam("id_partie") int id_partie);


    @POST
    @Path("/isPartieFinie")
    @Consumes("application/json")
    boolean isPartieFinie(@QueryParam("id_partie") int id_partie);

    /**
     * Renvoie l'id du joueur ayant le plus grand score
     *
     * @param id_partie id de la partie
     * @return id du joueur qui a le plus grand score
     */
    @POST
    @Path("/get_vainqueur")
    @Consumes("application/json")
    int getVainqueur(@QueryParam("id_partie") int id_partie);

    @POST
    @Path("/isJoueurAdmin")
    @Consumes("application/json")
    boolean isJoueurAdmin(@QueryParam("id_joueur") int id_joueur, @QueryParam("id_partie") int id_partie);

    @POST
    @Path("/reset_score")
    @Consumes("application/json")
    void resetScore(@QueryParam("id_joueur") int id_joueur);

    @POST
    @Path("/get_score")
    @Consumes("application/json")
    int getScore(@QueryParam("id_partie") int id_partie, @QueryParam("id_joueur") int id_joueur);

    @POST
    @Path("/attendre_reponses")
    @Consumes("application/json")
    void attendreReponses(@QueryParam("id_partie") int id_partie, @QueryParam("id_round") int id_round);
}