package nl.han.ica.oose.dea.dewihu.businesslogic;

import nl.han.ica.oose.dea.dewihu.dataaccess.LoginDAO;
import nl.han.ica.oose.dea.dewihu.dataaccess.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import javax.inject.Inject;
import java.util.ArrayList;

public class TrackLogic {
    private LoginDAO loginDAO;
    private TrackDAO trackDAO;

    @Inject
    public TrackLogic (LoginDAO loginDAO, TrackDAO trackDAO) {
        this.loginDAO = loginDAO;
        this.trackDAO = trackDAO;
    }

    public ArrayList<TrackModel> getAvailableTracks(int playlistId, String token) throws InvalidTokenException {
        ArrayList<TrackModel> tracks;

        if (loginDAO.readAccount(token).getToken() == null) {
            throw new InvalidTokenException();
        }

        tracks = trackDAO.getAvailableTracks(playlistId);

        return tracks;
    }

    public ArrayList<TrackModel> getTracks(int playlistId, String token) throws InvalidTokenException {
        ArrayList<TrackModel> tracks;

        if (loginDAO.readAccount(token).getToken() == null) {
            throw new InvalidTokenException();
        }

        tracks = trackDAO.getTracksInPlaylist(playlistId);

        return tracks;
    }

    public ArrayList<TrackModel> deleteTrack(int playlistId, int trackId, String token) throws InvalidTokenException {
        ArrayList<TrackModel> tracks;

        if (loginDAO.readAccount(token).getToken() == null) {
            throw new InvalidTokenException();
        }

        trackDAO.deleteTrack(playlistId, trackId);

        tracks = trackDAO.getTracksInPlaylist(playlistId);

        return  tracks;
    }

    public ArrayList<TrackModel> createTrack(int playlistId, int trackId, boolean offlineAvailable, String token) throws InvalidTokenException {
        ArrayList<TrackModel> tracks;

        if (loginDAO.readAccount(token).getToken() == null) {
            throw new InvalidTokenException();
        }

        trackDAO.createTrack(playlistId, trackId, offlineAvailable);

        tracks = trackDAO.getTracksInPlaylist(playlistId);

        return tracks;
    }


}
