package nl.han.ica.oose.dea.dewihu.businesslogic;

import nl.han.ica.oose.dea.dewihu.dataaccess.LoginDAO;
import nl.han.ica.oose.dea.dewihu.dataaccess.PlaylistDAO;
import nl.han.ica.oose.dea.dewihu.dataaccess.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import javax.inject.Inject;
import java.util.ArrayList;

public class PlaylistLogic {
    private LoginDAO loginDAO;
    private PlaylistDAO playlistDAO;
    private TrackDAO trackDAO;

    @Inject
    public PlaylistLogic (LoginDAO loginDAO, PlaylistDAO playlistDAO, TrackDAO trackDAO) {
        this.loginDAO = loginDAO;
        this.playlistDAO = playlistDAO;
        this.trackDAO = trackDAO;
    }

    public ArrayList<PlaylistModel> getPlaylists(String token) throws InvalidTokenException {
        ArrayList<PlaylistModel> playlists;

        if (loginDAO.readAccount(token).getToken() == null) {
            throw new InvalidTokenException();
        }

        playlists = playlistDAO.readPlaylists(token);

        for (PlaylistModel p : playlists) {
            int playlistId = p.getId();
            ArrayList<TrackModel> tracks = trackDAO.getTracksInPlaylist(playlistId);
            p.setTracks(tracks);
        }

        return playlists;
    }

    public ArrayList<PlaylistModel> deletePlaylist(int id, String token) throws InvalidTokenException {
        ArrayList<PlaylistModel> playlists;

        if (loginDAO.readAccount(token).getToken() == null) {
            throw new InvalidTokenException();
        }

        playlistDAO.deletePlaylist(id);

        playlists = playlistDAO.readPlaylists(token);

        return playlists;
    }

    public ArrayList<PlaylistModel> createPlaylist(String name, String token) throws InvalidTokenException {
        ArrayList<PlaylistModel> playlists;

        if (loginDAO.readAccount(token).getToken() == null) {
            throw new InvalidTokenException();
        }

        playlistDAO.createPlaylist(name, token);

        playlists = playlistDAO.readPlaylists(token);

        return playlists;
    }

    public ArrayList<PlaylistModel> updatePlaylist(int id, String name, String token) throws InvalidTokenException {
        ArrayList<PlaylistModel> playlists;

        if (loginDAO.readAccount(token).getToken() == null) {
            throw new InvalidTokenException();
        }

        playlistDAO.updatePlaylist(id, name);

        playlists = playlistDAO.readPlaylists(token);

        return playlists;
    }

    public int length(ArrayList<PlaylistModel> playlists) {
        int length = 0;

        for (PlaylistModel p: playlists) {
            for (TrackModel t: p.getTracks()) {
                int duration = t.getDuration();
                length += duration;
            }
        }
        return length;
    }

}
