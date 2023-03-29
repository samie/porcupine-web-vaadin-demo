package com.example.application.views.empty;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.example.application.Porcupine;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Random;

@PageTitle("Empty")
@Route(value = "")
public class EmptyView extends VerticalLayout {

    public EmptyView() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

        final Paragraph info = new Paragraph("Try saying 'Computer.'");
        info.setVisible(false);

        final Porcupine porcupine = new Porcupine(System.getenv("PICOVOICE_ACCESSKEY"));
        final Button startButton = new Button("Start wake word detection", e -> {
            Button button = e.getSource();
            if (!porcupine.isStarted()) {
                porcupine.start();
                button.setIcon(new Icon(VaadinIcon.MICROPHONE));
                button.setText("Stop wake word detection");
                info.setVisible(true);
            } else {
                porcupine.stop();
                button.setIcon(null);
                button.setText("Start wake word detection");
                info.setVisible(false);
            }
        });
        startButton.addClickShortcut(Key.SPACE);
        add(startButton);
        add(info);

        Porcupine.addListener(evt -> {
            Notification.show("Detected word '"+evt.getLabel()+"'");
            evt.getSource().getStyle().set("background-color", "#"+Integer.toHexString(new Random().nextInt(16777215))+"60");
        });
    }
}
