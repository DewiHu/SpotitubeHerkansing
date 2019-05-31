package nl.han.ica.oose.dea.dewihu.dataaccess;

import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;

public class SpotitubePlaylistDAO extends DatabaseConnection implements PlaylistDAO{

    @Inject
    public SpotitubePlaylistDAO(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    @Override
    public ArrayList<PlaylistModel> readPlaylists(String token) {
        ArrayList<PlaylistModel> playlists = new ArrayList<>();

        String sql = "SELECT * FROM PLAYLIST";
        String error = "Error communicating with database " + getDatabaseProperties().connectionString();

        try {
            Connection conn = DriverManager.getConnection(getDatabaseProperties().connectionString(),
                    getDatabaseProperties().connectionUser(), getDatabaseProperties().connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            setPlaylistFromResultSet(playlists, token, st);
            st.close();
            conn.close();
        } catch (SQLException e) {

            getLogger().log(Level.SEVERE, error, e);
        }

        return playlists;
    }

    @Override
    public void deletePlaylist(int id) {
        String sql = "DELETE FROM PLAYLIST WHERE ID = " + id;
        executeStatement(sql);
    }

    @Override
    public void createPlaylist(String name, String token) {
        String sql = "INSERT INTO PLAYLIST (NAME, TOKEN) VALUES ('" + name + "', '" + token + "')";
        executeStatement(sql);
    }

    @Override
    public void updatePlaylist(int id, String name) {
        String sql = "UPDATE PLAYLIST SET NAME = '" + name + "' WHERE ID = " + id;
        executeStatement(sql);
    }

    private void setPlaylistFromResultSet(ArrayList<PlaylistModel> playlists, String token, PreparedStatement st) throws SQLException {
        String ColumnId = "ID";
        String ColumnName = "NAME";
        String ColumnToken = "TOKEN";
        ResultSet rS = st.executeQuery();

        while(rS.next()) {
            var playlist = new PlaylistModel();

            playlist.setId(rS.getInt(ColumnId));
            playlist.setName(rS.getString(ColumnName));
            playlist.setOwner(rS.getString(ColumnToken).equals(token));
            playlist.setTracks(new ArrayList<>());

            playlists.add(playlist);
        }
    }
}

