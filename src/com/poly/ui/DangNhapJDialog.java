/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.TaiKhoanDAO;
import com.poly.entity.TaiKhoan;
import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import java.awt.Color;
import java.awt.Cursor;

/**
 *
 * @author ADMIN
 */
public class DangNhapJDialog extends javax.swing.JDialog {
    TaiKhoanDAO dao = new TaiKhoanDAO();
    /**
     * Creates new form DangNhapJDialog
     */
    public DangNhapJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    
     public void init() {
        setLocationRelativeTo(null);
        lblDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblThoat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        setDefaultCloseOperation(DangNhapJDialog.this.DO_NOTHING_ON_CLOSE);
    }

    void login() {
        if (check()) {
            String taiKhoan = lblTaiKhoan.getText();
            String matKhau = new String(lblMatKhau.getPassword());
            TaiKhoan tk = dao.findById(taiKhoan);
            if (tk == null) {
                DialogHelper.alert(this, "Sai tên tài khoản");
            } else if (!matKhau.equals(tk.getMatKhau())) {
                DialogHelper.alert(this, "Sai mật khẩu");
            } else {
                Auth.user = tk;
                this.dispose();
            }
        }
    }

    void exit() {
        if (DialogHelper.confirm(this, "Bạn có muốn thoát khỏi ứng dụng không?")) {
            System.exit(0);
        }
    }
    
     boolean check() {
        if (lblTaiKhoan.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa nhập tên tài khoản");
            lblTaiKhoan.requestFocus();
            return false;
        }
        else if (lblMatKhau.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa nhập mật khẩu");
            lblMatKhau.requestFocus();
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

        lblMatKhau = new javax.swing.JPasswordField();
        lblTaiKhoan = new javax.swing.JTextField();
        lblThoat = new javax.swing.JLabel();
        lblDangNhap = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Đăng Nhập");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMatKhau.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        lblMatKhau.setText("444");
        lblMatKhau.setBorder(null);
        getContentPane().add(lblMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, 260, 40));

        lblTaiKhoan.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        lblTaiKhoan.setText("Tuannt01");
        lblTaiKhoan.setBorder(null);
        getContentPane().add(lblTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 260, 40));

        lblThoat.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        lblThoat.setText("Thoát");
        lblThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThoatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblThoatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblThoatMouseExited(evt);
            }
        });
        getContentPane().add(lblThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 340, -1, -1));

        lblDangNhap.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        lblDangNhap.setText("Đăng nhập");
        lblDangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangNhapMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangNhapMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangNhapMouseExited(evt);
            }
        });
        getContentPane().add(lblDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, -1, -1));

        lblHinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/library.png"))); // NOI18N
        getContentPane().add(lblHinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblDangNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseClicked
        login();
    }//GEN-LAST:event_lblDangNhapMouseClicked

    private void lblThoatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThoatMouseClicked
        exit();
    }//GEN-LAST:event_lblThoatMouseClicked

    private void lblDangNhapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseEntered
        lblDangNhap.setText("<html><b>Đăng nhập</b></html>");
        lblDangNhap.setForeground(Color.red);
    }//GEN-LAST:event_lblDangNhapMouseEntered

    private void lblDangNhapMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseExited
        lblDangNhap.setText("<html>Đăng nhập</html>");
        lblDangNhap.setForeground(Color.black);
    }//GEN-LAST:event_lblDangNhapMouseExited

    private void lblThoatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThoatMouseEntered
        lblThoat.setText("<html><b>Thoát</b></html>");
        lblThoat.setForeground(Color.red);
    }//GEN-LAST:event_lblThoatMouseEntered

    private void lblThoatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThoatMouseExited
        lblThoat.setText("<html>Thoát</html>");
        lblThoat.setForeground(Color.black);
    }//GEN-LAST:event_lblThoatMouseExited

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
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DangNhapJDialog dialog = new DangNhapJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel lblDangNhap;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JPasswordField lblMatKhau;
    private javax.swing.JTextField lblTaiKhoan;
    private javax.swing.JLabel lblThoat;
    // End of variables declaration//GEN-END:variables
}
