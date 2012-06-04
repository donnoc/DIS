package de.dis2011.persistance.core;

import java.util.Random;

public class PersistenceClient implements Runnable {
	private final int clientId;
	private int pageIdRange;

	public PersistenceClient(int clientId) {
		this.clientId = clientId;
		this.pageIdRange = clientId * 10;
	}

	public void run() {
		Random random = new Random();
		PersistenceManager manager = PersistenceManager.getInstance();
		int taid;
		// bis zu zehn mal Transaktion starten
		for (int i = 0; i <= random.nextInt(10); i++) {
			try {
				Thread.sleep(random.nextInt(150));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			taid = manager.beginTransaction();

			// bis zu 100 mal write() machen pro Transaktion
			for (int j = 0; j <= random.nextInt(100); j++) {
				int pageid = random.nextInt(10) + pageIdRange;
				manager.write(taid, pageid, "Ich bin Client Nummer " + clientId
						+ " und schreibe Seite " + pageid + " im " + i
						+ ". Durchlauf");
				try {
					Thread.sleep(random.nextInt(100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (!(taid % 10 == 0))
				manager.commit(taid);
		}
	}
}
