/*
Clase base dedicada a la/s consulta/s;
 */
package bussines;

/**
 *
 * @author Fran
 */
public class Visit {
    private int id;
    private String date;
    private String reason;
    private String treatment;
    private String complementaryStudies;
    private String laboratory;
    private String diagnosis;
    private String physicalExam;
    private String biopsy;

    public Visit() {
    }

    public Visit(int id,String date, String reason, String treatment,
                    String complementaryStudies, String physicalExam, 
                    String biopsy, String laboratory, String diagnosis) {
        this.id = id;
        this.date = date;
        this.reason = reason;
        this.treatment = treatment;
        this.complementaryStudies = complementaryStudies;
        this.physicalExam = physicalExam;
        this.biopsy = biopsy;
        this.laboratory = laboratory;
        this.diagnosis = diagnosis;
    }
    
    public String getComplementaryStudies() {
        return complementaryStudies;
    }

    public void setComplementaryStudies(String complementaryStudies) {
        this.complementaryStudies = complementaryStudies;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
  
    @Override
    public int hashCode(){
        return id;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getPhysicalExam() {
        return physicalExam;
    }

    public void setPhysicalExam(String physicalExam) {
        this.physicalExam = physicalExam;
    }

    public String getBiopsy() {
        return biopsy;
    }

    public void setBiopsy(String biopsy) {
        this.biopsy = biopsy;
    }
    
    @Override
    public boolean equals(Object Consulta){
        if(Consulta instanceof Visit)
            return hashCode() == Consulta.hashCode();
        else
            return false;
    }
}
