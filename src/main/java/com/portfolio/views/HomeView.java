package com.portfolio.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Vaibhav | Fullstack Software Engineer")
public class HomeView extends Div {
    public HomeView() {
        setText("Welcome to Vaibhav's Retro Portfolio");
    }
}
