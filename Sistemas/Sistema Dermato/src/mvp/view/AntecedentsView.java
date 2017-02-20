/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.view;

import ClasesBase.Antecedents;

/**
 *
 * @author fran
 */
public interface AntecedentsView extends GeneralView{
    
    public void displayAntecedents(Antecedents antecedents);

    public void exitWindow();
    
    public void finishUpdatingAntecedents();
}
