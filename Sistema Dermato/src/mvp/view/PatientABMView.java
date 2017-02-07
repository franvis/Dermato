package mvp.view;

import ClasesBase.DniType;
import ClasesBase.MedicalCoverage;
import ClasesBase.Patient;
import java.util.List;

/**
 *
 * @author fran
 */
public interface PatientABMView extends GeneralView{
    
    public void displayMedicalCoverages(List<MedicalCoverage> medicalCoverages);
    
    public void displayPatientData(Patient patient);
    
    public void displayDniTypes(List<DniType> dniTypes);
    
    public void exitWindow();
    
    public void finishUpdatingPatient(Patient patient);
    
    public void finishRegisteringPatient(Patient patient);

    public void finishRegisteringMedicalCoverage(String medicalCoverageName);
}
