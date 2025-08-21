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
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 *
 * @author redmibook
 */

public class NotaDetail extends javax.swing.JFrame {
    private int idNota;

    public NotaDetail() {
    initComponents(); // You might skip loadCartItems() here if it depends on idNota
}
    /**
     * Creates new form DashKepala
     */
    public NotaDetail(int idNota) {
       setTitle("Fixed Size Window");

        // Set fixed window size
        setSize(1440, 720); // Width = 800px, Height = 600px

        // Center the window on the screen
        setLocationRelativeTo(null);

        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
         this.idNota = idNota;
        initComponents();
        loadCartItems();
        try {
            Font Text = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(30f);
            Font DialogText = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(18f);
            Font Heading = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(50f);
        Cancel.setFont(Text);
        Receipt.setFont(Text);
        Accept.setFont(Text);
//        jList1.setFont(Text);
        Title.setFont(Heading);
        Status.setFont(Heading);
        
        jList1.setCellRenderer(new DefaultListCellRenderer() {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        String text = value.toString();
        String[] parts = text.split(" -- Supplier: ");
        String line1 = "* " + parts[0]; // nama produk dan harga
        String supplierText = parts.length > 1 ? "  Supplier: " + parts[1] : "";

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(isSelected ? Color.BLACK : Color.BLACK);
        textPane.setForeground(isSelected ? Color.YELLOW : Color.WHITE);
        textPane.setOpaque(true);
        textPane.setBorder(null);

        StyledDocument doc = textPane.getStyledDocument();

        try {
            // Load font dan register
            File fontFile = new File("src/fonts/DeterminationSansWebRegular-369X.ttf");
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(baseFont);

            // Ambil nama font family setelah register
            String fontFamily = baseFont.getFamily();

            // Style untuk line 1 (nama produk)
            SimpleAttributeSet line1Style = new SimpleAttributeSet();
            StyleConstants.setFontFamily(line1Style, fontFamily);
            StyleConstants.setFontSize(line1Style, 25);
            StyleConstants.setForeground(line1Style, isSelected ? Color.YELLOW : Color.WHITE);

            // Style untuk supplier
            SimpleAttributeSet supplierStyle = new SimpleAttributeSet();
            StyleConstants.setFontFamily(supplierStyle, fontFamily);
            StyleConstants.setFontSize(supplierStyle, 15);
            StyleConstants.setForeground(supplierStyle, isSelected ? Color.YELLOW : Color.WHITE);

            // Tambahkan ke dokumen
            doc.insertString(doc.getLength(), line1 + "\n", line1Style);
            doc.insertString(doc.getLength(), supplierText, supplierStyle);

        } catch (Exception e) {
            e.printStackTrace();
            textPane.setText(line1 + "\n" + supplierText); // fallback plain text
        }

        return textPane;
    }
});



        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        
    }
    

// ...

private void loadCartItems() {
    try {
        Connection conn = koneksi.getKoneksi();

        // Ambil produk + nama supplier dari transaksi & produk
        String sql = "SELECT p.nama, p.harga, u.nama AS supplier FROM transaksi t JOIN produk p ON t.id_produk = p.id_produk JOIN user u ON p.id_supplier = u.id_user WHERE t.id_nota = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idNota);
        ResultSet rs = stmt.executeQuery();

        DefaultListModel<String> productModel = new DefaultListModel<>();
        int totalHarga = 0;

        while (rs.next()) {
            String nama = rs.getString("nama");
            int harga = rs.getInt("harga");
            String supplier = rs.getString("supplier");
            totalHarga += harga;

            String itemDisplay = nama + " -- Rp " + String.format("%,d", harga).replace(',', '.') + " -- Supplier: " + supplier;
            productModel.addElement(itemDisplay);
        }

        jList1.setModel(productModel);

        // Format harga total (tanpa desimal)
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        rupiahFormat.setMaximumFractionDigits(0);
        rupiahFormat.setMinimumFractionDigits(0);
        String formattedTotal = rupiahFormat.format(totalHarga);

        // Ambil status dari nota
        String statusSQL = "SELECT status FROM nota WHERE id_nota = ?";
        PreparedStatement statusStmt = conn.prepareStatement(statusSQL);
        statusStmt.setInt(1, idNota);
        ResultSet statusRs = statusStmt.executeQuery();

        String statusText = "";
        if (statusRs.next()) {
            int status = statusRs.getInt("status");
            switch (status) {
                case 1:
                    statusText = "Pending";
                    Status.setForeground(Color.YELLOW);
                    break;
                case 2:
                    statusText = "Sending";
                    Status.setForeground(Color.ORANGE);
                    break;
                case 3:
                case 4:
                    statusText = "Done";
                    Status.setForeground(Color.GREEN);
                    break;
            }

            // ✅ Enable/disable Accept button based on status
            if (status == 3) {
                Accept.setEnabled(true);
                Accept.setVisible(true);
            } else {
                Accept.setEnabled(false);
                Accept.setVisible(false);
            }
        }

        Status.setText(statusText + " | Total: " + formattedTotal);

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
        Status = new javax.swing.JLabel();
        CategoryList = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        Title = new javax.swing.JLabel();
        Receipt = new javax.swing.JButton();
        Accept = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();
        DialogBox = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 720));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Status.setFont(new java.awt.Font("Lucida Calligraphy", 1, 60)); // NOI18N
        Status.setForeground(new java.awt.Color(255, 255, 255));
        Status.setText("Status.");
        jPanel1.add(Status, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 540, 550, 130));

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

        jPanel1.add(CategoryList, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, 770, 460));

        Title.setFont(new java.awt.Font("Lucida Calligraphy", 1, 60)); // NOI18N
        Title.setForeground(new java.awt.Color(255, 255, 255));
        Title.setText("Order Detail");
        jPanel1.add(Title, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, 550, 130));

        Receipt.setBackground(new java.awt.Color(255, 255, 255,0));
        Receipt.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Receipt.setForeground(new java.awt.Color(255, 255, 255));
        Receipt.setText("* PRINT RECEIPT");
        Receipt.setBorder(null);
        Receipt.setBorderPainted(false);
        Receipt.setFocusPainted(false);
        Receipt.setFocusable(false);
        Receipt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Receipt.setOpaque(false);
        Receipt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ReceiptMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ReceiptMouseExited(evt);
            }
        });
        Receipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReceiptActionPerformed(evt);
            }
        });
        jPanel1.add(Receipt, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 530, 220, 30));

        Accept.setBackground(new java.awt.Color(255, 255, 255,0));
        Accept.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Accept.setForeground(new java.awt.Color(255, 255, 255));
        Accept.setText("*ACCEPT ORDER");
        Accept.setBorder(null);
        Accept.setBorderPainted(false);
        Accept.setFocusPainted(false);
        Accept.setFocusable(false);
        Accept.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Accept.setOpaque(false);
        Accept.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AcceptMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AcceptMouseExited(evt);
            }
        });
        Accept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcceptActionPerformed(evt);
            }
        });
        jPanel1.add(Accept, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 470, 220, 30));

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
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 590, 130, 30));

        DialogBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/cartbox.png"))); // NOI18N
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
        new order().show();
        this.dispose();
    }//GEN-LAST:event_CancelActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

    }//GEN-LAST:event_jList1ValueChanged

    private void ReceiptMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReceiptMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_ReceiptMouseEntered

    private void ReceiptMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReceiptMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_ReceiptMouseExited

    private void ReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReceiptActionPerformed
        // TODO add your handling code here:
        try {
        Connection conn = koneksi.getKoneksi();
        StringBuilder notaContent = new StringBuilder();

        notaContent.append("=========== NOTA PEMBELIAN ===========\n");
        notaContent.append("ID Nota: ").append(idNota).append("\n");
        notaContent.append("ID Customer: ").append(User.currentUserId).append("\n\n");

        String sql = "SELECT p.nama, p.harga, u.nama AS supplier " +
                     "FROM transaksi t " +
                     "JOIN produk p ON t.id_produk = p.id_produk " +
                     "JOIN user u ON p.id_supplier = u.id_user " +
                     "WHERE t.id_nota = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idNota);
        ResultSet rs = stmt.executeQuery();

        int totalHarga = 0;

        while (rs.next()) {
            String nama = rs.getString("nama");
            int harga = rs.getInt("harga");
            String supplier = rs.getString("supplier");
            totalHarga += harga;

            notaContent.append("- ")
                       .append(nama)
                       .append(" (Rp ")
                       .append(String.format("%,d", harga).replace(',', '.'))
                       .append(") - Supplier: ")
                       .append(supplier)
                       .append("\n");
        }

        notaContent.append("\nTotal: Rp ")
                   .append(String.format("%,d", totalHarga).replace(',', '.'))
                   .append("\n");
        notaContent.append("=======================================\n");

        // Show dialog with options
        JTextArea textArea = new JTextArea(notaContent.toString());
        textArea.setEditable(false);
        int option = JOptionPane.showOptionDialog(
            this,
            new JScrollPane(textArea),
            "Print or Save Receipt",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new Object[]{"Print", "Save as .txt", "Cancel"},
            "Cancel"
        );

        if (option == 0) {
            textArea.print(); // 🖨️ Print the receipt
        } else if (option == 1) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("nota_" + idNota + ".txt"));
            int fileOption = fileChooser.showSaveDialog(this);
            if (fileOption == JFileChooser.APPROVE_OPTION) {
                FileWriter writer = new FileWriter(fileChooser.getSelectedFile());
                writer.write(notaContent.toString());
                writer.close();
                JOptionPane.showMessageDialog(this, "Receipt saved successfully.");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to process receipt: " + e.getMessage());
    }
    }//GEN-LAST:event_ReceiptActionPerformed

    private void AcceptMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcceptMouseEntered
        // TODO add your handling code here:\
          Accept.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_AcceptMouseEntered

    private void AcceptMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AcceptMouseExited
        // TODO add your handling code here:
          Accept.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AcceptMouseExited

    private void AcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptActionPerformed
// Pastikan ada nota yang dipilih
if (idNota <= 0) {
    JOptionPane.showMessageDialog(this, "Pilih nota terlebih dahulu.");
    return;
}

int confirm = JOptionPane.showConfirmDialog(this,
        "Yakin ingin menandai pesanan ini sebagai selesai?", 
        "Konfirmasi", JOptionPane.YES_NO_OPTION);

if (confirm != JOptionPane.YES_OPTION) return;

Connection conn = null;
PreparedStatement ps = null;

try {
    conn = koneksi.getKoneksi(); // ambil koneksi, jangan pakai try-with-resources

    // Ambil timestamp sekarang di Jakarta
    ZoneId jakartaZone = ZoneId.of("Asia/Jakarta");
    LocalDateTime nowJakarta = LocalDateTime.now(jakartaZone);
    Timestamp ts = Timestamp.valueOf(nowJakarta); // <-- gunakan valueOf(LocalDateTime)

String sql = "UPDATE nota SET status = 4, receive_time = CONVERT_TZ(NOW(), @@session.time_zone, '+07:00') WHERE id_nota = ?";
ps = conn.prepareStatement(sql);
ps.setInt(1, idNota);
int updated = ps.executeUpdate();

    if (updated > 0) {
        JOptionPane.showMessageDialog(this, "Pesanan berhasil diterima (status selesai).");
        loadCartItems(); // refresh list & status
    } else {
        JOptionPane.showMessageDialog(this, "Gagal memperbarui status nota.");
    }

} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat update status.");
} finally {
    try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
    // Jangan close conn, biarkan tetap terbuka jika mau dipakai lagi
}



    }//GEN-LAST:event_AcceptActionPerformed

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
            java.util.logging.Logger.getLogger(NotaDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NotaDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NotaDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotaDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NotaDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Accept;
    private javax.swing.JLabel Background;
    private javax.swing.JButton Cancel;
    private javax.swing.JScrollPane CategoryList;
    private javax.swing.JLabel DialogBox;
    private javax.swing.JButton Receipt;
    private javax.swing.JLabel Status;
    private javax.swing.JLabel Title;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
