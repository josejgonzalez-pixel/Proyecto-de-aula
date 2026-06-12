/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.IngresoDao;
import Util.Response;
import Model.Ingreso;
/**
 *
 * @author hp
 */
public class IngresoService {

    private IngresoDao repo;

    public IngresoService() {
        repo = new IngresoDao();
    }

    // INSERTAR
    public Response<Ingreso> insertar(Ingreso ingreso) throws Exception {
        // 1. Validaciones de Negocio
        if (ingreso.getDescripcion() == null || ingreso.getDescripcion().trim().isEmpty()) {
            return new Response<>(false, "La descripción no puede estar vacía.", null, null);
        }
        
        if (ingreso.getMonto() <= 0) {
            return new Response<>(false, "El monto debe ser mayor a cero.", null, null);
        }

        // 2. Llamada al DAO
        Response<Ingreso> respuestaDao = repo.insertar(ingreso);
        
        if (respuestaDao.isEstado()) { // Asumiendo que tu clase Response tiene un método isEstado() o getEstado()
            return new Response<>(true, "Ingreso registrado correctamente.", null, null);
        } else {
            return new Response<>(false, "Error: " + respuestaDao.getMensaje(), null, null);
        }
    }

    // ACTUALIZAR
    public Response<Ingreso> actualizar(Ingreso ingreso) throws Exception {
        return repo.actualizar(ingreso);
    }

    // ELIMINAR
    public Response<Ingreso> eliminar(int id) throws Exception {
        return repo.eliminar(id);
    }

    // OBTENER POR ID
    public Response<Ingreso> obtenerPorId(int id) throws Exception {
        return repo.obtenerPorId(id);
    }

    // OBTENER TODOS
    public Response<Ingreso> obtenerTodos() throws Exception {
        return repo.obtenerTodos();
    }

    // CALCULAR TOTAL DE INGRESOS
    public double calcularTotalIngresos() throws Exception {

        double total = 0;

        Response<Ingreso> respuesta = repo.obtenerTodos();

        if (respuesta.getLista() != null) {

            for (Ingreso ingreso : respuesta.getLista()) {
                total += ingreso.getMonto();
            }
        }

        return total;
    }
}