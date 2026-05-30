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
public class Ingreso extends Transaccion{
    
    private String fuente;

    public Ingreso() {
    }

    public Ingreso(String fuente, int idTransaccion, double monto, LocalDate fecha, String descripcion, int idUsuario, int idCategoria) {
        super(idTransaccion, monto, fecha, descripcion, idUsuario, idCategoria);
        this.fuente = fuente;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
}
