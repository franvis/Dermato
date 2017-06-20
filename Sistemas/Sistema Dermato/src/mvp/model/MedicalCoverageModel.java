package mvp.model;

import bussines.MedicalCoverage;
import dao.DAOMedicalCoverage;
import java.util.List;

/**
 *
 * @author Francisco Visintini
 */
public class MedicalCoverageModel {

    private final DAOMedicalCoverage daoMedicalCoverage;

    public MedicalCoverageModel() {
        daoMedicalCoverage = new DAOMedicalCoverage();
    }

    public String updateMedicalCoverage(MedicalCoverage medicalCoverage) {
        return daoMedicalCoverage.updateMedicalCoverage(medicalCoverage);
    }

    public List<MedicalCoverage> getAllMedicalCoverages(boolean withNoCoverage) {
        return daoMedicalCoverage.getAllMedicalCoverages(withNoCoverage);
    }

    public String registerMedicalCoverage(MedicalCoverage medicalCoverage) {
        if (!isAlreadyRegistered(medicalCoverage)) {
            return daoMedicalCoverage.registerMedicalCoverage(medicalCoverage);
        } else {
            return "La obra social ya se encuentra registrada.";
        }
    }

    public String deleteMedicalCoverage(MedicalCoverage medicalCoverage) {
        return daoMedicalCoverage.deleteMedicalCoverage(medicalCoverage);
    }

    /**
     * Validates if a medical coverage is already registered.
     *
     * @param medicalCoverage
     * @return true if already registered, false otherwise
     */
    private boolean isAlreadyRegistered(MedicalCoverage medicalCoverage) {
        return daoMedicalCoverage.isMedicalCoverageAlreadyRegistered(medicalCoverage.getName());
    }
}
