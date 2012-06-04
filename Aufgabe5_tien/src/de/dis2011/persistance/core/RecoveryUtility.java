package de.dis2011.persistance.core;

import de.dis2011.persistance.data.*;

import java.io.*;

public class RecoveryUtility {
	private final String logPath = "C:\\DIS\\log\\";
	private final String dataPath = "C:\\DIS\\data\\";
	
	public void startRecovery()
	{
		File logDir = new File(logPath);
		if(logDir.exists() && logDir.isDirectory())
		{
			for(File logFile : logDir.listFiles())
			{
				recover(logFile);
			}
		}
	}
	
	private void recover(File logFile)
	{
		PageData logPage = readPageData(logFile);
		File dataFile = new File(dataPath + logPage.getPageID() + ".dat");
		
		if(logPage != null)
		{
			if(dataFile.exists() && dataFile.isFile())
			{
				PageData dataPage = readPageData(dataFile);
				if(dataPage.getLogSequenceNumber() < logPage.getLogSequenceNumber())
				{
					writeData(logPage);
				}
			}
			else
			{
				writeData(logPage);
			}
		}
	}
	
	private PageData readPageData(File file)
	{
		FileReader fr;
		BufferedReader br;
		int lsn, taID, pageID;
		String data;
		
		try
		{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String[] content = br.readLine().split(",");

			fr.close();
			br.close();
			
			if(content.length == 4) // Log
			{
				taID = Integer.parseInt(content[0]);
				lsn = Integer.parseInt(content[1]);
				pageID = Integer.parseInt(content[2]);
				data = content[3];
			}
			else if(content.length == 3) // Data
			{
				pageID = Integer.parseInt(content[0]);
				lsn = Integer.parseInt(content[1]);
				data = content[2];
			}
			else // Unknown Format
				return null;
			
			PageData page = PageData.createPage(pageID, lsn, data);
			return page;
		}
		catch(Throwable e)
		{
			System.err.println("Could not read Log-File: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean writeData(PageData page)
	{
		try
		{
			File file = new File(dataPath+ page.getPageID() + ".dat");
			FileWriter fw = new FileWriter(file);
			fw.write(String.format("%d,%d,%s", page.getPageID(), page.getLogSequenceNumber(), page.getData()));
			System.out.println("Writing file " + file.getName());
			fw.close();
			return true;
		}
		catch(Throwable e)
		{
			System.err.println("Data-File could not be written: " + e.getMessage());
			return false;
		}
	}
	
	public static void main(String[] args) {
		RecoveryUtility ru = new RecoveryUtility();
		ru.startRecovery();
	}
}
