package ru.dsi.geekbrains.testproject.gwt.client;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import ru.dsi.geekbrains.testproject.common.TaskDto;

import javax.ws.rs.*;
import java.util.List;

@Path("/task")
public interface TaskClient extends RestService {
    @GET
    void getAll(@QueryParam("status") String status, @QueryParam("assignee") String assignee, MethodCallback<List<TaskDto>> items);

    @DELETE
    @Path("{id}")
    void remove(@PathParam("id") String id, MethodCallback<Void> result);
}
