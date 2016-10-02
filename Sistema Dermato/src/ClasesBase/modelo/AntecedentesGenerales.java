/*
Clase Base dedicada a los Antecedentes Personales de un Paciente;
 */
package ClasesBase.modelo;

/**
 *
 * @author Fran
 */
public class AntecedentesGenerales {
    private String antecedentesPersonales;
    private String antecedentesQuirurgicos;
    private String antecedentesToxicos;

    public AntecedentesGenerales() {
    }

    public AntecedentesGenerales(String antecedentesPersonales,String antecedentesQuirurgicos,
                                String antecedentesToxicos) 
    {
        
        this.antecedentesPersonales = antecedentesPersonales;
        this.antecedentesQuirurgicos = antecedentesQuirurgicos;
        this.antecedentesToxicos = antecedentesToxicos;
        
    }

    public String getAntecedentesPersonales() {
        return antecedentesPersonales;
    }

    public void setAntecedentesPersonales(String antecedentesPersonales) {
        this.antecedentesPersonales = antecedentesPersonales;
    }

    public String getAntecedentesQuirurgicos() {
        return antecedentesQuirurgicos;
    }

    public void setAntecedentesQuirurgicos(String antecedentesQuirurgicos) {
        this.antecedentesQuirurgicos = antecedentesQuirurgicos;
    }

    public String getAntecedentesToxicos() {
        return antecedentesToxicos;
    }

    public void setAntecedentesToxicos(String antecedentesToxicos) {
        this.antecedentesToxicos = antecedentesToxicos;
    }
    
    
    
}
