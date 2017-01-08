package GUI;

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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static Utils.GeneralUtils.calculateAge;
import static Utils.GeneralUtils.changeTableSize;
import static Utils.GeneralUtils.clearTable;
import static Utils.Constants.FULLNAME;

public class ClinicalHistory extends javax.swing.JFrame {

    private static final String TABLE_COLUMN_DATE = "Fecha";
    private static final String TABLE_COLUMN_REASON = "Motivo";
    private static final String TABLE_COLUMN_DIAGNOSIS = "Diagnóstico";

    private Principal principalParent;
    private final DAOPatient patientDao;
    private final DAOVisit visitDao;
    private LinkedList<Visit> visits;
    private final LinkedList<JFrame> openWindows;
    private final DAOAntecedents antecedentsDao;
    private DefaultTableModel visitsDtm;
    private AntecedentsDialog antecedents;
    private Patient patient;
    private boolean antecedentsModified;

    public ClinicalHistory(Component parent, Patient patient, int origin) {
        initComponents();
        antecedentsModified = false;
        patientDao = new DAOPatient();
        visitDao = new DAOVisit();
        antecedentsDao = new DAOAntecedents();
        openWindows = new LinkedList<>();
        this.patient = patient;
        this.btnModifyPatient.grabFocus();
        this.btnSeeVisit.setEnabled(false);
        fillFields(patient);
        tblVisits.getColumn(TABLE_COLUMN_DATE).setMaxWidth(100);
        tblVisits.getColumn(TABLE_COLUMN_DATE).setResizable(false);
        tblVisits.getColumn(TABLE_COLUMN_REASON).setResizable(false);
        tblVisits.getColumn(TABLE_COLUMN_DIAGNOSIS).setResizable(false);
        tblVisits.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 14));
        tblVisits.setDefaultRenderer(String.class, new MultiLineCellRenderer());
        tblVisits.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fillTable(patient.getDni());
        StyleManager.paint(this);
        pnlNameLastname.setBackground(StyleManager.getSecondaryColor(StyleManager.actualColor));
        principalParent = (Principal) parent;
        this.setExtendedState(principalParent.getExtendedState());
        this.setLocationRelativeTo(principalParent);
        //eventos de la página
        KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        this.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principalParent.setVisible(true);
                dispose();
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

        pnlVisits = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVisits = new javax.swing.JTable();
        btnSeeVisit = new javax.swing.JButton();
        pnlPatientData = new javax.swing.JPanel();
        lblstaticDni = new javax.swing.JLabel();
        lblstaticInsuranceNumber = new javax.swing.JLabel();
        lblstaticPPHealthInsurance = new javax.swing.JLabel();
        lblstaticBirthday = new javax.swing.JLabel();
        lblDni = new javax.swing.JLabel();
        lblBirthday = new javax.swing.JLabel();
        lblInsuranceNumber = new javax.swing.JLabel();
        lblPPHealthInsurance = new javax.swing.JLabel();
        lblstaticFirstVisitDate = new javax.swing.JLabel();
        btnModifyPatient = new javax.swing.JButton();
        lblPhone = new javax.swing.JLabel();
        lblstaticPhone = new javax.swing.JLabel();
        pnlNameLastname = new javax.swing.JPanel();
        lblNombrePaciente = new javax.swing.JLabel();
        lblstaticNombre = new javax.swing.JLabel();
        lblstaticCity = new javax.swing.JLabel();
        lblCity = new javax.swing.JLabel();
        btnAntecedents = new javax.swing.JButton();
        lblFirstVisitDate = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblstaticAddress = new javax.swing.JLabel();
        btnNewVisit = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Historia Clínica");
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(723, 530));
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
        jScrollPane1.setViewportView(tblVisits);
        if (tblVisits.getColumnModel().getColumnCount() > 0) {
            tblVisits.getColumnModel().getColumn(0).setMinWidth(80);
            tblVisits.getColumnModel().getColumn(0).setPreferredWidth(80);
            tblVisits.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        btnSeeVisit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSeeVisit.setForeground(new java.awt.Color(0, 51, 102));
        btnSeeVisit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ver_enabled.png"))); // NOI18N
        btnSeeVisit.setText("Ver");
        btnSeeVisit.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnSeeVisit.setContentAreaFilled(false);
        btnSeeVisit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSeeVisit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSeeVisit.setNextFocusableComponent(btnNewVisit);
        btnSeeVisit.setOpaque(true);
        btnSeeVisit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSeeVisitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSeeVisitMouseExited(evt);
            }
        });
        btnSeeVisit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeeVisitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlVisitsLayout = new javax.swing.GroupLayout(pnlVisits);
        pnlVisits.setLayout(pnlVisitsLayout);
        pnlVisitsLayout.setHorizontalGroup(
            pnlVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVisitsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(btnSeeVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnlVisitsLayout.setVerticalGroup(
            pnlVisitsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVisitsLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSeeVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlPatientData.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Datos del Paciente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N
        pnlPatientData.setForeground(new java.awt.Color(204, 204, 255));

        lblstaticDni.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticDni.setText("Nro. Documento:");

        lblstaticInsuranceNumber.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticInsuranceNumber.setText("Nro. Afiliado:");

        lblstaticPPHealthInsurance.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticPPHealthInsurance.setText("Obra Social:");

        lblstaticBirthday.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticBirthday.setText("Fecha de Nacimiento:");

        lblDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDni.setText("35953232");

        lblBirthday.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblBirthday.setText("21/03/1991");

        lblInsuranceNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblInsuranceNumber.setText("3-4534543-2");

        lblPPHealthInsurance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPPHealthInsurance.setText("OSPAC");

        lblstaticFirstVisitDate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticFirstVisitDate.setText("Fecha Primera Consulta:");

        btnModifyPatient.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModifyPatient.setForeground(new java.awt.Color(0, 51, 102));
        btnModifyPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_enabled.png"))); // NOI18N
        btnModifyPatient.setText("Modificar");
        btnModifyPatient.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnModifyPatient.setContentAreaFilled(false);
        btnModifyPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModifyPatient.setOpaque(true);
        btnModifyPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModifyPatientMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModifyPatientMouseExited(evt);
            }
        });
        btnModifyPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyPatientActionPerformed(evt);
            }
        });

        lblPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPhone.setText("423654");

        lblstaticPhone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticPhone.setText("Teléfono:");

        pnlNameLastname.setBackground(new java.awt.Color(228, 228, 241));
        pnlNameLastname.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(153, 153, 153), new java.awt.Color(255, 255, 255), null));

        lblNombrePaciente.setFont(new java.awt.Font("Tahoma", 3, 15)); // NOI18N
        lblNombrePaciente.setForeground(new java.awt.Color(0, 51, 102));
        lblNombrePaciente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNombrePaciente.setText("Lopez, Juan Carlos");
        lblNombrePaciente.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        lblstaticNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticNombre.setText("Apellido y Nombre:");

        javax.swing.GroupLayout pnlNameLastnameLayout = new javax.swing.GroupLayout(pnlNameLastname);
        pnlNameLastname.setLayout(pnlNameLastnameLayout);
        pnlNameLastnameLayout.setHorizontalGroup(
            pnlNameLastnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLastnameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblstaticNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombrePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlNameLastnameLayout.setVerticalGroup(
            pnlNameLastnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNameLastnameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblNombrePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblstaticNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
        );

        lblstaticCity.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticCity.setText("Localidad:");

        lblCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCity.setText("0");

        btnAntecedents.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnAntecedents.setForeground(new java.awt.Color(0, 51, 102));
        btnAntecedents.setText("Antecendentes");
        btnAntecedents.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnAntecedents.setContentAreaFilled(false);
        btnAntecedents.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAntecedents.setOpaque(true);
        btnAntecedents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntecedentsActionPerformed(evt);
            }
        });

        lblFirstVisitDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFirstVisitDate.setText("21/03/1991");

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAddress.setText("0");

        lblstaticAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblstaticAddress.setText("Domicilio:");

        javax.swing.GroupLayout pnlPatientDataLayout = new javax.swing.GroupLayout(pnlPatientData);
        pnlPatientData.setLayout(pnlPatientDataLayout);
        pnlPatientDataLayout.setHorizontalGroup(
            pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatientDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblstaticCity)
                            .addComponent(lblstaticBirthday)
                            .addComponent(lblstaticDni)
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
                                    .addComponent(lblstaticFirstVisitDate)
                                    .addComponent(lblstaticPhone)
                                    .addComponent(lblstaticAddress))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblFirstVisitDate, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblstaticInsuranceNumber)
                                            .addComponent(lblstaticPPHealthInsurance))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblInsuranceNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                                            .addComponent(lblPPHealthInsurance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                                        .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(pnlPatientDataLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAntecedents, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(pnlNameLastname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlPatientDataLayout.setVerticalGroup(
            pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatientDataLayout.createSequentialGroup()
                .addComponent(pnlNameLastname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPatientDataLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblstaticBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                            .addComponent(lblBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPatientDataLayout.createSequentialGroup()
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPPHealthInsurance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblstaticPPHealthInsurance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblstaticPhone)
                                .addComponent(lblPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblstaticDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblstaticFirstVisitDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFirstVisitDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblInsuranceNumber)
                        .addComponent(lblstaticInsuranceNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblstaticCity, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                            .addComponent(lblCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(17, 17, 17))
                    .addGroup(pnlPatientDataLayout.createSequentialGroup()
                        .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblstaticAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAddress))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(pnlPatientDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModifyPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAntecedents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9))
        );

        btnNewVisit.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNewVisit.setForeground(new java.awt.Color(0, 51, 102));
        btnNewVisit.setText("Nueva Consulta");
        btnNewVisit.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnNewVisit.setContentAreaFilled(false);
        btnNewVisit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNewVisit.setOpaque(true);
        btnNewVisit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewVisitActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPatientData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNewVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlVisits, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPatientData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlVisits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(473, 473, 473)
                        .addComponent(btnNewVisit, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
        ABMVisit abmVisit = new ABMVisit(this, patient.getDni(), patient);
        openWindows.add(abmVisit);
        abmVisit.setVisible(true);
        abmVisit.requestFocus();
    }//GEN-LAST:event_btnNewVisitActionPerformed

    private void btnAntecedentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntecedentsActionPerformed
        if (!antecedentsModified) {
            patient.setAntecendents(antecedentsDao.getAntecedent(patient.getDni()));
            antecedentsModified = true;
        }
        antecedents = new AntecedentsDialog(this, true, patient);
        antecedents.setVisible(true);
    }//GEN-LAST:event_btnAntecedentsActionPerformed

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        this.setFont(btnBack, true);
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        this.setFont(btnBack, false);
    }//GEN-LAST:event_btnBackMouseExited

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        principalParent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnModifyPatientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyPatientMouseEntered
        this.setFont(btnModifyPatient, true);
    }//GEN-LAST:event_btnModifyPatientMouseEntered

    private void btnModifyPatientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyPatientMouseExited
        this.setFont(btnModifyPatient, false);
    }//GEN-LAST:event_btnModifyPatientMouseExited

    private void btnModifyPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyPatientActionPerformed
        for (JFrame aux : openWindows) {
            if (aux instanceof ABMPatient) {
                aux.setVisible(true);
            }
            return;
        }
        patient = patientDao.getFullPatient(patient.getDni());
        if (antecedentsModified == false) {
            patient.setAntecendents(antecedentsDao.getAntecedent(patient.getDni()));
            antecedentsModified = true;
        }
        ABMPatient pacienteInterfaz = new ABMPatient(this, true, 2, this.patient);
        openWindows.add(pacienteInterfaz);
        pacienteInterfaz.setVisible(true);
    }//GEN-LAST:event_btnModifyPatientActionPerformed

    private void btnSeeVisitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeVisitMouseEntered
        this.setFont(btnSeeVisit, true);
    }//GEN-LAST:event_btnSeeVisitMouseEntered

    private void btnSeeVisitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeVisitMouseExited
        this.setFont(btnSeeVisit, false);
    }//GEN-LAST:event_btnSeeVisitMouseExited

    private void btnSeeVisitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeeVisitActionPerformed
        int j = 0, id = this.tblVisits.getSelectedRow();
        for (JFrame aux : openWindows) {
            if (aux instanceof ABMVisit) {
                ABMVisit auxCons = (ABMVisit) aux;
                if (auxCons.getVisitId() == this.visits.get(id).getId()) {
                    aux.setVisible(true);
                    return;
                }
            }
        }
        Visit visit = visitDao.getFullVisit(this.visits.get(id).getId(), patient.getDni());
        ABMVisit abmVisit = new ABMVisit(this, patient.getDni(), patient, visit);
        abmVisit.setVisible(true);
        abmVisit.requestFocus();
        openWindows.add(abmVisit);
    }//GEN-LAST:event_btnSeeVisitActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        principalParent.closeChild(this);
        closeOpenWindows();
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void setFont(JButton jbtn, boolean mouseEntering) {
        if (jbtn.isEnabled()) {
            if (mouseEntering) {
                jbtn.setFont(new java.awt.Font("Tahoma", 1, 15));
            } else {
                jbtn.setFont(new java.awt.Font("Tahoma", 1, 14));
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAntecedents;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnModifyPatient;
    private javax.swing.JButton btnNewVisit;
    private javax.swing.JButton btnSeeVisit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBirthday;
    private javax.swing.JLabel lblCity;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblFirstVisitDate;
    private javax.swing.JLabel lblInsuranceNumber;
    private javax.swing.JLabel lblNombrePaciente;
    private javax.swing.JLabel lblPPHealthInsurance;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblstaticAddress;
    private javax.swing.JLabel lblstaticBirthday;
    private javax.swing.JLabel lblstaticCity;
    private javax.swing.JLabel lblstaticDni;
    private javax.swing.JLabel lblstaticFirstVisitDate;
    private javax.swing.JLabel lblstaticInsuranceNumber;
    private javax.swing.JLabel lblstaticNombre;
    private javax.swing.JLabel lblstaticPPHealthInsurance;
    private javax.swing.JLabel lblstaticPhone;
    private javax.swing.JPanel pnlNameLastname;
    private javax.swing.JPanel pnlPatientData;
    private javax.swing.JPanel pnlVisits;
    private javax.swing.JTable tblVisits;
    // End of variables declaration//GEN-END:variables
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Method used to fill all the fields for the clinical history of a certain patient
     *
     * @param patient
     */
    public void fillFields(Patient patient) {
        this.lblNombrePaciente.setText(String.format(FULLNAME, patient.getLastname(), patient.getName()));

        this.lblBirthday.setText(String.format(BIRTHDAY_WITH_AGE, patient.getBirthday(), calculateAge(patient.getBirthday())));
        this.lblDni.setText(patient.getDni() + "");
        this.lblCity.setText(patient.getCity());
        
        this.lblPhone.setText(patient.getPhone());
        this.lblFirstVisitDate.setText(patient.getFirstVisitDate());
        this.lblAddress.setText(patient.getAddress());
        
        this.lblPPHealthInsurance.setText(patient.getPrepaidHealthInsurance().getName());
        this.lblInsuranceNumber.setText(patient.getPrepaidHealthInsuranceNumber());
    }

    /**
     * Method used to fill the visits table
     *
     * @param dni patient's dni
     */
    public void fillTable(long dni) {
        Object[] o;
        visitsDtm = (DefaultTableModel) this.tblVisits.getModel();
        clearTable(visitsDtm);
        visits = new LinkedList<>();
        visits = visitDao.getAllPatientVisits(dni);

        if (visits.isEmpty()) {
            changeTableSize(visitsDtm, 8);
        } else {
            changeTableSize(visitsDtm, 0);
            this.btnSeeVisit.setEnabled(true);
        }
        for (int i = 0; i < visits.size(); i++) {
            o = new Object[3];
            o[0] = visits.get(i).getDate();
            o[1] = visits.get(i).getReason();
            o[2] = visits.get(i).getDiagnosis();
            visitsDtm.addRow(o);
        }
        tblVisits.changeSelection(0, 0, false, false);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/sistema.png"));
        return retValue;
    }

    /**
     * Communicates color changes in the GUI to those frames that has this one as a parent.
     * @param color New color
     */
    public void paintChilds(int color) {
        for (JFrame window : openWindows) {
            Utils.StyleManager.paint(window, color);
        }
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

    /**
     * Returns this clinical history patient's dni
     *
     * @return dni
     */
    public long getPatientDni() {
        return patient.getDni();
    }

    private void closeOpenWindows() {
        for (JFrame window : openWindows) {
            window.dispose();
        }
        openWindows.clear();
    }

}
