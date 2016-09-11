/*
Clase Base dedicada a los Antecedentes Ginecologicos de un Paciente;
 */
package ClasesBase.modelo;

/**
 *
 * @author Fran
 */
public class AntecedentesGinecologicos {
    
    private int menarca;
    private String telarca;
    private int menopausia;
    private int duracionMenstrual;//cantidad de dias que dura el sangrado!
    private int periodoMenstrual;//cantidad de dias que dura el ciclo!
    private int gestaciones;
    private int abortos;
    private int vivos;
    private int muertos;
    private boolean Dispareunia;
    private boolean Dismenorrea;
    private boolean sdpm;
    private String anticonceptivos;
    private int partos;
    private int cesareas;
    private String observaciones;

    public AntecedentesGinecologicos() {
    }

    public AntecedentesGinecologicos(int menarca, String telarca, int menopausia,
                                     int duracionMenstrual, int periodoMenstrual,
                                     int gestaciones, int abortos, int vivos, int muertos,
                                     boolean Dispareunia, boolean Dismenorrea, boolean sdpm,
                                     String anticonceptivos, int partos, int cesareas, String observaciones) {
        this.menarca = menarca;
        this.telarca = telarca;
        this.menopausia = menopausia;
        this.duracionMenstrual = duracionMenstrual;
        this.periodoMenstrual = periodoMenstrual;
        this.gestaciones = gestaciones;
        this.abortos = abortos;
        this.vivos = vivos;
        this.muertos = muertos;
        this.Dispareunia = Dispareunia;
        this.Dismenorrea = Dismenorrea;
        this.sdpm = sdpm;
        this.anticonceptivos = anticonceptivos;
        this.partos = partos;
        this.cesareas = cesareas;
        this.observaciones = observaciones;
    }

    public boolean isDismenorrea() {
        return Dismenorrea;
    }

    public void setDismenorrea(boolean Dismenorrea) {
        this.Dismenorrea = Dismenorrea;
    }

    public boolean isDispareunia() {
        return Dispareunia;
    }

    public void setDispareunia(boolean Dispareunia) {
        this.Dispareunia = Dispareunia;
    }

    public int getAbortos() {
        return abortos;
    }

    public void setAbortos(int abortos) {
        this.abortos = abortos;
    }

    public String getAnticonceptivos() {
        return anticonceptivos;
    }

    public void setAnticonceptivos(String anticonceptivos) {
        this.anticonceptivos = anticonceptivos;
    }

    public int getCesareas() {
        return cesareas;
    }

    public void setCesareas(int cesareas) {
        this.cesareas = cesareas;
    }

    public int getDuracionMenstrual() {
        return duracionMenstrual;
    }

    public void setDuracionMenstrual(int duracionMenstrual) {
        this.duracionMenstrual = duracionMenstrual;
    }

    public int getPeriodoMenstrual() {
        return periodoMenstrual;
    }

    public void setPeriodoMenstrual(int periodoMenstrual) {
        this.periodoMenstrual = periodoMenstrual;
    }

    public int getGestaciones() {
        return gestaciones;
    }

    public void setGestaciones(int gestaciones) {
        this.gestaciones = gestaciones;
    }

    public int getMenarca() {
        return menarca;
    }

    public void setMenarca(int menarca) {
        this.menarca = menarca;
    }

    public int getMenopausia() {
        return menopausia;
    }

    public void setMenopausia(int menopausia) {
        this.menopausia = menopausia;
    }

    public int getMuertos() {
        return muertos;
    }

    public void setMuertos(int muertos) {
        this.muertos = muertos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getPartos() {
        return partos;
    }

    public void setPartos(int partos) {
        this.partos = partos;
    }

    public boolean isSdpm() {
        return sdpm;
    }

    public void setSdpm(boolean sdpm) {
        this.sdpm = sdpm;
    }

    public String getTelarca() {
        return telarca;
    }

    public void setTelarca(String telarca) {
        this.telarca = telarca;
    }

    public int getVivos() {
        return vivos;
    }

    public void setVivos(int vivos) {
        this.vivos = vivos;
    }
}
