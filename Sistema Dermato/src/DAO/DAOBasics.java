/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author fran
 */
public abstract class DAOBasics {
    protected final DAOConnection daoConnection = new DAOConnection();
    protected ResultSet resultSet;
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected String query;
    protected Statement statement;
}
