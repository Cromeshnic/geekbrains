package ru.dsi.geekbrains.testproject.gwt.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import ru.dsi.geekbrains.testproject.common.JwtAuthRequestDto;
import ru.dsi.geekbrains.testproject.common.JwtAuthResponseDto;

import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

public interface AuthClient extends RestService {
    @POST
    @Path("http://localhost:8189/rest/authenticate")
    void authenticate(@BeanParam() JwtAuthRequestDto authRequest, MethodCallback<JwtAuthResponseDto> result);

    /*@POST
    @Path("http://localhost:8189/market/authenticateTheUser")
    void authenticate(@FormParam("username") String username, @FormParam("password") String password, MethodCallback<String> result);*/
}