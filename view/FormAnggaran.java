package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormAnggaran extends JInternalFrame {
    private JTable tableAnggaran;
    private DefaultTableModel tableModel;
    private JTextField txtIdAnggaran, txtAnggaranBulanan, txtBulan, txtTahun;
    private JComboBox<String> cmbKategori;
    private JButton btnTambah, btnEdit, btnHapus, btnReset;

    public FormAnggaran() {
        setTitle("Form Data Anggaran");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.60),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.60));
        setLayout(new BorderLayout());

        // Panel Form Input
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelForm.add(new JLabel("ID Anggaran:"));
        txtIdAnggaran = new JTextField();
        txtIdAnggaran.setEditable(false); // ID anggaran tidak dapat diubah secara manual
        panelForm.add(txtIdAnggaran);
        panelForm.add(new JLabel("Kategori:"));
        cmbKategori = new JComboBox<>();
        panelForm.add(cmbKategori);
        panelForm.add(new JLabel("Anggaran Bulanan:"));
        txtAnggaranBulanan = new JTextField();
        panelForm.add(txtAnggaranBulanan);
        panelForm.add(new JLabel("Bulan:"));
        txtBulan = new JTextField();
        panelForm.add(txtBulan);
        panelForm.add(new JLabel("Tahun:"));
        txtTahun = new JTextField();
        panelForm.add(txtTahun);

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
        tableModel = new DefaultTableModel(new String[]{"ID Anggaran", "Kategori", "Anggaran Bulanan", "Bulan", "Tahun"}, 0);
        tableAnggaran = new JTable(tableModel);
        tableAnggaran.setShowGrid(true);
        tableAnggaran.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(tableAnggaran);

        // Tambahkan ke Frame
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    public void clearForm() {
        txtIdAnggaran.setText("");
        txtAnggaranBulanan.setText("");
        txtBulan.setText("");
        txtTahun.setText("");
    }

    public void loadKategoriToComboBox(String[] kategoriNames) {
        cmbKategori.removeAllItems();
        for (String kategori : kategoriNames) {
            cmbKategori.addItem(kategori);
        }
    }

    public JTextField getTxtIdAnggaran() {
        return txtIdAnggaran;
    }

    public JTextField getTxtAnggaranBulanan() {
        return txtAnggaranBulanan;
    }

    public JTextField getTxtBulan() {
        return txtBulan;
    }

    public JTextField getTxtTahun() {
        return txtTahun;
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

    public JTable getTableAnggaran() {
        return tableAnggaran;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
