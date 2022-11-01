package org.rlnieto.pruebas.utils;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.jgroups.annotations.Property;
import org.rlnieto.pruebas.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;


/**
 * Hace una carga inicial de la cache para las pruebas
 * Si la cache no existe la crea
 *
 */
@Component
public class CacheLoader {
    private RemoteCacheManager cacheManager;

    @Value("${cache.books}")
    private String cacheName;

    public CacheLoader(RemoteCacheManager cacheManager){
        this.cacheManager = cacheManager;
    }

    public int load(){

        // Recuperamos una referencia a la cache. Si no existe la creamos
        RemoteCache<String, Book> booksCache = cacheManager.administration().getOrCreateCache(cacheName, DefaultTemplate.DIST_SYNC);

        /*Book myBook = new Book("ISBN0001", "Titulo1", "Autor1", 100, "Editorial1");
        booksCache.put(myBook.getIsbn(), myBook);

        myBook = new Book("ISBN0002", "Titulo2", "Autor2", 200, "Editorial2");
        booksCache.put(myBook.getIsbn(), myBook);
        */
        return 2;
    }

}
