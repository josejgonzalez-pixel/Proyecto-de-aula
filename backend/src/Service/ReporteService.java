/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.ReporteDao;
import Util.Response;
import Model.Reporte;
import Model.Transaccion;
/**
 *
 * @author hp
 */
public class ReporteService {

    private ReporteDao repo;

    public ReporteService() {
        repo = new ReporteDao();
    }

    // INSERTAR
    public Response<Reporte> insertar(Reporte r) {
        return repo.insertar(r);
    }

    // ACTUALIZAR
    public Response<Reporte> actualizar(Reporte r) {
        return repo.actualizar(r);
    }

    // ELIMINAR
    public Response<Reporte> eliminar(int id) {
        return repo.eliminar(id);
    }

    // OBTENER POR ID
    public Response<Reporte> obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    // OBTENER TODOS
    public Response<Reporte> obtenerTodos() {
        return repo.obtenerTodos();
    }

    // OBTENER DATOS PARA GRAFICO
    public Response<Transaccion> obtenerDatosParaGrafico(String tipo) {
        return repo.obtenerDatosParaGrafico(tipo);
    }

    // OBTENER RESUMEN FINANCIERO
    public double obtenerResumenFinanciero() {
        return repo.obtenerResumenFinanciero();
    }
}
