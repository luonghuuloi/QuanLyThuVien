/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.DocGia;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class DocGiaDAO {
     public void insert(DocGia model) {
        String sql = "INSERT INTO DocGia (MaDG, HoTenDG, ChuyenNganh, Email, SDT, GioiTinh, DiaChi) VALUES (?, ?, ?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaDocGia(),
                model.getHoTenDG(),
                model.getChuyenNganh(),
                model.getEmail(),
                model.getSDT(),
                model.isGioiTinh(),
                model.getDiaChi());
    }

    public void update(DocGia model) {
        String sql = "UPDATE DocGia SET HoTenDG=?, ChuyenNganh=?, Email=?, SDT=?, GioiTinh=?, DiaChi=? WHERE MaDG=?";
        JDBCHelper.executeUpdate(sql,
                model.getHoTenDG(),
                model.getChuyenNganh(),
                model.getEmail(),
                model.getSDT(),
                model.isGioiTinh(),
                model.getDiaChi(),
                model.getMaDocGia());
    }

    public void delete(String MaDG) {
        String sql = "DELETE FROM DocGia WHERE MaDG=?";
        JDBCHelper.executeUpdate(sql, MaDG);
    }

    public List<DocGia> select() {
        String sql = "SELECT * FROM DocGia";
        return select(sql);
    }

    public DocGia findById(String madg) {
        String sql = "SELECT * FROM DocGia WHERE MaDG=?";
        List<DocGia> list = select(sql, madg);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<DocGia> select(String sql, Object... args) {
        List<DocGia> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    DocGia model = readFromResultSet(rs);
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

    private DocGia readFromResultSet(ResultSet rs) throws SQLException {
        DocGia model = new DocGia();
        model.setMaDocGia(rs.getString("MaDG"));
        model.setHoTenDG(rs.getString("HoTenDG"));
        model.setChuyenNganh(rs.getString("ChuyenNganh"));
        model.setEmail(rs.getString("Email"));
        model.setSDT(rs.getString("SDT"));
        model.setGioiTinh(rs.getBoolean("GioiTinh"));
        model.setDiaChi(rs.getString("DiaChi"));
        return model;
    }
}
