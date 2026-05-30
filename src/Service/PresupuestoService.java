/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.PresupuestoDao;
import DAO.Response;
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
    public Response<Presupuesto> insertar(Presupuesto p) {
        return repo.insertar(p);
    }

    // ACTUALIZAR
    public Response<Presupuesto> actualizar(Presupuesto p) {
        return repo.actualizar(p);
    }

    // ELIMINAR
    public Response<Presupuesto> eliminar(int id) {
        return repo.eliminar(id);
    }

    // OBTENER POR ID
    public Response<Presupuesto> obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    // OBTENER TODOS
    public Response<Presupuesto> obtenerTodos() {
        return repo.obtenerTodos();
    }

    // ACTUALIZAR MONTO ACTUAL
    public Response<Presupuesto> actualizarMontoActual(int idPresupuesto, double nuevoMonto) {

        Response<Presupuesto> respuesta = repo.obtenerPorId(idPresupuesto);

        if (!respuesta.isSuccess() || respuesta.getObjeto() == null) {

            return new Response<>(
                    false,
                    "No se encontro el presupuesto",
                    null,
                    null
            );
        }

        Presupuesto presupuesto = respuesta.getObjeto();

        presupuesto.setMontoActual(nuevoMonto);

        return repo.actualizar(presupuesto);
    }

    // VERIFICAR LIMITE
    public boolean verificarLimite(int idPresupuesto) {

        Response<Presupuesto> respuesta = repo.obtenerPorId(idPresupuesto);

        if (!respuesta.isSuccess() || respuesta.getObjeto() == null) {
            return false;
        }

        Presupuesto presupuesto = respuesta.getObjeto();

        return presupuesto.verificarLimite();
    }
}