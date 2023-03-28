package com.example.application.views.empty;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.example.application.Porcupine;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Empty")
@Route(value = "")
public class EmptyView extends VerticalLayout {

    public EmptyView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

        final Porcupine porcupine = new Porcupine(System.getenv("PICOVOICE_ACCESSKEY"));
        add(new Button("Start wake word detection", e -> {
            Button button = e.getSource();
            if (!porcupine.isStarted()) {
                porcupine.start();
                button.setIcon(new Icon(VaadinIcon.MICROPHONE));
                button.setText("Stop wake word detection");
            } else {
                porcupine.stop();
                button.setIcon(null);
                button.setText("Start wake word detection");
            }
        }));
    }
}
