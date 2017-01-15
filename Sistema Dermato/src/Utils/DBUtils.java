/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import static Utils.DBConstants.COLUMN_WITH_TABLE_PREFIX;
import static Utils.DBConstants.INNER_JOIN;
import static Utils.DBConstants.LEFT_JOIN;
import static Utils.DBConstants.MAX_COLUMN_AS;
import static Utils.DBConstants.PatientDBColumns.dni;
import static Utils.DBConstants.PatientDBColumns.name;
import static Utils.DBConstants.RIGHT_JOIN;
import Utils.DBConstants.Tables;
import static Utils.DBConstants.PatientDBColumns.lastname;
import static Utils.DBConstants.Tables.Patient;

/**
 *
 * @author fran
 */
public class DBUtils {

    //INSERT STATEMENTS
    public static String getInsertStatementWithValuesOnly(Tables table) {
        switch (table) {
            case Antecedents:
                return String.format(DBConstants.INSERT_WITH_VALUES_ONLY,
                        table.name(), DBConstants.ANTECEDENTS_INSERT);
            case Patient:
                return String.format(DBConstants.INSERT_WITH_VALUES_ONLY,
                        table.name(), DBConstants.PATIENT_INSERT);
            case PrepaidHealthInsurance:
                return String.format(DBConstants.INSERT_WITH_VALUES_ONLY,
                        table.name(), DBConstants.PRE_PAID_HEALTH_INSURANCE_INSERT);
            case Visit:
                return String.format(DBConstants.INSERT_WITH_VALUES_ONLY,
                        table.name(), DBConstants.VISIT_INSERT);
        }
        throw new NoClassDefFoundError("Table: " + table.name() + "not supported yet.");
    }

    public static String getInsertStatementWithColumns(Tables table,
            String columns, String values) {
        return String.format(DBConstants.INSERT_WITH_COLUMNS, table.name(), columns,
                values);
    }

    //SELECT STATEMENTS
    public static String getSelectAllStatement(Tables table) {
        return String.format(DBConstants.SELECT_ALL, table.name());
    }

    public static String getSelectAllStatementWithWhere(Tables table,
            String whereConditions) {
        return String.format(DBConstants.SELECT_ALL_WITH_WHERE, table.name(),
                whereConditions);
    }

    public static String getSelectAllStatementWithOrder(Tables table,
            String orderConditions) {
        return String.format(DBConstants.SELECT_ALL_WITH_ORDER, table.name(),
                orderConditions);
    }

    public static String getSelectAllStatementWithWhereAndOrder(Tables table,
            String whereConditions, String orderConditions) {
        return String.format(DBConstants.SELECT_ALL_WITH_WHERE_AND_ORDER, table.name(),
                whereConditions, orderConditions);
    }

    public static String getSelectColumnsStatementWithWhereGroupByAndOrder(Tables table, String columns,
            String whereConditions, String groupByCondition, String orderConditions) {
        return String.format(DBConstants.SELECT_COLUMNS_WITH_WHERE_GROUP_BY_AND_ORDER, table.name(),
                columns, whereConditions, groupByCondition, orderConditions);
    }

    public static String getSelectColumnsMultipleTablesStatementWithWhere(String columns,
            String from, String whereConditions) {
        return String.format(DBConstants.SELECT_COLUMNS_MULTIPLE_TABLES_WITH_WHERE,
                columns, from, whereConditions);
    }

    public static String getSelectColumnsMultipleTablesStatementWithWhereAndGroupBy(String columns,
            String from, String whereConditions, String groupBy) {
        return String.format(DBConstants.SELECT_COLUMNS_MULTIPLE_TABLES_WITH_WHERE_AND_GROUP_BY,
                columns, from, whereConditions, groupBy);
    }

    public static String getSelectAllMultipleTablesStatementWithWhere(
            String from, String whereConditions) {
        return String.format(DBConstants.SELECT_ALL_MULTIPLE_TABLES_WITH_WHERE,
                from, whereConditions);
    }

    public static String getSelectColumnsMultipleTablesStatementWithWhereGroupByAndOrder(String columns,
            String from, String whereConditions, String groupByCondition, String orderConditions) {
        return String.format(DBConstants.SELECT_COLUMNS_MULTIPLE_TABLES_WITH_WHERE_GROUP_BY_AND_ORDER,
                columns, from, whereConditions, groupByCondition, orderConditions);
    }

    public static String getSelectColumnsStatementWithWhere(Tables table, String columns, String whereConditions) {
        return String.format(DBConstants.SELECT_COLUMNS_WITH_WHERE, columns, table.name(),
                whereConditions);
    }

    public static String getSelectStatementWithColumnsAndOrder(Tables table,
            String columns, String whereConditions, String orderConditions) {
        return String.format(DBConstants.SELECT_COLUMNS_WITH_WHERE_AND_ORDER, columns, table.name(),
                whereConditions, orderConditions);
    }

    public static String getSelectOneStatementWithWhere(Tables table,
            String whereConditions) {
        return String.format(DBConstants.SELECT_ONE_WITH_WHERE, table.name(),
                whereConditions);
    }

    //UPDATE STATEMENTS
    public static String getUpdateStatement(Tables table, String columns,
            String whereConditions) {
        return String.format(DBConstants.UPDATE, table.name(),
                columns, whereConditions);
    }

    //DELETE STATEMENTS
    public static String getDeleteStatement(Tables table,
            String whereConditions) {
        return String.format(DBConstants.DELETE, table.name(), whereConditions);
    }

    //WHERE ORDER AND OTHERS
    public static String getSimpleWhereCondition(String column) {
        return String.format(DBConstants.SIMPLE_WHERE_CONDITION, column);
    }

    public static String getIsNullWhereCondition(String column) {
        return String.format(DBConstants.IS_NULL_WHERE_CONDITION, column);
    }

    public static String getOrderByCondition(String column, boolean asc) {
        if (asc) {
            return String.format(DBConstants.ORDER_BY_COLUMN_ASC, column);
        } else {
            return String.format(DBConstants.ORDER_BY_COLUMN_DESC, column);
        }
    }

    public static String getTableJoin(int joinType, Tables firstTable,
            Tables secondTable, String firstTableColumn, String secondTableColumn) {
        return String.format(DBConstants.JOIN, firstTable.name(), getJoinType(joinType), secondTable.name(),
                firstTableColumn, secondTableColumn);
    }
    
    public static String appendTableJoin(int joinType, String currentFrom, Tables tableToJoin,
            String currentFormColumn, String tableToJoinColumn){
        return String.format(DBConstants.APPEND_JOIN, currentFrom, getJoinType(joinType), tableToJoin, 
                currentFormColumn, tableToJoinColumn);
    }

    public static String getMaxColumnAs(String column, String as) {
        return String.format(MAX_COLUMN_AS, column, as);
    }

    public static String getStringWithValuesSeparatedWithCommas(String... values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                builder.append(values[i]);
            } else {
                builder.append(", ").append(values[i]);
            }
        }
        return builder.toString();
    }

    public static String getStringWithValuesSeparatedWithCommasForUpdate(String... values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i == 0 || i == values.length - 1) {
                builder.append(values[i]).append(" = ?");
            } else {
                builder.append(", ").append(values[i]).append(" = ?");
            }
        }
        return builder.toString();
    }

    public static String getWhereConditions(String... values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i == 0 || i == values.length - 1) {
                builder.append(values[i]);
            } else {
                builder.append(" AND ").append(values[i]);
            }
        }
        return builder.toString();
    }

    private static String getJoinType(int joinType) {
        switch (joinType) {
            case INNER_JOIN:
                return "INNER JOIN";
            case LEFT_JOIN:
                return "LEFT JOIN";
            case RIGHT_JOIN:
                return "RIGHT_JOIN";
            default:
                return null;
        }
    }

    public static String getWhereForFilters(String filterName, String filterLastName, String filterDni) {
        StringBuilder where = new StringBuilder();
        if (!filterDni.isEmpty()) {
            where.append(getSimpleWhereCondition("CONVERT(" + dni.name() + ", CHAR)"));
        }

        if (!filterLastName.isEmpty()) {
            if (!where.toString().isEmpty()) {
                where.append(" AND ");
            }
            where.append(getSimpleWhereCondition(lastname.name()));
        }

        if (!filterName.isEmpty()) {
            if (!where.toString().isEmpty()) {
                where.append(" AND ");
            }
            where.append(getSimpleWhereCondition(name.name()));
        }

        return where.toString();
    }
    
    public static String getColumnWithTablePrefix(Tables table, String column){
        return String.format(COLUMN_WITH_TABLE_PREFIX, table.name(), column);
    }

    public static String getOrderByForFilters(String filterName, String filterLastName, String filterDni) {
        StringBuilder orderBy = new StringBuilder();
        if (!filterDni.isEmpty()) {
            orderBy.append(getOrderByCondition(dni.name(), true));
        }

        if (!filterLastName.isEmpty()) {
            if (!orderBy.toString().isEmpty()) {
                orderBy.append(" , ");
            }
            orderBy.append(getOrderByCondition(lastname.name(), true));
        }

        if (!filterName.isEmpty()) {
            if (!orderBy.toString().isEmpty()) {
                orderBy.append(" , ");
            }
            orderBy.append(getOrderByCondition(name.name(), true));
        }
        return orderBy.toString();
    }

    public static String getFormattedDate(String date) {
        String anio, mes, dia;
        anio = date.substring(0, 4);
        mes = date.substring(5, 7);
        dia = date.substring(8, 10);
        return String.format("%s/%s/%s", dia, mes, anio);
    }
}
