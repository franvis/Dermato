/*
 * Clase DAO dedicada a las obras sociales;
 */
package DAO;

import ClasesBase.modelo.ObraSocial;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOObraSocial {
    
    private DAOConexion conexion;
    private ResultSet rs;
    private Connection conn;
    private ObraSocial o;
    private String consulta;
    private PreparedStatement pst;
    private LinkedList<ObraSocial> obras;

    public DAOObraSocial() {
        conexion = new DAOConexion();
    }
    
    /**
     * Metodo utilizado para obtener todas las obras sociales
     * @return Lista de todas las obras sociales
     */
    public LinkedList<ObraSocial> getAllObrasSociales()
    {
        obras = new LinkedList<>();
        conn = conexion.conectarBD();
        consulta = "SELECT * FROM OBRASOCIAL ORDER BY nombre ASC";
        try {
            pst = conn.prepareStatement(consulta);
            pst.executeQuery(consulta);
            rs = pst.getResultSet();
            while(rs.next())
            {
                o = new ObraSocial();
                o.setId(rs.getInt("idObraSocial"));
                o.setNombre(rs.getString("nombre"));
                obras.add(o);
            }
            pst.close();
        }
        catch (SQLException ex) {
        }
        conexion.desconectarBD(conn);
        return obras;
    }
    
    /**
     * Metodo utilizado para registrar una obra social
     * @param o Obra Social a registrar
     * @return true si se registra correctamente, false si no se registra
     */
    public boolean registrarObraSocial(ObraSocial o) {
        try {
            conn = conexion.conectarBD();
            String cons = "INSERT INTO sistemaCarla.ObraSocial VALUES (null,?)";
            pst = conn.prepareStatement(cons);
            pst.setString(1, o.getNombre());
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOObraSocial.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            conexion.desconectarBD(conn);
        }
    }
    
     /**
     * Metodo utilizado para actualizar una Obra Social
     * @param ObraSocial Obra Social modificada para actualizar
     * @return true si se actualiza correctamente, false si no se actualiza
     */
    public boolean actualizarObraSocial(ObraSocial o) {
        try {
            conn = conexion.conectarBD();
            String cons = "UPDATE sistemaCarla.ObraSocial SET nombre = ? WHERE idObraSocial = ?";
            pst = conn.prepareStatement(cons);
            pst.setString(1, o.getNombre());
            pst.setInt(2, o.getId());
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOObraSocial.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            conexion.desconectarBD(conn);
        }
    }
    
    /**
     * Metodo utilizado para eliminar una Obra Social
     * @param ObraSocial Obra Social a eliminar
     * @return true si se elimina correctamente, false si no se elimina
     */
    public boolean eliminarObraSocial(ObraSocial o)
    {
        boolean rtdo = false;
        try {
            conn = conexion.conectarBD();
            conn.setAutoCommit(false);
            PreparedStatement deleteObraSocial =  conn.prepareStatement(
			"DELETE FROM sistemaCarla.ObraSocial WHERE idObraSocial = ? ");
            deleteObraSocial.setInt(1, o.getId());
            deleteObraSocial.executeUpdate();
            PreparedStatement updatePacientes = conn.prepareStatement(
                        "UPDATE sistemaCarla.Paciente SET numeroAfiliado = '' WHERE obraSocial IS NULL");
            updatePacientes.executeUpdate();
            conn.commit();
            rtdo = true;
            conn.setAutoCommit(true);
        }
        
        catch (Exception ex) {
            try {
                conn.rollback(); 
            }
            catch (SQLException e) {
                System.out.println("RollBack Failure." + e.getMessage());
            }
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return rtdo;
    }
    
    /**
    * Metodo utilizado para obtener una obra social
    * @param id id de la obra social a obtener
    * @return Obra Social buscada
    */
    public ObraSocial getObraSocial(int id) {
        consulta = "SELECT * FROM OBRASOCIAL WHERE idObraSocial = ?";
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1,id);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                o = new ObraSocial();
                o.setId(rs.getInt("idObraSocial"));
                o.setNombre(rs.getString("nombre"));
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return o;
    }

}
