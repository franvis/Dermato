/*
Clase base dedicada a la/s consulta/s;
 */
package ClasesBase.modelo;

/**
 *
 * @author Fran
 */
public class Consulta {
    private int id;
    private String fecha;
    private String motivo;
    private String tratamiento;
    private String estudiosComplementarios;
    private ExamenGinecologico examenGinecologico;
    private ExamenObstetrico examenObstetrico;
    private String observaciones;
    private String diagnostico;
    private String tipoConsulta;

    public Consulta() {
    }

    public Consulta(int id,String fecha, String motivo, String tratamiento,
                    String estudiosComplementarios, ExamenGinecologico examenGinecologico,
                    ExamenObstetrico examenObstetrico, String observaciones,
                    String diagnostico, String tipoConsulta) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.tratamiento = tratamiento;
        this.estudiosComplementarios = estudiosComplementarios;
        this.examenGinecologico = examenGinecologico;
        this.examenObstetrico = examenObstetrico;
        this.observaciones = observaciones;
        this.diagnostico = diagnostico;
        this.tipoConsulta = tipoConsulta;
    }

    
    public String getEstudiosComplementarios() {
        return estudiosComplementarios;
    }

    public void setEstudiosComplementarios(String estudiosComplementarios) {
        this.estudiosComplementarios = estudiosComplementarios;
    }

    public ExamenGinecologico getExamenGinecologico() {
        return examenGinecologico;
    }

    public void setExamenGinecologico(ExamenGinecologico examenGinecologico) {
        this.examenGinecologico = examenGinecologico;
    }

    public ExamenObstetrico getExamenObstetrico() {
        return examenObstetrico;
    }

    public void setExamenObstetrico(ExamenObstetrico examenObstetrico) {
        this.examenObstetrico = examenObstetrico;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
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

    @Override
    public boolean equals(Object Consulta){
        if(Consulta instanceof Consulta)
            return hashCode() == Consulta.hashCode();
        else
            return false;
    }
    
}
