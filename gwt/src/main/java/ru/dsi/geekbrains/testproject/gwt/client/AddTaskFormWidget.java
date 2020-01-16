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

public class AddTaskFormWidget extends Composite {
    @UiField
    FormPanel form;

    @UiField
    TextBox titleText;

    @UiField
    ListBox statusId;

    private TaskTableWidget taskTableWidget;
    private TaskClient taskClient;

    @UiTemplate("AddTaskForm.ui.xml")
    interface AddTaskFormBinder extends UiBinder<Widget, AddTaskFormWidget> {
    }

    private static AddTaskFormWidget.AddTaskFormBinder uiBinder = GWT.create(AddTaskFormWidget.AddTaskFormBinder.class);

    public AddTaskFormWidget(TaskTableWidget taskTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.form.setAction(Defaults.getServiceRoot().concat("task"));
        this.taskTableWidget = taskTableWidget;
        this.taskClient = GWT.create(TaskClient.class);
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        GWT.log("STORAGE: " + token);
        taskClient.add(token, statusId.getSelectedValue(), titleText.getValue(), new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Невозможно добавить задачу: "+method.getResponse().getText());
            }

            @Override
            public void onSuccess(Method method, Void result) {
                taskTableWidget.refresh();
            }
        });
    }
}
