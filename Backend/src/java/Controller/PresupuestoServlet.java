package Controller;

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
import javax.servlet.http.HttpServlet; // ESTO ES OBLIGATORIO
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "presupuestoServlet", urlPatterns = {"/presupuesto"})
public class PresupuestoServlet extends HttpServlet { // Extiende de HttpServlet

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)); // Formato "yyyy-MM-dd"
                }
            })
            .create();
    private final PresupuestoDao presupuestoDao = new PresupuestoDao();

    // 1. MÉTODO PARA TRAER DATOS (El que usará tu useEffect)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        configurarHeaders(response);
        PrintWriter out = response.getWriter();
        try {

            Response<Presupuesto> resultado = presupuestoDao.obtenerTodos();

            out.print(this.gson.toJson(resultado));
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("estado", false);
            errorMap.put("mensaje", "Error en el servidor: " + e.getMessage());
            out.print(this.gson.toJson(errorMap));
        } finally {
            out.flush();
        }
    }

    // 2. MÉTODO AUXILIAR PARA CORS
    private void configurarHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Accion recibida: "+ request.getParameter("accion"));
        configurarHeaders(response);

        System.out.println("DEBUG: Recibiendo POST...");
        System.out.println("Monto Inicial: " + request.getParameter("montoInicial"));
        System.out.println("Fecha: " + request.getParameter("fechaCreacion"));

        PrintWriter out = response.getWriter();
        String accion = request.getParameter("accion");

        try {
            if ("eliminar".equals(accion)) {
            // SOLO LEE EL ID PARA ELIMINAR
            int id = Integer.parseInt(request.getParameter("idPresupuesto"));
            Response<Presupuesto> res = presupuestoDao.eliminar(id);
            out.print(this.gson.toJson(res));
        } 
        else {
            // AQUÍ SÍ LEE LOS OTROS PARÁMETROS PORQUE ES "insertar" O "actualizar"
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
                Presupuesto p = new Presupuesto(0, nombre, montoInicial, montoActual, LocalDate.parse(fecha), idUsuario);
                out.print(this.gson.toJson(presupuestoDao.insertar(p)));
            }
        }

        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("estado", false);
            errorMap.put("mensaje", "Error: " + e.getMessage());
            out.print(this.gson.toJson(errorMap));
        } finally {
            out.flush();
        }
    }
}
