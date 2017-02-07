/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.model;

import ClasesBase.DniType;
import ClasesBase.MedicalCoverage;
import ClasesBase.Patient;
import DAO.DAODniType;
import DAO.DAOPatient;
import DAO.DAOMedicalCoverage;
import java.util.List;

/**
 *
 * @author fran
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

    public boolean updatePatient(Patient patient, Patient oldPatient) {
        return daoPatient.updatePatient(patient, oldPatient);
    }

    public String validatePatientInsuranceNumber(Patient patient) {
//        Patient match = daoPatient.validatePatientByInsuranceNumber(
//                patient.getMedicalCoverage().getId(), patient.getMedicalCoverageNumber());
//        return match != null ? match.getFullName() : EMPTY_STRING;
        return EMPTY_STRING;
    }

    public String verifyPatientDni(Patient patient) {
//        Patient match = daoPatient.verifyPatient(patient.getDni());
//        return match != null ? match.getFullName() : EMPTY_STRING;
        return EMPTY_STRING;
    }

    public boolean registerMedicalCoverage(MedicalCoverage medicalCoverage) {
        return daoMedicalCoverage.registerMedicalCoverage(medicalCoverage);
    }

    public boolean registerPatient(Patient patient) {
        return daoPatient.registerPatient(patient);
    }
    
    public List<DniType> getDniTypes(){
        return daoDniType.getAllDniTypes();
    }

}
