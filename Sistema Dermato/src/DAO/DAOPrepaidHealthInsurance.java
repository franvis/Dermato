/*
 * Clase DAO dedicada a las obras sociales;
 */
package DAO;

import ClasesBase.PrePaidHealthInsurance;
import Utils.DBConstants;
import static Utils.DBConstants.PatientDBColumns.prePaidHealthInsuranceNumber;
import Utils.DBConstants.PrePaidHealthInsuranceDBColumns;
import static Utils.DBConstants.PrePaidHealthInsuranceDBColumns.name;
import Utils.DBConstants.Tables;
import Utils.DBUtils;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Utils.DBUtils.getSimpleWhereCondition;
import static Utils.DBUtils.getWhereConditions;
import static Utils.DBConstants.PrePaidHealthInsuranceDBColumns.idPrepaidHealthInsurance;


/**
 *
 * @author Fran
 */
public class DAOPrepaidHealthInsurance extends DAOBasics{

    private PrePaidHealthInsurance prePaidHealthInsurance;
    private LinkedList<PrePaidHealthInsurance> prePaidHealthInsurances;

    public DAOPrepaidHealthInsurance(){
        daoConnection = new DAOConnection();
    }
    
    /**
     * Method used to get all the pre paid health insurances
     * @return All the pre paid health insurances
     */
    public LinkedList<PrePaidHealthInsurance> getAllPrePaidHealthInsurances()
    {
        prePaidHealthInsurances = new LinkedList<>();
        connection = daoConnection.openDBConnection();
        query = DBUtils.getSelectAllStatementWithOrder(Tables.PrepaidHealthInsurance,
                DBUtils.getOrderByCondition(name.name(), true));
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery(query);
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next())
            {
                prePaidHealthInsurance = new PrePaidHealthInsurance();
                prePaidHealthInsurance.setId(resultSet.getInt(idPrepaidHealthInsurance.name()));
                prePaidHealthInsurance.setName(resultSet.getString(name.name()));
                prePaidHealthInsurances.add(prePaidHealthInsurance);
            }
            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.out.println("query: " + query);
        }
        daoConnection.closeDBConnection(connection);
        return prePaidHealthInsurances;
    }
    
    /**
     * Method used to register a pre paid health insurance
     *
     * @param prePaidHealthInsurance pre paid health insurance to register
     * @return true if registered correctly, false otherwise
     */
    public boolean registerPrePaidHealthInsurance(PrePaidHealthInsurance prePaidHealthInsurance) {
        try {
            connection = daoConnection.openDBConnection();
            query = DBUtils.getInsertStatementWithValuesOnly(Tables.PrepaidHealthInsurance);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prePaidHealthInsurance.getName());
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrepaidHealthInsurance.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean updatePrepaidHealthInsurance(PrePaidHealthInsurance prepaidHealthInsurance) {
        try {
            connection = daoConnection.openDBConnection();
            
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    name.name());
            
            where = DBUtils.getSimpleWhereCondition(idPrepaidHealthInsurance.name());
            
            query = DBUtils.getUpdateStatement(Tables.PrepaidHealthInsurance, columns, where);
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prepaidHealthInsurance.getName());
            preparedStatement.setInt(2, prepaidHealthInsurance.getId());
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrepaidHealthInsurance.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            daoConnection.closeDBConnection(connection);
        }
    }
    
    /**
     * Method used to delete a Pre paid health insurance
     * @param prePaidHealthInsurance 
     * @return true if deleted, false otherwise
     */
    public boolean deletePrePaidHealthInsurance(PrePaidHealthInsurance prePaidHealthInsurance)
    {
        boolean rtdo = false;
        try {
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);
            
            where = getWhereConditions(getSimpleWhereCondition(idPrepaidHealthInsurance.name()));
            
            query = DBUtils.getDeleteStatement(Tables.PrepaidHealthInsurance, where);
            
            preparedStatement =  connection.prepareStatement(
			query);
            preparedStatement.setInt(1, prePaidHealthInsurance.getId());
            preparedStatement.executeUpdate();
            
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    prePaidHealthInsuranceNumber.name());
            
            where = DBUtils.getIsNullWhereCondition(idPrepaidHealthInsurance.name());
            
            query = DBUtils.getUpdateStatement(Tables.PrepaidHealthInsurance, columns, where);
            
            preparedStatement = connection.prepareStatement(
                        query);
            preparedStatement.executeUpdate();
            connection.commit();
            rtdo = true;
            connection.setAutoCommit(true);
        }
        
        catch (Exception ex) {
            try {
                connection.rollback(); 
            }
            catch (SQLException e) {
                System.out.println("RollBack Failure." + e.getMessage());
            }
            System.out.println(ex.getMessage());
        }
        finally {
            daoConnection.closeDBConnection(connection);
        }
        return rtdo;
    }
    
    /**
    * Method used to retrieve a Pre paid Health Insurance
    * @param idPrePaidHealthInsurance id of the pre paid health insurance
    * @return Pre paid health insurance
    */
    public PrePaidHealthInsurance getPPHealthInsurance(int idPrePaidHealthInsurance) {
        where = getWhereConditions(getSimpleWhereCondition(PrePaidHealthInsuranceDBColumns.idPrepaidHealthInsurance.name()));
        
        query = DBUtils.getSelectAllStatement(Tables.PrepaidHealthInsurance, 
                 where);
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,idPrePaidHealthInsurance);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next())
            {
                prePaidHealthInsurance = new PrePaidHealthInsurance();
                prePaidHealthInsurance.setId(resultSet.getInt(DBConstants.
                        PrePaidHealthInsuranceDBColumns.idPrepaidHealthInsurance.name()));
                prePaidHealthInsurance.setName(resultSet.getString(name.name()));
            }
            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            daoConnection.closeDBConnection(connection);
        }
        return prePaidHealthInsurance;
    }
}
