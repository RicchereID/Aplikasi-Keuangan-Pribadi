package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormLaporanKeuangan extends JInternalFrame {
    private JTable tableLaporan;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbBulan, cmbTahun;
    private JButton btnTampilkan, btnReset;

    public FormLaporanKeuangan() {
        setTitle("Form Laporan Keuangan");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.60),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.60));
        setLayout(new BorderLayout());

        // Panel Filter Laporan
        JPanel panelFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFilter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFilter.add(new JLabel("Bulan:"));
        cmbBulan = new JComboBox<>(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
        panelFilter.add(cmbBulan);

        panelFilter.add(new JLabel("Tahun:"));
        cmbTahun = new JComboBox<>();
        panelFilter.add(cmbTahun);

        btnTampilkan = new JButton("Tampilkan Laporan");
        btnReset = new JButton("Reset");
        panelFilter.add(btnTampilkan);
        panelFilter.add(btnReset);

        // Panel Tabel Laporan
        tableModel = new DefaultTableModel(new String[]{"Kategori", "Pemasukan", "Pengeluaran", "Anggaran", "Sisa Anggaran"}, 0);
        tableLaporan = new JTable(tableModel);
        tableLaporan.setShowGrid(true);
        tableLaporan.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(tableLaporan);

        // Tambahkan ke Frame
        add(panelFilter, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Clear form filter
    public void clearForm() {
        cmbBulan.setSelectedIndex(0);
        cmbTahun.setSelectedIndex(-1);
    }

    // Load Tahun ke ComboBox
    public void loadTahunToComboBox(String[] tahunList) {
        cmbTahun.removeAllItems();
        for (String tahun : tahunList) {
            cmbTahun.addItem(tahun);
        }
    }

    // Getter dan Setter
    public JComboBox<String> getCmbBulan() {
        return cmbBulan;
    }

    public JComboBox<String> getCmbTahun() {
        return cmbTahun;
    }

    public JButton getBtnTampilkan() {
        return btnTampilkan;
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public JTable getTableLaporan() {
        return tableLaporan;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
