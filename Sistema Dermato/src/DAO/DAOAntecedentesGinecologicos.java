/*
 * Clase DAO dedicada a los antecedentes ginecologicos
 */
package DAO;

import ClasesBase.modelo.AntecedentesGinecologicos;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOAntecedentesGinecologicos {
    
    private DAOConexion conexion;
    private ResultSet rs;
    private Connection conn;
    private AntecedentesGinecologicos ag;
    private PreparedStatement pst;

    public DAOAntecedentesGinecologicos() {
        conexion = new DAOConexion();
    }
    
    /**
     * Metodo utilizado para registrar un antecedente ginecologico
     * @param aginec Antecedente Ginecologico a registrar
     * @param dni dni del paciente que posee ese antecedente ginecologico
     * @return true si se registra correctamente, false si no se registra
     */
    public boolean registrarAntecedentesGinecologicos(AntecedentesGinecologicos aginec,long dni) {
        try {
            conn = conexion.conectarBD();
            String cons = "INSERT INTO sistemaCarla.AntecedentesGinecologicos VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst =  conn.prepareStatement(cons);
            pst.setInt(1, aginec.getMenarca());
            pst.setString(2, aginec.getTelarca());
            pst.setInt(3, aginec.getMenopausia());
            pst.setInt(4, aginec.getDuracionMenstrual());
            pst.setInt(5, aginec.getPeriodoMenstrual());
            pst.setInt(6, aginec.getGestaciones());
            pst.setInt(7, aginec.getAbortos());
            pst.setInt(8, aginec.getVivos());
            pst.setInt(9, aginec.getMuertos());
            pst.setBoolean(10, aginec.isDispareunia());
            pst.setBoolean(11, aginec.isDismenorrea());
            pst.setBoolean(12, aginec.isSdpm());
            pst.setString(13, aginec.getAnticonceptivos());
            pst.setInt(14, aginec.getPartos());
            pst.setInt(15, aginec.getCesareas());
            pst.setString(16, aginec.getObservaciones());
            pst.setLong(17, dni);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedentesGenerales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedentesGinecologicos.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
    /**
     * Metodo utilizado para obtener un antecedente ginecologico
     * @param dni dni del paciente cuyo antecedente se quiere obtener
     * @return Antecedente Ginecologico buscado
     */
    public AntecedentesGinecologicos getAntecedenteGinecologico(long dni)
    {
        ag = null;
        String consulta = "SELECT * FROM sistemaCarla.AntecedentesGinecologicos WHERE dniPaciente = ?";
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                ag = new AntecedentesGinecologicos();
                ag.setAbortos(rs.getInt("abortos"));
                ag.setAnticonceptivos(rs.getString("anticonceptivos"));
                ag.setCesareas(rs.getInt("cesareas"));
                ag.setDismenorrea(rs.getBoolean("dismenorrea"));
                ag.setDispareunia(rs.getBoolean("dispareunia"));
                ag.setDuracionMenstrual(rs.getInt("duracionMenstrual"));
                ag.setGestaciones(rs.getInt("gestaciones"));
                ag.setMenarca(rs.getInt("menarca"));
                ag.setMenopausia(rs.getInt("menopausia"));
                ag.setMuertos(rs.getInt("muertos"));
                ag.setObservaciones(rs.getString("observaciones"));
                ag.setPartos(rs.getInt("partos"));
                ag.setPeriodoMenstrual(rs.getInt("periodoMenstrual"));
                ag.setSdpm(rs.getBoolean("sdpm"));
                ag.setTelarca(rs.getString("telarca"));
                ag.setVivos(rs.getInt("vivos"));
                
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
     * Metodo utilizado para actualizar un antecedente ginecologico
     * @param aginec Antecedente Ginecologico modificado para actualizar
     * @param dni dni del paciente que posee ese antecedente familiar
     * @return true si se actualiza correctamente, false si no se actualiza
     */
    public boolean actualizarAntecedente(AntecedentesGinecologicos aginec, long dni) {
         try {
            conn = conexion.conectarBD();
            String cons = "UPDATE sistemaCarla.AntecedentesGinecologicos SET "
                    + "menarca = ?,"
                    + "telarca = ?,"
                    + "menopausia = ?,"
                    + "duracionMenstrual = ?,"
                    + "periodoMenstrual = ?,"
                    + "gestaciones = ?,"
                    + "abortos = ?,"
                    + "vivos = ?,"
                    + "muertos = ?,"
                    + "dispareunia = ?, dismenorrea = ?,"
                    + "sdpm = ?,"
                    + "anticonceptivos = ?,"
                    + "partos = ?,"
                    + "cesareas = ?,"
                    + "observaciones = ?"
                    + " WHERE dniPaciente = ?";
            pst =  conn.prepareStatement(cons);
            pst.setInt(1, aginec.getMenarca());
            pst.setString(2, aginec.getTelarca());
            pst.setInt(3, aginec.getMenopausia());
            pst.setInt(4, aginec.getDuracionMenstrual());
            pst.setInt(5, aginec.getPeriodoMenstrual());
            pst.setInt(6, aginec.getGestaciones());
            pst.setInt(7, aginec.getAbortos());
            pst.setInt(8, aginec.getVivos());
            pst.setInt(9, aginec.getMuertos());
            pst.setBoolean(10, aginec.isDispareunia());
            pst.setBoolean(11, aginec.isDismenorrea());
            pst.setBoolean(12, aginec.isSdpm());
            pst.setString(13, aginec.getAnticonceptivos());
            pst.setInt(14, aginec.getPartos());
            pst.setInt(15, aginec.getCesareas());
            pst.setString(16, aginec.getObservaciones());
            pst.setLong(17, dni);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedentesGenerales.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedentesGinecologicos.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
    
    
}
