/*
 * Clase DAO dedicada a los antecedentes generales
 */
package DAO;

import ClasesBase.Antecedents;
import Utils.DBConstants;
import static Utils.DBConstants.AntecedentsDBColumns.family;
import static Utils.DBConstants.AntecedentsDBColumns.patient;
import static Utils.DBConstants.AntecedentsDBColumns.personal;
import static Utils.DBConstants.AntecedentsDBColumns.pharmacological;
import static Utils.DBConstants.AntecedentsDBColumns.surgical;
import static Utils.DBConstants.AntecedentsDBColumns.toxic;
import Utils.DBConstants.Tables;
import Utils.DBUtils;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOAntecedents extends DAOBasics{

    private Antecedents antecedents;
    
    /**
     * Method used to register the patient antecedents
     * @param antecedents Antecedents
     * @param dni patient dni
     * @return true if registered, false otherwise
     */
    public boolean registerAntecedents(Antecedents antecedents, long dni) {
        try {
            connection = daoConnection.openDBConnection();
            query = DBUtils.getInsertStatementWithValuesOnly(
                    Tables.Antecedents, "null,?, ?, ?, ?, ?");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antecedents.getPersonalAntecedents());
            preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
            preparedStatement.setString(3, antecedents.getToxicAntecedents());
            preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
            preparedStatement.setString(5, antecedents.getFamilyAntecedents());
            preparedStatement.setLong(6, dni);
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            }
            daoConnection.closeDBConnection(connection);
        }
    }
    
    /**
     * Method used to retrieve some patient antecedents
     * @param dni dni of the patient
     * @return Antecedent
     */ 
    public Antecedents getAntecedent(long dni) {
        antecedents = null;
        
        String whereCondition = DBUtils.getSimpleWhereCondition(patient.name());
        query = DBUtils.getSelectAllStatementWithWhere(Tables.Antecedents, whereCondition);
        
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next())
            {   
                antecedents = new Antecedents();
                antecedents.setPersonalAntecedents(resultSet.getString(personal.name()));
                antecedents.setSurgicalAntecedents(resultSet.getString(surgical.name()));
                antecedents.setToxicAntecedents(resultSet.getString(toxic.name()));
                antecedents.setFamilyAntecedents(resultSet.getString(family.name()));
                antecedents.setPharmacologicalAntecedents(resultSet.getString(pharmacological.name()));
            }
            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            daoConnection.closeDBConnection(connection);
        }
        return antecedents;
    }
    
     /**
     * Method used to update a patient antecedents
     * @param antecedents Updated antecedents
     * @param dni patient's dni
     * @return true if updated correctly, false otherwise
     */
    public boolean updateAntecedents(Antecedents antecedents, long dni) {
        try {
            connection = daoConnection.openDBConnection();
            String columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
            personal.name(), surgical.name(), toxic.name(), pharmacological.name(), family.name());
            String whereCondition = DBUtils.getSimpleWhereCondition(patient.name());
            query = DBUtils.getUpdateStatement(Tables.Antecedents, columns, whereCondition);
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antecedents.getPersonalAntecedents());
            preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
            preparedStatement.setString(3, antecedents.getToxicAntecedents());
            preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
            preparedStatement.setString(5, antecedents.getFamilyAntecedents());
            preparedStatement.setLong(6, dni);
            
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            }
            daoConnection.closeDBConnection(connection);
        }
    }
}
