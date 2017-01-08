/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import ClasesBase.Antecedents;
import Utils.ValidationsAndMessages;
import ClasesBase.Patient;
import DAO.DAOAntecedents;
import static Utils.GeneralUtils.handleFocus;
import static Utils.GeneralUtils.setCustomFont;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 *
 * @author Fran
 */
public class AntecedentsDialog extends javax.swing.JDialog {

    private Antecedents antecedents;
    private final DAOAntecedents daoAntecedents;
    private final Patient patient;

    /**
     * Creates new form AntecGenerales
     *
     * @param parent
     * @param modal
     * @param patient
     */
    public AntecedentsDialog(java.awt.Frame parent, boolean modal, Patient patient) {
        super(parent, modal);
        initComponents();
        this.btnModify.grabFocus();
        this.patient = patient;
        daoAntecedents = new DAOAntecedents();
        fillFields(patient);
        setFieldsState(false);
        this.setLocationRelativeTo(parent);
        setIconImage(getIconImage());
        Utils.StyleManager.paint(this);
        //eventos de la página
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

        pnlGeneral = new javax.swing.JPanel();
        pnlSurgical = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtaSurgical = new javax.swing.JTextArea();
        pnlToxic = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtaToxic = new javax.swing.JTextArea();
        pnlPersonal = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtaPersonal = new javax.swing.JTextArea();
        pnlPharmacological = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtaPharmacological = new javax.swing.JTextArea();
        pnlFamily = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtaFamily = new javax.swing.JTextArea();
        pnlButtons = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnModify = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Antecedentes Generales");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlGeneral.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        pnlSurgical.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Quirúrgicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaSurgical.setEditable(false);
        txtaSurgical.setColumns(20);
        txtaSurgical.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaSurgical.setLineWrap(true);
        txtaSurgical.setRows(5);
        txtaSurgical.setWrapStyleWord(true);
        txtaSurgical.setNextFocusableComponent(txtaToxic);
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
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );
        pnlSurgicalLayout.setVerticalGroup(
            pnlSurgicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6)
        );

        pnlToxic.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Tóxicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaToxic.setEditable(false);
        txtaToxic.setColumns(20);
        txtaToxic.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaToxic.setLineWrap(true);
        txtaToxic.setRows(5);
        txtaToxic.setWrapStyleWord(true);
        txtaToxic.setNextFocusableComponent(btnSave);
        txtaToxic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtaToxicKeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(txtaToxic);

        javax.swing.GroupLayout pnlToxicLayout = new javax.swing.GroupLayout(pnlToxic);
        pnlToxic.setLayout(pnlToxicLayout);
        pnlToxicLayout.setHorizontalGroup(
            pnlToxicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
        );
        pnlToxicLayout.setVerticalGroup(
            pnlToxicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
        );

        pnlPersonal.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Personales", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaPersonal.setEditable(false);
        txtaPersonal.setColumns(20);
        txtaPersonal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaPersonal.setLineWrap(true);
        txtaPersonal.setRows(5);
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
            .addComponent(jScrollPane3)
        );
        pnlPersonalLayout.setVerticalGroup(
            pnlPersonalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
        );

        pnlPharmacological.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Medicamentosos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaPharmacological.setColumns(20);
        txtaPharmacological.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaPharmacological.setLineWrap(true);
        txtaPharmacological.setRows(5);
        txtaPharmacological.setTabSize(0);
        txtaPharmacological.setWrapStyleWord(true);
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
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );
        pnlPharmacologicalLayout.setVerticalGroup(
            pnlPharmacologicalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        pnlFamily.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true), "Familiares", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 51, 102))); // NOI18N

        txtaFamily.setColumns(20);
        txtaFamily.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtaFamily.setLineWrap(true);
        txtaFamily.setRows(5);
        txtaFamily.setTabSize(0);
        txtaFamily.setWrapStyleWord(true);
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
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );
        pnlFamilyLayout.setVerticalGroup(
            pnlFamilyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );

        pnlButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSave.setForeground(new java.awt.Color(0, 51, 102));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save_enabled.png"))); // NOI18N
        btnSave.setText("Guardar");
        btnSave.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnSave.setContentAreaFilled(false);
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSave.setEnabled(false);
        btnSave.setNextFocusableComponent(btnCancel);
        btnSave.setOpaque(true);
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSaveMouseExited(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(0, 51, 102));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancel_enabled.png"))); // NOI18N
        btnCancel.setText("Cancelar");
        btnCancel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnCancel.setContentAreaFilled(false);
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCancel.setEnabled(false);
        btnCancel.setNextFocusableComponent(btnBack);
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

        btnModify.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnModify.setForeground(new java.awt.Color(0, 51, 102));
        btnModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit_enabled.png"))); // NOI18N
        btnModify.setText("Modificar");
        btnModify.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnModify.setContentAreaFilled(false);
        btnModify.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModify.setNextFocusableComponent(txtaPersonal);
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

        btnBack.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnBack.setForeground(new java.awt.Color(0, 51, 102));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/home_enabled.png"))); // NOI18N
        btnBack.setText("Volver");
        btnBack.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnBack.setContentAreaFilled(false);
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBack.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBack.setNextFocusableComponent(btnModify);
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
            .addGroup(pnlButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModify, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlButtonsLayout.setVerticalGroup(
            pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                .addComponent(btnModify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlGeneralLayout = new javax.swing.GroupLayout(pnlGeneral);
        pnlGeneral.setLayout(pnlGeneralLayout);
        pnlGeneralLayout.setHorizontalGroup(
            pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeneralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlFamily, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlToxic, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPersonal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSurgical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlPharmacological, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlGeneralLayout.createSequentialGroup()
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlGeneralLayout.setVerticalGroup(
            pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGeneralLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPersonal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSurgical, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPharmacological, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlToxic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnlFamily, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void txtaPersonalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaPersonalKeyPressed
    handleFocus(evt);
}//GEN-LAST:event_txtaPersonalKeyPressed

private void txtaSurgicalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaSurgicalKeyPressed
    handleFocus(evt);
}//GEN-LAST:event_txtaSurgicalKeyPressed

private void txtaToxicKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaToxicKeyPressed
    handleFocus(evt);
}//GEN-LAST:event_txtaToxicKeyPressed

    private void btnSaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseEntered
        setCustomFont(btnSave, true);
    }//GEN-LAST:event_btnSaveMouseEntered

    private void btnSaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseExited
        setCustomFont(btnSave, false);
    }//GEN-LAST:event_btnSaveMouseExited

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        antecedents = new Antecedents();
        antecedents.setPersonalAntecedents(this.txtaPersonal.getText());
        antecedents.setSurgicalAntecedents(this.txtaSurgical.getText());
        antecedents.setToxicAntecedents(this.txtaToxic.getText());
        patient.setAntecendents(antecedents);
        if (daoAntecedents.updateAntecedents(antecedents, patient.getDni())) {
            ValidationsAndMessages.showInfo(this, "Actualización Exitosa.");
            setFieldsState(false);
            this.btnSave.setEnabled(false);
            this.btnModify.setEnabled(true);
            patient.setAntecendents(antecedents);
        } else {
            ValidationsAndMessages.showError(this, "Actualización Fallida.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseEntered
        setCustomFont(btnCancel, true);
    }//GEN-LAST:event_btnCancelMouseEntered

    private void btnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseExited
        setCustomFont(btnCancel, false);
    }//GEN-LAST:event_btnCancelMouseExited

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.fillFields(patient);
        this.setFieldsState(false);
        this.btnSave.setEnabled(false);
        this.btnModify.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

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
        this.txtaPersonal.grabFocus();
        setFieldsState(true);
        this.btnSave.setEnabled(true);
        this.btnModify.setEnabled(false);
    }//GEN-LAST:event_btnModifyActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.exit();
    }//GEN-LAST:event_formWindowClosing

    private void txtaPharmacologicalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaPharmacologicalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaPharmacologicalKeyPressed

    private void txtaFamilyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtaFamilyKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaFamilyKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnModify;
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlFamily;
    private javax.swing.JPanel pnlGeneral;
    private javax.swing.JPanel pnlPersonal;
    private javax.swing.JPanel pnlPharmacological;
    private javax.swing.JPanel pnlSurgical;
    private javax.swing.JPanel pnlToxic;
    private javax.swing.JTextArea txtaFamily;
    private javax.swing.JTextArea txtaPersonal;
    private javax.swing.JTextArea txtaPharmacological;
    private javax.swing.JTextArea txtaSurgical;
    private javax.swing.JTextArea txtaToxic;
    // End of variables declaration//GEN-END:variables

    /**
     * Fills all antecedents fields.
     *
     * @param patient Patient with the antecedents
     */
    private void fillFields(Patient patient) {
        this.txtaPersonal.setText(patient.getAntecendents().getPersonalAntecedents());
        this.txtaToxic.setText(patient.getAntecendents().getToxicAntecedents());
        this.txtaSurgical.setText(patient.getAntecendents().getSurgicalAntecedents());
        this.txtaFamily.setText(patient.getAntecendents().getFamilyAntecedents());
        this.txtaPharmacological.setText(patient.getAntecendents().getPharmacologicalAntecedents());
    }

    /**
     * Sets the state of the fields(enabled,disabled).
     *
     * @param state true (enabled), false (disabled)
     */
    private void setFieldsState(boolean state) {
        this.txtaPersonal.setEditable(state);
        this.txtaToxic.setEditable(state);
        this.txtaSurgical.setEditable(state);
        this.txtaPersonal.setFocusable(state);
        this.txtaToxic.setFocusable(state);
        this.txtaSurgical.setFocusable(state);
        this.btnCancel.setEnabled(state);
    }

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
            this.getOwner().setVisible(true);
        } else {
            ValidationsAndMessages.validarSalidaVentana(this);
        }
    }
}
