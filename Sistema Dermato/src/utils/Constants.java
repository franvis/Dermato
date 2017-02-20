package utils;

/**
 *
 * @author Denise
 */
public class Constants {
    
//    No Modificar - Entorno de instalación
//    Datos de Conexión con BD
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String USER = "root";
    public static final String PASSWORD = "3282";
    public static final String DB = "Dermato";
    public static final String URL = "jdbc:mysql://localhost:3306/Dermato";
    
    //Datos para el manejo de Archivos
    public static final String COLOR_FILE_PATH = "C:\\Program Files\\Sistema Dermatologico\\Archivos Auxiliares\\Color.dat";
    public static final String AUXILIAR_AUTOMATIC_BACKUP_PATH = "C:\\Program Files\\Sistema Dermatologico\\Backup Automatico\\BackUp.dat";
    public static final String BACKUP_PRCESS_PATH = "\"C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump.exe\"";
    public static final String BACKUPS_FOLDER_PATH = "C:\\Program Files\\Sistema Dermatologico\\Backup Automatico\\";
    
    //OTHERS
    public static final String FULLNAME = "%s, %s";
    public static final String BIRTHDAY_WITH_AGE = "%s(%d años)";
    public static final String SYSTEM_FONT = "Tahoma";
    public static final String SYSTEM_ICON_IMAGE_PATH = "Imagenes/sistema.png";
}
