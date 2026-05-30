/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Camil
 */
public class UsuarioDao extends BaseDao{
    
    // INSERTAR USUARIO
    public Response<Usuario> insertar(Usuario u) {

        try {
            String sql = "INSERT INTO usuario (nombre, correo, contrasena) VALUES (?, ?, ?)";

            Connection cn = getConnection();
            PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getContrasena());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setIdUsuario(rs.getInt(1));
            }

            cn.close();

            return new Response<>(true, "Usuario insertado correctamente", u, null);

        } catch (Exception e) {
            return new Response<>(false, "Error al insertar usuario: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR USUARIO
    public Response<Usuario> actualizar(Usuario u) {

        try {
            String sql = "UPDATE usuario SET nombre=?, correo=?, contrasena=? WHERE idUsuario=?";

            Connection cn = getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCorreo());
            ps.setString(3, u.getContrasena());
            ps.setInt(4, u.getIdUsuario());

            int filas = ps.executeUpdate();
            cn.close();

            if (filas > 0) {
                return new Response<>(true, "Usuario actualizado correctamente", u, null);
            } else {
                return new Response<>(false, "Usuario no encontrado", null, null);
            }

        } catch (Exception e) {
            return new Response<>(false, "Error al actualizar usuario: " + e.getMessage(), null, null);
        }
    }

    // ELIMINAR USUARIO
    public Response<Usuario> eliminar(int id) {

        try {
            String sql = "DELETE FROM usuario WHERE idUsuario=?";

            Connection cn = getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            cn.close();

            if (filas > 0) {
                return new Response<>(true, "Usuario eliminado correctamente", null, null);
            } else {
                return new Response<>(false, "Usuario no encontrado", null, null);
            }

        } catch (Exception e) {
            return new Response<>(false, "Error al eliminar usuario: " + e.getMessage(), null, null);
        }
    }

    // OBTENER POR ID
    public Response<Usuario> obtenerPorId(int id) {

        try {
            String sql = "SELECT * FROM usuario WHERE idUsuario=?";

            Connection cn = getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            Usuario u = null;

            if (rs.next()) {
                u = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                );
            }

            cn.close();

            if (u != null) {
                return new Response<>(true, "Usuario encontrado", u, null);
            } else {
                return new Response<>(false, "Usuario no existe", null, null);
            }

        } catch (Exception e) {
            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }

    // OBTENER TODOS
    public Response<Usuario> obtenerTodos() {

        try {
            List<Usuario> lista = new ArrayList<>();

            String sql = "SELECT * FROM usuario";

            Connection cn = getConnection();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                );

                lista.add(u);
            }

            cn.close();

            return new Response<>(true, "Lista de usuarios obtenida", null, lista);

        } catch (Exception e) {
            return new Response<>(false, "Error: " + e.getMessage(), null, null);
        }
    }
}
