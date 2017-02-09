package gui;

import GUI.*;
import ClasesBase.Visit;
import Utils.StyleManager;
import Utils.MultiLineCellRenderer;
import ClasesBase.Patient;
import DAO.*;
import static Utils.Constants.BIRTHDAY_WITH_AGE;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static Utils.GeneralUtils.calculateAge;
import static Utils.GeneralUtils.changeTableSize;
import static Utils.GeneralUtils.clearTable;
import static Utils.Constants.FULLNAME;
import static Utils.Constants.SYSTEM_ICON_IMAGE_PATH;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static Utils.GeneralUtils.setButtonFontForPointerEvent;
import gui.PatientABM;
import gui.Principal;
import mvp.view.listener.PatientUpdatedListener;

public class ClinicalHistory extends javax.swing.JDialog implements PatientUpdatedListener {

    //STATIC VARS
    private static final String TABLE_COLUMN_DATE = "Fecha";
    private static final String TABLE_COLUMN_REASON = "Motivo";
    private static final String TABLE_COLUMN_DIAGNOSIS = "Diagnóstico";

    //UI
    private final LinkedList<JFrame> openWindows;
    private final Principal principalParent;
    private DefaultTableModel visitsDtm;
    private AntecedentsDialog antecedents;

    //DATA
    private final DAOPatient patientDao;
    private final DAOVisit visitDao;
    private final DAOAntecedents antecedentsDao;

    //MODELS
    private LinkedList<Visit> visits;
    private Patient patient;

    //OTHER VARS
    private boolean antecedentsModified;

    public ClinicalHistory(java.awt.Frame parent, Patient patient) {
        super(parent, true);

        patientDao = new DAOPatient();
        visitDao = new DAOVisit();
        antecedentsDao = new DAOAntecedents();
        openWindows = new LinkedList<>();
        antecedentsModified = false;
        this.patient = patient;
        principalParent = (Principal) parent;

        initComponents();
        setupInitialUI();
    }

    @Override
    public JRootPane getRootPane() {
        super.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            exit();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        return super.getRootPane();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Method used to fill all the fields for the clinical history of a certain
     * patient.
     */
    public void fillFields() {
        this.lblPatientName.setText(String.format(FULLNAME, patient.getLastname(), patient.getName()));
        this.lblDni.setText(patient.getDniType().getName() + " - " + patient.getDni() + "");

        if (patient.getBirthday() != null && !patient.getBirthday().isEmpty()) {
            this.lblBirthday.setText(String.format(BIRTHDAY_WITH_AGE, patient.getBirthday(), calculateAge(patient.getBirthday())));
        }
        if (patient.getCity() == null || patient.getCity().isEmpty()) {
            this.lblCity.setText("-");
        } else {
            this.lblCity.setText(patient.getCity());
        }
        if (patient.getPhone() == null || patient.getPhone().isEmpty()) {
            this.lblPhone.setText("-");
        } else {
            this.lblPhone.setText(patient.getPhone());
        }
        if (patient.getFirstVisitDate()== null || patient.getFirstVisitDate().isEmpty()) {
            this.lblFirstVisitDate.setText("-");
        } else {
            this.lblFirstVisitDate.setText(patient.getFirstVisitDate());
        }
        if (patient.getAddress() == null || patient.getAddress().isEmpty()) {
            this.lblAddress.setText("-");
        } else {
            this.lblAddress.setText(patient.getAddress());
        }
        this.lblMedicalCoverage.setText(patient.getMedicalCoverage().getName());
        if (patient.getMedicalCoverageNumber() == null || patient.getMedicalCoverageNumber().isEmpty()) {
            this.lblMedicalCoverageNumber.setText("-");
        } else {
            this.lblMedicalCoverageNumber.setText(patient.getMedicalCoverageNumber());
        }
    }

    /**
     * Method used to fill the visits table
     *
     * @param dni patient's dni
     */
    public void fillTable(long dni) {
//        Object[] o;
//        visitsDtm = (DefaultTableModel) this.tblVisits.getModel();
//        clearTable(visitsDtm);
//        visits = new LinkedList<>();
//        visits = visitDao.getAllPatientVisits(dni);
//
//        if (visits.isEmpty()) {
//            changeTableSize(visitsDtm, 8);
//        } else {
//            changeTableSize(visitsDtm, 0);
//        }
//
//        for (int i = 0; i < visits.size(); i++) {
//            o = new Object[3];
//            o[0] = visits.get(i).getDate();
//            o[1] = visits.get(i).getReason();
//            o[2] = visits.get(i).getDiagnosis();
//            visitsDtm.addRow(o);
//        }
//        tblVisits.changeSelection(0, 0, false, false);
//
//        tblVisits.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent me) {
//                JTable table = (JTable) me.getSource();
//                Point p = me.getPoint();
//                int row = table.rowAtPoint(p);
//                if (me.getClickCount() == 2 && row != -1
//                        && ClinicalHistory.this.visits.size() > 0
//                        && ClinicalHistory.this.visits.get(row) != null) {
//                    for (JFrame aux : openWindows) {
//                        if (aux instanceof ABMVisit) {
//                            ABMVisit auxCons = (ABMVisit) aux;
//                            if (auxCons.getVisitId() == ClinicalHistory.this.visits.get(row).getId()) {
//                                aux.setVisible(true);
//                                return;
//                            }
//                        }
//                    }
//                    Visit visit = visitDao.getFullVisit(ClinicalHistory.this.visits.get(row).getId(), patient.getDni());
////                    ABMVisit abmVisit = new ABMVisit(ClinicalHistory.this, patient, visit);
////                    abmVisit.setVisible(true);
////                    abmVisit.requestFocus();
////                    openWindows.add(abmVisit);
//                }
//            }
//        });
    }

    /**
     * Communicates color changes in the GUI to those frames that has this one
     * as a parent.
     *
     * @param color New color
     */
    public void paintChilds(int color) {
        openWindows.stream().forEach((window) -> {
            Utils.StyleManager.paint(window, color);
        });
        if (antecedents != null) {
            Utils.StyleManager.paint(antecedents, color);
        }
    }

    /**
     * Updates child when some of them is closed
     *
     * @param closedWindow
     */
    public void closeChild(JFrame closedWindow) {
        if (closedWindow != null) {
            openWindows.remove(closedWindow);
        }
    }

    private void closeOpenWindows() {
        for (JFrame window : openWindows) {
            window.dispose();
        }
        openWindows.clear();
    }

    private void exit() {
        principalParent.setVisible(true);
        dispose();
    }

    private void setupInitialUI() {
        btnModifyPatient.grabFocus();
        fillFields();
        tblVisits.getColumn(TABLE_COLUMN_DATE).setMaxWidth(120);
        tblVisits.getColumn(TABLE_COLUMN_DATE).setResizable(false);
        tblVisits.getColumn(TABLE_COLUMN_REASON).setResizable(false);
        tblVisits.getColumn(TABLE_COLUMN_DIAGNOSIS).setResizable(false);
        tblVisits.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        tblVisits.setDefaultRenderer(String.class, new MultiLineCellRenderer());
        tblVisits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        fillTable(patient);
        pnlNameLastname.setBackground(StyleManager.getSecondaryColor(StyleManager.actualColor));
        StyleManager.paint(this);
        setLocationRelativeTo(principalParent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlVisits = new javax.swing.JPanel();
        scrollPaneVisits = new javax.swing.JScrollPane();
        tblVisits = new javax.swing.JTable();
        pnlPatientData = new javax.swing.JPanel();
        lblsDni = new javax.swing.JLabel();
        lblsInsuranceNumber = new javax.swing.JLabel();
        lblsPPHealthInsurance = new javax.swing.JLabel();
        lblsBirthday = new javax.swing.JLabel();
        lblDni = new javax.swing.JLabel();
        lblBirthday = new javax.swing.JLabel();
        lblMedicalCoverageNumber = new javax.swing.JLabel();
        lblMedicalCoverage = new javax.swing.JLabel();
        lblsFirstVisitDate = new javax.swing.JLabel();
        btnModifyPatient = new javax.swing.JButton();
        lblPhone = new javax.swing.JLabel();
        lblsPhone = new javax.swing.JLabel();
        pnlNameLastname = new javax.swing.JPanel();
        lblPatientName = new javax.swing.JLabel();
        lblsPatientName = new javax.swing.JLabel();
        lblsCity = new javax.swing.JLabel();
        lblCity = new javax.swing.JLabel();
        btnAntecedents = new javax.swing.JButton();
        lblFirstVisitDate = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblsAddress = new javax.swing.JLabel();
        pnlButtons = new javax.swing.JPanel();
        btnNewVisit = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Historia Clínica");
        setIconImage(null);
        setMinimumSize(new java.awt.Dimension(850, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlVisits.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Consultas Anteriores", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        tblVisits.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblVisits.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Fecha", "Motivo", "Diagnóstico"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVisits.getTableHeader().setReorderingAllowed(false);
        scrollPaneVisits.setViewportView(tblVisits);
        if (tblVisits.getColumnModel().getColumnCount() > 0) {
            tblVisits.getColumnModel().getColumn(0).setMinWidth(100);
            tblVisits.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblVisits.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        javax.swing.GroupLayout pnlVisitsLayout = new javax.swing.GroupLayout(pnlVisits);
        pnlVisits.setLayout(pnlVisitsLayout);
        pnlVisitsLayout.setHorizontalGroup(
            pnlVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVisitsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneVisits, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlVisitsLayout.setVerticalGroup(
            pnlVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVisitsLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(scrollPaneVisits, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlPatientData.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Datos del Paciente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlPatientData.setForeground(new java.awt.Color(204, 204, 255));

        lblsDni.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsDni.setText("Nro. Documento:");

        lblsInsuranceNumber.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsInsuranceNumber.setText("Nro. Afiliado:");

        lblsPPHealthInsurance.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsPPHealthInsurance.setText("Obra Social:");

        lblsBirthday.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsBirthday.setText("Fecha de Nacimiento:");

        lblDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDni.setText("35953232");

        lblBirthday.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBirthday.setText("21/03/1991");

        lblMedicalCoverageNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMedicalCoverageNumber.setText("3-4534543-2");

        lblMedicalCoverage.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMedicalCoverage.setText("OSPAC");

        lblsFirstVisitDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsFirstVisitDate.setText("Fecha Primera Consulta:");

        btnModifyPatient.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnModifyPatient.setForeground(new java.awt.Color(0, 51, 102));
        btnModifyPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_enabled.png"))); // NOI18N
        btnModifyPatient.setText("Modificar");
        btnModifyPatient.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnModifyPatient.setContentAreaFilled(false);
        btnModifyPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModifyPatient.setOpaque(true);
        btnModifyPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModifyPatientMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModifyPatientMouseEntered(evt);
            }
        });
        btnModifyPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyPatientActionPerformed(evt);
            }
        });

        lblPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPhone.setText("423654");

        lblsPhone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsPhone.setText("Teléfono:");

        pnlNameLastname.setBackground(new java.awt.Color(228, 228, 241));
        pnlNameLastname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(153, 153, 153), new java.awt.Color(255, 255, 255), null));

        lblPatientName.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        lblPatientName.setForeground(new java.awt.Color(0, 51, 102));
        lblPatientName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblPatientName.setText("Lopez, Juan Carlos");
        lblPatientName.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblsPatientName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsPatientName.setText("Apellido y Nombre:");

        javax.swing.GroupLayout pnlNameLastnameLayout = new javax.swing.GroupLayout(pnlNameLastname);
        pnlNameLastname.setLayout(pnlNameLastnameLayout);
        pnlNameLastnameLayout.setHorizontalGroup(
            pnlNameLastnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLastnameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblsPatientName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPatientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNameLastnameLayout.setVerticalGroup(
            pnlNameLastnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLastnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblPatientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblsPatientName, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
        );

        lblsCity.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsCity.setText("Localidad:");

        lblCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCity.setText("0");

        btnAntecedents.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAntecedents.setForeground(new java.awt.Color(0, 51, 102));
        btnAntecedents.setText("Antecendentes");
        btnAntecedents.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAntecedents.setContentAreaFilled(false);
        btnAntecedents.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAntecedents.setOpaque(true);
        btnAntecedents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAntecedentsMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAntecedentsMouseEntered(evt);
            }
        });
        btnAntecedents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntecedentsActionPerformed(evt);
            }
        });

        lblFirstVisitDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFirstVisitDate.setText("21/03/1991");

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAddress.setText("0");

        lblsAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblsAddress.setText("Domicilio:");

        javax.swing.GroupLayout pnlPatientDataLayout = new javax.swing.GroupLayout(pnlPatientData);
        pnlPatientData.setLayout(pnlPatientDataLayout);
        pnlPatientDataLayout.setHorizontalGroup(
            pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatientDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblsCity)
                            .addComponent(lblsBirthday)
                            .addComponent(lblsDni)
                            .addComponent(btnModifyPatient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPatientDataLayout.createSequentialGroup()
                                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                    .addComponent(lblCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblsFirstVisitDate)
                                    .addComponent(lblsPhone)
                                    .addComponent(lblsAddress))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblFirstVisitDate, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblsInsuranceNumber)
                                            .addComponent(lblsPPHealthInsurance))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblMedicalCoverageNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                            .addComponent(lblMedicalCoverage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                                        .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(pnlPatientDataLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAntecedents, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(pnlNameLastname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlPatientDataLayout.setVerticalGroup(
            pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatientDataLayout.createSequentialGroup()
                .addComponent(pnlNameLastname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPatientDataLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                            .addComponent(lblBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPatientDataLayout.createSequentialGroup()
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMedicalCoverage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblsPPHealthInsurance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblsPhone)
                                .addComponent(lblPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblsDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblsFirstVisitDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFirstVisitDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMedicalCoverageNumber)
                        .addComponent(lblsInsuranceNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsCity, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                            .addComponent(lblCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(17, 17, 17))
                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblsAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAddress))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAntecedents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModifyPatient, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnNewVisit.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNewVisit.setForeground(new java.awt.Color(0, 51, 102));
        btnNewVisit.setText("Nueva Consulta");
        btnNewVisit.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnNewVisit.setContentAreaFilled(false);
        btnNewVisit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNewVisit.setOpaque(true);
        btnNewVisit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNewVisitMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNewVisitMouseEntered(evt);
            }
        });
        btnNewVisit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewVisitActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnBack.setForeground(new java.awt.Color(0, 51, 102));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home_enabled.png"))); // NOI18N
        btnBack.setText("Volver");
        btnBack.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnBack.setContentAreaFilled(false);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBack.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBack.setOpaque(true);
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBackMouseExited(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPatientData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlVisits, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPatientData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlVisits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewVisitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewVisitActionPerformed
        for (JFrame aux : openWindows) {
            if (aux instanceof ABMVisit && ((ABMVisit) aux).getVisitId() == -1) {
                aux.setVisible(true);
                return;
            }
        }
//        ABMVisit abmVisit = new ABMVisit(this, patient);
//        openWindows.add(abmVisit);
//        abmVisit.setVisible(true);
//        abmVisit.requestFocus();
    }//GEN-LAST:event_btnNewVisitActionPerformed

    private void btnAntecedentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntecedentsActionPerformed
        if (!antecedentsModified) {
            patient.setAntecendents(antecedentsDao.getAntecedent(patient));
            antecedentsModified = true;
        }
//        antecedents = new AntecedentsDialog(this, true, patient);
        antecedents.setVisible(true);
    }//GEN-LAST:event_btnAntecedentsActionPerformed

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        setButtonFontForPointerEvent(btnBack, true);
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        setButtonFontForPointerEvent(btnBack, false);
    }//GEN-LAST:event_btnBackMouseExited

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        principalParent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnModifyPatientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyPatientMouseEntered
        setButtonFontForPointerEvent(btnModifyPatient, true);
    }//GEN-LAST:event_btnModifyPatientMouseEntered

    private void btnModifyPatientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyPatientMouseExited
        setButtonFontForPointerEvent(btnModifyPatient, false);
    }//GEN-LAST:event_btnModifyPatientMouseExited

    private void btnModifyPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyPatientActionPerformed
        patient = patientDao.getFullPatient(patient);
        if (antecedentsModified == false) {
            patient.setAntecendents(antecedentsDao.getAntecedent(patient));
            antecedentsModified = true;
        }
        PatientABM pacienteInterfaz = new PatientABM((java.awt.Frame) getParent(), this, patient);
        pacienteInterfaz.setVisible(true);
    }//GEN-LAST:event_btnModifyPatientActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
//        principalParent.closeChild(this);
        closeOpenWindows();
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void btnAntecedentsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAntecedentsMouseEntered
        setButtonFontForPointerEvent(btnAntecedents, true);
    }//GEN-LAST:event_btnAntecedentsMouseEntered

    private void btnAntecedentsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAntecedentsMouseExited
        setButtonFontForPointerEvent(btnAntecedents, false);
    }//GEN-LAST:event_btnAntecedentsMouseExited

    private void btnNewVisitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewVisitMouseEntered
        setButtonFontForPointerEvent(btnNewVisit, true);
    }//GEN-LAST:event_btnNewVisitMouseEntered

    private void btnNewVisitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewVisitMouseExited
        setButtonFontForPointerEvent(btnNewVisit, false);
    }//GEN-LAST:event_btnNewVisitMouseExited

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAntecedents;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnModifyPatient;
    private javax.swing.JButton btnNewVisit;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblFirstVisitDate;
    private javax.swing.JLabel lblMedicalCoverage;
    private javax.swing.JLabel lblMedicalCoverageNumber;
    private javax.swing.JLabel lblPatientName;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblsAddress;
    private javax.swing.JLabel lblsBirthday;
    private javax.swing.JLabel lblsCity;
    private javax.swing.JLabel lblsDni;
    private javax.swing.JLabel lblsFirstVisitDate;
    private javax.swing.JLabel lblsInsuranceNumber;
    private javax.swing.JLabel lblsPPHealthInsurance;
    private javax.swing.JLabel lblsPatientName;
    private javax.swing.JLabel lblsPhone;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlNameLastname;
    private javax.swing.JPanel pnlPatientData;
    private javax.swing.JPanel pnlVisits;
    private javax.swing.JScrollPane scrollPaneVisits;
    private javax.swing.JTable tblVisits;
    // End of variables declaration//GEN-END:variables

    @Override
    public void patientUpdated(Patient patient) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
