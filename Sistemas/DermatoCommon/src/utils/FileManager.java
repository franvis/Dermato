package utils;

import java.awt.Component;
import java.io.*;
import java.util.Calendar;

/**
 * Handles interaction with configuration files.
 *
 * @author Francisco Visintini
 */
public class FileManager {

    private static final int BUFFER = 1024;
    //to save in RAM
    private static StringBuffer temp = null;
    //to save SQL file
    private static FileWriter backupFile = null;
    private static PrintWriter pw = null;
    //DB data
    private final static String HOST = Constants.HOST;
    private final static String PORT = Constants.PORT;
    private final static String USER = Constants.USER;
    private final static String PASSWORD = Constants.PASSWORD;
    private final static String DB = Constants.DB;
    private static String day, month, year, hour, minute, second;

    /**
     * Performs a data base backup.
     *
     * @param component Component that calls the method
     * @param finalLocation Backup file final location
     * @return true if correctly backed up, false otherwise
     */
    public static boolean manualBackUp(Component component, File finalLocation) {
        try {
            if (finalLocation == null) {
                return false;
            }

            Process run = Runtime.getRuntime().exec(
                    Constants.BACKUP_PRCESS_PATH + " --host=" + HOST + " --port=" + PORT
                    + " --user=" + USER + " --password=" + PASSWORD
                    + " --compact --database sistemacarla --add-drop-database --complete-insert --extended-insert --skip-quote-names"
                    + " --skip-comments --skip-triggers " + DB);

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
            day = c.get(Calendar.DAY_OF_MONTH) + "";
            month = c.get(Calendar.MONTH) + 1 + "";
            year = c.get(Calendar.YEAR) + "";
            hour = c.get(Calendar.HOUR) + "";
            minute = c.get(Calendar.MINUTE) + "";
            second = c.get(Calendar.SECOND) + "";

            backupFile = new FileWriter(finalLocation.getAbsolutePath() + "\\BackUp " + day + "-" + month + "-" + year + "-" + hour + "h" + minute + "m" + second + "s.sql");
            pw = new PrintWriter(backupFile);
            pw.println(temp.toString());

            ValidationsAndMessages.showInfo(component, "Back Up exitoso en " + finalLocation.getAbsolutePath());
            run.destroy();
            return true;
        } catch (IOException e) {
            ValidationsAndMessages.showError(component, "Back Up fallido en " + finalLocation.getAbsolutePath() + ". " + e.getMessage());
            return false;
        } finally {
            try {
                if (null != backupFile) {
                    backupFile.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Performs a backup.
     */
    public static void backUp() {
        try {
            Process run = Runtime.getRuntime().exec(
                    Constants.BACKUP_PRCESS_PATH + " --host=" + HOST + " --port=" + PORT
                    + " --user=" + USER + " --password=" + PASSWORD
                    + " --compact --complete-insert --extended-insert --skip-quote-names"
                    + " --skip-comments --skip-triggers " + DB);

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

            File archBackUp = new File(Constants.BACKUPS_FOLDER_PATH + "\\BackUp.sql");
            archBackUp.delete();
            archBackUp.createNewFile();

            backupFile = new FileWriter(archBackUp);
            pw = new PrintWriter(backupFile);
            pw.print(temp.toString());

        } catch (IOException e) {
            ValidationsAndMessages.showError(null, "Back Up automático fallido en " + Constants.BACKUPS_FOLDER_PATH + ". " + e.getMessage());
        } finally {
            try {
                if (null != backupFile) {
                    backupFile.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Saves a color preference.
     * 
     * @param component
     * @param color 
     * 
     * @return true if correctly saved, false otherwise
     */
    public static boolean saveColor(Component component, int color) {
        try {
            //We create the folder if it doesn't exist
//            File folder = new File(Constants.COLOR_FILE_PATH.substring(0, Constants.COLOR_FILE_PATH.lastIndexOf("\\")));
            File folder = new File(Constants.COLOR_FILE_PATH.substring(0, Constants.COLOR_FILE_PATH.lastIndexOf("/")));
            if (!folder.exists()) {
                folder.mkdir();
            }
            //We create the color file
            File oldFile = new File(Constants.COLOR_FILE_PATH);
            oldFile.delete();
            oldFile.createNewFile();
            backupFile = new FileWriter(oldFile);
            pw = new PrintWriter(backupFile);
            pw.println(color + "");
        } catch (Exception e) {
            ValidationsAndMessages.showError(component, "No se pudo guardar el código de color. Error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (null != backupFile) {
                    backupFile.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return true;
    }

    /**
     * Reads a color preference.
     * 
     * @return preferred color
     */
    public static int readColor() {
        try {
            File colorFile = new File(Constants.COLOR_FILE_PATH);
            if (!colorFile.canRead()) {
                saveColor(null, StyleManager.actualColor);
                return StyleManager.actualColor;
            }
            FileReader fr = new FileReader(new File(Constants.COLOR_FILE_PATH));
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
        } catch (IOException | NumberFormatException e) {
            return 0;
        } finally {
            try {
                if (null != backupFile) {
                    backupFile.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
