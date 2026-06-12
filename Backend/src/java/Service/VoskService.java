/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.io.IOException;
import org.vosk.Model;
import org.vosk.Recognizer;

/**
 *
 * @author jos13
 */
public class VoskService {
    private Model model;

    public VoskService() {
        try {
            // Ajusta la ruta a tu carpeta del modelo. 
            // Si está dentro de src/java, esta ruta suele funcionar:
            this.model = new Model("C:/Users/jos13/Documents/NetBeansProjects/Proyecto-de-aula/Backend/src/java/vosk-model-small-es-0.42");
        } catch (IOException e) {
            System.err.println("Error cargando el modelo: " + e.getMessage());
        }
    }

    public String transcribir(byte[] audioData) throws IOException {
        try (Recognizer recognizer = new Recognizer(model, 16000.0f)) {
            if (recognizer.acceptWaveForm(audioData, audioData.length)) {
                return recognizer.getResult(); // Devuelve JSON con el texto
            } else {
                return recognizer.getPartialResult();
            }
        }
    }
}