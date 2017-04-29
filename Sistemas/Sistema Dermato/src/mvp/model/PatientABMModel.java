package mvp.model;

import bussines.DniType;
import bussines.MedicalCoverage;
import bussines.Patient;
import dao.DAODniType;
import dao.DAOPatient;
import dao.DAOMedicalCoverage;
import java.util.List;

/**
 *
 * @author Francisco Visintini
 */
public class PatientABMModel {

    private final DAOPatient daoPatient;
    private final DAODniType daoDniType;
    private final DAOMedicalCoverage daoMedicalCoverage;
    private final static String EMPTY_STRING = "";

    public PatientABMModel() {
        daoPatient = new DAOPatient();
        daoMedicalCoverage = new DAOMedicalCoverage();
        daoDniType = new DAODniType();
    }

    public Patient getFullPatient(Patient patient) {
        return daoPatient.getFullPatient(patient);
    }

    public List<MedicalCoverage> getAllMedicalCoverages() {
        return daoMedicalCoverage.getAllMedicalCoverages();
    }

    public String updatePatient(Patient patient, Patient oldPatient) {
        return daoPatient.updatePatient(patient, oldPatient);
    }

    public String validatePatientInsuranceNumber(Patient patient) {
        Patient match = daoPatient.validatePatientByInsuranceNumber(
                patient.getMedicalCoverage().getId(), patient.getMedicalCoverageNumber());
        return match != null ? match.getFullName() : EMPTY_STRING;
    }

    public String verifyPatientDni(Patient patient) {
        Patient match = daoPatient.verifyPatient(patient.getDni(), patient.getDniType());
        return match != null ? match.getFullName() : EMPTY_STRING;
    }

    public String registerMedicalCoverage(MedicalCoverage medicalCoverage) {
        return daoMedicalCoverage.registerMedicalCoverage(medicalCoverage);
    }

    public String registerPatient(Patient patient) {
        return daoPatient.registerPatient(patient);
    }
    
    public List<DniType> getDniTypes(){
        return daoDniType.getAllDniTypes();
    }
}