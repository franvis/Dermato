/*
Clase DAO dedicada a la consulta;
 */
package DAO;

import ClasesBase.Visit;
import Utils.DBConstants;
import static Utils.DBConstants.MAX_WITH_ALIAS;
import static Utils.DBConstants.SIMPLE_WHERE_CONDITION;
import Utils.DBConstants.Tables;
import Utils.DBConstants.VisitDBColumns;
import static Utils.DBConstants.VisitDBColumns.biopsy;
import static Utils.DBConstants.VisitDBColumns.complementaryStudies;
import static Utils.DBConstants.VisitDBColumns.date;
import static Utils.DBConstants.VisitDBColumns.diagnosis;
import static Utils.DBConstants.VisitDBColumns.idVisit;
import static Utils.DBConstants.VisitDBColumns.laboratory;
import static Utils.DBConstants.VisitDBColumns.patient;
import static Utils.DBConstants.VisitDBColumns.physicalExam;
import static Utils.DBConstants.VisitDBColumns.reason;
import static Utils.DBConstants.VisitDBColumns.treatment;
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
public class DAOVisit {

    private final DAOConnection daoConnection;
    private ResultSet resultSet;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Visit visit;

    public DAOVisit() {
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
    public boolean RegistrarConsulta(Visit visit, long dni) {
        boolean result = false;
        try {
            connection = daoConnection.openDBConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DBUtils.
                    getInsertStatementWithValuesOnly(Tables.Visit,
                            "null,str_to_date(?, '%d/%c/%Y'),?,?,?,?,?,?,?,?"));
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
        String columns = String.format(MAX_WITH_ALIAS, idVisit.name(),
                idVisit.name());
        String whereCondition = String.format(SIMPLE_WHERE_CONDITION, patient.name());

        String consulta = DBUtils.getSelectStatementWithColumns(Tables.Visit,
                columns, whereCondition);

        long lastVisit = 0;
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(consulta);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                lastVisit = resultSet.getLong(idVisit.name());
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
     * Method used to retrieve all the patient's consults
     *
     * @param dni patient
     * @return List of patient's visits
     */
    public LinkedList<Visit> getAllPatientVisits(long dni) {
        LinkedList<Visit> visits = new LinkedList<>();

        String columns = DBUtils.getStringWithValuesSeparatedWithCommas(
                idVisit.name(), date.name(), reason.name(), diagnosis.name());
        String whereConditions = getSimpleWhereCondition(patient.name());
        String orderConditions = DBUtils.getStringWithValuesSeparatedWithCommas(
                DBUtils.getOrderByCondition(date.name(), false),
                DBUtils.getOrderByCondition(idVisit.name(), false));

        String query = DBUtils.getSelectStatementWithColumnsAndOrderCondition(
                Tables.Visit, columns, whereConditions, orderConditions);
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                visit = new Visit();
                String fecha = resultSet.getString(date.name());
                String año = fecha.substring(0, 4);
                String mes = fecha.substring(5, 7);
                String dia = fecha.substring(8, 10);
                visit.setId(Integer.parseInt(resultSet.getString(idVisit.name())));
                visit.setDate(dia + "/" + mes + "/" + año);
                visit.setReason(resultSet.getString(reason.name()));
                visit.setDiagnosis(resultSet.getString(diagnosis.name()));
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
    public Visit getFullVisit(int visitId, long dni) {

        String whereCondition = getWhereConditions(
                getSimpleWhereCondition(patient.name()),
                getSimpleWhereCondition(idVisit.name()));
        
        String query = DBUtils.getSelectStatementWithoutColumns(Tables.Visit, 
                 whereCondition);
        
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, dni);
            preparedStatement.setInt(2, visitId);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                visit = new Visit();
                String fecha = resultSet.getString(date.name());
                String año = fecha.substring(0, 4);
                String mes = fecha.substring(5, 7);
                String dia = fecha.substring(8, 10);
                visit.setId(resultSet.getInt(idVisit.name()));
                visit.setDate(dia + "/" + mes + "/" + año);
                visit.setReason(resultSet.getString(reason.name()));
                visit.setTreatment(resultSet.getString(treatment.name()));
                visit.setComplementaryStudies(resultSet.getString(complementaryStudies.name()));
                visit.setLaboratory(resultSet.getString(laboratory.name()));
                visit.setDiagnosis(resultSet.getString(diagnosis.name()));
                visit.setPhysicalExam(resultSet.getString(physicalExam.name()));
                visit.setBiopsy(resultSet.getString(biopsy.name()));
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
    public boolean actualizarConsulta(Visit visit) {
        try {
            this.visit = visit;
            connection = daoConnection.openDBConnection();
                        
            String columns = DBUtils.getStringWithValuesSeparatedWithCommas(
                    reason.name(), treatment.name(), complementaryStudies.name(),
                    laboratory.name(), diagnosis.name(), physicalExam.name(),
                    biopsy.name());
            
            String whereCondition = DBUtils.getSimpleWhereCondition(idVisit.name());
            
            String cons = DBUtils.getUpdateStatement(Tables.Visit, columns, whereCondition);
            
            preparedStatement = connection.prepareStatement(cons);
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
}
