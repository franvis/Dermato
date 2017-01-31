/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import static Utils.Constants.SYSTEM_FONT;
import Utils.FileManager;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel;
import gui.Principal;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author fran
 */
public class Start {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(new SyntheticaMauveMetallicLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(Boolean.FALSE);
            SyntheticaLookAndFeel.setWindowsDecorated(false);
            SyntheticaLookAndFeel.setUseSystemFileIcons(true);
            SyntheticaLookAndFeel.setFont(new java.awt.Font(SYSTEM_FONT, 0, 13));

        } catch (ParseException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FileManager.AutomaticBackup();
        
        java.awt.EventQueue.invokeLater(() -> {
            new Principal().setVisible(true);
        });
    }

}
