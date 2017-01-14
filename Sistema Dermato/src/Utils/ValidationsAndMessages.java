package Utils;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Handles messages to user and validations
 */
public class ValidationsAndMessages {

    private static final String EXIT_SYSTEM_MESSAGE = "¿Realmente desea salir?";
    private static final String EXIT_WINDOW_MESSAGE = "¿Realmente desea salir sin guardar?";
    private static final String EXIT_WINDOW_MESSAGE_TITLE = "Confirmación de salida";
    private static final String OPTION_OK = "Aceptar";
    private static final String OPTION_CANCEL = "Cancelar";
    private static final String ATENTION = "Atención";
    private static final String DEFAULT = "default";
    private static final String ABOUT_MESSAGE = "Sistema de Gestión de Pacientes de Dermatologia\nVersión 1.0";
    private static final String ABOUT_TITLE = "Acerca De...";

    /**
     * Validates and denies unnecessary characters for a float field.
     *
     * @param evt writing event
     * @param ch component where event was triggered
     * 
     * @return true if the character was denied, false otherwise
     */
    public static boolean denyCharactersForFloatField(KeyEvent evt, Component ch) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE) || c == KeyEvent.VK_PERIOD)) {
            ch.getToolkit().beep();
            evt.consume();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates and limits the length of the text for a text field
     *
     * @param txtf text field to validate
     * @param evt writing event
     * @param ch component where event was triggered
     * @param textMaxLength maximum length for the given text field
     */
    public static void validateTextLength(JTextField txtf, KeyEvent evt, int textMaxLength, Component ch) {
        if (txtf.getText().length() == textMaxLength) {
            ch.getToolkit().beep();
            evt.consume();
        }
    }

    /**
     * Shows an error to the user
     *
     * @param f component where error has been triggered
     * @param error error to show
     */
    public static void showError(Component f, String error) {
        JOptionPane.showOptionDialog(
                f,
                error,
                ATENTION,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                null,
                OPTION_OK);
    }

    /**
     * Shows information to the user
     *
     * @param f component where info is wanted to be shown
     * @param info information to show
     */
    public static void showInfo(Component f, String info) {
        JOptionPane.showOptionDialog(
                f,
                info,
                ATENTION,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                OPTION_OK);
    }

    /**
     * Validates system exiting asking to the user if it's sure.
     *
     * @param f message component
     */
    public static void validateExit(Component f) {
        int ans = JOptionPane.showConfirmDialog(
                f,
                EXIT_SYSTEM_MESSAGE,
                EXIT_WINDOW_MESSAGE_TITLE,
                JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Shows about this dialog to user
     *
     * @param f message component
     */
    public static void showAbout(Component f) {
        JOptionPane.showMessageDialog(
                f,
                ABOUT_MESSAGE,
                ABOUT_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Validates that a certain date is inside the common ranges
     *
     * @param d day
     * @param m month
     * @param a year
     *
     * @return empty string if it's in range and a wrong fields string if it
     * isn't
     */
    public static String validateDateInCommonRange(String d, String m, String a) {
        int day, month, year;
        String wrongFields = "";
        Calendar c = Calendar.getInstance();
        if (!d.isEmpty()) {
            day = Integer.parseInt(d);
        } else {
            day = 0;
        }

        if (!m.isEmpty()) {
            month = Integer.parseInt(m);
        } else {
            month = 0;
        }

        if (!a.isEmpty()) {
            year = Integer.parseInt(a);
        } else {
            year = 0;
        }

        if (year <= 1900 || year > c.get(Calendar.YEAR)) {
            wrongFields += "Año";
        }

        if (month <= 0 || month > 12) {
            wrongFields += "Mes \n";
        }

        if ((month == 4 || month == 6 || month == 9 || month == 11) && (day < 0 || day > 30)) {
            wrongFields += "Día \n";
        } else if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day < 0 || day > 31)) {
            wrongFields += "Día \n";
        }

        if (isLeapYear(year)) {
            if (month == 2 && (day < 0 || day > 29)) {
                wrongFields += "Día \n";
            }
        } else if (month == 2 && (day < 0 || day > 28)) {
            wrongFields += "Día \n";
        }

        return wrongFields;
    }

    /**
     * Denies letter characters in a certain component.
     *
     * @param evt writing event
     * @param component component where event was triggered
     * @return true if the character is denied, false otherwise
     */
    public static boolean denyLetterCharacter(KeyEvent evt, Component component) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            component.getToolkit().beep();
            evt.consume();
            return true;
        }
        return false;
    }

    /**
     * Denies numbers characters in a certain component.
     *
     * @param evt writing event
     * @param component component where event was triggered
     * @return true if the character is denied, false otherwise
     */
    public static boolean denyNumberCharacter(KeyEvent evt, Component component) {
        char c = evt.getKeyChar();
        if (!(!Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            component.getToolkit().beep();
            evt.consume();
            return true;
        }
        return false;
    }

    /**
     * Validates character entries for Health Insurance Number
     *
     * @param evt writing event
     * @param component Component where event was triggered
     */
    public static void validateHealthInsuranceNumberCharacter(KeyEvent evt, Component component) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SLASH) || (c == KeyEvent.VK_PERIOD) || (c == KeyEvent.VK_MINUS))) {
            component.getToolkit().beep();
            evt.consume();
        }
    }

    /**
     * Validates leap years
     *
     * @param year
     * @return true if leap, false otherwise
     */
    public static boolean isLeapYear(int year) {
        if (year == 0) {
            return false;
        }
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    /**
     * Validates window exiting asking to the user if it's sure to do it.
     *
     * @param windowToClose
     */
    public static void validateWindowExit(JDialog windowToClose) {
        if (windowToClose == null) {
            return;
        }
        int ans = JOptionPane.showOptionDialog(windowToClose,
                EXIT_WINDOW_MESSAGE,
                EXIT_WINDOW_MESSAGE_TITLE,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{OPTION_OK, OPTION_CANCEL},
                DEFAULT);
        if (ans == JOptionPane.YES_OPTION) {
            windowToClose.dispose();
            windowToClose.getOwner().setVisible(true);
        }
    }

    /**
     * Validates window exiting asking to the user if it's sure.
     *
     * @param windowToClose
     */
    public static void validateWindowExit(JFrame windowToClose) {
        if (windowToClose == null) {
            return;
        }
        int ans = JOptionPane.showOptionDialog(windowToClose,
                EXIT_WINDOW_MESSAGE,
                EXIT_WINDOW_MESSAGE_TITLE,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{OPTION_OK, OPTION_CANCEL},
                DEFAULT);
        if (ans == JOptionPane.YES_OPTION) {
            windowToClose.dispose();
            if (windowToClose.getParent() != null) {
                windowToClose.getParent().setVisible(true);
            }
        }
    }
}
