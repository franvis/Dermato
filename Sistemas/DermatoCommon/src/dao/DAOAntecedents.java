/*
 * Clase DAO dedicada a los antecedentes generales
 */
package dao;

import bussines.Antecedents;
import bussines.Patient;
import utils.DBConstants;
import utils.DBConstants.Tables;
import utils.DBUtils;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOAntecedents extends DAOBasics {

    public static final String ANTECEDENTS_INSERT = "null,?,?,?,?,?,?";
    public static final String PERSONAL = "personal";
    public static final String SURGICAL = "surgical";
    public static final String TOXIC = "toxic";
    public static final String PHARMACOLOGICAL = "pharmacological";
    public static final String FAMILY = "family";
    public static final String PATIENT_ID = "patient";
    
    private Antecedents antecedents;

    public DAOAntecedents() {
        daoConnection = new DAOConnection();
    }

    /**
     * Method used to register the patient antecedents
     *
     * @param antecedents
     * @param patientId
     * 
     * @return "SUCCESS" if registered successful, error otherwise
     */
    public String registerAntecedents(Antecedents antecedents, int patientId) {
        try {
            connection = daoConnection.openDBConnection();
            query = getInsertStatement();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antecedents.getPersonalAntecedents());
            preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
            preparedStatement.setString(3, antecedents.getToxicAntecedents());
            preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
            preparedStatement.setString(5, antecedents.getFamilyAntecedents());
            preparedStatement.setInt(6, patientId);
            return (preparedStatement.executeUpdate() > 0) ? DB_COMMAND_SUCCESS : dbCommandFailed("executeUpdated registering antecedents returned <= 0");
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            return dbCommandFailed(ex.getMessage() == null ? "SqlException Error code " + ex.getErrorCode() : ex.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            }
            daoConnection.closeDBConnection(connection);
        }
    }

    /**
     * Method used to register the patient antecedents using a transaction
     * connection
     *
     * @param antecedents
     * @param connection active transaction connection
     * @param patientId
     * 
     * @return "SUCCESS" if registered successful, error otherwise
     */
    public String registerAntecedents(Antecedents antecedents, int patientId, Connection connection) {
        boolean isTransaction = connection != null;
        try {
            if (!isTransaction) {
                connection = daoConnection.openDBConnection();
            }
            query = getInsertStatement();
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antecedents.getPersonalAntecedents());
            preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
            preparedStatement.setString(3, antecedents.getToxicAntecedents());
            preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
            preparedStatement.setString(5, antecedents.getFamilyAntecedents());
            preparedStatement.setInt(6, patientId);
            return (preparedStatement.executeUpdate() > 0) ? DB_COMMAND_SUCCESS : dbCommandFailed("executeUpdated registering antecedents returned <= 0");
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            return dbCommandFailed(ex.getMessage() == null ? "SqlException Error code " + ex.getErrorCode() : ex.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!isTransaction) {
                daoConnection.closeDBConnection(connection);
            }
        }
    }

    /**
     * Method used to retrieve some patient antecedents
     *
     * @param patient patient
     * @return Antecedent
     */
    public Antecedents getAntecedents(Patient patient) {
        antecedents = null;
        where = DBUtils.getSimpleWhereCondition(PATIENT_ID);
        query = DBUtils.getSelectAllStatementWithWhere(Tables.Antecedents, where);
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patient.getPatientId());
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                antecedents = new Antecedents();
                antecedents.setPersonalAntecedents(resultSet.getString(PERSONAL));
                antecedents.setSurgicalAntecedents(resultSet.getString(SURGICAL));
                antecedents.setToxicAntecedents(resultSet.getString(TOXIC));
                antecedents.setFamilyAntecedents(resultSet.getString(FAMILY));
                antecedents.setPharmacologicalAntecedents(resultSet.getString(PHARMACOLOGICAL));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        System.out.println(query);
        return antecedents;
    }

    /**
     * Method used to update a patient antecedents
     *
     * @param antecedents
     * @param patientId
     * @return "SUCCESS" if updated successful, error otherwise
     */
    public String updateAntecedents(Antecedents antecedents, int patientId) {
        boolean withConnection = true;
        try {
            if (connection == null || connection.isClosed()) {
                connection = daoConnection.openDBConnection();
                withConnection = false;
            }
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    PERSONAL, SURGICAL, TOXIC, PHARMACOLOGICAL, FAMILY);
            where = DBUtils.getSimpleWhereCondition(PATIENT_ID);
            query = DBUtils.getUpdateStatement(Tables.Antecedents, columns, where);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antecedents.getPersonalAntecedents());
            preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
            preparedStatement.setString(3, antecedents.getToxicAntecedents());
            preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
            preparedStatement.setString(5, antecedents.getFamilyAntecedents());
            preparedStatement.setInt(6, patientId);

            return (preparedStatement.executeUpdate() > 0) ? DB_COMMAND_SUCCESS : dbCommandFailed("executeUpdated updating antecedents returned <= 0");
        } catch (SQLException ex) {
            Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            return dbCommandFailed(ex.getMessage() == null ? "SqlException Error code " + ex.getErrorCode() : ex.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOAntecedents.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!withConnection) {
                daoConnection.closeDBConnection(connection);
            }
        }
    }

    public DAOAntecedents withConnection(Connection connection) {
        this.connection = connection;
        return this;
    }
    
    @Override
    String getInsertStatement() {
        return String.format(DBConstants.INSERT_WITH_VALUES_ONLY,
                        DBConstants.Tables.Antecedents.name(), ANTECEDENTS_INSERT);
    }
}
