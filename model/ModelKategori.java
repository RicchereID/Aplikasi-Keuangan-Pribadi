package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModelKategori {
    private Connection connection;

    public ModelKategori(Connection connection) {
        this.connection = connection;
    }

    // Menambahkan fungsi untuk mendapatkan ID kategori berdasarkan nama kategori
    public int getIdKategoriByName(String kategori) {
        int idKategori = -1;
        String query = "SELECT id FROM kategori WHERE nama_kategori = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, kategori);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idKategori = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idKategori;
    }

    // Menambahkan kategori baru
    public boolean tambahKategori(String namaKategori) {
        String query = "INSERT INTO tb_kategori (nama_kategori) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaKategori);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mengedit kategori berdasarkan ID
    public boolean editKategori(int idKategori, String namaKategori) {
        String query = "UPDATE tb_kategori SET nama_kategori = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaKategori);
            stmt.setInt(2, idKategori);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Menghapus kategori berdasarkan ID
    public boolean hapusKategori(int idKategori) {
        String query = "DELETE FROM tb_kategori WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idKategori);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String[]> getAllKategori() {
        List<String[]> kategoriList = new ArrayList<>();
        String query = "SELECT id, nama_kategori FROM tb_kategori";
    
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                String[] kategori = new String[2];
                kategori[0] = String.valueOf(rs.getInt("id"));
                kategori[1] = rs.getString("nama_kategori");
                kategoriList.add(kategori);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return kategoriList;
    }                    
}