package mvp.model;

import ClasesBase.Patient;
import ClasesBase.Visit;
import DAO.DAOAntecedents;
import DAO.DAOPatient;
import DAO.DAOVisit;
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
}
