package pack;

import jakarta.websocket.*;
import java.io.IOException;
import java.util.Set;
import java.util.Collections;
import java.util.HashSet;

@ServerEndpoint("/Serv")
public class GameWebSocket {

    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("Message reçu : " + message);
        
        // Si le message contient "LANCER_PARTIE", on prévient tous les joueurs
        if (message.contains("LANCER_PARTIE")) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendText("{\"status\": \"START\"}");
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session); 
    }
}