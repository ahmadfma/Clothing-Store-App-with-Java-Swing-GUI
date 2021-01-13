/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmadstore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Ahmad Fathanah
 */
public class Barang {
    
    private static JLabel label;
    
    /*
    Kategori : Kemeja, Kaos, Celana, Jaket, Jam tangan, Sepatu
    isi tabel : No | image | nama barang | Warna tersedia | ukuran tersedia | harga satuan
    format : namaBarang#UkuranTersedia#WarnaTersedia#HargaSatuan
    */
    
    public static void checkDatabase_Barang() throws IOException{
        File kemeja = new File("Barang_Kemeja.txt");
        File kaos = new File("Barang_Kaos.txt");
        File celana = new File("Barang_Celana.txt");
        File jaket = new File("Barang_Jaket.txt");
        File jamTangan = new File("Barang_JamTangan.txt");
        File sepatu = new File("Barang_Sepatu.txt");
        
        if(!kemeja.exists()) {
            kemeja.createNewFile();
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(kemeja, true));
            writeToFile.write("Denim Disney Frozen#S,M,L,XL#Biru#300000");
            writeToFile.newLine();
            writeToFile.write("Kemeja Disney Frozen#S,M,L#Putih#200000");
            writeToFile.newLine();
            writeToFile.write("Flanel Unisex#S,M,L,XL#Hitam#250000");
            writeToFile.newLine();
            writeToFile.write("Kemeja Katun Mustard#S,M,L#Mustard#150000");
            writeToFile.newLine();
            writeToFile.write("Kemeja Mots#S,M,L#Abu-abu#120000");
            writeToFile.newLine();
            writeToFile.flush();
            writeToFile.close();
        }
        if(!kaos.exists()) {
            kaos.createNewFile();
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(kaos, true));
            writeToFile.write("Lengan Panjang Variasi Kancing#S,M,L,XL#Hijau#95000");
            writeToFile.newLine();
            writeToFile.write("Kaos Halah Bacot#S,M,L#Kuning#55000");
            writeToFile.newLine();
            writeToFile.write("Kaos Peta Indonesia#S,M,L,Xl#Hitam#45000");
            writeToFile.newLine();
            writeToFile.write("Kaos Lawless - The Truth#S,M,L,Xl#Hitam#65000");
            writeToFile.newLine();
            writeToFile.write("Kaos Polos Lengan Panjang#S,M,L,XL#hitam,kuning,merah,putih#50000");
            writeToFile.newLine();
            writeToFile.write("Kaos Puma#S,M,L,Xl#Biru,Merah,Hitam#75000");
            writeToFile.newLine();
            writeToFile.write("Kaos Strip Hitam#S,M,L#Putih#25000");
            writeToFile.newLine();
            writeToFile.flush();
            writeToFile.close();
            
        }
        if(!celana.exists()) {
            celana.createNewFile();
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(celana, true));
            writeToFile.write("Chinos Jogger Pria#11,12,13,14,15#Hitam,Abu-abu,Mocca,Cream#145000");
            writeToFile.newLine();
            writeToFile.write("Highwaist Boyfriend Jeans Wanita#11,12,13,14,15#Biru#255000");
            writeToFile.newLine();
            writeToFile.write("Jeans Cardinal Pria#11,12,13,14,15#Hitam#195000");
            writeToFile.newLine();
            writeToFile.write("Jilly Jeans Wanita#11,12,13,14,15#Hitam,Biru,Putih#165000");
            writeToFile.newLine();
            writeToFile.write("Jogger Pants Wanita#11,12,13,14,15#Cokelat#179000");
            writeToFile.newLine();
            writeToFile.write("Jogger Inna Pants#11,12,13,14,15#Cream,Cokelat#179000");
            writeToFile.newLine();
            writeToFile.write("Levis Pria#11,12,13,14,15#Hitam#179000");
            writeToFile.newLine();
            writeToFile.flush();
            writeToFile.close();
        }
        if(!jaket.exists()) {
            jaket.createNewFile();
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(jaket, true));
            writeToFile.write("Jaket Azalia#S,M,L,XL#Abu-abu#155000");
            writeToFile.newLine();
            writeToFile.write("Jaket Bolak Balik Bankers#S,M,L#Hitam#255000");
            writeToFile.newLine();
            writeToFile.write("Jaket Motor WP#S,M,L,Xl#Hitam#195000");
            writeToFile.newLine();
            writeToFile.write("Hoddie Zipper Beludru#S,M,L,Xl#Putih#165000");
            writeToFile.newLine();
            writeToFile.write("Bomber Deluxe Bapin#S,M,L,XL#Cream#179000");
            writeToFile.newLine();
            writeToFile.flush();
            writeToFile.close();
        }
        if(!jamTangan.exists()) {
            jamTangan.createNewFile();
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(jamTangan, true));
            writeToFile.write("Bostanten Digital 2052#-#Hitam#185000");
            writeToFile.newLine();
            writeToFile.write("Casio MTP 1094E#-#Putih#1185000");
            writeToFile.newLine();
            writeToFile.write("Curren 8301#-#Cokelat#195000");
            writeToFile.newLine();
            writeToFile.write("Curren 8329#-#Biru#165000");
            writeToFile.newLine();
            writeToFile.write("Fossil Lux Luther#-#silver#1350000");
            writeToFile.newLine();
            writeToFile.write("G-Shock GA700#-#Hitam#1245000");
            writeToFile.newLine();
            writeToFile.write("Seiko Stainles Stell SNXS 77K1#-#silver#1450000");
            writeToFile.newLine();
            writeToFile.flush();
            writeToFile.close();
        }
        if(!sepatu.exists()) {
            sepatu.createNewFile();
            BufferedWriter writeToFile = new BufferedWriter(new FileWriter(sepatu, true));
            writeToFile.write("Converse All Star Chuck Taylor#39,40,41,42,43#Hitam#185000");
            writeToFile.newLine();
            writeToFile.write("Converse All Star#39,40,41,42,43#Putih#185000");
            writeToFile.newLine();
            writeToFile.write("Sepatu Import NDD 05 Original#39,40,41,42,43#Hitam,Putih#195000");
            writeToFile.newLine();
            writeToFile.write("Sepatu NDD-14#39,40,41,42,43#Cream#165000");
            writeToFile.newLine();
            writeToFile.write("Sneakers Adidas Pria Wanita#39,40,41,42,43#Hitam#110000");
            writeToFile.newLine();
            writeToFile.write("Sneakers ELFYDO#39,40,41,42,43#Hitam,Putih,Orange#245000");
            writeToFile.newLine();
            writeToFile.write("Sneakers Leedoo MR112#39,40,41,42,43#Hitam#130000");
            writeToFile.newLine();
            writeToFile.write("Sneakers Wanita RY12#39,40,41,42,43#Pink#170000");
            writeToFile.newLine();
            writeToFile.flush();
            writeToFile.close();
        }      
    }
       
    //KEMEJA   
    public void setImageKemeja(int index, JLabel lbl) {
        
        switch (index) {
            case 1 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kemeja/denimDisneyFrozen.jpg")));
                break;
            case 2 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kemeja/disneyFrozen.jpg")));
                break;
            case 3 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kemeja/flanelUninsex.jpg")));
                break;
            case 4 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kemeja/kemejaKatunMustard.jpg")));
                break;
            case 5 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kemeja/mots.jpg")));
                break;
            default:
                break;
        }
        
       
    }
       
   //KAOS   
    public void setImageKaos(int index, JLabel lbl) {
        
        switch (index) {
            case 1 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kaos/LpanjangVariasiKancing.jpg")));
                break;
            case 2 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kaos/halahBacot.jpg")));
                break;
            case 3 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kaos/kaosLogoIndonesia.jpg")));
                break;
            case 4 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kaos/lawlessTheTruth.jpg")));
                break;
            case 5 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kaos/polosLenganPanjang.jpg")));
                break;
            case 6:
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kaos/puma.jpg")));
                break;
            case 7:
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/kaos/striphitamputih.jpg")));
                break;
            default:
                break;
        }
        
       
    }
            
    //JAKET
    public void setImageJaket(int index, JLabel lbl) {
        
        switch (index) {
            case 1 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jaketAzalia.jpg")));
                break;
            case 2 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jaketBolakBalikBankers.jpg")));
                break;
            case 3 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jaketMotorWP.jpg")));
                break;
            case 4 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/hoddieZipperBeludru.jpg")));
                break;
            case 5 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/bomberDeluxeBapin.jpg")));
                break;
            default:
                break;
        }
        
       
    }

    //Celana
    public void setImageCelana(int index, JLabel lbl) {
        
        switch (index) {
            case 1 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/celana/chinosJoggerPria.jpg")));
                break;
            case 2 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/celana/highwaistBoyfriendJeansWanita.jpg")));
                break;
            case 3 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/celana/jeansCardinalPria.jpg")));
                break;
            case 4 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/celana/jillyJeansWanita.jpg")));
                break;
            case 5 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/celana/jogerPantsWanita.jpg")));
                break;
            case 6 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/celana/joggerInnaPants.jpg")));
                break;
            case 7 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/celana/levisPria.jpg")));
                break;
            case 8 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/celana/skinnyJeansP&B.jpg")));
                break;
            default:
                break;
        }
        
       
    }
    
    //Sepatu
    public void setImageSepatu(int index, JLabel lbl) {
        switch (index) {
            case 1 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/sepatu/chuckTaylor.jpg")));
                break;
            case 2 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/sepatu/converseAllStar.jpg")));
                break;
            case 3 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/sepatu/sepatuImporNDD05Ori.jpg")));
                break;
            case 4 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/sepatu/sepatuNDD-14.jpg")));
                break;
            case 5 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/sepatu/sneakersAdidasPriaWanita.jpg")));
                break;
            case 6 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/sepatu/sneakersElfydo.jpg")));
                break;
            case 7 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/sepatu/sneakersLeedooMR112.jpg")));
                break;
            case 8 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/sepatu/sneakersWanitaRY12.jpg")));
                break;
            default:
                break;
        }
    }
    
    //Jam Tangan
    public void setImageJamTangan(int index, JLabel lbl) {
        switch (index) {
            case 1 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jamTangan/bostantenDigital2052.jpg")));
                break;
            case 2 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jamTangan/casioMTP1094E.jpg")));
                break;
            case 3 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jamTangan/curren8301.jpg")));
                break;
            case 4 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jamTangan/curren8329.jpg")));
                break;
            case 5 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jamTangan/fossilLuxLuther.jpg")));
                break;
            case 6 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jamTangan/gshockGA700.jpg")));
                break;
            case 7 :
                lbl.setIcon(new ImageIcon(getClass().getResource("/img/barang/jamTangan/seikoStainlesStellSNXS77K1.jpg")));
                break;            
            default:
                break;
        }
    }
         
    private static int getJumlahBarangTersedia(String jenisBarang) throws IOException {
        int jumlahBarang = 0;
        BufferedReader readFile = null;
        
        switch (jenisBarang) {
            case "Kaos":
                readFile = new BufferedReader(new FileReader("Barang_Kaos.txt"));
                break;
            case "Kemeja":
                readFile = new BufferedReader(new FileReader("Barang_Kemeja.txt"));
                break;
            case "Jaket":
                readFile = new BufferedReader(new FileReader("Barang_Jaket.txt"));
                break;
            case "Celana":
                readFile = new BufferedReader(new FileReader("Barang_Celana.txt"));
                break;
            case "Sepatu":
                readFile = new BufferedReader(new FileReader("Barang_Sepatu.txt"));
                break;
            case "JamTangan":
                readFile = new BufferedReader(new FileReader("Barang_JamTangan.txt"));
                break;
            default:
                break;
        }
        
        String data = readFile.readLine();
        
        while (data != null) {
            jumlahBarang++;
            data = readFile.readLine();
        }
        
        return jumlahBarang;
    }
    
    public static Object[][] getBarangTersedia(String jenisBarang) throws IOException {
        Scanner datascan;
        Object[][] barang = new Object[getJumlahBarangTersedia(jenisBarang)][6];
        checkDatabase_Barang();
        BufferedReader readFile = null;
        switch (jenisBarang) {
            case "Kaos":
                readFile = new BufferedReader(new FileReader("Barang_Kaos.txt"));
                break;
            case "Kemeja":
                readFile = new BufferedReader(new FileReader("Barang_Kemeja.txt"));
                break;
            case "Jaket":
                readFile = new BufferedReader(new FileReader("Barang_Jaket.txt"));
                break;
            case "Celana":
                readFile = new BufferedReader(new FileReader("Barang_Celana.txt"));
                break;
            case "Sepatu":
                readFile = new BufferedReader(new FileReader("Barang_Sepatu.txt"));
                break;
            case "JamTangan":
                readFile = new BufferedReader(new FileReader("Barang_JamTangan.txt"));
                break;
            default:
                break;
        }
        String data = readFile.readLine();
        
        int line = 0;
        int baris = 0;
        int colom = 0;
              
        while (data != null) {
            datascan = new Scanner(data);
            datascan.useDelimiter("#");
            line++;
            
            if(baris < barang.length) {
                while(datascan.hasNext()) {
                    if(colom == 0) {
                        barang[baris][colom] = line;                        
                    } else if(colom == 1) {
                        label = new JLabel();
                        switch (jenisBarang) {
                            case "Kaos":
                                new Barang().setImageKaos(line, label);
                                break;
                            case "Kemeja":
                                new Barang().setImageKemeja(line, label);
                                break;
                            case "Jaket":
                                new Barang().setImageJaket(line, label);
                                break;
                            case "Celana":
                                new Barang().setImageCelana(line, label);
                                break;
                            case "Sepatu":
                                new Barang().setImageSepatu(line, label);
                                break;
                            case "JamTangan":
                                new Barang().setImageJamTangan(line, label);
                                break;
                            default:
                                break;
                        }                      
                        barang[baris][colom] = label;
                    } else if (colom == 5)  {
                        String input = datascan.next();
                        Locale localId = new Locale("in", "ID");
                        NumberFormat formatter = NumberFormat.getCurrencyInstance(localId);
                        String strFormat;
                        strFormat = formatter.format(Integer.parseInt(input));
                        barang[baris][colom] = strFormat;
                    } else {
                        String input = datascan.next();
                        barang[baris][colom] = input;
                    }
                    colom++;
                }
            }
            
            colom = 0;
            baris++;
            data = readFile.readLine();
        }
        
        return barang;
    }
    
    public DefaultComboBoxModel getUkuranBarang(int selectedRow) throws IOException {
        Keranjang.getFile();
        DefaultComboBoxModel mdl = new DefaultComboBoxModel();
        File file = Keranjang.file;
        Scanner scan;
        int checkPoint = 0;
        BufferedReader readFile = new BufferedReader(new FileReader(file));
        String data = readFile.readLine();
        
        while(data != null) {
            
            if(checkPoint == selectedRow) {
                scan = new Scanner(data);
                scan.useDelimiter("#");
                scan.next();
                String ukuran = scan.next();
                scan = new Scanner(ukuran);
                scan.useDelimiter(",");
                
                while(scan.hasNext()) {
                    mdl.addElement(scan.next());
                }
                
            }
            
            checkPoint++;
            data = readFile.readLine();
        }
        
        
        return mdl;
    }
    
    public DefaultComboBoxModel getWarnaBarang(int selectedRow) throws IOException {
        Keranjang.getFile();
        DefaultComboBoxModel mdl = new DefaultComboBoxModel();
        File file = Keranjang.file;
        Scanner scan;
        int checkPoint = 0;
        BufferedReader readFile = new BufferedReader(new FileReader(file));
        String data = readFile.readLine();
        
        while(data != null) {
            
            if(checkPoint == selectedRow) {
                scan = new Scanner(data);
                scan.useDelimiter("#");
                scan.next();
                scan.next();
                String warna = scan.next();
                scan = new Scanner(warna);
                scan.useDelimiter(",");
                
                while(scan.hasNext()) {
                    mdl.addElement(scan.next());
                }
                
            }
            
            checkPoint++;
            data = readFile.readLine();
        }
        
        
        return mdl;
    }
    
    
    
}