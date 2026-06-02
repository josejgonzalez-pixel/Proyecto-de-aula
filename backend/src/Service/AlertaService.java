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
    public Response<Alerta> eliminar(int idAlerta) throws Exception {
        return repo.eliminar(idAlerta);
    }

    // OBTENER POR ID
    public Response<Alerta> obtenerPorId(int idAlerta) throws Exception {
        return repo.obtenerPorId(idAlerta);
    }

    // OBTENER TODAS
    public Response<Alerta> obtenerTodos() throws Exception {
        return repo.obtenerTodos();
    }

    // ENVIAR ALERTA
    public void enviarAlerta(int idAlerta) throws Exception {

        Response<Alerta> respuesta = repo.obtenerPorId(idAlerta);

        if (respuesta.isEstado()
                && respuesta.getEntidad() != null) {

            respuesta.getEntidad().enviarAlerta();
        }
    }
}