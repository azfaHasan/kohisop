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

    public int getJumlahItemTerdaftar()
    {
        return this.jumlahItemTerdaftar;
    }

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
        if(inputPesan.equalsIgnoreCase("S") || inputPesan.equalsIgnoreCase("0"))
        {
            for(int i = 0; i < jumlahItemTerdaftar; i++)
            {
                if(pesanan[i].getMenu().getKode().equalsIgnoreCase(menu.getKode()))
                {
                    for(int j = i; j < jumlahItemTerdaftar; j++)
                    {
                        pesanan[j] = pesanan[j + 1];
                    }
                    pesanan[--jumlahItemTerdaftar] = null;
                    System.out.println("Pesanan " + menu.getNama() + " berhasil dibatalkan.");
                    return;
                }
            }
        }

        // // Bagian di bawah ini sesuai soal, untuk bagian yang pesanan berulang
        // // Misalnya udah pesan A1 dengan jumlah 1. Kemudian pesan A1 lagi dengan jumlah 2, maka kuantitasnya jadi 3
        int jumlahNew = 0;
        if(inputPesan == null || inputPesan.trim().equals(""))
        {
            jumlahNew = 1;
        }
        else
        {
            try
            {
                jumlahNew = Integer.parseInt(inputPesan.trim());
                if(jumlahNew <= 0)
                {
                    System.out.println("Jumlah pesanan tidak bisa 0");
                    return;
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("Input tidak valid, masukkan 0 atau S untuk batal. Angka > 0 untuk jumlah pesanan");
                return;
            }
        }

        // Loop dan If Else ini digunakan saat user ingin menambahkan jumlah pesanan yang sudah dipesan sebelumnya
        for(int i = 0; i < jumlahItemTerdaftar; i++)
        {
            if(pesanan[i].getMenu().getKode().equalsIgnoreCase(menu.getKode()))
            {
                int jumlahBefore = pesanan[i].getJumlah();
                int jumlahAfter = jumlahBefore + jumlahNew;

                if(menu instanceof Minuman && jumlahAfter > 3)
                {
                    System.out.println("Pemesanan Minuman" + menu.getNama() + " tidak bisa lebih dari 3");
                    return;                
                }
                else if(menu instanceof Makanan && jumlahAfter > 2)
                {
                    System.out.println("Pemesanan Makanan" + menu.getNama() + " tidak bisa lebih dari 2");
                    return;
                }

                pesanan[i].setJumlah(jumlahAfter);
                System.out.println("Jumlah " + menu.getNama() + " berhasil diperbarui menjadi " + jumlahAfter);
                return;
            }
        }

        // If Else ini digunakan pas user mesan Menu baru, bukan menambahkan jumlah pesanan
        if(menu instanceof Minuman && jumlahNew > 3)
        {
            System.out.println("Pemesanan Minuman " + menu.getNama() + " tidak bisa lebih dari 3");
            return;
        }
        // // Masih nyambung atasnya bagian ini agar jumlah total menu (jika termasuk Makanan) tidak lebih dari 2
        else if(menu instanceof Makanan && jumlahNew > 2)
        {
            System.out.println("Pemesanan Makanan " + menu.getNama() + " tidak bisa lebih dari 2");
            return;
        }

        // Menambahkan item ke array pesanan, dengan index yang digunakan adalah jumlahItemTerdaftar saat ini sebagai lokasi index kosong
        pesanan[jumlahItemTerdaftar++] = new ItemPesanan(menu, jumlahNew);
        System.out.println("Pesanan " + menu.getNama() + " berhasil ditambahkan sebanyak " + jumlahNew);
    }

    //Method untuk menghitung pajak (zen)
    private double hitungPajak(Menu menu)
    {
        double harga = menu.getHarga();
        double pajak = 0;

        if(menu instanceof Minuman)
        {
            if(harga < 50) pajak = 0;
            else if(harga <= 55) pajak = (harga * 0.08);
            else pajak = (harga * 0.11);
        }
        else if(menu instanceof Makanan)
        {
            if(harga > 50) pajak = (harga * 0.08);
            else pajak = (harga * 0.11);
        }

        return pajak;
    }

    // Method untuk menampilkan HASIL PESANAN USER
    public void tampilkanPesanan()
    {
        double totalHargaMakanan = 0;
        double totalHargaMinuman = 0;
        double totalPajakMinuman = 0; //
        double totalPajakMakanan = 0; //

        // Jika menu yang di loop dalam forEach loop termasuk minuman, maka dimasukkan di tabel minuman
        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-10s | %-10s | %-10s |\n", "Kode", "Menu Minuman", "Kuantitas", "Harga", "Pajak");
        System.out.println("+----------------------------------------------------------------------------------+");
        
        for(int i = 0; i < jumlahItemTerdaftar; i++)
        {
            if(pesanan[i].getMenu() instanceof Minuman)
            {
                int jumlah = pesanan[i].getJumlah();
                double subtotal = pesanan[i].getMenu().getHarga() * jumlah;
                double pajak = hitungPajak(pesanan[i].getMenu()) * jumlah;

                System.out.printf("| %-5s | %-33s | %-10d | %-10.2f | IDR %-6.2f |\n",
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
                double subtotal = pesanan[i].getMenu().getHarga() * jumlah;
                double pajak = hitungPajak(pesanan[i].getMenu()) * jumlah;

                System.out.printf("| %-5s | %-33s | %-10d | %-10.2f | IDR %-6.2f |\n",
                    pesanan[i].getMenu().getKode(), pesanan[i].getMenu().getNama(), jumlah, subtotal, pajak);

                totalHargaMinuman += subtotal;
                totalPajakMinuman += pajak;
            }
        }

        double totalHarga = totalHargaMakanan + totalHargaMinuman;
        double totalPajak = totalPajakMinuman + totalPajakMakanan;

        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-20s | %-18.2f |\n", "Total Harga", totalHarga);
        System.out.printf("| %-20s | %-18.2f |\n", "Total Pajak", totalPajak);
        System.out.printf("| %-20s | %-18.2f |\n", "Total Bayar + Pajak", totalHarga + totalPajak);
        System.out.println("+-------------------------------------------+");
    }
}
