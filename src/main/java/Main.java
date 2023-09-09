import struct.Node;
import util.analizer.PcapAnalyzer;
import util.analizer.PortScanner;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int menu = 0;
        String filePath = "";
        String macTarget = "";
        if (args.length == 2) {
            filePath = args[0];
            macTarget = args[1];
        } else {
            filePath = "src/main/resources/unfiltered_union.pcap";
            macTarget = "00:14:a3:00:10:08";
        }
        PcapAnalyzer pa = new PcapAnalyzer(filePath, macTarget);

        System.out.println(pa.getMacm());
        do {
            System.out.println("Menù");
            System.out.println("1) Continua ad analizzare");
            System.out.println("2) PortScan");
            System.out.println("3) DNSScan");
            System.out.println("4) Mostra MACM");
            System.out.println("5) Modifica MACM");
            System.out.println("-1) Uscita");
            menu = sc.nextInt();
            sc.nextLine();
            switch (menu) {
                case 1 -> {
                    System.out.println("Inserisci il prossimo nodo da anlizzare (MAC)");
                    pa.analyze(sc.nextLine());
                    System.out.println(pa.getMacm());
                }
                case 2 ->{
                    System.out.println("Inserisci il nodo su cui eseguire il PortScan (MAC)");
                    String mac = sc.nextLine();
                    for (Node n : pa.getMacm().getNodes()){
                        if(n.getMac().compareToIgnoreCase(mac) == 0){
                            PortScanner.runPortScan(n, 65535);
                        }
                    }
                }
                case 3 ->{
                    System.out.println("Inserisci il nodo su cui eseguire il DNSScan (MAC)");
                }
                case 4 -> System.out.println(pa.getMacm());
                case 5 ->{
                    System.out.println("Modifica MACM");
                    System.out.println("Digita N per modificare un nodo");
                    System.out.println("Digita E per modificare un arco");
                }
                default -> System.err.println("Opzione non valida");
            }
        } while (menu != -1);
    }
}
//        if (args.length == 0) {
//            JFileChooser f = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//            int returnValue = f.showOpenDialog(null);
//            if (returnValue == JFileChooser.APPROVE_OPTION) {
//                filePath = f.getSelectedFile().getAbsolutePath();
//            }
//            System.out.println("Inserisci l'indirizzo MAC del dispositivo da cui far partire la scansione");
//            macTarget = sc.nextLine();
//        } else
//        JFrame f = new JFrame("PcapToMACM");
//        f.setLayout(null);
//
//        f.add(pa.getMacm());
//
//        f.setSize(500, 500);
//
//        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        f.setVisible(true);
