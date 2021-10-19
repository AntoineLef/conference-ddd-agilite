package ca.nexapp.conf.ddd.ws;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.nexapp.conf.ddd.TelephonyWsMain;

import static io.restassured.RestAssured.get;

@ExtendWith(MockitoExtension.class)
public class ContactResourceIT {

    private static Thread t;

    @BeforeAll
    public static void setUp()
            throws Exception {

        t = new Thread() {
            public void run() {
                try {
                    TelephonyWsMain.main(new String[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }

    @AfterAll
    public static void tearDown() {
        t.interrupt();
    }

    @Test
    public void givenContacts_whenGetAllContacts_thenContactsReturned() {
        get("/api/telephony/contacts").then().body("name", Matchers.hasItem("Steve Jobs"));
    }
}
