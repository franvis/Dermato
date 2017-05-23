package start;

import static utils.Constants.SYSTEM_FONT;
import utils.FileManager;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel;
import gui.PrincipalJFrame;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Francisco Visintini
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
            javax.swing.UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        } catch (ParseException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PrincipalJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        FileManager.backUp();

        java.awt.EventQueue.invokeLater(() -> {
            new PrincipalJFrame().setVisible(true);
        });
    }
}
