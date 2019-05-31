package nl.han.ica.oose.dea.dewihu.dataaccess;

import nl.han.ica.oose.dea.dewihu.models.AccountModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.logging.Level;

public class SpotitubeLoginDAO extends  DatabaseConnection implements LoginDAO{

    @Inject
    public SpotitubeLoginDAO(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    @Override
    public AccountModel login(String user, String password) {
        var account = new AccountModel();

        String sql = "SELECT FULLNAME, TOKEN FROM ACCOUNT WHERE USERNAME LIKE '" + user + "' AND PASSWORD LIKE '" + password + "'";

        setAccountFromDatabase(account, sql);

        return account;
    }

    public AccountModel readAccount (String token) {
        var account = new AccountModel();
        String sql = "SELECT FULLNAME, TOKEN FROM ACCOUNT WHERE TOKEN LIKE '" + token + "'";

        setAccountFromDatabase(account, sql);

        return account;
    }

    private void setAccountFromDatabase(AccountModel account, String sql) {
        try {
            Connection conn = DriverManager.getConnection(getDatabaseProperties().connectionString(),
                    getDatabaseProperties().connectionUser(), getDatabaseProperties().connectionPassword());
            PreparedStatement st = conn.prepareStatement(sql);
            setAccountFromResultSet(account, st);
            st.close();
            conn.close();
        } catch (SQLException e) {
            String error = "Error communicating with database " + getDatabaseProperties().connectionString();

            getLogger().log(Level.SEVERE, error, e);
        }
    }

    private void setAccountFromResultSet(AccountModel accountModel, PreparedStatement st) throws SQLException {
        String columnFullName = "FULLNAME";
        String columnToken = "TOKEN";

        ResultSet rS = st.executeQuery();

        while(rS.next()) {
            accountModel.setName(rS.getString(columnFullName));
            accountModel.setToken(rS.getString(columnToken));
        }
    }
}
