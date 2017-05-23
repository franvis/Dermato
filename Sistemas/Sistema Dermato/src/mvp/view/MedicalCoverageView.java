package mvp.view;

import bussines.MedicalCoverage;
import java.util.List;

/**
 *
 * @author Francisco Visintini
 */
public interface MedicalCoverageView extends GeneralView{
    
    public void displayMedicalCoverages(List<MedicalCoverage> medicalCoverages);

    public void displayMedicalCoverageInfo(MedicalCoverage medicalCoverage);
    
    public void exitWindow();
    
    public void finishUpdatingMedicalCoverage(int selectedMedicalCoverage);
    
    public void finishRegisteringMedicalCoverage();
    
    public void finishDeletingMedicalCoverage();
}
