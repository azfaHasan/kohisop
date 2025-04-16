public class DaftarMenu 
{
    // private ArrayList<Makanan> daftarMakanan;
    // private ArrayList<Minuman> daftarMinuman;

    private Makanan[] daftarMakanan;
    private Minuman[] daftarMinuman;

    public DaftarMenu()
    {
        daftarMakanan = new Makanan[6];
        daftarMinuman = new Minuman[6];
        kontenMenu();
    }

    // Ini method buat mengisi arrayList daftarMakanan/Minuman
    // Sebenarnya bisa dijadikan satu di constructor, tapi nanti jadi kurang enak dibaca
    // Method arrayList yang dipakek itu .add untuk memasukkan data ke arrayList
    void kontenMenu()
    {
        // daftarMakanan.add(new Makanan("M1", "Petemania Pizza", 112));
        // daftarMakanan.add(new Makanan("M2", "Mie Rebus Super Mario", 35));
        // daftarMakanan.add(new Makanan("M3", "Ayam Baka Goreng Rebus Spesial", 72));
        // daftarMakanan.add(new Makanan("S1", "Singkong Bakar A La Carte", 37));
        // daftarMakanan.add(new Makanan("S2", "Ubi Cilembu Bakar Arang", 58));
        // daftarMakanan.add(new Makanan("S3", "Tempe Mendoan Kering", 18));

        // daftarMinuman.add(new Minuman("A1", "Caffe Latte", 46));
        // daftarMinuman.add(new Minuman("A2", "Cappuccino", 46));
        // daftarMinuman.add(new Minuman("A3", "Kapal Api", 30));
        // daftarMinuman.add(new Minuman("B1", "Freshly Brewed Coffee", 23));
        // daftarMinuman.add(new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50));
        // daftarMinuman.add(new Minuman("B3", "Cold Brew", 44));
        
        daftarMakanan[0] = (new Makanan("M1", "Petemania Pizza", 112));
        daftarMakanan[1] = (new Makanan("M2", "Mie Rebus Super Mario", 35));
        daftarMakanan[2] = (new Makanan("M3", "Ayam Baka Goreng Rebus Spesial", 72));
        daftarMakanan[3] = (new Makanan("S1", "Singkong Bakar A La Carte", 37));
        daftarMakanan[4] = (new Makanan("S2", "Ubi Cilembu Bakar Arang", 58));
        daftarMakanan[5] = (new Makanan("S3", "Tempe Mendoan Kering", 18));

        daftarMinuman[0] = (new Minuman("A1", "Caffe Latte", 46));
        daftarMinuman[1] = (new Minuman("A2", "Cappuccino", 46));
        daftarMinuman[2] = (new Minuman("A3", "Kapal Api", 30));
        daftarMinuman[3] = (new Minuman("B1", "Freshly Brewed Coffee", 23));
        daftarMinuman[4] = (new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50));
        daftarMinuman[5] = (new Minuman("B3", "Cold Brew", 44));
    }

    // Method ini dipakek di method main untuk validasi hasil inputan user, ada/tidak kode makanan minuman yang diinput
    public Menu validasiMenu(String input)
    {
        for(Makanan makanan : daftarMakanan)
        {
            if(makanan.getKode().equalsIgnoreCase(input))
            {
                return makanan;
            }
        }

        for(Minuman minuman : daftarMinuman)
        {
            if(minuman.getKode().equalsIgnoreCase(input))
            {
                return minuman;
            }
        }

        return null;
    }

    // Method untuk nampilin semua menu dalam arrayList yang udah diisi sebelumnya
    public void tampilkanDataAll()
    {
        // Minuman
        System.out.println("+--------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-2s |\n", "Kode", "Menu Minuman", "Harga (Rp)");
        System.out.println("+--------------------------------------------------------+");
        for(Minuman minuman : daftarMinuman)
        {
            System.out.printf("| %-5s | %-33s | %-10d |\n", minuman.getKode(), minuman.getNama(), minuman.getHarga());
        }
        System.out.println("+--------------------------------------------------------+");

        // Makanan
        System.out.println("\n+--------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-2s |\n", "Kode", "Menu Makanan", "Harga (Rp)");
        System.out.println("+--------------------------------------------------------+");
        for(Makanan makanan : daftarMakanan)
        {
            System.out.printf("| %-5s | %-33s | %-10d |\n", makanan.getKode(), makanan.getNama(), makanan.getHarga());
        }
        System.out.println("+--------------------------------------------------------+");

    }
}
