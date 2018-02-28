/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eindcourse6;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.util.List;
import java.util.Set;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * GUI waarmee de applicatie wordt gemaakt.
 *
 * @author Awan Al Koerdi
 */
public class VirusGUI extends JFrame implements ActionListener {

    private JPanel panel;
    private JLabel url, classif, hostid, sorteer, vl1, vl2, overeenkomstl;
    private JTextField urlfield;
    private JButton search, open, vergelijk;
    private JComboBox<String> classifbox, host1, host2, sorteerbox;
    private JTextArea lijst1, lijst2, overeenkomst;
    private List<Virus> virusList = new java.util.ArrayList<>();

    JFileChooser fileChooser;
    File selectedFile;

    /**
     * Methode waarin de frame wordt vastgesteld en zichtbaar wordt gemaakt.
     *
     * @param args
     */
    public static void main(String[] args) {
        VirusGUI frame = new VirusGUI();
        frame.setSize(570, 450);
        frame.setTitle("Virus Applicatie");
        frame.createGUI();
        frame.setVisible(true);
    }

    /**
     * Methode waarbij de GUI wordt gecreeerd.
     *
     */
    public void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        panel = new JPanel();
        window.add(panel);

        url = new JLabel("File or URL:");
        urlfield = new JTextField();
        urlfield.setPreferredSize(new Dimension(300, 20));
        search = new JButton("Browse");
        open = new JButton("Open");
        search.addActionListener(this);
        open.addActionListener(this);

        window.add(url);
        window.add(urlfield);
        window.add(search);
        window.add(open);

        classif = new JLabel("                  Viral Classification:     ");
        classifbox = new JComboBox();
        classifbox.setVisible(rootPaneCheckingEnabled);
        classifbox.setPreferredSize(new Dimension(350, 20));
        classifbox.addActionListener(this);

        window.add(classif);
        window.add(classifbox);

        hostid = new JLabel("      Host ID:   ");
        host1 = new JComboBox();
        host1.setVisible(true);
        host1.setPreferredSize(new Dimension(225, 20));
        host1.addActionListener(this);
        host2 = new JComboBox();
        host2.setVisible(true);
        host2.setPreferredSize(new Dimension(225, 20));
        host2.addActionListener(this);

        window.add(hostid);
        window.add(host1);
        window.add(host2);

        sorteer = new JLabel("     Sorteer:    ");
        sorteerbox = new JComboBox();
        sorteerbox.setVisible(true);
        sorteerbox.setPreferredSize(new Dimension(450, 20));
        sorteerbox.addActionListener(this);
        window.add(sorteer);
        window.add(sorteerbox);

        vl1 = new JLabel("                        Virus 1                             ");
        lijst1 = new JTextArea();
        lijst1.setPreferredSize(new Dimension(200, 100));
        vl2 = new JLabel("                        Virus 2                              ");
        lijst2 = new JTextArea();
        lijst2.setPreferredSize(new Dimension(200, 100));
        vergelijk = new JButton("Vergelijk");
        vergelijk.setPreferredSize(new Dimension(400, 20));
        vergelijk.addActionListener(this);

        window.add(vl1);
        window.add(vl2);
        window.add(lijst1);

        window.add(lijst2);
        window.add(vergelijk);

        overeenkomstl = new JLabel("                 Overeenkomsten virus 1 en virus 2                 ");
        overeenkomst = new JTextArea();
        overeenkomst.setPreferredSize(new Dimension(300, 100));

        window.add(overeenkomstl);
        window.add(overeenkomst);
    }

    /**
     * GUI elementen worden gekoppeld aan de actionlistener. Hierbij kunnen
     * functies worden gegeven aan deze variabelen.
     *
     * @param evt
     */
    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {

        if (evt.getSource() == search) {
            fileChooser = new JFileChooser();
            int reply = fileChooser.showOpenDialog(this);
            if (reply == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                urlfield.setText(selectedFile.getAbsolutePath());
            }
        }

        if (evt.getSource() == open) {
            open();
        }

        if (evt.getSource() == classifbox) {
            classificatie();
        }

        if (evt.getSource() == host1) {
            String hostlijst = host1.getSelectedItem().toString();
            getSelectedItem(hostlijst, lijst1);
        }

        if (evt.getSource() == host2) {
            String hostlijst = host2.getSelectedItem().toString();
            getSelectedItem(hostlijst, lijst2);
        }

        if (evt.getSource() == vergelijk) {
            getOverlap();
        }

        if (evt.getSource() == sorteer) {

        }
    }

    /**
     * Methode die een bestand opent. removeAllItems zorgt ervoor dat de boxen
     * leeg zijn.
     */
    private void open() {
        host1.removeAllItems();
        host2.removeAllItems();
        selectedFile = new File(urlfield.getText());
        virusList = VirusLogica.readFile(selectedFile);
        ArrayList<String> claslist = (ArrayList<String>) VirusLogica.selectBoxViral(virusList);
        classifbox.setModel(new DefaultComboBoxModel(claslist.toArray()));
        VirusLogica.getHostVirus(virusList);
    }

    /**
     * Methode die de classificatiebox vult.
     */
    private void classificatie() {
        String viralclassif = classifbox.getSelectedItem().toString();
        Set<String> boxcontent = VirusLogica.boxVullen(viralclassif, virusList);
        host1.setModel(new DefaultComboBoxModel(boxcontent.toArray()));
        host2.setModel(new DefaultComboBoxModel(boxcontent.toArray()));
        setSortingCombo();
    }

    /**
     * Methode die een geselecteerde item in een textveld zet.
     *
     * @param host
     * @param textA
     */
    private void getSelectedItem(String host, JTextArea textArea) {
        textArea.setText("");
        String[] boxboxbox = VirusLogica.selectBoxHost(host);
        Arrays.sort(boxboxbox);
        for (String virusid : boxboxbox) {
            textArea.append(virusid + System.lineSeparator());
        }
    }

    /**
     * Methode die de overlap tussen de twee uitgekozen hosts in een textveld
     * zet.
     */
    private void getOverlap() {
        if (!"".equals(lijst1.getText()) || !"".equals(lijst2.getText())) {

            overeenkomst.setText("");
            String[] virusid1 = lijst1.getText().split("\n");
            String[] virusid2 = lijst2.getText().split("\n");
            Set<String> overlap = VirusLogica.getOverlap(virusid1, virusid2);

            for (String id : overlap) {
                overeenkomst.append(id + System.lineSeparator());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lists empty");
        }
    }

    /**
     * Methode die zou moeten sorteren. Kan alleen de box vullen met een String.
     */
    private void setSortingCombo() {
        String[] options = {"VirusID", "Aantal hosts"};
        sorteerbox.setModel(new DefaultComboBoxModel<>(options));
    }

}
