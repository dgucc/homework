Pour documenter les erreurs HTTP dans votre service en utilisant **Springdoc** (qui est basé sur **Swagger** pour générer de la documentation d'API dans une application Spring), vous pouvez annoter vos méthodes de contrôleur avec les annotations spécifiques à Springdoc qui permettent de spécifier les réponses HTTP, y compris les erreurs.

Voici comment vous pouvez documenter les erreurs HTTP gérées par votre service avec Springdoc :

### 1. Utilisation de `@ApiResponse` pour documenter les erreurs HTTP

L'annotation `@ApiResponse` permet de spécifier les réponses possibles d'une méthode, y compris les erreurs HTTP. Vous pouvez l'utiliser avec l'annotation `@Operation` pour fournir des informations détaillées sur les réponses d'erreur dans le Swagger UI.

### Exemple :

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Operation(summary = "Obtenir un utilisateur par son ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
        @ApiResponse(responseCode = "400", description = "ID invalide", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/user")
    public User getUser(@RequestParam("id") String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID ne peut pas être vide");
        }
        // Supposons que vous récupériez l'utilisateur ici
        User user = findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("Utilisateur non trouvé pour l'ID " + id);
        }
        return user;
    }
}
```

### Explication des annotations :
- **`@Operation`** : Cette annotation permet de décrire l'API d'une méthode spécifique (par exemple, un résumé ou une description).
- **`@ApiResponses`** : Cette annotation permet de spécifier plusieurs réponses possibles pour l'API, y compris les erreurs.
- **`@ApiResponse`** : Cette annotation définit une réponse HTTP spécifique, où vous pouvez spécifier :
  - **`responseCode`** : Le code HTTP pour la réponse (par exemple, 400, 404, etc.).
  - **`description`** : Une description de la réponse.
  - **`content`** : Spécifie le type de contenu de la réponse, comme `application/json`, et vous pouvez inclure un modèle de la réponse d'erreur.

### 2. Utiliser `@ExceptionHandler` pour gérer les erreurs et les documenter

Si vous gérez des exceptions dans un contrôleur ou dans un gestionnaire global d'exceptions avec `@ExceptionHandler`, vous pouvez également documenter ces réponses d'erreur. Par exemple :

#### Exemple avec un gestionnaire d'exceptions global :

```java
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Demande invalide", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
```

### 3. Créer un modèle d'erreur personnalisé

Dans l'exemple précédent, un modèle `ErrorResponse` est utilisé pour structurer les informations d'erreur. Vous pouvez créer cette classe pour décrire le format de vos réponses d'erreur :

```java
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    // Getters et setters
}
```

### Conclusion

En résumé, avec **Springdoc** et **Swagger**, vous pouvez utiliser les annotations `@Operation`, `@ApiResponses`, et `@ApiResponse` pour documenter les réponses d'erreur HTTP dans vos API. En complément, la gestion des exceptions via `@ExceptionHandler` ou un gestionnaire global `@ControllerAdvice` permet de centraliser le traitement des erreurs tout en les documentant efficacement.