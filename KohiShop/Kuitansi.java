// Zain
import java.util.ArrayList;

public class Kuitansi {
  public static void cetak(
      ArrayList<ItemPesanan> jumlahItem,
      String metodePembayaran,
      String mataUang,
      double saldo,
      IKonversiMataUang konversi) {

    System.out.println("\n+--------------------------------------------------------------------------------+");
    System.out.println("|                                    Kuitansi                                    |");
    System.out.println("|                                 Warkop KohiShop                                |");
    System.out.println("+--------------------------------------------------------------------------------+");

    int totalHargaMinuman = 0, totalHargaMakanan = 0;
    double totalPajakMinuman = 0, totalPajakMakanan = 0;

    System.out.println("| >> MINUMAN                                                                     |");
    for (ItemPesanan item : jumlahItem) {
      if (item.getMenu() instanceof Minuman) {
        int jumlah = item.getJumlah();
        double harga = item.getMenu().getHarga();
        double subtotal = harga * jumlah;
        double pajak = hitungPajakMinuman(harga) * jumlah;
        totalHargaMinuman += subtotal;
        totalPajakMinuman += pajak;

        System.out.printf("| %-30s (%s) : %2d x %7.2f = %8.2f + Pajak: %7.2f |\n",
            item.getMenu().getNama(),
            item.getMenu().getKode(),
            jumlah, harga, subtotal, pajak);
      }
    }

    System.out.println("| >> MAKANAN                                                                     |");
    for (ItemPesanan item : jumlahItem) {
      if (item.getMenu() instanceof Makanan) {
        int jumlah = item.getJumlah();
        double harga = item.getMenu().getHarga();
        double subtotal = harga * jumlah;
        double pajak = hitungPajakMakanan(harga) * jumlah;
        totalHargaMakanan += subtotal;
        totalPajakMakanan += pajak;

        System.out.printf("| %-30s (%s) : %2d x %7.2f = %8.2f + Pajak: %7.2f |\n",
            item.getMenu().getNama(),
            item.getMenu().getKode(),
            jumlah, harga, subtotal, pajak);
      }
    }

    int totalSebelumPajak = totalHargaMinuman + totalHargaMakanan;
    double totalPajak = totalPajakMinuman + totalPajakMakanan;
    double totalSetelahPajak = totalSebelumPajak + totalPajak;

    System.out.println("+--------------------------------------------------------------------------------+");
    System.out.printf("| %-35s | %36.2f IDR |\n", "Subtotal (sebelum pajak)", (double) totalSebelumPajak);
    System.out.printf("| %-35s | %36.2f IDR |\n", "Total Pajak", totalPajak);
    System.out.printf("| %-35s | %36.2f IDR |\n", "Total (setelah pajak)", totalSetelahPajak);

    double diskon = 0;
    double biayaAdmin = 0;

    if (metodePembayaran.equalsIgnoreCase("QRIS")) {
      diskon = totalSetelahPajak * 0.05;
    } else if (metodePembayaran.equalsIgnoreCase("eMoney")) {
      diskon = totalSetelahPajak * 0.07;
      biayaAdmin = 20;
    }

    double totalAkhir = totalSetelahPajak - diskon + biayaAdmin;

    System.out.printf("| %-35s | %36.2f IDR |\n", "Diskon", -diskon);
    System.out.printf("| %-35s | %36.2f IDR |\n", "Biaya Admin", biayaAdmin);
    System.out.printf("| %-35s | %36.2f IDR |\n", "Total Akhir", totalAkhir);

    double totalDalamMataUang = konversi.konversi(totalAkhir, mataUang);
    System.out.printf("| %-35s | %36.2f %s |\n", "Total dalam " + mataUang, totalDalamMataUang, mataUang);

    if (!metodePembayaran.equalsIgnoreCase("Tunai")) {
      if (saldo < totalAkhir) {
        System.out.println("| !! Saldo tidak mencukupi. Pembayaran gagal.                                    |");
        System.out.println("+--------------------------------------------------------------------------------+");
        return;
      } else {
        System.out.printf("| %-35s | %36.2f IDR |\n", "Sisa saldo setelah pembayaran", saldo - totalAkhir);
      }
    }

    System.out.println("+--------------------------------------------------------------------------------+");
    System.out.println("|                      Terima kasih dan silakan datang kembali!                  |");
    System.out.println("+--------------------------------------------------------------------------------+");
  }

  private static double hitungPajakMinuman(double harga) {
    if (harga < 50)
      return 0;
    else if (harga <= 55)
      return harga * 0.08;
    else
      return harga * 0.11;
  }

  private static double hitungPajakMakanan(double harga) {
    if (harga > 50)
      return harga * 0.08;
    else
      return harga * 0.11;
  }
}
