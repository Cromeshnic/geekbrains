package ru.dsi.geekbrains.testproject.gwt.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import ru.dsi.geekbrains.testproject.common.UserDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/user")
public interface UserClient extends RestService {
    @GET
    void getAll(MethodCallback<List<UserDto>> items);
}