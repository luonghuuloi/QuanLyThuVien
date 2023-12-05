/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

import com.poly.entity.ChiTietPhieuMuon;
import com.poly.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class ChiTietPhieuMuonDAO {
      public void insert(ChiTietPhieuMuon model) {
        String sql = "INSERT INTO ChiTietPhieuMuon (MaPM, MaSach, GhiChu) VALUES (?, ?, ?)";
         JDBCHelper.executeUpdate(sql,
                model.getMaPhieuMuon(),
                model.getMaSach(),
                model.getGhiChu());
    }

    public List<ChiTietPhieuMuon> select() {
        String sql = "SELECT * FROM ChiTietPhieuMuon";
        return select(sql);
    }

    private List<ChiTietPhieuMuon> select(String sql, Object... args) {
        List<ChiTietPhieuMuon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ChiTietPhieuMuon model = readFromResultSet(rs);
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

    private ChiTietPhieuMuon readFromResultSet(ResultSet rs) throws SQLException {
        ChiTietPhieuMuon model = new ChiTietPhieuMuon();
        model.setMaPhieuMuon(rs.getString("MaPM"));
        model.setMaSach(rs.getString("MaSach"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
