package controller;

import model.ModelLaporanKeuangan;
import view.FormLaporanKeuangan;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;

public class ControllerLaporanKeuangan {
    private FormLaporanKeuangan formLaporanKeuangan;
    private ModelLaporanKeuangan modelLaporanKeuangan;

    public ControllerLaporanKeuangan(FormLaporanKeuangan formLaporanKeuangan, Connection connection) {
        this.formLaporanKeuangan = formLaporanKeuangan;
        this.modelLaporanKeuangan = new ModelLaporanKeuangan(connection);
        initListeners();
        loadTahunToComboBox();
    }

    // Menginisialisasi aksi tombol
    private void initListeners() {
        formLaporanKeuangan.getBtnTampilkan().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanLaporan();
            }
        });

        formLaporanKeuangan.getBtnReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
    }

    // Menampilkan laporan keuangan berdasarkan bulan dan tahun
    private void tampilkanLaporan() {
        DefaultTableModel model = formLaporanKeuangan.getTableModel();
        model.setRowCount(0); // Menghapus data lama

        int bulan = Integer.parseInt(formLaporanKeuangan.getCmbBulan().getSelectedItem().toString());
        int tahun = Integer.parseInt(formLaporanKeuangan.getCmbTahun().getSelectedItem().toString());

        List<Object[]> laporanList = modelLaporanKeuangan.getLaporanKeuangan(bulan, tahun);
        for (Object[] laporan : laporanList) {
            model.addRow(laporan);
        }
    }

    // Memuat tahun ke dalam ComboBox
    private void loadTahunToComboBox() {
        List<String> tahunList = modelLaporanKeuangan.getAllTahun();
        formLaporanKeuangan.loadTahunToComboBox(tahunList.toArray(new String[0]));
    }

    // Mereset form
    private void resetForm() {
        formLaporanKeuangan.clearForm();
    }
}
