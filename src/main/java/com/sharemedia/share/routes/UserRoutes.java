package com.sharemedia.share.routes;

import com.sharemedia.share.controllers.UserController;
import com.sharemedia.share.models.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/users")
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
    public Response getUser(@PathParam("id") int id) {
        Optional<User> user = UserController.getUser(id);
        return user.isPresent() ? Response.ok(user.get(), MediaType.APPLICATION_JSON).build() : Response.serverError().build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addUser(User user) {
        return UserController.addUser(user) ? Response.ok(user.toString() + " was added", MediaType.TEXT_PLAIN).build() : Response.serverError().build();
    }
}
