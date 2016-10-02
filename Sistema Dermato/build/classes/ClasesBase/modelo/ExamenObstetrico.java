/*
 Clase Base dedicada a los datos de un examen obstetrico;
 */
package ClasesBase.modelo;

/**
 *
 * @author Fran
 */
public class ExamenObstetrico {

    private float talla;
    private float peso;
    private float imc;
    private float au;//altura uterina (cm)
    private String pa;//presion arterial (mm/hg)    
    private int lcf;//latidos cardio fetales
    private boolean maf;//movimientos activos fetales SI = true, NO = false;
    private String observaciones;

    public ExamenObstetrico() {
    }

    public ExamenObstetrico(float talla, float peso, float imc, float au, String pa, int lcf, boolean maf, String observaciones) {
        this.talla = talla;
        this.peso = peso;
        this.imc = imc;
        this.au = au;
        this.pa = pa;
        this.lcf = lcf;
        this.maf = maf;
        this.observaciones = observaciones;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public float getAu() {
        return au;
    }

    public void setAu(float au) {
        this.au = au;
    }

    public int getLcf() {
        return lcf;
    }

    public void setLcf(int lcf) {
        this.lcf = lcf;
    }

    public boolean getMaf() {
        return maf;
    }

    public void setMaf(boolean maf) {
        this.maf = maf;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getTalla() {
        return talla;
    }

    public void setTalla(float talla) {
        this.talla = talla;
    }
    
}
