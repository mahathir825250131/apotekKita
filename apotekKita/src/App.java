import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {

//format harga biar lebih rapih
public static String formatHarga(float harga) {
return String.format(java.util.Locale.US, "%,.0f", harga).replace(",", ".");//misal dari 10000.0 jadi 10.000
}

//cari jumlah obat yg sama di keranjang
public static int cariJumlah(ArrayList<Obat> keranjang, String nama) {
int jumlah = 0;

for(Obat obat: keranjang) {
if(obat.getNama().equals(nama)) {
jumlah++;
}
}

return jumlah;
}

//cek apakah nama obat sudah diproses
public static boolean sudahDiproses(ArrayList<String> namaSudah, String nama) {
for(String namaObat: namaSudah) {
if(namaObat.equals(nama)) {
return true;
}
}

return false;
}

//cari nomor transaksi berikutnya
public static int getNomorTransaksi() {
int nomor = 1;

try {
File file = new File("transaksi.txt");

if(file.exists()) {
Scanner reader = new Scanner(file);

while(reader.hasNextLine()) {
String data = reader.nextLine();

if(data.startsWith("TRANSAKSI ")) {
String[] bagian = data.split(" ");

try {
int nomorLama = Integer.parseInt(bagian[1]);

if(nomorLama >= nomor) {
nomor = nomorLama + 1;
}
}
catch(NumberFormatException e) {
//lewati kalau nomor transaksi salah
}
}

}

reader.close();
}
}
catch(FileNotFoundException e) {
//kalau file belum ada, mulai dari 1
}

return nomor;
}

//buat BILL# random 4 angka yg belum pernah dipakai
public static String getBillNumber() {
Random random = new Random();

while(true) {
int angka = 1000 + random.nextInt(9000);
String bill = String.valueOf(angka);
boolean sudahAda = false;

try {
File file = new File("transaksi.txt");

if(file.exists()) {
Scanner reader = new Scanner(file);

while(reader.hasNextLine()) {
String data = reader.nextLine();

if(data.startsWith("BILL# ")) {
String billLama = data.substring(6).trim();//line ini untuk ambil BILL# lama dari file transaksi.txt

if(billLama.equals(bill)) {
sudahAda = true;
break;
}
}
}

reader.close();
}
}
catch(FileNotFoundException e) {
//kalau file belum ada, BILL# dianggap belum pernah dipakai
}

if(!sudahAda) {
return bill;
}
}
}

public static void main(String[] args) {
ArrayList<Obat> listObat = new ArrayList<Obat>();

//baca data obat dari file
try {
File myObj = new File("obat.txt");
Scanner myReader = new Scanner(myObj);

while(myReader.hasNextLine()) {
String data = myReader.nextLine();
String[] str = data.split(",");

//buat object ObatResep dari data file
if(str[0].equals("RESEP")) {
ObatResep obat = new ObatResep(
str[1], str[2], Float.parseFloat(str[3]),
Integer.parseInt(str[4]), str[5]
);
listObat.add(obat);
}

//buat object ObatBebas dari data file
else if(str[0].equals("BEBAS")) {
ObatBebas obat = new ObatBebas(
str[1], str[2], Float.parseFloat(str[3]),
Integer.parseInt(str[4]), str[5]
);
listObat.add(obat);
}
}

myReader.close();
}
catch(FileNotFoundException e) {
System.out.println("File obat tidak ditemukan.");
return;
}

Scanner input = new Scanner(System.in);

//buat object pembayaran
Pembayaran pembayaran = new Pembayaran(0, "", 0);//ini nanti akan diubah sesuai input user

//pilih obat sampai user input 0
while(true) {
System.out.println();
System.out.println("====================================================");
System.out.println("              CENTURY CENTRAL PARK");
System.out.println("                 DAFTAR OBAT");
System.out.println("====================================================");
System.out.println("+----+----------------------+------------+-------+");
System.out.println("| No | Nama Obat            | Harga      | Stok  |");
System.out.println("+----+----------------------+------------+-------+");

int no = 1;

for(Obat obat: listObat) {
String nama = obat.getNama();

if(nama.length() > 20) {
nama = nama.substring(0, 20);
}

System.out.printf("| %-2d | %-20s | Rp.%-7s | %-5d |%n",//contoh: | 1  | Paracetamol          | Rp.10.000  | 50    |
no, nama, formatHarga(obat.getHarga()), obat.getStok());

no++;
}

System.out.println("+----+----------------------+------------+-------+");
System.out.println("Note : Masukkan nomor obat untuk memesan.");
System.out.println("       Masukkan 0 jika sudah selesai.");
System.out.println("       Jumlah pesanan tidak boleh melebihi stok.");
System.out.println("====================================================");
System.out.print("Pilih nomor obat: ");

String pilihan = input.nextLine();

//0 utk selesai pilih obat
if(pilihan.equals("0")) {
break;
}

int nomor;

//ubah input nomor jadi int
try {
nomor = Integer.parseInt(pilihan);
}
catch(NumberFormatException e) {
System.out.println("Input harus berupa angka.");
continue;
}

//cek nomor obat
if(nomor < 1 || nomor > listObat.size()) {
System.out.println("Nomor obat tidak tersedia.");
continue;
}

Obat obatDipilih = listObat.get(nomor - 1);

//cek stok
if(obatDipilih.getStok() <= 0) {
System.out.println("Stok obat habis.");
continue;
}

System.out.print("Mau pesan berapa? ");
String inputJumlah = input.nextLine();

int jumlah;

//ubah jumlah pesanan jadi int
try {
jumlah = Integer.parseInt(inputJumlah);
}
catch(NumberFormatException e) {
System.out.println("Jumlah harus berupa angka.");
continue;
}

//cek jumlah pesanan
if(jumlah <= 0) {
System.out.println("Jumlah harus lebih dari 0.");
continue;
}

if(jumlah > obatDipilih.getStok()) {
System.out.println("Jumlah melebihi stok yang tersedia.");
continue;
}

//masukkan obat sesuai jumlah ke keranjang
for(int i = 0; i < jumlah; i++) {
pembayaran.tambahObat(obatDipilih);
}

//kurangi stok sesuai jumlah pesanan
obatDipilih.setStok(obatDipilih.getStok() - jumlah);

System.out.println(jumlah + " x " + obatDipilih.getNama() +
" berhasil ditambahkan ke keranjang.");
}

//cek keranjang
if(pembayaran.getListObat().size() == 0) {
System.out.println("Keranjang masih kosong.");
input.close();
return;
}

//pilih metode pembayaran
System.out.println();
System.out.println("====================================================");
System.out.println("                 METODE PEMBAYARAN");
System.out.println("====================================================");
System.out.println("  1. Tunai");
System.out.println("  2. QRIS");
System.out.println("----------------------------------------------------");
System.out.print("Pilih metode : ");

String pilihanMetode = input.nextLine();

//buat object sesuai pilihan
if(pilihanMetode.equals("1")) {
Tunai tunai = new Tunai();
pembayaran.setMetodeBayar(tunai);
}
else if(pilihanMetode.equals("2")) {
QRIS qris = new QRIS();
pembayaran.setMetodeBayar(qris);
}
else {
System.out.println("Pilihan metode tidak tersedia.");
input.close();
return;
}

//tampilin pembayaran
System.out.println();
pembayaran.cetakInfo();
pembayaran.bayar();

//cetak struk
String billNumber = getBillNumber();
LocalDateTime waktuCetak = LocalDateTime.now();
String printDate = waktuCetak.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
String printTime = waktuCetak.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

System.out.println();
System.out.println("====================================================");
System.out.println("                 Century Central Park");
System.out.println("                     Cashier Bill");
System.out.println("====================================================");
System.out.println("Century Central Park");
System.out.println("Jl. S. Parman Lantai LG");
System.out.println("Telp 56985086");
System.out.println();
System.out.println("BILL#      : " + billNumber);
System.out.println("Print Date : " + printDate);
System.out.println("Print Time : " + printTime);
System.out.println("Outlet     : Central Park");
System.out.println();
System.out.println("----------------------------------------------------");
System.out.printf("%-4s %-24s %5s %13s%n", "No", "Nama Obat", "Qty", "Subtotal");
System.out.println("----------------------------------------------------");

ArrayList<String> namaSudah = new ArrayList<String>();
int nomorItem = 1;

for(Obat obat: pembayaran.getListObat()) {
if(!sudahDiproses(namaSudah, obat.getNama())) {
int jumlah = cariJumlah(pembayaran.getListObat(), obat.getNama());
float subtotal = obat.getHarga() * jumlah;
String nama = obat.getNama();

if(nama.length() > 24) {
nama = nama.substring(0, 24);
}

System.out.printf("%-4d %-24s %5d %13s%n",//contoh: 1   Paracetamol              2     Rp.20.000
nomorItem, nama, jumlah, "Rp." + formatHarga(subtotal));

namaSudah.add(obat.getNama());
nomorItem++;
}
}

System.out.println("----------------------------------------------------");
System.out.printf("%-34s %13s%n", "TOTAL", "Rp." + formatHarga(pembayaran.getTotalHarga()));
System.out.printf("%-22s : %s%n", "Metode Pembayaran", pembayaran.getMetodeBayar());
System.out.println("----------------------------------------------------");
System.out.println("              May You Recover Soon !");
System.out.println("====================================================");

//simpan perubahan stok ke file
try {
FileWriter fw = new FileWriter("obat.txt");

for(Obat obat: listObat) {
if(obat instanceof ObatResep) {
ObatResep resep = (ObatResep) obat;

fw.write("RESEP," + resep.getKode() + "," + resep.getNama() + "," +
resep.getHarga() + "," + resep.getStok() + "," + resep.getNomorResep());
}
else if(obat instanceof ObatBebas) {
ObatBebas bebas = (ObatBebas) obat;

fw.write("BEBAS," + bebas.getKode() + "," + bebas.getNama() + "," +
bebas.getHarga() + "," + bebas.getStok() + "," + bebas.getKategori());
}

fw.write(System.lineSeparator());
}

fw.close();
}
catch(IOException e) {//klo misal obat.txt ada trouble
System.out.println("Data obat gagal disimpan.");
}

//simpan transaksi ke file
try {
FileWriter fw = new FileWriter("transaksi.txt", true);

int nomorTransaksi = getNomorTransaksi();

fw.write("====================================================" + System.lineSeparator());
fw.write("TRANSAKSI " + nomorTransaksi + System.lineSeparator());
fw.write("BILL# " + billNumber + System.lineSeparator());
fw.write("PrintDate=" + printDate + System.lineSeparator());
fw.write("PrintTime=" + printTime + System.lineSeparator());
fw.write("Outlet=Central Park" + System.lineSeparator());

ArrayList<String> namaTransaksi = new ArrayList<String>();

for(Obat obat: pembayaran.getListObat()) {
if(!sudahDiproses(namaTransaksi, obat.getNama())) {
int jumlah = cariJumlah(pembayaran.getListObat(), obat.getNama());
float subtotal = obat.getHarga() * jumlah;

fw.write(obat.getNama() + " " + jumlah + "x - Rp." +
formatHarga(subtotal) + System.lineSeparator());

namaTransaksi.add(obat.getNama());
}
}

fw.write("Jumlah obat=" + pembayaran.getListObat().size() + System.lineSeparator());
fw.write("Total=Rp." + formatHarga(pembayaran.getTotalHarga()) + System.lineSeparator());
fw.write("MetodeBayar=" + pembayaran.getMetodeBayar() + System.lineSeparator());
fw.write("====================================================" + System.lineSeparator());

fw.close();
}
catch(IOException e) {//misalnya file transaksi.txt tidak bisa dibuka untuk ditulis
System.out.println("Transaksi gagal disimpan.");
}

input.close();
}
}