/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.Response;
import Model.Alerta;
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
public class AlertaDao extends BaseDao {

    // INSERTAR
    public Response<Alerta> insertar(Alerta a) throws Exception {

    try {

        try (Connection cn = getConnection()) {

            String sql = "INSERT INTO alerta (mensaje, fechaAlerta, idUsuario) VALUES (?, ?, ?)";

            PreparedStatement ps = cn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, a.getMensaje());
            ps.setDate(2, Date.valueOf(a.getFechaAlerta()));
            ps.setInt(3, a.getIdUsuario());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                a.setIdAlerta(rs.getInt(1));
            }
        }

        return new Response<>(true, "Alerta registrada", a, null);

    } catch (SQLException e) {

        return new Response<>(false, "Error: " + e.getMessage(), null, null);
    }
}

    // ACTUALIZAR
    public Response<Alerta> actualizar(Alerta a) throws Exception {

    try {

        int filas;

        try (Connection cn = getConnection()) {

            String sql = "UPDATE alerta SET mensaje=?, fechaAlerta=?, idUsuario=? WHERE idAlerta=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, a.getMensaje());
            ps.setDate(2, Date.valueOf(a.getFechaAlerta()));
            ps.setInt(3, a.getIdUsuario());
            ps.setInt(4, a.getIdAlerta());

            filas = ps.executeUpdate();
        }

        if (filas > 0) {

            return new Response<>(true, "Alerta actualizada", a, null);

        } else {

            return new Response<>(false, "No existe la alerta", null, null);
        }

    } catch (SQLException e) {

        return new Response<>(false, "Error: " + e.getMessage(), null, null);
    }
}

    // ELIMINAR
    public Response<Alerta> eliminar(int idAlerta) throws Exception {

    try {

        int filas;

        try (Connection cn = getConnection()) {

            String sql = "DELETE FROM alerta WHERE idAlerta=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, idAlerta);

            filas = ps.executeUpdate();
        }

        if (filas > 0) {

            return new Response<>(true, "Alerta eliminada", null, null);

        } else {

            return new Response<>(false, "No existe la alerta", null, null);
        }

    } catch (SQLException e) {

        return new Response<>(false, "Error: " + e.getMessage(), null, null);
    }
}

    // BUSCAR POR MENSAJE
    public Response<Alerta> obtenerPorId(int idAlerta) throws Exception {

    try {

        Alerta a = null;

        try (Connection cn = getConnection()) {

            String sql = "SELECT * FROM alerta WHERE idAlerta=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, idAlerta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                a = new Alerta(
                        rs.getInt("idAlerta"),
                        rs.getString("mensaje"),
                        rs.getDate("fechaAlerta").toLocalDate(),
                        rs.getInt("idUsuario")
                );
            }
        }

        if (a != null) {

            return new Response<>(true, "Alerta encontrada", a, null);

        } else {

            return new Response<>(false, "No existe la alerta", null, null);
        }

    } catch (SQLException e) {

        return new Response<>(false, "Error: " + e.getMessage(), null, null);
    }
}

    // LISTAR TODAS
    public Response<Alerta> obtenerTodos() throws Exception {

    try {

        List<Alerta> lista = new ArrayList<>();

        try (Connection cn = getConnection()) {

            String sql = "SELECT * FROM alerta";

            Statement st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                Alerta a = new Alerta(
                        rs.getInt("idAlerta"),
                        rs.getString("mensaje"),
                        rs.getDate("fechaAlerta").toLocalDate(),
                        rs.getInt("idUsuario")
                );

                lista.add(a);
            }
        }

        return new Response<>(true, "Lista de alertas obtenida", null, lista);

    } catch (SQLException e) {

        return new Response<>(false, "Error: " + e.getMessage(), null, null);
    }
}
}
