
// Faiz
import java.util.Scanner;

public class PembayaranEMoney implements IPembayaran {
    @Override
    public double prosesPembayaran(double totalTagihan) {
        double diskon = totalTagihan * 0.07; // Diskon 7%
        double biayaAdmin = 20.0; // Biaya admin 20 IDR
        System.out.printf("Metode eMoney: Mendapat diskon 7%% (Rp %,.2f) dengan biaya admin Rp %,.2f\n", diskon,
                biayaAdmin);
        return (totalTagihan - diskon) + biayaAdmin;
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