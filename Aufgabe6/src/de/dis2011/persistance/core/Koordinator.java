//FIXME nicht uptodate
package de.dis2011.persistance.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

public class Koordinator
{
    private final static Koordinator            _instance;
    private int                                 _taid;
    private Hashtable<Integer, ResourceManager> _resourceManagers = new Hashtable<Integer, ResourceManager>();
    private int                                 _lsn;
    private String                              _userDir;

    static
    {
        try
        {
            _instance = new Koordinator();
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Koordinator()
    {
        set_taid(1000);
        set_lsn(1000);
    }

    public static Koordinator getInstance()
    {
        return _instance;
    }

    /**
     * startet die Transaktion und vergibt eine TaID
     * 
     * @return
     */
    public int begin()
    {
        inkre_taid();
        return get_taid();
    }

    public boolean commit(int taid)
    {
        boolean rueckgabe = false;
        if (_resourceManagers.containsKey(taid))
        {
            // wenn prepare = success
            writeToLog(taid, "prepare");
            if (_resourceManagers.get(taid).prepare(taid))
            {
                writeToLog(taid, "prepared");
                if (_resourceManagers.get(taid).commit(taid))
                {
                    rueckgabe = true;
                    // writeToLog(taid, "commited");
                }
            }
            else if (rollback(taid))
            {
                writeToLog(taid, "aborted");
            }
        }
        if (rueckgabe == true)
            writeToLog(taid, "commited");
        return rueckgabe;
    }

    public boolean rollback(int taid)
    {
        boolean rueckgabe = false;
        if (_resourceManagers.containsKey(taid))
        {
            rueckgabe = _resourceManagers.get(taid).rollback(taid);
            writeToLog(taid, "aborted");
            //System.out.println("Rollback erfolgreich!");
        }
        return rueckgabe;
    }

    private boolean writeToLog(int taid, String state)
    {
        inkre_lsn();

        try
        {
            int lsn = get_lsn();
            FileWriter fw = new FileWriter(_userDir + "log/log_ko.dat", true);
            fw.write(String.format("%d,%d,%s%s", lsn, taid, state, System.getProperty("line.separator")));
            fw.close();
            return true;
        }
        catch (Throwable e)
        {
            System.err.println("Logfile could not be written: " + e.getMessage());
            return false;
        }
    }

    public boolean reg(ResourceManager rm, Integer taid)
    {
        boolean rueckgabe = false;
        // wenn schon drin
        if (_resourceManagers.contains(rm))
            rueckgabe = true;
        // wenn noch nicht drin
         if (!_resourceManagers.contains(rm))
            // wenn erfolgreich eingefuegt
            if (_resourceManagers.put(taid, rm) != null)
                rueckgabe = true;
        return rueckgabe;
    }

    public boolean isCommitted(int taid)
    {
        boolean rueckgabe = false;
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(_userDir + "log/log_ko.dat"));
            while (in.ready())
            {
                String[] line = in.readLine().split(",");
                int taid_log = Integer.parseInt(line[1]);
                if (taid_log == taid)
                {
                    String state = line[2];
                    if (state.equals("commited"))
                    {
                        return true;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return rueckgabe;
    }

    private void inkre_taid()
    {
        _taid = _taid + 1;
    }

    private int get_taid()
    {
        return _taid;
    }

    private void set_taid(int i)
    {
        this._taid = i;
    }

    private void inkre_lsn()
    {
        this._lsn = this._lsn + 1;
    }

    private void set_lsn(int lsn)
    {
        this._lsn = lsn;
    }

    private int get_lsn()
    {
        return _lsn;
    }

    public void setUserDir(String userDir)
    {
        _userDir = userDir;
    }

}
