/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.AlertaDao;
import Util.Response;
import Model.Alerta;
/**
 *
 * @author hp
 */
public class AlertaService {

    private AlertaDao repo;

    public AlertaService() {
        repo = new AlertaDao();
    }

    // INSERTAR
    public Response<Alerta> insertar(Alerta alerta) throws Exception {
        return repo.insertar(alerta);
    }

    // ACTUALIZAR
    public Response<Alerta> actualizar(Alerta alerta) throws Exception {
        return repo.actualizar(alerta);
    }

    // ELIMINAR
    public Response<Alerta> eliminar(String mensaje) throws Exception {
        return repo.eliminar(mensaje);
    }

    // OBTENER POR MENSAJE
    public Response<Alerta> obtenerPorMensaje(String mensaje) throws Exception {
        return repo.obtenerPorMensaje(mensaje);
    }

    // OBTENER TODAS
    public Response<Alerta> obtenerTodos() throws Exception {
        return repo.obtenerTodos();
    }

    // ENVIAR ALERTA
    public void enviarAlerta(String mensaje) throws Exception {

        Response<Alerta> respuesta =
                repo.obtenerPorMensaje(mensaje);

        if (respuesta.isEstado()
            && respuesta.getEntidad() != null) {

            respuesta.getEntidad().enviarAlerta();
        } 
    }
}