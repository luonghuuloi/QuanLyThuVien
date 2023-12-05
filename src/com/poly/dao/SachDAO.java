/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.LoaiSach;
import com.poly.entity.Sach;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class SachDAO {
     public void insert(Sach model) {
        String sql = "INSERT INTO Sach (MaSach, MaLoaiSach, MaNV, TenSach, NhaXuatBan, SoLuong, GiaTien, NgayNhap, Hinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaSach(),
                model.getMaLoai(),
                model.getMaNhanVien(),
                model.getTenSach(),
                model.getNhaXuatBan(),
                model.getSoLuong(),
                model.getGiaTien(),
                model.getNgayNhap(),
                model.getHinh());
    }

    public void update(Sach model) {
        String sql = "UPDATE Sach SET MaLoaiSach=?, MaNV=?, TenSach=?, NhaXuatBan=?, SoLuong=?, GiaTien=?, NgayNhap=?, Hinh=? WHERE MaSach=?";
        JDBCHelper.executeUpdate(sql,
                model.getMaLoai(),
                model.getMaNhanVien(),
                model.getTenSach(),
                model.getNhaXuatBan(),
                model.getSoLuong(),
                model.getGiaTien(),
                model.getNgayNhap(),
                model.getHinh(),
                model.getMaSach());
    }

    public void delete(String MaSach) {
        String sql = "DELETE FROM Sach WHERE MaSach=?";
        JDBCHelper.executeUpdate(sql, MaSach);
    }

    public List<Sach> select() {
        String sql = "SELECT * FROM Sach";
        return select(sql);
    }
    

    public Sach findById(String masach) {
        String sql = "SELECT * FROM Sach WHERE MaSach=?";
        List<Sach> list = select(sql, masach);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<Sach> select(String sql, Object... args) {
        List<Sach> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    Sach model = readFromResultSet(rs);
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

    private Sach readFromResultSet(ResultSet rs) throws SQLException {
        Sach model = new Sach();
        model.setMaSach(rs.getString("MaSach"));
        model.setMaLoai(rs.getString("MaLoaiSach"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setTenSach(rs.getString("TenSach"));
        model.setNhaXuatBan(rs.getString("NhaXuatBan"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setGiaTien(rs.getDouble("GiaTien"));
        model.setNgayNhap(rs.getDate("NgayNhap"));
        model.setHinh(rs.getString("Hinh"));
        return model;
    }
    
     public List<Sach> selectByMaLoai(String maloai) {
        String sql = "Select * from Sach where MaLoaiSach = ?";
        return select(sql, maloai);
    }
}
