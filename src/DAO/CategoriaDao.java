/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import Model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author hp
 */
public class CategoriaDao extends BaseDao {

    // INSERTAR
    public Response<Categoria> insertar(Categoria c) {

        try {

            Connection cn = getConnection();

            String sql = "INSERT INTO categoria " + "(nombreCategoria, tipo) "
                    + "VALUES (?, ?)";

            PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, c.getNombreCategoria());
            ps.setString(2, c.getTipo());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                c.setIdCategoria(rs.getInt(1));
            }

            cn.close();

            return new Response<>(true, "Categoria registrada", c, null);

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR
    public Response<Categoria> actualizar(Categoria c) {

        try {

            Connection cn = getConnection();

            String sql = "UPDATE categoria SET "
                    + "nombreCategoria=?, "
                    + "tipo=? "
                    + "WHERE idCategoria=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, c.getNombreCategoria());
            ps.setString(2, c.getTipo());
            ps.setInt(3, c.getIdCategoria());

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(true, "Categoria actualizada", c, null);

            } else {

                return new Response<>(false, "No existe la categoria", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR
    public Response<Categoria> eliminar(int id) {

        try {

            Connection cn = getConnection();

            String sql = "DELETE FROM categoria WHERE idCategoria=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            cn.close();

            if (filas > 0) {

                return new Response<>(true,  "Categoria eliminada", null, null);

            } else {

                return new Response<>(false, "No existe la categoria", null, null);
            }

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // BUSCAR POR ID
    public Response<Categoria> obtenerPorId(int id) {

        try {

            Connection cn = getConnection();

            String sql = "SELECT * FROM categoria WHERE idCategoria=?";

            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Categoria c = null;

            if (rs.next()) {

                c = new Categoria(
                        rs.getInt("idCategoria"),
                        rs.getString("nombreCategoria"),
                        rs.getString("tipo")
                );
            }

            cn.close();

            if (c != null) {

                return new Response<>(true, "Categoria encontrada", c, null);

            } else {

                return new Response<>(false, "No existe la categoria", null, null);
            }

        } catch (Exception e) {

            return new Response<>(
                    false, "Error: " + e.getMessage(), null, null);
        }
    }

    // LISTAR TODAS
    public Response<Categoria> obtenerTodos() {

        try {

            Connection cn = getConnection();

            String sql = "SELECT * FROM categoria";

            Statement st = cn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            List<Categoria> lista = new ArrayList<>();

            while (rs.next()) {

                Categoria c = new Categoria(
                        rs.getInt("idCategoria"),
                        rs.getString("nombreCategoria"),
                        rs.getString("tipo")
                );

                lista.add(c);
            }

            cn.close();

            return new Response<>(true,"Lista de categorias obtenida", null, lista);

        } catch (Exception e) {

            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }
}