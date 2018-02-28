/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eindcourse6;

public class Virus implements Comparable {

    /**
     * initialiseerd en declareerd variabalen.
     */
    private int VirusID;
    private String VirusNaam;
    private String classificatie;
    private int HostID;
    private String HostNaam;
    private String hostList;

    /**
     * Constructor waarin de rest van de variabelen wordt gedeclareerd.
     *
     * @param VirusID
     * @param VirusNaam
     * @param classificatie
     * @param HostID
     * @param HostNaam
     * @param hostList
     */
    Virus(int VirusID, String VirusNaam, String classificatie, int HostID, String HostNaam, String hostList) {
        this.VirusID = VirusID;
        this.VirusNaam = VirusNaam;
        this.classificatie = classificatie;
        this.HostID = HostID;
        this.HostNaam = HostNaam;
        this.hostList = hostList;
    }

    /**
     * Getters en Setters van de variabelen.
     *
     * @return
     */
    public String getHostList() {
        return hostList;
    }

    public void addHostList(int host) {
        this.hostList = Integer.toString(host);
    }

    public void setVirusID(int VirusID) {
        this.VirusID = VirusID;
    }

    public void setVirusNaam(String VirusNaam) {
        this.VirusNaam = VirusNaam;
    }

    public void setClassificatie(String classificatie) {
        this.classificatie = classificatie;
    }

    public void setHostID(int HostID) {
        this.HostID = HostID;
    }

    public void setHostNaam(String HostNaam) {
        this.HostNaam = HostNaam;
    }

    public int getVirusID() {
        return VirusID;
    }

    public String getVirusNaam() {
        return VirusNaam;
    }

    public String getClassificatie() {
        return classificatie;
    }

    public int getHostID() {
        return HostID;
    }

    public String getHostNaam() {
        return HostNaam;
    }

    /**
     * Sorteert de VirusID in de tekstvelden.
     *
     * @param o
     * @return hulp Thijs Weenink
     */
    @Override
    public int compareTo(Object o) {
        Virus v = (Virus) o;
        if (v.VirusID > this.VirusID) {
            return -1;
        } else if (v.VirusID < this.VirusID) {
            return 1;
        } else {
            return 0;
        }
    }

}
