/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.view;

import bussines.Antecedents;
import bussines.MedicalCoverage;
import java.util.List;

/**
 *
 * @author fran
 */
public interface MedicalCoverageView extends GeneralView{
    
    public void displayMedicalCoverages(List<MedicalCoverage> medicalCoverages);

    public void displayMedicalCoverageInfo(MedicalCoverage medicalCoverage);
    
    public void exitWindow();
    
    public void finishUpdatingMedicalCoverage(int selectedMedicalCoverage);
    
    public void finishRegisteringMedicalCoverage();
    
    public void finishDeletingMedicalCoverage();
}
