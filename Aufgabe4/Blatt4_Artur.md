#4. Aufgabenblatt
Passwort: `66kG5IY5`

- Listet auf ob DB2 auto commit ON OFF ist

		db2 list command options

##4.1 - Isolationsstufen

###4.1 a)
- Profile laden 

		. /homeLocal/db2inst1/sqllib/db2profile
		
- verbinden mit 

		db2 connect to VSISP user vsisp72
		
- DB starten
		
		db2
		
- Um die CURRENT ISOLATION zu sehen eintippen
	
		VALUES CURRENT ISOLATION

- Ausgabe ist 

		b2 => VALUES CURRENT ISOLATION
		1 
		--

		1 record(s) selected.
	Isolationssperre ist nicht gesetzt. 

- Nun setzen wir eine Isolationsstufe mit

		SET CURRENT ISOLATION = CS
	auf CS
	
###4.1 b)
- ok

		CREATE TABLE opk (
			id       INT      NOT NULL 
				GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
			name     VARCHAR(45) NOT NULL
		);

		INSERT INTO makler (name) VALUES ('hans');
		INSERT INTO makler (name) VALUES ('klaus');
		INSERT INTO makler (name) VALUES ('mark');
		INSERT INTO makler (name) VALUES ('klaudia');
		INSERT INTO makler (name) VALUES ('Maria');
		INSERT INTO makler (name) VALUES ('Sofie');
		INSERT INTO makler (name) VALUES ('Martin');
		INSERT INTO makler (name) VALUES ('Blubb');

###4.1 c)
- Erst mal Profile wieder machen
	
		. /homeLocal/db2inst1/sqllib/db2profile

- Nun verbinden auf DB2
		
		db2 connect to VSISP user vsisp72

- und auto commit ausschalten. Einfach in der Konsole ausgeben (nicht DB2)
		
		export DB2OPTIONS='+c'

- Prüfen ob autocommit wirklich aus ist

		db2 list command options
		
- DB starten

		db2

- Dann mit 

		SELECT * FROM opk WHERE id = 1

	eine select anweisung ausführen

- neu verbinden mit attach

		db2 attach to vsisls4 user vsisp72

- db2 starten
	
		db2
		
- Auflisten der Application Halnder
	
		list applications

	ausgabe ist
	
		Auth Id  Application    Appl.      Application Id                                                 DB       # of
		         Name           Handle                                                                    Name    Agents
		-------- -------------- ---------- -------------------------------------------------------------- -------- -----
		VSISP72  db2jcc_applica 10794      134.100.4.108.4597.120515203853        (attach)                VSISP    1    
		VSISP72  db2jcc_applica 10766      134.100.4.108.56820.12051520223        (attach)                VSISP    1    
		VSISP72  db2bp          11020      134.100.11.70.65193.12051522244        (connect)               VSISP    1
		...

- mit der `connect`-Verbindung einen Select ausführen

		SELECT * FROM opk WHERE id = 1
				
- Snapshot holen
		
		get snapshot for locks for application agentid 11020
		
	die gehaltenen sperren ausgeben
	
		            Application Lock Snapshot

		Snapshot timestamp                         = 2012-05-16 00.25.55.625533

		Application handle                         = 11020
		Application ID                             = 134.100.11.70.65193.12051522244
		Sequence number                            = 00001
		Application name                           = db2bp
		CONNECT Authorization ID                   = VSISP72
		Application status                         = Connect Completed
		Status change time                         = Not Collected
		Application code page                      = 1208
		Locks held                                 = 0
		Total wait time (ms)                       = Not Collected
	
	keine Locks werden gehalten
	
- in der `connect`- verbindung commiten

		commit

- in der `connect`- verbindung den Isolation LVL auf `RS` setzen

		SET CURRENT ISOLATION = RS

- mit der `connect`-Verbindung einen Select ausführen

		SELECT * FROM opk WHERE id = 1

- Snapshot holen

		get snapshot for locks for application agentid 11020

- immer noch keine lock

		            Application Lock Snapshot

		Snapshot timestamp                         = 2012-05-16 00.35.12.478382

		Application handle                         = 11020
		Application ID                             = 134.100.11.70.65193.12051522244
		Sequence number                            = 00009
		Application name                           = db2bp
		CONNECT Authorization ID                   = VSISP72
		Application status                         = UOW Waiting
		Status change time                         = Not Collected
		Application code page                      = 1208
		Locks held                                 = 0
		Total wait time (ms)                       = Not Collected


- in der `connect`- verbindung commiten

		commit


##4.3 Deadlocks

###4.3 a)

- Isolationsstufe in zweiter Verbindung auf RS setzen

	SET CURRENT ISOLATION = RR

- Erste Verbindung eine Zeile ausgeben

		SELECT * FROM opk WHERE id = 1

- Zweite Verbindung eine andere Zeile ausgeben

		SELECT * FROM opk WHERE id = 2

- Erste Verbindung

		UPDATE opk SET name = 'hans' WHERE id = 2

- Zweite Verbindung

		UPDATE opk SET name = 'klaus' WHERE id = 1


- Snapshot holen

		get snapshot for locks for application agentid 11020

- ergebniss

		            Application Lock Snapshot

		Snapshot timestamp                         = 2012-05-16 00.46.45.024441

		Application handle                         = 11041
		Application ID                             = 134.100.11.70.18876.12051522390
		Sequence number                            = 00005
		Application name                           = db2bp
		CONNECT Authorization ID                   = VSISP72
		Application status                         = UOW Waiting
		Status change time                         = Not Collected
		Application code page                      = 1208
		Locks held                                 = 0
		Total wait time (ms)                       = Not Collected

	lassen sich ohne Probleme commiten


###4.3 b)

- Isolationsstufe in zweiter Verbindung auf RS setzen

	SET CURRENT ISOLATION = RS

- Erste Verbindung eine Zeile ausgeben

		SELECT * FROM opk WHERE id = 1

- Zweite Verbindung eine andere Zeile ausgeben

		SELECT * FROM opk WHERE id = 2

- Erste Verbindung

		UPDATE opk SET name = 'hans' WHERE id = 2

- Zweite Verbindung

		UPDATE opk SET name = 'klaus' WHERE id = 1


- Snapshot holen

		get snapshot for locks for application agentid 11020

- ergebniss

		            Application Lock Snapshot

		Snapshot timestamp                         = 2012-05-16 00.54.21.566724

		Application handle                         = 11020
		Application ID                             = 134.100.11.70.65193.12051522244
		Sequence number                            = 00021
		Application name                           = db2bp
		CONNECT Authorization ID                   = VSISP72
		Application status                         = UOW Waiting
		Status change time                         = Not Collected
		Application code page                      = 1208
		Locks held                                 = 0
		Total wait time (ms)                       = Not Collected

- kein unterschied








