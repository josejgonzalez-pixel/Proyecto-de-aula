/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author Camil
 */
public class Alerta {
    
    private String mensaje;
    private LocalDate fechaAlerta;

    public Alerta() {
    } 

    public Alerta(String mensaje, LocalDate fechaAlerta) {
        this.mensaje = mensaje;
        this.fechaAlerta = fechaAlerta;
    }
    
    public void enviarAlerta() {
        System.out.println(mensaje);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDate getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(LocalDate fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }
}
