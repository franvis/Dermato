/*
 * Clase base dedicada a las obrasSociales
 */
package ClasesBase.modelo;

/**
 *
 * @author Fran
 */
public class ObraSocial {
    private int id;
    private String nombre;

    public ObraSocial(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ObraSocial() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ObraSocial otraObraSocial = (ObraSocial) obj;
        if (this.id != otraObraSocial.id) {
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
