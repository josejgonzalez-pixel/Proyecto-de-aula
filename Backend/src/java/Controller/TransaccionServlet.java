/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Service.TransaccionService;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jos13
 */
@WebServlet(name = "TransaccionServlet", urlPatterns = {"/api/transacciones"})
public class TransaccionServlet extends HttpServlet {

    private final TransaccionService service = new TransaccionService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Cabeceras de control de acceso unificadas para Next.js (Puerto 3000)
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // Tu lógica de captura de parámetros aquí (request.getParameter)
            // Lógica de interconexión con las capas de Álvaro y Camila
        } catch (Exception e) {
            // Manejo de errores estructurado en JSON
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setContentType("application/json;charset=UTF-8");
        String accion = request.getParameter("accion");

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setContentType("application/json;charset=UTF-8");

        try {
            if ("resumen".equals(accion)) {
                response.getWriter().print(gson.toJson(service.obtenerResumen(1)));
            } else if ("gastosPorCategoria".equals(accion)) {
                response.getWriter().print(gson.toJson(service.obtenerGastosPorCategoria(1)));
            } else {
                response.getWriter().print(gson.toJson(service.obtenerTodos(1)));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
