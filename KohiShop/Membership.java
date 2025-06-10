
// KohiShop Part 2 : Membership : Blueprint member KohiShop
import java.util.Random;

public class Membership {
    Random rand = new Random();

    private String kode;
    private String nama;
    private int poin;
    private int jumlahBelanja;

    public Membership(String nama) {
        this.nama = nama;
        this.poin = 0;
        this.jumlahBelanja = 0;
        this.kode = kodeGenerator();
    }

    public String getNama() {
        return this.nama;
    }

    public int getPoin() {
        return this.poin;
    }

    public void resetPoin() {
        this.poin = 0;
    }

    public int getJumlahBelanja() {
        return this.jumlahBelanja;
    }

    public String getKode() {
        return this.kode;
    }

    public void addBelanjaAndPoin(int jumlahMenuDibeli) {
        System.out.println("Jumlah poin " + getNama() + " sekarang: " + poin);

        this.jumlahBelanja += jumlahMenuDibeli;

        int poinNew = jumlahMenuDibeli / 10;

        if (poinNew > 0) {
            if (kode.contains("A")) {
                poinNew *= 2;
                System.out.println("Kode membermu ada huruf A, kamu dapat " + poinNew + " poin!");
            }

            this.poin += poinNew;

            System.out.println("Poin member " + getNama() + " bertambah! sekarang jadi " + poin + " poin!");
        } else {
            System.out.println("Jumlah belanjamu masih kurang buat dapat poin.");
        }
    }

    private String kodeGenerator() {
        String alfanumerik = "ABCDEF0123456789";
        StringBuilder hasil = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            hasil.append(alfanumerik.charAt(rand.nextInt(alfanumerik.length())));
        }

        return hasil.toString();
    }

    // Faiz
    public void setPoin(int poin) {
        this.poin = poin;
    }
}
