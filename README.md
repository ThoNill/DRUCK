DRUCK
=====

Print rendering library, output formats: Postscript, PDF

Diese Library erstellt aus einer Textdatei, die
die Daten enthält eine PS oder PDF Datei.

Die Datendatei hat folgendes Format:

Befehl| ... durch | getrennte Daten ,,,,

in dieser Datei wird auch festgelegt, welches Layout
benutzt wird. Es können verschiedene Layouts hintereinander
folgen.

Das Layout wird über XML Dateien definiert.
Es kann Verweise auf andere Layouts enthalten.

Es werden keine Datenbankverbindungen genutzt.
Die Textdatei wird von einem getrennten Prozess erstellt
und fällt nicht unter diese Library, es geht 
"nur" um die Aufbereitung zu Ausdruck.
