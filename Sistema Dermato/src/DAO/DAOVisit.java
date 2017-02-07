/*
Clase DAO dedicada a la consulta;
 */
package DAO;

import ClasesBase.Patient;
import ClasesBase.Visit;
import Utils.DBConstants;
import static Utils.DBConstants.MAX_WITH_ALIAS;
import static Utils.DBConstants.SIMPLE_WHERE_CONDITION;
import Utils.DBConstants.Tables;
import Utils.DBUtils;
import static Utils.DBUtils.getSimpleWhereCondition;
import static Utils.DBUtils.getWhereConditions;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOVisit extends DAOBasics{

    private static final String VISIT_INSERT = "null,str_to_date(?, '%d/%c/%Y'),?,?,?,?,?,?,?,?";
    
    // TABLE COLUMNS
    public static final String VISIT_ID = "idVisit";
    public static final String DATE = "date";
    public static final String REASON = "reason";
    public static final String TREATMENT = "treatment";
    public static final String COMPLEMENTARY_STUDIES = "complementaryStudies";
    public static final String LABORATORY = "laboratory";
    public static final String DIAGNOSIS = "diagnosis";
    public static final String PHYSICAL_EXAM = "physicalExamen";
    public static final String BIOPSY = "biopsy";
    public static final String PATIENT = "patient";
    
    private Visit visit;

    public DAOVisit(){
        daoConnection = new DAOConnection();
    }
    
    /**
     * Method used to register a new visit
     *
     * @param visit
     * @param dni
     *
     * @return true if registered, false otherwise
     */
    public boolean registerVisit(Visit visit, long dni) {
        boolean result = false;
        try {
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(getInsertStatement());
            
            preparedStatement.setString(1, visit.getDate());
            preparedStatement.setString(2, visit.getReason());
            preparedStatement.setString(3, visit.getTreatment());
            preparedStatement.setString(4, visit.getComplementaryStudies());
            preparedStatement.setString(5, visit.getLaboratory());
            preparedStatement.setString(6, visit.getDiagnosis());
            preparedStatement.setString(7, visit.getPhysicalExam());
            preparedStatement.setString(8, visit.getBiopsy());
            preparedStatement.setLong(9, dni);
            preparedStatement.executeUpdate();

            connection.commit();
            result = true;
            connection.setAutoCommit(true);
            preparedStatement.close();
        } catch (Exception ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.out.println("RollBack Failure." + e.getMessage());
            }
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return result;
    }

    /**
     * Method used to retrieve the last consult id for a certain patient
     *
     * @param dni patient's dni
     *
     * @return id last visit id
     */
    public long getLastVisitId(long dni) {
        connection = daoConnection.openDBConnection();
        return getLastVisitId(connection, dni);
    }

    /**
     * Method used to retrieve the last consult id for a certain patient with a
     * certain connection
     *
     * @param connection Connection used to perform the query
     * @param dni patient's dni
     *
     * @return id last visit id
     */
    public long getLastVisitId(Connection connection, long dni) {
        columns = String.format(MAX_WITH_ALIAS, VISIT_ID,
                VISIT_ID);
        where = String.format(SIMPLE_WHERE_CONDITION, PATIENT);

        query = DBUtils.getSelectColumnsStatementWithWhere(Tables.Visit,
                columns, where);

        long lastVisit = 0;
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                lastVisit = resultSet.getLong(VISIT_ID);
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return lastVisit;
    }

    /**
     * Method used to retrieve all the patient's visits
     *
     * @param dni patient
     * @return List of patient's visits
     */
    public LinkedList<Visit> getAllPatientVisits(Patient patient) {
        LinkedList<Visit> visits = new LinkedList<>();

        columns = DBUtils.getStringWithValuesSeparatedWithCommas(
                VISIT_ID, DATE, REASON, DIAGNOSIS);
        where = getSimpleWhereCondition(PATIENT);
        orderBy = DBUtils.getStringWithValuesSeparatedWithCommas(
                DBUtils.getOrderByCondition(DATE, false),
                DBUtils.getOrderByCondition(VISIT_ID, false));

        query = DBUtils.getSelectStatementWithColumnsAndOrder(
                Tables.Visit, columns, where, orderBy);
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                visit = new Visit();
                visit.setId(Integer.parseInt(resultSet.getString(VISIT_ID)));
                visit.setDate(DBUtils.getFormattedDate(resultSet.getString(DATE)));
                visit.setReason(resultSet.getString(REASON));
                visit.setDiagnosis(resultSet.getString(DIAGNOSIS));
                visits.add(visit);
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        return visits;
    }

    /**
     * Method used to retrieve a patient's full visit
     *
     * @param visitId visit
     * @param dni patient
     * @return Full patient's visit
     */
    public Visit getFullVisit(int visitId, Patient patient) {

        where = getWhereConditions(
                getSimpleWhereCondition(PATIENT),
                getSimpleWhereCondition(VISIT_ID));
        
        query = DBUtils.getSelectAllStatementWithWhere(Tables.Visit, 
                 where);
        
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patient.getDni());
            preparedStatement.setInt(2, visitId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                visit = new Visit();
                visit.setId(resultSet.getInt(VISIT_ID));
                visit.setDate(DBUtils.getFormattedDate(resultSet.getString(DATE)));
                visit.setReason(resultSet.getString(REASON));
                visit.setTreatment(resultSet.getString(TREATMENT));
                visit.setComplementaryStudies(resultSet.getString(COMPLEMENTARY_STUDIES));
                visit.setLaboratory(resultSet.getString(LABORATORY));
                visit.setDiagnosis(resultSet.getString(DIAGNOSIS));
                visit.setPhysicalExam(resultSet.getString(PHYSICAL_EXAM));
                visit.setBiopsy(resultSet.getString(BIOPSY));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
        }
        daoConnection.closeDBConnection(connection);
        
        return visit;
    }

    /**
     * Method used to update a visit
     *
     * @param visit modified visit
     * @return true if updated correctly, false otherwise
     */
    public boolean updateVisit(Visit visit) {
        try {
            this.visit = visit;
            connection = daoConnection.openDBConnection();
                        
            columns = DBUtils.getStringWithValuesSeparatedWithCommasForUpdate(
                    REASON, TREATMENT, COMPLEMENTARY_STUDIES,
                    LABORATORY, DIAGNOSIS, PHYSICAL_EXAM,
                    BIOPSY);
            
            where = DBUtils.getSimpleWhereCondition(VISIT_ID);
            
            query = DBUtils.getUpdateStatement(Tables.Visit, columns, where);
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, visit.getReason());
            preparedStatement.setString(2, visit.getTreatment());
            preparedStatement.setString(3, visit.getComplementaryStudies());
            preparedStatement.setString(4, visit.getLaboratory());
            preparedStatement.setString(5, visit.getDiagnosis());
            preparedStatement.setString(6, visit.getPhysicalExam());
            preparedStatement.setString(7, visit.getBiopsy());
            preparedStatement.setInt(8, visit.getId());

            return (((preparedStatement.executeUpdate() > 0)));
        } catch (SQLException ex) {
            Logger.getLogger(DAOVisit.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOVisit.class.getName()).log(Level.SEVERE, null, ex);
            }
            daoConnection.closeDBConnection(connection);
        }
    }
    
    @Override
    String getInsertStatement() {
        return String.format(DBConstants.INSERT_WITH_VALUES_ONLY,
                        DBConstants.Tables.Visit.name(), VISIT_INSERT);
    }
}
