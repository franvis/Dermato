package utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Class that handle styles along the application
 *
 * @author Francisco Visintini
 */
public class StyleManager {

    public static int actualColor = 1;

    public static final Color DEFAULT_TEXT_COLOR = new Color(153, 153, 153);

    /* 0 - Por defecto
     * 1 - Palid rose
     * 2 - Old rose
     * 3 - Green
     * 4 - Yellow
     * 5 - Blue
     * 6 - Purple
     */
    /**
     * Retrieves primary color in the color pallet to use.
     *
     * @param color Representative code of the color pallet
     * @return Color Primary color of the color pallet
     */
    public static Color getPrimaryColor(int color) {
        int r, g, b;
        switch (color) {
            case 0:
                r = 240;
                g = 240;
                b = 240;
                break;
            case 1:
                r = 255;
                g = 204;
                b = 204;
                return Color.getHSBColor(r, g, 0.62f * b);
            case 2:
                r = 250;
                g = 174;
                b = 174;
                break;
            case 3:
                r = 179;
                g = 225;
                b = 194;
                break;
            case 4:
                r = 255;
                g = 252;
                b = 204;
                return Color.getHSBColor(r, g, 0.28f * b);
            case 5:
                r = 225;
                g = 225;
                b = 254;
                break;
            case 6:
                r = 240;
                g = 208;
                b = 248;
                break;
            default:
                r = 240;
                g = 240;
                b = 240;
        }
        return new Color(r, g, b);
    }

    /**
     * Retrieves secondary color in the color pallet to use.
     *
     * @param color Representative code of the color pallet
     * @return Color Secondary color of the color pallet
     */
    public static Color getSecondaryColor(int color) {
        int r, g, b;
        switch (color) {
            case 0:
                r = 228;
                g = 228;
                b = 241;
                break;
            case 1:
                r = 235;
                g = 197;
                b = 218;
                break;
            case 2:
                r = 255;
                g = 204;
                b = 204;
                break;
            case 3:
                r = 176;
                g = 205;
                b = 186;
                break;
            case 4:
                r = 253;
                g = 222;
                b = 160;
                break;
            case 5:
                r = 204;
                g = 204;
                b = 255;
                break;
            case 6:
                r = 232;
                g = 190;
                b = 245;
                break;
            default:
                r = 255;
                g = 240;
                b = 240;
        }
        return new Color(r, g, b);
    }

    /**
     * Retrieves tertiary color in the color pallet to use.
     *
     * @param color Representative code of the color pallet
     * @return Color tertiary color of the color pallet
     */
    public static Color getTertiaryColor(int color) {
        int r, g, b;
        switch (color) {
            case 0:
                r = 255;
                g = 255;
                b = 255;
                break;
            case 1:
                r = 250;
                g = 230;
                b = 250;
                break;
            case 2:
                r = 250;
                g = 230;
                b = 250;
                break;
            case 3:
                r = 220;
                g = 240;
                b = 240;
                break;
            case 4:
                r = 249;
                g = 249;
                b = 222;
                break;
            case 5:
                r = 229;
                g = 229;
                b = 250;
                break;
            case 6:
                r = 244;
                g = 224;
                b = 244;
                break;
            default:
                r = 240;
                g = 240;
                b = 240;
        }
        return new Color(r, g, b);
    }

    /**
     * Returns tertiary color according to current style.
     *
     * @return actual tertiary color
     */
    public static Color getTertiaryColor() {
        return StyleManager.getTertiaryColor(actualColor);
    }

    /**
     * Retrieves text color in the color pallet to use.
     *
     * @param color Representative text color in the current pallet.
     *
     * @return Color
     */
    public static Color getTextColor(int color) {
        int r, g, b;
        switch (color) {
            case 0:
                r = 0;
                g = 0;
                b = 0;
                break;
            case 1:
                r = 57;
                g = 4;
                b = 4;
                break;
            case 2:
                r = 57;
                g = 4;
                b = 4;
                break;
            case 3:
                r = 0;
                g = 51;
                b = 51;
                break;
            case 4:
                r = 91;
                g = 41;
                b = 2;
                break;
            case 5:
                r = 0;
                g = 51;
                b = 102;
                break;
            case 6:
                r = 61;
                g = 1;
                b = 61;
                break;
            default:
                r = 0;
                g = 0;
                b = 0;
        }
        return new Color(r, g, b);
    }

    /**
     * Returns text color according to actual style.
     *
     * @return Color
     */
    public static Color getTextColor() {
        return getTextColor(actualColor);
    }

    /**
     * Paints a container according to saved color.
     *
     * @param container
     */
    public static void paint(Container container) {
        try {
            actualColor = FileManager.readColor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        paintComponents(container, actualColor);
    }

    /**
     * Paints all component child from a container.
     *
     * @param container
     * @param color
     */
    public static void paintComponents(Container container, int color) {
        for (Component componente : container.getComponents()) {
            if (componente instanceof JRadioButton) {
                break;
            }
            //Background color
            if (componente instanceof JTextArea) {
                componente.setBackground(getTertiaryColor(color));
                ((JTextArea) componente).setDisabledTextColor(getTextColor(color));
                ((JTextArea) componente).setSelectionColor(getSecondaryColor(color));
            } else if (componente instanceof JTextField) {
                componente.setBackground(getTertiaryColor(color));
                ((JTextField) componente).setDisabledTextColor(getTextColor(color));
                ((JTextField) componente).setSelectionColor(getSecondaryColor(color));
            } else if (componente instanceof JButton) {
                componente.setBackground(getSecondaryColor(color));
            } else if (componente instanceof JTable) {
                componente.setBackground(getTertiaryColor(color));
            } else if (componente instanceof JComboBox) {
                JComboBox aux = (JComboBox) componente;
                ComboBoxEditor editor = aux.getEditor();
                JTextField etf = (JTextField) editor.getEditorComponent();
                etf.setDisabledTextColor(StyleManager.getTextColor(color));
                etf.setBackground(StyleManager.getTertiaryColor(color));
            } else {
                componente.setBackground(getPrimaryColor(color));
            }

            //Font setting
            if (componente instanceof JButton && componente.isEnabled()) {
                componente.setForeground(getTextColor(color));
            }

            //Title in panel borders setting
            if (componente instanceof JPanel) {
                JPanel comp = (JPanel) componente;
                if (comp.getBorder() instanceof TitledBorder) {
                    String texto = ((TitledBorder) comp.getBorder()).getTitle();
                    comp.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), texto, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 15), getTextColor(actualColor)));
                }
            }
            if (componente instanceof Container || componente instanceof JPanel) {
                paintComponents((Container) componente, color);
            }
        }
    }
}
