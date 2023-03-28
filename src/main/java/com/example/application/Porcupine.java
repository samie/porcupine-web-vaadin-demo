package com.example.application;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@NpmPackage(value = "@picovoice/porcupine-web", version = "2.1.16")
@NpmPackage(value = "@picovoice/web-voice-processor", version = "4.0.5")
@JsModule("./porcupine-integration.js")
public class Porcupine { 

    boolean started = false;
    
    public Porcupine(String picovoiceAccesskey) {
        UI.getCurrent().getPage().executeJs("window.vaadinPorcupine.key=$0;"
					, picovoiceAccesskey);
    }

    public void start() {
        this.started = true;
        UI.getCurrent().getPage().executeJs("window.vaadinPorcupine.start()");
    }

    public void stop() {
        this.started = false;
        UI.getCurrent().getPage().executeJs("window.vaadinPorcupine.stop()");
    }

    public boolean isStarted() {
        return this.started;
    }

    public static class WakewordEvent
            extends ComponentEvent<UI> {
        private final String label;

        public WakewordEvent(UI source,
                             boolean fromClient,
                             String label) {
            super(source, fromClient);
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }
    }

    public static void addListener(ComponentEventListener<WakewordEvent> listener) {
        UI.getCurrent().getElement().addEventListener("voice-wakeword", e -> {
            listener.onComponentEvent(new WakewordEvent(UI.getCurrent(),
                    true,
                    e.getEventData().getString("event.detail")));
        }).addEventData("event.detail");
    }

}
