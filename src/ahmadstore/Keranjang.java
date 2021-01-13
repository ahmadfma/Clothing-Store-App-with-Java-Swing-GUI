/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmadstore;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Ahmad Fathanah
 */
public class Keranjang {
    
    /* 
        nomor Jenis Barang :
            - Kemeja = 1
            - Kaos = 2
            - Celana = 3
            - Jaket = 4
            - jam tangan = 5
            - sepatu = 6
    */
    
    private static Object dataBelanjaan[][];
    public static File file;
    public static int noJenis;
    public static String namaBarang = "";
    public static String hargaSatuan;
    private static JLabel label;
    public static String usernameLogin = "";
    
    private static void getImg(String jenisBarang) throws IOException {           
        switch(jenisBarang) {
            case "Kemeja" :
                label = imgKemeja();
                break;
            case "Kaos":
                label = imgKaos();
                break;
            case "Jaket":
                label = imgJaket();
                break;
            case "Celana":
                label = imgCelana();
                break;
            case "Sepatu":
                label = imgSepatu();
                break;
            case "JamTangan":
                label = imgJamTangan();
                break;
            default:
                break;
        }          
    }
    
    public static JLabel imgKemeja() {
        
        JLabel lbl = new JLabel();
        
        switch(namaBarang) {
            case "Denim Disney Frozen":
                new Barang().setImageKemeja(1, lbl);
                break;
            case "Kemeja Disney Frozen":
                new Barang().setImageKemeja(2, lbl);
                break;
            case "Flanel Unisex":
                new Barang().setImageKemeja(3, lbl);
                break;
            case "Kemeja Katun Mustard":
                new Barang().setImageKemeja(4, lbl);
                break;
            case "Kemeja Mots":
                new Barang().setImageKemeja(5, lbl);
                break;
            default:
                break;
        }
        
        return lbl;
    }
    
    public static JLabel imgKaos() {
        
        JLabel lbl = new JLabel();
        
        switch(namaBarang) {
            case "Lengan Panjang Variasi Kancing":
                new Barang().setImageKaos(1, lbl);
                break;
            case "Kaos Halah Bacot":
                new Barang().setImageKaos(2, lbl);
                break;
            case "Kaos Peta Indonesia":
                new Barang().setImageKaos(3, lbl);
                break;
            case "Kaos Lawless - The Truth":
                new Barang().setImageKaos(4, lbl);
                break;
            case "Kaos Polos Lengan Panjang":
                new Barang().setImageKaos(5, lbl);
                break;
            case "Kaos Puma":
                new Barang().setImageKaos(6, lbl);
                break;
            case "Kaos Strip Hitam":
                new Barang().setImageKaos(7, lbl);
                break;
            default:
                break;
        }
        
        return lbl;
    }
    
    public static JLabel imgJaket() {
        
        JLabel lbl = new JLabel();
        
        switch(namaBarang) {
            case "Jaket Azalia":
                new Barang().setImageJaket(1, lbl);
                break;
            case "Jaket Bolak Balik Bankers":
                new Barang().setImageJaket(2, lbl);
                break;
            case "Jaket Motor WP":
                new Barang().setImageJaket(3, lbl);
                break;
            case "Hoddie Zipper Beludru":
                new Barang().setImageJaket(4, lbl);
                break;
            case "Bomber Deluxe Bapin":
                new Barang().setImageJaket(5, lbl);
                break;
            default:
                break;
        }
        
        return lbl;
    }
     
    public static JLabel imgCelana() {
        
        JLabel lbl = new JLabel();
        
        switch(namaBarang) {
            case "Chinos Jogger Pria":
                new Barang().setImageCelana(1, lbl);
                break;
            case "Highwaist Boyfriend Jeans Wanita":
                new Barang().setImageCelana(2, lbl);
                break;
            case "Jeans Cardinal Pria":
                new Barang().setImageCelana(3, lbl);
                break;
            case "Jilly Jeans Wanita":
                new Barang().setImageCelana(4, lbl);
                break;
            case "Jogger Pants Wanita":
                new Barang().setImageCelana(5, lbl);
                break;
            case "Jogger Inna Pants":
                new Barang().setImageCelana(6, lbl);
                break;
            case "Levis Pria":
                new Barang().setImageCelana(7, lbl);
                break;
            default:
                break;
        }
        
        return lbl;
    }
    
    public static JLabel imgSepatu() {
        JLabel lbl = new JLabel();
        
        switch(namaBarang) {
            case "Converse All Star Chuck Taylor":
                new Barang().setImageSepatu(1, lbl);
                break;
            case "Converse All Star":
                new Barang().setImageSepatu(2, lbl);
                break;
            case "Sepatu Import NDD 05 Original":
                new Barang().setImageSepatu(3, lbl);
                break;
            case "Sepatu NDD-14":
                new Barang().setImageSepatu(4, lbl);
                break;
            case "Sneakers Adidas Pria Wanita":
                new Barang().setImageSepatu(5, lbl);
                break;
            case "Sneakers ELFYDO":
                new Barang().setImageSepatu(6, lbl);
                break;
            case "Sneakers Leedoo MR112":
                new Barang().setImageSepatu(7, lbl);
                break;
            case "Sneakers Wanita RY12":
                new Barang().setImageSepatu(8, lbl);
                break;
            default:
                break;
        }
        
        return lbl;
    }
    
    public static JLabel imgJamTangan() {
        JLabel lbl = new JLabel();
        
        switch(namaBarang) {
            case "Bostanten Digital 2052":
                new Barang().setImageJamTangan(1, lbl);
                break;
            case "Casio MTP 1094E":
                new Barang().setImageJamTangan(2, lbl);
                break;
            case "Curren 8301":
                new Barang().setImageJamTangan(3, lbl);
                break;
            case "Curren 8329":
                new Barang().setImageJamTangan(4, lbl);
                break;
            case "Fossil Lux Luther":
                new Barang().setImageJamTangan(5, lbl);
                break;
            case "G-Shock GA700":
                new Barang().setImageJamTangan(6, lbl);
                break;
            case "Seiko Stainles Stell SNXS 77K1":
                new Barang().setImageJamTangan(7, lbl);
                break;
            default:
                break;
        }
        
        return lbl;
    }
    
    public static int getTotalBelanjaan() throws IOException {
        int totalBelanjaan = 0;
        
        BufferedReader readFile = new BufferedReader(new FileReader("Keranjang_"+Keranjang.usernameLogin+".txt"));
        String data = readFile.readLine();
        
        while(data != null) {
            totalBelanjaan++;
            data = readFile.readLine();
        }
              
        readFile.close();
        return totalBelanjaan;
    }
    
    public static Object[][] getBelanjaan () throws IOException {
        Scanner datascan;
        dataBelanjaan = new Object[getTotalBelanjaan()][10];
        int baris = 0;
        int colom = 0;
        int line = 0;
        
        BufferedReader readDatabase = new BufferedReader(new FileReader("Keranjang_"+Keranjang.usernameLogin+".txt"));
        String data = readDatabase.readLine();
        
        while (data != null) {
            datascan = new Scanner(data);
            datascan.useDelimiter("#");
            String jenisBarang = datascan.next();
            namaBarang = datascan.next();
            line++;
            
            datascan = new Scanner(data);
            datascan.useDelimiter("#");
           
            if(baris < dataBelanjaan.length) {
                
                while(datascan.hasNext()) {
                    if(colom == 0) {                       
                        dataBelanjaan[baris][colom] = line;
                    }else if (colom == 1)  {
                        dataBelanjaan[baris][colom] = false;
                    }
                    else if (colom == 2) {                       
                        label = new JLabel();
                        getImg(jenisBarang);
                        dataBelanjaan[baris][colom] = label;
                    }
                    else if(colom == 8 || colom == 9) {
                        String input = datascan.next();
                        Locale localId = new Locale("in", "ID");
                        NumberFormat formatter = NumberFormat.getCurrencyInstance(localId);
                        String strFormat;
                        strFormat = formatter.format(Integer.parseInt(input));
                        dataBelanjaan[baris][colom] = strFormat;
                    }                 
                    else {
                        String input = datascan.next();
                        dataBelanjaan[baris][colom] = input;                       
                    }                   
                    colom++;
                }               
            }
            
            colom = 0;
            baris++;
            data = readDatabase.readLine();
        }
        
        readDatabase.close();
              
        return dataBelanjaan;
    }
    
    public static int getTotalBayaran() throws IOException {
        Scanner datascan;
        int totalBayaran = 0;
        BufferedReader readFile = new BufferedReader(new FileReader("Keranjang_"+Keranjang.usernameLogin+".txt"));
        String data = readFile.readLine();
        
        if(data == null) {
            readFile.close();
            return 0;
        }
        
        while(data != null) {
            datascan = new Scanner(data);
            datascan.useDelimiter("#");
            datascan.next();datascan.next();datascan.next();datascan.next();datascan.next();datascan.next();
            String totalbayar = datascan.next();
            totalBayaran += Integer.parseInt(totalbayar);
            
            data = readFile.readLine();
        }
              
        readFile.close();
        return totalBayaran;
    }
    
    public static void masukkanKeKaranjang(String namaBarang, String ukuran, String warna, int jumlah, String harga) throws  IOException{
        String jenis = "";
        switch(noJenis) {
            case 1:
                jenis = "Kemeja";
                break;
            case 2:
                jenis = "Kaos";
                break;
            case 3:
                jenis = "Celana";
                break;
            case 4:
                jenis = "Jaket";
                break;
            case 5:
                jenis = "JamTangan";
                break;
            case 6:
                jenis = "Sepatu";
                break;
            default:
                break;
        }
        
        harga = harga.replace("Rp", "").replace(".", "").replace(",00", "");
        int hargaSat = Integer.parseInt(harga);
        
        BufferedWriter writeToFile = new BufferedWriter(new FileWriter("Keranjang_"+Keranjang.usernameLogin+".txt", true));
        if(jenis.equals("JamTangan")) {
            writeToFile.write(jenis+"#"+namaBarang+"#"+"-"+"#"+warna.toUpperCase()+"#"+jumlah+"#"+hargaSat+"#"+jumlah*hargaSat);
        } else {
            writeToFile.write(jenis+"#"+namaBarang+"#"+ukuran.toUpperCase()+"#"+warna.toUpperCase()+"#"+jumlah+"#"+hargaSat+"#"+jumlah*hargaSat);
        }       
        writeToFile.newLine();
        writeToFile.flush();
        writeToFile.close();
    }
    
    public static boolean hapusBelanjaan(int nomorBelanjaan)throws IOException{
        boolean isDelete = false;
        
        File database = new File("Keranjang_"+Keranjang.usernameLogin+".txt");
        File tempFile = new File("Temp_Keranjang.txt");
        
        BufferedReader readDatabase = new BufferedReader(new FileReader(database));
        BufferedWriter writeToTempFile = new BufferedWriter(new FileWriter(tempFile));
        
        String data = readDatabase.readLine();
        int checkPoint = 0;
        
        while (data != null) {
            checkPoint++;
            
            if(checkPoint == nomorBelanjaan) {
                isDelete = true;
            } else {
                writeToTempFile.write(data);
                writeToTempFile.newLine();
            }
                    
            data = readDatabase.readLine();
        }
        
        writeToTempFile.flush();
        
        readDatabase.close();
        writeToTempFile.close();
        
        database.delete();
        tempFile.renameTo(database);
        
        return isDelete;
    }
    
    public static void getFile() {
       
        switch(noJenis) {
            case 1:
                file = new File("Barang_Kemeja.txt");
                break;
            case 2:
                file = new File("Barang_Kaos.txt");
                break;
            case 3:
                file = new File("Barang_Celana.txt");
                break;
            case 4:
                file = new File("Barang_Jaket.txt");
                break;
            case 5:
                file = new File("Barang_JamTangan.txt");
                break;
            case 6:
                file = new File("Barang_Sepatu.txt");
                break;
            default:
                break;
        }
    }
      
}

