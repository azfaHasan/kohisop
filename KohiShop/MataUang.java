public class MataUang implements KonversiMataUang {
  @Override
  public double konversi(double totalAkhir, String mataUang) {
      switch (mataUang.toUpperCase()) {
          case "USD":
              return totalAkhir / 15.0;
          case "JPY":
              return totalAkhir * 10.0;
          case "MYR":
              return totalAkhir / 4.0;
          case "EUR":
              return totalAkhir / 14.0;
          default: // IDR
              return totalAkhir;
      }
  }
}
