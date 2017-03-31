/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.presenter;

import bussines.DniType;
import bussines.Patient;
import bussines.MedicalCoverage;
import dao.DAOBasics;
import java.util.List;
import mvp.model.PatientABMModel;
import mvp.view.PatientABMView;

/**
 *
 * @author fran
 */
public class PatientABMPresenter {

    private PatientABMView view;
    private final PatientABMModel model;

    private Patient patient;
    private List<MedicalCoverage> medicalCoverages;
    private List<DniType> dniTypes;

    public PatientABMPresenter(PatientABMView view) {
        this.view = view;
        model = new PatientABMModel();
    }

    public void setView(PatientABMView view) {
        this.view = view;
    }

    public void loadMedicalCoverages() {
        if (view == null) {
            return;
        }

        medicalCoverages = model.getAllMedicalCoverages();
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

        dniTypes = model.getDniTypes();
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

        this.patient = model.getFullPatient(patient);

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

        MedicalCoverage medicalCoverage = medicalCoverages.get(medicalCoverageSelected);
        patient.setMedicalCoverage(medicalCoverage);

        String patientInsuranceNumberMatch = model.validatePatientInsuranceNumber(patient);
        String patientDniMatch = model.verifyPatientDni(patient);
        DniType dniType = dniTypes.get(dniTypeSelected);
        patient.setDniType(dniType);

        if (patientInsuranceNumberMatch != null && !patientInsuranceNumberMatch.isEmpty()) {
            view.showErrorMessage("El paciente ya se encuentra registrado bajo el nombre de "
                    + patientInsuranceNumberMatch + " con la misma obra social y numero de afiliado.");
        } else if (patientDniMatch != null && !patientDniMatch.isEmpty()) {
            view.showErrorMessage("El paciente ya se encuentra registrado bajo el nombre de "
                    + patientInsuranceNumberMatch + " con el mismo dni.");
        } else {
            String result = model.updatePatient(patient, this.patient);
            if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {
                view.showInfoMessage("Paciente actualizado correctamente.");
                view.finishUpdatingPatient(patient);
            } else {
                view.showErrorMessage("Actualización Fallida: " + result);
            }
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

        String result = model.registerPatient(patient);
        
        if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {
            view.finishRegisteringPatient(patient);
        } else {
            view.showErrorMessage("Registro fallido: " + result);
        }
    }

    public void registerMedicalCoverage(MedicalCoverage medicalCoverage) {
        if (view == null) {
            return;
        }
        String result = model.registerMedicalCoverage(medicalCoverage);
        if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {

            medicalCoverages = model.getAllMedicalCoverages();
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

}
