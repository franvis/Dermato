/*
 * Clase DAO dedicada a los antecedentes generales
 */
package DAO;

import ClasesBase.Antecedents;
import ClasesBase.Patient;
import Utils.DBConstants;
import Utils.DBConstants.Tables;
import Utils.DBUtils;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOAntecedents extends DAOBasics {

    public static final String ANTECEDENTS_INSERT = "null,?,?,?,?,?,?,?";
    public static final String PERSONAL = "personal";
    public static final String SURGICAL = "surgical";
    public static final String TOXIC = "toxic";
    public static final String PHARMACOLOGICAL = "pharmacological";
    public static final String FAMILY = "family";
    public static final String PATIENT_DNI = "patientDni";
    public static final String PATIENT_DNI_TYPE = "patientDniType";
    
    private Antecedents antecedents;

    public DAOAntecedents() {
        daoConnection = new DAOConnection();
    }

    /**
     * Method used to register the patient antecedents
     *
     * @param antecedents Antecedents
     * @param dni patient dni
     * @param dniType patient dni type
     * 
     * @return true if registered, false otherwise
     */
    public boolean registerAntecedents(Antecedents antecedents, String dni, int dniType) {
        try {
            connection = daoConnection.openDBConnection();
            query = getInsertStatement();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antecedents.getPersonalAntecedents());
            preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
            preparedStatement.setString(3, antecedents.getToxicAntecedents());
            preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
            preparedStatement.setString(5, antecedents.getFamilyAntecedents());
            preparedStatement.setString(6, dni);
            preparedStatement.setInt(7, dniType);
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

    /**
     * Method used to register the patient antecedents using a transaction
     * connection
     *
     * @param antecedents Antecedents
     * @param dni patient dni
     * @param connection connection
     * @param dniType dni type
     * 
     * @return true if registered, false otherwise
     */
    public boolean registerAntecedents(Antecedents antecedents, String dni, int dniType, Connection connection) {
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
            preparedStatement.setString(6, dni);
            preparedStatement.setInt(7, dniType);
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
    public Antecedents getAntecedent(Patient patient) {
        antecedents = null;

        where = DBUtils.getWhereConditions(
                DBUtils.getSimpleWhereCondition(PATIENT_DNI),
                DBUtils.getSimpleWhereCondition(PATIENT_DNI_TYPE));
        
        query = DBUtils.getSelectAllStatementWithWhere(Tables.Antecedents, where);

        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patient.getDni());
            preparedStatement.setInt(2, patient.getDniType().getId());
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
     * @param antecedents Updated antecedents
     * @param dni patient's dni
     * @return true if updated correctly, false otherwise
     */
    public boolean updateAntecedents(Antecedents antecedents, String dni, int dniType) {
        boolean withConnection = true;
        try {
            if (connection == null || connection.isClosed()) {
                connection = daoConnection.openDBConnection();
                withConnection = false;
            }
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    PERSONAL, SURGICAL, TOXIC, PHARMACOLOGICAL, FAMILY);
            where = DBUtils.getWhereConditions(
                DBUtils.getSimpleWhereCondition(PATIENT_DNI),
                DBUtils.getSimpleWhereCondition(PATIENT_DNI_TYPE));
            
            query = DBUtils.getUpdateStatement(Tables.Antecedents, columns, where);

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antecedents.getPersonalAntecedents());
            preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
            preparedStatement.setString(3, antecedents.getToxicAntecedents());
            preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
            preparedStatement.setString(5, antecedents.getFamilyAntecedents());
            preparedStatement.setString(6, dni);
            preparedStatement.setInt(7, dniType);

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
