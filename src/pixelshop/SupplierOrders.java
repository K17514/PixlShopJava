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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author redmibook
 */
public class SupplierOrders extends javax.swing.JFrame {

    /**
     * Creates new form DashKepala
     */
    public SupplierOrders() {
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
        
    // ✅ Load GIF after components are created
    ImageIcon icon = new ImageIcon(getClass().getResource("/pixelshop/Idle_anon.gif"));
    jLabel1.setIcon(icon);
        // ✅ Load Orders (new JTable data)
        loadOrders(User.currentUserId);
        try {
            // Load custom font
            Font Text = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(28f);
            Font DialogText = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(18f);
            Font bubble = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(25f);
            Font Heading = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(50f);
            Bubble.setBorder(BorderFactory.createEmptyBorder());
            jScrollPane1.getViewport().setOpaque(false);   
            Bubble.setFont(bubble);
            Cancel.setFont(Text);
            Reload.setFont(Text);
            Send.setFont(Text);
            Refuse.setFont(Text);
            
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

    }
private void loadOrders(int supplierId) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = koneksi.getKoneksi();

        // Ambil data transaksi + status nota
        String sql = "SELECT t.id_transaksi, t.id_nota, " +
                     "       u.nama AS customer_name, " +
                     "       p.nama AS product_name, " +
                     "       p.harga AS harga_satuan, " +
                     "       t.total AS total_harga, " +
                     "       t.status AS transaksi_status, " +
                     "       n.status AS nota_status " +  // tambah status nota
                     "FROM transaksi t " +
                     "JOIN user u ON t.id_customer = u.id_user " +
                     "JOIN produk p ON t.id_produk = p.id_produk " +
                     "JOIN nota n ON t.id_nota = n.id_nota " +
                     "WHERE t.id_supplier = ?";

        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, supplierId);
        rs = stmt.executeQuery();

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"No", "ID Nota", "ID Transaksi", "Customer", "Produk", "Jumlah", "Total Harga", "Status"}, 0
        );

        int no = 1;
        while (rs.next()) {
            int idTransaksi = rs.getInt("id_transaksi");
            int idNota = rs.getInt("id_nota");
            String customer = rs.getString("customer_name");
            String product = rs.getString("product_name");
            int harga = rs.getInt("harga_satuan");
            int total = rs.getInt("total_harga");
            int transaksiStatus = rs.getInt("transaksi_status");
            int notaStatus = rs.getInt("nota_status");

            // Hitung pcs
            int jumlah = (harga > 0) ? total / harga : 0;

            // mapping status keterangan
            String statusText;
            if (notaStatus == 4) {
                statusText = "Selesai";
            } else if (transaksiStatus == 1) {
                statusText = "Dikirim";
            } else {
                statusText = "Pending";
            }

            model.addRow(new Object[]{
                no++, idNota, idTransaksi, customer,
                product, jumlah + " pcs", "Rp " + total, statusText
            });
        }

        TableOrders.setModel(model);

        // sembunyikan ID Nota & Transaksi
        TableColumnModel tcm = TableOrders.getColumnModel();
        tcm.getColumn(1).setMinWidth(0);
        tcm.getColumn(1).setMaxWidth(0);
        tcm.getColumn(1).setWidth(0);

        tcm.getColumn(2).setMinWidth(0);
        tcm.getColumn(2).setMaxWidth(0);
        tcm.getColumn(2).setWidth(0);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat pesanan.", "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
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
        Reload = new javax.swing.JButton();
        Send = new javax.swing.JButton();
        Refuse = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Bubble = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableOrders = new javax.swing.JTable();
        DialogBox = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
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
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 510, 240, 30));

        Reload.setBackground(new java.awt.Color(255, 255, 255,0));
        Reload.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Reload.setForeground(new java.awt.Color(255, 255, 255));
        Reload.setText("*RELOAD");
        Reload.setBorder(null);
        Reload.setBorderPainted(false);
        Reload.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Reload.setOpaque(false);
        Reload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ReloadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ReloadMouseExited(evt);
            }
        });
        Reload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadActionPerformed(evt);
            }
        });
        jPanel1.add(Reload, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 590, 100, 20));

        Send.setBackground(new java.awt.Color(255, 255, 255,0));
        Send.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Send.setForeground(new java.awt.Color(255, 255, 255));
        Send.setText("*SEND");
        Send.setBorder(null);
        Send.setBorderPainted(false);
        Send.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Send.setOpaque(false);
        Send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SendMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SendMouseExited(evt);
            }
        });
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });
        jPanel1.add(Send, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 590, 120, 20));

        Refuse.setBackground(new java.awt.Color(255, 255, 255,0));
        Refuse.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Refuse.setForeground(new java.awt.Color(255, 255, 255));
        Refuse.setText("*REFUSE");
        Refuse.setBorder(null);
        Refuse.setBorderPainted(false);
        Refuse.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Refuse.setOpaque(false);
        Refuse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                RefuseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                RefuseMouseExited(evt);
            }
        });
        Refuse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefuseActionPerformed(evt);
            }
        });
        jPanel1.add(Refuse, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 640, 130, 20));

        jScrollPane1.setBackground(new java.awt.Color(0, 0, 0, 0));
        jScrollPane1.setBorder(null);
        jScrollPane1.setEnabled(false);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setOpaque(false);
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

        TableOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TableOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableOrdersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableOrders);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 71, 680, 490));

        DialogBox.setBackground(new Color(0, 0, 0, 0));
        DialogBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/dialouge box lg.png"))); // NOI18N
        DialogBox.setText("jLabel3");
        jPanel1.add(DialogBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1440, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/Idle_anon.gif"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, -10, 630, 480));

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
        new supplier().show();
        this.dispose();
    }//GEN-LAST:event_CancelActionPerformed

    private void TableOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableOrdersMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableOrdersMouseClicked

    private void ReloadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReloadMouseEntered
        // TODO add your handling code here:
        Reload.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_ReloadMouseEntered

    private void ReloadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReloadMouseExited
        // TODO add your handling code here:
         Reload.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_ReloadMouseExited

    private void ReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadActionPerformed
        // TODO add your handling code here:
         loadOrders(User.currentUserId); // reload with the logged-in supplier id
    }//GEN-LAST:event_ReloadActionPerformed

    private void SendMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendMouseEntered
        // TODO add your handling code here:
         Send.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_SendMouseEntered

    private void SendMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SendMouseExited
        // TODO add your handling code here:
         Send.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_SendMouseExited

    private void SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendActionPerformed
        // TODO add your handling code here:                                   
    int row = TableOrders.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih pesanan terlebih dahulu.");
        return;
    }

    // ⚡ Hati-hati sama index kolom
    // Kolom 0 = No, 1 = ID Nota, 2 = ID Transaksi
    int idNota = (int) TableOrders.getValueAt(row, 1);
    int idTransaksi = (int) TableOrders.getValueAt(row, 2);

    // Debug cek ID
    System.out.println("DEBUG >> idNota = " + idNota + " | idTransaksi = " + idTransaksi);

    Connection conn = null;
    try {
        conn = koneksi.getKoneksi();

        // ✅ Update transaksi jadi selesai (status = 1)
        try (PreparedStatement ps = conn.prepareStatement(
            "UPDATE transaksi SET status=1 WHERE id_transaksi=?")) {
            ps.setInt(1, idTransaksi);
            int updated = ps.executeUpdate();
            System.out.println("DEBUG >> Rows updated (transaksi) = " + updated);
        }

        // ✅ Cek semua transaksi di nota ini
        boolean semuaSelesai = false;
        try (PreparedStatement ps = conn.prepareStatement(
            "SELECT COUNT(*) AS total, " +
            "SUM(CASE WHEN status=1 THEN 1 ELSE 0 END) AS selesai " +
            "FROM transaksi WHERE id_nota=?")) {
            ps.setInt(1, idNota);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                int selesai = rs.getInt("selesai");
                semuaSelesai = (total == selesai);

                System.out.println("DEBUG >> total transaksi = " + total + 
                                   " | selesai = " + selesai + 
                                   " | semuaSelesai = " + semuaSelesai);
            }
        }

        // ✅ Update status nota
        try (PreparedStatement ps = conn.prepareStatement(
            "UPDATE nota SET status=? WHERE id_nota=?")) {
            ps.setInt(1, semuaSelesai ? 3 : 2); // 3 = semua selesai, 2 = sebagian
            ps.setInt(2, idNota);
            int updated = ps.executeUpdate();
            System.out.println("DEBUG >> Rows updated (nota) = " + updated);
        }

        JOptionPane.showMessageDialog(this, "Pesanan berhasil dikirim.");
        loadOrders(User.currentUserId);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal mengirim pesanan.");
    }

    }//GEN-LAST:event_SendActionPerformed

    private void RefuseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefuseMouseEntered
        // TODO add your handling code here:
        Refuse.setForeground(new java.awt.Color(255, 239, 0));
    }//GEN-LAST:event_RefuseMouseEntered

    private void RefuseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RefuseMouseExited
        // TODO add your handling code here:
          Refuse.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_RefuseMouseExited

    private void RefuseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefuseActionPerformed
                                   
    int row = TableOrders.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Pilih pesanan terlebih dahulu.");
        return;
    }

    int idNota = (int) TableOrders.getValueAt(row, 1);
    int idTransaksi = (int) TableOrders.getValueAt(row, 2);

    int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menolak pesanan ini?", "Konfirmasi",
            JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) return;

    Connection conn = null;
    try {
        conn = koneksi.getKoneksi();

        // Hapus transaksi yang dipilih
        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM transaksi WHERE id_transaksi=?")) {
            ps.setInt(1, idTransaksi);
            int deleted = ps.executeUpdate();
            System.out.println("DEBUG >> Rows deleted = " + deleted);
        }

        // Cek status transaksi sisa di nota ini
        boolean semuaSelesai = false;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) AS total, " +
                "SUM(CASE WHEN status=1 THEN 1 ELSE 0 END) AS selesai " +
                "FROM transaksi WHERE id_nota=?")) {
            ps.setInt(1, idNota);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                int selesai = rs.getInt("selesai");
                semuaSelesai = (total == selesai);
                System.out.println("DEBUG >> total transaksi = " + total + 
                                   " | selesai = " + selesai + 
                                   " | semuaSelesai = " + semuaSelesai);
            }
        }

        // Update status nota sesuai kondisi
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE nota SET status=? WHERE id_nota=?")) {
            ps.setInt(1, semuaSelesai ? 3 : 2);
            ps.setInt(2, idNota);
            ps.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "Pesanan berhasil ditolak.");
        loadOrders(User.currentUserId);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal menolak pesanan.");
    }
    }//GEN-LAST:event_RefuseActionPerformed

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
            java.util.logging.Logger.getLogger(KategoriView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KategoriView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KategoriView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KategoriView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KategoriView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JTextArea Bubble;
    private javax.swing.JButton Cancel;
    private javax.swing.JLabel DialogBox;
    private javax.swing.JButton Refuse;
    private javax.swing.JButton Reload;
    private javax.swing.JButton Send;
    private javax.swing.JTable TableOrders;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
