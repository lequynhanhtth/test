/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import Validication.Check;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class InsertLuong extends javax.swing.JDialog{

    /**
     * Creates new form InsertLuong
     */
    public InsertLuong(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        lblHovaten.setVisible(false);
        lblLuongthang.setVisible(false);
        lblManv.setVisible(false);
        lblnocong.setVisible(false);
        lbltiencom.setVisible(false);
        lbltiendth.setVisible(false);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=QLNV", "sa", "123");
            pstInsert = con.prepareStatement("Insert into QLLuong  ([MaNV],[HVT],[LuongThang],[NoCong],[TienCom],[TienDth]) values (?,?,?,?,?,?)");
            st = con.createStatement();
            rs = st.executeQuery("Select Manv from Qlluong");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
          LoadCbo();
    }
    Connection con;
    PreparedStatement pstInsert;
    Statement st;
    ResultSet rs;

    public void LoadCbo() {
        try {
            PreparedStatement pstDetail = con.prepareStatement("Select Manv,HVT from QLThongtin", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            PreparedStatement pstDuplicate = con.prepareStatement("Select Manv from QLLuong", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rst = pstDetail.executeQuery();
            ResultSet rstQL = pstDuplicate.executeQuery();
            cboManv.removeAllItems();
            while (rst.next()) {
                rstQL.beforeFirst();
                boolean dublicate = false;
                while (rstQL.next()) {
                    if (rst.getString(1).equalsIgnoreCase(rstQL.getString(1))) {
                        dublicate = true;
                        break;
                    }
                }
                if (!dublicate) {

                    cboManv.addItem(rst.getString(1));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void checkHVT(StringBuilder sb) {
        if (Check.checkEmpty(txtHovaten, sb, "Vui lòng không để tróng họ và tên")) {
            lblHovaten.setVisible(false);
        } else {
            lblHovaten.setVisible(true);
        }
    }

    public void checkLuongthang(StringBuilder sb) {
        if (Check.checkEmpty(txtluongthang, sb, "Vui lòng không để tróng lương tháng")) {
            lblLuongthang.setVisible(false);
        } else {
            lblLuongthang.setVisible(true);
        }
    }

    public void checkNocong(StringBuilder sb) {
        if (Check.checkEmpty(txtnocong, sb, "Vui lòng không để tróng nợ công")) {
            lblnocong.setVisible(false);
        } else {
            lblnocong.setVisible(true);
        }
    }

    public void checkTiencom(StringBuilder sb) {
        if (Check.checkEmpty(txtTiencom, sb, "Vui lòng không để tróng tiền cơm")) {
            lbltiencom.setVisible(false);
        } else {
            lbltiencom.setVisible(true);
        }
    }

    public void checkTiendth(StringBuilder sb) {
        if (Check.checkEmpty(txttiendth, sb, "Vui lòng không để tróng tiền điện thoại")) {
            lbltiendth.setVisible(false);
        } else {
            lbltiendth.setVisible(true);
        }
    }

    public boolean check() {
        StringBuilder sb = new StringBuilder();

        checkHVT(sb);
        checkLuongthang(sb);
        checkNocong(sb);
        checkTiencom(sb);
        checkTiendth(sb);
        if (sb.length() != 0) {
            JOptionPane.showMessageDialog(this, sb, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }

//    public boolean checkDuplicate() {
//        try {
//            rs.beforeFirst();
//            while (rs.next()) {
//                String manv = rs.getString(1);
//                if (manv.equalsIgnoreCase(txtManv.getText())) {
//                    JOptionPane.showMessageDialog(this, "Mã nhân viên này đã nhập lương ");
//                    lblManv.setVisible(true);
//                    return false;
//                }
//            }
//            lblManv.setVisible(false);
//            return true;
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, e);
//            return false;
//        }
//    }
//    "Insert into QLLuong  ([MaNV],[HVT],[LuongThang],[NoCong],[TienCom],[TienDth]) values (?,?,?,?,?,?)"
    public void Insert() {
        try {
            pstInsert.setString(1, (String) cboManv.getSelectedItem());
            pstInsert.setString(2, txtHovaten.getText());
            pstInsert.setLong(3, Long.parseLong(txtluongthang.getText()));
            pstInsert.setLong(4, Long.parseLong(txtnocong.getText()));
            pstInsert.setLong(5, Long.parseLong(txtTiencom.getText()));
            pstInsert.setLong(6, Long.parseLong(txttiendth.getText()));
            pstInsert.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

//    public boolean checkExists() {
//        try {
//            Statement stthongtin = con.createStatement();
//            ResultSet rst = stthongtin.executeQuery("Select Manv from QLthongtin");
//            while (rst.next()) {
//                String manv = rst.getString(1);
//                if (manv.equalsIgnoreCase(txtManv.getText())) {            
//                    lblManv.setVisible(false);
//                    return true;
//                }
//            }
//            JOptionPane.showMessageDialog(this, "Ma nhân viên không tôn tại");
//            lblManv.setVisible(true);
//
//            return false;
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, e);
//            return true;
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtHovaten = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        txtluongthang = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtnocong = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtTiencom = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txttiendth = new javax.swing.JTextField();
        lblLuongthang = new javax.swing.JLabel();
        lblnocong = new javax.swing.JLabel();
        lbltiendth = new javax.swing.JLabel();
        lbltiencom = new javax.swing.JLabel();
        lblManv = new javax.swing.JLabel();
        lblHovaten = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnthoat = new javax.swing.JButton();
        cboManv = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Mã nhân viên");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Họ và tên");

        txtHovaten.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtHovaten.setCaretColor(new java.awt.Color(255, 255, 255));
        txtHovaten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtHovatenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtHovatenMouseExited(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)), "Lương", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(0, 0, 0));

        txtluongthang.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtluongthang.setCaretColor(new java.awt.Color(255, 255, 255));
        txtluongthang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtluongthangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtluongthangMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtluongthangMouseReleased(evt);
            }
        });
        txtluongthang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtluongthangActionPerformed(evt);
            }
        });
        txtluongthang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtluongthangKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Lương tháng :");

        txtnocong.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtnocong.setCaretColor(new java.awt.Color(255, 255, 255));
        txtnocong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtnocongMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtnocongMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtnocongMouseReleased(evt);
            }
        });
        txtnocong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnocongKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Nợ công :");

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Tiền cơm :");

        txtTiencom.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtTiencom.setCaretColor(new java.awt.Color(255, 255, 255));
        txtTiencom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtTiencomMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtTiencomMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtTiencomMouseReleased(evt);
            }
        });
        txtTiencom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTiencomKeyReleased(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 0));
        jLabel18.setText("Tiền điện thoại :");

        txttiendth.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txttiendth.setCaretColor(new java.awt.Color(255, 255, 255));
        txttiendth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txttiendthMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txttiendthMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txttiendthMouseReleased(evt);
            }
        });
        txttiendth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txttiendthKeyReleased(evt);
            }
        });

        lblLuongthang.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblnocong.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lbltiendth.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lbltiencom.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtluongthang, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(txtTiencom))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblLuongthang)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtnocong, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lbltiencom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txttiendth, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblnocong, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbltiendth, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txtluongthang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(txtnocong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblLuongthang)
                    .addComponent(lblnocong))
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txtTiencom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(txttiendth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbltiendth)
                    .addComponent(lbltiencom))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        lblManv.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblHovaten.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        btnthem.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnthoat.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnthoat.setText("Thoát");
        btnthoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthoatActionPerformed(evt);
            }
        });

        cboManv.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cboManv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboManvActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(cboManv, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblManv)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtHovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHovaten)
                        .addGap(101, 101, 101))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(btnthoat, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(115, 115, 115))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblManv)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(txtHovaten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboManv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHovaten))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthoat)
                    .addComponent(btnthem))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 820, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        if (check()) {
//            if (checkExists()) {
//                if (checkDuplicate()) {
            Insert();
//                }
//            }
        }
        LoadCbo();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnthoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthoatActionPerformed
        this.setVisible(false);
       
    }//GEN-LAST:event_btnthoatActionPerformed

    private void cboManvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboManvActionPerformed

        try {
            PreparedStatement pstDetail = con.prepareStatement("Select Manv,HVT from QLThongtin", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rst = pstDetail.executeQuery();
            rst.beforeFirst();
            while (rst.next()) {
                String manv = rst.getString(1);
                if (manv.equalsIgnoreCase((String) cboManv.getSelectedItem())) {
                    txtHovaten.setText(rst.getString(2));
                    break;
                }
            }
          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);

        }
    }//GEN-LAST:event_cboManvActionPerformed

    private void txttiendthKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txttiendthKeyReleased
        // TODO add your handling code here:
        if (!txttiendth.getText().matches("[0-9]+")) {
            String s = txttiendth.getText();
            s = s.replaceAll("[^\\d]+", "");
            txttiendth.setText(s);
        }
    }//GEN-LAST:event_txttiendthKeyReleased

    private void txttiendthMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txttiendthMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txttiendthMouseReleased

    private void txttiendthMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txttiendthMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txttiendthMouseExited

    private void txttiendthMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txttiendthMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txttiendthMouseEntered

    private void txtTiencomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTiencomKeyReleased
        // TODO add your handling code here:
        if (!txtTiencom.getText().matches("[0-9]+")) {
            String s = txtTiencom.getText();
            s = s.replaceAll("[^\\d]+", "");
            txtTiencom.setText(s);
        }
    }//GEN-LAST:event_txtTiencomKeyReleased

    private void txtTiencomMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTiencomMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTiencomMouseReleased

    private void txtTiencomMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTiencomMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTiencomMouseExited

    private void txtTiencomMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTiencomMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTiencomMouseEntered

    private void txtnocongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnocongKeyReleased
        // TODO add your handling code here:
        if (!txtnocong.getText().matches("[0-9]+")) {
            String s = txtnocong.getText();
            s = s.replaceAll("[^\\d]+", "");
            txtnocong.setText(s);
        }
    }//GEN-LAST:event_txtnocongKeyReleased

    private void txtnocongMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtnocongMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnocongMouseReleased

    private void txtnocongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtnocongMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnocongMouseExited

    private void txtnocongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtnocongMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnocongMouseEntered

    private void txtluongthangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtluongthangKeyReleased
        // TODO add your handling code here:
        if (!txtluongthang.getText().matches("[0-9]+")) {
            String s = txtluongthang.getText();
            s = s.replaceAll("[^\\d]+", "");
            txtluongthang.setText(s);
        }
    }//GEN-LAST:event_txtluongthangKeyReleased

    private void txtluongthangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtluongthangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtluongthangActionPerformed

    private void txtluongthangMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtluongthangMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtluongthangMouseReleased

    private void txtluongthangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtluongthangMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_txtluongthangMouseExited

    private void txtluongthangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtluongthangMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_txtluongthangMouseEntered

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        LoadCbo();
    }//GEN-LAST:event_formComponentShown

    private void txtHovatenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHovatenMouseEntered
        // TODO add your handling code her:
        txtHovaten.setEnabled(false);
    }//GEN-LAST:event_txtHovatenMouseEntered

    private void txtHovatenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtHovatenMouseExited
        // TODO add your handling code here:
        txtHovaten.setEnabled(true);
    }//GEN-LAST:event_txtHovatenMouseExited

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
            java.util.logging.Logger.getLogger(InsertLuong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsertLuong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsertLuong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsertLuong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InsertLuong dialog = new InsertLuong(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnthoat;
    private javax.swing.JComboBox<String> cboManv;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblHovaten;
    private javax.swing.JLabel lblLuongthang;
    private javax.swing.JLabel lblManv;
    private javax.swing.JLabel lblnocong;
    private javax.swing.JLabel lbltiencom;
    private javax.swing.JLabel lbltiendth;
    private javax.swing.JTextField txtHovaten;
    private javax.swing.JTextField txtTiencom;
    private javax.swing.JTextField txtluongthang;
    private javax.swing.JTextField txtnocong;
    private javax.swing.JTextField txttiendth;
    // End of variables declaration//GEN-END:variables
}
