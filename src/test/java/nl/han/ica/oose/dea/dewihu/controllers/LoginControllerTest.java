package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.businesslogic.LoginLogic;
import nl.han.ica.oose.dea.dewihu.controllers.dto.LoginRequestDto;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

class LoginControllerTest {
    private static final String USERNAME = "meron";
    private static final String PASSWORD = "MySuperSecretPassword12341";
    private LoginController loginController;
    private LoginLogic loginLogicMock;

    @BeforeEach
    void setup() {
        loginLogicMock = Mockito.mock(LoginLogic.class);

        loginController = new LoginController();
        loginController.setLoginLogic(loginLogicMock);
    }

    @Test
    void doesEndPointDelegateLoginToLogic() {
        //Assemble
        var dto = new LoginRequestDto();
        dto.setUser(USERNAME);
        dto.setPassword(PASSWORD);

        var accountModel = new AccountModel();
        accountModel.setName("Meron Brouwer");
        accountModel.setToken("1234-1234-1234");
        Mockito.when(loginLogicMock.login(USERNAME, PASSWORD)).thenReturn(accountModel);

        //Act
        Response response = loginController.login(dto);

        //Assert
        Mockito.verify(loginLogicMock).login(USERNAME, PASSWORD);
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void doesEndPointReturnCorrectStatusWithTokenValueOfNull() {
        //Assemble
        var dto = new LoginRequestDto();
        dto.setUser(USERNAME);
        dto.setPassword(PASSWORD);

        var accountModel = new AccountModel();
        Mockito.when(loginLogicMock.login(USERNAME, PASSWORD)).thenReturn(accountModel);

        //Act
        Response response = loginController.login(dto);

        //Assert
        Mockito.verify(loginLogicMock).login(USERNAME, PASSWORD);
        Assertions.assertEquals(403, response.getStatus());
    }
}