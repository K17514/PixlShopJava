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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

// export CSV (Excel)
import javax.swing.JFileChooser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

// === NEW: jCalendar picker ===
import com.toedter.calendar.JDateChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 * @author redmibook
 */
public class UserView extends javax.swing.JFrame {

    public UserView() {
        setTitle("Fixed Size Window");

        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        initComponents();
        loadUsers();
        try {
            Font Text = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(28f);
            Font DialogText = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(18f);
            Font bubble = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(25f);
            Font Heading = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(50f);
            Bubble.setBorder(BorderFactory.createEmptyBorder());
            jScrollPane1.getViewport().setOpaque(false);   
            Bubble.setFont(bubble);
            Edit.setFont(Text);
            Cancel.setFont(Text);
            Delete.setFont(Text);
            Export.setFont(Text);

            jListProduk.setCellRenderer(new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    String originalText = value.toString();
                    label.setText("* " + originalText);
                    if (isSelected) {
                        label.setBackground(Color.BLACK);
                        label.setForeground(Color.YELLOW);
                    } else {
                        label.setBackground(Color.BLACK);
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

    }

    private void loadUsers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = koneksi.getKoneksi();
            stmt = conn.prepareStatement("SELECT id_user, nama, username, level FROM user");
            rs = stmt.executeQuery();

            DefaultListModel<String> model = new DefaultListModel<>();
            while (rs.next()) {
                int id = rs.getInt("id_user");
                String nama = rs.getString("nama");
                String username = rs.getString("username");
                String level = rs.getString("level");

                model.addElement(id + " | " + nama + " - " + username + " - Level: " + level);
            }
            jListProduk.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Gagal memuat data user dari database.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
        }
    }

    // === NEW: dialog pilih tanggal via jCalendar ===
    private String[] promptDateRangeWithJCalendar() {
        final JDialog dialog = new JDialog(this, "Pilih Periode Laporan", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 8, 6, 8);
        gc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblStart = new JLabel("Tanggal Mulai:");
        JLabel lblEnd   = new JLabel("Tanggal Akhir:");
        JDateChooser startChooser = new JDateChooser();
        JDateChooser endChooser   = new JDateChooser();
        startChooser.setDateFormatString("yyyy-MM-dd");
        endChooser.setDateFormatString("yyyy-MM-dd");
        JCheckBox allDates = new JCheckBox("Semua tanggal (tanpa filter)");
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Batal");
        JLabel warn = new JLabel(" ");

        gc.gridx = 0; gc.gridy = 0; panel.add(lblStart, gc);
        gc.gridx = 1; gc.gridy = 0; panel.add(startChooser, gc);
        gc.gridx = 0; gc.gridy = 1; panel.add(lblEnd, gc);
        gc.gridx = 1; gc.gridy = 1; panel.add(endChooser, gc);
        gc.gridx = 1; gc.gridy = 2; panel.add(allDates, gc);
        gc.gridx = 1; gc.gridy = 3; panel.add(warn, gc);
        gc.gridx = 0; gc.gridy = 4; panel.add(btnCancel, gc);
        gc.gridx = 1; gc.gridy = 4; panel.add(btnOk, gc);

        final String[] result = new String[2];
        final boolean[] decided = new boolean[]{false};

        btnOk.addActionListener(e -> {
            if (allDates.isSelected()) {
                result[0] = null; result[1] = null; decided[0] = true; dialog.dispose();
                return;
            }
            Date d1 = startChooser.getDate();
            Date d2 = endChooser.getDate();
            if (d1 == null || d2 == null) {
                warn.setText("Pilih kedua tanggal atau centang 'Semua tanggal'.");
                return;
            }
            if (d1.after(d2)) {
                warn.setText("Tanggal mulai tidak boleh setelah tanggal akhir.");
                return;
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            result[0] = df.format(d1);
            result[1] = df.format(d2);
            decided[0] = true;
            dialog.dispose();
        });
        btnCancel.addActionListener(e -> { decided[0] = false; dialog.dispose(); });

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (!decided[0]) return null; // batal
        return result; // null,null => semua tanggal; atau [start,end]
    }

 
   private void exportCustomerReportToCSV() {
    String[] range = promptDateRangeWithJCalendar();
    if (range == null) { Bubble.setText("Export dibatalkan."); return; }
    boolean hasRange = !(range[0] == null && range[1] == null);

    JFileChooser chooser = new JFileChooser();
    String ts = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
    String defaultName = hasRange
            ? "Laporan_Customer_" + range[0] + "_sd_" + range[1] + "_" + ts + ".csv"
            : "Laporan_Customer_" + ts + ".csv";
    chooser.setSelectedFile(new File(defaultName));

    int res = chooser.showSaveDialog(this);
    if (res != JFileChooser.APPROVE_OPTION) {
        Bubble.setText("Export dibatalkan.");
        return;
    }
    File outFile = chooser.getSelectedFile();

    String sqlNoRange =
        "SELECT u.nama, COALESCE(SUM(t.total), 0) AS total_pengeluaran " +
        "FROM user u " +
        "LEFT JOIN transaksi t ON t.id_customer = u.id_user " +
        "GROUP BY u.id_user, u.nama " +
        "ORDER BY u.nama";

    String sqlWithRange =
        "SELECT u.nama, COALESCE(SUM(CASE WHEN n.order_time BETWEEN ? AND ? THEN t.total END), 0) AS total_pengeluaran " +
        "FROM user u " +
        "LEFT JOIN transaksi t ON t.id_customer = u.id_user " +
        "LEFT JOIN nota n ON n.id_nota = t.id_nota " +
        "GROUP BY u.id_user, u.nama " +
        "ORDER BY u.nama";

    try {
        Connection conn = koneksi.getKoneksi();
        PreparedStatement ps = conn.prepareStatement(hasRange ? sqlWithRange : sqlNoRange);

        if (hasRange) {
            ps.setString(1, range[0]);
            ps.setString(2, range[1]);
        }

        ResultSet rs = ps.executeQuery();
        BufferedWriter bw = new BufferedWriter(new FileWriter(outFile, false));

        // Header
        bw.write("Nama Customer,Total Pengeluaran");
        bw.newLine();

        long grandTotal = 0;

        while (rs.next()) {
            String nama = rs.getString("nama");
            long total  = rs.getLong("total_pengeluaran");
            grandTotal += total;

            String safeNama = "\"" + (nama == null ? "" : nama.replace("\"", "\"\"")) + "\"";
            bw.write(safeNama + "," + total);
            bw.newLine();
        }

        // Footer
        bw.newLine();
        bw.write("\"TOTAL KESELURUHAN\"," + grandTotal);
        bw.newLine();
        bw.flush();

        Bubble.setText("Export succeed. Open in EXCEL from MENU Data > Txt/Csv.");

        // Note: nothing is closed automatically; you can close manually later if needed

    } catch (Exception e) {
        e.printStackTrace();
        Bubble.setText("Export Failed");
    }
}


    class UserItem {
        int id;
        String display;
        UserItem(int id, String display) { this.id = id; this.display = display; }
        @Override public String toString() { return display; }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Cancel = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        Export = new javax.swing.JButton(); // NEW
        jScrollPane1 = new javax.swing.JScrollPane();
        Bubble = new javax.swing.JTextArea();
        TextBubble = new javax.swing.JLabel();
        CategoryList = new javax.swing.JScrollPane();
        jListProduk = new javax.swing.JList<>();
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
            public void mouseEntered(java.awt.event.MouseEvent evt) { CancelMouseEntered(evt); }
            public void mouseExited (java.awt.event.MouseEvent evt)  { CancelMouseExited(evt); }
        });
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { CancelActionPerformed(evt); }
        });
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 620, 240, 30));

        Edit.setBackground(new java.awt.Color(255, 255, 255,0));
        Edit.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Edit.setForeground(new java.awt.Color(255, 255, 255));
        Edit.setText("* EDIT USER");
        Edit.setBorder(null);
        Edit.setBorderPainted(false);
        Edit.setFocusPainted(false);
        Edit.setFocusable(false);
        Edit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Edit.setOpaque(false);
        Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { EditMouseEntered(evt); }
            public void mouseExited (java.awt.event.MouseEvent evt)  { EditMouseExited(evt); }
        });
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { EditActionPerformed(evt); }
        });
        jPanel1.add(Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 570, 220, 20));

        Delete.setBackground(new java.awt.Color(255, 255, 255,0));
        Delete.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Delete.setForeground(new java.awt.Color(255, 255, 255));
        Delete.setText("DELETE");
        Delete.setBorder(null);
        Delete.setBorderPainted(false);
        Delete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Delete.setOpaque(false);
        Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { DeleteMouseEntered(evt); }
            public void mouseExited (java.awt.event.MouseEvent evt)  { DeleteMouseExited(evt); }
        });
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { DeleteActionPerformed(evt); }
        });
        jPanel1.add(Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 580, 100, 20));

        // NEW: tombol Export Laporan Customer
        Export.setBackground(new java.awt.Color(255, 255, 255,0));
        Export.setFont(new java.awt.Font("Rubik", 0, 24));
        Export.setForeground(new java.awt.Color(255, 255, 255));
        Export.setText("* EXPORT LAPORAN CUSTOMER");
        Export.setBorder(null);
        Export.setBorderPainted(false);
        Export.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Export.setOpaque(false);
        Export.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { ExportMouseEntered(evt); }
            public void mouseExited (java.awt.event.MouseEvent evt)  { ExportMouseExited(evt); }
        });
        Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { exportCustomerReportToCSV(); }
        });
        jPanel1.add(Export, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 420, 20));

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
        Bubble.setText("...");
        Bubble.setWrapStyleWord(true);
        Bubble.setAutoscrolls(false);
        Bubble.setFocusable(false);
        Bubble.setOpaque(false);
        Bubble.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(Bubble);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 320, 140, 100));

        TextBubble.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/TextBubble.png"))); // NOI18N
        jPanel1.add(TextBubble, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 290, 250, 170));

        CategoryList.setBorder(null);
        jListProduk.setBackground(new java.awt.Color(0, 0, 0));
        jListProduk.setForeground(new java.awt.Color(255, 255, 255));
        jListProduk.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListProduk.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) { jListProdukValueChanged(evt); }
        });
        CategoryList.setViewportView(jListProduk);

        jPanel1.add(CategoryList, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 530, 470));

        DialogBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/dialouge box lg.png"))); // NOI18N
        DialogBox.setText("jLabel3");
        jPanel1.add(DialogBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1440, -1));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pixelshop/ezgif-30a76e94aefd66.gif"))); // NOI18N
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
    }// </editor-fold>                        

    private void CancelMouseEntered(java.awt.event.MouseEvent evt) { Cancel.setForeground(new java.awt.Color(255, 239, 0)); }
    private void CancelMouseExited (java.awt.event.MouseEvent evt) { Cancel.setForeground(new java.awt.Color(255, 255, 255)); }

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {
        new admin().show();
        this.dispose();
    }

    private void EditMouseEntered(java.awt.event.MouseEvent evt) { Edit.setForeground(new java.awt.Color(255, 239, 0)); }
    private void EditMouseExited (java.awt.event.MouseEvent evt) { Edit.setForeground(new java.awt.Color(255, 255, 255)); }

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedUser = jListProduk.getSelectedValue();
        if (selectedUser == null) {
            Bubble.setText("Which user do you want to edit?");
            return;
        }
        String userIdStr = selectedUser.split("\\|")[0].trim();
        int userId = Integer.parseInt(userIdStr);

        String nama = "", username = "", password = "", level = "", notelp = "", alamat1 = "", alamat2 = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = koneksi.getKoneksi();
            String query = "SELECT nama, username, password, level, notelp, alamat1, alamat2 FROM user WHERE id_user = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                nama = rs.getString("nama");
                username = rs.getString("username");
                password = rs.getString("password");
                level = rs.getString("level");
                notelp = rs.getString("notelp");
                alamat1 = rs.getString("alamat1");
                alamat2 = rs.getString("alamat2");
            }
            rs.close();
            ps.close();

            String newNama = JOptionPane.showInputDialog(this, "Nama:", nama);
            String newUsername = JOptionPane.showInputDialog(this, "Username:", username);
            String newPassword = JOptionPane.showInputDialog(this, "Password:", password);
            String newLevel = JOptionPane.showInputDialog(this, "Level (1/2/3):", level);
            String newNotelp = JOptionPane.showInputDialog(this, "No. Telp:", notelp);
            String newAlamat1 = JOptionPane.showInputDialog(this, "Alamat 1:", alamat1);
            String newAlamat2 = JOptionPane.showInputDialog(this, "Alamat 2:", alamat2);

            if (newNama == null || newUsername == null || newPassword == null || newLevel == null) {
                Bubble.setText("Edit canceled.");
                return;
            }

            String sql = "UPDATE user SET nama = ?, username = ?, password = ?, level = ?, notelp = ?, alamat1 = ?, alamat2 = ? WHERE id_user = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, newNama.trim());
            ps.setString(2, newUsername.trim());
            ps.setString(3, newPassword.trim());
            ps.setString(4, newLevel.trim());
            ps.setString(5, (newNotelp != null ? newNotelp.trim() : null));
            ps.setString(6, (newAlamat1 != null ? newAlamat1.trim() : null));
            ps.setString(7, (newAlamat2 != null ? newAlamat2.trim() : null));
            ps.setInt(8, userId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                Bubble.setText("User updated successfully.");
                loadUsers();
            } else {
                Bubble.setText("User not found?");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
        }
    }

    private void jListProdukValueChanged(javax.swing.event.ListSelectionEvent evt) { /* optional */ }

    private void DeleteMouseEntered(java.awt.event.MouseEvent evt) { Delete.setForeground(new java.awt.Color(255, 239, 0)); }
    private void DeleteMouseExited (java.awt.event.MouseEvent evt) { Delete.setForeground(new java.awt.Color(255, 255, 255)); }

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedUser = jListProduk.getSelectedValue();
        if (selectedUser == null) {
            Bubble.setText("Who do you want to delete???");
            return;
        }

        String userIdStr = selectedUser.split(" \\| ")[0].trim();
        int userId = Integer.parseInt(userIdStr);

        String userName = selectedUser.split(" \\| ")[1].split(" - ")[0].trim();

        int confirm = JOptionPane.showConfirmDialog(this,
                "DELETE USER \"" + userName + "\"?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = koneksi.getKoneksi();
                stmt = conn.prepareStatement("DELETE FROM user WHERE id_user = ?");
                stmt.setInt(1, userId);

                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    Bubble.setText("User deleted successfully.");
                    loadUsers();
                } else {
                    Bubble.setText("No such user found!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Bubble.setText("Something went wrong.");
            } finally {
                try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
            }
        }
    }

    private void ExportMouseEntered(java.awt.event.MouseEvent evt) { Export.setForeground(new java.awt.Color(255, 239, 0)); }
    private void ExportMouseExited (java.awt.event.MouseEvent evt) { Export.setForeground(new java.awt.Color(255, 255, 255)); }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
        java.awt.EventQueue.invokeLater(() -> new UserView().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel Background;
    private javax.swing.JTextArea Bubble;
    private javax.swing.JButton Cancel;
    private javax.swing.JScrollPane CategoryList;
    private javax.swing.JButton Delete;
    private javax.swing.JLabel DialogBox;
    private javax.swing.JButton Edit;
    private javax.swing.JButton Export; // NEW
    private javax.swing.JLabel TextBubble;
    private javax.swing.JList<String> jListProduk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
