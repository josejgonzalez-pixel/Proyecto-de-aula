/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Camil
 */
import Service.PresupuestoService;
import Model.Presupuesto;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/api/presupuesto")
public class PresupuestoServlet extends HttpServlet {

    private final PresupuestoService service = new PresupuestoService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {

            String id = request.getParameter("id");

            if (id != null) {

                Response<Presupuesto> r =
                        service.obtenerPorId(Integer.parseInt(id));

                response.getWriter().print(gson.toJson(r));

            } else {

                Response<Presupuesto> r =
                        service.obtenerTodos();

                response.getWriter().print(gson.toJson(r));
            }

        } catch (Exception e) {
            response.getWriter().print(gson.toJson(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Presupuesto p = new Presupuesto(
                    0,
                    Double.parseDouble(request.getParameter("montoInicial")),
                    Double.parseDouble(request.getParameter("montoActual")),
                    LocalDate.parse(request.getParameter("fechaCreacion")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Presupuesto> r = service.insertar(p);

            response.getWriter().print(gson.toJson(r));

        } catch (Exception e) {
            response.getWriter().print(gson.toJson(e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Presupuesto p = new Presupuesto(
                    Integer.parseInt(request.getParameter("idPresupuesto")),
                    Double.parseDouble(request.getParameter("montoInicial")),
                    Double.parseDouble(request.getParameter("montoActual")),
                    LocalDate.parse(request.getParameter("fechaCreacion")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Presupuesto> r = service.actualizar(p);

            response.getWriter().print(gson.toJson(r));

        } catch (Exception e) {
            response.getWriter().print(gson.toJson(e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            Response<Presupuesto> r = service.eliminar(id);

            response.getWriter().print(gson.toJson(r));

        } catch (Exception e) {
            response.getWriter().print(gson.toJson(e.getMessage()));
        }
    }
}
