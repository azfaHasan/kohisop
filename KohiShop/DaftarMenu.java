import java.util.ArrayList;;

public class DaftarMenu 
{
    private ArrayList<Makanan> daftarMakanan = new ArrayList<>();
    private ArrayList<Minuman> daftarMinuman = new ArrayList<>();

    public DaftarMenu()
    {
        isikontenMenu();
    }

    // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Method untuk mengisi menu ke arratlist agar rapi
    void isikontenMenu()
    {   
        daftarMakanan.add(new Makanan("M1", "Petemania Pizza", 112));
        daftarMakanan.add(new Makanan("M2", "Mie Rebus Super Mario", 35));
        daftarMakanan.add(new Makanan("M3", "Ayam Baka Goreng Rebus Spesial", 72));
        daftarMakanan.add(new Makanan("M4", "Soto Kambing Iga Guling", 124));
        daftarMakanan.add(new Makanan("S1", "Singkong Bakar A La Carte", 37));
        daftarMakanan.add(new Makanan("S2", "Ubi Cilembu Bakar Arang", 58));
        daftarMakanan.add(new Makanan("S3", "Tempe Mendoan Kering", 18));
        daftarMakanan.add(new Makanan("S4", "Tahu Bakso Ekstra Telur", 28));

        daftarMinuman.add(new Minuman("A1", "Caffe Latte", 46));
        daftarMinuman.add(new Minuman("A2", "Cappuccino", 46));
        daftarMinuman.add(new Minuman("A3", "Kapal Api", 30));
        daftarMinuman.add(new Minuman("E1", "Caffe Americano", 37));
        daftarMinuman.add(new Minuman("E2", "Caffe Mocha", 55));
        daftarMinuman.add(new Minuman("E3", "Caramel Macchiato", 59));
        daftarMinuman.add(new Minuman("E4", "Asian Dolce Latte", 55));
        daftarMinuman.add(new Minuman("E5", "Double Shots Iced Shaken Espresso", 50));
        daftarMinuman.add(new Minuman("B1", "Freshly Brewed Coffee", 23));
        daftarMinuman.add(new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50));
        daftarMinuman.add(new Minuman("B3", "Cold Brew", 44));
    }

    // Kohishop Part 2 : Pemesanan Makanan dan Minuman : Method untuk menampilkan daftar Menu 
    public void tampilkanDataAll()
    {
        // Minuman
        System.out.println("+--------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-2s |\n", "Kode", "Menu Minuman", "Harga (Rp)");
        System.out.println("+--------------------------------------------------------+");
        for(Minuman minuman : daftarMinuman)
        {
            System.out.printf("| %-5s | %-33s | %-10.2f |\n", minuman.getKode(), minuman.getNama(), minuman.getHarga());
        }
        System.out.println("+--------------------------------------------------------+");
        
        // Makanan
        System.out.println("\n+--------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-2s |\n", "Kode", "Menu Makanan", "Harga (Rp)");
        System.out.println("+--------------------------------------------------------+");
        for(Makanan makanan : daftarMakanan)
        {
            System.out.printf("| %-5s | %-33s | %-10.2f |\n", makanan.getKode(), makanan.getNama(), makanan.getHarga());
        }
        System.out.println("+--------------------------------------------------------+");
        
    }
    
    // KohiShop Part 2 : Pemesanan Makanan dan Minuman : Method untuk validasi kode menu dari input pengguna. Mengambil data menu dari arraylist class ini
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
}
