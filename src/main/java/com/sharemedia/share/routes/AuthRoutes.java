package com.sharemedia.share.routes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonParser;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.sharemedia.share.auth.AuthRequired;
import com.sharemedia.share.auth.Credentials;
import com.sharemedia.share.auth.TokenDecoder;
import com.sharemedia.share.controllers.UserController;
import com.sharemedia.share.models.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.message.internal.Token;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Path("/auth")
public class AuthRoutes {

    @GET
    @Path("/decode")
    @Produces(MediaType.APPLICATION_JSON)
    @AuthRequired
    public Response decode(@HeaderParam("Authorization") String auth) {
        return Response.ok(TokenDecoder.decodeToken(auth)).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logIn(Credentials credentials) {
        Optional<User> user = UserController.connectUser(credentials.getUserID(), credentials.getPassword());

        if (user.isPresent()) {
            Algorithm algorithm = Algorithm.HMAC256("sharemedia");

            Map<String, String> payload = new HashMap<>();
            payload.put("user_id", String.valueOf(user.get().getId()));
            payload.put("password", user.get().getPassword());
            payload.put("name", user.get().getName());
            payload.put("surname", user.get().getSurname());

            String token = JWT.create().withPayload(payload).withIssuer("auth0").sign(algorithm);

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user.get());

            return Response.ok(data).build();
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

            Map<String, String> payload = new HashMap<>();
            payload.put("user_id", String.valueOf(user.getId()));
            payload.put("password", user.getPassword());
            payload.put("name", user.getName());
            payload.put("surname", user.getSurname());

            String token = JWT.create().withPayload(payload).withIssuer("auth0").sign(algorithm);

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("user", user);

            return response ? Response.ok(data).build() : Response.status(401).build();
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }

        return Response.serverError().build();
    }
}
