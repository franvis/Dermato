package dbconverter;

import java.io.IOException;
import nl.knaw.dans.common.dbflib.CorruptedTableException;

/**
 *
 * @author Francisco Visintini
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DBConverter dBConverter = new DBConverter();
            dBConverter.convertDB();
        } catch (IOException | CorruptedTableException e) {
        }
    }
}
