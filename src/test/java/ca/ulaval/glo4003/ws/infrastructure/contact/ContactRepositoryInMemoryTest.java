package ca.ulaval.glo4003.ws.infrastructure.contact;

import ca.ulaval.glo4003.ws.domain.contact.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class ContactRepositoryInMemoryTest {

    private static final String CONTACT_ID = "id";

    @Mock
    private Contact contact;

    private ContactRepositoryInMemory contactRepositoryInMemory;

    @BeforeEach
    public void setUp() {
        contactRepositoryInMemory = new ContactRepositoryInMemory();
        BDDMockito.given(contact.getId()).willReturn(CONTACT_ID);
    }

    @Test
    public void givenContact_whenFindAll_ThenReturnContactInMemory() {
        //given
        contactRepositoryInMemory.save(contact);

        // when
        List<Contact> contacts = contactRepositoryInMemory.findAll();

        // then
        assertThat(contacts, org.hamcrest.Matchers.hasItem(contact));
    }

}