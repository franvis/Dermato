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
public class DBUtils {
    
    public enum Tables { Patient, Antecedents, PrepaidHealthInsurance, Visit };
    
    public static final String INSERT_WITH_VALUES_ONLY = "INSERT INTO Dermato"
            + ".$1%s VALUES ($2%s)";
    public static final String INSERT_WITH_COLUMNS = "INSERT INTO Dermato.$1%s "
            + "($2%s) VALUES ($3%s)";
    
    public static String getInsertStatementWithValuesOnly(Tables table, String values){
        return String.format(INSERT_WITH_VALUES_ONLY, table.name(), values);
    }
    
    public static String getInsertStatementWithColumns(Tables table, 
            String columns, String values){
        return String.format(INSERT_WITH_COLUMNS, table.name(), columns, 
                values);
    }
    
}
