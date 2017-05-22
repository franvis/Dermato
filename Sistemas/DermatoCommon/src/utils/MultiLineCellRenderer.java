package utils;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 * Handles JTable renders when multi line is needed
 *
 * @author Francisco Visintini
 */
public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

    public MultiLineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
        setEditable(false);
    }

    /**
     * Sets all cells characteristics for a certain table.
     *
     * @param table
     * @param value cell value
     * @param isSelected true if cell is selected, false otherwise
     * @param hasFocus true if cell is focused
     * @param row row index
     * @param column column index
     * @return JTextArea to be included in the cell
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        setFont(table.getFont());
        Color color, aux;
        if (isSelected) {
            setForeground(new java.awt.Color(0, 0, 0));
            color = StyleManager.getSecondaryColor(StyleManager.actualColor);
            aux = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50);
            setBackground(aux);
        } else {
            setForeground(table.getForeground());
            color = StyleManager.getTertiaryColor(StyleManager.actualColor);
            aux = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50);
            setBackground(aux);
        }
        setEditable(false);

        // We calculate the amount of characters by line according to the cell width
        int cellWidth = (int) table.getCellRect(row, column, false).getWidth();
        int characterWidth = table.getFontMetrics(table.getFont()).charWidth('Y');
        int charactersByLine = (new BigDecimal((double) cellWidth / characterWidth)).setScale(0, RoundingMode.UP).intValue();;

        //We calculate the height of the cell according to the height of the used font and the amount of characters in the text
        int characterHeight = table.getFontMetrics(table.getFont()).getHeight() + 2;
        if (value != null && value.toString().length() > 0) {
            int linesAmount = (new BigDecimal((double) value.toString().length() / charactersByLine)).setScale(0, RoundingMode.UP).intValue();
            if (table.getRowHeight(row) < characterHeight * linesAmount) {
                table.setRowHeight(row, characterHeight * linesAmount);
            }
        } else {
            table.setRowHeight(row, characterHeight);
        }

        setText((value == null) ? "" : " " + value.toString());
        return this;
    }
}