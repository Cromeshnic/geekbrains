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
import ru.dsi.geekbrains.testproject.common.JwtAuthRequestDto;
import ru.dsi.geekbrains.testproject.common.JwtAuthResponseDto;

public class LoginForm extends Composite {
    @UiField
    FormPanel form;

    @UiField
    TextBox textUsername;

    @UiField
    TextBox textPassword;

    @UiTemplate("LoginForm.ui.xml")
    interface LoginFormBinder extends UiBinder<Widget, LoginForm> {
    }

    private TaskTableWidget itemsTableWidget;
    private TabLayoutPanel tabPanel;

    private static LoginForm.LoginFormBinder uiBinder = GWT.create(LoginForm.LoginFormBinder.class);

    public LoginForm(TabLayoutPanel tabPanel, TaskTableWidget itemsTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.form.setAction(Defaults.getServiceRoot().concat("items"));
        this.itemsTableWidget = itemsTableWidget;
        this.tabPanel = tabPanel;
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        Window.alert(event.getResults());
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        /*AuthClient authClient = GWT.create(AuthClient.class);
        authClient.authenticate("admin", "100", new MethodCallback<String>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log("FAILED: " + method.getResponse().getStatusCode() + " " + method.getResponse().getStatusText());
            }

            @Override
            public void onSuccess(Method method, String s) {
                GWT.log("OK: " + method.getResponse().getStatusCode() + " " + method.getResponse().getStatusText());
            }
        });*/


        JwtAuthRequestDto jwtAuthRequestDto = new JwtAuthRequestDto(textUsername.getValue(), textPassword.getValue());
        AuthClient authClient = GWT.create(AuthClient.class);
        authClient.authenticate(jwtAuthRequestDto, new MethodCallback<JwtAuthResponseDto>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(method.getResponse().getText());
                Window.alert(method.getResponse().getText());
            }

            @Override
            public void onSuccess(Method method, JwtAuthResponseDto jwtAuthResponseDto) {
                GWT.log("Token: "+jwtAuthResponseDto.getToken());
                Storage.getLocalStorageIfSupported().setItem("jwt", "Bearer " +  jwtAuthResponseDto.getToken());
                itemsTableWidget.refresh();
                tabPanel.selectTab(1);
            }
        });
    }
}