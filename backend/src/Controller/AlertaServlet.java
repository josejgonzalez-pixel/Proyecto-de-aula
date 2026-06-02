/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Camil
 */
import Service.AlertaService;
import Model.Alerta;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/api/alerta")
public class AlertaServlet extends HttpServlet {

    private final AlertaService service = new AlertaService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {

            String id = request.getParameter("id");

            if (id != null) {

                Response<Alerta> r =
                        service.obtenerPorId(Integer.parseInt(id));

                response.getWriter().print(gson.toJson(r));

            } else {

                Response<Alerta> r =
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

            Alerta a = new Alerta(
                    0,
                    request.getParameter("mensaje"),
                    LocalDate.parse(request.getParameter("fechaAlerta")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Alerta> r =
                    service.insertar(a);

            response.getWriter().print(gson.toJson(r));

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

            Alerta a = new Alerta(
                    Integer.parseInt(request.getParameter("idAlerta")),
                    request.getParameter("mensaje"),
                    LocalDate.parse(request.getParameter("fechaAlerta")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Alerta> r =
                    service.actualizar(a);

            response.getWriter().print(gson.toJson(r));

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

            Response<Alerta> r =
                    service.eliminar(id);

            response.getWriter().print(gson.toJson(r));

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }
}
