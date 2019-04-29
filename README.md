# Grib Translate

Questo programma serve a tradurre un file di tipo *.grib in un file *.sparql. <br>
Il software è funzionante solo in parte.<br>

Per ora, una volta inserito il file all'interno della cartella dove è contenuto il package del progetto,
si possono inserire valori in termine di longitudine e latitudine. <br>

Viene successivamente restituito la temperatura che in un certo orario ad un certo giorno 
(il programma prende questi valori automaticamente dalle sezioni 1-2 del file grib) era presente nella posizione specificata dall'utente. <br>

Tutto questo viene registrato in un file log.txt, ogni movimento ed ogni accesso al file vengono presi in considerazione
tramite l'applet NTSystem.
