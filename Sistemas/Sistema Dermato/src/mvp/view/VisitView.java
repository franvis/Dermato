package mvp.view;

import bussines.Patient;
import bussines.Visit;

/**
 *
 * @author Francisco Visintini
 */
public interface VisitView extends GeneralView{
    
    public void displayVisitData(Visit visit);
    
    public void displayPatientData(Patient patient);

    public void exitWindow();
    
    public void finishRegisteringVisit();
    
    public void finishUpdatingVisit();
}
