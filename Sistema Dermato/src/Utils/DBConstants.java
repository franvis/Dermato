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
    
    //SQL STATEMENTS
    
    public static final String DB_NAME = "Dermato";
    
    public static final String MAX_WITH_ALIAS = "MAX(%s) AS %s";
    
    public static final String SIMPLE_WHERE_CONDITION = "%s = ?";
    
    public static final String IS_NULL_WHERE_CONDITION = "%s IS NULL";
    
    public static final String ORDER_BY_CONDITION = " ORDER BY %s";
    
    public static final String ORDER_BY_COLUMN_ASC = "%s DESC";
    
    public static final String ORDER_BY_COLUMN_DESC = "%s ASC";
    
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
    
    public static final String SELECT_ALL_WITH_ORDER = SELECT_ALL 
            + ORDER_BY_CONDITION;
    
    public static final String SELECT_ALL_WITH_WHERE_AND_ORDER = SELECT_ALL_WITH_WHERE 
            + ORDER_BY_CONDITION;
    
    public static final String SELECT_COLUMNS_WITH_WHERE = "SELECT %s FROM "+DB_NAME
            +".%s WHERE %s";
    
    public static final String SELECT_COLUMNS_WITH_WHERE_AND_ORDER = "SELECT %s FROM "+DB_NAME
            +".%s WHERE %s ORDER BY %s";
    
    //UPDATE STATEMENTS
    public static final String UPDATE = "UPDATE "+ DB_NAME+".%s SET %s WHERE %s";
    
    //DELETE STATEMENTS
    public static final String DELETE = "DELETE FROM "+ DB_NAME +".%s WHERE %s";
    
    //TABLES
    public enum Tables { Patient, Antecedents, PrePaidHealthInsurance, Visit };
    
    // TABLE COLUMNS
    public enum VisitDBColumns{ idVisit, date, reason, treatment, 
    complementaryStudies, laboratory, diagnosis, physicalExam, biopsy, patient }
    
    public enum AntecedentsDBColumns{ personal, surgical, toxic, pharmacological
    , family, patient }
    
    public enum PrePaidHealthInsuranceDBColumns{ idPrePaidHealthInsurance, name }
    
    public enum PatientDBColumns{ prePaidHealthInsuranceNumber }
    
}
