public class ObatResep extends Obat {
private String nomorResep;

//constructor
public ObatResep(String kode, String nama, float harga, int stok, String nomorResep) {
super(kode, nama, harga);
setStok(stok);
this.nomorResep = nomorResep;
}

public ObatResep() {
super("", "", 0);
nomorResep = "";
}

//setter dan getter
public void setNomorResep(String nomorResep) { this.nomorResep = nomorResep; }
public String getNomorResep() { return nomorResep; }

//overriding method dari parent
public String deskripsiObat() { return "Obat Resep - No. Resep: " + nomorResep; }
}
