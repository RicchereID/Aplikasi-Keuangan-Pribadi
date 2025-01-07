package controller;

import model.ModelTransaksi;
import model.ModelKategori;
import view.FormTransaksi;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ControllerTransaksi {
    private FormTransaksi formTransaksi;
    private ModelTransaksi modelTransaksi;
    private ModelKategori modelKategori;

    public ControllerTransaksi(FormTransaksi formTransaksi, Connection connection) {
        this.formTransaksi = formTransaksi;
        this.modelTransaksi = new ModelTransaksi(connection);
        this.modelKategori = new ModelKategori(connection);
        initListeners();
        loadKategoriToComboBox();
        tampilkanTransaksi();
    }

    // Menginisialisasi aksi tombol
    private void initListeners() {
        formTransaksi.getBtnTambah().addActionListener(e -> tambahTransaksi());
        formTransaksi.getBtnEdit().addActionListener(e -> editTransaksi());
        formTransaksi.getBtnHapus().addActionListener(e -> hapusTransaksi());
        formTransaksi.getBtnReset().addActionListener(e -> resetForm());
    }

    // Menampilkan kategori di ComboBox
    private void loadKategoriToComboBox() {
        List<String> kategoriNames = modelTransaksi.getAllKategoriNames();
        System.out.println("Kategori yang diterima: " + kategoriNames);  // Log tambahan
        if (kategoriNames.isEmpty()) {
            JOptionPane.showMessageDialog(formTransaksi, "Kategori tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        formTransaksi.loadKategoriToComboBox(kategoriNames.toArray(new String[0]));
    }

    public void tampilkanTransaksi() {
        List<String> kategoriNames = modelTransaksi.getAllKategoriNames();
        System.out.println("Data kategori yang diterima: " + kategoriNames);
        formTransaksi.loadKategoriToComboBox(kategoriNames.toArray(new String[0]));
    }

    // Validasi input transaksi
    private boolean validasiInput() {
        if (formTransaksi.getTxtJumlah().getText().isEmpty() ||
            formTransaksi.getTxtDeskripsi().getText().isEmpty() ||
            formTransaksi.getCmbKategori().getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(formTransaksi, "Semua kolom harus diisi!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            Double.parseDouble(formTransaksi.getTxtJumlah().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(formTransaksi, "Jumlah harus berupa angka!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    // Menambah transaksi baru
    private void tambahTransaksi() {
        if (!validasiInput()) {
            return;
        }

        String jenisTransaksi = formTransaksi.getCmbJenisTransaksi().getSelectedItem().toString();
        double jumlah = Double.parseDouble(formTransaksi.getTxtJumlah().getText());
        String deskripsi = formTransaksi.getTxtDeskripsi().getText();
        String kategori = formTransaksi.getCmbKategori().getSelectedItem().toString();
        int idKategori = modelKategori.getIdKategoriByName(kategori); // Menyesuaikan dengan nama kategori

        if (modelTransaksi.tambahTransaksi(jenisTransaksi, jumlah, deskripsi, idKategori)) {
            JOptionPane.showMessageDialog(formTransaksi, "Transaksi berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilkanTransaksi();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(formTransaksi, "Gagal menambahkan transaksi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mengedit transaksi
    private void editTransaksi() {
        int selectedRow = formTransaksi.getTableTransaksi().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(formTransaksi, "Pilih transaksi yang ingin diedit.", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validasiInput()) {
            return;
        }

        int idTransaksi = Integer.parseInt(formTransaksi.getTableModel().getValueAt(selectedRow, 0).toString());
        String jenisTransaksi = formTransaksi.getCmbJenisTransaksi().getSelectedItem().toString();
        double jumlah = Double.parseDouble(formTransaksi.getTxtJumlah().getText());
        String deskripsi = formTransaksi.getTxtDeskripsi().getText();
        String kategori = formTransaksi.getCmbKategori().getSelectedItem().toString();
        int idKategori = modelKategori.getIdKategoriByName(kategori);

        if (modelTransaksi.editTransaksi(idTransaksi, jenisTransaksi, jumlah, deskripsi, idKategori)) {
            JOptionPane.showMessageDialog(formTransaksi, "Transaksi berhasil diedit.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            tampilkanTransaksi();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(formTransaksi, "Gagal mengedit transaksi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghapus transaksi
    private void hapusTransaksi() {
        int selectedRow = formTransaksi.getTableTransaksi().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(formTransaksi, "Pilih transaksi yang ingin dihapus.", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(formTransaksi, "Yakin ingin menghapus transaksi ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int idTransaksi = Integer.parseInt(formTransaksi.getTableModel().getValueAt(selectedRow, 0).toString());
            if (modelTransaksi.hapusTransaksi(idTransaksi)) {
                JOptionPane.showMessageDialog(formTransaksi, "Transaksi berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampilkanTransaksi();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(formTransaksi, "Gagal menghapus transaksi.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Mereset form
    private void resetForm() {
        formTransaksi.clearForm();
    }
}