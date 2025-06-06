//Faiz
public interface IPembayaran {
    double prosesPembayaran(double totalTagihan);

    boolean cekSaldo(double saldo, double totalTagihan);
}