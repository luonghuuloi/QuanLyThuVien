/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.dao;

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
public class ThongKeDAO {

    public List<Object[]> danhSach() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT PhieuMuon.MaPM, DocGia.MaDG, DocGia.HoTenDG, Sach.MaSach, Sach.TenSach, PhieuMuon.NgayMuon, PhieuMuon.NgayTra, PhieuMuon.MaNV\n"
                        + "FROM PhieuMuon INNER JOIN DocGia ON PhieuMuon.MaDG = DocGia.MaDG INNER JOIN Sach ON PhieuMuon.MaNV = Sach.MaNV ORDER BY PhieuMuon.MaPM";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaPM"),
                        rs.getString("MaDG"),
                        rs.getString("HoTenDG"),
                        rs.getString("MaSach"),
                        rs.getString("TenSach"),
                        XDate.toString(rs.getDate("NgayMuon"), "dd-MM-yyyy"),
                        rs.getDate("NgayTra") == null ? "Chưa trả" : XDate.toString(rs.getDate("NgayTra"), "dd-MM-yyyy"),
                        rs.getString("MaNV")
                    };
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

    public List<Object[]> docGiaChuaTraSach() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT PhieuMuon.MaPM, DocGia.MaDG, DocGia.HoTenDG, PhieuMuon.NgayMuon, PhieuMuon.NgayTra\n"
                        + "FROM PhieuMuon INNER JOIN DocGia ON PhieuMuon.MaDG = DocGia.MaDG WHERE PhieuMuon.NgayTra IS NULL";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaPM"),
                        rs.getString("MaDG"),
                        rs.getString("HoTenDG"),
                        XDate.toString(rs.getDate("NgayMuon"), "dd-MM-yyyy"),
                        rs.getDate("NgayTra") == null ? "Chưa trả" : XDate.toString(rs.getDate("NgayTra"), "dd-MM-yyyy")
                    };
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

    public List<Object[]> sachMuonNhieuNhat() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "SELECT MaSach, COUNT(MaSach) AS N'Số lần mượn' FROM ChiTietPhieuMuon\n"
                        + "GROUP BY MaSach ORDER BY COUNT(MaSach) DESC";
                rs = JDBCHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getString("MaSach"),
                        rs.getString("Số lần mượn")
                    };
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
}
