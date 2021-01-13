/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmadstore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author Ahmad Fathanah
 */
public class Account {
    
    public static String userLogin;
    public static String user_fullname;
    public static String namaDepan;
    public static String namaBelakang;
    public static String passwordLama;
    public static String passwordbaruInput;
    public static String konPasswordbaruInput;
    public static String passwordlamaInput;
    
    public static void checkDatabase() throws IOException {
        File database = new File("Account_Database.txt");
        if(!database.exists()) {
            database.createNewFile();
        }
    }
    
    public static String getUser_fullName() throws IOException {
        BufferedReader readFile = new BufferedReader(new FileReader("Account_Database.txt"));
        String data = readFile.readLine();
        Scanner scan;
        while(data != null) {
            scan = new Scanner(data);
            scan.useDelimiter("#");
            String username = scan.next();
            if(userLogin.equals(username)) {
                scan.next();
                namaDepan = scan.next();
                namaBelakang = scan.next();
                user_fullname = namaDepan + " " + namaBelakang;
            }
            data = readFile.readLine();
        }
        
        readFile.close();
        return user_fullname;
    }
    
    public static boolean usernameIsExist(String usrnme) throws IOException {
        boolean usernameIsExist = false;
        BufferedReader readFile = new BufferedReader(new FileReader("Account_Database.txt"));
        String data = readFile.readLine();
        Scanner scan;
        while(data != null) {
            scan = new Scanner(data);
            scan.useDelimiter("#");
            String usrnameInFile = scan.next();
            if(usrnameInFile.equals(usrnme)) {
                usernameIsExist = true;
            }
            data = readFile.readLine();
        }
        
        readFile.close();
        return usernameIsExist;
    }
    
    public static boolean login (String userNameInput, String passwordInput) throws IOException {
        boolean isExist = false;
        Scanner datascan;
        BufferedReader readFile = new BufferedReader(new FileReader("Account_Database.txt"));
        String data = readFile.readLine();
        
        while(data != null) {
            
            datascan = new Scanner(data);
            datascan.useDelimiter("#");
            String usernameInFile = datascan.next();
            String passwordInFile = datascan.next();
            
            if(userNameInput.equals(usernameInFile) && passwordInput.equals(passwordInFile)) {
                isExist = true;
                Keranjang.usernameLogin = userNameInput;
                break;
            }
            
            data = readFile.readLine();
        }
        
        readFile.close();
        return isExist;
    }
    
    public static boolean register (String userName, char[] password, char[] coPassword, String namaDepan, String namaBelakang) throws IOException {
        boolean isReg = true;
        BufferedWriter writeToFile = new BufferedWriter(new FileWriter("Account_Database.txt", true));
        String psw = "";
        if(password.length != coPassword.length) {
            isReg = false;
        } else {
            for(int i = 0; i < password.length; i++) {
                if(password[i] != coPassword[i]) {
                    isReg = false;
                    break;
                }
                psw += ""+password[i];
            }
        }
        
        
        if(isReg) {
            File createKeranjangUser = new File("Keranjang_"+userName+".txt");
            createKeranjangUser.createNewFile();
            File createPesananUser = new File("Pesanan_"+userName+".txt");
            createPesananUser.createNewFile();
            writeToFile.write(userName+"#"+psw+"#"+namaDepan+"#"+namaBelakang);
            writeToFile.newLine();
            writeToFile.flush();
            writeToFile.close();
        } 
        
        writeToFile.close();
        return isReg;
    }
    
    public static boolean gantiPassword(String pLama) throws IOException{
        boolean isChange = false;
        Scanner scan;
        String paswordbaru = "";
        File database = new File("Account_Database.txt");
        File temp = new File("tempAkun.txt");
        BufferedReader file = new BufferedReader(new FileReader(database));
        BufferedWriter tempFile = new BufferedWriter(new FileWriter(temp));
        
        
        String data = file.readLine();
        
        while (data != null) {
            
            scan = new Scanner(data);
            scan.useDelimiter("#");
            String username = scan.next();
            if(username.equals(userLogin)) {
                passwordLama = scan.next();
                String namadpn = scan.next();
                String namaBlkng = scan.next();
                paswordbaru = getPasswordbaru(passwordbaruInput, konPasswordbaruInput);
                if(!paswordbaru.equals("")) {
                    if(pLama.equals(passwordLama)) {
                        isChange = true;
                        tempFile.write(username+"#"+paswordbaru+"#"+namadpn+"#"+namaBlkng);
                        tempFile.newLine();
                    } else {
                        tempFile.write(data);
                        tempFile.newLine();
                    }
                } else {
                    tempFile.write(data);
                    tempFile.newLine();
                }                         
            } else {
                tempFile.write(data);
                tempFile.newLine();
            }
            
            data = file.readLine();
        }
        tempFile.flush();
        file.close();
        tempFile.close();
              
        database.delete();
        temp.renameTo(database);
        return isChange;
    }
    
    public static String getPasswordbaru(String passwordbaru, String konfirmasi) {
        String pswrd = "";
        if(passwordbaru.equals(konfirmasi)) {
            pswrd = passwordbaru;
        }     
        return pswrd;
    }
    
    public static boolean gantiNama(String namaDpn, String namaBlkng) throws IOException {
        boolean isChange = false;
        File database = new File("Account_Database.txt");
        File temp = new File("tempFile.txt");
        
        BufferedReader readDatabase = new BufferedReader(new FileReader(database));
        BufferedWriter tempFile = new BufferedWriter(new FileWriter(temp));
        Scanner scan;
        
        String data = readDatabase.readLine();
        while(data != null) {
            scan = new Scanner(data);
            scan.useDelimiter("#");
            if(scan.next().equals(userLogin)) {
                String pswrd = scan.next();
                scan.next();
                scan.next();
                tempFile.write(userLogin+"#"+pswrd+"#"+namaDpn+"#"+namaBlkng);
                tempFile.newLine();   
                isChange = true;
            } else {
                tempFile.write(data);
                tempFile.newLine();
            }
            
            data = readDatabase.readLine();
        }
        
        tempFile.flush();
        readDatabase.close();
        tempFile.close();
        
        database.delete();
        temp.renameTo(database);
        return isChange;
    }
    
}

