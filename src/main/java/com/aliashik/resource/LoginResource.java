package com.aliashik.resource;

import com.aliashik.model.Message;
import com.aliashik.model.User;
import com.aliashik.service.JWTService;
import com.aliashik.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.util.Map;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;


@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)

public class LoginResource {

    @Autowired
    private Logger logger;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User user) {
        try {

            logger.info(":::: Request have reached to resource ::::");
            Map userMap = userService.getUserByUsername(user.getUsername());
            if(!userMap.get("password").equals(user.getPassword())){
                throw new Exception();
            }
            logger.info(":::: Authentication successful ::::");
            String token = jwtService.createJWT((Integer)userMap.get("userId"),
                    user.getUsername(),
                    uriInfo.getAbsolutePath().toString(),
                    1000000);
            logger.info(":::: Token creation successful...preparing response ::::");
            return Response.ok()
                    .entity(new Message("SUCCESS", "Login Successful"))
                    .header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED)
                    .entity(new Message("FAILED", "Login Unsuccessful")).build();
        }
    }
}
