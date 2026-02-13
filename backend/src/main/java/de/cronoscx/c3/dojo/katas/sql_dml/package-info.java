/// # Hintergrund
/// Du arbeitest an einem neuen Feature und musst dafür eine neue Tabelle anlegen, die zukünftig eine große Anzahl von
/// Kontaktdaten enthalten wird. Da die Produktivdaten unternehmenskritische Vertriebsinfos darstellen und Dir keine
/// legitimierte Person vertraut, erhältst Du als Dankeschön für Deinen Einsatz einen fiktiven Beispieldatensatz, der
/// fast alle Pflichtfelder umfasst.
///
/// # Anforderungen
/// Für eine realistische Bewertung der implementierten Kontaktsuche benötigst Du eine repräsentative Anzahl
/// repräsentiver Datensätze (mindestens 1 Million) in der Tabelle {@code sql_dml.contacts}.
///
/// Bewertungskritierien sind insbesondere
///
/// * Reproduzierbarkeit
/// * Repräsentativität / Variabilität der Attributwerte
/// * Komplexität der Lösung
/// * Speicherbedarf & Skalierbarkeit
/// * Geschwindigkeit (was auch immer damit gemeint ist)
/// * Lässigkeit (einhändiges Coding bringt 20% extra)
///
/// # Tipp
/// Lass' Dich nicht stressen, es ist an der Zeit <s>kreativ zu werden</s> rational zu bleiben.
///
/// Erdrückend viele Möglichkeiten - hier ein paar "konkrete" Vorschläge:
/// * Erzeuge den Kram innerhalb der Datenbank durch eine Kombination aus WITH-, INSERT- und SELECT-Statements. Die
///   als WITH-Block definierte "ad-hoc-View" backt sich ihre Datensätze häufig mittels
///   {@code generate_series(from, to)}.
///   Im Ergebnis erhält man hierdurch einen reproduzierbaren Stream von Datensätzen mit Attributwerten, die sich
///   häufig nur durch die Sequenz-Variable unterscheiden. Unser INSERT akzeptiert im Anschluss neben einem langweiligen
///   VALUES auch ein SELECT unserer "ad-hoc-View" mit Beispieldaten (natürlich vorausgesetzt, die selektierten Daten
///   passen zu den Anforderungen des INSERT).
/// * Scripting! Wir beide wissen, dass Du es willst... schon von JEP 512 gehört?
/// * Zeige Dich als polyglottes Wesen im Lichte der Erkenntnis. Vergiss' dabei nur bitte nicht die Aufgabe und habe
///   Argumente.
///
/// # Referenzen
/// * http://localhost:4200/katas/sql/dml
/// * https://www.postgresql.org/docs/18/functions-uuid.html
/// * https://www.postgresql.org/docs/18/functions-math.html).
/// * https://www.postgresql.org/docs/current/functions-srf.html
/// * https://www.postgresql.org/docs/current/functions-datetime.html ("interval"... *hust*)
/// * https://docs.oracle.com/en/java/javase/25/language/compact-source-files-and-instance-main-methods.html#GUID-A7B00DE3-B0CC-40F3-85A7-15D6F3D4ABCD
///
package de.cronoscx.c3.dojo.katas.sql_dml;
