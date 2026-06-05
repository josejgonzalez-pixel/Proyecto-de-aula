/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import DAO.UsuarioDao;
import Util.Response;
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

    //REGISTRAR USUARIO
    public Response<Usuario> RegistrarNuevoUsuario(Usuario usuario) {
        return repo.RegistrarNuevoUsuario(usuario);
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

        // Recuperamos la respuesta del DAO
        Response responseDao = repo.obtenerTodos();

        if (responseDao == null) {
            System.out.println("ERROR: El objeto Response del DAO vino completamente NULL.");
            return false;
        }

        List<Usuario> usuarios = (List<Usuario>) responseDao.getLista();

        if (usuarios == null) {
            System.out.println(" ERROR: La lista de usuarios dentro del Response es NULL.");
            return false;
        }

        System.out.println("====== REVISANDO LISTA DE USUARIOS ======");
        System.out.println("-> Total usuarios cargados en memoria: " + usuarios.size());
        System.out.println("-> Buscando al correo: [" + correo.trim() + "]");

        String correoLimpio = correo.trim();
        String contrasenaLimpia = contrasena.trim();

        for (Usuario u : usuarios) {
            if (u.getCorreo() != null && u.getContrasena() != null) {
                String bdCorreo = u.getCorreo().trim();
                String bdContrasena = u.getContrasena().trim();

                // Imprime cada usuario que Java realmente leyó de la BD
                System.out.println(" Comparando con BD -> Usuario: [" + bdCorreo + "] | Clave: [" + bdContrasena + "]");

                if (bdCorreo.equalsIgnoreCase(correoLimpio) && bdContrasena.equals(contrasenaLimpia)) {
                    System.out.println("  ¡COINCIDENCIA EXACTA ENCONTRADA!");
                    return true;
                }
            } else {
                System.out.println("  Usuario omitido en el bucle por tener datos nulos.");
            }
        }

        System.out.println("FIN: Se recorrió toda la lista y no hubo coincidencia.");
        return false;
    }

    public Usuario obtenerUsuarioPorCredenciales(String correo, String contrasena) {
        // Asumimos que tienes una instancia de tu DAO aquí
        return repo.validarYObtenerUsuario(correo, contrasena);
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
