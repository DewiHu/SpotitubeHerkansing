package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.businesslogic.InvalidTokenException;
import nl.han.ica.oose.dea.dewihu.businesslogic.TrackLogic;
import nl.han.ica.oose.dea.dewihu.controllers.dto.TrackRequestDto;
import nl.han.ica.oose.dea.dewihu.controllers.dto.TrackResponseDto;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/")
public class TrackController {
    private TrackLogic trackLogic;

    @GET
    @Path("tracks")
    @Produces("application/json")
    public Response getTracksAvailable(@QueryParam("forPlaylist") int forPlaylist, @QueryParam("token") String token) {
        ArrayList<TrackModel> tracks;

        try {
            tracks = trackLogic.getAvailableTracks(forPlaylist, token);
        } catch (InvalidTokenException e) {
            return Response.status(403).build();
        }

        if (tracks.isEmpty()) {
            return Response.status(400).build();
        }

        TrackResponseDto response = buildTrackResponse(tracks);

        return Response.ok().entity(response).build();
    }

    @GET
    @Path("playlists/{playlistId}/tracks")
    @Produces("application/json")
    public Response getTracksInPlaylist(@PathParam ("playlistId") int playlistId, @QueryParam("token") String token) {
        ArrayList<TrackModel> tracks;

        try {
            tracks = trackLogic.getTracks(playlistId, token);
        } catch (InvalidTokenException e) {
            return Response.status(403).build();
        }

        TrackResponseDto response = buildTrackResponse(tracks);

        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("playlists/{playlistId}/tracks/{trackId}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response delTrack (@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId, @QueryParam("token") String token) {
        ArrayList<TrackModel> tracks;

        try {
            tracks = trackLogic.deleteTrack(playlistId, trackId, token);
        } catch (InvalidTokenException e) {
            return Response.status(403).build();
        }

        TrackResponseDto response = buildTrackResponse(tracks);

        return Response.ok().entity(response).build();
    }

    @POST
    @Path("playlists/{playlistId}/tracks")
    @Produces("application/json")
    @Consumes("application/json")
    public Response postTrack (@PathParam("playlistId") int playlistId, @QueryParam("token") String token, TrackRequestDto request) {
        int trackId = request.getId();
        boolean offlineAvailable = request.isOfflineAvailable();
        ArrayList<TrackModel> tracks;

        try {
            tracks = trackLogic.createTrack(playlistId, trackId, offlineAvailable, token);
        } catch (InvalidTokenException e) {
            return Response.status(403).build();
        }

        if (tracks.isEmpty()) {
            return Response.status(400).build();
        }

        TrackResponseDto response = buildTrackResponse(tracks);

        return Response.ok().entity(response).build();
    }

    private TrackResponseDto buildTrackResponse(ArrayList<TrackModel> tracks) {
        TrackResponseDto response = new TrackResponseDto();
        response.setTracks(tracks);
        return response;
    }


    @Inject
    public void setTrackLogic (TrackLogic trackLogic){
        this.trackLogic = trackLogic;
    }
}