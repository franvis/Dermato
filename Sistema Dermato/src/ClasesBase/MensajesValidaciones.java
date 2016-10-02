package ClasesBase;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase para el manejo de mensajes al usuario y validaciones de campos
 */
public class MensajesValidaciones {

    /**
     * El componente que origina el mensaje
     */
    private  Component c;
    

    /**
     * Metodo dedicado a la prohibicion de caracteres innecesarios en la escritura de un numero decimal.
     * @param evt Evento de escritura
     * @param ch Componente donde se ejecuto el evento
     */
    public static boolean negarCaracteresFloat(KeyEvent evt, Component ch) {
        char c = evt.getKeyChar();
        boolean resultado = false;
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) ||
        (c == KeyEvent.VK_DELETE) || c == KeyEvent.VK_PERIOD)) {
            ch.getToolkit().beep();
            evt.consume();
            resultado = true;
        }
        return resultado;
    }
    
    
    /**
     * Metodo dedicado a la validacion del largo permitido en la escritura
     * @param txtf Caja de texto a validar
     * @param evt Evento de escritura
     * @param ch Componente donde se ejecuto el evento
     */
    public static void limitarLargo(JTextField txtf, KeyEvent evt, int i,Component ch) {
        if(txtf.getText().length() == i)
        {
            ch.getToolkit().beep();
            evt.consume();
        }
    }
    
    
    /**
     * Metodo dedicado a mostrar por pantalla un error mediante un mensaje
     * @param f Componente donde se ejecuto el evento
     * @param error Error a mostrar
     */
    public static void mostrarError(Component f,String error) {
         JOptionPane.showOptionDialog(
                    f,
                    error,
                    "Atención",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    null,
                    "Aceptar");
    }
     
    
    /**
     * Metodo dedicado a mostrar por pantalla un error mediante un mensaje
     * @param f Componente donde se ejecuto el evento
     * @param info Informacion a mostrar
     */
    public static void mostrarInformacion(Component f,String info)
    {
        JOptionPane.showOptionDialog(
                    f,
                    info,
                    "Atención",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    null,
                    "Aceptar");
    }
    
    /**
     * metodo para validar la salida del sistema mediante un mensaje
     * @param f Componente del mensaje
     */
    public static void validarSalida(Component f){
        int ans = JOptionPane.showConfirmDialog(
                f,
                "¿Realmente desea salir?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION)
            System.exit(0);
    }
    
    /**
     * metodo para validar la salida del sistema
     * @param f Componente del mensaje
     */
    public static void mostrarAcercaDe(Component f){
        JOptionPane.showMessageDialog(
                f,
                "Sistema de Gestión de Pacientes\nVersión 1.5",
                "Acerca De...",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * metodo que corrobora que la fecha sea "valida"
     * 
     * @param d dia
     * @param m mes
     * @param a año
     */
    public static String corroborarFecha(String d, String m, String a) {
    int dia,mes,año;
    String incorrectos="";
    Calendar c = Calendar.getInstance();
    if(!d.isEmpty())
        dia = Integer.parseInt(d);
    else
        dia=0;
    
    if(!m.isEmpty())
        mes = Integer.parseInt(m);  
    else
        mes = 0;
    
    if(!a.isEmpty())
        año = Integer.parseInt(a);
    else
        año = 0;
    
        if(año<=1900 || año > c.get(Calendar.YEAR))
        incorrectos +="Año";
        
        if(mes<=0 || mes>12)
        incorrectos +="Mes \n";
        
        if((mes == 4 || mes == 6 || mes == 9 || mes == 11) && (dia < 0 || dia > 30))
        incorrectos += "Día \n";
        else if((mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) && (dia < 0 || dia > 31))
        incorrectos+="Día \n";
    
    if(isBiciesto(año))
    {
        if (mes == 2 && (dia < 0 || dia > 29))
        incorrectos+="Día \n";
    }
    else
    {
        
        if (mes == 2 && (dia < 0 || dia > 28))
        incorrectos+="Día \n";
    }
    
    return incorrectos;
    }    
    
      
     
      
    /**
     * Metodo dedicado a la prohibicion de letras en la escritura
     * @param evt Evento de escritura
     * @param ch Componente donde se ejecuto el evento
     */
    public static void negarLetras(KeyEvent evt,Component ch) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) ||
        (c == KeyEvent.VK_DELETE))) {
            ch.getToolkit().beep();
            evt.consume();
        }
    }

    /**
     * Metodo dedicado a la prohibicion de numeros en la escritura
     * @param evt Evento de escritura
     * @param ch Componente donde se ejecuto el evento
     */
    public static void negarNumeros(KeyEvent evt,Component ch) {
        char c = evt.getKeyChar();
        if (!(!Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) ||
        (c == KeyEvent.VK_DELETE))) {
            ch.getToolkit().beep();
            evt.consume();
        }
        
    }
    
    /**
     * Metodo dedicado a la prohibicion de letras en la escritura
     * @param evt Evento de escritura
     * @param ch Componente donde se ejecuto el evento
     * @return true si el caracter fue negado, false si es valido.
     */
    public static boolean negarLetrasBool(KeyEvent evt,Component ch) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) ||
        (c == KeyEvent.VK_DELETE))) {
            ch.getToolkit().beep();
            evt.consume();
            return true;
        }
        return false;
    }
    
    /**
     * Metodo dedicado a la prohibicion de numeros en la escritura
     * @param evt Evento de escritura
     * @param ch Componente donde se ejecuto el evento
     * @return true si el caracter fue negado, false si es valido
     */
    public static boolean negarNumerosBool(KeyEvent evt,Component ch) {
        char c = evt.getKeyChar();
        if (!(!Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) ||
        (c == KeyEvent.VK_DELETE))) {
            ch.getToolkit().beep();
            evt.consume();
            return true;
        }
        return false;
    }
    
    /**
     * Metodo dedicado a la validacion del numero de afiliado
     * @param evt Evento de escritura
     * @param ch Componente donde se ejecuto el evento
     */
    public static void validarNumeroAfiliado(KeyEvent evt,Component ch) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) ||
        (c == KeyEvent.VK_DELETE) ||(c== KeyEvent.VK_SLASH) ||(c== KeyEvent.VK_PERIOD) ||(c== KeyEvent.VK_MINUS))) {
            ch.getToolkit().beep();
            evt.consume();
        }
    }
    
    
    /**
     * Metodo utilizado para validar si un año es biciesto
     * @param year año
     * @return true si es biciesto, false si no lo es o es 0
     */
    public static boolean isBiciesto(int year) {
    if(year == 0)
    return false;
    return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }
    
   
    /**
     * metodo para validar la salida de una ventana mediante un mensaje 
     * @param windowToClose ventana a cerrar
     */
    public static void validarSalidaVentana(JDialog windowToClose){
        if (windowToClose == null) return;
        int ans = JOptionPane.showConfirmDialog(
                windowToClose,
                "¿Realmente desea salir sin guardar?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION)
        {
            windowToClose.dispose();
            windowToClose.getOwner().setVisible(true);
        }
    }
    
    /**
     * metodo para validar la salida de una ventana mediante un mensaje 
     * @param windowToClose ventana a cerrar
     */
    public static void validarSalidaVentana(JFrame windowToClose){
        if (windowToClose == null) return;
        int ans = JOptionPane.showConfirmDialog(
                windowToClose,
                "¿Realmente desea salir sin guardar?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION)
        {
            windowToClose.dispose();
            if (windowToClose.getParent() != null)
                windowToClose.getParent().setVisible(true);
        }
    }
}
