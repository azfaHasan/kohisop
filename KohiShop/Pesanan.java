class Pesanan {
    String namaPelanggan;
    String menu;
    String status;

    public Pesanan(String namaPelanggan, String menu) {
        this.namaPelanggan = namaPelanggan;
        this.menu = menu;
        this.status = "Diterima";
    }

    public void proses() {
        status = "Diproses";
    }

    public void selesai() {
        status = "Selesai";
    }

    public void tampilkan() {
        System.out.println("Pelanggan: " + namaPelanggan + " | Menu: " + menu + " | Status: " + status);
    }
}

