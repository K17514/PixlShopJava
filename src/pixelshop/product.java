/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixelshop;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;


/**
 *
 * @author redmibook
 */
public class product extends javax.swing.JFrame {

    /**
     * Creates new form DashKepala
     */
    public product() {
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
        loadCategories();
        loadProducts();
        try {
            // Load custom font
            Font Text = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(28f);
            Font DialogText = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(18f);
            Font Heading = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(50f);
          Bubble.setFont(DialogText);
          Cart.setFont(Text);
          Cancel.setFont(Text);
          AddCart.setFont(Text);
          NextBtn.setFont(Text);
          PrevBtn.setFont(Text);
        
        jList1.setCellRenderer(new DefaultListCellRenderer() {
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String originalText = value.toString();
        label.setText("* " + originalText);
        if (isSelected) {
            label.setBackground(Color.BLACK);
            label.setForeground(Color.YELLOW);   
        } else {
            label.setBackground(Color.BLACK); // Custom selected bg
            label.setForeground(Color.WHITE);   
        
        }
        label.setFont(Text);
        label.setBorder(null);
        return label;
    }
});
        
        jList2.setCellRenderer(new DefaultListCellRenderer() {
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String originalText = value.toString();
        label.setText("* " + originalText);
        if (isSelected) {
            label.setBackground(Color.BLACK);
            label.setForeground(Color.YELLOW);   
        } else {
            label.setBackground(Color.BLACK); // Custom selected bg
            label.setForeground(Color.WHITE);   
        
        }
        label.setFont(Text);
        label.setBorder(null);
        return label;
    }
});
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        
        
        SmilingSpeaking.setVisible(false);
        NormalSpeaking.setVisible(false);
        HappySpeaking.setVisible(false);
    }
    
    
    
    private void loadCategories() {
    try {
        Connection conn = koneksi.getKoneksi();
        String sql = "SELECT nama FROM kategori";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        DefaultListModel<String> categoryModel = new DefaultListModel<>();
        while (rs.next()) {
            categoryModel.addElement(rs.getString("nama"));
        }
        jList1.setModel(categoryModel);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    private void loadProducts() {
    try {
        Connection conn = koneksi.getKoneksi();
        String sql = "SELECT nama FROM produk";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        DefaultListModel<String> productModel = new DefaultListModel<>();
        while (rs.next()) {
            productModel.addElement(rs.getString("nama"));
        }
        jList2.setModel(productModel);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
    private void loadProductsByCategory(String categoryName) {
    try {
        Connection conn = koneksi.getKoneksi();
        String sql = "SELECT p.nama FROM produk p JOIN kategori c ON p.id_kategori = c.id_kategori WHERE c.nama = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, categoryName);
        ResultSet rs = stmt.executeQuery();

        DefaultListModel<String> productModel = new DefaultListModel<>();
        while (rs.next()) {
            productModel.addElement(rs.getString("nama"));
        }
        jList2.setModel(productModel);
    } catch (SQLException e) {
        e.printStackTrace();
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
        Cancel = new javax.swing.JButton();
        Cart = new javax.swing.JButton();
        PrevBtn = new javax.swing.JButton();
        NextBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Bubble = new javax.swing.JTextArea();
        TextBubble = new javax.swing.JLabel();
        AddCart = new javax.swing.JButton();
        ProductList = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        CategoryList = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        HappySpeaking = new javax.swing.JLabel();
        SmilingSpeaking = new javax.swing.JLabel();
        NormalSpeaking = new javax.swing.JLabel();
        Idle = new javax.swing.JLabel();
        DialogBox = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 720));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Cancel.setBackground(new java.awt.Color(255, 255, 255,0));
        Cancel.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Cancel.setForeground(new java.awt.Color(255, 255, 255));
        Cancel.setText("* BACK");
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
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 620, 240, 30));

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
        jPanel1.add(Cart, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 570, 120, 20));

        PrevBtn.setBackground(new java.awt.Color(255, 255, 255,0));
        PrevBtn.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        PrevBtn.setForeground(new java.awt.Color(255, 255, 255));
        PrevBtn.setText("< PREV");
        PrevBtn.setBorder(null);
        PrevBtn.setBorderPainted(false);
        PrevBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PrevBtn.setOpaque(false);
        PrevBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                PrevBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PrevBtnMouseExited(evt);
            }
        });
        PrevBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrevBtnActionPerformed(evt);
            }
        });
        jPanel1.add(PrevBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 590, 90, 20));

        NextBtn.setBackground(new java.awt.Color(255, 255, 255,0));
        NextBtn.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        NextBtn.setForeground(new java.awt.Color(255, 255, 255));
        NextBtn.setText("NEXT >");
        NextBtn.setBorder(null);
        NextBtn.setBorderPainted(false);
        NextBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        NextBtn.setOpaque(false);
        NextBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                NextBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                NextBtnMouseExited(evt);
            }
        });
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });
        jPanel1.add(NextBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 590, 90, 20));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255, 0));
        jScrollPane1.setBorder(null);
        jScrollPane1.setEnabled(false);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setRequestFocusEnabled(false);
        jScrollPane1.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setWheelScrollingEnabled(false);

        Bubble.setEditable(false);
        Bubble.setBackground(new Color(0, 0, 0, 0));
        Bubble.setColumns(5);
        Bubble.setLineWrap(true);
        Bubble.setRows(2);
        Bubble.setText("Found something intresting?");
        Bubble.setWrapStyleWord(true);
        Bubble.setAutoscrolls(false);
        Bubble.setFocusable(false);
        Bubble.setOpaque(false);
        Bubble.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(Bubble);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 260, 140, 100));

        TextBubble.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/TextBubble.png"))); // NOI18N
        jPanel1.add(TextBubble, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 230, 250, 170));

        AddCart.setBackground(new java.awt.Color(255, 255, 255,0));
        AddCart.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        AddCart.setForeground(new java.awt.Color(255, 255, 255));
        AddCart.setText("* ADD TO CART");
        AddCart.setBorder(null);
        AddCart.setBorderPainted(false);
        AddCart.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        AddCart.setOpaque(false);
        AddCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AddCartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddCartMouseExited(evt);
            }
        });
        AddCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCartActionPerformed(evt);
            }
        });
        jPanel1.add(AddCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 180, 20));

        ProductList.setBorder(null);

        jList2.setBackground(new java.awt.Color(0, 0, 0));
        jList2.setForeground(new java.awt.Color(255, 255, 255));
        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        ProductList.setViewportView(jList2);

        jPanel1.add(ProductList, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 430, 470));

        CategoryList.setBorder(null);

        jList1.setBackground(new java.awt.Color(0, 0, 0));
        jList1.setForeground(new java.awt.Color(255, 255, 255));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        CategoryList.setViewportView(jList1);

        jPanel1.add(CategoryList, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 210, 470));

        HappySpeaking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/HappySpeaking-ezgif.com-effects.gif"))); // NOI18N
        jPanel1.add(HappySpeaking, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 100, 590, 380));

        SmilingSpeaking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/smilingspeaking.gif"))); // NOI18N
        jPanel1.add(SmilingSpeaking, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 100, 590, 380));

        NormalSpeaking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/normalspeaking.gif"))); // NOI18N
        jPanel1.add(NormalSpeaking, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 100, 590, 380));

        Idle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/idle.gif"))); // NOI18N
        jPanel1.add(Idle, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 100, 590, 380));

        DialogBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/dialouge box lg.png"))); // NOI18N
        DialogBox.setText("jLabel3");
        jPanel1.add(DialogBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1440, -1));

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
        new indoor().show();
        this.dispose();
    }//GEN-LAST:event_CancelActionPerformed

    private void CartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CartMouseEntered
        // TODO add your handling code here:
        Cart.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_CartMouseEntered

    private void CartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CartMouseExited
        // TODO add your handling code here:
        Cart.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_CartMouseExited

    private void CartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CartActionPerformed
                new cart().show();
                this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_CartActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

        String selectedCategory = jList1.getSelectedValue();
        if (selectedCategory != null) {
            loadProductsByCategory(selectedCategory);
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void AddCartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddCartMouseEntered
        // TODO add your handling code here:
        AddCart.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_AddCartMouseEntered

    private void AddCartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddCartMouseExited
        // TODO add your handling code here:
        AddCart.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AddCartMouseExited

    private void AddCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCartActionPerformed
         try {
        Connection conn = koneksi.getKoneksi();
       String selectedProduct = jList2.getSelectedValue();

        // Get product ID from database based on selected name
        String getIdQuery = "SELECT id_produk FROM produk WHERE nama = ?";
        PreparedStatement getIdStmt = conn.prepareStatement(getIdQuery);
        getIdStmt.setString(1, selectedProduct);
        ResultSet rs = getIdStmt.executeQuery();

        if (rs.next()) {
            int productId = rs.getInt("id_produk");

            // Insert into cart
            String insertQuery = "INSERT INTO cart (id_customer, id_produk) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setInt(1, User.currentUserId);
            insertStmt.setInt(2, productId);
            insertStmt.executeUpdate();

            Bubble.setText("Thank you for purchasing!");
            HappySpeaking.setVisible(true);
            SmilingSpeaking.setVisible(false);
            NormalSpeaking.setVisible(false);
            Idle.setVisible(false);
        } else {
            Bubble.setText("Hm? I dont think i recognize that product?.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        Bubble.setText("Error adding to cart: " + e.getMessage());
    }
    }//GEN-LAST:event_AddCartActionPerformed

    private void NextBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextBtnMouseEntered
        // TODO add your handling code here:
        NextBtn.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_NextBtnMouseEntered

    private void NextBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NextBtnMouseExited
        // TODO add your handling code here:
        NextBtn.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_NextBtnMouseExited

    private void NextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NextBtnActionPerformed

    private void PrevBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrevBtnMouseEntered
        // TODO add your handling code here:
        PrevBtn.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_PrevBtnMouseEntered

    private void PrevBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PrevBtnMouseExited
        // TODO add your handling code here:
        PrevBtn.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_PrevBtnMouseExited

    private void PrevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrevBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevBtnActionPerformed

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
      // Assume 'conn' is your valid database connection
 String selectedProduct = jList2.getSelectedValue(); // Get the selected item
    if (selectedProduct != null) {
        try {
            Connection conn = koneksi.getKoneksi();
            String sql = "SELECT jumlah, harga FROM produk WHERE nama = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, selectedProduct);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int jumlah = rs.getInt("jumlah");
                double harga = rs.getDouble("harga");

                Bubble.setText(
                    selectedProduct + "?" +
                    "\nI have " + jumlah + " of those." +
                    "\nIt's only " + String.format("Rp%,.0f", harga).replace(',', '.') + 
                    "\n you intrested?"
                );
                if (jList2.getSelectedIndex() == -1) {
    Bubble.setText("found anything intresting?");
}
            }
            NormalSpeaking.setVisible(true);
            HappySpeaking.setVisible(false);
            SmilingSpeaking.setVisible(false);
            Idle.setVisible(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_jList2ValueChanged

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
            java.util.logging.Logger.getLogger(product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new product().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddCart;
    private javax.swing.JLabel Background;
    private javax.swing.JTextArea Bubble;
    private javax.swing.JButton Cancel;
    private javax.swing.JButton Cart;
    private javax.swing.JScrollPane CategoryList;
    private javax.swing.JLabel DialogBox;
    private javax.swing.JLabel HappySpeaking;
    private javax.swing.JLabel Idle;
    private javax.swing.JButton NextBtn;
    private javax.swing.JLabel NormalSpeaking;
    private javax.swing.JButton PrevBtn;
    private javax.swing.JScrollPane ProductList;
    private javax.swing.JLabel SmilingSpeaking;
    private javax.swing.JLabel TextBubble;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
