/*
 Clase Base dedicada a los datos del paciente;
 */
package ClasesBase;

import java.util.LinkedList;

/**
 *
 * @author Fran
 */
public class Patient {

    private static final String FULLNAME = "%s, %s";
    
    private String name;
    private String lastname;
    private String phone;
    private DniType dniType;
    private String dni;
    private String address;
    private String city;
    private String birthday;
    private MedicalCoverage medicalCoverage;
    private String medicalCoverageNumber;
    private String firstVisitDate;
    private Antecedents antecedents;
    private String lastVisitDate;
    private LinkedList<Visit> visits = new LinkedList<>();

    public Patient() {
    }

    public Patient(int id, String name, String lastname, String phone, DniType dniType, String dni,
            String address, String city, String birthday, 
            MedicalCoverage prepaidHealthInsurance, String prepaidHealthInsuranceNumber, Antecedents antecendent,
            String firstVisitDate) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.dniType = dniType;
        this.dni = dni;
        this.address = address;
        this.city = city;
        this.birthday = birthday;
        this.medicalCoverage = prepaidHealthInsurance;
        this.medicalCoverageNumber = prepaidHealthInsuranceNumber;
        this.antecedents = antecendent;
        this.firstVisitDate = firstVisitDate;
    }

    public Patient(int id, String name, String lastname, String phone, DniType dniType, String dni,
            String address, String city, String birthday,
            MedicalCoverage prepaidHealthInsurance, String prepaidHealthInsuranceNumber, Antecedents antecendent, 
            String firstVisitDate, LinkedList<Visit> visits) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.dniType = dniType;
        this.dni = dni;
        this.address = address;
        this.city = city;
        this.birthday = birthday;
        this.medicalCoverage = prepaidHealthInsurance;
        this.medicalCoverageNumber = prepaidHealthInsuranceNumber;
        this.antecedents = antecendent;
        this.firstVisitDate = firstVisitDate;
        this.visits = visits;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String Lastname) {
        this.lastname = Lastname;
    }

    public LinkedList<Visit> getVisits() {
        return visits;
    }

    public void setVisits(LinkedList<Visit> Visits) {
        this.visits = Visits;
    }

    public DniType getDniType(){
        return dniType;
    }
    
    public void setDniType(DniType dniType){
        this.dniType = dniType;
    }
    
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicalCoverageNumber() {
        return medicalCoverageNumber;
    }

    public void setMedicalCoverageNumber(String prepaidHealthInsuranceNumber) {
        this.medicalCoverageNumber = prepaidHealthInsuranceNumber;
    }

    public MedicalCoverage getMedicalCoverage() {
        return medicalCoverage;
    }

    public void setMedicalCoverage(MedicalCoverage prepaidHealthInsurance) {
        this.medicalCoverage = prepaidHealthInsurance;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String fechaUltimaConsulta) {
        this.lastVisitDate = fechaUltimaConsulta;
    }

    public String getFirstVisitDate() {
        return firstVisitDate;
    }

    public void setFirstVisitDate(String firstVisitDate) {
        this.firstVisitDate = firstVisitDate;
    }

    public Antecedents getAntecedents() {
        return antecedents;
    }

    public void setAntecedents(Antecedents antecedents) {
        this.antecedents = antecedents;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public String getFullName(){
        return String.format(FULLNAME, lastname, name);
    }
}
