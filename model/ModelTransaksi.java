package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelTransaksi {
    private Connection connection;

    public ModelTransaksi(Connection connection) {
        this.connection = connection;
    }

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
        // Debugging: Tampilkan kategori yang berhasil diambil
        System.out.println("Kategori yang ditemukan: " + kategoriNames);
        return kategoriNames;
    }       

    public boolean tambahTransaksi(String jenisTransaksi, double jumlah, String deskripsi, int idKategori) {
        String query = "INSERT INTO tb_transaksi (jenis_transaksi, jumlah, deskripsi, id_kategori) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, jenisTransaksi);
            stmt.setDouble(2, jumlah);
            stmt.setString(3, deskripsi);
            stmt.setInt(4, idKategori);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> getAllTransaksi() {
        List<String[]> transaksiList = new ArrayList<>();
        String query = "SELECT t.id, t.jenis_transaksi, t.jumlah, t.tanggal, t.deskripsi, k.nama_kategori " +
                       "FROM tb_transaksi t JOIN tb_kategori k ON t.id_kategori = k.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                transaksiList.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("jenis_transaksi"),
                        String.valueOf(rs.getDouble("jumlah")),
                        rs.getString("tanggal"),
                        rs.getString("deskripsi"),
                        rs.getString("nama_kategori")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaksiList;
    }

    // Mengedit transaksi berdasarkan ID
    public boolean editTransaksi(int idTransaksi, String jenisTransaksi, double jumlah, String deskripsi, int idKategori) {
        String query = "UPDATE tb_transaksi SET jenis_transaksi = ?, jumlah = ?, deskripsi = ?, id_kategori = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, jenisTransaksi);
            stmt.setDouble(2, jumlah);
            stmt.setString(3, deskripsi);
            stmt.setInt(4, idKategori);
            stmt.setInt(5, idTransaksi);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Menghapus transaksi berdasarkan ID
    public boolean hapusTransaksi(int idTransaksi) {
        String query = "DELETE FROM tb_transaksi WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idTransaksi);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}