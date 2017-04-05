/*
 *Clase DAO dedicada al paciente;
 */
package dao;

import bussines.Antecedents;
import bussines.DniType;
import bussines.MedicalCoverage;
import bussines.Patient;
import utils.DBConstants;
import utils.DBConstants.Tables;
import utils.DBUtils;
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
    private static final String MEDICAL_COVERAGE_DEFAULT_NAME = "Sin Obra Social";
    public static final String PATIENT_INSERT = "?,?,?,?,?,?,?,"
            + "str_to_date(?, '%d/%c/%Y'),?,?,str_to_date(?, '%d/%c/%Y'),?";
    public static final String PATIENT_INSERT_WITHOUT_MEDICAL_COVERAGE
            = "null,?,?,?,?,?,?,"
            + "str_to_date(?, '%d/%c/%Y'),null,'',str_to_date(?, '%d/%c/%Y')";

    public static final String DNI_TYPE = "dniType";
    public static final String DNI = "dni";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String CITY = "city";
    public static final String BIRTHDAY = "birthday";
    public static final String MEDICAL_COVERAGE = "medicalCoverage";
    public static final String MEDICAL_COVERAGE_NUMBER = "medicalCoverageNumber";
    public static final String FIRST_VISIT_DATE = "firstVisitDate";

    private final DAOAntecedents daoAntecedents;
    private LinkedList<Patient> pacientes;
    private Patient patient;

    public DAOPatient() {
        daoAntecedents = new DAOAntecedents();
        daoConnection = new DAOConnection();
    }

    /**
     * Method used to register a patient
     *
     * @param patient Patient to register
     * @return "SUCCESS" if registered successful, error otherwise
     */
    public String registerPatient(Patient patient) {
        try {
            boolean hasPPHealthInsurance = patient.getMedicalCoverage().getId() != 0;
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);
            query = getInsertStatement();
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            if (hasPPHealthInsurance) {
                preparedStatement.setInt(9, patient.getMedicalCoverage().getId());
                preparedStatement.setString(10, patient.getMedicalCoverageNumber());
            } else {
                preparedStatement.setNull(9, java.sql.Types.INTEGER);
                preparedStatement.setNull(10, java.sql.Types.VARCHAR);
            }
            preparedStatement.setString(1, patient.getDni());
            preparedStatement.setInt(2, patient.getDniType().getId());
            preparedStatement.setString(3, patient.getName());
            preparedStatement.setString(4, patient.getLastname());
            preparedStatement.setString(5, patient.getPhone());
            preparedStatement.setString(6, patient.getAddress());
            preparedStatement.setString(7, patient.getCity());
            preparedStatement.setString(8, patient.getBirthday());
            preparedStatement.setString(11, patient.getFirstVisitDate());
            preparedStatement.setNull(12, java.sql.Types.VARCHAR);
            if (preparedStatement.executeUpdate() != 0) {
                query = daoAntecedents.getInsertStatement();
                Antecedents antecedents = patient.getAntecedents();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, antecedents.getPersonalAntecedents());
                preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
                preparedStatement.setString(3, antecedents.getToxicAntecedents());
                preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
                preparedStatement.setString(5, antecedents.getFamilyAntecedents());
                preparedStatement.setString(6, patient.getDni());
                preparedStatement.setInt(7, patient.getDniType().getId());
                if (preparedStatement.executeUpdate() == 0) {
                    return dbCommandFailed("executeUpdated registering patient returned <= 0");
                }
                connection.commit();
            } else {
                return dbCommandFailed("executeUpdated registering patient returned <= 0");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPatient.class.getName()).log(Level.SEVERE, null, ex);
            return dbCommandFailed(ex.getMessage() == null ? "SqlException Error code " + ex.getErrorCode() : ex.getMessage());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                return dbCommandFailed(ex.getMessage() == null ? "SqlException Error code " + ex.getErrorCode() : ex.getMessage());
            }
            daoConnection.closeDBConnection(connection);
        }
        return DB_COMMAND_SUCCESS;
    }

    /**
     * Method used to retrieve all the patients according to the filters
     *
     * @param filterName
     * @param filterLastName
     * @param filterDni
     * @param filterDniType
     *
     * @return List containing all the patients according to the filters
     */
    public LinkedList<Patient> getAllPatients(String filterName, String filterLastName, String filterDni, int filterDniType) {
        pacientes = new LinkedList<>();

        from = "Patient p INNER JOIN DniType d on p.dniType = d.idDniType LEFT JOIN Visit v on"
                + " p.dniType = v.patientDniType AND p.dni = v.patientDni";

        columns = DBUtils.getStringWithValuesSeparatedWithCommas("d." + DAODniType.DNI_TYPE_NAME, DNI_TYPE, DNI,
                NAME, LASTNAME, BIRTHDAY, DBUtils
                .getMaxColumnAs(DAOVisit.DATE, LAST_VISIT_DATE_KEY));

        where = DBUtils.getWhereForFilters(filterName, filterLastName, filterDni, filterDniType);
        orderBy = DBUtils.getOrderByForFilters(filterName, filterLastName, filterDni);
        groupBy = DBUtils.getStringWithValuesSeparatedWithCommas(DNI_TYPE, DNI, NAME,
                LASTNAME, BIRTHDAY);

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
                cant++;
                preparedStatement.setInt(cant, filterDniType);
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
            DniType dniType;
            while (resultSet.next()) {
                patient = new Patient();
                dniType = new DniType(resultSet.getInt(DNI_TYPE), resultSet.getString(DAODniType.DNI_TYPE_NAME));
                patient.setDniType(dniType);
                patient.setDni(resultSet.getString(DNI));
                patient.setName(resultSet.getString(NAME));
                patient.setLastname(resultSet.getString(LASTNAME));
                if (resultSet.getString(BIRTHDAY) != null) {
                    patient.setBirthday(DBUtils.getFormattedDate(resultSet.getString(BIRTHDAY)));
                }
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
     * @param dniType patient's dni type
     * @return Complete patient if exists, null patient otherwise
     */
    public Patient verifyPatient(String dni, DniType dniType) {
        connection = daoConnection.openDBConnection();
        where = DBUtils.getWhereConditions(
                DBUtils.getSimpleWhereCondition(DNI),
                DBUtils.getSimpleWhereCondition(DNI_TYPE));

        query = DBUtils.getSelectAllStatementWithWhere(Tables.Patient,
                where);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dni);
            preparedStatement.setInt(2, dniType.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                patient = new Patient();
                patient.setDniType(dniType);
                patient.setDni(dni);
                patient.setName(resultSet.getString(NAME));
                patient.setLastname(resultSet.getString(LASTNAME));
            } else {
                preparedStatement.close();
                return null;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return patient;
    }

    /**
     * Method used to retrieve a full data patient.
     *
     * @param patient
     *
     * @return Required full data patient
     */
    public Patient getFullPatient(Patient patient) {
        Antecedents antecedents;
        this.patient = null;

        from = "Patient p INNER JOIN DniType d on p.dniType = d.idDniType LEFT JOIN Visit v on"
                + " p.dniType = v.patientDniType AND p.dni = v.patientDni LEFT JOIN Antecedents a on"
                + " p.dniType = a.patientDniType AND p.dni = a.patientDni LEFT JOIN MedicalCoverage m on"
                + " p.medicalCoverage = m.idMedicalCoverage";

        columns = DBUtils.getStringWithValuesSeparatedWithCommas(
                "d." + DAODniType.DNI_TYPE_ID, "d." + DAODniType.DNI_TYPE_NAME, "p." + DNI_TYPE, "p." + DNI, "p." + NAME, "p." + LASTNAME, "p." + PHONE, "p." + ADDRESS,
                "p." + CITY, "p." + BIRTHDAY, "p." + MEDICAL_COVERAGE, "p." + MEDICAL_COVERAGE_NUMBER,
                "p." + FIRST_VISIT_DATE, DBUtils.getMaxColumnAs("v." + DAOVisit.DATE, LAST_VISIT_DATE_KEY),
                "a." + DAOAntecedents.PERSONAL, "a." + DAOAntecedents.SURGICAL, "a." + DAOAntecedents.TOXIC, "a." + DAOAntecedents.FAMILY,
                "a." + DAOAntecedents.PHARMACOLOGICAL, "m." + DAOMedicalCoverage.MEDICAL_COVERAGE_NAME,
                "m." + DAOMedicalCoverage.MEDICAL_COVERAGE_ID);

        groupBy = DBUtils.getStringWithValuesSeparatedWithCommas(
                DNI_TYPE, DNI, NAME, LASTNAME, PHONE, ADDRESS,
                CITY, BIRTHDAY, MEDICAL_COVERAGE, MEDICAL_COVERAGE_NUMBER,
                FIRST_VISIT_DATE, "a." + DAOAntecedents.PERSONAL, "a." + DAOAntecedents.SURGICAL, "a." + DAOAntecedents.TOXIC, "a." + DAOAntecedents.FAMILY,
                "a." + DAOAntecedents.PHARMACOLOGICAL, "m." + DAOMedicalCoverage.MEDICAL_COVERAGE_NAME,
                "m." + DAOMedicalCoverage.MEDICAL_COVERAGE_ID);

        where = DBUtils.getWhereConditions(
                DBUtils.getSimpleWhereCondition(DNI),
                DBUtils.getSimpleWhereCondition(DNI_TYPE));

        query = DBUtils.getSelectColumnsMultipleTablesStatementWithWhereAndGroupBy(columns, from,
                where, groupBy);
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patient.getDni());
            preparedStatement.setInt(2, patient.getDniType().getId());
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                patient = new Patient();
                patient.setDni(resultSet.getString(DNI));
                DniType dniType = new DniType();
                dniType.setId(resultSet.getInt(DAODniType.DNI_TYPE_ID));
                dniType.setName(resultSet.getString(DAODniType.DNI_TYPE_NAME));
                patient.setDniType(dniType);
                patient.setName(resultSet.getString(NAME));
                patient.setLastname(resultSet.getString(LASTNAME));
                patient.setAddress(resultSet.getString(ADDRESS));
                patient.setCity(resultSet.getString(CITY));
                if (resultSet.getString(FIRST_VISIT_DATE) != null) {
                    patient.setFirstVisitDate(DBUtils.getFormattedDate(resultSet.getString(FIRST_VISIT_DATE)));
                }
                if (resultSet.getString(BIRTHDAY) != null) {
                    patient.setBirthday(DBUtils.getFormattedDate(resultSet.getString(BIRTHDAY)));
                }
                if (resultSet.getObject(MEDICAL_COVERAGE) != null && resultSet.getInt(MEDICAL_COVERAGE) != 0) {
                    patient.setMedicalCoverage(new MedicalCoverage(resultSet.getInt("m." + DAOMedicalCoverage.MEDICAL_COVERAGE_ID),
                            resultSet.getString("m." + DAOMedicalCoverage.MEDICAL_COVERAGE_NAME)));
                    patient.setMedicalCoverageNumber(resultSet.getString(MEDICAL_COVERAGE_NUMBER));
                } else {
                    patient.setMedicalCoverage(new MedicalCoverage(0, MEDICAL_COVERAGE_DEFAULT_NAME));
                }
                patient.setPhone(resultSet.getString(PHONE));
                String lastVisitDate = LAST_VISIT_DATE_DEFAULT_VALUE;
                if (resultSet.getObject(LAST_VISIT_DATE_KEY) != null) {
                    lastVisitDate = DBUtils.getFormattedDate(resultSet.
                            getString(LAST_VISIT_DATE_KEY));
                }
                patient.setLastVisitDate(lastVisitDate);

                antecedents = new Antecedents();
                antecedents.setPersonalAntecedents(resultSet.getString(DAOAntecedents.PERSONAL));
                antecedents.setSurgicalAntecedents(resultSet.getString(DAOAntecedents.SURGICAL));
                antecedents.setToxicAntecedents(resultSet.getString(DAOAntecedents.TOXIC));
                antecedents.setFamilyAntecedents(resultSet.getString(DAOAntecedents.FAMILY));
                antecedents.setPharmacologicalAntecedents(resultSet.getString(DAOAntecedents.PHARMACOLOGICAL));
                patient.setAntecedents(antecedents);
            }

            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            daoConnection.closeDBConnection(connection);
        }

        System.out.println(query);

        return patient;
    }

    /**
     * Method used to update a patient
     *
     * @param patient updated patient
     * @param oldPatient old patient
     *
     * @return "SUCCESS" if updated successful, error otherwise
     */
    public String updatePatient(Patient patient, Patient oldPatient) {
        try {
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);

            //UPDATE PATIENT
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    DNI_TYPE, DNI, NAME, LASTNAME, PHONE,
                    MEDICAL_COVERAGE, MEDICAL_COVERAGE_NUMBER,
                    CITY, ADDRESS);
            columns = DBUtils.getStringWithValuesSeparatedWithCommas(columns,
                    String.format(DBConstants.STR_TO_DATE_UPDATE_COLUMN, BIRTHDAY));
            where = DBUtils.getWhereConditions(
                    DBUtils.getSimpleWhereCondition(DNI),
                    DBUtils.getSimpleWhereCondition(DNI_TYPE));
            query = DBUtils.getUpdateStatement(Tables.Patient, columns, where);
            preparedStatement = connection.prepareStatement(query);
            if (patient.getMedicalCoverage().getId() != 0) {
                preparedStatement.setInt(6, patient.getMedicalCoverage().getId());
                preparedStatement.setString(7, patient.getMedicalCoverageNumber());
            } else {
                preparedStatement.setNull(6, Types.INTEGER);
                preparedStatement.setNull(7, Types.VARCHAR);
            }
            preparedStatement.setString(11, oldPatient.getDni());
            preparedStatement.setInt(12, oldPatient.getDniType().getId());
            preparedStatement.setInt(1, patient.getDniType().getId());
            preparedStatement.setString(2, patient.getDni());
            preparedStatement.setString(3, patient.getName());
            preparedStatement.setString(4, patient.getLastname());
            preparedStatement.setString(5, patient.getPhone());
            preparedStatement.setString(8, patient.getCity());
            preparedStatement.setString(9, patient.getAddress());
            preparedStatement.setString(10, patient.getBirthday());
            preparedStatement.executeUpdate();

            //UPDATE ANTECEDENTS
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    DAOAntecedents.PERSONAL, DAOAntecedents.SURGICAL,
                    DAOAntecedents.TOXIC, DAOAntecedents.PHARMACOLOGICAL,
                    DAOAntecedents.FAMILY);
            where = DBUtils.getWhereConditions(
                    DBUtils.getSimpleWhereCondition(DAOAntecedents.PATIENT_DNI),
                    DBUtils.getSimpleWhereCondition(DAOAntecedents.PATIENT_DNI_TYPE));
            query = DBUtils.getUpdateStatement(Tables.Antecedents, columns, where);
            Antecedents antecedents = patient.getAntecedents();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, antecedents.getPersonalAntecedents());
            preparedStatement.setString(2, antecedents.getSurgicalAntecedents());
            preparedStatement.setString(3, antecedents.getToxicAntecedents());
            preparedStatement.setString(4, antecedents.getPharmacologicalAntecedents());
            preparedStatement.setString(5, antecedents.getFamilyAntecedents());
            preparedStatement.setString(6, patient.getDni());
            preparedStatement.setInt(7, patient.getDniType().getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return dbCommandFailed(ex.getMessage() == null ? "SqlException Error code " + ex.getErrorCode() : ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return DB_COMMAND_SUCCESS;
    }

    /**
     * Method used to delete a patient
     *
     * @param dni dni of the patient intended to delete
     * @param dniType dni type of the patient
     *
     * @return true if deleted correctly, false otherwise
     */
    public String deletePatient(String dni, DniType dniType) {
        try {
            connection = daoConnection.openDBConnection();
            where = DBUtils.getWhereConditions(
                    DBUtils.getSimpleWhereCondition(DNI),
                    DBUtils.getSimpleWhereCondition(DNI_TYPE));
            query = DBUtils.getDeleteStatement(Tables.Patient, where);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dni);
            preparedStatement.setInt(2, dniType.getId());
            return (preparedStatement.executeUpdate() > 0) ? DB_COMMAND_SUCCESS : dbCommandFailed("executeUpdated registering patient returned <= 0");
        } catch (SQLException ex) {
            return dbCommandFailed(ex.getMessage() == null ? "SqlException Error code " + ex.getErrorCode() : ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
    }

    /**
     * Allows to verify that duplicated patients don't exist for a certain Pre
     * Paid Health Insurance with a certain Insurance Number
     *
     * @param medicalCoverageId pre paid health insurance id
     * @param insuranceNumber patient's insurance number
     *
     * @return the only patient that exists for the pre paid health insurance
     * and with that insurance number null if no match were found
     */
    public Patient validatePatientByInsuranceNumber(int medicalCoverageId, String insuranceNumber) {
        Patient match = null;
        try {
            connection = daoConnection.openDBConnection();
            columns = DBUtils.getStringWithValuesSeparatedWithCommas(DNI, NAME, LASTNAME);
            where = DBUtils.getWhereConditions(DBUtils.getSimpleWhereCondition(
                    MEDICAL_COVERAGE), DBUtils.getSimpleWhereCondition(MEDICAL_COVERAGE_NUMBER));
            query = DBUtils.getSelectColumnsStatementWithWhere(Tables.Patient, columns, where);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, medicalCoverageId);
            preparedStatement.setString(2, insuranceNumber);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                match = new Patient();
                match.setDni(resultSet.getString(DNI));
                match.setName(resultSet.getString(NAME));
                match.setLastname(resultSet.getString(LASTNAME));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return match;
    }

    @Override
    String getInsertStatement() {
        return String.format(DBConstants.INSERT_WITH_VALUES_ONLY,
                DBConstants.Tables.Patient.name(), PATIENT_INSERT);
    }
}
