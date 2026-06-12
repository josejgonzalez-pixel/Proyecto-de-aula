/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Categoria;
import Service.CategoriaService;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
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
@WebServlet(name = "CategoriaServlet", urlPatterns = {"/api/categorias"})
public class CategoriaServlet extends HttpServlet {

    private final CategoriaService service = new CategoriaService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);

        // Retorna toda la lista de categorías
        response.getWriter().print(gson.toJson(service.obtenerTodos()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println("DEBUG: Acción recibida -> " + accion);

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            if ("eliminar".equals(accion)) {
                String idParam = request.getParameter("id");

                if (idParam != null && !idParam.isEmpty()) {
                    int id = Integer.parseInt(idParam);
                    // Capturamos el objeto Response
                    Response<Categoria> res = service.eliminar(id);

                    // Verificamos si fue exitoso usando el método de tu clase Response
                    if (res.isEstado()) {
                        response.getWriter().print(gson.toJson(res));
                    } else {
                        response.getWriter().print(gson.toJson(res));
                    }
                }
            } else {
                // Lógica de registro
                Categoria nuevaCategoria = gson.fromJson(request.getReader(), Categoria.class);
                Response<Categoria> res = service.insertar(nuevaCategoria);
                response.getWriter().print(gson.toJson(res));
            }
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("estado", false);
            errorMap.put("mensaje", "Error: " + e.getMessage());
            response.getWriter().print(gson.toJson(errorMap));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
