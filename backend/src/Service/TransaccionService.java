/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Util.Response;
import DAO.TransaccionDao;
import Model.Transaccion;
/**
 *
 * @author hp
 */
public class TransaccionService {

    private TransaccionDao repo;

    public TransaccionService() {
        repo = new TransaccionDao();
    }

    // INSERTAR
    public Response<Transaccion> insertar(Transaccion t) {
        return repo.insertar(t);
    }

    // ACTUALIZAR
    public Response<Transaccion> actualizar(Transaccion t) {
        return repo.actualizar(t);
    }

    // ELIMINAR
    public Response<Transaccion> eliminar(int id) {
        return repo.eliminar(id);
    }

    // BUSCAR POR ID
    public Response<Transaccion> obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    // LISTAR TODAS
    public Response<Transaccion> obtenerTodos() {
        return repo.obtenerTodos();
    }

    // OBTENER BALANCE TOTAL
    public double obtenerBalanceTotal() {

        double total = 0;

        Response<Transaccion> respuesta = repo.obtenerTodos();

        if (respuesta.getLista() != null) {

            for (Transaccion t : respuesta.getLista()) {

                total += t.getMonto();
            }
        }

        return total;
    }
}
