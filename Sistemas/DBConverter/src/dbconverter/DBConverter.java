/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconverter;

import bussines.MedicalCoverage;
import bussines.Patient;
import dao.DAOMedicalCoverage;
import dao.DAOPatient;
import static dao.DAOPatient.MEDICAL_COVERAGE_DEFAULT_NAME;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import utils.DateTimeUtils;
import utils.GeneralUtils;

/**
 *
 * @author fran
 */
public class DBConverter {

    private final DAOPatient daoPatient;
    private final DAOMedicalCoverage daoMedicalCoverage;

    public DBConverter() {
        this.daoPatient = new DAOPatient();
        this.daoMedicalCoverage = new DAOMedicalCoverage();
    }

    public void convertDB() throws FileNotFoundException, IOException, CorruptedTableException {
        final Table t1 = new Table(new File("src/files/FICHERO.DBF"));

        try {
            t1.open(IfNonExistent.ERROR);

            final Iterator<Record> recordIterator = t1.recordIterator();

            Record r;
            Patient patient;
            int i = 0;
            while (recordIterator.hasNext()) {
                r = recordIterator.next();
                i++;
                patient = new Patient();

                //NAME AND LASTNAME
                String patientName = r.getStringValue("PAC").trim();
                patient.setLastname(patientName.substring(0, patientName.indexOf(" ")).trim());
                patient.setName(patientName.substring(patientName.indexOf(" ") + 1, patientName.length()).trim());

                //ADDRESS
                if (r.getStringValue("DIR") != null && !r.getStringValue("DIR").trim().isEmpty()) {
                    patient.setAddress(r.getStringValue("DIR").trim());
                }

                //CITY
                if (r.getStringValue("LOC") != null && !r.getStringValue("LOC").trim().isEmpty()) {
                    patient.setCity(r.getStringValue("LOC").trim());
                }

                //PHONE
                if (r.getStringValue("TEL") != null && !r.getStringValue("TEL").trim().isEmpty()
                        && !r.getStringValue("TEL").trim().equals("NO TIENE")
                        && !r.getStringValue("TEL").trim().equals("NO")
                        && !r.getStringValue("TEL").trim().equals("NT")
                        && !r.getStringValue("TEL").trim().equals("N/T")) {
                    patient.setPhone(r.getStringValue("TEL").trim());
                }

                //MEDICAL COVERAGE
                if (r.getStringValue("MUT") != null && !r.getStringValue("MUT").trim().isEmpty()) {
                    patient.setMedicalCoverage(daoMedicalCoverage.getMedicalCoverage(r.getStringValue("MUT").trim()));
                } else {
                    patient.setMedicalCoverage(new MedicalCoverage(0, MEDICAL_COVERAGE_DEFAULT_NAME));
                }
                
                //BIRTHDAY
                if (r.getDateValue("NAC") != null) {
                    patient.setBirthday(DateTimeUtils.convertDateToString(r.getDateValue("NAC")));
                }
                
                //BIRTHDAY
                if (r.getStringValue("TAR") != null && !r.getStringValue("TAR").trim().isEmpty()) {
                    patient.setPreviousCH(r.getStringValue("TAR"));
                }
                
                //FINAL REGISTER
                daoPatient.registerPatient(patient);
                if(i == 50){
                    break;
                }
            }
        } finally {
            t1.close();
        }
    }

}
