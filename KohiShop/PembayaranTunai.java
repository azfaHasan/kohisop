// Faiz
public class PembayaranTunai implements IPembayaran {
    @Override
    public double prosesPembayaran(double totalTagihan) {
        System.out.println("Metode Tunai: Tidak ada diskon.");
        return totalTagihan;
    }

    @Override
    public boolean cekSaldo(double saldo, double totalTagihan) {
        return true;
    }
}