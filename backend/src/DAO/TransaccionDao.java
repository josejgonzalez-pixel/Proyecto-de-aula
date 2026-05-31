/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

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

            String sql = "INSERT INTO transaccion "
                    + "(monto, fecha, descripcion, idUsuario, idCategoria) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (Connection cn = getConnection()) {

                PreparedStatement ps = cn.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                );

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

            return new Response<>(false, "Error al insertar transacción: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR TRANSACCION
    public Response<Transaccion> actualizar(Transaccion t) throws Exception {

        try {

            String sql = "UPDATE transaccion SET "
                    + "monto=?, "
                    + "fecha=?, "
                    + "descripcion=?, "
                    + "idUsuario=?, "
                    + "idCategoria=? "
                    + "WHERE idTransaccion=?";

            int filas;
            try (Connection cn = getConnection()) {
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setDouble(1, t.getMonto());
                ps.setDate(2, Date.valueOf(t.getFecha()));
                ps.setString(3, t.getDescripcion());
                ps.setInt(4, t.getIdUsuario());
                ps.setInt(5, t.getIdCategoria());
                ps.setInt(6, t.getIdTransaccion());
                filas = ps.executeUpdate();
            }

            if (filas > 0) {

                return new Response<>(true, "Transacción actualizada correctamente", t, null);

            } else {

                return new Response<>(false, "Transaccion no encontrada", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error al actualizar transaccion: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR TRANSACCION
    public Response<Transaccion> eliminar(int id) throws Exception {

        try {

            String sql = "DELETE FROM transaccion WHERE idTransaccion=?";

            int filas;
            try (Connection cn = getConnection()) {
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                filas = ps.executeUpdate();
            }

            if (filas > 0) {

                return new Response<>(true, "Transaccion eliminada correctamente", null, null);

            } else {

                return new Response<>(false, "Transaccion no encontrada", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error al eliminar transaccion: " + e.getMessage(), null, null);
        }
    }

    // OBTENER TRANSACCION POR ID
    public Response<Transaccion> obtenerPorId(int id) throws Exception {

        try {

            String sql = "SELECT * FROM transaccion WHERE idTransaccion=?";

            Transaccion t = null;

            try (Connection cn = getConnection()) {

                PreparedStatement ps = cn.prepareStatement(sql);

                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

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

                return new Response<>(true, "Transaccion encontrada", t, null);

            } else {

                return new Response<>(false, "Transaccion no existe", null, null);
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

            try (Connection cn = getConnection()) {

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

            return new Response<>(true, "Lista de transacciones obtenida",null, lista);

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }
}
