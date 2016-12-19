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
    public enum Tables { Patient, Antecedents, PrePaidHealthInsurance, Visit };
    
    //SQL STATEMENTS
    
    public static final String DB_NAME = "Dermato";
    
    public static final String MAX_WITH_ALIAS = "MAX(%s) AS %s";
    
    public static final String SIMPLE_WHERE_CONDITION = "%s = ?";
    
    public static final String IS_NULL_WHERE_CONDITION = "%s IS NULL";
    
    public static final String ORDER_BY_CONDITION = "ORDER BY %s";
    
    public static final String ORDER_BY_COLUMN_ASC = "%s DESC";
    
    public static final String ORDER_BY_COLUMN_DESC = "%s ASC";
    
    //INSERT STATEMENTS
    
    public static final String INSERT_WITH_VALUES_ONLY = "INSERT INTO "+DB_NAME
            + ".%s VALUES (%s)";
    public static final String INSERT_WITH_COLUMNS = "INSERT INTO "+DB_NAME
            +".%s (%s) VALUES (%s)";
    
    //SELECT STATEMENTS
    
    public static final String SELECT_ALL_WITHOUT_WHERE = "SELECT * FROM "+DB_NAME
            +".%s";
    
    public static final String SELECT_WITH_COLUMNS = "SELECT %s FROM "+DB_NAME
            +".%s WHERE %s";
    
    public static final String SELECT_WITH_COLUMNS_AND_ORDER = "SELECT %s FROM "+DB_NAME
            +".%s WHERE %s ORDER BY %s";
    
    public static final String SELECT_WITHOUT_COLUMNS = "SELECT * FROM "+DB_NAME
            +".%s WHERE %s";
    
    public static final String SELECT_WITHOUT_COLUMNS_WITH_ORDER = SELECT_WITHOUT_COLUMNS 
            + " ORDER BY %s";
    
    public static final String SELECT_WITHOUT_COLUMNS_WITHOUT_WHERE_WITH_ORDER = SELECT_ALL_WITHOUT_WHERE 
            + " ORDER BY %s";
    
    //UPDATE STATEMENTS
    public static final String UPDATE = "UPDATE "+ DB_NAME+".%s SET %s WHERE %s";
    
    //DELETE STATEMENTS
    public static final String DELETE = "DELETE FROM "+ DB_NAME +".%s WHERE %s";
    
    // TABLE COLUMNS
    public enum VisitDBColumns{ idVisit, date, reason, treatment, 
    complementaryStudies, laboratory, diagnosis, physicalExam, biopsy, patient}
    
    public enum PrePaidHealthInsuranceDBColumns{ idPrePaidHealthInsurance, name}
    
    public enum PatientDBColumns{ prePaidHealthInsuranceNumber
    }
    
}
