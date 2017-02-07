/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvp.model;

import DAO.DAOAntecedents;
import DAO.DAOPatient;
import DAO.DAOVisit;

/**
 *
 * @author fran
 */
public class ClinicalHistoryModel {

    DAOPatient patientDao;
    DAOVisit visitDao;
    DAOAntecedents antecedentsDao;
    
    public ClinicalHistoryModel(){
        patientDao = new DAOPatient();
        visitDao = new DAOVisit();
        antecedentsDao = new DAOAntecedents();
    }
}
