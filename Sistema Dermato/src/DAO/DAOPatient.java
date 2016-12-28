/*
 *Clase DAO dedicada al paciente;
 */
package DAO;

import ClasesBase.Patient;
import static Utils.DBConstants.LEFT_JOIN;
import static Utils.DBConstants.PatientDBColumns.dni;
import Utils.DBConstants.Tables;
import Utils.DBConstants.VisitDBColumns;
import Utils.DBUtils;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fran
 */
public class DAOPatient extends DAOBasics{

    private final DAOPrepaidHealthInsurance daoPrepaidHealthInsurance;
    private final DAOAntecedents daoAntecedents;
    private LinkedList<Patient> pacientes;
    private Patient patient;

    public DAOPatient() {
        daoPrepaidHealthInsurance = new DAOPrepaidHealthInsurance();
        daoAntecedents = new DAOAntecedents();
    }

    /**
     * Method used to register a patient
     *
     * @param patient Patient to register
     * @return true if registered successful, false otherwise
     */
    public boolean registerPatient(Patient patient) {
        try {
            connection = daoConnection.openDBConnection();
            String cons = DBUtils.getInsertStatementWithValuesOnly(Tables.Patient,
                    "?,?,?,?,?,?,str_to_date(?, '%d/%c/%Y'),?,?,"
                    + "str_to_date(?, '%d/%c/%Y')");
            preparedStatement = connection.prepareStatement(cons);
            if (patient.getPrepaidHealthInsurance().getId() != 0) {
                preparedStatement.setInt(8, patient.getPrepaidHealthInsurance().getId());
                preparedStatement.setString(9, patient.getPrepaidHealthInsuranceNumber());
            } else {
                preparedStatement = connection.prepareStatement(cons);
                preparedStatement.setNull(8, java.sql.Types.INTEGER);
                preparedStatement.setString(9, "");
            }
            preparedStatement.setLong(1, patient.getDni());
            preparedStatement.setString(2, patient.getName());
            preparedStatement.setString(3, patient.getLastname());
            preparedStatement.setString(4, patient.getPhone());
            preparedStatement.setString(5, patient.getAddress());
            preparedStatement.setString(6, patient.getCity());
            preparedStatement.setString(7, patient.getBirthday());
            preparedStatement.setString(10, patient.getFirstVisitDate());
            return ((preparedStatement.executeUpdate() > 0) && registerAntecedents(patient));
        } catch (SQLException ex) {
            Logger.getLogger(DAOPrepaidHealthInsurance.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOPatient.class.getName()).log(Level.SEVERE, null, ex);
            }
            daoConnection.closeDBConnection(connection);
        }
    }

    /**
     * Method used to register the patient antecedents
     *
     * @param patient Patient with antecedents
     * @return true if registered correctly, false otherwise
     */
    private boolean registerAntecedents(Patient patient) {
        return (daoAntecedents.registerAntecedents(patient.getAntecendents(),
                patient.getDni()));
    }

    /**
     * Method used to retrieve all the patients according to the filters
     *
     * @param nombre name filter
     * @param apellido last name filter
     * @param dni dni filter
     * @return List containing all the patients according to the filters
     */
    public LinkedList<Patient> getAllPatients(String filterName, String filterLastName, String filterDni) {
        pacientes = new LinkedList<>();

        query = "SELECT dni, nombre, apellido, fechaNacimiento, MAX(fecha) AS fechaUltimaConsulta FROM  ";

        String from = DBUtils.getTableJoin(LEFT_JOIN, Tables.Patient, 
                Tables.Visit, dni.name(), VisitDBColumns.patient.name());
        
//        String where = "";
//        String orderBy = "";
//
//        if (!filterDni.isEmpty()) {
//            if (!where.isEmpty()) {
//                where += " AND ";
//            }
//            where += " CONVERT(dni, CHAR) LIKE ? ";
//            if (!orderBy.isEmpty()) {
//                orderBy += " , ";
//            }
//            orderBy += " dni ASC ";
//        }
//
//        if (!apellido.isEmpty()) {
//            if (!where.isEmpty()) {
//                where += " AND ";
//            }
//            where += " apellido LIKE ? ";
//            if (!orderBy.isEmpty()) {
//                orderBy += " , ";
//            }
//            orderBy += " apellido ASC ";
//        }
//
//        if (!nombre.isEmpty()) {
//            if (!where.isEmpty()) {
//                where += " AND ";
//            }
//            where += " nombre LIKE ? ";
//            if (!orderBy.isEmpty()) {
//                orderBy += " , ";
//            }
//            orderBy += " nombre ASC ";
//        }
//
//        if (!where.isEmpty()) {
//            query = DBUtils.getSelectColumnsStatementWithWhereGroupByAndOrder(from, columns, whereCondition, groupByColumns);
//            query += " WHERE " + where + " GROUP BY dni, nombre, apellido, fechaNacimiento ORDER BY " + orderBy;
//        } else {
//            return pacientes;
//        }
//
//        try {
//            connection = daoConnection.openDBConnection();
//            preparedStatement = connection.prepareStatement(query);
//            int cant = 0;
//            if (where.contains("dni")) {
//                cant++;
//                preparedStatement.setString(cant, dni + '%');
//            }
//            if (where.contains("apellido")) {
//                cant++;
//                preparedStatement.setString(cant, apellido + '%');
//            }
//            if (where.contains("nombre")) {
//                cant++;
//                preparedStatement.setString(cant, nombre + '%');
//            }
//            preparedStatement.executeQuery();
//            resultSet = preparedStatement.getResultSet();
//            while (resultSet.next()) {
//                patient = new Patient();
//                patient.setDni(resultSet.getLong("dni"));
//                if (patient.getDni() == 0) {
//                    break;
//                }
//                patient.setNombre(resultSet.getString("nombre"));
//                patient.setApellido(resultSet.getString("apellido"));
//                String año, mes, dia;
//                String fecha = resultSet.getString("fechaNacimiento");
//                año = fecha.substring(0, 4);
//                mes = fecha.substring(5, 7);
//                dia = fecha.substring(8, 10);
//                patient.setFechaNacimiento(dia + "/" + mes + "/" + año);
//                String fechaUltimaConsulta = "Sin consultas";
//                if (resultSet.getObject("fechaUltimaConsulta") != null) {
//                    fechaUltimaConsulta = resultSet.getString("fechaUltimaConsulta");
//                    año = fechaUltimaConsulta.substring(0, 4);
//                    mes = fechaUltimaConsulta.substring(5, 7);
//                    dia = fechaUltimaConsulta.substring(8, 10);
//                    fechaUltimaConsulta = dia + "/" + mes + "/" + año;
//                }
//                patient.setLastVisitDate(fechaUltimaConsulta);
//                pacientes.add(patient);
//            }
//            preparedStatement.close();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        daoConnection.closeDBConnection(connection);
        return pacientes;
    }

    /**
     * Metodo utilizado para verificar la existencia de un paciente
     *
     * @param dni dni del paciente a validar
     * @return true si se encuentra, false si no se encuentra
     */
    public boolean verificarPaciente(long dni) {
        boolean verificacion = false;
//        connection = daoConnection.openDBConnection();
//        query = "SELECT 1 FROM sistemaCarla.paciente WHERE dni = ?";
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setLong(1, dni);
//            preparedStatement.executeQuery();
//            if (preparedStatement.getResultSet().next()) {
//                verificacion = true;
//            }
//            preparedStatement.close();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            daoConnection.closeDBConnection(connection);
//        }
        return verificacion;
    }

    /**
     * Metodo utilizado para obtener datos básicos de un paciente
     *
     * @param dni
     * @return Paciente con datos básicos para ventana principal: dni, nombre,
     * apellido, fechaNacimiento, fechaUltimaConsulta
     */
    public Patient getPacienteBasico(long dni) {
        patient = null;
//        query = "SELECT dni, nombre, apellido, fechaNacimiento, MAX(fecha) AS fechaUltimaConsulta FROM sistemaCarla.paciente LEFT JOIN sistemaCarla.consulta ON dni=dniPaciente WHERE dni = ?";
//        try {
//            connection = daoConnection.openDBConnection();
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setLong(1, dni);
//            preparedStatement.executeQuery();
//            resultSet = preparedStatement.getResultSet();
//            while (resultSet.next()) {
//                patient = new Patient();
//                patient.setDni(dni);
//                patient.setNombre(resultSet.getString("nombre"));
//                patient.setApellido(resultSet.getString("apellido"));
//                String fecha = resultSet.getString("fechaNacimiento");
//                String año = fecha.substring(0, 4);
//                String mes = fecha.substring(5, 7);
//                String dia = fecha.substring(8, 10);
//                patient.setFechaNacimiento(dia + "/" + mes + "/" + año);
//                String fechaUltimaConsulta = "Sin consultas";
//                if (resultSet.getObject("fechaUltimaConsulta") != null) {
//                    fechaUltimaConsulta = resultSet.getString("fechaUltimaConsulta");
//                    año = fechaUltimaConsulta.substring(0, 4);
//                    mes = fechaUltimaConsulta.substring(5, 7);
//                    dia = fechaUltimaConsulta.substring(8, 10);
//                    fechaUltimaConsulta = dia + "/" + mes + "/" + año;
//                }
//                patient.setLastVisitDate(fechaUltimaConsulta);
//            }
//            preparedStatement.close();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            daoConnection.closeDBConnection(connection);
//        }
        return patient;
    }

    /**
     * Metodo utilizado para obtener un paciente completo
     *
     * @param dni
     * @return Paciente completo requerido
     */
    public Patient getPacienteCompleto(long dni) {
        patient = null;
//        query = "SELECT * FROM paciente WHERE dni = ?";
//        try {
//            connection = daoConnection.openDBConnection();
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setLong(1, dni);
//            preparedStatement.executeQuery();
//            resultSet = preparedStatement.getResultSet();
//            while (resultSet.next()) {
//                patient = new Patient();
//                patient.setDni(dni);
//                patient.setNombre(resultSet.getString("nombre"));
//                patient.setApellido(resultSet.getString("apellido"));
//                String año, mes, dia;
//                String fecha = resultSet.getString("fechaNacimiento");
//                año = fecha.substring(0, 4);
//                mes = fecha.substring(5, 7);
//                dia = fecha.substring(8, 10);
//                patient.setFechaNacimiento(dia + "/" + mes + "/" + año);
//                patient.setFactor(resultSet.getBoolean("factor"));
//                patient.setGrupoSanguineo(resultSet.getString("grupoSanguineo"));
//                patient.setNumeroAfiliado(resultSet.getString("numeroAfiliado"));
//                if (resultSet.getObject("obraSocial") != null && resultSet.getInt("obraSocial") != 0) {
//                    patient.setObraSocial(daoPrepaidHealthInsurance.getObraSocial(resultSet.getInt("obraSocial")));
//                } else {
//                    patient.setObraSocial(new PrepaidHealthInsurance(0, "Sin Obra Social"));
//                }
//                patient.setTelefono(resultSet.getString("telefono"));
//            }
//            preparedStatement.close();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            daoConnection.closeDBConnection(connection);
//        }
//
//        patient.setAntecFam(daoAntecFam.getAntecedenteFamiliar(dni));
//        patient.setAntecGinec(daoAntecGinec.getAntecedenteGinecologico(dni));
//        patient.setAntecGen(daoAntecGen.getAntecedenteGeneral(dni));

        return patient;
    }

    /**
     * Metodo utilizado para obtener un paciente sin antecedentes
     *
     * @param dni dni del paciente buscado
     * @return Paciente buscado
     */
    public Patient getPatient(long dni) {
        patient = null;
//        query = "SELECT * FROM paciente WHERE dni = ?";
//        try {
//            connection = daoConnection.openDBConnection();
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setLong(1, dni);
//            preparedStatement.executeQuery();
//            resultSet = preparedStatement.getResultSet();
//            while (resultSet.next()) {
//                patient = new Patient();
//                patient.setDni(dni);
//                patient.setNombre(resultSet.getString("nombre"));
//                patient.setApellido(resultSet.getString("apellido"));
//                String año, mes, dia;
//                String fecha = resultSet.getString("fechaNacimiento");
//                año = fecha.substring(0, 4);
//                mes = fecha.substring(5, 7);
//                dia = fecha.substring(8, 10);
//                patient.setFechaNacimiento(dia + "/" + mes + "/" + año);
//                patient.setFactor(resultSet.getBoolean("factor"));
//                patient.setGrupoSanguineo(resultSet.getString("grupoSanguineo"));
//                patient.setNumeroAfiliado(resultSet.getString("numeroAfiliado"));
//                if (resultSet.getObject("obraSocial") != null && resultSet.getInt("obraSocial") != 0) {
//                    patient.setObraSocial(daoPrepaidHealthInsurance.getObraSocial(resultSet.getInt("obraSocial")));
//                } else {
//                    patient.setObraSocial(new PrepaidHealthInsurance(0, "Sin Obra Social"));
//                }
//                patient.setTelefono(resultSet.getString("telefono"));
//            }
//            preparedStatement.close();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            daoConnection.closeDBConnection(connection);
//        }
        return patient;
    }

    /**
     * Metodo utilizado para actualizar un paciente
     *
     * @param dniAnterior dni del paciente anterior a la modificacion
     * @param p Paciente a actualizar
     * @return true si se actualiza correctamente, false si no se actualiza
     */
    public boolean actualizarPaciente(Patient p, long dniAnterior) {
        boolean rtdo = false;
//        try {
//            String cons = "";
//            connection = daoConnection.openDBConnection();
//            if (p.getObraSocial().getId() != 0) {
//                cons = "UPDATE sistemaCarla.Paciente SET "
//                        + "dni = ?,"
//                        + "nombre = ?,"
//                        + "apellido = ?,"
//                        + "telefono = ?,"
//                        + "fechaNacimiento = str_to_date(?, '%d/%c/%Y'),"
//                        + "grupoSanguineo = ?,"
//                        + "factor = ?,"
//                        + "obraSocial = ?,"
//                        + "numeroAfiliado = ? "
//                        + "WHERE dni = ?";
//                preparedStatement = connection.prepareStatement(cons);
//                preparedStatement.setInt(8, p.getObraSocial().getId());
//                preparedStatement.setString(9, p.getNumeroAfiliado());
//                preparedStatement.setLong(10, dniAnterior);
//            } else {
//                cons = "UPDATE sistemaCarla.Paciente SET "
//                        + "dni = ?,"
//                        + "nombre = ?,"
//                        + "apellido = ?,"
//                        + "telefono = ?,"
//                        + "fechaNacimiento = str_to_date(?, '%d/%c/%Y'),"
//                        + "grupoSanguineo = ?,"
//                        + "factor = ?,"
//                        + "obraSocial = NULL,"
//                        + "numeroAfiliado = NULL "
//                        + "WHERE dni = ?";
//                preparedStatement = connection.prepareStatement(cons);
//                preparedStatement.setLong(8, dniAnterior);
//            }
//            preparedStatement.setLong(1, p.getDni());
//            preparedStatement.setString(2, p.getNombre());
//            preparedStatement.setString(3, p.getApellido());
//            preparedStatement.setString(4, p.getTelefono());
//            preparedStatement.setString(5, p.getFechaNacimiento());
//            preparedStatement.setString(6, p.getGrupoSanguineo());
//            preparedStatement.setBoolean(7, p.getFactor());
//            rtdo = (((preparedStatement.executeUpdate() > 0) ? true : false) && actualizarAntecedentes(p));
//            preparedStatement.close();
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            daoConnection.closeDBConnection(connection);
//        }
        return rtdo;
    }

    /**
     * Metodo utilizado para actualizar los antecedentes de un paciente
     *
     * @param p Paciente que contiene los antecedentes a actualizar
     * @return true si se actualizan correctamente, false si no se actualizan
     */
    private boolean actualizarAntecedentes(Patient p) {
//        long dni = p.getDni();
//        return (daoAntecFam.actualizarAntecedente(p.getAntecFam(), dni)
//                && daoAntecGen.actualizarAntecedente(p.getAntecGen(), dni)
//                && daoAntecGinec.actualizarAntecedente(p.getAntecGinec(), dni));
        return false;
    }

    /**
     * Metodo utilizado para eliminar un paciente
     *
     * @param dni dni del paciente a eliminar
     * @return true si se elimina correctamente, false si no se elimina
     */
    public boolean borrarPaciente(long dni) {
//        try {
//            connection = daoConnection.openDBConnection();
//            String cons = "DELETE FROM sistemaCarla.PACIENTE WHERE dni = ?";
//            preparedStatement = connection.prepareStatement(cons);
//            preparedStatement.setLong(1, dni);
//            return (preparedStatement.executeUpdate() > 0) ? true : false;
//        } catch (SQLException ex) {
//            Logger.getLogger(DAOPrepaidHealthInsurance.class.getName()).log(Level.SEVERE, null, ex);
            return false;
//        } finally {
//            daoConnection.closeDBConnection(connection);
//        }
    }

    /**
     * Permite verificar que no existan dos pacientes registrados en la misma
     * obra social con el mismo número de afiliado
     *
     * @param idObraSocial id de la Obra Social a la que pertenece el número de
     * afiliado a verificar
     * @param numeroAfiliado número de afiliado a verificar
     * @return el paciente que concuerda con dicha obra social y n° de afiliado,
     * null si no hubo coincidencias
     */
    public Patient verificarNroAfiliado(int idObraSocial, String numeroAfiliado) {
        Patient match = null;
//        try {
//            connection = daoConnection.openDBConnection();
//            query = "SELECT dni, nombre, apellido FROM sistemaCarla.paciente WHERE obraSocial = ? AND numeroAfiliado = ?";
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, idObraSocial);
//            preparedStatement.setString(2, numeroAfiliado);
//            preparedStatement.executeQuery();
//            resultSet = preparedStatement.getResultSet();
//            while (resultSet.next()) {
//                match = new Patient();
//                match.setDni(resultSet.getLong("dni"));
//                match.setNombre(resultSet.getString("nombre"));
//                match.setApellido(resultSet.getString("apellido"));
//            }
//            preparedStatement.close();
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        } finally {
//            daoConnection.closeDBConnection(connection);
//        }
        return match;
    }
}
