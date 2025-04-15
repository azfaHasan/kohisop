import java.util.Scanner;;

public class PesanApp 
{
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        DaftarMenu daftar = new DaftarMenu();
        Pesanan pesanan = new Pesanan();

        System.out.println("Selamat Datang, silahkan pilih menu yang tertera!");
       
        String inputAwal = "";
        boolean sudahPesan = false;

        while (true) 
        {
            System.out.println("1. Lihat Daftar Menu");
            System.out.println("2. Pesan Sekarang"); 
            System.out.println("3. Lihat Pesanan Anda"); 
            System.out.println("\nCC. Batalkan Program");

            inputAwal = input.nextLine();
            
            if(inputAwal.equalsIgnoreCase("1"))
            {
                daftar.tampilkanDataAll();
            }
            
            else if(inputAwal.equalsIgnoreCase("2"))
            {
                while(true)
                {
                    String kodeMenu;
                    System.out.print("Input Kode Menu (input 0 untuk kembali ke menu utama): ");
                    kodeMenu = input.nextLine();

                    if(!kodeMenu.equalsIgnoreCase("0"))
                    {
                        Menu menu = daftar.validasiMenu(kodeMenu);
                        if(menu == null)
                        {
                            System.out.println("Kode menu tidak ditemukan!");
                            continue;
                        }
    
                        String kuantitas;
                        System.out.print("Input jumlah pesanan (input 0 atau S untuk membatalkan menu ini): ");
                        kuantitas = input.nextLine();
                        Integer.parseInt(kuantitas);
    
                        pesanan.prosesPesan(menu, kuantitas);
                        sudahPesan = true;
                    }
                    else
                    {
                        break;
                    }
                }
            }

            else if(inputAwal.equalsIgnoreCase("3")) {
                if(sudahPesan) {
                    pesanan.tampilkanPesan();
            
                    while(true)
                    {
                        System.out.print("Ingin lanjut ke pembayaran? (y/n): ");
                        String lanjut = input.nextLine();
                
                        if(lanjut.equalsIgnoreCase("y")) {
                            int totalHarga = 0;
                            for (Menu menu : pesanan.getPesanan().keySet()) {
                                totalHarga += menu.getHarga() * pesanan.getPesanan().get(menu);
                            }
                
                            System.out.println("\nPilih Metode Pembayaran:");
                            System.out.println("1. Tunai");
                            System.out.println("2. QRIS");
                            System.out.println("3. eMoney");
                            System.out.print("Masukkan pilihan: ");
                            String pilihan = input.nextLine();
                
                            Pembayaran bayar = null;
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
                                    saldo = PembayaranQRIS.getSaldoJikaPerlu("qris", input);
                                    input.nextLine(); // Buang newline
                                    break;
                                case "3":
                                    bayar = new PembayaranEMoney();
                                    metode = "eMoney";
                                    saldo = PembayaranEMoney.getSaldoJikaPerlu("emoney", input);
                                    input.nextLine(); // Buang newline
                                    break;
                                default:
                                    System.out.println("Pilihan tidak valid.");
                                    continue;
                            }
                
                            // Hitung total setelah diskon dan biaya admin
                            double totalSetelahDiskon = bayar.prosesPembayaran(totalHarga);
                
                            // Pajak harus ditambahkan
                            double totalPajak = 0;
                            for (Menu menu : pesanan.getPesanan().keySet()) {
                                int jumlah = pesanan.getPesanan().get(menu);
                                if (menu instanceof Minuman) {
                                    totalPajak += jumlah * (menu.getHarga() >= 50 ? (menu.getHarga() <= 55 ? menu.getHarga() * 0.08 : menu.getHarga() * 0.11) : 0);
                                } else {
                                    totalPajak += jumlah * (menu.getHarga() > 50 ? menu.getHarga() * 0.08 : menu.getHarga() * 0.11);
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
                            break;
                        }
                        else if(lanjut.equalsIgnoreCase("n"))
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Silahkan input (y) untuk lanjut, atau (n) untuk kemabali ke menu utama!");
                        }
                    }
                }
                else 
                {
                    System.out.println("Anda belum membuat pesanan!");
                }
            }
            else if(inputAwal.equalsIgnoreCase("CC"))
            {
                break;
            }

            else
            {
                System.out.println("Input tidak valid, silahkan pilih salah satu menu yang tertera!");
            }
        }
    }
}