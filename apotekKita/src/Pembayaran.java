import java.util.ArrayList;

public class Pembayaran implements Cetak {
private float totalHarga;
private MetodeBayar metodeBayar;
private ArrayList<Obat> listObat;

//constructor sesuai diagram
public Pembayaran(float totalHarga, String namaMetode, float nilaiTambahan) {
this.totalHarga = totalHarga;
metodeBayar = null;
listObat = new ArrayList<Obat>();
}

public Pembayaran() {
totalHarga = 0;
metodeBayar = null;
listObat = new ArrayList<Obat>();
}

//setter dan getter
public void setTotalHarga(float totalHarga) {
this.totalHarga = totalHarga;
}

public float getTotalHarga() {
return totalHarga;
}

public void setMetodeBayar(String namaMetode) {
if(metodeBayar != null) {
metodeBayar.setNamaMetode(namaMetode);
}
}

//setter utk object metode bayar
public void setMetodeBayar(MetodeBayar metodeBayar) {
this.metodeBayar = metodeBayar;
}

public String getMetodeBayar() {
if(metodeBayar == null) {
return "-";
}
return metodeBayar.getNamaMetode();
}

//tambah obat ke list
public void tambahObat(Obat obat) {
listObat.add(obat);
totalHarga = totalHarga + obat.getHarga();
}

public ArrayList<Obat> getListObat() {
return listObat;
}

//panggil fungsi bayar
public void bayar() {
if(metodeBayar != null) {
metodeBayar.bayar(totalHarga);
}
}

//cetak info pembayaran
public void cetakInfo() {
System.out.println("========================================");
System.out.println("          RINGKASAN PEMBAYARAN");
System.out.println("========================================");
System.out.println("Metode bayar : " + getMetodeBayar());
System.out.println("Total harga  : Rp." + String.format(java.util.Locale.US, "%,.0f", totalHarga).replace(",", "."));
}

//cetak struk
public void cetakStruk() {
System.out.println();
System.out.println("========================================");
System.out.println("             STRUK APOTEK");
System.out.println("========================================");

for(int i = 0; i < listObat.size(); i++) {
Obat obat = listObat.get(i);
System.out.println((i + 1) + ". " + obat.getNama() +
" - Rp" + obat.getHarga());
}

System.out.println("----------------------------------------");
System.out.println("Total  : Rp" + totalHarga);
System.out.println("Bayar  : " + getMetodeBayar());
System.out.println("========================================");
}
}
