package ca.ulaval.glo4003.ws.api.calllog;


import ca.ulaval.glo4003.ws.api.calllog.dto.CallLogDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/telephony/calllogs")
public interface CallLogResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CallLogDto> getCallLogs();

    @DELETE
    @Path("{id}")
    void deleteCallLog(@PathParam("id") String id);
}
