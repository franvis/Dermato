/*
 *Clase DAO dedicada al paciente;
 */
package DAO;

import ClasesBase.Antecedents;
import ClasesBase.Patient;
import ClasesBase.MedicalCoverage;
import Utils.DBConstants;
import Utils.DBConstants.AntecedentsDBColumns;
import static Utils.DBConstants.AntecedentsDBColumns.family;
import static Utils.DBConstants.AntecedentsDBColumns.personal;
import static Utils.DBConstants.AntecedentsDBColumns.pharmacological;
import static Utils.DBConstants.AntecedentsDBColumns.surgical;
import static Utils.DBConstants.AntecedentsDBColumns.toxic;
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

    private static final String LAST_VISIT_DATE_KEY = "lastVisitDate";
    private static final String LAST_VISIT_DATE_DEFAULT_VALUE = "Sin consultas";
    private static final String PP_HEALTH_INSURANCE_NAME_DEFAULT_VALUE = "Sin Obra Social";

    private final DAOMedicalCoverage daoPrepaidHealthInsurance;
    private final DAOAntecedents daoAntecedents;
    private LinkedList<Patient> pacientes;
    private Patient patient;

    public DAOPatient() {
        daoPrepaidHealthInsurance = new DAOMedicalCoverage();
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
            boolean hasPPHealthInsurance = patient.getMedicalCoverage().getId() != 0;
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);
            query = DBUtils.getInsertStatementWithValuesOnly(Tables.Patient);
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (hasPPHealthInsurance) {
                preparedStatement.setInt(8, patient.getMedicalCoverage().getId());
                preparedStatement.setString(9, patient.getMedicalCoverageNumber());
            } else {
                preparedStatement.setNull(8, java.sql.Types.INTEGER);
                preparedStatement.setNull(9, java.sql.Types.VARCHAR);
            }
            preparedStatement.setLong(1, patient.getDni());
            preparedStatement.setString(2, patient.getName());
            preparedStatement.setString(3, patient.getLastname());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.setString(5, patient.getAddress());
            preparedStatement.setString(6, patient.getCity());
            preparedStatement.setString(7, patient.getBirthday());
            preparedStatement.setString(10, patient.getFirstVisitDate());
            preparedStatement.setNull(11, java.sql.Types.VARCHAR);
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    registerAntecedents(patient, generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DAOMedicalCoverage.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("query: " + query);
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
    private boolean registerAntecedents(Patient patient, long patientId) {
        return (daoAntecedents.registerAntecedents(patient.getAntecendents(),
                patientId, connection));
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

        from = DBUtils.getTableJoin(LEFT_JOIN, Tables.Patient,
                Tables.Visit, dni.name(), VisitDBColumns.patient.name());

        columns = DBUtils.getStringWithValuesSeparatedWithCommas(dni.name(),
                name.name(), lastname.name(), birthday.name(), DBUtils
                .getMaxColumnAs(VisitDBColumns.date.name(), LAST_VISIT_DATE_KEY));

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
                String lastVisitDate = LAST_VISIT_DATE_DEFAULT_VALUE;
                if (resultSet.getObject(LAST_VISIT_DATE_KEY) != null) {
                    lastVisitDate = DBUtils.getFormattedDate(resultSet.
                            getString(LAST_VISIT_DATE_KEY));
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
    public Patient verifyPatient(long dni) {
        Patient patient = null;
        connection = daoConnection.openDBConnection();
        query = DBUtils.getSelectAllStatementWithWhere(Tables.Patient,
                DBUtils.getSimpleWhereCondition(PatientDBColumns.dni.name()));
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            if (preparedStatement.getResultSet().next()) {
                patient = new Patient();
                patient.setDni(resultSet.getInt(PatientDBColumns.dni.name()));
                patient.setName(resultSet.getString(name.name()));
                patient.setLastname(resultSet.getString(lastname.name()));
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
     * Method used to retrieve all the basic data for a patient
     *
     * @param dni
     * @return Patient containing dni, name, last name, birthday and last visit
     * date
     */
    public Patient getBasicPatient(long dni) {
        patient = null;

        from = DBUtils.getTableJoin(LEFT_JOIN, Tables.Patient,
                Tables.Visit, PatientDBColumns.dni.name(), VisitDBColumns.patient.name());

        columns = DBUtils.getStringWithValuesSeparatedWithCommas(PatientDBColumns.dni.name(),
                name.name(), lastname.name(), birthday.name(), DBUtils
                .getMaxColumnAs(VisitDBColumns.date.name(), LAST_VISIT_DATE_KEY));

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
                patient.setName(resultSet.getString(name.name()));
                patient.setLastname(resultSet.getString(lastname.name()));
                patient.setBirthday(DBUtils.getFormattedDate(resultSet.getString(birthday.name())));
                String lastVisitDate = LAST_VISIT_DATE_DEFAULT_VALUE;
                if (resultSet.getObject(LAST_VISIT_DATE_KEY) != null) {
                    lastVisitDate = DBUtils.getFormattedDate(resultSet.
                            getString(LAST_VISIT_DATE_KEY));
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
     * @param id
     * @return Required full data patient
     */
    public Patient getFullPatient(long id) {
        Antecedents antecedents;
        patient = null;

        from = DBUtils.appendTableJoin(LEFT_JOIN,
                DBUtils.getTableJoin(LEFT_JOIN, Tables.Patient, Tables.Visit, idPatient.name(), DBUtils.getColumnWithTablePrefix(Tables.Visit, VisitDBColumns.patient.name())),
                Tables.Antecedents,
                idPatient.name(),
                DBUtils.getColumnWithTablePrefix(Tables.Antecedents, AntecedentsDBColumns.patient.name()));

        columns = DBUtils.getStringWithValuesSeparatedWithCommas(
                idPatient.name(), PatientDBColumns.dni.name(), name.name(), lastname.name(), phone.name(), address.name(),
                city.name(), birthday.name(), medicalCoverage.name(), medicalCoverageNumber.name(),
                firstVisitDate.name(), DBUtils.getMaxColumnAs(VisitDBColumns.date.name(), LAST_VISIT_DATE_KEY),
                        personal.name(), surgical.name(), toxic.name(), family.name(),
                        pharmacological.name());

        groupBy = DBUtils.getStringWithValuesSeparatedWithCommas(
                idPatient.name(), PatientDBColumns.dni.name(), name.name(), lastname.name(), phone.name(), address.name(),
                city.name(), birthday.name(), medicalCoverage.name(), medicalCoverageNumber.name(),
                firstVisitDate.name(), personal.name(), surgical.name(), toxic.name(), family.name(),
                        pharmacological.name());

        query = DBUtils.getSelectColumnsMultipleTablesStatementWithWhereAndGroupBy(columns, from,
                DBUtils.getSimpleWhereCondition(PatientDBColumns.idPatient.name()), groupBy);
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                patient = new Patient();
                patient.setDni(id);
                patient.setId(resultSet.getInt(idPatient.name()));
                patient.setName(resultSet.getString(name.name()));
                patient.setLastname(resultSet.getString(lastname.name()));
                patient.setAddress(resultSet.getString(address.name()));
                patient.setCity(resultSet.getString(city.name()));
                patient.setFirstVisitDate(DBUtils.getFormattedDate(resultSet.getString(firstVisitDate.name())));
                patient.setBirthday(DBUtils.getFormattedDate(resultSet.getString(birthday.name())));
                patient.setPrepaidHealthInsuranceNumber(resultSet.getString(medicalCoverageNumber.name()));
                if (resultSet.getObject(medicalCoverage.name()) != null && resultSet.getInt(medicalCoverage.name()) != 0) {
                    patient.setMedicalCoverage(daoPrepaidHealthInsurance.getPPHealthInsurance(resultSet.getInt(medicalCoverage.name())));
                } else {
                    patient.setMedicalCoverage(new MedicalCoverage(0, PP_HEALTH_INSURANCE_NAME_DEFAULT_VALUE));
                }
                patient.setPhone(resultSet.getString(phone.name()));
                String lastVisitDate = LAST_VISIT_DATE_DEFAULT_VALUE;
                if (resultSet.getObject(LAST_VISIT_DATE_KEY) != null) {
                    lastVisitDate = DBUtils.getFormattedDate(resultSet.
                            getString(LAST_VISIT_DATE_KEY));
                }
                patient.setLastVisitDate(lastVisitDate);

                antecedents = new Antecedents();
                antecedents.setPersonalAntecedents(resultSet.getString(personal.name()));
                antecedents.setSurgicalAntecedents(resultSet.getString(surgical.name()));
                antecedents.setToxicAntecedents(resultSet.getString(toxic.name()));
                antecedents.setFamilyAntecedents(resultSet.getString(family.name()));
                antecedents.setPharmacologicalAntecedents(resultSet.getString(pharmacological.name()));
                patient.setAntecendents(antecedents);
            }

            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }

        System.out.println(query);

        return patient;
    }

    /**
     * Method used to update a patient
     *
     * @param patient Updated patient
     * @return true if updated correctly, false otherwise
     */
    public boolean updatePatient(Patient patient) {
        try {
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    dni.name(), name.name(), lastname.name(), phone.name(),
                    medicalCoverage.name(), medicalCoverageNumber.name(),
                    city.name(), address.name());
            columns = DBUtils.getStringWithValuesSeparatedWithCommas(columns,
                    String.format(DBConstants.STR_TO_DATE_UPDATE_COLUMN, birthday.name()));
            query = DBUtils.getUpdateStatement(Tables.Patient, columns, DBUtils.getSimpleWhereCondition(idPatient.name()));
            preparedStatement = connection.prepareStatement(query);
            if (patient.getMedicalCoverage().getId() != 0) {
                preparedStatement.setInt(5, patient.getMedicalCoverage().getId());
                preparedStatement.setString(6, patient.getMedicalCoverageNumber());
            } else {
                preparedStatement.setNull(5, Types.INTEGER);
                preparedStatement.setNull(6, Types.VARCHAR);
            }
            preparedStatement.setLong(10, patient.getId());
            preparedStatement.setLong(1, patient.getDni());
            preparedStatement.setString(2, patient.getName());
            preparedStatement.setString(3, patient.getLastname());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.setString(7, patient.getCity());
            preparedStatement.setString(8, patient.getAddress());
            preparedStatement.setString(9, patient.getBirthday());
            preparedStatement.executeUpdate();
            daoAntecedents.withConnection(connection)
                    .updateAntecedents(patient.getAntecendents(), patient.getDni());
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
            Logger.getLogger(DAOMedicalCoverage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            daoConnection.closeDBConnection(connection);
        }
    }

    /**
     * Allows to verify that duplicated patients don't exist for a certain Pre
     * Paid Health Insurance with a certain Insurance Number
     *
     * @param prePaidHealthInsuranceId pre paid health insurance id
     * @param insuranceNumber patient's insurance number
     * @return the only patient that exists for the pre paid health insurance
     * and with that insurance number null if no match were found
     */
    public Patient validatePatientByInsuranceNumber(int prePaidHealthInsuranceId, String insuranceNumber) {
        Patient match = null;
        try {
            connection = daoConnection.openDBConnection();
            columns = DBUtils.getStringWithValuesSeparatedWithCommas(dni.name(), name.name(), lastname.name());
            where = DBUtils.getWhereConditions(DBUtils.getSimpleWhereCondition(
                    medicalCoverage.name()), DBUtils.getSimpleWhereCondition(medicalCoverageNumber.name()));
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
