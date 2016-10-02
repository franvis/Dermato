/*
 Clase Base dedicada a los Antecedentes Familiares de un Paciente;
 */
package ClasesBase.modelo;

/**
 *
 * @author Fran
 */
public class AntecedentesFamiliares {
    private boolean dbt;//diabetes
    private boolean hta;//hipertension arterial
    private boolean oncologicos;//cancer
    private boolean tiroides;
    private String descripcionTiroides;
    private String descripcionOncologicos;//tipo cancer
    private String observaciones;

    public AntecedentesFamiliares() {
    }

    public AntecedentesFamiliares(boolean dbt, boolean hta, boolean oncologicos,
                                  boolean tiroides, String descripcionTiroides,
                                  String descripcionOncologicos, String observaciones) {
        this.dbt = dbt;
        this.hta = hta;
        this.oncologicos = oncologicos;
        this.tiroides = tiroides;
        this.descripcionTiroides = descripcionTiroides;
        this.descripcionOncologicos = descripcionOncologicos;
        this.observaciones = observaciones;
    }

    public boolean isDbt() {
        return dbt;
    }

    public void setDbt(boolean dbt) {
        this.dbt = dbt;
    }

    public String getDescripcionOncologicos() {
        return descripcionOncologicos;
    }

    public void setDescripcionOncologicos(String descripcionOncologicos) {
        this.descripcionOncologicos = descripcionOncologicos;
    }

    public String getDescripcionTiroides() {
        return descripcionTiroides;
    }

    public void setDescripcionTiroides(String descripcionTiroides) {
        this.descripcionTiroides = descripcionTiroides;
    }

    public boolean isHta() {
        return hta;
    }

    public void setHta(boolean hta) {
        this.hta = hta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isOncologicos() {
        return oncologicos;
    }

    public void setOncologicos(boolean oncologicos) {
        this.oncologicos = oncologicos;
    }

    public boolean isTiroides() {
        return tiroides;
    }

    public void setTiroides(boolean tiroides) {
        this.tiroides = tiroides;
    }
}
