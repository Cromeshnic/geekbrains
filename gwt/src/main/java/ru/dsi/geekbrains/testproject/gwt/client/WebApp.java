package ru.dsi.geekbrains.testproject.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.fusesource.restygwt.client.Defaults;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8189/rest/api/1.0");
        TaskTableWidget itemsTableWidget = new TaskTableWidget();
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(new AddTaskFormWidget(itemsTableWidget));
        verticalPanel.add(itemsTableWidget);
        RootPanel.get().add(verticalPanel);
    }
}