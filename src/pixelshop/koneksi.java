/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author redmibook
 */
public class koneksi {
    private static Connection koneksi;
    public static Connection getKoneksi() {
    if (koneksi == null) {
        try {
            String url = "jdbc:mysql://localhost:3306/pixelshop?serverTimezone=UTC";
            String user = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver"); // properly handled
            koneksi = DriverManager.getConnection(url, user, password);
            System.out.println("Berhasil");
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Have you tried CHECK-ing your XAMPP?");
            System.out.println("Database Error.");
        }
    }
    return koneksi;
}
    public static void main(String args[]){
        getKoneksi();
    }  

    void connect() {
       
    }


}