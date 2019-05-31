package nl.han.ica.oose.dea.dewihu.dataaccess;

import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import java.util.ArrayList;

public interface TrackDAO {
    ArrayList<TrackModel> getTracksInPlaylist(int playlistId);

    ArrayList<TrackModel> getAvailableTracks(int playlistId);

    void deleteTrack(int playlistId, int trackId);

    void createTrack(int playlistId, int trackId, boolean offlineAvailable);
}
