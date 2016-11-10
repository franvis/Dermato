/*
 * Clase DAO dedicada a los antecedentes generales
 */
package DAO;

import ClasesBase.modelo.Antecedents;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOAntecedentes {
    
    private DAOConexion conexion;
    private ResultSet rs;
    private Connection conn;
    private Antecedents ag;
    private PreparedStatement pst;

    public DAOAntecedentes() {
        conexion = new DAOConexion();
    }
    
    
    /**
     * Method used to register the patient antecedents
     * @param antecedents Antecedents
     * @param dni patient dni
     * @return true if registered, false otherwise
     */
    public boolean registerAntecedents(Antecedents antecedents,long dni) {
        try {
            conn = conexion.conectarBD();
            String cons = "INSERT INTO sistemaCarla.AntecedentesGenerales VALUES (null,?, ?, ?, ?)";
            pst = conn.prepareStatement(cons);
            pst.setString(1, antecedents.getAntecedentesPersonales());
            pst.setString(2, antecedents.getAntecedentesQuirurgicos());
            pst.setString(3, antecedents.getAntecedentesToxicos());
            pst.setLong(4, dni);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedentes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedentes.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
    /**
     * Metodo utilizado para obtener un antecedente general
     * @param dni dni del paciente cuyo antecedente se quiere obtener
     * @return Antecedente General buscado
     */ 
    public Antecedents getAntecedenteGeneral(long dni) {
        ag = null;
        String consulta = "SELECT * FROM sistemaCarla.AntecedentesGenerales WHERE dniPaciente = ?";
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {   
                ag = new Antecedents();
                ag.setAntecedentesPersonales(rs.getString("antecedentesP"));
                ag.setAntecedentesQuirurgicos(rs.getString("antecedentesQ"));
                ag.setAntecedentesToxicos(rs.getString("antecedentesT"));
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return ag;
    }
    
     /**
     * Metodo utilizado para actualizar un antecedente general
     * @param agen Antecedente General modificado para actualizar
     * @param dni dni del paciente que posee ese antecedente familiar
     * @return true si se actualiza correctamente, false si no se actualiza
     */
    public boolean actualizarAntecedente(Antecedents agen,long dni) {
        try {
            conn = conexion.conectarBD();
            String cons = "UPDATE sistemaCarla.AntecedentesGenerales SET "
                    + "antecedentesP = ?,"
                    + "antecedentesQ = ?,"
                    + "antecedentesT = ? "
                    + "WHERE dniPaciente = ?";
            pst = conn.prepareStatement(cons);
            pst.setString(1, agen.getAntecedentesPersonales());
            pst.setString(2, agen.getAntecedentesQuirurgicos());
            pst.setString(3, agen.getAntecedentesToxicos());
            pst.setLong(4, dni);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedentes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedentes.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
}
