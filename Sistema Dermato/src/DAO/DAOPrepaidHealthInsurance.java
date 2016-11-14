/*
 * Clase DAO dedicada a las obras sociales;
 */
package DAO;

import ClasesBase.PrepaidHealthInsurance;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOPrepaidHealthInsurance {

    private final DAOConnection daoConnection;
    private ResultSet resultSet;
    private Connection connection;
    private PrepaidHealthInsurance prepaidHealthInsurance;
    private String query;
    private PreparedStatement preparedStatement;
    private LinkedList<PrepaidHealthInsurance> prepaidHealthInsurances;

    public DAOPrepaidHealthInsurance() {
        daoConnection = new DAOConnection();
    }

//    /**
//     * Method used to get all the pre paid health insurances
//     * @return All the pre paid health insurances
//     */
//    public LinkedList<PrepaidHealthInsurance> getAllPrePaidHealthInsurances()
//    {
//        prepaidHealthInsurances = new LinkedList<>();
//        connection = daoConnection.openDBConnection();
//        query = "SELECT * FROM OBRASOCIAL ORDER BY nombre ASC";
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.executeQuery(query);
//            resultSet = preparedStatement.getResultSet();
//            while(resultSet.next())
//            {
//                prepaidHealthInsurance = new PrepaidHealthInsurance();
//                prepaidHealthInsurance.setId(resultSet.getInt("idObraSocial"));
//                prepaidHealthInsurance.setNombre(resultSet.getString("nombre"));
//                prepaidHealthInsurances.add(prepaidHealthInsurance);
//            }
//            preparedStatement.close();
//        }
//        catch (SQLException ex) {
//        }
//        daoConnection.closeDBConnection(connection);
//        return prepaidHealthInsurances;
//    }
    
    /**
     * Method used to register a pre paid health insurance
     *
     * @param prePaidHealthInsurance pre paid health insurance to register
     * @return true if registered correctly, false otherwise
     */
    public boolean registerPrePaidHealthInsurance(PrepaidHealthInsurance prePaidHealthInsurance) {
        try {
            connection = daoConnection.openDBConnection();
            String cons = "INSERT INTO sistemaCarla.ObraSocial VALUES (null,?)";
            preparedStatement = connection.prepareStatement(cons);
            preparedStatement.setString(1, prePaidHealthInsurance.getName());
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrepaidHealthInsurance.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            daoConnection.closeDBConnection(connection);
        }
    }

//     /**
//     * Metodo utilizado para actualizar una Obra Social
//     * @param ObraSocial Obra Social modificada para actualizar
//     * @return true si se actualiza correctamente, false si no se actualiza
//     */
//    public boolean actualizarObraSocial(PrepaidHealthInsurance o) {
//        try {
//            connection = daoConnection.openDBConnection();
//            String cons = "UPDATE sistemaCarla.ObraSocial SET nombre = ? WHERE idObraSocial = ?";
//            preparedStatement = connection.prepareStatement(cons);
//            preparedStatement.setString(1, o.getNombre());
//            preparedStatement.setInt(2, o.getId());
//            return (preparedStatement.executeUpdate() > 0) ? true : false;
//        } catch (SQLException ex) {
//            Logger.getLogger(DAOPrepaidHealthInsurance.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//        finally {
//            daoConnection.closeDBConnection(connection);
//        }
//    }
//    
//    /**
//     * Metodo utilizado para eliminar una Obra Social
//     * @param ObraSocial Obra Social a eliminar
//     * @return true si se elimina correctamente, false si no se elimina
//     */
//    public boolean eliminarObraSocial(PrepaidHealthInsurance o)
//    {
//        boolean rtdo = false;
//        try {
//            connection = daoConnection.openDBConnection();
//            connection.setAutoCommit(false);
//            PreparedStatement deleteObraSocial =  connection.prepareStatement(
//			"DELETE FROM sistemaCarla.ObraSocial WHERE idObraSocial = ? ");
//            deleteObraSocial.setInt(1, o.getId());
//            deleteObraSocial.executeUpdate();
//            PreparedStatement updatePacientes = connection.prepareStatement(
//                        "UPDATE sistemaCarla.Paciente SET numeroAfiliado = '' WHERE obraSocial IS NULL");
//            updatePacientes.executeUpdate();
//            connection.commit();
//            rtdo = true;
//            connection.setAutoCommit(true);
//        }
//        
//        catch (Exception ex) {
//            try {
//                connection.rollback(); 
//            }
//            catch (SQLException e) {
//                System.out.println("RollBack Failure." + e.getMessage());
//            }
//            System.out.println(ex.getMessage());
//        }
//        finally {
//            daoConnection.closeDBConnection(connection);
//        }
//        return rtdo;
//    }
//    
//    /**
//    * Metodo utilizado para obtener una obra social
//    * @param id id de la obra social a obtener
//    * @return Obra Social buscada
//    */
//    public PrepaidHealthInsurance getObraSocial(int id) {
//        query = "SELECT * FROM OBRASOCIAL WHERE idObraSocial = ?";
//        try {
//            connection = daoConnection.openDBConnection();
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setLong(1,id);
//            preparedStatement.executeQuery();
//            resultSet = preparedStatement.getResultSet();
//            while(resultSet.next())
//            {
//                prepaidHealthInsurance = new PrepaidHealthInsurance();
//                prepaidHealthInsurance.setId(resultSet.getInt("idObraSocial"));
//                prepaidHealthInsurance.setNombre(resultSet.getString("nombre"));
//            }
//            preparedStatement.close();
//        }
//        catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        finally {
//            daoConnection.closeDBConnection(connection);
//        }
//        return prepaidHealthInsurance;
//    }
}
