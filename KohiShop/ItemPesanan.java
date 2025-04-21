// Alif
// Class Baru ini digunakan untuk mengganti teknik (key -> value) dari HashMap
// Untuk (key -> value) = (Menu(Makanan/Minuman) -> jumlah yang dipesan)
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
}
