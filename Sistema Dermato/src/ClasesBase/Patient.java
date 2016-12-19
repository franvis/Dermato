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

    private String name;
    private String lastname;
    private String phone;
    private long dni;
    private String address;
    private String city;
    private String birthday;
    private PrePaidHealthInsurance prepaidHealthInsurance;
    private String prepaidHealthInsuranceNumber;
    private String firstVisitDate;
    private Antecedents antecendents;
    private String lastVisitDate;
    private LinkedList<Visit> visits = new LinkedList<>();

    public Patient() {
    }

    public Patient(String name, String lastname, String phone, long dni,
            String address, String city, String birthday, 
            PrePaidHealthInsurance prepaidHealthInsurance, String prepaidHealthInsuranceNumber, Antecedents antecendent,
            String firstVisitDate) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.dni = dni;
        this.address = address;
        this.city = city;
        this.birthday = birthday;
        this.prepaidHealthInsurance = prepaidHealthInsurance;
        this.prepaidHealthInsuranceNumber = prepaidHealthInsuranceNumber;
        this.antecendents = antecendent;
        this.firstVisitDate = firstVisitDate;
    }

    public Patient(String name, String lastname, String phone, long dni,
            String address, String city, String birthday,
            PrePaidHealthInsurance prepaidHealthInsurance, String prepaidHealthInsuranceNumber, Antecedents antecendent, 
            String firstVisitDate, LinkedList<Visit> visits) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.dni = dni;
        this.address = address;
        this.city = city;
        this.birthday = birthday;
        this.prepaidHealthInsurance = prepaidHealthInsurance;
        this.prepaidHealthInsuranceNumber = prepaidHealthInsuranceNumber;
        this.antecendents = antecendent;
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

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
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

    public String getPrepaidHealthInsuranceNumber() {
        return prepaidHealthInsuranceNumber;
    }

    public void setPrepaidHealthInsuranceNumber(String prepaidHealthInsuranceNumber) {
        this.prepaidHealthInsuranceNumber = prepaidHealthInsuranceNumber;
    }

    public PrePaidHealthInsurance getPrepaidHealthInsurance() {
        return prepaidHealthInsurance;
    }

    public void setPrepaidHealthInsurance(PrePaidHealthInsurance prepaidHealthInsurance) {
        this.prepaidHealthInsurance = prepaidHealthInsurance;
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

    public Antecedents getAntecendents() {
        return antecendents;
    }

    public void setAntecendents(Antecedents antecendents) {
        this.antecendents = antecendents;
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
}
