/// # Hintergrund
/// Dein Kunde plant dieses Jahr das Geld, das bislang die Gitte aus dem Callcenter erhalten hat, an den Jeff umzuleiten
/// und Du sollst dafür sorgen, dass der Chatbot keine allzuvertraulichen Infos raushaut.
/// Kurzum: Du darfst die intern ermittelten Infos vor der Veröffentlichung _reinigen_. Da sich die Prävention von
/// Datenlecks in der Vergangenheit als komplexe Herausforderung zeigte, existieren ganz unterschiedliche Ansätze und
/// inhaltliche Spezialisierungen.
///
/// # Anforderungen
/// Deine Aufgabe ist es, für eine optimale Konfiguration der vorhandenen Reinigungs-Services (hier "Sanitizer" genannt)
/// zu sorgen.
///   
/// Innerhalb der Methode {@link de.cronoscx.c3.dojo.katas.spring_feature_flags.SpringFeatureFlagsUseCases#sanitize(java.lang.String)}
/// sollen alle relevanten Implementationen des Interface {@link de.cronoscx.c3.dojo.katas.spring_feature_flags.Sanitizer}
/// für die Datenbereinigung aufgerufen werden.
///
/// Die Liste der benutzten {@link de.cronoscx.c3.dojo.katas.spring_feature_flags.Sanitizer} muss ohne Anpassungen
/// an der Klasse {@link de.cronoscx.c3.dojo.katas.spring_feature_flags.SpringFeatureFlagsUseCases} erfolgen.
///
/// ## {@link de.cronoscx.c3.dojo.katas.spring_feature_flags.RegexSanitizer}
/// Dieser Service darf nur aktiviert werden, wenn das Feature Flag {@code my.custom.features.regex-sanitizer-enabled}
/// auf {@code true} gesetzt ist.
///
/// ## {@link de.cronoscx.c3.dojo.katas.spring_feature_flags.MacOsSanitizer}
/// Dieser Service darf nur aktiviert werden, wenn die Anwendung unter MacOS läuft.
///
/// ## {@link de.cronoscx.c3.dojo.katas.spring_feature_flags.LegacyJavaSanitizer}
/// Dieser Service darf nur aktiviert werden, wenn die Anwendung mit einem JDK < 21 ausgeführt wird.
///
/// ## {@link de.cronoscx.c3.dojo.katas.spring_feature_flags.AwsEcsSanitizer}
/// Dieser Service darf nur aktiviert werden, wenn die Anwendung im Kontext einer ECS-Instanz innerhalb der AWS
/// ausgeführt wird.
///
/// # Tipp
/// Unsere tägliche Portion Bohnen entspringt nicht immer einer Kombination aus {@code @ComponentScan} und
/// {@code @Component}. Je nach Bedarf kann der Inhalt des {@link org.springframework.context.ApplicationContext} auch
/// explizit zusammengestellt werden. Und die explizite Konfiguration über {@code @Bean} ermöglicht auch mehr Kontrolle.
///
/// # Referenzen
/// * https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html#features.developing-auto-configuration.condition-annotations
///
package de.cronoscx.c3.dojo.katas.spring_feature_flags;
