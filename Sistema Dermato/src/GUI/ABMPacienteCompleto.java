package GUI;

import ClasesBase.GestorEstilos;
import ClasesBase.MensajesValidaciones;
import ClasesBase.modelo.*;
import DAO.DAOObraSocial;
import DAO.DAOPaciente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;

public class ABMPacienteCompleto extends javax.swing.JFrame {
    private Frame padre;
    private Principal padreP;
    private HistoriaClinica hc;
    private DAOPaciente daoPaciente;
    private Paciente p;
    private DAOObraSocial daoObraSocial;
    private LinkedList<ObraSocial> obras;
    private AntecedentesFamiliares af;
    private AntecedentesGenerales agen;
    private AntecedentesGinecologicos aginec;
    private int procedencia;
    private long dniOriginal;
    private int idObraSocialOriginal;
    private String nroAfiliadoOriginal;
    private String tiroidesAux = "", oncologógicosAux = "";
    
    /**
     * Creates new form DatosPaciente
     */
    public ABMPacienteCompleto(java.awt.Frame parent, boolean modal,int procedencia) {     //procedencia: 0 principal nuevo, 
        initComponents();
        ComboBoxEditor editor = cmbObraSocial.getEditor();
        JTextField etf = (JTextField) editor.getEditorComponent();
        etf.setDisabledTextColor(GestorEstilos.getColorTexto());
        etf.setBackground(GestorEstilos.getColorTerciario());
        txtfNumeroAfiliado.setDisabledTextColor(GestorEstilos.getColorTexto());
        this.btnModificar.setVisible(false);
        this.btnModificar.setEnabled(false);
        daoPaciente = new DAOPaciente();
        daoObraSocial = new DAOObraSocial();
        obras = new LinkedList<>();
        this.procedencia = procedencia;
        llenarObrasSociales();
        if(procedencia == 0 || procedencia == 1)
        {
        padreP = (Principal) parent;
        padre = padreP;
        }
        else if(procedencia == 2)
        {
        hc = (HistoriaClinica) parent;
        padre = hc;
        }
        setIconImage(getIconImage());
        ClasesBase.GestorEstilos.pintar(this);
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
        pnlOtherData.setBorder(javax.swing.BorderFactory.createTitledBorder(
                new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true),
                "Otros Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13),
                new java.awt.Color(0, 51, 102))); 
    }
    
    public ABMPacienteCompleto(java.awt.Frame parent, boolean modal,int procedencia, Paciente p) {
        initComponents();
        ComboBoxEditor editor = cmbObraSocial.getEditor();
        JTextField etf = (JTextField) editor.getEditorComponent();
        etf.setDisabledTextColor(GestorEstilos.getColorTexto());
        etf.setBackground(GestorEstilos.getColorTerciario());
        txtfNumeroAfiliado.setDisabledTextColor(GestorEstilos.getColorTexto());
        daoPaciente = new DAOPaciente();
        daoObraSocial = new DAOObraSocial();
        obras = new LinkedList<>();
        this.procedencia = procedencia;
        llenarObrasSociales();
        if(procedencia == 1)
             { 
             llenarCajas(p);
             this.p = p;
             dniOriginal = Long.parseLong(this.txtfDni.getText());
             padreP = (Principal) parent;
             padre = padreP;
             }

        else if(procedencia == 2)
        {
        this.btnModificar.setVisible(false);
        hc = (HistoriaClinica) parent;
        llenarCajas(p);
        this.p = p;
        dniOriginal = Long.parseLong(this.txtfDni.getText());
        if (p.getObraSocial() != null)
            nroAfiliadoOriginal = p.getNumeroAfiliado();
        }
        
        this.setLocationRelativeTo(parent);
        setIconImage(getIconImage());
        ClasesBase.GestorEstilos.pintar(this);
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
        pnlOtherData.setBorder(javax.swing.BorderFactory.createTitledBorder(
                new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true),
                "Otros Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13),
                new java.awt.Color(0, 51, 102))); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrpSangre = new javax.swing.ButtonGroup();
        tabbedPaneAntecedentes = new javax.swing.JTabbedPane();
        pnlGenerales = new javax.swing.JPanel();
        pnlPersonales = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtaPersonales = new javax.swing.JTextArea();
        pnlQuirurgicos = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtaQuirurgicos = new javax.swing.JTextArea();
        pnlToxicos = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtaToxicos = new javax.swing.JTextArea();
        pnlDatosPersonales = new javax.swing.JPanel();
        lblstaticNombres = new javax.swing.JLabel();
        lblstaticApellidos = new javax.swing.JLabel();
        lblstaticTelefono = new javax.swing.JLabel();
        lblstaticFechaNacimiento = new javax.swing.JLabel();
        txtfNombres = new javax.swing.JTextField();
        txtfApellidos = new javax.swing.JTextField();
        txtfTelefono = new javax.swing.JTextField();
        txtfDni = new javax.swing.JTextField();
        lblstaticDni = new javax.swing.JLabel();
        ftxtfFechaNacimiento = new javax.swing.JFormattedTextField();
        pnlObraSocial = new javax.swing.JPanel();
        cmbObraSocial = new javax.swing.JComboBox();
        btnNuevaObraSocial = new javax.swing.JButton();
        lblstaticNumAfiliado = new javax.swing.JLabel();
        txtfNumeroAfiliado = new javax.swing.JTextField();
        btnGuardarOS = new javax.swing.JButton();
        txtfNuevaObraSocial = new javax.swing.JTextField();
        btnCancelarOS = new javax.swing.JButton();
        lblstaticAddress = new javax.swing.JLabel();
        txtfAddress = new javax.swing.JTextField();
        lblstaticCity = new javax.swing.JLabel();
        txtfCity = new javax.swing.JTextField();
        pnlButtons = new javax.swing.JPanel();
        btnVolver = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        pnlOtherData = new javax.swing.JPanel();
        lblstaticFirstConsultDate = new javax.swing.JLabel();
        ftxtfFechaNacimiento1 = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Nuevo Paciente");
        setIconImages(getIconImages());
        setMinimumSize(getPreferredSize());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tabbedPaneAntecedentes.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Antecedentes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        tabbedPaneAntecedentes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabbedPaneAntecedentes.setOpaque(true);
        tabbedPaneAntecedentes.setPreferredSize(new java.awt.Dimension(500, 300));

        pnlGenerales.setNextFocusableComponent(txtaPersonales);

        pnlPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaPersonales.setColumns(20);
        txtaPersonales.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaPersonales.setLineWrap(true);
        txtaPersonales.setRows(5);
        txtaPersonales.setTabSize(0);
        txtaPersonales.setWrapStyleWord(true);
        txtaPersonales.setNextFocusableComponent(txtaQuirurgicos);
        txtaPersonales.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaPersonalesKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(txtaPersonales);

        javax.swing.GroupLayout pnlPersonalesLayout = new javax.swing.GroupLayout(pnlPersonales);
        pnlPersonales.setLayout(pnlPersonalesLayout);
        pnlPersonalesLayout.setHorizontalGroup(
            pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        );
        pnlPersonalesLayout.setVerticalGroup(
            pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        pnlQuirurgicos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Quirúrgicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaQuirurgicos.setColumns(20);
        txtaQuirurgicos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaQuirurgicos.setLineWrap(true);
        txtaQuirurgicos.setRows(5);
        txtaQuirurgicos.setTabSize(0);
        txtaQuirurgicos.setWrapStyleWord(true);
        txtaQuirurgicos.setNextFocusableComponent(txtaToxicos);
        txtaQuirurgicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaQuirurgicosKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(txtaQuirurgicos);

        javax.swing.GroupLayout pnlQuirurgicosLayout = new javax.swing.GroupLayout(pnlQuirurgicos);
        pnlQuirurgicos.setLayout(pnlQuirurgicosLayout);
        pnlQuirurgicosLayout.setHorizontalGroup(
            pnlQuirurgicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        );
        pnlQuirurgicosLayout.setVerticalGroup(
            pnlQuirurgicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        pnlToxicos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Tóxicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaToxicos.setColumns(20);
        txtaToxicos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaToxicos.setLineWrap(true);
        txtaToxicos.setRows(5);
        txtaToxicos.setTabSize(0);
        txtaToxicos.setWrapStyleWord(true);
        txtaToxicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaToxicosKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(txtaToxicos);

        javax.swing.GroupLayout pnlToxicosLayout = new javax.swing.GroupLayout(pnlToxicos);
        pnlToxicos.setLayout(pnlToxicosLayout);
        pnlToxicosLayout.setHorizontalGroup(
            pnlToxicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
        );
        pnlToxicosLayout.setVerticalGroup(
            pnlToxicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlGeneralesLayout = new javax.swing.GroupLayout(pnlGenerales);
        pnlGenerales.setLayout(pnlGeneralesLayout);
        pnlGeneralesLayout.setHorizontalGroup(
            pnlGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlPersonales, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlQuirurgicos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlToxicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGeneralesLayout.setVerticalGroup(
            pnlGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlQuirurgicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlToxicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneAntecedentes.addTab("Generales", pnlGenerales);

        pnlDatosPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        lblstaticNombres.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticNombres.setText("Nombres:");

        lblstaticApellidos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticApellidos.setText("Apellidos:");

        lblstaticTelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticTelefono.setText("Teléfono:");

        lblstaticFechaNacimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticFechaNacimiento.setText("Fecha de Nacimiento:");

        txtfNombres.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfNombresKeyTyped(evt);
            }
        });

        txtfApellidos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfApellidosKeyTyped(evt);
            }
        });

        txtfTelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfTelefonoKeyTyped(evt);
            }
        });

        txtfDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfDniKeyTyped(evt);
            }
        });

        lblstaticDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticDni.setText("Nro. Doc.:");

        try {
            ftxtfFechaNacimiento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtfFechaNacimiento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        pnlObraSocial.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Obra Social", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlObraSocial.setOpaque(false);

        cmbObraSocial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbObraSocial.setFocusCycleRoot(true);
        cmbObraSocial.setNextFocusableComponent(btnNuevaObraSocial);
        cmbObraSocial.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbObraSocialItemStateChanged(evt);
            }
        });

        btnNuevaObraSocial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNuevaObraSocial.setText("Nueva");
        btnNuevaObraSocial.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevaObraSocial.setContentAreaFilled(false);
        btnNuevaObraSocial.setNextFocusableComponent(txtfNumeroAfiliado);
        btnNuevaObraSocial.setOpaque(true);
        btnNuevaObraSocial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaObraSocialActionPerformed(evt);
            }
        });

        lblstaticNumAfiliado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticNumAfiliado.setText("Nro. Afiliado:");

        txtfNumeroAfiliado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfNumeroAfiliado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtfNumeroAfiliado.setEnabled(false);
        txtfNumeroAfiliado.setNextFocusableComponent(pnlPersonales);
        txtfNumeroAfiliado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfNumeroAfiliadoKeyTyped(evt);
            }
        });

        btnGuardarOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGuardarOS.setText("Guardar");
        btnGuardarOS.setEnabled(false);
        btnGuardarOS.setNextFocusableComponent(btnCancelarOS);
        btnGuardarOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarOSActionPerformed(evt);
            }
        });

        txtfNuevaObraSocial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfNuevaObraSocial.setEnabled(false);
        txtfNuevaObraSocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfNuevaObraSocialKeyTyped(evt);
            }
        });

        btnCancelarOS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelarOS.setText("Cancelar");
        btnCancelarOS.setEnabled(false);
        btnCancelarOS.setNextFocusableComponent(cmbObraSocial);
        btnCancelarOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlObraSocialLayout = new javax.swing.GroupLayout(pnlObraSocial);
        pnlObraSocial.setLayout(pnlObraSocialLayout);
        pnlObraSocialLayout.setHorizontalGroup(
            pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlObraSocialLayout.createSequentialGroup()
                .addGroup(pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlObraSocialLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlObraSocialLayout.createSequentialGroup()
                                .addComponent(lblstaticNumAfiliado, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfNumeroAfiliado))
                            .addGroup(pnlObraSocialLayout.createSequentialGroup()
                                .addComponent(cmbObraSocial, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNuevaObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlObraSocialLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtfNuevaObraSocial))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlObraSocialLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnGuardarOS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelarOS, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlObraSocialLayout.setVerticalGroup(
            pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlObraSocialLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevaObraSocial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfNuevaObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelarOS)
                    .addComponent(btnGuardarOS))
                .addGap(13, 13, 13)
                .addGroup(pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfNumeroAfiliado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblstaticNumAfiliado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblstaticAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticAddress.setText("Domicilio:");

        txtfAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfAddressKeyTyped(evt);
            }
        });

        lblstaticCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticCity.setText("Localidad:");

        txtfCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfCity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfCityKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlDatosPersonalesLayout = new javax.swing.GroupLayout(pnlDatosPersonales);
        pnlDatosPersonales.setLayout(pnlDatosPersonalesLayout);
        pnlDatosPersonalesLayout.setHorizontalGroup(
            pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlObraSocial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblstaticNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblstaticApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblstaticTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblstaticDni, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtfApellidos)
                            .addComponent(txtfNombres)
                            .addComponent(txtfTelefono)
                            .addComponent(txtfDni)))
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(lblstaticFechaNacimiento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ftxtfFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 85, Short.MAX_VALUE))
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(lblstaticAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfAddress))
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(lblstaticCity, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfCity)))
                .addContainerGap())
        );
        pnlDatosPersonalesLayout.setVerticalGroup(
            pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfNombres)
                    .addComponent(lblstaticNombres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfApellidos)
                    .addComponent(lblstaticApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfTelefono)
                    .addComponent(lblstaticTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfDni)
                    .addComponent(lblstaticDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfAddress)
                    .addComponent(lblstaticAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfCity)
                    .addComponent(lblstaticCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftxtfFechaNacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblstaticFechaNacimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnVolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(0, 51, 102));
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home_enabled.png"))); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnVolver.setContentAreaFilled(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVolver.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnVolver.setOpaque(true);
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVolverMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVolverMouseExited(evt);
            }
        });
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 51, 102));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_enabled.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.setOpaque(true);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
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
        btnModificar.setOpaque(true);
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModificarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModificarMouseExited(evt);
            }
        });
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlOtherData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Otros Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        lblstaticFirstConsultDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticFirstConsultDate.setText("Fecha de Primera Consulta:");

        try {
            ftxtfFechaNacimiento1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtfFechaNacimiento1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnlOtherDataLayout = new javax.swing.GroupLayout(pnlOtherData);
        pnlOtherData.setLayout(pnlOtherDataLayout);
        pnlOtherDataLayout.setHorizontalGroup(
            pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblstaticFirstConsultDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ftxtfFechaNacimiento1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlOtherDataLayout.setVerticalGroup(
            pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ftxtfFechaNacimiento1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblstaticFirstConsultDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDatosPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlOtherData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPaneAntecedentes, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlOtherData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tabbedPaneAntecedentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlOtherData.getAccessibleContext().setAccessibleName("Otros datos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfTelefonoKeyTyped
        MensajesValidaciones.negarLetras(evt,this);
        MensajesValidaciones.limitarLargo(this.txtfTelefono, evt, 45,this);
    }//GEN-LAST:event_txtfTelefonoKeyTyped

    private void txtfDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfDniKeyTyped
        MensajesValidaciones.negarLetras(evt,this);
    }//GEN-LAST:event_txtfDniKeyTyped

    private void txtfNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNombresKeyTyped
        MensajesValidaciones.negarNumeros(evt,this);
        MensajesValidaciones.limitarLargo(this.txtfNombres,evt,45,this);
    }//GEN-LAST:event_txtfNombresKeyTyped

    private void txtfApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfApellidosKeyTyped
        MensajesValidaciones.negarNumeros(evt,this);
        MensajesValidaciones.limitarLargo(this.txtfApellidos,evt,45,this);
    }//GEN-LAST:event_txtfApellidosKeyTyped

    private void btnNuevaObraSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaObraSocialActionPerformed
        this.txtfNuevaObraSocial.setEnabled(true);
        this.btnGuardarOS.setEnabled(true);
        this.btnCancelarOS.setEnabled(true);
        this.btnNuevaObraSocial.setEnabled(false);
        this.cmbObraSocial.setEnabled(false);
    }//GEN-LAST:event_btnNuevaObraSocialActionPerformed

    private void txtfNumeroAfiliadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNumeroAfiliadoKeyTyped
        MensajesValidaciones.limitarLargo(this.txtfNumeroAfiliado, evt, 45, this);
        MensajesValidaciones.validarNumeroAfiliado(evt, this);
    }//GEN-LAST:event_txtfNumeroAfiliadoKeyTyped

    private void btnGuardarOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarOSActionPerformed
        if (this.txtfNuevaObraSocial.getText().isEmpty()) {
            MensajesValidaciones.mostrarError(this, "Ingrese un nombre de obra social válido...");
            return;
        }
        String nuevaObra = this.txtfNuevaObraSocial.getText();
        if (daoObraSocial.registrarObraSocial(new ObraSocial(0, nuevaObra))) {
            MensajesValidaciones.mostrarInformacion(this, "Registro Exitoso.");
            llenarObrasSociales();
        } else {
            MensajesValidaciones.mostrarError(this, "Registro Fallido.");
        }
        
        this.txtfNuevaObraSocial.setEnabled(false);
        this.btnGuardarOS.setEnabled(false);
        this.btnNuevaObraSocial.setEnabled(true);
        this.cmbObraSocial.setEnabled(true);
        this.cmbObraSocial.setSelectedItem(nuevaObra);
        this.txtfNuevaObraSocial.setText("");
    }//GEN-LAST:event_btnGuardarOSActionPerformed

    private void txtfNuevaObraSocialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNuevaObraSocialKeyTyped
        MensajesValidaciones.limitarLargo(this.txtfNuevaObraSocial, evt, 80, this);
    }//GEN-LAST:event_txtfNuevaObraSocialKeyTyped

    private void btnCancelarOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarOSActionPerformed
        this.txtfNuevaObraSocial.setText("");
        this.txtfNuevaObraSocial.setEnabled(false);
        this.btnGuardarOS.setEnabled(false);
        this.btnCancelarOS.setEnabled(false);
        this.btnNuevaObraSocial.setEnabled(true);
        this.cmbObraSocial.setEnabled(true);
    }//GEN-LAST:event_btnCancelarOSActionPerformed

private void cmbObraSocialItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbObraSocialItemStateChanged
    if(cmbObraSocial.getSelectedIndex() == 0)
    {
        txtfNumeroAfiliado.setText("");
        txtfNumeroAfiliado.setEnabled(false);
    }
    else
    {
        txtfNumeroAfiliado.setEnabled(true);
    }
}//GEN-LAST:event_cmbObraSocialItemStateChanged

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        this.setearLabels(btnGuardar, true);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        this.setearLabels(btnGuardar, false);
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    af = new AntecedentesFamiliares();  
    agen = new AntecedentesGenerales();
    aginec = new AntecedentesGinecologicos();
    String error = comprobarDatosObligatorios();
    if(!error.isEmpty())
    {
        MensajesValidaciones.mostrarError(this,"Debe completar los siguientes datos obligatorios: \n"+error);
        return;
    }   
    long dniPaciente = Long.parseLong(this.txtfDni.getText());
    
    ObraSocial osAux = new ObraSocial();
    for (int i = 0; i < obras.size(); i++) {
        if(obras.get(i).getNombre().compareTo(cmbObraSocial.getSelectedItem().toString()) == 0)
            osAux = obras.get(i);
    }
    
    Paciente match = daoPaciente.verificarNroAfiliado(osAux.getId(), txtfNumeroAfiliado.getText());
    
    if(procedencia == 0) //Principal, nuevo
    {
        
        if(daoPaciente.verificarPaciente(dniPaciente)){
            MensajesValidaciones.mostrarError(this, "El paciente ya se encuentra registrado. Corrija el DNI o búsquelo en la ventana principal.");
            this.txtfDni.setText("");
            this.txtfDni.grabFocus();
            return;
        }
                    
        if(cmbObraSocial.getSelectedIndex() != 0 && match != null){
            MensajesValidaciones.mostrarError(this, "El paciente '"+match.getApellido().toUpperCase()+", "+match.getNombre()+"', DNI N°"+match.getDni()
                    + " ya se encuentra registrado con misma obra social y N° de afiliado.");
            this.txtfNumeroAfiliado.setText("");
            this.txtfNumeroAfiliado.grabFocus();
            return;
        }
        
        generarAntecedentes();
    
        if(!generarPaciente())
            return;
    
        if(daoPaciente.registrarPaciente(p))
        {
            MensajesValidaciones.mostrarInformacion(this,"Registro Exitoso.");
            hc = new HistoriaClinica(padre, p, 1);
            hc.llenarCampos(p,1);
            padreP.actualizarListaPacientes(null);
            this.dispose();
            hc.setVisible(true);
        }
        else{
            MensajesValidaciones.mostrarError(this,"Registro Fallido.");
        }
    }
    else if(procedencia == 1 || procedencia == 2)//Historia clinica(2), Principal-Modificar(1) 
    {
        if(dniPaciente != dniOriginal && daoPaciente.verificarPaciente(dniPaciente)){
            MensajesValidaciones.mostrarError(this,"El dni ingresado para la modificación ya se encuentra en la base de datos a nombre de otro paciente.\n"
                                             + " Corrija el DNI.");
            this.txtfDni.setText("");
            return;
        }
        
        if(cmbObraSocial.getSelectedIndex() != 0 
                && (idObraSocialOriginal != osAux.getId() || !nroAfiliadoOriginal.equals(txtfNumeroAfiliado.getText())) 
                && match != null
                && match.getDni() != dniOriginal){
            MensajesValidaciones.mostrarError(this, "El paciente '"+match.getApellido().toUpperCase()+", "+match.getNombre()+"', DNI N°"+match.getDni()
                    + " ya se encuentra registrado con misma obra social y N° de afiliado.");
            this.txtfNumeroAfiliado.setText("");
            this.txtfNumeroAfiliado.grabFocus();
            return;
        }
                        
        generarAntecedentes();
    
    
        if(error.isEmpty())
        {
            if(!generarPaciente())
                return;
        }
    
        else
        {
            MensajesValidaciones.mostrarError(this,"Debe completar los siguientes campos obligatorios: \n"+error);
            return;
        }   
    
        if(daoPaciente.actualizarPaciente(p,dniOriginal))
        {
            MensajesValidaciones.mostrarInformacion(this,"Actualización Exitosa.");
            
                if(this.procedencia == 2)
                {
                    hc.setPaciente(p);
                    hc.llenarCampos(p,1);
                    this.dispose();
                    hc.setVisible(true);
                }
                
                else
                {
                    LinkedList<Paciente> pacienteList = new LinkedList<Paciente>();
                    pacienteList.add(daoPaciente.getPacienteBasico(p.getDni()));
                    padreP.actualizarListaPacientes(pacienteList);
                }
        }
        else{
            MensajesValidaciones.mostrarError(this,"Actualización Fallida.");
        }
    }
    if(procedencia == 1)
    {
    cambiarEstadoCajas(false);
    this.btnModificar.setEnabled(true);
    this.btnGuardar.setEnabled(false);
    }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
        this.cambiarEstadoCajas(true);
        this.btnModificar.setEnabled(false);
        this.btnGuardar.setEnabled(true);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.salir();
    }//GEN-LAST:event_formWindowClosing

    private void txtfAddressKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfAddressKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfAddressKeyTyped

    private void txtfCityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfCityKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfCityKeyTyped

    private void txtaPersonalesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaPersonalesKeyPressed
        this.controlarFoco(evt);
    }//GEN-LAST:event_txtaPersonalesKeyPressed

    private void txtaToxicosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaToxicosKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB && !evt.isShiftDown()) {
            evt.consume();
            this.tabbedPaneAntecedentes.setSelectedComponent(this.pnlGinecologicos);
        } else if (evt.getKeyCode() == KeyEvent.VK_TAB
            && evt.isShiftDown()) {
            evt.consume();
            KeyboardFocusManager.getCurrentKeyboardFocusManager().focusPreviousComponent();
        }
    }//GEN-LAST:event_txtaToxicosKeyPressed

    private void txtaQuirurgicosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaQuirurgicosKeyPressed
        this.controlarFoco(evt);
    }//GEN-LAST:event_txtaQuirurgicosKeyPressed

    
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
    private javax.swing.JButton btnCancelarOS;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarOS;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevaObraSocial;
    private javax.swing.JButton btnVolver;
    private javax.swing.ButtonGroup btngrpSangre;
    private javax.swing.JComboBox cmbObraSocial;
    private javax.swing.JFormattedTextField ftxtfFechaNacimiento;
    private javax.swing.JFormattedTextField ftxtfFechaNacimiento1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblstaticAddress;
    private javax.swing.JLabel lblstaticApellidos;
    private javax.swing.JLabel lblstaticCity;
    private javax.swing.JLabel lblstaticDni;
    private javax.swing.JLabel lblstaticFechaNacimiento;
    private javax.swing.JLabel lblstaticFirstConsultDate;
    private javax.swing.JLabel lblstaticNombres;
    private javax.swing.JLabel lblstaticNumAfiliado;
    private javax.swing.JLabel lblstaticTelefono;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlDatosPersonales;
    private javax.swing.JPanel pnlGenerales;
    private javax.swing.JPanel pnlObraSocial;
    private javax.swing.JPanel pnlOtherData;
    private javax.swing.JPanel pnlPersonales;
    private javax.swing.JPanel pnlPersonales1;
    private javax.swing.JPanel pnlPersonales2;
    private javax.swing.JPanel pnlQuirurgicos;
    private javax.swing.JPanel pnlToxicos;
    private javax.swing.JTabbedPane tabbedPaneAntecedentes;
    private javax.swing.JTextArea txtaPersonales;
    private javax.swing.JTextArea txtaPersonales1;
    private javax.swing.JTextArea txtaPersonales2;
    private javax.swing.JTextArea txtaQuirurgicos;
    private javax.swing.JTextArea txtaToxicos;
    private javax.swing.JTextField txtfAddress;
    private javax.swing.JTextField txtfApellidos;
    private javax.swing.JTextField txtfCity;
    private javax.swing.JTextField txtfDni;
    private javax.swing.JTextField txtfNombres;
    private javax.swing.JTextField txtfNuevaObraSocial;
    private javax.swing.JTextField txtfNumeroAfiliado;
    private javax.swing.JTextField txtfTelefono;
    // End of variables declaration//GEN-END:variables
 
    /**
     * Se genera el objeto paciente que se desea guardar en la base de datos!
     */
    private boolean generarPaciente() {
        String error;
        p = new Paciente();
        
        p.setNombre(this.txtfNombres.getText());
        p.setApellido(this.txtfApellidos.getText());
        p.setTelefono(this.txtfTelefono.getText());
        p.setDni(Long.parseLong(this.txtfDni.getText()));
        
        try {
            String dia = this.ftxtfFechaNacimiento.getText(0,2);
            String mes = this.ftxtfFechaNacimiento.getText(3,2);
            String año = this.ftxtfFechaNacimiento.getText(6,4);
            
            error = MensajesValidaciones.corroborarFecha(dia,mes,año);
           
            if(!error.isEmpty())
            {
                MensajesValidaciones.mostrarError(this, "Los siguientes valores de la fecha no son válidos o están fuera de rango: \n" + error);
                return false;
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(ABMPacienteCompleto.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.setFechaNacimiento(this.ftxtfFechaNacimiento.getText());
        
        p.setGrupoSanguineo((String)this.cmbGrupoS.getSelectedItem());
        
        
        if(!this.rbtnFactorNeg.isSelected() && !this.rbtnFactorPos.isSelected())
        {
            MensajesValidaciones.mostrarError(this,"No se ha seleccionado ningún factor.");
            return false;
        }
        else
        {
            if(btngrpSangre.isSelected(this.rbtnFactorPos.getModel()))
                p.setFactor(true);
            else
                p.setFactor(false);
        }
        
        for (int i = 0; i < obras.size(); i++) {
            if(obras.get(i).getNombre().compareTo((String)cmbObraSocial.getSelectedItem()) == 0)
               p.setObraSocial(obras.get(i));
        }
        p.setNumeroAfiliado(this.txtfNumeroAfiliado.getText());
        p.setAntecGen(agen);
        p.setAntecGinec(aginec);
        p.setAntecFam(af);
        return true;
    }

    /**
     * metodo para la generacion de los antecedentes del paciente!
     */
    private void generarAntecedentes() {
        //Antecedentes generales
        agen = new AntecedentesGenerales();
        
            agen.setAntecedentesPersonales(this.txtaPersonales.getText());
            agen.setAntecedentesQuirurgicos(this.txtaQuirurgicos.getText());
            agen.setAntecedentesToxicos(this.txtaToxicos.getText());
      
       //Antecedentes Ginecologicos
       aginec = new AntecedentesGinecologicos();
            
            if(!this.txtfAbortos.getText().isEmpty())
                aginec.setAbortos(Integer.parseInt(this.txtfAbortos.getText()));
            
            aginec.setAnticonceptivos(this.txtfAnticonceptivos.getText());
            
            if(!this.txtfCesareas.getText().isEmpty())
                aginec.setCesareas(Integer.parseInt(this.txtfCesareas.getText()));
            
            aginec.setDismenorrea(this.chkDismenorrea.isSelected());
            aginec.setDispareunia(this.chkDispareunia.isSelected());
            
            if(!this.txtfDuracionMenstrual.getText().isEmpty())
                aginec.setDuracionMenstrual(Integer.parseInt(this.txtfDuracionMenstrual.getText()));
            
            if(!this.txtfGestaciones.getText().isEmpty())
                aginec.setGestaciones(Integer.parseInt(this.txtfGestaciones.getText()));
            
            if(!this.txtfMenarca.getText().isEmpty())
                aginec.setMenarca(Integer.parseInt(this.txtfMenarca.getText()));
            
            if(!this.txtfMenopausia.getText().isEmpty())
                aginec.setMenopausia(Integer.parseInt(this.txtfMenopausia.getText()));
            
            if(!this.txtfMuertos.getText().isEmpty())
                aginec.setMuertos(Integer.parseInt(this.txtfMuertos.getText()));
            
            aginec.setObservaciones(this.txtaGinecObserv.getText());
            
            if(!this.txtfPartos.getText().isEmpty())
                aginec.setPartos(Integer.parseInt(this.txtfPartos.getText()));
             
            if(!this.txtfPeriodoMenstrual.getText().isEmpty())
                aginec.setPeriodoMenstrual(Integer.parseInt(this.txtfPeriodoMenstrual.getText()));
            
            aginec.setSdpm(this.chkSdpm.isSelected());
            aginec.setTelarca(this.txtfTelarca.getText());
            
            if(!this.txtfVivos.getText().isEmpty())
                aginec.setVivos(Integer.parseInt(this.txtfVivos.getText()));
       //Antecedentes Familiares     
       af = new AntecedentesFamiliares();
       
            af.setDbt(this.chkDiabetes.isSelected());
            af.setDescripcionOncologicos(this.txtaOncologicos.getText());
            af.setDescripcionTiroides(this.txtfTiroides.getText());
            af.setHta(this.chkHipertension.isSelected());
            af.setObservaciones(this.txtaFamObservaciones.getText());
            af.setOncologicos(this.chkOncologicos.isSelected());
            af.setTiroides(this.chkTiroides.isSelected());
    }
    
    /**
     *comprueba que las cajas obligatorias esten completas
     * Obligatorias: nombres,apellidos,telefono,dni,fechaNacimiento,numeroAfiliado
     */
    private String comprobarDatosObligatorios() {
        String incompletas="";
        
        if(this.txtfNombres.getText().isEmpty())
            incompletas+="Nombres \n";
        
        if(this.txtfApellidos.getText().isEmpty())
            incompletas+="Apellidos \n";
        
        if(this.txtfTelefono.getText().isEmpty())
            incompletas+="Teléfono \n";
        
        if(this.txtfDni.getText().isEmpty())
            incompletas+="Nro. de Documento \n";
        
        if(this.ftxtfFechaNacimiento.getText().compareTo("  /  /    ") == 0)
            incompletas+="Fecha de Nacimiento \n";
        
        if(this.txtfNumeroAfiliado.getText().isEmpty() && this.txtfNumeroAfiliado.isEnabled())
            incompletas+="Nro. de Afiliado \n";
        
        if(this.rbtnFactorNeg.isSelected() == false && this.rbtnFactorPos.isSelected() == false)
            incompletas +="Factor \n";
        
        if(this.chkTiroides.isSelected() && this.txtfTiroides.getText().isEmpty())
            incompletas+="Descripción de Tiroides \n";
        
        if(this.chkOncologicos.isSelected() && this.txtaOncologicos.getText().isEmpty())
            incompletas+="Descripción de Oncológicos \n";
        
        return incompletas;
    }
    
        
    /**
     * Metodo generico utilizado para llenar todos los datos.
     */
    private void llenarCajas(Paciente p) {
        this.txtfNombres.setText(p.getNombre());
        this.txtfApellidos.setText(p.getApellido());
        this.txtfTelefono.setText(p.getTelefono());
        this.txtfDni.setText(p.getDni()+"");
        this.ftxtfFechaNacimiento.setText(p.getFechaNacimiento());
        this.cmbObraSocial.setSelectedItem(p.getObraSocial().getNombre());
        this.txtfNumeroAfiliado.setText(p.getNumeroAfiliado());
        
        if(p.getFactor())
            this.btngrpSangre.setSelected(this.rbtnFactorPos.getModel(), true);
        else
            this.btngrpSangre.setSelected(this.rbtnFactorNeg.getModel(), true);
        
        this.txtaPersonales.setText(p.getAntecGen().getAntecedentesPersonales());
        this.txtaToxicos.setText(p.getAntecGen().getAntecedentesToxicos());
        this.txtaQuirurgicos.setText(p.getAntecGen().getAntecedentesQuirurgicos());
        this.txtaFamObservaciones.setText(p.getAntecFam().getObservaciones());
        this.txtaGinecObserv.setText(p.getAntecGinec().getObservaciones());
        this.txtfAbortos.setText(p.getAntecGinec().getAbortos()+"");
        this.txtfAnticonceptivos.setText(p.getAntecGinec().getAnticonceptivos());
        this.txtaOncologicos.setText(p.getAntecFam().getDescripcionOncologicos());
        this.txtfCesareas.setText(p.getAntecGinec().getCesareas()+"");
        this.txtfDuracionMenstrual.setText(p.getAntecGinec().getDuracionMenstrual()+"");
        this.txtfPeriodoMenstrual.setText(p.getAntecGinec().getPeriodoMenstrual()+"");
        this.txtfGestaciones.setText(p.getAntecGinec().getGestaciones()+"");
        if (p.getAntecGinec().getMenarca() == 0)
            this.txtfMenarca.setText("");
        else
            this.txtfMenarca.setText(p.getAntecGinec().getMenarca()+"");
        if (p.getAntecGinec().getMenopausia() == 0)
            this.txtfMenopausia.setText("");
        else
            this.txtfMenopausia.setText(p.getAntecGinec().getMenopausia()+"");
        this.txtfMuertos.setText(p.getAntecGinec().getMuertos()+"");
        this.txtfPartos.setText(p.getAntecGinec().getPartos()+""); 
        this.txtfTelarca.setText(p.getAntecGinec().getTelarca());
        this.txtfTiroides.setText(p.getAntecFam().getDescripcionTiroides());
        this.txtfVivos.setText(p.getAntecGinec().getVivos()+"");
        this.cmbGrupoS.setSelectedItem(p.getGrupoSanguineo());
        this.chkDiabetes.setSelected(p.getAntecFam().isDbt());
        this.chkDismenorrea.setSelected(p.getAntecGinec().isDismenorrea());
        this.chkDispareunia.setSelected(p.getAntecGinec().isDispareunia());
        this.chkHipertension.setSelected(p.getAntecFam().isHta());
        this.chkOncologicos.setSelected(p.getAntecFam().isOncologicos());
        this.chkSdpm.setSelected(p.getAntecGinec().isSdpm());
        this.chkTiroides.setSelected(p.getAntecFam().isTiroides());
        if (!p.getAntecFam().isTiroides()) 
            this.txtfTiroides.setEnabled(false);
        if (!p.getAntecFam().isOncologicos())
            this.txtaOncologicos.setEnabled(false);
    }
    
    /**
     * Metodo utilizado para llenar las obras sociales
     */
    private void llenarObrasSociales() {
        obras = new LinkedList<>();
        cmbObraSocial.removeAllItems();
        obras.add(new ObraSocial(0, "Sin Obra Social"));
        obras.addAll(daoObraSocial.getAllObrasSociales());
        for(int i = 0; i<obras.size(); i++)
            cmbObraSocial.addItem(obras.get(i).getNombre());
        cmbObraSocial.setSelectedIndex(0);
    }

    /**
     * Metodo utilizado para limpiar las cajas
     */
    private void limpiar() {
        
        this.txtfNombres.setText("");
        this.txtfApellidos.setText("");
        this.txtfTelefono.setText("");
        this.txtfDni.setText("");
        this.ftxtfFechaNacimiento.setText("");
        this.cmbGrupoS.setSelectedIndex(0);
        this.rbtnFactorNeg.setSelected(false);
        this.rbtnFactorPos.setSelected(false);
        this.cmbObraSocial.setSelectedIndex(0);
        this.txtfNumeroAfiliado.setText("");
        this.txtaPersonales.setText("");
        this.txtaToxicos.setText("");
        this.txtaQuirurgicos.setText("");
        this.txtaFamObservaciones.setText("");
        this.txtaGinecObserv.setText("");
        this.txtfAbortos.setText("");
        this.txtfAnticonceptivos.setText("");
        this.txtaOncologicos.setText("");
        this.txtfCesareas.setText("");
        this.txtfDuracionMenstrual.setText("");
        this.txtfPeriodoMenstrual.setText("");
        this.txtfGestaciones.setText("");
        this.txtfMenarca.setText("");
        this.txtfMenopausia.setText("");
        this.txtfPartos.setText("");
        this.txtfTelarca.setText("");
        this.txtfTiroides.setText("");
        this.txtfVivos.setText("");
        this.txtfMuertos.setText("");
        this.rbtnFactorNeg.setSelected(false);
        this.rbtnFactorPos.setSelected(false);
        this.chkDiabetes.setSelected(false);
        this.chkDismenorrea.setSelected(false);
        this.chkDispareunia.setSelected(false);
        this.chkHipertension.setSelected(false);
        this.chkOncologicos.setSelected(false);
        this.chkSdpm.setSelected(false);
        this.chkTiroides.setSelected(false);
        this.txtfTiroides.setEditable(false);
        this.txtaOncologicos.setEditable(false);
    }
    
    /**
     * Metodo utilizado para cambiar el estado de las cajas (Habilitadas, Deshabilitadas)
     * @param b true (Habilitadas), false ()Deshabilitadas
     */
    private void cambiarEstadoCajas(boolean b) {
        this.txtfNombres.setEditable(b);
        this.txtfApellidos.setEditable(b);
        this.txtfTelefono.setEditable(b);
        this.txtfDni.setEditable(b);
        this.ftxtfFechaNacimiento.setEditable(b);
        this.txtfNumeroAfiliado.setEditable(b);
        this.txtaPersonales.setEditable(b);
        this.txtaToxicos.setEditable(b);
        this.txtaQuirurgicos.setEditable(b);
        this.txtaFamObservaciones.setEditable(b);
        this.txtaGinecObserv.setEditable(b);
        this.txtfAbortos.setEditable(b);
        this.txtfAnticonceptivos.setEditable(b);
        this.txtaOncologicos.setEditable(b);
        this.txtfCesareas.setEditable(b);
        this.txtfDuracionMenstrual.setEditable(b);
        this.txtfPeriodoMenstrual.setEditable(b);
        this.txtfGestaciones.setEditable(b);
        this.txtfMenarca.setEditable(b);
        this.txtfMenopausia.setEditable(b);
        this.txtfPartos.setEditable(b);
        this.txtfTelarca.setEditable(b);
        this.txtfTiroides.setEditable(b);
        this.txtfVivos.setEditable(b);
        this.txtfMuertos.setEditable(b);
        this.txtfTiroides.setEditable(b);
        this.txtaOncologicos.setEditable(b);
        
        this.cmbGrupoS.setEditable(!b);
        this.cmbObraSocial.setEditable(!b);
        
        this.cmbObraSocial.setEnabled(b);
        this.cmbGrupoS.setEnabled(b);
        
        this.rbtnFactorNeg.setEnabled(b);
        this.rbtnFactorPos.setEnabled(b);
        
        this.txtfNombres.setFocusable(b);
        this.txtfApellidos.setFocusable(b);
        this.txtfTelefono.setFocusable(b);
        this.txtfDni.setFocusable(b);
        this.ftxtfFechaNacimiento.setFocusable(b);
        this.rbtnFactorNeg.setFocusable(b);
        this.rbtnFactorPos.setFocusable(b);
        this.txtfNumeroAfiliado.setFocusable(b);
        this.txtaPersonales.setFocusable(b);
        this.txtaToxicos.setFocusable(b);
        this.txtaQuirurgicos.setFocusable(b);
        this.txtaFamObservaciones.setFocusable(b);
        this.txtaGinecObserv.setFocusable(b);
        this.txtfAbortos.setFocusable(b);
        this.txtfAnticonceptivos.setFocusable(b);
        this.txtaOncologicos.setFocusable(b);
        this.txtfCesareas.setFocusable(b);
        this.txtfDuracionMenstrual.setFocusable(b);
        this.txtfPeriodoMenstrual.setFocusable(b);
        this.txtfGestaciones.setFocusable(b);
        this.txtfMenarca.setFocusable(b);
        this.txtfMenopausia.setFocusable(b);
        this.txtfPartos.setFocusable(b);
        this.txtfTelarca.setFocusable(b);
        this.txtfTiroides.setFocusable(b);
        this.txtfVivos.setFocusable(b);
        this.txtfMuertos.setFocusable(b);
        this.chkDiabetes.setFocusable(b);
        this.chkDismenorrea.setFocusable(b);
        this.chkDispareunia.setFocusable(b);
        this.chkHipertension.setFocusable(b);
        this.chkOncologicos.setFocusable(b);
        this.chkSdpm.setFocusable(b);
        this.chkTiroides.setFocusable(b);
        this.txtfTiroides.setFocusable(b);
        this.txtaOncologicos.setFocusable(b);
        
        this.btnNuevaObraSocial.setEnabled(b);
    }
    
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/sistema.png"));
        return retValue;
    }
    
    /**
     * Método controlador del cierre de la ventana y validaciones oportunas
     */
    private void salir() {
        if(!btnGuardar.isEnabled()){
            this.dispose();
            if (this.getParent() != null)
                this.getParent().setVisible(true);
            if (hc != null)
                hc.cerrarHijo(this);
            else 
                padreP.cerrarHijo(this);
        }
        else
        {
            MensajesValidaciones.validarSalidaVentana(this);
        }
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

    /**
     * Devuelve el número de dni del paciente en cuestión
     * @return el dni del paciente, -1 si es un nuevo paciente aún no guardado
     */
    public long getDniPaciente() {
        if (p != null)
            return p.getDni();
        return -1;
    }
}
