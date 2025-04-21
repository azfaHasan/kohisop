// Alif
public class Makanan extends Menu
{
    public Makanan(String kode, String nama, double harga) 
    {
        super(kode, nama, harga);
    }

    @Override
    public void tampilkanData()
    {
        System.out.printf("| %-2s | %-33s | %-2.2f |\n", getKode(), getNama(), getHarga());
    }
}
