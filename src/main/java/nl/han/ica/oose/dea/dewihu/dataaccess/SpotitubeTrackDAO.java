package nl.han.ica.oose.dea.dewihu.dataaccess;

import nl.han.ica.oose.dea.dewihu.models.TrackModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class SpotitubeTrackDAO extends DatabaseConnection implements TrackDAO {

    @Inject
    public SpotitubeTrackDAO(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    @Override
    public ArrayList<TrackModel> getTracksInPlaylist(int playlistId) {
        String sql = "SELECT T.ID, T.TITLE, T.PERFORMER, T.DURATION, T.ALBUM, T.PLAYCOUNT, T.PUBLICATIONDATE, T.DESCRIPTION, TP.OFFLINEAVAILABLE " +
                "FROM TRACK T LEFT OUTER JOIN TRACK_IN_PLAYLIST TP ON T.ID = TP.TRACK_ID WHERE TP.PLAYLIST_ID = " + playlistId;

        ArrayList<TrackModel> tracks = new ArrayList<>();
        setTracksFromDatabase(tracks, sql);
        return tracks;
    }

    @Override
    public ArrayList<TrackModel> getAvailableTracks(int playlistId) {
        String sql = "SELECT DISTINCT T.ID, T.TITLE, T.PERFORMER, T.DURATION, T.ALBUM, T.PLAYCOUNT, T.PUBLICATIONDATE, T.DESCRIPTION, TP.OFFLINEAVAILABLE " +
                "FROM TRACK T LEFT OUTER JOIN TRACK_IN_PLAYLIST TP " +
                "ON T.ID = TP.TRACK_ID " +
                "WHERE TP.PLAYLIST_ID != " + playlistId + " OR TP.PLAYLIST_ID IS NULL";

        ArrayList<TrackModel> tracks = new ArrayList<>();
        setTracksFromDatabase(tracks, sql);
        return tracks;
    }

    @Override
    public void deleteTrack(int playlistId, int trackId) {
        String sql = "DELETE FROM TRACK_IN_PLAYLIST WHERE PLAYLIST_ID = " + playlistId + " AND TRACK_ID = " + trackId;
        executeStatement(sql);
    }

    @Override
    public void createTrack(int playlistId, int trackId, boolean offlineAvailable) {
        int offlineAvailableBit = 0;
        if (offlineAvailable) offlineAvailableBit = 1;
        String sql = "INSERT INTO TRACK_IN_PLAYLIST (TRACK_ID, PLAYLIST_ID, OFFLINEAVAILABLE) " +
                "VALUES (" + trackId + ", " + playlistId + ", " + offlineAvailableBit + ")";
        executeStatement(sql);
    }

    private void setTracksFromDatabase(ArrayList<TrackModel> tracks, String sql) {
        String error = "Error communicating with database " + getDatabaseProperties().connectionString();

        try {
            Connection conn = DriverManager.getConnection(getDatabaseProperties().connectionString(),
                    getDatabaseProperties().connectionUser(), getDatabaseProperties().connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            setTracksFromResultSet(tracks, st);
            st.close();
            conn.close();
        } catch (SQLException e) {
            getLogger().log(Level.SEVERE, error, e);
        }
    }

    private void setTracksFromResultSet(ArrayList<TrackModel> tracks, PreparedStatement st) throws SQLException {
        String ColumnId = "ID";
        String columnTitle = "TITLE";
        String columnPerformer = "PERFORMER";
        String columnDuration = "DURATION";
        String columnAlbum = "ALBUM";
        String columnPlaycount = "PLAYCOUNT";
        String columnPublicationDate = "PUBLICATIONDATE";
        String columnDescription = "DESCRIPTION";
        String columnOfflineAvailable = "OFFLINEAVAILABLE";
        ResultSet rS = st.executeQuery();
        while (rS.next()) {
            TrackModel track = new TrackModel();

            track.setId(rS.getInt(ColumnId));
            track.setTitle(rS.getString(columnTitle));
            track.setPerformer(rS.getString(columnPerformer));
            track.setDuration(rS.getInt(columnDuration));
            track.setAlbum(rS.getString(columnAlbum));
            track.setPlaycount(rS.getInt(columnPlaycount));
            track.setPublicationDate(rS.getString(columnPublicationDate));
            track.setDescription(rS.getString(columnDescription));
            track.setOfflineAvailable(rS.getBoolean(columnOfflineAvailable));

            tracks.add(track);
        }
    }
}
