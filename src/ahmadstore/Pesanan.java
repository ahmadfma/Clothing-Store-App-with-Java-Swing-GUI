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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Ahmad Fathanah
 */
public class Pesanan {
    
    
    public static void masukkanKePesanan(Object[][] arr, String metodeBayar, String opsiKirim, String totalBayar) throws IOException{
        File database = new File("Pesanan_" + Account.userLogin + ".txt");
        BufferedWriter writeToFile = new BufferedWriter(new FileWriter(database, true));
        
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < 2; j++) {
                if(j == 1) {
                    writeToFile.write(""+arr[i][j]);
                    writeToFile.newLine();
                } 
            }
        }
        
        writeToFile.write("***#" + metodeBayar + "#" + opsiKirim + "#" + totalBayar);
        writeToFile.newLine();
        writeToFile.flush();
        
        writeToFile.close();
        
    }
    
    private static int getTotalPesanan() throws IOException {
        BufferedReader readFile = new BufferedReader(new FileReader("Pesanan_" + Account.userLogin + ".txt"));
        int total = 0;
        
        String data = readFile.readLine();
        
        while(data != null) {
            
            if(data.contains("***")) {
                total++;
            }
            
            data = readFile.readLine();
        }
        
        readFile.close();
        return total;
    }
    
    public static Object[][] getPesanan() throws IOException {
        Object[][] pesanan = new Object[getTotalPesanan()][5];       
        BufferedReader readFile = new BufferedReader(new FileReader("Pesanan_" + Account.userLogin + ".txt"));
        String data = readFile.readLine();
        Scanner scan;
        int jmlhPesanan = 0;
        int row = 0;
        int col = 0;
        
        while (data != null) {
            
            if(data.contains("***")) {
                jmlhPesanan++;
                scan = new Scanner(data);
                scan.useDelimiter("#");
                scan.next();
                String psn = "Pesanan " + jmlhPesanan;
                
                pesanan[row][col] = psn;
                pesanan[row][col+1] = scan.next();
                pesanan[row][col+2] = scan.next();
                pesanan[row][col+3] = scan.next();
                pesanan[row][col+4] = "Pesanan Sedang Diproses";
                row++;
                col = 0;
            }
            
            data = readFile.readLine();
        }
        
        readFile.close();
        
        return pesanan;
    }
    
    public static int getTotalDetailPesanan(int row) throws IOException {
        int total = 0;
        
        BufferedReader read = new BufferedReader(new FileReader("Pesanan_" + Account.userLogin + ".txt"));
        String data = read.readLine();
        
        while(data != null) {
            
            
            
            data = read.readLine();
        }
        
        
        return total;
    }   
    
    public static Object[][] getDetailPesanan(int row) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        Object[][] detail;
        BufferedReader readFile = new BufferedReader(new FileReader("Pesanan_" + Account.userLogin + ".txt"));
        String data = readFile.readLine();

        int checkpoint = 0;
        
        while (data != null) {
            
            if(row == 0) {
                if(!data.contains("***")) {
                    list.add(data);
                } else {
                    break;
                }
            } else {
                if(checkpoint == row) {
                    if(!data.contains("***")) {
                        list.add(data);
                    } else {
                        break;
                    }
                }
            }
            
            if(data.contains("***")) {
                checkpoint++;
            }
            
            data = readFile.readLine();
        }
        
        JLabel lbl;
        Scanner scan;
        detail = new Object[list.size()][2];
        for(int i = 0; i < detail.length; i++) {
            String isi = list.get(i);
            scan = new Scanner(isi);
            lbl = new JLabel();
            scan.useDelimiter(",");
            String jenisBarang = scan.next();
            String namaBarang = scan.next();
            jenisBarang = jenisBarang.replace("Jenis = ", "");
            namaBarang = namaBarang.replace(" Nama Barang = ", "");
            Keranjang.namaBarang = namaBarang;
            switch(jenisBarang) {
                case "Kemeja":
                    lbl = Keranjang.imgKemeja();
                    break;
                case "Kaos":
                    lbl = Keranjang.imgKaos();
                    break;
                case "Celana":
                    lbl = Keranjang.imgCelana();
                    break;
                case "Jaket":
                    lbl = Keranjang.imgJaket();
                    break;
                case "Sepatu":
                    lbl = Keranjang.imgSepatu();
                    break;
                case "JamTangan":
                    lbl = Keranjang.imgJamTangan();
                    break;
                default:
                    break;
            }
            detail[i][0] = lbl;
            detail[i][1] = isi;
        }
        
        readFile.close();
        return detail;
    }
    
}
