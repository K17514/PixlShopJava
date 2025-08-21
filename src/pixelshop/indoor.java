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
public class indoor extends javax.swing.JFrame {
    public void showThankYouMessage() {
        YoBills.setVisible(true); // show label when called
        Dialog.setText("Your order is being processed, here's your bill");
    }
public void showNota(int idNota) {
    try {
        Connection conn = koneksi.getKoneksi();
        String sql = "SELECT t.id_transaksi, p.nama AS nama_produk, t.total, u.nama AS nama_supplier " +
                     "FROM transaksi t " +
                     "JOIN produk p ON t.id_produk = p.id_produk " +
                     "JOIN user u ON t.id_supplier = u.id_user " +
                     "WHERE t.id_nota = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idNota);
        ResultSet rs = ps.executeQuery();

        StringBuilder notaText = new StringBuilder();
        notaText.append("========= INVOICE / NOTA =========\n");
        notaText.append("Nota ID: ").append(idNota).append("\n");
        notaText.append("Customer ID: ").append(User.currentUserId).append("\n\n");

        int grandTotal = 0;
        while (rs.next()) {
            String namaProduk = rs.getString("nama_produk");
            int total = rs.getInt("total");
            String namaSupplier = rs.getString("nama_supplier");

            notaText.append("- ").append(namaProduk)
                    .append(" | Supplier: ").append(namaSupplier)
                    .append(" | Harga: ").append(total).append("\n");

            grandTotal += total;
        }

        notaText.append("\nTotal Pembayaran: ").append(grandTotal).append("\n");
        notaText.append("==================================\n");

        // Show it in a text area or dialog
        JTextArea textArea = new JTextArea(notaText.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int option = JOptionPane.showOptionDialog(
            this,
            scrollPane,
            "Nota / Invoice",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Print", "Save as .txt", "Close"},
            "Close"
        );

        if (option == 0) {
            textArea.print(); // Print
        } else if (option == 1) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("nota_" + idNota + ".txt"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                FileWriter fw = new FileWriter(fileChooser.getSelectedFile());
                fw.write(notaText.toString());
                fw.close();
                JOptionPane.showMessageDialog(this, "Nota saved successfully.");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to show nota: " + e.getMessage());
    }
}

    /**
     * Creates new form DashKepala
     */
    public indoor() {
       setTitle("Fixed Size Window");

        // Set fixed window size
        setSize(1440, 720); // Width = 800px, Height = 600px

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Disable resizing
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        initComponents();
        
        try {
            // Load custom font
            Font Text = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(28f);
            Font DialogText = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(30f);
            Font Heading = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(50f);
            Exit.setFont(Text);
            Dialog.setFont(DialogText);
            Product.setFont(Text);
            Order.setFont(Text);
            Cart.setFont(Text);
            Talk.setFont(Text);
            Action1.setFont(Text);
        Action2.setFont(Text);
        Cancel.setFont(Text);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        // Initially hide jLabel4 and jLabel10
        
        
        YoBills.setVisible(false);
        Product.setVisible(false);
        Order.setVisible(false);
        Cart.setVisible(false);
        Talk.setVisible(false);
        Idle.setVisible(false);
        SmilingSpeaking.setVisible(false);
        Action1.setVisible(false);
        Action2.setVisible(false);
        Cancel.setVisible(false);

// Only run once on first click anywhere in the panel
jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
    private boolean done = false;

    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (done) return;
        done = true;

        // Hide buttons and idle image
        Idle.setVisible(true);
        Order.setVisible(true);
        Product.setVisible(true);
        Cart.setVisible(true);
        Talk.setVisible(true);
        

        // Show speaking gif and text
        NormalSpeaking.setVisible(false);
        YoBills.setVisible(false);
Dialog.setVisible(false);
    }
});


//         this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        jButton3 = new javax.swing.JButton();
        Action1 = new javax.swing.JButton();
        Action2 = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();
        Product = new javax.swing.JButton();
        Order = new javax.swing.JButton();
        Cart = new javax.swing.JButton();
        Talk = new javax.swing.JButton();
        Exit = new javax.swing.JButton();
        Dialog = new javax.swing.JLabel();
        SmilingSpeaking = new javax.swing.JLabel();
        YoBills = new javax.swing.JLabel();
        NormalSpeaking = new javax.swing.JLabel();
        Idle = new javax.swing.JLabel();
        DialogBox = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 720));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setBackground(new java.awt.Color(255, 255, 255,0));
        jButton3.setFont(new java.awt.Font("Rubik", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/Setting_Main_Icon.png"))); // NOI18N
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(53, 101, 161), 2, true));
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);
        jButton3.setFocusable(false);
        jButton3.setRequestFocusEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 70, 60));

        Action1.setBackground(new java.awt.Color(255, 255, 255,0));
        Action1.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Action1.setForeground(new java.awt.Color(255, 255, 255));
        Action1.setText("* About this shop");
        Action1.setBorder(null);
        Action1.setBorderPainted(false);
        Action1.setFocusPainted(false);
        Action1.setFocusable(false);
        Action1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Action1.setOpaque(false);
        Action1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Action1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Action1MouseExited(evt);
            }
        });
        Action1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Action1ActionPerformed(evt);
            }
        });
        jPanel1.add(Action1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, 250, 30));

        Action2.setBackground(new java.awt.Color(255, 255, 255,0));
        Action2.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Action2.setForeground(new java.awt.Color(255, 255, 255));
        Action2.setText("* Who are you?");
        Action2.setBorder(null);
        Action2.setBorderPainted(false);
        Action2.setFocusPainted(false);
        Action2.setFocusable(false);
        Action2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Action2.setOpaque(false);
        Action2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Action2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Action2MouseExited(evt);
            }
        });
        Action2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Action2ActionPerformed(evt);
            }
        });
        jPanel1.add(Action2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 240, 30));

        Cancel.setBackground(new java.awt.Color(255, 255, 255,0));
        Cancel.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Cancel.setForeground(new java.awt.Color(255, 255, 255));
        Cancel.setText("* ...");
        Cancel.setBorder(null);
        Cancel.setBorderPainted(false);
        Cancel.setFocusPainted(false);
        Cancel.setFocusable(false);
        Cancel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Cancel.setOpaque(false);
        Cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                CancelMouseExited(evt);
            }
        });
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 610, 240, 30));

        Product.setBackground(new java.awt.Color(255, 255, 255,0));
        Product.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Product.setForeground(new java.awt.Color(255, 255, 255));
        Product.setText("* PRODUCT LIST");
        Product.setBorder(null);
        Product.setBorderPainted(false);
        Product.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Product.setOpaque(false);
        Product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ProductMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ProductMouseExited(evt);
            }
        });
        Product.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProductActionPerformed(evt);
            }
        });
        jPanel1.add(Product, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, 250, 20));

        Order.setBackground(new java.awt.Color(255, 255, 255,0));
        Order.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Order.setForeground(new java.awt.Color(255, 255, 255));
        Order.setText("* MY ORDERS");
        Order.setBorder(null);
        Order.setBorderPainted(false);
        Order.setFocusPainted(false);
        Order.setFocusable(false);
        Order.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Order.setOpaque(false);
        Order.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                OrderMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                OrderMouseExited(evt);
            }
        });
        Order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderActionPerformed(evt);
            }
        });
        jPanel1.add(Order, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 470, 190, 20));

        Cart.setBackground(new java.awt.Color(255, 255, 255,0));
        Cart.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Cart.setForeground(new java.awt.Color(255, 255, 255));
        Cart.setText("* MY CART");
        Cart.setBorder(null);
        Cart.setBorderPainted(false);
        Cart.setFocusPainted(false);
        Cart.setFocusable(false);
        Cart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Cart.setOpaque(false);
        Cart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                CartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                CartMouseExited(evt);
            }
        });
        Cart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CartActionPerformed(evt);
            }
        });
        jPanel1.add(Cart, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 120, 20));

        Talk.setBackground(new java.awt.Color(255, 255, 255,0));
        Talk.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Talk.setForeground(new java.awt.Color(255, 255, 255));
        Talk.setText("* TALK");
        Talk.setBorder(null);
        Talk.setBorderPainted(false);
        Talk.setFocusPainted(false);
        Talk.setFocusable(false);
        Talk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Talk.setOpaque(false);
        Talk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TalkMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TalkMouseExited(evt);
            }
        });
        Talk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TalkActionPerformed(evt);
            }
        });
        jPanel1.add(Talk, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 610, 90, 20));

        Exit.setBackground(new java.awt.Color(255, 255, 255,0));
        Exit.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Exit.setForeground(new java.awt.Color(255, 255, 255));
        Exit.setText("Exit");
        Exit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Exit.setBorderPainted(false);
        Exit.setFocusPainted(false);
        Exit.setFocusable(false);
        Exit.setOpaque(false);
        Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ExitMouseExited(evt);
            }
        });
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });
        jPanel1.add(Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 110, 230, 520));

        Dialog.setFont(new java.awt.Font("Lucida Calligraphy", 1, 24)); // NOI18N
        Dialog.setForeground(new java.awt.Color(255, 255, 255));
        Dialog.setText("* Welcome, what are you searching for?");
        Dialog.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel1.add(Dialog, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 450, 970, 80));

        SmilingSpeaking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/smilingspeaking.gif"))); // NOI18N
        jPanel1.add(SmilingSpeaking, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 590, 380));

        YoBills.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/YoBills-ezgif.com-effects.gif"))); // NOI18N
        jPanel1.add(YoBills, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 590, 380));

        NormalSpeaking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/normalspeaking.gif"))); // NOI18N
        jPanel1.add(NormalSpeaking, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 590, 380));

        Idle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/idle.gif"))); // NOI18N
        jPanel1.add(Idle, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 50, 590, 380));

        DialogBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/dialouge box.png"))); // NOI18N
        jPanel1.add(DialogBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 1110, 380));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/download.gif"))); // NOI18N
        jPanel1.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, -490, 2110, 1620));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1440, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        // TODO add your handling code here:
        new Login().show();
        this.dispose();
    }//GEN-LAST:event_ExitActionPerformed

    private void ProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProductActionPerformed
        // TODO add your handling code here:
        new product().show();
        this.dispose();
    }//GEN-LAST:event_ProductActionPerformed

    private void CartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CartActionPerformed
        new cart().show();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_CartActionPerformed

    private void TalkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TalkActionPerformed
        // TODO add your handling code here:
        Cancel.setVisible(true);
        Action1.setVisible(true);
        Action2.setVisible(true);
        
        Product.setVisible(false);
        Order.setVisible(false);
        Cart.setVisible(false);
        Talk.setVisible(false);
//        new Siswa1().show();
//        this.dispose();
    }//GEN-LAST:event_TalkActionPerformed

    private void ProductMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductMouseEntered
        // TODO add your handling code here:
        Product.setForeground(new java.awt.Color(255, 239, 0)); // default color
    }//GEN-LAST:event_ProductMouseEntered

    private void ProductMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProductMouseExited
        // TODO add your handling code here:
        Product.setForeground(new java.awt.Color(255, 255, 255)); // default color
    }//GEN-LAST:event_ProductMouseExited

    private void CartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CartMouseEntered
        // TODO add your handling code here:
        Cart.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_CartMouseEntered

    private void TalkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TalkMouseEntered
        // TODO add your handling code here:
        Talk.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_TalkMouseEntered

    private void CartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CartMouseExited
        // TODO add your handling code here:
        Cart.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_CartMouseExited

    private void TalkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TalkMouseExited
        // TODO add your handling code here:
        Talk.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_TalkMouseExited

    private void ExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitMouseEntered
        // TODO add your handling code here:
        Exit.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_ExitMouseEntered

    private void ExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitMouseExited
        // TODO add your handling code here:
        Exit.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_ExitMouseExited

    private void CancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelMouseEntered
        // TODO add your handling code here:
        Cancel.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_CancelMouseEntered

    private void CancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CancelMouseExited
        // TODO add your handling code here:
        Cancel.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_CancelMouseExited

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        // TODO add your handling code here:
        Cancel.setVisible(false);
        Action1.setVisible(false);
        Action2.setVisible(false);
        
        Product.setVisible(true);
        Order.setVisible(true);
        Cart.setVisible(true);
        Talk.setVisible(true);
    }//GEN-LAST:event_CancelActionPerformed

    private void Action2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Action2MouseEntered
        // TODO add your handling code here:
        Action2.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_Action2MouseEntered

    private void Action2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Action2MouseExited
        // TODO add your handling code here:
        Action2.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_Action2MouseExited

    private void Action2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Action2ActionPerformed
        // TODO add your handling code here:
        NormalSpeaking.setVisible(true);
        Cancel.setVisible(false);
    Action1.setVisible(false);
    Action2.setVisible(false);
    Idle.setVisible(false);

    Dialog.setVisible(true);
    Dialog.setText("I am the creator of this 'world'.");
    
    
    final int[] clickCount = {0}; // Use array to allow modification inside inner class

    jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            clickCount[0]++;

            if (clickCount[0] == 1) {
                Dialog.setText("Nah, just kidding. I'm Ryuku, the owner of this shop.");
                SmilingSpeaking.setVisible(true);
                NormalSpeaking.setVisible(false);
            } else if (clickCount[0] == 2) {
                Dialog.setText("What are you searching for?");
            } else if (clickCount[0] == 3) {
                Idle.setVisible(true);
                Dialog.setVisible(false);
                Talk.setVisible(true);
                Cart.setVisible(true);
                Product.setVisible(true);
                Order.setVisible(true);
                SmilingSpeaking.setVisible(false);
            } else {
                removeMouseListener(this);
            }
        }
    });
    }//GEN-LAST:event_Action2ActionPerformed

    private void Action1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Action1MouseEntered
        // TODO add your handling code here:
        Action1.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_Action1MouseEntered

    private void Action1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Action1MouseExited
        // TODO add your handling code here:
        Action1.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_Action1MouseExited

    private void Action1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Action1ActionPerformed
        // TODO add your handling code here:
        NormalSpeaking.setVisible(true);
        Idle.setVisible(false);
        Cancel.setVisible(false);
    Action1.setVisible(false);
    Action2.setVisible(false);
    

    Dialog.setVisible(true);
    Dialog.setText("It just opened this morning, feel honored to be the first customer.");
    
    
    final int[] clickCount = {0}; // Use array to allow modification inside inner class

    jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            clickCount[0]++;

            if (clickCount[0] == 1) {
                Dialog.setText("I will give you an achievement once I master Java Netbeans, stay tuned.");
                SmilingSpeaking.setVisible(true);
                NormalSpeaking.setVisible(false);            
            } else if (clickCount[0] == 2) {
                Dialog.setText("What are you searching for?");
            } else if (clickCount[0] == 3) {
                Dialog.setVisible(false);
                Idle.setVisible(true);
                SmilingSpeaking.setVisible(false);
                Talk.setVisible(true);
                Cart.setVisible(true);
                Product.setVisible(true);
                Order.setVisible(true);
            } else {
                removeMouseListener(this);
            }
        }
    });
    }//GEN-LAST:event_Action1ActionPerformed

    private void OrderMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OrderMouseEntered
        // TODO add your handling code here:
        Order.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_OrderMouseEntered

    private void OrderMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OrderMouseExited
        // TODO add your handling code here:
        Order.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_OrderMouseExited

    private void OrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderActionPerformed
        // TODO add your handling code here:
        new order().show();
        this.dispose();
    }//GEN-LAST:event_OrderActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Setting().show();
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(indoor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(indoor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(indoor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(indoor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new indoor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Action1;
    private javax.swing.JButton Action2;
    private javax.swing.JLabel Background;
    private javax.swing.JButton Cancel;
    private javax.swing.JButton Cart;
    private javax.swing.JLabel Dialog;
    private javax.swing.JLabel DialogBox;
    private javax.swing.JButton Exit;
    private javax.swing.JLabel Idle;
    private javax.swing.JLabel NormalSpeaking;
    private javax.swing.JButton Order;
    private javax.swing.JButton Product;
    private javax.swing.JLabel SmilingSpeaking;
    private javax.swing.JButton Talk;
    private javax.swing.JLabel YoBills;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
