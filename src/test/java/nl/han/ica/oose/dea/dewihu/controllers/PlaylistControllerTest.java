package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.businesslogic.InvalidTokenException;
import nl.han.ica.oose.dea.dewihu.businesslogic.PlaylistLogic;
import nl.han.ica.oose.dea.dewihu.controllers.dto.PlaylistRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
class PlaylistControllerTest {
    private static final String TOKEN = "1234-1234-1234";
    private ArrayList playlistArrayListMock;
    private PlaylistLogic playlistLogicMock;
    private PlaylistController playlistController;


    @BeforeEach
    void setup() {
        playlistArrayListMock = Mockito.mock(ArrayList.class);
        playlistLogicMock = Mockito.mock(PlaylistLogic.class);

        playlistController = new PlaylistController();
        playlistController.setPlaylistLogic(playlistLogicMock);
    }

    @Test
    void doesEndPointDelegateToLogic() {
        //Assemble
        try {
            Mockito.when(playlistLogicMock.getPlaylists(TOKEN)).thenReturn(playlistArrayListMock);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }

        //Act
        Response response = playlistController.getPlaylists(TOKEN);

        //Assert
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointDelegateRequestToLogic() {
        //Assemble
        var dto = new PlaylistRequestDto();
        dto.setName("Test");
        try {
            Mockito.when(playlistLogicMock.createPlaylist(dto.getName(), TOKEN)).thenReturn(playlistArrayListMock);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }

        //Act
        Response response = playlistController.postPlaylists(TOKEN, dto);

        //Assert

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointReturnCorrectStatusWithEmptyPlaylist() {
        //Assemble
        var dto = new PlaylistRequestDto();
        dto.setName("Test");

        //Act
        Response response = playlistController.postPlaylists(TOKEN, dto);

        //Assert

        Assertions.assertEquals(400, response.getStatus());
    }
}