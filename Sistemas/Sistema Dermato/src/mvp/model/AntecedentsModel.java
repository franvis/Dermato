package mvp.model;

import bussines.Antecedents;
import bussines.Patient;
import dao.DAOAntecedents;

/**
 *
 * @author Francisco Visintini
 */
public class AntecedentsModel {

    private final DAOAntecedents daoAntecedents;
    
    public AntecedentsModel(){
        daoAntecedents = new DAOAntecedents();
    }

    public String updateAntecedents(Antecedents antecedents, int patientId) {
        return daoAntecedents.updateAntecedents(antecedents, patientId);
    }

    public Antecedents loadAntecedentsData(Patient patient) {
        return daoAntecedents.getAntecedents(patient);
    }
}
