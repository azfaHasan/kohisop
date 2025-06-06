// KohiShop Part 2 : Membership : Blueprint member KohiShop
import java.util.Random;

public class Membership 
{
    Random rand = new Random();

    private String kode;
    private String nama;
    private int poin;
    private int jumlahBelanja;

    public Membership(String nama)
    {
        this.nama = nama;
        this.poin = 0;
        this.jumlahBelanja = 0;
        this.kode = kodeGenerator();
    }

    public String getNama()
    {
        return this.nama;
    }

    public int getPoin()
    {
        return this.poin;
    }

    public int getJumlahBelanja()
    {
        return this.jumlahBelanja;
    }

    public String getKode()
    {
        return this.kode;
    }

    public void addBelanjaAndPoin(int jumlahMenuDibeli)
    {
        this.jumlahBelanja += jumlahMenuDibeli;

        int poinNew = jumlahMenuDibeli / 10;

        if(poinNew > 0)
        {
            if(kode.contains("A"))
            {
                poinNew *= 2;
                System.out.println("\nKode membermu ada huruf A! poin yang didapatkan saat ini jadi " + poin);
            }

            this.poin += poinNew;
            
            System.out.println("\nPoin member" + getNama() + " bertambah menjadi " + poin + " poin!");
        }
        else
        {
            System.out.println("\nJumlah belanjamu masih kurang buat dapat poin.");
        }
    }

    private String kodeGenerator()
    {
        String alfanumerik = "ABCDEF0123456789";
        StringBuilder hasil = new StringBuilder();

        for(int i = 0; i < 6; i++)
        {
            hasil.append(alfanumerik.charAt(rand.nextInt(alfanumerik.length())));
        }

        return hasil.toString();
    }
}

// Faiz 
public void gunakanPoin(int poinYangDigunakan) {
    this.poin -= poinYangDigunakan;
}

// Fai
public void setPoin(int poin) {
    this.poin = poin;
}
