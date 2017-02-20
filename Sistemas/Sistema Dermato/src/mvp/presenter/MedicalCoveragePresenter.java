/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.presenter;

import ClasesBase.MedicalCoverage;
import java.util.List;
import mvp.model.MedicalCoverageModel;
import mvp.view.MedicalCoverageView;

/**
 *
 * @author fran
 */
public class MedicalCoveragePresenter {

    private final MedicalCoverageView view;
    private final MedicalCoverageModel model;

    private List<MedicalCoverage> medicalCoverages;
    private MedicalCoverage medicalCoverage;

    public MedicalCoveragePresenter(MedicalCoverageView view) {
        this.view = view;
        model = new MedicalCoverageModel();
    }

    /**
     * Method used to load all the antecedents data.
     *
     */
    public void loadMedicalCoverages() {
        if (view == null) {
            return;
        }

        this.medicalCoverages = model.loadAllMedicalCoverages();

        if (this.medicalCoverages == null) {
            view.showErrorMessage("No se pudieron encontrar las obras sociales. Por favor "
                    + "reinicie el sistema e intente nuevamente."
                    + "\nSi el error persiste comuniquese con el administrador.");
        } else {
            view.displayMedicalCoverages(medicalCoverages);
        }
    }

    public void deleteMedicalCoverage(int medicalCoverage) {
        if (view == null) {
            return;
        }

        if (!medicalCoverages.isEmpty()) {
            if (model.deleteMedicalCoverage(medicalCoverages.get(medicalCoverage))) {
                view.showInfoMessage("Borrado Exitoso.");
                view.finishDeletingMedicalCoverage();
            } else {
                view.showErrorMessage("Borrado Fallido.");
            }
        }
    }

    public void registerMedicalCoverage(String medicalCoverageName) {
        if (view == null) {
            return;
        }

        if (model.registerMedicalCoverage(new MedicalCoverage(0, medicalCoverageName))) {
            view.showInfoMessage("Registro Exitoso.");
            loadMedicalCoverages();
            view.finishRegisteringMedicalCoverage();
        } else {
            view.showErrorMessage("Registro Fallido.");
        }
    }

    public void updateMedicalCoverage(String newName) {
        if (view == null) {
            return;
        }

        medicalCoverage.setName(newName);

        if (model.updateMedicalCoverage(medicalCoverage)) {
            view.showInfoMessage("Actualización Exitosa.");
            loadMedicalCoverages();
            view.finishRegisteringMedicalCoverage();
        } else {
            view.showErrorMessage("Actualización Fallida.");
        }
    }

    public void loadMedicalCoverageInfo(int selectedMedicalCoverage) {
        if (view == null) {
            return;
        }

        medicalCoverage = medicalCoverages.get(selectedMedicalCoverage);

        view.displayMedicalCoverageInfo(medicalCoverage);
    }
}
