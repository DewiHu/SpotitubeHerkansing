package nl.han.ica.oose.dea.dewihu.businesslogic;

import nl.han.ica.oose.dea.dewihu.dataaccess.LoginDAO;
import nl.han.ica.oose.dea.dewihu.dataaccess.TrackDAO;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;


class TrackLogicTest {
    private static final String TOKEN = "1234-1234-1234";
    private static final int PLAYLISTID = 1;
    private LoginDAO loginDAOMock;
    private TrackDAO trackDAOMock;
    private TrackLogic trackLogic;
    private ArrayList<TrackModel> tracks;
    private TrackModel trackModelMock;
    private AccountModel account;

    @BeforeEach
    void setup() {
        trackModelMock = Mockito.mock(TrackModel.class);
        loginDAOMock = Mockito.mock(LoginDAO.class);
        trackDAOMock = Mockito.mock(TrackDAO.class);
        trackLogic = new TrackLogic(loginDAOMock, trackDAOMock);
        tracks = new ArrayList<>();
        tracks.add(trackModelMock);

        account = new AccountModel();
        account.setToken(TOKEN);
    }

    @Test
    void doesEndPointDelegateReadAvailableTracksToDAO() throws InvalidTokenException {
        //Assemble
        var dto = new ArrayList<TrackModel>();
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(trackDAOMock.getAvailableTracks(PLAYLISTID)).thenReturn(tracks);

        //Act
        dto = trackLogic.getAvailableTracks(PLAYLISTID, TOKEN);

        //Assert
        Assertions.assertEquals(tracks, dto);

    }

    @Test
    void doesEndPointDelegateReadTracksToDAO() throws InvalidTokenException {
        //Assemble
        var dto = new ArrayList<TrackModel>();
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(trackDAOMock.getTracksInPlaylist(PLAYLISTID)).thenReturn(tracks);

        //Act
        dto = trackLogic.getTracks(PLAYLISTID, TOKEN);

        //Assert
        Assertions.assertEquals(tracks, dto);

    }

    @Test
    void doesEndPointDelegateDeleteTrackToDAO() throws InvalidTokenException {
        //Assemble
        var dto = new ArrayList<TrackModel>();
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(trackDAOMock.getTracksInPlaylist(PLAYLISTID)).thenReturn(tracks);

        //Act
        dto = trackLogic.deleteTrack(PLAYLISTID, trackModelMock.getId(), TOKEN);

        //Assert
        Assertions.assertEquals(tracks, dto);

    }

    @Test
    void doesEndPointDelegateCreateTrackToDAO() throws InvalidTokenException {
        //Assemble
        var dto = new ArrayList<TrackModel>();
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(trackDAOMock.getTracksInPlaylist(PLAYLISTID)).thenReturn(tracks);

        //Act
        dto = trackLogic.createTrack(PLAYLISTID, trackModelMock.getId(), false, TOKEN);

        //Assert
        Assertions.assertEquals(tracks, dto);
    }

    @Test
    void doesLogicThrowException() {
        //Assemble
        account.setToken(null);
        account.setName(null);
        Mockito.when(loginDAOMock.readAccount(TOKEN)).thenReturn(account);
        Mockito.when(trackDAOMock.getTracksInPlaylist(PLAYLISTID)).thenReturn(tracks);

        //Act

        //Assert
        Assertions.assertThrows(InvalidTokenException.class, () -> {
            trackLogic.getTracks(PLAYLISTID, TOKEN);
        });
    }
}