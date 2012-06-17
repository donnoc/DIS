package de.dis2011.persistance.core;

import de.dis2011.persistance.data.*;
import java.util.Hashtable;
import java.io.*;
import java.util.*;

public class PersistenceManager
{
    private final static PersistenceManager                  instance;
    private Hashtable<Integer, Hashtable<Integer, PageData>> buffer;
    private int                                              transactionCounter;
    private int                                              logSequenceNumber;
    private int                                              bufferCounter;
    private static final int                                 BUFFER_SIZE = 5;
    private Vector<Integer>                                  commitedTransactions;
    private final String                                     userDir     = "./";

    static
    {
        try
        {
            instance = new PersistenceManager();
        }
        catch (Throwable e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    private PersistenceManager()
    {
        buffer = new Hashtable<Integer, Hashtable<Integer, PageData>>(5);
        transactionCounter = 1;
        logSequenceNumber = 1000;
        commitedTransactions = new Vector<Integer>(20);
    }

    public static PersistenceManager getInstance()
    {
        return instance;
    }

    public int beginTransaction()
    {
        int transactionID = createTransactionID();
        return transactionID;
    }

    private synchronized int createTransactionID()
    {
        int value = transactionCounter;
        transactionCounter++;
        return value;
    }

    private synchronized int createLogSequenceNumber()
    {
        int value = logSequenceNumber;
        logSequenceNumber++;
        return value;
    }

    public boolean write(int transactionID, int pageID, String data)
    {
        int logSequenceNumber = createLogSequenceNumber();
        PageData page = PageData.createPage(pageID, logSequenceNumber, data);
        boolean success;
        // write changes to log file
        success = writeToLog(transactionID, page);
        // write changes to buffer
        addToBuffer(transactionID, page);

        if (isBufferFull())
            writeBufferToDisk();

        return success;
    }

    public void addToBuffer(int transactionID, PageData page)
    {
        if (!buffer.containsKey(transactionID))
        {
            buffer.put(transactionID, new Hashtable<Integer, PageData>(5));
        }
        Hashtable<Integer, PageData> pages = buffer.get(transactionID);
        // create new
        if (!pages.containsKey(page.getPageID()))
        {
            pages.put(page.getPageID(), page);
            this.incrementBufferCounter();
        }
        // overwrite
        else
        {
            pages.put(page.getPageID(), page);
        }
    }

    private synchronized void incrementBufferCounter()
    {
        bufferCounter++;
    }

    private synchronized void decrementBufferCounter()
    {
        bufferCounter--;
    }

    private boolean writeToLog(int transactionID, PageData page)
    {
        try
        {
            checkForDirectory("log");
            File logFile = new File(String.format(userDir + "/log/log.dat", page.getLogSequenceNumber()));
            FileWriter fw = new FileWriter(logFile, true);
            fw.write(String.format("%d,%d,%d,%s%s", page.getLogSequenceNumber(), transactionID, page.getPageID(), page.getData(), System.getProperty("line.separator")));
            fw.close();
            return true;
        }
        catch (Throwable e)
        {
            System.err.println("Log-File could not be written: " + e.getMessage());
            return false;
        }
    }

    private boolean writeToDisk(PageData page)
    {
        try
        {
            checkForDirectory("data");
            File file = new File(String.format(userDir + "/data/%s.dat", page.getPageID()));
            FileWriter fw = new FileWriter(file);
            fw.write(String.format("%d,%d,%s", page.getPageID(), page.getLogSequenceNumber(), page.getData()));
            fw.close();
            return true;
        }
        catch (Throwable e)
        {
            System.err.println("Data-File could not be written: " + e.getMessage());
            return false;
        }
    }

    private void checkForDirectory(String dirName)
    {
        File logDirectory = new File(userDir + dirName);
        if (!logDirectory.exists() || !logDirectory.isDirectory())
            logDirectory.mkdir();
    }

    public synchronized void commit(int transactionID)
    {
        commitedTransactions.add(transactionID);

        int lsn = createLogSequenceNumber();

        try
        {
            FileWriter fw = new FileWriter(userDir + "log/log.dat", true);
            fw.write(String.format("%d,%d,commit%s", lsn, transactionID, System.getProperty("line.separator")));
            fw.close();
        }
        catch (Throwable e)
        {
            System.err.println("Logfile could not be written: " + e.getMessage());
        }
    }

    private void writeBufferToDisk()
    {
        for (int transactionID : commitedTransactions)
        {
            Hashtable<Integer, PageData> pages = buffer.get(transactionID);
            // no data for this transaction
            if (pages == null)
                continue;
            for (PageData page : pages.values())
            {
                writeToDisk(page);
                decrementBufferCounter();
            }
            // clean up buffer
            pages.clear();
            buffer.remove(transactionID);
        }
    }

    private boolean isBufferFull()
    {
        return bufferCounter > BUFFER_SIZE;
    }

    /*
     * public void recover() { RecoveryUtility ru = new RecoveryUtility();
     * ru.startRecover(); }
     */


    public static void main(String[] args)
    {
        PersistenceManager pm = PersistenceManager.getInstance();
        int taID = pm.beginTransaction();
        pm.write(taID, 20, "AAAAA");
        pm.write(taID, 21, "BBBBB");
        pm.write(taID, 20, "AAAAA");
        pm.write(taID, 21, "BBBBB");
        pm.write(taID, 20, "AADAA");
        pm.write(taID, 21, "BBBBC");
        pm.commit(taID);
        pm.write(taID, 20, "AxxA");
        pm.write(taID, 21, "xBBB");
    }
}
