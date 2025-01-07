package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelLaporanKeuangan {
    private Connection connection;

    public ModelLaporanKeuangan(Connection connection) {
        this.connection = connection;
    }

    // Mengambil data laporan berdasarkan bulan dan tahun
    public List<Object[]> getLaporanKeuangan(int bulan, int tahun) {
        List<Object[]> laporanList = new ArrayList<>();
        String query = "SELECT k.nama_kategori, " +
               "SUM(CASE WHEN t.jenis_transaksi = 'Pemasukan' THEN t.jumlah ELSE 0 END) AS pemasukan, " +
               "SUM(CASE WHEN t.jenis_transaksi = 'Pengeluaran' THEN t.jumlah ELSE 0 END) AS pengeluaran, " +
               "a.anggaran_bulanan, " +
               "(a.anggaran_bulanan - SUM(CASE WHEN t.jenis_transaksi = 'Pengeluaran' THEN t.jumlah ELSE 0 END)) AS sisa_anggaran " +
               "FROM tb_transaksi t " +
               "JOIN tb_kategori k ON t.id_kategori = k.id " +
               "JOIN tb_anggaran a ON t.id_kategori = a.id_kategori " +
               "WHERE a.bulan = ? AND a.tahun = ? " +
               "AND MONTH(t.tanggal) = ? AND YEAR(t.tanggal) = ? " +
               "GROUP BY k.nama_kategori, a.anggaran_bulanan";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, bulan); // Parameter bulan
                stmt.setInt(2, tahun); // Parameter tahun
                stmt.setInt(3, bulan); // Bulan pada transaksi
                stmt.setInt(4, tahun); // Tahun pada transaksi
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    laporanList.add(new Object[]{
                        rs.getString("nama_kategori"),
                        rs.getDouble("pemasukan"),
                        rs.getDouble("pengeluaran"),
                        rs.getDouble("anggaran_bulanan"),
                        rs.getDouble("sisa_anggaran")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return laporanList;
    }

    // Mengambil daftar tahun yang tersedia dalam transaksi
    public List<String> getAllTahun() {
        List<String> tahunList = new ArrayList<>();
        String query = "SELECT DISTINCT tahun FROM tb_anggaran ORDER BY tahun DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tahunList.add(rs.getString("tahun"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tahunList;
    }    
}
