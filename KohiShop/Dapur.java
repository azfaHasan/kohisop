// KohiShop Part 2 : Proses Pesanan Dapur : Class dapur untuk semua proses di dapur
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Dapur 
{
    PriorityQueue<ItemPesanan> antrianMakanan = new PriorityQueue<>((a, b) -> Double.compare(b.getMenu().getHarga(), a.getMenu().getHarga()));
    Stack<ItemPesanan> antrianMinuman = new Stack<ItemPesanan>();

    public void prosesDapur(ArrayList<Membership> members)
    {
        antrianMakanan.clear();
        antrianMinuman.clear();

        int jumlahProsesDapur = 0;

        for(Membership m: members)
        {
            if(jumlahProsesDapur >= 3)
            {
                break;
            }

            for(ItemPesanan item : m.getRiwayatPesanan())
            {
                if(item.getMenu() instanceof Makanan)
                {
                    antrianMakanan.add(item);
                }
                else if(item.getMenu() instanceof Minuman)
                {
                    antrianMinuman.push(item);
                }
            }
            jumlahProsesDapur++;
        }

        // KohiShop Part 2 : Proses Pesanan Dapur : print di tabel makanan jika menu termasuk minuman
        System.out.println("Antrian Minuman");
        System.out.println("+---------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-10s | %-10s |\n", "Kode", "Menu Minuman", "Kuantitas", "Harga");
        System.out.println("+---------------------------------------------------------------------+");
        while (!antrianMinuman.isEmpty()) 
        {
            ItemPesanan minuman = antrianMinuman.pop();
            System.out.printf("| %-5s | %-33s | %-10d | IDR %-6.2f |\n", minuman.getMenu().getKode(), minuman.getMenu().getNama(), minuman.getJumlah(), minuman.getMenu().getHarga());        
        }
        System.out.println("+---------------------------------------------------------------------+");
        
        
        // KohiShop Part 2 : Proses Pesanan Dapur : print di tabel makanan jika menu termasuk makanan
        System.out.println("\nAntrian Makanan");
        System.out.println("+---------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-33s | %-10s | %-10s |\n", "Kode", "Menu Makanan", "Kuantitas", "Harga");
        System.out.println("+---------------------------------------------------------------------+");
        while (!antrianMakanan.isEmpty()) 
        {
            ItemPesanan makanan = antrianMakanan.poll();
            System.out.printf("| %-5s | %-33s | %-10d | IDR %-6.2f |\n", makanan.getMenu().getKode(), makanan.getMenu().getNama(), makanan.getJumlah(), makanan.getMenu().getHarga());        
        }
        System.out.println("+---------------------------------------------------------------------+");


    }
}
