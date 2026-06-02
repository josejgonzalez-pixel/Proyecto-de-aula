/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author Camil
 */
import Service.CategoriaService;
import Model.Categoria;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;

@WebServlet("/api/categoria")
public class CategoriaServlet extends HttpServlet {

    private final CategoriaService service = new CategoriaService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        try {

            String id = request.getParameter("id");

            if (id != null) {

                Response<Categoria> r =
                        service.obtenerPorId(Integer.parseInt(id));

                response.getWriter().print(gson.toJson(r));

            } else {

                Response<Categoria> r =
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

            Categoria c = new Categoria(
                    0,
                    request.getParameter("nombreCategoria"),
                    request.getParameter("tipo")
            );

            Response<Categoria> r =
                    service.insertar(c);

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

            Categoria c = new Categoria(
                    Integer.parseInt(request.getParameter("idCategoria")),
                    request.getParameter("nombreCategoria"),
                    request.getParameter("tipo")
            );

            Response<Categoria> r =
                    service.actualizar(c);

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

            Response<Categoria> r =
                    service.eliminar(id);

            response.getWriter().print(gson.toJson(r));

        } catch (Exception e) {

            response.getWriter().print(
                    gson.toJson(e.getMessage()));
        }
    }
}
