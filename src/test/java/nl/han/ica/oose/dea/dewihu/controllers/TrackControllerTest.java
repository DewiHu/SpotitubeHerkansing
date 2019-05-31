package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.businesslogic.InvalidTokenException;
import nl.han.ica.oose.dea.dewihu.businesslogic.TrackLogic;
import nl.han.ica.oose.dea.dewihu.controllers.dto.TrackRequestDto;
import nl.han.ica.oose.dea.dewihu.models.TrackModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

class TrackControllerTest {
    private static final String TOKEN = "1234-1234-1234";
    private static final int PLAYLISTID = 1;
    private TrackModel trackModel;
    private ArrayList trackArrayMock;
    private TrackLogic trackLogicMock;
    private TrackController trackController;

    @BeforeEach
    void setup() {
        trackModel = Mockito.mock(TrackModel.class);
        trackArrayMock = Mockito.mock(ArrayList.class);
        trackLogicMock = Mockito.mock(TrackLogic.class);

        trackModel.setId(1);

        trackController = new TrackController();
        trackController.setTrackLogic(trackLogicMock);
    }

    @Test
    void doesEndPointDelegateToLogic() {
        //Assemble
        try {
            Mockito.when(trackLogicMock.getTracks(PLAYLISTID, TOKEN)).thenReturn(trackArrayMock);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }

        //Act
        Response response = trackController.getTracksInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointDelegateRequestToLogic() {
        //Assemble
        var dto = new TrackRequestDto();
        dto.setOfflineAvailable(false);
        try {
            Mockito.when(trackLogicMock.createTrack(PLAYLISTID, trackModel.getId(), dto.isOfflineAvailable(), TOKEN)).thenReturn(trackArrayMock);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }

        //Act
        Response response = trackController.postTrack(PLAYLISTID, TOKEN, dto);

        //Assert

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointReturnCorrectStatusWithEmptyPlaylist() {
        //Assemble
        var dto = new TrackRequestDto();
        dto.setOfflineAvailable(false);

        //Act
        Response response = trackController.postTrack(PLAYLISTID, TOKEN, dto);

        //Assert

        Assertions.assertEquals(400, response.getStatus());
    }
}