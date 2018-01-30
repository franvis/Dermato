package mvp.presenter;

import bussines.DniType;
import bussines.Patient;
import bussines.MedicalCoverage;
import dao.DAOBasics;
import java.util.List;
import mvp.model.MedicalCoverageModel;
import mvp.model.PatientABMModel;
import mvp.view.PatientABMView;

/**
 *
 * @author Francisco Visintini
 */
public class PatientABMPresenter {

    private PatientABMView view;
    private final PatientABMModel patientAbmModel;
    private final MedicalCoverageModel medicalCoverageModel;

    private Patient patient;
    private List<MedicalCoverage> medicalCoverages;
    private List<DniType> dniTypes;

    public PatientABMPresenter(PatientABMView view) {
        this.view = view;
        patientAbmModel = new PatientABMModel();
        medicalCoverageModel = new MedicalCoverageModel();
    }

    public void setView(PatientABMView view) {
        this.view = view;
    }

    public void loadMedicalCoverages() {
        if (view == null) {
            return;
        }

        medicalCoverages = medicalCoverageModel.getAllMedicalCoverages(true);
        if (medicalCoverages == null || medicalCoverages.isEmpty()) {
            view.showErrorMessage("No se pudo recuperar las obras sociales. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.displayMedicalCoverages(medicalCoverages);
        }
    }

    public void loadDniTypes() {
        if (view == null) {
            return;
        }

        dniTypes = patientAbmModel.getDniTypes();
        if (dniTypes.isEmpty() || dniTypes == null) {
            view.showErrorMessage("No se pudo recuperar los tipos de dni. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.displayDniTypes(dniTypes);
        }

    }

    /**
     * Method used only when user is modifying a patient to load all the
     * patients data.
     *
     * @param patient
     */
    public void loadPatientData(Patient patient) {
        if (view == null) {
            return;
        }

        this.patient = patientAbmModel.getFullPatient(patient);

        if (this.patient == null) {
            view.showErrorMessage("No se pudo encontrar el paciente. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.displayPatientData(this.patient);
        }
    }

    public void updatePatient(Patient patient, int medicalCoverageSelected,
            int dniTypeSelected) {
        if (view == null) {
            return;
        }
        patient.setPatientId(this.patient.getPatientId());
        MedicalCoverage medicalCoverage = medicalCoverages.get(medicalCoverageSelected);
        patient.setMedicalCoverage(medicalCoverage);
        DniType dniType = dniTypes.get(dniTypeSelected);
        patient.setDniType(dniType);

        if (isDniModified(patient)) {
            String patientDniMatch = patientAbmModel.verifyPatientDni(patient);

            if (patientDniMatch != null && !patientDniMatch.isEmpty()) {
                view.showErrorMessage("El paciente ya se encuentra registrado bajo el nombre de "
                        + patientDniMatch + " con el mismo dni.");
                return;
            }
        } 
        
        String result = patientAbmModel.updatePatient(patient);
        if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {
            this.patient = patient;
            view.showInfoMessage("Paciente actualizado correctamente.");
            view.finishUpdatingPatient(patient);
        } else {
            view.showErrorMessage("Actualización Fallida: " + result);
        }
    }

    public void registerPatient(Patient patient, int medicalCoverageSelected,
            int dniTypeSelected) {
        if (view == null) {
            return;
        }

        MedicalCoverage medicalCoverage = medicalCoverages.get(medicalCoverageSelected);
        patient.setMedicalCoverage(medicalCoverage);
        DniType dniType = dniTypes.get(dniTypeSelected);
        patient.setDniType(dniType);

        String patientDniMatch = patientAbmModel.verifyPatientDni(patient);

        if (patientDniMatch != null && !patientDniMatch.isEmpty()) {
            view.showErrorMessage("El paciente ya se encuentra registrado bajo el nombre de "
                    + patientDniMatch + " con el mismo dni.");
        } else {
            String result = patientAbmModel.registerPatient(patient);

            if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {
                view.finishRegisteringPatient(patient);
            } else {
                view.showErrorMessage("Registro fallido: " + result);
            }
        }
    }

    public void registerMedicalCoverage(MedicalCoverage medicalCoverage) {
        if (view == null) {
            return;
        }
        String result = medicalCoverageModel.registerMedicalCoverage(medicalCoverage);
        if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {

            medicalCoverages = patientAbmModel.getAllMedicalCoverages(true);
            if (medicalCoverages == null || medicalCoverages.isEmpty()) {
                view.showErrorMessage("No se pudieron actualizar las obras sociales despues de registrar una nueva. Por favor "
                        + "reinicie el sistema e intente nuevamente."
                        + "\nSi el error persiste comuniquese con el administrador.");
                view.exitWindow();
            } else {
                view.displayMedicalCoverages(medicalCoverages);
                view.finishRegisteringMedicalCoverage(medicalCoverage.getName());
            }
        } else {
            view.showErrorMessage(result);
        }
    }

    private boolean isDniModified(Patient patient) {
        return (this.patient.getDniType() == null && patient.getDniType() != null)
                || (this.patient.getDniType() != null && patient.getDniType() == null) //AVOID NULLS IN DNI TYPE
                || (this.patient.getDni() == null && patient.getDni() != null)
                || (this.patient.getDni() != null && patient.getDni() == null) //AVOID NULLS IN DNI
                || !this.patient.getDni().equals(patient.getDni())
                || this.patient.getDniType().getId() != patient.getDniType().getId();
    }
}
