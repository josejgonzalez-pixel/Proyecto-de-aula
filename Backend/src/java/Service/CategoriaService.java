/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.CategoriaDao;
import Util.Response;
import Model.Categoria;
/**
 *
 * @author hp
 */
public class CategoriaService {

    private CategoriaDao repo;

    public CategoriaService() {
        repo = new CategoriaDao();
    }

    // INSERTAR
    public Response<Categoria> insertar(Categoria categoria) {
        return repo.insertar(categoria);
    }

    // ACTUALIZAR
    public Response<Categoria> actualizar(Categoria categoria) {
        return repo.actualizar(categoria);
    }

    // ELIMINAR
    public Response<Categoria> eliminar(int id) {
        return repo.eliminar(id);
    }

    // OBTENER POR ID
    public Response<Categoria> obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    // OBTENER TODAS
    public Response<Categoria> obtenerTodos() {
        return repo.obtenerTodos();
    }

    // VERIFICAR SI LA CATEGORÍA ESTÁ EN USO
    public boolean categoriaEnUso(int idCategoria) {

        Response<Categoria> respuesta = repo.obtenerPorId(idCategoria);

        return respuesta.isEstado()
        && respuesta.getEntidad() != null;
    }
    
    public Integer obtenerIdPorNombre(String nombreCategoria) {

    Response<Categoria> respuesta = repo.obtenerTodos();

    if (respuesta.getLista() != null) {

        for (Categoria categoria : respuesta.getLista()) {

            if (categoria.getNombreCategoria()
                    .equalsIgnoreCase(nombreCategoria)) {

                return categoria.getIdCategoria();
            }
        }
    }

    return null;
}
}
