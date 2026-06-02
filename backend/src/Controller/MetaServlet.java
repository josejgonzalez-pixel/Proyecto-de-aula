/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Camil
 */
import Service.MetaService;
import Model.Meta;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/api/meta")
public class MetaServlet extends HttpServlet {

    private final MetaService service = new MetaService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {

            String id = request.getParameter("id");

            if (id != null) {

                Response<Meta> r =
                        service.obtenerPorId(Integer.parseInt(id));

                response.getWriter().print(gson.toJson(r));

            } else {

                Response<Meta> r =
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

            Meta m = new Meta(
                    0,
                    request.getParameter("nombreMeta"),
                    Double.parseDouble(request.getParameter("montoMeta")),
                    Double.parseDouble(request.getParameter("montoActual")),
                    LocalDate.parse(request.getParameter("fechaLimite")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Meta> r =
                    service.insertar(m);

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

            Meta m = new Meta(
                    Integer.parseInt(request.getParameter("idMeta")),
                    request.getParameter("nombreMeta"),
                    Double.parseDouble(request.getParameter("montoMeta")),
                    Double.parseDouble(request.getParameter("montoActual")),
                    LocalDate.parse(request.getParameter("fechaLimite")),
                    Integer.parseInt(request.getParameter("idUsuario"))
            );

            Response<Meta> r =
                    service.actualizar(m);

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

            Response<Meta> r =
                    service.eliminar(id);

            response.getWriter().print(gson.toJson(r));

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }
}
