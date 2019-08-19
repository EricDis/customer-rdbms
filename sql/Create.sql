CREATE TABLE kunde (
   kundenID   NUMBER(10,0),
   vorname    VARCHAR2(20) NOT NULL,
   nachname   VARCHAR2(20) NOT NULL,
   ort        VARCHAR(20) NOT NULL,
   plz        INTEGER NOT NULL,
   strasse    VARCHAR2(80) NOT NULL,
   hausNr	  VARCHAR2(5) NOT NULL,
   PRIMARY KEY (kundenID),
   CONSTRAINT wirbt FOREIGN KEY (kundenID) REFERENCES kunde(kundenID) --1 Kunde wirbt n kunden   
);

CREATE TABLE privaterKunde (
	eMail 	 VARCHAR2(40),
	kundenID NUMBER(10,0) UNIQUE NOT NULL,
	rang	 INTEGER,
	gender	 VARCHAR2(1) CHECK (gender in ('M','W')),
	PRIMARY KEY (eMail),
	FOREIGN KEY (kundenID) REFERENCES kunde(kundenID) ON DELETE CASCADE	
);

CREATE TABLE firmenKunde (
	firmenID NUMBER(20,0),
	kundenID NUMBER(10,0) UNIQUE NOT NULL,
	fKontakt VARCHAR2(40) NOT NULL,
	fRang	 INTEGER,
	PRIMARY KEY (firmenID),
	FOREIGN KEY (kundenID) REFERENCES kunde(kundenID) ON DELETE CASCADE	
);

CREATE TABLE warenkorb (
	warenkorbID			NUMBER(20,0),
	kundenID 			NUMBER(10,0) UNIQUE NOT NULL,
	anzahlArtikel		INTEGER NULL,
	wert				NUMBER(10,2),
	PRIMARY KEY (warenkorbID),
	FOREIGN KEY (kundenID) REFERENCES kunde(kundenID) ON DELETE CASCADE	
);

CREATE TABLE bestellung (
	bestellungsID	NUMBER(20,0),
	warenkorbID 	NUMBER(20,0) UNIQUE NOT NULL,
	kundenID		NUMBER(10,0) UNIQUE NOT NULL,
	typ				INTEGER,
	zeitpunkt		VARCHAR2(12),
	PRIMARY KEY (bestellungsID),
	FOREIGN KEY (kundenID) REFERENCES kunde(kundenID) ON DELETE CASCADE,
	FOREIGN KEY (warenkorbID) REFERENCES warenkorb(warenkorbID) ON DELETE CASCADE	
);

CREATE TABLE rechnung (
	rechnungsNr 		NUMBER(20,0),
	bestellungsID		NUMBER(20,0),
	betrag				NUMBER(10,2) NULL,
	steuersatz			INTEGER NOT NULL,
	FOREIGN KEY (bestellungsID) REFERENCES bestellung(bestellungsID) ON DELETE CASCADE,
	PRIMARY KEY (rechnungsNr, bestellungsID)	
);

CREATE TABLE artikel (
	artNr			NUMBER(20,0),
	bezeichnung		VARCHAR2(20) NOT NULL,
	preis			NUMBER(10,2) NOT NULL,
	kategorie		VARCHAR2(25) NOT NULL,
	PRIMARY KEY (artNr)
);

CREATE TABLE enthaelt ( --junktion table für warenkorb enthält artikel
	warenkorbID NUMBER(20,0),
	artNr		NUMBER(20,0),
	stueckzahl 	INTEGER NOT NULL,
	FOREIGN KEY (warenkorbID) REFERENCES warenkorb(warenkorbID) ON DELETE CASCADE,
	FOREIGN KEY (artNr) REFERENCES artikel(artNr) ON DELETE CASCADE,
	UNIQUE (warenkorbID, artNr)	
);

----Auto increment sequence for kundenID

CREATE SEQUENCE seq_kunden_id
  START WITH 1
  MINVALUE 1
  INCREMENT BY 1
  CACHE 100;

CREATE OR REPLACE TRIGGER tri_kunden_id
  BEFORE INSERT
  ON kunde
  FOR EACH ROW
  BEGIN
    SELECT seq_kunden_id.nextval
    INTO :new.kundenID
    FROM dual
 END;
  
----auto increment seq for firmenID
 
CREATE SEQUENCE seq_firmen_id
  START WITH 1
  MINVALUE 1
  INCREMENT BY 1
  CACHE 100;

CREATE OR REPLACE TRIGGER tri_firmen_id
  BEFORE INSERT
  ON firmenKunde
  FOR EACH ROW
  BEGIN
    SELECT seq_firmen_id.nextval
    INTO :new.firmenID
    FROM dual;
 END;
 
---auto seq for warenkorbID

CREATE SEQUENCE seq_waren_id
  START WITH 1
  MINVALUE 1
  INCREMENT BY 1
  CACHE 100;

CREATE OR REPLACE TRIGGER tri_waren_id
  BEFORE INSERT
  ON warenkorb
  FOR EACH ROW
  BEGIN
    SELECT seq_waren_id.nextval
    INTO :new.warenkorbID
    FROM dual;
END;
  
---auto seq for bestellungsID

CREATE SEQUENCE seq_bestell_id
  START WITH 1
  MINVALUE 1
  INCREMENT BY 1
  CACHE 100;

CREATE OR REPLACE TRIGGER tri_bestell_id
  BEFORE INSERT
  ON bestellung
  FOR EACH ROW
  BEGIN
    SELECT seq_bestell_id.nextval
    INTO :new.bestellungsID
    FROM dual;
  END;
  
---auto seq for rechnungsNr

CREATE SEQUENCE seq_rech_id
  START WITH 1
  MINVALUE 1
  INCREMENT BY 1
  CACHE 100;

CREATE OR REPLACE TRIGGER tri_rech_id
  BEFORE INSERT
  ON rechnung
  FOR EACH ROW
  BEGIN
    SELECT seq_rech_id.nextval
    INTO :new.rechnungsNr
    FROM dual;
  END;
 
---auto seq for artNr

CREATE SEQUENCE seq_art_id
  START WITH 1
  MINVALUE 1
  INCREMENT BY 1
  CACHE 100;

CREATE OR REPLACE TRIGGER tri_art_id
  BEFORE INSERT
  ON artikel
  FOR EACH ROW
  BEGIN
    SELECT seq_art_id.nextval
    INTO :new.artNr
    FROM dual;
  END;