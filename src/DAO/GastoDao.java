/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Gasto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class GastoDao extends BaseDao {

    // INSERTAR 
    public Response<Gasto> insertar(Gasto g) {

        try {

            Connection cn = getConnection();

            String sql = "INSERT INTO gasto " + "(tipoPago, monto, fecha, descripcion, idUsuario, idCategoria) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, g.getTipoPago());
            ps.setDouble(2, g.getMonto());
            ps.setDate(3, Date.valueOf(g.getFecha()));
            ps.setString(4, g.getDescripcion());
            ps.setInt(5, g.getIdUsuario());
            ps.setInt(6, g.getIdCategoria());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                g.setIdTransaccion(rs.getInt(1));
            }

            cn.close();

            return new Response<>(true, "Gasto registrado", g, null);

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR
    public Response<Gasto> actualizar(Gasto g) {

        try {

            Connection cn = getConnection();

            String sql = "UPDATE gasto SET "
                    + "tipoPago=?, "
                    + "monto=?, "
                    + "fecha=?, "
                    + "descripcion=?, "
                    + "idUsuario=?, "
                    + "idCategoria=? "
                    + "WHERE idTransaccion=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, g.getTipoPago());
            ps.setDouble(2, g.getMonto());
            ps.setDate(3, Date.valueOf(g.getFecha()));
            ps.setString(4, g.getDescripcion());
            ps.setInt(5, g.getIdUsuario());
            ps.setInt(6, g.getIdCategoria());
            ps.setInt(7, g.getIdTransaccion());

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(true, "Gasto actualizado", g, null);

            } else {

                return new Response<>(false, "No existe el gasto", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR
    public Response<Gasto> eliminar(int id) {

        try {

            Connection cn = getConnection();

            String sql = "DELETE FROM gasto WHERE idTransaccion=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(true, "Gasto eliminado", null, null);

            } else {

                return new Response<>(false, "No existe el gasto", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // BUSCAR POR ID
    public Response<Gasto> obtenerPorId(int id) {

        try {

            Connection cn = getConnection();

            String sql = "SELECT * FROM gasto WHERE idTransaccion=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Gasto g = null;

            if (rs.next()) {

                g = new Gasto(
                        rs.getString("tipoPago"),
                        rs.getInt("idTransaccion"),
                        rs.getDouble("monto"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("descripcion"),
                        rs.getInt("idUsuario"),
                        rs.getInt("idCategoria")
                );
            }

            cn.close();

            if (g != null) {

                return new Response<>(true, "Gasto encontrado", g, null);

            } else {

                return new Response<>(false, "No existe el gasto", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // LISTAR TODOS
    public Response<Gasto> obtenerTodos() {

        try {

            Connection cn = getConnection();

            String sql = "SELECT * FROM gasto";

            Statement st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            List<Gasto> lista = new ArrayList<>();

            while (rs.next()) {

                Gasto g = new Gasto(
                        rs.getString("tipoPago"),
                        rs.getInt("idTransaccion"),
                        rs.getDouble("monto"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("descripcion"),
                        rs.getInt("idUsuario"),
                        rs.getInt("idCategoria")
                );

                lista.add(g);
            }

            cn.close();

            return new Response<>(true, "Lista de gastos obtenida", null, lista);

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }
}
