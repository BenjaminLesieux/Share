package com.sharemedia.share.routes;

import com.sharemedia.share.auth.AuthRequired;
import com.sharemedia.share.controllers.UserController;
import com.sharemedia.share.models.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Optional;

@Path("/users")
@Provider
public class UserRoutes {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        Optional<List<User>> users = UserController.getUsers();
        return users.isPresent() ? Response.ok(users, MediaType.APPLICATION_JSON).build() : Response.serverError().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @AuthRequired
    public Response getUser(@PathParam("id") int id) {
        Optional<User> user = UserController.getUser(id);
        return user.isPresent() ? Response.ok(user.get(), MediaType.APPLICATION_JSON).build() : Response.serverError().build();
    }
}
