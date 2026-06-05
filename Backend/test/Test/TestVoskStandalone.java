/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.vosk.Model;
import org.vosk.Recognizer;

/**
 *
 * @author jos13
 */
public class TestVoskStandalone {
    public static void main(String[] args) {
        try {
            // 1. Cargar modelo (asegúrate de que esta ruta sea correcta en tu PC)
            Model model =  new Model("C:/Users/jos13/Documents/NetBeansProjects/Proyecto-de-aula/Backend/src/java/vosk-model-small-es-0.42");
            
            // 2. Leer un archivo WAV (PCM 16kHz, 16bit, Mono)
            byte[] audioBytes = Files.readAllBytes(Paths.get("audio_prueba.wav"));

            // 3. Procesar con Vosk
            try (Recognizer recognizer = new Recognizer(model, 16000.0f)) {
                if (recognizer.acceptWaveForm(audioBytes, audioBytes.length)) {
                    System.out.println("Resultado final: " + recognizer.getResult());
                } else {
                    System.out.println("Resultado parcial: " + recognizer.getPartialResult());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
