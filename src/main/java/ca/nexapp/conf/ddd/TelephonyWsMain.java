package ca.nexapp.conf.ddd;

import java.net.URI;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import ca.nexapp.conf.ddd.ws.api.contact.ContactResource;
import ca.nexapp.conf.ddd.ws.api.contact.ContactResourceImpl;
import ca.nexapp.conf.ddd.ws.domain.contact.Contact;
import ca.nexapp.conf.ddd.ws.domain.contact.ContactAssembler;
import ca.nexapp.conf.ddd.ws.domain.contact.ContactRepository;
import ca.nexapp.conf.ddd.ws.domain.contact.ContactService;
import ca.nexapp.conf.ddd.ws.infrastructure.contact.ContactDevDataFactory;
import ca.nexapp.conf.ddd.ws.infrastructure.contact.ContactRepositoryInMemory;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class TelephonyWsMain {
    public static boolean isDev = true; // Would be a JVM argument or in a .property file
    public static final String BASE_URI = "http://localhost:8080/";

    public static void main(String[] args)
            throws Exception {

        // Setup resources (API)
        ContactResource contactResource = createContactResource();

        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(contactResource).to(ContactResource.class);
            }
        };

        final ResourceConfig config = new ResourceConfig();
        config.register(binder);
        config.packages("ca.nexapp.conf.ddd.ws.api");


        try {
            // Setup http server
            final Server server =
                    JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);


            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Shutting down the application...");
                    server.stop();
                    System.out.println("Done, exit.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }));

            System.out.println(
                    String.format("Application started.%nStop the application using CTRL+C"));

            // block and wait shut down signal, like CTRL+C
            Thread.currentThread().join();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }


    }

    private static ContactResource createContactResource() {
        // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
        ContactRepository contactRepository = new ContactRepositoryInMemory();

        // For development ease
        if (isDev) {
            ContactDevDataFactory contactDevDataFactory = new ContactDevDataFactory();
            List<Contact> contacts = contactDevDataFactory.createMockData();
            contacts.stream().forEach(contactRepository::save);
        }

        ContactAssembler contactAssembler = new ContactAssembler();
        ContactService contactService = new ContactService(contactRepository, contactAssembler);

        return new ContactResourceImpl(contactService);
    }

}
