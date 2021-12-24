package id.sadhaka.readdict.Model;

public class BorrowHandler {

    private int id_peminjaman;
    private String username;
    private String judul_buku;
    private String tanggal_peminjaman;
    private String tanggal_kembali;
    private String status;
    boolean statusAPI;
    private String message;

    public int getId_peminjaman() {
        return id_peminjaman;
    }

    public void setId_peminjaman(int id_peminjaman) {
        this.id_peminjaman = id_peminjaman;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJudul_buku() {
        return judul_buku;
    }

    public void setJudul_buku(String judul_buku) {
        this.judul_buku = judul_buku;
    }

    public String getTanggal_peminjaman() {
        return tanggal_peminjaman;
    }

    public void setTanggal_peminjaman(String tanggal_peminjaman) {
        this.tanggal_peminjaman = tanggal_peminjaman;
    }

    public String getTanggal_kembali() {
        return tanggal_kembali;
    }

    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isStatusAPI() {
        return statusAPI;
    }

    public void setStatusAPI(boolean statusAPI) {
        this.statusAPI = statusAPI;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
