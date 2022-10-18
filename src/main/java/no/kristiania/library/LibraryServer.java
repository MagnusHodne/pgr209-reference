package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class LibraryServer {
    private final Server server;
    private BookStorage bookStorage = new BookStorage();

    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);

    private static WebAppContext createWebApp() throws IOException {
        var webContext = new WebAppContext();
        webContext.setContextPath("/");

        var resources = Resource.newClassPathResource("/webapp");
        var sourceDirectory = new File(resources.getFile().getAbsoluteFile().toString()
                .replace('\\', '/')
                //Copy the frontend code to the target directory
                .replace("target/classes", "src/main/resources"));
        if (sourceDirectory.isDirectory()) {
            webContext.setBaseResource(Resource.newResource(sourceDirectory));
            webContext.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            webContext.setBaseResource(resources);
        }
        return webContext;
    }
    public LibraryServer(int port) throws IOException {

        this.server = new Server(port);

        var apiContext = new ServletContextHandler();
        apiContext.setContextPath("/api");
        apiContext.addServlet(new ServletHolder(new BooksApiServlet(bookStorage)), "/books");
        server.setHandler(new HandlerList(apiContext, createWebApp()));
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
