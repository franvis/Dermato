/*
 * Clase base dedicada a las obrasSociales
 */
package ClasesBase;

/**
 *
 * @author Fran
 */
public class PrePaidHealthInsurance {
    public static final String NO_PRE_PAID_HEALTH_INSURANCE_NAME = 
            "Sin Obra Social";
    
    private int id;
    private String name;

    public PrePaidHealthInsurance(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PrePaidHealthInsurance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PrePaidHealthInsurance otherPrepaidHealthInsurance = (PrePaidHealthInsurance) obj;
        if (this.id != otherPrepaidHealthInsurance.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.id;
        return hash;
    }
}
