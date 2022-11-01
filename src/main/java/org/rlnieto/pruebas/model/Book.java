package org.rlnieto.pruebas.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="book")
public class Book implements Serializable {
    @Id
    private @NonNull String isbn;
    private @NonNull String title;
    private @NonNull String author;
    private @NonNull int paginas;
    private @NonNull String editorial;

    public Book(){}
}
