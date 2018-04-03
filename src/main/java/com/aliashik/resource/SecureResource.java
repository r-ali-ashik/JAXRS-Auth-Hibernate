package com.aliashik.resource;

import com.aliashik.annotation.SecureAPI;
import com.aliashik.constant.Role;
import com.aliashik.model.Message;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/echo")
public class SecureResource {

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response echo(@QueryParam("message") String message) {
        return Response.ok().entity(new Message("SUCCESSFUL", "Public api")).build();
    }

    @GET
    @Path("jwt")
    @SecureAPI({Role.ROLE_ADMIN, Role.ROLE_USER})
    @Produces(MediaType.APPLICATION_JSON)
    public Response echoWithJWTToken() {
        return Response.ok().entity(new Message("SUCCESSFUL", "Authorized api")).build();
    }
}
