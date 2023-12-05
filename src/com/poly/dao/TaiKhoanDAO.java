/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.TaiKhoan;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class TaiKhoanDAO {
     public void insert(TaiKhoan model) {
        String sql = "INSERT INTO TaiKhoan (TenTaiKhoan, MaNV, MatKhau, VaiTro) VALUES (?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getTenTaiKhoan(),
                model.getMaNhanVien(),
                model.getMatKhau(),
                model.isVaiTro());
    }

    public void update(TaiKhoan model) {
        String sql = "UPDATE TaiKhoan SET MaNV=?, MatKhau=?,  VaiTro=? WHERE TenTaiKhoan=?";
        JDBCHelper.executeUpdate(sql,
                model.getMaNhanVien(),
                model.getMatKhau(),
                model.isVaiTro(),
                model.getTenTaiKhoan());
    }

    public void delete(String username) {
        String sql = "DELETE FROM TaiKhoan WHERE TenTaiKhoan=?";
        JDBCHelper.executeUpdate(sql, username);
    }

    public List<TaiKhoan> select() {
        String sql = "SELECT * FROM TaiKhoan";
        return select(sql);
    }

     public TaiKhoan findById(String username) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenTaiKhoan=?";
        List<TaiKhoan> list = select(sql, username);
        return list.size() > 0 ? list.get(0) : null;
    }
     
      public TaiKhoan findByMaNV(String manv) {
        String sql = "SELECT * FROM TaiKhoan WHERE MaNV=?";
        List<TaiKhoan> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<TaiKhoan> select(String sql, Object... args) {
        List<TaiKhoan> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    TaiKhoan model = readFromResultSet(rs);
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

    private TaiKhoan readFromResultSet(ResultSet rs) throws SQLException {
        TaiKhoan model = new TaiKhoan();
        model.setTenTaiKhoan(rs.getString("TenTaiKhoan"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        return model;
    }
}
