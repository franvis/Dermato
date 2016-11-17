/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author fran
 */
public class DBConstants {
    
    //TABLES
    public enum Tables { Patient, Antecedents, PrepaidHealthInsurance, Visit };
    
    //SQL STATEMENTS
    
    public static final String DB_NAME = "Dermato";
    
    public static final String MAX_WITH_ALIAS = "MAX(%s) AS %s";
    
    public static final String SIMPLE_WHERE_CONDITION = "%s = ?";
    
    public static final String ORDER_BY_CONDITION = "ORDER BY %s";
    
    public static final String ORDER_BY_COLUMN_ASC = "%s DESC";
    
    public static final String ORDER_BY_COLUMN_DESC = "%s ASC";
    
    public static final String INSERT_WITH_VALUES_ONLY = "INSERT INTO "+DB_NAME
            + ".%s VALUES (%s)";
    public static final String INSERT_WITH_COLUMNS = "INSERT INTO "+DB_NAME
            +".%s (%s) VALUES (%s)";
    
    public static final String SELECT_WITH_COLUMNS = "SELECT %s FROM "+DB_NAME
            +".%s WHERE %s";
    
    public static final String SELECT_WITH_COLUMNS_AND_ORDER = "SELECT %s FROM "+DB_NAME
            +".%s WHERE %s ORDER BY %s";
    
    public static final String SELECT_WITHOUT_COLUMNS = "SELECT * FROM "+DB_NAME
            +".%s WHERE %s";
    
    public static final String SELECT_WITHOUT_COLUMNS_WITH_ORDER = SELECT_WITHOUT_COLUMNS 
            + " ORDER BY %s";
    
    //VISIT TABLE COLUMNS
    public enum VisitDBColumns{ idVisit, date, reason, treatment, 
    complementaryStudies, laboratory, diagnosis, physicalExam, biopsy, patient}
    
    
}
