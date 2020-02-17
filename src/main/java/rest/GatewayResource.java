package rest;

import entity.Gateway;
import facades.FacadeFactory;
import facades.GatewayFacade;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static rest.JSONConverter.getJSONfromObject;

@Path("gateway")
public class GatewayResource {

    private final GatewayFacade GATEWAY_FACADE;

    public GatewayResource() {
        GATEWAY_FACADE = FacadeFactory.getGatewayFacade();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        List<Gateway> gateways = GATEWAY_FACADE.getAllGateways();
        return Response.ok().entity(getJSONfromObject(gateways)).build();
    }

}
