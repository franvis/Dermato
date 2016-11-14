/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utils.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fran
 */
public class DAOConnection {
    
    private static final String USER = Constants.USER;
    private static final String PASS = Constants.PASSWORD;
    private static final String URL = Constants.URL;
    private Connection conn = null;
    private Statement st;
    
    private static final String OPENING_CONNECTION_WITH_DB_OK = "Conexión a base de datos %s ... Ok";
    private static final String PROBLEM_OPENING_CONNECTION_WITH_DB = "Hubo un problema al intentar conectarse con la base de datos $1%s $2%s";
    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String PROBLEM_CLOSING_CONNECTION = "No se pudo cerrar. Error: \n%s";
    private static final String CLOSING_CONNECTION_OK = "Se desconectó";
     
    /**
     * Method used to open a new connection with de DB
     * @return Open Connection
     */ 
     public Connection openDBConnection() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            conn = DriverManager.getConnection(URL, USER, PASS);
            if (conn != null) {
                System.out.println(String.format(OPENING_CONNECTION_WITH_DB_OK, URL));
            }
        return conn;
        }
        catch (SQLException ex) {
            System.out.println(String.format(PROBLEM_OPENING_CONNECTION_WITH_DB, URL, ex.getSQLState()));
        }
        catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return null;
    }
     
    /**
     * Method used to close an open connection
     * @param connection Open connection to close
     */ 
    public void closeDBConnection(Connection connection) {
        try {
            connection.close();
            System.out.println(CLOSING_CONNECTION_OK);
        }
        catch (SQLException ex) {
            System.out.println(String.format(PROBLEM_CLOSING_CONNECTION, ex.getSQLState()));
        }
    }


    /**
     * Method used to execute INSERT, UPDATE and DELETE queries
     * @param consult query to execute
     * @return true if correctly executed, false otherwise
     */
    public boolean executeNonQuery(String consult) {
        Connection c = openDBConnection();
        try {
            st = conn.createStatement();
            st.execute(consult);
            st.close();
            closeDBConnection(c);
            return true;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        closeDBConnection(c);
        return false;
    }
}
