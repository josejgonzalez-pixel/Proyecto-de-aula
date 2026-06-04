/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author jos13
 */
import Service.MetaService;
import Model.Meta;
import Util.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/meta")
public class MetaServlet extends HttpServlet {

    private final MetaService service = new MetaService();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> 
                new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .create();

    private void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // Método auxiliar para configurar los encabezados CORS en cada respuesta

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        setCORSHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        setCORSHeaders(response);

        try {
            String id = request.getParameter("id");
            if (id != null) {
                Response<Meta> r = service.obtenerPorId(Integer.parseInt(id));
                response.getWriter().print(gson.toJson(r));
            } else {
                Response<Meta> r = service.obtenerTodos();
                response.getWriter().print(gson.toJson(r));
            }
        } catch (Exception e) {
            response.getWriter().print(gson.toJson(new Response<>(false, e.getMessage(), null, null)));
        }
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    setCORSHeaders(response);
    String accion = request.getParameter("accion");
    PrintWriter out = response.getWriter();
    
    try {
        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("idMeta"));
            out.print(gson.toJson(service.eliminar(id)));
        } else if ("insertar".equals(accion) || "actualizar".equals(accion)) {
            Meta m = new Meta(
                "actualizar".equals(accion) ? Integer.parseInt(request.getParameter("idMeta")) : 0,
                request.getParameter("nombreMeta"),
                Double.parseDouble(request.getParameter("montoMeta")),
                Double.parseDouble(request.getParameter("montoActual")),
                LocalDate.parse(request.getParameter("fechaLimite")),
                Integer.parseInt(request.getParameter("idUsuario"))
            );
            out.print(gson.toJson("insertar".equals(accion) ? service.insertar(m) : service.actualizar(m)));
        } else {
            // Acción por defecto: listar
            out.print(gson.toJson(service.obtenerTodos()));
        }
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        out.print(gson.toJson(new Response<>(false, e.getMessage(), null, null)));
    } finally {
        out.flush();
    }
}

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORSHeaders(response);

        try {
            Meta m = new Meta(
                    Integer.parseInt(request.getParameter("idMeta")),
                    request.getParameter("nombreMeta"),
                    Double.parseDouble(request.getParameter("montoMeta")),
                    Double.parseDouble(request.getParameter("montoActual")),
                    LocalDate.parse(request.getParameter("fechaLimite")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Meta> r = service.actualizar(m);
            response.getWriter().print(gson.toJson(r));
        } catch (Exception e) {
            response.getWriter().print(gson.toJson(new Response<>(false, "Error: " + e.getMessage(), null, null)));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setCORSHeaders(response);

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Response<Meta> r = service.eliminar(id);
            response.getWriter().print(gson.toJson(r));
        } catch (Exception e) {
            response.getWriter().print(gson.toJson(new Response<>(false, "Error: " + e.getMessage(), null, null)));
        }
    }
}