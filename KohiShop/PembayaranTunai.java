// Faiz
public class PembayaranTunai implements Pembayaran {
    
    @Override
    public double prosesPembayaran(double totalHarga) {
        return totalHarga; // Tidak ada diskon
    }

    @Override
    public boolean cekSaldo(double saldo, double totalAkhir) {
        return true; // Tunai tidak perlu cek saldo
    }
}
