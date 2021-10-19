package ca.nexapp.conf.ddd.ws.api.calllog;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import ca.nexapp.conf.ddd.ws.api.calllog.dto.CallLogDto;

@Path("/api/telephony/calllogs")
public interface CallLogResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CallLogDto> getCallLogs();

    @DELETE
    @Path("{id}")
    void deleteCallLog(@PathParam("id") String id);
}
