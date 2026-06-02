/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Camil
 */
import Service.GastoService;
import Model.Gasto;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/api/gasto")
public class GastoServlet extends HttpServlet {

    private final GastoService service = new GastoService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {

            String id = request.getParameter("id");

            if (id != null) {

                Response<Gasto> r =
                        service.obtenerPorId(Integer.parseInt(id));

                response.getWriter().print(gson.toJson(r));

            } else {

                Response<Gasto> r =
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

            Gasto g = new Gasto(
                    request.getParameter("tipoPago"),
                    0,
                    Double.parseDouble(request.getParameter("monto")),
                    LocalDate.parse(request.getParameter("fecha")),
                    request.getParameter("descripcion"),
                    Integer.parseInt(request.getParameter("idUsuario")),
                    Integer.parseInt(request.getParameter("idCategoria"))
            );

            Response<Gasto> r =
                    service.insertar(g);

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

            Gasto g = new Gasto(
                    request.getParameter("tipoPago"),
                    Integer.parseInt(request.getParameter("idTransaccion")),
                    Double.parseDouble(request.getParameter("monto")),
                    LocalDate.parse(request.getParameter("fecha")),
                    request.getParameter("descripcion"),
                    Integer.parseInt(request.getParameter("idUsuario")),
                    Integer.parseInt(request.getParameter("idCategoria"))
            );

            Response<Gasto> r =
                    service.actualizar(g);

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

            Response<Gasto> r =
                    service.eliminar(id);

            response.getWriter().print(gson.toJson(r));

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }
}
