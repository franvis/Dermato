/*
Clase Base dedicada a los Antecedentes de un Paciente;
 */
package ClasesBase.modelo;

/**
 *
 * @author Fran
 */
public class Antecedents {

    private String personalAntecedents;
    private String surgicalAntecedents;
    private String toxicAntecedents;
    private String pharmacologicalAntecedents;
    private String familyAntecedents;

    public Antecedents() {
    }

    public Antecedents(String personalAntecedents, String surgicalAntecedents,
            String toxicAntecedents, String pharmacologicalAntecedents, 
            String familyAntecedents) {
        this.personalAntecedents = personalAntecedents;
        this.surgicalAntecedents = surgicalAntecedents;
        this.toxicAntecedents = toxicAntecedents;
        this.pharmacologicalAntecedents = pharmacologicalAntecedents;
        this.familyAntecedents = familyAntecedents;
    }

    public String getPersonalAntecedents() {
        return personalAntecedents;
    }

    public void setPersonalAntecedents(String personalAntecedents) {
        this.personalAntecedents = personalAntecedents;
    }

    public String getSurgicalAntecedents() {
        return surgicalAntecedents;
    }

    public void setSurgicalAntecedents(String surgicalAntecedents) {
        this.surgicalAntecedents = surgicalAntecedents;
    }

    public String getToxicAntecedents() {
        return toxicAntecedents;
    }

    public void setToxicAntecedents(String toxicAntecedents) {
        this.toxicAntecedents = toxicAntecedents;
    }

    public String getPharmacologicalAntecedents() {
        return pharmacologicalAntecedents;
    }

    public void setPharmacologicalAntecedents(String pharmacologicalAntecedents) {
        this.pharmacologicalAntecedents = pharmacologicalAntecedents;
    }

    public String getFamilyAntecedents() {
        return familyAntecedents;
    }

    public void setFamilyAntecedents(String familyAntecedents) {
        this.familyAntecedents = familyAntecedents;
    }
}
