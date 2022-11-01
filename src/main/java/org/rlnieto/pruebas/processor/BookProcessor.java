package org.rlnieto.pruebas.processor;

import org.rlnieto.pruebas.model.Book;
import org.rlnieto.pruebas.model.BookRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookProcessor {

    private BookRepository bookRepository;

    /**
     * Constructor
     * @param bookRepository
     */
    public BookProcessor(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    /**
     * Get a book by isbn (key)
     * @param isbn
     * @return
     */
    @Cacheable("booksCache")
    public Optional<Book> getByIsbn(String isbn){
        Optional<Book> book = bookRepository.findById(isbn);

        this.simulateSlowService();
        return bookRepository.findById(isbn);
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
