/*
 *Clase DAO dedicada al paciente;
 */
package DAO;

import ClasesBase.modelo.ObraSocial;
import ClasesBase.modelo.Paciente;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOPaciente {
    
    private DAOConexion conexion;
    private DAOObraSocial daoObraSocial;
    private DAOAntecedentesFamiliares daoAntecFam;
    private DAOAntecedentesGenerales daoAntecGen;
    private DAOAntecedentesGinecologicos daoAntecGinec;
    private LinkedList<Paciente> pacientes;
    private ResultSet rs;
    private Connection conn;
    private Paciente p;
    private String consulta;
    private PreparedStatement pst;
    
    public DAOPaciente() {
        conexion = new DAOConexion();
        daoObraSocial = new DAOObraSocial();
        daoAntecFam = new DAOAntecedentesFamiliares();
        daoAntecGen = new DAOAntecedentesGenerales();
        daoAntecGinec = new DAOAntecedentesGinecologicos();
    }
    
    /**
     * Metodo utilizado para registrar un paciente
     * @param p Paciente a registrar
     * @return true si se registra correctamente, false si no se registra
     */ 
    public boolean registrarPaciente(Paciente p)
    {
        try {
            conn = conexion.conectarBD();
            String cons ="";
            if (p.getObraSocial().getId()!=0){
                cons = "INSERT INTO sistemacarla.paciente VALUES (?,?,?,?,str_to_date(?, '%d/%c/%Y'),?,?,?,?)";
                pst = conn.prepareStatement(cons);
                pst.setInt(8,p.getObraSocial().getId());
                pst.setString(9,p.getNumeroAfiliado());
            } else {
                cons = "INSERT INTO sistemacarla.paciente (dni, nombre, apellido, telefono, fechaNacimiento, grupoSanguineo, factor, obrasocial, numeroAfiliado) VALUES (?,?,?,?,str_to_date(?, '%d/%c/%Y'),?,?,?,?)";
                pst = conn.prepareStatement(cons);
                pst.setNull(8,java.sql.Types.INTEGER);
                pst.setString(9,"");
            }
            pst.setLong(1,p.getDni());
            pst.setString(2,p.getNombre());
            pst.setString(3,p.getApellido());
            pst.setString(4,p.getTelefono());
            pst.setString(5,p.getFechaNacimiento());
            pst.setString(6,p.getGrupoSanguineo());
            pst.setBoolean(7,p.getFactor());
           return (((pst.executeUpdate() > 0) ? true : false) && registrarAntecedentes(p));
        } catch (SQLException ex) {
            Logger.getLogger(DAOObraSocial.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOPaciente.class.getName()).log(Level.SEVERE, null, ex);
            }
            conexion.desconectarBD(conn);
        }

    }
    
    /**
     * Metodo utilizado para registrar los antecedentes de un paciente
     * @param p Paciente que posee los antecedentes a registrar
     * @return true si se registran correctamente, false si no se registran
     */
    private boolean registrarAntecedentes(Paciente p) 
    { 
        return (daoAntecFam.registrarAntecedentesFamiliares(p.getAntecFam(),p.getDni()) && 
                daoAntecGen.registrarAntecedentesGenerales(p.getAntecGen(),p.getDni()) &&
                daoAntecGinec.registrarAntecedentesGinecologicos(p.getAntecGinec(),p.getDni()));
    }
    
    /**
     * Metodo utilizado para obtener todos los pacientes segun filtro
     * @param nombre nombre a partir del cual filtrar
     * @param apellido apellido a partir del cual filtrar
     * @param dni dni a partir del cual filtrar
     * @return Lista que contiene todos los pacientes segun filtros
     */
    public LinkedList<Paciente> getAllPacientes(String nombre,String apellido, String dni)
    {
        pacientes = new LinkedList<>();
        
        consulta = "SELECT dni, nombre, apellido, fechaNacimiento, MAX(fecha) AS fechaUltimaConsulta FROM sistemacarla.paciente LEFT JOIN sistemacarla.consulta ON dni=dniPaciente ";
        
        String where = "";
        String orderBy = "";
        
        if (!dni.isEmpty()){
            if (!where.isEmpty())
                where +=  " AND ";
            where += " CONVERT(dni, CHAR) LIKE ? ";
            if (!orderBy.isEmpty())
                orderBy +=  " , ";
            orderBy += " dni ASC ";
        }
        
        if (!apellido.isEmpty()){
            if (!where.isEmpty())
                where +=  " AND ";
            where += " apellido LIKE ? ";
            if (!orderBy.isEmpty())
                orderBy +=  " , ";
            orderBy += " apellido ASC ";
        }
        
        if (!nombre.isEmpty()){
            if (!where.isEmpty())
                where +=  " AND ";
            where += " nombre LIKE ? ";
            if (!orderBy.isEmpty())
                orderBy +=  " , ";
            orderBy += " nombre ASC ";
        }        
        
        if(!where.isEmpty())
            consulta += " WHERE "+where+" GROUP BY dni, nombre, apellido, fechaNacimiento ORDER BY "+ orderBy;
        else
            return pacientes;
        
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            int cant = 0;
            if (where.contains("dni")){
                cant++;
                pst.setString(cant, dni+'%');
            }
            if (where.contains("apellido")){
                cant++;
                pst.setString(cant, apellido+'%');
            }
            if (where.contains("nombre")){
                cant++;
                pst.setString(cant, nombre+'%');
            }
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                p = new Paciente();
                p.setDni(rs.getLong("dni"));
                if (p.getDni() == 0)
                   break;
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                String año, mes, dia;
                String fecha = rs.getString("fechaNacimiento");
                año = fecha.substring(0, 4);
                mes = fecha.substring(5, 7);
                dia = fecha.substring(8, 10);
                p.setFechaNacimiento(dia+"/"+mes+"/"+año);
                String fechaUltimaConsulta = "Sin consultas";
                if(rs.getObject("fechaUltimaConsulta") != null){
                    fechaUltimaConsulta = rs.getString("fechaUltimaConsulta");
                    año = fechaUltimaConsulta.substring(0, 4);
                    mes = fechaUltimaConsulta.substring(5, 7);
                    dia = fechaUltimaConsulta.substring(8, 10);
                    fechaUltimaConsulta = dia+"/"+mes+"/"+año;
                }
                p.setFechaUltimaConsulta(fechaUltimaConsulta);
                pacientes.add(p);
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        conexion.desconectarBD(conn);
        return pacientes;
    }
    
    /**
     * Metodo utilizado para verificar la existencia de un paciente
     * @param dni dni del paciente a validar
     * @return true si se encuentra, false si no se encuentra
     */
    public boolean verificarPaciente(long dni)
    {
        boolean verificacion = false;
        conn = conexion.conectarBD();
        consulta = "SELECT 1 FROM sistemacarla.paciente WHERE dni = ?";
        try {
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            if(pst.getResultSet().next())
            {
                verificacion = true;
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return verificacion;
    }
    
    /**
     * Metodo utilizado para obtener datos básicos de un paciente
     * @param dni
     * @return Paciente con datos básicos para ventana principal: dni, nombre, apellido, fechaNacimiento, fechaUltimaConsulta
     */
    public Paciente getPacienteBasico(long dni)
    {
        p = null;
        consulta = "SELECT dni, nombre, apellido, fechaNacimiento, MAX(fecha) AS fechaUltimaConsulta FROM sistemacarla.paciente LEFT JOIN sistemacarla.consulta ON dni=dniPaciente WHERE dni = ?";
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                p = new Paciente();
                p.setDni(dni);
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                String fecha = rs.getString("fechaNacimiento");
                String año = fecha.substring(0, 4);
                String mes = fecha.substring(5, 7);
                String dia = fecha.substring(8, 10);
                p.setFechaNacimiento(dia+"/"+mes+"/"+año);
                String fechaUltimaConsulta = "Sin consultas";
                if(rs.getObject("fechaUltimaConsulta") != null){
                    fechaUltimaConsulta = rs.getString("fechaUltimaConsulta");
                    año = fechaUltimaConsulta.substring(0, 4);
                    mes = fechaUltimaConsulta.substring(5, 7);
                    dia = fechaUltimaConsulta.substring(8, 10);
                    fechaUltimaConsulta = dia+"/"+mes+"/"+año;
                }
                p.setFechaUltimaConsulta(fechaUltimaConsulta);
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return p;
    }
    
    /**
     * Metodo utilizado para obtener un paciente completo
     * @param dni
     * @return Paciente completo requerido
     */
    public Paciente getPacienteCompleto(long dni)
    {
        p = null;
        consulta = "SELECT * FROM paciente WHERE dni = ?";
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet(); 
            while(rs.next())
            {
                p = new Paciente();
                p.setDni(dni);
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                String año, mes, dia;
                String fecha = rs.getString("fechaNacimiento");
                año = fecha.substring(0, 4);
                mes = fecha.substring(5, 7);
                dia = fecha.substring(8, 10);
                p.setFechaNacimiento(dia+"/"+mes+"/"+año);
                p.setFactor(rs.getBoolean("factor"));
                p.setGrupoSanguineo(rs.getString("grupoSanguineo"));
                p.setNumeroAfiliado(rs.getString("numeroAfiliado"));
                if(rs.getObject("obraSocial") != null && rs.getInt("obraSocial") != 0 )
                    p.setObraSocial(daoObraSocial.getObraSocial(rs.getInt("obraSocial")));
                else
                    p.setObraSocial(new ObraSocial(0, "Sin Obra Social"));
                p.setTelefono(rs.getString("telefono"));
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        
        p.setAntecFam(daoAntecFam.getAntecedenteFamiliar(dni));
        p.setAntecGinec(daoAntecGinec.getAntecedenteGinecologico(dni));
        p.setAntecGen(daoAntecGen.getAntecedenteGeneral(dni));
        
        return p;
    }
    
    /**
     * Metodo utilizado para obtener un paciente sin antecedentes
     * @param dni dni del paciente buscado
     * @return Paciente buscado
     */
    public Paciente getPaciente(long dni)
    {
        p = null;
        consulta = "SELECT * FROM paciente WHERE dni = ?";
        try {
            conn = conexion.conectarBD();
            pst = conn.prepareStatement(consulta);
            pst.setLong(1, dni);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                p = new Paciente();
                p.setDni(dni);
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                String año, mes, dia;
                String fecha = rs.getString("fechaNacimiento");
                año = fecha.substring(0, 4);
                mes = fecha.substring(5, 7);
                dia = fecha.substring(8, 10);
                p.setFechaNacimiento(dia+"/"+mes+"/"+año);
                p.setFactor(rs.getBoolean("factor"));
                p.setGrupoSanguineo(rs.getString("grupoSanguineo"));
                p.setNumeroAfiliado(rs.getString("numeroAfiliado"));
                if(rs.getObject("obraSocial") != null && rs.getInt("obraSocial") != 0 )
                    p.setObraSocial(daoObraSocial.getObraSocial(rs.getInt("obraSocial")));
                else
                    p.setObraSocial(new ObraSocial(0, "Sin Obra Social"));
                p.setTelefono(rs.getString("telefono"));
            }
            pst.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }        
        return p;
    }

    /**
     * Metodo utilizado para actualizar un paciente
     * @param dniAnterior dni del paciente anterior a la modificacion
     * @param p Paciente a actualizar
     * @return true si se actualiza correctamente, false si no se actualiza
     */
    public boolean actualizarPaciente(Paciente p,long dniAnterior) {
        boolean rtdo = false;
        try{
        String cons = "";
        conn = conexion.conectarBD();
        if(p.getObraSocial().getId() != 0){
            cons = "UPDATE sistemacarla.paciente SET "
                    + "dni = ?,"
                    + "nombre = ?,"
                    + "apellido = ?,"
                    + "telefono = ?,"
                    + "fechaNacimiento = str_to_date(?, '%d/%c/%Y'),"
                    + "grupoSanguineo = ?,"
                    + "factor = ?,"
                    + "obraSocial = ?,"
                    + "numeroAfiliado = ? "
                    + "WHERE dni = ?";
            pst = conn.prepareStatement(cons);
            pst.setInt(8, p.getObraSocial().getId());
            pst.setString(9, p.getNumeroAfiliado());
            pst.setLong(10, dniAnterior);
        } else {
            cons = "UPDATE sistemacarla.paciente SET "
                    + "dni = ?,"
                    + "nombre = ?,"
                    + "apellido = ?,"
                    + "telefono = ?,"
                    + "fechaNacimiento = str_to_date(?, '%d/%c/%Y'),"
                    + "grupoSanguineo = ?,"
                    + "factor = ?,"
                    + "obraSocial = NULL,"
                    + "numeroAfiliado = NULL "
                    + "WHERE dni = ?";
            pst = conn.prepareStatement(cons);
            pst.setLong(8, dniAnterior);
        }
        pst.setLong(1, p.getDni());
        pst.setString(2, p.getNombre());
        pst.setString(3, p.getApellido());
        pst.setString(4, p.getTelefono());
        pst.setString(5, p.getFechaNacimiento());
        pst.setString(6, p.getGrupoSanguineo());
        pst.setBoolean(7, p.getFactor());
        rtdo = (((pst.executeUpdate() > 0) ? true : false) && actualizarAntecedentes(p));
        pst.close();
        }
        catch(Exception ex) 
        {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
       return rtdo;
    }
    
    /**
     * Metodo utilizado para actualizar los antecedentes de un paciente
     * @param p Paciente que contiene los antecedentes a actualizar
     * @return true si se actualizan correctamente, false si no se actualizan
     */
    private boolean actualizarAntecedentes(Paciente p) {
       long dni = p.getDni();
       return (daoAntecFam.actualizarAntecedente(p.getAntecFam(),dni) &&
               daoAntecGen.actualizarAntecedente(p.getAntecGen(),dni) &&
               daoAntecGinec.actualizarAntecedente(p.getAntecGinec(),dni));
   
    }
    
    /**
     * Metodo utilizado para eliminar un paciente
     * @param dni dni del paciente a eliminar
     * @return true si se elimina correctamente, false si no se elimina
     */
    public boolean borrarPaciente(long dni) {
        try {
            conn = conexion.conectarBD();
            String cons = "DELETE FROM sistemacarla.paciente WHERE dni = ?";
            pst = conn.prepareStatement(cons);
            pst.setLong(1, dni);
            return (pst.executeUpdate() > 0) ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DAOObraSocial.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        finally {
            conexion.desconectarBD(conn);
        }
    }

    /**
     * Permite verificar que no existan dos pacientes registrados en la misma obra social con el mismo número de afiliado
     * @param idObraSocial id de la Obra Social a la que pertenece el número de afiliado a verificar
     * @param numeroAfiliado número de afiliado a verificar
     * @return el paciente que concuerda con dicha obra social y n° de afiliado, null si no hubo coincidencias
     */
    public Paciente verificarNroAfiliado(int idObraSocial, String numeroAfiliado) {
        Paciente match = null;
        try {
            conn = conexion.conectarBD();
            consulta = "SELECT dni, nombre, apellido FROM sistemacarla.paciente WHERE obraSocial = ? AND numeroAfiliado = ?";
            pst = conn.prepareStatement(consulta);
            pst.setInt(1, idObraSocial);
            pst.setString(2, numeroAfiliado);
            pst.executeQuery();
            rs = pst.getResultSet();
            while(rs.next())
            {
                match = new Paciente();
                match.setDni(rs.getLong("dni"));
                match.setNombre(rs.getString("nombre"));
                match.setApellido(rs.getString("apellido"));
            }
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            conexion.desconectarBD(conn);
        }
        return match;
    }

}
