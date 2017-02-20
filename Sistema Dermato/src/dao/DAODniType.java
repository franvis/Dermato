/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import bussines.DniType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fran
 */
public class DAODniType extends DAOBasics{
    
    public static final String DNI_TYPE_ID = "idDniType";
    
    public static final String DNI_TYPE_NAME = "dniTypeName";
    
    public DAODniType(){
        daoConnection = new DAOConnection();
    }
    
    public List<DniType> getAllDniTypes(){
        ArrayList<DniType> dniTypes = new ArrayList<>();
        DniType dniType;
        query = "SELECT * FROM DniType";
        
        try {
            connection = daoConnection.openDBConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();
            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                dniType = new DniType();
                dniType.setId(resultSet.getInt(DNI_TYPE_ID));
                dniType.setName(resultSet.getString(DNI_TYPE_NAME));
                dniTypes.add(dniType);
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            daoConnection.closeDBConnection(connection);
        }
        
        return dniTypes;
    }

    @Override
    String getInsertStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
