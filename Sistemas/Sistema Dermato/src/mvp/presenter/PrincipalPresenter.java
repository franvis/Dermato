/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.presenter;

import bussines.DniType;
import bussines.Patient;
import java.util.List;
import mvp.model.PrincipalModel;
import mvp.view.PrincipalView;

/**
 *
 * @author fran
 */
public class PrincipalPresenter {

    private final PrincipalView view;
    private final PrincipalModel model;

    private List<Patient> patientsList;
    private List<DniType> dniTypes;

    public PrincipalPresenter(PrincipalView view) {
        this.view = view;
        model = new PrincipalModel();
    }

    /**
     * Method used to update the patients list according to the filters.
     *
     * @param name name filter
     * @param lastname last name filter
     * @param dni dni filter
     */
    public void filterPatients(String name, String lastname, String dni, int dniTypeSelected) {

        if (view == null) {
            return;
        }

        if (dniTypes == null || dniTypes.isEmpty()) {
            view.showErrorMessage("No se pudo recuperar los tipos de dni. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            DniType dniType = dniTypes.get(dniTypeSelected);
            patientsList = model.getPatientsByFilters(name, lastname, dni, dniType.getId());
            if (patientsList == null || patientsList.isEmpty()) {
                view.showEmptyTable();
            } else {
                view.fillTable(patientsList);
            }
        }
    }

    /**
     * Retrieves full patient to see his Clinical History if patients are being
     * showed on patients table.
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
            patient = model.getFullPatient(patient);
            
            if (patient == null) {
                view.showInfoMessage("No se pudo encontrar el paciente. Por favor "
                        + "reinicie el sistema e intente nuevamente."
                        + "\nSi el error persiste comuniquese con el administrador.");
            } else {
                view.showPatientClinicalHistory(patient);
            }
        }
    }

    public void modifyPatient(int selectedPatient) {
        if (view == null) {
            view.showErrorMessage("Algo ocurrió mal. Por favor comuniquese con el administrador del sistema.");
            return;
        }

        if (patientsList == null || patientsList.isEmpty()
                || patientsList.get(selectedPatient) == null) {
            view.showInfoMessage("No se pudo encontrar el paciente. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            Patient patient = patientsList.get(selectedPatient);

            view.modifyPatientData(patient);
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
}
