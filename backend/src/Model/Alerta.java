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
    
    private int idAlerta;
    private String mensaje;
    private LocalDate fechaAlerta;
    private int idUsuario;

    public Alerta() {
    }

    public Alerta(int idAlerta, String mensaje,
                  LocalDate fechaAlerta, int idUsuario) {

        this.idAlerta = idAlerta;
        this.mensaje = mensaje;
        this.fechaAlerta = fechaAlerta;
        this.idUsuario = idUsuario;
    }

    public void enviarAlerta() {
        System.out.println(mensaje);
    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(int idAlerta) {
        this.idAlerta = idAlerta;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
