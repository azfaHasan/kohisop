// Faiz
import java.util.Scanner;

public class PembayaranEMoney implements IPembayaran {
    public static double getSaldoJikaPerlu(String channel, Scanner scanner) {
        if (channel.equalsIgnoreCase("qris") || channel.equalsIgnoreCase("emoney")) {
            System.out.print("Masukkan saldo Anda: ");
            return scanner.nextDouble();
        }
        return 0;
    }

    @Override
    public double prosesPembayaran(double totalHarga) {
        return (totalHarga * 0.93) + 20; // Diskon 7% + Admin 20
    }
    
    @Override
    public boolean cekSaldo(double saldo, double totalAkhir) {
        return saldo >= totalAkhir;
    }
}
