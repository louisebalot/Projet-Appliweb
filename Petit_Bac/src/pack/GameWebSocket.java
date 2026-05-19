package pack;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/ws")
public class GameWebSocket {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    public static void broadcast(String text) {
        synchronized (sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    s.getAsyncRemote().sendText(text);
                }
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Nouvelle connexion : " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("JSON reçu : " + message);

        if (message.contains("LANCER_PARTIE")) {
            broadcast("{\"status\": \"START\"}");

        } else if (message.contains("STOP")) {
            broadcast("{\"status\": \"FINI\"}");
            
        } else if (message.contains("CONTINUER")) {
            broadcast("{\"status\": \"NEXT_ROUND_READY\"}");
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Connexion fermée : " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessions.remove(session);
    }
}