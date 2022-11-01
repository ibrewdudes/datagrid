package org.rlnieto.pruebas;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Cliente que no usa Spring Boot para acceder a Infinispan
 *
 */
public class SimpleClientApp {

    private final String SERVER = "hotrod://developer:developer@192.168.0.22:11222";
    private final String CACHE_FILE = "weatherCacheConfig.xml";
    private final String CACHE_NAME = "weather";

    private RemoteCacheManager remoteCacheManager;

    /**
     * Conecta al servidor RHDG
     */
    public void connect() {
        //------------------------------------------------------------------------------------------
        // Creamos la configuración:
        //   - Intelligence
        //   - URL
        //   - Configuración de las cachés que queremos crear
        //------------------------------------------------------------------------------------------
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.clientIntelligence(ClientIntelligence.BASIC);   // La recomendada es HASH_DISTRIBUTION_AWARE
        builder.uri(SERVER);

        //------------------------------------------------------------------------------------------
        // Tenemos la configuración de la cache que queremos crear en un fichero
        // XML y cargamos ese fichero
        // Si sabemos que la caché existe, esta parte no es necesaria, simplemente la
        // referenciamos por su nombre
        //------------------------------------------------------------------------------------------
        URI weatherCacheConfig;
        try {
            weatherCacheConfig = getClass().getClassLoader().getResource(CACHE_FILE).toURI();
        } catch (URISyntaxException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        // Si no existe en el servidor la crea en el momento de llamar al métdo ".getCache"
        builder.remoteCache(CACHE_NAME).configurationURI(weatherCacheConfig);

        //------------------------------------------------------------------------------------------
        // Conectamos al servidor. Accederemos a RHDG a través de un cache manager
        //------------------------------------------------------------------------------------------
        this.remoteCacheManager = new RemoteCacheManager(builder.build());

        // Creamos una referencia a la cache usando el caché manager. Si no existe la crea
        RemoteCache<String, String> cache = this.remoteCacheManager.getCache(CACHE_NAME);

        //------------------------------------------------------------------------------------------
        // Ya podemos grabar y leer de la cache que hemos creado/obtenido
        //------------------------------------------------------------------------------------------
        cache.put("uno", "dos");
        cache.put("tres", "cuatro");

        // Podemos leer datos de la caché
        String clave = "uno";
        String valor = cache.get(clave);
        System.out.println(String.format("Dato leído de la caché: (%s, %s)", clave, valor));

        clave = "ocho";
        valor = cache.get(clave);

        if (null == valor) {
            System.out.println(String.format("No existe la clave %s en la cache %s", clave, CACHE_NAME));
        } else {
            System.out.println(String.format("Dato leído de la caché: (%s, %s)", clave, valor));
        }

        //------------------------------------------------------------------------------------------
        // Cerramos la conexión con infinispan
        //------------------------------------------------------------------------------------------
        remoteCacheManager.close();

    }


    /**
     * main
     *
     * @param arg
     */
    public static void main(String[] arg) {

        SimpleClientApp app = new SimpleClientApp();
        app.connect();

    }
}
