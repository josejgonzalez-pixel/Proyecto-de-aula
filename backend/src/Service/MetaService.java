/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.MetaDao;
import Util.Response;
import Model.Meta;
/**
 *
 * @author hp
 */
public class MetaService {

    private MetaDao repo;

    public MetaService() {
        repo = new MetaDao();
    }

    // INSERTAR
    public Response<Meta> insertar(Meta meta) {
        return repo.insertar(meta);
    }

    // ACTUALIZAR
    public Response<Meta> actualizar(Meta meta) {
        return repo.actualizar(meta);
    }

    // ELIMINAR
    public Response<Meta> eliminar(int id) {
        return repo.eliminar(id);
    }

    // OBTENER POR ID
    public Response<Meta> obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    // OBTENER TODAS
    public Response<Meta> obtenerTodos() {
        return repo.obtenerTodos();
    }

    // CALCULAR PROGRESO
    public double calcularProgreso(int idMeta) {

        Response<Meta> respuesta = repo.obtenerPorId(idMeta);

        if (respuesta.isSuccess() && respuesta.getObjeto() != null) {

            Meta meta = respuesta.getObjeto();

            return meta.calcularProgreso();
        }

        return 0;
    }
}
