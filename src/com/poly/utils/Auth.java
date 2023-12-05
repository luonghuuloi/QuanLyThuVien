/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.utils;

import com.poly.entity.TaiKhoan;

/**
 *
 * @author ADMIN
 */
public class Auth {
    public static TaiKhoan user = null;

    public static void dangXuat() {
        Auth.user = null;
    }

    public static boolean isLogin() {
        return Auth.user != null;
    }

    public static boolean isManager() {
        return Auth.user != null && user.isVaiTro();
    }
}
