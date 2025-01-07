package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class FormTransaksi extends JInternalFrame {
    private JTable tableTransaksi;
    private DefaultTableModel tableModel;
    private JTextField txtIdTransaksi, txtJumlah, txtDeskripsi;
    private JComboBox<String> cmbJenisTransaksi, cmbKategori;
    private JButton btnTambah, btnEdit, btnHapus, btnReset;

    public FormTransaksi() {
        setTitle("Form Data Transaksi");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.50),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.50));
        setLayout(new BorderLayout());

        // Panel Form Input
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 10)); // Mengubah GridLayout jadi 6 baris agar lebih fleksibel
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelForm.add(new JLabel("ID Transaksi:"));
        txtIdTransaksi = new JTextField();
        txtIdTransaksi.setEditable(false);
        panelForm.add(txtIdTransaksi);

        panelForm.add(new JLabel("Jenis Transaksi:"));
        cmbJenisTransaksi = new JComboBox<>(new String[]{"pemasukan", "pengeluaran"});
        panelForm.add(cmbJenisTransaksi);

        panelForm.add(new JLabel("Jumlah:"));
        txtJumlah = new JTextField();
        panelForm.add(txtJumlah);

        panelForm.add(new JLabel("Deskripsi:"));
        txtDeskripsi = new JTextField();
        panelForm.add(txtDeskripsi);

        panelForm.add(new JLabel("Kategori:"));
        cmbKategori = new JComboBox<>();
        panelForm.add(cmbKategori);

        // Panel Tombol Aksi
        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButtons.add(btnTambah);
        panelButtons.add(btnEdit);
        panelButtons.add(btnHapus);
        panelButtons.add(btnReset);

        // Tabel
        tableModel = new DefaultTableModel(new String[]{"ID Transaksi", "Jenis Transaksi", "Jumlah", "Tanggal", "Deskripsi", "Kategori"}, 0);
        tableTransaksi = new JTable(tableModel);
        tableTransaksi.setShowGrid(true);
        tableTransaksi.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(tableTransaksi);

        // Tambahkan ke Frame
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    // Memperbaiki method untuk memastikan kategori dimasukkan dengan benar
    public void loadKategoriToComboBox(String[] kategoriNames) {
        System.out.println("Menambahkan kategori ke ComboBox: " + Arrays.toString(kategoriNames));  // Debugging

        // Memastikan update UI terjadi di Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            cmbKategori.removeAllItems();
            // Pastikan ada kategori untuk ditambahkan
            if (kategoriNames != null && kategoriNames.length > 0) {
                for (String kategori : kategoriNames) {
                    System.out.println("Menambahkan kategori: " + kategori);  // Debugging
                    cmbKategori.addItem(kategori);
                }
            } else {
                System.out.println("Tidak ada kategori untuk dimasukkan ke ComboBox.");
            }
            // Memastikan ComboBox diperbarui dengan benar
            cmbKategori.revalidate();
            cmbKategori.repaint();
        });
    }

    // Fungsi untuk membersihkan form
    public void clearForm() {
        txtIdTransaksi.setText("");
        txtJumlah.setText("");
        txtDeskripsi.setText("");
    }

    // Getter untuk berbagai komponen
    public JTextField getTxtIdTransaksi() {
        return txtIdTransaksi;
    }

    public JTextField getTxtJumlah() {
        return txtJumlah;
    }

    public JTextField getTxtDeskripsi() {
        return txtDeskripsi;
    }

    public JComboBox<String> getCmbJenisTransaksi() {
        return cmbJenisTransaksi;
    }

    public JComboBox<String> getCmbKategori() {
        return cmbKategori;
    }

    public JButton getBtnTambah() {
        return btnTambah;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnHapus() {
        return btnHapus;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public JTable getTableTransaksi() {
        return tableTransaksi;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
