// Class Pesanan buat saat di method main user milih menu tampilkan pesanan
// Class ini buat bikin logic pemesanan, nyimpen hasil pesanan, nampilin hasil pesanan
public class Pesanan 
{
    private ItemPesanan[] pesanan;
    private int jumlahItemTerdaftar;
    
    public Pesanan()
    {
        pesanan = new ItemPesanan[10];
        jumlahItemTerdaftar = 0;
    }
    
    public ItemPesanan[] getPesanan() { //pais//
        return this.pesanan; //pais//
    } //pais//

    public void prosesPesan(Menu menu, String inputPesan)
    {
        // Mulai dari variabel jumlah ini, digunakan untuk pengecekan kuantitas
        // Sesuai soal, jenis makanan atau minuman yang dipesan tidak bisa lebih dari 5 jenis unik
        int jumlahMakanan = 0;
        int jumlahMinuman = 0;

        for(int i = 0; i < jumlahItemTerdaftar; i++)
        {
            if(pesanan[i].getMenu() instanceof Makanan)
            {
                jumlahMakanan++;
            }
            else
            {
                jumlahMinuman++;
            }
        }

        if ((menu instanceof Makanan && jumlahMakanan >= 5) || (menu instanceof Minuman && jumlahMinuman >= 5)) {
            System.out.println("Pesanan untuk kategori " + (menu instanceof Makanan ? "Makanan" : "Minuman") + " sudah mencapai batas maksimal 5 jenis.");
            return;
        }

        // Mulai if di bawah ini untuk user jika ingi membatalkan pesanan
        // Misalkan udah input menu A1. Pas ditanyain jumlah pesanan sama program, jika inputnya S atau 0 maka pesanan untuk menu itu gajadi
        if(inputPesan.equalsIgnoreCase("S") || inputPesan == "0")
        {
            for(int i = 0; i < jumlahItemTerdaftar; i++)
            {
                if(pesanan[i].getMenu().getKode().equalsIgnoreCase(menu.getKode()))
                {
                    for(int j = i; j < jumlahItemTerdaftar; j++)
                    {
                        pesanan[j] = pesanan[j + 1];
                    }
                    pesanan[jumlahItemTerdaftar - 1] = null;
                    jumlahItemTerdaftar--;
                }
            }
        }

        // // Bagian di bawah ini sesuai soal, untuk bagian yang pesanan berulang
        // // Misalnya udah pesan A1 dengan jumlah 1. Kemudian pesan A1 lagi dengan jumlah 2, maka kuantitasnya jadi 3
        int jumlah = 0;
        try
        {
            jumlah = Integer.parseInt(inputPesan);
        }
        catch(NumberFormatException e)
        {
            System.out.println("Input tidak valid, masukkan 0 atau S untuk batal. Angka > 0 untuk jumlah pesanan");
            return;
        }

        for(int i = 0; i < jumlahItemTerdaftar; i++)
        {
            if(pesanan[i].getMenu().getKode().equalsIgnoreCase(menu.getKode()))
            {
                pesanan[i].setJumlah(jumlah);
                return;
            }
        }
        // // Masih nyambung atasnya bagian ini agar jumlah total menu (jika termasuk Minuman) tidak lebih dari 3
        if(menu instanceof Minuman && jumlahMinuman > 3)
        {
            System.out.println("Pemesanan Minuman" + menu.getNama() + " tidak bisa lebih dari 3");
            return;
        }
        else if(menu instanceof Makanan && jumlahMakanan > 2)
        {
            System.out.println("Pemesanan Makanan" + menu.getNama() + " tidak bisa lebih dari 2");
            return;
        }

        // Menambahkan item ke array pesanan, dengan index yang digunakan adalah jumlahItemTerdaftar saat ini sebagai lokasi index kosong
        pesanan[jumlahItemTerdaftar++] = new ItemPesanan(menu, jumlah);
    }

    //Method untuk menghitung pajak (zen)
    private int hitungPajak(Menu menu)
    {
        int harga = menu.getHarga();
        int pajak = 0;

        if(menu instanceof Minuman)
        {
            if(harga < 50) pajak = 0;
            else if(harga <= 55) pajak = (int)(harga * 0.08);
            else pajak = (int)(harga * 0.11);
        }
        else if(menu instanceof Makanan)
        {
            if(harga > 50) pajak = (int)(harga * 0.08);
            else pajak = (int)(harga * 0.11);
        }

        return pajak;
    }

    // Method untuk menampilkan HASIL PESANAN USER
    public void tampilkanPesan()
    {
        int totalHargaMakanan = 0;
        int totalHargaMinuman = 0;
        int totalPajakMinuman = 0; //
        int totalPajakMakanan = 0; //

        // Jika menu yang di loop dalam forEach loop termasuk minuman, maka dimasukkan di tabel minuman
        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-10s | %-10s | %-10s |\n", "Kode", "Menu Minuman", "Kuantitas", "Harga", "Pajak");
        System.out.println("+----------------------------------------------------------------------------------+");
        
        for(int i = 0; i < jumlahItemTerdaftar; i++)
        {
            if(pesanan[i].getMenu() instanceof Minuman)
            {
                int jumlah = pesanan[i].getJumlah();
                int subtotal = pesanan[i].getMenu().getHarga() * jumlah;
                int pajak = hitungPajak(pesanan[i].getMenu()) * jumlah;

                System.out.printf("| %-5s | %-33s | %-10d | %-10d | IDR %-6d |\n",
                    pesanan[i].getMenu().getKode(), pesanan[i].getMenu().getNama(), jumlah, subtotal, pajak);

                totalHargaMinuman += subtotal;
                totalPajakMinuman += pajak;
            }
        }
        
        // Jika menu yang di loop dalam forEach loop termasuk makanan, maka dimasukkan di tabel minuman
        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-10s | %-10s | %-10s |\n", "Kode", "Menu Makanan", "Kuantitas", "Harga", "Pajak");
        System.out.println("+----------------------------------------------------------------------------------+");
        
        for(int i = 0; i < jumlahItemTerdaftar; i++)
        {
            if(pesanan[i].getMenu() instanceof Makanan)
            {
                int jumlah = pesanan[i].getJumlah();
                int subtotal = pesanan[i].getMenu().getHarga() * jumlah;
                int pajak = hitungPajak(pesanan[i].getMenu()) * jumlah;

                System.out.printf("| %-5s | %-33s | %-10d | %-10d | IDR %-6d |\n",
                    pesanan[i].getMenu().getKode(), pesanan[i].getMenu().getNama(), jumlah, subtotal, pajak);

                totalHargaMinuman += subtotal;
                totalPajakMinuman += pajak;
            }
        }

        int totalHarga = totalHargaMakanan + totalHargaMinuman;
        int totalPajak = totalPajakMinuman + totalPajakMakanan;

        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-20s | %-18d |\n", "Total Harga", totalHarga);
        System.out.printf("| %-20s | %-18d |\n", "Total Pajak", totalPajak);
        System.out.printf("| %-20s | %-18d |\n", "Total Bayar + Pajak", totalHarga + totalPajak);
        System.out.println("+-------------------------------------------+");
    }
}
