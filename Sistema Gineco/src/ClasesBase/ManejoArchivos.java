package ClasesBase;

import java.awt.Component;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase para manejas las interacciones con archivos físicos
 * @author Denise
 */
public class ManejoArchivos {

    private static final int BUFFER = 1024;
    //para guardar en memoria
    private static StringBuffer temp = null;
    //para guardar el archivo SQL
    private static FileWriter fichero = null;
    private static PrintWriter pw = null;
    //datos de la BD
    private static String host = VariablesLocales.host;
    private static String port = VariablesLocales.port;
    private static String user = VariablesLocales.user;
    private static String password = VariablesLocales.password;
    private static String db = VariablesLocales.db;
    private static String dia, mes, año, hora, minuto, segundo;

    /**
     * Método de realización de Back Up manual de la Base de Datos
     * @param j Component que llama al método
     * @param destino File ubicación final del archivo
     * @return true de realizar exitosamente el backup, false de los contrario
     */
    public static boolean backUp(Component j, File destino) {
        try {
            if (destino == null) {
                return false;
            }

            Process run = Runtime.getRuntime().exec(
                    VariablesLocales.rutaProcesoBackUp + " --host=" + host + " --port=" + port
                    + " --user=" + user + " --password=" + password
                    + " --compact --database sistemacarla --add-drop-database --complete-insert --extended-insert --skip-quote-names"
                    + " --skip-comments --skip-triggers " + db);

            InputStream in = run.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            temp = new StringBuffer();
            temp.append("/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;\n");
            int count;
            char[] cbuf = new char[BUFFER];
            while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
                temp.append(cbuf, 0, count);
            }
            br.close();
            in.close();
            temp.append("/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;");
            
            Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH) + "";
            mes = c.get(Calendar.MONTH) + 1 + "";
            año = c.get(Calendar.YEAR) + "";
            hora = c.get(Calendar.HOUR) + "";
            minuto = c.get(Calendar.MINUTE) + "";
            segundo = c.get(Calendar.SECOND) + "";

            fichero = new FileWriter(destino.getAbsolutePath() + "\\BackUp " + dia + "-" + mes + "-" + año + "-" + hora + "h" + minuto + "m" + segundo + "s.sql");
            pw = new PrintWriter(fichero);
            pw.println(temp.toString());

            MensajesValidaciones.mostrarInformacion(j, "Back Up exitoso en " + destino.getAbsolutePath());
            run.destroy();
            return true;
        } catch (IOException e) {
            MensajesValidaciones.mostrarError(j, "Back Up fallido en " + destino.getAbsolutePath() + ". " + e.getMessage());
            return false;
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * Método de realización de Back Up automático cada 30 días de la Base de Datos
     */
    public static void backUpAutomatico() {
        try {
                Process run = Runtime.getRuntime().exec(
                        VariablesLocales.rutaProcesoBackUp + " --host=" + host + " --port=" + port
                        + " --user=" + user + " --password=" + password
                        + " --compact --complete-insert --extended-insert --skip-quote-names"
                        + " --skip-comments --skip-triggers " + db);

                InputStream in = run.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                temp = new StringBuffer();
                int count;
                char[] cbuf = new char[BUFFER];
                while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
                    temp.append(cbuf, 0, count);
                }
                br.close();
                in.close();

                File archBackUp = new File(VariablesLocales.rutaCarpetaBackUps + "\\BackUp.sql");
                archBackUp.delete();
                archBackUp.createNewFile();
                
                fichero = new FileWriter(archBackUp);
                pw = new PrintWriter(fichero);
                pw.print(temp.toString());
            
        } catch (IOException e) {
            MensajesValidaciones.mostrarError(null, "Back Up automático fallido en " + VariablesLocales.rutaCarpetaBackUps + ". " + e.getMessage());
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * Método de realización de Back Up automático cada 30 días de la Base de Datos
     */
    
    public static void backUpAutomatico30Dias() {
        try {
            if(esHoyBackUp())
            {
                Process run = Runtime.getRuntime().exec(
                        VariablesLocales.rutaProcesoBackUp + " --host=" + host + " --port=" + port
                        + " --user=" + user + " --password=" + password
                        + " --compact --complete-insert --extended-insert --skip-quote-names"
                        + " --skip-comments --skip-triggers " + db);

                InputStream in = run.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                temp = new StringBuffer();
                int count;
                char[] cbuf = new char[BUFFER];
                while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
                    temp.append(cbuf, 0, count);
                }
                br.close();
                in.close();

                Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH) + "";
                mes = c.get(Calendar.MONTH) + 1 + "";
                año = c.get(Calendar.YEAR) + "";
                hora = c.get(Calendar.HOUR) + "";
                minuto = c.get(Calendar.MINUTE) + "";
                segundo = c.get(Calendar.SECOND) + "";

                fichero = new FileWriter(VariablesLocales.rutaCarpetaBackUps + "\\BackUp " + dia + "-" + mes + "-" + año + "-" + hora + "h" + minuto + "m" + segundo + "s.sql");
                pw = new PrintWriter(fichero);
                pw.print(temp.toString());
            }
        } catch (IOException e) {
            MensajesValidaciones.mostrarError(null, "Back Up automático fallido en " + VariablesLocales.rutaCarpetaBackUps + ". " + e.getMessage());
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * Determina si en la fecha actual -del sistema- se debe realizar el Back Up automático.
     * Realización de Back Up cada 30 días.
     * @return true de tener que realizarse el Back Up, false de lo contrario
     */
    
    private static boolean esHoyBackUp() {
        boolean eshoy = false;
        try
        {
            Calendar c = Calendar.getInstance();
            int dia = c.get(Calendar.DAY_OF_MONTH);
            int mes = c.get(Calendar.MONTH) + 1;
            int año = c.get(Calendar.YEAR);
            hora = c.get(Calendar.HOUR) + "";
            minuto = c.get(Calendar.MINUTE) + "";
            segundo = c.get(Calendar.SECOND) + "";
            File archBackUp = new File(VariablesLocales.rutaAuxiliarBackUpAutomatico);
            String info = "";
            if (archBackUp.canRead()) {
                FileReader fr = new FileReader(archBackUp);
                BufferedReader br = new BufferedReader(fr);
                temp = new StringBuffer();
                int count;
                char[] cbuf = new char[BUFFER];
                while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
                    temp.append(cbuf, 0, count);
                }
                br.close();
                fr.close();
                info = temp.toString();
            }
            if (!info.isEmpty()) {
                String fecha = info.substring(0, info.lastIndexOf('/')+5);
                int diaGuardado = Integer.parseInt(fecha.substring(0, fecha.indexOf('/')));
                int mesGuardado = Integer.parseInt(fecha.substring(fecha.indexOf('/')+1, fecha.lastIndexOf('/')));
                int añoGuardado = Integer.parseInt(fecha.substring(fecha.lastIndexOf('/')+1));
                GregorianCalendar fechaActual = new GregorianCalendar(año, mes, dia);
                GregorianCalendar fechaGuardada = new GregorianCalendar(añoGuardado, mesGuardado, diaGuardado);
                int diasPasados = fechaActual.get(GregorianCalendar.DAY_OF_YEAR) - fechaGuardada.get(GregorianCalendar.DAY_OF_YEAR);
                if(diasPasados > 30)
                    eshoy = true;
            }
            else
                eshoy = true;
            archBackUp.delete();
            fichero = new FileWriter(VariablesLocales.rutaAuxiliarBackUpAutomatico);
            pw = new PrintWriter(fichero);
            pw.println(dia + "/" + mes + "/" + año);
        
        } catch (IOException e) {
            MensajesValidaciones.mostrarError(null, "Imposible leer archivo de back up en " + VariablesLocales.rutaAuxiliarBackUpAutomatico + ". " + e.getMessage());
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return eshoy;
    }

    public static void guardarColor(Component j, int color) {
        try {
            //Creamos la carpeta si no existe
            File carpeta = new File(VariablesLocales.rutaArchivoColor.substring(0, VariablesLocales.rutaArchivoColor.lastIndexOf("\\")));
            if (!carpeta.exists())
                carpeta.mkdir();
            //Creamos el archivo de color
            File archViejo = new File(VariablesLocales.rutaArchivoColor);
            archViejo.delete();
            archViejo.createNewFile();
            fichero = new FileWriter(archViejo);
            pw = new PrintWriter(fichero);
            pw.println(color + "");
        } catch (Exception e) {
            MensajesValidaciones.mostrarError(j, "No se pudo guardar el código de color.");
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static int leerColor() {
        try {
            File archivoColor = new File(VariablesLocales.rutaArchivoColor);
            if (!archivoColor.canRead()) {
                guardarColor(null, 0);            
                return 0;
            }
            FileReader fr = new FileReader(new File(VariablesLocales.rutaArchivoColor));
            BufferedReader br = new BufferedReader(fr);
            temp = new StringBuffer();
            int count;
            char[] cbuf = new char[BUFFER];
            while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
                temp.append(cbuf, 0, count);
            }
            br.close();
            fr.close();
            int a = Integer.parseInt(temp.toString().substring(0, 1));
            return a;
        } catch (Exception e) {
            //MensajesValidaciones.mostrarError(null, "Imposible leer color desde " + VariablesLocales.rutaArchivoColor + ". " + e.getMessage());
            return 0;
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}