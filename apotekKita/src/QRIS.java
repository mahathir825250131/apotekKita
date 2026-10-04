public class QRIS extends MetodeBayar {

//constructor
public QRIS() { super("QRIS"); }//super untuk memanggil constructor dari class induk (MetodeBayar) dengan parameter "QRIS"

//overriding method bayar
public void bayar(float jumlah) { System.out.println("Bayar QRIS: Rp." + String.format(java.util.Locale.US, "%,.0f", jumlah).replace(",", ".")); }
}
