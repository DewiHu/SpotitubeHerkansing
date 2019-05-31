package nl.han.ica.oose.dea.dewihu.businesslogic;

import nl.han.ica.oose.dea.dewihu.dataaccess.LoginDAO;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class LoginLogicTest {
    private static final String USER = "dewi";
    private static final String PASSWORD = "MySuperDuperSecretPassword12345";
    private LoginDAO loginDAOMock;
    private LoginLogic loginLogic;

    @BeforeEach
    void setup() {
        loginDAOMock = Mockito.mock(LoginDAO.class);
        loginLogic = new LoginLogic(loginDAOMock);
    }

    @Test
    void doesLogicDelegateToDAO() {
        //Assemble
        var expected = new AccountModel();
        expected.setName("Dewi Hu");
        expected.setToken("1111-1111-1111");
        Mockito.when(loginDAOMock.login(USER, PASSWORD)).thenReturn(expected);

        //Act
        AccountModel account = loginLogic.login(USER, PASSWORD);

        //Assert
        Assertions.assertEquals(expected, account);
    }

    @Test
    void doesLogicReturnNull() {
        //Assemble
        var dto = new AccountModel();
        var expected = new AccountModel();
        Mockito.when(loginDAOMock.login(USER, PASSWORD)).thenReturn(expected);

        //Act
        dto = loginLogic.login(USER, PASSWORD);

        //Assert
        Assertions.assertEquals(expected, dto);
    }
}