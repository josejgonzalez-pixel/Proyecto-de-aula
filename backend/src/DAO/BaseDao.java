/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.Conexion;
import java.sql.Connection;

/**
 *
 * @author Camil
 */
public class BaseDao {
      protected Connection getConnection() throws Exception {
        return Conexion.getConnection(); 
    }
}
