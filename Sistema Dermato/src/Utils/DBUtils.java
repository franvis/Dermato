/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Utils.DBConstants.Tables;

/**
 *
 * @author fran
 */
public class DBUtils {

    //INSERT STATEMENTS
    
    public static String getInsertStatementWithValuesOnly(Tables table, String values) {
        return String.format(DBConstants.INSERT_WITH_VALUES_ONLY, table.name(), values);
    }

    public static String getInsertStatementWithColumns(Tables table,
            String columns, String values) {
        return String.format(DBConstants.INSERT_WITH_COLUMNS, table.name(), columns,
                values);
    }

    //SELECT STATEMENTS
    
    public static String getSelectStatementWithColumns(Tables table, String columns, String whereConditions) {
        return String.format(DBConstants.SELECT_WITH_COLUMNS, columns, table.name(),
                whereConditions);
    }
    
    public static String getSelectStatementWithoutColumns(Tables table, String whereConditions) {
        return String.format(DBConstants.SELECT_WITHOUT_COLUMNS, table.name(),
                whereConditions);
    }

    public static String getSelectStatementWithColumnsAndOrderCondition(Tables table, 
            String columns, String whereConditions, String orderConditions) {
        return String.format(DBConstants.SELECT_WITH_COLUMNS_AND_ORDER, columns, table.name(),
                whereConditions, orderConditions);
    }
    
    public static String getSelectStatementWithoutColumnsWithOrder(Tables table,
            String whereConditions, String orderConditions) {
        return String.format(DBConstants.SELECT_WITHOUT_COLUMNS_WITH_ORDER, table.name(),
                whereConditions, orderConditions);
    }

    //UPDATE STATEMENTS
    
    //WHERE ORDER AND OTHERS
    
    public static String getSimpleWhereCondition(String column){
        return String.format(DBConstants.SIMPLE_WHERE_CONDITION, column);
    }
    
    public static String getOrderByCondition(String column, boolean asc) {
        if (asc) {
            return String.format(DBConstants.ORDER_BY_COLUMN_ASC, column);
        } else {
            return String.format(DBConstants.ORDER_BY_COLUMN_DESC, column);
        }
    }
    
    public static String getStringWithValuesSeparatedWithCommas(String... values){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if(i==0 || i == values.length - 1)
                builder.append(values[i]);
            else
                builder.append(", ").append(values[i]);
        }
        return builder.toString();
    }
    
    public static String getWhereConditions(String... values){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if(i==0 || i == values.length - 1)
                builder.append(values[i]);
            else
                builder.append(" AND ").append(values[i]);
        }
        return builder.toString();
    }
}
