package com.aliashik.filter;

import com.aliashik.annotation.SecureAPI;
import com.aliashik.constant.Role;
import com.aliashik.model.Message;
import com.aliashik.service.JWTService;
import io.jsonwebtoken.Claims;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Provider
@SecureAPI
@Priority(Priorities.AUTHENTICATION)
@Produces(MediaType.APPLICATION_JSON)
public class SecurityFilter implements ContainerRequestFilter {

    @Autowired
    private Logger logger;

    @Autowired
    private JWTService jwtService;

    @Context
    private ResourceInfo resourceInfo;

    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        String token = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = null;
        try {
            claims = jwtService.parseToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(new Message("UNAUTHORIZED", "Invalid access token"))
                            .build());
        }

        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<String> classRoles = extractRoles(resourceClass);
        Method resourceMethod = resourceInfo.getResourceMethod();
        List<String> methodRoles = extractRoles(resourceMethod);

        try {
            if (methodRoles.isEmpty()) {
                checkPermissions(classRoles, claims);
            } else {
                checkPermissions(methodRoles, claims);
            }
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity(new Message("UNAUTHORIZED", "Access denied"))
                            .build());
        }
        return;
    }

    private List<String> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<String>();
        } else {
            SecureAPI secured = annotatedElement.getAnnotation(SecureAPI.class);
            if (secured == null) {
                return new ArrayList<String>();
            } else {
                //converting @SecureAPI annotation params(list of enums)
                //to list of string as after parsing the token we get ROLES as a list of strings
                List<String> enumNames = Stream.of(secured.value())
                        .map(Enum::name)
                        .collect(Collectors.toList());
                return enumNames;
            }
        }
    }
    private void checkPermissions(List<String> allowedRoles, Claims claims) throws Exception {
        if( null == claims )
            throw new Exception("No role found in the claims");
        List<Role> userRoles = (List<Role>) claims.get("Roles");
        allowedRoles.retainAll(userRoles);

        if(allowedRoles.size() == 0) {
            throw new Exception("Forbidden");
        }

    }
}