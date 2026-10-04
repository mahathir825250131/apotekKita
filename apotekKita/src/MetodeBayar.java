public class MetodeBayar {
private String namaMetode;

//constructor
public MetodeBayar(String namaMetode) { this.namaMetode = namaMetode; }
public MetodeBayar() { namaMetode = ""; }

//setter dan getter
public void setNamaMetode(String namaMetode) { this.namaMetode = namaMetode; }
public String getNamaMetode() { return namaMetode; }

//method pembayaran
public void bayar(float jumlah) { System.out.println("Pembayaran sebesar Rp" + jumlah); }
}
