package mvp.model;

import bussines.Patient;
import bussines.Visit;
import dao.DAOPatient;
import dao.DAOVisit;

/**
 *
 * @author Francisco Visintini
 */
public class VisitModel {

    private final DAOVisit daoVisit;
    private final DAOPatient daoPatient;
    
    public VisitModel(){
        daoVisit = new DAOVisit();
        daoPatient = new DAOPatient();
    }

    public String registerVisit(Visit visit, Patient patient) {
        return daoVisit.registerVisit(visit, patient);
    }

    public String updateVisit(Visit visit) {
        return daoVisit.updateVisit(visit);
    }
    
    public Patient getFullPatient(Patient patient) {
        return daoPatient.getFullPatient(patient);
    }
}
