/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.presenter;

import bussines.Patient;
import bussines.Visit;
import java.util.List;
import mvp.model.ClinicalHistoryModel;
import mvp.view.ClinicalHistoryView;

/**
 *
 * @author fran
 */
public class ClinicalHistoryPresenter {

    private final ClinicalHistoryView view;
    private final ClinicalHistoryModel model;

    private Patient patient;
    private List<Visit> visits;

    public ClinicalHistoryPresenter(ClinicalHistoryView view) {
        this.view = view;
        model = new ClinicalHistoryModel();
    }

    /**
     * Method used to load all the patients data.
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

    public void loadPatientVisits() {
        if (view == null) {
            view.showErrorMessage("Algo ocurrió mal. Por favor comuniquese con el administrador del sistema.");
            return;
        }
        
        if (this.patient == null) {
            view.showErrorMessage("No se pudo encontrar el paciente. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
            return;
        }

        visits = model.getPatientVisits(patient);
        if(visits == null){
            view.showErrorMessage("No se pudieron recuperar las consultas del paciente. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        }else{
            view.displayPatientVisits(visits);
        }
    }

    public void modifyPatient() {
        if (view == null) {
            view.showErrorMessage("Algo ocurrió mal. Por favor comuniquese con el administrador del sistema.");
            return;
        }

        if (patient == null) {
            view.showInfoMessage("No se pudo encontrar el paciente. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.modifyPatientData(patient);
        }
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
