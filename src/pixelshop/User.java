/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelshop;

/**
 *
 * @author USER
 */
public class User {
    public static int currentUserId = 0;

    public int id_user, level;
    public String username, password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
