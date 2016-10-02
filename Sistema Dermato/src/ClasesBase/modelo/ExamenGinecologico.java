/*
 Clase Base dedicada a los datos de un examen ginecologico;
 */
package ClasesBase.modelo;

/**
 *
 * @author Fran
 */
public class ExamenGinecologico {
    
    private String mamas;
    private String genitourinario;
    private String bethesda;//I,II,etc..al V
    private String papycolposcopia;//descripcion

    public ExamenGinecologico() {
    }

    public ExamenGinecologico(String mamas, String genitourinario, String bethesda, String papycolposcopia) {
        this.mamas = mamas;
        this.genitourinario = genitourinario;
        this.bethesda = bethesda;
        this.papycolposcopia = papycolposcopia;
    }

    public String getBethesda() {
        return bethesda;
    }

    public void setBethesda(String bethesda) {
        this.bethesda = bethesda;
    }

    public String getGenitourinario() {
        return genitourinario;
    }

    public void setGenitourinario(String genitourinario) {
        this.genitourinario = genitourinario;
    }

    public String getMamas() {
        return mamas;
    }

    public void setMamas(String mamas) {
        this.mamas = mamas;
    }

    public String getPapycolposcopia() {
        return papycolposcopia;
    }

    public void setPapycolposcopia(String papycolposcopia) {
        this.papycolposcopia = papycolposcopia;
    }    
}
