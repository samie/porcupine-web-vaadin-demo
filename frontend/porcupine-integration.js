import { WebVoiceProcessor } from "@picovoice/web-voice-processor";
import { PorcupineWorker, BuiltInKeyword } from "@picovoice/porcupine-web";
import modelParams from "./porcupine_params.js";

// a global 'vaadinPorcupine' integration instance is enough
window.vaadinPorcupine = window.vaadinPorcupine || {
    key: null,
    async start() {
        console.log('Starting wake word detection');
        window.vaadinPorcupine._worker = window.vaadinPorcupine._worker || await PorcupineWorker.create(
            window.vaadinPorcupine.key,
            [BuiltInKeyword.Computer],
            window.vaadinPorcupine.keywordDetectionCallback,
            {base64: modelParams }
        );
        await WebVoiceProcessor.subscribe(window.vaadinPorcupine._worker);
    },
    async stop() {
        console.log('Stopping wake word detection');
        await WebVoiceProcessor.unsubscribe(window.vaadinPorcupine._worker);
    },
    keywordDetectionCallback(detection) {
        console.log(`Detected keyword: ${detection.label}`);
        const e = new CustomEvent("voice-wakeword", 
                    { "detail": detection.label });
        document.body.dispatchEvent(e);
    }
}
