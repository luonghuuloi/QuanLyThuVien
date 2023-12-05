/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.DocGiaDAO;
import com.poly.entity.DocGia;
import com.poly.utils.DialogHelper;
import com.poly.utils.XImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyDocGia extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyDocGia
     */
    public QuanLyDocGia() {
        initComponents();
        init();
    }

    DocGiaDAO dao = new DocGiaDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyDocGia.this.DISPOSE_ON_CLOSE);
        fillTable();
    }

    void fillTable() {
        String header[] = {"Mã ĐG", "Họ và tên", "Chuyên Ngành", "Email", "SĐT", "Giới tính", "Địa chỉ"};
        DefaultTableModel model = new DefaultTableModel(header, 0);

        try {
            List<DocGia> list = dao.select();
            for (DocGia dg : list) {
                Object[] row = {
                    dg.getMaDocGia(),
                    dg.getHoTenDG(),
                    dg.getChuyenNganh(),
                    dg.getEmail(),
                    dg.getSDT(),
                    dg.isGioiTinh() ? "Nam" : "Nữ",
                    dg.getDiaChi()
                };
                model.addRow(row);
            }
            tblDocGia.setModel(model);
            //Điều chỉnh độ rộng các cột
        tblDocGia.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblDocGia.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblDocGia.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblDocGia.getColumnModel().getColumn(3).setPreferredWidth(110);
        tblDocGia.getColumnModel().getColumn(4).setPreferredWidth(60);
        tblDocGia.getColumnModel().getColumn(5).setPreferredWidth(30);
        tblDocGia.getColumnModel().getColumn(6).setPreferredWidth(50);
        //Canh lề dữ liệu trong bảng
        DefaultTableCellRenderer canhLe = new DefaultTableCellRenderer();
        canhLe.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        tblDocGia.getColumn("Mã ĐG").setCellRenderer(canhLe);
        tblDocGia.getColumn("SĐT").setCellRenderer(canhLe);
        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        if (check()) {
            DocGia model = getForm();
            try {
                dao.insert(model);
                this.fillTable();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm đọc giả thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm đọc giả thất bại");
                System.out.println(e);
            }
        }
    }

    public boolean checkUpdate() {
        int row = tblDocGia.getSelectedRow();
        if (row < 0) {
                DialogHelper.alert(this, "Bạn chưa chọn đọc giả để cập nhật");
                return false;
            }
        if (txtMaDG.getText().equals("")) {
            DialogHelper.alert(this, "Mã đọc giả không được trống");
            txtMaDG.requestFocus();
            return false;
        } else {
            if (txtHoTen.getText().equals("")) {
                DialogHelper.alert(this, "Họ tên không được trống");
                txtHoTen.requestFocus();
                return false;
            }
            if (txtChuyenNganh.getText().equals("")) {
                DialogHelper.alert(this, "Chuyên ngành không được trống");
                txtChuyenNganh.requestFocus();
                return false;
            } else {
                String email = "(\\w)+@{1}(\\w)+(\\.(\\w)+)+(\\.(\\w)+)?";
                if (txtEmail.getText().equals("")) {
                    DialogHelper.alert(this, "Email không được trống");
                    txtEmail.requestFocus();
                    return false;
                } else if (!txtEmail.getText().matches(email)) {
                    DialogHelper.alert(this, "Emai không đúng định dạng");
                    txtEmail.requestFocus();
                    return false;
                } else {
                    String sdt = "0\\d{9}";
                    if (txtSDT.getText().equals("")) {
                        DialogHelper.alert(this, "SĐT không được trống");
                        txtSDT.requestFocus();
                        return false;
                    } else if (!txtSDT.getText().matches(sdt)) {
                        DialogHelper.alert(this, "SĐT không đúng định dạng");
                        txtSDT.requestFocus();
                        return false;
                    }
                }
            }
            if (txtDiaChi.getText().equals("")) {
                DialogHelper.alert(this, "Địa chỉ không được trống");
                txtDiaChi.requestFocus();
                return false;
            }
        }
        return true;
    }

    void update() {
        if (checkUpdate()) {
            if (DialogHelper.confirm(this, "Bạn có muốn cập nhật hay không?")) {
                DocGia model = getForm();
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
        if (txtMaDG.getText().equals("")) {
            DialogHelper.alert(this, "Bạn chưa chọn đọc giả để xóa");
            return false;
        }
        return true;
    }

    void delete() {
        if (checkXoa()) {
            if (DialogHelper.confirm(this, "Bạn có muốn xóa đọc giả này không?")) {
                String macd = txtMaDG.getText();
                try {
                    dao.delete(macd);
                    this.fillTable();
                    this.clear();
                    btnThem.setEnabled(false);
                    DialogHelper.alert(this, "Xóa đọc giả thành công");
                } catch (Exception e) {
                    DialogHelper.alert(this, "Không thể xóa đọc giả đang mượn sách!!");
                }
            }
        }
    }

    void edit() {
        String madg = (String) tblDocGia.getValueAt(row, 0);
        DocGia dg = dao.findById(madg);
        setForm(dg);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void clear() {
//        txtMaDG.setText("");
//        txtHoTen.setText("");
//        txtChuyenNganh.setText("");
//        txtEmail.setText("");
//        txtSDT.setText("");
//        buttonGroup1.clearSelection();
//        txtDiaChi.setText("");
//        row = -1;
//        updateStatus();
//        txtMaDG.requestFocus();
        DocGia model = new DocGia();
        row = -1;
        updateStatus();
        txtMaDG.requestFocus();
        this.setForm(model);
    }

    void setForm(DocGia model) {
        txtMaDG.setText(model.getMaDocGia());
        txtHoTen.setText(model.getHoTenDG());
        txtChuyenNganh.setText(model.getChuyenNganh());
        txtEmail.setText(model.getEmail());
        txtSDT.setText(model.getSDT());
        rdoNam.setSelected(model.isGioiTinh());
        rdoNu.setSelected(!model.isGioiTinh());
        txtDiaChi.setText(model.getDiaChi());
    }

    DocGia getForm() {
        DocGia dg = new DocGia();
        dg.setMaDocGia(txtMaDG.getText());
        dg.setHoTenDG(txtHoTen.getText());
        dg.setChuyenNganh(txtChuyenNganh.getText());
        dg.setEmail(txtEmail.getText());
        dg.setSDT(txtSDT.getText());
        dg.setGioiTinh(rdoNam.isSelected());
        dg.setDiaChi(txtDiaChi.getText());
        return dg;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaDG.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    void lapPhieuMuon() {
        new QuanLyPhieuMuon().setVisible(true);
    }

    public boolean check() {
        if (dao.findById(txtMaDG.getText()) != null) {
            DialogHelper.alert(this, "Mã đọc giả này đã tồn tại");
            txtMaDG.requestFocus();
            return false;
        }
        if (txtMaDG.getText().equals("")) {
            DialogHelper.alert(this, "Mã đọc giả không được trống");
            txtMaDG.requestFocus();
            return false;
        } else {
            if (txtHoTen.getText().equals("")) {
                DialogHelper.alert(this, "Họ tên không được trống");
                txtHoTen.requestFocus();
                return false;
            }
            if (txtChuyenNganh.getText().equals("")) {
                DialogHelper.alert(this, "Chuyên ngành không được trống");
                txtChuyenNganh.requestFocus();
                return false;
            } else {
                String email = "(\\w)+@{1}(\\w)+(\\.(\\w)+)+(\\.(\\w)+)?";
                if (txtEmail.getText().equals("")) {
                    DialogHelper.alert(this, "Email không được trống");
                    txtEmail.requestFocus();
                    return false;
                } else if (!txtEmail.getText().matches(email)) {
                    DialogHelper.alert(this, "Emai không đúng định dạng");
                    txtEmail.requestFocus();
                    return false;
                } else {
                    String sdt = "0\\d{9}";
                    if (txtSDT.getText().equals("")) {
                        DialogHelper.alert(this, "SĐT không được trống");
                        txtSDT.requestFocus();
                        return false;
                    } else if (!txtSDT.getText().matches(sdt)) {
                        DialogHelper.alert(this, "SĐT không đúng định dạng");
                        txtSDT.requestFocus();
                        return false;
                    }
                }
            }
            if (txtDiaChi.getText().equals("")) {
                DialogHelper.alert(this, "Địa chỉ không được trống");
                txtDiaChi.requestFocus();
                return false;
            }
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
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaDG = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtChuyenNganh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDiaChi = new javax.swing.JTextArea();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnLapPhieuMuon = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDocGia = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnDanhSach = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý đọc giả");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("Quản Lý Đọc Giả");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setText("Mã Đọc Giả:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel3.setText("Họ Tên:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel4.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel5.setText("Chuyên Ngành:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel6.setText("SĐT:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel7.setText("Giới Tính:");

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        rdoNu.setText("Nữ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setText("Địa Chỉ:");

        txtDiaChi.setColumns(20);
        txtDiaChi.setRows(5);
        jScrollPane1.setViewportView(txtDiaChi);

        btnThem.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Edit.png"))); // NOI18N
        btnSua.setText("Cập Nhật");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Refresh.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnLapPhieuMuon.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        btnLapPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Notes.png"))); // NOI18N
        btnLapPhieuMuon.setText("Lập Phiếu Mượn");
        btnLapPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLapPhieuMuonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaDG)
                    .addComponent(txtHoTen)
                    .addComponent(txtChuyenNganh)
                    .addComponent(txtEmail)
                    .addComponent(txtSDT)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdoNam)
                        .addGap(27, 27, 27)
                        .addComponent(rdoNu))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(63, 63, 63))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnLapPhieuMuon)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtChuyenNganh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(rdoNam)
                    .addComponent(rdoNu))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel8)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLapPhieuMuon)
                                .addGap(72, 72, 72))))))
        );

        tabs.addTab("CẬP NHẬT", jPanel1);

        tblDocGia.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDocGia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocGiaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDocGia);

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnDanhSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/List.png"))); // NOI18N
        btnDanhSach.setText("Xem danh sách");
        btnDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDanhSachActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel9.setText("Tìm tên đọc giả:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem)
                        .addGap(18, 18, 18)
                        .addComponent(btnDanhSach)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTimKiem)
                    .addComponent(btnDanhSach)
                    .addComponent(txtTimKiem)
                    .addComponent(jLabel9))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        tabs.addTab("DANH SÁCH", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(253, 253, 253)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(tabs)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void tblDocGiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocGiaMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblDocGia.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblDocGiaMouseClicked

    private void btnLapPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLapPhieuMuonActionPerformed
        lapPhieuMuon();
    }//GEN-LAST:event_btnLapPhieuMuonActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String header[] = {"Mã Đọc Giả", "Họ và tên", "Chuyên Ngành", "Email", "SDT", "Giới tính", "Địa chỉ"};
        DefaultTableModel tblModel = new DefaultTableModel(header, 0);
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            String user = "sa";
            String pass = "123";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien";
            con = DriverManager.getConnection(url, user, pass);
            String sql = "select * from DocGia";
            if (txtTimKiem.getText().length() > 0) {
                sql = sql + " where HoTenDG like N'%" + txtTimKiem.getText() + "%'";
            }
            st = con.createStatement();
            rs = st.executeQuery(sql);
            Vector data = null;
            tblModel.setRowCount(0);
            if (rs.isBeforeFirst() == false) {
                JOptionPane.showMessageDialog(this, "Đọc giả không tồn tại");
                return;
            }
            while (rs.next()) {
                data = new Vector();
                data.add(rs.getString("MaDG"));
                data.add(rs.getString("HoTenDG"));
                data.add(rs.getString("ChuyenNganh"));
                data.add(rs.getString("Email"));
                data.add(rs.getString("SDT"));
                data.add(rs.getBoolean("GioiTinh"));
                data.add(rs.getString("DiaChi"));
                tblModel.addRow(data);
            }
            tblDocGia.setModel(tblModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDanhSachActionPerformed
        fillTable();
    }//GEN-LAST:event_btnDanhSachActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyDocGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyDocGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyDocGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyDocGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyDocGia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDanhSach;
    private javax.swing.JButton btnLapPhieuMuon;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDocGia;
    private javax.swing.JTextField txtChuyenNganh;
    private javax.swing.JTextArea txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaDG;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
