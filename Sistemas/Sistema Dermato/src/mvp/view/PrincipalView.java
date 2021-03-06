package mvp.view;

import bussines.DniType;
import bussines.Patient;
import java.util.List;

/**
 *
 * @author Francisco Visintini
 */
public interface PrincipalView extends GeneralView{
    
    /**
     * Method used to fill the patients table
     *
     * @param patients patients list used to fill the table
     */
    public void fillTable(List<Patient> patients);
    
    /**
     * Method used to show patients table as empty.
     */
    public void showEmptyTable();
    
    /**
     * Method used to show the clinical history of the selected patient.
     * 
     * @param patient selected patient
     */
    public void showPatientClinicalHistory(Patient patient);
    
    /**
     * Method used to modify the personal data of the selected patient.
     * 
     * @param patient selected patient
     */
    public void modifyPatientData(Patient patient);
    
    /**
     * Method used to fill dni types for the dni patient filter
     *
     * @param dniTypes all the dni types
     */
    public void displayDniTypes(List<DniType> dniTypes);
}
