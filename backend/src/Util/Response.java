/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.List;

/**
 *
 * @author Camil
 */
public class Response<T> {
    
    public boolean estado;
    public String mensaje;
    public T entidad;
    public List<T> lista;

    public Response() {}

    public Response(boolean estado, String mensaje, T entidad, List<T> lista) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.entidad = entidad;
        this.lista = lista;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getEntidad() {
        return entidad;
    }

    public void setEntidad(T entidad) {
        this.entidad = entidad;
    }

    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }
}
