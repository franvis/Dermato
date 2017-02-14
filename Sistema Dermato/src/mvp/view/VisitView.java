/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.view;

import ClasesBase.Patient;
import ClasesBase.Visit;

/**
 *
 * @author fran
 */
public interface VisitView extends GeneralView{
    
    public void displayVisitData(Visit visit);
    
    public void displayPatientData(Patient patient);

    public void exitWindow();
    
    public void finishRegisteringVisit();
    
    public void finishUpdatingVisit();
}
