package nl.han.ica.oose.dea.dewihu.dataaccess;

import nl.han.ica.oose.dea.dewihu.models.AccountModel;

public interface LoginDAO {
    AccountModel login(String user, String password);

    AccountModel readAccount(String token);
}
