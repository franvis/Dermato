/*
 *Clase DAO dedicada al paciente;
 */
package DAO;

import ClasesBase.Patient;
import ClasesBase.PrePaidHealthInsurance;
import Utils.DBConstants;
import static Utils.DBConstants.LEFT_JOIN;
import Utils.DBConstants.PatientDBColumns;
import static Utils.DBConstants.PatientDBColumns.*;
import Utils.DBConstants.Tables;
import Utils.DBConstants.VisitDBColumns;
import Utils.DBUtils;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOPatient extends DAOBasics {

    private final DAOPrepaidHealthInsurance daoPrepaidHealthInsurance;
    private final DAOAntecedents daoAntecedents;
    private LinkedList<Patient> pacientes;
    private Patient patient;

    public DAOPatient() {
        daoPrepaidHealthInsurance = new DAOPrepaidHealthInsurance();
        daoAntecedents = new DAOAntecedents();
        daoConnection = new DAOConnection();
    }

    /**
     * Method used to register a patient
     *
     * @param patient Patient to register
     * @return true if registered successful, false otherwise
     */
    public boolean registerPatient(Patient patient) {
        try {
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);
            query = DBUtils.getInsertStatementWithValuesOnly(Tables.Patient);
            preparedStatement = connection.prepareStatement(query);
            if (patient.getPrepaidHealthInsurance().getId() != 0) {
                preparedStatement.setInt(8, patient.getPrepaidHealthInsurance().getId());
                preparedStatement.setString(9, patient.getPrepaidHealthInsuranceNumber());
            } else {
                preparedStatement.setNull(8, java.sql.Types.INTEGER);
                preparedStatement.setString(9, "");
            }
            preparedStatement.setLong(1, patient.getDni());
            preparedStatement.setString(2, patient.getName());
            preparedStatement.setString(3, patient.getLastname());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.setString(5, patient.getAddress());
            preparedStatement.setString(6, patient.getCity());
            preparedStatement.setString(7, patient.getBirthday());
            preparedStatement.setString(10, patient.getFirstVisitDate());
            preparedStatement.executeUpdate();
            registerAntecedents(patient);
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrepaidHealthInsurance.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("query: "+query);
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOPatient.class.getName()).log(Level.SEVERE, null, ex);
            }
            daoConnection.closeDBConnection(connection);
        }
        return true;
    }

    /**
     * Method used to register the patient antecedents
     *
     * @param patient Patient with antecedents
     * @return true if registered correctly, false otherwise
     */
    private boolean registerAntecedents(Patient patient) {
        return (daoAntecedents.registerAntecedents(patient.getAntecendents(),
                patient.getDni(), connection));
    }

    /**
     * Method used to retrieve all the patients according to the filters
     *
     * @param filterName
     * @param filterLastName
     * @param filterDni
     * @return List containing all the patients according to the filters
     */
    public LinkedList<Patient> getAllPatients(String filterName, String filterLastName, String filterDni) {
        pacientes = new LinkedList<>();

        String lastVisitDateColumn = "lastVisitDate";

        from = DBUtils.getTableJoin(LEFT_JOIN, Tables.Patient,
                Tables.Visit, dni.name(), VisitDBColumns.patient.name());

        columns = DBUtils.getStringWithValuesSeparatedWithCommas(dni.name(),
                name.name(), lastname.name(), birthday.name(), DBUtils
                .getMaxColumnAs(VisitDBColumns.date.name(), lastVisitDateColumn));

        where = DBUtils.getWhereForFilters(filterName, filterLastName, filterDni);
        orderBy = DBUtils.getOrderByForFilters(filterName, filterLastName, filterDni);
        groupBy = DBUtils.getStringWithValuesSeparatedWithCommas(dni.name(), name.name(),
                lastname.name(), birthday.name());

        if (!where.isEmpty()) {
            query = DBUtils.getSelectColumnsMultipleTablesStatementWithWhereGroupByAndOrder(
                    columns, from, where, groupBy, orderBy);
        } else {
            return pacientes;
        }

        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            int cant = 0;
            if (!filterDni.isEmpty()) {
                cant++;
                preparedStatement.setString(cant, filterDni + '%');
            }
            if (!filterLastName.isEmpty()) {
                cant++;
                preparedStatement.setString(cant, filterLastName + '%');
            }
            if (!filterName.isEmpty()) {
                cant++;
                preparedStatement.setString(cant, filterName + '%');
            }
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                patient = new Patient();
                patient.setDni(resultSet.getLong(dni.name()));
                if (patient.getDni() == 0) {
                    break;
                }
                patient.setName(resultSet.getString(name.name()));
                patient.setLastname(resultSet.getString(lastname.name()));
                patient.setBirthday(DBUtils.getFormattedDate(resultSet.getString(birthday.name())));
                String lastVisitDate = "Sin consultas";
                if (resultSet.getObject(lastVisitDateColumn) != null) {
                    lastVisitDate = DBUtils.getFormattedDate(resultSet.
                            getString(lastVisitDateColumn));
                }
                patient.setLastVisitDate(lastVisitDate);
                pacientes.add(patient);
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(query);
        daoConnection.closeDBConnection(connection);
        return pacientes;
    }

    /**
     * Method used to verify the existence of a patient
     *
     * @param dni patient's dni
     * @return true if exists, false otherwise
     */
    public boolean verifyPatient(long dni) {
        boolean exists = false;
        connection = daoConnection.openDBConnection();
        query = DBUtils.getSelectOneStatementWithWhere(Tables.Patient,
                DBUtils.getSimpleWhereCondition(PatientDBColumns.dni.name()));
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            if (preparedStatement.getResultSet().next()) {
                exists = true;
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return exists;
    }

    /**
     * Method used to retrieve all the basic data for a patient
     *
     * @param dni
     * @return Patient containing dni, name, last name, birthday and last visit
     * date
     */
    public Patient getBasicPatient(long dni) {
        patient = null;
        String lastVisitDateColumn = "lastVisitDate";

        from = DBUtils.getTableJoin(LEFT_JOIN, Tables.Patient,
                Tables.Visit, PatientDBColumns.dni.name(), VisitDBColumns.patient.name());

        columns = DBUtils.getStringWithValuesSeparatedWithCommas(PatientDBColumns.dni.name(),
                name.name(), lastname.name(), birthday.name(), DBUtils
                .getMaxColumnAs(VisitDBColumns.date.name(), lastVisitDateColumn));

        query = DBUtils.getSelectColumnsMultipleTablesStatementWithWhere(columns, from,
                DBUtils.getSimpleWhereCondition(PatientDBColumns.dni.name()));
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                patient = new Patient();
                patient.setDni(dni);
                patient.setName(resultSet.getString("nombre"));
                patient.setLastname(resultSet.getString("apellido"));
                patient.setBirthday(DBUtils.getFormattedDate(resultSet.getString(birthday.name())));
                String lastVisitDate = "Sin consultas";
                if (resultSet.getObject(lastVisitDateColumn) != null) {
                    lastVisitDate = DBUtils.getFormattedDate(resultSet.
                            getString(lastVisitDateColumn));
                }
                patient.setLastVisitDate(lastVisitDate);
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return patient;
    }

    /**
     * Method used to retrieve a full data patient
     *
     * @param dni
     * @return Required full data patient
     */
    public Patient getFullPatient(long dni) {
        patient = null;
        String lastVisitDateColumn = "lastVisitDate";

        from = DBUtils.getTableJoin(LEFT_JOIN, Tables.Patient,
                Tables.Visit, PatientDBColumns.dni.name(), VisitDBColumns.patient.name());

        columns = DBUtils.getStringWithValuesSeparatedWithCommas(PatientDBColumns.dni.name(),
                name.name(), lastname.name(), birthday.name(), DBUtils
                .getMaxColumnAs(VisitDBColumns.date.name(), lastVisitDateColumn));

        query = DBUtils.getSelectAllMultipleTablesStatementWithWhere(columns, from,
                DBUtils.getSimpleWhereCondition(PatientDBColumns.dni.name()));
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                patient = new Patient();
                patient.setDni(dni);
                patient.setName(resultSet.getString(name.name()));
                patient.setLastname(resultSet.getString(lastname.name()));
                patient.setAddress(resultSet.getString(address.name()));
                patient.setCity(resultSet.getString(city.name()));
                patient.setFirstVisitDate(resultSet.getString(firstVisitDate.name()));
                patient.setBirthday(DBUtils.getFormattedDate(resultSet.getString(birthday.name())));
                patient.setPrepaidHealthInsuranceNumber(resultSet.getString(prePaidHealthInsuranceNumber.name()));
                if (resultSet.getObject(prepaidHealthInsurance.name()) != null && resultSet.getInt(prepaidHealthInsurance.name()) != 0) {
                    patient.setPrepaidHealthInsurance(daoPrepaidHealthInsurance.getPPHealthInsurance(resultSet.getInt(prepaidHealthInsurance.name())));
                } else {
                    patient.setPrepaidHealthInsurance(new PrePaidHealthInsurance(0, "Sin Obra Social"));
                }
                patient.setPhone(resultSet.getString(phone.name()));
                String lastVisitDate = "Sin consultas";
                if (resultSet.getObject(lastVisitDateColumn) != null) {
                    lastVisitDate = DBUtils.getFormattedDate(resultSet.
                            getString(lastVisitDateColumn));
                }
                patient.setLastVisitDate(lastVisitDate);
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }

        patient.setAntecendents(daoAntecedents.getAntecedent(patient.getDni()));

        return patient;
    }

    /**
     * Method used to update a patient
     *
     * @param dniAnterior Patient's dni before modifying it
     * @param p Updated patient
     * @return true if updated correctly, false otherwise
     */
    public boolean updatePatient(Patient p, long dniAnterior) {
        try {
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    dni.name(), name.name(), lastname.name(), phone.name(),
                    prepaidHealthInsurance.name(), prePaidHealthInsuranceNumber.name(),
                    city.name(), address.name());
            columns = DBUtils.getStringWithValuesSeparatedWithCommas(columns,
                    String.format(DBConstants.STR_TO_DATE_UPDATE_COLUMN, birthday.name()));
            query = DBUtils.getUpdateStatement(Tables.Patient, columns, DBUtils.getSimpleWhereCondition(dni.name()));
            preparedStatement = connection.prepareStatement(query);
            if (p.getPrepaidHealthInsurance().getId() != 0) {
                preparedStatement.setInt(5, p.getPrepaidHealthInsurance().getId());
                preparedStatement.setString(6, p.getPrepaidHealthInsuranceNumber());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
                preparedStatement.setNull(6, Types.VARCHAR);
            }
            preparedStatement.setLong(10, dniAnterior);
            preparedStatement.setLong(1, p.getDni());
            preparedStatement.setString(2, p.getName());
            preparedStatement.setString(3, p.getLastname());
            preparedStatement.setString(4, p.getPhone());
            preparedStatement.setString(7, p.getCity());
            preparedStatement.setString(8, p.getAddress());
            preparedStatement.setString(9, p.getBirthday());
            preparedStatement.executeUpdate();
            daoAntecedents.withConnection(connection)
                    .updateAntecedents(p.getAntecendents(), p.getDni());
            preparedStatement.close();
            connection.commit();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return true;
    }

    /**
     * Method used to delete a patient
     *
     * @param dni dni of the patient intended to delete
     * @return true if deleted correctly, false otherwise
     */
    public boolean deletePatient(long dni) {
        try {
            connection = daoConnection.openDBConnection();
            query = DBUtils.getDeleteStatement(Tables.Patient, 
                    DBUtils.getSimpleWhereCondition(PatientDBColumns.dni.name()));
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrepaidHealthInsurance.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            daoConnection.closeDBConnection(connection);
        }
    }

    /**
     * Allows to verify that duplicated patients don't exist for a certain Pre Paid Health 
     * Insurance with a certain Insurance Number
     *
     * @param prePaidHealthInsuranceId pre paid health insurance id
     * @param insuranceNumber patient's insurance number
     * @return the only patient that exists for the pre paid health insurance and with that insurance number
     * null if no match were found
     */
    public Patient verificarNroAfiliado(int prePaidHealthInsuranceId, String insuranceNumber) {
        Patient match = null;
        try {
            connection = daoConnection.openDBConnection();
            columns = DBUtils.getStringWithValuesSeparatedWithCommas(dni.name(), name.name(), lastname.name());
            where = DBUtils.getWhereConditions(DBUtils.getSimpleWhereCondition(
                    prepaidHealthInsurance.name()), DBUtils.getSimpleWhereCondition(prePaidHealthInsuranceNumber.name()));
            query = DBUtils.getSelectColumnsStatementWithWhere(Tables.Patient, columns, where);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, prePaidHealthInsuranceId);
            preparedStatement.setString(2, insuranceNumber);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                match = new Patient();
                match.setDni(resultSet.getLong(dni.name()));
                match.setName(resultSet.getString(name.name()));
                match.setLastname(resultSet.getString(lastname.name()));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return match;
    }
}
