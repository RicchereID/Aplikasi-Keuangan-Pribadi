package controller;

import model.ModelAnggaran;
import view.FormAnggaran;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ControllerAnggaran {
    private FormAnggaran formAnggaran;
    private ModelAnggaran modelAnggaran;

    public ControllerAnggaran(FormAnggaran formAnggaran, Connection connection) {
        this.formAnggaran = formAnggaran;
        this.modelAnggaran = new ModelAnggaran(connection);
        initListeners();
        tampilkanAnggaran();
        loadKategoriToComboBox();
    }

    // Menambahkan metode untuk mendapatkan FormAnggaran
    public FormAnggaran getFormAnggaranView() {
        return formAnggaran;
    }

    // Menginisialisasi aksi tombol
    private void initListeners() {
        formAnggaran.getBtnTambah().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahAnggaran();
            }
        });

        formAnggaran.getBtnEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAnggaran();
            }
        });

        formAnggaran.getBtnHapus().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusAnggaran();
            }
        });

        formAnggaran.getBtnReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
    }

    // Menampilkan data anggaran di tabel
    public void tampilkanAnggaran() {
        DefaultTableModel model = formAnggaran.getTableModel();
        model.setRowCount(0); // Menghapus data lama

        List<String[]> anggaranList = modelAnggaran.getAllAnggaran();
        for (String[] anggaran : anggaranList) {
            model.addRow(anggaran);
        }
    }

    // Memuat kategori ke dalam ComboBox
    private void loadKategoriToComboBox() {
        List<String> kategoriNames = modelAnggaran.getAllKategoriNames();
        formAnggaran.loadKategoriToComboBox(kategoriNames.toArray(new String[0]));
    }

    // Menambah anggaran baru
    private void tambahAnggaran() {
        try {
            int selectedCategoryIndex = formAnggaran.getCmbKategori().getSelectedIndex();
            if (selectedCategoryIndex == -1) {
                JOptionPane.showMessageDialog(formAnggaran, "Kategori harus dipilih.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String anggaranBulananStr = formAnggaran.getTxtAnggaranBulanan().getText();
            String bulanStr = formAnggaran.getTxtBulan().getText();
            String tahunStr = formAnggaran.getTxtTahun().getText();

            if (anggaranBulananStr.isEmpty() || bulanStr.isEmpty() || tahunStr.isEmpty()) {
                JOptionPane.showMessageDialog(formAnggaran, "Semua field harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double anggaranBulanan = Double.parseDouble(anggaranBulananStr);
            int bulan = Integer.parseInt(bulanStr);
            int tahun = Integer.parseInt(tahunStr);

            // Ambil ID kategori dari ComboBox
            int idKategori = selectedCategoryIndex + 1; // Karena ComboBox dimulai dari indeks 0

            if (modelAnggaran.tambahAnggaran(idKategori, anggaranBulanan, bulan, tahun)) {
                JOptionPane.showMessageDialog(formAnggaran, "Anggaran berhasil ditambahkan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampilkanAnggaran();
                formAnggaran.clearForm();
            } else {
                JOptionPane.showMessageDialog(formAnggaran, "Gagal menambahkan anggaran.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(formAnggaran, "Masukkan nilai yang valid untuk anggaran bulanan, bulan, dan tahun.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mengedit anggaran
    private void editAnggaran() {
        int selectedRow = formAnggaran.getTableAnggaran().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(formAnggaran, "Pilih anggaran yang ingin diedit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int idAnggaran = Integer.parseInt(formAnggaran.getTableModel().getValueAt(selectedRow, 0).toString());
            int idKategori = formAnggaran.getCmbKategori().getSelectedIndex() + 1;
            double anggaranBulanan = Double.parseDouble(formAnggaran.getTxtAnggaranBulanan().getText());
            int bulan = Integer.parseInt(formAnggaran.getTxtBulan().getText());
            int tahun = Integer.parseInt(formAnggaran.getTxtTahun().getText());

            if (modelAnggaran.editAnggaran(idAnggaran, idKategori, anggaranBulanan, bulan, tahun)) {
                JOptionPane.showMessageDialog(formAnggaran, "Anggaran berhasil diedit.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampilkanAnggaran();
                formAnggaran.clearForm();
            } else {
                JOptionPane.showMessageDialog(formAnggaran, "Gagal mengedit anggaran.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(formAnggaran, "Masukkan nilai yang valid untuk anggaran bulanan, bulan, dan tahun.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghapus anggaran
    private void hapusAnggaran() {
        int selectedRow = formAnggaran.getTableAnggaran().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(formAnggaran, "Pilih anggaran yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idAnggaran = Integer.parseInt(formAnggaran.getTableModel().getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(formAnggaran, "Apakah Anda yakin ingin menghapus anggaran ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (modelAnggaran.hapusAnggaran(idAnggaran)) {
                JOptionPane.showMessageDialog(formAnggaran, "Anggaran berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                tampilkanAnggaran();
                formAnggaran.clearForm();
            } else {
                JOptionPane.showMessageDialog(formAnggaran, "Gagal menghapus anggaran.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Mereset form
    private void resetForm() {
        formAnggaran.clearForm();
    }
}
