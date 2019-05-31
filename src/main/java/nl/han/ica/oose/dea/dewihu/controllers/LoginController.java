package nl.han.ica.oose.dea.dewihu.controllers;

import nl.han.ica.oose.dea.dewihu.businesslogic.LoginLogic;
import nl.han.ica.oose.dea.dewihu.controllers.dto.LoginRequestDto;
import nl.han.ica.oose.dea.dewihu.controllers.dto.LoginResponseDto;
import nl.han.ica.oose.dea.dewihu.models.AccountModel;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {
    private LoginLogic loginLogic;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequestDto request) {
        String requestUser = request.getUser();
        String requestPassword = request.getPassword();
        AccountModel login = loginLogic.login(requestUser, requestPassword);

        if (login.getToken() == null) {
            return Response.status(403).build();
        }

        LoginResponseDto response = buildLoginResponse(login);

        return Response.ok().entity(response).build();
    }

    private LoginResponseDto buildLoginResponse(AccountModel login) {
        LoginResponseDto response = new LoginResponseDto();
        String loginToken = login.getToken();
        String loginName = login.getName();

        response.setToken(loginToken);
        response.setUser(loginName);
        return response;
    }

    @Inject
    public void setLoginLogic(LoginLogic loginLogic) {
        this.loginLogic = loginLogic;
    }

}
