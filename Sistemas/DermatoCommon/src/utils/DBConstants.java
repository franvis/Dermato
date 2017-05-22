package utils;

/**
 * @author Francisco Visintini
 */
public class DBConstants {
    
    //SQL STATEMENTS
    
    public static final int INNER_JOIN = 0;
    
    public static final int LEFT_JOIN = -1;
    
    public static final int RIGHT_JOIN = 1;
    
    public static final String DB_NAME = "Dermato";
    
    public static final String STR_TO_DATE_UPDATE_COLUMN = "%s = str_to_date(?, '%%d/%%c/%%Y')";
    
    public static final String MAX_WITH_ALIAS = "MAX(%s) AS %s";
    
    public static final String SIMPLE_WHERE_CONDITION = "%s LIKE ?";
    
    public static final String IS_NULL_WHERE_CONDITION = "%s IS NULL";
    
    public static final String ORDER_BY_CONDITION = " ORDER BY %s";
    
    public static final String GROUP_BY_CONDITION = " GROUP BY %s";
    
    public static final String ORDER_BY_COLUMN_ASC = "%s ASC";
    
    public static final String ORDER_BY_COLUMN_DESC = "%s DESC";
    
    public static final String MAX_COLUMN_AS = "MAX(%s) AS %s";
    
    public static final String JOIN = DB_NAME+".%s %s "+DB_NAME+".%s ON %s=%s";
    
    public static final String APPEND_JOIN = "%s %s "+DB_NAME+".%s ON %s=%s";
    
    public static final String COLUMN_WITH_TABLE_PREFIX = "%s.%s";
    
    //INSERT STATEMENTS
    
    public static final String INSERT_WITH_VALUES_ONLY = "INSERT INTO "+DB_NAME
            + ".%s VALUES (%s)";
    public static final String INSERT_WITH_COLUMNS = "INSERT INTO "+DB_NAME
            +".%s (%s) VALUES (%s)";
    
    //SELECT STATEMENTS
    
    public static final String SELECT_ALL = "SELECT * FROM "+DB_NAME
            +".%s";
 
    public static final String SELECT_ALL_WITH_WHERE = "SELECT * FROM "+DB_NAME
            +".%s WHERE %s";
    
    public static final String SELECT_ALL_MULTIPLE_TABLES_WITH_WHERE = "SELECT * FROM %s WHERE %s";
    
    public static final String SELECT_ALL_WITH_ORDER = SELECT_ALL 
            + ORDER_BY_CONDITION;
    
    public static final String SELECT_ALL_WITH_WHERE_AND_ORDER = SELECT_ALL_WITH_WHERE 
            + ORDER_BY_CONDITION;
    
    public static final String SELECT_COLUMNS_WITH_WHERE = "SELECT %s FROM "+DB_NAME
            +".%s WHERE %s";

    public static final String SELECT_COLUMNS_MULTIPLE_TABLES_WITH_WHERE = "SELECT %s FROM %s WHERE %s";
    
    public static final String SELECT_COLUMNS_MULTIPLE_TABLES_WITH_WHERE_AND_GROUP_BY = SELECT_COLUMNS_MULTIPLE_TABLES_WITH_WHERE + GROUP_BY_CONDITION;
    
    public static final String SELECT_COLUMNS_WITH_WHERE_GROUP_BY_AND_ORDER = SELECT_COLUMNS_WITH_WHERE 
            + GROUP_BY_CONDITION + ORDER_BY_CONDITION;
    
    public static final String SELECT_COLUMNS_MULTIPLE_TABLES_WITH_WHERE_GROUP_BY_AND_ORDER = SELECT_COLUMNS_MULTIPLE_TABLES_WITH_WHERE 
            + GROUP_BY_CONDITION + ORDER_BY_CONDITION;
    
    public static final String SELECT_COLUMNS_WITH_WHERE_AND_ORDER = "SELECT %s FROM "+DB_NAME
            +".%s WHERE %s ORDER BY %s";
    
    public static final String SELECT_ONE_WITH_WHERE = "SELECT 1 FROM "+DB_NAME
            +".%s WHERE %s";
    
    //UPDATE STATEMENTS
    public static final String UPDATE = "UPDATE "+ DB_NAME+".%s SET %s WHERE %s";
    
    //DELETE STATEMENTS
    public static final String DELETE = "DELETE FROM "+ DB_NAME +".%s WHERE %s";
    
    //TABLES
    public enum Tables { Patient, Antecedents, MedicalCoverage, Visit, DniType };
}
