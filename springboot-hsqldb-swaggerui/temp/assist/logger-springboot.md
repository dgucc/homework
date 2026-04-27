Pour ajouter un logger dans un projet Spring Boot avec Java 17, vous pouvez utiliser la bibliothèque `SLF4J` (Simple Logging Facade for Java) qui est compatible avec différents frameworks de logging tels que `Logback`, `Log4j`, ou `Java Util Logging`. Spring Boot configure automatiquement `Logback` comme framework de logging par défaut.

Voici les étapes pour configurer un logger dans un projet Spring Boot :

### 1. Ajouter la dépendance dans le fichier `pom.xml`
Spring Boot inclut déjà la dépendance pour `SLF4J` et `Logback`. Par conséquent, il n'est généralement pas nécessaire de l'ajouter manuellement. Si vous souhaitez utiliser un autre framework de logging, vous pouvez l'ajouter explicitement dans votre fichier `pom.xml`.

**Pour Logback (qui est déjà inclus par défaut)** :
```xml
<dependencies>
    <!-- Dépendance de Spring Boot Starter Web (inclut SLF4J et Logback) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

### 2. Utiliser le Logger dans votre code
Dans vos classes Java, vous pouvez maintenant utiliser un logger comme suit :

**Exemple dans un contrôleur ou un service** :
```java
package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    // Création du logger
    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @GetMapping("/hello")
    public String sayHello() {
        logger.info("La méthode sayHello a été appelée");
        try {
            // Simulation d'une erreur
            int result = 10 / 0;
        } catch (Exception e) {
            logger.error("Une exception s'est produite : ", e);
        }
        return "Bonjour, le logging fonctionne!";
    }
}
```

Dans cet exemple :
- `logger.info` est utilisé pour des messages d'information.
- `logger.error` est utilisé pour des messages d'erreur, notamment lors de la gestion d'exception.
- Vous pouvez également utiliser `logger.debug`, `logger.warn`, et `logger.trace` selon vos besoins.

### 3. Configuration du logger (optionnel)
Si vous souhaitez personnaliser la configuration du logging (par exemple, modifier le niveau de log ou rediriger les logs vers un fichier), vous pouvez créer un fichier `logback-spring.xml` ou `application.properties` dans le répertoire `src/main/resources`.

**Exemple de configuration avec `application.properties`** :
```properties
# Définir le niveau de log global
logging.level.root=INFO

# Définir le niveau de log spécifique pour une classe ou un package
logging.level.com.example.demo=DEBUG

# Configurer le fichier de log (si nécessaire)
logging.file.name=logs/app.log
```

**Exemple de configuration avec `logback-spring.xml`** :
Créez un fichier `logback-spring.xml` dans `src/main/resources/` :

```xml
<configuration>

    <!-- Console appender -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Loggers -->
    <logger name="com.example.demo" level="DEBUG"/>

    <!-- Root logger -->
    <root level="INFO">
        <appender-ref ref="console"/>
    </root>

</configuration>
```

### 4. Test
Lorsque vous lancez l'application, vous verrez les logs dans la console ou dans le fichier spécifié, selon votre configuration.

En résumé, vous avez tout ce qu'il vous faut pour ajouter un logger avec Spring Boot, Java 17, et SLF4J. Le framework Spring Boot configure automatiquement un système de logging de base pour vous.