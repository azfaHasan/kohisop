// Alif
public abstract class Menu 
{
    protected String nama;
    protected String kode;
    protected int harga;

    public Menu(String kode, String nama, int harga)
    {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }

    // Setiap menu (makanan/minuman) harus punya method untuk menampilkan datanya sendiri
    abstract void tampilkanData();

    // Method getter untuk setiap atribut Abstract Menu
    public String getKode()
    {
        return this.kode;
    }

    public String getNama()
    {
        return this.nama;
    }

    public int getHarga()
    {
        return this.harga;
    }
}
