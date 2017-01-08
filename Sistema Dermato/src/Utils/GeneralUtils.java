/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author fran
 */
public class GeneralUtils {

    /**
     * Method used to clean a table deleting all of the rows
     *
     * @param dtm DefaultTableModel used to erase the rows
     */
    public static void clearTable(DefaultTableModel dtm) {
        int j = dtm.getRowCount();
        for (int i = 0; i < dtm.getRowCount();) {
            dtm.removeRow(0);
        }
    }

    /**
     * Method used to change the size of a table
     *
     * @param dtm DefaultTableModel to change
     * @param rows new row size
     */
    public static void changeTableSize(DefaultTableModel dtm, int rows) {
        dtm.setNumRows(rows);
    }

    /**
     * Calculates age basing on a String birthday with the format dd/MM/yyyy
     *
     * @param birthday
     * @return age
     */
    public static int calculateAge(String birthday) {
        Calendar calendar = Calendar.getInstance();
        int todayDay = calendar.get(Calendar.DAY_OF_MONTH);
        int todayMonth = calendar.get(Calendar.MONTH) + 1;
        int todayYear = calendar.get(Calendar.YEAR);

        int birthdayDay = Integer.parseInt(birthday.substring(0, 2));
        int birthdayMonth = Integer.parseInt(birthday.substring(3, 5));
        int birthdayYear = Integer.parseInt(birthday.substring(6, 10));

        int age = todayYear - birthdayYear;

        if (todayMonth < birthdayMonth) {
            return age - 1;
        } else if ((todayMonth == birthdayMonth) && (todayDay < birthdayDay)) {
            return age - 1;
        }

        return age;
    }
    
    
    public static void setCustomFont(JButton jbtn, boolean mouseEntering) {
        if (jbtn.isEnabled()) {
            if (mouseEntering) {
                jbtn.setFont(new java.awt.Font("Tahoma", 1, 15));
            } else {
                jbtn.setFont(new java.awt.Font("Tahoma", 1, 14));
            }
        }
    }
    
    /**
     * Handle the focus gain-loss with TAB and shift TAB keys.
     *
     * @param evt
     */
    public static void handleFocus(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_TAB && !evt.isShiftDown()) {
            evt.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
        } else if (evt.getKeyCode() == KeyEvent.VK_TAB
                && evt.isShiftDown()) {
            evt.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
        }
    }
}
