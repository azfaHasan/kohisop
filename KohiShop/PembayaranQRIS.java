// Faiz
import java.util.Scanner;

public class PembayaranQRIS implements IPembayaran {
    public static double getSaldoJikaPerlu(String channel, Scanner scanner) {
        if (channel.equalsIgnoreCase("qris") || channel.equalsIgnoreCase("emoney")) {
            System.out.print("Masukkan saldo Anda: ");
            return scanner.nextDouble();
        }
        return 0;
    }

    @Override
    public double prosesPembayaran(double totalHarga) {
        return totalHarga * 0.95; // Diskon 5%
    }

    @Override
    public boolean cekSaldo(double saldo, double totalAkhir) {
        return saldo >= totalAkhir;
    }
}
