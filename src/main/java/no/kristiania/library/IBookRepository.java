package no.kristiania.library;

import java.util.List;

public interface IBookRepository {
    public void save(Book book);
    public Book retrieve(long id);
    public List<Book> listAll();
}
