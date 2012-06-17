package de.dis2011.persistance.core;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Hashtable;
import de.dis2011.persistance.data.*;

public class ResourceManager
{
	private int logSequenceNumber;
	private String userDir;
	private String name;
	
	private Hashtable<Integer, State> states;
	
	private Hashtable<Integer, Hashtable<Integer, PageData>> buffer;
	private int bufferCounter;
	
	public ResourceManager(String name, String userDir)
	{
		logSequenceNumber = 1000;
		this.userDir = userDir;
		this.buffer = new Hashtable<Integer, Hashtable<Integer,PageData>>();
		this.name = name;
		this.states = new Hashtable<Integer, ResourceManager.State>();
	}

    public boolean write(int transactionID, int pageID, String data)
    {
		boolean success;
    	// not yet registered?
    	if(!states.containsKey(transactionID))
    	{
    		Koordinator koordinator = Koordinator.getInstance();
    		// register
    		if(!koordinator.reg(this, transactionID))
    		{
    			output("Registration failed.", true);
    			return false;
    		}
			states.put(transactionID, State.Active);
    		output("Registration complete.", transactionID, false);
    	}
    	if(states.get(transactionID) == State.Active)
    	{
			PageData page = PageData.createPage(pageID, data);
			// write changes to log file and buffer
			success = writeToLog(transactionID, page) 
				      && addToBuffer(transactionID, page);
			output("Write successful.", transactionID, false);
		}
		else
		{
			output("Write not allowed.", transactionID, true);
			success = false;
		}
		return success;
    }

    public boolean prepare(int transactionID)
    {
    	boolean success;
    	if(states.get(transactionID) == State.Active)
    	{
    		success = writeToLog(transactionID, "prepare")
			       && writeBufferToDisk(transactionID);
    		if(success)
    		{
    			states.put(transactionID, State.Prepared);
    			output("Prepare successful.", transactionID, false);
    		}
    		else
    		{
    			output("Prepare failed.", transactionID, true);
    		}
    	}
    	else
    	{
    		output("Prepare not allowed.", transactionID, true);
    		success = false;
    	}
    	return success;
    }

    public boolean commit(int transactionID)
    {
    	if(states.get(transactionID) == State.Prepared)
    	{
    		boolean success = writeToLog(transactionID, "commit");
    		if(success)
    		{
    			output("Commit successful.", false);
    			states.put(transactionID, State.Committed);
    		}
    		else
    		{
    			output("Commit failed.", transactionID, true);
    		}
    		return success;
    	}
    	else
    	{
    		output("Commit not allowed.", transactionID, true);
    		return false;
    	}
    }
    
    private boolean writeBufferToDisk(int transactionID)
    {
    	// no buffer entry
    	if(!buffer.containsKey(transactionID))
    		return false;
    	
        Hashtable<Integer, PageData> pages = buffer.get(transactionID);
        for (PageData page : pages.values())
        {
            writeToDisk(page);
            decrementBufferCounter();
        }
        // clean up buffer
        pages.clear();
        buffer.remove(transactionID);
        return true;
    }

    private boolean writeToDisk(PageData page)
    {
    	int lsn = createLogSequenceNumber();
        try
        {
            checkForDirectory("data/" + this.name);
            File file = new File(String.format("%s/data/%s/%s.dat", userDir, this.name, page.getPageID()));
            FileWriter fw = new FileWriter(file);
            fw.write(String.format("%d,%d,%s", page.getPageID(), lsn, page.getData()));
            fw.close();
            return true;
        }
        catch (Throwable e)
        {
            output("Data-File could not be written: " + e.getMessage(), true);
            return false;
        }
    }

    public boolean rollback(int transactionID)
    {
    	boolean success;
    	if(states.get(transactionID) == State.Prepared || states.get(transactionID) == State.Active)
    	{
    		success = writeToLog(transactionID, "rollback");
			states.put(transactionID, State.Aborted);
    		RecoveryUtility recovery = new RecoveryUtility();
    		HashMap<Integer, PageData> pages = recovery.recover(transactionID, userDir + "log/log_" + this.name + ".dat");
    		if(pages != null && !pages.isEmpty())
    		{
	    		for(PageData page : pages.values())
	    		{
	    			writeToDisk(page);
	    		}
	    		output("Rollback successful.", transactionID, false);
    		}
    		else
    		{
    			output("Noting to rollback.", transactionID, true);
    		}
    	}
    	else
    	{
    		success = false;
    		output("Rollback not allowed.", transactionID, true);
    	}
    	return success;
    }
    
	private synchronized int createLogSequenceNumber() {
		int value = logSequenceNumber;
		logSequenceNumber++;
		return value;
	}
    
    private boolean writeToLog(int transactionID, String message)
    {
    	int lsn = createLogSequenceNumber();

		try {
			checkForDirectory("log");
			File logFile = new File(String.format("%s/log/log_%s.dat", userDir, this.name));
			FileWriter fw = new FileWriter(logFile, true);
			fw.write(String.format("%d,%d,%s%s", lsn, transactionID, message,
					System.getProperty("line.separator")));
			fw.close();
			return true;
		}
		catch(Throwable e)
		{
			output("Log-File could not be written: " + e.getMessage(), transactionID, true);
			return false;
		}
    }
    
    private boolean writeToLog(int transactionID, PageData page) {
    	int lsn = createLogSequenceNumber();
    	
		try {
			checkForDirectory("log");
			File logFile = new File(String.format("%s/log/log_%s.dat", userDir, this.name));
			FileWriter fw = new FileWriter(logFile, true);
			fw.write(String.format("%d,%d,%d,%s%s",
					lsn, transactionID,
					page.getPageID(), page.getData(),
					System.getProperty("line.separator")));
			fw.close();
			return true;
		} catch (Throwable e) {
			output("Log-File could not be written: " + e.getMessage(), transactionID, true);
			return false;
		}
	}
    
	private void checkForDirectory(String dirName) {
		File directory = new File(userDir + dirName);
		if (!directory.exists() || !directory.isDirectory())
			directory.mkdir();
	}
    
	public boolean addToBuffer(int transactionID, PageData page) {
		if (!buffer.containsKey(transactionID)) {
			buffer.put(transactionID, new Hashtable<Integer, PageData>(5));
		}
		Hashtable<Integer, PageData> pages = buffer.get(transactionID);
		// create new
		if (!pages.containsKey(page.getPageID())) {
			pages.put(page.getPageID(), page);
			this.incrementBufferCounter();
		}
		// overwrite
		else {
			pages.put(page.getPageID(), page);
		}
		return true;
	}
    
    private void output(String message, int transactionID, boolean error)
    {
    	if(error)
    		System.err.println(String.format("Error in RessourceManager %s - Transaction %d (state: %s): %s", this.name, transactionID, this.states.get(transactionID), message));
    	else
    		System.out.println(String.format("RessourceManager %s - Transaction %d (state: %s): %s", this.name, transactionID, this.states.get(transactionID), message));
    }
    

    private void output(String message, boolean error)
    {
    	if(error)
    		System.err.println(String.format("Error in RessourceManager %s: %s", this.name, message));
    	else
    		System.out.println(String.format("RessourceManager %s: %s", this.name, message));
    }
    
    private synchronized void incrementBufferCounter() {
		bufferCounter++;
	}

	private synchronized void decrementBufferCounter() {
		bufferCounter--;
	}
    
    private enum State
    {
    	Active,
    	Prepared,
    	Committed,
    	Aborted
    }
}
