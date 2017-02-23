package mvp.model;

import bussines.Antecedents;
import bussines.Patient;
import bussines.Visit;
import dao.DAOAntecedents;
import dao.DAOPatient;
import dao.DAOVisit;
import java.util.List;

/**
 *
 * @author Francisco Visintini
 */
public class ClinicalHistoryModel {

    DAOPatient patientDao;
    DAOVisit visitDao;
    DAOAntecedents antecedentsDao;
    
    public ClinicalHistoryModel(){
        patientDao = new DAOPatient();
        visitDao = new DAOVisit();
        antecedentsDao = new DAOAntecedents();
    }
    
    public Patient getFullPatient(Patient patient) {
        return patientDao.getFullPatient(patient);
    }

    public List<Visit> getPatientVisits(Patient patient) {
        return visitDao.getAllPatientVisits(patient);
    }

    public Antecedents getPatientAntecedents(Patient patient) {
        return antecedentsDao.getAntecedents(patient);
    }
}
