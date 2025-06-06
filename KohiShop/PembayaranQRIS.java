
// Faiz
import java.util.Scanner;

public class PembayaranQRIS implements IPembayaran {
    @Override
    public double prosesPembayaran(double totalTagihan) {
        double diskon = totalTagihan * 0.05; // Diskon 5%
        System.out.printf("Metode QRIS: Mendapat diskon 5%% sebesar Rp %,.2f\n", diskon);
        return totalTagihan - diskon;
    }

    @Override
    public boolean cekSaldo(double saldo, double totalTagihan) {
        return saldo >= totalTagihan; // Saldo harus cukup
    }

    // Ini method helper dari teman Anda, kita pertahankan
    public static double getSaldoJikaPerlu(String metode, Scanner input) {
        System.out.print("Masukkan saldo " + metode + " Anda: ");
        return input.nextDouble();
    }
}