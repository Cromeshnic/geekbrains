package ru.dsi.geekbrains.testproject.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Defaults;

public class AddTaskFormWidget extends Composite {
    @UiField
    FormPanel form;

    @UiField
    TextBox titleText;

    private TaskTableWidget taskTableWidget;

    @UiTemplate("AddTaskForm.ui.xml")
    interface AddTaskFormBinder extends UiBinder<Widget, AddTaskFormWidget> {
    }

    private static AddTaskFormWidget.AddTaskFormBinder uiBinder = GWT.create(AddTaskFormWidget.AddTaskFormBinder.class);

    public AddTaskFormWidget(TaskTableWidget taskTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.form.setAction(Defaults.getServiceRoot().concat("task"));
        this.taskTableWidget = taskTableWidget;
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
        if (titleText.getText().length() < 4) {
            Window.alert("Название должно быть не менее 4 символов");
            event.cancel();
        }
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        taskTableWidget.refresh();
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        form.submit();
    }
}
