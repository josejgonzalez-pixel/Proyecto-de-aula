/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import DAO.GastoDao;
import Util.Response;
import Model.Gasto;

/**
 *
 * @author hp
 */
public class GastoService {

    private GastoDao repo;

    public GastoService() {
        repo = new GastoDao();
    }

    // INSERTAR
    public Response<Gasto> insertar(Gasto gasto) {
        return repo.insertar(gasto);
    }

    // ACTUALIZAR
    public Response<Gasto> actualizar(Gasto gasto) {
        return repo.actualizar(gasto);
    }

    // ELIMINAR
    public Response<Gasto> eliminar(int id) {
        return repo.eliminar(id);
    }

    // OBTENER POR ID
    public Response<Gasto> obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    // OBTENER TODOS
    public Response<Gasto> obtenerTodos() {
        return repo.obtenerTodos();
    }

    // CALCULAR TOTAL DE GASTOS
    public double calcularTotalGastos() {

        double total = 0;

        Response<Gasto> respuesta = repo.obtenerTodos();

        if (respuesta.getLista() != null) {

            for (Gasto gasto : respuesta.getLista()) {
                total += gasto.getMonto();
            }
        }

        return total;
    }
}