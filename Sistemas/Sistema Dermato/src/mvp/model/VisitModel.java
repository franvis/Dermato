package mvp.model;

import ClasesBase.Patient;
import ClasesBase.Visit;
import DAO.DAOVisit;

/**
 *
 * @author Francisco Visintini
 */
public class VisitModel {

    private DAOVisit daoVisit;
    
    public VisitModel(){
        daoVisit = new DAOVisit();
    }

    public boolean registerVisit(Visit visit, Patient patient) {
        return daoVisit.registerVisit(visit, patient);
    }

    public boolean updateVisit(Visit visit) {
        return daoVisit.updateVisit(visit);
    }
}
