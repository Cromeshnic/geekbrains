package ru.dsi.geekbrains.testproject.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.fusesource.restygwt.client.Defaults;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8189/rest/api/1.0");
        TaskTableWidget itemsTableWidget = new TaskTableWidget();
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(new AddTaskFormWidget(itemsTableWidget));
        verticalPanel.add(itemsTableWidget);

        TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Style.Unit.EM);
        tabPanel.setAnimationDuration(100);
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);

        LoginForm loginForm = new LoginForm(tabPanel, itemsTableWidget);
        tabPanel.add(loginForm, "Login");

        tabPanel.add(verticalPanel, "Main Page");

        tabPanel.setHeight("800px");

        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("cwTabPanel");
        tabPanel.getTabWidget(0).setVisible(false);
        tabPanel.getTabWidget(1).setVisible(false);

        RootPanel.get().add(tabPanel);
    }
}