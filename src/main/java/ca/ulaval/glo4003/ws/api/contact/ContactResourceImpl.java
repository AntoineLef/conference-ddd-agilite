package ca.ulaval.glo4003.ws.api.contact;


import ca.ulaval.glo4003.ws.api.contact.dto.ContactDto;
import ca.ulaval.glo4003.ws.domain.contact.ContactNotFoundException;
import ca.ulaval.glo4003.ws.domain.contact.ContactService;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class ContactResourceImpl implements ContactResource {

    private ContactService contactService;

    public ContactResourceImpl(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public List<ContactDto> getContacts() {
        return contactService.findAllContacts();
    }

    @Override
    public ContactDto getContact(String id) {
        return contactService.findContact(id);
    }

    @Override
    public void addContact(ContactDto contactDto) {
        contactService.addContact(contactDto);
    }

    @Override
    public void updateContact(String id, ContactDto contactDto) {
        try {
            contactService.updateContact(id, contactDto);
        } catch (ContactNotFoundException e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build());
        }
    }

    @Override
    public void deleteContact(String id) {
        contactService.deleteContact(id);
    }
}
