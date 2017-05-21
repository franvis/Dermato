package gui;

import bussines.Antecedents;
import bussines.DniType;
import bussines.MedicalCoverage;
import bussines.Patient;
import static utils.GeneralUtils.handleFocus;
import utils.StyleManager;
import utils.ValidationsAndMessages;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import static utils.GeneralUtils.setButtonFontForPointerEvent;
import static utils.ValidationsAndMessages.BIRTHDAY_DATE_FORMAT_ERROR;
import static utils.ValidationsAndMessages.FIRST_VISIT_DATE_FORMAT_ERROR;
import static utils.ValidationsAndMessages.MANDATORY_FIELDS_ERROR;
import static utils.ValidationsAndMessages.PRE_PAID_HEALTH_INSURANCE_NAME_EMPTY;
import java.awt.Frame;
import java.util.Date;
import java.util.List;
import mvp.presenter.PatientABMPresenter;
import mvp.view.PatientABMView;
import mvp.view.listener.PatientUpdatedListener;
import utils.GeneralUtils;

public class PatientJDialog extends JDialog implements PatientABMView {

    private static final String DATE_MASK = "  /  /    ";

    private final PatientABMPresenter presenter;
    private PatientUpdatedListener patientUpdatedListener;

    /**
     * Creates new form DatosPaciente to register a new patient.
     *
     * @param parent parent frame where the new creation call is being made
     */
    public PatientJDialog(Frame parent) {
        super(parent, true);
        presenter = new PatientABMPresenter(this);

        initComponents();
        setupInitialUI();
        btnModify.setVisible(false);
    }

    /**
     * Creates new form DatosPaciente to modify a certain patient.
     *
     * @param parent parent frame where the new creation call is being made
     * @param patientUpdatedListener listener for the patient update.
     * @param patient patient to modify.
     */
    public PatientJDialog(Frame parent,
            PatientUpdatedListener patientUpdatedListener, Patient patient) {
        super(parent, true);
        this.patientUpdatedListener = patientUpdatedListener;
        presenter = new PatientABMPresenter(this);

        initComponents();
        setupInitialUI();
        btnModify.setEnabled(false);
        presenter.loadPatientData(patient);
    }

    /**
     * Generates patient object that is going to be saved in the database.
     *
     * @return Patient if correctly generated, null if an error occurred
     */
    private Patient generatePatient() {
        String error;
        Patient patient = new Patient();

        patient.setName(txtfNames.getText());
        patient.setLastname(txtfLastNames.getText());
        patient.setDni(txtfDni.getText());
        patient.setPhone(txtfPhone.getText());
        patient.setAddress(txtfAddress.getText());
        patient.setCity(txtfCity.getText());

        String birthday = ftxtfBirthday.getText();
        String firstVisitDate = ftxtfFirstVisitDate.getText();

        if (!firstVisitDate.isEmpty() && firstVisitDate.compareTo(DATE_MASK) != 0
                && !birthday.isEmpty() && birthday.compareTo(DATE_MASK) != 0) {
            Date birthdayDate = GeneralUtils.stringDateParser(birthday);
            Date firstVisitDateDate = GeneralUtils.stringDateParser(firstVisitDate);
            if (firstVisitDateDate.before(birthdayDate)) {
                showErrorMessage("La fecha de primera visita no puede ser menor a la fecha de nacimiento.");
                return null;
            }
        }

        if (!birthday.isEmpty() && birthday.compareTo(DATE_MASK) != 0) {
            error = ValidationsAndMessages.validateDateInCommonRange(ftxtfBirthday.getText());
            if (!error.isEmpty()) {
                showErrorMessage(String.format(BIRTHDAY_DATE_FORMAT_ERROR, error));
                return null;
            } else {
                patient.setBirthday(this.ftxtfBirthday.getText());
            }
        }

        if (!firstVisitDate.isEmpty() && firstVisitDate.compareTo(DATE_MASK) != 0) {
            error = ValidationsAndMessages.validateDateInCommonRange(ftxtfFirstVisitDate.getText());
            if (!error.isEmpty()) {
                showErrorMessage(String.format(FIRST_VISIT_DATE_FORMAT_ERROR, error));
                return null;
            } else {
                patient.setFirstVisitDate(this.ftxtfFirstVisitDate.getText());
            }
        }

        patient.setMedicalCoverageNumber(this.txtfMedicalCoverageNumber.getText());
        generateAntecedents(patient);

        return patient;
    }

    /**
     * Generates the patient's antecedents that are going to be saved in the
     * database.
     */
    private void generateAntecedents(Patient patient) {
        Antecedents antecedents = new Antecedents();

        antecedents.setPersonalAntecedents(this.txtaPersonal.getText());
        antecedents.setSurgicalAntecedents(this.txtaSurgical.getText());
        antecedents.setToxicAntecedents(this.txtaToxics.getText());
        antecedents.setFamilyAntecedents(this.txtaFamily.getText());
        antecedents.setPharmacologicalAntecedents(this.txtaPharmacological.getText());

        patient.setAntecedents(antecedents);
    }

    /**
     * Validates that mandatory fields are filled(name, last name, dni, dni
     * type).
     */
    private boolean validateMandatoryFields() {
        String incomplete = "";

        if (this.txtfNames.getText().isEmpty()) {
            incomplete += "Nombres \n";
        }

        if (this.txtfLastNames.getText().isEmpty()) {
            incomplete += "Apellidos \n";
        }

        if (this.txtfDni.getText().isEmpty()) {
            incomplete += "Nro. de Documento \n";
        }

        if (!incomplete.isEmpty()) {
            showErrorMessage(String.format(MANDATORY_FIELDS_ERROR, incomplete));
            return false;
        } else if (cmbMedicalCoverage.getSelectedIndex() != 0 && txtfMedicalCoverageNumber.getText().isEmpty()) {
            showErrorMessage("Por favor ingrese el numero de afiliado para la obra social seleccionada.");
            return false;
        } else {
            return true;
        }
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
        this.cmbDniType.setEditable(!state);
        this.cmbDniType.setEnabled(state);
        this.txtfDni.setEditable(state);
        this.txtfAddress.setEditable(state);
        this.txtfCity.setEditable(state);
        this.ftxtfBirthday.setEditable(state);
        this.cmbMedicalCoverage.setEditable(!state);
        this.cmbMedicalCoverage.setEnabled(state);
        this.txtfMedicalCoverageNumber.setEditable(state);
        this.txtaPersonal.setEditable(state);
        this.txtaToxics.setEditable(state);
        this.txtaSurgical.setEditable(state);
        this.txtaPharmacological.setEditable(state);
        this.txtaFamily.setEditable(state);
        this.ftxtfFirstVisitDate.setEditable(state);

        this.txtfNames.setFocusable(state);
        this.txtfLastNames.setFocusable(state);
        this.txtfPhone.setFocusable(state);
        this.txtfDni.setFocusable(state);
        this.txtfAddress.setFocusable(state);
        this.txtfCity.setFocusable(state);
        this.ftxtfBirthday.setFocusable(state);
        this.txtfMedicalCoverageNumber.setFocusable(state);
        this.txtaPersonal.setFocusable(state);
        this.txtaToxics.setFocusable(state);
        this.txtaSurgical.setFocusable(state);
        this.txtaPharmacological.setFocusable(state);
        this.txtaFamily.setFocusable(state);
        this.ftxtfFirstVisitDate.setFocusable(state);

        this.btnNewMedicalCoverage.setEnabled(state);
    }

    private void setupInitialUI() {
        JTextField etf = (JTextField) cmbMedicalCoverage.getEditor()
                .getEditorComponent();
        etf.setDisabledTextColor(StyleManager.getTextColor());
        etf.setBackground(StyleManager.getThirdColor());

        txtfMedicalCoverageNumber.setDisabledTextColor(StyleManager.getTextColor());

        presenter.loadMedicalCoverages();
        presenter.loadDniTypes();
        setLocationRelativeTo(getParent());
    }

    @Override
    public void displayMedicalCoverages(java.util.List<MedicalCoverage> medicalCoverages) {
        cmbMedicalCoverage.removeAllItems();
        medicalCoverages.stream().forEach((medicalCoverage) -> {
            cmbMedicalCoverage.addItem(medicalCoverage.getName());
        });

        cmbMedicalCoverage.setSelectedIndex(0);
    }

    @Override
    public void displayPatientData(Patient patient) {
        txtfNames.setText(patient.getName());
        txtfLastNames.setText(patient.getLastname());
        txtfPhone.setText(patient.getPhone());
        txtfAddress.setText(patient.getAddress());
        txtfCity.setText(patient.getCity());
        if (patient.getDniType() != null) {
            cmbDniType.setSelectedItem(patient.getDniType().getName());
        }
        if (patient.getDni() != null && !patient.getDni().isEmpty()) {
            txtfDni.setText(patient.getDni() + "");
        }
        ftxtfBirthday.setText(patient.getBirthday());
        cmbMedicalCoverage.setSelectedItem(patient.getMedicalCoverage().getName());
        txtfMedicalCoverageNumber.setText(patient.getMedicalCoverageNumber());
        ftxtfFirstVisitDate.setText(patient.getFirstVisitDate());

        txtaPersonal.setText(patient.getAntecedents().getPersonalAntecedents());
        txtaToxics.setText(patient.getAntecedents().getToxicAntecedents());
        txtaSurgical.setText(patient.getAntecedents().getSurgicalAntecedents());
        txtaFamily.setText(patient.getAntecedents().getFamilyAntecedents());
        txtaPharmacological.setText(patient.getAntecedents().getPharmacologicalAntecedents());
    }

    @Override
    public void showErrorMessage(String error) {
        ValidationsAndMessages.showError(this, error);
    }

    @Override
    public void showInfoMessage(String info) {
        ValidationsAndMessages.showInfo(this, info);
    }

    @Override
    public void exitWindow() {
        if (!btnSave.isEnabled()) {
            dispose();
        } else {
            ValidationsAndMessages.validateWindowExit(this);
        }
    }

    @Override
    public void finishUpdatingPatient(Patient patient) {
        changeFieldsState(false);
        this.btnModify.setEnabled(true);
        this.btnSave.setEnabled(false);
        patientUpdatedListener.patientUpdated(patient);
    }

    @Override
    public void finishRegisteringPatient(Patient patient) {
        try {
            ClinicalHistoryJDialog clinicalHistory = new ClinicalHistoryJDialog((java.awt.Frame) getParent(), patient);
            dispose();
            clinicalHistory.setVisible(true);
        } catch (Exception ex) {
            showErrorMessage("El paciente se registro correctamente pero no se pudo crear la Historia Clinica.\nIntente ingresar buscando el paciente desde el panel principal.");
            dispose();
        }
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
        pnlPersonalData = new javax.swing.JPanel();
        lblsNames = new javax.swing.JLabel();
        lblsLastNames = new javax.swing.JLabel();
        lblsPhone = new javax.swing.JLabel();
        lblsBirthday = new javax.swing.JLabel();
        txtfNames = new javax.swing.JTextField();
        txtfLastNames = new javax.swing.JTextField();
        txtfPhone = new javax.swing.JTextField();
        lblsDniType = new javax.swing.JLabel();
        ftxtfBirthday = new javax.swing.JFormattedTextField();
        pnlMedicalCoverage = new javax.swing.JPanel();
        cmbMedicalCoverage = new javax.swing.JComboBox();
        btnNewMedicalCoverage = new javax.swing.JButton();
        lblsMedicalCoverageNumber = new javax.swing.JLabel();
        txtfMedicalCoverageNumber = new javax.swing.JTextField();
        btnSaveMedicalCoverage = new javax.swing.JButton();
        txtfNewMedicalCoverage = new javax.swing.JTextField();
        btnCancelMedicalCoverage = new javax.swing.JButton();
        lblsAddress = new javax.swing.JLabel();
        txtfAddress = new javax.swing.JTextField();
        lblsCity = new javax.swing.JLabel();
        txtfCity = new javax.swing.JTextField();
        lblsDni = new javax.swing.JLabel();
        txtfDni = new javax.swing.JTextField();
        cmbDniType = new javax.swing.JComboBox();
        pnlButtons = new javax.swing.JPanel();
        btnModify = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        pnlReferences = new javax.swing.JPanel();
        lblsMandatoryFieldsReference = new javax.swing.JLabel();
        pnlAntecedents = new javax.swing.JPanel();
        pnlPersonal = new javax.swing.JPanel();
        scrollPanePersonal = new javax.swing.JScrollPane();
        txtaPersonal = new javax.swing.JTextArea();
        pnlSurgical = new javax.swing.JPanel();
        scrollPaneSurgical = new javax.swing.JScrollPane();
        txtaSurgical = new javax.swing.JTextArea();
        pnlToxics = new javax.swing.JPanel();
        scrollPaneToxics = new javax.swing.JScrollPane();
        txtaToxics = new javax.swing.JTextArea();
        pnlFamily = new javax.swing.JPanel();
        scrollPaneFamily = new javax.swing.JScrollPane();
        txtaFamily = new javax.swing.JTextArea();
        pnlPharmacological = new javax.swing.JPanel();
        scrollPanePharmacological = new javax.swing.JScrollPane();
        txtaPharmacological = new javax.swing.JTextArea();
        pnlOtherData = new javax.swing.JPanel();
        lblsFirstVisitDate = new javax.swing.JLabel();
        ftxtfFirstVisitDate = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Nuevo Paciente");
        setIconImages(getIconImages());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlPersonalData.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Datos Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        lblsNames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsNames.setText("(*) Nombres:");

        lblsLastNames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsLastNames.setText("(*) Apellidos:");

        lblsPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsPhone.setText("Teléfono:");

        lblsBirthday.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsBirthday.setText("Nacimiento:");

        txtfNames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfNames.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfNames.setNextFocusableComponent(txtfLastNames);
        txtfNames.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfNamesKeyTyped(evt);
            }
        });

        txtfLastNames.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfLastNames.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfLastNames.setNextFocusableComponent(txtfPhone);
        txtfLastNames.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfLastNamesKeyTyped(evt);
            }
        });

        txtfPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfPhone.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfPhone.setNextFocusableComponent(cmbDniType);
        txtfPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfPhoneKeyTyped(evt);
            }
        });

        lblsDniType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsDniType.setText("(*) Tipo Doc:");

        try {
            ftxtfBirthday.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtfBirthday.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ftxtfBirthday.setMargin(new java.awt.Insets(0, 2, 0, 0));

        pnlMedicalCoverage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Obra Social", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlMedicalCoverage.setOpaque(false);

        cmbMedicalCoverage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbMedicalCoverage.setFocusCycleRoot(true);
        cmbMedicalCoverage.setNextFocusableComponent(txtfMedicalCoverageNumber.isEnabled() ? txtfMedicalCoverageNumber : ftxtfFirstVisitDate);
        cmbMedicalCoverage.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMedicalCoverageItemStateChanged(evt);
            }
        });

        btnNewMedicalCoverage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnNewMedicalCoverage.setText("Nueva");
        btnNewMedicalCoverage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNewMedicalCoverage.setContentAreaFilled(false);
        btnNewMedicalCoverage.setNextFocusableComponent(txtfMedicalCoverageNumber);
        btnNewMedicalCoverage.setOpaque(true);
        btnNewMedicalCoverage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewMedicalCoverageActionPerformed(evt);
            }
        });

        lblsMedicalCoverageNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsMedicalCoverageNumber.setText("Nro. Afiliado:");

        txtfMedicalCoverageNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfMedicalCoverageNumber.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtfMedicalCoverageNumber.setEnabled(false);
        txtfMedicalCoverageNumber.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfMedicalCoverageNumber.setNextFocusableComponent(ftxtfFirstVisitDate);
        txtfMedicalCoverageNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfMedicalCoverageNumberKeyTyped(evt);
            }
        });

        btnSaveMedicalCoverage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSaveMedicalCoverage.setText("Guardar");
        btnSaveMedicalCoverage.setEnabled(false);
        btnSaveMedicalCoverage.setNextFocusableComponent(btnCancelMedicalCoverage);
        btnSaveMedicalCoverage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveMedicalCoverageActionPerformed(evt);
            }
        });

        txtfNewMedicalCoverage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfNewMedicalCoverage.setEnabled(false);
        txtfNewMedicalCoverage.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfNewMedicalCoverage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfNewMedicalCoverageKeyTyped(evt);
            }
        });

        btnCancelMedicalCoverage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelMedicalCoverage.setText("Cancelar");
        btnCancelMedicalCoverage.setEnabled(false);
        btnCancelMedicalCoverage.setNextFocusableComponent(cmbMedicalCoverage);
        btnCancelMedicalCoverage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelMedicalCoverageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMedicalCoverageLayout = new javax.swing.GroupLayout(pnlMedicalCoverage);
        pnlMedicalCoverage.setLayout(pnlMedicalCoverageLayout);
        pnlMedicalCoverageLayout.setHorizontalGroup(
            pnlMedicalCoverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMedicalCoverageLayout.createSequentialGroup()
                .addGroup(pnlMedicalCoverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMedicalCoverageLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlMedicalCoverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMedicalCoverageLayout.createSequentialGroup()
                                .addComponent(lblsMedicalCoverageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtfMedicalCoverageNumber))
                            .addGroup(pnlMedicalCoverageLayout.createSequentialGroup()
                                .addComponent(cmbMedicalCoverage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNewMedicalCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlMedicalCoverageLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtfNewMedicalCoverage))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMedicalCoverageLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelMedicalCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSaveMedicalCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlMedicalCoverageLayout.setVerticalGroup(
            pnlMedicalCoverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMedicalCoverageLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(pnlMedicalCoverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMedicalCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewMedicalCoverage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfNewMedicalCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMedicalCoverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelMedicalCoverage)
                    .addComponent(btnSaveMedicalCoverage))
                .addGap(13, 13, 13)
                .addGroup(pnlMedicalCoverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfMedicalCoverageNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblsMedicalCoverageNumber))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblsAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsAddress.setText("Domicilio:");

        txtfAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfAddress.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfAddress.setNextFocusableComponent(txtfCity);

        lblsCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsCity.setText("Localidad:");

        txtfCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfCity.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfCity.setNextFocusableComponent(ftxtfBirthday);

        lblsDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsDni.setText("(*) Nro. Doc.:");

        txtfDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfDni.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfDni.setNextFocusableComponent(txtfAddress);

        cmbDniType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cmbDniType.setFocusCycleRoot(true);
        cmbDniType.setNextFocusableComponent(txtfDni);

        javax.swing.GroupLayout pnlPersonalDataLayout = new javax.swing.GroupLayout(pnlPersonalData);
        pnlPersonalData.setLayout(pnlPersonalDataLayout);
        pnlPersonalDataLayout.setHorizontalGroup(
            pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMedicalCoverage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlPersonalDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblsBirthday)
                    .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblsNames, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblsLastNames, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblsDniType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblsPhone)
                        .addComponent(lblsAddress)
                        .addComponent(lblsCity)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtfLastNames, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtfPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPersonalDataLayout.createSequentialGroup()
                        .addComponent(cmbDniType, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblsDni)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtfDni, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                    .addComponent(txtfAddress)
                    .addComponent(txtfCity)
                    .addComponent(txtfNames)
                    .addGroup(pnlPersonalDataLayout.createSequentialGroup()
                        .addComponent(ftxtfBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPersonalDataLayout.setVerticalGroup(
            pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPersonalDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfNames)
                    .addComponent(lblsNames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfLastNames)
                    .addComponent(lblsLastNames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfPhone)
                    .addComponent(lblsPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblsDniType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtfDni)
                    .addComponent(lblsDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbDniType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfAddress)
                    .addComponent(lblsAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfCity)
                    .addComponent(lblsCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPersonalDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftxtfBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblsBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlMedicalCoverage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnModify.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModify.setForeground(new java.awt.Color(0, 51, 102));
        btnModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit_enabled.png"))); // NOI18N
        btnModify.setText("Modificar");
        btnModify.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnModify.setContentAreaFilled(false);
        btnModify.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModify.setEnabled(false);
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
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save_enabled.png"))); // NOI18N
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
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home_enabled.png"))); // NOI18N
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        pnlReferences.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Referencias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        lblsMandatoryFieldsReference.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsMandatoryFieldsReference.setText("(*) = Campo Obligatorio");

        javax.swing.GroupLayout pnlReferencesLayout = new javax.swing.GroupLayout(pnlReferences);
        pnlReferences.setLayout(pnlReferencesLayout);
        pnlReferencesLayout.setHorizontalGroup(
            pnlReferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReferencesLayout.createSequentialGroup()
                .addComponent(lblsMandatoryFieldsReference, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlReferencesLayout.setVerticalGroup(
            pnlReferencesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReferencesLayout.createSequentialGroup()
                .addComponent(lblsMandatoryFieldsReference)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        txtaPersonal.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtaPersonal.setNextFocusableComponent(txtaSurgical);
        txtaPersonal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaPersonalKeyPressed(evt);
            }
        });
        scrollPanePersonal.setViewportView(txtaPersonal);

        javax.swing.GroupLayout pnlPersonalLayout = new javax.swing.GroupLayout(pnlPersonal);
        pnlPersonal.setLayout(pnlPersonalLayout);
        pnlPersonalLayout.setHorizontalGroup(
            pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanePersonal, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
        );
        pnlPersonalLayout.setVerticalGroup(
            pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanePersonal)
        );

        pnlSurgical.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Quirúrgicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaSurgical.setColumns(20);
        txtaSurgical.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaSurgical.setLineWrap(true);
        txtaSurgical.setRows(5);
        txtaSurgical.setTabSize(0);
        txtaSurgical.setWrapStyleWord(true);
        txtaSurgical.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtaSurgical.setNextFocusableComponent(txtaToxics);
        txtaSurgical.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaSurgicalKeyPressed(evt);
            }
        });
        scrollPaneSurgical.setViewportView(txtaSurgical);

        javax.swing.GroupLayout pnlSurgicalLayout = new javax.swing.GroupLayout(pnlSurgical);
        pnlSurgical.setLayout(pnlSurgicalLayout);
        pnlSurgicalLayout.setHorizontalGroup(
            pnlSurgicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneSurgical)
        );
        pnlSurgicalLayout.setVerticalGroup(
            pnlSurgicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneSurgical)
        );

        pnlToxics.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Tóxicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaToxics.setColumns(20);
        txtaToxics.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaToxics.setLineWrap(true);
        txtaToxics.setRows(5);
        txtaToxics.setTabSize(0);
        txtaToxics.setWrapStyleWord(true);
        txtaToxics.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtaToxics.setNextFocusableComponent(txtaPharmacological);
        txtaToxics.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaToxicsKeyPressed(evt);
            }
        });
        scrollPaneToxics.setViewportView(txtaToxics);

        javax.swing.GroupLayout pnlToxicsLayout = new javax.swing.GroupLayout(pnlToxics);
        pnlToxics.setLayout(pnlToxicsLayout);
        pnlToxicsLayout.setHorizontalGroup(
            pnlToxicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneToxics)
        );
        pnlToxicsLayout.setVerticalGroup(
            pnlToxicsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneToxics, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pnlFamily.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Familiares", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaFamily.setColumns(20);
        txtaFamily.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaFamily.setLineWrap(true);
        txtaFamily.setRows(5);
        txtaFamily.setTabSize(0);
        txtaFamily.setWrapStyleWord(true);
        txtaFamily.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtaFamily.setNextFocusableComponent(txtfNames);
        txtaFamily.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaFamilyKeyPressed(evt);
            }
        });
        scrollPaneFamily.setViewportView(txtaFamily);

        javax.swing.GroupLayout pnlFamilyLayout = new javax.swing.GroupLayout(pnlFamily);
        pnlFamily.setLayout(pnlFamilyLayout);
        pnlFamilyLayout.setHorizontalGroup(
            pnlFamilyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneFamily)
        );
        pnlFamilyLayout.setVerticalGroup(
            pnlFamilyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneFamily, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pnlPharmacological.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Medicamentosos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaPharmacological.setColumns(20);
        txtaPharmacological.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaPharmacological.setLineWrap(true);
        txtaPharmacological.setRows(5);
        txtaPharmacological.setTabSize(0);
        txtaPharmacological.setWrapStyleWord(true);
        txtaPharmacological.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtaPharmacological.setNextFocusableComponent(txtaFamily);
        txtaPharmacological.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaPharmacologicalKeyPressed(evt);
            }
        });
        scrollPanePharmacological.setViewportView(txtaPharmacological);

        javax.swing.GroupLayout pnlPharmacologicalLayout = new javax.swing.GroupLayout(pnlPharmacological);
        pnlPharmacological.setLayout(pnlPharmacologicalLayout);
        pnlPharmacologicalLayout.setHorizontalGroup(
            pnlPharmacologicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanePharmacological)
        );
        pnlPharmacologicalLayout.setVerticalGroup(
            pnlPharmacologicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanePharmacological, javax.swing.GroupLayout.Alignment.TRAILING)
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
                .addComponent(pnlPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlSurgical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlToxics, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPharmacological, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFamily, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        pnlOtherData.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Otros Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        lblsFirstVisitDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsFirstVisitDate.setText("Fecha de Primera Consulta:");

        try {
            ftxtfFirstVisitDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtfFirstVisitDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ftxtfFirstVisitDate.setMargin(new java.awt.Insets(0, 2, 0, 0));
        ftxtfFirstVisitDate.setNextFocusableComponent(txtaPersonal);

        javax.swing.GroupLayout pnlOtherDataLayout = new javax.swing.GroupLayout(pnlOtherData);
        pnlOtherData.setLayout(pnlOtherDataLayout);
        pnlOtherDataLayout.setHorizontalGroup(
            pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblsFirstVisitDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ftxtfFirstVisitDate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlOtherDataLayout.setVerticalGroup(
            pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOtherDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ftxtfFirstVisitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblsFirstVisitDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlPersonalData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlReferences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlOtherData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(pnlAntecedents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlPersonalData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlOtherData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlReferences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlAntecedents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlReferences.getAccessibleContext().setAccessibleName("Otros datos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtfPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfPhoneKeyTyped
        ValidationsAndMessages.denyLetterCharacter(evt, this);
        ValidationsAndMessages.validateTextLength(this.txtfPhone, evt, 45, this);
    }//GEN-LAST:event_txtfPhoneKeyTyped

    private void txtfNamesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNamesKeyTyped
        ValidationsAndMessages.denyNumberCharacter(evt, this);
        ValidationsAndMessages.validateTextLength(this.txtfNames, evt, 45, this);
    }//GEN-LAST:event_txtfNamesKeyTyped

    private void txtfLastNamesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfLastNamesKeyTyped
        ValidationsAndMessages.denyNumberCharacter(evt, this);
        ValidationsAndMessages.validateTextLength(this.txtfLastNames, evt, 45, this);
    }//GEN-LAST:event_txtfLastNamesKeyTyped

    private void btnNewMedicalCoverageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewMedicalCoverageActionPerformed
        txtfNewMedicalCoverage.setEnabled(true);
        btnSaveMedicalCoverage.setEnabled(true);
        btnCancelMedicalCoverage.setEnabled(true);
        btnNewMedicalCoverage.setEnabled(false);
        cmbMedicalCoverage.setEnabled(false);
        txtfNewMedicalCoverage.requestFocus();
    }//GEN-LAST:event_btnNewMedicalCoverageActionPerformed

    private void txtfMedicalCoverageNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfMedicalCoverageNumberKeyTyped
        ValidationsAndMessages.validateTextLength(this.txtfMedicalCoverageNumber, evt, 45, this);
        ValidationsAndMessages.validateHealthInsuranceNumberCharacter(evt, this);
    }//GEN-LAST:event_txtfMedicalCoverageNumberKeyTyped

    private void btnSaveMedicalCoverageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveMedicalCoverageActionPerformed
        if (this.txtfNewMedicalCoverage.getText().isEmpty()) {
            ValidationsAndMessages.showError(this, PRE_PAID_HEALTH_INSURANCE_NAME_EMPTY);
            return;
        }

        String nuevaObra = this.txtfNewMedicalCoverage.getText();
        presenter.registerMedicalCoverage(new MedicalCoverage(0, nuevaObra));
    }//GEN-LAST:event_btnSaveMedicalCoverageActionPerformed

    private void txtfNewMedicalCoverageKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNewMedicalCoverageKeyTyped
        ValidationsAndMessages.validateTextLength(this.txtfNewMedicalCoverage, evt, 80, this);
    }//GEN-LAST:event_txtfNewMedicalCoverageKeyTyped

    private void btnCancelMedicalCoverageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelMedicalCoverageActionPerformed
        this.txtfNewMedicalCoverage.setText("");
        this.txtfNewMedicalCoverage.setEnabled(false);
        this.btnSaveMedicalCoverage.setEnabled(false);
        this.btnCancelMedicalCoverage.setEnabled(false);
        this.btnNewMedicalCoverage.setEnabled(true);
        this.cmbMedicalCoverage.setEnabled(true);
    }//GEN-LAST:event_btnCancelMedicalCoverageActionPerformed

private void cmbMedicalCoverageItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMedicalCoverageItemStateChanged
    if (cmbMedicalCoverage.getSelectedIndex() == 0) {
        txtfMedicalCoverageNumber.setText("");
        txtfMedicalCoverageNumber.setEnabled(false);
    } else {
        txtfMedicalCoverageNumber.setEnabled(true);
    }
}//GEN-LAST:event_cmbMedicalCoverageItemStateChanged

    private void btnSaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseEntered
        setButtonFontForPointerEvent(btnSave, true);
    }//GEN-LAST:event_btnSaveMouseEntered

    private void btnSaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseExited
        setButtonFontForPointerEvent(btnSave, false);
    }//GEN-LAST:event_btnSaveMouseExited

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!validateMandatoryFields()) {
            return;
        }

        Patient patient = generatePatient();

        if (patient == null) {
            return;
        }
        if (patientUpdatedListener == null) {
            presenter.registerPatient(patient, cmbMedicalCoverage.getSelectedIndex(),
                    cmbDniType.getSelectedIndex());
        } else {
            presenter.updatePatient(patient, cmbMedicalCoverage.getSelectedIndex(),
                    cmbDniType.getSelectedIndex());
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        setButtonFontForPointerEvent(btnBack, true);
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        setButtonFontForPointerEvent(btnBack, false);
    }//GEN-LAST:event_btnBackMouseExited

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        exitWindow();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnModifyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyMouseEntered
        setButtonFontForPointerEvent(btnModify, true);
    }//GEN-LAST:event_btnModifyMouseEntered

    private void btnModifyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyMouseExited
        setButtonFontForPointerEvent(btnModify, false);
    }//GEN-LAST:event_btnModifyMouseExited

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        this.changeFieldsState(true);
        this.btnModify.setEnabled(false);
        this.btnSave.setEnabled(true);
    }//GEN-LAST:event_btnModifyActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitWindow();
    }//GEN-LAST:event_formWindowClosing

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
    private javax.swing.JButton btnCancelMedicalCoverage;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnNewMedicalCoverage;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSaveMedicalCoverage;
    private javax.swing.ButtonGroup btngrpSangre;
    private javax.swing.JComboBox cmbDniType;
    private javax.swing.JComboBox cmbMedicalCoverage;
    private javax.swing.JFormattedTextField ftxtfBirthday;
    private javax.swing.JFormattedTextField ftxtfFirstVisitDate;
    private javax.swing.JLabel lblsAddress;
    private javax.swing.JLabel lblsBirthday;
    private javax.swing.JLabel lblsCity;
    private javax.swing.JLabel lblsDni;
    private javax.swing.JLabel lblsDniType;
    private javax.swing.JLabel lblsFirstVisitDate;
    private javax.swing.JLabel lblsLastNames;
    private javax.swing.JLabel lblsMandatoryFieldsReference;
    private javax.swing.JLabel lblsMedicalCoverageNumber;
    private javax.swing.JLabel lblsNames;
    private javax.swing.JLabel lblsPhone;
    private javax.swing.JPanel pnlAntecedents;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlFamily;
    private javax.swing.JPanel pnlMedicalCoverage;
    private javax.swing.JPanel pnlOtherData;
    private javax.swing.JPanel pnlPersonal;
    private javax.swing.JPanel pnlPersonalData;
    private javax.swing.JPanel pnlPharmacological;
    private javax.swing.JPanel pnlReferences;
    private javax.swing.JPanel pnlSurgical;
    private javax.swing.JPanel pnlToxics;
    private javax.swing.JScrollPane scrollPaneFamily;
    private javax.swing.JScrollPane scrollPanePersonal;
    private javax.swing.JScrollPane scrollPanePharmacological;
    private javax.swing.JScrollPane scrollPaneSurgical;
    private javax.swing.JScrollPane scrollPaneToxics;
    private javax.swing.JTextArea txtaFamily;
    private javax.swing.JTextArea txtaPersonal;
    private javax.swing.JTextArea txtaPharmacological;
    private javax.swing.JTextArea txtaSurgical;
    private javax.swing.JTextArea txtaToxics;
    private javax.swing.JTextField txtfAddress;
    private javax.swing.JTextField txtfCity;
    private javax.swing.JTextField txtfDni;
    private javax.swing.JTextField txtfLastNames;
    private javax.swing.JTextField txtfMedicalCoverageNumber;
    private javax.swing.JTextField txtfNames;
    private javax.swing.JTextField txtfNewMedicalCoverage;
    private javax.swing.JTextField txtfPhone;
    // End of variables declaration//GEN-END:variables

    @Override
    public JRootPane getRootPane() {
        super.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            exitWindow();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        return super.getRootPane();
    }

    @Override
    public void finishRegisteringMedicalCoverage(String medicalCoverageName) {
        this.txtfNewMedicalCoverage.setEnabled(false);
        this.btnSaveMedicalCoverage.setEnabled(false);
        this.btnNewMedicalCoverage.setEnabled(true);
        this.cmbMedicalCoverage.setEnabled(true);
        this.cmbMedicalCoverage.setSelectedItem(medicalCoverageName);
        this.txtfNewMedicalCoverage.setText("");
    }

    @Override
    public void displayDniTypes(List<DniType> dniTypes) {
        cmbDniType.removeAllItems();
        dniTypes.stream().forEach((dniType) -> {
            cmbDniType.addItem(dniType.getName());
        });

        cmbDniType.setSelectedIndex(0);
    }
}
