/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.presenter;

import ClasesBase.Patient;
import ClasesBase.Antecedents;
import ClasesBase.MedicalCoverage;
import java.util.List;
import mvp.model.AntecedentsModel;
import mvp.model.MedicalCoverageModel;
import mvp.view.AntecedentsView;
import mvp.view.MedicalCoverageView;

/**
 *
 * @author fran
 */
public class MedicalCoveragePresenter {

    private final MedicalCoverageView view;
    private final MedicalCoverageModel model;
    
    private List<MedicalCoverage> medicalCoverages;

    public MedicalCoveragePresenter(MedicalCoverageView view) {
        this.view = view;
        model = new MedicalCoverageModel();
    }

    /**
     * Method used to load all the antecedents data.
     *
     */
    public void loadMedicalCoveragesData() {
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
}
