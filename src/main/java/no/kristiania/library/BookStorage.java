package no.kristiania.library;

import java.util.ArrayList;

public class BookStorage {
    private final ArrayList<Book> books = new ArrayList<>();

    public void store(Book book) {
        books.add(book);
    }

    public ArrayList<Book> listAll() {
        return books;
    }

    public Book retrieve(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void delete(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}
