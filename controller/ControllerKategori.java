package controller;

import model.ModelKategori;
import view.FormKategori;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;

public class ControllerKategori {
    private FormKategori formKategori;
    private ModelKategori modelKategori;

    public ControllerKategori(FormKategori formKategori, Connection connection) {
        this.formKategori = formKategori;
        this.modelKategori = new ModelKategori(connection);
        initListeners();
        tampilkanKategori();
    }

    // Menginisialisasi aksi tombol
    private void initListeners() {
        formKategori.getBtnTambah().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahKategori();
            }
        });

        formKategori.getBtnEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editKategori();
            }
        });

        formKategori.getBtnHapus().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusKategori();
            }
        });

        formKategori.getBtnReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        // Menambahkan listener klik pada tabel untuk menampilkan data di form input
        formKategori.getTableKategori().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = formKategori.getTableKategori().getSelectedRow();
                if (selectedRow != -1) {
                    // Ambil data dari baris yang dipilih
                    String idKategori = formKategori.getTableModel().getValueAt(selectedRow, 0).toString();
                    String namaKategori = formKategori.getTableModel().getValueAt(selectedRow, 1).toString();
                    
                    // Set data ke form input
                    formKategori.getTxtIdKategori().setText(idKategori);
                    formKategori.getTxtNamaKategori().setText(namaKategori);
                }
            }
        });
    }

    // Menampilkan data kategori di tabel
    public void tampilkanKategori() {
        DefaultTableModel model = formKategori.getTableModel(); // Mendapatkan model tabel
        model.setRowCount(0); // Hapus data lama di tabel
    
        List<String[]> kategoriList = modelKategori.getAllKategori(); // Ambil data kategori
        for (String[] kategori : kategoriList) {
            model.addRow(kategori); // Tambahkan setiap kategori (ID dan Nama Kategori) ke tabel
        }
    }
        

    public FormKategori getFormKategoriView() {
        return formKategori;
    }

    // Menambah kategori baru
    private void tambahKategori() {
        String namaKategori = formKategori.getTxtNamaKategori().getText();

        if (namaKategori.isEmpty()) {
            JOptionPane.showMessageDialog(formKategori, "Nama kategori harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (modelKategori.tambahKategori(namaKategori)) {
            JOptionPane.showMessageDialog(formKategori, "Kategori berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilkanKategori(); // Refresh tabel
            formKategori.clearForm(); // Kosongkan form input
        } else {
            JOptionPane.showMessageDialog(formKategori, "Gagal menambahkan kategori.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mengedit kategori
    private void editKategori() {
        int selectedRow = formKategori.getTableKategori().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(formKategori, "Pilih kategori yang ingin diedit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idKategori = Integer.parseInt(formKategori.getTableModel().getValueAt(selectedRow, 0).toString());
        String namaKategori = formKategori.getTxtNamaKategori().getText();

        if (namaKategori.isEmpty()) {
            JOptionPane.showMessageDialog(formKategori, "Nama kategori harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (modelKategori.editKategori(idKategori, namaKategori)) {
            JOptionPane.showMessageDialog(formKategori, "Kategori berhasil diedit.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilkanKategori(); // Refresh tabel
            formKategori.clearForm(); // Kosongkan form input
        } else {
            JOptionPane.showMessageDialog(formKategori, "Gagal mengedit kategori.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghapus kategori
    private void hapusKategori() {
        int selectedRow = formKategori.getTableKategori().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(formKategori, "Pilih kategori yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idKategori = Integer.parseInt(formKategori.getTableModel().getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(formKategori, "Apakah Anda yakin ingin menghapus kategori ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (modelKategori.hapusKategori(idKategori)) {
                JOptionPane.showMessageDialog(formKategori, "Kategori berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampilkanKategori(); // Refresh tabel
                formKategori.clearForm(); // Kosongkan form input
            } else {
                JOptionPane.showMessageDialog(formKategori, "Gagal menghapus kategori.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Mereset form
    private void resetForm() {
        formKategori.clearForm();
    }
}
