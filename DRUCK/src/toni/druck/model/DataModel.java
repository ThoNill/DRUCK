package toni.druck.model;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * 
 * @author Thomas Nill
 * 
 *  Ein {@link DataModel} entspricht einer Klasse
 * 
 */
public class DataModel {

	private static final long serialVersionUID = 1025512692106740806L;

	private HashMap<String, Integer> fieldMap;
	private int size;
	private String[] data;

	public DataModel() {
		super();
		fieldMap = new HashMap<String, Integer>();
		size = 0;
	}

	public synchronized int getIndex(String name) {
		Integer index = fieldMap.get(name);
		if (index != null) {
			return index.intValue();
		}
		fieldMap.put(name, new Integer(size));
		size++;
		return size - 1;
	}

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
		put(getIndex(name),value);
	}

	public String get(String name) {
		return get(getIndex(name));
	}

	public void debug(Logger log) {
		if (log.isDebugEnabled()) {
			log.debug(getClass().getSimpleName() + " size " + size);
			for (String a : data) {
				log.debug(a);
			}
		}
	}

}
