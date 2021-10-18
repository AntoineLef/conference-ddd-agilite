package ca.ulaval.glo4003;

import ca.ulaval.glo4003.ws.api.calllog.CallLogResource;
import ca.ulaval.glo4003.ws.api.calllog.CallLogResourceImpl;
import ca.ulaval.glo4003.ws.api.contact.ContactResource;
import ca.ulaval.glo4003.ws.api.contact.ContactResourceImpl;
import ca.ulaval.glo4003.ws.domain.calllog.CallLog;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogAssembler;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogRepository;
import ca.ulaval.glo4003.ws.domain.calllog.CallLogService;
import ca.ulaval.glo4003.ws.domain.contact.Contact;
import ca.ulaval.glo4003.ws.domain.contact.ContactAssembler;
import ca.ulaval.glo4003.ws.domain.contact.ContactRepository;
import ca.ulaval.glo4003.ws.domain.contact.ContactService;
import ca.ulaval.glo4003.ws.http.CORSResponseFilter;
import ca.ulaval.glo4003.ws.infrastructure.calllog.CallLogDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.calllog.CallLogRepositoryInMemory;
import ca.ulaval.glo4003.ws.infrastructure.contact.ContactDevDataFactory;
import ca.ulaval.glo4003.ws.infrastructure.contact.ContactRepositoryInMemory;
import org.eclipse.jetty.server.Server;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.List;

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
        CallLogResource callLogResource = createCallLogResource();


        final AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(contactResource).to(ContactResource.class);
                bind(callLogResource).to(CallLogResource.class);
            }
        };

        final ResourceConfig config = new ResourceConfig();
        config.register(binder);
        config.register(new CORSResponseFilter());
        config.packages("ca.ulaval.glo4003.ws.api");


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

    private static CallLogResource createCallLogResource() {
        // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
        CallLogRepository callLogRepository = new CallLogRepositoryInMemory();

        // For development ease
        if (isDev) {
            CallLogDevDataFactory callLogDevDataFactory = new CallLogDevDataFactory();
            List<CallLog> callLogs = callLogDevDataFactory.createMockData();
            callLogs.stream().forEach(callLogRepository::save);
        }

        CallLogAssembler callLogAssembler = new CallLogAssembler();
        CallLogService callLogService = new CallLogService(callLogRepository, callLogAssembler);

        return new CallLogResourceImpl(callLogService);
    }
}
