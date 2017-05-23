package mvp.view;

import bussines.Antecedents;

/**
 *
 * @author Francisco Visintini
 */
public interface AntecedentsView extends GeneralView{
    
    public void displayAntecedents(Antecedents antecedents);

    public void exitWindow();
    
    public void finishUpdatingAntecedents();
}
