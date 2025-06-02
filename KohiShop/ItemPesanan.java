// KoshiShop Part 2 : Pemesanan Makanan dan Minuman : Class ItemPesanan untuk mempermudah dalam mengolah menu dan jumlahnya
public class ItemPesanan 
{
    private Menu menu;
    private int jumlah;

    ItemPesanan(Menu menu, int jumlah)
    {
        this.menu = menu;
        this.jumlah = jumlah;
    }  
    
    public Menu getMenu()
    {
        return this.menu;
    }

    public int getJumlah()
    {
        return this.jumlah;
    }

    public void setJumlah(int jumlah)
    {
        this.jumlah = jumlah;
    }

    public void addJumlah(int jumlah)
    {
        this.jumlah += jumlah;
    }
}
