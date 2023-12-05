/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import com.poly.utils.XImage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author ADMIN
 */
public class QuanLyThuVien extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyThuVien
     */
    public QuanLyThuVien() {
        initComponents();
        init();
    }

    void dongho() {
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                String text = formatter.format(date);
                lblDongHo.setText(text);
            }
        }).start();
    }

    void init() {
        this.setLocationRelativeTo(null);
        dongho();
        setIconImage(XImage.getAppIcon());
        new DangNhapJDialog(this, true).setVisible(true);
        lblTenTaiKhoan.setText("Xin chào, " +Auth.user.getTenTaiKhoan());
    }

    void openNhanVien(int index) {
        if (Auth.isLogin()) {
            if (index == 1 && !Auth.isManager()) {
                DialogHelper.alert(this, "Không có quyền xem nhân viên");
            } else {
                QuanLyNhanVien nv = new QuanLyNhanVien();
                nv.setVisible(true);
            }
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }

    void checkopenNhanVien() {
        if (Auth.isLogin()) {
            if (!Auth.isManager()) {
                // mniQLNhanVien.setEnabled(false);

            } else if (Auth.isManager()) {
                // mniQLNhanVien.setEnabled(true);
            }
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
    
     void openSach() {
        if (Auth.isLogin()) {
            new QuanLySach().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openLoaiSach() {
        if (Auth.isLogin()) {
            new QuanLyLoaiSach().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openDocGia() {
        if (Auth.isLogin()) {
            new QuanLyDocGia().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openPhieuMuon() {
        if (Auth.isLogin()) {
            new QuanLyPhieuMuon().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void openThongKe() {
        if (Auth.isLogin()) {
            new ThongKe().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
     void dangXuat() {
        if (Auth.isLogin()) {
            Auth.dangXuat();
            new DangNhapJDialog(this, true).setVisible(true);
            lblTenTaiKhoan.setText("Xin chào, " +Auth.user.getTenTaiKhoan());
            checkopenNhanVien();
           // checkopenThongKe();
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
            new DangNhapJDialog(this, true).setVisible(true);
        }
    }
     
     void openDoiMatKhau() {
        if (Auth.isLogin()) {
            new DoiMatKhau().setVisible(true);
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập");
        }
    }
     
     void openDangKy(int index) {
        if (Auth.isLogin()) {
            if (index == 1 && !Auth.isManager()) {
                DialogHelper.alert(this, "Bạn không có quyền đăng ký tài khoản");
            } else {
                DangKy dk = new DangKy();
                dk.setVisible(true);
            }
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }
     
    void openQLTaiKhoan(int index) {
        if (Auth.isLogin()) {
            if (index == 1 && !Auth.isManager()) {
                DialogHelper.alert(this, "Bạn không có quyền quản lý tài khoản");
            } else {
                QuanLyTaiKhoan qltk = new QuanLyTaiKhoan();
                qltk.setVisible(true);
            }
        } else {
            DialogHelper.alert(this, "Bạn chưa đăng nhập!");
        }
    }

    void ketThuc() {
        if (DialogHelper.confirm(this, "Bạn có muốn thoát khỏi ứng dụng không?")) {
            System.exit(0);
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

        lblQLNhanVien = new javax.swing.JLabel();
        lblQLSach = new javax.swing.JLabel();
        lblQuanLyLoaiSach = new javax.swing.JLabel();
        lblQLDocGia = new javax.swing.JLabel();
        lblQLPhieuMuon = new javax.swing.JLabel();
        lblThongKe = new javax.swing.JLabel();
        lblDongHo = new javax.swing.JLabel();
        lblTenTaiKhoan = new javax.swing.JLabel();
        lblHinhNen = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mniDangXuat = new javax.swing.JMenuItem();
        mniDoiMatKhau = new javax.swing.JMenuItem();
        mniDangKy = new javax.swing.JMenuItem();
        mniKetThuc = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mniTaiKhoan = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mniLienHe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Thư Viện");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQLNhanVien.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lblQLNhanVien.setText("NHÂN VIÊN");
        lblQLNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLNhanVienMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLNhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLNhanVienMouseExited(evt);
            }
        });
        getContentPane().add(lblQLNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        lblQLSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lblQLSach.setText("SÁCH");
        lblQLSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLSachMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLSachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLSachMouseExited(evt);
            }
        });
        getContentPane().add(lblQLSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, -1, -1));

        lblQuanLyLoaiSach.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lblQuanLyLoaiSach.setText("LOẠI SÁCH");
        lblQuanLyLoaiSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQuanLyLoaiSachMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQuanLyLoaiSachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQuanLyLoaiSachMouseExited(evt);
            }
        });
        getContentPane().add(lblQuanLyLoaiSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));

        lblQLDocGia.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lblQLDocGia.setText("ĐỌC GIẢ");
        lblQLDocGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLDocGiaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLDocGiaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLDocGiaMouseExited(evt);
            }
        });
        getContentPane().add(lblQLDocGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 350, -1, -1));

        lblQLPhieuMuon.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lblQLPhieuMuon.setText("PHIẾU MƯỢN");
        lblQLPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblQLPhieuMuonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblQLPhieuMuonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblQLPhieuMuonMouseExited(evt);
            }
        });
        getContentPane().add(lblQLPhieuMuon, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 570, -1, -1));

        lblThongKe.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        lblThongKe.setText("THỐNG KÊ");
        lblThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThongKeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblThongKeMouseExited(evt);
            }
        });
        getContentPane().add(lblThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 570, -1, -1));

        lblDongHo.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(0, 0, 255));
        lblDongHo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Alarm.png"))); // NOI18N
        getContentPane().add(lblDongHo, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 640, 170, 40));

        lblTenTaiKhoan.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblTenTaiKhoan.setForeground(new java.awt.Color(51, 0, 255));
        lblTenTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Funny.png"))); // NOI18N
        getContentPane().add(lblTenTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 100, 240, 30));

        lblHinhNen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/giaodienchinh.png"))); // NOI18N
        getContentPane().add(lblHinhNen, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 691));

        jMenu1.setText("Hệ thống");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mniDangXuat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniDangXuat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Log out.png"))); // NOI18N
        mniDangXuat.setText("Đăng xuất");
        mniDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDangXuatActionPerformed(evt);
            }
        });
        jMenu1.add(mniDangXuat);

        mniDoiMatKhau.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Refresh.png"))); // NOI18N
        mniDoiMatKhau.setText("Đổi mật khẩu");
        mniDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDoiMatKhauActionPerformed(evt);
            }
        });
        jMenu1.add(mniDoiMatKhau);

        mniDangKy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniDangKy.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniDangKy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Create.png"))); // NOI18N
        mniDangKy.setText("Đăng ký");
        mniDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDangKyActionPerformed(evt);
            }
        });
        jMenu1.add(mniDangKy);

        mniKetThuc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        mniKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniKetThuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Stop.png"))); // NOI18N
        mniKetThuc.setText("Kết thúc");
        mniKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniKetThucActionPerformed(evt);
            }
        });
        jMenu1.add(mniKetThuc);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Tài khoản");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mniTaiKhoan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniTaiKhoan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Microsoft.png"))); // NOI18N
        mniTaiKhoan.setText("Quản lý tài khoản");
        mniTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTaiKhoanActionPerformed(evt);
            }
        });
        jMenu3.add(mniTaiKhoan);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Trợ giúp");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mniLienHe.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        mniLienHe.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mniLienHe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Brick house.png"))); // NOI18N
        mniLienHe.setText("Địa chỉ liên hệ");
        mniLienHe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniLienHeActionPerformed(evt);
            }
        });
        jMenu2.add(mniLienHe);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblQLNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVienMouseClicked
        openNhanVien(1);
    }//GEN-LAST:event_lblQLNhanVienMouseClicked

    private void lblQLNhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVienMouseEntered
        lblQLNhanVien.setText("<html><b>NHÂN VIÊN</b></html>");
        lblQLNhanVien.setForeground(Color.red);
    }//GEN-LAST:event_lblQLNhanVienMouseEntered

    private void lblQLNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLNhanVienMouseExited
        lblQLNhanVien.setText("<html>NHÂN VIÊN</html>");
        lblQLNhanVien.setForeground(Color.black);
    }//GEN-LAST:event_lblQLNhanVienMouseExited

    private void lblQLSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLSachMouseClicked
        openSach();
    }//GEN-LAST:event_lblQLSachMouseClicked

    private void lblQLSachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLSachMouseEntered
        lblQLSach.setText("<html><b>SÁCH</b></html>");
        lblQLSach.setForeground(Color.red);
    }//GEN-LAST:event_lblQLSachMouseEntered

    private void lblQLSachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLSachMouseExited
        lblQLSach.setText("<html>SÁCH</html>");
        lblQLSach.setForeground(Color.black);
    }//GEN-LAST:event_lblQLSachMouseExited

    private void lblQuanLyLoaiSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuanLyLoaiSachMouseClicked
        openLoaiSach();
    }//GEN-LAST:event_lblQuanLyLoaiSachMouseClicked

    private void lblQuanLyLoaiSachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuanLyLoaiSachMouseEntered
        lblQuanLyLoaiSach.setText("<html><b>LOẠI SÁCH</b></html>");
        lblQuanLyLoaiSach.setForeground(Color.red);
    }//GEN-LAST:event_lblQuanLyLoaiSachMouseEntered

    private void lblQuanLyLoaiSachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQuanLyLoaiSachMouseExited
        lblQuanLyLoaiSach.setText("<html>LOẠI SÁCH</html>");
        lblQuanLyLoaiSach.setForeground(Color.black);
    }//GEN-LAST:event_lblQuanLyLoaiSachMouseExited

    private void lblQLDocGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLDocGiaMouseClicked
        openDocGia();
    }//GEN-LAST:event_lblQLDocGiaMouseClicked

    private void lblQLDocGiaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLDocGiaMouseEntered
        lblQLDocGia.setText("<html><b>ĐỌC GIẢ</b></html>");
        lblQLDocGia.setForeground(Color.red);
    }//GEN-LAST:event_lblQLDocGiaMouseEntered

    private void lblQLDocGiaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLDocGiaMouseExited
        lblQLDocGia.setText("<html>ĐỌC GIẢ</html>");
        lblQLDocGia.setForeground(Color.black);
    }//GEN-LAST:event_lblQLDocGiaMouseExited

    private void lblQLPhieuMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLPhieuMuonMouseClicked
        openPhieuMuon();
    }//GEN-LAST:event_lblQLPhieuMuonMouseClicked

    private void lblQLPhieuMuonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLPhieuMuonMouseEntered
        lblQLPhieuMuon.setText("<html><b>PHIẾU MƯỢN</b></html>");
        lblQLPhieuMuon.setForeground(Color.red);
    }//GEN-LAST:event_lblQLPhieuMuonMouseEntered

    private void lblQLPhieuMuonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblQLPhieuMuonMouseExited
        lblQLPhieuMuon.setText("<html>PHIẾU MƯỢN</html>");
        lblQLPhieuMuon.setForeground(Color.black);
    }//GEN-LAST:event_lblQLPhieuMuonMouseExited

    private void lblThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseClicked
        openThongKe();
    }//GEN-LAST:event_lblThongKeMouseClicked

    private void lblThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseEntered
        lblThongKe.setText("<html><b>THỐNG KÊ</b></html>");
        lblThongKe.setForeground(Color.red);
    }//GEN-LAST:event_lblThongKeMouseEntered

    private void lblThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThongKeMouseExited
        lblThongKe.setText("<html>THỐNG KÊ</html>");
        lblThongKe.setForeground(Color.black);
    }//GEN-LAST:event_lblThongKeMouseExited

    private void mniDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDangXuatActionPerformed
        dangXuat();
    }//GEN-LAST:event_mniDangXuatActionPerformed

    private void mniDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDoiMatKhauActionPerformed
        openDoiMatKhau();
    }//GEN-LAST:event_mniDoiMatKhauActionPerformed

    private void mniKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniKetThucActionPerformed
        ketThuc();
    }//GEN-LAST:event_mniKetThucActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
         int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát không?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (chon == JOptionPane.NO_OPTION) {
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }else{
                System.exit(0);
            }
    }//GEN-LAST:event_formWindowClosing

    private void mniDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDangKyActionPerformed
        openDangKy(1);
    }//GEN-LAST:event_mniDangKyActionPerformed

    private void mniTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTaiKhoanActionPerformed
        openQLTaiKhoan(1);
    }//GEN-LAST:event_mniTaiKhoanActionPerformed

    private void mniLienHeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniLienHeActionPerformed
         DialogHelper.alert(this, "Mọi chi tiết gửi về Email: thuvienfpt@gmail.com");
    }//GEN-LAST:event_mniLienHeActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyThuVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyThuVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyThuVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyThuVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyThuVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblHinhNen;
    private javax.swing.JLabel lblQLDocGia;
    private javax.swing.JLabel lblQLNhanVien;
    private javax.swing.JLabel lblQLPhieuMuon;
    private javax.swing.JLabel lblQLSach;
    private javax.swing.JLabel lblQuanLyLoaiSach;
    private javax.swing.JLabel lblTenTaiKhoan;
    private javax.swing.JLabel lblThongKe;
    private javax.swing.JMenuItem mniDangKy;
    private javax.swing.JMenuItem mniDangXuat;
    private javax.swing.JMenuItem mniDoiMatKhau;
    private javax.swing.JMenuItem mniKetThuc;
    private javax.swing.JMenuItem mniLienHe;
    private javax.swing.JMenuItem mniTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
