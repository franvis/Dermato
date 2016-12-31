package GUI;

import ClasesBase.PrePaidHealthInsurance;
import ClasesBase.Patient;
import Utils.StyleManager;
import Utils.ValidationsAndMessages;
import DAO.DAOPrepaidHealthInsurance;
import DAO.DAOPatient;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;

public class ABMPatient extends javax.swing.JFrame {
    private Frame padre;
    private Principal padreP;
    private ClinicalHistory hc;
    private DAOPatient daoPaciente;
    private Patient p;
    private DAOPrepaidHealthInsurance daoObraSocial;
    private LinkedList<PrePaidHealthInsurance> obras;
    private AntecedentesFamiliares af;
    private Antecedentes agen;
    private AntecedentesGinecologicos aginec;
    private int procedencia;
    private long dniOriginal;
    private int idObraSocialOriginal;
    private String nroAfiliadoOriginal;
    private String tiroidesAux = "", oncologógicosAux = "";
    
    /**
     * Creates new form DatosPaciente
     */
    public ABMPatient(java.awt.Frame parent, boolean modal,int procedencia) {     //procedencia: 0 principal nuevo, 
        initComponents();
        ComboBoxEditor editor = cmbObraSocial.getEditor();
        JTextField etf = (JTextField) editor.getEditorComponent();
        etf.setDisabledTextColor(StyleManager.getColorTexto());
        etf.setBackground(StyleManager.getColorTerciario());
        txtfNumeroAfiliado.setDisabledTextColor(StyleManager.getColorTexto());
        this.btnModificar.setVisible(false);
        this.btnModificar.setEnabled(false);
        daoPaciente = new DAOPatient();
        daoObraSocial = new DAOPrepaidHealthInsurance();
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
        hc = (ClinicalHistory) parent;
        padre = hc;
        }
        setIconImage(getIconImage());
        Utils.StyleManager.paint(this);
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
    
    public ABMPatient(java.awt.Frame parent, boolean modal,int procedencia, Patient p) {
        initComponents();
        ComboBoxEditor editor = cmbObraSocial.getEditor();
        JTextField etf = (JTextField) editor.getEditorComponent();
        etf.setDisabledTextColor(StyleManager.getColorTexto());
        etf.setBackground(StyleManager.getColorTerciario());
        txtfNumeroAfiliado.setDisabledTextColor(StyleManager.getColorTexto());
        daoPaciente = new DAOPatient();
        daoObraSocial = new DAOPrepaidHealthInsurance();
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
        hc = (ClinicalHistory) parent;
        llenarCajas(p);
        this.p = p;
        dniOriginal = Long.parseLong(this.txtfDni.getText());
        if (p.getObraSocial() != null)
            nroAfiliadoOriginal = p.getNumeroAfiliado();
        }
        
        this.setLocationRelativeTo(parent);
        setIconImage(getIconImage());
        Utils.StyleManager.paint(this);
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
        btnModificar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        pnlOtherData = new javax.swing.JPanel();
        lblstaticFirstConsultDate = new javax.swing.JLabel();
        ftxtfFechaPrimeraConsulta = new javax.swing.JFormattedTextField();
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
        pnlFamiliares = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtaFamiliares = new javax.swing.JTextArea();
        pnlMedicamentosos = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtaMedicamentosos = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Nuevo Paciente");
        setIconImages(getIconImages());
        setMinimumSize(getPreferredSize());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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
        txtfNombres.setNextFocusableComponent(txtfApellidos);
        txtfNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfNombresKeyTyped(evt);
            }
        });

        txtfApellidos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfApellidos.setNextFocusableComponent(txtfTelefono);
        txtfApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfApellidosKeyTyped(evt);
            }
        });

        txtfTelefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfTelefono.setNextFocusableComponent(txtfDni);
        txtfTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfTelefonoKeyTyped(evt);
            }
        });

        txtfDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfDni.setNextFocusableComponent(txtfAddress);
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
        ftxtfFechaNacimiento.setNextFocusableComponent(cmbObraSocial);

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
        txtfNumeroAfiliado.setNextFocusableComponent(ftxtfFechaPrimeraConsulta);
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
        txtfAddress.setNextFocusableComponent(txtfCity);
        txtfAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfAddressKeyTyped(evt);
            }
        });

        lblstaticCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticCity.setText("Localidad:");

        txtfCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfCity.setNextFocusableComponent(ftxtfFechaNacimiento);
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
                        .addGap(0, 0, Short.MAX_VALUE))
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

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 51, 102));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_enabled.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.setNextFocusableComponent(btnVolver);
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

        btnVolver.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(0, 51, 102));
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home_enabled.png"))); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnVolver.setContentAreaFilled(false);
        btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnVolver.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnVolver.setNextFocusableComponent(txtfNombres);
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

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlOtherData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Otros Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        lblstaticFirstConsultDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticFirstConsultDate.setText("Fecha de Primera Consulta:");

        try {
            ftxtfFechaPrimeraConsulta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtfFechaPrimeraConsulta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ftxtfFechaPrimeraConsulta.setNextFocusableComponent(txtaPersonales);

        javax.swing.GroupLayout pnlOtherDataLayout = new javax.swing.GroupLayout(pnlOtherData);
        pnlOtherData.setLayout(pnlOtherDataLayout);
        pnlOtherDataLayout.setHorizontalGroup(
            pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblstaticFirstConsultDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ftxtfFechaPrimeraConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );
        pnlOtherDataLayout.setVerticalGroup(
            pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ftxtfFechaPrimeraConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblstaticFirstConsultDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlGenerales.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Antecedentes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlGenerales.setNextFocusableComponent(txtaPersonales);

        pnlPersonales.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );
        pnlPersonalesLayout.setVerticalGroup(
            pnlPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        pnlQuirurgicos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Quirúrgicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

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
            .addComponent(jScrollPane6)
        );
        pnlQuirurgicosLayout.setVerticalGroup(
            pnlQuirurgicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        pnlToxicos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Tóxicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaToxicos.setColumns(20);
        txtaToxicos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaToxicos.setLineWrap(true);
        txtaToxicos.setRows(5);
        txtaToxicos.setTabSize(0);
        txtaToxicos.setWrapStyleWord(true);
        txtaToxicos.setNextFocusableComponent(txtaMedicamentosos);
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
            .addComponent(jScrollPane7)
        );
        pnlToxicosLayout.setVerticalGroup(
            pnlToxicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        pnlFamiliares.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Familiares", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaFamiliares.setColumns(20);
        txtaFamiliares.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaFamiliares.setLineWrap(true);
        txtaFamiliares.setRows(5);
        txtaFamiliares.setTabSize(0);
        txtaFamiliares.setWrapStyleWord(true);
        txtaFamiliares.setNextFocusableComponent(btnGuardar);
        txtaFamiliares.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaFamiliaresKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(txtaFamiliares);

        javax.swing.GroupLayout pnlFamiliaresLayout = new javax.swing.GroupLayout(pnlFamiliares);
        pnlFamiliares.setLayout(pnlFamiliaresLayout);
        pnlFamiliaresLayout.setHorizontalGroup(
            pnlFamiliaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
        );
        pnlFamiliaresLayout.setVerticalGroup(
            pnlFamiliaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        pnlMedicamentosos.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Medicamentosos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaMedicamentosos.setColumns(20);
        txtaMedicamentosos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaMedicamentosos.setLineWrap(true);
        txtaMedicamentosos.setRows(5);
        txtaMedicamentosos.setTabSize(0);
        txtaMedicamentosos.setWrapStyleWord(true);
        txtaMedicamentosos.setNextFocusableComponent(txtaFamiliares);
        txtaMedicamentosos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaMedicamentososKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(txtaMedicamentosos);

        javax.swing.GroupLayout pnlMedicamentososLayout = new javax.swing.GroupLayout(pnlMedicamentosos);
        pnlMedicamentosos.setLayout(pnlMedicamentososLayout);
        pnlMedicamentososLayout.setHorizontalGroup(
            pnlMedicamentososLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9)
        );
        pnlMedicamentososLayout.setVerticalGroup(
            pnlMedicamentososLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlGeneralesLayout = new javax.swing.GroupLayout(pnlGenerales);
        pnlGenerales.setLayout(pnlGeneralesLayout);
        pnlGeneralesLayout.setHorizontalGroup(
            pnlGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneralesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPersonales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlQuirurgicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlToxicos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFamiliares, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMedicamentosos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlGeneralesLayout.setVerticalGroup(
            pnlGeneralesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneralesLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(pnlPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlQuirurgicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlToxicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlMedicamentosos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFamiliares, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnlOtherData, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlDatosPersonales, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlGenerales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlGenerales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlOtherData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlOtherData.getAccessibleContext().setAccessibleName("Otros datos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfTelefonoKeyTyped
        ValidationsAndMessages.negarLetras(evt,this);
        ValidationsAndMessages.limitarLargo(this.txtfTelefono, evt, 45,this);
    }//GEN-LAST:event_txtfTelefonoKeyTyped

    private void txtfDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfDniKeyTyped
        ValidationsAndMessages.negarLetras(evt,this);
    }//GEN-LAST:event_txtfDniKeyTyped

    private void txtfNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNombresKeyTyped
        ValidationsAndMessages.negarNumeros(evt,this);
        ValidationsAndMessages.limitarLargo(this.txtfNombres,evt,45,this);
    }//GEN-LAST:event_txtfNombresKeyTyped

    private void txtfApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfApellidosKeyTyped
        ValidationsAndMessages.negarNumeros(evt,this);
        ValidationsAndMessages.limitarLargo(this.txtfApellidos,evt,45,this);
    }//GEN-LAST:event_txtfApellidosKeyTyped

    private void btnNuevaObraSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaObraSocialActionPerformed
        this.txtfNuevaObraSocial.setEnabled(true);
        this.btnGuardarOS.setEnabled(true);
        this.btnCancelarOS.setEnabled(true);
        this.btnNuevaObraSocial.setEnabled(false);
        this.cmbObraSocial.setEnabled(false);
    }//GEN-LAST:event_btnNuevaObraSocialActionPerformed

    private void txtfNumeroAfiliadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNumeroAfiliadoKeyTyped
        ValidationsAndMessages.limitarLargo(this.txtfNumeroAfiliado, evt, 45, this);
        ValidationsAndMessages.validarNumeroAfiliado(evt, this);
    }//GEN-LAST:event_txtfNumeroAfiliadoKeyTyped

    private void btnGuardarOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarOSActionPerformed
        if (this.txtfNuevaObraSocial.getText().isEmpty()) {
            ValidationsAndMessages.mostrarError(this, "Ingrese un nombre de obra social válido...");
            return;
        }
        String nuevaObra = this.txtfNuevaObraSocial.getText();
        if (daoObraSocial.registerPrePaidHealthInsurance(new PrePaidHealthInsurance(0, nuevaObra))) {
            ValidationsAndMessages.mostrarInformacion(this, "Registro Exitoso.");
            llenarObrasSociales();
        } else {
            ValidationsAndMessages.mostrarError(this, "Registro Fallido.");
        }
        
        this.txtfNuevaObraSocial.setEnabled(false);
        this.btnGuardarOS.setEnabled(false);
        this.btnNuevaObraSocial.setEnabled(true);
        this.cmbObraSocial.setEnabled(true);
        this.cmbObraSocial.setSelectedItem(nuevaObra);
        this.txtfNuevaObraSocial.setText("");
    }//GEN-LAST:event_btnGuardarOSActionPerformed

    private void txtfNuevaObraSocialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNuevaObraSocialKeyTyped
        ValidationsAndMessages.limitarLargo(this.txtfNuevaObraSocial, evt, 80, this);
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
    agen = new Antecedentes();
    aginec = new AntecedentesGinecologicos();
    String error = comprobarDatosObligatorios();
    if(!error.isEmpty())
    {
        ValidationsAndMessages.mostrarError(this,"Debe completar los siguientes datos obligatorios: \n"+error);
        return;
    }   
    long dniPaciente = Long.parseLong(this.txtfDni.getText());
    
    PrePaidHealthInsurance osAux = new PrePaidHealthInsurance();
    for (int i = 0; i < obras.size(); i++) {
        if(obras.get(i).getNombre().compareTo(cmbObraSocial.getSelectedItem().toString()) == 0)
            osAux = obras.get(i);
    }
    
    Patient match = daoPaciente.verificarNroAfiliado(osAux.getId(), txtfNumeroAfiliado.getText());
    
    if(procedencia == 0) //Principal, nuevo
    {
        
        if(daoPaciente.verifyPatient(dniPaciente)){
            ValidationsAndMessages.mostrarError(this, "El paciente ya se encuentra registrado. Corrija el DNI o búsquelo en la ventana principal.");
            this.txtfDni.setText("");
            this.txtfDni.grabFocus();
            return;
        }
                    
        if(cmbObraSocial.getSelectedIndex() != 0 && match != null){
            ValidationsAndMessages.mostrarError(this, "El paciente '"+match.getApellido().toUpperCase()+", "+match.getNombre()+"', DNI N°"+match.getDni()
                    + " ya se encuentra registrado con misma obra social y N° de afiliado.");
            this.txtfNumeroAfiliado.setText("");
            this.txtfNumeroAfiliado.grabFocus();
            return;
        }
        
        generarAntecedentes();
    
        if(!generarPaciente())
            return;
    
        if(daoPaciente.registerPatient(p))
        {
            ValidationsAndMessages.mostrarInformacion(this,"Registro Exitoso.");
            hc = new ClinicalHistory(padre, p, 1);
            hc.llenarCampos(p,1);
            padreP.updatePatientList(null);
            this.dispose();
            hc.setVisible(true);
        }
        else{
            ValidationsAndMessages.mostrarError(this,"Registro Fallido.");
        }
    }
    else if(procedencia == 1 || procedencia == 2)//Historia clinica(2), Principal-Modificar(1) 
    {
        if(dniPaciente != dniOriginal && daoPaciente.verifyPatient(dniPaciente)){
            ValidationsAndMessages.mostrarError(this,"El dni ingresado para la modificación ya se encuentra en la base de datos a nombre de otro paciente.\n"
                                             + " Corrija el DNI.");
            this.txtfDni.setText("");
            return;
        }
        
        if(cmbObraSocial.getSelectedIndex() != 0 
                && (idObraSocialOriginal != osAux.getId() || !nroAfiliadoOriginal.equals(txtfNumeroAfiliado.getText())) 
                && match != null
                && match.getDni() != dniOriginal){
            ValidationsAndMessages.mostrarError(this, "El paciente '"+match.getApellido().toUpperCase()+", "+match.getNombre()+"', DNI N°"+match.getDni()
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
            ValidationsAndMessages.mostrarError(this,"Debe completar los siguientes campos obligatorios: \n"+error);
            return;
        }   
    
        if(daoPaciente.updatePatient(p,dniOriginal))
        {
            ValidationsAndMessages.mostrarInformacion(this,"Actualización Exitosa.");
            
                if(this.procedencia == 2)
                {
                    hc.setPaciente(p);
                    hc.llenarCampos(p,1);
                    this.dispose();
                    hc.setVisible(true);
                }
                
                else
                {
                    LinkedList<Patient> pacienteList = new LinkedList<Patient>();
                    pacienteList.add(daoPaciente.getBasicPatient(p.getDni()));
                    padreP.updatePatientList(pacienteList);
                }
        }
        else{
            ValidationsAndMessages.mostrarError(this,"Actualización Fallida.");
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
        this.controlarFoco(evt);
    }//GEN-LAST:event_txtaToxicosKeyPressed

    private void txtaQuirurgicosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaQuirurgicosKeyPressed
        this.controlarFoco(evt);
    }//GEN-LAST:event_txtaQuirurgicosKeyPressed

    private void txtaFamiliaresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaFamiliaresKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaFamiliaresKeyPressed

    private void txtaMedicamentososKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaMedicamentososKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaMedicamentososKeyPressed

    
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
    private javax.swing.JFormattedTextField ftxtfFechaPrimeraConsulta;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
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
    private javax.swing.JPanel pnlFamiliares;
    private javax.swing.JPanel pnlGenerales;
    private javax.swing.JPanel pnlMedicamentosos;
    private javax.swing.JPanel pnlObraSocial;
    private javax.swing.JPanel pnlOtherData;
    private javax.swing.JPanel pnlPersonales;
    private javax.swing.JPanel pnlQuirurgicos;
    private javax.swing.JPanel pnlToxicos;
    private javax.swing.JTextArea txtaFamiliares;
    private javax.swing.JTextArea txtaMedicamentosos;
    private javax.swing.JTextArea txtaPersonales;
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
        p = new Patient();
        
        p.setNombre(this.txtfNombres.getText());
        p.setApellido(this.txtfApellidos.getText());
        p.setTelefono(this.txtfTelefono.getText());
        p.setDni(Long.parseLong(this.txtfDni.getText()));
        
        try {
            String dia = this.ftxtfFechaNacimiento.getText(0,2);
            String mes = this.ftxtfFechaNacimiento.getText(3,2);
            String año = this.ftxtfFechaNacimiento.getText(6,4);
            
            error = ValidationsAndMessages.corroborarFecha(dia,mes,año);
           
            if(!error.isEmpty())
            {
                ValidationsAndMessages.mostrarError(this, "Los siguientes valores de la fecha no son válidos o están fuera de rango: \n" + error);
                return false;
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(ABMPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.setFechaNacimiento(this.ftxtfFechaNacimiento.getText());
        
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
        agen = new Antecedentes();
        
            agen.setAntecedentesPersonales(this.txtaPersonales.getText());
            agen.setAntecedentesQuirurgicos(this.txtaQuirurgicos.getText());
            agen.setAntecedentesToxicos(this.txtaToxicos.getText());
      
       //Antecedentes Ginecologicos
       aginec = new AntecedentesGinecologicos();
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
        
        return incompletas;
    }
    
        
    /**
     * Metodo generico utilizado para llenar todos los datos.
     */
    private void llenarCajas(Patient p) {
        this.txtfNombres.setText(p.getNombre());
        this.txtfApellidos.setText(p.getApellido());
        this.txtfTelefono.setText(p.getTelefono());
        this.txtfDni.setText(p.getDni()+"");
        this.ftxtfFechaNacimiento.setText(p.getFechaNacimiento());
        this.cmbObraSocial.setSelectedItem(p.getObraSocial().getNombre());
        this.txtfNumeroAfiliado.setText(p.getNumeroAfiliado());
        
        this.txtaPersonales.setText(p.getAntecGen().getAntecedentesPersonales());
        this.txtaToxicos.setText(p.getAntecGen().getAntecedentesToxicos());
        this.txtaQuirurgicos.setText(p.getAntecGen().getAntecedentesQuirurgicos());
    }
    
    /**
     * Metodo utilizado para llenar las obras sociales
     */
    private void llenarObrasSociales() {
        obras = new LinkedList<>();
        cmbObraSocial.removeAllItems();
        obras.add(new PrePaidHealthInsurance(0, "Sin Obra Social"));
        obras.addAll(daoObraSocial.getAllPrePaidHealthInsurances());
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
        this.cmbObraSocial.setSelectedIndex(0);
        this.txtfNumeroAfiliado.setText("");
        this.txtaPersonales.setText("");
        this.txtaToxicos.setText("");
        this.txtaQuirurgicos.setText("");
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
        this.cmbObraSocial.setEditable(!b);
        
        this.cmbObraSocial.setEnabled(b);
        
        this.txtfNombres.setFocusable(b);
        this.txtfApellidos.setFocusable(b);
        this.txtfTelefono.setFocusable(b);
        this.txtfDni.setFocusable(b);
        this.ftxtfFechaNacimiento.setFocusable(b);
        this.txtfNumeroAfiliado.setFocusable(b);
        this.txtaPersonales.setFocusable(b);
        this.txtaToxicos.setFocusable(b);
        this.txtaQuirurgicos.setFocusable(b);
        
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
                padreP.closeChild(this);
        }
        else
        {
            ValidationsAndMessages.validarSalidaVentana(this);
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
    public long getPatientDni() {
        if (p != null)
            return p.getDni();
        return -1;
    }
}
