/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Reporte;
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
public class ReporteDao extends BaseDao {

    // INSERTAR
    public Response<Reporte> insertar(Reporte r) {

        try {

            Connection cn = getConnection();

            String sql = "INSERT INTO reporte " + "(tipoReporte, fechaGeneracion, idUsuario) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, r.getTipoReporte());
            ps.setDate(2, Date.valueOf(r.getFechaGeneracion()));
            ps.setInt(3, r.getIdUsuario());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                r.setIdReporte(rs.getInt(1));
            }

            cn.close();

            return new Response<>(
                    true,
                    "Reporte registrado",
                    r,
                    null
            );

        } catch (Exception e) {

            return new Response<>(
                    false,
                    "Error: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    // ACTUALIZAR
    public Response<Reporte> actualizar(Reporte r) {

        try {

            Connection cn = getConnection();

            String sql = "UPDATE reporte SET "
                    + "tipoReporte=?, "
                    + "fechaGeneracion=?, "
                    + "idUsuario=? "
                    + "WHERE idReporte=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, r.getTipoReporte());
            ps.setDate(2, Date.valueOf(r.getFechaGeneracion()));
            ps.setInt(3, r.getIdUsuario());
            ps.setInt(4, r.getIdReporte());

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(
                        true,
                        "Reporte actualizado", r,
                        null
                );

            } else {

                return new Response<>(
                        false,
                        "No existe el reporte",
                        null,
                        null
                );
            }

        } catch (Exception e) {

            return new Response<>(
                    false,
                    "Error: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    // ELIMINAR
    public Response<Reporte> eliminar(int id) {

        try {

            Connection cn = getConnection();

            String sql = "DELETE FROM reporte WHERE idReporte=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(
                        true,
                        "Reporte eliminado",
                        null,
                        null
                );

            } else {

                return new Response<>(
                        false,
                        "No existe el reporte",
                        null,
                        null
                );
            }

        } catch (Exception e) {

            return new Response<>(
                    false,
                    "Error: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    // BUSCAR POR ID
    public Response<Reporte> obtenerPorId(int id) {

        try {

            Connection cn = getConnection();

            String sql = "SELECT * FROM reporte WHERE idReporte=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Reporte r = null;

            if (rs.next()) {

                r = new Reporte(
                        rs.getInt("idReporte"),
                        rs.getString("tipoReporte"),
                        rs.getDate("fechaGeneracion").toLocalDate(),
                        rs.getInt("idUsuario")
                );
            }

            cn.close();

            if (r != null) {

                return new Response<>(
                        true,
                        "Reporte encontrado",
                        r,
                        null
                );

            } else {

                return new Response<>(
                        false,
                        "No existe el reporte",
                        null,
                        null
                );
            }

        } catch (Exception e) {

            return new Response<>(
                    false,
                    "Error: " + e.getMessage(),
                    null,
                    null
            );
        }
    }

    // LISTAR TODOS
    public Response<Reporte> obtenerTodos() {

        try {

            Connection cn = getConnection();
            String sql = "SELECT * FROM reporte";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            List<Reporte> lista = new ArrayList<>();

            while (rs.next()) {

                Reporte r = new Reporte(
                        rs.getInt("idReporte"),
                        rs.getString("tipoReporte"),
                        rs.getDate("fechaGeneracion").toLocalDate(),
                        rs.getInt("idUsuario")
                );

                lista.add(r);
            }

            cn.close();

            return new Response<>(
                    true,
                    "Lista de reportes obtenida",
                    null,
                    lista
            );

        } catch (Exception e) {

            return new Response<>(
                    false,
                    "Error: " + e.getMessage(),
                    null,
                    null
            );
        }
    }
}
