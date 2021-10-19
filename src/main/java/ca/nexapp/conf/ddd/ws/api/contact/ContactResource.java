package ca.nexapp.conf.ddd.ws.api.contact;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import ca.nexapp.conf.ddd.ws.api.contact.dto.ContactDto;

@Path("/api/telephony/contacts")
public interface ContactResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ContactDto> getContacts();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    ContactDto getContact(@PathParam("id") String id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void addContact(ContactDto contactDto);

    @PUT
    @Path("{id}")
    void updateContact(@PathParam("id") String id,
                       ContactDto contactDto);

    @DELETE
    @Path("{id}")
    void deleteContact(@PathParam("id") String id);
}
