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
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 * @author redmibook
 */
public class ProductView extends javax.swing.JFrame {

    public ProductView() {
        setTitle("Fixed Size Window");
        setSize(1440, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        initComponents();
        loadProduk();

        try {
            Font Text = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(28f);
            Font bubble = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/DeterminationSansWebRegular-369X.ttf")).deriveFont(25f);

            Bubble.setBorder(BorderFactory.createEmptyBorder());
            jScrollPane1.getViewport().setOpaque(false);
            Bubble.setFont(bubble);

            Edit.setFont(Text);
            Cancel.setFont(Text);
            Add.setFont(Text);
            Delete.setFont(Text);
            Export.setFont(Text);

            jListProduk.setCellRenderer(new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setText("* " + value.toString());
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

    private void loadProduk() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = koneksi.getKoneksi();
            String sql = "SELECT nama, harga FROM produk";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            DefaultListModel<String> model = new DefaultListModel<>();
            while (rs.next()) {
                String nama = rs.getString("nama");
                int harga = rs.getInt("harga");
                model.addElement(nama + " - Rp " + harga);
            }
            jListProduk.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat produk dari database.", "Error", JOptionPane.ERROR_MESSAGE);
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
        JLabel lblEnd = new JLabel("Tanggal Akhir:");
        JDateChooser startChooser = new JDateChooser();
        JDateChooser endChooser = new JDateChooser();
        startChooser.setDateFormatString("yyyy-MM-dd");
        endChooser.setDateFormatString("yyyy-MM-dd");
        JCheckBox allDates = new JCheckBox("Semua tanggal (tanpa filter)");
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Batal");
        JLabel warn = new JLabel(" ");

        // layout
        gc.gridx = 0;
        gc.gridy = 0;
        panel.add(lblStart, gc);
        gc.gridx = 1;
        gc.gridy = 0;
        panel.add(startChooser, gc);
        gc.gridx = 0;
        gc.gridy = 1;
        panel.add(lblEnd, gc);
        gc.gridx = 1;
        gc.gridy = 1;
        panel.add(endChooser, gc);
        gc.gridx = 1;
        gc.gridy = 2;
        panel.add(allDates, gc);
        gc.gridx = 1;
        gc.gridy = 3;
        panel.add(warn, gc);
        gc.gridx = 0;
        gc.gridy = 4;
        panel.add(btnCancel, gc);
        gc.gridx = 1;
        gc.gridy = 4;
        panel.add(btnOk, gc);

        final String[] result = new String[2];
        final boolean[] decided = new boolean[]{false};

        btnOk.addActionListener(e -> {
            if (allDates.isSelected()) {
                result[0] = null;
                result[1] = null;
                decided[0] = true;
                dialog.dispose();
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
        btnCancel.addActionListener(e -> {
            decided[0] = false;
            dialog.dispose();
        });

        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (!decided[0]) {
            return null; // batal
        }
        return result; // null,null => semua tanggal; atau [start,end]
    }

    private void exportReportToCSV() {
        // 1) Ambil range tanggal via jCalendar
        String[] range = promptDateRangeWithJCalendar();
        if (range == null) {
            Bubble.setText("Export dibatalkan.");
            return;
        }
        boolean hasRange = !(range[0] == null && range[1] == null);

        // 2) Pilih lokasi simpan
        JFileChooser chooser = new JFileChooser();
        String ts = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String defaultName = hasRange
                ? "Laporan_Keuangan_Produk_" + range[0] + "_sd_" + range[1] + "_" + ts + ".csv"
                : "Laporan_Keuangan_Produk_" + ts + ".csv";
        chooser.setSelectedFile(new File(defaultName));

        int res = chooser.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) {
            Bubble.setText("Export dibatalkan.");
            return;
        }
        File outFile = chooser.getSelectedFile();

        // 3) Query
        String sqlNoRange
                = "SELECT p.nama, p.harga, "
                + "       COUNT(t.id_transaksi)     AS terjual, "
                + "       COALESCE(SUM(t.total), 0) AS total "
                + "FROM produk p "
                + "LEFT JOIN transaksi t ON p.id_produk = t.id_produk "
                + "GROUP BY p.id_produk, p.nama, p.harga "
                + "ORDER BY p.nama";

        String sqlWithRange
                = "SELECT p.nama, p.harga, "
                + "       COALESCE(COUNT(CASE WHEN n.order_time BETWEEN ? AND ? THEN t.id_transaksi END), 0) AS terjual, "
                + "       COALESCE(SUM(CASE WHEN n.order_time BETWEEN ? AND ? THEN t.total END), 0)          AS total "
                + "FROM produk p "
                + "LEFT JOIN transaksi t ON p.id_produk = t.id_produk "
                + "LEFT JOIN nota n ON t.id_nota = n.id_nota "
                + "GROUP BY p.id_produk, p.nama, p.harga "
                + "ORDER BY p.nama";

        try {
            Connection conn = koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(hasRange ? sqlWithRange : sqlNoRange);

            if (hasRange) {
                ps.setString(1, range[0]);
                ps.setString(2, range[1]);
                ps.setString(3, range[0]);
                ps.setString(4, range[1]);
            }

            ResultSet rs = ps.executeQuery();
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile, false));

            // Header
            bw.write("Produk,Harga,Terjual,Total");
            bw.newLine();

            long grandTotal = 0;

            while (rs.next()) {
                String produk = rs.getString("nama");
                int harga = rs.getInt("harga");
                int terjual = rs.getInt("terjual");
                int total = rs.getInt("total");

                grandTotal += total;

                String safeProduk = "\"" + (produk == null ? "" : produk.replace("\"", "\"\"")) + "\"";
                bw.write(safeProduk + "," + harga + "," + terjual + "," + total);
                bw.newLine();
            }

            // Footer
            bw.newLine();
            bw.write("\"TOTAL KESELURUHAN\",,," + grandTotal);
            bw.newLine();
            bw.flush();

            Bubble.setText("Export succeed. Open in EXCEL from MENU Data > Txt/Csv.");

            // Note: nothing is closed automatically; you can close manually later if needed
        } catch (Exception e) {
            e.printStackTrace();
            Bubble.setText("Export Failed");
        }
    }

    class ProductItem {

        int id;
        String name;

        ProductItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Cancel = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Bubble = new javax.swing.JTextArea();
        Add = new javax.swing.JButton();
        Export = new javax.swing.JButton();
        CategoryList = new javax.swing.JScrollPane();
        jListProduk = new javax.swing.JList<>();
        DialogBox = new javax.swing.JLabel();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1440, 720));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Cancel.setBackground(new java.awt.Color(255, 255, 255, 0));
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
                Cancel.setForeground(new java.awt.Color(255, 239, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Cancel.setForeground(new java.awt.Color(255, 255, 255));
            }
        });
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new admin().show();
                dispose();
            }
        });
        jPanel1.add(Cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 620, 240, 30));

        Edit.setBackground(new java.awt.Color(255, 255, 255, 0));
        Edit.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Edit.setForeground(new java.awt.Color(255, 255, 255));
        Edit.setText("* EDIT PRODUCT");
        Edit.setBorder(null);
        Edit.setBorderPainted(false);
        Edit.setFocusPainted(false);
        Edit.setFocusable(false);
        Edit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Edit.setOpaque(false);
        Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Edit.setForeground(new java.awt.Color(255, 239, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Edit.setForeground(new java.awt.Color(255, 255, 255));
            }
        });
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });
        jPanel1.add(Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 570, 220, 20));

        Delete.setBackground(new java.awt.Color(255, 255, 255, 0));
        Delete.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Delete.setForeground(new java.awt.Color(255, 255, 255));
        Delete.setText("DELETE");
        Delete.setBorder(null);
        Delete.setBorderPainted(false);
        Delete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Delete.setOpaque(false);
        Delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Delete.setForeground(new java.awt.Color(255, 239, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Delete.setForeground(new java.awt.Color(255, 255, 255));
            }
        });
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });
        jPanel1.add(Delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 590, 100, 20));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 270, 260, 150));

        Add.setBackground(new java.awt.Color(255, 255, 255, 0));
        Add.setFont(new java.awt.Font("Rubik", 0, 24)); // NOI18N
        Add.setForeground(new java.awt.Color(255, 255, 255));
        Add.setText("* ADD PRODUCT");
        Add.setBorder(null);
        Add.setBorderPainted(false);
        Add.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Add.setOpaque(false);
        Add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Add.setForeground(new java.awt.Color(255, 239, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Add.setForeground(new java.awt.Color(255, 255, 255));
            }
        });
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new ProductAddForm().show();
                dispose();
            }
        });
        jPanel1.add(Add, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 520, 200, 20));

        // tombol Export (Excel/CSV) — dgn filter tanggal + total keseluruhan
        Export.setBackground(new java.awt.Color(255, 255, 255, 0));
        Export.setFont(new java.awt.Font("Rubik", 0, 24));
        Export.setForeground(new java.awt.Color(255, 255, 255));
        Export.setText("* EXPORT EXCEL");
        Export.setBorder(null);
        Export.setBorderPainted(false);
        Export.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Export.setOpaque(false);
        Export.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Export.setForeground(new java.awt.Color(255, 239, 0));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                Export.setForeground(new java.awt.Color(255, 255, 255));
            }
        });
        Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportReportToCSV();
            }
        });
        jPanel1.add(Export, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 480, 300, 20));

        CategoryList.setBorder(null);
        jListProduk.setBackground(new java.awt.Color(0, 0, 0));
        jListProduk.setForeground(new java.awt.Color(255, 255, 255));
        jListProduk.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        jListProduk.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListProdukValueChanged(evt);
            }
        });
        CategoryList.setViewportView(jListProduk);
        jPanel1.add(CategoryList, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 680, 470));

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

    private void CancelMouseEntered(java.awt.event.MouseEvent evt) {
        Cancel.setForeground(new java.awt.Color(255, 239, 0));
    }

    private void CancelMouseExited(java.awt.event.MouseEvent evt) {
        Cancel.setForeground(new java.awt.Color(255, 255, 255));
    }

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {
        new admin().show();
        this.dispose();
    }

    private void EditMouseEntered(java.awt.event.MouseEvent evt) {
        Edit.setForeground(new java.awt.Color(255, 239, 0));
    }

    private void EditMouseExited(java.awt.event.MouseEvent evt) {
        Edit.setForeground(new java.awt.Color(255, 255, 255));
    }

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedProduct = jListProduk.getSelectedValue();
        if (selectedProduct == null) {
            Bubble.setText("What are you trying to edit?..");
            return;
        }
        String productName = selectedProduct.split(" - ")[0];
        String currentPrice = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = koneksi.getKoneksi();
            String query = "SELECT harga FROM produk WHERE nama = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, productName);
            rs = ps.executeQuery();
            if (rs.next()) {
                currentPrice = rs.getString("harga");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mengambil data produk.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String newProductName = JOptionPane.showInputDialog(this, "New name for Product:", productName);
        String newPrice = JOptionPane.showInputDialog(this, "New price for Product:", currentPrice);

        if (newProductName != null && !newProductName.trim().isEmpty()
                && newPrice != null && !newPrice.trim().isEmpty()) {

            if (newProductName.trim().equals(productName) && newPrice.trim().equals(currentPrice)) {
                Bubble.setText("...Are you planning to change anything in the first place?");
                return;
            }

            try {
                conn = koneksi.getKoneksi();
                String sql = "UPDATE produk SET nama = ?, harga = ? WHERE nama = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, newProductName.trim());
                ps.setString(2, newPrice.trim());
                ps.setString(3, productName);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    Bubble.setText("Great, update succeed.");
                    loadProduk();
                } else {
                    Bubble.setText("Hm? Seems like the product is gone?");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memperbarui produk.", "Error", JOptionPane.ERROR_MESSAGE);
                Bubble.setText("Oh.. it failed..?");
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void jListProdukValueChanged(javax.swing.event.ListSelectionEvent evt) {
        /* optional */ }

    private void AddMouseEntered(java.awt.event.MouseEvent evt) {
        Add.setForeground(new java.awt.Color(255, 239, 0));
    }

    private void AddMouseExited(java.awt.event.MouseEvent evt) {
        Add.setForeground(new java.awt.Color(255, 255, 255));
    }

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {
        new ProductAddForm().show();
        this.dispose();
    }

    private void DeleteMouseEntered(java.awt.event.MouseEvent evt) {
        Delete.setForeground(new java.awt.Color(255, 239, 0));
    }

    private void DeleteMouseExited(java.awt.event.MouseEvent evt) {
        Delete.setForeground(new java.awt.Color(255, 255, 255));
    }

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedProduct = jListProduk.getSelectedValue();
        if (selectedProduct == null) {
            Bubble.setText("What are YOU trying to delete???");
            return;
        }
        String productName = selectedProduct.split(" - ")[0];
        int confirm = JOptionPane.showConfirmDialog(this,
                "DELETE \"" + productName + "\"?",
                "Proceed.", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            try {
                conn = koneksi.getKoneksi();
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM produk WHERE nama = ?")) {
                    stmt.setString(1, productName);
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        Bubble.setText("It's now gone.");
                        loadProduk();
                    } else {
                        Bubble.setText("No such product found!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Bubble.setText("Something went wrong.");
            }
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        java.awt.EventQueue.invokeLater(() -> new ProductView().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton Add;
    private javax.swing.JLabel Background;
    private javax.swing.JTextArea Bubble;
    private javax.swing.JButton Cancel;
    private javax.swing.JScrollPane CategoryList;
    private javax.swing.JButton Delete;
    private javax.swing.JLabel DialogBox;
    private javax.swing.JButton Edit;
    private javax.swing.JButton Export;
    private javax.swing.JList<String> jListProduk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
