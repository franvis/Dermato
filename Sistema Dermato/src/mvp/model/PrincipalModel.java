/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.model;

import ClasesBase.DniType;
import ClasesBase.Patient;
import DAO.DAODniType;
import DAO.DAOPatient;
import java.util.List;

/**
 *
 * @author fran
 */
public class PrincipalModel {

    private final DAOPatient daoPatient;
    private final DAODniType daoDniType;
    
    public PrincipalModel(){
        daoPatient = new DAOPatient();
        daoDniType = new DAODniType();
    }
    
    public List<Patient> getPatientsByFilters(String name, String lastname, String dni, int dniTypeId) {
        return daoPatient.getAllPatients(name, lastname, dni, dniTypeId);
    }

    public Patient getFullPatient(Patient patient) {
        return daoPatient.getFullPatient(patient);
    }
    
    public List<DniType> getDniTypes(){
        return daoDniType.getAllDniTypes();
    }
}
