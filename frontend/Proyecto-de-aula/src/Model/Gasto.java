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
public class Gasto extends Transaccion{
    
    private String tipoPago;

    public Gasto() {
    }

    public Gasto(String tipoPago, int idTransaccion, double monto, LocalDate fecha, String descripcion, int idUsuario, int idCategoria) {
        super(idTransaccion, monto, fecha, descripcion, idUsuario, idCategoria);
        this.tipoPago = tipoPago;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
}
