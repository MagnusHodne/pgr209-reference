package no.kristiania.library;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BooksApiServlet extends HttpServlet {

    private final BookStorage bookStorage;

    public BooksApiServlet(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.getWriter().print(bookStorage.listAll());
    }
}
