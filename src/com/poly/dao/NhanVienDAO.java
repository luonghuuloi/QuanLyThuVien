/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.NhanVien;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class NhanVienDAO {
     public void insert(NhanVien model) {
        String sql = "INSERT INTO NhanVien (MaNV, HoTen, NgaySinh, Email, SDT, GioiTinh, DiaChi, Hinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaNhanVien(),
                model.getHoTenNV(),
                model.getNgaySinh(),
                model.getEmail(),
                model.getSDT(),
                model.isGioiTinh(),
                model.getDiaChi(),
                model.getHinh());
    }

    public void update(NhanVien model) {
        String sql = "UPDATE NhanVien SET HoTen=?, NgaySinh=?, Email=?, SDT=?, GioiTinh=?, DiaChi=?, Hinh=? WHERE MaNV=?";
        JDBCHelper.executeUpdate(sql,
                model.getHoTenNV(),
                model.getNgaySinh(),
                model.getEmail(),
                model.getSDT(),
                model.isGioiTinh(),
                model.getDiaChi(),
                model.getHinh(),
                model.getMaNhanVien());
    }

    public void delete(String MaNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        JDBCHelper.executeUpdate(sql, MaNV);
    }

    public List<NhanVien> select() {
        String sql = "SELECT * FROM NhanVien";
        return select(sql);
    }

    public NhanVien findById(String manv) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NhanVien> select(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NhanVien model = readFromResultSet(rs);
                    list.add(model);                  
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }
        return list;
    }

    private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        NhanVien model = new NhanVien();
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setHoTenNV(rs.getString("HoTen"));
        model.setNgaySinh(rs.getDate("NgaySinh"));
        model.setEmail(rs.getString("Email"));
        model.setSDT(rs.getString("SDT"));
        model.setGioiTinh(rs.getBoolean("GioiTinh"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setHinh(rs.getString("Hinh"));
        return model;
    }
}
