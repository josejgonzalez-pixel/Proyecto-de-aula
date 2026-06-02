/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.File;

/**
 *
 * @author jos13
 */
public class Principal {
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080); // Aquí abres el puerto real para Next.js
    
    // Aquí le dices a Tomcat dónde encontrar tu UsuarioServlet
    tomcat.addWebapp("/", new File("src").getAbsolutePath()); 
    
    tomcat.start();
    System.out.println("Servidor HTTP Tomcat escuchando en el puerto 8080...");
    tomcat.getServer().await();
    }
}
