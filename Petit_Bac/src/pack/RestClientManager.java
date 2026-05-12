package pack;

import org.apache.http.impl.conn.PoolingClientConnectionManager; // Note le nom différent ici
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;

public class RestClientManager {
    private static final String BASE_URL = "http://localhost:8080/facade"; 
    private static final Facade proxy;

    static {
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(50);

        DefaultHttpClient httpClient = new DefaultHttpClient(cm);

        ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);
        
        ResteasyClient client = new ResteasyClientBuilder().httpEngine(engine).build();
        proxy = client.target(BASE_URL).proxy(Facade.class);
    }

    public static Facade getProxy() {
        return proxy;
    }
}