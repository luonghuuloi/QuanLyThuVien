/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.NhanVienDAO;
import com.poly.dao.TaiKhoanDAO;
import com.poly.entity.NhanVien;
import com.poly.entity.TaiKhoan;
import com.poly.utils.DialogHelper;
import com.poly.utils.XImage;
import java.awt.Color;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author ADMIN
 */
public class DangKy extends javax.swing.JFrame {

    /**
     * Creates new form DangKy
     */
    public DangKy() {
        initComponents();
        init();
    }
    TaiKhoanDAO dao = new TaiKhoanDAO();
    NhanVienDAO nvdao = new NhanVienDAO();

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(DangKy.this.DISPOSE_ON_CLOSE);
        loadCboTenNV();
        layMaNVTheoTen();
    }

    void loadCboTenNV() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<NhanVien> list = nvdao.select();
        for (NhanVien nv : list) {
            model.addElement(nv);
        }
        cboTenNV.setModel(model);
    }

    void layMaNVTheoTen() {
        NhanVien nv = (NhanVien) cboTenNV.getSelectedItem();
        txtMaNV.setText(String.valueOf(nv.getMaNhanVien()));
    }

    void dangKy() {
        if (check()) {
            TaiKhoan model = getForm();
            try {
                dao.insert(model);
                DialogHelper.alert(this, "Đăng ký tài khoản thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Đăng ký tài khoản thất bại");
            }
        }
    }

    TaiKhoan getForm() {
        TaiKhoan tk = new TaiKhoan();
        tk.setTenTaiKhoan(txtTenTK.getText());
        tk.setMatKhau(txtMatKhau.getText());
        tk.setMaNhanVien(txtMaNV.getText());
        tk.setVaiTro(rdoQuanLy.isSelected());
        return tk;
    }

    boolean check() {
          if (dao.findByMaNV(txtMaNV.getText()) != null) {
            DialogHelper.alert(this, "Nhân viên này đã có tài khoản");
            txtMaNV.requestFocus();
            return false;
        }
        if (txtTenTK.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa nhập tên tài khoản");
            txtTenTK.requestFocus();
            return false;
        } else if (txtMatKhau.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa nhập mật khẩu");
            txtMatKhau.requestFocus();
            return false;
        }
        return true;
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
        txtTenTK = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();
        cboTenNV = new javax.swing.JComboBox<>();
        txtMaNV = new javax.swing.JTextField();
        lblDangKy = new javax.swing.JLabel();
        lblHuyBo = new javax.swing.JLabel();
        rdoQuanLy = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        lblHinhNen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng ký");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTenTK.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtTenTK.setBorder(null);
        getContentPane().add(txtTenTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, 190, 20));

        txtMatKhau.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMatKhau.setBorder(null);
        getContentPane().add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, 190, 30));

        cboTenNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenNVActionPerformed(evt);
            }
        });
        getContentPane().add(cboTenNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 300, 240, 40));

        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMaNV.setBorder(null);
        getContentPane().add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 200, -1));

        lblDangKy.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDangKy.setText("Đăng Ký");
        lblDangKy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangKyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangKyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangKyMouseExited(evt);
            }
        });
        getContentPane().add(lblDangKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 490, -1, -1));

        lblHuyBo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblHuyBo.setText("Hủy Bỏ");
        lblHuyBo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHuyBoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHuyBoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHuyBoMouseExited(evt);
            }
        });
        getContentPane().add(lblHuyBo, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 490, -1, -1));

        buttonGroup1.add(rdoQuanLy);
        rdoQuanLy.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        rdoQuanLy.setText("Quản lý");
        rdoQuanLy.setBorder(null);
        getContentPane().add(rdoQuanLy, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, -1, -1));

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        rdoNhanVien.setSelected(true);
        rdoNhanVien.setText("Nhân viên");
        rdoNhanVien.setBorder(null);
        getContentPane().add(rdoNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 430, -1, -1));

        lblHinhNen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/dangky.png"))); // NOI18N
        getContentPane().add(lblHinhNen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboTenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenNVActionPerformed
        layMaNVTheoTen();
    }//GEN-LAST:event_cboTenNVActionPerformed

    private void lblDangKyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKyMouseClicked
        dangKy();
    }//GEN-LAST:event_lblDangKyMouseClicked

    private void lblDangKyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKyMouseEntered
        lblDangKy.setText("<html><b>Đăng Ký</b></html>");
        lblDangKy.setForeground(Color.red);
    }//GEN-LAST:event_lblDangKyMouseEntered

    private void lblDangKyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKyMouseExited
        lblDangKy.setText("<html>Đăng Ký</html>");
        lblDangKy.setForeground(Color.black);
    }//GEN-LAST:event_lblDangKyMouseExited

    private void lblHuyBoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHuyBoMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblHuyBoMouseClicked

    private void lblHuyBoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHuyBoMouseEntered
        lblHuyBo.setText("<html><b>Hủy Bỏ</b></html>");
        lblHuyBo.setForeground(Color.red);
    }//GEN-LAST:event_lblHuyBoMouseEntered

    private void lblHuyBoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHuyBoMouseExited
        lblHuyBo.setText("<html>Hủy Bỏ</html>");
        lblHuyBo.setForeground(Color.black);
    }//GEN-LAST:event_lblHuyBoMouseExited

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
            java.util.logging.Logger.getLogger(DangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangKy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DangKy().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboTenNV;
    private javax.swing.JLabel lblDangKy;
    private javax.swing.JLabel lblHinhNen;
    private javax.swing.JLabel lblHuyBo;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoQuanLy;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenTK;
    // End of variables declaration//GEN-END:variables
}
