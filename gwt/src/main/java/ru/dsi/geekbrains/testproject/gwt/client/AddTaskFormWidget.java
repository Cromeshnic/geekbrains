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
        taskClient.add(token, statusId.getSelectedValue(), titleText.getValue(), new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                Window.alert("Невозможно добавить задачу: "+throwable.getMessage());
            }

            @Override
            public void onSuccess(Method method, Void result) {
                taskTableWidget.refresh();
            }
        });
    }
}
