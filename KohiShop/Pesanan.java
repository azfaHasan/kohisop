import java.util.HashMap;;

// Class Pesanan buat saat di method main user milih menu tampilkan pesanan
// Class ini buat bikin logic pemesanan, nyimpen hasil pesanan, nampilin hasil pesanan
// Hash Map dipakek biar Menu sama Kuantitasnya gampang dioperasiin
public class Pesanan 
{
    private HashMap<Menu, Integer> pesanan;
    
    public Pesanan()
    {
        pesanan = new HashMap<>();
    }
    
    public HashMap<Menu, Integer> getPesanan() { //pais//
        return this.pesanan; //pais//
    } //pais//

    public void prosesPesan(Menu menu, String inputPesan)
    {
        // Mulai dari jumlah ini untuk pengecekan kuantitas
        // Sesuai soal, jenis makanan atau minuman yang dipesan tidak bisa lebih dari 5 jenis unik
        int jumlahMakanan = 0;
        int jumlahMinuman = 0;

        for(Menu m : pesanan.keySet())
        {
            if(m instanceof Makanan)
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
            if(pesanan.containsKey(menu))
            {
                pesanan.remove(menu);
                System.out.println("Pesanan " + menu.getNama() + " dibatalkan");
                return;
            }
            else
            {
                System.out.println("Tidak ada pembatalan pesanan");
                return;
            }
        }

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

        // Bagian di bawah ini sesuai soal, untuk bagian yang pesanan berulang
        // Misalnya udah pesan A1 dengan jumlah 1. Kemudian pesan A1 lagi dengan jumlah 2, maka kuantitasnya jadi 3
        int jumlahSebelumnya = pesanan.getOrDefault(menu, 0);
        int jumlahTotal = jumlahSebelumnya + jumlah;

        // Masih nyambung atasnya bagian ini agar jumlah total menu (jika termasuk Minuman) tidak lebih dari 3
        if(menu instanceof Minuman && jumlahTotal > 3)
        {
            System.out.println("Pemesanan " + menu.getNama() + " tidak bisa lebih dari 3");
            return;
        }
        // Masih nyambung atasnya bagian ini agar jumlah total menu (jika termasuk Makanan) tidak lebih dari 2
        else if(menu instanceof Makanan && jumlahTotal > 2)
        {
            System.out.println("Pemesanan " + menu.getNama() + " tidak bisa lebih dari 2");
            return;
        }

        // method .put HashMap dipakek untuk menambahkan key-> value (menu, kuantitasnya) ke HashMap
        pesanan.put(menu, jumlahTotal);
        System.out.println(menu.getNama() + " berhasil ditambahkan sebanyak " + jumlahTotal + " porsi");
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
        
        for(Menu menu : pesanan.keySet())
        {
            if(menu instanceof Minuman)
            {
                int jumlah = pesanan.get(menu);
                int subtotal = menu.getHarga() * jumlah;
                int pajak = hitungPajak(menu) * jumlah;

                System.out.printf("| %-5s | %-33s | %-10d | %-10d | IDR %-6d |\n",
                    menu.getKode(), menu.getNama(), jumlah, subtotal, pajak);

                totalHargaMinuman += subtotal;
                totalPajakMinuman += pajak;
            }
        }
        
        // Jika menu yang di loop dalam forEach loop termasuk minuman, maka dimasukkan di tabel minuman
        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-10s | %-10s | %-10s |\n", "Kode", "Menu Makanan", "Kuantitas", "Harga", "Pajak");
        System.out.println("+----------------------------------------------------------------------------------+");
        
        for(Menu menu : pesanan.keySet())
        {
            if(menu instanceof Makanan)
            {
                int jumlah = pesanan.get(menu);
                int subtotal = menu.getHarga() * jumlah;
                int pajak = hitungPajak(menu) * jumlah;

                System.out.printf("| %-5s | %-33s | %-10d | %-10d | IDR %-6d |\n",
                    menu.getKode(), menu.getNama(), jumlah, subtotal, pajak);

                totalHargaMakanan += subtotal;
                totalPajakMakanan += pajak;
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
