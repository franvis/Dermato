package gui;

import bussines.DniType;
import utils.MultiLineCellRenderer;
import utils.TextFilter;
import utils.StyleManager;
import bussines.Patient;
import static utils.Constants.SYSTEM_FONT;
import static utils.Constants.SYSTEM_ICON_IMAGE_PATH;
import static utils.GeneralUtils.changeTableSize;
import static utils.GeneralUtils.clearTable;
import static utils.GeneralUtils.performBackup;
import static utils.GeneralUtils.setButtonFontForPointerEvent;
import static utils.StyleManager.DEFAULT_TEXT_COLOR;
import utils.ValidationsAndMessages;
import static utils.ValidationsAndMessages.showAbout;
import static utils.ValidationsAndMessages.validateExit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import mvp.presenter.PrincipalPresenter;
import mvp.view.listener.PatientUpdatedListener;
import mvp.view.PrincipalView;
import mvp.view.listener.DialogExitedListener;

/**
 *
 * @author Francisco Visintini
 */
public class PrincipalJFrame extends javax.swing.JFrame implements PrincipalView, PatientUpdatedListener, DialogExitedListener {

    //STATIC VARS
    public static final String PATIENTS_TABLE_LASTNAME_COLUMN_TITLE = "Apellido";
    public static final String PATIENTS_TABLE_NAME_COLUMN_TITLE = "Nombre";
    public static final String PATIENTS_TABLE_BIRTHDAY_COLUMN_TITLE = "Fecha de Nacimiento";
    public static final String PATIENTS_TABLE_LAST_VISIT_COLUMN_TITLE = "Ultima Consulta";

    //OTHER
    private boolean updatingPatient;

    //UI
    private DefaultTableModel dtmPatients;
    private MedicalCoverageJDialog abmMedicalCoverages;

    //PRESENTER
    private final PrincipalPresenter presenter;

    /**
     * Creates new form Principal
     */
    public PrincipalJFrame() {
        presenter = new PrincipalPresenter(this);
        initComponents();
        setupInitialUI();
        presenter.loadDniTypes();
    }

    private void setupInitialUI() {
        //Textfields
        txtfDni.setEditable(true);
        txtfLastname.setDocument(new TextFilter(TextFilter.LETTERS));
        txtfName.setDocument(new TextFilter(TextFilter.LETTERS));
        txtfDni.setDocument(new TextFilter(TextFilter.DIGITS_AND_LETTERS));

        //Buttons
        changeSideBarButtonsHighlight(false);

        //Table
        tblPatients.getColumn(PATIENTS_TABLE_LASTNAME_COLUMN_TITLE).setResizable(false);
        tblPatients.getColumn(PATIENTS_TABLE_NAME_COLUMN_TITLE).setResizable(false);
        tblPatients.getColumn(PATIENTS_TABLE_BIRTHDAY_COLUMN_TITLE).setResizable(false);
        tblPatients.getColumn(PATIENTS_TABLE_LAST_VISIT_COLUMN_TITLE).setResizable(false);
        tblPatients.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        tblPatients.getTableHeader().setFont(new Font(SYSTEM_FONT, Font.PLAIN, 14));
        tblPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dtmPatients = (DefaultTableModel) tblPatients.getModel();

        tblPatients.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2 && row != -1) {
                    PrincipalJFrame.this.presenter.seeClinicalHistory(
                            tblPatients.getSelectedRow());
                }
            }
        });

        tblPatients.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //DO NOTHING
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int eventKeyCode = e.getKeyCode();

                if ((eventKeyCode == KeyEvent.VK_ENTER) && tblPatients.getSelectedRow() != -1) {
                    PrincipalJFrame.this.presenter.seeClinicalHistory(
                            tblPatients.getSelectedRow());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //DO NOTHING
            }
        });

        //Window
        setLocationRelativeTo(getRootPane());
        StyleManager.paint(this);
        setExtendedState(PrincipalJFrame.MAXIMIZED_BOTH);
        setIconImage(getIconImage());
        StyleManager.paint(this);
    }

    @Override
    public void fillTable(java.util.List<Patient> patients) {
        clearTable(dtmPatients);

        Object[] o;
        dtmPatients = (DefaultTableModel) tblPatients.getModel();
        changeTableSize(dtmPatients, 0);

        for (int i = 0; i < patients.size(); i++) {
            boolean currentPatientHasBirthday = patients.get(i).getBirthday() != null
                    && !patients.get(i).getBirthday().isEmpty();
            boolean currentPatientHasLastVisitDate = patients.get(i).getLastVisitDate() != null
                    && !patients.get(i).getLastVisitDate().isEmpty();
            o = new Object[4];
            o[0] = patients.get(i).getLastname();
            o[1] = patients.get(i).getName();
            o[2] = currentPatientHasBirthday ? patients.get(i).getBirthday() : "-";
            o[3] = currentPatientHasLastVisitDate ? patients.get(i).getLastVisitDate() : "-";
            dtmPatients.addRow(o);
        }
        tblPatients.changeSelection(0, 0, false, false);

        if (updatingPatient && patients.isEmpty()) {
            clearFilters();
        }
        updatingPatient = false;
    }

    /**
     *
     */
    @Override
    public void showEmptyTable() {
        tblPatients.clearSelection();
        clearTable(dtmPatients);
        changeSideBarButtonsHighlight(false);
    }

    /**
     * Sets the buttons in the principal menu according to the mouse pointer
     *
     * @param jbtn JButton to set
     * @param entering boolean true if the mouse pointer is entering the jButton
     * area, false otherwise
     */
    private void buttonHighlight(JButton jbtn, boolean entering) {
        if (tblPatients.getSelectedRow() != -1 || (jbtn == btnNewPatient || jbtn == btnPerformBackup)) {
            setButtonFontForPointerEvent(jbtn, entering);
            changeButtonState(jbtn, entering);
        }
    }

    public void changeButtonState(JButton jbtn, boolean state) {
        jbtn.setEnabled(state);
        jbtn.setForeground(state ? StyleManager.getTextColor(
                StyleManager.actualColor) : DEFAULT_TEXT_COLOR);
    }

    private String captureTextWhenDeleting(char eventKeyChar, JTextField textField) {
        String text = "";
        int textFieldLength = textField.getText().length();
        if (eventKeyChar == KeyEvent.VK_BACK_SPACE || eventKeyChar == KeyEvent.VK_DELETE) {
            if (textFieldLength > 1) {
                text = textField.getText().substring(0, textFieldLength);
            } else if (textFieldLength == 1) {
                text = textField.getText();
            }
        } else {
            text = textField.getText();
        }
        return text;
    }

    private void scrollInTable(int eventKeyCode, JTextField textField) {
        try {
            textField.setNextFocusableComponent(scrollPanePatientsTable);
            textField.transferFocus();
            Robot r = new Robot();
            System.out.println(eventKeyCode);
            r.keyPress(VK_DOWN);
            r.keyRelease(VK_DOWN);
        } catch (AWTException ex) {
            Logger.getLogger(PrincipalJFrame.class.getName()).log(Level.SEVERE, null, ex);
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

        jMenuItem1 = new javax.swing.JMenuItem();
        pnlSearchPatient = new javax.swing.JPanel();
        txtfLastname = new javax.swing.JTextField();
        lblsLastName = new javax.swing.JLabel();
        lblsDni = new javax.swing.JLabel();
        txtfDni = new javax.swing.JTextField();
        txtfName = new javax.swing.JTextField();
        lblsName = new javax.swing.JLabel();
        lblsDniType = new javax.swing.JLabel();
        cmbDniType = new javax.swing.JComboBox();
        pnlButtons = new javax.swing.JPanel();
        btnDeletePatient = new javax.swing.JButton();
        btnSeeCH = new javax.swing.JButton();
        btnModifyPatient = new javax.swing.JButton();
        btnPerformBackup = new javax.swing.JButton();
        btnNewPatient = new javax.swing.JButton();
        pnlPatientsTable = new javax.swing.JPanel();
        scrollPanePatientsTable = new javax.swing.JScrollPane();
        tblPatients = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuNewPatient = new javax.swing.JMenuItem();
        menuPerformBackUp = new javax.swing.JMenuItem();
        menuMedicalCoverage = new javax.swing.JMenuItem();
        menuExit = new javax.swing.JMenuItem();
        menuWindow = new javax.swing.JMenu();
        menuChangeColor = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        menuAbout = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Gestión de Pacientes");
        setBounds(new java.awt.Rectangle(5, 5, 0, 0));
        setMinimumSize(new java.awt.Dimension(824, 443));
        setName("principal"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlSearchPatient.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Buscar por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13))); // NOI18N
        pnlSearchPatient.setToolTipText("");
        pnlSearchPatient.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtfLastname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfLastname.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfLastname.setNextFocusableComponent(txtfName);
        txtfLastname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfLastnameKeyReleased(evt);
            }
        });

        lblsLastName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsLastName.setText("Apellido:");

        lblsDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsDni.setText("Nro. Doc.:");

        txtfDni.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfDni.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfDni.setNextFocusableComponent(txtfLastname);
        txtfDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfDniKeyReleased(evt);
            }
        });

        txtfName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtfName.setMargin(new java.awt.Insets(0, 2, 0, 0));
        txtfName.setNextFocusableComponent(cmbDniType);
        txtfName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfNameKeyReleased(evt);
            }
        });

        lblsName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsName.setText("Nombre:");

        lblsDniType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblsDniType.setText("Tipo Doc:");

        cmbDniType.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmbDniType.setFocusCycleRoot(true);
        cmbDniType.setNextFocusableComponent(txtfDni);
        cmbDniType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDniTypeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchPatientLayout = new javax.swing.GroupLayout(pnlSearchPatient);
        pnlSearchPatient.setLayout(pnlSearchPatientLayout);
        pnlSearchPatientLayout.setHorizontalGroup(
            pnlSearchPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchPatientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblsLastName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfLastname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblsName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblsDniType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDniType, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblsDni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfDni, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSearchPatientLayout.setVerticalGroup(
            pnlSearchPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchPatientLayout.createSequentialGroup()
                .addGroup(pnlSearchPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtfLastname)
                    .addComponent(txtfName)
                    .addComponent(txtfDni)
                    .addComponent(lblsName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblsDniType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbDniType)
                    .addComponent(lblsLastName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlButtons.setBackground(new java.awt.Color(245, 245, 245));
        pnlButtons.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pnlButtons.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlButtons.setOpaque(false);

        btnDeletePatient.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnDeletePatient.setForeground(new java.awt.Color(153, 153, 153));
        btnDeletePatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_user_enabled.png"))); // NOI18N
        btnDeletePatient.setText("Borrar Paciente");
        btnDeletePatient.setBorder(null);
        btnDeletePatient.setContentAreaFilled(false);
        btnDeletePatient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDeletePatient.setEnabled(false);
        btnDeletePatient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeletePatient.setOpaque(true);
        btnDeletePatient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeletePatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDeletePatientMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDeletePatientMouseEntered(evt);
            }
        });
        btnDeletePatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePatientActionPerformed(evt);
            }
        });

        btnSeeCH.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSeeCH.setForeground(new java.awt.Color(153, 153, 153));
        btnSeeCH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/HC_enabled.png"))); // NOI18N
        btnSeeCH.setText("Ver Historia Clínica");
        btnSeeCH.setBorder(null);
        btnSeeCH.setContentAreaFilled(false);
        btnSeeCH.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSeeCH.setEnabled(false);
        btnSeeCH.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSeeCH.setOpaque(true);
        btnSeeCH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSeeCH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSeeCHMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSeeCHMouseEntered(evt);
            }
        });
        btnSeeCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeeCHActionPerformed(evt);
            }
        });

        btnModifyPatient.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnModifyPatient.setForeground(new java.awt.Color(153, 153, 153));
        btnModifyPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit_profile_enabled.png"))); // NOI18N
        btnModifyPatient.setText("Modificar Paciente");
        btnModifyPatient.setBorder(null);
        btnModifyPatient.setContentAreaFilled(false);
        btnModifyPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModifyPatient.setEnabled(false);
        btnModifyPatient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModifyPatient.setOpaque(true);
        btnModifyPatient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
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

        btnPerformBackup.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnPerformBackup.setForeground(new java.awt.Color(153, 153, 153));
        btnPerformBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/backup_enabled.png"))); // NOI18N
        btnPerformBackup.setText("Realizar Back Up");
        btnPerformBackup.setBorder(null);
        btnPerformBackup.setContentAreaFilled(false);
        btnPerformBackup.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPerformBackup.setEnabled(false);
        btnPerformBackup.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPerformBackup.setOpaque(true);
        btnPerformBackup.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPerformBackup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPerformBackupMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPerformBackupMouseExited(evt);
            }
        });
        btnPerformBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerformBackupActionPerformed(evt);
            }
        });

        btnNewPatient.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNewPatient.setForeground(new java.awt.Color(153, 153, 153));
        btnNewPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_user_enabled.png"))); // NOI18N
        btnNewPatient.setText("Nuevo Paciente");
        btnNewPatient.setBorder(null);
        btnNewPatient.setContentAreaFilled(false);
        btnNewPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNewPatient.setEnabled(false);
        btnNewPatient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewPatient.setOpaque(true);
        btnNewPatient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNewPatientMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNewPatientMouseEntered(evt);
            }
        });
        btnNewPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPatientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDeletePatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSeeCH, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
            .addComponent(btnModifyPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPerformBackup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNewPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addComponent(btnNewPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeeCH, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModifyPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeletePatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPerformBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlPatientsTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabla de Pacientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13))); // NOI18N
        pnlPatientsTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        tblPatients.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblPatients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", "", ""},
                {"", null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Apellido", "Nombre", "Fecha de Nacimiento", "Ultima Consulta"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPatients.setMaximumSize(new java.awt.Dimension(788, 443));
        tblPatients.setOpaque(false);
        tblPatients.getTableHeader().setReorderingAllowed(false);
        scrollPanePatientsTable.setViewportView(tblPatients);

        javax.swing.GroupLayout pnlPatientsTableLayout = new javax.swing.GroupLayout(pnlPatientsTable);
        pnlPatientsTable.setLayout(pnlPatientsTableLayout);
        pnlPatientsTableLayout.setHorizontalGroup(
            pnlPatientsTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPatientsTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanePatientsTable, javax.swing.GroupLayout.DEFAULT_SIZE, 984, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPatientsTableLayout.setVerticalGroup(
            pnlPatientsTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPatientsTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPanePatientsTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        menuFile.setText("Archivo");
        menuFile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        menuNewPatient.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNewPatient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuNewPatient.setText("Nuevo Paciente");
        menuNewPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNewPatientActionPerformed(evt);
            }
        });
        menuFile.add(menuNewPatient);

        menuPerformBackUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        menuPerformBackUp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuPerformBackUp.setText("Realizar Back Up");
        menuPerformBackUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPerformBackUpActionPerformed(evt);
            }
        });
        menuFile.add(menuPerformBackUp);

        menuMedicalCoverage.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuMedicalCoverage.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuMedicalCoverage.setText("Gestion de Obras Sociales");
        menuMedicalCoverage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMedicalCoverageActionPerformed(evt);
            }
        });
        menuFile.add(menuMedicalCoverage);

        menuExit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salir.png"))); // NOI18N
        menuExit.setText("Salir");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        menuFile.add(menuExit);

        menuBar.add(menuFile);

        menuWindow.setText("Ventana");
        menuWindow.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        menuChangeColor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuChangeColor.setText("Cambiar Color");
        menuChangeColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuChangeColorActionPerformed(evt);
            }
        });
        menuWindow.add(menuChangeColor);

        menuBar.add(menuWindow);

        menuHelp.setText("Ayuda");
        menuHelp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        menuAbout.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuAbout.setText("Acerca De...");
        menuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuAbout);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPatientsTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSearchPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlSearchPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlPatientsTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        validateExit(this);
    }//GEN-LAST:event_menuExitActionPerformed

    private void menuNewPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNewPatientActionPerformed
        PatientJDialog patientFrame = new PatientJDialog(this, this);
        patientFrame.setVisible(true);
    }//GEN-LAST:event_menuNewPatientActionPerformed

    private void menuPerformBackUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPerformBackUpActionPerformed
        performBackup(this);
    }//GEN-LAST:event_menuPerformBackUpActionPerformed

    private void menuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAboutActionPerformed
        showAbout(this);
    }//GEN-LAST:event_menuAboutActionPerformed

    private void menuMedicalCoverageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMedicalCoverageActionPerformed
        abmMedicalCoverages = new MedicalCoverageJDialog(this);
        abmMedicalCoverages.setVisible(true);
        abmMedicalCoverages.toFront();
        abmMedicalCoverages.requestFocus();
    }//GEN-LAST:event_menuMedicalCoverageActionPerformed

    private void txtfLastnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfLastnameKeyReleased
        String lastname, name, dni;
        int eventKeyCode = evt.getKeyCode();
        char eventKeyChar = evt.getKeyChar();

        if (eventKeyCode == KeyEvent.VK_ENTER) {
            return;
        }

        lastname = captureTextWhenDeleting(eventKeyChar, txtfLastname);

        if ((eventKeyCode == VK_DOWN) && !lastname.isEmpty()) {
            scrollInTable(eventKeyCode, txtfLastname);
        }

        name = txtfName.getText();
        dni = txtfDni.getText();

        presenter.filterPatients(name, lastname, dni, cmbDniType.getSelectedIndex());
    }//GEN-LAST:event_txtfLastnameKeyReleased

    private void txtfNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNameKeyReleased
        String name, lastname, dni;
        int eventKeyCode = evt.getKeyCode();
        char eventKeyChar = evt.getKeyChar();

        if (eventKeyCode == KeyEvent.VK_ENTER) {
            return;
        }

        name = captureTextWhenDeleting(eventKeyChar, txtfName);

        if ((eventKeyCode == VK_DOWN) && !name.isEmpty()) {
            scrollInTable(eventKeyCode, txtfName);
            return;
        }

        lastname = txtfLastname.getText();
        dni = txtfDni.getText();

        presenter.filterPatients(name, lastname, dni, cmbDniType.getSelectedIndex());
    }//GEN-LAST:event_txtfNameKeyReleased

    private void txtfDniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfDniKeyReleased
        String name, lastname, dni;
        int eventKeyCode = evt.getKeyCode();
        char eventKeyChar = evt.getKeyChar();

        if (eventKeyCode == KeyEvent.VK_ENTER) {
            return;
        }

        dni = captureTextWhenDeleting(eventKeyChar, txtfDni);

        if ((eventKeyCode == VK_DOWN) && !dni.isEmpty()) {
            scrollInTable(eventKeyCode, txtfDni);
            return;
        }

        name = txtfName.getText();
        lastname = txtfLastname.getText();

        presenter.filterPatients(name, lastname, dni, cmbDniType.getSelectedIndex());
    }//GEN-LAST:event_txtfDniKeyReleased

    private void btnPerformBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerformBackupActionPerformed
        performBackup(this);
    }//GEN-LAST:event_btnPerformBackupActionPerformed

    private void btnPerformBackupMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPerformBackupMouseEntered
        buttonHighlight(btnPerformBackup, true);
    }//GEN-LAST:event_btnPerformBackupMouseEntered

    private void btnPerformBackupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPerformBackupMouseExited
        buttonHighlight(btnPerformBackup, false);
    }//GEN-LAST:event_btnPerformBackupMouseExited

    private void btnModifyPatientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyPatientMouseEntered
        buttonHighlight(btnModifyPatient, true);
    }//GEN-LAST:event_btnModifyPatientMouseEntered

    private void btnModifyPatientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyPatientMouseExited
        buttonHighlight(btnModifyPatient, false);
    }//GEN-LAST:event_btnModifyPatientMouseExited

    private void btnModifyPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyPatientActionPerformed
        presenter.modifyPatient(tblPatients.getSelectedRow());
    }//GEN-LAST:event_btnModifyPatientActionPerformed

    private void btnSeeCHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeCHMouseEntered
        buttonHighlight(btnSeeCH, true);
    }//GEN-LAST:event_btnSeeCHMouseEntered

    private void btnSeeCHMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeCHMouseExited
        buttonHighlight(btnSeeCH, false);
    }//GEN-LAST:event_btnSeeCHMouseExited

    private void btnSeeCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeeCHActionPerformed
        if (tblPatients.getSelectedRow() != -1) {
            presenter.seeClinicalHistory(tblPatients.getSelectedRow());
        }
    }//GEN-LAST:event_btnSeeCHActionPerformed

    private void btnDeletePatientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeletePatientMouseEntered
        buttonHighlight(btnDeletePatient, true);
    }//GEN-LAST:event_btnDeletePatientMouseEntered

    private void btnDeletePatientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeletePatientMouseExited
        buttonHighlight(btnDeletePatient, false);
    }//GEN-LAST:event_btnDeletePatientMouseExited

    private void btnDeletePatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletePatientActionPerformed
        if (validatePatientDeleting()) {
            String name = txtfName.getText();
            String lastname = txtfLastname.getText();
            String dni = txtfDni.getText();
            presenter.deletePatient(tblPatients.getSelectedRow(), name, lastname, dni, cmbDniType.getSelectedIndex());
        }
    }//GEN-LAST:event_btnDeletePatientActionPerformed

private void menuChangeColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuChangeColorActionPerformed
    ChooseColorJDialog choseColorDialog = new ChooseColorJDialog(this, true);
    choseColorDialog.setVisible(true);
    choseColorDialog.toFront();
    choseColorDialog.requestFocus();
}//GEN-LAST:event_menuChangeColorActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        validateExit(this);
    }//GEN-LAST:event_formWindowClosing

    private void cmbDniTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDniTypeItemStateChanged
        presenter.filterPatients(txtfName.getText(), txtfLastname.getText(),
                txtfDni.getText(), cmbDniType.getSelectedIndex());
    }//GEN-LAST:event_cmbDniTypeItemStateChanged

    private void btnNewPatientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewPatientMouseExited
        buttonHighlight(btnNewPatient, false);
    }//GEN-LAST:event_btnNewPatientMouseExited

    private void btnNewPatientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewPatientMouseEntered
        buttonHighlight(btnNewPatient, true);
    }//GEN-LAST:event_btnNewPatientMouseEntered

    private void btnNewPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPatientActionPerformed
        PatientJDialog patientABM = new PatientJDialog(this, this);
        clearTable(dtmPatients);
        clearFilters();
        patientABM.setVisible(true);
    }//GEN-LAST:event_btnNewPatientActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletePatient;
    private javax.swing.JButton btnModifyPatient;
    private javax.swing.JButton btnNewPatient;
    private javax.swing.JButton btnPerformBackup;
    private javax.swing.JButton btnSeeCH;
    private javax.swing.JComboBox cmbDniType;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JLabel lblsDni;
    private javax.swing.JLabel lblsDniType;
    private javax.swing.JLabel lblsLastName;
    private javax.swing.JLabel lblsName;
    private javax.swing.JMenuItem menuAbout;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuChangeColor;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuMedicalCoverage;
    private javax.swing.JMenuItem menuNewPatient;
    private javax.swing.JMenuItem menuPerformBackUp;
    private javax.swing.JMenu menuWindow;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlPatientsTable;
    private javax.swing.JPanel pnlSearchPatient;
    private javax.swing.JScrollPane scrollPanePatientsTable;
    private javax.swing.JTable tblPatients;
    private javax.swing.JTextField txtfDni;
    private javax.swing.JTextField txtfLastname;
    private javax.swing.JTextField txtfName;
    // End of variables declaration//GEN-END:variables

    @Override
    public Image getIconImage() {
        return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(SYSTEM_ICON_IMAGE_PATH));
    }

    @Override
    public JRootPane getRootPane() {
        super.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            validateExit(this);
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        return super.getRootPane();
    }

    @Override
    public void showPatientClinicalHistory(Patient patient) {
        changeButtonState(btnSeeCH, false);
        clearTable(dtmPatients);
        clearFilters();
        ClinicalHistoryJDialog clinicalHistory = new ClinicalHistoryJDialog(this,
                this, patient, this);
        clinicalHistory.setVisible(true);
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
    public void modifyPatientData(Patient patient) {
        PatientJDialog pacienteInterfaz = new PatientJDialog(this, this, patient);
        pacienteInterfaz.setVisible(true);
        pacienteInterfaz.toFront();
        pacienteInterfaz.requestFocus();
    }

    private void changeSideBarButtonsHighlight(boolean state) {
        buttonHighlight(btnModifyPatient, state);
        buttonHighlight(btnSeeCH, state);
        buttonHighlight(btnDeletePatient, state);
        buttonHighlight(btnPerformBackup, state);
    }

    @Override
    public void patientUpdated(Patient patient) {
        updatingPatient = true;
        presenter.filterPatients(txtfName.getText(), txtfLastname.getText(),
                txtfDni.getText(), cmbDniType.getSelectedIndex());
    }

    @Override
    public void displayDniTypes(java.util.List<DniType> dniTypes) {
        cmbDniType.removeAllItems();
        dniTypes.stream().forEach((dniType) -> {
            cmbDniType.addItem(dniType.getName());
        });

        cmbDniType.setSelectedIndex(0);
    }

    private void clearFilters() {
        txtfDni.setText("");
        txtfLastname.setText("");
        txtfName.setText("");
    }

    private boolean validatePatientDeleting() {
        String OPTION_OK = "Si";
        String OPTION_CANCEL = "No";
        String EXIT_SYSTEM_MESSAGE = "¿Realmente desea borrar este paciente?";
        String EXIT_WINDOW_MESSAGE_TITLE = "Borrar Paciente";
        int ans = JOptionPane.showOptionDialog(this,
                EXIT_SYSTEM_MESSAGE,
                EXIT_WINDOW_MESSAGE_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{OPTION_OK, OPTION_CANCEL},
                "");

        return ans == JOptionPane.YES_OPTION;
    }

    @Override
    public void windowExited() {
        txtfLastname.grabFocus();
    }
}
