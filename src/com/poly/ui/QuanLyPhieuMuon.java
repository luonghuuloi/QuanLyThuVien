/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.ui;

import com.poly.dao.PhieuMuonDAO;
import com.poly.dao.ChiTietPhieuMuonDAO;
import com.poly.dao.DocGiaDAO;
import com.poly.dao.SachDAO;
import com.poly.entity.ChiTietPhieuMuon;
import com.poly.entity.DocGia;
import com.poly.entity.PhieuMuon;
import com.poly.entity.Sach;
import com.poly.utils.Auth;
import com.poly.utils.DialogHelper;
import com.poly.utils.XDate;
import com.poly.utils.XImage;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class QuanLyPhieuMuon extends javax.swing.JFrame {

    /**
     * Creates new form QuanLyPhieuMuon
     */
    public QuanLyPhieuMuon() {
        initComponents();
        init();
    }

    PhieuMuonDAO dao = new PhieuMuonDAO();
    ChiTietPhieuMuonDAO ctpmdao = new ChiTietPhieuMuonDAO();
    SachDAO sdao = new SachDAO();
    DocGiaDAO dgdao = new DocGiaDAO();
    int row = -1;

    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        setDefaultCloseOperation(QuanLyPhieuMuon.this.DISPOSE_ON_CLOSE);
        fillTable();
        loadCboTenDocGia();
        layMaDGTheoTen();
        loadCboMaPhieuMuon();
        loadCboTenSach();
        fillTableChiTietPM();
        layMaSachTheoTen();
        clear();
    }

    void fillTable() {
        String header[] = {"Mã Phiếu Mượn", "Mã Đọc Giả", "Mã Nhân Viên", "Ngày Mượn", "Ngày Trả"};
        DefaultTableModel model = new DefaultTableModel(header, 0);

        try {
            List<PhieuMuon> list = dao.select();
            for (PhieuMuon pm : list) {
                Object[] row = {
                    pm.getMaPhieuMuon(),
                    pm.getMaDocGia(),
                    pm.getMaNhanVien(),
                    XDate.toString(pm.getNgayMuon(), "dd-MM-yyyy"),
                    //pm.getNgayMuon(),
                    pm.getNgayTra() == null ? "Chưa trả" : XDate.toString(pm.getNgayTra(), "dd-MM-yyyy")
                };
                model.addRow(row);
            }
            tblPhieuMuon.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
   

    void loadCboTenDocGia() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<DocGia> list = dgdao.select();
        for (DocGia dg : list) {
            model.addElement(dg);
        }
        cboTenDG.setModel(model);
    }

    void layMaDGTheoTen() {
        DocGia dg = (DocGia) cboTenDG.getSelectedItem();
        txtMaDG.setText(String.valueOf(dg.getMaDocGia()));
    }

    void insert() {
        if (checkPM()) {
            PhieuMuon model = getForm();
            try {
                dao.insert(model);
                this.fillTable();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm phiếu mượn thành công");
                loadCboMaPhieuMuon();
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm phiếu mượn thất bại");
            }
        }
    }

    void update() {
        if (DialogHelper.confirm(this, "Bạn có muốn cập nhật phiếu mượn này hay không?")) {

            PhieuMuon model = getForm();
            try {
                dao.update(model);
                this.fillTable();
                DialogHelper.alert(this, "Cập nhật thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Cập nhật thất bại");
            }
        }
    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn có muốn xóa phiếu mượn này hay không?")) {
            String macd = txtMaPM.getText();
            try {
                dao.delete(macd);
                this.fillTable();
                this.clear();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Xóa phiếu mượn thành công");
                loadCboMaPhieuMuon();
            } catch (Exception e) {
                DialogHelper.alert(this, "Không thể xóa phiếu mượn đã thêm sách!!");
            }
        }
    }

    boolean checkPM() {
        if (dao.findById(txtMaPM.getText()) != null) {
            DialogHelper.alert(this, "Mã phiếu mượn này đã tồn tại");
            txtMaPM.requestFocus();
            return false;
        }
        if (txtMaPM.getText().equals("")) {
            DialogHelper.alert(this, "Mã phiếu mượn không được trống");
            txtMaPM.requestFocus();
            return false;
        }
        return true;
    }

    void edit() {
        String mapm = (String) tblPhieuMuon.getValueAt(row, 0);
        PhieuMuon pm = dao.findById(mapm);
        setForm(pm);
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    void clear() {
        PhieuMuon model = new PhieuMuon();
        model.setMaNhanVien(Auth.user.getMaNhanVien());
        model.setNgayMuon(XDate.toDate(XDate.toString(new Date(), "dd-MM-yyyy"), "dd-MM-yyyy"));
        row = -1;
        updateStatus();
        txtMaPM.requestFocus();
        this.setForm(model);
    }

    void setForm(PhieuMuon model) {
        txtMaPM.setText(model.getMaPhieuMuon());
        txtMaNV.setText(model.getMaNhanVien());
        //cboTenDG.getModel().setSelectedItem(dg.getHoTenDG());
        txtMaDG.setText(model.getMaDocGia());
        txtNgayMuon.setText(XDate.toString(model.getNgayMuon(), "dd-MM-yyyy"));
        //txtNgayTra.setText(XDate.toString(model.getNgayTra(), "dd/MM/yyyy"));
    }

    PhieuMuon getForm() {
        PhieuMuon pm = new PhieuMuon();
        pm.setMaPhieuMuon(txtMaPM.getText());
        //pm.setMaDocGia(cboTenDG.getSelectedItem().toString());
        pm.setMaDocGia(txtMaDG.getText());
        pm.setMaNhanVien(txtMaNV.getText());
        pm.setNgayMuon(XDate.toDate(txtNgayMuon.getText(), "dd-MM-yyyy"));
        return pm;
    }

    void updateStatus() {
        boolean edit = row >= 0;
        txtMaNV.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    PhieuMuon getFormTra() {
        PhieuMuon pm = new PhieuMuon();
        pm.setMaPhieuMuon(txtMaPM.getText());
        //pm.setMaDocGia(cboTenDG.getSelectedItem().toString());
        pm.setMaDocGia(txtMaDG.getText());
        pm.setMaNhanVien(txtMaNV.getText());
        pm.setNgayMuon(XDate.toDate(txtNgayMuon.getText(), "dd-MM-yyyy"));
        pm.setNgayTra(XDate.toDate(XDate.toString(new Date(), "dd-MM-yyyy"), "dd-MM-yyyy"));
        return pm;
    }

    boolean checkTraPhieu(){
          int row = tblPhieuMuon.getSelectedRow();
            if (row < 0) {
                DialogHelper.alert(this, "Bạn chưa chọn phiếu mượn nào trong bảng");
                return false;
            }
            return true;
    }
    
    void traPhieuMuon() {
        if(checkTraPhieu()){
          if (DialogHelper.confirm(this, "Bạn có chắc phiếu mượn này đã được trả hay không?")) {
            PhieuMuon model = getFormTra();
            try {
                dao.update(model);
                this.fillTable();
                loadCboMaPhieuMuon();
                DialogHelper.alert(this, "Trả phiếu mượn thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Trả phiếu mượn thất bại");
            }
        }
        }
      
    }

    void fillTableChiTietPM() {
        String header[] = {"Mã Phiếu Mượn", "Mã Sách", "Ghi Chú"};
        DefaultTableModel model = new DefaultTableModel(header, 0);

        try {
            List<ChiTietPhieuMuon> list = ctpmdao.select();
            for (ChiTietPhieuMuon ctpm : list) {
                Object[] row = {
                    ctpm.getMaPhieuMuon(),
                    ctpm.getMaSach(),
                    ctpm.getGhiChu()
                };
                model.addRow(row);
            }
            tblChiTietPM.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void loadCboMaPhieuMuon() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
//        List<PhieuMuon> list = dao.select();
//        for (PhieuMuon pm : list) {
//            model.addElement(pm);
//        }
        List<PhieuMuon> list = dao.phieuMuonChuaTra();
        for (PhieuMuon pm : list) {
            model.addElement(pm);
        }
        cboMaPM.setModel(model);
    }

    void loadCboTenSach() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<Sach> list = sdao.select();
        for (Sach s : list) {
            model.addElement(s);
        }
        cboTenSach.setModel(model);
    }

    void layMaSachTheoTen() {
        Sach s = (Sach) cboTenSach.getSelectedItem();
        txtMaSach.setText(String.valueOf(s.getMaSach()));
    }

    void insertChiTietPM() {
        if (checkChiTietPM()) {
            ChiTietPhieuMuon model = getFormChiTietPM();
            try {
                ctpmdao.insert(model);
                this.fillTableChiTietPM();
                btnThem.setEnabled(false);
                DialogHelper.alert(this, "Thêm thành công");
            } catch (Exception e) {
                DialogHelper.alert(this, "Bạn đã cho mượn sách này rồi");
            }
        }
    }

    boolean checkChiTietPM() {
        if (txtGhiChu.getText().equals("")) {
            DialogHelper.alert(this, "Ghi chú không được trống");
            txtGhiChu.requestFocus();
            return false;
        }
        return true;
    }

    void setFormChiTietPM(ChiTietPhieuMuon model) {
        cboMaPM.getModel().setSelectedItem(model.getMaPhieuMuon());
        cboTenSach.getModel().setSelectedItem(model.getMaSach());
        txtGhiChu.setText(model.getGhiChu());
    }

    ChiTietPhieuMuon getFormChiTietPM() {
        ChiTietPhieuMuon ctpm = new ChiTietPhieuMuon();
        ctpm.setMaPhieuMuon(cboMaPM.getSelectedItem().toString());
        //ctpm.setMaSach(cboTenSach.getSelectedItem().toString());
        ctpm.setMaSach(txtMaSach.getText());
        ctpm.setGhiChu(txtGhiChu.getText());
        return ctpm;
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
        txtMaPM = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNgayMuon = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        btnTraPhieuMuon = new javax.swing.JButton();
        cboTenDG = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtMaDG = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cboMaPM = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cboTenSach = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        btnDongY = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChiTietPM = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        txtHuyBo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý phiếu mượn");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 255));
        jLabel1.setText("Quản Lý Phiếu Mượn");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel2.setText("Mã Phiếu Mượn:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel3.setText("Tên Đọc Giả:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel4.setText("Mã Nhân Viên:");

        txtMaNV.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel5.setText("Ngày Mượn:");

        txtNgayMuon.setEnabled(false);

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

        tblPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuMuonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblPhieuMuon);

        btnTraPhieuMuon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Text.png"))); // NOI18N
        btnTraPhieuMuon.setText("Trả Phiếu Mượn");
        btnTraPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraPhieuMuonActionPerformed(evt);
            }
        });

        cboTenDG.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenDG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenDGActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel6.setText("Mã Đọc Giả:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaPM, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(txtMaNV)
                    .addComponent(txtNgayMuon)
                    .addComponent(cboTenDG, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaDG))
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTraPhieuMuon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaPM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua)
                    .addComponent(jLabel3)
                    .addComponent(cboTenDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnXoa)
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMoi)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTraPhieuMuon)
                            .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtMaDG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("PHIẾU MƯỢN", jPanel1);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel7.setText("Mã Phiếu Mượn:");

        cboMaPM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setText("Tên Sách:");

        cboTenSach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboTenSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenSachActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel9.setText("Ghi Chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        btnDongY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/Accept.png"))); // NOI18N
        btnDongY.setText("Đồng Ý");
        btnDongY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYActionPerformed(evt);
            }
        });

        tblChiTietPM.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblChiTietPM);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel10.setText("Mã Sách:");

        txtHuyBo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/poly/icon/No.png"))); // NOI18N
        txtHuyBo.setText("Hủy Bỏ");
        txtHuyBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHuyBoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10))
                                .addGap(53, 53, 53))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9)
                                .addGap(44, 44, 44)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboMaPM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboTenSach, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(txtMaSach))
                        .addGap(47, 47, 47)
                        .addComponent(btnDongY)
                        .addGap(35, 35, 35)
                        .addComponent(txtHuyBo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboMaPM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cboTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(36, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDongY)
                            .addComponent(txtHuyBo))
                        .addGap(234, 234, 234))))
        );

        tabs.addTab("CHI TIẾT PHIẾU MƯỢN", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(309, 309, 309)
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
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
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

    private void tblPhieuMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuMuonMouseClicked
        if (evt.getClickCount() >= 1) {
            row = tblPhieuMuon.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblPhieuMuonMouseClicked

    private void btnTraPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraPhieuMuonActionPerformed
        traPhieuMuon();
    }//GEN-LAST:event_btnTraPhieuMuonActionPerformed

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
        insertChiTietPM();
    }//GEN-LAST:event_btnDongYActionPerformed

    private void cboTenSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenSachActionPerformed
        layMaSachTheoTen();
    }//GEN-LAST:event_cboTenSachActionPerformed

    private void cboTenDGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenDGActionPerformed
        layMaDGTheoTen();
    }//GEN-LAST:event_cboTenDGActionPerformed

    private void txtHuyBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHuyBoActionPerformed
        this.dispose();
    }//GEN-LAST:event_txtHuyBoActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyPhieuMuon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyPhieuMuon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyPhieuMuon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyPhieuMuon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyPhieuMuon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDongY;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTraPhieuMuon;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboMaPM;
    private javax.swing.JComboBox<String> cboTenDG;
    private javax.swing.JComboBox<String> cboTenSach;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiTietPM;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JButton txtHuyBo;
    private javax.swing.JTextField txtMaDG;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaPM;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNgayMuon;
    // End of variables declaration//GEN-END:variables
}
