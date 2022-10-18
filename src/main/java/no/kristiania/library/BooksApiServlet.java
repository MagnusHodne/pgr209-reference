package no.kristiania.library;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BooksApiServlet extends HttpServlet {

    private final BookStorage bookStorage;
    private final JsonArrayBuilder jsonArrayBuilder;

    public BooksApiServlet(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
        jsonArrayBuilder = Json.createArrayBuilder();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        for (var book : bookStorage.listAll()) {
            jsonArrayBuilder.add(Json.createObjectBuilder()
                    .add("id", book.getId())
                    .add("title", book.getTitle())
                    .add("author", book.getAuthor()));
        }
        res.getWriter().write(jsonArrayBuilder.build().toString());
    }
}
