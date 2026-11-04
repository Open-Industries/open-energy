package org.openindustries.energy.dispatch;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/dispatch")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Dispatch", description = "Guarded dispatch capacity per asset and the dispatch orders against it.")
public class DispatchResource {

    @Inject
    DispatchService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a dispatchcapacity")
    public Response open(@Valid OpenDispatchCapacityRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public DispatchCapacity get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/orders")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public DispatchOrder consume(@PathParam("id") UUID id, @Valid CreateDispatchOrderRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/orders")
    @Operation(summary = "List consumption records of an account")
    public List<DispatchOrder> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
