package mvp.model;

import bussines.DniType;
import bussines.Patient;
import dao.DAODniType;
import dao.DAOPatient;
import java.util.List;

/**
 *
 * @author Francisco Visintini
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
    
    public String deletePatient(int patientId){
        return daoPatient.deletePatient(patientId);
    }
}
