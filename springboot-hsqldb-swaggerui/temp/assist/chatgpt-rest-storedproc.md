Voici un exemple complet d'un service REST Spring Boot en Java 17 qui appelle une procédure stockée DB2 avec trois paramètres (`annee`, `montant`, `date`), en utilisant `JpaRepository` pour effectuer la gestion des entités. L'objectif ici est de configurer un service qui appelle la procédure stockée et renvoie le résultat sous forme d'une réponse JSON.

### 1. Dépendances Maven

Voici les dépendances Maven que vous devez ajouter à votre fichier `pom.xml` pour Spring Boot et DB2 :

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.ibm.db2</groupId>
        <artifactId>jcc</artifactId>
        <version>11.5.5.0</version> <!-- Assurez-vous d'avoir la bonne version pour DB2 -->
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jackson</artifactId>
    </dependency>
</dependencies>
```

### 2. Configuration de la source de données DB2 dans `application.properties`

Voici un exemple de configuration de la source de données DB2 dans le fichier `application.properties` :

```properties
spring.datasource.url=jdbc:db2://<HOST>:<PORT>/<DB_NAME>
spring.datasource.username=<USERNAME>
spring.datasource.password=<PASSWORD>
spring.datasource.driver-class-name=com.ibm.db2.jcc.DB2Driver
spring.datasource.jpa.hibernate.ddl-auto=none
spring.jpa.database-platform=org.hibernate.dialect.DB2Dialect
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
```

### 3. Entité (Entity)

Supposons que le résultat du `SELECT` retourné par la procédure stockée contient un champ `id` et `nom`. Vous pouvez créer une entité pour refléter ce résultat.

```java
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Resultat {
    
    @Id
    private Long id;
    private String nom;
    
    // Getters et Setters
}
```

### 4. Définir le `JpaRepository` pour l'entité `Resultat`

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultatRepository extends JpaRepository<Resultat, Long> {
    // Vous pouvez ajouter des méthodes spécifiques si nécessaire.
}
```

### 5. Appeler la procédure stockée à l'aide d'un `@Query` natif

Utilisez l'annotation `@Query` de Spring Data JPA pour appeler la procédure stockée.

```java
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {

    @Transactional
    @Procedure(name = "nom_de_la_procedure_stockee")
    List<Resultat> executerProcedureStockee(int annee, double montant, String date);
}
```

Ici, nous supposons que la procédure stockée renvoie un ensemble de résultats qui peuvent être mappés dans une liste d'objets `Resultat`.

### 6. Service qui appelle la procédure stockée

Créez un service pour encapsuler la logique d'appel à la procédure stockée.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultatService {

    @Autowired
    private ResultatRepository resultatRepository;

    public List<Resultat> obtenirResultats(int annee, double montant, String date) {
        return resultatRepository.executerProcedureStockee(annee, montant, date);
    }
}
```

### 7. Contrôleur REST pour exposer l'API

Créez un contrôleur REST qui permettra de faire une requête HTTP et d'exécuter la procédure stockée.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResultatController {

    @Autowired
    private ResultatService resultatService;

    @GetMapping("/resultats")
    public List<Resultat> getResultats(
            @RequestParam("annee") int annee,
            @RequestParam("montant") double montant,
            @RequestParam("date") String date) {
        return resultatService.obtenirResultats(annee, montant, date);
    }
}
```

### 8. Procédure stockée DB2 (exemple)

Voici un exemple de procédure stockée DB2 qui pourrait correspondre aux besoins. Cette procédure prend trois paramètres et retourne une liste de résultats.

```sql
CREATE PROCEDURE nom_de_la_procedure_stockee(
    IN annee INT,
    IN montant DECIMAL(10, 2),
    IN date DATE
)
LANGUAGE SQL
BEGIN
    -- Exécution d'une requête SELECT
    SELECT id, nom
    FROM table_resultats
    WHERE annee = annee
    AND montant > montant
    AND date <= date;
END;
```

### Explication des étapes :

- **Entité `Resultat`** : Cette classe représente la structure des données retournées par la procédure stockée.
- **`ResultatRepository`** : Interface qui permet d'utiliser `@Procedure` pour appeler la procédure stockée dans la base de données.
- **Service `ResultatService`** : Service qui gère la logique métier, appelant le `Repository` pour exécuter la procédure.
- **Contrôleur `ResultatController`** : Contrôleur REST qui expose un endpoint pour exécuter la procédure stockée via HTTP.

### Conclusion

Ce code vous permet de créer un service Spring Boot qui appelle une procédure stockée DB2 avec des paramètres donnés et retourne les résultats sous forme de JSON. Vous pouvez ajuster le code en fonction de votre base de données et de votre logique métier.