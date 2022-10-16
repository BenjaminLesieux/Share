package com.sharemedia.share.routes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.common.hash.Hashing;
import com.sharemedia.share.controllers.UserController;
import com.sharemedia.share.models.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Path("/auth")
public class AuthRoutes {

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(@QueryParam("userID") int userID, @QueryParam("password") String password) {
        Optional<User> user = UserController.connectUser(userID, password);

        if (user.isPresent()) {
            Algorithm algorithm = Algorithm.HMAC256("sharemedia");
            String token = JWT.create().withSubject(user.get().getPassword()).withIssuer("auth0").sign(algorithm);
            return Response.ok(token).build();
        }

        return Response.ok().build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        try {
            String hashedPassword = Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString();
            user.setPassword(hashedPassword);

            boolean response = UserController.addUser(user);

            Algorithm algorithm = Algorithm.HMAC256("sharemedia");
            String token = JWT.create().withSubject(hashedPassword).withIssuer("auth0").sign(algorithm);

            return response ? Response.ok(token).build() : Response.status(401).build();
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }

        return Response.serverError().build();
    }
}
