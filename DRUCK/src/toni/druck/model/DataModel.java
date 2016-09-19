package toni.druck.model;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * 
 * @author Thomas Nill
 * 
 *         Ein DataModel ist eine Beschreibung einer Kollektion von Strings
 *         ähnliche einer Klassse.
 * 
 *         Diese Strings weren in einem Array gehalten und sie sind benannt. Zum
 *         schnellen Zugriff auf ein Objekt mit einem bestimmten Namen, kann man
 *         sich den Index des Strings im Array merken und mit diesem Index auf
 *         den String zugreifen.
 * 
 */
public class DataModel {

    private static final long serialVersionUID = 1025512692106740806L;

    private HashMap<String, Integer> fieldMap; // Feldnamen, dient zum Namen ->
                                               // Index im Array Mapping
    private int size;
    private String[] data; // Daten

    public DataModel() {
        super();
        fieldMap = new HashMap<String, Integer>();
        size = 0;
    }

    // Funktionen um vom Namen zum Index zu kommen

    public synchronized int getIndex(String name) {
        Integer index = fieldMap.get(name);
        if (index != null) {
            return index.intValue();
        }
        fieldMap.put(name, new Integer(size));
        size++;
        return size - 1;
    }

    // Mehrere Name in Form von "nam1, name2, name3 ... " auf Indexe mappen

    public int[] getMultiIndex(String fieldNames) {
        return getMultiIndex(fieldNames.split(" *, *"));
    }

    private int[] getMultiIndex(String[] splaces) {
        int[] place = new int[splaces.length];
        for (int i = 0; i < splaces.length; i++) {
            place[i] = getIndex(splaces[i]);
        }
        return place;
    }

    // Funktionen um einen Wert aus dem DatenModel zu holen, bzw zu setzen

    public void put(int index, String value) {
        if (data == null) {
            data = new String[size];
        }
        data[index] = value;
    }

    public String get(int index) {
        if (data == null) {
            data = new String[size];
        }
        return data[index];
    }

    public void put(String name, String value) {
        put(getIndex(name), value);
    }

    public String get(String name) {
        return get(getIndex(name));
    }

    // Ausgabe des Inhalts dines DataModel zum debuggen

    public void debug(Logger log) {
        if (log.isDebugEnabled()) {
            log.debug(getClass().getSimpleName() + " size " + size);
            for (String a : data) {
                log.debug(a);
            }
        }
    }

}
