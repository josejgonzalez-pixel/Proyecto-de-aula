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
public abstract class Transaccion {
    
    protected int idTransaccion;
    protected double monto;
    protected LocalDate fecha;
    protected String descripcion;

    protected int idUsuario;
    protected int idCategoria;

    public Transaccion() {
    }

    public Transaccion(int idTransaccion, double monto, LocalDate fecha, String descripcion, int idUsuario, int idCategoria) {
        this.idTransaccion = idTransaccion;
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
