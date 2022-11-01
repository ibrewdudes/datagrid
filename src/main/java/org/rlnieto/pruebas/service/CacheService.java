package org.rlnieto.pruebas.service;

import org.apache.catalina.connector.Response;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.rlnieto.pruebas.processor.CacheProcessor;
import org.rlnieto.pruebas.model.Book;
import org.rlnieto.pruebas.model.OpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Cache related endpoints
 *
 */
@RestController
public class CacheService {

    @Value("${cache.books}")
    private String cacheName;

    @Autowired
    RemoteCacheManager cacheManager;

    @Autowired
    CacheProcessor cacheProcessor;


    @GetMapping("/")
    public ResponseEntity root(){
        return ResponseEntity
                .status(200)
                .body("OK");
    }

    /**
     * Creación de una cache
     * @param cacheName
     * @return
     */
    @PostMapping("/cache/{cacheName}")
    public ResponseEntity newCache(@PathVariable String cacheName){
        OpStatus opStatus = cacheProcessor.newCache(cacheName);
        int status = Response.SC_OK;    // Asumimos operacion ok
        String mensaje = "";            // Asumimos operacion ok

        if(!opStatus.isOk()){
            status = Response.SC_INTERNAL_SERVER_ERROR;
            mensaje = opStatus.getText();
        }

        return ResponseEntity
                .status(status)
                .body(mensaje);

    }

    /**
     * Borrado de una cache
     * @param cacheName
     * @return
     */
    @DeleteMapping("/cache/{cacheName}")
    public ResponseEntity removeCache(@PathVariable String cacheName){
        OpStatus opStatus = cacheProcessor.removeCache(cacheName);

        int status = Response.SC_OK;    // Asumimos operacion ok
        String mensaje = "";            // Asumimos operacion ok

        if(!opStatus.isOk()){
            status = Response.SC_INTERNAL_SERVER_ERROR;
            mensaje = opStatus.getText();
        }

        return ResponseEntity
                .status(status)
                .body(mensaje);


    }

}
