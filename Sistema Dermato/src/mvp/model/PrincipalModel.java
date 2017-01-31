/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.model;

import ClasesBase.Patient;
import DAO.DAOPatient;
import java.util.List;
import mvp.view.PrincipalView;

/**
 *
 * @author fran
 */
public class PrincipalModel {

    private DAOPatient daoPatient;
    
    public PrincipalModel(){
        daoPatient = new DAOPatient();
    }
    
    public List<Patient> getPatientsByFilters(String name, String lastname, String dni) {
        return daoPatient.getAllPatients(name, lastname, dni);
    }

    public Patient getFullPatient(int id) {
        return daoPatient.getFullPatient(id);
    }
}
