package Controller;

import Model.ChatRequest;
import Service.ChatbotService;
import DAO.PresupuestoDao;
import Model.Presupuesto;
import Util.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "presupuestoServlet", urlPatterns = {"/presupuesto"})
public class PresupuestoServlet extends HttpServlet {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> 
                new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .create();
    
    private final PresupuestoDao presupuestoDao = new PresupuestoDao();

    private void configurarHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configurarHeaders(response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configurarHeaders(response);
        try {
            response.getWriter().print(this.gson.toJson(presupuestoDao.obtenerTodos()));
        } catch (Exception e) {
            response.getWriter().print("{\"estado\": false, \"mensaje\": \"Error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configurarHeaders(response);
        PrintWriter out = response.getWriter();
        String accion = request.getParameter("accion");

        try {
            if ("eliminar".equals(accion)) {
                // Lógica para eliminar
                int id = Integer.parseInt(request.getParameter("idPresupuesto"));
                Response<Presupuesto> res = presupuestoDao.eliminar(id);
                out.print(this.gson.toJson(res));
            } else {
                // Lógica para insertar o actualizar
                String nombre = request.getParameter("nombre");
                double montoInicial = Double.parseDouble(request.getParameter("montoInicial"));
                double montoActual = Double.parseDouble(request.getParameter("montoActual"));
                String fecha = request.getParameter("fechaCreacion");
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

                if ("actualizar".equals(accion)) {
                    int id = Integer.parseInt(request.getParameter("idPresupuesto"));
                    Presupuesto p = new Presupuesto(id, nombre, montoInicial, montoActual, LocalDate.parse(fecha), idUsuario);
                    out.print(this.gson.toJson(presupuestoDao.actualizar(p)));
                } else {
                    // Acción por defecto: insertar
                    Presupuesto p = new Presupuesto(0, nombre, montoInicial, montoActual, LocalDate.parse(fecha), idUsuario);
                    out.print(this.gson.toJson(presupuestoDao.insertar(p)));
                }
            }
        } catch (Exception e) {
            // Manejo de errores
            out.print("{\"estado\": false, \"mensaje\": \"Error: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}