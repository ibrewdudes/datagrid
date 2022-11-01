package org.rlnieto.pruebas.service;

import org.apache.catalina.connector.Response;
import org.rlnieto.pruebas.model.Book;
import org.rlnieto.pruebas.processor.BookProcessor;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import java.util.Optional;

/**
 * Book related endpoints
 *
 */
@RestController
public class BookService {
    private CacheManager cacheManager;
    private BookProcessor bookProcessor;

    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

    /**
     * Constructor
     * @param cacheManager
     */
    public BookService(CacheManager cacheManager, BookProcessor bookProcessor){
        this.cacheManager = cacheManager;
        this.bookProcessor = bookProcessor;
    }

    /**
     * Consulta de libro por ISBN
     * @param isbn
     * @return
     */
    @GetMapping("/book/{isbn}")
    public ResponseEntity populateCache(@PathVariable String isbn){

        //long startTime = System.nanoTime();
        long startTime = System.currentTimeMillis();

        ResponseEntity responseEntity = null;

        Optional<Book> book = bookProcessor.getByIsbn(isbn);
        if(book.isEmpty())
            responseEntity = ResponseEntity.status(Response.SC_NOT_FOUND).body("");
        else
            responseEntity = ResponseEntity.status(Response.SC_OK).body(book.get());

        long endTime = System.currentTimeMillis();
        LOG.info(String.format("Service time: %d ms", endTime - startTime));


        return responseEntity;
    }

}
