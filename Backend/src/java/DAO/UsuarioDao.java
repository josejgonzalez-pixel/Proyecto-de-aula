/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Util.Response;
import Model.Usuario;
import Util.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Camil
 */
public class UsuarioDao extends BaseDao {

    // REGISTRAR NUEVO USUARIO
    public Response<Usuario> RegistrarNuevoUsuario(Usuario u) {
        Connection cn = null;
        PreparedStatement psVerificar = null;
        PreparedStatement psInsertar = null;
        ResultSet rs = null;
        ResultSet rsId = null;

        try {
            cn = getConnection();

            //  1. ACTIVAR AUTO-COMMIT: Obliga a MySQL a persistir los datos de forma inmediata
            if (cn != null) {
                cn.setAutoCommit(true);
            } else {
                return new Response<>(false, "No se pudo establecer conexión con la base de datos.", null, null);
            }

            // 2. Verificar si el correo ya existe
            String sqlVerificar = "SELECT * FROM Usuario WHERE correo=?";
            psVerificar = cn.prepareStatement(sqlVerificar);
            psVerificar.setString(1, u.getCorreo());
            rs = psVerificar.executeQuery();

            if (rs.next()) {
                return new Response<>(false, "El correo ya se encuentra registrado", null, null);
            }

            // 3. Registrar e insertar usuario de forma definitiva
            String sqlInsertar = "INSERT INTO Usuario(nombre, correo, contrasena) VALUES(?,?,?)";
            psInsertar = cn.prepareStatement(sqlInsertar, Statement.RETURN_GENERATED_KEYS);

            psInsertar.setString(1, u.getNombre());
            psInsertar.setString(2, u.getCorreo());
            psInsertar.setString(3, u.getContrasena());

            // Ejecuta el insert físico en la BD
            int filasAfectadas = psInsertar.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✅ ÉXITO SQL: Fila insertada correctamente en la tabla Usuario.");
            }

            // 4. Recuperar ID autogenerado
            rsId = psInsertar.getGeneratedKeys();
            if (rsId.next()) {
                u.setIdUsuario(rsId.getInt(1));
            }

            return new Response<>(true, "Usuario registrado correctamente", u, null);

        } catch (Exception e) {
            System.out.println("❌ ERROR EN DAO: " + e.getMessage());
            return new Response<>(false, "Error al registrar usuario: " + e.getMessage(), null, null);
        } finally {
            //  5. BLOQUE FINALLY: Cierra de forma segura todos los recursos JDBC para que impacten en DBeaver
            try {
                if (rs != null) {
                    rs.close();
                }
                if (rsId != null) {
                    rsId.close();
                }
                if (psVerificar != null) {
                    psVerificar.close();
                }
                if (psInsertar != null) {
                    psInsertar.close();
                }
                if (cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos JDBC: " + ex.getMessage());
            }
        }
    }

    // INSERTAR USUARIO
    public Response<Usuario> insertar(Usuario u) {

        try {
            String sql = "INSERT INTO Usuario (nombre, correo, contrasena) VALUES (?, ?, ?)";

            try (Connection cn = getConnection()) {
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, u.getNombre());
                ps.setString(2, u.getCorreo());
                ps.setString(3, u.getContrasena());

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    u.setIdUsuario(rs.getInt(1));
                }
            }

            return new Response<>(true, "Usuario insertado correctamente", u, null);

        } catch (Exception e) {
            return new Response<>(false, "Error al insertar usuario: " + e.getMessage(), null, null);
        }
    }

    // ACTUALIZAR USUARIO
    public Response<Usuario> actualizar(Usuario u) {

        try {
            String sql = "UPDATE Usuario SET nombre=?, correo=?, contrasena=? WHERE idUsuario=?";

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
            String sql = "DELETE FROM Usuario WHERE idUsuario=?";

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
            String sql = "SELECT * FROM Usuario WHERE idUsuario=?";

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
    
    public Usuario validarYObtenerUsuario(String correo, String contrasena) {
    // IMPORTANTE: Asegúrate de que el nombre de la tabla sea "Usuario" (como en tus otros métodos) 
    // y no "usuarios" si en tu base de datos la tabla está en singular.
    String sql = "SELECT idUsuario, nombre, correo, contrasena FROM Usuario WHERE correo = ? AND contrasena = ?";
    
    try (Connection conn = Conexion.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, correo);
        ps.setString(2, contrasena);
        
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            // Pasamos los valores directamente al constructor de 4 parámetros
            return new Usuario(
                rs.getInt("idUsuario"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("contrasena")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    // OBTENER TODOS
    public Response<Usuario> obtenerTodos() {

        try {
            List<Usuario> lista = new ArrayList<>();

            String sql = "SELECT * FROM Usuario";

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
