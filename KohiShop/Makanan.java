// Alif
public class Makanan extends Menu
{
    public Makanan(String kode, String nama, int harga) 
    {
        super(kode, nama, harga);
    }

    @Override
    public void tampilkanData()
    {
        System.out.printf("| %-2s | %-33s | %-2d |\n", getKode(), getNama(), getHarga());
    }
}
