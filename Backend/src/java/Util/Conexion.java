/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Camil
 */
public class Conexion {    
    // URL de conexión a tu base de datos en Docker
    private static final String URL = "jdbc:mysql://localhost:3306/finanziapp";
    
    // credenciales de MySQL (las que pusiste en docker)
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // método para obtener la conexión
    public static Connection getConnection() {
        Connection con = null;

        try {
            // carga la conexión
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa a FinanziApp");

        }catch (ClassNotFoundException e) {
            System.out.println("❌ Driver no encontrado en el classpath de Tomcat: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error de SQL / Conexión: " + e.getMessage());
        }

        return con;
    }
}
