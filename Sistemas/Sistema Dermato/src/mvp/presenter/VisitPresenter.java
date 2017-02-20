/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.presenter;

import bussines.Patient;
import bussines.Visit;
import mvp.model.VisitModel;
import mvp.view.VisitView;

/**
 *
 * @author fran
 */
public class VisitPresenter {

    private final VisitView view;
    private final VisitModel model;
    private Visit visit;
    private final Patient patient;

    public VisitPresenter(VisitView view, Patient patient) {
        this.view = view;
        this.patient = patient;
        model = new VisitModel();
    }

    /**
     * Method used only when user is modifying a visit to load all the visit
     * data.
     *
     * @param visit
     */
    public void loadVisitData(Visit visit) {
        if (view == null) {
            return;
        }

        this.visit = visit;

        if (this.visit == null) {
            view.showErrorMessage("No se pudo encontrar la consulta. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.displayVisitData(this.visit);
        }
    }

    public void cancelVisitModifcation() {
        if (view == null) {
            return;
        }

        if (this.visit == null) {
            view.showErrorMessage("No se pudo encontrar la consulta. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.displayVisitData(this.visit);
        }
    }

    public void registerVisit(Visit visit) {
        if (view == null) {
            return;
        }

        if (model.registerVisit(visit, patient)) {
            view.showInfoMessage("Registro exitoso.");
            view.finishRegisteringVisit();
        } else {
            view.showErrorMessage("No se pudo registrar la visita. Intente nuevamente"
                    + ", y si el error persiste, comuniquese con el administrador.");
        }
    }
    
    public void updateVisit(Visit visit){
        if (view == null) {
            return;
        }
        
        //Setting id to new visit
        visit.setId(this.visit.getId());
        
        if (model.updateVisit(visit)) {
            view.showInfoMessage("Consulta actualizado correctamente.");
            view.finishUpdatingVisit();
        } else {
            view.showErrorMessage("No se pudo actualizar la consulta."
                    + "\nSi el error persiste comuniquese con el administrador.");
        }
    }
}