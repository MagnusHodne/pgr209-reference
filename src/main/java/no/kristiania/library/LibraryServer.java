package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

public class LibraryServer {
    private final Server server;
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
    }

}
