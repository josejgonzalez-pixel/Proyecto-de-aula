/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jos13
 */
@WebServlet (name = "presupuestoServlet", urlPatterns = {"/api/presupuesto"} )
public class PresupuestoServlet {
    private final Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Map<String, Object> respuestaJson = new HashMap<>();

        try {
            double montoInicial = Double.parseDouble(request.getParameter("montoInicial"));
            int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

            // Lógica de guardado del presupuesto asignado
            boolean exito = true; 

            if (exito) {
                respuestaJson.put("estado", true);
                respuestaJson.put("mensaje", "Presupuesto establecido con éxito.");
            } else {
                respuestaJson.put("estado", false);
                respuestaJson.put("mensaje", "Error al configurar el tope presupuestal.");
            }
            out.print(this.gson.toJson(respuestaJson));

        } catch (Exception e) {
            respuestaJson.put("estado", false);
            respuestaJson.put("mensaje", "Error: " + e.getMessage());
            out.print(this.gson.toJson(respuestaJson));
        } finally {
            out.flush();
        }
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
