/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Util.Response;
import DAO.TransaccionDao;
import Model.Transaccion;
import java.util.Map;

/**
 *
 * @author hp
 */
public class TransaccionService {

    private TransaccionDao repo;

    public Map<String, Double> obtenerResumen(int idUsuario) throws Exception {
        return repo.obtenerResumen(idUsuario);
    }

    public TransaccionService() {
        repo = new TransaccionDao();
    }
    
    // INSERTAR
    public Response<Transaccion> insertar(Transaccion t) throws Exception {
        return repo.insertar(t);
    }

    // ACTUALIZAR
    public Response<Transaccion> actualizar(Transaccion t) throws Exception {
        return repo.actualizar(t);
    }

    // ELIMINAR
    public Response<Transaccion> eliminar(int id) throws Exception {
        return repo.eliminar(id);
    }

    // BUSCAR POR ID
    public Response<Transaccion> obtenerPorId(int id) throws Exception {
        return repo.obtenerPorId(id);
    }

    // LISTAR TODAS
    public Response<Transaccion> obtenerTodos(int idUsuario) throws Exception {
        return repo.obtenerTodosPorUsuario(idUsuario);
    }
}
