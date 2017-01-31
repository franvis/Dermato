/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.presenter;

import ClasesBase.Patient;
import java.util.List;
import mvp.model.PrincipalModel;
import mvp.view.PrincipalView;

/**
 *
 * @author fran
 */
public class PrincipalPresenter {

    private PrincipalView view;
    private PrincipalModel model;

    private List<Patient> patientsList;

    public PrincipalPresenter(PrincipalView principalView) {
        this.view = principalView;
        model = new PrincipalModel();
    }

    public void setView(PrincipalView principalView) {
        this.view = principalView;
    }

    /**
     * Method used to update the patients list according to the filters.
     *
     * @param name name filter
     * @param lastname last name filter
     * @param dni dni filter
     */
    public void retrievePatientsByFilters(String name, String lastname, String dni) {

        if (view == null) {
            return;
        }

        patientsList = model.getPatientsByFilters(name, lastname, dni);
        if (patientsList == null || patientsList.isEmpty()) {
            view.showEmptyTable();
        } else {
            view.fillTable(patientsList);
        }
    }

    /**
     * Retrieves full patient to see his Clinical History if patients are 
     * being showed on patients table.
     * 
     * @param selectedPatient 
     */
    public void seeClinicalHistory(int selectedPatient) {
        if (view == null) {
            view.showErrorMessage("Algo ocurrió mal. Por favor comuniquese con el administrador del sistema.");
            return;
        }

        if (patientsList == null || patientsList.isEmpty()) {
            view.showInfoMessage("No se pudo encontrar el paciente. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            Patient patient = patientsList.get(selectedPatient);
            patient = model.getFullPatient(patient.getId());

            view.showPatientClinicalHistory(patient);
        }
    }
    
    public void modifyPatient(int selectedPatient){
        if (view == null) {
            view.showErrorMessage("Algo ocurrió mal. Por favor comuniquese con el administrador del sistema.");
            return;
        }
        
        if (patientsList == null || patientsList.isEmpty()) {
            view.showInfoMessage("No se pudo encontrar el paciente. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            Patient patient = patientsList.get(selectedPatient);
            patient = model.getFullPatient(patient.getId());

            view.modifyPatientData(patient);
        }
        
    }
}
