/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconverter;

import dao.DAOPatient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.Version;
import sun.nio.cs.ext.IBM865;

/**
 *
 * @author fran
 */
public class DBConverter {

    private final DAOPatient daoPatient;
    
    public DBConverter(){
        this.daoPatient = new DAOPatient();
    }
    
    public void convertDB() throws FileNotFoundException, IOException, CorruptedTableException {
        final Table t1 = new Table(new File("src/files/FICHERO.DBF"));

        try {
            t1.open(IfNonExistent.ERROR);

            final Iterator<Record> recordIterator = t1.recordIterator();

            Record r;
            int i = 0;
            while (recordIterator.hasNext()) {
                i++;
                r = recordIterator.next();
                if (r.getStringValue("PAC") != null && r.getStringValue("TAR") != null) {
                    String value = r.getStringValue("TAR");
                    System.out.println(value);
                }
                if (i == 10) {
                    break;
                }
            }
        } finally {
            t1.close();
        }
    }

    
    
}
