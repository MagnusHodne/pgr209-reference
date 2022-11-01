package no.kristiania.library;

import java.util.List;

public interface BookRepository {
    void save(Book book);
    Book retrieve(long id);
    List<Book> listAll();
}
