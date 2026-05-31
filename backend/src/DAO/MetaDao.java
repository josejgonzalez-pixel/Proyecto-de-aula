/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.Response;
import Model.Meta;
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
public class MetaDao extends BaseDao {

    // INSERTAR
    public Response<Meta> insertar(Meta m) throws Exception {

        try {

            try (Connection cn = getConnection()) {
                String sql = "INSERT INTO meta " + "(nombreMeta, montoMeta, montoActual, fechaLimite, idUsuario) "
                        + "VALUES (?, ?, ?, ?, ?)";
                
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                
                ps.setString(1, m.getNombreMeta());
                ps.setDouble(2, m.getMontoMeta());
                ps.setDouble(3, m.getMontoActual());
                ps.setDate(4, Date.valueOf(m.getFechaLimite()));
                ps.setInt(5, m.getIdUsuario());
                
                ps.executeUpdate();
                
                ResultSet rs = ps.getGeneratedKeys();
                
                if (rs.next()) {
                    m.setIdMeta(rs.getInt(1));
                }
            }

            return new Response<>(true, "Meta registrada", m, null);

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR
    public Response<Meta> actualizar(Meta m) throws Exception {

        try {

            int filas;
            try (Connection cn = getConnection()) {
                String sql = "UPDATE meta SET "
                        + "nombreMeta=?, "
                        + "montoMeta=?, "
                        + "montoActual=?, "
                        + "fechaLimite=?, "
                        + "idUsuario=? "
                        + "WHERE idMeta=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setString(1, m.getNombreMeta());
                ps.setDouble(2, m.getMontoMeta());
                ps.setDouble(3, m.getMontoActual());
                ps.setDate(4, Date.valueOf(m.getFechaLimite()));
                ps.setInt(5, m.getIdUsuario());
                ps.setInt(6, m.getIdMeta());
                filas = ps.executeUpdate();
            }

            if (filas > 0) {

                return new Response<>(true,"Meta actualizada", m, null);

            } else {

                return new Response<>(false,"No existe la meta",null, null
                );
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR
    public Response<Meta> eliminar(int id) throws Exception {

        try {

            int filas;
            try (Connection cn = getConnection()) {
                String sql = "DELETE FROM meta WHERE idMeta=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                filas = ps.executeUpdate();
            }

            if (filas > 0) {

                return new Response<>(
                        true,
                        "Meta eliminada",
                        null,
                        null
                );

            } else {

                return new Response<>(false, "No existe la meta", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // BUSCAR POR ID
    public Response<Meta> obtenerPorId(int id) throws Exception {

        try {

            Meta m;
            try (Connection cn = getConnection()) {
                String sql = "SELECT * FROM meta WHERE idMeta=?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                m = null;
                if (rs.next()) {
                    
                    m = new Meta(
                            rs.getInt("idMeta"),
                            rs.getString("nombreMeta"),
                            rs.getDouble("montoMeta"),
                            rs.getDouble("montoActual"),
                            rs.getDate("fechaLimite").toLocalDate(),
                            rs.getInt("idUsuario")
                    );
                }
            }

            if (m != null) {

                return new Response<>(true,"Meta encontrada", m, null);

            } else {

                return new Response<>(false, "No existe la meta", null, null);
            }

        } catch (SQLException e) {

            return new Response<>(false,"Error: " + e.getMessage(), null, null);
        }
    }

    // LISTAR TODAS
    public Response<Meta> obtenerTodos() throws Exception {

        try {

            List<Meta> lista;
            try (Connection cn = getConnection()) {
                String sql = "SELECT * FROM meta";
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                lista = new ArrayList<>();
                while (rs.next()) {
                    
                    Meta m = new Meta(
                            rs.getInt("idMeta"),
                            rs.getString("nombreMeta"),
                            rs.getDouble("montoMeta"),
                            rs.getDouble("montoActual"),
                            rs.getDate("fechaLimite").toLocalDate(),
                            rs.getInt("idUsuario")
                    );
                    
                    lista.add(m);
                }
            }

            return new Response<>(true, "Lista de metas obtenida", null, lista);

        } catch (SQLException e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }
}