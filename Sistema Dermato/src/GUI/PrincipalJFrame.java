package GUI;

import Utils.MultiLineCellRenderer;
import Utils.FileManager;
import Utils.TextFilter;
import Utils.StyleManager;
import ClasesBase.Patient;
import DAO.DAOPatient;
import static GUI.ABMPatientJFrame.Origin.PRINCIPAL_MODIFY;
import static GUI.ABMPatientJFrame.Origin.PRINCIPAL_NEW;
import static Utils.Constants.SYSTEM_FONT;
import static Utils.Constants.SYSTEM_ICON_IMAGE_PATH;
import static Utils.GeneralUtils.changeTableSize;
import static Utils.GeneralUtils.clearTable;
import static Utils.GeneralUtils.performBackup;
import static Utils.GeneralUtils.setButtonFontForPointerEvent;
import static Utils.ValidationsAndMessages.showAbout;
import static Utils.ValidationsAndMessages.validateExit;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PrincipalJFrame extends javax.swing.JFrame {

    //STATIC VARS
    public static final String PATIENTS_TABLE_LASTNAME_COLUMN_TITLE = "Apellido";
    public static final String PATIENTS_TABLE_NAME_COLUMN_TITLE = "Nombre";
    public static final String PATIENTS_TABLE_BIRTHDAY_COLUMN_TITLE = "Fecha de Nacimiento";
    public static final String PATIENTS_TABLE_LAST_VISIT_COLUMN_TITLE = "Ultima Consulta";

    //UI
    private DefaultTableModel dtmPatients;
    private final LinkedList<JFrame> openFrames;
    private ABMPrePaidHealthInsurances abmPrePaidHealthInsurances;

    //Data
    private final DAOPatient daoPatient;

    //Models
    private LinkedList<Patient> patientsList;
    private Patient patient;

    /**
     * Creates new form Principal
     */
    public PrincipalJFrame() {
        patientsList = new LinkedList<>();
        openFrames = new LinkedList<>();
        daoPatient = new DAOPatient();

        initComponents();
        setupInitialUI();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(new SyntheticaMauveMetallicLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(Boolean.FALSE);
            SyntheticaLookAndFeel.setWindowsDecorated(false);
            SyntheticaLookAndFeel.setUseSystemFileIcons(true);
            SyntheticaLookAndFeel.setFont(new java.awt.Font(SYSTEM_FONT, 0, 13));

        } catch (ParseException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PrincipalJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        FileManager.AutomaticBackup();

        java.awt.EventQueue.invokeLater(() -> {
            new PrincipalJFrame().setVisible(true);
        });
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(SYSTEM_ICON_IMAGE_PATH));
        return retValue;
    }

    @Override
    public JRootPane getRootPane() {
        super.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            validateExit(this);
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        return super.getRootPane();
    }

    private void setupInitialUI() {
        //Textfields
        txtfDni.setEditable(true);
        txtfLastname.setDocument(new TextFilter(TextFilter.LETTERS));
        txtfName.setDocument(new TextFilter(TextFilter.LETTERS));
        txtfDni.setDocument(new TextFilter(TextFilter.DIGITS));

        //Buttons
        buttonSetup(btnModifyPatient, false);
        buttonSetup(btnNewPatient, false);
        buttonSetup(btnPerformBackup, false);
        buttonSetup(btnSeeCH, false);

        //Table
        tblPatients.getColumn(PATIENTS_TABLE_LASTNAME_COLUMN_TITLE).setResizable(false);
        tblPatients.getColumn(PATIENTS_TABLE_NAME_COLUMN_TITLE).setResizable(false);
        tblPatients.getColumn(PATIENTS_TABLE_BIRTHDAY_COLUMN_TITLE).setResizable(false);
        tblPatients.getColumn(PATIENTS_TABLE_LAST_VISIT_COLUMN_TITLE).setResizable(false);
        tblPatients.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        tblPatients.getTableHeader().setFont(new Font(SYSTEM_FONT, Font.PLAIN, 14));
        tblPatients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dtmPatients = (DefaultTableModel) tblPatients.getModel();
        changeTableSize(dtmPatients, 10);

        tblPatients.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2 && row != -1) {
                    seePatientsCH();
                }
            }
        });

        //Window
        setLocationRelativeTo(getRootPane());
        StyleManager.paint(this);
        setExtendedState(PrincipalJFrame.MAXIMIZED_BOTH);
    }

    /**
     * Method used to fill the patients table
     *
     * @param patientsList patients list used to fill the table
     */
    private void fillTable() {
        clearTable(dtmPatients);
        if (patientsList.isEmpty()) {
            btnModifyPatient.setEnabled(false);
            btnSeeCH.setEnabled(false);
            changeTableSize(dtmPatients, 10);
            return;
        }

        Object[] o;
        dtmPatients = (DefaultTableModel) tblPatients.getModel();
        changeTableSize(dtmPatients, 0);

        for (int i = 0; i < patientsList.size(); i++) {
            o = new Object[4];
            o[0] = patientsList.get(i).getLastname();
            o[1] = patientsList.get(i).getName();
            o[2] = patientsList.get(i).getBirthday();
            o[3] = patientsList.get(i).getLastVisitDate();
            dtmPatients.addRow(o);
        }
        tblPatients.changeSelection(0, 0, false, false);
    }

    /**
     * Method used to register a new patient
     */
    private void newPatient() {
        for (JFrame aux : openFrames) {
            if (aux instanceof ABMPatientJFrame && ((ABMPatientJFrame) aux).getPatientDni() == -1) {
                aux.setVisible(true);
                return;
            }
        }
        ABMPatientJFrame patientFrame = new ABMPatientJFrame(this, PRINCIPAL_NEW);
        patientFrame.setVisible(true);
        buttonSetup(btnModifyPatient, false);
        openFrames.add(patientFrame);
    }

    /**
     * Method used to update the patients list according to the filters from
     * another frame.
     *
     */
    public void updatePatientList() {
        patientsList = daoPatient.getAllPatients(txtfName.getText(),
                txtfLastname.getText(), txtfDni.getText());
        fillTable();
    }

    /**
     * Sets the buttons in the principal menu according to the mouse pointer
     *
     * @param jbtn JButton to set
     * @param entering boolean true if the mouse pointer is entering the jButton
     * area, false otherwise
     */
    private void buttonSetup(JButton jbtn, boolean entering) {
        if (!patientsList.isEmpty() || (jbtn == btnNewPatient || jbtn == btnPerformBackup)) {
            if (entering) {
                jbtn.setEnabled(true);
                setButtonFontForPointerEvent(jbtn, entering);
                jbtn.setForeground(StyleManager.getTextColor(StyleManager.actualColor));
            } else {
                jbtn.setEnabled(false);
                setButtonFontForPointerEvent(jbtn, entering);
                jbtn.setForeground(new Color(153, 153, 153));
            }
        }
    }

    /**
     * Method used to set the color of this frame child
     *
     * @param color selectedColor
     */
    public void paintChilds(int color) {
        openFrames.stream().forEach((window) -> {
            Utils.StyleManager.paint(window, color);
        });
        if (abmPrePaidHealthInsurances != null) {
            Utils.StyleManager.paint(abmPrePaidHealthInsurances, color);
        }
    }

    /**
     * Notifies to other frames that some of the frame that had it as owners has
     * been closed.
     *
     * @param closedWindow closed frame
     */
    public void closeChild(JFrame closedWindow) {
        if (closedWindow != null) {
            openFrames.remove(closedWindow);
        }
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
            textField.setNextFocusableComponent(scrollPaneTablaPacientes);
            textField.transferFocus();
            Robot r = new Robot();
            System.out.println(eventKeyCode);
            r.keyPress(VK_DOWN);
            r.keyRelease(VK_DOWN);
        } catch (AWTException ex) {
            Logger.getLogger(PrincipalJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void seePatientsCH() {
        if (!patientsList.isEmpty()) {
            patient = new Patient();
            patient = patientsList.get(tblPatients.getSelectedRow());
            for (JFrame aux : openFrames) {
                if (aux instanceof ClinicalHistoryJFrame && ((ClinicalHistoryJFrame) aux).getPatientDni() == patient.getDni()) {
                    aux.setVisible(true);
                    clearTable(dtmPatients);
                    txtfLastname.setText("");
                    txtfName.setText("");
                    txtfDni.setText("");
                    buttonSetup(btnSeeCH, false);
                    patientsList.removeAll(patientsList);
                    dtmPatients.setNumRows(15);
                    return;
                }
            }
            patient = daoPatient.getFullPatient(patient.getDni());

            ClinicalHistoryJFrame clinicalHistory = new ClinicalHistoryJFrame(this, patient);
            clinicalHistory.setVisible(true);
            openFrames.add(clinicalHistory);
            clearTable(dtmPatients);
            txtfLastname.setText("");
            txtfName.setText("");
            txtfDni.setText("");
            buttonSetup(btnSeeCH, false);
            patientsList.removeAll(patientsList);
            dtmPatients.setNumRows(15);
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
        pnlBuscar = new javax.swing.JPanel();
        txtfLastname = new javax.swing.JTextField();
        lblstaticApellido = new javax.swing.JLabel();
        lblstaticDni = new javax.swing.JLabel();
        txtfDni = new javax.swing.JTextField();
        txtfName = new javax.swing.JTextField();
        lblstaticNombre = new javax.swing.JLabel();
        pnlBotones = new javax.swing.JPanel();
        btnNewPatient = new javax.swing.JButton();
        btnSeeCH = new javax.swing.JButton();
        btnModifyPatient = new javax.swing.JButton();
        btnPerformBackup = new javax.swing.JButton();
        pnlTablaPacientes = new javax.swing.JPanel();
        scrollPaneTablaPacientes = new javax.swing.JScrollPane();
        tblPatients = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuNuevoPaciente = new javax.swing.JMenuItem();
        menuRealizarBackUp = new javax.swing.JMenuItem();
        menuObrasSociales = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuSalir = new javax.swing.JMenuItem();
        menuVentana = new javax.swing.JMenu();
        menuCambiarColor = new javax.swing.JMenuItem();
        menuVer = new javax.swing.JMenu();
        menuAcercaDe = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión de Pacientes");
        setBounds(new java.awt.Rectangle(5, 5, 0, 0));
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(824, 443));
        setName("principal"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlBuscar.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Buscar por", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13))); // NOI18N
        pnlBuscar.setToolTipText("");
        pnlBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtfLastname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfLastname.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtfLastname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfLastnameKeyReleased(evt);
            }
        });

        lblstaticApellido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticApellido.setText("Apellido:");

        lblstaticDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticDni.setText("Nro. Doc.:");

        txtfDni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfDni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfDniKeyReleased(evt);
            }
        });

        txtfName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtfName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfNameKeyReleased(evt);
            }
        });

        lblstaticNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblstaticNombre.setText("Nombre:");

        javax.swing.GroupLayout pnlBuscarLayout = new javax.swing.GroupLayout(pnlBuscar);
        pnlBuscar.setLayout(pnlBuscarLayout);
        pnlBuscarLayout.setHorizontalGroup(
            pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblstaticApellido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfLastname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblstaticNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblstaticDni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtfDni)
                .addContainerGap())
        );
        pnlBuscarLayout.setVerticalGroup(
            pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarLayout.createSequentialGroup()
                .addGroup(pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtfLastname)
                        .addComponent(txtfName)
                        .addComponent(txtfDni))
                    .addGroup(pnlBuscarLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblstaticApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblstaticNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblstaticDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pnlBotones.setBackground(new java.awt.Color(245, 245, 245));
        pnlBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pnlBotones.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlBotones.setOpaque(false);

        btnNewPatient.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNewPatient.setForeground(new java.awt.Color(153, 153, 153));
        btnNewPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_user_enabled.png"))); // NOI18N
        btnNewPatient.setText("Nuevo Paciente");
        btnNewPatient.setBorder(null);
        btnNewPatient.setContentAreaFilled(false);
        btnNewPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnNewPatient.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_user_not_enabled.png"))); // NOI18N
        btnNewPatient.setEnabled(false);
        btnNewPatient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewPatient.setOpaque(true);
        btnNewPatient.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNewPatientMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNewPatientMouseExited(evt);
            }
        });
        btnNewPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPatientActionPerformed(evt);
            }
        });

        btnSeeCH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSeeCH.setForeground(new java.awt.Color(153, 153, 153));
        btnSeeCH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/HC_enabled.png"))); // NOI18N
        btnSeeCH.setText("Ver Historia Clínica");
        btnSeeCH.setBorder(null);
        btnSeeCH.setContentAreaFilled(false);
        btnSeeCH.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSeeCH.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/HC_not_enabled.png"))); // NOI18N
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

        btnModifyPatient.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModifyPatient.setForeground(new java.awt.Color(153, 153, 153));
        btnModifyPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_profile_enabled.png"))); // NOI18N
        btnModifyPatient.setText("Modificar Paciente");
        btnModifyPatient.setBorder(null);
        btnModifyPatient.setContentAreaFilled(false);
        btnModifyPatient.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModifyPatient.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_profile_not_enabled.png"))); // NOI18N
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

        btnPerformBackup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPerformBackup.setForeground(new java.awt.Color(153, 153, 153));
        btnPerformBackup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/backup_enabled.png"))); // NOI18N
        btnPerformBackup.setText("Realizar Back Up");
        btnPerformBackup.setBorder(null);
        btnPerformBackup.setContentAreaFilled(false);
        btnPerformBackup.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPerformBackup.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/backup_not_enabled.png"))); // NOI18N
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

        javax.swing.GroupLayout pnlBotonesLayout = new javax.swing.GroupLayout(pnlBotones);
        pnlBotones.setLayout(pnlBotonesLayout);
        pnlBotonesLayout.setHorizontalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNewPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSeeCH, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
            .addComponent(btnModifyPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnPerformBackup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlBotonesLayout.setVerticalGroup(
            pnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotonesLayout.createSequentialGroup()
                .addComponent(btnNewPatient, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeeCH, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModifyPatient, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPerformBackup, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnlTablaPacientes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabla de Pacientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13))); // NOI18N
        pnlTablaPacientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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
        scrollPaneTablaPacientes.setViewportView(tblPatients);

        javax.swing.GroupLayout pnlTablaPacientesLayout = new javax.swing.GroupLayout(pnlTablaPacientes);
        pnlTablaPacientes.setLayout(pnlTablaPacientesLayout);
        pnlTablaPacientesLayout.setHorizontalGroup(
            pnlTablaPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTablaPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneTablaPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTablaPacientesLayout.setVerticalGroup(
            pnlTablaPacientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTablaPacientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneTablaPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        menuArchivo.setText("Archivo");
        menuArchivo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        menuNuevoPaciente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNuevoPaciente.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuNuevoPaciente.setText("Nuevo Paciente");
        menuNuevoPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoPacienteActionPerformed(evt);
            }
        });
        menuArchivo.add(menuNuevoPaciente);

        menuRealizarBackUp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        menuRealizarBackUp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuRealizarBackUp.setText("Realizar Back Up");
        menuRealizarBackUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRealizarBackUpActionPerformed(evt);
            }
        });
        menuArchivo.add(menuRealizarBackUp);

        menuObrasSociales.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuObrasSociales.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuObrasSociales.setText("Gestion de Obras Sociales");
        menuObrasSociales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuObrasSocialesActionPerformed(evt);
            }
        });
        menuArchivo.add(menuObrasSociales);
        menuArchivo.add(jSeparator1);

        menuSalir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        menuSalir.setText("Salir");
        menuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuSalir);

        menuBar.add(menuArchivo);

        menuVentana.setText("Ventana");
        menuVentana.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        menuCambiarColor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuCambiarColor.setText("Cambiar Color");
        menuCambiarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCambiarColorActionPerformed(evt);
            }
        });
        menuVentana.add(menuCambiarColor);

        menuBar.add(menuVentana);

        menuVer.setText("Ver");
        menuVer.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        menuAcercaDe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuAcercaDe.setText("Acerca De...");
        menuAcercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAcercaDeActionPerformed(evt);
            }
        });
        menuVer.add(menuAcercaDe);

        menuBar.add(menuVer);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTablaPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlTablaPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirActionPerformed
        validateExit(this);
    }//GEN-LAST:event_menuSalirActionPerformed

    private void menuNuevoPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoPacienteActionPerformed
        newPatient();
    }//GEN-LAST:event_menuNuevoPacienteActionPerformed

    private void menuRealizarBackUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRealizarBackUpActionPerformed
        performBackup(this);
    }//GEN-LAST:event_menuRealizarBackUpActionPerformed

    private void menuAcercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAcercaDeActionPerformed
        showAbout(this);
    }//GEN-LAST:event_menuAcercaDeActionPerformed

    private void menuObrasSocialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuObrasSocialesActionPerformed
        abmPrePaidHealthInsurances = new ABMPrePaidHealthInsurances(this);
        abmPrePaidHealthInsurances.setVisible(true);
    }//GEN-LAST:event_menuObrasSocialesActionPerformed

    private void txtfLastnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfLastnameKeyReleased
        String lastname, name, dni;
        int eventKeyCode = evt.getKeyCode();
        char eventKeyChar = evt.getKeyChar();

        lastname = captureTextWhenDeleting(eventKeyChar, txtfLastname);

        if ((eventKeyCode == VK_DOWN) && !lastname.isEmpty()) {
            scrollInTable(eventKeyCode, txtfLastname);
        }

        name = txtfName.getText();
        dni = txtfDni.getText();
        patientsList = new LinkedList<>();
        patientsList = daoPatient.getAllPatients(name, lastname, dni);
        fillTable();
    }//GEN-LAST:event_txtfLastnameKeyReleased

    private void txtfNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfNameKeyReleased
        String name, lastname, dni;
        int eventKeyCode = evt.getKeyCode();
        char eventKeyChar = evt.getKeyChar();

        name = captureTextWhenDeleting(eventKeyChar, txtfName);

        if ((eventKeyCode == VK_DOWN) && !name.isEmpty()) {
            scrollInTable(eventKeyCode, txtfName);
            return;
        }

        lastname = txtfLastname.getText();
        dni = txtfDni.getText();
        patientsList = new LinkedList<>();
        patientsList = daoPatient.getAllPatients(name, lastname, dni);
        fillTable();
    }//GEN-LAST:event_txtfNameKeyReleased

    private void txtfDniKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfDniKeyReleased
        String name, lastname, dni;
        int eventKeyCode = evt.getKeyCode();
        char eventKeyChar = evt.getKeyChar();

        dni = captureTextWhenDeleting(eventKeyChar, txtfDni);

        if ((eventKeyCode == VK_DOWN) && !dni.isEmpty()) {
            scrollInTable(eventKeyCode, txtfDni);
            return;
        }

        name = txtfName.getText();
        lastname = txtfLastname.getText();
        patientsList = new LinkedList<>();
        patientsList = daoPatient.getAllPatients(name, lastname, dni);
        fillTable();
    }//GEN-LAST:event_txtfDniKeyReleased

    private void btnPerformBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPerformBackupActionPerformed
        performBackup(this);
    }//GEN-LAST:event_btnPerformBackupActionPerformed

    private void btnPerformBackupMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPerformBackupMouseEntered
        buttonSetup(btnPerformBackup, true);
    }//GEN-LAST:event_btnPerformBackupMouseEntered

    private void btnPerformBackupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPerformBackupMouseExited
        buttonSetup(btnPerformBackup, false);
    }//GEN-LAST:event_btnPerformBackupMouseExited

    private void btnModifyPatientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyPatientMouseEntered
        buttonSetup(btnModifyPatient, true);
    }//GEN-LAST:event_btnModifyPatientMouseEntered

    private void btnModifyPatientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyPatientMouseExited
        buttonSetup(btnModifyPatient, false);
    }//GEN-LAST:event_btnModifyPatientMouseExited

    private void btnModifyPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyPatientActionPerformed
        if (!patientsList.isEmpty()) {
            patient = new Patient();
            dtmPatients = (DefaultTableModel) tblPatients.getModel();
            patient = patientsList.get(tblPatients.getSelectedRow());
            for (JFrame aux : openFrames) {
                if (aux instanceof ABMPatientJFrame && ((ABMPatientJFrame) aux).getPatientDni() == patient.getDni()) {
                    aux.setVisible(true);
                    return;
                }
            }
            patient = daoPatient.getFullPatient(patient.getDni());

            ABMPatientJFrame pacienteInterfaz = new ABMPatientJFrame(this, PRINCIPAL_MODIFY, patient);
            pacienteInterfaz.setVisible(true);
            openFrames.add(pacienteInterfaz);
            buttonSetup(btnModifyPatient, false);
        }
    }//GEN-LAST:event_btnModifyPatientActionPerformed

    private void btnSeeCHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeCHMouseEntered
        buttonSetup(btnSeeCH, true);
    }//GEN-LAST:event_btnSeeCHMouseEntered

    private void btnSeeCHMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSeeCHMouseExited
        buttonSetup(btnSeeCH, false);
    }//GEN-LAST:event_btnSeeCHMouseExited

    private void btnSeeCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeeCHActionPerformed
        seePatientsCH();
    }//GEN-LAST:event_btnSeeCHActionPerformed

    private void btnNewPatientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewPatientMouseEntered
        buttonSetup(btnNewPatient, true);
    }//GEN-LAST:event_btnNewPatientMouseEntered

    private void btnNewPatientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNewPatientMouseExited
        buttonSetup(btnNewPatient, false);
    }//GEN-LAST:event_btnNewPatientMouseExited

    private void btnNewPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPatientActionPerformed
        newPatient();
    }//GEN-LAST:event_btnNewPatientActionPerformed

private void menuCambiarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCambiarColorActionPerformed
    new ChooseColor(this, true).setVisible(true);
}//GEN-LAST:event_menuCambiarColorActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        validateExit(this);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModifyPatient;
    private javax.swing.JButton btnNewPatient;
    private javax.swing.JButton btnPerformBackup;
    private javax.swing.JButton btnSeeCH;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblstaticApellido;
    private javax.swing.JLabel lblstaticDni;
    private javax.swing.JLabel lblstaticNombre;
    private javax.swing.JMenuItem menuAcercaDe;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuCambiarColor;
    private javax.swing.JMenuItem menuNuevoPaciente;
    private javax.swing.JMenuItem menuObrasSociales;
    private javax.swing.JMenuItem menuRealizarBackUp;
    private javax.swing.JMenuItem menuSalir;
    private javax.swing.JMenu menuVentana;
    private javax.swing.JMenu menuVer;
    private javax.swing.JPanel pnlBotones;
    private javax.swing.JPanel pnlBuscar;
    private javax.swing.JPanel pnlTablaPacientes;
    private javax.swing.JScrollPane scrollPaneTablaPacientes;
    private javax.swing.JTable tblPatients;
    private javax.swing.JTextField txtfDni;
    private javax.swing.JTextField txtfLastname;
    private javax.swing.JTextField txtfName;
    // End of variables declaration//GEN-END:variables
}
