
--#################################
/**
 * DROP TABLE ...
 */
DROP TABLE makler;
DROP TABLE immobilien;
DROP TABLE wohnung;
DROP TABLE haus;
DROP TABLE person;
DROP TABLE vertrag;
DROP TABLE kaufvertrag;
DROP TABLE mietvertrag;
DROP VIEW immobilien_wohnung;
DROP VIEW immobilien_haus;
DROP VIEW vertrag_mietvertrag;
DROP VIEW vertrag_kaufvertrag;

--#################################
/**
 * CREATE TABLE ...
 */

/**
 * Makler
 */
CREATE TABLE makler (
	id       INT      NOT NULL 
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name     VARCHAR(45) NOT NULL ,
	adresse  VARCHAR(45) NOT NULL ,
	login    VARCHAR(45) NOT NULL UNIQUE,
	password VARCHAR(45) NOT NULL ,
	PRIMARY KEY (id),
	CONSTRAINT makler_uniq UNIQUE (login)
);

INSERT INTO makler (name,    adresse,          login,  password)
	VALUES         ('hans', 'Berliner Weg 15', 'hans', 'hans');

/**
 * Immobilien
 */
CREATE TABLE immobilien (
	id         INT      NOT NULL
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	id_makler  INT      NOT NULL ,
	ort        VARCHAR(45) NOT NULL ,
	plz        VARCHAR(5)  NOT NULL ,
	strasse    VARCHAR(45) NOT NULL ,
	hausnummer    VARCHAR(6)  NOT NULL ,
	flaeche    INT      NOT NULL ,
	PRIMARY KEY (id) ,
	CONSTRAINT fk_immobilien_makler
		FOREIGN KEY ( id_makler )
		REFERENCES makler ( id )
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
);

INSERT INTO immobilien (id_makler,  ort,       plz,     strasse,          hausnummer,  flaeche)
	VALUES             (1,         'Berlin',  '11111', 'Klingenstrasse', '2',      '80'),
	                   (1,         'Hamburg', '54664', 'Hansbeckerweg',  '45',     '120');

/**
 * Wohnung
 */
CREATE TABLE wohnung (
	id            INT      NOT NULL 
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	id_immobilien INT      NOT NULL ,
	stockwerk     INT      NOT NULL ,
	mietpreis     INT      NOT NULL ,
	zimmer        INT      NOT NULL ,
	balkon        SMALLINT NOT NULL ,
	ebk           SMALLINT NOT NULL ,
	PRIMARY KEY (id) ,
	CONSTRAINT fk_wohnung_immobilien
		FOREIGN KEY ( id_immobilien )
		REFERENCES immobilien ( id )
		ON DELETE CASCADE
		ON UPDATE NO ACTION
);

INSERT INTO wohnung (id_immobilien, stockwerk, mietpreis, zimmer, balkon, ebk)
	VALUES          (1,             5,         600,       3,      1,      0);


/**
 * Haus
 */
CREATE TABLE haus (
	id            INT      NOT NULL 
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	id_immobilien INT      NOT NULL ,
	stockwerk     INT      NOT NULL ,
	kaufpreis     INT      NOT NULL ,
	garten        SMALLINT NOT NULL ,
	PRIMARY KEY (id) ,
	CONSTRAINT fk_haus_immobilien
		FOREIGN KEY ( id_immobilien )
		REFERENCES immobilien ( id )
		ON DELETE CASCADE
		ON UPDATE NO ACTION
);

INSERT INTO haus (id_immobilien, stockwerk, kaufpreis, garten)
	VALUES          (2,             5,         500,       3);
	

/**
 * Person
 */
CREATE TABLE person (
	id       INT      NOT NULL 
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	vorname  VARCHAR(45) NOT NULL ,
	nachname VARCHAR(45) NOT NULL ,
	adresse  VARCHAR(45) NOT NULL ,
	PRIMARY KEY (id)
);

INSERT INTO person (vorname, nachname,   adresse)
	VALUES         ('hans',  'Werner',   'Bobergeranger 34'),
	               ('klaus', 'Petersen', 'Lingenstrasse 78');


/**
 * Vertrag
 */
CREATE TABLE vertrag (
	id         INT      NOT NULL 
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	vertragsnr VARCHAR(45) NOT NULL ,
	datum      VARCHAR(45) NOT NULL , -- Unix time als string
	ort        VARCHAR(45) NOT NULL ,
	PRIMARY KEY (id)
);

INSERT INTO vertrag (vertragsnr, datum, ort)
	VALUES          ('234345', '1334756939', 'Hamburg'),
	                ('365456', '1334769939', 'Berlin');


/**
 * Kaufvertrag
 */
CREATE TABLE kaufvertrag (
	id           INT NOT NULL
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	id_person    INT NOT NULL ,
	id_haus      INT NOT NULL ,
	id_vertrag   INT NOT NULL ,
	anzahl_raten INT NOT NULL ,
	ratenzins    INT NOT NULL ,
	PRIMARY KEY (id) ,
	CONSTRAINT fk_kaufvertrag_person
		FOREIGN KEY ( id_person )
		REFERENCES person ( id )
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	CONSTRAINT fk_kaufvertrag_haus
		FOREIGN KEY ( id_haus )
		REFERENCES haus ( id )
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	CONSTRAINT fk_kaufvertrag_vertrag
		FOREIGN KEY ( id_vertrag )
		REFERENCES vertrag ( id )
		ON DELETE CASCADE
		ON UPDATE NO ACTION
);

INSERT INTO kaufvertrag (id_person, id_haus, id_vertrag, anzahl_raten, ratenzins)
	VALUES              (1,         1,       1,          4,            3);

/**
 * Mietvertrag
 */
CREATE TABLE mietvertrag (
	id          INT      NOT NULL
		GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	id_person   INT      NOT NULL ,
	id_wohnung  INT      NOT NULL ,
	id_vertrag  INT      NOT NULL ,
	mietbegin   VARCHAR(45) NOT NULL ,
	dauer       INT      NOT NULL ,
	nebenkosten INT      NOT NULL ,
	PRIMARY KEY (id) ,
	CONSTRAINT fk_mietvertrag_person
		FOREIGN KEY ( id_person )
		REFERENCES person ( id )
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	CONSTRAINT fk_mietvertrag_wohnung
		FOREIGN KEY ( id_wohnung )
		REFERENCES wohnung ( id )
		ON DELETE CASCADE
		ON UPDATE NO ACTION,
	CONSTRAINT fk_mietvertrag_vertrag
		FOREIGN KEY ( id_vertrag )
		REFERENCES vertrag ( id )
		ON DELETE CASCADE
		ON UPDATE NO ACTION
);

INSERT INTO mietvertrag (id_person, id_wohnung, id_vertrag, mietbegin, dauer, nebenkosten)
	VALUES              (1,          1,         2,          4,         3,     455);



--##########################################
/**
 * CREATE VIEWS
 */

/**
 * Wohnung + Immobilien
 */
CREATE VIEW immobilien_wohnung (id_immobilien, id_makler, ort, plz, strasse, hausnummer, flaeche, stockwerk, mietpreis, zimmer, balkon, ebk) AS 
	SELECT 	immobilien.id,
			immobilien.id_makler,
			immobilien.ort,
			immobilien.plz,
			immobilien.strasse,
			immobilien.hausnummer,
			immobilien.flaeche,
			wohnung.stockwerk,
			wohnung.mietpreis,
			wohnung.zimmer,
			wohnung.balkon,
			wohnung.ebk
	FROM wohnung, immobilien
	WHERE wohnung.id_immobilien = immobilien.id;


/**
 * Haus + Immobilien
 */
CREATE VIEW immobilien_haus (id_immobilien, id_makler, ort, plz, strasse, hausnummer, flaeche, stockwerk, kaufpreis, garten) AS 
	SELECT	immobilien.id,
			immobilien.id_makler,
			immobilien.ort,
			immobilien.plz,
			immobilien.strasse,
			immobilien.hausnummer,
			immobilien.flaeche,
			haus.stockwerk,
			haus.kaufpreis,
			haus.garten
	FROM haus, immobilien
	WHERE haus.id_immobilien = immobilien.id;

/**
 * Vertrag + Mietvertrag
 */
CREATE VIEW vertrag_mietvertrag (id_vertrag, vertragsnr, datum, ort, id_person, id_wohnung, mietbegin, dauer, nebenkosten) AS
	SELECT	vertrag.id,
			vertrag.vertragsnr,
			vertrag.datum,
			vertrag.ort,
			mietvertrag.id_person,
			mietvertrag.id_wohnung,
			mietvertrag.mietbegin,
			mietvertrag.dauer,
			mietvertrag.nebenkosten
	FROM vertrag, mietvertrag
	WHERE mietvertrag.id_vertrag = vertrag.id;

/**
 * Vertrag + Kaufvertrag
 */
CREATE VIEW vertrag_kaufvertrag (id_vertrag, vertragsnr, datum, ort, id_person, id_haus, anzahl_raten, ratenzins) AS
	SELECT	vertrag.id,
			vertrag.vertragsnr,
			vertrag.datum,
			vertrag.ort,
			kaufvertrag.id_person,
			kaufvertrag.id_haus,
			kaufvertrag.anzahl_raten,
			kaufvertrag.ratenzins
	FROM vertrag, kaufvertrag
	WHERE kaufvertrag.id_vertrag = vertrag.id;
















