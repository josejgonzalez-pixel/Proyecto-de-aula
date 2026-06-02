/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Camil
 */
import Service.TransaccionService;
import Model.Transaccion;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/api/transaccion")
public class TransaccionServlet extends HttpServlet {

    private final TransaccionService service = new TransaccionService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {

            String id = request.getParameter("id");

            if (id != null) {

                Response<Transaccion> r =
                        service.obtenerPorId(Integer.parseInt(id));

                response.getWriter().print(gson.toJson(r));

            } else {

                Response<Transaccion> r =
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

            Transaccion t = new Transaccion(
                    0,
                    Double.parseDouble(request.getParameter("monto")),
                    LocalDate.parse(request.getParameter("fecha")),
                    request.getParameter("descripcion"),
                    Integer.parseInt(request.getParameter("idUsuario")),
                    Integer.parseInt(request.getParameter("idCategoria"))
            ) {};

            Response<Transaccion> r =
                    service.insertar(t);

            response.getWriter().print(
                    gson.toJson(r));

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

            Transaccion t = new Transaccion(
                    Integer.parseInt(request.getParameter("idTransaccion")),
                    Double.parseDouble(request.getParameter("monto")),
                    LocalDate.parse(request.getParameter("fecha")),
                    request.getParameter("descripcion"),
                    Integer.parseInt(request.getParameter("idUsuario")),
                    Integer.parseInt(request.getParameter("idCategoria"))
            ) {};

            Response<Transaccion> r =
                    service.actualizar(t);

            response.getWriter().print(
                    gson.toJson(r));

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

            Response<Transaccion> r =
                    service.eliminar(id);

            response.getWriter().print(
                    gson.toJson(r));

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }
}
