package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryServer {
    private final Server server;
    private BookStorage bookStorage = new BookStorage();

    private final Logger logger = LoggerFactory.getLogger(LibraryServer.class);
    public LibraryServer(int port){

        this.server = new Server(port);

        var apiContext = new ServletContextHandler();
        apiContext.setContextPath("/api");
        apiContext.addServlet(new ServletHolder(new BooksApiServlet()), "/books");
        var reactContext = new WebAppContext();
        reactContext.setContextPath("/");
        reactContext.setBaseResource(Resource.newClassPathResource("/webapp"));
        server.setHandler(new HandlerList(apiContext, reactContext));
    }
    public static void main(String[] args) throws Exception{
        var server = new LibraryServer(8080);
        server.start();
    }

    public void start() throws Exception {
        server.start();
        logger.info("http://" + server.getURI().getHost() + ":" + server.getURI().getPort());

        initBookStorage();
    }

    private void initBookStorage() {
        bookStorage.store(new Book(1, "The Lord of the Rings", "J.R.R. Tolkien"));
        bookStorage.store(new Book(2, "Harry Potter and the Philosopher's Stone", "J.K. Rowling"));
        bookStorage.store(new Book(3, "And Then There Were None", "Agatha Christie"));
    }

}
