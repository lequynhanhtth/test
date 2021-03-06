/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import Form.DangNhap;
import Validication.Check;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class Doimatkhau extends javax.swing.JDialog {

    /**
     * Creates new form
     *
     */
    public Doimatkhau(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        lblConfirm.setVisible(false);
        lblpass.setVisible(false);
    }

    public boolean check() {
        StringBuilder sb = new StringBuilder();
        if (Check.checkEmpty(txtpass, sb, "Vui lòng nhập mật khẩu muốn đổi")) {
            if (Check.checkKTDB(txtpass, sb, "Mật khẩu không được dùng ký tự đặc biệt")) {
                lblpass.setVisible(true);
            } else {
                lblpass.setVisible(false);
            }
        } else {
            lblpass.setVisible(false);
        }
        if (sb.length() != 0) {
            JOptionPane.showMessageDialog(this, sb, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }

    }

    public boolean checkconfirm() {
        if (txtpass.getText().equals(txtconfirm.getText())) {
            lblConfirm.setVisible(false);
            return true;
        } else {
            lblConfirm.setVisible(true);
            return false;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnDangKy = new javax.swing.JButton();
        btnthoat = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtpass = new javax.swing.JPasswordField();
        txtconfirm = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        lblConfirm = new javax.swing.JLabel();
        lblpass = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(83, 92, 104));
        jPanel1.setForeground(new java.awt.Color(83, 92, 104));

        jLabel3.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_lock_48px.png")); // NOI18N

        jPanel2.setBackground(new java.awt.Color(47, 54, 64));

        btnDangKy.setBackground(new java.awt.Color(51, 51, 51));
        btnDangKy.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnDangKy.setForeground(new java.awt.Color(255, 255, 255));
        btnDangKy.setText("Đổi mật khẩu");
        btnDangKy.setAlignmentY(0.0F);
        btnDangKy.setBorder(null);
        btnDangKy.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKyActionPerformed(evt);
            }
        });

        btnthoat.setBackground(new java.awt.Color(51, 51, 51));
        btnthoat.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnthoat.setForeground(new java.awt.Color(255, 255, 255));
        btnthoat.setText("Thoát");
        btnthoat.setAlignmentY(0.0F);
        btnthoat.setBorder(null);
        btnthoat.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnthoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(238, 238, 238)
                .addComponent(btnDangKy, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnthoat, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDangKy, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnthoat, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Đổi mật khẩu");

        txtpass.setBackground(new java.awt.Color(149, 175, 192));
        txtpass.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        txtpass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpassKeyReleased(evt);
            }
        });

        txtconfirm.setBackground(new java.awt.Color(149, 175, 192));
        txtconfirm.setFont(new java.awt.Font("Dialog", 0, 20)); // NOI18N
        txtconfirm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtconfirmKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtconfirmKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_reset_48px.png")); // NOI18N

        lblConfirm.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_36px.png")); // NOI18N

        lblpass.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_36px.png")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtconfirm))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblConfirm)
                            .addComponent(lblpass))
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(139, 139, 139))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblpass)
                            .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblConfirm)
                            .addComponent(txtconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtconfirmKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtconfirmKeyReleased
        checkconfirm();
    }//GEN-LAST:event_txtconfirmKeyReleased

    private void txtconfirmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtconfirmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtconfirmKeyPressed

    private void txtpassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpassKeyReleased
        checkconfirm();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpassKeyReleased

    private void btnthoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthoatActionPerformed
        // TODO add your handling code here:
        setVisible(false);
    }//GEN-LAST:event_btnthoatActionPerformed

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        // TODO add your handling code here:
        if (check()) {
            if (checkconfirm()) {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=QLNV", "sa", "123");
                    PreparedStatement pst = con.prepareStatement("Update Users set Password = ? where Username = ? ");
                    pst.setString(1, txtpass.getText());
                    pst.setString(2, DangNhap.tendangnhap);
                    pst.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu không giống nhau", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btnDangKyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Doimatkhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Doimatkhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Doimatkhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Doimatkhau.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Doimatkhau dialog = new Doimatkhau(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangKy;
    private javax.swing.JButton btnthoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblConfirm;
    private javax.swing.JLabel lblpass;
    private javax.swing.JPasswordField txtconfirm;
    private javax.swing.JPasswordField txtpass;
    // End of variables declaration//GEN-END:variables
}
