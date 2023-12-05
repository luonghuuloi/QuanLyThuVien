/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.PhieuMuon;
import com.poly.utils.JDBCHelper;
import com.poly.utils.XDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class PhieuMuonDAO {
      public void insert(PhieuMuon model) {
        String sql = "INSERT INTO PhieuMuon (MaPM, MaDG, MaNV, NgayMuon, NgayTra) VALUES (?, ?, ?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaPhieuMuon(),
                model.getMaDocGia(),
                model.getMaNhanVien(),
                model.getNgayMuon(),
                model.getNgayTra());
    }

    public void update(PhieuMuon model) {
        String sql = "UPDATE PhieuMuon SET MaDG=?, MaNV=?, NgayMuon=?, NgayTra=? WHERE MaPM=?";
        JDBCHelper.executeUpdate(sql,
                model.getMaDocGia(),
                model.getMaNhanVien(),
                model.getNgayMuon(),
                model.getNgayTra(),
                model.getMaPhieuMuon());
    }

    public void delete(String MaPM) {
        String sql = "DELETE FROM PhieuMuon WHERE MaPM=?";
        JDBCHelper.executeUpdate(sql, MaPM);
    }

    public List<PhieuMuon> select() {
        String sql = "SELECT * FROM PhieuMuon";
        return select(sql);
    }

    public PhieuMuon findById(String mapm) {
        String sql = "SELECT * FROM PhieuMuon WHERE MaPM=?";
        List<PhieuMuon> list = select(sql, mapm);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<PhieuMuon> select(String sql, Object... args) {
        List<PhieuMuon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    PhieuMuon model = readFromResultSet(rs);
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

    private PhieuMuon readFromResultSet(ResultSet rs) throws SQLException {
        PhieuMuon model = new PhieuMuon();
        model.setMaPhieuMuon(rs.getString("MaPM"));
        model.setMaDocGia(rs.getString("MaDG"));
        model.setMaNhanVien(rs.getString("MaNV"));
        model.setNgayMuon(rs.getDate("NgayMuon"));
        model.setNgayTra(rs.getDate("NgayTra"));
        return model;
    }
    
     public List<PhieuMuon> phieuMuonChuaTra() {
        List<PhieuMuon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT MaPM FROM PhieuMuon WHERE NgayTra IS NULL";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    PhieuMuon model = readFromResultSetMaPM(rs);
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
     
      private PhieuMuon readFromResultSetMaPM(ResultSet rs) throws SQLException {
        PhieuMuon model = new PhieuMuon();
        model.setMaPhieuMuon(rs.getString("MaPM"));
        return model;
    }
}
