package rest;

import entity.Gateway;
import entity.PeripheralDevice;
import facades.FacadeFactory;
import facades.GatewayFacade;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static rest.JSONConverter.getJSONfromObject;
import static rest.JSONConverter.getPeripheralDeviceFromJSON;

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

    @GET
    @Path("{gatewayId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGateway(@PathParam("gatewayId") String id) {

        Gateway gw = null;
        try {
            gw = GATEWAY_FACADE.getGatewayById(Long.parseLong(id));
        } catch (NoResultException nre) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No gateway with id: " + id).build();
        }

        return Response.ok().entity(getJSONfromObject(gw)).build();
    }

    @POST
    @Path("{gatewayId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDeviceToGateway(@PathParam("gatewayId") String gatewayId,
            @DefaultValue("undefined") @QueryParam("deviceId") String deviceId,
            String jsonRequest) {

        System.out.println(jsonRequest);
        Gateway gw = null;
        try {
            gw = GATEWAY_FACADE.getGatewayById(Long.parseLong(gatewayId));
        } catch (NoResultException nre) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No gateway with id: " + gatewayId).build();
        }

        PeripheralDevice device = null;
        if (deviceId.equals("undefined")) {

            device = getPeripheralDeviceFromJSON(jsonRequest);
            device = new PeripheralDevice(device.getDeviceUID(), device.getVendor(), new Date(), device.isOnline());

        } else {

            try {
                device = FacadeFactory.getPeripheralDeviceFacade().getGatewayById(Long.parseLong(deviceId));
            } catch (NoResultException nre) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No peripheral device with id: " + deviceId).build();
            }

        }
        try {
            gw.addDevice(device);
            GATEWAY_FACADE.updateGateway(gw);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
            return Response.status(Response.Status.FORBIDDEN).entity("Gateway capacity(10) reached!").build();
        }
        System.out.println(gw);
        return Response.ok().entity(getJSONfromObject(gw)).build();
    }

}
