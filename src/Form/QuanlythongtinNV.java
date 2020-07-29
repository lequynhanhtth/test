package Form;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Dialog.Doimatkhau;
import Dialog.Insert;
import Validication.Check;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import Validication.Icon;
import Validication.getdate;
import Validication.help;
import com.sun.imageio.plugins.jpeg.JPEG;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Administrator
 */
public class QuanlythongtinNV extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form QuanlythongtinNV
     */
    public QuanlythongtinNV() {
        initComponents();
        lblCMND.setVisible(false);
        lblDantoc.setVisible(false);
        lblDiachi.setVisible(false);
        lblManv.setVisible(false);
        lblNBD.setVisible(false);
        lblNgaysinh.setVisible(false);
        lblNoiSinh.setVisible(false);
        lblSDT.setVisible(false);
        lblhethanHD.setVisible(false);
        lbltuoi.setVisible(false);
        lblHovaten.setVisible(false);
        icon.SetIcon(btnthem, "Icon\\Add1.png", 64);
        icon.SetIcon(btnxoa, "Icon\\delete.png", 64);
        icon.SetIcon(btnLui, "Icon\\previous.png", 64);
        icon.SetIcon(btnTiep, "Icon\\next.png", 64);
        icon.SetIcon(btncapnhat, "Icon\\update.png", 64);
        setLocationRelativeTo(null);
        //conect to SQL
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=QLNV", "sa", "123");
            pstDelete = con.prepareStatement(sqldelete);
            pstUpdate = con.prepareStatement(sqlupdate);
            pstDetail = con.prepareStatement("Select * from QLthongtin", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rts = pstDetail.executeQuery();
            loadData();
            Fill = true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

    }
    Icon icon = new Icon();
    Connection con = null;
    Vector data = new Vector();

    String sqldelete = "delete from QLthongtin where Manv = ? ";
    String sqlupdate = "Update QLthongtin set HVT = ? , Ngaysinh = ? , GioiTinh = ? , NoiSinh = ? , CMND = ? , Dantoc = ? ,SDT=? , Diachi=  ? ,NgayBD= ? ,HethanHD=? , Hinh = ? , Tuoi = ?  where MaNV = ?  ";
    Vector Col = new Vector();
    Boolean Fill = false;
    PreparedStatement pstDelete;
    PreparedStatement pstUpdate;
    PreparedStatement pstDetail;
    ResultSet rts;
    String Hinh;
    String sqlfind = "";

    public void loadData() {

        String sql = "";
        if (sqlfind.length() == 0) {
            sql = "Select MaNV , HVT from QLThongtin";
        } else {
            sql = "Select Manv , HVT from QLthongtin" + sqlfind;
        }
        data.clear();
        try {
            rts = pstDetail.executeQuery();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (!Fill) {
                ResultSetMetaData rsmd = rs.getMetaData();
                Col.add(rsmd.getColumnName(1));
                Col.add(rsmd.getColumnName(2));
            }
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString(1));
                v.add(rs.getString(2));
                data.add(v);
            }
            TableModel model = new DefaultTableModel(data, Col);
            tblNhanvien.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void Delete() {
        try {
            rts.beforeFirst();
            boolean delete = false;
            while (rts.next()) {
                if (rts.getString(1).equalsIgnoreCase(txtManv.getText())) {
                    int i = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa nhân viên có mã :" + rts.getString(1), "Delete", JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        pstDelete.setString(1, rts.getString(1));
                        pstDelete.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Xóa thành công");
                        delete = true;
                    } else {
                        delete = true;
                    }
                }
            }
            if (delete == false) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void checkManv(StringBuilder sb) {
        if (Check.checkEmpty(txtManv, sb, "Không để tróng mã nhân viên\n")) {
            if (Check.checkKTDB(txtManv, sb, "Mã nhân viên không được dùng ký tự đặc biệt\n")) {
                lblSDT.setVisible(false);
            } else {
                lblManv.setVisible(true);
            }
        } else {
            lblManv.setVisible(true);
        }

    }

    public void checkHVT(StringBuilder sb) {
        if (Check.checkEmpty(txtHovaten, sb, "Không để tróng Họ và tên\n")) {
            lblHovaten.setVisible(false);
        } else {
            lblHovaten.setVisible(true);
        }

    }

    public void checkNgaySinh(StringBuilder sb) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String date = format.format(txtNgaysinh.getDate());
            lblNgaysinh.setVisible(false);
        } catch (Exception e) {
            sb.append("Không để tróng ngày sinh !!!! \n");
            lblNgaysinh.setVisible(true);
        }

    }

    public void checkBatdau(StringBuilder sb) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String date = format.format(txtngaybatdau.getDate());
            lblNBD.setVisible(false);

        } catch (Exception e) {
            sb.append("Không để tróng ngày bắt đầu !!!! \n");
            lblNBD.setVisible(true);
        }

    }

    public void checkHethanHD(StringBuilder sb) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String date = format.format(txthethanhd.getDate());
            lblhethanHD.setVisible(false);

        } catch (Exception e) {
            sb.append("Không để tróng hết hạn HĐ !!!! \n");
            lblhethanHD.setVisible(true);
        }

    }

    public void checkTuoi(StringBuilder sb) {
        if (Check.checkEmpty(txtTuoi, sb, "Không để tróng tuổi !!!! \n")) {
            try {
                int t = Integer.parseInt(txtTuoi.getText());
                if (t < 18) {
                    sb.append("Tuổi phải lớn hơn 18 \n");
                    lbltuoi.setVisible(true);
                } else {
                    lbltuoi.setVisible(false);
                }
            } catch (Exception e) {
                sb.append("Tuổi không đúng định dạng \n");
                lbltuoi.setVisible(true);
            }
        } else {
            lbltuoi.setVisible(true);
        }

    }

    public void CheckCMND(StringBuilder sb) {
        if (Check.checkEmpty(txtCMND, sb, "Không được để tróng CMND \n")) {
            if (txtCMND.getText().length() != 10) {
                sb.append("CMND không phù hợp \n");
                lblCMND.setVisible(true);
            } else {
                try {
                    long cmnd = Long.parseLong(txtCMND.getText());
                    lblCMND.setVisible(false);
                } catch (Exception e) {
                    sb.append("CMND Không phù hợp \n");
                    lblCMND.setVisible(true);
                }
            }

        } else {
            lblCMND.setVisible(true);
        }
    }

    public void CheckNoiSinh(StringBuilder sb) {
        if (Check.checkEmpty(txtNoiSinh, sb, "Không để tróng nơi sinh")) {
            lblNoiSinh.setVisible(false);
        } else {
            lblNoiSinh.setVisible(true);
        }

    }

    public void CheckDantoc(StringBuilder sb) {
        if (Check.checkEmpty(txtDantoc, sb, "Không để tróng dân tộc")) {
            lblDantoc.setVisible(false);
        } else {
            lblDantoc.setVisible(true);
        }

    }

    public void checkSDT(StringBuilder sb) {
        if (Check.checkEmpty(txtDienthoai, sb, "Không được để tróng SĐT \n")) {
            if (txtDienthoai.getText().length() < 10 || txtDienthoai.getText().length() > 12) {
                sb.append("SĐT không phù hợp \n");
                lblSDT.setVisible(false);
            }
            try {
                long cmnd = Long.parseLong(txtDienthoai.getText());
                lblSDT.setVisible(false);
            } catch (Exception e) {
                sb.append("SĐT Không phù hợp \n");
                lblSDT.setVisible(false);
            }

        } else {
            lblSDT.setVisible(true);
        }
    }

    public void CheckDiachi(StringBuilder sb) {
        if (Check.checkEmpty(txtDiachi, sb, "Không để tróng địa chỉ")) {
            lblDiachi.setVisible(false);
        } else {
            lblDiachi.setVisible(true);
        }

    }

    public boolean checkImage(StringBuilder sb) {

        Icon.SetImage(lblHinh, Hinh, 205, 159, sb);

        if (sb.length() != 0) {
            return false;
        }
        return true;
    }

    public boolean checkImage() {
        StringBuilder sb = new StringBuilder();
        Icon.SetImage(lblHinh, Hinh, 205, 159, sb);
        if (sb.length() != 0) {
            return false;
        }
        return true;
    }

    public boolean check() {
        StringBuilder sb = new StringBuilder();
        checkManv(sb);
        checkHVT(sb);
        checkNgaySinh(sb);
        checkTuoi(sb);
        CheckCMND(sb);
        CheckDiachi(sb);
        CheckDantoc(sb);
        checkSDT(sb);
        CheckNoiSinh(sb);
        checkBatdau(sb);
        checkHethanHD(sb);
        if (sb.length() != 0) {
            return false;
        } else {
            return true;
        }
    }

    //"Update QLthongtin set HVT = ? , Ngaysinh = ? , GioiTinh = ? , NoiSinh = ? , CMND = ? , Dantoc = ? ,SDT=? , Diachi=  ? ,NgayBD= ? ,HethanHD=? , Hinh = ?  where MaNV = ?  ";
    public void update() {
        try {
            pstUpdate.setString(1, txtHovaten.getText());
            pstUpdate.setString(2, getdate.Xdate(txtNgaysinh));
            pstUpdate.setString(3, (String) cboGioitinh.getSelectedItem());
            pstUpdate.setString(4, txtNoiSinh.getText());
            pstUpdate.setString(5, txtCMND.getText());
            pstUpdate.setString(6, txtDantoc.getText());
            pstUpdate.setString(7, txtDienthoai.getText());
            pstUpdate.setString(8, txtDiachi.getText());
            pstUpdate.setString(9, getdate.Xdate(txtngaybatdau));
            pstUpdate.setString(10, getdate.Xdate(txthethanhd));
            pstUpdate.setString(11, Hinh);
            pstUpdate.setString(12, txtTuoi.getText());
            pstUpdate.setString(13, txtManv.getText());
            pstUpdate.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void click() {

        try {
            rts.beforeFirst();
            int row = tblNhanvien.getSelectedRow();
            String Manv = (String) tblNhanvien.getValueAt(row, 0);
            while (rts.next()) {

                if (rts.getString(1).equalsIgnoreCase(Manv)) {

                    txtManv.setText(rts.getString(1));
                    txtHovaten.setText(rts.getString(2));
                    txtNgaysinh.setDate(getdate.getXdate(rts.getString(3)));
                    txtTuoi.setText(rts.getString(4));
                    cboGioitinh.setSelectedItem(rts.getString(5));
                    txtCMND.setText(rts.getString(7));
                    txtNoiSinh.setText(rts.getString(6));
                    txtDantoc.setText(rts.getString(8));
                    txtDienthoai.setText(rts.getString(9));
                    txtDiachi.setText(rts.getString(10));
                    txtngaybatdau.setDate(getdate.getXdate(rts.getString(11)));
                    txthethanhd.setDate(getdate.getXdate(rts.getString(12)));
                    Hinh = rts.getString(13);
                    checkImage();
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void next() {
        try {
            rts.next();
            if (rts.isAfterLast()) {
                rts.beforeFirst();
                rts.next();
                txtManv.setText(rts.getString(1));
                txtHovaten.setText(rts.getString(2));
                txtNgaysinh.setDate(getdate.getXdate(rts.getString(3)));
                txtTuoi.setText(rts.getString(4));
                cboGioitinh.setSelectedItem(rts.getString(5));
                txtCMND.setText(rts.getString(7));
                txtNoiSinh.setText(rts.getString(6));
                txtDantoc.setText(rts.getString(8));
                txtDienthoai.setText(rts.getString(9));
                txtDiachi.setText(rts.getString(10));
                txtngaybatdau.setDate(getdate.getXdate(rts.getString(11)));
                txthethanhd.setDate(getdate.getXdate(rts.getString(12)));
                Hinh = rts.getString(13);
                checkImage();
            } else {
                txtManv.setText(rts.getString(1));
                txtHovaten.setText(rts.getString(2));
                txtNgaysinh.setDate(getdate.getXdate(rts.getString(3)));
                txtTuoi.setText(rts.getString(4));
                cboGioitinh.setSelectedItem(rts.getString(5));
                txtCMND.setText(rts.getString(7));
                txtNoiSinh.setText(rts.getString(6));
                txtDantoc.setText(rts.getString(8));
                txtDienthoai.setText(rts.getString(9));
                txtDiachi.setText(rts.getString(10));
                txtngaybatdau.setDate(getdate.getXdate(rts.getString(11)));
                txthethanhd.setDate(getdate.getXdate(rts.getString(12)));
                Hinh = rts.getString(13);
                checkImage();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void previous() {
        try {
            rts.previous();
            if (rts.isBeforeFirst()) {
                rts.afterLast();
                rts.previous();
                txtManv.setText(rts.getString(1));
                txtHovaten.setText(rts.getString(2));
                txtNgaysinh.setDate(getdate.getXdate(rts.getString(3)));
                txtTuoi.setText(rts.getString(4));
                cboGioitinh.setSelectedItem(rts.getString(5));
                txtCMND.setText(rts.getString(7));
                txtNoiSinh.setText(rts.getString(6));
                txtDantoc.setText(rts.getString(8));
                txtDienthoai.setText(rts.getString(9));
                txtDiachi.setText(rts.getString(10));
                txtngaybatdau.setDate(getdate.getXdate(rts.getString(11)));
                txthethanhd.setDate(getdate.getXdate(rts.getString(12)));
                Hinh = rts.getString(13);
                checkImage();
            } else {
                txtManv.setText(rts.getString(1));
                txtHovaten.setText(rts.getString(2));
                txtNgaysinh.setDate(getdate.getXdate(rts.getString(3)));
                txtTuoi.setText(rts.getString(4));
                cboGioitinh.setSelectedItem(rts.getString(5));
                txtCMND.setText(rts.getString(7));
                txtNoiSinh.setText(rts.getString(6));
                txtDantoc.setText(rts.getString(8));
                txtDienthoai.setText(rts.getString(9));
                txtDiachi.setText(rts.getString(10));
                txtngaybatdau.setDate(getdate.getXdate(rts.getString(11)));
                txthethanhd.setDate(getdate.getXdate(rts.getString(12)));
                Hinh = rts.getString(13);
                checkImage();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void find() {
        String dm = (String) cboTim.getSelectedItem();
        sqlfind = " where " + dm + " like N'%" + txtTim.getText() + "%'";
        JOptionPane.showMessageDialog(this, sqlfind);

    }

    public void MouseRelease() {
        if (tblNhanvien.getCellEditor() != null) {
            tblNhanvien.getCellEditor().cancelCellEditing();
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

        FileHinh = new javax.swing.JFileChooser();
        FileExcel = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnTiep = new javax.swing.JButton();
        btnLui = new javax.swing.JButton();
        btncapnhat = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtTim = new javax.swing.JTextField();
        cboTim = new javax.swing.JComboBox<>();
        btnTim = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtManv = new javax.swing.JTextField();
        txtHovaten = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblHinh = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNgaysinh = new com.toedter.calendar.JDateChooser();
        txtTuoi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cboGioitinh = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtNoiSinh = new javax.swing.JTextField();
        txtCMND = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDantoc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDienthoai = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDiachi = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblNgaysinh = new javax.swing.JLabel();
        lblNoiSinh = new javax.swing.JLabel();
        lblCMND = new javax.swing.JLabel();
        lbltuoi = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        lblDantoc = new javax.swing.JLabel();
        lblDiachi = new javax.swing.JLabel();
        lblhethanHD = new javax.swing.JLabel();
        lblNBD = new javax.swing.JLabel();
        txtngaybatdau = new com.toedter.calendar.JDateChooser();
        txthethanhd = new com.toedter.calendar.JDateChooser();
        btnChon = new javax.swing.JButton();
        lblManv = new javax.swing.JLabel();
        lblHovaten = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanvien = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MnThaydoi = new javax.swing.JMenuItem();
        mndmk = new javax.swing.JMenuItem();
        mnExcel = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnnext = new javax.swing.JMenuItem();
        mnprevious = new javax.swing.JMenuItem();
        mninsert = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        FileExcel.setApproveButtonText("");
        FileExcel.setApproveButtonToolTipText("");
        FileExcel.setFileHidingEnabled(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(61, 193, 211));

        jPanel1.setBackground(new java.awt.Color(119, 139, 235));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("QUẢN LÝ THÔNG TIN NHÂN VIÊN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(256, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(182, 182, 182))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(99, 205, 218));

        btnthem.setContentAreaFilled(false);
        btnthem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnthemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnthemMouseExited(evt);
            }
        });
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnxoa.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\Delete.png")); // NOI18N
        btnxoa.setBorderPainted(false);
        btnxoa.setContentAreaFilled(false);
        btnxoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnxoaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnxoaMouseExited(evt);
            }
        });
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btnTiep.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\next.png")); // NOI18N
        btnTiep.setBorderPainted(false);
        btnTiep.setContentAreaFilled(false);
        btnTiep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTiepMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTiepMouseExited(evt);
            }
        });
        btnTiep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiepActionPerformed(evt);
            }
        });

        btnLui.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\previous.png")); // NOI18N
        btnLui.setBorderPainted(false);
        btnLui.setContentAreaFilled(false);
        btnLui.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLuiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLuiMouseExited(evt);
            }
        });
        btnLui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuiActionPerformed(evt);
            }
        });

        btncapnhat.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\update.png")); // NOI18N
        btncapnhat.setBorderPainted(false);
        btncapnhat.setContentAreaFilled(false);
        btncapnhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btncapnhatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btncapnhatMouseExited(evt);
            }
        });
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTiep, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnTiep, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        jPanel3.setBackground(new java.awt.Color(61, 193, 211));

        jLabel1.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_people_100px.png")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        txtTim.setBackground(new java.awt.Color(204, 204, 204));
        txtTim.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        cboTim.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cboTim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MaNV", "HVT" }));

        btnTim.setBackground(new java.awt.Color(153, 153, 153));
        btnTim.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnTim.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_search_36px.png")); // NOI18N
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Mã nhân viên");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Họ và tên");

        txtManv.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtManv.setCaretColor(new java.awt.Color(255, 255, 255));
        txtManv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtManvMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtManvMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtManvMouseReleased(evt);
            }
        });

        txtHovaten.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtHovaten.setCaretColor(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)), "Thông tin chi tiết", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 18), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(0, 0, 0));

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Ngày sinh:");

        txtNgaysinh.setDateFormatString("dd/MM/ yyyy");
        txtNgaysinh.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        txtTuoi.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtTuoi.setCaretColor(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tuổi:");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Giới tính:");

        cboGioitinh.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cboGioitinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nử", " " }));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nơi Sinh:");

        txtNoiSinh.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtNoiSinh.setCaretColor(new java.awt.Color(255, 255, 255));

        txtCMND.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtCMND.setCaretColor(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("CMND:");

        txtDantoc.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtDantoc.setCaretColor(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Dân Tộc:");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("SĐT:");

        txtDienthoai.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtDienthoai.setCaretColor(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Địa chỉ:");

        txtDiachi.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtDiachi.setCaretColor(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Ngày bắt đầu:");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Hết hạn HĐ:");

        lblNgaysinh.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblNoiSinh.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblCMND.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lbltuoi.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblSDT.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblDantoc.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblDiachi.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblhethanHD.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblNBD.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        txtngaybatdau.setDateFormatString("dd/MM/ yyyy");
        txtngaybatdau.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        txthethanhd.setDateFormatString("dd/MM/ yyyy");
        txthethanhd.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        btnChon.setText("Import Image");
        btnChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(txtngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNBD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txtNgaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNgaysinh)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbltuoi)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(cboGioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCMND))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txthethanhd, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtNoiSinh, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(txtDantoc, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblDantoc)
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtDienthoai))
                                    .addComponent(txtDiachi))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNoiSinh)
                                    .addComponent(lblSDT)
                                    .addComponent(lblhethanHD)
                                    .addComponent(lblDiachi)
                                    .addComponent(lblCMND))))))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(btnChon))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbltuoi)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNgaysinh)
                                    .addComponent(txtNgaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))))
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(cboGioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9)
                                        .addComponent(txtCMND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblCMND))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtNoiSinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblNoiSinh, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(txtDantoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(txtDienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblDantoc)))
                    .addComponent(lblSDT, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(lblNBD)
                            .addComponent(txtngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txthethanhd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(lblDiachi)
                        .addGap(18, 18, 18)
                        .addComponent(lblhethanHD)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnChon)
                .addGap(65, 65, 65))
        );

        lblManv.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        lblHovaten.setIcon(new javax.swing.ImageIcon("F:\\Java\\ProjecAss\\Icon\\icons8_high_priority_30px.png")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboTim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblManv)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtHovaten, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblHovaten))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(btnTim)))
                .addContainerGap(53, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboTim, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblManv)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHovaten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHovaten))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblNhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MãNV", "Họ và tên"
            }
        ));
        tblNhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanvienMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblNhanvienMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanvien);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setForeground(new java.awt.Color(0, 0, 0));

        jMenu1.setText("File");

        MnThaydoi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        MnThaydoi.setText("Thay đổi tài khoản");
        MnThaydoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnThaydoiActionPerformed(evt);
            }
        });
        jMenu1.add(MnThaydoi);

        mndmk.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        mndmk.setText("Đổi mật khẩu");
        jMenu1.add(mndmk);

        mnExcel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mnExcel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        mnExcel.setText("Export to Excel");
        mnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExcelActionPerformed(evt);
            }
        });
        jMenu1.add(mnExcel);
        jMenu1.add(jSeparator1);

        mnExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mnExit.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        mnExit.setText("Exit");
        mnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        mnnext.setText("Next");
        mnnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnnextActionPerformed(evt);
            }
        });
        jMenu2.add(mnnext);

        mnprevious.setText("Back");
        jMenu2.add(mnprevious);

        mninsert.setText("Insert");
        mninsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mninsertActionPerformed(evt);
            }
        });
        jMenu2.add(mninsert);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem1.setText("About");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemMouseEntered
        // TODO add your handling code here:
        icon.SetIcon(btnthem, "Icon\\Add.png", 64);
    }//GEN-LAST:event_btnthemMouseEntered

    private void btnthemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnthemMouseExited
        // TODO add your handling code here:
        icon.SetIcon(btnthem, "Icon\\Add1.png", 64);
    }//GEN-LAST:event_btnthemMouseExited

    private void btnxoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoaMouseEntered
        // TODO add your handling code here:
        icon.SetIcon(btnxoa, "Icon\\delete1.png", 64);
    }//GEN-LAST:event_btnxoaMouseEntered

    private void btnxoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnxoaMouseExited
        // TODO add your handling code here:
        icon.SetIcon(btnxoa, "Icon\\delete.png", 64);
    }//GEN-LAST:event_btnxoaMouseExited

    private void btnTiepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTiepMouseEntered
        // TODO add your handling code here:
        icon.SetIcon(btnTiep, "Icon\\next1.png", 64);
    }//GEN-LAST:event_btnTiepMouseEntered

    private void btnTiepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTiepMouseExited
        // TODO add your handling code here:
        icon.SetIcon(btnTiep, "Icon\\next.png", 64);
    }//GEN-LAST:event_btnTiepMouseExited

    private void btnLuiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuiMouseEntered
        // TODO add your handling code here:
        icon.SetIcon(btnLui, "Icon\\previous1.png", 64);
    }//GEN-LAST:event_btnLuiMouseEntered

    private void btnLuiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuiMouseExited
        // TODO add your handling code here:
        icon.SetIcon(btnLui, "Icon\\previous.png", 64);
    }//GEN-LAST:event_btnLuiMouseExited

    private void btncapnhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncapnhatMouseEntered
        // TODO add your handling code here:
        icon.SetIcon(btncapnhat, "Icon\\update1.png", 64);
    }//GEN-LAST:event_btncapnhatMouseEntered

    private void btncapnhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btncapnhatMouseExited
        // TODO add your handling code here:
        icon.SetIcon(btncapnhat, "Icon\\update.png", 64);
    }//GEN-LAST:event_btncapnhatMouseExited

    private void btnChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonActionPerformed
        // TODO add your handling code here:
        if (FileHinh.showOpenDialog(null) == FileHinh.APPROVE_OPTION) {
            Hinh = FileHinh.getSelectedFile().toString();
            if (!checkImage()) {
                JOptionPane.showMessageDialog(this, "Đây không phải là file PNG");
                Hinh = "";
            }
        }
    }//GEN-LAST:event_btnChonActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        Insert insert = new Insert(this, true);
        insert.setVisible(true);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (!insert.isVisible()) {
                            loadData();
                            Hinh = "";
                            break;
                        }
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        }.start();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        // TODO add your handling code here:
        Delete();
        Hinh = "";
        loadData();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        // TODO add your handling code here:
        update();
        loadData();
        Hinh = "";
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnTiepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiepActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnTiepActionPerformed

    private void btnLuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuiActionPerformed
        // TODO add your handling code here:
        previous();
    }//GEN-LAST:event_btnLuiActionPerformed

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel6MouseClicked

    private void tblNhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanvienMouseClicked
        // TODO add your handling code here:
        click();

    }//GEN-LAST:event_tblNhanvienMouseClicked

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        find();
        loadData();


    }//GEN-LAST:event_btnTimActionPerformed

    private void txtManvMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManvMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtManvMouseReleased

    private void txtManvMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManvMouseEntered
        // TODO add your handling code here:
        txtManv.setEnabled(false);
    }//GEN-LAST:event_txtManvMouseEntered

    private void txtManvMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtManvMouseExited
        // TODO add your handling code here:
        txtManv.setEnabled(true);
    }//GEN-LAST:event_txtManvMouseExited

    private void tblNhanvienMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanvienMouseReleased
        // TODO add your handling code here:
        MouseRelease();
    }//GEN-LAST:event_tblNhanvienMouseReleased

    private void MnThaydoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnThaydoiActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        DangNhap dn = new DangNhap();
        dn.setVisible(true);
    }//GEN-LAST:event_MnThaydoiActionPerformed

    private void mnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExcelActionPerformed
        // TODO add your handling code here:
        if (FileExcel.showSaveDialog(null) == FileExcel.APPROVE_OPTION) {
            String s = FileExcel.getSelectedFile().toString() + ".xlsx";
            File file = new File(s);
            help.ExportExcel(file, rts);
        }
    }//GEN-LAST:event_mnExcelActionPerformed

    private void mnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExitActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thoát", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            try {
                con.close();
                pstDelete.close();
                pstDetail.close();
                pstUpdate.close();
                rts.close();
            } catch (SQLException ex) {
                Logger.getLogger(QuanlythongtinNV.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.exit(0);
        }
    }//GEN-LAST:event_mnExitActionPerformed

    private void mnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnnextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_mnnextActionPerformed

    private void mninsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mninsertActionPerformed
        // TODO add your handling code here:
        Insert insert = new Insert(this, true);
        insert.setVisible(true);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (!insert.isVisible()) {
                            loadData();
                            Hinh = "";
                            break;
                        }
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        }.start();

    }//GEN-LAST:event_mninsertActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        StringBuilder sb = new StringBuilder();
        sb.append("Tên đăng nhập : " + DangNhap.tendangnhap);
        sb.append("\n");
        sb.append("Mật khẩu : " + DangNhap.matkhau);
        sb.append("\n");
        sb.append("Chức vụ : " + DangNhap.chucvu);
        sb.append("\n");
        JOptionPane.showMessageDialog(this, sb, "Information", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(QuanlythongtinNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanlythongtinNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanlythongtinNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanlythongtinNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanlythongtinNV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser FileExcel;
    private javax.swing.JFileChooser FileHinh;
    private javax.swing.JMenuItem MnThaydoi;
    private javax.swing.JButton btnChon;
    private javax.swing.JButton btnLui;
    private javax.swing.JButton btnTiep;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cboGioitinh;
    private javax.swing.JComboBox<String> cboTim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblCMND;
    private javax.swing.JLabel lblDantoc;
    private javax.swing.JLabel lblDiachi;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblHovaten;
    private javax.swing.JLabel lblManv;
    private javax.swing.JLabel lblNBD;
    private javax.swing.JLabel lblNgaysinh;
    private javax.swing.JLabel lblNoiSinh;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblhethanHD;
    private javax.swing.JLabel lbltuoi;
    private javax.swing.JMenuItem mnExcel;
    private javax.swing.JMenuItem mnExit;
    private javax.swing.JMenuItem mndmk;
    private javax.swing.JMenuItem mninsert;
    private javax.swing.JMenuItem mnnext;
    private javax.swing.JMenuItem mnprevious;
    private javax.swing.JTable tblNhanvien;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextField txtDantoc;
    private javax.swing.JTextField txtDiachi;
    private javax.swing.JTextField txtDienthoai;
    private javax.swing.JTextField txtHovaten;
    private javax.swing.JTextField txtManv;
    private com.toedter.calendar.JDateChooser txtNgaysinh;
    private javax.swing.JTextField txtNoiSinh;
    private javax.swing.JTextField txtTim;
    private javax.swing.JTextField txtTuoi;
    private com.toedter.calendar.JDateChooser txthethanhd;
    private com.toedter.calendar.JDateChooser txtngaybatdau;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
