class Pesanan {
    String namaPelanggan;
    String menu;
    String status;

    public Pesanan(String namaPelanggan, String menu) {
        this.namaPelanggan = namaPelanggan;
        this.menu = menu;
        this.status = "Diterima";
    }

    public void proses() {
        status = "Diproses";
    }

    public void selesai() {
        status = "Selesai";
    }

    public void tampilkan() {
        System.out.println("Pelanggan: " + namaPelanggan + " | Menu: " + menu + " | Status: " + status);
    }
}

public class ProsesPesananDapur {
    public static void main(String[] args) {
        Queue<Pesanan> antrianPesanan = new LinkedList<>();
        Scanner input = new Scanner(System.in);
        int pilihan;

        do {
            System.out.println("\n=== Sistem Proses Pesanan Tim Dapur ===");
            System.out.println("1. Tambah Pesanan");
            System.out.println("2. Proses Pesanan");
            System.out.println("3. Tampilkan Semua Pesanan");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.print("Nama Pelanggan: ");
                    String nama = input.nextLine();
                    System.out.print("Menu yang dipesan: ");
                    String menu = input.nextLine();
                    Pesanan pesananBaru = new Pesanan(nama, menu);
                    antrianPesanan.add(pesananBaru);
                    System.out.println("Pesanan ditambahkan!");
                    break;
                case 2:
                    if (!antrianPesanan.isEmpty()) {
                        Pesanan pesananDiproses = antrianPesanan.poll();
                        pesananDiproses.proses();
                        pesananDiproses.selesai();
                        System.out.println("Pesanan selesai diproses:");
                        pesananDiproses.tampilkan();
                    } else {
                        System.out.println("Tidak ada pesanan dalam antrian.");
                    }
                    break;
                case 3:
                    if (antrianPesanan.isEmpty()) {
                        System.out.println("Antrian pesanan kosong.");
                    } else {
                        System.out.println("Daftar Pesanan Dalam Antrian:");
                        for (Pesanan p : antrianPesanan) {
                            p.tampilkan();
                        }
                    }
                    break;
                case 0:
                    System.out.println("Keluar dari sistem.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 0);
        input.close();
    }
}
