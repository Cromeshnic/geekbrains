package ru.dsi.geekbrains.testproject.gwt.client;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import ru.dsi.geekbrains.testproject.common.IdTitle;
import ru.dsi.geekbrains.testproject.common.TaskDto;
import ru.dsi.geekbrains.testproject.common.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskTableWidget extends Composite {
    @UiField
    CellTable<TaskDto> table;

    @UiField
    ListBox status;

    @UiField
    ListBox assignee;

    private TaskClient taskClient;

    private UserClient userClient;

    @UiTemplate("TaskTable.ui.xml")
    interface TaskTableBinder extends UiBinder<Widget, TaskTableWidget> {
    }

    private static TaskTableBinder uiBinder = GWT.create(TaskTableBinder.class);

    public TaskTableWidget() {
        initWidget(uiBinder.createAndBindUi(this));
//         table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<TaskDto> idColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                return taskDto.getId().toString();
            }
        };
        table.addColumn(idColumn, "ID");

        TextColumn<TaskDto> titleColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                return taskDto.getTitle();
            }
        };
        table.addColumn(titleColumn, "Title");

        TextColumn<TaskDto> statusColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                return taskDto.getStatus().getTitle();
            }
        };
        table.addColumn(statusColumn, "Status");

        TextColumn<TaskDto> ownerColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                return Optional.ofNullable(taskDto.getOwner()).map(IdTitle::getTitle).orElse("");
            }
        };
        table.addColumn(ownerColumn, "Owner");

        TextColumn<TaskDto> assigneeColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                return Optional.ofNullable(taskDto.getAssignee()).map(IdTitle::getTitle).orElse("");
            }
        };
        table.addColumn(assigneeColumn, "Assignee");

        taskClient = GWT.create(TaskClient.class);

        userClient = GWT.create(UserClient.class);

        Column<TaskDto, TaskDto> actionColumn = new Column<TaskDto, TaskDto>(
                new ActionCell<TaskDto>("REMOVE", new ActionCell.Delegate<TaskDto>() {
                    @Override
                    public void execute(TaskDto taskDto) {
                        taskClient.remove(taskDto.getId().toString(), new MethodCallback<Void>() {
                            @Override
                            public void onFailure(Method method, Throwable throwable) {
                                GWT.log(throwable.toString());
                                GWT.log(throwable.getMessage());
                            }

                            @Override
                            public void onSuccess(Method method, Void result) {
                                refresh();
                            }
                        });
                    }
                })) {
            @Override
            public TaskDto getValue(TaskDto taskDto) {
                return taskDto;
            }
        };

        table.addColumn(actionColumn, "Actions");

        table.setColumnWidth(idColumn, 100, Style.Unit.PX);
        table.setColumnWidth(titleColumn, 400, Style.Unit.PX);
        table.setColumnWidth(actionColumn, 200, Style.Unit.PX);

        //Заполняем список пользователей в фильтре
        userClient.getAll( new MethodCallback<List<UserDto>>(){
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
            }
            @Override
            public void onSuccess(Method method, List<UserDto> userDtoList) {
                for (UserDto userDto : userDtoList) {
                    assignee.addItem(userDto.getTitle(), String.valueOf(userDto.getId()));
                }
            }
        });
        
        //assignee.addItem();

        refresh();
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        refresh();
    }

    public void refresh() {//Integer.valueOf() Integer.valueOf()
        taskClient.getAll(status.getSelectedValue(), assignee.getSelectedValue(), new MethodCallback<List<TaskDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                Window.alert("Невозможно получить список задач: Сервер не отвечает");
            }

            @Override
            public void onSuccess(Method method, List<TaskDto> i) {
                GWT.log("Received " + i.size() + " tasks");
                List<TaskDto> tasks = new ArrayList<>(i);
                table.setRowData( tasks);
            }
        });
    }
}
