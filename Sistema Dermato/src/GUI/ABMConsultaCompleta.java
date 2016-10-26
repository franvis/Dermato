/*
 * Interfaz donde se registran y modifican las consultas
 */
package GUI;

import ClasesBase.modelo.Consulta;
import ClasesBase.modelo.ExamenObstetrico;
import ClasesBase.modelo.Paciente;
import ClasesBase.modelo.ExamenGinecologico;
import ClasesBase.*;
import DAO.DAOConsulta;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class ABMConsultaCompleta extends javax.swing.JFrame {
    private HistoriaClinica HC;
    private ExamenGinecologico eGinec;
    private ExamenObstetrico eObste;
    private DAOConsulta daoConsulta;
    private Consulta cons;
    private Calendar c;
    private String dia;
    private String mes;
    private String año;
    private int tipoConsulta,idConsulta;//tipoConsulta = 0 Obstetrica , 1 Ginecologica y 2 Completa
    public static int OBSTETRICA = 0;
    public static int GINECOLOGICA = 1;
    public static int COMPLETA = 2;
    private long dni;
    private boolean procedencia;//true si proviene de "ver" una consulta, false si es nueva consulta.
    
    /**
     * Creates new form ABMConsultaCompleta
     */
    public ABMConsultaCompleta(java.awt.Frame parent, boolean modal,int tipoConsulta,long dni, Paciente paciente) {
        initComponents();
        this.txtfAlturaUterina.setDisabledTextColor(GestorEstilos.getColorTexto());
        this.txtfAlturaUterina.setBackground(GestorEstilos.getColorTerciario());
        this.txtfAlturaUterina.setSelectionColor(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        this.txtfPresionArterialmm.setBackground(GestorEstilos.getColorTerciario());
        this.txtfPresionArterialmm.setDisabledTextColor(GestorEstilos.getColorTexto());
        this.txtfPresionArterialmm.setSelectionColor(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        this.txtfPresionArterialHg.setBackground(GestorEstilos.getColorTerciario());
        this.txtfPresionArterialHg.setDisabledTextColor(GestorEstilos.getColorTexto());
        this.txtfPresionArterialHg.setSelectionColor(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        this.txtaMotivo.grabFocus();
        eGinec = new ExamenGinecologico();
        eObste = new ExamenObstetrico();
        daoConsulta = new DAOConsulta();
        this.lblIMC.setText("");
        this.procedencia = false;
        HC = (HistoriaClinica)parent;
        this.dni = dni;
        this.tipoConsulta = tipoConsulta;
        this.llenarCampos(paciente);
        c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH) +"";
        mes = c.get(Calendar.MONTH)+1+"";
        año = c.get(Calendar.YEAR)+"";
        this.lblFecha.setText(dia+"/"+mes+"/"+año);
        if(tipoConsulta != 2)
        {
            tpaneExamenes.remove(tipoConsulta);
        }
        if(tipoConsulta == 2)
        {
            this.tpaneExamenes.setSelectedComponent(this.pnlFisico);
            this.txtaDiagnostico.setNextFocusableComponent(this.txtaMamas);
            this.txtaPapYColpo.setNextFocusableComponent(this.txtfTalla);
        }
        else if(tipoConsulta == 1)
        {   
            this.setTitle("Consulta Ginecológica");
            this.txtaPapYColpo.setNextFocusableComponent(this.btnGuardar);
        }
        else if(tipoConsulta == 0)
        {
            this.setTitle("Consulta Obstétrica");
            this.tpaneExamenes.setSelectedComponent(this.pnlObstetrico);
            this.txtaDiagnostico.setNextFocusableComponent(this.txtfTalla);
        }
        setIconImage(getIconImage());
        GestorEstilos.pintar(this);
        pnlFecha.setBackground(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        pnlApellidoNombre.setBackground(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        this.setMinimumSize(new Dimension(789, 490));
        this.setMaximumSize(new Dimension(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().height));
        this.setExtendedState(parent.getExtendedState());
        this.setLocationRelativeTo(parent);//Mantener siempre debajo del anterior if
        //eventos de la página
        KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        this.getRootPane().registerKeyboardAction(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    salir();
                }
            }, strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    
    /**
     * Creates new form ABMConsultaCompleta
     */
    public ABMConsultaCompleta(java.awt.Frame parent, boolean modal,int tipoConsulta,long dni, Paciente paciente, Consulta con) {
        initComponents();
        this.txtfAlturaUterina.setDisabledTextColor(GestorEstilos.getColorTexto());
        this.txtfAlturaUterina.setBackground(GestorEstilos.getColorTerciario());
        this.txtfAlturaUterina.setSelectionColor(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        this.txtfPresionArterialmm.setBackground(GestorEstilos.getColorTerciario());
        this.txtfPresionArterialmm.setDisabledTextColor(GestorEstilos.getColorTexto());
        this.txtfPresionArterialmm.setSelectionColor(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        this.txtfPresionArterialHg.setBackground(GestorEstilos.getColorTerciario());
        this.txtfPresionArterialHg.setDisabledTextColor(GestorEstilos.getColorTexto());
        this.txtfPresionArterialHg.setSelectionColor(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        this.txtaMotivo.grabFocus();
        eGinec = new ExamenGinecologico();
        eObste = new ExamenObstetrico();
        daoConsulta = new DAOConsulta();
        HC = (HistoriaClinica)parent;
        this.procedencia = true;
        this.dni = dni;
        this.tipoConsulta = tipoConsulta;
        this.lblIMC.setText("");
        idConsulta = con.getId();
        c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH) +"";
        mes = c.get(Calendar.MONTH)+1+"";
        año = c.get(Calendar.YEAR)+"";
        this.lblFecha.setText(dia+"/"+mes+"/"+año);
        cons = con;
        llenarCajas(con);
        llenarCampos(paciente);
        cambiarBotones(false);
        setearEstadoCajas(false,tipoConsulta);
        if(tipoConsulta != 2)
        {
            tpaneExamenes.remove(tipoConsulta);
        }
        if(tipoConsulta == 2)
        {
            this.tpaneExamenes.setSelectedComponent(this.pnlFisico);
            this.txtaDiagnostico.setNextFocusableComponent(this.txtaMamas);
            this.txtaPapYColpo.setNextFocusableComponent(this.txtfTalla);
        }
        else if(tipoConsulta == 1)
        {
            this.setTitle("Consulta Ginecológica");
            this.txtaPapYColpo.setNextFocusableComponent(this.btnGuardar);
        }
        else if(tipoConsulta == 0)
        {
            this.setTitle("Consulta Obstétrica");
            this.tpaneExamenes.setSelectedComponent(this.pnlObstetrico);
            this.txtaDiagnostico.setNextFocusableComponent(this.txtfPeso);
        }
        this.setLocationRelativeTo(parent);
        this.setearEstadoCajas(!procedencia, tipoConsulta);
        setIconImage(getIconImage());
        GestorEstilos.pintar(this);
        pnlFecha.setBackground(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        pnlApellidoNombre.setBackground(GestorEstilos.getColorSecundario(GestorEstilos.colorActual));
        this.setMinimumSize(new Dimension(789, 490));
        this.setMaximumSize(new Dimension(this.getToolkit().getScreenSize().width, this.getToolkit().getScreenSize().height));
        this.setExtendedState(parent.getExtendedState());
        this.setLocationRelativeTo(parent);//Mantener siempre debajo del anterior if
        //eventos de la página
        KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        this.getRootPane().registerKeyboardAction(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    salir();
                }
            }, strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrpMAF = new javax.swing.ButtonGroup();
        tpaneExamenes = new javax.swing.JTabbedPane();
        pnlFisico = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        txtaExamenFisico = new javax.swing.JTextArea();
        pnlMotivo = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtaMotivo = new javax.swing.JTextArea();
        pnlFecha = new javax.swing.JPanel();
        lblstaticFecha = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        pnlTratamiento = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtaTratamiento = new javax.swing.JTextArea();
        pnlEstudiosComplementarios = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtaEstudiosComple = new javax.swing.JTextArea();
        pnlLaboratorio = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtaLaboratorio = new javax.swing.JTextArea();
        pnlObservaciones1 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtaDiagnostico = new javax.swing.JTextArea();
        pnlBotones1 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        pnlDatosPersonales = new javax.swing.JPanel();
        lblstaticDni = new javax.swing.JLabel();
        lblstaticNumAfiliado = new javax.swing.JLabel();
        lblstaticObraSocial = new javax.swing.JLabel();
        lblstaticFechaNacimiento = new javax.swing.JLabel();
        lblDni = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        lblNumeroAfiliado = new javax.swing.JLabel();
        lblObraSocial = new javax.swing.JLabel();
        lblstaticFechaPrimeraConsulta = new javax.swing.JLabel();
        lblFechaPrimeraConsulta = new javax.swing.JLabel();
        lblLocalidad = new javax.swing.JLabel();
        lblstaticLocalidad = new javax.swing.JLabel();
        pnlApellidoNombre = new javax.swing.JPanel();
        lblNombrePaciente = new javax.swing.JLabel();
        lblstaticNombre = new javax.swing.JLabel();
        lblstaticEdad = new javax.swing.JLabel();
        lblEdad = new javax.swing.JLabel();
        lblLocalidad1 = new javax.swing.JLabel();
        lblstaticLocalidad1 = new javax.swing.JLabel();
        pnlBiopsia = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        txtaBiopsia = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Consulta Completa");
        setMinimumSize(new java.awt.Dimension(889, 490));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tpaneExamenes.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Exámenes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        tpaneExamenes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtaExamenFisico.setColumns(20);
        txtaExamenFisico.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaExamenFisico.setLineWrap(true);
        txtaExamenFisico.setRows(2);
        txtaExamenFisico.setTabSize(0);
        txtaExamenFisico.setWrapStyleWord(true);
        txtaExamenFisico.setNextFocusableComponent(txtaEstudiosComple);
        txtaExamenFisico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaExamenFisicoKeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(txtaExamenFisico);

        javax.swing.GroupLayout pnlFisicoLayout = new javax.swing.GroupLayout(pnlFisico);
        pnlFisico.setLayout(pnlFisicoLayout);
        pnlFisicoLayout.setHorizontalGroup(
            pnlFisicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );
        pnlFisicoLayout.setVerticalGroup(
            pnlFisicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );

        tpaneExamenes.addTab("Físico", pnlFisico);

        pnlMotivo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Motivo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlMotivo.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        txtaMotivo.setColumns(20);
        txtaMotivo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaMotivo.setLineWrap(true);
        txtaMotivo.setRows(2);
        txtaMotivo.setTabSize(0);
        txtaMotivo.setWrapStyleWord(true);
        txtaMotivo.setNextFocusableComponent(txtaTratamiento);
        txtaMotivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaMotivoKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(txtaMotivo);

        javax.swing.GroupLayout pnlMotivoLayout = new javax.swing.GroupLayout(pnlMotivo);
        pnlMotivo.setLayout(pnlMotivoLayout);
        pnlMotivoLayout.setHorizontalGroup(
            pnlMotivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
        );
        pnlMotivoLayout.setVerticalGroup(
            pnlMotivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
        );

        pnlFecha.setBackground(new java.awt.Color(228, 228, 241));
        pnlFecha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(153, 153, 153), new java.awt.Color(255, 255, 255), null));

        lblstaticFecha.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        lblstaticFecha.setForeground(new java.awt.Color(0, 51, 102));
        lblstaticFecha.setText("Fecha:");

        lblFecha.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(0, 51, 102));
        lblFecha.setText("FechaDeHoy");

        javax.swing.GroupLayout pnlFechaLayout = new javax.swing.GroupLayout(pnlFecha);
        pnlFecha.setLayout(pnlFechaLayout);
        pnlFechaLayout.setHorizontalGroup(
            pnlFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFechaLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(lblstaticFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(240, Short.MAX_VALUE))
        );
        pnlFechaLayout.setVerticalGroup(
            pnlFechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblstaticFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
            .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );

        pnlTratamiento.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Tratamiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlTratamiento.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        txtaTratamiento.setColumns(20);
        txtaTratamiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaTratamiento.setLineWrap(true);
        txtaTratamiento.setRows(2);
        txtaTratamiento.setTabSize(0);
        txtaTratamiento.setWrapStyleWord(true);
        txtaTratamiento.setNextFocusableComponent(txtaEstudiosComple);
        txtaTratamiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaTratamientoKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(txtaTratamiento);

        javax.swing.GroupLayout pnlTratamientoLayout = new javax.swing.GroupLayout(pnlTratamiento);
        pnlTratamiento.setLayout(pnlTratamientoLayout);
        pnlTratamientoLayout.setHorizontalGroup(
            pnlTratamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
        );
        pnlTratamientoLayout.setVerticalGroup(
            pnlTratamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );

        pnlEstudiosComplementarios.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Estudios Complementarios", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlEstudiosComplementarios.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        txtaEstudiosComple.setColumns(20);
        txtaEstudiosComple.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaEstudiosComple.setLineWrap(true);
        txtaEstudiosComple.setRows(2);
        txtaEstudiosComple.setTabSize(0);
        txtaEstudiosComple.setWrapStyleWord(true);
        txtaEstudiosComple.setNextFocusableComponent(txtaLaboratorio);
        txtaEstudiosComple.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaEstudiosCompleKeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(txtaEstudiosComple);

        javax.swing.GroupLayout pnlEstudiosComplementariosLayout = new javax.swing.GroupLayout(pnlEstudiosComplementarios);
        pnlEstudiosComplementarios.setLayout(pnlEstudiosComplementariosLayout);
        pnlEstudiosComplementariosLayout.setHorizontalGroup(
            pnlEstudiosComplementariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
        );
        pnlEstudiosComplementariosLayout.setVerticalGroup(
            pnlEstudiosComplementariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
        );

        pnlLaboratorio.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Laboratorio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlLaboratorio.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        txtaLaboratorio.setColumns(20);
        txtaLaboratorio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaLaboratorio.setLineWrap(true);
        txtaLaboratorio.setRows(2);
        txtaLaboratorio.setTabSize(0);
        txtaLaboratorio.setWrapStyleWord(true);
        txtaLaboratorio.setNextFocusableComponent(txtaDiagnostico);
        txtaLaboratorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaLaboratorioKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(txtaLaboratorio);

        javax.swing.GroupLayout pnlLaboratorioLayout = new javax.swing.GroupLayout(pnlLaboratorio);
        pnlLaboratorio.setLayout(pnlLaboratorioLayout);
        pnlLaboratorioLayout.setHorizontalGroup(
            pnlLaboratorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
        );
        pnlLaboratorioLayout.setVerticalGroup(
            pnlLaboratorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pnlObservaciones1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Diagnóstico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlObservaciones1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        txtaDiagnostico.setColumns(20);
        txtaDiagnostico.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaDiagnostico.setLineWrap(true);
        txtaDiagnostico.setRows(2);
        txtaDiagnostico.setTabSize(0);
        txtaDiagnostico.setWrapStyleWord(true);
        txtaDiagnostico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaDiagnosticoKeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(txtaDiagnostico);

        javax.swing.GroupLayout pnlObservaciones1Layout = new javax.swing.GroupLayout(pnlObservaciones1);
        pnlObservaciones1.setLayout(pnlObservaciones1Layout);
        pnlObservaciones1Layout.setHorizontalGroup(
            pnlObservaciones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11)
        );
        pnlObservaciones1Layout.setVerticalGroup(
            pnlObservaciones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
        );

        pnlBotones1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(0, 51, 102));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel_enabled.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCancelar.setEnabled(false);
        btnCancelar.setNextFocusableComponent(btnVolver);
        btnCancelar.setOpaque(true);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 51, 102));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_enabled.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.setNextFocusableComponent(btnCancelar);
        btnGuardar.setOpaque(true);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(0, 51, 102));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_enabled.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnModificar.setContentAreaFilled(false);
        btnModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificar.setEnabled(false);
        btnModificar.setNextFocusableComponent(txtaMotivo);
        btnModificar.setOpaque(true);
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarMouseEntered(evt);
            }
        });
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnVolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(0, 51, 102));
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home_enabled.png"))); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnVolver.setContentAreaFilled(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVolver.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnVolver.setNextFocusableComponent(btnModificar);
        btnVolver.setOpaque(true);
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVolverMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVolverMouseEntered(evt);
            }
        });
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBotones1Layout = new javax.swing.GroupLayout(pnlBotones1);
        pnlBotones1.setLayout(pnlBotones1Layout);
        pnlBotones1Layout.setHorizontalGroup(
            pnlBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotones1Layout.createSequentialGroup()
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlBotones1Layout.setVerticalGroup(
            pnlBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotones1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Datos del Paciente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlDatosPersonales.setForeground(new java.awt.Color(204, 204, 255));

        lblstaticDni.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticDni.setText("Nro. Documento:");

        lblstaticNumAfiliado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticNumAfiliado.setText("Nro. Afiliado:");

        lblstaticObraSocial.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticObraSocial.setText("Obra Social:");

        lblstaticFechaNacimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticFechaNacimiento.setText("Fecha de Nacimiento:");

        lblDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDni.setText("35953232");

        lblFechaNacimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFechaNacimiento.setText("21/03/1991");

        lblNumeroAfiliado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNumeroAfiliado.setText("3-4534543-2");

        lblObraSocial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblObraSocial.setText("OSPAC");

        lblstaticFechaPrimeraConsulta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticFechaPrimeraConsulta.setText("Fecha de Primera Consulta:");

        lblFechaPrimeraConsulta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFechaPrimeraConsulta.setText("0 -");

        lblLocalidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLocalidad.setText("423654");

        lblstaticLocalidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticLocalidad.setText("Localidad:");

        pnlApellidoNombre.setBackground(new java.awt.Color(228, 228, 241));
        pnlApellidoNombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(153, 153, 153), new java.awt.Color(255, 255, 255), null));

        lblNombrePaciente.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        lblNombrePaciente.setForeground(new java.awt.Color(0, 51, 102));
        lblNombrePaciente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNombrePaciente.setText("Lopez, Juan Carlos");
        lblNombrePaciente.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblstaticNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticNombre.setText("Apellido y Nombre:");

        javax.swing.GroupLayout pnlApellidoNombreLayout = new javax.swing.GroupLayout(pnlApellidoNombre);
        pnlApellidoNombre.setLayout(pnlApellidoNombreLayout);
        pnlApellidoNombreLayout.setHorizontalGroup(
            pnlApellidoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlApellidoNombreLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblstaticNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombrePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        pnlApellidoNombreLayout.setVerticalGroup(
            pnlApellidoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlApellidoNombreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblNombrePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addComponent(lblstaticNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
        );

        lblstaticEdad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticEdad.setText("Edad:");

        lblEdad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEdad.setText("0");

        lblLocalidad1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblLocalidad1.setText("423654");

        lblstaticLocalidad1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticLocalidad1.setText("Domicilio:");

        javax.swing.GroupLayout pnlDatosPersonalesLayout = new javax.swing.GroupLayout(pnlDatosPersonales);
        pnlDatosPersonales.setLayout(pnlDatosPersonalesLayout);
        pnlDatosPersonalesLayout.setHorizontalGroup(
            pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(pnlApellidoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(lblstaticFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(4, 4, 4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosPersonalesLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblstaticLocalidad, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblstaticEdad, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblstaticDni, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblEdad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(31, 31, 31))
                            .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblLocalidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblFechaNacimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)))
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblstaticFechaPrimeraConsulta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDatosPersonalesLayout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblstaticObraSocial)
                                    .addComponent(lblstaticNumAfiliado)))
                            .addComponent(lblstaticLocalidad1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(lblFechaPrimeraConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(112, 112, 112))
                            .addComponent(lblObraSocial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNumeroAfiliado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblLocalidad1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(168, 168, 168))))
        );
        pnlDatosPersonalesLayout.setVerticalGroup(
            pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                .addComponent(pnlApellidoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(lblstaticFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(3, 3, 3)
                                .addComponent(lblstaticDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5)
                                .addComponent(lblstaticEdad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                                .addComponent(lblFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(3, 3, 3)
                                .addComponent(lblDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(5, 5, 5)
                                .addComponent(lblEdad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblstaticLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblstaticFechaPrimeraConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFechaPrimeraConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(3, 3, 3)
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblstaticObraSocial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblObraSocial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblstaticNumAfiliado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNumeroAfiliado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLocalidad1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblstaticLocalidad1))))
                .addContainerGap())
        );

        pnlBiopsia.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Biopsia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlBiopsia.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N

        txtaBiopsia.setColumns(20);
        txtaBiopsia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaBiopsia.setLineWrap(true);
        txtaBiopsia.setRows(2);
        txtaBiopsia.setTabSize(0);
        txtaBiopsia.setWrapStyleWord(true);
        txtaBiopsia.setNextFocusableComponent(txtaDiagnostico);
        txtaBiopsia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaBiopsiaKeyPressed(evt);
            }
        });
        jScrollPane13.setViewportView(txtaBiopsia);

        javax.swing.GroupLayout pnlBiopsiaLayout = new javax.swing.GroupLayout(pnlBiopsia);
        pnlBiopsia.setLayout(pnlBiopsiaLayout);
        pnlBiopsiaLayout.setHorizontalGroup(
            pnlBiopsiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13)
        );
        pnlBiopsiaLayout.setVerticalGroup(
            pnlBiopsiaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlMotivo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pnlFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(pnlTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnlEstudiosComplementarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlLaboratorio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlObservaciones1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tpaneExamenes)
                    .addComponent(pnlBotones1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBiopsia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(pnlMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlTratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlEstudiosComplementarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tpaneExamenes)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlBiopsia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(pnlBotones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlLaboratorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlObservaciones1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        this.setearLabels(btnGuardar, true);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        this.setearLabels(btnGuardar, false);
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String error = comprobarObligatorias(tipoConsulta);
        if(!error.isEmpty())
        {
            MensajesValidaciones.mostrarError(this,"Debe completar los siguientes datos obligatorios: \n"+error);
            return;
        }   
    
        generarConsultaMedica();

        if(!procedencia)
        {
            if(daoConsulta.RegistrarConsulta(cons, dni,tipoConsulta))
            {
                this.idConsulta = cons.getId();
                MensajesValidaciones.mostrarInformacion(this, "Registro Exitoso.");
            }
            else
            {
                MensajesValidaciones.mostrarError(this, "Registro Fallido.");
                return;
            }
        }
        else
        {
            if(daoConsulta.actualizarConsulta(cons))
            {
                MensajesValidaciones.mostrarInformacion(this, "Actualización Exitosa.");
                cambiarBotones(false);
            }
            else
            {
                MensajesValidaciones.mostrarError(this, "Actualización Fallida.");
                return;
            }
        }
        this.setearEstadoCajas(false, tipoConsulta);
        HC.llenarTabla(dni);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        this.setearLabels(btnCancelar, true);
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        this.setearLabels(btnCancelar, false);
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.llenarCajas(cons);
        this.setearEstadoCajas(false, tipoConsulta);
        this.btnGuardar.setEnabled(false);
        this.btnModificar.setEnabled(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnVolverMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVolverMouseEntered
        this.setearLabels(btnVolver, true);
    }//GEN-LAST:event_btnVolverMouseEntered

    private void btnVolverMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVolverMouseExited
        this.setearLabels(btnVolver, false);
    }//GEN-LAST:event_btnVolverMouseExited

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        this.salir();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnModificarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseEntered
        this.setearLabels(btnModificar, true);
    }//GEN-LAST:event_btnModificarMouseEntered

    private void btnModificarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseExited
        this.setearLabels(btnModificar, false);
    }//GEN-LAST:event_btnModificarMouseExited

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        cambiarBotones(true);
        setearEstadoCajas(true, tipoConsulta);
        this.btnGuardar.setEnabled(true);
        this.btnModificar.setEnabled(false);
        this.procedencia = true;
    }//GEN-LAST:event_btnModificarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.salir();
    }//GEN-LAST:event_formWindowClosing

    private void txtaMotivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaMotivoKeyPressed
        this.controlarFoco(evt);
    }//GEN-LAST:event_txtaMotivoKeyPressed

    private void txtaTratamientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaTratamientoKeyPressed
        this.controlarFoco(evt);
    }//GEN-LAST:event_txtaTratamientoKeyPressed

    private void txtaEstudiosCompleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaEstudiosCompleKeyPressed
        this.controlarFoco(evt);
    }//GEN-LAST:event_txtaEstudiosCompleKeyPressed

    private void txtaLaboratorioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaLaboratorioKeyPressed
        this.controlarFoco(evt);
    }//GEN-LAST:event_txtaLaboratorioKeyPressed

    private void txtaDiagnosticoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaDiagnosticoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB && !evt.isShiftDown()) {
            evt.consume();
            if (tipoConsulta == ABMConsultaCompleta.COMPLETA || tipoConsulta == ABMConsultaCompleta.GINECOLOGICA)
                this.txtaMamas.grabFocus();
            else
                this.txtfTalla.grabFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_TAB
                && evt.isShiftDown()) {
            evt.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
        }
    }//GEN-LAST:event_txtaDiagnosticoKeyPressed

    private void txtaExamenFisicoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaExamenFisicoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaExamenFisicoKeyPressed

    private void txtaBiopsiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaBiopsiaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaBiopsiaKeyPressed

    private void setearLabels(JButton jbtn, boolean entrada){
        if(jbtn.isEnabled())
            if (entrada) {
                jbtn.setFont(new java.awt.Font("Tahoma", 1, 15));
            }
            else {
                jbtn.setFont(new java.awt.Font("Tahoma", 1, 14));
            }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnVolver;
    private javax.swing.ButtonGroup btngrpMAF;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblEdad;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblFechaPrimeraConsulta;
    private javax.swing.JLabel lblLocalidad;
    private javax.swing.JLabel lblLocalidad1;
    private javax.swing.JLabel lblNombrePaciente;
    private javax.swing.JLabel lblNumeroAfiliado;
    private javax.swing.JLabel lblObraSocial;
    private javax.swing.JLabel lblstaticDni;
    private javax.swing.JLabel lblstaticEdad;
    private javax.swing.JLabel lblstaticFecha;
    private javax.swing.JLabel lblstaticFechaNacimiento;
    private javax.swing.JLabel lblstaticFechaPrimeraConsulta;
    private javax.swing.JLabel lblstaticLocalidad;
    private javax.swing.JLabel lblstaticLocalidad1;
    private javax.swing.JLabel lblstaticNombre;
    private javax.swing.JLabel lblstaticNumAfiliado;
    private javax.swing.JLabel lblstaticObraSocial;
    private javax.swing.JPanel pnlApellidoNombre;
    private javax.swing.JPanel pnlBiopsia;
    private javax.swing.JPanel pnlBotones1;
    private javax.swing.JPanel pnlDatosPersonales;
    private javax.swing.JPanel pnlEstudiosComplementarios;
    private javax.swing.JPanel pnlFecha;
    private javax.swing.JPanel pnlFisico;
    private javax.swing.JPanel pnlLaboratorio;
    private javax.swing.JPanel pnlMotivo;
    private javax.swing.JPanel pnlObservaciones1;
    private javax.swing.JPanel pnlTratamiento;
    private javax.swing.JTabbedPane tpaneExamenes;
    private javax.swing.JTextArea txtaBiopsia;
    private javax.swing.JTextArea txtaDiagnostico;
    private javax.swing.JTextArea txtaEstudiosComple;
    private javax.swing.JTextArea txtaExamenFisico;
    private javax.swing.JTextArea txtaLaboratorio;
    private javax.swing.JTextArea txtaMotivo;
    private javax.swing.JTextArea txtaTratamiento;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Metodo utilizado para llenar los datos de una consulta
     * @param c Consulta
     */
    private void llenarCajas(Consulta c) {
        this.lblFecha.setText(c.getFecha());
        this.txtaMotivo.setText(c.getMotivo());
        this.txtaTratamiento.setText(c.getTratamiento());
        this.txtaEstudiosComple.setText(c.getEstudiosComplementarios());
        this.txtaLaboratorio.setText(c.getObservaciones());
        this.txtaDiagnostico.setText(c.getDiagnostico());
        if(tipoConsulta == 2 || tipoConsulta == 1)
        {
            this.txtaMamas.setText(c.getExamenGinecologico().getMamas());
            this.txtaGenitoUrinario.setText(c.getExamenGinecologico().getGenitourinario());
            this.txtaPapYColpo.setText(c.getExamenGinecologico().getPapycolposcopia());
            this.cmbBethesda.setSelectedItem(c.getExamenGinecologico().getBethesda());
        }
        if(tipoConsulta == 2 || tipoConsulta == 0)
        {
            this.txtfTalla.setText(c.getExamenObstetrico().getTalla()+"");
            this.txtfPeso.setText(c.getExamenObstetrico().getPeso()+"");
            this.txtfAlturaUterina.setText(c.getExamenObstetrico().getAu()+"");     
            int pos = c.getExamenObstetrico().getPa().indexOf("/");
            this.txtfPresionArterialmm.setText(c.getExamenObstetrico().getPa().substring(0, pos));
            this.txtfPresionArterialHg.setText(c.getExamenObstetrico().getPa().substring(pos + 1));
            this.txtfLCF.setText(c.getExamenObstetrico().getLcf()+"");
            this.txtaObstetObservaciones.setText(c.getExamenObstetrico().getObservaciones()+"");
            if(c.getExamenObstetrico().getMaf())
                this.rbtnMafSi.setSelected(true);
            else
                this.rbtnMafNo.setSelected(true);
            this.lblIMC.setText(c.getExamenObstetrico().getImc()+"");
        }
    }
    
    /**
     * Metodo utilizado para validar el ingreso en las cajas obligatorias
     * @param tipoConsulta tipo de consulta (0 Obstetrica , 1 Ginecologica y 2 Completa)
     */
    private String comprobarObligatorias(int tipoConsulta) {//estudios complementarios y observaciones no son obligatorios
        String incompletas="";
        
        if(this.txtaMotivo.getText().isEmpty())
            incompletas+="Motivo \n";
        
        if(this.txtaTratamiento.getText().isEmpty())
            incompletas+="Tratamiento \n";
        
        if(this.txtaDiagnostico.getText().isEmpty())
            incompletas+="Diagnóstico \n";
        
        if(tipoConsulta == 2 || tipoConsulta == 1)
        {
            if(this.txtaMamas.getText().isEmpty())
                incompletas+="Mamas \n";
        
            if(this.txtaGenitoUrinario.getText().isEmpty())
                incompletas += "Genitourinario \n";
        
            if(this.txtaPapYColpo.getText().isEmpty())
                incompletas += "Papanicolau y Colposcopia \n";
        }
        
        if(tipoConsulta == 2 || tipoConsulta == 0)
        {
            if(this.txtfTalla.getText().isEmpty())
                incompletas+="Talla \n";
            
            else if (Float.parseFloat(this.txtfTalla.getText()) == 0)
                incompletas+="Talla no puede ser 0 \n";
            
            if(this.txtfPeso.getText().isEmpty())
                incompletas += "Peso \n";
            
            else if(Float.parseFloat(this.txtfPeso.getText()) == 0)
                incompletas += "Peso no puede ser 0 \n";
            
            if(this.txtfAlturaUterina.getText().isEmpty())
                incompletas += "Altura Uterina \n";
            
            if(this.txtfPresionArterialmm.getText().isEmpty() || this.txtfPresionArterialHg.getText().isEmpty())
                incompletas += "Presión Arterial \n";
            
            if(this.txtfLCF.getText().isEmpty())
                incompletas += "LCF \n";
            
            if(this.rbtnMafNo.isSelected() == false && this.rbtnMafSi.isSelected() == false)
                incompletas += "MAF";        
        
        }
        return incompletas;

    }
    
    /**
     * Metodo utilizado para generar una consulta
     */
    private void generarConsultaMedica() {
        cons = new Consulta();
        cons.setId(idConsulta);
        cons.setDiagnostico(this.txtaDiagnostico.getText());
        cons.setEstudiosComplementarios(this.txtaEstudiosComple.getText());
        cons.setFecha(this.lblFecha.getText());
        cons.setMotivo(this.txtaMotivo.getText());
        cons.setObservaciones(this.txtaLaboratorio.getText());
        switch(tipoConsulta)
        {
            case 0 : cons.setTipoConsulta("Obstetrica");  
                     break;
            case 1 : cons.setTipoConsulta("Ginecologica");
                     break;
            case 2 : cons.setTipoConsulta("Completa");
                     break;
        }
        cons.setTratamiento(this.txtaTratamiento.getText());
        
        generarExamenes();
        
        cons.setExamenGinecologico(eGinec);
        cons.setExamenObstetrico(eObste);
        }

    /**
     * Metodo utilizado para generar los examenes de las consultas
     */
    private void generarExamenes() {
        eGinec = new ExamenGinecologico();
        eObste = new ExamenObstetrico();
        
        if(tipoConsulta == 1 || tipoConsulta == 2){
        eGinec.setBethesda(this.cmbBethesda.getSelectedItem().toString());
        eGinec.setGenitourinario(this.txtaGenitoUrinario.getText());
        eGinec.setMamas(this.txtaMamas.getText());
        eGinec.setPapycolposcopia(this.txtaPapYColpo.getText());
        }
        
        if(tipoConsulta == 0 || tipoConsulta == 2){
        eObste.setAu(Float.parseFloat(this.txtfAlturaUterina.getText()));
        eObste.setImc(Float.parseFloat(this.lblIMC.getText()));
        eObste.setLcf(Integer.parseInt(this.txtfLCF.getText()));
        if(rbtnMafNo.isSelected())
            eObste.setMaf(false);
        else
            eObste.setMaf(true);
        eObste.setObservaciones(this.txtaObstetObservaciones.getText());
        eObste.setPa(this.txtfPresionArterialmm.getText() + "/" + this.txtfPresionArterialHg.getText());
        eObste.setPeso(Float.parseFloat(this.txtfPeso.getText()));
        eObste.setTalla(Float.parseFloat(this.txtfTalla.getText()));
    }
    }
    
    /**
     * Metodo utilizado para setear el estado de las cajas (Habilitado, Deshabilitado)
     * @param   accion true (Habilitado), false (Deshabilitado)
     * @param tipoConsulta tipo de consulta (0 Obstetrica , 1 Ginecologica y 2 Completa)
     */
    private void setearEstadoCajas(boolean accion, int tipoConsulta) {
        this.txtaMotivo.setEnabled(accion);
        this.txtaTratamiento.setEnabled(accion);
        this.txtaEstudiosComple.setEnabled(accion);
        this.txtaLaboratorio.setEnabled(accion);
        this.txtaDiagnostico.setEnabled(accion);
        this.btnCancelar.setEnabled(accion);
        this.btnModificar.setEnabled(!accion);
        this.btnGuardar.setEnabled(accion);
        if(tipoConsulta == 1 || tipoConsulta == 2)
        {
            this.txtaMamas.setEnabled(accion);
            this.txtaGenitoUrinario.setEnabled(accion);
            this.cmbBethesda.setEnabled(accion);
            this.txtaPapYColpo.setEnabled(accion);
        }
        if(tipoConsulta == 0 || tipoConsulta == 2)
        {
            this.txtfTalla.setEnabled(accion);
            this.txtfPeso.setEnabled(accion);
            this.txtfAlturaUterina.setEnabled(accion);
            this.txtfPresionArterialmm.setEnabled(accion);
            this.txtfPresionArterialHg.setEnabled(accion);
            this.txtfLCF.setEnabled(accion);
            this.rbtnMafNo.setEnabled(accion);
            this.rbtnMafSi.setEnabled(accion);
            this.txtaObstetObservaciones.setEnabled(accion);
        }
        
    }
    
    /**
     * Metodo utilizado para invertir el estado de los botones (Habilitado, Deshabilitado)
     * @param   accion true (Habilitado), false (Deshabilitado)
     */
    private void cambiarBotones(boolean accion) {
        this.btnGuardar.setEnabled(accion);
        this.btnModificar.setEnabled(!accion);
    }

    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/sistema.png"));
        return retValue;
    }
    
    /**
     * Metodo utilizado para llenar los campos de la historia clinica de un paciente
     * @param p Paciente
     */
    private void llenarCampos(Paciente p) {
        this.lblNombrePaciente.setText(p.getApellido()+", "+p.getNombre());

        this.lblFechaNacimiento.setText(p.getFechaNacimiento());
        lblEdad.setText(calcularEdad(p.getFechaNacimiento())+"");
        
        this.lblNumeroAfiliado.setText(p.getNumeroAfiliado());
        this.lblObraSocial.setText(p.getObraSocial().getNombre());
        this.lblLocalidad.setText(p.getTelefono());
        this.lblDni.setText(p.getDni()+"");
        
        if(p.getFactor())
            this.lblFechaPrimeraConsulta.setText(p.getGrupoSanguineo()+" +");   
        else
            this.lblFechaPrimeraConsulta.setText(p.getGrupoSanguineo()+" -");
    }
    
    private int calcularEdad(String fechaNacimiento) {
        Calendar c = Calendar.getInstance();
        int diaActual = c.get(Calendar.DAY_OF_MONTH);
        int mesActual = c.get(Calendar.MONTH)+1;
        int añoActual = c.get(Calendar.YEAR);
        
        int diaNacimiento = Integer.parseInt(fechaNacimiento.substring(0, 2));
        int mesNacimiento = Integer.parseInt(fechaNacimiento.substring(3, 5));
        int añoNacimiento = Integer.parseInt(fechaNacimiento.substring(6, 10));
        
        int edad = añoActual - añoNacimiento;
        
        if(mesActual < mesNacimiento)
            return edad-1;
        else
            if ((mesActual == mesNacimiento) && (diaActual < diaNacimiento))
                return edad-1;
        
        return edad;
        }

    /**
     * Método controlador del cierre de la ventana y validaciones oportunas
     */
    private void salir() {
        if(!btnGuardar.isEnabled()){
            this.dispose();
            if (this.getParent() != null)
                this.getParent().setVisible(true);
            HC.cerrarHijo(this);
        }
        else
        {
            MensajesValidaciones.validarSalidaVentana(this);
        }
    }

    /**
     * Retorna el código de tipo de consulta de la consulta trabajada en la ventana
     * @return tipoConsulta = 0 Obstetrica , 1 Ginecologica y 2 Completa
     */
    public int getTipoConsulta() {
        return tipoConsulta;
    }
    
    /**
     * Retorna el id de la consulta trabajada en la ventana
     * @return id de la consulta, -1 si es una nueva consulta no guardada aún
     */
    public int getIdConsulta(){
        if (cons != null)
            return cons.getId();
        return -1;
    }
    
    /**
     * Maneja el paso del foco con las taclas TAB y Shift TAB.
     * Éste método cobra importancia para los componentes JTextArea.
     * @param evt 
     */
    private void controlarFoco(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_TAB && !evt.isShiftDown()) {
            evt.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
        } else if (evt.getKeyCode() == KeyEvent.VK_TAB
                && evt.isShiftDown()) {
            evt.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
        }
    }
    
    }

