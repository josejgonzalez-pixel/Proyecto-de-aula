/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Service.ChatbotService;
import Util.ChatResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ChatbotServlet", urlPatterns = {"/chatbot"})
public class ChatbotServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final ChatbotService chatbotService = new ChatbotService();

    private void configurarHeaders(HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doOptions(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        configurarHeaders(response);

        try {

            StringBuilder json = new StringBuilder();

            BufferedReader reader = request.getReader();

            String linea;

            while ((linea = reader.readLine()) != null) {
                json.append(linea);
            }

            ChatRequest chatRequest =
                    gson.fromJson(json.toString(), ChatRequest.class);

            String respuesta =
                    chatbotService.procesarMensaje(
                            chatRequest.getMensaje()
                    );

            ChatResponse chatResponse =
                    new ChatResponse(respuesta);

            response.getWriter().print(
                    gson.toJson(chatResponse)
            );

        } catch (Exception e) {

            ChatResponse error =
                    new ChatResponse(
                            "Error: " + e.getMessage()
                    );

            response.getWriter().print(
                    gson.toJson(error)
            );
        }
    }
}