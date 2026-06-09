package com.portfolio.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "contact", layout = MainLayout.class)
public class ContactView extends Div implements BeforeEnterObserver {
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // Reroute to HomeView (empty route)
        event.forwardTo("");

        // Smooth scroll to the contact shulker boxes section
        UI.getCurrent().getPage().executeJs(
            "setTimeout(() => { " +
            "  const el = document.getElementById('contact');" +
            "  if (el) el.scrollIntoView({ behavior: 'smooth' });" +
            "}, 150);"
        );
    }
}
