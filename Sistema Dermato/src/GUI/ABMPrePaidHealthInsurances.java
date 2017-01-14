/*
 * Clase dedicada a la alta, baja y modificacion de obras sociales
 */

 /*
 * ABMObrasSociales.java
 *
 * Created on 14-ago-2012, 17:36:11
 */
package GUI;

import Utils.ValidationsAndMessages;
import ClasesBase.PrePaidHealthInsurance;
import DAO.DAOPrepaidHealthInsurance;
import static Utils.GeneralUtils.clearTable;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import static Utils.GeneralUtils.setCustomFont;

public class ABMPrePaidHealthInsurances extends javax.swing.JFrame {

    private final DAOPrepaidHealthInsurance daoPrePaidHealthInsurance;
    private LinkedList<PrePaidHealthInsurance> PrePaidHealthInsurances;
    private DefaultTableModel dtmPrePaidHealthInsurances;
    private boolean isUpdating;

    /**
     * Creates new form ABMObrasSociales
     *
     * @param parent
     */
    public ABMPrePaidHealthInsurances(Frame parent) {
        initComponents();
        daoPrePaidHealthInsurance = new DAOPrepaidHealthInsurance();
        PrePaidHealthInsurances = daoPrePaidHealthInsurance.getAllPrePaidHealthInsurances();
        fillPrePaidHealthInsurances(PrePaidHealthInsurances);
        this.setLocationRelativeTo(parent);
        isUpdating = false;
        Utils.StyleManager.paint(this);
        KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        this.getRootPane().registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pnlPrePaidHealthInsurances = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPrePaidHealthInsurances = new javax.swing.JTable();
        pnlButtons = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        pnlNewPPHealthInsurance = new javax.swing.JPanel();
        txtPPHealthInsuranceName = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Gestión de Obras Sociales");
        setIconImage(getIconImage());
        setName("frmObrasSociales"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlPrePaidHealthInsurances.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlPrePaidHealthInsurances.setName("pnlObrasSociales"); // NOI18N

        jScrollPane2.setFocusable(false);

        tblPrePaidHealthInsurances.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblPrePaidHealthInsurances.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Obras Sociales"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblPrePaidHealthInsurances);

        javax.swing.GroupLayout pnlPrePaidHealthInsurancesLayout = new javax.swing.GroupLayout(pnlPrePaidHealthInsurances);
        pnlPrePaidHealthInsurances.setLayout(pnlPrePaidHealthInsurancesLayout);
        pnlPrePaidHealthInsurancesLayout.setHorizontalGroup(
            pnlPrePaidHealthInsurancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrePaidHealthInsurancesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPrePaidHealthInsurancesLayout.setVerticalGroup(
            pnlPrePaidHealthInsurancesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrePaidHealthInsurancesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnlButtons.setName("pnlButtons"); // NOI18N

        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(0, 51, 102));
        btnDelete.setText("Eliminar");
        btnDelete.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnDelete.setContentAreaFilled(false);
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDelete.setOpaque(true);
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDeleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDeleteMouseExited(evt);
            }
        });
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(0, 51, 102));
        btnCancel.setText("Cancelar");
        btnCancel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCancel.setEnabled(false);
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCancel.setOpaque(true);
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelMouseExited(evt);
            }
        });
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnModify.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModify.setForeground(new java.awt.Color(0, 51, 102));
        btnModify.setText("Modificar");
        btnModify.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnModify.setContentAreaFilled(false);
        btnModify.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModify.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnModify.setOpaque(true);
        btnModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnModifyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnModifyMouseExited(evt);
            }
        });
        btnModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlButtonsLayout = new javax.swing.GroupLayout(pnlButtons);
        pnlButtons.setLayout(pnlButtonsLayout);
        pnlButtonsLayout.setHorizontalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlButtonsLayout.createSequentialGroup()
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlNewPPHealthInsurance.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nueva Obra Social", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtPPHealthInsuranceName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(0, 51, 102));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_enabled.png"))); // NOI18N
        btnSave.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNewPPHealthInsuranceLayout = new javax.swing.GroupLayout(pnlNewPPHealthInsurance);
        pnlNewPPHealthInsurance.setLayout(pnlNewPPHealthInsuranceLayout);
        pnlNewPPHealthInsuranceLayout.setHorizontalGroup(
            pnlNewPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewPPHealthInsuranceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPPHealthInsuranceName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave)
                .addContainerGap())
        );
        pnlNewPPHealthInsuranceLayout.setVerticalGroup(
            pnlNewPPHealthInsuranceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewPPHealthInsuranceLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPPHealthInsuranceName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(pnlNewPPHealthInsuranceLayout.createSequentialGroup()
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnBack.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPrePaidHealthInsurances, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlNewPPHealthInsurance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlNewPPHealthInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlPrePaidHealthInsurances, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBack)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        setCustomFont(btnBack, true);
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        setCustomFont(btnBack, false);
    }//GEN-LAST:event_btnBackMouseExited

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.exit();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnDeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseEntered
        setCustomFont(btnDelete, true);
    }//GEN-LAST:event_btnDeleteMouseEntered

    private void btnDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseExited
        setCustomFont(btnDelete, false);
    }//GEN-LAST:event_btnDeleteMouseExited

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!PrePaidHealthInsurances.isEmpty()) {
            int idObraSeleccionada = PrePaidHealthInsurances.get(tblPrePaidHealthInsurances.getSelectedRow()).getId();
            if (daoPrePaidHealthInsurance.deletePrePaidHealthInsurance(new PrePaidHealthInsurance(idObraSeleccionada, ""))) {
                ValidationsAndMessages.showInfo(this, "Borrado Exitoso.");
                PrePaidHealthInsurances = daoPrePaidHealthInsurance.getAllPrePaidHealthInsurances();
                fillPrePaidHealthInsurances(PrePaidHealthInsurances);
            } else {
                ValidationsAndMessages.showInfo(this, "Borrado Fallido.");
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseEntered
        setCustomFont(btnCancel, true);
    }//GEN-LAST:event_btnCancelMouseEntered

    private void btnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseExited
        setCustomFont(btnCancel, false);
    }//GEN-LAST:event_btnCancelMouseExited

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.btnDelete.setEnabled(true);
        this.btnCancel.setEnabled(false);
        this.txtPPHealthInsuranceName.setText("");
        this.btnModify.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnModifyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyMouseEntered
        setCustomFont(btnModify, true);
    }//GEN-LAST:event_btnModifyMouseEntered

    private void btnModifyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModifyMouseExited
        setCustomFont(btnModify, false);
    }//GEN-LAST:event_btnModifyMouseExited

    private void btnModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActionPerformed
        if (!PrePaidHealthInsurances.isEmpty()) {
            int idSelectedPPHealthInsurance = PrePaidHealthInsurances.get(tblPrePaidHealthInsurances.getSelectedRow()).getId();
            PrePaidHealthInsurance pPHealthInsuranceToModify = daoPrePaidHealthInsurance.getPPHealthInsurance(idSelectedPPHealthInsurance);
            txtPPHealthInsuranceName.setText(pPHealthInsuranceToModify.getName());
            txtPPHealthInsuranceName.grabFocus();
            isUpdating = true;
            this.btnDelete.setEnabled(false);
            this.btnCancel.setEnabled(true);
            this.btnModify.setEnabled(false);
        }
    }//GEN-LAST:event_btnModifyActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (this.txtPPHealthInsuranceName.getText().isEmpty()) {
            ValidationsAndMessages.showError(this, "Ingrese un nombre de obra social válido...");
            return;
        }
        if (!isUpdating) {
            if (daoPrePaidHealthInsurance.registerPrePaidHealthInsurance(new PrePaidHealthInsurance(0, txtPPHealthInsuranceName.getText()))) {
                ValidationsAndMessages.showInfo(this, "Registro Exitoso.");
                this.txtPPHealthInsuranceName.setText("");
                this.PrePaidHealthInsurances.removeAll(PrePaidHealthInsurances);
                this.PrePaidHealthInsurances = daoPrePaidHealthInsurance.getAllPrePaidHealthInsurances();
                fillPrePaidHealthInsurances(PrePaidHealthInsurances);
            } else {
                ValidationsAndMessages.showError(this, "Registro Fallido.");
            }
        } else {
            int idSelectedPPHealthInsurance = PrePaidHealthInsurances.get(tblPrePaidHealthInsurances.getSelectedRow()).getId();
            if (daoPrePaidHealthInsurance.updatePrepaidHealthInsurance(new PrePaidHealthInsurance(idSelectedPPHealthInsurance, txtPPHealthInsuranceName.getText()))) {
                ValidationsAndMessages.showInfo(this, "Actualización Exitosa.");
                this.txtPPHealthInsuranceName.setText("");
                this.PrePaidHealthInsurances.removeAll(PrePaidHealthInsurances);
                this.PrePaidHealthInsurances = daoPrePaidHealthInsurance.getAllPrePaidHealthInsurances();
                fillPrePaidHealthInsurances(PrePaidHealthInsurances);
            } else {
                ValidationsAndMessages.showError(this, "Actualización Fallida.");
            }
            this.txtPPHealthInsuranceName.setText("");
            isUpdating = false;
            this.btnDelete.setEnabled(true);
            this.btnModify.setEnabled(true);
            this.btnCancel.setEnabled(false);
            txtPPHealthInsuranceName.grabFocus();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.exit();
    }//GEN-LAST:event_formWindowClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlNewPPHealthInsurance;
    private javax.swing.JPanel pnlPrePaidHealthInsurances;
    private javax.swing.JTable tblPrePaidHealthInsurances;
    private javax.swing.JTextField txtPPHealthInsuranceName;
    // End of variables declaration//GEN-END:variables

    /**
     * Fills the table with all existing pre paid health insurances.
     *
     * @param prePaidHealthInsurances
     */
    private void fillPrePaidHealthInsurances(LinkedList<PrePaidHealthInsurance> prePaidHealthInsurances) {
        Object[] o;
        this.dtmPrePaidHealthInsurances = (DefaultTableModel) this.tblPrePaidHealthInsurances.getModel();
        clearTable(dtmPrePaidHealthInsurances);
        for (int i = 0; i < prePaidHealthInsurances.size(); i++) {
            o = new Object[1];
            o[0] = prePaidHealthInsurances.get(i).getName();
            dtmPrePaidHealthInsurances.addRow(o);
        }
        this.tblPrePaidHealthInsurances.changeSelection(0, 0, false, false);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Imagenes/sistema.png"));
        return retValue;
    }

    /**
     * Exits application validating with the user.
     */
    private void exit() {
        if (isUpdating) {
            ValidationsAndMessages.validateWindowExit(this);
        } else {
            this.dispose();
        }
    }
}
