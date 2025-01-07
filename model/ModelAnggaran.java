package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelAnggaran {
    private Connection connection;

    public ModelAnggaran(Connection connection) {
        this.connection = connection;
    }

    // Menambahkan anggaran baru
    public boolean tambahAnggaran(int idKategori, double anggaranBulanan, int bulan, int tahun) {
        String query = "INSERT INTO tb_anggaran (id_kategori, anggaran_bulanan, bulan, tahun) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idKategori);
            stmt.setDouble(2, anggaranBulanan);
            stmt.setInt(3, bulan);
            stmt.setInt(4, tahun);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mengedit anggaran berdasarkan ID
    public boolean editAnggaran(int idAnggaran, int idKategori, double anggaranBulanan, int bulan, int tahun) {
        String query = "UPDATE tb_anggaran SET id_kategori = ?, anggaran_bulanan = ?, bulan = ?, tahun = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idKategori);
            stmt.setDouble(2, anggaranBulanan);
            stmt.setInt(3, bulan);
            stmt.setInt(4, tahun);
            stmt.setInt(5, idAnggaran);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Menghapus anggaran berdasarkan ID
    public boolean hapusAnggaran(int idAnggaran) {
        String query = "DELETE FROM tb_anggaran WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idAnggaran);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mengambil semua data anggaran
    public List<String[]> getAllAnggaran() {
        List<String[]> anggaranList = new ArrayList<>();
        String query = "SELECT a.id, k.nama_kategori, a.anggaran_bulanan, a.bulan, a.tahun " +
                       "FROM tb_anggaran a JOIN tb_kategori k ON a.id_kategori = k.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                anggaranList.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("nama_kategori"),
                        String.valueOf(rs.getDouble("anggaran_bulanan")),
                        String.valueOf(rs.getInt("bulan")),
                        String.valueOf(rs.getInt("tahun"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return anggaranList;
    }

    // Mengambil daftar kategori untuk ComboBox
    public List<String> getAllKategoriNames() {
        List<String> kategoriNames = new ArrayList<>();
        String query = "SELECT nama_kategori FROM tb_kategori";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                kategoriNames.add(rs.getString("nama_kategori"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kategoriNames;
    }
}
