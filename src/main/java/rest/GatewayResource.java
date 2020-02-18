package rest;

import entity.Gateway;
import entity.PeripheralDevice;
import facades.FacadeFactory;
import facades.GatewayFacade;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
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

        return Response.ok().entity(getJSONfromObject(gw)).build();
    }

    @PUT
    @Path("{gatewayId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeDeviceFromGateway(@PathParam("gatewayId") String gatewayId,
            @DefaultValue("undefined") @QueryParam("deviceId") String deviceId) {

        Gateway gw = null;
        try {
            gw = GATEWAY_FACADE.getGatewayById(Long.parseLong(gatewayId));
        } catch (NoResultException nre) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No gateway with id: " + gatewayId).build();
        }
        PeripheralDevice device = null;
        if (deviceId.equals("undefined")) {

            return Response.status(Response.Status.BAD_REQUEST).entity("Query param deviceId missing!").build();

        } else {

            try {
                device = FacadeFactory.getPeripheralDeviceFacade().getGatewayById(Long.parseLong(deviceId));
            } catch (NoResultException nre) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No peripheral device with id: " + deviceId).build();
            }

        }

        if (!gw.removeDevice(device)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Device not part of gateway!").build();
        }
        
        GATEWAY_FACADE.updateGateway(gw);
        return Response.ok().entity(getJSONfromObject(gw)).build();
    }

}
