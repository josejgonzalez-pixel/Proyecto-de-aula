/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Model.TipoComando;

/**
 *
 * @author Camil
 */
public class IntentDetector {
    public TipoComando detectar(String mensaje) {

        mensaje = mensaje.toLowerCase();

        if(mensaje.contains("crear categoria")
                || mensaje.contains("crear categoría")) {
            return TipoComando.CREAR_CATEGORIA;
        }

        if(mensaje.contains("crear meta")) {
            return TipoComando.CREAR_META;
        }

        if(mensaje.contains("crear presupuesto")) {
            return TipoComando.CREAR_PRESUPUESTO;
        }

        if(mensaje.contains("mostrar metas")) {
            return TipoComando.MOSTRAR_METAS;
        }

        if(mensaje.contains("mostrar gastos")) {
            return TipoComando.MOSTRAR_GASTOS;
        }

        if(mensaje.contains("mostrar ingresos")) {
            return TipoComando.MOSTRAR_INGRESOS;
        }

        if(mensaje.contains("consultar presupuesto")
                || mensaje.contains("mostrar presupuesto")) {
            return TipoComando.CONSULTAR_PRESUPUESTO;
        }

        return TipoComando.DESCONOCIDO;
    }
}
