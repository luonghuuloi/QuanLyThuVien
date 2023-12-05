/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.LoaiSach;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class LoaiSachDAO {
     public void insert(LoaiSach model) {
        String sql = "INSERT INTO LoaiSach (MaLoaiSach, TenLoaiSach, MaNV) VALUES (?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaLoai(),
                model.getTenLoai(),
                model.getMaNhanVien());
    }

    public void update(LoaiSach model) {
        String sql = "UPDATE LoaiSach SET TenLoaiSach=?, MaNV=? WHERE MaLoaiSach=?";
        JDBCHelper.executeUpdate(sql,
                model.getTenLoai(),
                model.getMaNhanVien(),
                model.getMaLoai());
    }

    public void delete(String MaLoaiSach) {
        String sql = "DELETE FROM LoaiSach WHERE MaLoaiSach=?";
        JDBCHelper.executeUpdate(sql, MaLoaiSach);
    }

    public List<LoaiSach> select() {
        String sql = "SELECT * FROM LoaiSach";
        return select(sql);
    }
    
    public LoaiSach findById(String maloai) {
        String sql = "SELECT * FROM LoaiSach WHERE MaLoaiSach=?";
        List<LoaiSach> list = select(sql, maloai);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<LoaiSach> select(String sql, Object... args) {
        List<LoaiSach> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    LoaiSach model = readFromResultSet(rs);
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

    private LoaiSach readFromResultSet(ResultSet rs) throws SQLException {
        LoaiSach model = new LoaiSach();
        model.setMaLoai(rs.getString("MaLoaiSach"));
        model.setTenLoai(rs.getString("TenLoaiSach"));
        model.setMaNhanVien(rs.getString("MaNV"));
        return model;
    }
}
