// Zain
public class Kuitansi {
  public static void cetak(
    ItemPesanan[] itemPesanan,
    int jumlahItem,
    String metodePembayaran,
    String mataUang,
    double saldo, // Changed from int to double
    KonversiMataUang konversi) {
      System.out.println("\n========== KUITANSI PESANAN ==========");
      System.out.println("           Warkop KohiShop           ");
      System.out.println("======================================");
      
      int totalHargaMinuman = 0, totalHargaMakanan = 0;
      double totalPajakMinuman = 0, totalPajakMakanan = 0;
      int itemNotNull = jumlahItem;

    System.out.println(">> MINUMAN");
    for (int i = 0; i < itemNotNull;i++) {
      if (itemPesanan[i].getMenu() instanceof Minuman) {
        int jumlah = itemPesanan[i].getJumlah();
        double harga = itemPesanan[i].getMenu().getHarga();
        double subtotal = harga * jumlah;
        double pajak = hitungPajakMinuman(harga) * jumlah;
        totalHargaMinuman += subtotal;
        totalPajakMinuman += pajak;

        System.out.printf("- %s (%s): %d x %.2f = %.2f + Pajak: %.2f\n", itemPesanan[i].getMenu().getNama(), itemPesanan[i].getMenu().getKode(), jumlah, harga,
            subtotal, pajak);
      }
    }

    System.out.println(">> MAKANAN");
    for (int i = 0; i < itemNotNull; i++) {
      if (itemPesanan[i].getMenu() instanceof Makanan) {
        int jumlah = itemPesanan[i].getJumlah();
        double harga = itemPesanan[i].getMenu().getHarga();
        double subtotal = harga * jumlah;
        double pajak = hitungPajakMakanan(harga) * jumlah;
        totalHargaMakanan += subtotal;
        totalPajakMakanan += pajak;

        System.out.printf("- %s (%s): %d x %.2f = %.2f + Pajak: %.2f\n", itemPesanan[i].getMenu().getNama(), itemPesanan[i].getMenu().getKode(), jumlah, harga,
            subtotal, pajak);
      }
    }

    int totalSebelumPajak = totalHargaMinuman + totalHargaMakanan;
    double totalPajak = totalPajakMinuman + totalPajakMakanan;
    double totalSetelahPajak = totalSebelumPajak + totalPajak;

    System.out.println("--------------------------------------");
    System.out.printf("Subtotal (sebelum pajak): %.2f IDR\n", (double) totalSebelumPajak);
    System.out.printf("Total Pajak: %.2f IDR\n", totalPajak);
    System.out.printf("Total (setelah pajak): %.2f IDR\n", totalSetelahPajak);

    double diskon = 0;
    double biayaAdmin = 0;

    if (metodePembayaran.equalsIgnoreCase("QRIS")) {
      diskon = totalSetelahPajak * 0.05;
    } else if (metodePembayaran.equalsIgnoreCase("eMoney")) {
      diskon = totalSetelahPajak * 0.07;
      biayaAdmin = 20;
    }

    double totalAkhir = totalSetelahPajak - diskon + biayaAdmin;

    System.out.println("Metode Pembayaran: " + metodePembayaran);
    System.out.printf("Diskon: -%.2f IDR\n", diskon);
    System.out.printf("Biaya Admin: +%.2f IDR\n", biayaAdmin);
    System.out.printf("Total Akhir: %.2f IDR\n", totalAkhir);

    double totalDalamMataUang = konversi.konversi(totalAkhir, mataUang);
    System.out.printf("Total Dibayar dalam %s: %.2f %s\n", mataUang, totalDalamMataUang, mataUang);

    if (!metodePembayaran.equalsIgnoreCase("Tunai")) {
      if (saldo < totalAkhir) {
        System.out.println("!! Saldo tidak mencukupi. Pembayaran gagal.");
        return;
      } else {
        System.out.printf("Sisa saldo setelah pembayaran: %.2f IDR\n", saldo - totalAkhir);
      }
    }

    System.out.println("======================================");
    System.out.println("Terima kasih dan silakan datang kembali!");
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
