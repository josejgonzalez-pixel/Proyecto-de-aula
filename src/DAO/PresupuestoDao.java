/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Presupuesto;
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
public class PresupuestoDao extends BaseDao {

    // INSERTAR
    public Response<Presupuesto> insertar(Presupuesto p) {

        try {

            Connection cn = getConnection();

            String sql = "INSERT INTO presupuesto " + "(montoInicial, montoActual, fechaCreacion, idUsuario) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement ps = cn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            ps.setDouble(1, p.getMontoInicial());
            ps.setDouble(2, p.getMontoActual());
            ps.setDate(3, Date.valueOf(p.getFechaCreacion()));
            ps.setInt(4, p.getIdUsuario());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                p.setIdPresupuesto(rs.getInt(1));
            }

            cn.close();

            return new Response<>(true, "Presupuesto registrado", p,null);

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR
    public Response<Presupuesto> actualizar(Presupuesto p) {

        try {

            Connection cn = getConnection();

            String sql = "UPDATE presupuesto SET "
                    + "montoInicial=?, "
                    + "montoActual=?, "
                    + "fechaCreacion=?, "
                    + "idUsuario=? "
                    + "WHERE idPresupuesto=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setDouble(1, p.getMontoInicial());
            ps.setDouble(2, p.getMontoActual());
            ps.setDate(3, Date.valueOf(p.getFechaCreacion()));
            ps.setInt(4, p.getIdUsuario());
            ps.setInt(5, p.getIdPresupuesto());

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(true, "Presupuesto actualizado", p, null);

            } else {

                return new Response<>(false, "No existe el presupuesto", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false,"Error: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR
    public Response<Presupuesto> eliminar(int id) {

        try {

            Connection cn = getConnection();

            String sql = "DELETE FROM presupuesto WHERE idPresupuesto=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(true, "Presupuesto eliminado", null, null);

            } else {

                return new Response<>(false, "No existe el presupuesto", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // BUSCAR POR ID
    public Response<Presupuesto> obtenerPorId(int id) {

        try {

            Connection cn = getConnection();

            String sql = "SELECT * FROM presupuesto WHERE idPresupuesto=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Presupuesto p = null;

            if (rs.next()) {

                p = new Presupuesto(
                        rs.getInt("idPresupuesto"),
                        rs.getDouble("montoInicial"),
                        rs.getDouble("montoActual"),
                        rs.getDate("fechaCreacion").toLocalDate(),
                        rs.getInt("idUsuario")
                );
            }

            cn.close();

            if (p != null) {

                return new Response<>(true, "Presupuesto encontrado",  p,  null);

            } else {

                return new Response<>( false, "No existe el presupuesto", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(),null, null);
        }
    }

    // LISTAR TODOS
    public Response<Presupuesto> obtenerTodos() {

        try {

            Connection cn = getConnection();

            String sql = "SELECT * FROM presupuesto";

            Statement st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            List<Presupuesto> lista = new ArrayList<>();

            while (rs.next()) {

                Presupuesto p = new Presupuesto(
                        rs.getInt("idPresupuesto"),
                        rs.getDouble("montoInicial"),
                        rs.getDouble("montoActual"),
                        rs.getDate("fechaCreacion").toLocalDate(),
                        rs.getInt("idUsuario")
                );

                lista.add(p);
            }

            cn.close();

            return new Response<>(true, "Lista de presupuestos obtenida", null, lista);

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }
}