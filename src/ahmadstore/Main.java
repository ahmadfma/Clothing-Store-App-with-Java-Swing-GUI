/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahmadstore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Ahmad Fathanah
 */
public class Main extends javax.swing.JFrame {   
    
    private Object dataBelanjaan[][];
    private Object belanjaanCheckout[][];
    
    private int checkoutCount = 0;
    private boolean isCheckout = false;
    private boolean isExecute = false;
    private int time;
    private int hoveredRow = -1;
    private int selectedRoww = 0;
    private int jumlahPesanan = 0;
    
    class LabelRenderer extends JTable implements TableCellRenderer {

        public LabelRenderer(JTable tabel) {
            TableColumn tc = tabel.getColumn("Image");
            tc.setMinWidth(150);
            tabel.setRowHeight(200);
        }
       
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {          
            return (Component)value;
        }
        
    }
    
    class multipleLine extends JTextArea implements TableCellRenderer {        
        public multipleLine() {
            this.setLineWrap(true);
            this.setWrapStyleWord(true);
            this.setOpaque(true);
            this.setBackground(Color.WHITE);
            this.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 8, 4,4));
            this.setFont(new Font("Verdana", 0, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null)?  "" : value.toString());
            setEditable(false);
            return this;
        }
        
              
    }
    
    class myTableModel extends AbstractTableModel {
        private Object[][] data;
        private String[] colNames;
        

        public myTableModel(Object[][] d, String[] col) {
            data = d;
            colNames = col;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {         
            return false;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }      
        
    }
    
    class comboBoxRenderer extends JLabel implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            
            comboImageText it = (comboImageText)value;
            setIcon(it.getImg());
            setText(it.getText());           
            if(isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            
            return this;
        }
        
    }
    
    class comboImageText {
        private Icon img;
        private String text;
        
        public comboImageText(Icon icon, String txt) {
            this.img = icon;
            this.text = txt;
        }

        public Icon getImg() {
            return img;
        }

        public void setImg(Icon img) {
            this.img = img;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
      
    }
       
    private void threading(JLabel label) {
        time = 3;
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (time > 0) {
                        if(isExecute) {
                            Thread.sleep(1000);
                            time--;                      
                        } else {                            
                            break;
                        }
                    }
                    if(time == 0) {
                        label.setText("");
                    }
                } catch (InterruptedException e) {};
            }
        });
        thr.start();
    }
    
    private String formatRupiah (int ttl) {
        String str = "";      
        Locale localId = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localId);
        str = formatter.format(ttl);
        return str;
    }
    
    private void showMessage () {      
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/icon/me.png"));       
        JOptionPane.showMessageDialog(null, "Hai, Nama Saya Ahmad Fathanah\n"
                + "Terima Kasih Telah Membuka Aplikasi Saya\n"
                + "Jika Ingin Menghubungi Saya, Silahkan menghubungi contact dibawah ini :)\n\n"
                + "Email         : ahmadfathanah05@gmail.com\n"
                + "Instagram : Ahmadfma_\n"
                + "LinkedIn    : Ahmad Fathanah M.Adil", "See yuu :)", JOptionPane.INFORMATION_MESSAGE, icon);
    }
    
    private String stringCapital (String str) {       
        String str1 = str.trim();
        char[] chr = str1.toCharArray();
        StringBuilder strbld = new StringBuilder(str1);
        strbld.replace(0, 1, str1.substring(0, 1).toUpperCase());
        for(int i = 0; i < chr.length; i++) {
            if(chr[i] == ' ') {
                strbld.replace(i+1, i+2, str1.substring(i+1, i+2).toUpperCase());
            }
        }
       
        String str2 = strbld.toString();    
        return str2;
    }
    
    private JPanel getPanel() throws IOException{
        JPanel panel = new JPanel();
        JTable tabel = new JTable();
        JScrollPane scrl = new JScrollPane();
        JLabel lbl = new JLabel("Daftar Pesanan : ");
        lbl.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
        
        panel.setLayout(new BorderLayout(10,10));
        
        String[] colomNames = {"Image", "Detail"};    
        tabel.setModel(new myTableModel(Pesanan.getDetailPesanan(selectedRoww), colomNames));
        tabel.getColumn("Image").setCellRenderer(new LabelRenderer(tabel));
        tabel.setDefaultRenderer(Object.class, new multipleLine());
        tabel.setShowGrid(true);   
        JTableHeader header = tabel.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 15));
        TableColumn col1 = tabel.getColumnModel().getColumn(0);
        col1.setPreferredWidth(120);
        TableColumn col2 = tabel.getColumnModel().getColumn(1);
        col2.setPreferredWidth(230);
        
        scrl.setViewportView(tabel);
        
        panel.add(lbl, BorderLayout.NORTH);
        panel.add(scrl, BorderLayout.CENTER);
              
        return panel;
    }

    /**
     * Creates new form Main
     */
    public Main() throws IOException {   
        initComponents();
        initCompAgain();
        initMoreComponents();
        setLocationRelativeTo(null);
    
    }

    private int getTotalBayaran() {
        int totalBayaran = 0;
        boolean isCeklis;
        String temp = "";
        for(int i = 0; i < tabelBelanjaan.getModel().getRowCount(); i++) {
            isCeklis = (boolean)tabelBelanjaan.getValueAt(i, 1);
            if(isCeklis) {
                temp = (String)tabelBelanjaan.getValueAt(i, 9);
                totalBayaran += Integer.parseInt(temp.replace("Rp", "").replace(".", "").replace(",00", ""));
            }
        }
        
        return totalBayaran;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane9 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        mainPanel = new javax.swing.JPanel();
        homePage = new javax.swing.JPanel();
        signUpBtn = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        usernameInput = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        passwordInput = new javax.swing.JPasswordField();
        signInBtn = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        infoHomeLabel = new javax.swing.JLabel();
        inAppPanel = new javax.swing.JPanel();
        tombolNavigasi = new javax.swing.JPanel();
        belanjaBtn = new javax.swing.JLabel();
        keranjangBtn = new javax.swing.JLabel();
        pengaturanBtn = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        userFullnameLabel = new javax.swing.JLabel();
        keluarPanel = new javax.swing.JPanel();
        KeluarBtnPanel = new javax.swing.JPanel();
        keluarBtn = new javax.swing.JLabel();
        konfirmasiKeluarPanel = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        yaKeluarBtn = new javax.swing.JButton();
        tidakKeluarBtn = new javax.swing.JButton();
        pesananBtn = new javax.swing.JLabel();
        UI = new javax.swing.JLabel();
        mainContainer = new javax.swing.JPanel();
        belanjaPanel = new javax.swing.JPanel();
        kaosBtn = new javax.swing.JLabel();
        jaketBtn = new javax.swing.JLabel();
        celanaBtn = new javax.swing.JLabel();
        kemejaBtn = new javax.swing.JLabel();
        sepatuBtn = new javax.swing.JLabel();
        jamTanganBtn = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        keranjangPanel = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelBelanjaan = new javax.swing.JTable();
        checkoutAll = new javax.swing.JCheckBox();
        totalBayaranField = new javax.swing.JTextField();
        navigasiKeranjangPanel = new javax.swing.JPanel();
        hapusORcheckoutPanel = new javax.swing.JPanel();
        gotoHapusPesananPanel = new javax.swing.JLabel();
        gotoCheckoutPanel = new javax.swing.JLabel();
        infoKeranjangLabel = new javax.swing.JLabel();
        hapusPesananPanel = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        nmrPesananHapusInput = new javax.swing.JTextField();
        hapusPesananBtn = new javax.swing.JButton();
        infoHapusLabel = new javax.swing.JLabel();
        konfirmasiPembayaranPanel = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        gotoBayarPanel = new javax.swing.JButton();
        pengaturanPanel = new javax.swing.JPanel();
        homePengaturanPanel = new javax.swing.JPanel();
        editNamaBtn = new javax.swing.JButton();
        gantiPasswordBtn = new javax.swing.JButton();
        editPasswordPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        passwordLamaInput = new javax.swing.JPasswordField();
        jPanel9 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        passwordBaruInput = new javax.swing.JPasswordField();
        jPanel10 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        konPasswordBaruInput = new javax.swing.JPasswordField();
        konfirmasiEditPswrdBtn = new javax.swing.JButton();
        infoPasswordLabel = new javax.swing.JLabel();
        editNamaPanel = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel69 = new javax.swing.JLabel();
        namaDepanLabel = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        namaBelakangLabel = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        namaDepanBaru = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        namaBelakangBaru = new javax.swing.JTextField();
        konfirmasiUbahnamaBtn = new javax.swing.JButton();
        infoUbahNamaLabel = new javax.swing.JLabel();
        kaosPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelKaos = new javax.swing.JTable() {
            private static final long serialVersionUID = 1;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer,
                int row,
                int column) {
                Component component =
                super.prepareRenderer(renderer, row, column);

                if (row == hoveredRow) {
                    component.setBackground(new Color(102, 165, 173));
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(getBackground());
                    component.setForeground(getForeground());
                }

                return component;
            }
        };
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        nmrBarangKaos = new javax.swing.JTextField();
        ukuranPilihanKaos = new javax.swing.JTextField();
        warnaPilihanKaos = new javax.swing.JTextField();
        jumlahPesananKaos = new javax.swing.JTextField();
        keKeranjangKaosBtn = new javax.swing.JLabel();
        infoKaosLabel = new javax.swing.JLabel();
        comboUkuranKaos = new javax.swing.JComboBox<>();
        kurangKaosbtn = new javax.swing.JButton();
        tambahKaosBtn = new javax.swing.JButton();
        comboWarnaKaos = new javax.swing.JComboBox<>();
        kemejaPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelKemeja = new javax.swing.JTable() {
            private static final long serialVersionUID = 1;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer,
                int row,
                int column) {
                Component component =
                super.prepareRenderer(renderer, row, column);

                if (row == hoveredRow) {
                    component.setBackground(new Color(102, 165, 173));
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(getBackground());
                    component.setForeground(getForeground());
                }

                return component;
            }
        };
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        nmrBarangKemejaInput = new javax.swing.JTextField();
        ukuranBarangKemejaInput = new javax.swing.JTextField();
        warnaBarangKemejaInput = new javax.swing.JTextField();
        jumlahPesananBarangKemeja = new javax.swing.JTextField();
        keKeranjangKemejaBtn = new javax.swing.JLabel();
        infoKemejaLabel = new javax.swing.JLabel();
        comboUkuranKemeja = new javax.swing.JComboBox<>();
        comboWarnaKemeja = new javax.swing.JComboBox<>();
        kurangKemejaBtn = new javax.swing.JButton();
        tambahKemejaBtn = new javax.swing.JButton();
        celanaPanel = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelCelana = new javax.swing.JTable() {
            private static final long serialVersionUID = 1;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer,
                int row,
                int column) {
                Component component =
                super.prepareRenderer(renderer, row, column);

                if (row == hoveredRow) {
                    component.setBackground(new Color(102, 165, 173));
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(getBackground());
                    component.setForeground(getForeground());
                }

                return component;
            }
        };
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        nmrBarangCelana = new javax.swing.JTextField();
        ukuranPilCelana = new javax.swing.JTextField();
        warnaPilCelana = new javax.swing.JTextField();
        jumlahPesananCelana = new javax.swing.JTextField();
        keKeranjangCelanaBtn = new javax.swing.JLabel();
        infoCelanaLabel = new javax.swing.JLabel();
        comboUkuranCelana = new javax.swing.JComboBox<>();
        comboWarnaCelana = new javax.swing.JComboBox<>();
        kurangCelanaBtn = new javax.swing.JButton();
        tambahCelanaBtn = new javax.swing.JButton();
        jaketPanel = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelJaket = new javax.swing.JTable() {
            private static final long serialVersionUID = 1;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer,
                int row,
                int column) {
                Component component =
                super.prepareRenderer(renderer, row, column);

                if (row == hoveredRow) {
                    component.setBackground(new Color(102, 165, 173));
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(getBackground());
                    component.setForeground(getForeground());
                }

                return component;
            }
        };
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        nmrPesananJaket = new javax.swing.JTextField();
        ukuranPilJaket = new javax.swing.JTextField();
        warnaPilJaket = new javax.swing.JTextField();
        jumlahPesananJaket = new javax.swing.JTextField();
        keKeranjangJaketBtn = new javax.swing.JLabel();
        infoJaketLabel = new javax.swing.JLabel();
        comboUkuranJaket = new javax.swing.JComboBox<>();
        comboWarnaJaket = new javax.swing.JComboBox<>();
        kurangJaketBtn = new javax.swing.JButton();
        tambahJaketBtn = new javax.swing.JButton();
        sepatuPanel = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelSepatu = new javax.swing.JTable() {
            private static final long serialVersionUID = 1;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer,
                int row,
                int column) {
                Component component =
                super.prepareRenderer(renderer, row, column);

                if (row == hoveredRow) {
                    component.setBackground(new Color(102, 165, 173));
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(getBackground());
                    component.setForeground(getForeground());
                }

                return component;
            }
        };
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        nmrBarangSepatu = new javax.swing.JTextField();
        ukuranPilSepatu = new javax.swing.JTextField();
        warnaPilSepatu = new javax.swing.JTextField();
        jumlahPesananSepatu = new javax.swing.JTextField();
        keKeranjangSepatuBtn = new javax.swing.JLabel();
        infoSepatuLabel = new javax.swing.JLabel();
        comboUkuranSepatu = new javax.swing.JComboBox<>();
        comboWarnaSepatu = new javax.swing.JComboBox<>();
        kurangSepatuBtn = new javax.swing.JButton();
        tambahSepatuBtn = new javax.swing.JButton();
        jamTanganPanel = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelJamTangan = new javax.swing.JTable() {
            private static final long serialVersionUID = 1;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer,
                int row,
                int column) {
                Component component =
                super.prepareRenderer(renderer, row, column);

                if (row == hoveredRow) {
                    component.setBackground(new Color(102, 165, 173));
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(getBackground());
                    component.setForeground(getForeground());
                }

                return component;
            }
        };
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        nmrBarangJamtangan = new javax.swing.JTextField();
        ukuranPilJamTangan = new javax.swing.JTextField();
        warnaPilJamtangan = new javax.swing.JTextField();
        jumlahPesananJamTangan = new javax.swing.JTextField();
        keKeranjangJamTanganBtn = new javax.swing.JLabel();
        infoJamTanganLabel = new javax.swing.JLabel();
        comboUkuranJam = new javax.swing.JComboBox<>();
        comboWarnaJam = new javax.swing.JComboBox<>();
        kurangJamBtn = new javax.swing.JButton();
        tambahJamBtn = new javax.swing.JButton();
        pesananPanel = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tabelPesanan = new javax.swing.JTable() {
            private static final long serialVersionUID = 1;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer,
                int row,
                int column) {
                Component component =
                super.prepareRenderer(renderer, row, column);

                if (row == hoveredRow) {
                    component.setBackground(new Color(102, 165, 173));
                    component.setForeground(Color.WHITE);
                } else {
                    component.setBackground(getBackground());
                    component.setForeground(getForeground());
                }

                return component;
            }
        };
        jLabel7 = new javax.swing.JLabel();
        registerPage = new javax.swing.JPanel();
        signIn2Btn = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        namaDepanRegis = new javax.swing.JTextField();
        namaBlakangRegis = new javax.swing.JTextField();
        usernameRegis = new javax.swing.JTextField();
        registerBtn = new javax.swing.JLabel();
        passwordRegis = new javax.swing.JPasswordField();
        confirmPasswordRegis = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        infoRegisLabel = new javax.swing.JLabel();
        bayarPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelCheckout = new javax.swing.JTable();
        jLabel52 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        metodePembayaranLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        opsiPengiriman = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        kodeVoucher = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lbl1 = new javax.swing.JLabel();
        lbl5 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        totalBayarpanel = new javax.swing.JPanel();
        lbl4 = new javax.swing.JLabel();
        totalBayarLabel = new javax.swing.JLabel();
        subtotalBarang = new javax.swing.JLabel();
        subtotalPengiriman = new javax.swing.JLabel();
        potonganHargalabel = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        checkVoucherBtn = new javax.swing.JButton();
        infoBayarLabel = new javax.swing.JLabel();
        panelMetodeBayar = new javax.swing.JPanel();
        pilihanMetode = new javax.swing.JPanel();
        listBayar = new javax.swing.JComboBox<>();
        pilihanTransfer = new javax.swing.JPanel();
        listBank = new javax.swing.JComboBox<>();
        listPengiriman = new javax.swing.JComboBox<>();
        kembaliBtn = new javax.swing.JLabel();
        buatPesananBtn = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();

        jScrollPane9.setViewportView(jTextPane1);

        jScrollPane10.setViewportView(jTextPane2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Ahmad Store");
        setResizable(false);

        mainPanel.setLayout(new java.awt.CardLayout());

        homePage.setBackground(new java.awt.Color(242, 242, 242));
        homePage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        signUpBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sign up.png"))); // NOI18N
        signUpBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signUpBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signUpBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUpBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUpBtnMouseExited(evt);
            }
        });
        homePage.add(signUpBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 520, 210, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/UI/Ld.png"))); // NOI18N
        homePage.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel2.setBackground(new java.awt.Color(7, 87, 91));
        jLabel2.setFont(new java.awt.Font("Segoe Script", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(7, 87, 91));
        jLabel2.setText("Ahmad Store");
        homePage.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 760, 220, 40));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(7, 87, 91), 2));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/user.png"))); // NOI18N

        usernameInput.setBackground(new java.awt.Color(255, 255, 255));
        usernameInput.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        usernameInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        usernameInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameInputKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usernameInput, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
            .addComponent(usernameInput)
        );

        homePage.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(7, 87, 91), 2));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/lock.png"))); // NOI18N

        passwordInput.setBackground(new java.awt.Color(255, 255, 255));
        passwordInput.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        passwordInput.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        passwordInput.setEchoChar('•');
        passwordInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordInputKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordInput, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
            .addComponent(passwordInput)
        );

        homePage.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 360, -1, -1));

        signInBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sign in.png"))); // NOI18N
        signInBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signInBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signInBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signInBtnMouseExited(evt);
            }
        });
        homePage.add(signInBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 470, 210, 70));

        jLabel4.setBackground(new java.awt.Color(7, 87, 91));
        jLabel4.setFont(new java.awt.Font("Segoe Script", 1, 61)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(7, 87, 91));
        jLabel4.setText("Selamat Datang");
        homePage.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 60, 530, 100));

        infoHomeLabel.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        infoHomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        homePage.add(infoHomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 240, 470, 40));

        mainPanel.add(homePage, "card2");

        inAppPanel.setBackground(new java.awt.Color(242, 242, 242));

        tombolNavigasi.setBackground(new java.awt.Color(242, 242, 242));
        tombolNavigasi.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        belanjaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/belanja.png"))); // NOI18N
        belanjaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        belanjaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                belanjaBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                belanjaBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                belanjaBtnMouseExited(evt);
            }
        });
        tombolNavigasi.add(belanjaBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 211, 70));

        keranjangBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keranjang.png"))); // NOI18N
        keranjangBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keranjangBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keranjangBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                keranjangBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                keranjangBtnMouseExited(evt);
            }
        });
        tombolNavigasi.add(keranjangBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, 71));

        pengaturanBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pengaturan.png"))); // NOI18N
        pengaturanBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pengaturanBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pengaturanBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pengaturanBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pengaturanBtnMouseExited(evt);
            }
        });
        tombolNavigasi.add(pengaturanBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, -1, 71));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/user2.png"))); // NOI18N
        tombolNavigasi.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 770, -1, -1));

        userFullnameLabel.setBackground(new java.awt.Color(255, 255, 255));
        userFullnameLabel.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        userFullnameLabel.setForeground(new java.awt.Color(255, 255, 255));
        tombolNavigasi.add(userFullnameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 770, 260, 20));

        keluarPanel.setBackground(new java.awt.Color(31, 105, 108));
        keluarPanel.setForeground(new java.awt.Color(60, 63, 65));
        keluarPanel.setLayout(new java.awt.CardLayout());

        KeluarBtnPanel.setBackground(new java.awt.Color(31, 105, 108));

        keluarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keluar.png"))); // NOI18N
        keluarBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keluarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keluarBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                keluarBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                keluarBtnMouseExited(evt);
            }
        });

        javax.swing.GroupLayout KeluarBtnPanelLayout = new javax.swing.GroupLayout(KeluarBtnPanel);
        KeluarBtnPanel.setLayout(KeluarBtnPanelLayout);
        KeluarBtnPanelLayout.setHorizontalGroup(
            KeluarBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KeluarBtnPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(keluarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        KeluarBtnPanelLayout.setVerticalGroup(
            KeluarBtnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KeluarBtnPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(keluarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        keluarPanel.add(KeluarBtnPanel, "card2");

        konfirmasiKeluarPanel.setBackground(new java.awt.Color(31, 105, 108));

        jLabel64.setBackground(new java.awt.Color(255, 255, 255));
        jLabel64.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Yakin Ingin Keluar ?");

        yaKeluarBtn.setText("Ya");
        yaKeluarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yaKeluarBtnActionPerformed(evt);
            }
        });

        tidakKeluarBtn.setText("Tidak");
        tidakKeluarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidakKeluarBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout konfirmasiKeluarPanelLayout = new javax.swing.GroupLayout(konfirmasiKeluarPanel);
        konfirmasiKeluarPanel.setLayout(konfirmasiKeluarPanelLayout);
        konfirmasiKeluarPanelLayout.setHorizontalGroup(
            konfirmasiKeluarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(konfirmasiKeluarPanelLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(konfirmasiKeluarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64)
                    .addGroup(konfirmasiKeluarPanelLayout.createSequentialGroup()
                        .addComponent(yaKeluarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(tidakKeluarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        konfirmasiKeluarPanelLayout.setVerticalGroup(
            konfirmasiKeluarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(konfirmasiKeluarPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(konfirmasiKeluarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yaKeluarBtn)
                    .addComponent(tidakKeluarBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        keluarPanel.add(konfirmasiKeluarPanel, "card3");

        tombolNavigasi.add(keluarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 270, 90));

        pesananBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pesanan.png"))); // NOI18N
        pesananBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pesananBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pesananBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pesananBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pesananBtnMouseExited(evt);
            }
        });
        tombolNavigasi.add(pesananBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 210, 70));

        UI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/UI/11.png"))); // NOI18N
        tombolNavigasi.add(UI, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 800));

        mainContainer.setBackground(new java.awt.Color(242, 242, 242));
        mainContainer.setLayout(new java.awt.CardLayout());

        belanjaPanel.setBackground(new java.awt.Color(242, 242, 242));
        belanjaPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kaosBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kaosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kaosBtn.png"))); // NOI18N
        kaosBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kaosBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kaosBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kaosBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kaosBtnMouseExited(evt);
            }
        });
        belanjaPanel.add(kaosBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 167, 157));

        jaketBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jaketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/jaketBtn.png"))); // NOI18N
        jaketBtn.setToolTipText("");
        jaketBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jaketBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jaketBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jaketBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jaketBtnMouseExited(evt);
            }
        });
        belanjaPanel.add(jaketBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, 167, 157));

        celanaBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        celanaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/celanaBtn.png"))); // NOI18N
        celanaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        celanaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                celanaBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                celanaBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                celanaBtnMouseExited(evt);
            }
        });
        belanjaPanel.add(celanaBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, 167, 157));

        kemejaBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        kemejaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kemejaBtn.png"))); // NOI18N
        kemejaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kemejaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kemejaBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kemejaBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kemejaBtnMouseExited(evt);
            }
        });
        belanjaPanel.add(kemejaBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, -1, 157));

        sepatuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sepatuBtn.png"))); // NOI18N
        sepatuBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sepatuBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sepatuBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sepatuBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sepatuBtnMouseExited(evt);
            }
        });
        belanjaPanel.add(sepatuBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 460, 167, 157));

        jamTanganBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jamTanganBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/jamBtn.png"))); // NOI18N
        jamTanganBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jamTanganBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jamTanganBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jamTanganBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jamTanganBtnMouseExited(evt);
            }
        });
        belanjaPanel.add(jamTanganBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 460, 167, 157));

        jLabel25.setFont(new java.awt.Font("Dialog", 0, 30)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(31, 105, 108));
        jLabel25.setText("Silahkan Pilih Kategori");
        belanjaPanel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 310, 60));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/UI/13.png"))); // NOI18N
        belanjaPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 790, 690));

        mainContainer.add(belanjaPanel, "card2");

        keranjangPanel.setBackground(new java.awt.Color(242, 242, 242));

        jLabel38.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel38.setText("Daftar Belanjaan :");

        tabelBelanjaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelBelanjaan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane8.setViewportView(tabelBelanjaan);

        checkoutAll.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        checkoutAll.setText("Checkout Semua");
        checkoutAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkoutAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutAllActionPerformed(evt);
            }
        });

        totalBayaranField.setEditable(false);
        totalBayaranField.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        totalBayaranField.setText("Total Bayaran :");

        navigasiKeranjangPanel.setLayout(new java.awt.CardLayout());

        hapusORcheckoutPanel.setBackground(new java.awt.Color(242, 242, 242));

        gotoHapusPesananPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/hapusPesanan.png"))); // NOI18N
        gotoHapusPesananPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gotoHapusPesananPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gotoHapusPesananPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                gotoHapusPesananPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                gotoHapusPesananPanelMouseExited(evt);
            }
        });

        gotoCheckoutPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/checkout.png"))); // NOI18N
        gotoCheckoutPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gotoCheckoutPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gotoCheckoutPanelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                gotoCheckoutPanelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                gotoCheckoutPanelMouseExited(evt);
            }
        });

        infoKeranjangLabel.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        infoKeranjangLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout hapusORcheckoutPanelLayout = new javax.swing.GroupLayout(hapusORcheckoutPanel);
        hapusORcheckoutPanel.setLayout(hapusORcheckoutPanelLayout);
        hapusORcheckoutPanelLayout.setHorizontalGroup(
            hapusORcheckoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hapusORcheckoutPanelLayout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addGroup(hapusORcheckoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(infoKeranjangLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(hapusORcheckoutPanelLayout.createSequentialGroup()
                        .addComponent(gotoHapusPesananPanel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                        .addComponent(gotoCheckoutPanel)))
                .addGap(162, 162, 162))
        );
        hapusORcheckoutPanelLayout.setVerticalGroup(
            hapusORcheckoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hapusORcheckoutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoKeranjangLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(hapusORcheckoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gotoCheckoutPanel)
                    .addComponent(gotoHapusPesananPanel))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        navigasiKeranjangPanel.add(hapusORcheckoutPanel, "card2");

        hapusPesananPanel.setBackground(new java.awt.Color(242, 242, 242));

        jLabel59.setFont(new java.awt.Font("Dubai", 1, 18)); // NOI18N
        jLabel59.setText("Masukkan Nomor Pesanan Yang Ingin Dihapus");

        nmrPesananHapusInput.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N

        hapusPesananBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        hapusPesananBtn.setText("Hapus");
        hapusPesananBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hapusPesananBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusPesananBtnActionPerformed(evt);
            }
        });

        infoHapusLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        infoHapusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout hapusPesananPanelLayout = new javax.swing.GroupLayout(hapusPesananPanel);
        hapusPesananPanel.setLayout(hapusPesananPanelLayout);
        hapusPesananPanelLayout.setHorizontalGroup(
            hapusPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hapusPesananPanelLayout.createSequentialGroup()
                .addGroup(hapusPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(hapusPesananPanelLayout.createSequentialGroup()
                        .addGap(374, 374, 374)
                        .addComponent(nmrPesananHapusInput, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(hapusPesananBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(hapusPesananPanelLayout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addGroup(hapusPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infoHapusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(270, Short.MAX_VALUE))
        );
        hapusPesananPanelLayout.setVerticalGroup(
            hapusPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hapusPesananPanelLayout.createSequentialGroup()
                .addComponent(infoHapusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(hapusPesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nmrPesananHapusInput, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapusPesananBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        navigasiKeranjangPanel.add(hapusPesananPanel, "card3");

        konfirmasiPembayaranPanel.setBackground(new java.awt.Color(242, 242, 242));

        jLabel63.setBackground(new java.awt.Color(0, 0, 0));
        jLabel63.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(0, 0, 0));
        jLabel63.setText("Lanjutkan Pembayaran ?");

        gotoBayarPanel.setText("Ya");
        gotoBayarPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gotoBayarPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout konfirmasiPembayaranPanelLayout = new javax.swing.GroupLayout(konfirmasiPembayaranPanel);
        konfirmasiPembayaranPanel.setLayout(konfirmasiPembayaranPanelLayout);
        konfirmasiPembayaranPanelLayout.setHorizontalGroup(
            konfirmasiPembayaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(konfirmasiPembayaranPanelLayout.createSequentialGroup()
                .addGroup(konfirmasiPembayaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(konfirmasiPembayaranPanelLayout.createSequentialGroup()
                        .addGap(331, 331, 331)
                        .addComponent(jLabel63))
                    .addGroup(konfirmasiPembayaranPanelLayout.createSequentialGroup()
                        .addGap(395, 395, 395)
                        .addComponent(gotoBayarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(359, Short.MAX_VALUE))
        );
        konfirmasiPembayaranPanelLayout.setVerticalGroup(
            konfirmasiPembayaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(konfirmasiPembayaranPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gotoBayarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        navigasiKeranjangPanel.add(konfirmasiPembayaranPanel, "card4");

        javax.swing.GroupLayout keranjangPanelLayout = new javax.swing.GroupLayout(keranjangPanel);
        keranjangPanel.setLayout(keranjangPanelLayout);
        keranjangPanelLayout.setHorizontalGroup(
            keranjangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
            .addGroup(keranjangPanelLayout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(keranjangPanelLayout.createSequentialGroup()
                .addComponent(checkoutAll)
                .addGap(136, 136, 136)
                .addComponent(totalBayaranField, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(navigasiKeranjangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        keranjangPanelLayout.setVerticalGroup(
            keranjangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(keranjangPanelLayout.createSequentialGroup()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(keranjangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalBayaranField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkoutAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navigasiKeranjangPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainContainer.add(keranjangPanel, "card3");

        pengaturanPanel.setBackground(new java.awt.Color(242, 242, 242));
        pengaturanPanel.setLayout(new java.awt.CardLayout());

        homePengaturanPanel.setBackground(new java.awt.Color(242, 242, 242));

        editNamaBtn.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        editNamaBtn.setText("Edit Nama");
        editNamaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editNamaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editNamaBtnActionPerformed(evt);
            }
        });

        gantiPasswordBtn.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        gantiPasswordBtn.setText("Ganti Password");
        gantiPasswordBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gantiPasswordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gantiPasswordBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout homePengaturanPanelLayout = new javax.swing.GroupLayout(homePengaturanPanel);
        homePengaturanPanel.setLayout(homePengaturanPanelLayout);
        homePengaturanPanelLayout.setHorizontalGroup(
            homePengaturanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, homePengaturanPanelLayout.createSequentialGroup()
                .addContainerGap(353, Short.MAX_VALUE)
                .addGroup(homePengaturanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editNamaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gantiPasswordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(343, 343, 343))
        );
        homePengaturanPanelLayout.setVerticalGroup(
            homePengaturanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePengaturanPanelLayout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(editNamaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(gantiPasswordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(450, Short.MAX_VALUE))
        );

        pengaturanPanel.add(homePengaturanPanel, "card2");

        editPasswordPanel.setBackground(new java.awt.Color(242, 242, 242));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel66.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(0, 0, 0));
        jLabel66.setText(" Masukkan Password Lama :");

        passwordLamaInput.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        passwordLamaInput.setBorder(null);
        passwordLamaInput.setEchoChar('•');

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordLamaInput, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(passwordLamaInput)
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel67.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(0, 0, 0));
        jLabel67.setText(" Masukkan Password Baru  :");

        passwordBaruInput.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        passwordBaruInput.setBorder(null);
        passwordBaruInput.setEchoChar('•');

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordBaruInput, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(passwordBaruInput)
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel68.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(0, 0, 0));
        jLabel68.setText(" Konfirmasi Password Baru :");

        konPasswordBaruInput.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        konPasswordBaruInput.setBorder(null);
        konPasswordBaruInput.setEchoChar('•');

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel68)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(konPasswordBaruInput, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(konPasswordBaruInput)
        );

        konfirmasiEditPswrdBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        konfirmasiEditPswrdBtn.setText("Ganti");
        konfirmasiEditPswrdBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                konfirmasiEditPswrdBtnActionPerformed(evt);
            }
        });

        infoPasswordLabel.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        infoPasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout editPasswordPanelLayout = new javax.swing.GroupLayout(editPasswordPanel);
        editPasswordPanel.setLayout(editPasswordPanelLayout);
        editPasswordPanelLayout.setHorizontalGroup(
            editPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPasswordPanelLayout.createSequentialGroup()
                .addGroup(editPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editPasswordPanelLayout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addGroup(editPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(editPasswordPanelLayout.createSequentialGroup()
                        .addGap(379, 379, 379)
                        .addComponent(konfirmasiEditPswrdBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editPasswordPanelLayout.createSequentialGroup()
                .addGap(0, 159, Short.MAX_VALUE)
                .addComponent(infoPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155))
        );
        editPasswordPanelLayout.setVerticalGroup(
            editPasswordPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPasswordPanelLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(infoPasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(konfirmasiEditPswrdBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        pengaturanPanel.add(editPasswordPanel, "card3");

        editNamaPanel.setBackground(new java.awt.Color(242, 242, 242));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel69.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(0, 0, 0));
        jLabel69.setText(" Nama Depan      :");

        namaDepanLabel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaDepanLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(namaDepanLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel71.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 0, 0));
        jLabel71.setText(" Nama Belakang :");

        namaBelakangLabel.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaBelakangLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(namaBelakangLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel72.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(0, 0, 0));
        jLabel72.setText(" Masukkan Nama Depan Baru     :");

        namaDepanBaru.setBackground(new java.awt.Color(255, 255, 255));
        namaDepanBaru.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        namaDepanBaru.setBorder(null);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaDepanBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(namaDepanBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel73.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(0, 0, 0));
        jLabel73.setText(" Masukkan Nama Belakang Baru :");

        namaBelakangBaru.setBackground(new java.awt.Color(255, 255, 255));
        namaBelakangBaru.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        namaBelakangBaru.setBorder(null);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel73)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaBelakangBaru, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(namaBelakangBaru)
        );

        konfirmasiUbahnamaBtn.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        konfirmasiUbahnamaBtn.setText("Ganti");
        konfirmasiUbahnamaBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                konfirmasiUbahnamaBtnActionPerformed(evt);
            }
        });

        infoUbahNamaLabel.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        infoUbahNamaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout editNamaPanelLayout = new javax.swing.GroupLayout(editNamaPanel);
        editNamaPanel.setLayout(editNamaPanelLayout);
        editNamaPanelLayout.setHorizontalGroup(
            editNamaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editNamaPanelLayout.createSequentialGroup()
                .addGroup(editNamaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editNamaPanelLayout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addGroup(editNamaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(editNamaPanelLayout.createSequentialGroup()
                        .addGap(376, 376, 376)
                        .addComponent(konfirmasiUbahnamaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(editNamaPanelLayout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(infoUbahNamaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        editNamaPanelLayout.setVerticalGroup(
            editNamaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editNamaPanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(infoUbahNamaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(konfirmasiUbahnamaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(248, Short.MAX_VALUE))
        );

        pengaturanPanel.add(editNamaPanel, "card4");

        mainContainer.add(pengaturanPanel, "card4");

        kaosPanel.setBackground(new java.awt.Color(242, 242, 242));

        tabelKaos.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tabelKaos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Image", "Nama Barang", "Ukuran Tersedia", "Warna Tersedia", "Harga Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelKaos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane3.setViewportView(tabelKaos);

        jLabel26.setFont(new java.awt.Font("Verdana", 2, 24)); // NOI18N
        jLabel26.setText("Kategori : Kaos");

        jLabel27.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel27.setText("Silahkan Pilih Barang Yang Tersedia :");

        jLabel28.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel28.setText("Nomor Barang    :");

        jLabel29.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel29.setText("Ukuran Pilihan    :");

        jLabel30.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel30.setText("Warna Pilihan     :");

        jLabel31.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel31.setText("Jumlah Pesanan :");

        nmrBarangKaos.setEditable(false);
        nmrBarangKaos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nmrBarangKaos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ukuranPilihanKaos.setEditable(false);
        ukuranPilihanKaos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ukuranPilihanKaos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        warnaPilihanKaos.setEditable(false);
        warnaPilihanKaos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        warnaPilihanKaos.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jumlahPesananKaos.setEditable(false);
        jumlahPesananKaos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jumlahPesananKaos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jumlahPesananKaos.setText("0");

        keKeranjangKaosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png"))); // NOI18N
        keKeranjangKaosBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keKeranjangKaosBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keKeranjangKaosBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                keKeranjangKaosBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                keKeranjangKaosBtnMouseExited(evt);
            }
        });

        infoKaosLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        infoKaosLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboUkuranKaos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        kurangKaosbtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        kurangKaosbtn.setText("-");

        tambahKaosBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        tambahKaosBtn.setText("+");

        comboWarnaKaos.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout kaosPanelLayout = new javax.swing.GroupLayout(kaosPanel);
        kaosPanel.setLayout(kaosPanelLayout);
        kaosPanelLayout.setHorizontalGroup(
            kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kaosPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(kaosPanelLayout.createSequentialGroup()
                .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kaosPanelLayout.createSequentialGroup()
                        .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ukuranPilihanKaos, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(warnaPilihanKaos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jumlahPesananKaos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(kaosPanelLayout.createSequentialGroup()
                                .addComponent(kurangKaosbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tambahKaosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(comboUkuranKaos, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(comboWarnaKaos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(103, 103, 103)
                        .addComponent(keKeranjangKaosBtn))
                    .addGroup(kaosPanelLayout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nmrBarangKaos, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel27))
                .addGap(110, 110, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kaosPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kaosPanelLayout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(325, 325, 325))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kaosPanelLayout.createSequentialGroup()
                        .addComponent(infoKaosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(245, 245, 245))))
        );
        kaosPanelLayout.setVerticalGroup(
            kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kaosPanelLayout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(10, 10, 10)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoKaosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(nmrBarangKaos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(kaosPanelLayout.createSequentialGroup()
                        .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboUkuranKaos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ukuranPilihanKaos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addGap(18, 18, 18)
                        .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(warnaPilihanKaos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(comboWarnaKaos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(keKeranjangKaosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(kaosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jumlahPesananKaos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(kurangKaosbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahKaosBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        mainContainer.add(kaosPanel, "card5");

        kemejaPanel.setBackground(new java.awt.Color(242, 242, 242));

        tabelKemeja.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tabelKemeja.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Image", "Nama Barang", "Ukuran Tersedia", "Warna Tersedia", "Harga Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelKemeja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(tabelKemeja);

        jLabel19.setFont(new java.awt.Font("Verdana", 2, 24)); // NOI18N
        jLabel19.setText("Kategori : Kemeja");

        jLabel20.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel20.setText("Nomor Barang    :");

        jLabel21.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel21.setText("Silahkan Pilih Barang Yang Tersedia :");

        jLabel22.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel22.setText("Warna Pilihan     :");

        jLabel23.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel23.setText("Ukuran Pilihan    :");

        jLabel24.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel24.setText("Jumlah Pesanan :");

        nmrBarangKemejaInput.setEditable(false);
        nmrBarangKemejaInput.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nmrBarangKemejaInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ukuranBarangKemejaInput.setEditable(false);
        ukuranBarangKemejaInput.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ukuranBarangKemejaInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        warnaBarangKemejaInput.setEditable(false);
        warnaBarangKemejaInput.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        warnaBarangKemejaInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jumlahPesananBarangKemeja.setEditable(false);
        jumlahPesananBarangKemeja.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jumlahPesananBarangKemeja.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jumlahPesananBarangKemeja.setText("0");

        keKeranjangKemejaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png"))); // NOI18N
        keKeranjangKemejaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keKeranjangKemejaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keKeranjangKemejaBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                keKeranjangKemejaBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                keKeranjangKemejaBtnMouseExited(evt);
            }
        });

        infoKemejaLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        infoKemejaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboUkuranKemeja.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        comboWarnaKemeja.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        kurangKemejaBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        kurangKemejaBtn.setText("-");

        tambahKemejaBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        tambahKemejaBtn.setText("+");

        javax.swing.GroupLayout kemejaPanelLayout = new javax.swing.GroupLayout(kemejaPanel);
        kemejaPanel.setLayout(kemejaPanelLayout);
        kemejaPanelLayout.setHorizontalGroup(
            kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kemejaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kemejaPanelLayout.createSequentialGroup()
                        .addComponent(jumlahPesananBarangKemeja, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kurangKemejaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tambahKemejaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(kemejaPanelLayout.createSequentialGroup()
                        .addComponent(nmrBarangKemejaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 624, Short.MAX_VALUE))
                    .addGroup(kemejaPanelLayout.createSequentialGroup()
                        .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kemejaPanelLayout.createSequentialGroup()
                                .addComponent(warnaBarangKemejaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboWarnaKemeja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(kemejaPanelLayout.createSequentialGroup()
                                .addComponent(ukuranBarangKemejaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboUkuranKemeja, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(keKeranjangKemejaBtn)
                        .addGap(102, 102, 102))))
            .addGroup(kemejaPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(6, 6, 6))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kemejaPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(infoKemejaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(245, 245, 245))
            .addGroup(kemejaPanelLayout.createSequentialGroup()
                .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kemejaPanelLayout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kemejaPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        kemejaPanelLayout.setVerticalGroup(
            kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kemejaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(8, 8, 8)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kemejaPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(infoKemejaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kemejaPanelLayout.createSequentialGroup()
                                .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nmrBarangKemejaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addGap(18, 18, 18)
                                .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel23)
                                    .addComponent(ukuranBarangKemejaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kemejaPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(comboUkuranKemeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(warnaBarangKemejaInput, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(comboWarnaKemeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(kemejaPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(keKeranjangKemejaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(kemejaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jumlahPesananBarangKemeja, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kurangKemejaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahKemejaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(42, 42, 42))
        );

        mainContainer.add(kemejaPanel, "card8");

        celanaPanel.setBackground(new java.awt.Color(242, 242, 242));

        jLabel32.setFont(new java.awt.Font("Verdana", 2, 24)); // NOI18N
        jLabel32.setText("Kategori : Celana");

        jLabel33.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel33.setText("Silahkan Pilih Barang Yang Tersedia :");

        tabelCelana.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tabelCelana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Image", "Nama Barang", "Ukuran Tersedia", "Warna Tersedia", "Harga Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelCelana.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane4.setViewportView(tabelCelana);

        jLabel34.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel34.setText("Ukuran Pilihan    :");

        jLabel35.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel35.setText("Nomor Barang    :");

        jLabel36.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel36.setText("Jumlah Pesanan :");

        jLabel37.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel37.setText("Warna Pilihan     :");

        nmrBarangCelana.setEditable(false);
        nmrBarangCelana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nmrBarangCelana.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ukuranPilCelana.setEditable(false);
        ukuranPilCelana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ukuranPilCelana.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ukuranPilCelana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ukuranPilCelanaActionPerformed(evt);
            }
        });

        warnaPilCelana.setEditable(false);
        warnaPilCelana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        warnaPilCelana.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jumlahPesananCelana.setEditable(false);
        jumlahPesananCelana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jumlahPesananCelana.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        keKeranjangCelanaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png"))); // NOI18N
        keKeranjangCelanaBtn.setText("jLabel38");
        keKeranjangCelanaBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keKeranjangCelanaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keKeranjangCelanaBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                keKeranjangCelanaBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                keKeranjangCelanaBtnMouseExited(evt);
            }
        });

        infoCelanaLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        infoCelanaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboUkuranCelana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        comboWarnaCelana.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        kurangCelanaBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        kurangCelanaBtn.setText("-");

        tambahCelanaBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        tambahCelanaBtn.setText("+");

        javax.swing.GroupLayout celanaPanelLayout = new javax.swing.GroupLayout(celanaPanel);
        celanaPanel.setLayout(celanaPanelLayout);
        celanaPanelLayout.setHorizontalGroup(
            celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(celanaPanelLayout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(infoCelanaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(celanaPanelLayout.createSequentialGroup()
                .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(celanaPanelLayout.createSequentialGroup()
                        .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(celanaPanelLayout.createSequentialGroup()
                                .addGap(328, 328, 328)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(celanaPanelLayout.createSequentialGroup()
                                .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(celanaPanelLayout.createSequentialGroup()
                                        .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nmrBarangCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(celanaPanelLayout.createSequentialGroup()
                                                .addComponent(jumlahPesananCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(kurangCelanaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tambahCelanaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(celanaPanelLayout.createSequentialGroup()
                                        .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(celanaPanelLayout.createSequentialGroup()
                                                .addComponent(warnaPilCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboWarnaCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(celanaPanelLayout.createSequentialGroup()
                                                .addComponent(ukuranPilCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboUkuranCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                                        .addComponent(keKeranjangCelanaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(111, 111, 111))
                    .addComponent(jScrollPane4)
                    .addGroup(celanaPanelLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        celanaPanelLayout.setVerticalGroup(
            celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(celanaPanelLayout.createSequentialGroup()
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoCelanaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nmrBarangCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(celanaPanelLayout.createSequentialGroup()
                        .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel34)
                                .addComponent(comboUkuranCelana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(celanaPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ukuranPilCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(warnaPilCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboWarnaCelana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(celanaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jumlahPesananCelana, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)
                            .addComponent(kurangCelanaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tambahCelanaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 73, Short.MAX_VALUE))
                    .addGroup(celanaPanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(keKeranjangCelanaBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        mainContainer.add(celanaPanel, "card7");

        jaketPanel.setBackground(new java.awt.Color(242, 242, 242));
        jaketPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel39.setFont(new java.awt.Font("Verdana", 2, 24)); // NOI18N
        jLabel39.setText("Kategori : Jaket");

        jLabel40.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel40.setText("Silahkan Pilih Barang Yang Tersedia :");

        tabelJaket.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tabelJaket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Image", "Nama Barang", "Ukuran Tersedia", "Warna Tersedia", "Harga Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelJaket.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane5.setViewportView(tabelJaket);

        jLabel41.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel41.setText("Ukuran Pilihan    :");

        jLabel42.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel42.setText("Nomor Barang    :");

        jLabel43.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel43.setText("Warna Pilihan     :");

        jLabel44.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel44.setText("Jumlah Pesanan :");

        nmrPesananJaket.setEditable(false);
        nmrPesananJaket.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nmrPesananJaket.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ukuranPilJaket.setEditable(false);
        ukuranPilJaket.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ukuranPilJaket.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        warnaPilJaket.setEditable(false);
        warnaPilJaket.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        warnaPilJaket.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jumlahPesananJaket.setEditable(false);
        jumlahPesananJaket.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jumlahPesananJaket.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        keKeranjangJaketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png"))); // NOI18N
        keKeranjangJaketBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keKeranjangJaketBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keKeranjangJaketBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                keKeranjangJaketBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                keKeranjangJaketBtnMouseExited(evt);
            }
        });

        infoJaketLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        infoJaketLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboUkuranJaket.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        comboWarnaJaket.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        kurangJaketBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        kurangJaketBtn.setText("-");

        tambahJaketBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        tambahJaketBtn.setText("+");

        javax.swing.GroupLayout jaketPanelLayout = new javax.swing.GroupLayout(jaketPanel);
        jaketPanel.setLayout(jaketPanelLayout);
        jaketPanelLayout.setHorizontalGroup(
            jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jaketPanelLayout.createSequentialGroup()
                .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jaketPanelLayout.createSequentialGroup()
                        .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jaketPanelLayout.createSequentialGroup()
                                .addGap(315, 315, 315)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jaketPanelLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nmrPesananJaket, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jaketPanelLayout.createSequentialGroup()
                                .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jumlahPesananJaket, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kurangJaketBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tambahJaketBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(178, 178, 178)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jaketPanelLayout.createSequentialGroup()
                .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jaketPanelLayout.createSequentialGroup()
                        .addComponent(ukuranPilJaket, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboUkuranJaket, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jaketPanelLayout.createSequentialGroup()
                        .addComponent(warnaPilJaket, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboWarnaJaket, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(keKeranjangJaketBtn)
                .addGap(113, 113, 113))
            .addGroup(jaketPanelLayout.createSequentialGroup()
                .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jaketPanelLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(infoJaketLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel40))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jaketPanelLayout.setVerticalGroup(
            jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jaketPanelLayout.createSequentialGroup()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoJaketLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(nmrPesananJaket, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jaketPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(ukuranPilJaket, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(comboUkuranJaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(warnaPilJaket, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(comboWarnaJaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jaketPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(keKeranjangJaketBtn)))
                .addGap(17, 17, 17)
                .addGroup(jaketPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jumlahPesananJaket, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(kurangJaketBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahJaketBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        mainContainer.add(jaketPanel, "card6");

        sepatuPanel.setBackground(new java.awt.Color(242, 242, 242));
        sepatuPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel46.setFont(new java.awt.Font("Verdana", 2, 24)); // NOI18N
        jLabel46.setText("Kategori : Sepatu");

        jLabel47.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel47.setText("Silahkan Pilih Barang Yang Tersedia :");

        tabelSepatu.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tabelSepatu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Image", "Nama Barang", "Ukuran Tersedia", "Warna Tersedia", "Harga Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelSepatu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane6.setViewportView(tabelSepatu);

        jLabel48.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel48.setText("Ukuran Pilihan    :");

        jLabel49.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel49.setText("Nomor Barang    :");

        jLabel50.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel50.setText("Warna Pilihan     :");

        jLabel51.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel51.setText("Jumlah Pesanan :");

        nmrBarangSepatu.setEditable(false);
        nmrBarangSepatu.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nmrBarangSepatu.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ukuranPilSepatu.setEditable(false);
        ukuranPilSepatu.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ukuranPilSepatu.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        warnaPilSepatu.setEditable(false);
        warnaPilSepatu.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        warnaPilSepatu.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jumlahPesananSepatu.setEditable(false);
        jumlahPesananSepatu.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jumlahPesananSepatu.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        keKeranjangSepatuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png"))); // NOI18N
        keKeranjangSepatuBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keKeranjangSepatuBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keKeranjangSepatuBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                keKeranjangSepatuBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                keKeranjangSepatuBtnMouseExited(evt);
            }
        });

        infoSepatuLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        infoSepatuLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboUkuranSepatu.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        comboWarnaSepatu.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        kurangSepatuBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        kurangSepatuBtn.setText("-");

        tambahSepatuBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        tambahSepatuBtn.setText("+");

        javax.swing.GroupLayout sepatuPanelLayout = new javax.swing.GroupLayout(sepatuPanel);
        sepatuPanel.setLayout(sepatuPanelLayout);
        sepatuPanelLayout.setHorizontalGroup(
            sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sepatuPanelLayout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(infoSepatuLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(sepatuPanelLayout.createSequentialGroup()
                .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(sepatuPanelLayout.createSequentialGroup()
                        .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sepatuPanelLayout.createSequentialGroup()
                                .addComponent(jumlahPesananSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kurangSepatuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tambahSepatuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(sepatuPanelLayout.createSequentialGroup()
                                .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(sepatuPanelLayout.createSequentialGroup()
                                        .addComponent(warnaPilSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboWarnaSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(sepatuPanelLayout.createSequentialGroup()
                                        .addComponent(ukuranPilSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(comboUkuranSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                                .addComponent(keKeranjangSepatuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(115, 115, 115))
                    .addGroup(sepatuPanelLayout.createSequentialGroup()
                        .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sepatuPanelLayout.createSequentialGroup()
                                .addGap(330, 330, 330)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(sepatuPanelLayout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nmrBarangSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel47))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sepatuPanelLayout.setVerticalGroup(
            sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sepatuPanelLayout.createSequentialGroup()
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoSepatuLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(nmrBarangSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(sepatuPanelLayout.createSequentialGroup()
                        .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel48)
                            .addComponent(ukuranPilSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboUkuranSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel50)
                            .addComponent(warnaPilSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboWarnaSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(keKeranjangSepatuBtn))
                .addGap(17, 17, 17)
                .addGroup(sepatuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(jumlahPesananSepatu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kurangSepatuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahSepatuBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 25, Short.MAX_VALUE))
        );

        mainContainer.add(sepatuPanel, "card9");

        jamTanganPanel.setBackground(new java.awt.Color(242, 242, 242));

        jLabel53.setFont(new java.awt.Font("Verdana", 2, 24)); // NOI18N
        jLabel53.setText("Kategori : Jam Tangan");

        jLabel54.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel54.setText("Barang Yang Tersedia :");

        tabelJamTangan.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        tabelJamTangan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Image", "Nama Barang", "Ukuran Tersedia", "Warna Tersedia", "Harga Satuan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelJamTangan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane7.setViewportView(tabelJamTangan);

        jLabel55.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel55.setText("Nomor Barang    :");

        jLabel56.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel56.setText("Ukuran Pilihan    :");

        jLabel57.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel57.setText("Warna Pilihan     :");

        jLabel58.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel58.setText("Jumlah Pesanan :");

        nmrBarangJamtangan.setEditable(false);
        nmrBarangJamtangan.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nmrBarangJamtangan.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        ukuranPilJamTangan.setEditable(false);
        ukuranPilJamTangan.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ukuranPilJamTangan.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        warnaPilJamtangan.setEditable(false);
        warnaPilJamtangan.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        warnaPilJamtangan.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jumlahPesananJamTangan.setEditable(false);
        jumlahPesananJamTangan.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jumlahPesananJamTangan.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        keKeranjangJamTanganBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png"))); // NOI18N
        keKeranjangJamTanganBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        keKeranjangJamTanganBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keKeranjangJamTanganBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                keKeranjangJamTanganBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                keKeranjangJamTanganBtnMouseExited(evt);
            }
        });

        infoJamTanganLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        infoJamTanganLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        comboUkuranJam.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        comboWarnaJam.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        kurangJamBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        kurangJamBtn.setText("-");

        tambahJamBtn.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        tambahJamBtn.setText("+");

        javax.swing.GroupLayout jamTanganPanelLayout = new javax.swing.GroupLayout(jamTanganPanel);
        jamTanganPanel.setLayout(jamTanganPanelLayout);
        jamTanganPanelLayout.setHorizontalGroup(
            jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7)
                    .addGroup(jamTanganPanelLayout.createSequentialGroup()
                        .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nmrBarangJamtangan, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel58)
                                    .addComponent(jLabel57))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                        .addComponent(jumlahPesananJamTangan, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(kurangJamBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tambahJamBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                        .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                                .addComponent(warnaPilJamtangan, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(comboWarnaJam, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                                .addGap(117, 117, 117)
                                                .addComponent(comboUkuranJam, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                                        .addComponent(keKeranjangJamTanganBtn))))
                            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                .addComponent(jLabel56)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ukuranPilJamTangan, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(111, 111, 111))
                    .addGroup(jamTanganPanelLayout.createSequentialGroup()
                        .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                .addGap(312, 312, 312)
                                .addComponent(jLabel53))
                            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                                .addGap(245, 245, 245)
                                .addComponent(infoJamTanganLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jamTanganPanelLayout.setVerticalGroup(
            jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jamTanganPanelLayout.createSequentialGroup()
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoJamTanganLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(nmrBarangJamtangan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jamTanganPanelLayout.createSequentialGroup()
                        .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(ukuranPilJamTangan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboUkuranJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57)
                            .addComponent(warnaPilJamtangan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboWarnaJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(keKeranjangJamTanganBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jamTanganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jumlahPesananJamTangan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kurangJamBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tambahJamBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 31, Short.MAX_VALUE))
        );

        mainContainer.add(jamTanganPanel, "card10");

        pesananPanel.setBackground(new java.awt.Color(242, 242, 242));

        tabelPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Pesanan", "Metode Pembayaran", "Pengiriman", "Total Bayaran", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelPesanan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tabelPesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelPesananMouseEntered(evt);
            }
        });
        jScrollPane11.setViewportView(tabelPesanan);

        jLabel7.setFont(new java.awt.Font("Verdana", 2, 17)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Daftar Pesanan :");

        javax.swing.GroupLayout pesananPanelLayout = new javax.swing.GroupLayout(pesananPanel);
        pesananPanel.setLayout(pesananPanelLayout);
        pesananPanelLayout.setHorizontalGroup(
            pesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pesananPanelLayout.createSequentialGroup()
                .addGroup(pesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pesananPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 714, Short.MAX_VALUE))
                    .addComponent(jScrollPane11))
                .addContainerGap())
        );
        pesananPanelLayout.setVerticalGroup(
            pesananPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pesananPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        mainContainer.add(pesananPanel, "card11");

        javax.swing.GroupLayout inAppPanelLayout = new javax.swing.GroupLayout(inAppPanel);
        inAppPanel.setLayout(inAppPanelLayout);
        inAppPanelLayout.setHorizontalGroup(
            inAppPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inAppPanelLayout.createSequentialGroup()
                .addComponent(tombolNavigasi, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inAppPanelLayout.setVerticalGroup(
            inAppPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tombolNavigasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        mainPanel.add(inAppPanel, "card4");

        registerPage.setBackground(new java.awt.Color(242, 242, 242));
        registerPage.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        signIn2Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/signin2.png"))); // NOI18N
        signIn2Btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        signIn2Btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signIn2BtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signIn2BtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signIn2BtnMouseExited(evt);
            }
        });
        registerPage.add(signIn2Btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 520, 210, 70));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/UI/Rd.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        registerPage.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 0, 320, 800));

        jPanel3.setBackground(new java.awt.Color(242, 242, 242));

        namaDepanRegis.setBackground(new java.awt.Color(255, 255, 255));
        namaDepanRegis.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N

        namaBlakangRegis.setBackground(new java.awt.Color(255, 255, 255));
        namaBlakangRegis.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N

        usernameRegis.setBackground(new java.awt.Color(255, 255, 255));
        usernameRegis.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N

        registerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/register.png"))); // NOI18N
        registerBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerBtnMouseExited(evt);
            }
        });

        passwordRegis.setBackground(new java.awt.Color(255, 255, 255));
        passwordRegis.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        passwordRegis.setEchoChar('•');

        confirmPasswordRegis.setBackground(new java.awt.Color(255, 255, 255));
        confirmPasswordRegis.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        confirmPasswordRegis.setEchoChar('•');

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nama Depan");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Nama Belakang");

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Password");

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Username");

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Confirm Password");

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText(" :");

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText(" :");

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText(" :");

        jLabel16.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText(" :");

        jLabel17.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText(" :");

        infoRegisLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        infoRegisLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(346, 346, 346)
                        .addComponent(registerBtn))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(passwordRegis)
                            .addComponent(usernameRegis, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                            .addComponent(namaBlakangRegis, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                            .addComponent(namaDepanRegis, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                            .addComponent(confirmPasswordRegis, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(infoRegisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(infoRegisLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaDepanRegis, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaBlakangRegis, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(56, 56, 56)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameRegis, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordRegis, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmPasswordRegis, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(65, 65, 65)
                .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178))
        );

        registerPage.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 800));

        mainPanel.add(registerPage, "card3");

        bayarPanel.setBackground(new java.awt.Color(31, 105, 108));
        bayarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setBackground(new java.awt.Color(242, 242, 242));
        jScrollPane2.setForeground(new java.awt.Color(242, 242, 242));

        tabelCheckout.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Barang", "Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelCheckout);

        bayarPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 540, 683));

        jLabel52.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Daftar Pesanan :");
        bayarPanel.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 250, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel61.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(0, 0, 0));
        jLabel61.setText(" Metode Pembayaran :");

        metodePembayaranLabel.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        metodePembayaranLabel.setText("-");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(metodePembayaranLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(metodePembayaranLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bayarPanel.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 130, 310, 40));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel62.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(0, 0, 0));
        jLabel62.setText(" Opsi Pengiriman        :");

        opsiPengiriman.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        opsiPengiriman.setText("-");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opsiPengiriman, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(opsiPengiriman, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bayarPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 200, 310, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        kodeVoucher.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        kodeVoucher.setBorder(null);

        jLabel65.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(0, 0, 0));
        jLabel65.setText(" Kode Voucher           :");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kodeVoucher, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(kodeVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bayarPanel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 280, 310, 40));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl1.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        lbl1.setForeground(new java.awt.Color(0, 0, 0));
        lbl1.setText("Subtotal Untuk Barang         : ");

        lbl5.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        lbl5.setForeground(new java.awt.Color(0, 0, 0));
        lbl5.setText("Potongan Harga                    :");

        lbl2.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        lbl2.setForeground(new java.awt.Color(0, 0, 0));
        lbl2.setText("Subtotal Untuk Pengiriman  :");

        totalBayarpanel.setBackground(new java.awt.Color(204, 204, 204));
        totalBayarpanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl4.setForeground(new java.awt.Color(0, 0, 0));
        lbl4.setText(" Total Bayaran                      :");

        totalBayarLabel.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N
        totalBayarLabel.setForeground(new java.awt.Color(0, 0, 0));
        totalBayarLabel.setText("-");

        javax.swing.GroupLayout totalBayarpanelLayout = new javax.swing.GroupLayout(totalBayarpanel);
        totalBayarpanel.setLayout(totalBayarpanelLayout);
        totalBayarpanelLayout.setHorizontalGroup(
            totalBayarpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(totalBayarpanelLayout.createSequentialGroup()
                .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalBayarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
        );
        totalBayarpanelLayout.setVerticalGroup(
            totalBayarpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(totalBayarLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        subtotalBarang.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N
        subtotalBarang.setForeground(new java.awt.Color(0, 0, 0));

        subtotalPengiriman.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N
        subtotalPengiriman.setForeground(new java.awt.Color(0, 0, 0));
        subtotalPengiriman.setText("-");

        potonganHargalabel.setFont(new java.awt.Font("Verdana", 2, 14)); // NOI18N
        potonganHargalabel.setForeground(new java.awt.Color(0, 0, 0));
        potonganHargalabel.setText("-");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(totalBayarpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(subtotalBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(subtotalPengiriman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(potonganHargalabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subtotalBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subtotalPengiriman, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(potonganHargalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(totalBayarpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        bayarPanel.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 360, 500, 240));

        jLabel70.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(0, 0, 0));
        jLabel70.setText("*kosongkan bila tidak ada");
        bayarPanel.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 260, -1, -1));

        checkVoucherBtn.setText("Check");
        checkVoucherBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVoucherBtnActionPerformed(evt);
            }
        });
        bayarPanel.add(checkVoucherBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 280, -1, 40));

        infoBayarLabel.setFont(new java.awt.Font("Dialog", 2, 13)); // NOI18N
        infoBayarLabel.setForeground(new java.awt.Color(0, 0, 0));
        infoBayarLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bayarPanel.add(infoBayarLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 500, 40));

        panelMetodeBayar.setLayout(new java.awt.CardLayout());

        listBayar.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        listBayar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listBayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pilihanMetodeLayout = new javax.swing.GroupLayout(pilihanMetode);
        pilihanMetode.setLayout(pilihanMetodeLayout);
        pilihanMetodeLayout.setHorizontalGroup(
            pilihanMetodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listBayar, 0, 180, Short.MAX_VALUE)
        );
        pilihanMetodeLayout.setVerticalGroup(
            pilihanMetodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pilihanMetodeLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(listBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelMetodeBayar.add(pilihanMetode, "card2");

        listBank.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        listBank.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listBank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listBankActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pilihanTransferLayout = new javax.swing.GroupLayout(pilihanTransfer);
        pilihanTransfer.setLayout(pilihanTransferLayout);
        pilihanTransferLayout.setHorizontalGroup(
            pilihanTransferLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listBank, javax.swing.GroupLayout.Alignment.TRAILING, 0, 180, Short.MAX_VALUE)
        );
        pilihanTransferLayout.setVerticalGroup(
            pilihanTransferLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pilihanTransferLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(listBank, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panelMetodeBayar.add(pilihanTransfer, "card3");

        bayarPanel.add(panelMetodeBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 130, 180, 40));

        listPengiriman.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        listPengiriman.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        listPengiriman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listPengirimanActionPerformed(evt);
            }
        });
        bayarPanel.add(listPengiriman, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 200, 180, 40));

        kembaliBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kembali.png"))); // NOI18N
        kembaliBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        kembaliBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kembaliBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kembaliBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kembaliBtnMouseExited(evt);
            }
        });
        bayarPanel.add(kembaliBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 630, 200, 70));

        buatPesananBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pesan.png"))); // NOI18N
        buatPesananBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buatPesananBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buatPesananBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buatPesananBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                buatPesananBtnMouseExited(evt);
            }
        });
        bayarPanel.add(buatPesananBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 630, 200, 70));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon/17.png"))); // NOI18N
        bayarPanel.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 60, 560, 683));

        mainPanel.add(bayarPanel, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initCompAgain() throws IOException {
        Barang.checkDatabase_Barang();
        Account.checkDatabase();
        Border line = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        Border empty = new EmptyBorder(0, 20, 0, 0);
        CompoundBorder border = new CompoundBorder(line, empty);
        namaDepanRegis.setBorder(border);
        namaBlakangRegis.setBorder(border);
        usernameRegis.setBorder(border);
        passwordRegis.setBorder(border);
        confirmPasswordRegis.setBorder(border);
        ukuranPilJamTangan.setEnabled(false);
        comboUkuranJam.setEnabled(false);
        ListSelectionModel model = tabelBelanjaan.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {    
                if(!model.isSelectionEmpty()) {
                    int selectedRow = model.getMinSelectionIndex();
                    if(selectedRow != -1) {
                        if((boolean)tabelBelanjaan.getValueAt(selectedRow, 1)) {
                            tabelBelanjaan.setValueAt(false, selectedRow, 1);   
                            dataBelanjaan[selectedRow][1] = false;
                            checkoutCount--;
                        } else {
                            tabelBelanjaan.setValueAt(true, selectedRow, 1);  
                            dataBelanjaan[selectedRow][1] = true;
                            checkoutCount++;
                        }
                        Locale localId = new Locale("in", "ID");
                        NumberFormat formatter = NumberFormat.getCurrencyInstance(localId);
                        String strFormat;
                        int total = getTotalBayaran();
                        if(total > 0) {
                            strFormat = formatter.format(total);  
                            totalBayaranField.setText("Total bayaran : " + strFormat);
                        } else {
                            totalBayaranField.setText("Total bayaran : Rp0,00");
                        }
                    }                
                }              
                model.clearSelection();
            }
        });
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog(Main.this,"Yakin Ingin Keluar ?");  
                if(a == JOptionPane.YES_OPTION){  
                    showMessage();
                    setDefaultCloseOperation(EXIT_ON_CLOSE);                  
                }  
            }        
        });
        
               
        listBayar.setModel(listMetodePembayaran());
        listBayar.setRenderer(new comboBoxRenderer());
        listBank.setModel(listBank());
        listBank.setRenderer(new comboBoxRenderer());
        listPengiriman.setModel(listPengiriman());
        listPengiriman.setRenderer(new comboBoxRenderer());
        
        ListSelectionModel tblpsnModel = tabelPesanan.getSelectionModel();
        tblpsnModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRoww = tblpsnModel.getMinSelectionIndex();
                if(selectedRoww != -1) {
                    try {                    
                        JOptionPane.showMessageDialog(null, new JScrollPane(getPanel()),"Pesanan " + (selectedRoww+1),JOptionPane.PLAIN_MESSAGE);
                    }catch(IOException ex) {};                     
                }                  
                tblpsnModel.clearSelection();
            }
        });
        
    }
    
    private void initMoreComponents() {
        initSelection(tabelKaos, comboUkuranKaos, comboWarnaKaos, nmrBarangKaos,ukuranPilihanKaos, warnaPilihanKaos, jumlahPesananKaos);
        initSelection(tabelKemeja, comboUkuranKemeja, comboWarnaKemeja, nmrBarangKemejaInput, ukuranBarangKemejaInput, warnaBarangKemejaInput, jumlahPesananBarangKemeja);
        initSelection(tabelCelana, comboUkuranCelana, comboWarnaCelana, nmrBarangCelana, ukuranPilCelana, warnaPilCelana, jumlahPesananCelana);
        initSelection(tabelJaket, comboUkuranJaket, comboWarnaJaket, nmrPesananJaket,ukuranPilJaket, warnaPilJaket, jumlahPesananJaket);
        initSelection(tabelSepatu, comboUkuranSepatu, comboWarnaSepatu, nmrBarangSepatu, ukuranPilSepatu, warnaPilSepatu, jumlahPesananSepatu);
        initSelection(tabelJamTangan, comboUkuranJam, comboWarnaJam, nmrBarangJamtangan, ukuranPilJamTangan, warnaPilJamtangan, jumlahPesananJamTangan);
        
        initTableMouseListener(tabelPesanan);
        initTableMouseListener(tabelKaos);
        initTableMouseListener(tabelKemeja);
        initTableMouseListener(tabelCelana);
        initTableMouseListener(tabelJaket);
        initTableMouseListener(tabelSepatu);
        initTableMouseListener(tabelJamTangan);
        
        initActionPerfomedComboBox(comboUkuranKaos, ukuranPilihanKaos);
        initActionPerfomedComboBox(comboWarnaKaos, warnaPilihanKaos);
        initActionPerfomedTambahBtn(tambahKaosBtn, jumlahPesananKaos);
        initActionPerfomedKurangBtn(kurangKaosbtn, jumlahPesananKaos);
        
        initActionPerfomedComboBox(comboUkuranKemeja, ukuranBarangKemejaInput);
        initActionPerfomedComboBox(comboWarnaKemeja, warnaBarangKemejaInput);
        initActionPerfomedTambahBtn(tambahKemejaBtn, jumlahPesananBarangKemeja);
        initActionPerfomedKurangBtn(kurangKemejaBtn, jumlahPesananBarangKemeja);
        
        initActionPerfomedComboBox(comboUkuranCelana, ukuranPilCelana);
        initActionPerfomedComboBox(comboWarnaCelana, warnaPilCelana);
        initActionPerfomedTambahBtn(tambahCelanaBtn, jumlahPesananCelana);
        initActionPerfomedKurangBtn(kurangCelanaBtn, jumlahPesananCelana);
        
        initActionPerfomedComboBox(comboUkuranJaket, ukuranPilJaket);
        initActionPerfomedComboBox(comboWarnaJaket, warnaPilJaket);
        initActionPerfomedTambahBtn(tambahJaketBtn, jumlahPesananJaket);
        initActionPerfomedKurangBtn(kurangJaketBtn, jumlahPesananJaket);
        
        initActionPerfomedComboBox(comboUkuranSepatu, ukuranPilSepatu);
        initActionPerfomedComboBox(comboWarnaSepatu, warnaPilSepatu);
        initActionPerfomedTambahBtn(tambahSepatuBtn, jumlahPesananSepatu);
        initActionPerfomedKurangBtn(kurangSepatuBtn, jumlahPesananSepatu);
        
        initActionPerfomedComboBox(comboUkuranJam, ukuranPilJamTangan);
        initActionPerfomedComboBox(comboWarnaJam, warnaPilJamtangan);
        initActionPerfomedTambahBtn(tambahJamBtn, jumlahPesananJamTangan);
        initActionPerfomedKurangBtn(kurangJamBtn, jumlahPesananJamTangan);
        
                
    }
    
    private void initTableMouseListener(JTable table) {
        MouseInputListener hoveredRowUpdater = new MouseInputAdapter() {
            private void repaintRow(int row) {
                if (row >= 0) {
                    Rectangle bounds = table.getCellRect(row, 0, true);
                    int width = table.getWidth();
                    table.repaint(0, bounds.y, width, bounds.height);
                }
            }

            private void updateHoveredRowFrom(MouseEvent event) {
                int oldHoveredRow = hoveredRow;
                hoveredRow = table.rowAtPoint(event.getPoint());
                repaintRow(oldHoveredRow);
                repaintRow(hoveredRow);
            }

            @Override
            public void mouseEntered(MouseEvent event) {
                updateHoveredRowFrom(event);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                updateHoveredRowFrom(event);
            }

            @Override
            public void mouseMoved(MouseEvent event) {
                updateHoveredRowFrom(event);
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                updateHoveredRowFrom(event);
            }
        };
        table.addMouseListener(hoveredRowUpdater);
        table.addMouseMotionListener(hoveredRowUpdater);
    }
    
    private void initActionPerfomedComboBox(JComboBox comboBox, JTextField field) {
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field.setText(""+comboBox.getModel().getSelectedItem());
            }
        });
    }
    
    private void initActionPerfomedTambahBtn (JButton btn, JTextField field) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jumlahPesanan++;
                field.setText(""+jumlahPesanan);
            }
        });
    }
    
    private void initActionPerfomedKurangBtn (JButton btn, JTextField field) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jumlahPesanan > 0) {
                    jumlahPesanan--;
                    field.setText(""+jumlahPesanan);
                }             
            }
        });
    }
    
    private void refreshComboBox(JComboBox box) {
        box.setModel(new DefaultComboBoxModel(new String[] {}));
    }
    
    private void initTable() throws IOException {  
        setIsiTabelBelanjaan();
        setSizeTabelBelanjaan();    
            
        setIsiTabelBarang(tabelKemeja, "Kemeja");
        setSizeTabelBarang(tabelKemeja);
              
        setIsiTabelBarang(tabelKaos, "Kaos");
        setSizeTabelBarang(tabelKaos);
        
        setIsiTabelBarang(tabelCelana, "Celana");
        setSizeTabelBarang(tabelCelana);
        
        setIsiTabelBarang(tabelSepatu, "Sepatu");
        setSizeTabelBarang(tabelSepatu);
        
        setIsiTabelBarang(tabelJamTangan, "JamTangan");
        setSizeTabelBarang(tabelJamTangan);
        
        setIsiTabelBarang(tabelJaket, "Jaket");
        setSizeTabelBarang(tabelJaket);
    }
    
    private DefaultComboBoxModel listMetodePembayaran() {
        DefaultComboBoxModel mdl = new DefaultComboBoxModel();
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/cod.png")), "COD"));
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/transfer.png")), "Tranfer Bank"));       
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/indomaret.png")), "Indomaret"));
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/alfamart.png")), "Alfamart"));
        
        return mdl;
    }
    
    private DefaultComboBoxModel listBank() {
        DefaultComboBoxModel mdl = new DefaultComboBoxModel();
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/logoBRI.png")), "Bank BRI"));
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/logoBNI.png")), "Bank BNI"));       
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/logoBCA.png")), "Bank BCA"));
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/logoMandiri.png")), "Bank Mandiri"));
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/metodeLainnya.png")), "Metode Lainnya"));
        
        return mdl;
    }
    
    private DefaultComboBoxModel listPengiriman() {
        DefaultComboBoxModel mdl = new DefaultComboBoxModel();
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/jne.png")), "JNE Reguler"));
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/j&t.png")), "J&T Express"));       
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/ninjaxpress.png")), "Ninja Xpress"));
        mdl.addElement(new comboImageText(new ImageIcon(getClass().getResource("/img/icon/siCepat.png")), "siCepat REG"));
        
        return mdl;
    }
    
    private void refreshField(JTextField ukuranField, JTextField warnaField, JTextField jumlah) {
            ukuranField.setText("");
            warnaField.setText("");
            jumlahPesanan = 0;
            jumlah.setText(""+jumlahPesanan);            
    }
    
    private void initSelection(JTable table, JComboBox ukuran, JComboBox warna, JTextField nomor, JTextField ukuranField, JTextField warnaField, JTextField jumlah) {
        ListSelectionModel model = table.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectionRow = model.getMinSelectionIndex();
                if(selectionRow != -1) {
                    Thread thr = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {     
                                warna.setModel(new Barang().getWarnaBarang(selectionRow));
                                if(!table.equals(tabelJamTangan)) {                                                                    
                                    ukuran.setModel(new Barang().getUkuranBarang(selectionRow));
                                }                               
                                nomor.setText(""+(selectionRow+1));
                                refreshField(ukuranField, warnaField, jumlah);  
                                Keranjang.namaBarang = ""+table.getValueAt(selectionRow, 2);
                                Keranjang.hargaSatuan = ""+table.getValueAt(selectionRow, 5);
                            } catch(IOException ex) {};   
                        }
                    });
                    thr.start();
                }
                model.clearSelection();
            }
        });
    }
      
    // Table
    private void setSizeTabelBelanjaan() {
        //NO
        TableColumn col1 = tabelBelanjaan.getColumnModel().getColumn(0);
        col1.setPreferredWidth(30);
        //CEKLIS
        TableColumn col2 = tabelBelanjaan.getColumnModel().getColumn(1);
        col2.setPreferredWidth(30);
        //JENIS
        TableColumn col4 = tabelBelanjaan.getColumnModel().getColumn(3);
        col4.setPreferredWidth(40);
        //BARANG
        TableColumn col5 = tabelBelanjaan.getColumnModel().getColumn(4);
        col5.setPreferredWidth(120);
        //UKURAN
        TableColumn col6 = tabelBelanjaan.getColumnModel().getColumn(5);
        col6.setPreferredWidth(50);
        //WARNA
        TableColumn col7 = tabelBelanjaan.getColumnModel().getColumn(6);
        col7.setPreferredWidth(40);
        //JUMLAH
        TableColumn col8 = tabelBelanjaan.getColumnModel().getColumn(7);
        col8.setPreferredWidth(50);
        //HARGA
        TableColumn col9 = tabelBelanjaan.getColumnModel().getColumn(8);
        col9.setPreferredWidth(100);
        //TOTAL HARGA
        TableColumn col10 = tabelBelanjaan.getColumnModel().getColumn(9);
        col10.setPreferredWidth(100);
    }
    
    private void setIsiTabelBelanjaan() throws IOException {
        JTableHeader header = tabelBelanjaan.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 15));
        dataBelanjaan = Keranjang.getBelanjaan();
        String[] colomNames = {"No","", "Image", "Jenis", "Barang", "Ukuran", "Warna", "Jumlah", "Harga Satuan", "Total Harga"};
        tabelBelanjaan.setModel(new DefaultTableModel(dataBelanjaan, colomNames){
            Class[] types = new Class[] {
                Object.class, Boolean.class,Object.class,Object.class,Object.class,Object.class,Object.class,Object.class,Object.class,Object.class
            };
            
            boolean[] canEdit = new boolean [] {
                false, true, false, false,false,false,false,false,false,false
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelBelanjaan.getColumn("Image").setCellRenderer(new LabelRenderer(tabelBelanjaan));
        tabelBelanjaan.setDefaultRenderer(Object.class, new multipleLine());
        tabelBelanjaan.setShowGrid(true);
    }
    
    private void setIsiTabelBarang(JTable tbl, String jns) throws IOException {
        JTableHeader header = tbl.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 15));
        String[] colomNames = {"No", "Image", "Nama Barang", "Ukuran Tersedia", "Warna Tersedia", "Harga Satuan"  };       
        switch(jns) {
            case "Kaos":
                tbl.setModel(new myTableModel(Barang.getBarangTersedia("Kaos"), colomNames));
                break;
            case "Kemeja":
                tbl.setModel(new myTableModel(Barang.getBarangTersedia("Kemeja"), colomNames));
                break;
            case "Jaket":
                tbl.setModel(new myTableModel(Barang.getBarangTersedia("Jaket"), colomNames));
                break;
            case "Celana":
                tbl.setModel(new myTableModel(Barang.getBarangTersedia("Celana"), colomNames));
                break;
            case "Sepatu":
                tbl.setModel(new myTableModel(Barang.getBarangTersedia("Sepatu"), colomNames));
                break;
            case "JamTangan":
                tbl.setModel(new myTableModel(Barang.getBarangTersedia("JamTangan"), colomNames));
                break;
            default:
                break;
                    
        }
        tbl.getColumn("Image").setCellRenderer(new LabelRenderer(tbl));
        tbl.setDefaultRenderer(Object.class, new multipleLine());
        tbl.setShowGrid(true);
    }  
    
    private void setSizeTabelBarang(JTable tbl) {
        TableColumn col1 = tbl.getColumnModel().getColumn(0);
        col1.setPreferredWidth(30);
        TableColumn col2 = tbl.getColumnModel().getColumn(1);
        col2.setPreferredWidth(165);
        TableColumn col3 = tbl.getColumnModel().getColumn(2);
        col3.setPreferredWidth(150);
        TableColumn col4 = tbl.getColumnModel().getColumn(3);
        col4.setPreferredWidth(140);
        TableColumn col5 = tbl.getColumnModel().getColumn(4);
        col5.setPreferredWidth(140);
        TableColumn col6 = tbl.getColumnModel().getColumn(5);
        col6.setPreferredWidth(147);
    }
    
    private void setIsiTabelCheckout() {
        JTableHeader header = tabelCheckout.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 15));
        String[] colomNames = {"Image", "Detail"};    
        tabelCheckout.setModel(new myTableModel(belanjaanCheckout, colomNames));
        tabelCheckout.getColumn("Image").setCellRenderer(new LabelRenderer(tabelCheckout));
        tabelCheckout.setDefaultRenderer(Object.class, new multipleLine());
        tabelCheckout.setShowGrid(true);
    }
    
    private void setSizeTabelCheckout() {
        TableColumn col1 = tabelCheckout.getColumnModel().getColumn(0);
        col1.setPreferredWidth(180);
        TableColumn col2 = tabelCheckout.getColumnModel().getColumn(1);
        col2.setPreferredWidth(350);
    }
    
    private void setIsiTabelPesanan() throws IOException{
        JTableHeader header = tabelPesanan.getTableHeader();
        header.setFont(new Font("Dialog", Font.BOLD, 15));
        String[] colomNames = {"Pesanan", "Metode Bayar", "Pengiriman", "Total Bayaran", "Status"};        

        tabelPesanan.setModel(new myTableModel(Pesanan.getPesanan(), colomNames));
        tabelPesanan.setDefaultRenderer(Object.class, new multipleLine());
        tabelPesanan.setShowGrid(true);
        tabelPesanan.setRowHeight(80);
    }
    
    private void setSizeTabelPesanan() {
        TableColumn col1 = tabelPesanan.getColumnModel().getColumn(0);
        col1.setPreferredWidth(170);
        TableColumn col2 = tabelPesanan.getColumnModel().getColumn(1);
        col2.setPreferredWidth(170);
        TableColumn col3 = tabelPesanan.getColumnModel().getColumn(2);
        col3.setPreferredWidth(170);
        TableColumn col4 = tabelPesanan.getColumnModel().getColumn(3);
        col4.setPreferredWidth(170);
        TableColumn col5 = tabelPesanan.getColumnModel().getColumn(4);
        col5.setPreferredWidth(170);
    }
    
    // Home Page
    private void login() {
        namaDepanRegis.setText("");
        namaBlakangRegis.setText("");
        usernameRegis.setText("");
        passwordRegis.setText("");
        confirmPasswordRegis.setText("");
        
        if(usernameInput.getText().equals("") || passwordInput.getText().equals("")) {
            infoHomeLabel.setText("Masukkan Username Dan Password Anda !");
            usernameInput.setText("");
            passwordInput.setText("");
            return;
        }
        
               
        boolean isLogin;
        String usrname = usernameInput.getText();
        String password = passwordInput.getText();
        try {
            isLogin = Account.login(usrname, password);
            if(isLogin) {
                initTable();
                Account.userLogin = usrname;
                String userFullname = Account.getUser_fullName();
                userFullnameLabel.setText(userFullname);
                infoHomeLabel.setText("");
                usernameInput.setText("");
                passwordInput.setText("");
                
                mainPanel.removeAll();
                mainPanel.repaint();
                mainPanel.revalidate();

                mainPanel.add(inAppPanel);
                mainPanel.repaint();
                mainPanel.revalidate();
                
                mainContainer.removeAll();
                mainContainer.repaint();
                mainContainer.revalidate();

                mainContainer.add(belanjaPanel);
                mainContainer.repaint();
                mainContainer.revalidate();
                
                keluarPanel.removeAll();
                keluarPanel.repaint();
                keluarPanel.revalidate();

                keluarPanel.add(KeluarBtnPanel);
                keluarPanel.repaint();
                keluarPanel.revalidate();
                
            } else {
                infoHomeLabel.setText("Username dan Password Salah !");
                usernameInput.setText("");
                passwordInput.setText("");
            }
            
        } catch (IOException e) {};
    }
    
    private void signInBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInBtnMouseEntered
        signInBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/signin hover.png")));
    }//GEN-LAST:event_signInBtnMouseEntered

    private void signInBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInBtnMouseExited
        signInBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sign in.png")));
    }//GEN-LAST:event_signInBtnMouseExited

    private void signUpBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpBtnMouseEntered
        signUpBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/signup hover.png")));
    }//GEN-LAST:event_signUpBtnMouseEntered

    private void signUpBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpBtnMouseExited
        signUpBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sign up.png")));
    }//GEN-LAST:event_signUpBtnMouseExited

    private void signUpBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signUpBtnMouseClicked
        signUpBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sign up.png")));
        infoHomeLabel.setText("");
        namaDepanRegis.setText("");
        namaBlakangRegis.setText("");
        usernameRegis.setText("");
        passwordRegis.setText("");
        confirmPasswordRegis.setText("");
        usernameInput.setText("");
        passwordInput.setText("");
        infoRegisLabel.setText("");
        
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(registerPage);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_signUpBtnMouseClicked

    private void signInBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInBtnMouseClicked
        signInBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sign in.png")));
        login();
    }//GEN-LAST:event_signInBtnMouseClicked

    // Register Page 
    private void signIn2BtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signIn2BtnMouseExited
        signIn2Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/signin2.png")));
    }//GEN-LAST:event_signIn2BtnMouseExited
   
    private void signIn2BtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signIn2BtnMouseEntered
        signIn2Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/signin2 hover.png")));
    }//GEN-LAST:event_signIn2BtnMouseEntered

    private void signIn2BtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signIn2BtnMouseClicked
        signIn2Btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/signin2.png")));
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(homePage);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_signIn2BtnMouseClicked

    private void registerBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerBtnMouseEntered
        registerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/register hover.png")));
    }//GEN-LAST:event_registerBtnMouseEntered

    private void registerBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerBtnMouseExited
        registerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/register.png")));
    }//GEN-LAST:event_registerBtnMouseExited

    private void registerBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerBtnMouseClicked
        registerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/register.png")));
        isExecute = true;     
        
        if(usernameRegis.getText().equals("") || passwordRegis.getPassword().length == 0 || 
                confirmPasswordRegis.getPassword().length == 0 || namaDepanRegis.getText().equals("") ||
                namaBlakangRegis.getText().equals("")) {
            infoRegisLabel.setText("Harap Mengisi Semua Form !");
            threading(infoRegisLabel);
            return;
        }
        
        boolean isRegis;
        String usr = usernameRegis.getText();
        String usrname = usr.trim();
        char[] pswrd = passwordRegis.getPassword();
        char[] coPswrd = confirmPasswordRegis.getPassword();
        String dpn = namaDepanRegis.getText();
        String namadpn = stringCapital(dpn);
        String blkng = namaBlakangRegis.getText();
        String namaBlkng = stringCapital(blkng);
        
        String str = namadpn+namaBlkng;
        
        if(namadpn.length() == 0) {
            infoRegisLabel.setText("Harap Isi Nama Depan Anda !");
            threading(infoRegisLabel);
            return;
        }
        
        if(str.length() > 30) {
            infoRegisLabel.setText("Nama Depan dan Belakang Hanya Boleh Terdiri Kurang Dari 30 huruf !");
            threading(infoRegisLabel);
            return;
        }
        
        boolean cekNama = Pattern.matches("[a-zA-Z\\s]{2,30}", namadpn+namaBlkng);
        if(!cekNama) {
            infoRegisLabel.setText("Nama Depan atau Belakang Hanya Boleh Terdiri dari Huruf !");
            threading(infoRegisLabel);
            return;
        }
        
        if(usrname.length() < 5) {
            infoRegisLabel.setText("Mohon Masukkan Username lebih dari 5 huruf");
            threading(infoRegisLabel);
            return;
        }
        
        if(usrname.length() > 20) {
            infoRegisLabel.setText("Mohon Masukkan Username Kurang dari 20 huruf");
            threading(infoRegisLabel);
            return;
        }
        
        boolean cekUsername = Pattern.matches("[a-zA-Z0-9]{5,20}", usrname);       
        if(!cekUsername) {
            infoRegisLabel.setText("Username Hanya Boleh Terdiri dari Angka dan Huruf !");
            threading(infoRegisLabel);
            return;
        }
        
        if(pswrd.length < 3) {
            infoRegisLabel.setText("Password Anda Tidak Aman, Mohon Diganti !");
            threading(infoRegisLabel);
            return;
        }
               
        if(cekUsername && cekNama) {
            try {           
                boolean usernameIsExist = Account.usernameIsExist(usrname);
                if(usernameIsExist) {
                    infoRegisLabel.setText("Username sudah ada, mohon Diganti");
                } else {
                    isRegis = Account.register(usrname, pswrd, coPswrd, namadpn, namaBlkng);
                    if(isRegis) {
                        infoHomeLabel.setText("Akun Anda Berhasil Didaftarkan, Silahkan Login");
                        mainPanel.removeAll();
                        mainPanel.repaint();
                        mainPanel.revalidate();

                        mainPanel.add(homePage);
                        mainPanel.repaint();
                        mainPanel.revalidate();
                    } else {
                        infoRegisLabel.setText("Registrasi Gagal, mohon Periksa Kembali Masukkan Anda !");
                    }
                }     
            } catch (IOException e) {};  
            threading(infoRegisLabel);
        }
             
    }//GEN-LAST:event_registerBtnMouseClicked

    // In App Panel
    private void belanjaBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_belanjaBtnMouseEntered
        belanjaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/belanja hover.png")));
    }//GEN-LAST:event_belanjaBtnMouseEntered

    private void belanjaBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_belanjaBtnMouseExited
        belanjaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/belanja.png")));
    }//GEN-LAST:event_belanjaBtnMouseExited

    private void belanjaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_belanjaBtnMouseClicked
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(belanjaPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        keluarPanel.removeAll();
        keluarPanel.repaint();
        keluarPanel.revalidate();
        
        keluarPanel.add(KeluarBtnPanel);
        keluarPanel.repaint();
        keluarPanel.revalidate();
        
        infoKaosLabel.setText("");
        infoCelanaLabel.setText("");
        infoJaketLabel.setText("");
        infoJamTanganLabel.setText("");
        infoKemejaLabel.setText("");
        infoSepatuLabel.setText("");
        isExecute = false;
    }//GEN-LAST:event_belanjaBtnMouseClicked

    private void keranjangBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keranjangBtnMouseClicked
        // TODO add your handling code here:
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(keranjangPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        navigasiKeranjangPanel.removeAll();
        navigasiKeranjangPanel.repaint();
        navigasiKeranjangPanel.revalidate();
        
        keluarPanel.removeAll();
        keluarPanel.repaint();
        keluarPanel.revalidate();
        
        keluarPanel.add(KeluarBtnPanel);
        keluarPanel.repaint();
        keluarPanel.revalidate();
        
        navigasiKeranjangPanel.add(hapusORcheckoutPanel);
        navigasiKeranjangPanel.repaint();
        navigasiKeranjangPanel.revalidate();
        isExecute = false;
        checkoutAll.setSelected(false);
        checkoutAll.setEnabled(true);
        infoHapusLabel.setText("");
        checkoutCount = 0;
        try {
            setIsiTabelBelanjaan();
            setSizeTabelBelanjaan();           
        } catch (IOException e) {};
        
        Locale localId = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localId);
        String strFormat;
        int total = getTotalBayaran();
        if(total > 0) {
            strFormat = formatter.format(total);  
            totalBayaranField.setText("Total bayaran : " + strFormat);
        } else {
            totalBayaranField.setText("Total bayaran : Rp0,00");
        }
    }//GEN-LAST:event_keranjangBtnMouseClicked

    private void keranjangBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keranjangBtnMouseEntered
        keranjangBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keranjang hover.png")));
    }//GEN-LAST:event_keranjangBtnMouseEntered

    private void keranjangBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keranjangBtnMouseExited
        keranjangBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keranjang.png")));
    }//GEN-LAST:event_keranjangBtnMouseExited

    private void pengaturanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pengaturanBtnMouseClicked
        keluarPanel.removeAll();
        keluarPanel.repaint();
        keluarPanel.revalidate();
        
        keluarPanel.add(KeluarBtnPanel);
        keluarPanel.repaint();
        keluarPanel.revalidate();
        
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(homePengaturanPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
    }//GEN-LAST:event_pengaturanBtnMouseClicked

    private void pengaturanBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pengaturanBtnMouseEntered
        pengaturanBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pengaturan hover.png")));
    }//GEN-LAST:event_pengaturanBtnMouseEntered

    private void pengaturanBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pengaturanBtnMouseExited
        pengaturanBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pengaturan.png")));
    }//GEN-LAST:event_pengaturanBtnMouseExited

    private void keluarBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keluarBtnMouseClicked
        keluarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keluar.png")));
        keluarPanel.removeAll();
        keluarPanel.repaint();
        keluarPanel.revalidate();
        
        keluarPanel.add(konfirmasiKeluarPanel);
        keluarPanel.repaint();
        keluarPanel.revalidate();
    }//GEN-LAST:event_keluarBtnMouseClicked

    private void keluarBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keluarBtnMouseEntered
        keluarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keluar hover.png")));
    }//GEN-LAST:event_keluarBtnMouseEntered

    private void keluarBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keluarBtnMouseExited
        keluarBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keluar.png")));
    }//GEN-LAST:event_keluarBtnMouseExited
   
    // Tombol Kategori
    private void kaosBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kaosBtnMouseClicked
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(kaosPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        kaosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kaosBtn.png")));
        Keranjang.noJenis = 2;
        nmrBarangKaos.setText("");
        ukuranPilihanKaos.setText("");
        warnaPilihanKaos.setText("");
        jumlahPesananKaos.setText("");
        refreshComboBox(comboUkuranKaos);
        refreshComboBox(comboWarnaKaos);
    }//GEN-LAST:event_kaosBtnMouseClicked

    private void kaosBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kaosBtnMouseEntered
        kaosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kaosBtn hvr.png")));
    }//GEN-LAST:event_kaosBtnMouseEntered

    private void kaosBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kaosBtnMouseExited
        kaosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kaosBtn.png")));
    }//GEN-LAST:event_kaosBtnMouseExited

    private void jaketBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jaketBtnMouseClicked
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(jaketPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        jaketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/jaketBtn.png")));
        Keranjang.noJenis = 4;
        nmrPesananJaket.setText("");
        ukuranPilJaket.setText("");
        warnaPilJaket.setText("");
        jumlahPesananJaket.setText("");
    }//GEN-LAST:event_jaketBtnMouseClicked

    private void jaketBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jaketBtnMouseEntered
        jaketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/jaketBtn hvr.png")));
    }//GEN-LAST:event_jaketBtnMouseEntered

    private void jaketBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jaketBtnMouseExited
        jaketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/jaketBtn.png")));
    }//GEN-LAST:event_jaketBtnMouseExited

    private void celanaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_celanaBtnMouseClicked
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(celanaPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        celanaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/celanaBtn.png")));
        Keranjang.noJenis = 3;
        nmrBarangCelana.setText("");
        ukuranPilCelana.setText("");
        warnaPilCelana.setText("");
        jumlahPesananCelana.setText("");
        refreshComboBox(comboUkuranCelana);
        refreshComboBox(comboWarnaCelana);
    }//GEN-LAST:event_celanaBtnMouseClicked

    private void celanaBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_celanaBtnMouseEntered
        celanaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/celanaBtn hvr.png")));
    }//GEN-LAST:event_celanaBtnMouseEntered

    private void celanaBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_celanaBtnMouseExited
        celanaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/celanaBtn.png")));
    }//GEN-LAST:event_celanaBtnMouseExited

    private void kemejaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kemejaBtnMouseClicked
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(kemejaPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        kemejaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kemejaBtn.png")));
        Keranjang.noJenis = 1;
        nmrBarangKemejaInput.setText("");
        ukuranBarangKemejaInput.setText("");
        warnaBarangKemejaInput.setText("");
        jumlahPesananBarangKemeja.setText("");
        refreshComboBox(comboUkuranKaos);
        refreshComboBox(comboWarnaKaos);
    }//GEN-LAST:event_kemejaBtnMouseClicked

    private void kemejaBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kemejaBtnMouseEntered
        kemejaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kemejaBtn hvr.png")));
    }//GEN-LAST:event_kemejaBtnMouseEntered

    private void kemejaBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kemejaBtnMouseExited
        kemejaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kemejaBtn.png")));
    }//GEN-LAST:event_kemejaBtnMouseExited

    private void sepatuBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sepatuBtnMouseClicked
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(sepatuPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        sepatuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sepatuBtn.png")));
        Keranjang.noJenis = 6;
        nmrBarangSepatu.setText("");
        ukuranPilSepatu.setText("");
        warnaPilSepatu.setText("");
        jumlahPesananSepatu.setText("");
    }//GEN-LAST:event_sepatuBtnMouseClicked

    private void sepatuBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sepatuBtnMouseEntered
        sepatuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sepatuBtn hvr.png")));
    }//GEN-LAST:event_sepatuBtnMouseEntered

    private void sepatuBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sepatuBtnMouseExited
        sepatuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/sepatuBtn.png")));
    }//GEN-LAST:event_sepatuBtnMouseExited

    private void jamTanganBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jamTanganBtnMouseClicked
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(jamTanganPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        jamTanganBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/jamBtn.png")));
        Keranjang.noJenis = 5;
        nmrBarangJamtangan.setText("");
        ukuranPilJamTangan.setText("");
        warnaPilJamtangan.setText("");
        jumlahPesananJamTangan.setText("");
    }//GEN-LAST:event_jamTanganBtnMouseClicked

    private void jamTanganBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jamTanganBtnMouseEntered
        jamTanganBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/jamBtn hvr.png")));
    }//GEN-LAST:event_jamTanganBtnMouseEntered

    private void jamTanganBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jamTanganBtnMouseExited
        jamTanganBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/jamBtn.png")));
    }//GEN-LAST:event_jamTanganBtnMouseExited

    //Ke Keranjang Button   
    public void keKeranjang(JTextField nmrbarang, JTextField ukuran, JTextField warna, JTextField jumlahpesanan, JLabel infoLabel) {
        isExecute = true;
        
        if(Keranjang.noJenis != 5) {
            if(nmrbarang.getText().equals("") || 
                ukuran.getText().equals("") ||
                warna.getText().equals("") ||
                jumlahpesanan.getText().equals(""))  {
                infoLabel.setText("Harap Mengisi Semua Kolom");
                threading(infoLabel);
                return;
            }
        } else {
            if(nmrbarang.getText().equals("") || 
                warna.getText().equals("") ||
                jumlahpesanan.getText().equals(""))  {
                infoLabel.setText("Harap Mengisi Semua Kolom");
                threading(infoLabel);
                return;
            }
        }
        
        if(jumlahPesanan == 0) {
            infoLabel.setText("Harap Mengisi Jumlah Pesanan");
            threading(infoLabel);
            return;
        }
        
        String ukuranPil = ukuran.getText();
        String warnaPil = warna.getText();
        int jumlah = Integer.parseInt(jumlahpesanan.getText());
        
        Keranjang.getFile();
        try {
            Keranjang.masukkanKeKaranjang(Keranjang.namaBarang, ukuranPil, warnaPil, jumlah, Keranjang.hargaSatuan);
            infoLabel.setText("Pesanan Berhasil Dimasukkan Ke Keranjang");
            nmrbarang.setText("");
            ukuran.setText("");
            warna.setText("");
            jumlahpesanan.setText("");       
        } catch (IOException e) {};
        threading(infoLabel);
    }
    
    private void keKeranjangKaosBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangKaosBtnMouseClicked
        keKeranjang(nmrBarangKaos, ukuranPilihanKaos, warnaPilihanKaos, jumlahPesananKaos, infoKaosLabel);
    }//GEN-LAST:event_keKeranjangKaosBtnMouseClicked

    private void keKeranjangKaosBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangKaosBtnMouseEntered
        keKeranjangKaosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang hover.png")));
    }//GEN-LAST:event_keKeranjangKaosBtnMouseEntered

    private void keKeranjangKaosBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangKaosBtnMouseExited
        keKeranjangKaosBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png")));
    }//GEN-LAST:event_keKeranjangKaosBtnMouseExited

    private void keKeranjangKemejaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangKemejaBtnMouseClicked
        keKeranjang(nmrBarangKemejaInput, ukuranBarangKemejaInput, warnaBarangKemejaInput, jumlahPesananBarangKemeja, infoKemejaLabel);
    }//GEN-LAST:event_keKeranjangKemejaBtnMouseClicked

    private void keKeranjangKemejaBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangKemejaBtnMouseEntered
        keKeranjangKemejaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang hover.png")));
    }//GEN-LAST:event_keKeranjangKemejaBtnMouseEntered

    private void keKeranjangKemejaBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangKemejaBtnMouseExited
        keKeranjangKemejaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png")));
    }//GEN-LAST:event_keKeranjangKemejaBtnMouseExited

    private void keKeranjangCelanaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangCelanaBtnMouseClicked
        keKeranjang(nmrBarangCelana, ukuranPilCelana, warnaPilCelana, jumlahPesananCelana, infoCelanaLabel);
    }//GEN-LAST:event_keKeranjangCelanaBtnMouseClicked

    private void keKeranjangCelanaBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangCelanaBtnMouseEntered
        keKeranjangCelanaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang hover.png")));
    }//GEN-LAST:event_keKeranjangCelanaBtnMouseEntered

    private void keKeranjangCelanaBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangCelanaBtnMouseExited
        keKeranjangCelanaBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png")));
    }//GEN-LAST:event_keKeranjangCelanaBtnMouseExited

    private void keKeranjangJaketBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangJaketBtnMouseClicked
        keKeranjang(nmrPesananJaket, ukuranPilJaket, warnaPilJaket, jumlahPesananJaket, infoJaketLabel);
    }//GEN-LAST:event_keKeranjangJaketBtnMouseClicked

    private void keKeranjangJaketBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangJaketBtnMouseEntered
        keKeranjangJaketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang hover.png")));
    }//GEN-LAST:event_keKeranjangJaketBtnMouseEntered

    private void keKeranjangJaketBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangJaketBtnMouseExited
        keKeranjangJaketBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png")));
    }//GEN-LAST:event_keKeranjangJaketBtnMouseExited

    private void keKeranjangSepatuBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangSepatuBtnMouseClicked
        keKeranjang(nmrBarangSepatu, ukuranPilSepatu, warnaPilSepatu, jumlahPesananSepatu, infoSepatuLabel);
    }//GEN-LAST:event_keKeranjangSepatuBtnMouseClicked

    private void keKeranjangSepatuBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangSepatuBtnMouseEntered
        keKeranjangSepatuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang hover.png")));
    }//GEN-LAST:event_keKeranjangSepatuBtnMouseEntered

    private void keKeranjangSepatuBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangSepatuBtnMouseExited
        keKeranjangSepatuBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png")));
    }//GEN-LAST:event_keKeranjangSepatuBtnMouseExited

    private void keKeranjangJamTanganBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangJamTanganBtnMouseClicked
        keKeranjang(nmrBarangJamtangan, ukuranPilJamTangan, warnaPilJamtangan, jumlahPesananJamTangan, infoJamTanganLabel);
    }//GEN-LAST:event_keKeranjangJamTanganBtnMouseClicked

    private void keKeranjangJamTanganBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangJamTanganBtnMouseEntered
        keKeranjangJamTanganBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang hover.png")));
    }//GEN-LAST:event_keKeranjangJamTanganBtnMouseEntered

    private void keKeranjangJamTanganBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keKeranjangJamTanganBtnMouseExited
        keKeranjangJamTanganBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/keKeranjang.png")));
    }//GEN-LAST:event_keKeranjangJamTanganBtnMouseExited

    //checkout All checkBox
    private void checkoutAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkoutAllActionPerformed
        checkoutCount = 0; 
        if(checkoutAll.isSelected()) {
            for(int i = 0; i < tabelBelanjaan.getModel().getRowCount(); i++) {
                tabelBelanjaan.setValueAt(true, i, 1);
                dataBelanjaan[i][1] = true;
                checkoutCount++;
            }
        } else {
            for(int i = 0; i < tabelBelanjaan.getModel().getRowCount(); i++) {
                tabelBelanjaan.setValueAt(false, i, 1);
                dataBelanjaan[i][1] = false;
            }
        }
        
        Locale localId = new Locale("in", "ID");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(localId);
        String strFormat;
        int total = getTotalBayaran();
        if(total > 0) {
            strFormat = formatter.format(total);  
            totalBayaranField.setText("Total bayaran : " + strFormat);
        } else {
            totalBayaranField.setText("Total bayaran : Rp0,00");
        }
    }//GEN-LAST:event_checkoutAllActionPerformed

    //Tombol Navigasi Keranjang
    private void gotoHapusPesananPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gotoHapusPesananPanelMouseClicked
        navigasiKeranjangPanel.removeAll();
        navigasiKeranjangPanel.repaint();
        navigasiKeranjangPanel.revalidate();
        
        navigasiKeranjangPanel.add(hapusPesananPanel);
        navigasiKeranjangPanel.repaint();
        navigasiKeranjangPanel.revalidate();
        gotoHapusPesananPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/hapusPesanan.png")));
        checkoutAll.setSelected(false);
        checkoutAll.setEnabled(false);
    }//GEN-LAST:event_gotoHapusPesananPanelMouseClicked

    private void gotoHapusPesananPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gotoHapusPesananPanelMouseEntered
        gotoHapusPesananPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/hapusPesanan hover.png")));
    }//GEN-LAST:event_gotoHapusPesananPanelMouseEntered

    private void gotoHapusPesananPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gotoHapusPesananPanelMouseExited
        gotoHapusPesananPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/hapusPesanan.png")));
    }//GEN-LAST:event_gotoHapusPesananPanelMouseExited

    private void gotoCheckoutPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gotoCheckoutPanelMouseClicked
        if(checkoutCount > 0) {          
            navigasiKeranjangPanel.removeAll();
            navigasiKeranjangPanel.repaint();
            navigasiKeranjangPanel.revalidate();
            
            navigasiKeranjangPanel.add(konfirmasiPembayaranPanel);
            navigasiKeranjangPanel.repaint();
            navigasiKeranjangPanel.revalidate();
                   
        } else {
            infoKeranjangLabel.setText("Harap Ceklis Barang Yang Ingin Di Checkout !");
            isExecute = true;
            threading(infoKeranjangLabel);
        }
        
    }//GEN-LAST:event_gotoCheckoutPanelMouseClicked

    private void gotoCheckoutPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gotoCheckoutPanelMouseEntered
        gotoCheckoutPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/checkout hover.png")));
    }//GEN-LAST:event_gotoCheckoutPanelMouseEntered

    private void gotoCheckoutPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gotoCheckoutPanelMouseExited
        gotoCheckoutPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/checkout.png")));
    }//GEN-LAST:event_gotoCheckoutPanelMouseExited

    //hapus pesanan button
    private void hapusPesananBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusPesananBtnActionPerformed
        int nomorPesanan = 0;
        isExecute = true;
        try {
            nomorPesanan = Integer.parseInt(nmrPesananHapusInput.getText());
        } catch (Exception e) {
            infoHapusLabel.setText("Masukkan nomor pesanan yang valid !");
            nmrPesananHapusInput.setText("");
            threading(infoHapusLabel);
            return;
        } 
        
        try {
            boolean isDelete = Keranjang.hapusBelanjaan(nomorPesanan);
            if(isDelete) {
                setIsiTabelBelanjaan();
                
                Locale localId = new Locale("in", "ID");
                NumberFormat formatter = NumberFormat.getCurrencyInstance(localId);
                String strFormat;
                int total = getTotalBayaran();
                if(total > 0) {
                    strFormat = formatter.format(total);  
                    totalBayaranField.setText("Total bayaran : " + strFormat);
                } else {
                    totalBayaranField.setText("Total bayaran : Rp0,00");
                }
                
                infoHapusLabel.setText("Pesanan Berhasil Dihapus !");
                setIsiTabelBelanjaan();
                setSizeTabelBelanjaan();               
                nmrPesananHapusInput.setText("");
            } else {
                infoHapusLabel.setText("Nomor Pesanan Tidak Ditemukan !");
                nmrPesananHapusInput.setText("");
            }
            threading(infoHapusLabel);
        } catch (IOException e) {};
    }//GEN-LAST:event_hapusPesananBtnActionPerformed

    //Combo Box
    private void listBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listBayarActionPerformed
        
        if(listBayar.getSelectedIndex() == 1) {
            infoBayarLabel.setText("Silahkan Pilih Bank Anda");
            isExecute = true;
            threading(infoBayarLabel);
            panelMetodeBayar.removeAll();
            panelMetodeBayar.repaint();
            panelMetodeBayar.revalidate();
            
            panelMetodeBayar.add(pilihanTransfer);
            panelMetodeBayar.repaint();
            panelMetodeBayar.revalidate();
        } else {            
            metodePembayaranLabel.setText(((comboImageText)listBayar.getSelectedItem()).getText());
        }
        
    }//GEN-LAST:event_listBayarActionPerformed

    private void listBankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listBankActionPerformed
        if(listBank.getSelectedIndex() == 4) {
            panelMetodeBayar.removeAll();
            panelMetodeBayar.repaint();
            panelMetodeBayar.revalidate();
            
            panelMetodeBayar.add(pilihanMetode);
            panelMetodeBayar.repaint();
            panelMetodeBayar.revalidate();
        } else {           
            metodePembayaranLabel.setText("Tranfer - " + ((comboImageText)listBank.getSelectedItem()).getText());
        }
    }//GEN-LAST:event_listBankActionPerformed

    private void listPengirimanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listPengirimanActionPerformed
        opsiPengiriman.setText(((comboImageText)listPengiriman.getSelectedItem()).getText());
        int ongkos[] = {20000,19000,18000,17000};
        int index = listPengiriman.getSelectedIndex();
        switch (index) {
            case 0:
                subtotalPengiriman.setText(formatRupiah(ongkos[0]));
                break;
            case 1:
                subtotalPengiriman.setText(formatRupiah(ongkos[1]));
                break;
            case 2:
                subtotalPengiriman.setText(formatRupiah(ongkos[2]));
                break;
            case 3:
                subtotalPengiriman.setText(formatRupiah(ongkos[3]));
                break;
            default:
                subtotalPengiriman.setText(formatRupiah(0));
                break;
        }
        
        String str1 = subtotalBarang.getText().replace("Rp", "").replace(".", "").replace(",00", "");
        String str2 = subtotalPengiriman.getText().replace("Rp", "").replace(".", "").replace(",00", "");
        int subBarang = Integer.parseInt(str1);
        int subPengiriman = Integer.parseInt(str2);
        int totalBayar = subBarang + subPengiriman;
        String str = potonganHargalabel.getText();
        if(!str.equals("-")) {
            int potHarga = Integer.parseInt(str.replace("Rp", "").replace(".", "").replace(",00", ""));
            totalBayarLabel.setText(formatRupiah(totalBayar-potHarga));
        } else {
            totalBayarLabel.setText(formatRupiah(totalBayar));
        }       
        
    }//GEN-LAST:event_listPengirimanActionPerformed

    //check Voucher
    private void checkVoucherBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkVoucherBtnActionPerformed
        isExecute = true;        
        String input = kodeVoucher.getText();
        
        if(input.length() > 0) {
            String[] voucher = {"ahmad", "store", "151101"};
            int[] dskn = {5,10,15};
            int diskon = 0;
            boolean isExist = false;
            for(int i = 0; i < voucher.length; i++) {
                if(input.equals(voucher[i])) {
                    isExist = true;
                    diskon = dskn[i];
                    float flt = diskon/100f;
                    float potonganHarga = getTotalBayaran() * flt;
                    float totalBayar = getTotalBayaran() - potonganHarga; 
                    potonganHargalabel.setText(formatRupiah((int)potonganHarga));
                    String subKirim = subtotalPengiriman.getText();
                    if(!subKirim.equals("-")) {
                        int ongkir = Integer.parseInt(subKirim.replace("Rp", "").replace(".", "").replace(",00", ""));
                        totalBayarLabel.setText(formatRupiah((int)totalBayar + ongkir));
                    } else {
                        totalBayarLabel.setText(formatRupiah((int)totalBayar));
                    }     
                    infoBayarLabel.setText("Anda Mendapatkan Diskon Sebesar " + diskon + "%");                  
                    threading(infoBayarLabel);
                    break;
                }
            }
            if(!isExist) {
                kodeVoucher.setText("");
                infoBayarLabel.setText("Kode Voucher Tidak Ditemukan !");                  
                threading(infoBayarLabel);
            }
        }
        
        
        
    }//GEN-LAST:event_checkVoucherBtnActionPerformed

    //Bayar Panel
    private void kembaliBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kembaliBtnMouseClicked
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(inAppPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(keranjangPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
        
        navigasiKeranjangPanel.removeAll();
        navigasiKeranjangPanel.repaint();
        navigasiKeranjangPanel.revalidate();
        
        navigasiKeranjangPanel.add(hapusORcheckoutPanel);
        navigasiKeranjangPanel.repaint();
        navigasiKeranjangPanel.revalidate();
    }//GEN-LAST:event_kembaliBtnMouseClicked

    private void kembaliBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kembaliBtnMouseEntered
        kembaliBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kembali hover.png")));
    }//GEN-LAST:event_kembaliBtnMouseEntered

    private void kembaliBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kembaliBtnMouseExited
        kembaliBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/kembali.png")));
    }//GEN-LAST:event_kembaliBtnMouseExited

    private void buatPesananBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buatPesananBtnMouseClicked
        isExecute = true;
        if(metodePembayaranLabel.getText().equals("-") && opsiPengiriman.getText().equals("-")) {
            infoBayarLabel.setText("Harap Mengisi Metode Pembayaran dan Opsi Pengiriman !");
        } else {
            if(metodePembayaranLabel.getText().equals("-")) {
                infoBayarLabel.setText("Harap Mengisi Metode Pembayaran !");
            }
            
            else if(opsiPengiriman.getText().equals("-")) {
                infoBayarLabel.setText("Harap Mengisi Opsi Pengiriman !");
            } else {
                try {
                    Pesanan.masukkanKePesanan(belanjaanCheckout, metodePembayaranLabel.getText(), opsiPengiriman.getText(), totalBayarLabel.getText());
                    mainPanel.removeAll();
                    mainPanel.repaint();
                    mainPanel.revalidate();

                    mainPanel.add(inAppPanel);
                    mainPanel.repaint();
                    mainPanel.revalidate();

                    mainContainer.removeAll();
                    mainContainer.repaint();
                    mainContainer.revalidate();

                    mainContainer.add(pesananPanel);
                    mainContainer.repaint();
                    mainContainer.revalidate();
                    try {
                        setIsiTabelPesanan();
                        setSizeTabelPesanan();           
                    } catch (IOException e) {};
                    JOptionPane.showMessageDialog(null, "Pemesanan Anda Berhasil !");
                } catch (IOException e) {};               
            }
        }
        threading(infoBayarLabel);       
    }//GEN-LAST:event_buatPesananBtnMouseClicked

    private void buatPesananBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buatPesananBtnMouseEntered
        buatPesananBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pesan hover.png")));
    }//GEN-LAST:event_buatPesananBtnMouseEntered

    private void buatPesananBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buatPesananBtnMouseExited
        buatPesananBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pesan.png")));
    }//GEN-LAST:event_buatPesananBtnMouseExited

    //goto Bayar Panel
    private void gotoBayarPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gotoBayarPanelActionPerformed
        String detail;
        String[] colomNames = {"No","", "Image", "Jenis", "Barang", "Ukuran", "Warna", "Jumlah", "Harga Satuan", "Total Harga"};
        belanjaanCheckout = new Object[checkoutCount][2];
        int row = 0;
        
        for(int i =0; i < dataBelanjaan.length; i++) {
            detail = "";
            if((boolean)dataBelanjaan[i][1]) {
                for(int j = 0; j < 10; j++) {
                    isCheckout = true;
                    if(j == 3) {
                        detail += "Jenis = " + dataBelanjaan[i][3] + ", ";
                    } else if(j == 4) {
                        detail += "Nama Barang = " + dataBelanjaan[i][4] + ", ";
                    } else if (j == 5) {
                        detail += "Ukuran = " + dataBelanjaan[i][5] + ", ";
                    } else if (j == 6) {
                        detail += "Warna = " + dataBelanjaan[i][6] + ", ";
                    } else if (j == 7) {
                        detail += "Jumlah = " + dataBelanjaan[i][7] + ", ";
                    } else if (j == 9) {
                        detail += "Total Harga = " + dataBelanjaan[i][9] + ".";
                    }
                    belanjaanCheckout[row][0] = tabelBelanjaan.getValueAt(i, 2);
                    belanjaanCheckout[row][1] = detail;   
                    
                } 
                if(checkoutCount > 1) {
                    row++;
                }
            }                    
        }        
        setIsiTabelCheckout();
        setSizeTabelCheckout();     
        subtotalBarang.setText(formatRupiah(getTotalBayaran()));
        metodePembayaranLabel.setText("-");       
        opsiPengiriman.setText("-");
        kodeVoucher.setText("");
        subtotalPengiriman.setText("-");
        potonganHargalabel.setText("-");
        totalBayarLabel.setText("-");
        
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(bayarPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        infoBayarLabel.setText("Silahkan Pilih Metode Pembayaran dan Pengiriman");
        isExecute = true;
        threading(infoBayarLabel);
    }//GEN-LAST:event_gotoBayarPanelActionPerformed

    private void tidakKeluarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidakKeluarBtnActionPerformed
        keluarPanel.removeAll();
        keluarPanel.repaint();
        keluarPanel.revalidate();
        
        keluarPanel.add(KeluarBtnPanel);
        keluarPanel.repaint();
        keluarPanel.revalidate();
    }//GEN-LAST:event_tidakKeluarBtnActionPerformed

    private void yaKeluarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yaKeluarBtnActionPerformed
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();
        
        mainPanel.add(homePage);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_yaKeluarBtnActionPerformed

    //Pengaturan Panel
    private void editNamaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editNamaBtnActionPerformed
        namaDepanLabel.setText(Account.namaDepan);
        namaBelakangLabel.setText(Account.namaBelakang);
        mainContainer.removeAll();
        mainContainer.repaint();;
        mainContainer.revalidate();
        
        mainContainer.add(editNamaPanel);
        mainContainer.repaint();;
        mainContainer.revalidate();       
    }//GEN-LAST:event_editNamaBtnActionPerformed

    private void gantiPasswordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gantiPasswordBtnActionPerformed
        mainContainer.removeAll();
        mainContainer.repaint();;
        mainContainer.revalidate();
        
        mainContainer.add(editPasswordPanel);
        mainContainer.repaint();;
        mainContainer.revalidate();     
    }//GEN-LAST:event_gantiPasswordBtnActionPerformed

    private void konfirmasiEditPswrdBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirmasiEditPswrdBtnActionPerformed
        boolean isChange = false;
        isExecute = true;
        String pswrdLamaInput = passwordLamaInput.getText();
        Account.passwordbaruInput = passwordBaruInput.getText();
        Account.konPasswordbaruInput = konPasswordBaruInput.getText();
        
        if(pswrdLamaInput.length() == 0) {
            infoPasswordLabel.setText("Mohon Masukkan Password Lama Anda !");
            threading(infoPasswordLabel);
            return;
        }
        
        else if(Account.passwordbaruInput.length() == 0) {
            infoPasswordLabel.setText("Mohon Masukkan Password Baru Anda !");
            threading(infoPasswordLabel);
            return;
        }
        
        else if(Account.konPasswordbaruInput.length() == 0) {
            infoPasswordLabel.setText("Mohon Konfirmasi Password Baru Anda !");
            threading(infoPasswordLabel);
            return;
        }
        
        else if(Account.passwordbaruInput.length() < 3) {
            infoPasswordLabel.setText("Password Baru Anda Kurang Aman, Mohon Diganti !");
            threading(infoPasswordLabel);
            return;
        }  
        
        try {
            isChange = Account.gantiPassword(pswrdLamaInput);
            if(isChange) {
                infoPasswordLabel.setText("Password Anda Berhasil Diubah");
                threading(infoPasswordLabel);
                passwordBaruInput.setText("");
                passwordLamaInput.setText("");
                konPasswordBaruInput.setText("");
            } else {
                infoPasswordLabel.setText("Password Anda Gagal Diubah");
                threading(infoPasswordLabel);
            }
        } catch (IOException e){};
              
    }//GEN-LAST:event_konfirmasiEditPswrdBtnActionPerformed

    private void konfirmasiUbahnamaBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_konfirmasiUbahnamaBtnActionPerformed
        String namadpnBaru = namaDepanBaru.getText();
        String namablkngBaru = namaBelakangBaru.getText();
        isExecute = true;
        String str = namadpnBaru+namablkngBaru;
        boolean cekNama = Pattern.matches("[a-zA-Z\\s]{2,30}", namadpnBaru+namablkngBaru);
        if(namadpnBaru.length() == 0) {
            infoUbahNamaLabel.setText("Harap Isi Nama Depan Anda !");
        } else if(str.length() > 30) {
            infoUbahNamaLabel.setText("Nama Depan dan Belakang Hanya Boleh Terdiri Kurang Dari 30 Huruf");
        } else if(!cekNama) {
            infoUbahNamaLabel.setText("Nama Depan dan Belakang Hanya Boleh Terdiri Dari Huruf");
        } else {
            try {
                 boolean isChange = Account.gantiNama(stringCapital(namadpnBaru), stringCapital(namablkngBaru));
                 if(isChange) {
                     infoUbahNamaLabel.setText("Nama Anda Berhasil Diubah");  
                     userFullnameLabel.setText(Account.getUser_fullName());
                     namaDepanLabel.setText(Account.namaDepan);
                     namaBelakangLabel.setText(Account.namaBelakang);
                     namaDepanBaru.setText("");
                     namaBelakangBaru.setText("");
                 }
            } catch (IOException e) {};          
        }
        threading(infoUbahNamaLabel);  
    }//GEN-LAST:event_konfirmasiUbahnamaBtnActionPerformed

    //in app panel - Pesanan Button
    private void pesananBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pesananBtnMouseClicked
       
        try {
            setIsiTabelPesanan();
            setSizeTabelPesanan();           
        } catch (IOException e) {};
        
        mainContainer.removeAll();
        mainContainer.repaint();
        mainContainer.revalidate();
        
        mainContainer.add(pesananPanel);
        mainContainer.repaint();
        mainContainer.revalidate();
    }//GEN-LAST:event_pesananBtnMouseClicked

    private void pesananBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pesananBtnMouseEntered
        pesananBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pesanan hover.png")));
    }//GEN-LAST:event_pesananBtnMouseEntered

    private void pesananBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pesananBtnMouseExited
        pesananBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/pesanan.png")));
    }//GEN-LAST:event_pesananBtnMouseExited

    private void tabelPesananMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPesananMouseEntered

    }//GEN-LAST:event_tabelPesananMouseEntered

    private void usernameInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameInputKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            passwordInput.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            passwordInput.requestFocus();
        }
    }//GEN-LAST:event_usernameInputKeyPressed

    private void passwordInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordInputKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            usernameInput.requestFocus();
        }
    }//GEN-LAST:event_passwordInputKeyPressed

    private void ukuranPilCelanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ukuranPilCelanaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ukuranPilCelanaActionPerformed
      
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Main().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel KeluarBtnPanel;
    private javax.swing.JLabel UI;
    private javax.swing.JPanel bayarPanel;
    private javax.swing.JLabel belanjaBtn;
    private javax.swing.JPanel belanjaPanel;
    private javax.swing.JLabel buatPesananBtn;
    private javax.swing.JLabel celanaBtn;
    private javax.swing.JPanel celanaPanel;
    private javax.swing.JButton checkVoucherBtn;
    private javax.swing.JCheckBox checkoutAll;
    private javax.swing.JComboBox<String> comboUkuranCelana;
    private javax.swing.JComboBox<String> comboUkuranJaket;
    private javax.swing.JComboBox<String> comboUkuranJam;
    private javax.swing.JComboBox<String> comboUkuranKaos;
    private javax.swing.JComboBox<String> comboUkuranKemeja;
    private javax.swing.JComboBox<String> comboUkuranSepatu;
    private javax.swing.JComboBox<String> comboWarnaCelana;
    private javax.swing.JComboBox<String> comboWarnaJaket;
    private javax.swing.JComboBox<String> comboWarnaJam;
    private javax.swing.JComboBox<String> comboWarnaKaos;
    private javax.swing.JComboBox<String> comboWarnaKemeja;
    private javax.swing.JComboBox<String> comboWarnaSepatu;
    private javax.swing.JPasswordField confirmPasswordRegis;
    private javax.swing.JButton editNamaBtn;
    private javax.swing.JPanel editNamaPanel;
    private javax.swing.JPanel editPasswordPanel;
    private javax.swing.JButton gantiPasswordBtn;
    private javax.swing.JButton gotoBayarPanel;
    private javax.swing.JLabel gotoCheckoutPanel;
    private javax.swing.JLabel gotoHapusPesananPanel;
    private javax.swing.JPanel hapusORcheckoutPanel;
    private javax.swing.JButton hapusPesananBtn;
    private javax.swing.JPanel hapusPesananPanel;
    private javax.swing.JPanel homePage;
    private javax.swing.JPanel homePengaturanPanel;
    private javax.swing.JPanel inAppPanel;
    private javax.swing.JLabel infoBayarLabel;
    private javax.swing.JLabel infoCelanaLabel;
    private javax.swing.JLabel infoHapusLabel;
    private javax.swing.JLabel infoHomeLabel;
    private javax.swing.JLabel infoJaketLabel;
    private javax.swing.JLabel infoJamTanganLabel;
    private javax.swing.JLabel infoKaosLabel;
    private javax.swing.JLabel infoKemejaLabel;
    private javax.swing.JLabel infoKeranjangLabel;
    private javax.swing.JLabel infoPasswordLabel;
    private javax.swing.JLabel infoRegisLabel;
    private javax.swing.JLabel infoSepatuLabel;
    private javax.swing.JLabel infoUbahNamaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JLabel jaketBtn;
    private javax.swing.JPanel jaketPanel;
    private javax.swing.JLabel jamTanganBtn;
    private javax.swing.JPanel jamTanganPanel;
    private javax.swing.JTextField jumlahPesananBarangKemeja;
    private javax.swing.JTextField jumlahPesananCelana;
    private javax.swing.JTextField jumlahPesananJaket;
    private javax.swing.JTextField jumlahPesananJamTangan;
    private javax.swing.JTextField jumlahPesananKaos;
    private javax.swing.JTextField jumlahPesananSepatu;
    private javax.swing.JLabel kaosBtn;
    private javax.swing.JPanel kaosPanel;
    private javax.swing.JLabel keKeranjangCelanaBtn;
    private javax.swing.JLabel keKeranjangJaketBtn;
    private javax.swing.JLabel keKeranjangJamTanganBtn;
    private javax.swing.JLabel keKeranjangKaosBtn;
    private javax.swing.JLabel keKeranjangKemejaBtn;
    private javax.swing.JLabel keKeranjangSepatuBtn;
    private javax.swing.JLabel keluarBtn;
    private javax.swing.JPanel keluarPanel;
    private javax.swing.JLabel kembaliBtn;
    private javax.swing.JLabel kemejaBtn;
    private javax.swing.JPanel kemejaPanel;
    private javax.swing.JLabel keranjangBtn;
    private javax.swing.JPanel keranjangPanel;
    private javax.swing.JTextField kodeVoucher;
    private javax.swing.JPasswordField konPasswordBaruInput;
    private javax.swing.JButton konfirmasiEditPswrdBtn;
    private javax.swing.JPanel konfirmasiKeluarPanel;
    private javax.swing.JPanel konfirmasiPembayaranPanel;
    private javax.swing.JButton konfirmasiUbahnamaBtn;
    private javax.swing.JButton kurangCelanaBtn;
    private javax.swing.JButton kurangJaketBtn;
    private javax.swing.JButton kurangJamBtn;
    private javax.swing.JButton kurangKaosbtn;
    private javax.swing.JButton kurangKemejaBtn;
    private javax.swing.JButton kurangSepatuBtn;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JComboBox<String> listBank;
    private javax.swing.JComboBox<String> listBayar;
    private javax.swing.JComboBox<String> listPengiriman;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel metodePembayaranLabel;
    private javax.swing.JTextField namaBelakangBaru;
    private javax.swing.JLabel namaBelakangLabel;
    private javax.swing.JTextField namaBlakangRegis;
    private javax.swing.JTextField namaDepanBaru;
    private javax.swing.JLabel namaDepanLabel;
    private javax.swing.JTextField namaDepanRegis;
    private javax.swing.JPanel navigasiKeranjangPanel;
    private javax.swing.JTextField nmrBarangCelana;
    private javax.swing.JTextField nmrBarangJamtangan;
    private javax.swing.JTextField nmrBarangKaos;
    private javax.swing.JTextField nmrBarangKemejaInput;
    private javax.swing.JTextField nmrBarangSepatu;
    private javax.swing.JTextField nmrPesananHapusInput;
    private javax.swing.JTextField nmrPesananJaket;
    private javax.swing.JLabel opsiPengiriman;
    private javax.swing.JPanel panelMetodeBayar;
    private javax.swing.JPasswordField passwordBaruInput;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JPasswordField passwordLamaInput;
    private javax.swing.JPasswordField passwordRegis;
    private javax.swing.JLabel pengaturanBtn;
    private javax.swing.JPanel pengaturanPanel;
    private javax.swing.JLabel pesananBtn;
    private javax.swing.JPanel pesananPanel;
    private javax.swing.JPanel pilihanMetode;
    private javax.swing.JPanel pilihanTransfer;
    private javax.swing.JLabel potonganHargalabel;
    private javax.swing.JLabel registerBtn;
    private javax.swing.JPanel registerPage;
    private javax.swing.JLabel sepatuBtn;
    private javax.swing.JPanel sepatuPanel;
    private javax.swing.JLabel signIn2Btn;
    private javax.swing.JLabel signInBtn;
    private javax.swing.JLabel signUpBtn;
    private javax.swing.JLabel subtotalBarang;
    private javax.swing.JLabel subtotalPengiriman;
    private javax.swing.JTable tabelBelanjaan;
    private javax.swing.JTable tabelCelana;
    private javax.swing.JTable tabelCheckout;
    private javax.swing.JTable tabelJaket;
    private javax.swing.JTable tabelJamTangan;
    private javax.swing.JTable tabelKaos;
    private javax.swing.JTable tabelKemeja;
    private javax.swing.JTable tabelPesanan;
    private javax.swing.JTable tabelSepatu;
    private javax.swing.JButton tambahCelanaBtn;
    private javax.swing.JButton tambahJaketBtn;
    private javax.swing.JButton tambahJamBtn;
    private javax.swing.JButton tambahKaosBtn;
    private javax.swing.JButton tambahKemejaBtn;
    private javax.swing.JButton tambahSepatuBtn;
    private javax.swing.JButton tidakKeluarBtn;
    private javax.swing.JPanel tombolNavigasi;
    private javax.swing.JLabel totalBayarLabel;
    private javax.swing.JTextField totalBayaranField;
    private javax.swing.JPanel totalBayarpanel;
    private javax.swing.JTextField ukuranBarangKemejaInput;
    private javax.swing.JTextField ukuranPilCelana;
    private javax.swing.JTextField ukuranPilJaket;
    private javax.swing.JTextField ukuranPilJamTangan;
    private javax.swing.JTextField ukuranPilSepatu;
    private javax.swing.JTextField ukuranPilihanKaos;
    private javax.swing.JLabel userFullnameLabel;
    private javax.swing.JTextField usernameInput;
    private javax.swing.JTextField usernameRegis;
    private javax.swing.JTextField warnaBarangKemejaInput;
    private javax.swing.JTextField warnaPilCelana;
    private javax.swing.JTextField warnaPilJaket;
    private javax.swing.JTextField warnaPilJamtangan;
    private javax.swing.JTextField warnaPilSepatu;
    private javax.swing.JTextField warnaPilihanKaos;
    private javax.swing.JButton yaKeluarBtn;
    // End of variables declaration//GEN-END:variables
}
