/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.presenter;

import bussines.Patient;
import bussines.Antecedents;
import dao.DAOBasics;
import mvp.model.AntecedentsModel;
import mvp.view.AntecedentsView;

/**
 *
 * @author fran
 */
public class AntecedentsPresenter {

    private final AntecedentsView view;
    private final AntecedentsModel model;
    private final Patient patient;
    private Antecedents antecedents;

    public AntecedentsPresenter(AntecedentsView view, Patient patient) {
        this.view = view;
        this.patient = patient;
        model = new AntecedentsModel();
    }

    /**
     * Method used to load all the antecedents data.
     *
     * @param patient
     */
    public void loadAntecedentsData(Patient patient) {
        if (view == null) {
            return;
        }

        this.antecedents = model.loadAntecedentsData(patient);

        if (this.antecedents == null) {
            view.showErrorMessage("No se pudieron encontrar los antecedentes. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.displayAntecedents(this.antecedents);
        }
    }

    public void updateAntecedents(Antecedents antecedents){
        if (view == null) {
            return;
        }
        
        String result = model.updateAntecedents(antecedents, patient);
        
        if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {
            view.showInfoMessage("Antecedentes actualizados correctamente.");
            view.finishUpdatingAntecedents();
        } else {
            view.showErrorMessage("Antecedentes no actualizados: " + result);
        }
    }

    public void reloadAntecedentsData() {
        if (view == null) {
            return;
        }

        this.antecedents = model.loadAntecedentsData(patient);

        if (this.antecedents == null) {
            view.showErrorMessage("No se pudieron encontrar los antecedentes. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.displayAntecedents(this.antecedents);
        }
    }
}
