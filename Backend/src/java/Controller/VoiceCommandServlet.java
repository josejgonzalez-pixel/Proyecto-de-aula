/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.VoiceCommandRequest;
import Model.VoiceCommandResponse;
import Service.VoiceCommandService;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/voice-command")
public class VoiceCommandServlet extends HttpServlet {

    private VoiceCommandService service;
    private Gson gson;

    @Override
    public void init() {

        service = new VoiceCommandService();
        gson = new Gson();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {

            StringBuilder json =
                    new StringBuilder();

            BufferedReader reader =
                    request.getReader();

            String linea;

            while ((linea = reader.readLine()) != null) {

                json.append(linea);
            }

            VoiceCommandRequest req =
                    gson.fromJson(
                            json.toString(),
                            VoiceCommandRequest.class
                    );

            if (req == null
                    || req.getMensaje() == null
                    || req.getMensaje().trim().isEmpty()) {

                VoiceCommandResponse error =
                        new VoiceCommandResponse(
                                false,
                                "Debe enviar un mensaje."
                        );

                response.getWriter().write(
                        gson.toJson(error)
                );

                return;
            }

            VoiceCommandResponse respuesta =
                    service.procesar(
                            req.getMensaje(),
                            req.getIdUsuario()
                    );

            response.getWriter().write(
                    gson.toJson(respuesta)
            );

        } catch (Exception e) {

            VoiceCommandResponse error =
                    new VoiceCommandResponse(
                            false,
                            "Error: " + e.getMessage()
                    );

            response.getWriter().write(
                    gson.toJson(error)
            );
        }
    }
}