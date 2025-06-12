import java.util.ArrayList;
import java.util.Scanner;

public class PesanApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // KohiShop Part 2 : Pemesanan Makanan dan Minuman : objek-objek untuk proses pemesanan
        DaftarMenu daftar = new DaftarMenu();
        ProsesPesan pesanan = new ProsesPesan();

        // KohiShop Part 2 : Membership : objek-objek untuk membership
        MemberManagement membership = new MemberManagement();
        
        //KohiShop Part 2 : Proses Pesanan Tim Dapur : objek class dapur
        Dapur dapur = new Dapur();

        String inputAwal = "";
        boolean sudahPesan = false;

        tanyaNama(input, membership);
        String namaPelanggan = membership.getMember().getNama();
        
        while (true) {
            
            System.out.println("\nSelamat Datang " + namaPelanggan + "! silahkan pilih menu yang tertera!");
            System.out.println("1. Lihat Daftar Menu");
            System.out.println("2. Pesan Sekarang");
            System.out.println("3. Lihat atau Finalisasi Pesanan Anda");
            System.out.println("4. Lihat antrian dapur");
            System.out.println("\nCC. Keluar Dari Aplikasi\n");

            inputAwal = input.nextLine();

            if (inputAwal.equalsIgnoreCase("1")) {
                daftar.tampilkanDataAll();
            }

            else if (inputAwal.equalsIgnoreCase("2")) {
                while (true) {
                    System.out.print("Input Kode Menu (input B untuk kembali ke menu utama): ");
                    String kodeMenu = input.nextLine();

                    if (!kodeMenu.equalsIgnoreCase("b")) {
                        Menu menu = daftar.validasiMenu(kodeMenu);
                        if (menu == null) {
                            System.out.println("Kode menu tidak ditemukan!");
                            continue;
                        }

                        System.out.print("Input jumlah pesanan (input 0 atau S untuk membatalkan menu ini): ");
                        String kuantitas = input.nextLine();

                        pesanan.prosesPesan(menu, kuantitas);
                        sudahPesan = true;
                    } else {
                        break;
                    }
                }
            }
            else if (inputAwal.equalsIgnoreCase("3")) {
                if (sudahPesan == true) {
                    pesanan.tampilkanPesanan();

                    while (true) {
                        System.out.print("Ingin lanjut ke pembayaran? (y/n): ");
                        String lanjut = input.nextLine();

                        if (lanjut.equalsIgnoreCase("y")) {
                            int totalHarga = 0;

                            for (ItemPesanan item : pesanan.getPesanan()) {
                                Menu menu = item.getMenu();
                                int jumlah = item.getJumlah();
                                totalHarga += menu.getHarga() * jumlah;
                            }

                            System.out.println("\nPilih Metode Pembayaran:");
                            System.out.println("1. Tunai");
                            System.out.println("2. QRIS");
                            System.out.println("3. eMoney");
                            System.out.print("Masukkan pilihan: ");
                            String pilihan = input.nextLine();

                            IPembayaran bayar = null;
                            String metode = "";
                            double saldo = 0;

                            switch (pilihan) {
                                case "1":
                                    bayar = new PembayaranTunai();
                                    metode = "Tunai";
                                    break;
                                case "2":
                                    bayar = new PembayaranQRIS();
                                    metode = "QRIS";
                                    saldo = PembayaranQRIS.getSaldoQRIS("qris", input);
                                    input.nextLine();
                                    break;
                                case "3":
                                    bayar = new PembayaranEMoney();
                                    metode = "eMoney";
                                    saldo = PembayaranEMoney.getSaldoEMoney("emoney", input);
                                    input.nextLine();
                                    break;
                                default:
                                    System.out.println("Pilihan tidak valid.");
                                    continue;
                            }

                            // Hitung total setelah diskon dan biaya admin
                            double totalSetelahDiskon = bayar.prosesPembayaran(totalHarga);

                            // Cek apakah member dan memiliki kode berisi 'A' untuk bebas pajak
                            Membership currentMember = membership.getMember();

                            boolean bebasPajak = currentMember.getKode().contains("A");

                            double totalPajak = 0;

                            for (ItemPesanan item : pesanan.getPesanan()) {
                                Menu menu = item.getMenu();
                                int jumlah = item.getJumlah();
                                double harga = menu.getHarga();

                                if (bebasPajak) {
                                    continue; // bebas pajak
                                }

                                if (menu instanceof Minuman) {
                                    if (harga < 50) {
                                        // tidak kena pajak
                                    } else if (harga <= 55) {
                                        totalPajak += jumlah * harga * 0.08;
                                    } else {
                                        totalPajak += jumlah * harga * 0.11;
                                    }
                                } else {
                                    if (harga > 50) {
                                        totalPajak += jumlah * harga * 0.08;
                                    } else {
                                        totalPajak += jumlah * harga * 0.11;
                                    }
                                }
                            }

                            double totalAkhir = totalSetelahDiskon + totalPajak;

                            if (!bayar.cekSaldo(saldo, totalAkhir)) {
                                System.out.println("Saldo tidak mencukupi. Pembayaran dibatalkan.");
                                continue;
                            }

                            // Konversi dan cetak kuitansi
                            System.out.println("\nPilih mata uang untuk konversi:");
                            System.out.println("1. IDR (default)");
                            System.out.println("2. USD");
                            System.out.println("3. JPY");
                            System.out.println("4. MYR");
                            System.out.println("5. EUR");
                            System.out.print("Masukkan pilihan: ");
                            String kodeMataUang = input.nextLine();
                            String mataUang = switch (kodeMataUang) {
                                case "2" -> "USD";
                                case "3" -> "JPY";
                                case "4" -> "MYR";
                                case "5" -> "EUR";
                                default -> "IDR";
                            };

                            MataUang konversi = new MataUang();

                            Kuitansi.cetak(pesanan.getPesanan(), metode, mataUang, saldo, konversi);

                            // Jika IDR dan punya poin, gunakan poin untuk diskon
                            if (mataUang.equals("IDR") && currentMember.getPoin() > 0) {
                                int poin = currentMember.getPoin();
                                int diskonPoin = poin * 2; // 1 poin = 2 IDR
                                int poinTerpakai = (int) Math.ceil(totalAkhir / 2.0);
                                if (diskonPoin > totalAkhir) {
                                    totalAkhir -= diskonPoin;
                                    System.out.println("Diskon sebesar Rp " + diskonPoin + " telah digunakan dari " + poin + " poin.");
                                    int sisaPoint = poin - poinTerpakai;
                                    currentMember.setPoin(sisaPoint);
                                } else {
                                    System.out.println("Poin lebih kecil dari total harga. Poin tidak digunakan dalam pembayaran.");
                                }
                            } else if (!mataUang.equals("IDR")) {
                                System.out.println("Pembayaran bukan IDR, poin tidak dapat digunakan, tapi tetap disimpan.");
                            }

                            // KohiShop Part 2 : Membership : add belanja dan poin (jika memenuhi syarat) secara otomatis
                            int jumlahMenuDibeli = 0;
                            for (ItemPesanan item : pesanan.getPesanan()) {
                                jumlahMenuDibeli += item.getJumlah();
                            }
                            currentMember.addBelanjaAndPoin(jumlahMenuDibeli);

                            // KohiShop Part 2 : Membership : nyimpen riiwayat pemesanan current member ke database
                            currentMember.addRiwayatPesanan(new ArrayList<>(pesanan.getPesanan()));
                            pesanan.getPesanan().clear();

                            sudahPesan = false;
                            break;
                        } else if (lanjut.equalsIgnoreCase("n")) {
                            break;
                        } else {
                            System.out.println("Silahkan input (y) untuk lanjut, atau (n) untuk kemabali ke menu utama!");
                        }
                    }
                } else {
                    System.out.println("\nAnda belum membuat pesanan!");
                }
            } 
            // KohiShop Part 2 : Proses Pesanan Tim Dapur : pilihan user mengakses proses yang sedang berjalan
            else if(inputAwal.equalsIgnoreCase("4")){
                int totalProses = 0;

                for(Membership m : membership.getMemberDatabase())
                {
                    if(!m.getRiwayatPesanan().isEmpty())
                    {
                        totalProses++;
                    }
                }

                if(totalProses < 3)
                {
                    System.out.println("Belum ada proses yang berjalan di dapur!");
                }
                else
                {
                    dapur.prosesDapur(membership.getMemberDatabase());
                }
            }
            else if (inputAwal.equalsIgnoreCase("CC")) {
                tanyaNama(input, membership);
            }

            else {
                System.out.println("Input tidak valid, silahkan pilih salah satu menu yang tertera!");
            }
        }
    }

    // KohiShop Part 2 : Membership : method untuk menanyakan nama pelanggan
    // dipakek agar dalam satu sesi program bisa ada lebih dari 1 pelanggan
    public static void tanyaNama(Scanner input, MemberManagement database)
    {
        while (true) 
        {
            System.out.println("Selamat Datang di KohiSop");
            System.out.print("Halo pelanggan, siapa nama anda? (Input C untuk mematikan program)");
            
            String nama = input.nextLine();

            if(nama.equalsIgnoreCase("c"))
            {
                System.exit(1);
            }
            else
            {
                boolean terdaftar = false;
    
                for(Membership m : database.getMemberDatabase())
                {
                    if(m.getNama().equalsIgnoreCase(nama))
                    {
                        System.out.println("\nNama " + nama + " sudah terdaftar!");
                        database.setMember(m);
                        terdaftar = true;
                        break;
                    }
                }
    
                if(!terdaftar)
                {
                    if(nama.matches("[0-9]+"))
                    {
                        System.out.println("Error! Nama diawali angka.");
                        continue;
                    }

                    database.autoAddMember(nama);
                }
                break;
            }
        }
    }
}
