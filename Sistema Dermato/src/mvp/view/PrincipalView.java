/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.view;

import ClasesBase.Patient;
import java.util.List;

/**
 *
 * @author fran
 */
public interface PrincipalView {
    
    /**
     * Method used to fill the patients table
     *
     * @param patients patients list used to fill the table
     */
    public void fillTable(List<Patient> patients);
    
    /**
     * Method used to set an empty table
     *
     */
    public void showEmptyTable();
    
    public void showPatientClinicalHistory(Patient patient);
    
    public void showErrorMessage(String error);
    
    public void showInfoMessage(String info);
    
    public void modifyPatientData(Patient patient);
}
