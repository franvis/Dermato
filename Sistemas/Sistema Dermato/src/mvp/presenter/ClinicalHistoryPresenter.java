package mvp.presenter;

import bussines.Antecedents;
import bussines.Patient;
import bussines.Visit;
import java.util.List;
import mvp.model.ClinicalHistoryModel;
import mvp.view.ClinicalHistoryView;

/**
 *
 * @author Francisco Visintini
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
        if (visits == null) {
            view.showErrorMessage("No se pudieron recuperar las consultas del paciente. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
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

    public void seePatientAntecedents() {
        if (view == null) {
            return;
        }

        Antecedents antecedents = model.getPatientAntecedents(patient);
        patient.setAntecedents(antecedents);
        view.showAntecedents(patient);
    }

    public void newVisit() {
        if (view == null) {
            return;
        }

        view.newVisit(patient);
    }

    public void seeVisit(int selectedVisit) {
        if (view == null) {
            return;
        }

        if (visits.size() > 0 && visits.get(selectedVisit) != null) {
            Visit visit = model.getFullVisit(visits.get(selectedVisit).getId(), patient.getPatientId());
            if (visit != null) {
                view.displayVisit(patient, visit);
            } else {
                view.showErrorMessage("No se pudo recuperar la consulta. Por favor "
                        + "reinicie el sistema e intente nuevamente."
                        + "\nSi el error persiste comuniquese con el administrador.");
            }
        } else {
            view.showErrorMessage("No se pudo encontrar la consulta. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        }
    }

    public void seePatientPreviousHC() {
        if (view == null) {
            return;
        }

        if (patient.getPreviousCH() == null || patient.getPreviousCH().isEmpty()) {
            view.showInfoMessage("El paciente no posee historia clinica previa.");
        } else {
            view.showPreviousCH(patient.getPreviousCH());
        }
    }
}
