/*
 * Clase DAO dedicada a las obras sociales;
 */
package DAO;

import ClasesBase.MedicalCoverage;
import static ClasesBase.MedicalCoverage.NO_MEDICAL_COBERTURE_NAME;
import Utils.DBConstants;
import static Utils.DBConstants.PatientDBColumns.medicalCoverageNumber;
import Utils.DBConstants.MedicalCoverageDBColumns;
import static Utils.DBConstants.MedicalCoverageDBColumns.name;
import Utils.DBConstants.Tables;
import Utils.DBUtils;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Utils.DBUtils.getSimpleWhereCondition;
import static Utils.DBUtils.getWhereConditions;
import java.util.HashMap;
import static Utils.DBConstants.MedicalCoverageDBColumns.idMedicalCoverage;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Enum.name;

/**
 *
 * @author Fran
 */
public class DAOMedicalCoverage extends DAOBasics{

    private MedicalCoverage medicalCoverage;
    private ArrayList<MedicalCoverage> medicalCoverages;

    public DAOMedicalCoverage(){
        daoConnection = new DAOConnection();
    }
    
    /**
     * Method used to get all the pre paid health insurances
     * @return All the pre paid health insurances
     */
    public List<MedicalCoverage> getAllMedicalCoverages()
    {
        medicalCoverages = new ArrayList<>();
        medicalCoverages.add(new MedicalCoverage(0, NO_MEDICAL_COBERTURE_NAME));
        connection = daoConnection.openDBConnection();
        query = DBUtils.getSelectAllStatementWithOrder(Tables.MedicalCoverage,
                DBUtils.getOrderByCondition(name.name(), true));
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery(query);
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next())
            {
                medicalCoverage = new MedicalCoverage();
                int id = resultSet.getInt(idMedicalCoverage.name());
                medicalCoverage.setId(id);
                medicalCoverage.setName(resultSet.getString(name.name()));
                medicalCoverages.add(medicalCoverage);
            }
            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.out.println("query: " + query);
        }
        daoConnection.closeDBConnection(connection);
        return medicalCoverages;
    }
    
    /**
     * Method used to register a pre paid health insurance
     *
     * @param medicalCoverage pre paid health insurance to register
     * @return true if registered correctly, false otherwise
     */
    public boolean registerMedicalCoverage(MedicalCoverage medicalCoverage) {
        try {
            connection = daoConnection.openDBConnection();
            query = DBUtils.getInsertStatementWithValuesOnly(Tables.MedicalCoverage);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, medicalCoverage.getName());
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMedicalCoverage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            daoConnection.closeDBConnection(connection);
        }
    }

     /**
     * Method used to update a Pre paid health insurance
     * @param prepaidHealthInsurance  Pre paid Health Insurance to update
     * @return true if updated correctly, false otherwise
     */
    public boolean updateMedicalCoverage(MedicalCoverage prepaidHealthInsurance) {
        try {
            connection = daoConnection.openDBConnection();
            
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    name.name());
            
            where = DBUtils.getSimpleWhereCondition(idMedicalCoverage.name());
            
            query = DBUtils.getUpdateStatement(Tables.MedicalCoverage, columns, where);
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prepaidHealthInsurance.getName());
            preparedStatement.setInt(2, prepaidHealthInsurance.getId());
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOMedicalCoverage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            daoConnection.closeDBConnection(connection);
        }
    }
    
    /**
     * Method used to delete a Pre paid health insurance
     * @param medicalCoverage 
     * @return true if deleted, false otherwise
     */
    public boolean deleteMedicalCoverage(MedicalCoverage medicalCoverage)
    {
        try {
            connection = daoConnection.openDBConnection();
            
            where = getWhereConditions(getSimpleWhereCondition(idMedicalCoverage.name()));
            
            query = DBUtils.getDeleteStatement(Tables.MedicalCoverage, where);
            
            preparedStatement =  connection.prepareStatement(
			query);
            preparedStatement.setInt(1, medicalCoverage.getId());
            preparedStatement.executeUpdate();
            
            return true;
        }
        
        catch (Exception ex) {
            try {
                connection.rollback(); 
            }
            catch (SQLException e) {
                System.out.println("RollBack Failure." + e.getMessage());
            }
            System.out.println(ex.getMessage());
            return false;
        }
        finally {
            daoConnection.closeDBConnection(connection);
        }
    }
    
    /**
    * Method used to retrieve a Pre paid Health Insurance
    * @param idMedicalCoverage id of the pre paid health insurance
    * @return Pre paid health insurance
    */
    public MedicalCoverage getPPHealthInsurance(int idMedicalCoverage) {
        where = getWhereConditions(getSimpleWhereCondition(MedicalCoverageDBColumns.idMedicalCoverage.name()));
        
        query = DBUtils.getSelectAllStatementWithWhere(Tables.MedicalCoverage, 
                 where);
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,idMedicalCoverage);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next())
            {
                medicalCoverage = new MedicalCoverage();
                medicalCoverage.setId(resultSet.getInt(DBConstants.
                        MedicalCoverageDBColumns.idMedicalCoverage.name()));
                medicalCoverage.setName(resultSet.getString(name.name()));
            }
            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            daoConnection.closeDBConnection(connection);
        }
        return medicalCoverage;
    }
}
