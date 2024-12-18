# Spring Certified Professional Notizen

## Spring

Definition
: Java Framework (als Modulbaukasten), dass zum Bauen von Anwendungen genutzt wird.

- Ist Open Source
- Leichtgewichtig
- Besitzt einen DI Container (Inversion of Control Container)
- Apache 2 Lizenz
- Spring Jars sind klein und können in einer JRE laufen

### Nachteil

1. Aufsetzen einer Spring-Anwendung erfordert viele Konfigurationen. Damit eine Anwendung läuft, müssen einige
   Komponenten manuell konfiguriert werden

## Spring Boot

Definition
: Java Framework, dass zum Bauen von Anwendungen genutzt wird.

### Vorteil

1. Erleichtert das Arbeiten mit spring, in dem es die Abhängigkeiten vorkonfiguriert.
2. Durch die Vorkonfiguration wird der erste Start erleichtert.
3. Kommt mit einem integriertem Web-Server. Benötigt somit keinen externen Web-Server zum Einspielen von
   Web-Anwendungen.

## Allgemein

Idempotence
: Idempotente Operation hat immer denselben Output, wenn sie ausgeführt wird.

## Tests

### Nützliche Annotation

@JsonTest
: Markiert die KLasse als Testklasse und bietet Unterstützung durch das Jackson framework an. Bietet JSON-Testing und
das "parsen" an.

@DirtiesContext
: Markiert die Klasse oder die Methode, sodass der Test mit einer sauberen Umgebung ausgeführt wird.

@Configuration
: Alle Beans die in der Klasse spezifiziert werden sind für die `Spring Auto Configuration Engine` zugänglich.

### Testarten

Unit Tests
: Testet eine kleine "unit" eines Systems, dass vom restlichen System isoliert ist. Sie sollten simpel und schnell sein.

Integrations Test
: Testet mehrere "units" eines Systems. Komplizierter zu schreiben und zu verwalten. Laufen langsamer als Unit-Tests.

End-to-End Tests
: Testet das Interface, welches auch der User nutzen würde (wie ein Webbrowser). Sehr langsam und fragil.

Reg, Green, Refactor Loop
: Test schreiben, sodass der Test fehlschlägt. Implementieren, bis der Test grün wird. Refactoring des Codes.

## REST

Definition
: **Re**presentational **S**tate **T**ransfer. In einem RESTful System heißen die Datenobjekte "Resource
Representations". Der Zweck einer RESTful API ist es den Zustand einer Resource zu verwalten.

Beispiel eines RestControllers in Spring:

```java

@RestController
class MyNewController {
    @GetMapping("/myResource/{requestedId}")
    private ResponseEntity<MyResource> findById(@PathVariable Long requestedId) {
        return ResponseEntity.ok(new MyResource());
    }
}
```

## Inversion of Control Container

Spring-Beans werden durch Spring erzeugt und ersetzen das "new"-Keywort. Es gibt mehrere Arten Spring-Beans zu
instanziieren.

Beispiel:
`@RestController` veranlasst Spring in der `Component Scan phase` (Während des Start-ups) die Instanziierung der Klasse.
Ab dort wird die Bean im `Spring IoC container` gespeichert und kann von anderen Klassen durch ein `inject` genutzt
werden.

- Herzstück von Spring
- Die Kernprinzipien von Spring sind:
    - Dont repeat yourself
  - Separation of Concerns
  - Conversion over Configuration
      - Testbarkeit
- Singleton ist default Scope. Andere Scopes:
    - prototype
    - session
    - request
    - web socket scope
    - refresh scope
    - thread scope

### Beispiele

```java

ApplicationContext context = SpringApplication.run(ApplicationConfig.class);
MyService m1 = (MyService) context.getBean("myService");
MyService m2 = context.getBean("myService", MyService.class);
MyService m3 = (MyService) context.getBean(MyService.class);

```

## Properties laden

Mehrere Möglichkeiten:

```Java

@Configuration
@PropertySource("classpath:/com/package/app.properties")
@PropertySource("file:/config/app.properties") // Relativ oder absoluter Pfad
public class MyConfig {
}
```

```Java

@Configuration
public class MyConfig {

    @Bean
    public MyBean myBean(@Value("${my.prop}") String myProp) {
        // ...
    }
}
```

- Kann auch mit Profilen kombiniert werden

## Profiles

Kann aktiviert werden durch folgende Property: `-Dspring.profiles.active=meinProfil1,meinProfil2`

```Java

@Configuration
@Profile("meinProfil") // Aktiv, wenn meinProfil eingestellt ist
public class MyConfig {
}
```

```Java

@Configuration
@Profile("!meinProfil") // Aktiv, wenn meinProfil nicht eingestellt ist
public class MyConfig {
}
```

## Spring Expression Language (SpEL)

`systemProperties` wird von der Umgebung bereitgestellt.

Beispiel:

```Java

@Configuration
public class MyConfig {
    // Kann auch auf dem Argument-Level verwendet werden
    @Value("#{systemProperties['user.region']}")
    String region;
}
```

## Glossar

RBAC
: Role-Based Access Control

SOP
: Same Origin Policy

CORS
: Cross-Origin Resource Sharing

CSRF
: Cross-Site Request Forgery

XSS
: Cross-Site Scripting

## ToDo's

- Spring's Inversion of Control Container verstehen. https://docs.spring.io/spring-framework/reference/core/beans.html
- CSRF tests:
    - https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/csrf.html
    - https://docs.spring.io/spring-security/site/docs/5.2.0.RELEASE/reference/html/test-webflux.html#csrf-support
    - https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html#double-submit-cookie

## Nützliche Tipps:

### Tests toggeln

Mit dem Befehl werden die Tests jedes Mal ausgeführt, wenn sich der Code ändert.

``` shell
gradle test --continuous
```

### @Autowired sollte nur in Tests verwendet werden
