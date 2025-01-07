import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import model.*;
import controller.*;
import view.*;

public class AplikasiKeuanganPribadi extends JFrame {
    private static final double SCALE = 0.85; // Skala responsif ukuran frame
    private JDesktopPane mdiDesktopPane;
    private JMenuBar menuBar;

    private JMenu kategoriMenu;
    private JMenu transaksiMenu;
    private JMenu laporanMenu;
    private JMenu userMenu;

    private JMenuItem menuItemKategori;
    private JMenuItem menuItemPemasukanPengeluaran;
    private JMenuItem menuItemLaporanKeuangan;
    private JMenuItem menuItemLogout;
    private JMenuItem menuItemAnggaran; // Menu item untuk Anggaran (dimasukkan ke dalam Transaksi)

    private FormKategori formKategori;
    private FormTransaksi formTransaksi;
    private FormAnggaran formAnggaran; // Form untuk Anggaran
    private FormLaporanKeuangan formLaporanKeuangan;

    private Connection connection;
    private ControllerKategori kategoriController;
    private ControllerTransaksi transaksiController;
    private ControllerAnggaran anggaranController; // Controller untuk Anggaran
    private ControllerLaporanKeuangan laporanKeuanganController;

    public AplikasiKeuanganPribadi() {
        try {
            connection = Koneksi.getConnection();  // Pastikan Anda memiliki kelas Koneksi
            
            // Inisialisasi controller untuk FormKategori, FormTransaksi, FormAnggaran
            kategoriController = new ControllerKategori(new FormKategori(), connection);
            transaksiController = new ControllerTransaksi(new FormTransaksi(), connection);
            anggaranController = new ControllerAnggaran(new FormAnggaran(), connection);  // Inisialisasi ControllerAnggaran
            laporanKeuanganController = new ControllerLaporanKeuangan(new FormLaporanKeuangan(), connection);

            if (!login()) {
                System.exit(0); // Jika login gagal, keluar dari aplikasi
            }

            initComponents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menghubungkan ke database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void initComponents() {
        setTitle("Aplikasi Keuangan Pribadi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Mengatur ukuran frame agar responsif
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (SCALE * screenSize.getWidth()), (int) (SCALE * screenSize.getHeight()));
        setLocationRelativeTo(null);

        // Mengatur Look and Feel agar sesuai dengan sistem
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Gagal mengatur Nimbus LookAndFeel: " + e.getMessage());
        }

        mdiDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        // Membuat menu
        kategoriMenu = new JMenu("Kategori Pengeluaran");
        transaksiMenu = new JMenu("Transaksi");
        laporanMenu = new JMenu("Laporan Keuangan");
        userMenu = new JMenu("User");

        // Membuat item menu
        menuItemKategori = new JMenuItem("Kategori Pengeluaran");
        menuItemPemasukanPengeluaran = new JMenuItem("Pemasukan & Pengeluaran");
        menuItemLaporanKeuangan = new JMenuItem("Laporan Keuangan");
        menuItemLogout = new JMenuItem("Logout");
        menuItemAnggaran = new JMenuItem("Anggaran"); // Menu item untuk Anggaran

        // Menambahkan item menu ke menu
        setContentPane(mdiDesktopPane);
        setJMenuBar(menuBar);
        menuBar.add(kategoriMenu);
        menuBar.add(transaksiMenu);
        menuBar.add(laporanMenu);
        menuBar.add(userMenu);

        kategoriMenu.add(menuItemKategori);
        transaksiMenu.add(menuItemPemasukanPengeluaran);
        transaksiMenu.add(menuItemAnggaran); // Menambahkan menu item Anggaran di Transaksi
        laporanMenu.add(menuItemLaporanKeuangan);
        userMenu.add(menuItemLogout);

        // Menambahkan aksi untuk setiap item menu
        menuItemKategori.addActionListener(evt -> openFormKategori());
        menuItemPemasukanPengeluaran.addActionListener(evt -> openFormTransaksi());
        menuItemLaporanKeuangan.addActionListener(evt -> openFormLaporanKeuangan());
        menuItemAnggaran.addActionListener(evt -> openFormAnggaran()); // Aksi untuk membuka FormAnggaran
        menuItemLogout.addActionListener(evt -> logout());
    }

    // Membuka frame Kategori Pengeluaran
    private void openFormKategori() {
        if (formKategori == null || !formKategori.isVisible()) {
            formKategori = kategoriController.getFormKategoriView();
            mdiDesktopPane.add(formKategori);

            formKategori.setSize((int) (mdiDesktopPane.getWidth() * 0.8), (int) (mdiDesktopPane.getHeight() * 0.8));
            formKategori.setLocation((mdiDesktopPane.getWidth() - formKategori.getWidth()) / 2, (mdiDesktopPane.getHeight() - formKategori.getHeight()) / 2);

            kategoriController.tampilkanKategori();
        }
        formKategori.setVisible(true);
    }

    // Membuka frame Pemasukan & Pengeluaran
    private void openFormTransaksi() {
        if (formTransaksi == null || !formTransaksi.isVisible()) {
            formTransaksi = new FormTransaksi();
            mdiDesktopPane.add(formTransaksi);

            formTransaksi.setSize((int) (mdiDesktopPane.getWidth() * 0.8), (int) (mdiDesktopPane.getHeight() * 0.8));
            formTransaksi.setLocation(
                (mdiDesktopPane.getWidth() - formTransaksi.getWidth()) / 2,
                (mdiDesktopPane.getHeight() - formTransaksi.getHeight()) / 2
            );

            // Menampilkan daftar kategori di comboBox
            transaksiController.tampilkanTransaksi();
        }
        formTransaksi.setVisible(true);
    }

    // Membuka frame Laporan Keuangan
    private void openFormLaporanKeuangan() {
        if (formLaporanKeuangan == null || !formLaporanKeuangan.isVisible()) {
            formLaporanKeuangan = new FormLaporanKeuangan(); // Form khusus untuk laporan keuangan
            mdiDesktopPane.add(formLaporanKeuangan);

            // Pastikan ControllerLaporanKeuangan sudah didefinisikan dengan benar
            laporanKeuanganController = new ControllerLaporanKeuangan(formLaporanKeuangan, connection);

            formLaporanKeuangan.setSize((int) (mdiDesktopPane.getWidth() * 0.8), (int) (mdiDesktopPane.getHeight() * 0.8));
            formLaporanKeuangan.setLocation((mdiDesktopPane.getWidth() - formLaporanKeuangan.getWidth()) / 2, (mdiDesktopPane.getHeight() - formLaporanKeuangan.getHeight()) / 2);
        }
        formLaporanKeuangan.setVisible(true);
    }

    // Membuka frame Anggaran
    private void openFormAnggaran() {
        if (formAnggaran == null || !formAnggaran.isVisible()) {
            formAnggaran = anggaranController.getFormAnggaranView(); // Mengambil form anggaran dari controller
            mdiDesktopPane.add(formAnggaran);

            formAnggaran.setSize((int) (mdiDesktopPane.getWidth() * 0.8), (int) (mdiDesktopPane.getHeight() * 0.8));
            formAnggaran.setLocation((mdiDesktopPane.getWidth() - formAnggaran.getWidth()) / 2, (mdiDesktopPane.getHeight() - formAnggaran.getHeight()) / 2);

            anggaranController.tampilkanAnggaran();
        }
        formAnggaran.setVisible(true);
    }

    // Dialog login sederhana
    private boolean login() {
        // Username dan password yang ditentukan manual
        String validUsername = "admin";
        String validPassword = "123";
    
        // Membuat form untuk input username dan password
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };
    
        while (true) {
            int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Mendapatkan input username dan password
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
    
                // Memeriksa apakah username dan password sesuai
                if (username.equals(validUsername) && password.equals(validPassword)) {
                    return true; // Login berhasil
                } else {
                    JOptionPane.showMessageDialog(null, "Login gagal! Username atau Password salah.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                return false; // Jika user menekan Cancel
            }
        }
    }

    // Fungsi logout
    private void logout() {
        int option = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            for (JInternalFrame frame : mdiDesktopPane.getAllFrames()) {
                frame.dispose();
            }
            this.dispose();
            SwingUtilities.invokeLater(() -> new AplikasiKeuanganPribadi().setVisible(true));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplikasiKeuanganPribadi().setVisible(true));
    }
}
