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
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;


/**
 *
 * @author redmibook
 */
public class ProductAddFormSupplier extends javax.swing.JFrame {
    private PreparedStatement stat;
    private ResultSet rs;
    koneksi k = new koneksi();

    class ComboItem {
        private String label;
        private int value;

        public ComboItem(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public ProductAddFormSupplier() {
        setTitle("Fixed Size Window");

        // Set fixed window size
        setSize(1440, 720);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Disable resizing
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        initComponents();
        
          // ✅ Load GIF after components are created
    ImageIcon icon = new ImageIcon(getClass().getResource("/pixelshop/Idle_anon.gif"));
    jLabel1.setIcon(icon);

        loadCategories(comboKategori); // Fill category combo

        try {
            // Load custom font
            Font Text = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(28f);
            Font DialogText = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(18f);
            Font bubble = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(25f);
            Font Heading = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(50f);
            Bubble.setBorder(BorderFactory.createEmptyBorder());
            jScrollPane1.getViewport().setOpaque(false);   
            Bubble.setFont(bubble);
            jLabel4.setFont(DialogText);
            Cancel.setFont(Text);
            Add.setFont(Text);
            Reset.setFont(Text);
            textNamaProduk.setFont(Text);
            textHarga.setFont(Text);
            textStok.setFont(Text);
            comboKategori.setFont(Text);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCategories(JComboBox<ComboItem> comboKategori) {
        try {
            Connection conn = koneksi.getKoneksi();
            String sql = "SELECT id_kategori, nama FROM kategori";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            comboKategori.removeAllItems();

            while (rs.next()) {
                int id = rs.getInt("id_kategori");
                String name = rs.getString("nama");
                comboKategori.addItem(new ComboItem(name, id));
            }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    class TambahProduk {
        String namaProduk; // from textNamaProduk
        int idKategori;    // from comboKategori
        int stok;          // from textStok
        int harga;         // from textHarga

        public TambahProduk(String namaProduk, int idKategori, int stok, int harga) {
            this.namaProduk = namaProduk;
            this.idKategori = idKategori;
            this.stok = stok;
            this.harga = harga;
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
        textHarga = new javax.swing.JTextField();
        textNamaProduk = new javax.swing.JTextField();
        textStok = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboKategori = new javax.swing.JComboBox<>();
        Cancel = new javax.swing.JButton();
        Reset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Bubble = new javax.swing.JTextArea();
        Add = new javax.swing.JButton();
        DialogBox = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 720));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textHarga.setBackground(new java.awt.Color(0, 0, 0));
        textHarga.setFont(new java.awt.Font("Rubik", 0, 18)); // NOI18N
        textHarga.setForeground(new java.awt.Color(255, 255, 255));
        textHarga.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));
        textHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textHargaActionPerformed(evt);
            }
        });
        jPanel1.add(textHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, 390, 60));

        textNamaProduk.setBackground(new java.awt.Color(0, 0, 0));
        textNamaProduk.setFont(new java.awt.Font("Rubik", 0, 18)); // NOI18N
        textNamaProduk.setForeground(new java.awt.Color(255, 255, 255));
        textNamaProduk.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));
        textNamaProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNamaProdukActionPerformed(evt);
            }
        });
        jPanel1.add(textNamaProduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 390, 60));

        textStok.setBackground(new java.awt.Color(0, 0, 0));
        textStok.setFont(new java.awt.Font("Rubik", 0, 18)); // NOI18N
        textStok.setForeground(new java.awt.Color(255, 255, 255));
        textStok.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));
        textStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textStokActionPerformed(evt);
            }
        });
        jPanel1.add(textStok, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 390, 60));

        jLabel6.setFont(new java.awt.Font("Rubik", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Kategori");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 200, -1));

        jLabel7.setFont(new java.awt.Font("Rubik", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Harga");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 430, 200, -1));

        jLabel8.setFont(new java.awt.Font("Rubik", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Stok");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 320, 200, -1));

        jLabel4.setFont(new java.awt.Font("Rubik", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama Produk");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 200, -1));

        comboKategori.setBackground(new java.awt.Color(0, 0, 0));
        comboKategori.setForeground(new java.awt.Color(255, 255, 255));
        comboKategori.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255)));
        comboKategori.setFocusable(false);
        comboKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboKategoriActionPerformed(evt);
            }
        });
        jPanel1.add(comboKategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 390, 60));

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
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 560, 240, 30));

        Reset.setBackground(new java.awt.Color(255, 255, 255,0));
        Reset.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Reset.setForeground(new java.awt.Color(255, 255, 255));
        Reset.setText("RESET");
        Reset.setBorder(null);
        Reset.setBorderPainted(false);
        Reset.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Reset.setOpaque(false);
        Reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ResetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ResetMouseExited(evt);
            }
        });
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });
        jPanel1.add(Reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 590, 100, 20));

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
        Bubble.setForeground(new java.awt.Color(255, 255, 255));
        Bubble.setLineWrap(true);
        Bubble.setRows(2);
        Bubble.setText("...");
        Bubble.setWrapStyleWord(true);
        Bubble.setAutoscrolls(false);
        Bubble.setFocusable(false);
        Bubble.setOpaque(false);
        Bubble.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(Bubble);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 170, 270, 150));

        Add.setBackground(new java.awt.Color(255, 255, 255,0));
        Add.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Add.setForeground(new java.awt.Color(255, 255, 255));
        Add.setText("* ADD PRODUK");
        Add.setBorder(null);
        Add.setBorderPainted(false);
        Add.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Add.setOpaque(false);
        Add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddMouseExited(evt);
            }
        });
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        jPanel1.add(Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 200, 20));

        DialogBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/dialouge box lg.png"))); // NOI18N
        DialogBox.setText("jLabel3");
        jPanel1.add(DialogBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1440, -1));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, -10, 690, 480));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/ezgif-3b48dc6913bb46.gif"))); // NOI18N
        jPanel1.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1440, 720));

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
        new ProductViewSupplier().show();
        this.dispose();
    }//GEN-LAST:event_CancelActionPerformed

    private void AddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddMouseEntered
        // TODO add your handling code here:
        Add.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_AddMouseEntered

    private void AddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddMouseExited
        // TODO add your handling code here:
        Add.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AddMouseExited

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
  try {
    // Ambil id_supplier dari user yang login
    int idSupplier = User.currentUserId; // ← use the one from login

    // Ambil data dari form
    ComboItem selectedCategory = (ComboItem) comboKategori.getSelectedItem();
    int idKategori = selectedCategory.getValue();

    String namaProduk = textNamaProduk.getText().trim();
    String stokStr = textStok.getText().trim();
    String hargaStr = textHarga.getText().trim();

    // Validasi input
    if (namaProduk.isEmpty()) {
        Bubble.setText("What's the product name again?");
        return;
    }
    if (stokStr.isEmpty() || hargaStr.isEmpty()) {
        Bubble.setText("Don't forget stock and price!");
        return;
    }

    int stok = Integer.parseInt(stokStr);
    int harga = Integer.parseInt(hargaStr);

    // Buat object produk
    TambahProduk p = new TambahProduk(namaProduk, idKategori, stok, harga);

    // Insert ke database dengan id_supplier otomatis
    stat = k.getKoneksi().prepareStatement(
        "INSERT INTO produk (nama, id_kategori, jumlah, harga, id_supplier) VALUES (?, ?, ?, ?, ?)"
    );
    stat.setString(1, p.namaProduk);
    stat.setInt(2, p.idKategori);
    stat.setInt(3, p.stok);
    stat.setInt(4, p.harga);
    stat.setInt(5, idSupplier); // otomatis dari login
    stat.executeUpdate();

    Bubble.setText("*Hm... What to add next...");

    // Reset form
    textNamaProduk.setText("");
    textStok.setText("");
    textHarga.setText("");
    comboKategori.setSelectedIndex(0);

} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(null, "Stock and Price must be numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
}

    }//GEN-LAST:event_AddActionPerformed

    private void ResetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ResetMouseEntered
        // TODO add your handling code here:
        Reset.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_ResetMouseEntered

    private void ResetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ResetMouseExited
        // TODO add your handling code here:
        Reset.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_ResetMouseExited

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        // TODO add your handling code here:
      textNamaProduk.setText("");
comboKategori.setSelectedIndex(-1);
textStok.setText("");
textHarga.setText("");
Bubble.setText("Oh.. Nevermind");

    }//GEN-LAST:event_ResetActionPerformed

    private void textHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textHargaActionPerformed

    private void comboKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboKategoriActionPerformed

    private void textNamaProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNamaProdukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNamaProdukActionPerformed

    private void textStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textStokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textStokActionPerformed

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
            java.util.logging.Logger.getLogger(KategoriAddForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KategoriAddForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KategoriAddForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KategoriAddForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KategoriAddForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JLabel Background;
    private javax.swing.JTextArea Bubble;
    private javax.swing.JButton Cancel;
    private javax.swing.JLabel DialogBox;
    private javax.swing.JButton Reset;
    private javax.swing.JComboBox<ComboItem> comboKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField textHarga;
    private javax.swing.JTextField textNamaProduk;
    private javax.swing.JTextField textStok;
    // End of variables declaration//GEN-END:variables
}
