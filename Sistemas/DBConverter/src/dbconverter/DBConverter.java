/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconverter;

import bussines.MedicalCoverage;
import static bussines.MedicalCoverage.NO_MEDICAL_COVERAGE_NAME;
import bussines.Patient;
import dao.DAOMedicalCoverage;
import dao.DAOPatient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import utils.DateTimeUtils;

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
            while (recordIterator.hasNext()) {
                r = recordIterator.next();
                patient = new Patient();

                String patientName = r.getStringValue("PAC").trim();
                if (!patientName.isEmpty()) {
                    //NAME AND LASTNAME
                    if (patientName.contains(" ")) {
                        patient.setLastname(patientName.substring(0, patientName.indexOf(" ")).trim());
                        patient.setName(patientName.substring(patientName.indexOf(" ") + 1, patientName.length()).trim());
                    } else {
                        patient.setName(patientName);
                    }

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
                    if (r.getNumberValue("MUT1") != null && r.getNumberValue("MUT1").intValue() != 0
                            && r.getNumberValue("MUT1").intValue() != 9) { //9 PARTICULAR EN DOS
                        patient.setMedicalCoverage(daoMedicalCoverage.getMedicalCoverage(r.getNumberValue("MUT1").intValue()));
                    } else {
                        patient.setMedicalCoverage(new MedicalCoverage(0, NO_MEDICAL_COVERAGE_NAME));
                    }
                    
                    //BIRTHDAY
                    if (r.getStringValue("MUT") != null && !r.getStringValue("MUT").trim().isEmpty()) {
                        patient.setMedicalCoverageNumber(r.getStringValue("MUT"));
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
                }
            }
            System.out.println("DB IMPORT FINISHED CORRECTLY");
        } finally {
            t1.close();
        }
    }

}
