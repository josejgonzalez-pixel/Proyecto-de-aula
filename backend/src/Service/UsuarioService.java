/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.UsuarioDao;
import DAO.Response;
import Model.Usuario;
import java.util.List;
/**
 *
 * @author hp
 */
public class UsuarioService {

    private UsuarioDao repo;

    public UsuarioService() {
        repo = new UsuarioDao();
    }

    // INSERTAR
    public Response<Usuario> insertar(Usuario usuario) {
        return repo.insertar(usuario);
    }

    // ACTUALIZAR
    public Response<Usuario> actualizar(Usuario usuario) {
        return repo.actualizar(usuario);
    }

    // ELIMINAR
    public Response<Usuario> eliminar(int id) {
        return repo.eliminar(id);
    }

    // BUSCAR POR ID
    public Response<Usuario> obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    // LISTAR TODOS
    public Response<Usuario> obtenerTodos() {
        return repo.obtenerTodos();
    }

    // VALIDAR LOGIN
    public boolean validarLogin(String correo, String contrasena) {

        List<Usuario> usuarios = (List<Usuario>) repo.obtenerTodos().getLista();

        if (usuarios == null) {
            return false;
        }

        for (Usuario u : usuarios) {

            if (u.getCorreo().equals(correo)
                    && u.getContrasena().equals(contrasena)) {

                return true;
            }
        }

        return false;
    }

    // VALIDAR SI EL CORREO YA EXISTE
    public boolean existeCorreo(String correo) {

        List<Usuario> usuarios = (List<Usuario>) repo.obtenerTodos().getLista();

        if (usuarios == null) {
            return false;
        }

        for (Usuario u : usuarios) {

            if (u.getCorreo().equals(correo)) {
                return true;
            }
        }

        return false;
    }
}
