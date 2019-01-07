package utils;

/**
 * @author Francisco Visintini
 */
public class Constants {

//    //DB connection data
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String USER = "root";
    public static final String PASSWORD = "3654";
    public static final String DB = "dermato";
    public static final String URL = "jdbc:mysql://localhost:3306/dermato?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

//    //File managing data MARQUI
    public static final String COLOR_FILE_PATH = "C:\\Users\\Marcos\\Documents\\Sistema Dermatologico\\Archivos Auxiliares\\Color.dat";
    public static final String AUXILIAR_AUTOMATIC_BACKUP_PATH = "C:\\Users\\Marcos\\Documents\\Sistema Dermatologico\\Backup Automatico\\BackUp.dat";
    public static final String BACKUP_PRCESS_PATH = "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe\"";
    public static final String BACKUPS_FOLDER_PATH = "C:\\Users\\Marcos\\Documents\\Sistema Dermatologico\\Backup Automatico\\";

//    //File managing data PAPI
//    public static final String COLOR_FILE_PATH = "/Applications/Sistema Dermatologico/Archivos Auxiliares/Color.dat";
//    public static final String AUXILIAR_AUTOMATIC_BACKUP_PATH = "/Applications/Sistema Dermatologico/Backup Automatico/BackUp.dat";
//    public static final String BACKUP_PRCESS_PATH = "/usr/local/bin/mysqldump";
//    public static final String BACKUPS_FOLDER_PATH = "/Applications/Sistema Dermatologico/Backup Automatico/";
    
    //OTHERS
    public static final String FULLNAME = "%s, %s";
    public static final String BIRTHDAY_WITH_AGE = "%s(%d años)";
    public static final String SYSTEM_FONT = "Tahoma";
    public static final String SYSTEM_ICON_IMAGE_PATH = "images/system_icon.png";
    
    //DEVELOPMENT ENVIRONMENT
//    public static final String HOST = "localhost";
//    public static final String PORT = "3306";
//    public static final String USER = "root";
//    public static final String PASSWORD = "3282";
//    public static final String DB = "Dermato";
//    public static final String URL = "jdbc:mysql://localhost:3306/Dermato";
//    public static final String COLOR_FILE_PATH = "/home/fran/Desktop/Color.dat";
//    public static final String AUXILIAR_AUTOMATIC_BACKUP_PATH = "/home/fran/Desktop/BackUp.dat";
//    public static final String BACKUP_PRCESS_PATH = "/usr/bin/mysqldump";
//    public static final String BACKUPS_FOLDER_PATH = "/home/fran/Desktop";
}
