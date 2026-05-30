/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.ReporteDao;
import DAO.TransaccionDao;
import DAO.Response;
import Model.Reporte;
import Model.Transaccion;
/**
 *
 * @author hp
 */
public class ReporteService {

    private ReporteDao reporteDao;
    private TransaccionDao transaccionDao;

    public ReporteService() {
        reporteDao = new ReporteDao();
        transaccionDao = new TransaccionDao();
    }

    // INSERTAR
    public Response<Reporte> insertar(Reporte reporte) {
        return reporteDao.insertar(reporte);
    }

    // ACTUALIZAR
    public Response<Reporte> actualizar(Reporte reporte) {
        return reporteDao.actualizar(reporte);
    }

    // ELIMINAR
    public Response<Reporte> eliminar(int id) {
        return reporteDao.eliminar(id);
    }

    // BUSCAR POR ID
    public Response<Reporte> obtenerPorId(int id) {
        return reporteDao.obtenerPorId(id);
    }

    // LISTAR TODOS
    public Response<Reporte> obtenerTodos() {
        return reporteDao.obtenerTodos();
    }

    /**
     * Obtiene las transacciones para generar graficos.
     * Puedes filtrar posteriormente por ingresos o gastos.
     */
    public Response<Transaccion> obtenerDatosParaGrafico(String tipo) {

        Response<Transaccion> respuesta =
                transaccionDao.obtenerTodos();

        return respuesta;
    }

    /**
     * Calcula el balance financiero total.
     */
    public double obtenerResumenFinanciero() {

        double total = 0;

        Response<Transaccion> respuesta =
                transaccionDao.obtenerTodos();

        if (respuesta.getLista() != null) {

            for (Transaccion t : respuesta.getLista()) {

                total += t.getMonto();
            }
        }

        return total;
    }
}
