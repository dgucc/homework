# prefix r
En Python, le préfixe **`r`** (ou **`R`**) devant une chaîne de caractères signifie que c'est une **chaîne "raw"** (brute). Cela signifie que les **caractères d'échappement** comme `\n`, `\t`, `\\`, etc., **ne sont pas interprétés**.

### Exemple :

```python
chemin_normal = "C:\\Users\\Nom"
print(chemin_normal)  # Affiche : C:\Users\Nom

chemin_raw = r"C:\Users\Nom"
print(chemin_raw)     # Affiche aussi : C:\Users\Nom
```

Sans le `r`, tu dois **doubler les antislashs** (`\\`) pour qu’ils soient pris littéralement. Avec `r`, un seul antislash suffit.

### À quoi ça sert ?

* Très utile pour les **chemins Windows**
* Pour les **expressions régulières**, où `\` est fréquent

  ```python
  import re
  re.match(r"\d+", "123")  # correspond à un ou plusieurs chiffres
  ```

### Attention

Tu ne peux pas finir une chaîne raw avec un `\` seul :

```python
r"C:\Users\"   # ❌ Erreur de syntaxe
```

---

En résumé, `r` indique à Python de **ne pas traiter les antislashs comme des caractères spéciaux**, ce qui rend certaines chaînes plus lisibles et faciles à écrire.

---
# prefix f

En Python, le préfixe **`f`** (ou **`F`**) devant une chaîne de caractères signifie que c'est une **f-string** (format string), introduite en Python 3.6. Cela permet **d'insérer des expressions Python directement dans la chaîne** en les entourant avec des accolades `{}`.

---

### Exemple simple :

```python
nom = "Alice"
age = 30
message = f"{nom} a {age} ans"
print(message)  # Affiche : Alice a 30 ans
```

---

### Expressions dans les accolades :

Tu peux inclure des **expressions complexes** :

```python
a = 5
b = 3
print(f"{a} + {b} = {a + b}")  # Affiche : 5 + 3 = 8
```

---

### Formatage avec f-strings :

Tu peux aussi utiliser des **spécificateurs de format** :

```python
pi = 3.14159
print(f"Pi vaut environ {pi:.2f}")  # Affiche : Pi vaut environ 3.14
```

---

### Combinaison avec `r` :

Tu peux combiner `f` avec `r` pour des chaînes **raw formatées** :

```python
chemin = "Nom"
print(rf"C:\Users\{chemin}")  # Affiche : C:\Users\Nom
```

---

### En résumé :

| Préfixe | Signification | Utilisation principale     |
| ------- | ------------- | -------------------------- |
| `r""`   | Raw string    | Ne pas interpréter `\`     |
| `f""`   | Format string | Interpolation de variables |
| `rf""`  | Raw + format  | Chemins dynamiques, regex  |

Ces préfixes rendent la manipulation de chaînes plus flexible et lisible.
