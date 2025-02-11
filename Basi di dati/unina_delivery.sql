-- pgAdmin 4

--schema su cui lavorare
CREATE SCHEMA unina_delivery;

--imposto lo schema creato come primo nella ricerca
SET search_path TO unina_delivery, public;

/* ------------------- DEFINIZIONE TABELLE ------------------- */

--TABLE: Clienti
CREATE TABLE clienti
(
    idcliente	INTEGER		    PRIMARY KEY,
    nome	    VARCHAR(20) 	NOT NULL	CHECK (nome ~ '^[A-Za-z]+$'),
    cognome	    VARCHAR(20) 	NOT NULL	CHECK (cognome ~ '^[A-Za-z]+$'),
    via		    VARCHAR(100)	NOT NULL	CHECK (via ~ '^[A-Za-z][A-Za-z0-9\s.,-]*$'),
    civico	    VARCHAR(3)	    NOT NULL	CHECK (civico ~ '^[0-9]{1,3}$'),
    CAP		    CHAR(5)		    NOT NULL	CHECK (CAP ~ '^[0-9]{5}$'),
    cellulare	CHAR(10)	    UNIQUE		CHECK (cellulare ~ '^[0-9]{10}$'),
    email	    VARCHAR(50)	    UNIQUE		CHECK (email ~ '^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z]{2,}$'),
    /*sezione costraint*/
    CONSTRAINT cell_or_email CHECK (cellulare IS NOT NULL OR email IS NOT NULL)
);

-- Per la PK, verrà creata una sequenza ed un trigger che la sfrutta, prima dell'inserimento
-- Ciò è un procedimento che verrà ripetuto per molte tabelle

--TABLE: Operatori
CREATE TABLE operatori
(
    idoperatore INTEGER		    PRIMARY KEY,
    nome	    VARCHAR(20)	    NOT NULL		    CHECK (nome ~ '^[A-Za-z]+$'),
    cognome	    VARCHAR(20) 	NOT NULL		    CHECK (cognome ~ '^[A-Za-z]+$'),
    codFiscale	CHAR(16)	    NOT NULL	UNIQUE	CHECK (codFiscale ~ '^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$'),
    username	VARCHAR(50)	    NOT NULL	UNIQUE	CHECK (LENGTH(username) >= 4 AND username ~ '^[a-zA-Z0-9]+$'),
    pass	    VARCHAR(50)	    NOT NULL	UNIQUE	CHECK (LENGTH(pass) >= 8 AND pass ~ '^[a-zA-Z0-9]+$')
);

--TABLE: Corrieri
CREATE TABLE corrieri
(
    idcorriere		INTEGER		    PRIMARY KEY,
    nome		    VARCHAR(20)	    NOT NULL		    CHECK (nome ~ '^[A-Za-z]+$'),
    cognome		    VARCHAR(20)	    NOT NULL		    CHECK (cognome ~ '^[A-Za-z]+$'),
    codFiscale		CHAR(16)	    NOT NULL	UNIQUE	CHECK (codFiscale ~ '^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$'),
    cellulare		CHAR(10)	    NOT NULL	UNIQUE	CHECK (cellulare ~ '^[0-9]{10}$'),
    disponibilita	BOOLEAN		    NOT NULL	DEFAULT TRUE
);

--TABLE: Veicoli
CREATE TABLE veicoli
(
    targa		    CHAR(7)		    PRIMARY KEY	    CHECK (targa ~ '^[A-Z]{2}\d{3}[A-Z]{2}$'),
    pesoSupportato	DECIMAL(6,2)	NOT NULL	    CHECK (pesoSupportato > 0),
    disponibilita	BOOLEAN		    NOT NULL	    DEFAULT TRUE
);

--TABLE: Articoli
CREATE TABLE articoli
(
    idarticolo  VARCHAR(15)     PRIMARY KEY,
    nome        VARCHAR(50)     NOT NULL,
    descrizione VARCHAR(200)    NOT NULL,
    quantita    INTEGER         NOT NULL    CHECK (quantita >= 0),
    peso        DECIMAL(5,2)    NOT NULL    CHECK (peso > 0),
    prezzo      DECIMAL(8,2)    NOT NULL    CHECK (prezzo > 0)
);

--TABLE: Spedizioni
CREATE TABLE spedizioni
(
    idspedizione    VARCHAR(15) PRIMARY KEY,
    data_sped       DATE        NOT NULL    DEFAULT CURRENT_DATE,
    orario          TIME        NOT NULL    DEFAULT (DATE_TRUNC('minute', CURRENT_TIMESTAMP))::time,
    avviata         BOOLEAN     NOT NULL    DEFAULT FALSE,
    completata      BOOLEAN     NOT NULL    DEFAULT FALSE,
    idoperatore     INTEGER     NOT NULL,
    idcorriere      INTEGER     NOT NULL,
    targa           CHAR(8)     NOT NULL,
    /*sezione costraint*/
    CONSTRAINT fk_operatore_in_spedizione   FOREIGN KEY (idoperatore)   REFERENCES operatori(idoperatore) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_corriere_in_spedizione    FOREIGN KEY (idcorriere)    REFERENCES corrieri(idcorriere)   ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_targa_in_spedizione       FOREIGN KEY (targa)         REFERENCES veicoli(targa)         ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT completata_se_avviata CHECK (completata IS NOT TRUE OR avviata IS TRUE)
);

--TABLE: Ordini
CREATE TABLE ordini
(
    idordine        VARCHAR(15)     PRIMARY KEY,
    data_ord        DATE            NOT NULL                CHECK (data_ord <= CURRENT_DATE),
    via		        VARCHAR(100)    NOT NULL	            CHECK (via ~ '^[A-Za-z][A-Za-z0-9\s.,-]*$'),
    civico	        VARCHAR(3)	    NOT NULL	            CHECK (civico ~ '^[0-9]{1,3}$'),
    CAP		        CHAR(5)		    NOT NULL	            CHECK (CAP ~ '^[0-9]{5}$'),
    citta           VARCHAR(30)     NOT NULL                CHECK (citta ~ '^[A-Za-z][A-Za-z\s]*$'),
    cellulare	    CHAR(10)	    NOT NULL 	            CHECK (cellulare ~ '^[0-9]{10}$'),
    prezzoTot       DECIMAL(12,2)   NOT NULL    DEFAULT 0   CHECK (prezzoTot >= 0),
    consegnato      BOOLEAN         NOT NULL    DEFAULT FALSE,
    idcliente       INTEGER         NOT NULL,
    idoperatore     INTEGER,
    idspedizione    VARCHAR(15),
    /*sezione costraint*/
    CONSTRAINT fk_cliente_in_ordine     FOREIGN KEY (idcliente)     REFERENCES Clienti(idcliente)       ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_operatore_in_ordine   FOREIGN KEY (idoperatore)   REFERENCES operatori(idoperatore)   ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_spedizione_in_ordine  FOREIGN KEY (idspedizione)  REFERENCES spedizioni(idspedizione) ON DELETE SET NULL ON UPDATE CASCADE
);


--TABLE: CompOrdine (tabella ponte, composizione ordine)
CREATE TABLE compOrdine
(
    idordine    VARCHAR(15)     NOT NULL,
    idarticolo  VARCHAR(15)     NOT NULL,
    quantita    INTEGER         NOT NULL    CHECK (quantita > 0),
    pesoPar     DECIMAL(6,2)    NOT NULL    CHECK (pesoPar > 0),
    prezzoPar   DECIMAL(12,2)   NOT NULL    CHECK (prezzoPar > 0),
    /*sezione costraint*/
    CONSTRAINT fk_ordine_in_compOrdine      FOREIGN KEY (idordine)   REFERENCES ordini(idordine)     ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_articolo_in_compOrdine    FOREIGN KEY (idarticolo) REFERENCES articoli(idarticolo) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT unique_compOrdine    UNIQUE (idordine, idarticolo)
);


/* --------------- DEFINIZIONE SEQUENZE E RELATIVI TRIGGER --------------- */

-- Creazione sequenza per PK(clienti)
CREATE SEQUENCE idcliente_seq 
	START 1
	INCREMENT 1;

-- Funzione per ottenere un nuovo valore dalla sequenza
CREATE OR REPLACE FUNCTION nextval_idcliente()
RETURNS INTEGER AS $$
DECLARE
    next_id INTEGER;
BEGIN
    SELECT nextval('idcliente_seq') INTO next_id;

    RETURN next_id;
END;
$$ LANGUAGE plpgsql;

-- Trigger per utilizzare la sequenza prima dell'inserimento nella tabella
CREATE OR REPLACE FUNCTION before_insert_cliente()
RETURNS TRIGGER AS $$
BEGIN
    NEW.idcliente = nextval_idcliente();

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER clienti_insert
BEFORE INSERT ON clienti
FOR EACH ROW 
EXECUTE FUNCTION before_insert_cliente();


----------------------- ALTRA SEQUENZA -----------------------


-- Creazione sequenza per PK(operatori)
CREATE SEQUENCE idoperatore_seq 
	START 1
	INCREMENT 1;

-- Funzione per ottenere un nuovo valore dalla sequenza
CREATE OR REPLACE FUNCTION nextval_idoperatore()
RETURNS INTEGER AS $$
DECLARE
    next_id INTEGER;
BEGIN
    SELECT nextval('idoperatore_seq') INTO next_id;

    RETURN next_id;
END;
$$ LANGUAGE plpgsql;

-- Trigger per utilizzare la sequenza prima dell'inserimento nella tabella
CREATE OR REPLACE FUNCTION before_insert_operatore()
RETURNS TRIGGER AS $$
BEGIN
    NEW.idoperatore = nextval_idoperatore();

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER operatori_insert
BEFORE INSERT ON operatori
FOR EACH ROW 
EXECUTE FUNCTION before_insert_operatore();


----------------------- ALTRA SEQUENZA -----------------------


-- Creazione della sequenza per PK(corrieri)
CREATE SEQUENCE idcorriere_seq 
	START 1
	INCREMENT 1;

-- Funzione per ottenere un nuovo valore dalla sequenza
CREATE OR REPLACE FUNCTION nextval_idcorriere()
RETURNS INTEGER AS $$
DECLARE
    next_id INTEGER;
BEGIN
    SELECT nextval('idcorriere_seq') INTO next_id;

    RETURN next_id;
END;
$$ LANGUAGE plpgsql;

-- Trigger per utilizzare la sequenza prima dell'inserimento nella tabella
CREATE OR REPLACE FUNCTION before_insert_corriere()
RETURNS TRIGGER AS $$
BEGIN
    NEW.idcorriere = nextval_idcorriere();

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER corrieri_insert
BEFORE INSERT ON corrieri
FOR EACH ROW 
EXECUTE FUNCTION before_insert_corriere();


----------------------- ALTRA SEQUENZA -----------------------


-- Creazione della sequenza per PK(articoli)
CREATE SEQUENCE idarticolo_seq 
	START 1000
	INCREMENT 1;

-- Funzione per ottenere un nuovo valore dalla sequenza
CREATE OR REPLACE FUNCTION nextval_idarticolo()
RETURNS VARCHAR(15) AS $$
DECLARE
    next_id INTEGER;
    result_id VARCHAR(15);
BEGIN
    SELECT nextval('idarticolo_seq') INTO next_id;
     --concateno A col prossimo valore della sequenza
    result_id := 'A' || next_id::VARCHAR;

    RETURN result_id;
END;
$$ LANGUAGE plpgsql;

-- Trigger per utilizzare la sequenza prima dell'inserimento nella tabella
CREATE OR REPLACE FUNCTION before_insert_articolo()
RETURNS TRIGGER AS $$
BEGIN
    NEW.idarticolo = nextval_idarticolo();

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER articoli_insert
BEFORE INSERT ON articoli
FOR EACH ROW 
EXECUTE FUNCTION before_insert_articolo();


----------------------- ALTRA SEQUENZA -----------------------


-- Creazione sequenza per PK(spedizioni)
CREATE SEQUENCE idspedizione_seq 
	START 1000
	INCREMENT 1;

-- Funzione per ottenere un nuovo valore dalla sequenza
CREATE OR REPLACE FUNCTION nextval_idspedizione()
RETURNS VARCHAR(15) AS $$
DECLARE
    next_id INTEGER;
    result_id VARCHAR(15);
BEGIN
    SELECT nextval('idspedizione_seq') INTO next_id;
    --concateno S col prossimo valore della sequenza
    result_id := 'S' || next_id::VARCHAR;

    RETURN result_id;
END;
$$ LANGUAGE plpgsql;

-- Trigger per utilizzare la sequenza prima dell'inserimento nella tabella
CREATE OR REPLACE FUNCTION before_insert_spedizione()
RETURNS TRIGGER AS $$
BEGIN
    NEW.idspedizione = nextval_idspedizione();

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER spedizioni_insert
BEFORE INSERT ON spedizioni
FOR EACH ROW 
EXECUTE FUNCTION before_insert_spedizione();


----------------------- ALTRA SEQUENZA -----------------------


-- Creazione sequenza per PK(ordini)
CREATE SEQUENCE idordine_seq 
	START 1000
	INCREMENT 1;

-- Funzione per ottenere un nuovo valore dalla sequenza
CREATE OR REPLACE FUNCTION nextval_idordine()
RETURNS VARCHAR(15) AS $$
DECLARE
    next_id INTEGER;
    result_id VARCHAR(15);
BEGIN
    SELECT nextval('idordine_seq') INTO next_id;
    --concateno S col prossimo valore della sequenza
    result_id := 'O' || next_id::VARCHAR;

    RETURN result_id;
END;
$$ LANGUAGE plpgsql;

-- Trigger per utilizzare la sequenza prima dell'inserimento nella tabella
CREATE OR REPLACE FUNCTION before_insert_ordine()
RETURNS TRIGGER AS $$
BEGIN
    NEW.idordine = nextval_idordine();

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER ordini_insert
BEFORE INSERT ON ordini
FOR EACH ROW 
EXECUTE FUNCTION before_insert_ordine();



/* --------------- DEFINIZIONE TRIGGER PER VINCOLI --------------- */


-- TRIGGER PER CIFRARE LA PASSWORD
CREATE OR REPLACE FUNCTION cesare_plus_uno()
RETURNS TRIGGER AS $$
BEGIN
    NEW.pass := calcola_cesare_plus_uno(NEW.pass);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION calcola_cesare_plus_uno(pass VARCHAR(50))
RETURNS VARCHAR(50) AS $$
DECLARE
    result VARCHAR(50) := '';
    char_code INTEGER;
BEGIN
    FOR i IN 1..LENGTH(pass) LOOP
        char_code := ASCII(SUBSTRING(pass FROM i FOR 1));
        -- Controlla se il carattere è una lettera min dell'alfabeto
        IF char_code BETWEEN ASCII('a') AND ASCII('z') THEN
            char_code := ((char_code - ASCII('a') + 1) % 26) + ASCII('a');
        -- Controlla se il carattere è una lettera maiusc dell'alfabeto
        ELSIF char_code BETWEEN ASCII('A') AND ASCII('Z') THEN
            char_code := ((char_code - ASCII('A') + 1) % 26) + ASCII('A');
        -- Controlla se il carattere è una cifra numerica
	    ELSIF char_code BETWEEN ASCII('0') AND ASCII('9') THEN
	        char_code := ((char_code - ASCII('0') + 1) % 10) + ASCII('0');
        END IF;

        result := result || CHR(char_code);
    END LOOP;

    RETURN result;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER before_insert_pass
BEFORE INSERT ON operatori
FOR EACH ROW 
EXECUTE FUNCTION cesare_plus_uno();


---------------------------- ALTRO TRIGGER ----------------------------


-- CodFisc di operatore diverso da quelli di corriere
CREATE OR REPLACE FUNCTION is_operatore_valid()
RETURNS TRIGGER AS $$
DECLARE
    num_codfisc_uguali INTEGER := 0;
BEGIN
    -- Conto le righe di corrieri che hanno lo stesso codFiscale dell'operatore inserito
    -- Mi restituisce al piu' una riga
    SELECT COUNT(*)
    INTO num_codfisc_uguali
    FROM corrieri AS C
    WHERE C.codFiscale = NEW.codFiscale;

    IF num_codfisc_uguali = 1 THEN
        RAISE EXCEPTION 'L''operatore non puo'' essere anche un corriere, pertanto i codici fiscali devono essere diversi.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Controllo necessario sia al primo inserimento...
CREATE TRIGGER check_codfiscale_operatori
BEFORE INSERT ON operatori
FOR EACH ROW 
EXECUTE FUNCTION is_operatore_valid();

-- ...sia in caso di modifica del dato
CREATE TRIGGER check_update_codfiscale_operatori
BEFORE UPDATE ON operatori
FOR EACH ROW 
WHEN (OLD.codFiscale <> NEW.codFiscale)
EXECUTE FUNCTION is_operatore_valid();


---------------------------- ALTRO TRIGGER ----------------------------


-- Cellulare di cliente diverso da quelli di corriere
CREATE OR REPLACE FUNCTION is_cliente_valid()
RETURNS TRIGGER AS $$
DECLARE
    num_cellulari_uguali INTEGER := 0;
BEGIN
    -- Conto le righe di corrieri che hanno lo stesso cellulare del cliente inserito
    -- Mi restituisce al più una riga
    SELECT COUNT(*)
    INTO num_cellulari_uguali
    FROM corrieri AS C
    WHERE C.cellulare = NEW.cellulare;

    IF num_cellulari_uguali = 1 THEN
        RAISE EXCEPTION 'Il cliente non puo'' essere anche un corriere, pertanto i cellulari devono essere diversi.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Controllo necessario sia al primo inserimento...
CREATE TRIGGER check_cellulare_clienti
BEFORE INSERT ON clienti
FOR EACH ROW
EXECUTE FUNCTION is_cliente_valid();

-- ...sia in caso di modifica del dato
CREATE TRIGGER check_update_cellulare_clienti
BEFORE UPDATE ON clienti
FOR EACH ROW
WHEN (OLD.cellulare <> NEW.cellulare)
EXECUTE FUNCTION is_cliente_valid();


---------------------------- ALTRO TRIGGER ----------------------------


-- CodFisc di corriere diverso da quelli di operatori e 
-- cellulare di corriere diverso da quelli di clienti
CREATE OR REPLACE FUNCTION is_corriere_valid()
RETURNS TRIGGER AS $$
DECLARE
    num_codfisc_uguali INTEGER := 0;
    num_cellulari_uguali INTEGER := 0;
BEGIN
    -- Conto le righe di operatori che hanno lo stesso codFiscale del corriere inserito
    -- Mi restituisce al più una riga
    SELECT COUNT(*)
    INTO num_codfisc_uguali
    FROM operatori AS O
    WHERE O.codFiscale = NEW.codFiscale;

    -- Conto le righe di clienti che hanno lo stesso cellulare del corriere inserito
    -- Mi restituisce al più una riga
    SELECT COUNT(*)
    INTO num_cellulari_uguali
    FROM clienti AS C
    WHERE C.cellulare = NEW.cellulare;

    IF num_codfisc_uguali = 1 THEN
        RAISE EXCEPTION 'Il corriere non puo'' essere anche un operatore, pertanto i codici fiscali devono essere diversi.';
    ELSIF num_cellulari_uguali = 1 THEN
        RAISE EXCEPTION 'Il corriere non puo'' essere anche un cliente, pertanto i cellulari devono essere diversi.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Controllo necessario sia al primo inserimento...
CREATE TRIGGER check_codfiscale_cellulare_corrieri
BEFORE INSERT ON corrieri
FOR EACH ROW 
EXECUTE FUNCTION is_corriere_valid();

-- ...sia in caso di modifica del dato
CREATE TRIGGER check_update_codfiscale_cellulare_corrieri
BEFORE UPDATE ON corrieri
FOR EACH ROW 
WHEN ((OLD.codFiscale <> NEW.codFiscale) OR (OLD.cellulare <> NEW.cellulare))
EXECUTE FUNCTION is_corriere_valid();


---------------------------- ALTRO TRIGGER ----------------------------


-- Una spedizione va prima pianificata, e solo successivamente puo' avvenire
-- o, in alternativa, e' gia' avvenuta e conclusa
CREATE OR REPLACE FUNCTION is_sped_valid()
RETURNS TRIGGER AS $$
BEGIN
    NEW.avviata := FALSE;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER ricalibra_sped
BEFORE INSERT ON spedizioni
FOR EACH ROW
-- Caso in cui una spedizione ha saltato la fase di "pianifica"; 
-- E' possibile inserire una spedizione gia' completata in tempi passati
WHEN ((NEW.avviata = TRUE) AND (NEW.completata = FALSE))
EXECUTE FUNCTION is_sped_valid();


---------------------------- ALTRO TRIGGER ----------------------------


-- Peso totale delle merci che compongono un ordine 
CREATE OR REPLACE FUNCTION calcola_peso_ordine(idordineIN VARCHAR(15))
RETURNS DECIMAL(6,2) AS $$
DECLARE
    peso_par CURSOR FOR
        SELECT pesoPar
        FROM compOrdine
        WHERE idordine = idordineIN;

    exists_compOrdine INTEGER := 0;
    peso_ordine DECIMAL(6,2) := 0;
BEGIN
    -- Conto per verificare che esista almeno una riga in compOrdine associata
    SELECT COUNT(*)
    INTO exists_compOrdine
    FROM compOrdine
    WHERE idordine = idordineIN;

    -- Se l'ordine non prevede alcun articolo
    IF exists_compOrdine = 0 THEN
        RAISE EXCEPTION 'L''ordine % non prevede l''acquisto di alcun articolo. Si prega di aggiornarlo o eliminarlo.', idordineIN;
    END IF;

    -- Incremento man mano il peso dell'ordine
    FOR riga IN peso_par
    LOOP
        peso_ordine = peso_ordine + riga.pesoPar;
    END LOOP;

    RETURN peso_ordine;
END;
$$ LANGUAGE plpgsql;

-- Peso totale della merce degli ordini che compongono una spedizione
CREATE OR REPLACE FUNCTION calcola_peso_sped(idspedizioneIN VARCHAR(15))
RETURNS DECIMAL(6,2) AS $$
DECLARE
    codici_ordini CURSOR FOR
        SELECT idordine
        FROM ordini
        WHERE idspedizione = idspedizioneIN;
    
    pesoTot DECIMAL(6,2) := 0;
    exists_ordine INTEGER := 0;
BEGIN
    -- Conto per verificare che esista almeno una riga in Ordini associata
    SELECT COUNT(*)
    INTO exists_ordine
    FROM ordini
    WHERE idspedizione = idspedizioneIN;

    -- Se la spedizione non prevede di consegnare nulla
    IF exists_ordine = 0 THEN
        RAISE EXCEPTION 'La spedizione non prevede di consegnare nulla. Si prega di aggiornare prima quali ordini avranno tale spedizione.';
    END IF;

    -- Ottenuto il peso di ogni ordine, procedo a sommarlo nel peso totale
    FOR riga_cod IN codici_ordini
    LOOP
        pesoTot = pesoTot + calcola_peso_ordine(riga_cod.idordine);
    END LOOP;

    RETURN pesoTot;
END;
$$ LANGUAGE plpgsql;

-- Pianifica spedizione 1-2, corriere e veicolo disponibilita' 1
CREATE OR REPLACE FUNCTION are_corriere_veicolo_validi()
RETURNS TRIGGER AS $$
DECLARE
    disp_corriere BOOLEAN;
    disp_veicolo BOOLEAN;
    peso_da_sostenere DECIMAL(6,2) := 0;
    capacita_di_peso DECIMAL(6,2);
BEGIN
    -- Seleziono e salvo la disponibilita' del corriere incaricato
    SELECT disponibilita
    INTO disp_corriere
    FROM corrieri
    WHERE idcorriere = NEW.idcorriere;

    -- Seleziono e salvo la disponibilita' e peso supportato del veicolo adoperato
    SELECT disponibilita, pesoSupportato
    INTO disp_veicolo, capacita_di_peso
    FROM veicoli
    WHERE targa = NEW.targa;

    peso_da_sostenere = calcola_peso_sped(NEW.idspedizione);

    -- Controlla se il corriere non e' disponibile
    IF disp_corriere = FALSE THEN
        RAISE EXCEPTION 'Il corriere deve risultare disponibile per poterlo incaricare della spedizione.';
    -- Controlla se il veicolo non e' disponibile
    ELSIF disp_veicolo = FALSE THEN
        RAISE EXCEPTION 'Il veicolo deve risultare disponibile per poterlo adoperare nella spedizione.';
    -- Controlla se il veicolo regge il peso della merce
    ELSIF capacita_di_peso < peso_da_sostenere THEN
        RAISE EXCEPTION 'Si prega di selezionare un veicolo adatto al peso della spedizione, oppure di ripartire diversamente gli ordini.';
    END IF;

    -- Se tutto e' ok, permetto avviata = TRUE, ma non prima di aver aggiornato data e orario
    NEW.data_sped := CURRENT_DATE;
    NEW.orario := (DATE_TRUNC('minute', CURRENT_TIMESTAMP))::time;



    -- Una spedizione dve prima essere avviata, e poi puO' essere completata
    NEW.completata := FALSE;

    -- Devo inoltre aggiornare a FALSE la disponibilita' di corriere e veicolo
    UPDATE corrieri
    SET disponibilita = FALSE
    WHERE idcorriere = NEW.idcorriere;

    UPDATE veicoli
    SET disponibilita = FALSE
    WHERE targa = NEW.targa;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER pianifica_sped
BEFORE UPDATE ON spedizioni
FOR EACH ROW
WHEN ((OLD.avviata = FALSE) AND (NEW.avviata = TRUE))
EXECUTE FUNCTION are_corriere_veicolo_validi();


---------------------------- ALTRO TRIGGER ----------------------------


-- Consequenza del precedente trigger, aggiorna disponibilita' di corriere e veicolo precedentemente coinvolti
CREATE OR REPLACE FUNCTION update_disponibilita_true()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE corrieri
    SET disponibilita = TRUE
    WHERE idcorriere = OLD.idcorriere;

    UPDATE veicoli
    SET disponibilita = TRUE
    WHERE targa = OLD.targa;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER aggiorna_disponibilita_sped_cancellata
BEFORE DELETE ON spedizioni
FOR EACH ROW
WHEN ((OLD.avviata = TRUE) AND (OLD.completata = FALSE))
EXECUTE FUNCTION update_disponibilita_true();

CREATE TRIGGER aggiorna_disponibilita_sped_cambiata
BEFORE UPDATE ON spedizioni
FOR EACH ROW
WHEN (((OLD.avviata = TRUE) AND (OLD.completata = FALSE)) AND (NEW.avviata = FALSE))
EXECUTE FUNCTION update_disponibilita_true();


---------------------------- ALTRO TRIGGER ----------------------------


-- Componi spedizione
CREATE OR REPLACE FUNCTION is_possible_ordine_in_spedizione()
RETURNS TRIGGER AS $$
DECLARE
    sped_avviata BOOLEAN;
    sped_completata BOOLEAN;
BEGIN
    -- Seleziono e salvo gli attributi che descrivono se la spedizione è partita (ed eventualmente finita)
    SELECT avviata, completata
    INTO sped_avviata, sped_completata
    FROM spedizioni
    WHERE idspedizione = NEW.idspedizione;

    -- Se si tratta di una spedizione che sta avvenendo
    IF sped_avviata = TRUE AND sped_completata = FALSE THEN
        RAISE EXCEPTION 'Non e'' possibile aggiungere un ordine ad una spedizione gia'' avviata.';
    END IF;
    -- Nota: nel caso di sped_completata = TRUE, e' possibile inserire degli ordini con il riferimento a tale
    -- spedizione per permettere all'utente di inserire, qualora volesse, spedizioni che si sono verificate 
    -- in passato, senza pero' perdere il riferimento negli ordini.
    -- (prima creo tupla in spedizioni, poi aggiorno gli ordini con il giusto idspedizione)

    -- Se tutto e' ok, permetto il nuovo idspedizione
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER componi_sped
BEFORE UPDATE ON ordini
FOR EACH ROW
WHEN ((OLD.idspedizione IS NULL) OR (OLD.idspedizione <> NEW.idspedizione))
EXECUTE FUNCTION is_possible_ordine_in_spedizione();


---------------------------- ALTRO TRIGGER ----------------------------


-- Componi ordine e aggiorna scorte e ordine (prezzoTot, pesoPar, prezzoPar)
CREATE OR REPLACE FUNCTION is_possible_articolo_in_ordine()
RETURNS TRIGGER AS $$
DECLARE
    quantita_in_magazzino INTEGER;
    peso_articolo DECIMAL(5,2);
    prezzo_articolo DECIMAL(8,2);
BEGIN
   -- Seleziono e salvo la quantita' di uno specifico articolo in magazzino
    SELECT quantita, peso, prezzo
    INTO quantita_in_magazzino, peso_articolo, prezzo_articolo
    FROM articoli
    WHERE idarticolo = NEW.idarticolo;

    -- Se la quantita' dell'ordine e' maggiore di quella in magazzino
    IF quantita_in_magazzino < NEW.quantita THEN
        RAISE EXCEPTION 'Non e'' possibile ultimare l''ordine; articolo non disponibile al momento nelle quantita'' richieste.';
    END IF;

    -- Se tutto e' ok, permetto l'inserimento in compOrdine, ma non prima di aggiornare le scorte in magazzino
    UPDATE articoli 
    SET quantita = quantita - NEW.quantita
    WHERE idarticolo = NEW.idarticolo;

    -- Devo aggiornare anche gli attributi derivabili
    NEW.pesoPar := NEW.quantita * peso_articolo;
    NEW.prezzoPar := NEW.quantita * prezzo_articolo;

    UPDATE ordini
    SET prezzoTot = prezzoTot + NEW.prezzoPar
    WHERE idordine = NEW.idordine;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER componi_ordine_poi_aggiorna
BEFORE INSERT ON CompOrdine
FOR EACH ROW
EXECUTE FUNCTION is_possible_articolo_in_ordine();

-- Non prevedo la possibilita' di update in compOrdine perche' non
-- rientra nei poteri dell'utente


---------------------------- ALTRO TRIGGER ----------------------------


-- Aggiorna scorte se compOrdine cancellato
CREATE OR REPLACE FUNCTION update_scorte()
RETURNS TRIGGER AS $$
DECLARE 
    sped_avviata BOOLEAN;
BEGIN
    -- Seleziono e salvo se l'ordine fa parte di una spedizione gia' avviata
    SELECT avviata
    INTO sped_avviata
    FROM spedizioni NATURAL JOIN ordini
    WHERE idordine = NEW.idordine;

    IF sped_avviata = TRUE THEN
        RAISE EXCEPTION 'Non e'' possibile cancellare la composizione di un ordine gia'' in spedizione.';
    END IF;

    UPDATE articoli 
    SET quantita = quantita + OLD.quantita
    WHERE idarticolo = OLD.idarticolo;

    -- Sottraggo una componente che prima era stata sommata, 
    -- ergo non ho problemi di dominio su prezzoTot
    UPDATE ordini
    SET prezzoTot = prezzoTot - OLD.prezzoPar
    WHERE idordine = OLD.idordine;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER aggiorna_scorte_non_piu_in_ordine
BEFORE DELETE ON CompOrdine
FOR EACH ROW
EXECUTE FUNCTION update_scorte();


---------------------------- ALTRO TRIGGER ----------------------------


-- Quando gli ordini possono risultare consegnati?
-- Quando fanno parte di una spedizione ed essa e' stata avviata
CREATE OR REPLACE FUNCTION is_possible_ordine_consegnato()
RETURNS TRIGGER AS $$
DECLARE
    spedizione_avviata BOOLEAN;
BEGIN
    IF NEW.idspedizione IS NULL THEN
        RAISE EXCEPTION 'L''ordine non e'' associato a nessuna spedizione; pertanto, non puo'' risultare consegnato.';
    END IF;

    SELECT avviata
    INTO spedizione_avviata
    FROM spedizioni
    WHERE idspedizione = NEW.idspedizione;

    IF spedizione_avviata = FALSE THEN
        RAISE EXCEPTION 'L''ordine e'' associato ad una spedizione che risulta non essere ancora avvenuta, quindi non puo'' essere stato consegnato.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Possibile ordine consegnato
CREATE TRIGGER update_ordine_consegnabile
BEFORE UPDATE ON ordini
FOR EACH ROW
WHEN (NEW.consegnato = TRUE)
EXECUTE FUNCTION is_possible_ordine_consegnato();

CREATE TRIGGER ordine_consegnabile_con_insert
BEFORE INSERT ON ordini
FOR EACH ROW
WHEN (NEW.consegnato = TRUE)
EXECUTE FUNCTION is_possible_ordine_consegnato();


---------------------------- ALTRO TRIGGER ----------------------------


-- Spedizione completata 1 and Corriere e veicolo disponibilita' 2
CREATE OR REPLACE FUNCTION update_ordini_consegnato()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE ordini
    SET consegnato = TRUE
    WHERE idspedizione = NEW.idspedizione;

    UPDATE corrieri
    SET disponibilita = TRUE
    WHERE idcorriere = OLD.idcorriere;

    UPDATE veicoli
    SET disponibilita = TRUE
    WHERE targa = OLD.targa;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER spedizione_completata_then_ordini_consegnati
AFTER UPDATE ON spedizioni
FOR EACH ROW
WHEN (NEW.completata = TRUE)
EXECUTE FUNCTION update_ordini_consegnato();


---------------------------- ALTRO TRIGGER ----------------------------


-- Spedizione completata 2
CREATE OR REPLACE FUNCTION update_sped_completata()
RETURNS TRIGGER AS $$
DECLARE
    ordini_consegnato CURSOR FOR
        SELECT consegnato
        FROM ordini
        WHERE idspedizione = NEW.idspedizione;

    ordini_non_consegnati INTEGER := 0;
    sped_completata BOOLEAN;
BEGIN
    SELECT completata
    INTO sped_completata
    FROM spedizioni 
    WHERE idspedizione = NEW.idspedizione;

    -- Lo stack esplode causa loop se non eseguo questo check
    IF sped_completata <> TRUE THEN

        FOR riga IN ordini_consegnato
        LOOP
            IF riga.consegnato = FALSE THEN
                ordini_non_consegnati = ordini_non_consegnati + 1;
            END IF;
        END LOOP;

        IF ordini_non_consegnati = 0 THEN
            UPDATE spedizioni
            SET completata = TRUE
            WHERE idspedizione = NEW.idspedizione;
        END IF;

    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER ordini_consegnati_then_sped_completata
AFTER UPDATE ON ordini
FOR EACH ROW
WHEN (NEW.consegnato = TRUE)
EXECUTE FUNCTION update_sped_completata();


/* --------------- DEFINIZIONE FUNZIONI PER JAVA --------------- */


CREATE OR REPLACE FUNCTION get_id_operatore(usernameINP VARCHAR, passINP VARCHAR) 
RETURNS INT AS $$
DECLARE 
    id_operatore INT;
BEGIN
    SELECT idoperatore INTO id_operatore 
    FROM operatori 
    WHERE username = usernameINP AND pass = passINP;

    IF id_operatore IS NULL THEN
        RAISE EXCEPTION 'Operatore non trovato';
    END IF;

    RETURN id_operatore;
END;
$$ LANGUAGE plpgsql;


---------------------------- ALTRA FUNZIONE ----------------------------


CREATE OR REPLACE FUNCTION get_id_corriere(nome_corriere VARCHAR, cognome_corriere VARCHAR) 
RETURNS INT AS $$
DECLARE 
    id_corriere INT;
BEGIN
    SELECT idcorriere INTO id_corriere 
    FROM corrieri 
    WHERE nome = nome_corriere AND cognome = cognome_corriere;

    IF id_corriere IS NULL THEN
        RAISE EXCEPTION 'Corriere non trovato';
    END IF;

    RETURN id_corriere;
END;
$$ LANGUAGE plpgsql;


---------------------------- INSERT/UPDATE SAMPLES ----------------------------

-- CLIENTE
INSERT INTO clienti VALUES 
(DEFAULT, 'Vit', 'Testa', 'Via Giotto', '15', '80024', '3196420123', 'vit@unina.it'),
(DEFAULT, 'Marco', 'Verdi', 'Via delle Rose', '10', '20121', '3391122334', 'marco.verdi@gmail.com'),
(DEFAULT, 'Anna', 'Rossi', 'Piazza del Popolo', '1', '50122', '3334445566', 'anna.rossi@yahoo.it'),
(DEFAULT, 'Francesca', 'Neri', 'Via Milano', '5', '80122', '3409988776', 'francesco.ne@gmail.it'),
(DEFAULT, 'Alessandro', 'Bianchi', 'Via Roma', '45', '10121', '3478899001', 'alessandro.bianchi@gmail.com'),
(DEFAULT, 'Maria', 'Verdi', 'Piazza Garibaldi', '12', '80134', '3336677889', 'maria.verdi@libero.it'),
(DEFAULT, 'Luisa', 'Rossi', 'Via Dante', '7', '20122', '3402233445', 'luisa.rossi@hotmail.com'),
(DEFAULT, 'Giovanni', 'Neri', 'Via Manzoni', '50', '80131', '3456678990', 'giovanni.neri@unina.it'),
(DEFAULT, 'Elena', 'Moretti', 'Corso Umberto', '22', '50123', '3201122334', 'elena.moretti@yahoo.it'),
(DEFAULT, 'Roberto', 'Ferri', 'Via Milano', '18', '20124', '3285566778', 'roberto.ferri@gmail.com'),
(DEFAULT, 'Chiara', 'Conti', 'Via Napoli', '3', '80136', '3388899001', 'chiara.conti@outlook.com'),
(DEFAULT, 'Simone', 'Marini', 'Via Firenze', '27', '50134', '3396677888', 'simone.marini@virgilio.it'), 
(DEFAULT, 'Federico', 'Baldini', 'Via Leopardi', '9', '80141', '3345566778', 'federico.baldini@gmail.com'),
(DEFAULT, 'Sara', 'Fontana', 'Via Venezia', '11', '20135', '3278899002', 'sara.fontana@libero.it'),
(DEFAULT, 'Matteo', 'Lombardi', 'Via Roma', '28', '50125', '3461122335', 'matteo.lombardi@hotmail.com'),
(DEFAULT, 'Giorgia', 'Esposito', 'Via Torino', '33', '80142', '3296677887', 'giorgia.esposito@unina.it'),
(DEFAULT, 'Davide', 'Rinaldi', 'Corso Garibaldi', '5', '20121', '3379988776', 'davide.rinaldi@yahoo.it');

-- OPERATORE
INSERT INTO operatori VALUES
(DEFAULT, 'Luigi', 'Dota', 'DTOLGU03T01F123L', 'lagia', 'Luigi2003'),
(DEFAULT, 'Gianluca', 'Fiorentino', 'FRNGLC03R01M654T', 'stock', 'Luca2003');

-- CORRIERE
INSERT INTO corrieri VALUES				-- default = true
(DEFAULT, 'Valeria', 'Sipa', 'SPIVLR96T01S253Z', '3901231234', DEFAULT),
(DEFAULT, 'Cane', 'Pazzo', 'PZZCNA93R01M654T', '3196420124', DEFAULT),
(DEFAULT, 'Giulia', 'Manni', 'MNAGIU93T12L840Q', '3456677889', DEFAULT),
(DEFAULT, 'Lorenzo', 'Conti', 'CNTLNZ85A01F205L', '3312233445', DEFAULT),
(DEFAULT, 'Marco', 'Galli', 'GLLMRC93T01Q277A', '3386600123', DEFAULT);

-- ARTICOLO
INSERT INTO articoli VALUES                  -- quantita, peso in kg, prezzo in euro per una quantità
(DEFAULT, 'Pane', 'Baguette di origine francese', 500, 0.80, 0.50),
(DEFAULT, 'Pane', 'Pane cafone', 300, 1.00, 1.20),
(DEFAULT, 'Arancia', 'Arance fresche', 150, 1.00, 0.70),
(DEFAULT, 'Mela', 'Mele fresche', 200, 0.15, 0.80),
(DEFAULT, 'Latte', 'Latte fresco intero', 100, 1.00, 1.50),
(DEFAULT, 'Uova', 'Confezione da 12 uova', 50, 0.50, 2.00),
(DEFAULT, 'Pasta', 'Spaghetti di grano duro', 1000, 1.00, 1.10),
(DEFAULT, 'Riso', 'Riso Arborio per risotti', 500, 1.00, 2.50),
(DEFAULT, 'Olio', 'Olio extravergine d''oliva (bottiglia singola)', 200, 0.75, 5.50),
(DEFAULT, 'Zucchero', 'Zucchero bianco', 800, 1.00, 1.20),
(DEFAULT, 'Farina', 'Farina di grano tenero tipo 00', 600, 1.00, 0.90),
(DEFAULT, 'Biscotti', 'Biscotti al cioccolato', 300, 0.50, 2.30),
(DEFAULT, 'Cereali', 'Cereali integrali', 250, 0.50, 3.00),
(DEFAULT, 'Burro', 'Burro fresco', 150, 0.25, 2.20),
(DEFAULT, 'Yogurt', 'Yogurt alla fragola', 200, 0.12, 0.80),
(DEFAULT, 'Prosciutto', 'Prosciutto crudo affettato', 100, 0.15, 3.50),
(DEFAULT, 'Formaggio', 'Parmigiano Reggiano', 300, 0.50, 10.00),
(DEFAULT, 'Tonno', 'Tonno in scatola sott''olio', 500, 0.20, 1.50),
(DEFAULT, 'Cioccolato', 'Tavoletta di cioccolato fondente', 400, 0.10, 2.20),
(DEFAULT, 'Pizza', 'Pizza surgelata margherita', 100, 0.40, 3.50),
(DEFAULT, 'Patatine', 'Patatine in busta', 250, 0.20, 1.50),
(DEFAULT, 'Acqua', 'Bottiglia d''acqua naturale 1.5L (bottiglia singola)', 1000, 1.50, 0.50),
(DEFAULT, 'Acqua', 'Bottiglia d''acqua naturale 1.5L (confezione da 6)', 350, 9.00, 2.99),
(DEFAULT, 'Succhi di frutta', 'Succo di frutta alla pesca', 300, 1.00, 1.20),
(DEFAULT, 'Caffè', 'Caffè macinato per moka', 200, 0.25, 3.90),
(DEFAULT, 'Tè', 'Bustine di tè verde', 150, 0.20, 2.50),
(DEFAULT, 'Detersivo', 'Detersivo per lavatrice', 50, 1.50, 5.00);

-- VEICOLO
INSERT INTO veicoli VALUES 	-- default = true
('DM555AA', 450, DEFAULT),
('AA567SS', 169, FALSE),
('SS420CA', 69, DEFAULT),
('FF110SE', 118, DEFAULT),
('WS431CD', 200, DEFAULT),
('PA345CE', 5, DEFAULT);

-- ORDINE
INSERT INTO ordini VALUES 				-- mancano info su operatore e spedizione
(DEFAULT, '2023-10-01', 'Via Dante', '7', '80122', 'Napoli', '3402233445', DEFAULT, DEFAULT, 1),
(DEFAULT, '2023-10-01', 'Via Manzoni', '50', '80131', 'Napoli', '3456678990', DEFAULT, DEFAULT, 2),
(DEFAULT, '2023-10-01', 'Corso Umberto', '22', '50123', 'Firenze', '3201122334', DEFAULT, DEFAULT, 3),
(DEFAULT, '2023-10-04', 'Via Milano', '18', '20124', 'Milano', '3285566778', DEFAULT, DEFAULT, 4),
(DEFAULT, '2023-10-04', 'Via Napoli', '3', '80136', 'Napoli', '3388899001', DEFAULT, DEFAULT, 5),
(DEFAULT, '2023-10-06', 'Via Firenze', '27', '50134', 'Firenze', '3396677888', DEFAULT, DEFAULT, 6),
(DEFAULT, '2023-10-07', 'Via Leopardi', '9', '80141', 'Napoli', '3345566778', DEFAULT, DEFAULT, 7),
(DEFAULT, '2023-10-08', 'Via Venezia', '11', '20135', 'Milano', '3278899002', DEFAULT, DEFAULT, 8),
(DEFAULT, '2023-10-09', 'Via Roma', '28', '50125', 'Firenze', '3461122335', DEFAULT, DEFAULT, 9),
(DEFAULT, '2023-10-12', 'Via Torino', '33', '80142', 'Napoli', '3296677887', DEFAULT, DEFAULT, 10),

(DEFAULT, '2023-10-13', 'Corso Garibaldi', '5', '20121', 'Milano', '3379988776', DEFAULT, DEFAULT, 11),
(DEFAULT, '2023-10-14', 'Piazza Garibaldi', '12', '80134', 'Napoli', '3336677889', DEFAULT, DEFAULT, 12),
(DEFAULT, '2023-10-15', 'Piazza del Popolo', '1', '50122', 'Firenze', '3334445566', DEFAULT, DEFAULT, 13),
(DEFAULT, '2023-10-16', 'Via delle Rose', '10', '20121', 'Milano', '3391122334', DEFAULT, DEFAULT, 14),
(DEFAULT, '2023-10-20', 'Via Roma', '45', '10121', 'Torino', '3478899001', DEFAULT, DEFAULT, 15),
(DEFAULT, '2023-10-21', 'Via Dante', '7', '20122', 'Milano', '3402233445', DEFAULT, DEFAULT, 16),
(DEFAULT, '2023-10-22', 'Via Leopardi', '9', '80141', 'Napoli', '3345566778', DEFAULT, DEFAULT, 17),
(DEFAULT, '2023-11-05', 'Via Roma', '12', '80131', 'Napoli', '3456678990', DEFAULT, DEFAULT, 3),
(DEFAULT, '2023-11-05', 'Via Dante', '45', '20122', 'Milano', '3402233445', DEFAULT, DEFAULT, 7),
(DEFAULT, '2023-11-05', 'Corso Umberto', '2', '50123', 'Firenze', '3201122334', DEFAULT, DEFAULT, 12),

(DEFAULT, '2023-11-05', 'Via Napoli', '9', '80136', 'Napoli', '3388899001', DEFAULT, DEFAULT, 5),
(DEFAULT, '2023-11-10', 'Piazza Garibaldi', '12', '80134', 'Napoli', '3336677889', DEFAULT, DEFAULT, 6),
(DEFAULT, '2023-11-11', 'Via delle Rose', '45', '20121', 'Milano', '3391122334', DEFAULT, DEFAULT, 2),
(DEFAULT, '2023-11-11', 'Via Leopardi', '23', '80141', 'Napoli', '3345566778', DEFAULT, DEFAULT, 10),
(DEFAULT, '2023-11-11', 'Via Torino', '33', '80142', 'Napoli', '3296677887', DEFAULT, DEFAULT, 8),
(DEFAULT, '2023-11-11', 'Via Napoli', '6', '80136', 'Napoli', '3388899001', DEFAULT, DEFAULT, 4),
(DEFAULT, '2023-11-11', 'Via Dante', '27', '20122', 'Milano', '3402233445', DEFAULT, DEFAULT, 15),
(DEFAULT, '2023-12-02', 'Via Roma', '15', '10121', 'Torino', '3478899001', DEFAULT, DEFAULT, 12),
(DEFAULT, '2023-12-02', 'Via Dante', '5', '20122', 'Milano', '3402233445', DEFAULT, DEFAULT, 2),
(DEFAULT, '2023-12-02', 'Via Manzoni', '50', '80131', 'Napoli', '3456678990', DEFAULT, DEFAULT, 9),

(DEFAULT, '2023-12-08', 'Via Venezia', '3', '20135', 'Milano', '3278899002', DEFAULT, DEFAULT, 13),
(DEFAULT, '2023-12-08', 'Via Roma', '6', '10121', 'Torino', '3478899001', DEFAULT, DEFAULT, 15),
(DEFAULT, '2023-12-10', 'Via Dante', '2', '20122', 'Milano', '3402233445', DEFAULT, DEFAULT, 16),
(DEFAULT, '2023-12-23', 'Via Torino', '45', '80142', 'Napoli', '3296677887', DEFAULT, DEFAULT, 9),
(DEFAULT, '2023-12-23', 'Via Firenze', '7', '50134', 'Firenze', '3396677888', DEFAULT, DEFAULT, 13),
(DEFAULT, '2024-01-03', 'Via Roma', '12', '80131', 'Napoli', '3456678990', DEFAULT, DEFAULT, 4),
(DEFAULT, '2024-01-03', 'Via Torino', '8', '80142', 'Napoli', '3296677887', DEFAULT, DEFAULT, 11),
(DEFAULT, '2024-01-04', 'Via Firenze', '14', '50134', 'Firenze', '3396677888', DEFAULT, DEFAULT, 8),
(DEFAULT, '2024-01-05', 'Via Napoli', '6', '80136', 'Napoli', '3388899001', DEFAULT, DEFAULT, 5),
(DEFAULT, '2024-01-05', 'Piazza Garibaldi', '18', '80134', 'Napoli', '3336677889', DEFAULT, DEFAULT, 2),

(DEFAULT, '2024-01-07', 'Via Venezia', '7', '20135', 'Milano', '3278899002', DEFAULT, DEFAULT, 15),
(DEFAULT, '2024-01-08', 'Via Leopardi', '9', '80141', 'Napoli', '3345566778', DEFAULT, DEFAULT, 16),
(DEFAULT, '2024-01-09', 'Via Leopardi', '7', '80141', 'Napoli', '3345566778', DEFAULT, DEFAULT, 12),
(DEFAULT, '2024-01-10', 'Piazza Garibaldi', '30', '80134', 'Napoli', '3336677889', DEFAULT, DEFAULT, 3),
(DEFAULT, '2024-01-17', 'Via Venezia', '12', '20135', 'Milano', '3278899002', DEFAULT, DEFAULT, 6),
(DEFAULT, '2024-01-24', 'Via Dante', '5', '20122', 'Milano', '3402233445', DEFAULT, DEFAULT, 14),
(DEFAULT, '2024-01-24', 'Via Manzoni', '2', '80131', 'Napoli', '3456678990', DEFAULT, DEFAULT, 11),
(DEFAULT, '2024-01-30', 'Via Torino', '33', '80142', 'Napoli', '3296677887', DEFAULT, DEFAULT, 2),
(DEFAULT, '2024-01-30', 'Via Firenze', '10', '50134', 'Firenze', '3396677888', DEFAULT, DEFAULT, 15),
(DEFAULT, '2024-01-31', 'Via Firenze', '10', '50134', 'Firenze', '3396677888', DEFAULT, DEFAULT, 15);

-- COMPORDINE
INSERT INTO compOrdine VALUES -- quantita, pesoPar, prezzoPar
('O1000', 'A1000', 3, 0, 0),
('O1000', 'A1001', 2, 0, 0),
('O1001', 'A1002', 5, 0, 0),
('O1001', 'A1003', 4, 0, 0),
('O1001', 'A1004', 1, 0, 0),
('O1002', 'A1005', 10, 0, 0),
('O1003', 'A1007', 6, 0, 0),
('O1003', 'A1008', 1, 0, 0),
('O1003', 'A1009', 3, 0, 0),
('O1004', 'A1010', 7, 0, 0),
('O1004', 'A1011', 8, 0, 0),
('O1005', 'A1012', 4, 0, 0),
('O1005', 'A1013', 5, 0, 0),
('O1005', 'A1014', 6, 0, 0),
('O1006', 'A1015', 3, 0, 0),
('O1006', 'A1016', 2, 0, 0),
('O1007', 'A1017', 5, 0, 0),
('O1007', 'A1018', 4, 0, 0),
('O1008', 'A1019', 6, 0, 0),
('O1008', 'A1020', 1, 0, 0),
('O1009', 'A1021', 7, 0, 0),
('O1009', 'A1022', 8, 0, 0),
('O1010', 'A1023', 5, 0, 0),
('O1011', 'A1025', 4, 0, 0),
('O1011', 'A1026', 6, 0, 0),
('O1012', 'A1000', 10, 0, 0),
('O1012', 'A1001', 2, 0, 0),
('O1013', 'A1002', 3, 0, 0),
('O1013', 'A1003', 5, 0, 0),
('O1013', 'A1004', 7, 0, 0),
('O1014', 'A1005', 6, 0, 0),
('O1014', 'A1006', 3, 0, 0),
('O1015', 'A1007', 8, 0, 0),
('O1015', 'A1008', 9, 0, 0),
('O1016', 'A1010', 5, 0, 0),
('O1017', 'A1011', 7, 0, 0),
('O1017', 'A1012', 3, 0, 0),
('O1017', 'A1013', 4, 0, 0),
('O1018', 'A1014', 2, 0, 0),
('O1018', 'A1015', 6, 0, 0),
('O1019', 'A1016', 10, 0, 0),
('O1019', 'A1017', 5, 0, 0),
('O1020', 'A1018', 6, 0, 0),
('O1020', 'A1019', 4, 0, 0),
('O1021', 'A1020', 5, 0, 0),
('O1021', 'A1021', 3, 0, 0),
('O1022', 'A1022', 7, 0, 0),
('O1022', 'A1023', 4, 0, 0),
('O1022', 'A1024', 2, 0, 0),
('O1023', 'A1025', 8, 0, 0),
('O1023', 'A1026', 9, 0, 0),
('O1024', 'A1000', 6, 0, 0),
('O1024', 'A1001', 10, 0, 0),
('O1025', 'A1002', 5, 0, 0),
('O1025', 'A1003', 7, 0, 0),
('O1025', 'A1004', 6, 0, 0),
('O1026', 'A1005', 8, 0, 0),
('O1026', 'A1006', 9, 0, 0),
('O1027', 'A1007', 6, 0, 0),
('O1027', 'A1008', 5, 0, 0),
('O1028', 'A1009', 7, 0, 0),
('O1028', 'A1010', 10, 0, 0),
('O1029', 'A1011', 6, 0, 0),
('O1029', 'A1012', 5, 0, 0),
('O1029', 'A1013', 2, 0, 0),
('O1030', 'A1014', 3, 0, 0),
('O1030', 'A1015', 4, 0, 0),
('O1030', 'A1016', 6, 0, 0),
('O1031', 'A1017', 5, 0, 0),
('O1031', 'A1018', 7, 0, 0),
('O1032', 'A1019', 8, 0, 0),
('O1032', 'A1020', 6, 0, 0),
('O1033', 'A1021', 5, 0, 0),
('O1033', 'A1022', 4, 0, 0),
('O1034', 'A1023', 3, 0, 0),
('O1034', 'A1024', 6, 0, 0),
('O1035', 'A1025', 4, 0, 0),
('O1035', 'A1026', 7, 0, 0),
('O1036', 'A1000', 2, 0, 0),
('O1036', 'A1001', 1, 0, 0),
('O1037', 'A1002', 3, 0, 0),
('O1037', 'A1003', 4, 0, 0),
('O1038', 'A1004', 5, 0, 0),
('O1038', 'A1005', 6, 0, 0),
('O1039', 'A1006', 7, 0, 0),
('O1039', 'A1007', 8, 0, 0),
('O1040', 'A1008', 6, 0, 0),
('O1040', 'A1009', 9, 0, 0),
('O1041', 'A1010', 4, 0, 0),
('O1041', 'A1011', 7, 0, 0),
('O1042', 'A1012', 3, 0, 0),
('O1042', 'A1013', 2, 0, 0),
('O1043', 'A1014', 6, 0, 0),
('O1043', 'A1015', 5, 0, 0),
('O1044', 'A1016', 4, 0, 0),
('O1044', 'A1017', 6, 0, 0),
('O1045', 'A1018', 8, 0, 0),
('O1045', 'A1019', 3, 0, 0),
('O1046', 'A1020', 9, 0, 0),
('O1046', 'A1021', 7, 0, 0),
('O1047', 'A1022', 10, 0, 0),
('O1048', 'A1024', 5, 0, 0),
('O1048', 'A1025', 8, 0, 0),
('O1049', 'A1026', 6, 0, 0),
('O1049', 'A1000', 7, 0, 0);

-- SPEDIZIONE
INSERT INTO spedizioni VALUES		-- idOp, idCor
(DEFAULT, '2023-10-19', DEFAULT, TRUE, TRUE, 1, 1, 'DM555AA'),
(DEFAULT, '2023-10-31', DEFAULT, TRUE, TRUE, 2, 2, 'SS420CA'),
(DEFAULT, '2023-11-14', DEFAULT, TRUE, TRUE, 2, 3, 'WS431CD'),
(DEFAULT, '2023-12-10', DEFAULT, TRUE, TRUE, 1, 4, 'FF110SE'),
(DEFAULT, '2023-12-24', DEFAULT, TRUE, TRUE, 1, 1, 'SS420CA');

UPDATE ordini
SET idoperatore = 1, idspedizione = 'S1000', consegnato = TRUE
WHERE idordine BETWEEN 'O1000' AND 'O1009';

/*  
 * Non posso avviare la spedizione 1 con targa 'PA345CE'
 *
 * UPDATE SPEDIZIONI
 * SET AVVIATA = TRUE
 * WHERE idspedizione = 'S1000'
 *
 * Infatti, se provassi a farlo, il veicolo selezionato non sarebbe del peso adeguato.
 * Bisogna dunque cambiare il veicolo adoperato per la spedizione in questione
 *
 */
 

UPDATE ordini
SET idoperatore = 2, idspedizione = 'S1001', consegnato = TRUE
WHERE idordine BETWEEN 'O1010' AND 'O1016';

UPDATE ordini
SET idoperatore = 2, idspedizione = 'S1002', consegnato = TRUE
WHERE idordine BETWEEN 'O1017' AND 'O1026';

UPDATE ordini
SET idoperatore = 1, idspedizione = 'S1003', consegnato = TRUE
WHERE idordine BETWEEN 'O1027' AND 'O1031';

UPDATE ordini
SET idoperatore = 1, idspedizione = 'S1004', consegnato = TRUE
WHERE idordine BETWEEN 'O1032' AND 'O1034';
 
/*
UPDATE spedizioni
SET targa = 'DM555AA'
WHERE idspedizione = 'S1000';

UPDATE spedizioni
SET avviata = TRUE
WHERE idspedizione = 'S1000';

-- Per controllare che sia tutto in regola:
-- SELECT * FROM spedizioni;

-- Possiamo controllare anche che gli articoli dal magazzino siano stati effettivamente scalati
-- SELECT * FROM articoli;

-- E così per tutte le altre tabelle, come Ordine e CompOrdine con gli attributi derivabili

-- Diciamo adesso che gli ordini sono stati consegnati:
UPDATE ordini
SET consegnato = TRUE
WHERE idordine = 'O1000';

-- E, in un momento diverso gli altri...
UPDATE ordini
SET consegnato = TRUE
WHERE idordine BETWEEN 'O1001' AND 'O1009';

-- Possiamo notare come per effetto del trigger, la spedizione, che ha consegnato tutti i pacchi, risulti completata
-- SELECT * FROM spedizioni;*/
