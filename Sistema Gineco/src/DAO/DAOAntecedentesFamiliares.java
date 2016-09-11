/*
 * Clase DAO dedicada a los antecedentes familiares
 */
package DAO;

import ClasesBase.modelo.AntecedentesFamiliares;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOAntecedentesFamiliares {
    
    private DAOConexion conexion;
    private AntecedentesFamiliares af;
    private ResultSet rs;
    private Connection conn;
    private PreparedStatement pst;
    
    public DAOAntecedentesFamiliares() {
        conexion = new DAOConexion();
    }
    
    /**
     * Metodo utilizado para registrar un antecedente familiar
     * @param af Antecedente a registrar
     * @param dni dni del paciente que posee ese antecedente familiar
     * @return true si se registra correctamente, false si no se registra
     */ 
    public boolean registrarAntecedentesFamiliares(AntecedentesFamiliares af,long dni) {
        try {
            String cons =
                    "INSERT INTO sistemacarla.antecedentesfamiliares VALUES (null,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?, "
                    + "?)";
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(cons);
            pst.setBoolean(1, af.isDbt());
            pst.setBoolean(2, af.isHta());
            pst.setBoolean(3, af.isOncologicos());
            pst.setBoolean(4, af.isTiroides());
            pst.setString(5, af.getDescripcionTiroides());
            pst.setString(6, af.getDescripcionOncologicos());
            pst.setString(7, af.getObservaciones());
            pst.setLong(8, dni);
            
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedentesFamiliares.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedentesFamiliares.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
    
     
    /**
     * Metodo utilizado para obtener un antecedente familiar
     * @param dni dni del paciente cuyo antecedente se quiere obtener
     * @return Antecedente Familiar buscado
     */ 
    public AntecedentesFamiliares getAntecedenteFamiliar(long dni) {
        af = null;
        String consulta = "SELECT * FROM sistemacarla.antecedentesfamiliares WHERE dniPaciente = ?";
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                af = new AntecedentesFamiliares();
                af.setDbt(rs.getBoolean("diabetes"));
                af.setDescripcionOncologicos(rs.getString("descripcionOncologicos"));
                af.setDescripcionTiroides(rs.getString("descripcionTiroides"));
                af.setHta(rs.getBoolean("hipertension"));
                af.setObservaciones(rs.getString("Observaciones"));
                af.setOncologicos(rs.getBoolean("oncologicos"));
                af.setTiroides(rs.getBoolean("tiroides"));
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return af;
    }

    /**
     * Metodo utilizado para actualizar un antecedente
     * @param af Antecedente modificado para actualizar
     * @param dni dni del paciente que posee ese antecedente familiar
     * @return true si se actualiza correctamente, false si no se actualiza
     */ 
    public boolean actualizarAntecedente(AntecedentesFamiliares af,long dni) {
        try {
            String cons =
                    "UPDATE sistemacarla.antecedentesfamiliares "
                    + "SET diabetes = ?,"
                    + "hipertension = ?,"
                    + "oncologicos = ?,"
                    + "tiroides = ?,"
                    + "descripcionTiroides = ?,"
                    + "descripcionOncologicos = ?,"
                    + "observaciones = ? "
                    + "WHERE dniPaciente = ?";
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(cons);
            pst.setBoolean(1, af.isDbt());
            pst.setBoolean(2, af.isHta());
            pst.setBoolean(3, af.isOncologicos());
            pst.setBoolean(4, af.isTiroides());
            pst.setString(5, af.getDescripcionTiroides());
            pst.setString(6, af.getDescripcionOncologicos());
            pst.setString(7, af.getObservaciones());
            pst.setLong(8, dni);
            
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedentesFamiliares.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedentesFamiliares.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
}
