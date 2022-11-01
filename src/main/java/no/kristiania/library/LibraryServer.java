package no.kristiania.library;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LibraryServer {
    private final Server server;
    private static final Logger logger = LoggerFactory.getLogger(LibraryServer.class);
    public LibraryServer(int port) throws IOException {
        this.server = new Server(port);
        server.setHandler(new HandlerList(createApiContext(), createWebAppContext()));
    }

    private ServletContextHandler createApiContext() {
        var apiContext = new ServletContextHandler(server, "/api");
        apiContext.addServlet(new ServletHolder(new ServletContainer(new ServerConfig())), "/*");
        return apiContext;
    }

    private WebAppContext createWebAppContext() throws IOException {
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");

        Resource resource = Resource.newClassPathResource("/webapp");
        File sourceDirectory = getSourceDirectory(resource);
        if (sourceDirectory != null) {
            context.setBaseResource(Resource.newResource(sourceDirectory));
            context.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            context.setBaseResource(resource);
        }

        return context;
    }

    private static File getSourceDirectory(Resource resource) throws IOException {
        if (resource.getFile() == null) {
            return null;
        }
        File sourceDirectory = new File(resource.getFile().getAbsolutePath()
                .replace('\\', '/')
                .replace("target/classes", "src/main/resources"));
        return sourceDirectory.exists() ? sourceDirectory : null;
    }

    public static void main(String[] args) throws Exception{
        var server = new LibraryServer(8080);
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }

    public void start() throws Exception {
        server.start();
        logger.info("Started server at {}", getURL());
    }

}
