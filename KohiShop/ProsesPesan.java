import java.util.ArrayList;;

public class ProsesPesan 
{
    private ArrayList<ItemPesanan> pesanan = new ArrayList<>();

    // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Method tempat terjadinya proses pesan
    public void prosesPesan(Menu menu, String inputPesan)
    {
        // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Default jumlah pesanan
        if(inputPesan == null || inputPesan.trim().isEmpty())
        {
            inputPesan = "1";
        }

        // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Input pembatalan pesanan
        if(inputPesan.equalsIgnoreCase("S") || inputPesan.equalsIgnoreCase("0"))
        {
            batalkanPesanan(menu);
            return;
        }
        
        // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Pembatasan jumlah makanan (2 Porsi) dan minuman (3 porsi)
        // Ini untuk menu yang sudah dipesan sebelumnya (penambahan porsi)
        int jumlah = 0;
        try
        {
            jumlah = Integer.parseInt(inputPesan);
        }
        catch(NumberFormatException e)
        {
            System.out.println("Input tidak valid, Masukkan jumlah pesanan dalam angka!");
            return;
        }
        
        if(jumlah < 0)
        {
            System.out.println("Input tidak valid, masukkan 0 atau S untuk batal. Angka > 0 untuk jumlah pesanan!");
            return;
        }
        
        for(ItemPesanan item : pesanan)
        {
            if(item.getMenu().equals(menu))
            {
                int total = item.getJumlah() + jumlah;
                
                if(menu instanceof Makanan && total > 2)
                {
                    System.out.println("Pemesanan Makanan" + menu.getNama() + " tidak bisa lebih dari 2");
                    return;
                }
                else if(menu instanceof Minuman && total > 3)
                {
                    System.out.println("Pemesanan Makanan" + menu.getNama() + " tidak bisa lebih dari 3");
                    return;
                }
                item.setJumlah(total);
                System.out.println("Pesanan " + menu.getNama() + " berhasil ditambah menjadi " + total + " porsi!");
                return;
            }
        }
        
        // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Pembatasan 5 makanan dan 5 minuman unik
        int jumlahMakananUnik = 0;
        int jumlahMinumanUnik = 0;
        
        for(ItemPesanan item : pesanan)
        {
            if(item.getMenu() instanceof Makanan)
            {
                jumlahMakananUnik++;
            }
            else if(item.getMenu() instanceof Minuman)
            {
                jumlahMinumanUnik++;
            }
        }
        
        if(menu instanceof Makanan && jumlahMakananUnik > 5)
        {
            System.out.println("Pesanan untuk kategori Makanan sudah mencapai batas maksimal 5 jenis.");
            return;
        }
        else if(menu instanceof Minuman && jumlahMinumanUnik > 5)
        {
            System.out.println("Pesanan untuk kategori Minuman sudah mencapai batas maksimal 5 jenis.");
            return;
        }

        // KohiShop Part 2 : Pemesanan Makanan dakun Minuman : Pembatasan jumlah makanan (2 Porsi) dan minuman (3 porsi)
        // Ini untuk menu yang belum dipesan sebelumnya (penambahan menu baru)
        if(menu instanceof Makanan && jumlah > 2)
        {
            System.out.println("Pemesanan Makanan" + menu.getNama() + " tidak bisa lebih dari 2");
            return;
        }
        else if(menu instanceof Minuman && jumlah > 3)
        {
            System.out.println("Pemesanan Makanan" + menu.getNama() + " tidak bisa lebih dari 3");
            return;
        }

        // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Penambahan hasil pesanan ke arraylist
        pesanan.add(new ItemPesanan(menu, jumlah));
        System.out.println("Pesanan " + menu.getNama() + " berhasil ditambahkan sebanyak " + jumlah);
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

    //KohiShop Part 2 : Pemesanan Makanan dan Minuman : Method untuk menampilkan hasil pesanan pengguna
    public void tampilkanPesanan()
    {
        double totalHargaMakanan = 0;
        double totalHargaMinuman = 0;
        double totalPajakMinuman = 0;
        double totalPajakMakanan = 0;

        // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Daftar makanan dan minuman dalam arrayList yang diurutkan dengan bubble sort
        ArrayList<ItemPesanan> daftarMinuman = new ArrayList<>();

        for(ItemPesanan item : pesanan)
        {
            if(item.getMenu() instanceof Minuman)
            {
                daftarMinuman.add(item);
            }
        }

        for(int i = 0; i < daftarMinuman.size() - 1; i++)
        {
            for(int j = 0; j < daftarMinuman.size() - 1 - i; j++)
            {
                double harga1 = daftarMinuman.get(j).getMenu().getHarga() * daftarMinuman.get(j).getJumlah();
                double harga2 = daftarMinuman.get(j + 1).getMenu().getHarga() * daftarMinuman.get(j + 1).getJumlah();
            
                if(harga1 < harga2)
                {
                    ItemPesanan temp = daftarMinuman.get(j);
                    daftarMinuman.set(j, daftarMinuman.get(j + 1));
                    daftarMinuman.set(j + 1, temp);
                }
            }
        }
        
        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-10s | %-10s | %-10s |\n", "Kode", "Menu Minuman", "Kuantitas", "Harga", "Pajak");
        System.out.println("+----------------------------------------------------------------------------------+");
        
        for(ItemPesanan item : daftarMinuman)
        {
            int jumlah = item.getJumlah();
            double subtotal = item.getMenu().getHarga() * jumlah;
            double pajak = hitungPajak(item.getMenu()) * jumlah;
            
            System.out.printf("| %-5s | %-33s | %-10d | IDR %-6.2f | IDR %-6.2f |\n",
            item.getMenu().getKode(), item.getMenu().getNama(), jumlah, subtotal, pajak);
            
            totalHargaMinuman += subtotal;
            totalPajakMinuman += pajak;
        }
        
        ArrayList<ItemPesanan> daftarMakanan = new ArrayList<>();

        for(ItemPesanan item : pesanan)
        {
            if(item.getMenu() instanceof Makanan)
            {
                daftarMakanan.add(item);
            }
        }

        for(int i = 0; i < daftarMakanan.size() - 1; i++)
        {
            for(int j = 0; j < daftarMakanan.size() - 1 - i; j++)
            {
                double harga1 = daftarMakanan.get(j).getMenu().getHarga() * daftarMakanan.get(j).getJumlah();
                double harga2 = daftarMakanan.get(j + 1).getMenu().getHarga() * daftarMakanan.get(j + 1).getJumlah();
            
                if(harga1 < harga2)
                {
                    ItemPesanan temp = daftarMakanan.get(j);
                    daftarMakanan.set(j, daftarMakanan.get(j + 1));
                    daftarMakanan.set(j + 1, temp);
                }
            }
        }
        
        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-10s | %-10s | %-10s |\n", "Kode", "Menu Makanan", "Kuantitas", "Harga", "Pajak");
        System.out.println("+----------------------------------------------------------------------------------+");
        
        for(ItemPesanan item : daftarMakanan)
        {
            int jumlah = item.getJumlah();
            double subtotal = item.getMenu().getHarga() * jumlah;
            double pajak = hitungPajak(item.getMenu()) * jumlah;

            System.out.printf("| %-5s | %-33s | %-10d | IDR %-6.2f | IDR %-6.2f |\n",
            item.getMenu().getKode(), item.getMenu().getNama(), jumlah, subtotal, pajak);

            totalHargaMakanan += subtotal;
            totalPajakMakanan += pajak;
        }

        double totalHarga = totalHargaMakanan + totalHargaMinuman;
        double totalPajak = totalPajakMinuman + totalPajakMakanan;

        System.out.println("+----------------------------------------------------------------------------------+");
        System.out.printf("| %-20s | %-18.2f |\n", "Total Harga", totalHarga);
        System.out.printf("| %-20s | %-18.2f |\n", "Total Pajak", totalPajak);
        System.out.printf("| %-20s | %-18.2f |\n", "Total Bayar + Pajak", totalHarga + totalPajak);
        System.out.println("+-------------------------------------------+");
    }

    // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Method untuk mengapus menu di dalam arraylist
    public void batalkanPesanan(Menu menu)
    {
        for(int i = 0; i < pesanan.size(); i++)
        {
            if(pesanan.get(i).getMenu().equals(menu))
            {
                pesanan.remove(i);
                System.out.println("Pesanan " + menu.getNama() + " berhasil dibatalkan.");
                return;
            }
        }
    }

    public ArrayList<ItemPesanan> getPesanan() { //pais
    return this.pesanan; //pais
    } //pais
}