/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PresupuestoDao;
import Util.Response;
import Model.Presupuesto;

/**
 *
 * @author hp
 */
public class PresupuestoService {

    private PresupuestoDao repo;

    public PresupuestoService() {
        repo = new PresupuestoDao();
    }

    // INSERTAR
    public Response<Presupuesto> insertar(Presupuesto p) throws Exception {
        return repo.insertar(p);
    }

    // ACTUALIZAR
    public Response<Presupuesto> actualizar(Presupuesto p) throws Exception {
        return repo.actualizar(p);
    }

    // ELIMINAR
    public Response<Presupuesto> eliminar(int id) throws Exception {
        return repo.eliminar(id);
    }

    // OBTENER POR ID
    public Response<Presupuesto> obtenerPorId(int id) throws Exception {
        return repo.obtenerPorId(id);
    }

    // OBTENER TODOS
    public Response<Presupuesto> obtenerTodos() throws Exception {
        return repo.obtenerTodos();
    }

    // ACTUALIZAR MONTO ACTUAL
    public Response<Presupuesto> actualizarMontoActual(int idPresupuesto, double nuevoMonto) throws Exception {

        Response<Presupuesto> respuesta = repo.obtenerPorId(idPresupuesto);

        if (!respuesta.isSuccess() || respuesta.getObjeto() == null) {

            return new Response<>(
                    false,
                    "No se encontro el presupuesto",
                    null,
                    null
            );
        }

        Presupuesto presupuesto = (Presupuesto) respuesta.getObjeto();

        presupuesto.setMontoActual(nuevoMonto);

        return repo.actualizar(presupuesto);
    }

    // VERIFICAR LIMITE
    public boolean verificarLimite(int idPresupuesto) throws Exception {

        Response<Presupuesto> respuesta = repo.obtenerPorId(idPresupuesto);

        if (!respuesta.isSuccess() || respuesta.getObjeto() == null) {
            return false;
        }

        Presupuesto presupuesto = (Presupuesto) respuesta.getObjeto();

        return presupuesto.verificarLimite();
    }
}