/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eindcourse6;

/**
 * Eindopdracht Course 6 Herkansing
 * @author Awan Al Koerdi
 */
import static eindcourse6.VirusLogica.getClasHost;
import java.io.*;
import java.util.Map;
import java.util.*;
import javax.swing.*;

public class VirusLogica {

    /**
     * Initialiseren en declareren van variabelen.
     * hostvirusmap key bevat HostID en de value bevat de bijbehorende VirusID's.
     * clashostmap key bevat de classificatie en de value bevat de bijbehorende HostID's.
     */
    
    static HashMap<Integer, String> hostvirusmap = new HashMap<>();
    static HashMap<String, String> clashostmap = new HashMap<>();

    /**
     * Methode waarin bestand wordt gelezen. Het bestand wordt in een string
     * gezet en vervolgens wordt het gelezen. Verder worden indexen uit het
     * bestand in een string of een lijst gezet.
     *
     * @param selectedFile
     * @return virusList
     */
    public static List<Virus> readFile(File selectedFile) {
        List<Virus> virusList = new ArrayList<>();
        try {
            String line;
            BufferedReader inFile;
            inFile = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
            String[] array;
            inFile.readLine();

            while ((inFile.readLine()) != null) {
                line = inFile.readLine();
                array = line.split("\\t", -1);

                int virusID = Integer.parseInt(array[0]);
                String virusNaam = array[1];
                String[] classific = (array[2].split(";"))[1].split(",");
                String classificatie = classific[0];

                int hostID = 0;
                if (!array[7].isEmpty()) {
                    hostID = Integer.parseInt(array[7]);
                }
                String hostNaam = array[8];
                String HostIDList = (array[7] + '(' + array[8] + ')');

                virusList.add(new Virus(virusID, virusNaam, classificatie, hostID, hostNaam, HostIDList));
            }
            inFile.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(VirusGUI.getWindows()[0], "File Error: " + e.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(VirusGUI.getWindows()[0], "File Error: " + e.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(VirusGUI.getWindows()[0], "Unknown Error: " + e.toString());
        }
        return virusList;
    }

    /**
     * Methode die hostvirusmap vult door te kijken welke VirusID's horen bij welke HostID.
     *
     * @param v
     * @param virusList
     * @return
     * hulp Thijs Weenink
     */
    
    public static Map<Integer, String> getHostVirus(List<Virus> virusList) {
        for (Virus v : virusList) {
            if (hostvirusmap.containsKey(v.getHostID())) {
                String value = hostvirusmap.get(v.getHostID());
                String waarde = value + "$" + v.getVirusID();
                hostvirusmap.put(v.getHostID(), waarde);
            } else {
                hostvirusmap.put(v.getHostID(), Integer.toString(v.getVirusID()));
                hostvirusmap.remove("");
            }
        }
        return hostvirusmap;
    }
    
    /**
     * Methode die clashostmap vult door te kijken naar welke HostID's horen bij welke classificatie.
     * @param virusList
     * @return 
     */

    public static Map<String, String> getClasHost(List<Virus> virusList) {
        for (Virus v : virusList) {
            if (clashostmap.containsKey(v.getClassificatie())) {
                String value = clashostmap.get(v.getClassificatie());
                String waarde = value + "$" + Integer.toString(v.getHostID());
                clashostmap.put(v.getClassificatie(), waarde);
            } else {
                clashostmap.put(v.getClassificatie(), Integer.toString(v.getHostID()));
                clashostmap.remove("");
            }
        }
        return clashostmap;
    }

    /**
     * Methode die comboboxen vult wanneer aangeroepen in de GUI. Kijkt naar
     * virus in de lijst met alle variabelen.
     *
     * @param classification
     * @param virusList
     * @return hostbox
     */
    public static Set<String> boxVullen(String classification, List<Virus> virusList) {
        Set<String> hostbox = new HashSet<>();
        for (Virus v : virusList) {
            if (v.getClassificatie().contains(classification)) {
                hostbox.add(v.getHostList());
            }
        }
        return hostbox;
    }

    /**
     * Methode waarbij er actie wordt gevoerd nadat een bepaald iets wordt
     * geselecteerd in de combobox.
     *
     * @param virusList
     * @return claslist
     */
    public static List<String> selectBoxViral(List<Virus> virusList) {
        ArrayList<String> claslist = new ArrayList();
        for (Map.Entry<String, String> clasmap : getClasHost(virusList).entrySet()) {
            claslist.add(clasmap.getKey());
        }
        return claslist;
    }

    /** 
     * Methode die virusid's geeft na het selecteren van HostID's in de hostcomboboxen.
     * @param hostlijst
     * @return virusid
     */
    public static String[] selectBoxHost(String hostlijst) {
        Integer hostid = Integer.parseInt(hostlijst.split("\\(")[0].replace("\\(", ""));
        String[] virusid = ((String) hostvirusmap.get(hostid)).split("\\$");
        return virusid;
    }
    
    /**
     * Methode waarbij wordt de twee tekstvelden met VirusID's wordt vergeleken.
     * @param lijst1
     * @param lijst2
     * @return overlap
     */

    public static Set<String> getOverlap(String[] lijst1, String[] lijst2) {
        Set<String> overlap = new LinkedHashSet<>(Arrays.asList(lijst1));
        Set<String> set1 = new LinkedHashSet<>(Arrays.asList(lijst2));
        overlap.retainAll(set1);
        return overlap;
    }

}
