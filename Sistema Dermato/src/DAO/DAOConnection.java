/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utils.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Fran
 */
public class DAOConnection extends DAOBasics {

    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASSWORD;
    private static final String URL = Constants.URL;

    private static final String OPENING_CONNECTION_WITH_DB_OK = "Connection to Database %s ... Ok";
    private static final String PROBLEM_OPENING_CONNECTION_WITH_DB = "There was a problem trying to connect to Database $1%s $2%s";
    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String PROBLEM_CLOSING_CONNECTION = "Connection couldn't be closed. Error: \n%s";
    private static final String CLOSING_CONNECTION_OK = "Database disconected successfully";
    
    /**
     * Method used to open a new connection with de DB
     *
     * @return Open Connection
     */
    public Connection openDBConnection() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(URL, USER, PASS);
            if (connection != null) {
                System.out.println(String.format(OPENING_CONNECTION_WITH_DB_OK, URL));
            }
            return connection;
        } catch (SQLException ex) {
            System.out.println(String.format(PROBLEM_OPENING_CONNECTION_WITH_DB, URL, ex.getSQLState()));
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     * Method used to close an open connection
     *
     * @param connectionection Open connection to close
     */
    public void closeDBConnection(Connection connectionection) {
        try {
            connectionection.close();
            System.out.println(CLOSING_CONNECTION_OK);
        } catch (SQLException ex) {
            System.out.println(String.format(PROBLEM_CLOSING_CONNECTION, ex.getSQLState()));
        }
    }

    /**
     * Method used to execute INSERT, UPDATE and DELETE queries
     *
     * @param consult query to execute
     * @return true if correctly executed, false otherwise
     */
    public boolean executeNonQuery(String consult) {
        Connection c = openDBConnection();
        try {
            statement = connection.createStatement();
            statement.execute(consult);
            statement.close();
            closeDBConnection(c);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        closeDBConnection(c);
        return false;
    }
}
