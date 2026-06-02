/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Camil
 */
import Service.ReporteService;
import Model.Reporte;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/api/reporte")
public class ReporteServlet extends HttpServlet {

    private final ReporteService service = new ReporteService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {

            String id = request.getParameter("id");

            if (id != null) {

                Response<Reporte> r =
                        service.obtenerPorId(Integer.parseInt(id));

                response.getWriter().print(gson.toJson(r));

            } else {

                Response<Reporte> r =
                        service.obtenerTodos();

                response.getWriter().print(gson.toJson(r));
            }

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Reporte r = new Reporte(
                    0,
                    request.getParameter("tipoReporte"),
                    LocalDate.parse(request.getParameter("fechaGeneracion")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Reporte> respuesta =
                    service.insertar(r);

            response.getWriter().print(
                    gson.toJson(respuesta));

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Reporte r = new Reporte(
                    Integer.parseInt(request.getParameter("idReporte")),
                    request.getParameter("tipoReporte"),
                    LocalDate.parse(request.getParameter("fechaGeneracion")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Reporte> respuesta =
                    service.actualizar(r);

            response.getWriter().print(
                    gson.toJson(respuesta));

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id =
                    Integer.parseInt(request.getParameter("id"));

            Response<Reporte> r =
                    service.eliminar(id);

            response.getWriter().print(
                    gson.toJson(r));

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }
}
