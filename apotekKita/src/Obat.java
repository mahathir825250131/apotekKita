public abstract class Obat {
private String kode;
private String nama;
private float harga;
private int stok;

//constructor
public Obat(String kode, String nama, float harga) {
this.kode = kode;
this.nama = nama;
this.harga = harga;
this.stok = 0;
}

//setter dan getter
public void setKode(String kode) { this.kode = kode; }
public String getKode() { return kode; }
public void setNama(String nama) { this.nama = nama; }
public String getNama() { return nama; }
public void setHarga(float harga) { this.harga = harga; }
public float getHarga() { return harga; }
public void setStok(int stok) { this.stok = stok; }
public int getStok() { return stok; }

//method utk deskripsi obat
public String deskripsiObat() { return nama; }
}
