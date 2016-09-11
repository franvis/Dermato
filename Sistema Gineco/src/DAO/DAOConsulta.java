/*
Clase DAO dedicada a la consulta;
*/
package DAO;

import ClasesBase.modelo.Consulta;
import ClasesBase.modelo.ExamenGinecologico;
import ClasesBase.modelo.ExamenObstetrico;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOConsulta {
    private DAOConexion conexion;
    private DAOExamenGinecologico daoExamGinec;
    private DAOExamenObstetrico daoExamObste;
    private ResultSet rs;
    private Connection conn;
    private PreparedStatement pst;
    private Consulta c;
    
    public DAOConsulta()
    {
    conexion = new DAOConexion();
    daoExamGinec = new DAOExamenGinecologico();
    daoExamObste = new DAOExamenObstetrico();
    }
    
    /**
     * Metodo utilizado para registrar una consulta
     * @param c Consulta a registrar
     * @param dni dni del paciente que posee ese antecedente ginecologico
     * @param tipoConsulta tipo de consulta de la consulta : 0(Obstetrica), 1(Ginecologica) o 2(Completa)
     * @return true si se registra correctamente, false si no se registra
     */
    public boolean RegistrarConsulta(Consulta c,long dni,int tipoConsulta)
    {
        boolean rtdo = false;
        try{
            conn = conexion.conectarBD();
            conn.setAutoCommit(false);  
            PreparedStatement insertConsulta =  conn.prepareStatement(
                        "INSERT INTO sistemacarla.consulta VALUES (null,str_to_date(?, '%d/%c/%Y'),?,?,?,?,?,?,?)");
            insertConsulta.setString(1, c.getFecha());
            insertConsulta.setString(2, c.getMotivo());
            insertConsulta.setString(3, c.getTratamiento());
            insertConsulta.setString(4, c.getEstudiosComplementarios());
            insertConsulta.setString(5, c.getObservaciones());
            insertConsulta.setString(6, c.getDiagnostico());
            insertConsulta.setString(7, c.getTipoConsulta());
            insertConsulta.setLong(8, dni);
            insertConsulta.executeUpdate();
            
            c.setId((int)getIdUltimaConsulta(conn, dni));
            
            if (tipoConsulta == 0 || tipoConsulta == 2){
                ExamenObstetrico eo = c.getExamenObstetrico();
                daoExamObste.registrarExamObste(eo, c.getId(), conn);
            }
            
            if (tipoConsulta == 1 || tipoConsulta == 2){
                ExamenGinecologico eg = c.getExamenGinecologico();
                daoExamGinec.RegistrarExamGinec(eg, c.getId(), conn);
            }
            
            conn.commit();
            rtdo = true;
            conn.setAutoCommit(true);
            insertConsulta.close();
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
     * Metodo utilizado para obtener el id de la ultima consulta de un paciente
     * @param dni dni del paciente del cual queremos obtener el id de la ultima consulta
     * @return id de la ultima consulta
     */
    public long getIdUltimaConsulta(long dni)
    {
        conn = conexion.conectarBD();
        String consulta = "SELECT MAX(idconsulta) AS idconsulta FROM sistemacarla.consulta WHERE dniPaciente = ?";
        long ultimaConsulta = 0;
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet();
            if(rs.next())
            {
                ultimaConsulta = rs.getLong("idconsulta");
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return ultimaConsulta;
    
    }
    /**
     * Metodo utilizado para obtener el id de la ultima consulta de un paciente embebida en una transaccion
     * @param dni dni del paciente del cual queremos obtener el id de la ultima consulta
     * @return id de la ultima consulta
     */
    public long getIdUltimaConsulta(Connection con, long dni) {
        conn = con;
        String consulta = "SELECT MAX(idconsulta) AS idconsulta FROM sistemacarla.consulta WHERE dniPaciente = ?";
        long ultimaConsulta = 0;
        try {
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet();
            if(rs.next())
            {
                ultimaConsulta = rs.getLong("idconsulta");
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ultimaConsulta;
    
    }
    
    
    /**
     * Metodo utilizado para obtener todas las consultas de un paciente
     * @param dni dni del paciente del cual queremos obtener todas las consultas
     * @return Lista de consultas del paciente
     */
    public LinkedList<Consulta> getAllConsultasTabla(long dni) {
        LinkedList<Consulta> consultas = new LinkedList<>();
        
        String consulta = "SELECT idConsulta,fecha,motivo,diagnostico,tipoConsulta FROM sistemacarla.consulta WHERE dniPaciente = ? ORDER BY fecha DESC, idConsulta DESC";
        
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                c = new Consulta();
                String fecha = rs.getString("fecha");
                String año = fecha.substring(0, 4);
                String mes = fecha.substring(5, 7);
                String dia = fecha.substring(8, 10);
                c.setId(Integer.parseInt(rs.getString("idConsulta")));
                c.setFecha(dia+"/"+mes+"/"+año);
                c.setMotivo(rs.getString("motivo"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setTipoConsulta(rs.getString("tipoConsulta"));
                consultas.add(c);
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return consultas;
    }
    
    /**
     * Metodo utilizado para obtener una consulta completa de un paciente
     * @param id id de la consulta a buscar
     * @param dni dni del paciente del cual queremos obtener la consulta
     * @return Consulta completa
     */
    public Consulta getConsultaCompleta(int id, long dni) {
        
        String consulta = "SELECT * FROM sistemacarla.consulta WHERE dniPaciente = ? AND idConsulta = ?";
        
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.setInt(2, id);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                c = new Consulta();
                String fecha = rs.getString("fecha");
                String año = fecha.substring(0, 4);
                String mes = fecha.substring(5, 7);
                String dia = fecha.substring(8, 10);
                c.setId(rs.getInt("idConsulta"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setEstudiosComplementarios(rs.getString("estudiosComplementarios"));
                c.setObservaciones(rs.getString("observaciones"));
                c.setTratamiento(rs.getString("tratamiento"));
                c.setFecha(dia+"/"+mes+"/"+año);
                c.setMotivo(rs.getString("motivo"));
                c.setDiagnostico(rs.getString("diagnostico"));
                c.setTipoConsulta(rs.getString("tipoConsulta"));
            }
            pst.close();
        }
        catch (SQLException ex) {
        }
        conexion.desconectarBD(conn);
        if(c.getTipoConsulta().compareTo("Completa") == 0 || c.getTipoConsulta().compareTo("Ginecologica") == 0)
            c.setExamenGinecologico(daoExamGinec.getExamenGinec(c.getId()));
        
        if(c.getTipoConsulta().compareTo("Completa") == 0 || c.getTipoConsulta().compareTo("Obstetrica") == 0)
            c.setExamenObstetrico(daoExamObste.getExamenObste(c.getId()));
        return c;
        
    }
    
    /**
     * Metodo utilizado para actualizar una consulta
     * @param consulta consulta modificada para actualizar
     * @return true si se actualiza correctamente, false si no se actualiza
     */
    public boolean actualizarConsulta(Consulta consulta) {
        try {
            c = consulta;
            conn = conexion.conectarBD();
            String cons = "UPDATE sistemacarla.consulta SET "
                    + "motivo = ?,"
                    + "tratamiento = ?,"
                    + "estudiosComplementarios = ?,"
                    + "observaciones = ?,"
                    + "diagnostico = ?,"
                    + "tipoConsulta = ? "
                    + "WHERE idConsulta = ?";
            pst =  conn.prepareStatement(cons);
            pst.setString(1,c.getMotivo());
            pst.setString(2,c.getTratamiento());
            pst.setString(3,c.getEstudiosComplementarios());
            pst.setString(4,c.getObservaciones());
            pst.setString(5,c.getDiagnostico());
            pst.setString(6,c.getTipoConsulta());
            pst.setInt(7,c.getId());
            
            return(((pst.executeUpdate() > 0) ? true : false) && actualizarExamenes(c));
        } catch (SQLException ex) {
            Logger.getLogger(DAOConsulta.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally{
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOConsulta.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }
    }
    
    /**
     * Metodo utilizado para actualizar los examenes de una consulta
     * @param consulta consulta modificada que contiene los examenes a actualizar
     * @return true si se actualizan correctamente, false si no se actualizan
     */
    private boolean actualizarExamenes(Consulta c) {
        
        boolean Resultado1 = true,Resultado2 = true;
        switch(c.getTipoConsulta())
        {
            case "Obstetrica": Resultado2 = daoExamObste.ActualizarExamObste(c.getExamenObstetrico(),c.getId());
                               break;
            
            case "Ginecologica": Resultado1 = daoExamGinec.ActualizarExamGinec(c.getExamenGinecologico(),c.getId());
                                 break;
           
            case "Completa": Resultado1 = daoExamGinec.ActualizarExamGinec(c.getExamenGinecologico(),c.getId());
                             Resultado2 = daoExamObste.ActualizarExamObste(c.getExamenObstetrico(),c.getId());
                             break;
        }
     return (Resultado1 && Resultado2);  
        
    }
}

