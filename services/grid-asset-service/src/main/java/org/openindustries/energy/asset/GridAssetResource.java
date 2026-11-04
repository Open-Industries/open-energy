package org.openindustries.energy.asset;

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

@Path("/api/v1/grid-assets")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Grid Assets", description = "Registry of grid assets (CIM concept-level shapes).")
public class GridAssetResource {

    @Inject
    GridAssetService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a gridasset")
    public Response create(@Valid CreateGridAssetRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<GridAsset> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public GridAsset get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/maintain")
    @Operation(summary = "Maintain this gridasset (lifecycle transition, no body)")
    public GridAsset maintain(@PathParam("id") UUID id) {
        return service.maintain(id);
    }

    @POST
    @Path("/{id}/retire")
    @Operation(summary = "Retire this gridasset (lifecycle transition, no body)")
    public GridAsset retire(@PathParam("id") UUID id) {
        return service.retire(id);
    }
}
