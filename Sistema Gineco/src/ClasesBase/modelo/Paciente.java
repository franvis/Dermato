/*
 Clase Base dedicada a los datos del paciente;
 */
package ClasesBase.modelo;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Fran
 */
public class Paciente {
    
    private String nombre;
    private String Apellido;
    private String telefono;
    private long dni;
    private String fechaNacimiento;
    private String fechaUltimaConsulta;
    private String grupoSanguineo;//0,A,B,AB
    private boolean factor;//- o +
    private ObraSocial obraSocial;
    private String numeroAfiliado;
    private AntecedentesGenerales antecGen;
    private AntecedentesGinecologicos antecGinec;
    private AntecedentesFamiliares antecFam;
    private LinkedList<Consulta> consultas = new LinkedList<>();

    public Paciente() {
    }

    public Paciente(String nombre, String Apellido, String telefono, long dni, 
                    String fechaNacimiento, String grupoSanguineo, boolean factor, 
                    ObraSocial obraSocial, String numeroAfiliado, AntecedentesGenerales antecGen,
                    AntecedentesGinecologicos antecGinec, AntecedentesFamiliares antecFam) {
        this.nombre = nombre;
        this.Apellido = Apellido;
        this.telefono = telefono;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.grupoSanguineo = grupoSanguineo;
        this.factor = factor;
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
        this.antecGen = antecGen;
        this.antecGinec = antecGinec;
        this.antecFam = antecFam;
    }

    public Paciente(String nombre, String Apellido, String telefono, long dni,
                    String fechaNacimiento, String grupoSanguineo, boolean factor,
                    ObraSocial obraSocial, String numeroAfiliado, AntecedentesGenerales antecGen,
                    AntecedentesGinecologicos antecGinec, AntecedentesFamiliares antecFam,
                    LinkedList<Consulta> consultas) {
        this.nombre = nombre;
        this.Apellido = Apellido;
        this.telefono = telefono;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.grupoSanguineo = grupoSanguineo;
        this.factor = factor;
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
        this.antecGen = antecGen;
        this.antecGinec = antecGinec;
        this.antecFam = antecFam;
        this.consultas = consultas;
    }
    
    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public LinkedList<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(LinkedList<Consulta> Consultas) {
        this.consultas = Consultas;
    }

    public AntecedentesFamiliares getAntecFam() {
        return antecFam;
    }

    public void setAntecFam(AntecedentesFamiliares antecFam) {
        this.antecFam = antecFam;
    }

    public AntecedentesGenerales getAntecGen() {
        return antecGen;
    }

    public void setAntecGen(AntecedentesGenerales antecGen) {
        this.antecGen = antecGen;
    }

    public AntecedentesGinecologicos getAntecGinec() {
        return antecGinec;
    }

    public void setAntecGinec(AntecedentesGinecologicos antecGinec) {
        this.antecGinec = antecGinec;
    }

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public boolean getFactor() {
        return factor;
    }

    public void setFactor(boolean factor) {
        this.factor = factor;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroAfiliado() {
        return numeroAfiliado;
    }

    public void setNumeroAfiliado(String numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaUltimaConsulta() {
        return fechaUltimaConsulta;
    }

    public void setFechaUltimaConsulta(String fechaUltimaConsulta) {
        this.fechaUltimaConsulta = fechaUltimaConsulta;
    }
    
}
