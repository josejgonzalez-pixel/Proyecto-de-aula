/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Usuario;
import Service.UsuarioService;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jos13
 */
@WebServlet(name ="RegistrarServlet", urlPatterns = {"/api/registro"})
public class RegistrarServlet extends HttpServlet{
    private UsuarioService usuarioService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        this.usuarioService = new UsuarioService();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configurar codificación de entrada para evitar problemas con eñes o acentos
        request.setCharacterEncoding("UTF-8");

        // Configuración de cabeceras CORS para que Next.js pueda comunicarse sin bloqueos
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // 2. CAPTURAMOS LOS PARÁMETROS QUE ENVIARÁ NEXT.JS
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String contrasena = request.getParameter("contrasena");

            // Control en la consola de Tomcat para monitorear el intento de registro
            System.out.println("=========================================");
            System.out.println("LOG DESDE TOMCAT - NUEVO REGISTRO");
            System.out.println("Nombre: [" + nombre + "]");
            System.out.println("Correo: [" + correo + "]");
            System.out.println("=========================================");

            // Validamos que los campos no vengan vacíos
            if (nombre == null || correo == null || contrasena == null || 
                nombre.trim().isEmpty() || correo.trim().isEmpty() || contrasena.trim().isEmpty()) {
                
                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("estado", false);
                respuesta.put("mensaje", "Todos los campos son obligatorios.");
                out.print(this.gson.toJson(respuesta));
                return;
            }

            // 3. CREAMOS EL OBJETO USUARIO Y LLAMAMOS AL SERVICIO CORREGIDO
            Usuario  nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre.trim());
            nuevoUsuario.setCorreo(correo.trim());
            nuevoUsuario.setContrasena(contrasena.trim());

            Response<Usuario> resDao = usuarioService.RegistrarNuevoUsuario(nuevoUsuario);

            // 4. RETORNAMOS LA RESPUESTA EN FORMATO JSON
            Map<String, Object> respuestaJson = new HashMap<>();
            if (resDao.isEstado()) { // Usa el método correcto de tu Response (puede ser isEstado() o getEstado())
                respuestaJson.put("estado", true);
                respuestaJson.put("mensaje", "¡Usuario registrado con éxito!");
            } else {
                respuestaJson.put("estado", false);
                respuestaJson.put("mensaje", resDao.getMensaje());
            }

            out.print(this.gson.toJson(respuestaJson));

        } catch (Exception e) {
            Map<String, Object> errorJson = new HashMap<>();
            errorJson.put("estado", false);
            errorJson.put("mensaje", "Error interno en el servidor: " + e.getMessage());
            out.print(this.gson.toJson(errorJson));
        } finally {
            out.flush();
            out.close();
        }
    }

    // SOPORTE PARA PETICIONES OPTIONS (CORS)
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
