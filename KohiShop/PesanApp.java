import java.util.Scanner;

public class PesanApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // KohiShop Part 2 : Pemesanan Makanan dan Minuman : objek-objek untuk proses
        // pemesanan
        DaftarMenu daftar = new DaftarMenu();
        ProsesPesan pesanan = new ProsesPesan();

        // KohiShop Part 2 : Membership : objek-objek untuk membership
        MemberManagement membership = new MemberManagement();

        // KohiShop Part 2 : Membership : meminta nama pengguna untuk menjadi member
        System.out.print("Halo pelanggan, siapa nama anda ? ");
        String namaPelanggan = input.nextLine();
        membership.autoAddMember(namaPelanggan);
        System.out.println("Selamat Datang " + namaPelanggan + "! silahkan pilih menu yang tertera!");

        // Kode teman Anda untuk inisialisasi
        String inputAwal = "";
        boolean sudahPesan = false;

        while (true) {
            System.out.println("\n1. Lihat Daftar Menu");
            System.out.println("2. Pesan Sekarang");
            System.out.println("3. Lihat Pesanan Anda & Bayar");
            System.out.println("\nCC. Keluar dan Batalkan Pesanan\n");

            inputAwal = input.nextLine();

            if (inputAwal.equalsIgnoreCase("1")) {
                daftar.tampilkanDataAll();
            } else if (inputAwal.equalsIgnoreCase("2")) {
                while (true) {
                    System.out.print("Input Kode Menu (input B untuk kembali ke menu utama): ");
                    String kodeMenu = input.nextLine();

                    if (kodeMenu.equalsIgnoreCase("b")) {
                        break;
                    }

                    Menu menu = daftar.validasiMenu(kodeMenu);
                    if (menu == null) {
                        System.out.println("Kode menu tidak ditemukan!");
                        continue;
                    }

                    System.out.print("Input jumlah pesanan (input 0 atau S untuk membatalkan menu ini): ");
                    String kuantitas = input.nextLine();

                    pesanan.prosesPesan(menu, kuantitas);
                    sudahPesan = true;
                }
            } else if (inputAwal.equalsIgnoreCase("3")) {
                if (!sudahPesan) {
                    System.out.println("\nAnda belum membuat pesanan!");
                    continue;
                }

                pesanan.tampilkanPesanan();

                System.out.print("Ingin lanjut ke pembayaran? (y/n): ");
                String lanjut = input.nextLine();

                if (lanjut.equalsIgnoreCase("y")) {

                    // Hitung total harga dasar (Subtotal)
                    double totalHargaDasar = 0;
                    for (ItemPesanan item : pesanan.getPesanan()) {
                        totalHargaDasar += item.getMenu().getHarga() * item.getJumlah();
                    }
                    System.out.printf("\nSubtotal Pesanan: Rp %,.2f\n", totalHargaDasar);

                    // Hitung Pajak
                    Membership currentMember = membership.getMember();
                    double totalPajak = 0;
                    if (currentMember.getKode().contains("A")) {
                        System.out
                                .println("Info: Anda member dengan kode " + currentMember.getKode() + ", bebas pajak!");
                    } else {
                        for (ItemPesanan item : pesanan.getPesanan()) {
                            Menu menu = item.getMenu();
                            int jumlah = item.getJumlah();
                            double harga = menu.getHarga();
                            double tarifPajak = 0;
                            if (menu instanceof Minuman) {
                                if (harga >= 50 && harga <= 55)
                                    tarifPajak = 0.08;
                                else if (harga > 55)
                                    tarifPajak = 0.11;
                            } else {
                                if (harga > 50)
                                    tarifPajak = 0.08;
                                else
                                    tarifPajak = 0.11;
                            }
                            totalPajak += harga * tarifPajak * jumlah;
                        }
                        System.out.printf("Total Pajak: Rp %,.2f\n", totalPajak);
                    }

                    double totalSetelahPajak = totalHargaDasar + totalPajak;
                    System.out.printf("Total Tagihan Sementara: Rp %,.2f\n", totalSetelahPajak);

                    // Tawarkan Penggunaan Poin
                    double tagihanUntukDibayar = totalSetelahPajak;
                    if (currentMember.getPoin() > 0) {
                        System.out.println("\nAnda memiliki " + currentMember.getPoin() + " poin (senilai Rp "
                                + (currentMember.getPoin() * 2) + ")");
                        System.out.print("Gunakan poin untuk memotong tagihan? (y/n): ");
                        if (input.nextLine().equalsIgnoreCase("y")) {
                            double nilaiPoin = currentMember.getPoin() * 2.0;
                            if (nilaiPoin >= tagihanUntukDibayar) {
                                currentMember.setPoin((int) ((nilaiPoin - tagihanUntukDibayar) / 2));
                                tagihanUntukDibayar = 0;
                            } else {
                                tagihanUntukDibayar -= nilaiPoin;
                                currentMember.setPoin(0);
                            }
                            System.out.printf("Sisa tagihan setelah potong poin: Rp %,.2f\n", tagihanUntukDibayar);
                        }
                    }

                    double totalFinalIDR = tagihanUntukDibayar;

                    // Pilih Channel Pembayaran
                    if (tagihanUntukDibayar > 0) {
                        System.out.println("\nPilih Metode Pembayaran:");
                        System.out.println("1. Tunai");
                        System.out.println("2. QRIS");
                        System.out.println("3. eMoney");
                        System.out.print("Masukkan pilihan: ");
                        String pilihan = input.nextLine();

                        IPembayaran bayar = null;
                        double saldo = 0;

                        switch (pilihan) {
                            case "1":
                                bayar = new PembayaranTunai();
                                break;
                            case "2":
                                bayar = new PembayaranQRIS();
                                saldo = PembayaranQRIS.getSaldoJikaPerlu("qris", input);
                                input.nextLine();
                                break;
                            case "3":
                                bayar = new PembayaranEMoney();
                                saldo = PembayaranEMoney.getSaldoJikaPerlu("emoney", input);
                                input.nextLine();
                                break;
                            default:
                                System.out.println("Pilihan tidak valid.");
                                continue;
                        }

                        totalFinalIDR = bayar.prosesPembayaran(tagihanUntukDibayar);

                        if (!bayar.cekSaldo(saldo, totalFinalIDR)) {
                            System.out.println("Saldo tidak mencukupi. Pembayaran dibatalkan.");
                            continue;
                        }
                    } else {
                        System.out.println("\nTagihan Anda sudah lunas dengan Poin!");
                    }

                    // Pilih Mata Uang & Konversi
                    System.out.println("\nPilih mata uang untuk kuitansi:");
                    System.out.println("1. IDR (default) | 2. USD | 3. JPY | 4. MYR | 5. EUR");
                    System.out.print("Masukkan pilihan: ");
                    String kodeMataUang = input.nextLine();
                    String mataUangStr = switch (kodeMataUang) {
                        case "2" -> "USD";
                        case "3" -> "JPY";
                        case "4" -> "MYR";
                        case "5" -> "EUR";
                        default -> "IDR";
                    };

                    MataUang konverter = new MataUang();
                    double totalFinalDikonversi = konverter.konversi(totalFinalIDR, mataUangStr);
                    System.out.printf("\nTotal Tagihan Final: %s %,.2f\n", mataUangStr, totalFinalDikonversi);

                    // Cetak Kuitansi dan Tambah Poin Baru
                    System.out.println("\n--- Transaksi Berhasil ---");

                    // KohiShop Part 2 : Membership : add belanja dan poin (jika memenuhi syarat)
                    // secara otomatis
                    int jumlahMenuDibeli = 0;
                    for (ItemPesanan item : pesanan.getPesanan()) {
                        jumlahMenuDibeli += item.getJumlah();
                    }
                    currentMember.addBelanjaAndPoin(jumlahMenuDibeli);

                    pesanan.getPesanan().clear();
                    System.out.println("Terima kasih dan silakan datang kembali!");
                    break;
                }
            } else if (inputAwal.equalsIgnoreCase("CC")) {
                break;
            } else {
                System.out.println("Input tidak valid, silahkan pilih salah satu menu yang tertera!");
            }
        }
        input.close();
    }
}