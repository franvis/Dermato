package GUI;

import ClasesBase.Antecedents;
import ClasesBase.PrePaidHealthInsurance;
import ClasesBase.Patient;
import Utils.StyleManager;
import Utils.ValidationsAndMessages;
import DAO.DAOPrepaidHealthInsurance;
import DAO.DAOPatient;
import static Utils.GeneralUtils.handleFocus;
import static Utils.GeneralUtils.setCustomFont;
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

    private Frame frameParent;
    private Principal principalParent;
    private ClinicalHistory clinicalHistory;
    private final DAOPatient daoPatient;
    private Patient patient;
    private final DAOPrepaidHealthInsurance daoPrePaidHealthInsurance;
    private LinkedList<PrePaidHealthInsurance> prePaidHealthInsurances;
    private Antecedents antecedents;
    private final int origin;
    private long previousDni;
    private int previousPrePaidHealthInsuranceId;
    private String previousInsuranceNumber;

    /**
     * Creates new form DatosPaciente
     * @param parent
     * @param modal
     * @param origin
     */
    public ABMPatient(java.awt.Frame parent, boolean modal, int origin) {
        initComponents();
        ComboBoxEditor editor = cmbPPHealthInsurance.getEditor();
        JTextField etf = (JTextField) editor.getEditorComponent();
        etf.setDisabledTextColor(StyleManager.getTextColor());
        etf.setBackground(StyleManager.getThirdColor());
        txtfInsuranceNumber.setDisabledTextColor(StyleManager.getTextColor());
        this.btnModify.setVisible(false);
        this.btnModify.setEnabled(false);
        daoPatient = new DAOPatient();
        daoPrePaidHealthInsurance = new DAOPrepaidHealthInsurance();
        prePaidHealthInsurances = new LinkedList<>();
        this.origin = origin;
        fillPrePaidHealthInsurances();
        if (origin == 0 || origin == 1) {
            principalParent = (Principal) parent;
            frameParent = principalParent;
        } else if (origin == 2) {
            clinicalHistory = (ClinicalHistory) parent;
            frameParent = clinicalHistory;
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
                exit();
            }
        }, strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);
        pnlOtherData.setBorder(javax.swing.BorderFactory.createTitledBorder(
                new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true),
                "Otros Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13),
                new java.awt.Color(0, 51, 102)));
    }

    public ABMPatient(java.awt.Frame parent, boolean modal, int origin, Patient patient) {
        initComponents();
        ComboBoxEditor editor = cmbPPHealthInsurance.getEditor();
        JTextField etf = (JTextField) editor.getEditorComponent();
        etf.setDisabledTextColor(StyleManager.getTextColor());
        etf.setBackground(StyleManager.getThirdColor());
        txtfInsuranceNumber.setDisabledTextColor(StyleManager.getTextColor());
        daoPatient = new DAOPatient();
        daoPrePaidHealthInsurance = new DAOPrepaidHealthInsurance();
        prePaidHealthInsurances = new LinkedList<>();
        this.origin = origin;
        fillPrePaidHealthInsurances();
        if (origin == 1) {
            fillFields(patient);
            this.patient = patient;
            previousDni = Long.parseLong(this.txtfDni.getText());
            principalParent = (Principal) parent;
            frameParent = principalParent;
        } else if (origin == 2) {
            this.btnModify.setVisible(false);
            clinicalHistory = (ClinicalHistory) parent;
            fillFields(patient);
            this.patient = patient;
            previousDni = Long.parseLong(this.txtfDni.getText());
            if (patient.getPrepaidHealthInsurance() != null) {
                previousInsuranceNumber = patient.getPrepaidHealthInsuranceNumber();
            }
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
                exit();
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
        lblstaticNames = new javax.swing.JLabel();
        lblstaticLastNames = new javax.swing.JLabel();
        lblstaticPhone = new javax.swing.JLabel();
        lblstaticBirthday = new javax.swing.JLabel();
        txtfNames = new javax.swing.JTextField();
        txtfLastNames = new javax.swing.JTextField();
        txtfPhone = new javax.swing.JTextField();
        txtfDni = new javax.swing.JTextField();
        lblstaticDni = new javax.swing.JLabel();
        ftxtfBirthday = new javax.swing.JFormattedTextField();
        pnlPPHealthInsurance = new javax.swing.JPanel();
        cmbPPHealthInsurance = new javax.swing.JComboBox();
        btnNewPPHealthInsurance = new javax.swing.JButton();
        lblstaticInsuranceNumber = new javax.swing.JLabel();
        txtfInsuranceNumber = new javax.swing.JTextField();
        btnSavePPHealthInsurance = new javax.swing.JButton();
        txtfNewPPHealthInsurance = new javax.swing.JTextField();
        btnCancelPPHealthInsurance = new javax.swing.JButton();
        lblstaticAddress = new javax.swing.JLabel();
        txtfAddress = new javax.swing.JTextField();
        lblstaticCity = new javax.swing.JLabel();
        txtfCity = new javax.swing.JTextField();
        pnlButtons = new javax.swing.JPanel();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        pnlOtherData = new javax.swing.JPanel();
        lblstaticFirstVisitDate = new javax.swing.JLabel();
        ftxtfFirstVisitDate = new javax.swing.JFormattedTextField();
        pnlAntecedents = new javax.swing.JPanel();
        pnlPersonal = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtaPersonal = new javax.swing.JTextArea();
        pnlSurgical = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtaSurgical = new javax.swing.JTextArea();
        pnlToxics = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtaToxics = new javax.swing.JTextArea();
        pnlFamily = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtaFamily = new javax.swing.JTextArea();
        pnlPharmacological = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtaPharmacological = new javax.swing.JTextArea();

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

        lblstaticNames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticNames.setText("Nombres:");

        lblstaticLastNames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticLastNames.setText("Apellidos:");

        lblstaticPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticPhone.setText("Teléfono:");

        lblstaticBirthday.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticBirthday.setText("Fecha de Nacimiento:");

        txtfNames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfNames.setNextFocusableComponent(txtfLastNames);
        txtfNames.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfNamesKeyTyped(evt);
            }
        });

        txtfLastNames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfLastNames.setNextFocusableComponent(txtfPhone);
        txtfLastNames.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfLastNamesKeyTyped(evt);
            }
        });

        txtfPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfPhone.setNextFocusableComponent(txtfDni);
        txtfPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfPhoneKeyTyped(evt);
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
            ftxtfBirthday.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtfBirthday.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ftxtfBirthday.setNextFocusableComponent(cmbPPHealthInsurance);

        pnlPPHealthInsurance.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Obra Social", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlPPHealthInsurance.setOpaque(false);

        cmbPPHealthInsurance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbPPHealthInsurance.setFocusCycleRoot(true);
        cmbPPHealthInsurance.setNextFocusableComponent(btnNewPPHealthInsurance);
        cmbPPHealthInsurance.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbPPHealthInsuranceItemStateChanged(evt);
            }
        });

        btnNewPPHealthInsurance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNewPPHealthInsurance.setText("Nueva");
        btnNewPPHealthInsurance.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNewPPHealthInsurance.setContentAreaFilled(false);
        btnNewPPHealthInsurance.setNextFocusableComponent(txtfInsuranceNumber);
        btnNewPPHealthInsurance.setOpaque(true);
        btnNewPPHealthInsurance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPPHealthInsuranceActionPerformed(evt);
            }
        });

        lblstaticInsuranceNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticInsuranceNumber.setText("Nro. Afiliado:");

        txtfInsuranceNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfInsuranceNumber.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtfInsuranceNumber.setEnabled(false);
        txtfInsuranceNumber.setNextFocusableComponent(ftxtfFirstVisitDate);
        txtfInsuranceNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfInsuranceNumberKeyTyped(evt);
            }
        });

        btnSavePPHealthInsurance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSavePPHealthInsurance.setText("Guardar");
        btnSavePPHealthInsurance.setEnabled(false);
        btnSavePPHealthInsurance.setNextFocusableComponent(btnCancelPPHealthInsurance);
        btnSavePPHealthInsurance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePPHealthInsuranceActionPerformed(evt);
            }
        });

        txtfNewPPHealthInsurance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfNewPPHealthInsurance.setEnabled(false);
        txtfNewPPHealthInsurance.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfNewPPHealthInsuranceKeyTyped(evt);
            }
        });

        btnCancelPPHealthInsurance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelPPHealthInsurance.setText("Cancelar");
        btnCancelPPHealthInsurance.setEnabled(false);
        btnCancelPPHealthInsurance.setNextFocusableComponent(cmbPPHealthInsurance);
        btnCancelPPHealthInsurance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelPPHealthInsuranceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPPHealthInsuranceLayout = new javax.swing.GroupLayout(pnlPPHealthInsurance);
        pnlPPHealthInsurance.setLayout(pnlPPHealthInsuranceLayout);
        pnlPPHealthInsuranceLayout.setHorizontalGroup(
            pnlPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPPHealthInsuranceLayout.createSequentialGroup()
                .addGroup(pnlPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPPHealthInsuranceLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPPHealthInsuranceLayout.createSequentialGroup()
                                .addComponent(lblstaticInsuranceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfInsuranceNumber))
                            .addGroup(pnlPPHealthInsuranceLayout.createSequentialGroup()
                                .addComponent(cmbPPHealthInsurance, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNewPPHealthInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlPPHealthInsuranceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtfNewPPHealthInsurance))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPPHealthInsuranceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSavePPHealthInsurance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelPPHealthInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlPPHealthInsuranceLayout.setVerticalGroup(
            pnlPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPPHealthInsuranceLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(pnlPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPPHealthInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewPPHealthInsurance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfNewPPHealthInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelPPHealthInsurance)
                    .addComponent(btnSavePPHealthInsurance))
                .addGap(13, 13, 13)
                .addGroup(pnlPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfInsuranceNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblstaticInsuranceNumber))
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
        txtfCity.setNextFocusableComponent(ftxtfBirthday);
        txtfCity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfCityKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlDatosPersonalesLayout = new javax.swing.GroupLayout(pnlDatosPersonales);
        pnlDatosPersonales.setLayout(pnlDatosPersonalesLayout);
        pnlDatosPersonalesLayout.setHorizontalGroup(
            pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPPHealthInsurance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblstaticNames, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblstaticLastNames, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblstaticPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblstaticDni, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtfLastNames)
                            .addComponent(txtfNames)
                            .addComponent(txtfPhone)
                            .addComponent(txtfDni)))
                    .addGroup(pnlDatosPersonalesLayout.createSequentialGroup()
                        .addComponent(lblstaticBirthday)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ftxtfBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtfNames)
                    .addComponent(lblstaticNames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfLastNames)
                    .addComponent(lblstaticLastNames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDatosPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfPhone)
                    .addComponent(lblstaticPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(ftxtfBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblstaticBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPPHealthInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnModify.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModify.setForeground(new java.awt.Color(0, 51, 102));
        btnModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_enabled.png"))); // NOI18N
        btnModify.setText("Modificar");
        btnModify.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnModify.setContentAreaFilled(false);
        btnModify.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModify.setEnabled(false);
        btnModify.setOpaque(true);
        btnModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModifyMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModifyMouseEntered(evt);
            }
        });
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(0, 51, 102));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_enabled.png"))); // NOI18N
        btnSave.setText("Guardar");
        btnSave.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSave.setNextFocusableComponent(btnBack);
        btnSave.setOpaque(true);
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSaveMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSaveMouseEntered(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBack.setForeground(new java.awt.Color(0, 51, 102));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home_enabled.png"))); // NOI18N
        btnBack.setText("Volver");
        btnBack.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnBack.setContentAreaFilled(false);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBack.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBack.setNextFocusableComponent(txtfNames);
        btnBack.setOpaque(true);
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBackMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBackMouseEntered(evt);
            }
        });
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlOtherData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Otros Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        lblstaticFirstVisitDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticFirstVisitDate.setText("Fecha de Primera Consulta:");

        try {
            ftxtfFirstVisitDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtfFirstVisitDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ftxtfFirstVisitDate.setNextFocusableComponent(txtaPersonal);

        javax.swing.GroupLayout pnlOtherDataLayout = new javax.swing.GroupLayout(pnlOtherData);
        pnlOtherData.setLayout(pnlOtherDataLayout);
        pnlOtherDataLayout.setHorizontalGroup(
            pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblstaticFirstVisitDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ftxtfFirstVisitDate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );
        pnlOtherDataLayout.setVerticalGroup(
            pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ftxtfFirstVisitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblstaticFirstVisitDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlAntecedents.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Antecedentes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlAntecedents.setNextFocusableComponent(txtaPersonal);

        pnlPersonal.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaPersonal.setColumns(20);
        txtaPersonal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaPersonal.setLineWrap(true);
        txtaPersonal.setRows(5);
        txtaPersonal.setTabSize(0);
        txtaPersonal.setWrapStyleWord(true);
        txtaPersonal.setNextFocusableComponent(txtaSurgical);
        txtaPersonal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaPersonalKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(txtaPersonal);

        javax.swing.GroupLayout pnlPersonalLayout = new javax.swing.GroupLayout(pnlPersonal);
        pnlPersonal.setLayout(pnlPersonalLayout);
        pnlPersonalLayout.setHorizontalGroup(
            pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        );
        pnlPersonalLayout.setVerticalGroup(
            pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
        );

        pnlSurgical.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Quirúrgicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaSurgical.setColumns(20);
        txtaSurgical.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaSurgical.setLineWrap(true);
        txtaSurgical.setRows(5);
        txtaSurgical.setTabSize(0);
        txtaSurgical.setWrapStyleWord(true);
        txtaSurgical.setNextFocusableComponent(txtaToxics);
        txtaSurgical.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaSurgicalKeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(txtaSurgical);

        javax.swing.GroupLayout pnlSurgicalLayout = new javax.swing.GroupLayout(pnlSurgical);
        pnlSurgical.setLayout(pnlSurgicalLayout);
        pnlSurgicalLayout.setHorizontalGroup(
            pnlSurgicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
        );
        pnlSurgicalLayout.setVerticalGroup(
            pnlSurgicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
        );

        pnlToxics.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Tóxicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaToxics.setColumns(20);
        txtaToxics.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaToxics.setLineWrap(true);
        txtaToxics.setRows(5);
        txtaToxics.setTabSize(0);
        txtaToxics.setWrapStyleWord(true);
        txtaToxics.setNextFocusableComponent(txtaPharmacological);
        txtaToxics.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaToxicsKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(txtaToxics);

        javax.swing.GroupLayout pnlToxicsLayout = new javax.swing.GroupLayout(pnlToxics);
        pnlToxics.setLayout(pnlToxicsLayout);
        pnlToxicsLayout.setHorizontalGroup(
            pnlToxicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
        );
        pnlToxicsLayout.setVerticalGroup(
            pnlToxicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
        );

        pnlFamily.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Familiares", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaFamily.setColumns(20);
        txtaFamily.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaFamily.setLineWrap(true);
        txtaFamily.setRows(5);
        txtaFamily.setTabSize(0);
        txtaFamily.setWrapStyleWord(true);
        txtaFamily.setNextFocusableComponent(btnSave);
        txtaFamily.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaFamilyKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(txtaFamily);

        javax.swing.GroupLayout pnlFamilyLayout = new javax.swing.GroupLayout(pnlFamily);
        pnlFamily.setLayout(pnlFamilyLayout);
        pnlFamilyLayout.setHorizontalGroup(
            pnlFamilyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
        );
        pnlFamilyLayout.setVerticalGroup(
            pnlFamilyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pnlPharmacological.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Medicamentosos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaPharmacological.setColumns(20);
        txtaPharmacological.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaPharmacological.setLineWrap(true);
        txtaPharmacological.setRows(5);
        txtaPharmacological.setTabSize(0);
        txtaPharmacological.setWrapStyleWord(true);
        txtaPharmacological.setNextFocusableComponent(txtaFamily);
        txtaPharmacological.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaPharmacologicalKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(txtaPharmacological);

        javax.swing.GroupLayout pnlPharmacologicalLayout = new javax.swing.GroupLayout(pnlPharmacological);
        pnlPharmacological.setLayout(pnlPharmacologicalLayout);
        pnlPharmacologicalLayout.setHorizontalGroup(
            pnlPharmacologicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9)
        );
        pnlPharmacologicalLayout.setVerticalGroup(
            pnlPharmacologicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlAntecedentsLayout = new javax.swing.GroupLayout(pnlAntecedents);
        pnlAntecedents.setLayout(pnlAntecedentsLayout);
        pnlAntecedentsLayout.setHorizontalGroup(
            pnlAntecedentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAntecedentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAntecedentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSurgical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlToxics, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFamily, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPharmacological, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAntecedentsLayout.setVerticalGroup(
            pnlAntecedentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAntecedentsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSurgical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlToxics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPharmacological, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFamily, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addComponent(pnlAntecedents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlDatosPersonales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlOtherData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlAntecedents, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pnlOtherData.getAccessibleContext().setAccessibleName("Otros datos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfPhoneKeyTyped
        ValidationsAndMessages.negarLetras(evt, this);
        ValidationsAndMessages.limitarLargo(this.txtfPhone, evt, 45, this);
    }//GEN-LAST:event_txtfPhoneKeyTyped

    private void txtfDniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfDniKeyTyped
        ValidationsAndMessages.negarLetras(evt, this);
    }//GEN-LAST:event_txtfDniKeyTyped

    private void txtfNamesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNamesKeyTyped
        ValidationsAndMessages.negarNumeros(evt, this);
        ValidationsAndMessages.limitarLargo(this.txtfNames, evt, 45, this);
    }//GEN-LAST:event_txtfNamesKeyTyped

    private void txtfLastNamesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfLastNamesKeyTyped
        ValidationsAndMessages.negarNumeros(evt, this);
        ValidationsAndMessages.limitarLargo(this.txtfLastNames, evt, 45, this);
    }//GEN-LAST:event_txtfLastNamesKeyTyped

    private void btnNewPPHealthInsuranceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPPHealthInsuranceActionPerformed
        this.txtfNewPPHealthInsurance.setEnabled(true);
        this.btnSavePPHealthInsurance.setEnabled(true);
        this.btnCancelPPHealthInsurance.setEnabled(true);
        this.btnNewPPHealthInsurance.setEnabled(false);
        this.cmbPPHealthInsurance.setEnabled(false);
    }//GEN-LAST:event_btnNewPPHealthInsuranceActionPerformed

    private void txtfInsuranceNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfInsuranceNumberKeyTyped
        ValidationsAndMessages.limitarLargo(this.txtfInsuranceNumber, evt, 45, this);
        ValidationsAndMessages.validarNumeroAfiliado(evt, this);
    }//GEN-LAST:event_txtfInsuranceNumberKeyTyped

    private void btnSavePPHealthInsuranceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavePPHealthInsuranceActionPerformed
        if (this.txtfNewPPHealthInsurance.getText().isEmpty()) {
            ValidationsAndMessages.showError(this, "Ingrese un nombre de obra social válido...");
            return;
        }
        String nuevaObra = this.txtfNewPPHealthInsurance.getText();
        if (daoPrePaidHealthInsurance.registerPrePaidHealthInsurance(new PrePaidHealthInsurance(0, nuevaObra))) {
            ValidationsAndMessages.showInfo(this, "Registro Exitoso.");
            fillPrePaidHealthInsurances();
        } else {
            ValidationsAndMessages.showError(this, "Registro Fallido.");
        }

        this.txtfNewPPHealthInsurance.setEnabled(false);
        this.btnSavePPHealthInsurance.setEnabled(false);
        this.btnNewPPHealthInsurance.setEnabled(true);
        this.cmbPPHealthInsurance.setEnabled(true);
        this.cmbPPHealthInsurance.setSelectedItem(nuevaObra);
        this.txtfNewPPHealthInsurance.setText("");
    }//GEN-LAST:event_btnSavePPHealthInsuranceActionPerformed

    private void txtfNewPPHealthInsuranceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNewPPHealthInsuranceKeyTyped
        ValidationsAndMessages.limitarLargo(this.txtfNewPPHealthInsurance, evt, 80, this);
    }//GEN-LAST:event_txtfNewPPHealthInsuranceKeyTyped

    private void btnCancelPPHealthInsuranceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelPPHealthInsuranceActionPerformed
        this.txtfNewPPHealthInsurance.setText("");
        this.txtfNewPPHealthInsurance.setEnabled(false);
        this.btnSavePPHealthInsurance.setEnabled(false);
        this.btnCancelPPHealthInsurance.setEnabled(false);
        this.btnNewPPHealthInsurance.setEnabled(true);
        this.cmbPPHealthInsurance.setEnabled(true);
    }//GEN-LAST:event_btnCancelPPHealthInsuranceActionPerformed

private void cmbPPHealthInsuranceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbPPHealthInsuranceItemStateChanged
    if (cmbPPHealthInsurance.getSelectedIndex() == 0) {
        txtfInsuranceNumber.setText("");
        txtfInsuranceNumber.setEnabled(false);
    } else {
        txtfInsuranceNumber.setEnabled(true);
    }
}//GEN-LAST:event_cmbPPHealthInsuranceItemStateChanged

    private void btnSaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseEntered
        setCustomFont(btnSave, true);
    }//GEN-LAST:event_btnSaveMouseEntered

    private void btnSaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseExited
        setCustomFont(btnSave, false);
    }//GEN-LAST:event_btnSaveMouseExited

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        antecedents = new Antecedents();
        String error = validateMandatoryFields();
        if (!error.isEmpty()) {
            ValidationsAndMessages.showError(this, "Debe completar los siguientes datos obligatorios: \n" + error);
            return;
        }
        long dniPaciente = Long.parseLong(this.txtfDni.getText());

        PrePaidHealthInsurance osAux = new PrePaidHealthInsurance();
        for (int i = 0; i < prePaidHealthInsurances.size(); i++) {
            if (prePaidHealthInsurances.get(i).getName().compareTo(cmbPPHealthInsurance.getSelectedItem().toString()) == 0) {
                osAux = prePaidHealthInsurances.get(i);
            }
        }

        Patient match = daoPatient.verificarNroAfiliado(osAux.getId(), txtfInsuranceNumber.getText());

        if (origin == 0) //Principal, nuevo
        {

            if (daoPatient.verifyPatient(dniPaciente)) {
                ValidationsAndMessages.showError(this, "El paciente ya se encuentra registrado. Corrija el DNI o búsquelo en la ventana principal.");
                this.txtfDni.setText("");
                this.txtfDni.grabFocus();
                return;
            }

            if (cmbPPHealthInsurance.getSelectedIndex() != 0 && match != null) {
                ValidationsAndMessages.showError(this, "El paciente '" + match.getLastname().toUpperCase() + ", " + match.getName() + "', DNI N°" + match.getDni()
                        + " ya se encuentra registrado con misma obra social y N° de afiliado.");
                this.txtfInsuranceNumber.setText("");
                this.txtfInsuranceNumber.grabFocus();
                return;
            }

            generateAntecedents();

            if (!generatePatient()) {
                return;
            }

            if (daoPatient.registerPatient(patient)) {
                ValidationsAndMessages.showInfo(this, "Registro Exitoso.");
                clinicalHistory = new ClinicalHistory(frameParent, patient, 1);
                clinicalHistory.fillFields(patient);
                principalParent.updatePatientList(null);
                this.dispose();
                clinicalHistory.setVisible(true);
            } else {
                ValidationsAndMessages.showError(this, "Registro Fallido.");
            }
        } else if (origin == 1 || origin == 2)//Historia clinica(2), Principal-Modificar(1) 
        {
            if (dniPaciente != previousDni && daoPatient.verifyPatient(dniPaciente)) {
                ValidationsAndMessages.showError(this, "El dni ingresado para la modificación ya se encuentra en la base de datos a nombre de otro paciente.\n"
                        + " Corrija el DNI.");
                this.txtfDni.setText("");
                return;
            }

            if (cmbPPHealthInsurance.getSelectedIndex() != 0
                    && (previousPrePaidHealthInsuranceId != osAux.getId() || !previousInsuranceNumber.equals(txtfInsuranceNumber.getText()))
                    && match != null
                    && match.getDni() != previousDni) {
                ValidationsAndMessages.showError(this, "El paciente '" + match.getLastname().toUpperCase() + ", " + match.getName() + "', DNI N°" + match.getDni()
                        + " ya se encuentra registrado con misma obra social y N° de afiliado.");
                this.txtfInsuranceNumber.setText("");
                this.txtfInsuranceNumber.grabFocus();
                return;
            }

            generateAntecedents();

            if (error.isEmpty()) {
                if (!generatePatient()) {
                    return;
                }
            } else {
                ValidationsAndMessages.showError(this, "Debe completar los siguientes campos obligatorios: \n" + error);
                return;
            }

            if (daoPatient.updatePatient(patient, previousDni)) {
                ValidationsAndMessages.showInfo(this, "Actualización Exitosa.");

                if (this.origin == 2) {
                    clinicalHistory.setPatient(patient);
                    clinicalHistory.fillFields(patient);
                    this.dispose();
                    clinicalHistory.setVisible(true);
                } else {
                    LinkedList<Patient> pacienteList = new LinkedList<>();
                    pacienteList.add(daoPatient.getBasicPatient(patient.getDni()));
                    principalParent.updatePatientList(pacienteList);
                }
            } else {
                ValidationsAndMessages.showError(this, "Actualización Fallida.");
            }
        }
        if (origin == 1) {
            changeFieldsState(false);
            this.btnModify.setEnabled(true);
            this.btnSave.setEnabled(false);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        setCustomFont(btnBack, true);
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        setCustomFont(btnBack, false);
    }//GEN-LAST:event_btnBackMouseExited

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.exit();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnModifyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyMouseEntered
        setCustomFont(btnModify, true);
    }//GEN-LAST:event_btnModifyMouseEntered

    private void btnModifyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyMouseExited
        setCustomFont(btnModify, false);
    }//GEN-LAST:event_btnModifyMouseExited

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        this.changeFieldsState(true);
        this.btnModify.setEnabled(false);
        this.btnSave.setEnabled(true);
    }//GEN-LAST:event_btnModifyActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.exit();
    }//GEN-LAST:event_formWindowClosing

    private void txtfAddressKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfAddressKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfAddressKeyTyped

    private void txtfCityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfCityKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfCityKeyTyped

    private void txtaPersonalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaPersonalKeyPressed
        handleFocus(evt);
    }//GEN-LAST:event_txtaPersonalKeyPressed

    private void txtaToxicsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaToxicsKeyPressed
        handleFocus(evt);
    }//GEN-LAST:event_txtaToxicsKeyPressed

    private void txtaSurgicalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaSurgicalKeyPressed
        handleFocus(evt);
    }//GEN-LAST:event_txtaSurgicalKeyPressed

    private void txtaFamilyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaFamilyKeyPressed
        handleFocus(evt);
    }//GEN-LAST:event_txtaFamilyKeyPressed

    private void txtaPharmacologicalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaPharmacologicalKeyPressed
        handleFocus(evt);
    }//GEN-LAST:event_txtaPharmacologicalKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancelPPHealthInsurance;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnNewPPHealthInsurance;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSavePPHealthInsurance;
    private javax.swing.ButtonGroup btngrpSangre;
    private javax.swing.JComboBox cmbPPHealthInsurance;
    private javax.swing.JFormattedTextField ftxtfBirthday;
    private javax.swing.JFormattedTextField ftxtfFirstVisitDate;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblstaticAddress;
    private javax.swing.JLabel lblstaticBirthday;
    private javax.swing.JLabel lblstaticCity;
    private javax.swing.JLabel lblstaticDni;
    private javax.swing.JLabel lblstaticFirstVisitDate;
    private javax.swing.JLabel lblstaticInsuranceNumber;
    private javax.swing.JLabel lblstaticLastNames;
    private javax.swing.JLabel lblstaticNames;
    private javax.swing.JLabel lblstaticPhone;
    private javax.swing.JPanel pnlAntecedents;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlDatosPersonales;
    private javax.swing.JPanel pnlFamily;
    private javax.swing.JPanel pnlOtherData;
    private javax.swing.JPanel pnlPPHealthInsurance;
    private javax.swing.JPanel pnlPersonal;
    private javax.swing.JPanel pnlPharmacological;
    private javax.swing.JPanel pnlSurgical;
    private javax.swing.JPanel pnlToxics;
    private javax.swing.JTextArea txtaFamily;
    private javax.swing.JTextArea txtaPersonal;
    private javax.swing.JTextArea txtaPharmacological;
    private javax.swing.JTextArea txtaSurgical;
    private javax.swing.JTextArea txtaToxics;
    private javax.swing.JTextField txtfAddress;
    private javax.swing.JTextField txtfCity;
    private javax.swing.JTextField txtfDni;
    private javax.swing.JTextField txtfInsuranceNumber;
    private javax.swing.JTextField txtfLastNames;
    private javax.swing.JTextField txtfNames;
    private javax.swing.JTextField txtfNewPPHealthInsurance;
    private javax.swing.JTextField txtfPhone;
    // End of variables declaration//GEN-END:variables

    /**
     * Generates patient object that is going to be saved in the database
     */
    private boolean generatePatient() {
        String error;
        patient = new Patient();

        patient.setName(this.txtfNames.getText());
        patient.setLastname(this.txtfLastNames.getText());
        patient.setPhone(this.txtfPhone.getText());
        patient.setDni(Long.parseLong(this.txtfDni.getText()));
        patient.setAddress(this.txtfAddress.getText());
        patient.setCity(this.txtfCity.getText());

        try {
            String dia = this.ftxtfBirthday.getText(0, 2);
            String mes = this.ftxtfBirthday.getText(3, 2);
            String año = this.ftxtfBirthday.getText(6, 4);

            error = ValidationsAndMessages.corroborarFecha(dia, mes, año);

            if (!error.isEmpty()) {
                ValidationsAndMessages.showError(this, "Los siguientes valores de la fecha no son válidos o están fuera de rango: \n" + error);
                return false;
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(ABMPatient.class.getName()).log(Level.SEVERE, null, ex);
        }
        patient.setBirthday(this.ftxtfBirthday.getText());

        for (int i = 0; i < prePaidHealthInsurances.size(); i++) {
            if (prePaidHealthInsurances.get(i).getName().compareTo((String) cmbPPHealthInsurance.getSelectedItem()) == 0) {
                patient.setPrepaidHealthInsurance(prePaidHealthInsurances.get(i));
            }
        }
        patient.setPrepaidHealthInsuranceNumber(this.txtfInsuranceNumber.getText());
        patient.setAntecendents(antecedents);
        return true;
    }

    /**
     * Generates the patient's antecedents that are going to be saved in the
     * database
     */
    private void generateAntecedents() {
        antecedents = new Antecedents();

        antecedents.setPersonalAntecedents(this.txtaPersonal.getText());
        antecedents.setSurgicalAntecedents(this.txtaSurgical.getText());
        antecedents.setToxicAntecedents(this.txtaToxics.getText());
        antecedents.setFamilyAntecedents(this.txtaFamily.getText());
        antecedents.setPharmacologicalAntecedents(this.txtaPharmacological.getText());
    }

    /**
     * Validates that mandatory fields are filled(name, lastname, phone, dni, 
     * birthday, insurance number).
     */
    private String validateMandatoryFields() {
        String incomplete = "";

        if (this.txtfNames.getText().isEmpty()) {
            incomplete += "Nombres \n";
        }

        if (this.txtfLastNames.getText().isEmpty()) {
            incomplete += "Apellidos \n";
        }

        if (this.txtfPhone.getText().isEmpty()) {
            incomplete += "Teléfono \n";
        }

        if (this.txtfDni.getText().isEmpty()) {
            incomplete += "Nro. de Documento \n";
        }

        if (this.ftxtfBirthday.getText().compareTo("  /  /    ") == 0) {
            incomplete += "Fecha de Nacimiento \n";
        }

        if (this.txtfInsuranceNumber.getText().isEmpty() && this.txtfInsuranceNumber.isEnabled()) {
            incomplete += "Nro. de Afiliado \n";
        }

        return incomplete;
    }

    /**
     * Method used to fill all the data
     */
    private void fillFields(Patient p) {
        this.txtfNames.setText(p.getName());
        this.txtfLastNames.setText(p.getLastname());
        this.txtfPhone.setText(p.getPhone());
        this.txtfAddress.setText(p.getAddress());
        this.txtfCity.setText(p.getCity());
        this.txtfDni.setText(p.getDni() + "");
        this.ftxtfBirthday.setText(p.getBirthday());
        this.cmbPPHealthInsurance.setSelectedItem(p.getPrepaidHealthInsurance().getName());
        this.txtfInsuranceNumber.setText(p.getPrepaidHealthInsuranceNumber());

        this.txtaPersonal.setText(p.getAntecendents().getPersonalAntecedents());
        this.txtaToxics.setText(p.getAntecendents().getToxicAntecedents());
        this.txtaSurgical.setText(p.getAntecendents().getSurgicalAntecedents());
        this.txtaFamily.setText(p.getAntecendents().getFamilyAntecedents());
        this.txtaPharmacological.setText(p.getAntecendents().getPharmacologicalAntecedents());
    }

    /**
     * Method used to fill all the pre paid health insurances.
     */
    private void fillPrePaidHealthInsurances() {
        prePaidHealthInsurances = new LinkedList<>();
        cmbPPHealthInsurance.removeAllItems();
        prePaidHealthInsurances.add(new PrePaidHealthInsurance(0, "Sin Obra Social"));
        prePaidHealthInsurances.addAll(daoPrePaidHealthInsurance.getAllPrePaidHealthInsurances());
        for (int i = 0; i < prePaidHealthInsurances.size(); i++) {
            cmbPPHealthInsurance.addItem(prePaidHealthInsurances.get(i).getName());
        }
        cmbPPHealthInsurance.setSelectedIndex(0);
    }

    /**
     * Method used to clean fields.
     */
    private void cleanFields() {

        this.txtfNames.setText("");
        this.txtfLastNames.setText("");
        this.txtfPhone.setText("");
        this.txtfDni.setText("");
        this.txtfAddress.setText("");
        this.txtfCity.setText("");
        this.ftxtfBirthday.setText("");
        this.cmbPPHealthInsurance.setSelectedIndex(0);
        this.txtfInsuranceNumber.setText("");
        this.txtaPersonal.setText("");
        this.txtaToxics.setText("");
        this.txtaSurgical.setText("");
    }

    /**
     * Method used to change the fields state(enabled, disabled).
     *
     * @param state true (enabled), false (disabled)
     */
    private void changeFieldsState(boolean state) {
        this.txtfNames.setEditable(state);
        this.txtfLastNames.setEditable(state);
        this.txtfPhone.setEditable(state);
        this.txtfDni.setEditable(state);
        this.ftxtfBirthday.setEditable(state);
        this.txtfInsuranceNumber.setEditable(state);
        this.txtaPersonal.setEditable(state);
        this.txtaToxics.setEditable(state);
        this.txtaSurgical.setEditable(state);
        this.cmbPPHealthInsurance.setEditable(!state);

        this.cmbPPHealthInsurance.setEnabled(state);

        this.txtfNames.setFocusable(state);
        this.txtfLastNames.setFocusable(state);
        this.txtfPhone.setFocusable(state);
        this.txtfDni.setFocusable(state);
        this.ftxtfBirthday.setFocusable(state);
        this.txtfInsuranceNumber.setFocusable(state);
        this.txtaPersonal.setFocusable(state);
        this.txtaToxics.setFocusable(state);
        this.txtaSurgical.setFocusable(state);

        this.btnNewPPHealthInsurance.setEnabled(state);
    }

    /**
     * Method used to retrieve the system image.
     * @return 
     */
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/sistema.png"));
        return retValue;
    }

    /**
     * Method used to close de windows making all the needed validations.
     */
    private void exit() {
        if (!btnSave.isEnabled()) {
            this.dispose();
            if (this.getParent() != null) {
                this.getParent().setVisible(true);
            }
            if (clinicalHistory != null) {
                clinicalHistory.closeChild(this);
            } else {
                principalParent.closeChild(this);
            }
        } else {
            ValidationsAndMessages.validarSalidaVentana(this);
        }
    }

    /**
     * Returns patient's dni
     *
     * @return patient's dni, -1 if it's a new patient not saved yet
     */
    public long getPatientDni() {
        if (patient != null) {
            return patient.getDni();
        }
        return -1;
    }
}
