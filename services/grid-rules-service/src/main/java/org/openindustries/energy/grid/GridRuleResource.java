package org.openindustries.energy.grid;

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

@Path("/api/v1/grid-rules")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Grid Rules", description = "Decision rules binding zone types to load and outage envelopes.")
public class GridRuleResource {

    @Inject
    GridRuleService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a rule (starts as DRAFT)")
    public Response create(@Valid CreateGridRuleRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List rules")
    public List<GridRule> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one rule")
    public GridRule get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/activate")
    @Operation(summary = "Activate a rule (lifecycle transition, no body)")
    public GridRule activate(@PathParam("id") UUID id) {
        return service.activate(id);
    }

    @POST
    @Path("/evaluate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Evaluate whether an active rule covers a request")
    public Evaluation evaluate(@Valid EvaluateRequest request) {
        return service.evaluate(request);
    }
}
