package com.sharemedia.share.routes;

import com.sharemedia.share.controllers.CommentController;
import com.sharemedia.share.controllers.MediaItemController;
import com.sharemedia.share.models.Comment;
import com.sharemedia.share.models.ItemCategory;
import com.sharemedia.share.models.MediaItem;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/medias")
public class MediaItemRoutes {

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMediaItem(MediaItem item) {
        return MediaItemController.addMediaItem(item) ? Response.ok(item).build() : Response.serverError().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMediaItems() {

        Optional<List<MediaItem>> mediaItems = MediaItemController.getAllMediaItems();
        return mediaItems.isPresent() ? Response.ok(mediaItems.get()).build() : Response.serverError().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMediaItem(@PathParam("id") int mediaID) {
        Optional<MediaItem> mediaItem = MediaItemController.getMediaItem(mediaID);
        return mediaItem.isPresent() ? Response.ok(mediaItem.get()).build() : Response.serverError().build();
    }

    @GET
    @Path("/by/{userID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMediaItemsFromUser(@PathParam("userID") int userID) {
        Optional<List<MediaItem>> mediaItems = MediaItemController.getMediaItemsFromUser(userID);
        return mediaItems.isPresent() ? Response.ok(mediaItems).build() : Response.serverError().build();
    }

    @GET
    @Path("/from/{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMediaItemsFromCity(@PathParam("city") String city) {
        Optional<List<MediaItem>> mediaItems = MediaItemController.getMediaItemsFromCity(city);
        return mediaItems.isPresent() ? Response.ok(mediaItems).build() : Response.serverError().build();
    }

    @GET
    @Path("/search/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMediaItemsBySearch(@PathParam("key") String search) {
        Optional<List<MediaItem>> mediaItems = MediaItemController.getMediaItemsBySearch(search);
        return mediaItems.isPresent() ? Response.ok(mediaItems).build() : Response.serverError().build();
    }

    @GET
    @Path("/of/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMediaItemsOfType(@PathParam("type") String type) {
        try {
            int categoryID = ItemCategory.valueOf(type.toUpperCase()).getId();
            Optional<List<MediaItem>> mediaItems = MediaItemController.getMediaItemsOfCategory(categoryID);
            return mediaItems.isPresent() ? Response.ok(mediaItems.get()).build() : Response.serverError().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return Response.noContent().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/comments")
    public Response getAllComments(@PathParam("id") int mediaID) {
        Optional<List<Comment>> comments = CommentController.getCommentsFromMediaItem(mediaID);
        return comments.isPresent() ? Response.ok(comments.get()).build() : Response.serverError().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}/comments")
    public Response addComment(@PathParam("id") int mediaID, Comment comment) {
        boolean done = CommentController.addComment(comment, mediaID);
        return done ? Response.ok(comment).build() : Response.serverError().build();
    }
}
