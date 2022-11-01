package no.kristiania.library;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.library.database.Book;
import no.kristiania.library.database.BookRepository;

import java.util.List;

@Path("/books")
public class BookEndpoint {

    @Inject
    private BookRepository bookStorage;

    @POST
    public void addBook(Book book) {
        bookStorage.save(book);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listAll() {
        return bookStorage.listAll();
    }
}
