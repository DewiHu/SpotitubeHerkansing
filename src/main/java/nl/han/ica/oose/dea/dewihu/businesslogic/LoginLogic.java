package nl.han.ica.oose.dea.dewihu.businesslogic;

import nl.han.ica.oose.dea.dewihu.dataaccess.LoginDAO;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;
import javax.inject.Inject;

public class LoginLogic {
    private LoginDAO loginDAO;

    @Inject
    public LoginLogic(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    public AccountModel login(String user, String password) {
        var account = loginDAO.login(user, password);

        if (account.getToken() == null) {
            account.setToken(null);
            account.setName(null);
        }

        return account;
    }
}
