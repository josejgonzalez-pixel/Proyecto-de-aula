/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Camil
 */
public class VoiceCommandResponse {
    private boolean exito;
    private String respuesta;

    public VoiceCommandResponse() {
    }

    public VoiceCommandResponse(boolean exito, String respuesta) {
        this.exito = exito;
        this.respuesta = respuesta;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
