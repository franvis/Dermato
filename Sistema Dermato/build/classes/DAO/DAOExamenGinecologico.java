/*
 * Clase DAO correspondiente a los examenes ginecologicos
 */
package DAO;

import ClasesBase.modelo.ExamenGinecologico;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOExamenGinecologico {
    
    private DAOConexion conexion;
    private ExamenGinecologico eg;
    private ResultSet rs;
    private Connection conn;
    private PreparedStatement pst;
    
    public DAOExamenGinecologico()
    {
        conexion = new DAOConexion();
    }
    
    /**
     * Metodo utilizado para registrar un examen ginecologico
     * @param eg Examen Ginecologico a registrar
     * @param idConsulta id de la consulta a la cual pertenece el examen
     * @return true si se registra correctamente, false si no se registra
     */
    public boolean RegistrarExamGinec(ExamenGinecologico eg, long idConsulta)
    {
        try {
            conn = conexion.conectarBD();
            String cons = "INSERT INTO sistemaCarla.ExamenGinecologico VALUES (null,?,?,?,?,?)";
            pst = conn.prepareStatement(cons);
            pst.setString(1, eg.getMamas());
            pst.setString(2, eg.getGenitourinario());
            pst.setString(3, eg.getBethesda());
            pst.setString(4, eg.getPapycolposcopia());
            pst.setLong(5, idConsulta);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOExamenGinecologico.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOExamenGinecologico.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
    /**
     * Metodo utilizado para registrar un examen ginecologico dentro de una transacción
     * @param eg Examen Ginecologico a registrar
     * @param idConsulta id de la consulta a la cual pertenece el examen
     * @param conn conexión en la que se realiza la transacción
     */
    public void RegistrarExamGinec(ExamenGinecologico eg, long idConsulta, Connection conn) throws SQLException
    {
        try {
            String cons = "INSERT INTO sistemaCarla.ExamenGinecologico VALUES (null,?,?,?,?,?)";
            pst = conn.prepareStatement(cons);
            pst.setString(1, eg.getMamas());
            pst.setString(2, eg.getGenitourinario());
            pst.setString(3, eg.getBethesda());
            pst.setString(4, eg.getPapycolposcopia());
            pst.setLong(5, idConsulta);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException();
        }
    }
    
    /**
     * Metodo utilizado para obtener un examen ginecologico
     * @param id id de la consulta de la cual se quiere obtener el examen ginecologico
     * @return Examen Ginecologico buscado
     */
    public ExamenGinecologico getExamenGinec(int id) {
        
        String consulta = "SELECT * FROM sistemaCarla.ExamenGinecologico WHERE consulta = ?";
                
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setInt(1, id);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                eg = new ExamenGinecologico();
                eg.setBethesda(rs.getString("bethesda"));
                eg.setGenitourinario(rs.getString("genitourinario"));
                eg.setMamas(rs.getString("mamas"));
                eg.setPapycolposcopia(rs.getString("papycolpo"));
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return eg;
    }
    
    
    /**
     * Metodo utilizado para actualizar un examen ginecologico
     * @param examenGinecologico Examen Ginecologico modificado para actualizar
     * @param id id de la consulta que contiene el antecedente a actualizar
     * @return true si se actualiza correctamente, false si no se actualiza
     */
    public boolean ActualizarExamGinec(ExamenGinecologico examenGinecologico,int idConsulta) {
        try {
            conn = conexion.conectarBD();
            eg = examenGinecologico;
            String cons = "UPDATE sistemaCarla.ExamenGinecologico SET mamas = ?, genitourinario = ?, bethesda = ? ,papycolpo = ? WHERE consulta = ?";
            pst = conn.prepareStatement(cons);
            pst.setString(1, eg.getMamas());
            pst.setString(2, eg.getGenitourinario());
            pst.setString(3, eg.getBethesda());
            pst.setString(4, eg.getPapycolposcopia());
            pst.setInt(5, idConsulta);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOExamenGinecologico.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOExamenGinecologico.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
}
