/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import com.sun.jdi.connect.spi.Connection;
import Model.Transaccion;
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
public class TransaccionDao extends BaseDao {

    // INSERTAR TRANSACCION
    public Response<Transaccion> insertar(Transaccion t) throws Exception {

        try {

            String sql = "INSERT INTO transaccion " + "(monto, fecha, descripcion, idUsuario, idCategoria) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (java.sql.Connection cn = getConnection()) {
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                ps.setDouble(1, t.getMonto());
                ps.setDate(2, Date.valueOf(t.getFecha()));
                ps.setString(3, t.getDescripcion());
                ps.setInt(4, t.getIdUsuario());
                ps.setInt(5, t.getIdCategoria());
                
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    t.setIdTransaccion(rs.getInt(1));
                }
            }

            return new Response<>(true, "Transacción insertada correctamente", t, null);

        } catch (SQLException e) {

            return new Response<>(false, "Error al insertar transacción: " + e.getMessage(),null, null);
        }
    }

    // ACTUALIZAR TRANSACCION
    public Response<Transaccion> actualizar(Transaccion t) {

        try {

            String sql = "UPDATE transaccion SET "
                    + "monto=?, "
                    + "fecha=?, "
                    + "descripcion=?, "
                    + "idUsuario=?, "
                    + "idCategoria=? "
                    + "WHERE idTransaccion=?";

            Connection cn = (Connection) getConnection();

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setDouble(1, t.getMonto());
            ps.setDate(2, Date.valueOf(t.getFecha()));
            ps.setString(3, t.getDescripcion());
            ps.setInt(4, t.getIdUsuario());
            ps.setInt(5, t.getIdCategoria());
            ps.setInt(6, t.getIdTransaccion());

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(true, "Transacción actualizada correctamente", t, null);

            } else {

                return new Response<>(false, "Transacción no encontrada", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error al actualizar transacción: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR TRANSACCION
    public Response<Transaccion> eliminar(int id) {

        try {

            String sql = "DELETE FROM transaccion WHERE idTransaccion=?";

            Connection cn = (Connection) getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(true, "Transacción eliminada correctamente", null, null);

            } else {

                return new Response<>(false, "Transacción no encontrada", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error al eliminar transacción: " + e.getMessage(), null, null);
        }
    }

    // OBTENER TRANSACCION POR ID
    public Response<Transaccion> obtenerPorId(int id) throws Exception {

        try {

            String sql = "SELECT * FROM transaccion WHERE idTransaccion=?";

            Transaccion t;
            try (java.sql.Connection cn = getConnection()) {
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                t = null;
                if (rs.next()) {
                    
                    t = new Transaccion(
                            rs.getInt("idTransaccion"),
                            rs.getDouble("monto"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getString("descripcion"),
                            rs.getInt("idUsuario"),
                            rs.getInt("idCategoria")
                    ) {
                    };
                }
            }

            if (t != null) {

                return new Response<>(true, "Transacción encontrada", t, null);

            } else {

                return new Response<>(false,"Transacción no existe", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // OBTENER TODAS LAS TRANSACCIONES
    public Response<Transaccion> obtenerTodos() throws Exception {

        try {

            List<Transaccion> lista = new ArrayList<>();

            String sql = "SELECT * FROM transaccion";

            try (var cn = getConnection()) {
                Statement st = cn.createStatement();
                
                ResultSet rs = st.executeQuery(sql);
                
                while (rs.next()) {
                    
                    Transaccion t = new Transaccion(
                            rs.getInt("idTransaccion"),
                            rs.getDouble("monto"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getString("descripcion"),
                            rs.getInt("idUsuario"),
                            rs.getInt("idCategoria")
                    ) {
                    };
                    
                    lista.add(t);
                }
            }

            return new Response<>(true, "Lista de transacciones obtenida", null, lista);

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }
}
