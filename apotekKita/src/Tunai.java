public class Tunai extends MetodeBayar {

//constructor
public Tunai() { super("Tunai"); }

//overriding method bayar
public void bayar(float jumlah) { System.out.println("Bayar tunai: Rp." + String.format(java.util.Locale.US, "%,.0f", jumlah).replace(",", ".")); }
}
