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
public class Presupuesto {
    
    private int idPresupuesto;
    private double montoInicial;
    private double montoActual;
    private LocalDate fechaCreacion;
    private int idUsuario;

    public Presupuesto() {
    }

    public Presupuesto(int idPresupuesto, double montoInicial, double montoActual, LocalDate fechaCreacion, int idUsuario) {
        this.idPresupuesto = idPresupuesto;
        this.montoInicial = montoInicial;
        this.montoActual = montoActual;
        this.fechaCreacion = fechaCreacion;
        this.idUsuario = idUsuario;
    }
    
    public boolean verificarLimite() {
        return montoActual <= 0;
    }
}
