package nl.han.ica.oose.dea.dewihu.businesslogic;

import nl.han.ica.oose.dea.dewihu.dataaccess.LoginDAO;
import nl.han.ica.oose.dea.dewihu.dataaccess.PlaylistDAO;
import nl.han.ica.oose.dea.dewihu.dataaccess.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;
import nl.han.ica.oose.dea.dewihu.models.PlaylistModel;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class PlaylistLogicTest {
    private static final String TOKEN = "1234-1234-1234";
    private LoginDAO loginDAOMock;
    private PlaylistDAO playlistDAOMock;
    private TrackDAO trackDAOMock;
    private PlaylistLogic playlistLogic;
    private ArrayList<PlaylistModel> playlists;
    private ArrayList trackArrayListMock;
    private AccountModel account;

    @BeforeEach
    void setup() {
        loginDAOMock = Mockito.mock(LoginDAO.class);
        playlistDAOMock = Mockito.mock(PlaylistDAO.class);
        trackDAOMock = Mockito.mock(TrackDAO.class);
        playlists = new ArrayList<>();
        trackArrayListMock = Mockito.mock(ArrayList.class);
        PlaylistModel playlistModelMock = Mockito.mock(PlaylistModel.class);
        playlists.add(playlistModelMock);

        playlistLogic = new PlaylistLogic(loginDAOMock, playlistDAOMock, trackDAOMock);

        account = new AccountModel();
        account.setToken(TOKEN);
    }

    @Test
    void doesLogicDelegateReadToDAO() throws InvalidTokenException {
        //Assemble
        var dto = new ArrayList<PlaylistModel>();
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(playlistDAOMock.readPlaylists(TOKEN)).thenReturn(playlists);
        Mockito.when(trackDAOMock.getTracksInPlaylist(Mockito.anyInt())).thenReturn(trackArrayListMock);

        //Act
        dto = playlistLogic.getPlaylists(TOKEN);

        //Assert
        Assertions.assertEquals(playlists, dto);
    }

    @Test
    void doesLogicDelegateDeleteToDAO() throws InvalidTokenException {
        //Assemble
        var dto = new ArrayList<PlaylistModel>();
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(playlistDAOMock.readPlaylists(TOKEN)).thenReturn(playlists);
        Mockito.when(trackDAOMock.getTracksInPlaylist(Mockito.anyInt())).thenReturn(trackArrayListMock);

        //Act
        dto = playlistLogic.deletePlaylist(Mockito.anyInt(), TOKEN);

        //Assert
        Assertions.assertEquals(playlists, dto);
    }

    @Test
    void doesLogicDelegateCreateToDAO() throws InvalidTokenException {
        //Assemble
        var dto = new ArrayList<PlaylistModel>();
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(playlistDAOMock.readPlaylists(TOKEN)).thenReturn(playlists);
        Mockito.when(trackDAOMock.getTracksInPlaylist(Mockito.anyInt())).thenReturn(trackArrayListMock);

        //Act
        dto = playlistLogic.createPlaylist(Mockito.anyString(), TOKEN);

        //Assert
        Assertions.assertEquals(playlists, dto);
    }

    @Test
    void doesLogicDelegatePutToDAO() throws InvalidTokenException {
        //Assemble
        var dto = new ArrayList<PlaylistModel>();
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(playlistDAOMock.readPlaylists(TOKEN)).thenReturn(playlists);
        Mockito.when(trackDAOMock.getTracksInPlaylist(Mockito.anyInt())).thenReturn(trackArrayListMock);

        //Act
        dto = playlistLogic.updatePlaylist(Mockito.anyInt(), "", TOKEN);

        //Assert
        Assertions.assertEquals(playlists, dto);
    }

    @Test
    void doesLogicThrowException() {
        //Assemble
        account.setToken(null);
        account.setName(null);
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(playlistDAOMock.readPlaylists(TOKEN)).thenReturn(playlists);
        Mockito.when(trackDAOMock.getTracksInPlaylist(Mockito.anyInt())).thenReturn(trackArrayListMock);

        //Act

        //Assert
        Assertions.assertThrows(InvalidTokenException.class, () -> {
            playlistLogic.getPlaylists(TOKEN);
        });
    }

    @Test
    void doesLogicCalculateLengthOfPlaylists() {
        //Assemble
        var playlist = new PlaylistModel();
        var tracks = new ArrayList<TrackModel>();
        var track = new TrackModel();
        track.setDuration(100);
        tracks.add(track);
        playlist.setTracks(tracks);
        playlists.add(playlist);
        int expected = 100;

        //Act
        int length = playlistLogic.length(playlists);

        //Assert
        Assertions.assertEquals(expected, length);

    }
}