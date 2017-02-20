package mvp.view;

import ClasesBase.Patient;
import ClasesBase.Visit;
import java.util.List;

/**
 *
 * @author fran
 */
public interface ClinicalHistoryView extends GeneralView {
    
    /**
     * Method used to patient data in the clinical history.
     * 
     * @param patient
     */
    public void displayPatientData(Patient patient);

    /**
     * Method used to patient's visits in the clinical history.
     * 
     * @param visits 
     */
    public void displayPatientVisits(List<Visit> visits);
    
    /**
     * Method used to modify the personal data of the patient.
     * 
     * @param patient
     */
    public void modifyPatientData(Patient patient);
}
