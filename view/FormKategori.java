package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormKategori extends JInternalFrame {
    private JTable tableKategori;
    private DefaultTableModel tableModel;
    private JTextField txtIdKategori, txtNamaKategori;
    private JButton btnTambah, btnEdit, btnHapus, btnReset;

    public FormKategori() {
        setTitle("Form Data Kategori");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.50),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.50));
        setLayout(new BorderLayout());

        // Panel Form Input
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 10)); // 2 baris untuk id dan nama kategori
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelForm.add(new JLabel("ID Kategori:"));
        txtIdKategori = new JTextField();
        txtIdKategori.setEditable(false); // ID kategori tidak dapat diubah secara manual
        panelForm.add(txtIdKategori);
        panelForm.add(new JLabel("Nama Kategori:"));
        txtNamaKategori = new JTextField();
        panelForm.add(txtNamaKategori);

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
        tableModel = new DefaultTableModel(new String[]{"ID Kategori", "Nama Kategori"}, 0);
        tableKategori = new JTable(tableModel);
        tableKategori.setShowGrid(true);
        tableKategori.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(tableKategori);

        // Tambahkan ke Frame
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    public void clearForm() {
        txtIdKategori.setText("");
        txtNamaKategori.setText("");
    }

    public JTextField getTxtIdKategori() {
        return txtIdKategori;
    }

    public JTextField getTxtNamaKategori() {
        return txtNamaKategori;
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

    public JTable getTableKategori() {
        return tableKategori;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}