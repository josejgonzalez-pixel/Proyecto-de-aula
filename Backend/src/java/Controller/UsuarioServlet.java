/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author jos13
 */
import Model.Usuario;
import Service.UsuarioService;
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

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/api/login"})
public class UsuarioServlet extends HttpServlet {

    private final UsuarioService usuarioService = new UsuarioService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Map<String, Object> respuestaJson = new HashMap<>();

        try {
            String correo = request.getParameter("correo");
            String contrasena = request.getParameter("contrasena");

            boolean loginExitoso = usuarioService.validarLogin(correo, contrasena);
            Usuario usuarioLogueado = usuarioService.obtenerUsuarioPorCredenciales(correo, contrasena);

            if (loginExitoso && usuarioLogueado != null) {
                respuestaJson.put("estado", true);
                respuestaJson.put("idUsuario", usuarioLogueado.getIdUsuario());
                respuestaJson.put("mensaje", "Bienvenido!");
            } else {
                respuestaJson.put("estado", false);
                respuestaJson.put("mensaje", "Correo o contraseña incorrectos.");
            }

            out.print(this.gson.toJson(respuestaJson));

        } catch (Exception e) {
            respuestaJson.put("estado", false);
            respuestaJson.put("mensaje", "Error en el servidor: " + e.getMessage());
            out.print(this.gson.toJson(respuestaJson));
        } finally {
            out.flush();
            out.close();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}