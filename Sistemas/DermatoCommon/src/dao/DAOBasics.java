/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author fran
 */
public abstract class DAOBasics {
    
    public static final String DB_COMMAND_SUCCESS = "SUCCESS";
    private static final String DB_COMMAND_FAILED = "FAILED: %s";
    
    protected DAOConnection daoConnection;
    protected ResultSet resultSet;
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected String query;
    protected String columns;
    protected String where;
    protected String from;
    protected String orderBy;
    protected String groupBy;
    protected Statement statement;
    
    abstract String getInsertStatement();
    
    public String dbCommandFailed(String error){
        return String.format(DB_COMMAND_FAILED, error);
    }
}
