/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poly.utils;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author ADMIN
 */
public class XImage {
     public static Image getAppIcon() {
        String url = "src\\com\\poly\\icon\\fpt.png";
        try {
            return new ImageIcon(url).getImage();
        } catch (RuntimeException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    public static void save(File src) {
       File dst = new File("logos",src.getName());
       if(!dst.getParentFile().exists()){
           dst.getParentFile().mkdirs();
       }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from,to,StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ImageIcon read(String fileName) {
        File path = new File("Logos", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
}
