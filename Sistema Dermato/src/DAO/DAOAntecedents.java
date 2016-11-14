/*
 * Clase DAO dedicada a los antecedentes generales
 */
package DAO;

import ClasesBase.Antecedents;
import Utils.DBUtils;
import Utils.DBUtils.Tables;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOAntecedents {
    
    private final DAOConnection daoConnection;
    private ResultSet resultSet;
    private Connection connection;
    private Antecedents antecedents;
    private PreparedStatement pst;

    public DAOAntecedents() {
        daoConnection = new DAOConnection();
    }
    
    
    /**
     * Method used to register the patient antecedents
     * @param antecedents Antecedents
     * @param dni patient dni
     * @return true if registered, false otherwise
     */
    public boolean registerAntecedents(Antecedents antecedents,long dni) {
        try {
            connection = daoConnection.openDBConnection();
            String cons = DBUtils.getInsertStatementWithValuesOnly(
                    Tables.Antecedents, "null,?, ?, ?, ?, ?");
            pst = connection.prepareStatement(cons);
            pst.setString(1, antecedents.getPersonalAntecedents());
            pst.setString(2, antecedents.getSurgicalAntecedents());
            pst.setString(3, antecedents.getToxicAntecedents());
            pst.setString(4, antecedents.getPharmacologicalAntecedents());
            pst.setString(5, antecedents.getFamilyAntecedents());
            pst.setLong(6, dni);
            return (pst.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            }
            daoConnection.closeDBConnection(connection);
        }
    }
    
//    /**
//     * Method used to retrieve some patient antecedents
//     * @param dni dni of the patient
//     * @return Antecedent
//     */ 
//    public Antecedents getAntecedent(long dni) {
//        antecedents = null;
//        String consulta = "SELECT * FROM sistemaCarla.AntecedentesGenerales WHERE dniPaciente = ?";
//        try {
//            connection = daoConnection.openDBConnection();
//            pst = connection.prepareStatement(consulta);
//            pst.setLong(1, dni);
//            pst.executeQuery();
//            resultSet = pst.getResultSet();
//            while(resultSet.next())
//            {   
//                antecedents = new Antecedents();
//                antecedents.setAntecedentesPersonales(resultSet.getString("antecedentesP"));
//                antecedents.setAntecedentesQuirurgicos(resultSet.getString("antecedentesQ"));
//                antecedents.setAntecedentesToxicos(resultSet.getString("antecedentesT"));
//            }
//            pst.close();
//        }
//        catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        finally {
//            daoConnection.closeDBConnection(connection);
//        }
//        return antecedents;
//    }
//    
//     /**
//     * Metodo utilizado para actualizar un antecedente general
//     * @param agen Antecedente General modificado para actualizar
//     * @param dni dni del paciente que posee ese antecedente familiar
//     * @return true si se actualiza correctamente, false si no se actualiza
//     */
//    public boolean actualizarAntecedente(Antecedents agen,long dni) {
//        try {
//            connection = daoConnection.openDBConnection();
//            String cons = "UPDATE sistemaCarla.AntecedentesGenerales SET "
//                    + "antecedentesP = ?,"
//                    + "antecedentesQ = ?,"
//                    + "antecedentesT = ? "
//                    + "WHERE dniPaciente = ?";
//            pst = connection.prepareStatement(cons);
//            pst.setString(1, agen.getAntecedentesPersonales());
//            pst.setString(2, agen.getAntecedentesQuirurgicos());
//            pst.setString(3, agen.getAntecedentesToxicos());
//            pst.setLong(4, dni);
//            return (pst.executeUpdate() > 0) ? true : false;
//        } catch (SQLException ex) {
//            Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        } finally {
//            try {
//                pst.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            daoConnection.closeDBConnection(connection);
//        }
//    }
}
