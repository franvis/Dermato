/*
 * Clase DAO correspondiente a los examenes obstetricos
 */
package DAO;

import ClasesBase.modelo.ExamenObstetrico;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOExamenObstetrico {
    
    private DAOConexion conexion;
    private ExamenObstetrico eo;
    private ResultSet rs;
    private Connection conn;
    private PreparedStatement pst;
    
    public DAOExamenObstetrico()
    {   
        conexion = new DAOConexion();
    }
    
    /**
     * Metodo utilizado para registrar un examen obstetrico
     * @param eo Examen Obstetrico a registrar
     * @param idConsulta id de la consulta a la cual pertenece el examen
     * @return true si se registra correctamente, false si no se registra
     */
    public boolean registrarExamObste(ExamenObstetrico eo, long idConsulta)
    {
        try {
            conn = conexion.conectarBD();
            String cons = "INSERT INTO sistemacarla.examenobstetrico VALUES (null,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(cons);
            pst.setFloat(1,eo.getTalla());
            pst.setFloat(2,eo.getPeso());
            pst.setFloat(3,eo.getImc());
            pst.setFloat(4,eo.getAu());
            pst.setString(5,eo.getPa());
            pst.setFloat(6,eo.getLcf());
            pst.setBoolean(7,eo.getMaf());
            pst.setString(8,eo.getObservaciones());
            pst.setLong(9,idConsulta);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedentesGenerales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOExamenObstetrico.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
    /**
     * Metodo utilizado para registrar un examen obstetrico dentro de una transacción
     * @param eo Examen Obstetrico a registrar
     * @param idConsulta id de la consulta a la cual pertenece el examen
     * @param conn conexión en la que se realiza la transacción
     */
    public void registrarExamObste(ExamenObstetrico eo, long idConsulta, Connection conn) throws SQLException
    {
        try {
            String cons = "INSERT INTO sistemacarla.examenobstetrico VALUES (null,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(cons);
            pst.setFloat(1,eo.getTalla());
            pst.setFloat(2,eo.getPeso());
            pst.setFloat(3,eo.getImc());
            pst.setFloat(4,eo.getAu());
            pst.setString(5,eo.getPa());
            pst.setFloat(6,eo.getLcf());
            pst.setBoolean(7,eo.getMaf());
            pst.setString(8,eo.getObservaciones());
            pst.setLong(9,idConsulta);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    /**
     * Metodo utilizado para obtener un examen obstetrico
     * @param id id de la consulta de la cual se quiere obtener el examen obstetrico
     * @return Examen Obstetrico buscado
     */
    public ExamenObstetrico getExamenObste(int id) {
        String consulta = "SELECT * FROM sistemacarla.examenObstetrico WHERE Consulta = ?";
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setInt(1, id);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                eo = new ExamenObstetrico();
                eo.setAu(rs.getFloat("alturaUterina"));
                eo.setImc(rs.getFloat("imc"));
                eo.setLcf(rs.getInt("latidosCardioFetales"));
                eo.setMaf(rs.getBoolean("movActivosFetales"));
                eo.setObservaciones(rs.getString("observaciones"));
                eo.setPa(rs.getString("presionArterial"));
                eo.setPeso(rs.getFloat("peso"));
                eo.setTalla(rs.getFloat("talla"));
            }
            pst.close();
        }
        catch (SQLException ex) {
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return eo;
    }
    
    
    /**
     * Metodo utilizado para actualizar un examen obstetrico
     * @param examenObstetrico Examen Obstetrico modificado para actualizar
     * @param idConsulta id de la consulta que contiene el antecedente a actualizar
     * @return true si se actualiza correctamente, false si no se actualiza
     */
    public boolean ActualizarExamObste(ExamenObstetrico examenObstetrico, int idConsulta) {
        try {
            eo = examenObstetrico;
            conn = conexion.conectarBD();
            String cons = "UPDATE sistemacarla.examenobstetrico SET "
                    + "talla = ?,"
                    + "peso = ?,"
                    + "imc = ?,"
                    + "alturaUterina = ?,"
                    + "presionArterial = ?,"
                    + "latidosCardioFetales = ?,"
                    + "movActivosFetales = ?,"
                    + "observaciones = ? "
                    + "WHERE consulta = ?";
            pst = conn.prepareStatement(cons);
            pst.setFloat(1,eo.getTalla());
            pst.setFloat(2,eo.getPeso());
            pst.setFloat(3,eo.getImc());
            pst.setFloat(4,eo.getAu());
            pst.setString(5,eo.getPa());
            pst.setFloat(6,eo.getLcf());
            pst.setBoolean(7,eo.getMaf());
            pst.setString(8,eo.getObservaciones());
            pst.setLong(9,idConsulta);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedentesGenerales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOExamenObstetrico.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
}
