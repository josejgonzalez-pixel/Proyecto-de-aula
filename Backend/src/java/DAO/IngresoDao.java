/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.Response;
import Model.Ingreso;
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
public class IngresoDao extends BaseDao {

    // INSERTAR
    public Response<Ingreso> insertar(Ingreso i) throws Exception {

        try {

            try (Connection cn = getConnection()) {
                String sql = "INSERT INTO Ingreso " + "(fuente, monto, fecha, descripcion, idUsuario, idCategoria) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                
                PreparedStatement ps = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, i.getFuente());
                ps.setDouble(2, i.getMonto());
                ps.setDate(3, Date.valueOf(i.getFecha()));
                ps.setString(4, i.getDescripcion());
                ps.setInt(5, i.getIdUsuario());
                ps.setInt(6, i.getIdCategoria());
                
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                
                if (rs.next()) {
                    i.setIdTransaccion(rs.getInt(1));
                }
            }

            return new Response<>(true, "Ingreso registrado", i, null);

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR
    public Response<Ingreso> actualizar(Ingreso i) throws Exception {

        try {

            int filas;
            try (Connection cn = getConnection()) {
                String sql = "UPDATE Ingreso SET "
                        + "fuente=?, "
                        + "monto=?, "
                        + "fecha=?, "
                        + "descripcion=?, "
                        + "idUsuario=?, "
                        + "idCategoria=? "
                        + "WHERE idTransaccion=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, i.getFuente());
                ps.setDouble(2, i.getMonto());
                ps.setDate(3, Date.valueOf(i.getFecha()));
                ps.setString(4, i.getDescripcion());
                ps.setInt(5, i.getIdUsuario());
                ps.setInt(6, i.getIdCategoria());
                ps.setInt(7, i.getIdTransaccion());
                filas = ps.executeUpdate();
            }

            if (filas > 0) {

                return new Response<>(true, "Ingreso actualizado", i, null);

            } else {

                return new Response<>(false, "No existe el ingreso", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR
    public Response<Ingreso> eliminar(int id) throws Exception {

        try {

            int filas;
            try (Connection cn = getConnection()) {
                String sql = "DELETE FROM Ingreso WHERE idTransaccion=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                filas = ps.executeUpdate();
            }

            if (filas > 0) {

                return new Response<>(true, "Ingreso eliminado", null, null);

            } else {

                return new Response<>(false, "No existe el ingreso", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // BUSCAR POR ID
    public Response<Ingreso> obtenerPorId(int id) throws Exception {

        try {

            Ingreso i;
            try (Connection cn = getConnection()) {
                String sql = "SELECT * FROM Ingreso WHERE idTransaccion=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                i = null;
                if (rs.next()) {
                    
                    i = new Ingreso(
                            rs.getString("fuente"),
                            rs.getInt("idTransaccion"),
                            rs.getDouble("monto"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getString("descripcion"),
                            rs.getInt("idUsuario"),
                            rs.getInt("idCategoria")
                    );
                }
            }

            if (i != null) {

                return new Response<>(true, "Ingreso encontrado", i, null);

            } else {

                return new Response<>(false, "No existe el ingreso", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // LISTAR TODOS
    public Response<Ingreso> obtenerTodos() throws Exception {

        try {

            List<Ingreso> lista;
            try (Connection cn = getConnection()) {
                String sql = "SELECT * FROM Ingreso";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                lista = new ArrayList<>();
                while (rs.next()) {
                    
                    Ingreso i = new Ingreso(
                            rs.getString("fuente"),
                            rs.getInt("idTransaccion"),
                            rs.getDouble("monto"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getString("descripcion"),
                            rs.getInt("idUsuario"),
                            rs.getInt("idCategoria")
                    );
                    
                    lista.add(i);
                }
            }

            return new Response<>(true, "Lista de ingresos obtenida", null, lista);

        } catch (SQLException e) {

            return new Response<>( false, "Error: " + e.getMessage(), null, null);
        }
    }
}
