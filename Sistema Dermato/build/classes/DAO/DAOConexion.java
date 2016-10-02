/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import ClasesBase.VariablesLocales;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Fran
 */
public class DAOConexion {
    
    private static String login = VariablesLocales.user;
    private static String pass = VariablesLocales.password;
    private static String url = VariablesLocales.url;
    private Connection conn = null;
    private Statement st;
    
    
     
    /**
     * Metodo utilizado para abrir una conexion
     * @return Conexion abierta
     */ 
     public Connection conectarBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, login, pass);
            if (conn != null) {
                System.out.println("Conexión a base de datos " + url + " ... Ok");
            }
        return conn;
        }
        catch (SQLException ex) {
            System.out.println("Hubo un problema al intentar conectarse con la base de datos " + url + " " + ex.getSQLState());
        }
        catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        catch (Exception ex) {
        }
        return null;
    }
     
    /**
     * Metodo utilizado para cerrar una conexion abierta
     * @param c Conexion abierta a cerrar
     */ 
    public void desconectarBD(Connection c) {
        try {
            c.close();
            System.out.println("Se desconectó");
        }
        catch (SQLException ex) {
            System.out.println("No se pudo cerrar");
        }
    }


    /**
     * Metodo utilizado para ejecutar consultas INSERT, UPDATE y DELETE
     * @param cons Consulta a ejecutar
     * @return true si se ejecuto correctamente, false si se ejecuto incorrectamente
     */
    public boolean executeNonQuery(String cons) {
        Connection c = conectarBD();
        try {
            st = conn.createStatement();
            st.execute(cons);
            st.close();
            desconectarBD(c);
            return true;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        desconectarBD(c);
        return false;
    }
}
