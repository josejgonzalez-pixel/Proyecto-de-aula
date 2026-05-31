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
public class Meta {
    
    private int idMeta;
    private String nombreMeta;
    private double montoMeta;
    private double montoActual;
    private LocalDate fechaLimite;
    private int idUsuario;

    public Meta() {
    }

    public Meta(int idMeta, String nombreMeta, double montoMeta, double montoActual, LocalDate fechaLimite, int idUsuario) {
        this.idMeta = idMeta;
        this.nombreMeta = nombreMeta;
        this.montoMeta = montoMeta;
        this.montoActual = montoActual;
        this.fechaLimite = fechaLimite;
        this.idUsuario = idUsuario;
    }
    
    public double calcularProgreso() {
        return (montoActual / montoMeta) * 100;
    }

    public int getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(int idMeta) {
        this.idMeta = idMeta;
    }

    public String getNombreMeta() {
        return nombreMeta;
    }

    public void setNombreMeta(String nombreMeta) {
        this.nombreMeta = nombreMeta;
    }

    public double getMontoMeta() {
        return montoMeta;
    }

    public void setMontoMeta(double montoMeta) {
        this.montoMeta = montoMeta;
    }

    public double getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(double montoActual) {
        this.montoActual = montoActual;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
