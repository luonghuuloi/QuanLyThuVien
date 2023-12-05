/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ADMIN
 */
public class JDBCHelper {
     private static String driver ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyThuVien";
    private static String username = "sa";
    private static String password = "123";
    
    static {
        try {
            Class .forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }    
    
        public static PreparedStatement preparedStatement(String sql, Object...args) throws SQLException {
        Connection  connection = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;       
        if(sql.trim().startsWith("{")){
            pstmt = connection.prepareCall(sql);            
        } else {
           pstmt = connection.prepareStatement(sql);
        }
        for(int i = 0 ; i < args.length; i++){
            pstmt.setObject(i + 1, args[i]);
        }     
        return pstmt;
    }
    
    public static ResultSet executeQuery(String sql, Object...args){
        try {
            PreparedStatement stmt = preparedStatement(sql, args);
            return stmt.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
    
    public static int executeUpdate(String sql, Object...args){
        try {
            PreparedStatement stmt = preparedStatement(sql, args);
            try {
                return stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    
    public static Object value(String sql, Object...args){
        try {
            ResultSet rs =query(sql, args);
            if(rs.next()){
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    
        
    
    }

    private static ResultSet query(String sql, Object[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
