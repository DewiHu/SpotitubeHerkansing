package nl.han.ica.oose.dea.dewihu.dataaccess;

import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;

import java.util.ArrayList;

public interface PlaylistDAO {
    ArrayList<PlaylistModel> readPlaylists(String token);

    void deletePlaylist(int id);

    void createPlaylist(String name, String token);

    void updatePlaylist(int id, String name);
}
