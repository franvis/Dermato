package gui;

import utils.StyleManager;
import utils.FileManager;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import utils.ValidationsAndMessages;

/**
 *
 * @author Francisco Visintini
 */
public class ChooseColorJDialog extends javax.swing.JDialog {

    private int color = StyleManager.actualColor;

    /**
     * Allows to change style color.
     *
     * @param parent frame
     * @param modal true if modal dialog, false otherwise
     */
    public ChooseColorJDialog(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupInitialUi();
    }

    @Override
    public JRootPane getRootPane() {
        super.getRootPane().registerKeyboardAction((ActionEvent e) -> {
            exitWindow();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        return super.getRootPane();
    }

    public void exitWindow() {
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        rbtnYellow = new javax.swing.JRadioButton();
        rbtnBlue = new javax.swing.JRadioButton();
        rbtnPurple = new javax.swing.JRadioButton();
        rbtnPalidRose = new javax.swing.JRadioButton();
        rbtnOldRose = new javax.swing.JRadioButton();
        rbtnGreen = new javax.swing.JRadioButton();
        btnPreview = new javax.swing.JButton();
        btnApply = new javax.swing.JButton();
        rbtnDefault = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Color de Fondo");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        rbtnYellow.setBackground(new java.awt.Color(255, 252, 204));
        buttonGroup1.add(rbtnYellow);
        rbtnYellow.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtnYellow.setText("Amarillo");

        rbtnBlue.setBackground(new java.awt.Color(225, 225, 254));
        buttonGroup1.add(rbtnBlue);
        rbtnBlue.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtnBlue.setText("Azul");

        rbtnPurple.setBackground(new java.awt.Color(232, 179, 245));
        buttonGroup1.add(rbtnPurple);
        rbtnPurple.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtnPurple.setText("Lila");
        rbtnPurple.setMaximumSize(new java.awt.Dimension(45, 23));
        rbtnPurple.setMinimumSize(new java.awt.Dimension(45, 23));
        rbtnPurple.setPreferredSize(new java.awt.Dimension(45, 23));

        rbtnPalidRose.setBackground(new java.awt.Color(255, 204, 204));
        buttonGroup1.add(rbtnPalidRose);
        rbtnPalidRose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtnPalidRose.setText("Rosa Pálido");

        rbtnOldRose.setBackground(new java.awt.Color(255, 153, 153));
        buttonGroup1.add(rbtnOldRose);
        rbtnOldRose.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtnOldRose.setText("Rosa Viejo");

        rbtnGreen.setBackground(new java.awt.Color(179, 225, 194));
        buttonGroup1.add(rbtnGreen);
        rbtnGreen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtnGreen.setText("Verde");

        btnPreview.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnPreview.setText("Vista Previa");
        btnPreview.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnPreview.setContentAreaFilled(false);
        btnPreview.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        btnApply.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnApply.setText("Aplicar");
        btnApply.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        btnApply.setContentAreaFilled(false);
        btnApply.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtnDefault);
        rbtnDefault.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbtnDefault.setText("Color por defecto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnDefault, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnPreview, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbtnGreen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbtnOldRose, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbtnPalidRose, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtnYellow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbtnBlue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rbtnPurple, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnApply, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtnDefault, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rbtnYellow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnBlue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnPurple, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rbtnPalidRose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnOldRose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnGreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnApply, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
    this.preview();
}//GEN-LAST:event_btnPreviewActionPerformed

private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
    this.dispose();
    if (FileManager.saveColor(null, color)) {
        StyleManager.paint(getParent());
    } else {
        ValidationsAndMessages.showError(rootPane, "No se pudo actualizar el color."
                + " Por favor intente nuevamente mas tarde y si el error persiste"
                + " contactese con el administrador.");
    }
}//GEN-LAST:event_btnApplyActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exitWindow();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnPreview;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton rbtnBlue;
    private javax.swing.JRadioButton rbtnDefault;
    private javax.swing.JRadioButton rbtnGreen;
    private javax.swing.JRadioButton rbtnOldRose;
    private javax.swing.JRadioButton rbtnPalidRose;
    private javax.swing.JRadioButton rbtnPurple;
    private javax.swing.JRadioButton rbtnYellow;
    // End of variables declaration//GEN-END:variables

    private void preview() {
        if (rbtnDefault.isSelected()) {
            color = 0;
        }
        if (rbtnPalidRose.isSelected()) {
            color = 1;
        }
        if (rbtnOldRose.isSelected()) {
            color = 2;
        }
        if (rbtnGreen.isSelected()) {
            color = 3;
        }
        if (rbtnYellow.isSelected()) {
            color = 4;
        }
        if (rbtnBlue.isSelected()) {
            color = 5;
        }
        if (rbtnPurple.isSelected()) {
            color = 6;
        }
        utils.StyleManager.paintComponents(this, color);
    }

    private void setupInitialUi() {
        setLocationRelativeTo(getParent());
        StyleManager.paint(this);
        this.rbtnDefault.setBackground(new Color(240, 240, 240));
    }
}
