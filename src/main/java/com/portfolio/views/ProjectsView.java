package com.portfolio.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "projects", layout = MainLayout.class)
public class ProjectsView extends Div implements BeforeEnterObserver {
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        UI.getCurrent().getPage().setLocation("/#projects");
    }
}
