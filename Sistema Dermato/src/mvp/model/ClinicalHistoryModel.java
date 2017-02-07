package mvp.model;

import DAO.DAOAntecedents;
import DAO.DAOPatient;
import DAO.DAOVisit;

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
}
