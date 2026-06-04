/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.Response;
import Model.Presupuesto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author hp
 */
public class PresupuestoDao extends BaseDao {

    // INSERTAR
    public Response<Presupuesto> insertar(Presupuesto p) throws Exception {

        try {

            try (Connection cn = getConnection()) {
                String sql = "INSERT INTO Presupuesto "
                         + "(nombre, montoInicial, montoActual, fechaCreacion, idUsuario) "
                         + "VALUES (?, ?, ?, ?, ?)";
                
                PreparedStatement ps = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, p.getNombre());
                ps.setDouble(2, p.getMontoInicial());
                ps.setDouble(3, p.getMontoActual());
                ps.setDate(4, Date.valueOf(p.getFechaCreacion()));
                ps.setInt(5, p.getIdUsuario());
                
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                
                if (rs.next()) {
                    p.setIdPresupuesto(rs.getInt(1));
                }
            }

            return new Response<>(true, "Presupuesto registrado", p,null);

        } catch (SQLException e) {
            
            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR
    public Response<Presupuesto> actualizar(Presupuesto p) throws Exception {

        try {

            int filas;
            try (Connection cn = getConnection()) {
                String sql = "UPDATE Presupuesto SET "
                        + "nombre=?, "
                        + "montoInicial=?, "
                        + "montoActual=?, "
                        + "fechaCreacion=?, "
                        + "idUsuario=? "
                        + "WHERE idPresupuesto=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, p.getNombre());
                ps.setDouble(2, p.getMontoInicial());
                ps.setDouble(3, p.getMontoActual());
                ps.setDate(4, Date.valueOf(p.getFechaCreacion()));
                ps.setInt(5, p.getIdUsuario());
                ps.setInt(6, p.getIdPresupuesto());
                filas = ps.executeUpdate();
            }

            if (filas > 0) {

                return new Response<>(true, "Presupuesto actualizado", p, null);

            } else {

                return new Response<>(false, "No existe el presupuesto", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false,"Error: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR
    public Response<Presupuesto> eliminar(int id) throws Exception {

        try {

            int filas;
            try (Connection cn = getConnection()) {
                String sql = "DELETE FROM Presupuesto WHERE idPresupuesto=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                filas = ps.executeUpdate();
            }

            if (filas > 0) {

                return new Response<>(true, "Presupuesto eliminado", null, null);

            } else {

                return new Response<>(false, "No existe el presupuesto", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // BUSCAR POR ID
    public Response<Presupuesto> obtenerPorId(int id) throws Exception {

        try {

            Presupuesto p;
            try (Connection cn = getConnection()) {
                String sql = "SELECT * FROM Presupuesto WHERE idPresupuesto=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                p = null;
                if (rs.next()) {
                    
                    p = new Presupuesto(
                    rs.getInt("idPresupuesto"),
                    rs.getString("nombre"),
                    rs.getDouble("montoInicial"),
                    rs.getDouble("montoActual"),
                    rs.getDate("fechaCreacion").toLocalDate(),
                    rs.getInt("idUsuario")
                    );
                }
            }

            if (p != null) {

                return new Response<>(true, "Presupuesto encontrado",  p,  null);

            } else {

                return new Response<>( false, "No existe el presupuesto", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(),null, null);
        }
    }

    // LISTAR TODOS
    public Response<Presupuesto> obtenerTodos() throws Exception {

        try {

            List<Presupuesto> lista;
            try (Connection cn = getConnection()) {
                String sql = "SELECT * FROM Presupuesto";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                lista = new ArrayList<>();
                while (rs.next()) {
                    
                    Presupuesto p = new Presupuesto(
                    rs.getInt("idPresupuesto"),
                    rs.getString("nombre"),
                    rs.getDouble("montoInicial"),
                    rs.getDouble("montoActual"),
                    rs.getDate("fechaCreacion").toLocalDate(),
                    rs.getInt("idUsuario")
                    );
                    
                    lista.add(p);
                }
            }

            return new Response<>(true, "Lista de presupuestos obtenida", null, lista);

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }
}