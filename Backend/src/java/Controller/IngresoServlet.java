/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Ingreso;
import Service.IngresoService;
import Util.Response;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jos13
 */
@WebServlet(name = "IngresoServlet", urlPatterns = {"/ingresos"})
public class IngresoServlet extends HttpServlet{

    private IngresoService ingresoService;
    private Gson gson;

    
    @Override
    public void init() throws ServletException {
        this.ingresoService = new IngresoService();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Cabeceras CORS cruciales
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            String accion = request.getParameter("accion");

            if (accion != null && accion.equals("listar")) {
                // Se invoca  a IngresoService, que a su vez llama a IngresoDao para hacer el "SELECT * FROM Ingreso"
                Response<Ingreso> res = ingresoService.obtenerTodos();
                out.print(this.gson.toJson(res));
            } else {
                out.print(this.gson.toJson(new Response<>(false, "Acción inválida.", null, null)));
            }
        } catch (Exception e) {
            out.print(this.gson.toJson(new Response<>(false, "Error: " + e.getMessage(), null, null)));
        } finally {
            out.flush();
            out.close();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //DEBUG: imprime en consola la salida de netbeans lo que llega
        System.out.println("DEBUG: Recibiendo POST en IngresoServlet");
        System.out.println("Descripcion: " + request.getParameter("descripcion"));
        System.out.println("Monto: " + request.getParameter("monto"));
        
        // 1. Configurar codificación y cabeceras CORS para POST
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // 2. Capturar los parámetros que envía el formulario de Next.js
            String descripcion = request.getParameter("descripcion");
            String montoStr = request.getParameter("monto");
            String fecha = request.getParameter("fecha");
            String idUsuarioStr = request.getParameter("idUsuario");

            // 3. Validación de campos obligatorios
            if (descripcion == null || montoStr == null || fecha == null ||
                descripcion.trim().isEmpty() || montoStr.trim().isEmpty() || fecha.trim().isEmpty()) {
                
                out.print(this.gson.toJson(new Response<>(false, "Todos los campos son obligatorios.", null, null)));
                return;
            }

            // 4. Mapear los datos al objeto del Modelo
            double monto = Double.parseDouble(montoStr.trim());
            
            Ingreso nuevoIngreso = new Ingreso();
            nuevoIngreso.setDescripcion(descripcion.trim());
            nuevoIngreso.setMonto(monto);
            nuevoIngreso.setFecha(LocalDate.parse(fecha.trim()));
            
            if (idUsuarioStr != null) {
                nuevoIngreso.setIdUsuario(Integer.parseInt(idUsuarioStr.trim()));
            }

            // 5. Invocar al servicio para insertar en la Base de Datos
            // (Asegúrate de que tu ingresoService tenga un método llamado registrar o insertar)
            Response<Ingreso> resDao = ingresoService.insertar(nuevoIngreso); 

            // 6. Retornar respuesta estructurada en JSON
            out.print(this.gson.toJson(resDao));

        } catch (NumberFormatException e) {
            out.print(this.gson.toJson(new Response<>(false, "El monto ingresado no es un número válido.", null, null)));
        } catch (Exception e) {
            out.print(this.gson.toJson(new Response<>(false, "Error interno en el servidor: " + e.getMessage(), null, null)));
        } finally {
            out.flush();
            out.close();
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
