package mvp.model;

import ClasesBase.Antecedents;
import ClasesBase.MedicalCoverage;
import ClasesBase.Patient;
import DAO.DAOMedicalCoverage;
import java.util.List;

/**
 *
 * @author Francisco Visintini
 */
public class MedicalCoverageModel {

    private final DAOMedicalCoverage daoMedicalCoverage;
    
    public MedicalCoverageModel(){
        daoMedicalCoverage = new DAOMedicalCoverage();
    }

    public boolean updateMedicalCoverage(MedicalCoverage medicalCoverage) {
        return daoMedicalCoverage.updateMedicalCoverage(medicalCoverage);
    }

    public List<MedicalCoverage> loadAllMedicalCoverages() {
        return daoMedicalCoverage.getAllMedicalCoverages();
    }
    
    public boolean registerMedicalCoverage(MedicalCoverage medicalCoverage){
        return daoMedicalCoverage.registerMedicalCoverage(medicalCoverage);
    }
    
    public boolean deleteMedicalCoverage(MedicalCoverage medicalCoverage){
        return daoMedicalCoverage.deleteMedicalCoverage(medicalCoverage);
    }
}
