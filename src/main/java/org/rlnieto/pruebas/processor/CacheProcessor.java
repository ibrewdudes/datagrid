package org.rlnieto.pruebas.processor;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.exceptions.HotRodClientException;
import org.rlnieto.pruebas.model.Book;
import org.rlnieto.pruebas.model.OpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Operaciones con caches
 *
 */
@Component
public class CacheProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CacheProcessor.class);
    private RemoteCacheManager cacheManager;

    /**
     * Constructor
     *
     * @param cacheManager
     */
    public CacheProcessor(RemoteCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    /**
     * Crea una cache remota de tipo síncrono
     *
     * @param cacheName
     * @return
     */
    public OpStatus newCache(String cacheName) {

        // Creamos la cache
        try {
            RemoteCache<String, Book> booksCache = cacheManager.administration().createCache(cacheName, DefaultTemplate.DIST_SYNC);
        } catch (HotRodClientException e) {
            LOG.info(String.format("La cache %s ya existe", cacheName));
            return new OpStatus(false, e.getMessage());
        } catch (Exception e) {
            LOG.error(String.format("No se pudo crear la cache %s: %s", cacheName, e.getMessage()));
            return new OpStatus(false, e.getMessage());
        }
        LOG.info(String.format("Se creó la cache %s", cacheName));

        return new OpStatus(true);

    }

    /**
     * Borra una caché remota
     *
     * @param cacheName
     */
    public OpStatus removeCache(String cacheName) {
        // TODO: parece que no lanza ninguna excepción si la cache no existe
        //       pero no estoy seguro ¿Compruebo antes que existe? <- parece que no la está borrando!!!
        cacheManager.administration().removeCache(cacheName);
        return new OpStatus(true);
    }

}
