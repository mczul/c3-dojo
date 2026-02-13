package de.cronoscx.c3.dojo.katas.spring_feature_flags;

/// Dieses Interface definiert die API für Services, die Texte entgegen nehmen und vertrauliche Informationen
/// _reinigen_. Es sind sehr viele unterschiedliche Implementationen denkbar.
interface Sanitizer {

    /**
     * Beschreibt das Prefix, das eine Verarbeitung durch diese Instanz über {@link Sanitizer#process(String)}
     * innerhalb des Rückgabewerts markiert.
     */
    String getIndicator();

    /**
     * Die wesentliche Funktion zur Reinigung der Eingabewerte.
     *
     * @param input der Eingabewert (z.B. die Quelldaten oder die Rückgabe der zuvor ausgeführten {@link Sanitizer}).
     * @return der "gereinigte" Eingabewert
     */
    String process(String input);

}
