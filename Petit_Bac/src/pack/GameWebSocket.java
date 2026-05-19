package pack;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{idPartie}")
public class GameWebSocket {

    private static final Map<String, Set<Session>> parties = new ConcurrentHashMap<>();

    private final Facade facade = RestClientManager.getProxy();

    public static void broadcast(String idPartie, String text) {
        Set<Session> sessions = parties.get(idPartie);
        if (sessions != null) {
            synchronized (sessions) {
                for (Session s : sessions) {
                    if (s.isOpen()) {
                        s.getAsyncRemote().sendText(text);
                    }
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("idPartie") String idPartie) {
        parties.computeIfAbsent(idPartie, k -> Collections.synchronizedSet(new HashSet<>())).add(session);
        System.out.println("Partie " + idPartie + " -> Nouvelle connexion : " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("idPartie") String idPartie) throws IOException {
        System.out.println("Partie " + idPartie + " -> Message reçu : " + message);

        if ("DEMANDER_SCORES".equals(message)) {
            try {
                int id_partie_int = Integer.parseInt(idPartie);
                String jsonScores = transformerEnJsonScore(id_partie_int);
                broadcast(idPartie, jsonScores);
            } catch (NumberFormatException e) {
                System.err.println("ID de partie invalide dans le WS : " + idPartie);
            }

        } else if ("LANCER_PARTIE".equals(message)) {
            broadcast(idPartie, "{\"status\": \"START\"}");

        } else if ("STOP".equals(message)) {
            broadcast(idPartie, "{\"status\": \"FINI\"}");
            
        } else if ("CONTINUER".equals(message)) {
            broadcast(idPartie, "{\"status\": \"NEXT_ROUND_READY\"}");
        }
    }

    private String transformerEnJsonScore(int id_partie) {
        List<Integer> joueurs = facade.getListeJoueurs(id_partie);
        
        StringBuilder json = new StringBuilder();
        json.append("{\"status\": \"END_GAME\", \"scores\": [");
        
        for (int i = 0; i < joueurs.size(); i++) {
            int id_joueur = joueurs.get(i);
            String surnom = facade.getSurnom(id_partie, id_joueur);
            int scoreJoueur = facade.getScoreJoueur(id_partie, id_joueur); 
            
            json.append("{\"pseudo\": \"").append(surnom).append("\", \"score\": ").append(scoreJoueur).append("}");
            
            if (i < joueurs.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]}");
        return json.toString();
    }

    @OnClose
    public void onClose(Session session, @PathParam("idPartie") String idPartie) {
        Set<Session> sessions = parties.get(idPartie);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                parties.remove(idPartie);
            }
        }
        System.out.println("Partie " + idPartie + " -> Connexion fermée : " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("idPartie") String idPartie) {
        Set<Session> sessions = parties.get(idPartie);
        if (sessions != null) {
            sessions.remove(session);
        }
    }
}