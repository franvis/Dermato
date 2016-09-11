/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesBase;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 * Clase para el manejo de los estilos de JTables
 * @author Denise
 */
public class MultiLineCellRenderer extends JTextArea implements TableCellRenderer
{
    /**
     * Constructor por defecto
     */
    public MultiLineCellRenderer() {  
        setLineWrap(true);  
        setWrapStyleWord(true);  
        setOpaque(true);  
        setEditable(false);
    }  
    /**
     * Para setear las características de cada celda en una tabla
     * @param table JTable tabla a la que pertenece la celda
     * @param value Object el valor de la celda
     * @param isSelected boolean true si la celda está seleccionada
     * @param hasFocus boolean true si la celda posee el foco
     * @param row int índice de la fila a la que pertenece la celda
     * @param column int índice de la columna a la que pertenece la celda
     * @return el JTextArea a incluirse dentro de la celda
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setFont(table.getFont());
        Color color, aux;
        if (isSelected) {  
            setForeground(new java.awt.Color(0,0,0));  
            color = GestorEstilos.getColorSecundario(GestorEstilos.colorActual);
            aux = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50);
            setBackground(aux);  
        }  
        else {  
            setForeground(table.getForeground());  
            color = GestorEstilos.getColorTerciario(GestorEstilos.colorActual);
            aux = new Color(color.getRed(), color.getGreen(), color.getBlue(), 50);
            setBackground(aux);  
        }
        setEditable(false);
        
        //Calculamos la cantidad de caracteres por linea según el ancho de la celda
        int anchoCelda = (int) table.getCellRect(row, column, false).getWidth();
        int anchoCaracter = table.getFontMetrics(table.getFont()).charWidth('Y');
        int caracteresXLinea = (new BigDecimal((double) anchoCelda / anchoCaracter)).setScale(0, RoundingMode.UP).intValue();;
        
        //Calculamos la altura de la celda según la altura de la fuente usada y la cant de caracteres en el texto
        int altoCaracter = table.getFontMetrics(table.getFont()).getHeight()+2;
        if(value!= null && value.toString().length() > 0 ){
            int cantLineas = (new BigDecimal((double) value.toString().length()/caracteresXLinea)).setScale(0, RoundingMode.UP).intValue();
            if(table.getRowHeight(row)< altoCaracter*cantLineas)
                table.setRowHeight(row,altoCaracter*cantLineas);
        }
        else
            table.setRowHeight(row, altoCaracter);
        
        setText((value == null) ? "" : value.toString()); 
        return this;
    }
}
