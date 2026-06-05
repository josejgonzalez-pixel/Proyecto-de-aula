/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Service.ChatbotService;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author jos13
 */
@WebServlet("/chatbot-audio")
@MultipartConfig
public class ChatbotAudioServlet extends HttpServlet {

    private ChatbotService chatbotService = new ChatbotService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setContentType("application/json");

    try {
        Part filePart = request.getPart("file");
        if (filePart == null) throw new Exception("No se recibió el archivo");
        
        InputStream fileContent = filePart.getInputStream();
        byte[] audioBytes = fileContent.readAllBytes();

        // Llamada a tu servicio
        String respuesta = chatbotService.procesarAudio(audioBytes);

        // RESPUESTA EXITOSA
        response.getWriter().write("{\"respuesta\": \"" + respuesta + "\"}");

    } catch (Exception e) {
        e.printStackTrace(); // <--- MIRA TU CONSOLA DE NETBEANS AQUÍ
        // RESPUESTA DE ERROR (para que el frontend no rompa)
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("{\"respuesta\": \"Error en el servidor: " + e.getMessage() + "\"}");
    }
}
}
