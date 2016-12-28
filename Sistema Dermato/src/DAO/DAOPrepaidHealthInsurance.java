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
import static Utils.DBConstants.PrePaidHealthInsuranceDBColumns.idPrePaidHealthInsurance;
import static Utils.DBUtils.getSimpleWhereCondition;
import static Utils.DBUtils.getWhereConditions;

/**
 *
 * @author Fran
 */
public class DAOPrepaidHealthInsurance extends DAOBasics{

    private PrePaidHealthInsurance prePaidHealthInsurance;
    private LinkedList<PrePaidHealthInsurance> prePaidHealthInsurances;

    /**
     * Method used to get all the pre paid health insurances
     * @return All the pre paid health insurances
     */
    public LinkedList<PrePaidHealthInsurance> getAllPrePaidHealthInsurances()
    {
        prePaidHealthInsurances = new LinkedList<>();
        connection = daoConnection.openDBConnection();
        query = DBUtils.getSelectAllStatementWithOrder(Tables.PrePaidHealthInsurance,
                DBUtils.getOrderByCondition(name.name(), true));
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery(query);
            resultSet = preparedStatement.getResultSet();
            while(resultSet.next())
            {
                prePaidHealthInsurance = new PrePaidHealthInsurance();
                prePaidHealthInsurance.setId(resultSet.getInt(idPrePaidHealthInsurance.name()));
                prePaidHealthInsurance.setName(resultSet.getString(name.name()));
                prePaidHealthInsurances.add(prePaidHealthInsurance);
            }
            preparedStatement.close();
        }
        catch (SQLException ex) {
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
            String cons = "INSERT INTO sistemaCarla.ObraSocial VALUES (null,?)";
            preparedStatement = connection.prepareStatement(cons);
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
     * Method used to update a Prepaid health insurance
     * @param prepaidHealthInsurance  Prepaid Health Insurance to update
     * @return true if updated correctly, false otherwise
     */
    public boolean updatePrepaidHealthInsurance(PrePaidHealthInsurance prepaidHealthInsurance) {
        try {
            connection = daoConnection.openDBConnection();
            
            String columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    name.name());
            
            String whereCondition = DBUtils.getSimpleWhereCondition(idPrePaidHealthInsurance.name());
            
            query = DBUtils.getUpdateStatement(Tables.PrePaidHealthInsurance, columns, whereCondition);
            
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
            
            String whereCondition = getWhereConditions(
                getSimpleWhereCondition(idPrePaidHealthInsurance.name()));
            
            query = DBUtils.getDeleteStatement(Tables.PrePaidHealthInsurance, whereCondition);
            
            PreparedStatement deleteObraSocial =  connection.prepareStatement(
			query);
            deleteObraSocial.setInt(1, prePaidHealthInsurance.getId());
            deleteObraSocial.executeUpdate();
            
            String columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    prePaidHealthInsuranceNumber.name());
            
            whereCondition = DBUtils.getIsNullWhereCondition(idPrePaidHealthInsurance.name());
            
            query = DBUtils.getUpdateStatement(Tables.PrePaidHealthInsurance, columns, whereCondition);
            
            PreparedStatement updatePacientes = connection.prepareStatement(
                        query);
            updatePacientes.executeUpdate();
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
    public PrePaidHealthInsurance getObraSocial(int idPrePaidHealthInsurance) {
        String whereCondition = getWhereConditions(getSimpleWhereCondition(PrePaidHealthInsuranceDBColumns.idPrePaidHealthInsurance.name()));
        
        query = DBUtils.getSelectAllStatement(Tables.PrePaidHealthInsurance, 
                 whereCondition);
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
                        PrePaidHealthInsuranceDBColumns.idPrePaidHealthInsurance.name()));
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
