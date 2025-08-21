/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelshop;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author redmibook
 */
public class Route3 extends JPanel implements ActionListener, KeyListener {

    // Player properties
    int playerX = 100, playerY = 100;
    int speed = 4;
    String facing = "down";

    boolean up, down, left, right;

    // Game state
    boolean inDialogue = false;
    String dialogueText = "";

    // Walls
    ArrayList<Rectangle> walls = new ArrayList<>();

    // NPCs
    Rectangle npc = new Rectangle(200, 200, 32, 32);

    Timer timer;
    
     public Route3() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.BLACK);

        // Example walls
        walls.add(new Rectangle(50, 50, 300, 20));  // Top horizontal wall
        walls.add(new Rectangle(50, 330, 300, 20)); // Bottom horizontal wall
        walls.add(new Rectangle(50, 50, 20, 300));  // Left vertical wall
        walls.add(new Rectangle(330, 50, 20, 300)); // Right vertical wall

        timer = new Timer(16, this); // ~60 FPS
        timer.start();

        setFocusable(true);
        addKeyListener(this);
    }

      @Override
    public void actionPerformed(ActionEvent e) {
        if (!inDialogue) {
            int nextX = playerX;
            int nextY = playerY;

            if (up) {
                nextY -= speed;
                facing = "up";
            }
            if (down) {
                nextY += speed;
                facing = "down";
            }
            if (left) {
                nextX -= speed;
                facing = "left";
            }
            if (right) {
                nextX += speed;
                facing = "right";
            }

            if (!isColliding(nextX, nextY)) {
                playerX = nextX;
                playerY = nextY;
            }
        }

        repaint();
    }

    public boolean isColliding(int x, int y) {
        Rectangle playerRect = new Rectangle(x, y, 32, 32);
        for (Rectangle wall : walls) {
            if (playerRect.intersects(wall)) {
                return true;
            }
        }
        return false;
    }

    public void interact() {
        Rectangle interactZone = null;
        if (facing.equals("up")) interactZone = new Rectangle(playerX, playerY - 32, 32, 32);
        if (facing.equals("down")) interactZone = new Rectangle(playerX, playerY + 32, 32, 32);
        if (facing.equals("left")) interactZone = new Rectangle(playerX - 32, playerY, 32, 32);
        if (facing.equals("right")) interactZone = new Rectangle(playerX + 32, playerY, 32, 32);

        if (interactZone.intersects(npc)) {
            inDialogue = true;
            dialogueText = "Hello traveler! Press Z again to continue...";
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) up = true;
        if (code == KeyEvent.VK_DOWN) down = true;
        if (code == KeyEvent.VK_LEFT) left = true;
        if (code == KeyEvent.VK_RIGHT) right = true;

        if (code == KeyEvent.VK_Z) {
            if (!inDialogue) {
                interact();
            } else {
                // Dialogue click progression
                if (dialogueText.contains("continue")) {
                    dialogueText = "Safe travels!";
                } else {
                    inDialogue = false;
                    dialogueText = "";
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) up = false;
        if (code == KeyEvent.VK_DOWN) down = false;
        if (code == KeyEvent.VK_LEFT) left = false;
        if (code == KeyEvent.VK_RIGHT) right = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw walls
        g.setColor(Color.GRAY);
        for (Rectangle wall : walls) {
            g.fillRect(wall.x, wall.y, wall.width, wall.height);
        }

        // Draw NPC
        g.setColor(Color.YELLOW);
        g.fillRect(npc.x, npc.y, npc.width, npc.height);

        // Draw player
        g.setColor(Color.CYAN);
        g.fillRect(playerX, playerY, 32, 32);

        // Dialogue box
        if (inDialogue) {
            g.setColor(Color.WHITE);
            g.fillRect(20, 300, 360, 70);
            g.setColor(Color.BLACK);
            g.drawRect(20, 300, 360, 70);
            g.drawString(dialogueText, 30, 330);
        }
    }

   


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 720));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mini Undertale RPG");
        Route3 game = new Route3();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
         /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Route3().setVisible(true);
            }
        });
    }

      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
