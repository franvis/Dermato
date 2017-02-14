package mvp.model;

import ClasesBase.Antecedents;
import ClasesBase.Patient;
import DAO.DAOAntecedents;

/**
 *
 * @author Francisco Visintini
 */
public class AntecedentsModel {

    private final DAOAntecedents daoAntecedents;
    
    public AntecedentsModel(){
        daoAntecedents = new DAOAntecedents();
    }

    public boolean updateAntecedents(Antecedents antecedents, Patient patient) {
        return daoAntecedents.updateAntecedents(antecedents, patient.getDni(), patient.getDniType().getId());
    }

    public Antecedents loadAntecedentsData(Patient patient) {
        return daoAntecedents.getAntecedent(patient);
    }
}
