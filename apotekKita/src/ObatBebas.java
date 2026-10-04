public class ObatBebas extends Obat {
private String kategori;

//constructor
public ObatBebas(String kode, String nama, float harga, int stok, String kategori) {
super(kode, nama, harga);
setStok(stok);
this.kategori = kategori;
}

public ObatBebas() {
super("", "", 0);
kategori = "";
}

//setter dan getter
public void setKategori(String kategori) { this.kategori = kategori; }
public String getKategori() { return kategori; }

//overriding method dari parent
public String deskripsiObat() { return "Obat Bebas - Kategori: " + kategori; }
}
