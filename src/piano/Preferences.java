package piano;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * Preferences encapsulation.
 *
 * Written upon an example Taken from the book
 * Wireless Java - Developing with J2ME, second edition
 * by Jonathan Knudsen, published by Apress.
 *
 * @author Wincent Balin
 */

public class Preferences
{
    private String name;

    private Hashtable preferences;

    /**
     * Constructor.
     *
     * @param name Name of the record store
     *
     * @throws RecordStoreException
     */
    public Preferences(String name) throws RecordStoreException
    {
        // Store name for further usage
        this.name = name;

        // Initialize preferences
        preferences = new Hashtable();

        // Load settings
        load();

        // Set default values of needed
        String key;
        key = "Octave";
        if(!preferences.containsKey(key))
        {
            preferences.put(key, Integer.toString(PianoModel.PIANO_FIRST_OCTAVE));
        }
        key = "Velocity";
        if(!preferences.containsKey(key))
        {
            preferences.put(key, Integer.toString(100));  // 100/127
        }
        key = "Timbre";
        if(!preferences.containsKey(key))
        {
            preferences.put(key, Integer.toString(0)); // Acoustic grand piano
        }
    }

    /**
     * Get preference.
     *
     * @param key Name of the preference
     * @return Value of the preference
     */
    public String get(String key)
    {
        return (String) preferences.get(key);
    }

    /**
     * Get (integer) preference.
     *
     * @param key Name of the preference
     * @return Value of the preference
     */
    public int getInt(String key)
    {
        return Integer.parseInt((String) preferences.get(key));
    }

    /**
     * Set preference.
     *
     * @param key Name of the preference
     * @param value Value of the preference
     */
    public void set(String key, String value)
    {
        // Safeguard
        if(value == null)
        {
            value = "";
        }

        preferences.put(key, value);
    }

    /**
     * Set preference.
     *
     * @param key Name of the preference
     * @param value Value of the preference
     */
    public void set(String key, int value)
    {
        preferences.put(key, Integer.toString(value));
    }

    /**
     * Loading method.
     * 
     * @throws RecordStoreException
     */
    public void load() throws RecordStoreException
    {
        RecordStore rs = null;
        RecordEnumeration re = null;

        try
        {
            rs = RecordStore.openRecordStore(name, true);
            re = rs.enumerateRecords(null, null, false);

            while(re.hasNextElement())
            {
                byte[] raw = re.nextRecord();
                String pref = new String(raw);

                int index = pref.indexOf('|');
                String key = pref.substring(0, index);
                String value = pref.substring(index + 1);

                preferences.put(key, value);
            }
        }
        finally
        {
            if(re != null)
                re.destroy();

            if(rs != null)
                rs.closeRecordStore();
        }
    }

    /**
     * Saving method.
     *
     * @throws RecordStoreException
     */
    public void save() throws RecordStoreException
    {
        RecordStore rs = null;
        RecordEnumeration re = null;

        try
        {
            rs = RecordStore.openRecordStore(name, true);
            re = rs.enumerateRecords(null, null, false);

            // If fresh (empty) record store
            if(!re.hasNextElement())
            {
                Enumeration keys = preferences.keys();

                while(keys.hasMoreElements())
                {
                    String key = (String) keys.nextElement();
                    String value = get(key);
                    String pref = key + "|" + value;
                    byte[] raw = pref.getBytes();
                    rs.addRecord(raw, 0, raw.length);
                }
            }
            // Replace records
            else
            {
                while(re.hasNextElement())
                {
                    int id = re.nextRecordId();

                    String pref = new String(rs.getRecord(id));
                    int index = pref.indexOf('|');
                    String key = pref.substring(0, index);
                    String value = pref.substring(index + 1);

                    String currentValue = get(key);

                    if(!currentValue.equals(value))
                    {
                        rs.deleteRecord(id);
                        String newPref = new String(key + "|" + currentValue);
                        byte[] raw = newPref.getBytes();
                        rs.addRecord(raw, 0, raw.length);
                    }
                }
            }
        }
        finally
        {
            if(re != null)
                re.destroy();

            if(rs != null)
                rs.closeRecordStore();
        }
    }
}
