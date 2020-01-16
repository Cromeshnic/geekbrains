package ru.dsi.geekbrains.testproject.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import ru.dsi.geekbrains.testproject.common.TaskDto;
import ru.dsi.geekbrains.testproject.common.UserDto;

import java.util.List;

public class EditTaskFormWidget extends Composite {

    private Long taskId;
    private volatile TaskDto task;

    @UiField
    FormPanel form;

    @UiField
    TextBox titleText;

    @UiField
    TextBox description;

    @UiField
    ListBox statusId;

    @UiField
    ListBox ownerId;

    @UiField
    ListBox assigneeId;

    private TaskTableWidget taskTableWidget;
    private TabLayoutPanel tabPanel;
    private TaskClient taskClient;
    private UserClient userClient;

    @UiTemplate("EditTaskForm.ui.xml")
    interface EditTaskFormBinder extends UiBinder<Widget, EditTaskFormWidget> {
    }

    private static EditTaskFormWidget.EditTaskFormBinder uiBinder = GWT.create(EditTaskFormWidget.EditTaskFormBinder.class);

    public EditTaskFormWidget(TabLayoutPanel tabPanel) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.form.setAction(Defaults.getServiceRoot().concat("task"));
        this.tabPanel = tabPanel;
        this.taskClient = GWT.create(TaskClient.class);
        this.userClient = GWT.create(UserClient.class);
        this.taskId = -1L;
    }

    //Надо разобраться, как тут делать инъекции более красиво
    public void setTaskTableWidget(TaskTableWidget taskTableWidget){
        this.taskTableWidget = taskTableWidget;
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
        if (titleText.getText().length() < 4) {
            Window.alert("Название должно быть не менее 4 символов");
            event.cancel();
        }
    }

    /*@UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        taskTableWidget.refresh();
    }*/

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        GWT.log("STORAGE: " + token);
        if(taskId<=0){
            Window.alert("Укажите id задачи");
            return;
        }
        if(task==null){
            task = new TaskDto();
        }
        task.setId(taskId);
        task.setTitle(titleText.getValue());
        task.setDescription(description.getValue());
        task.setOwnerId(Long.valueOf(ownerId.getSelectedValue()));
        task.setAssigneeId(Long.valueOf(assigneeId.getSelectedValue()));
        task.setStatusId(Integer.parseInt(statusId.getSelectedValue()));

        taskClient.update(token, taskId, task, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                Window.alert("Невозможно обновить задачу: "+throwable.getMessage());
            }

            @Override
            public void onSuccess(Method method, Void result) {
                taskTableWidget.refresh();
                tabPanel.selectTab(1);
            }
        });
    }

    public void loadTask(Long taskId) {
        this.taskId = taskId;
        this.refresh();
    }

    private void refresh() {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");

        this.task = null;

        //Заполняем поля формы по id задачи
        if(taskId>0){
            taskClient.get(token, taskId, new MethodCallback<TaskDto>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    GWT.log(throwable.toString());
                    GWT.log(throwable.getMessage());
                }

                @Override
                public void onSuccess(Method method, TaskDto taskDto) {
                    task = taskDto;
                    titleText.setValue(taskDto.getTitle());
                    description.setValue(taskDto.getDescription());
                    for(int i=0;i<ownerId.getItemCount();i++){
                        if(String.valueOf(task.getOwnerId()).equals(ownerId.getValue(i))){
                            ownerId.setSelectedIndex(i);
                        }
                    }
                    for(int i=0;i<assigneeId.getItemCount();i++){
                        if(String.valueOf(task.getAssigneeId()).equals(assigneeId.getValue(i))){
                            assigneeId.setSelectedIndex(i);
                        }
                    }
                }
            });
        }

        //Заполняем список пользователей в списках
        userClient.getAll(token, new MethodCallback<List<UserDto>>(){
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
            }
            @Override
            public void onSuccess(Method method, List<UserDto> userDtoList) {
                assigneeId.clear();
                ownerId.clear();
                assigneeId.addItem("-any-","");
                ownerId.addItem("-any-","");
                int i = 1;
                for (UserDto userDto : userDtoList) {
                    ownerId.addItem(userDto.getTitle(), String.valueOf(userDto.getId()));
                    assigneeId.addItem(userDto.getTitle(), String.valueOf(userDto.getId()));
                    //Если уже загрузись даные по редактируемой задаче, устанавливаем текущую позицию в списках
                    if(task!=null){
                        if(userDto.getId().equals(task.getOwnerId())){
                            ownerId.setSelectedIndex(i);
                        }
                        if(userDto.getId().equals(task.getAssigneeId())){
                            assigneeId.setSelectedIndex(i);
                        }
                    }
                    i++;
                }
            }
        });
    }
}
