package ru.dsi.geekbrains.testproject.gwt.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import ru.dsi.geekbrains.testproject.common.TaskDto;

import javax.ws.rs.*;
import java.util.List;

@Path("/task")
public interface TaskClient extends RestService {
    @GET
    @Path("/")
    void getAll(@QueryParam("token") String token, @QueryParam("status") String status, @QueryParam("assignee") String assignee, MethodCallback<List<TaskDto>> items);
    //Так почему-то не работает на стороне клиента - хэдер не отправляется. Возможно, связано с кроссдоменностью. Не смог разобраться, переделал на QueryParam
    //void getAll(@HeaderParam("Authorization") String token, @QueryParam("status") String status, @QueryParam("assignee") String assignee, MethodCallback<List<TaskDto>> items);

    @DELETE
    @Path("{id}")
    void remove(@QueryParam("token") String token, @PathParam("id") String id, MethodCallback<Void> result);

    @POST
    @Path("/")
    void add(@QueryParam("token") String token, @QueryParam("statusId") String statusId, @QueryParam("title") String title, MethodCallback<Void> result);
}
