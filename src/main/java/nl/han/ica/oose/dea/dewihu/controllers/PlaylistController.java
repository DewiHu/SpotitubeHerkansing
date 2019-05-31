package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.businesslogic.InvalidTokenException;
import nl.han.ica.oose.dea.dewihu.businesslogic.PlaylistLogic;
import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistRequestDto;
import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistResponseDto;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/")
public class PlaylistController {
    private PlaylistLogic playlistLogic;

    @GET
    @Path("playlists")
    @Produces ("application/json")
    public Response getPlaylists(@QueryParam("token") String token) {
        ArrayList<PlaylistModel> playlists;

        try {
            playlists = playlistLogic.getPlaylists(token);
        } catch (InvalidTokenException e) {
            return Response.status(403).build();
        }

        if (playlists.isEmpty()) {
            return Response.status(400).build();
        }

        PlaylistResponseDto response = buildPlaylistResponse(playlists);

        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("playlists/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response delPlaylists (@PathParam("id") int id, @QueryParam("token") String token) {
        ArrayList<PlaylistModel> playlists;

        try {
            playlists = playlistLogic.deletePlaylist(id, token);
        } catch (InvalidTokenException e) {
            return Response.status(403).build();
        }

        if (playlists.isEmpty()) {
            return Response.status(400).build();
        }

        PlaylistResponseDto response = buildPlaylistResponse(playlists);

        return Response.ok().entity(response).build();
    }

    @POST
    @Path("playlists")
    @Produces("application/json")
    @Consumes("application/json")
    public Response postPlaylists (@QueryParam("token") String token, PlaylistRequestDto request) {
        String name = request.getName();
        ArrayList<PlaylistModel> playlists;

        try {
            playlists = playlistLogic.createPlaylist(name, token);
        } catch (InvalidTokenException e) {
            return Response.status(403).build();
        }

        if (playlists.isEmpty()) {
            return Response.status(400).build();
        }

        PlaylistResponseDto response = buildPlaylistResponse(playlists);

        return Response.ok().entity(response).build();
    }

    @PUT
    @Path("playlists/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response putPlaylists (@PathParam("id") int id, @QueryParam("token") String token, PlaylistRequestDto request) {
        String name = request.getName();
        ArrayList<PlaylistModel> playlists;

        try {
            playlists = playlistLogic.updatePlaylist(id, name, token);
        } catch (InvalidTokenException e) {
            return Response.status(403).build();
        }

        if (playlists.isEmpty()) {
            return Response.status(400).build();
        }

        PlaylistResponseDto response = buildPlaylistResponse(playlists);

        return Response.ok().entity(response).build();
    }

    private PlaylistResponseDto buildPlaylistResponse(ArrayList<PlaylistModel> playlists) {
        int length = playlistLogic.length(playlists);
        PlaylistResponseDto response = new PlaylistResponseDto();
        response.setPlaylists(playlists);
        response.setLength(length);
        return response;
    }

    @Inject
    public void setPlaylistLogic (PlaylistLogic playlistLogic) {
        this.playlistLogic = playlistLogic;
    }
}
