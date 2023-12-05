/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.LoaiSachDAO;
import com.poly.dao.SachDAO;
import com.poly.entity.LoaiSach;
import com.poly.entity.Sach;
import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import com.poly.utils.XDate;
import com.poly.utils.XImage;
import java.awt.Image;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLySach extends javax.swing.JFrame {

    /**
     * Creates new form QuanLySach
     */
    public QuanLySach() {
        initComponents();
        init();
    }

    SachDAO dao = new SachDAO();
    LoaiSachDAO lsdao = new LoaiSachDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLySach.this.DISPOSE_ON_CLOSE);
        fillCboTenLoaiSach();
        fillTable();
    }

    void fillCboTenLoaiSach() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<LoaiSach> list = lsdao.select();
        for (LoaiSach ls : list) {
            model.addElement(ls);
        }
        cboTenLoaiSach.setModel(model);
    }

    void fillTable() {
        String header[] = {"Mã Sách", "Mã Loại", "Mã NV", "Tên Sách", "NXB", "Số Lượng", "Giá Tiền", "Ngày Nhập", "Hình"};
        DefaultTableModel model = new DefaultTableModel(header, 0);
        try {
            LoaiSach ls = (LoaiSach) cboTenLoaiSach.getSelectedItem();
            List<Sach> list = dao.selectByMaLoai(ls.getMaLoai());
            for (Sach sach : list) {
                model.addRow(new Object[]{
                    sach.getMaSach(), sach.getMaLoai(), sach.getMaNhanVien(), sach.getTenSach(),
                    sach.getNhaXuatBan(), sach.getSoLuong(), sach.getGiaTien(), sach.getNgayNhap(), sach.getHinh()
                });
            }
            tblSach.setModel(model);
              //Điều chỉnh độ rộng các cột
        tblSach.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblSach.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblSach.getColumnModel().getColumn(2).setPreferredWidth(30);
        tblSach.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblSach.getColumnModel().getColumn(4).setPreferredWidth(120);
        tblSach.getColumnModel().getColumn(5).setPreferredWidth(40);
        tblSach.getColumnModel().getColumn(6).setPreferredWidth(40);
        tblSach.getColumnModel().getColumn(7).setPreferredWidth(50);
        tblSach.getColumnModel().getColumn(8).setPreferredWidth(20);
        //Canh lề dữ liệu trong bảng
        DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
        canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        tblSach.getColumn("Mã Sách").setCellRenderer(canhLe);
        tblSach.getColumn("Mã Loại").setCellRenderer(canhLe);
        tblSach.getColumn("Mã NV").setCellRenderer(canhLe);
        tblSach.getColumn("Số Lượng").setCellRenderer(canhLe);
        
        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void ChonTenLoaiSach() {
        LoaiSach ls = (LoaiSach) cboTenLoaiSach.getSelectedItem();
        txtMaLoaiSach.setText(String.valueOf(ls.getMaLoai()));
        fillTable();
        row = -1;
        tabs.setSelectedIndex(1);
    }

    void insert() {
        if (check()) {
            Sach model = getForm();
            try {
                dao.insert(model);
                this.fillTable();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm sách thành công");
            } catch (Exception e) {
                if (lblHinh.getToolTipText() == null) {
                    DialogHelper.alert(this, "Bạn chưa chọn hình");
                } else {
                    DialogHelper.alert(this, "Thêm sách thất bại");
                }
            }
        }
    }
    
     public boolean checkUpdate() {
        int row = tblSach.getSelectedRow();
        if (row < 0) {
                DialogHelper.alert(this, "Bạn chưa chọn sách để cập nhật");
                return false;
            }
        if (txtMaSach.getText().equals("")) {
            DialogHelper.alert(this, "Mã sách không được trống");
            txtMaSach.requestFocus();
            return false;
        } else {
            if (txtTenSach.getText().equals("")) {
                DialogHelper.alert(this, "Tên sách không được trống");
                txtTenSach.requestFocus();
                return false;
            } else {
                if (txtNXB.getText().equals("")) {
                    DialogHelper.alert(this, "Nhà xuất bản không được trống");
                    txtNXB.requestFocus();
                    return false;
                } else {
                    try {
                        if (txtSoLuong.getText().equals("")) {
                            DialogHelper.alert(this, "Số lượng không được trống");
                            txtSoLuong.requestFocus();
                            return false;
                        } else if (Integer.valueOf(txtSoLuong.getText()) <= 0) {
                            DialogHelper.alert(this, "Số lượng phải lớn hơn 0");
                            txtSoLuong.requestFocus();
                            return false;
                        }
                    } catch (Exception e) {
                        DialogHelper.alert(this, "Số lượng phải là số");
                        return false;
                    }
                }
            }
        }
        try {
            if (txtGiaTien.getText().equals("")) {
                DialogHelper.alert(this, "Giá tiền không được trống");
                txtGiaTien.requestFocus();
                return false;
            } else if (Double.valueOf(txtGiaTien.getText()) <= 0) {
                DialogHelper.alert(this, "Giá tiền phải lớn hơn 0");
                txtGiaTien.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Giá tiền phải là số");
            return false;
        }
        return true;
    }

    void update() {
        if (checkUpdate()) {
            if(DialogHelper.confirm(this, "Bạn có muốn cập nhật hay không?")){
              Sach model = getForm();
            try {
                dao.update(model);
                this.fillTable();
                DialogHelper.alert(this, "Cập nhật thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Cập nhật thất bại");
            }
            }
        }
    }

    boolean checkXoa() {
        if (txtMaSach.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa chọn sách để xóa");
            return false;
        }
        return true;
    }

    void delete() {
        if (checkXoa()) {
            if (DialogHelper.confirm(this, "Bạn có muốn xóa sách này hay không?")) {
                String masach = txtMaSach.getText();
                try {
                    dao.delete(masach);
                    this.fillTable();
                    this.clear();
                    btnThem.setEnabled(false);
                    DialogHelper.alert(this, "Xóa sách thành công");
                    fillTable();
                } catch (Exception e) {
                    DialogHelper.alert(this, "Không thể xóa sách đọc giả đang mượn!!");
                }
            }
        }

    }

    void edit() {
        String masach = (String) tblSach.getValueAt(row, 0);
        Sach sach = dao.findById(masach);
        setForm(sach);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void clear() {
        Sach model = new Sach();
        model.setMaNhanVien(Auth.user.getMaNhanVien());
        model.setNgayNhap(XDate.toDate(XDate.toString(new Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
        ResetAnh();
        row = -1;
        updateStatus();
        txtMaSach.requestFocus();
        this.setForm(model);
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    void setForm(Sach model) {
        // txtMaLoaiSach.setText(model.getMaLoai());
        txtMaSach.setText(model.getMaSach());
        txtMaNV.setText(model.getMaNhanVien());
        txtTenSach.setText(model.getTenSach());
        txtNXB.setText(model.getNhaXuatBan());
        txtSoLuong.setText(String.valueOf(model.getSoLuong()));
        txtGiaTien.setText(String.valueOf(model.getGiaTien()));
        //txtNgayNhap.setText(XDate.toString(model.getNgayNhap(), "dd/MM/yyyy"));
        jDateChooser_NgayNhap.setDate(model.getNgayNhap());
        if (model.getHinh() != null) {
            lblHinh.setToolTipText(model.getHinh());
            lblHinh.setIcon(new ImageIcon(XImage.read(model.getHinh()).getImage().getScaledInstance(224, 190, Image.SCALE_DEFAULT)));

        }
    }

    Sach getForm() {
        Sach sach = new Sach();
        sach.setMaLoai(txtMaLoaiSach.getText());
        sach.setMaSach(txtMaSach.getText());
        sach.setMaNhanVien(txtMaNV.getText());
        sach.setTenSach(txtTenSach.getText());
        sach.setNhaXuatBan(txtNXB.getText());
        sach.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        sach.setGiaTien(Double.valueOf(txtGiaTien.getText()));
        //sach.setNgayNhap(XDate.toDate(XDate.toString(new Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
        sach.setNgayNhap(jDateChooser_NgayNhap.getDate());
        sach.setHinh(lblHinh.getToolTipText());
        return sach;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaLoaiSach.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            String TenLogo = (XImage.read(file.getName()) + "");
            TenLogo = TenLogo.substring(TenLogo.lastIndexOf("\\") + 1, TenLogo.length());
            lblHinh.setIcon(new ImageIcon(new ImageIcon("logos/" + TenLogo).getImage().getScaledInstance(224, 190, Image.SCALE_DEFAULT)));
            lblHinh.setToolTipText(file.getName());
        }
    }

    void ResetAnh() {
        ImageIcon image = new ImageIcon("src\\Photos\\");
        Image img = image.getImage();
        ImageIcon icon = new ImageIcon(img.getScaledInstance(lblHinh.getWidth(), lblHinh.getHeight(), img.SCALE_SMOOTH));
        lblHinh.setIcon(icon);
    }

    public boolean check() {
        if (dao.findById(txtMaSach.getText()) != null) {
            DialogHelper.alert(this, "Mã sách này đã tồn tại!!");
            txtMaSach.requestFocus();
            return false;
        }
        if (txtMaSach.getText().equals("")) {
            DialogHelper.alert(this, "Mã sách không được trống");
            txtMaSach.requestFocus();
            return false;
        } else {
            if (txtTenSach.getText().equals("")) {
                DialogHelper.alert(this, "Tên sách không được trống");
                txtTenSach.requestFocus();
                return false;
            } else {
                if (txtNXB.getText().equals("")) {
                    DialogHelper.alert(this, "Nhà xuất bản không được trống");
                    txtNXB.requestFocus();
                    return false;
                } else {
                    try {
                        if (txtSoLuong.getText().equals("")) {
                            DialogHelper.alert(this, "Số lượng không được trống");
                            txtSoLuong.requestFocus();
                            return false;
                        } else if (Integer.valueOf(txtSoLuong.getText()) <= 0) {
                            DialogHelper.alert(this, "Số lượng phải lớn hơn 0");
                            txtSoLuong.requestFocus();
                            return false;
                        }
                    } catch (Exception e) {
                        DialogHelper.alert(this, "Số lượng phải là số");
                        return false;
                    }
                }
            }
        }
        try {
            if (txtGiaTien.getText().equals("")) {
                DialogHelper.alert(this, "Giá tiền không được trống");
                txtGiaTien.requestFocus();
                return false;
            } else if (Double.valueOf(txtGiaTien.getText()) <= 0) {
                DialogHelper.alert(this, "Giá tiền phải lớn hơn 0");
                txtGiaTien.requestFocus();
                return false;
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Giá tiền phải là số");
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

        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNXB = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtGiaTien = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        txtMaLoaiSach = new javax.swing.JTextField();
        jDateChooser_NgayNhap = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        cboTenLoaiSach = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý sách");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("Quản Lý Sách");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã Loại Sách:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Mã Sách:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tên Sách:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Nhà Xuất Bản:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Số Lượng:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Giá Tiền:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ngày Nhập:");

        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Edit.png"))); // NOI18N
        btnSua.setText("Cập Nhật");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Refresh.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        txtMaLoaiSach.setEnabled(false);

        jDateChooser_NgayNhap.setDateFormatString("dd-MM-yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel9)))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser_NgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaSach)
                    .addComponent(txtMaNV)
                    .addComponent(txtTenSach)
                    .addComponent(txtNXB)
                    .addComponent(txtGiaTien, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(txtSoLuong)
                    .addComponent(txtMaLoaiSach, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(45, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSua)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(txtMaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNXB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXoa)
                    .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser_NgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi)
                    .addComponent(jLabel9))
                .addContainerGap(108, Short.MAX_VALUE))
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSach);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        cboTenLoaiSach.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboTenLoaiSach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenLoaiSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenLoaiSachActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel10.setText("Chọn Loại Sách:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabs))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(284, 284, 284)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(cboTenLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTenLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboTenLoaiSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenLoaiSachActionPerformed
        ChonTenLoaiSach();
    }//GEN-LAST:event_cboTenLoaiSachActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblSach.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblSachMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clear();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        chonAnh();
    }//GEN-LAST:event_lblHinhMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLySach().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboTenLoaiSach;
    private com.toedter.calendar.JDateChooser jDateChooser_NgayNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtMaLoaiSach;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNXB;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSach;
    // End of variables declaration//GEN-END:variables
}
