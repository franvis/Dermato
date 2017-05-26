package mvp.presenter;

import bussines.Patient;
import bussines.Visit;
import dao.DAOBasics;
import mvp.model.VisitModel;
import mvp.view.VisitView;

/**
 *
 * @author Francisco Visintini
 */
public class VisitPresenter {

    private final VisitView view;
    private final VisitModel model;
    private Visit visit;
    private Patient patient;

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

        String result = model.registerVisit(visit, patient);
        
        if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {
            view.finishRegisteringVisit();
        } else {
            view.showErrorMessage("Registro Fallido: " + result);
        }
    }
    
    public void updateVisit(Visit visit){
        if (view == null) {
            return;
        }
        
        //Setting id to new visit
        visit.setId(this.visit.getId());
        
        String result = model.updateVisit(visit);
        
        if (result.equals(DAOBasics.DB_COMMAND_SUCCESS)) {
            view.showInfoMessage("Consulta actualizada correctamente.");
            view.finishUpdatingVisit();
        } else {
            view.showErrorMessage("Actualización fallida: " + result);
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
}
