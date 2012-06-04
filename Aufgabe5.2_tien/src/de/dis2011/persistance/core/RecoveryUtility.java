package de.dis2011.persistance.core;

import de.dis2011.persistance.data.*;

import java.util.*;

import java.io.*;

public class RecoveryUtility {
	private final String logPath = "./log/";
	private final String dataPath = "./data/";
	private int lsn_new = 0;
	private Set<Integer> uncommitedSet = new HashSet<Integer>();
	private Set<Integer> commitedSet = new HashSet<Integer>();

	// private Hashtable<Integer, PageData> pages = new Hashtable<Integer,
	// PageData>(50);

	private void analyse() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(logPath
					+ "log.dat"));
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(zeile, ",");
				lsn_new = new Integer(st.nextToken()).intValue();
				int taid = new Integer(st.nextToken()).intValue();
				uncommitedSet.add(taid);
				if (st.nextToken().equals("commit"))
					commitedSet.add(taid);
				uncommitedSet.removeAll(commitedSet);
			}

			/*
			 * System.out.println("uncommited"); for (Integer i : uncommitedSet)
			 * { System.out.println(i); } System.out.println("commited"); for
			 * (Integer y : commitedSet) { System.out.println(y); }
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void recover() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(logPath
					+ "log.dat"));
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(zeile, ",");
				int lnsLog = new Integer(st.nextToken()).intValue();
				int taid = new Integer(st.nextToken()).intValue();
				if (commitedSet.contains(taid)) {
					if (st.countTokens() == 2) {
						int pageLog = new Integer(st.nextToken()).intValue();
						String contentLog = st.nextToken();
						//System.out.println(lnsLog + taid + contentLog);
						redo(lnsLog, taid, pageLog, contentLog);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void redo(int lsnLog, int taid, int pageLog, String contentLog) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(dataPath
					+ pageLog + ".dat"));
			String zeile = null;
			if ((zeile = in.readLine()) != null) {
				lsn_new = lsn_new + 1;
				StringTokenizer st = new StringTokenizer(zeile, ",");
				st.nextToken();
				int lsnPage = new Integer(st.nextToken()).intValue();
				if (lsnPage < lsnLog) {
					try {
						PageData page = PageData.createPage(pageLog, lsnLog,
								contentLog);
						File file = new File(String.format(dataPath + pageLog
								+ ".dat"));
						FileWriter fw = new FileWriter(file);
						fw.write(String.format("%d,%d,%s", page.getPageID(),
								page.getLogSequenceNumber(), page.getData()));
						fw.close();
					} catch (Throwable e) {
						System.err.println("Data-File could not be written: "
								+ e.getMessage());
					}
				}
			}

		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		new RecoveryUtility().startRecover();
	}

	public void startRecover() {
		analyse();
		recover();

	}
}
