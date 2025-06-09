# Merge multiple docx files - python

## Setup virtual env
`> python -m venv venv`  
`> venv\Script\activate.bat`   

## Pip install python-docx
```cmd
(venv)> python -m pip install --upgrade pip

(venv)> pip install python-docx
```
> proxy workaround :  
(venv)> pip install python-docx --proxy http://userid:passwd@proxies.example.com:8080

## python to exe
`(venv)> pyinstaller --onefile fusion_documents.py`

## format datetime
```python
>>> from datetime import datetime
>>> print(datetime.now().strftime("%Y%m%d_%H-%M-%S"))
20250609_09-59-03
```

---

## Notes
- Ne marche pas avec les images... : doc1.docx  
- Les couleurs (police et surlignage) ne sont pas respectées : doc2.docx et doc3.docx  
---

## TODO

To integrate :  

```python
def lire_chemins_valides(fichier_chemins):
    """
    Lit un fichier texte contenant des chemins vers des fichiers .docx,
    et retourne une liste des chemins valides (existants et avec extension .docx).
    """
    chemins_valides = []

    try:
        with open(fichier_chemins, 'r', encoding='utf-8') as f:
            lignes = f.readlines()

        for ligne in lignes:
            chemin = ligne.strip()  # Supprime les espaces et sauts de ligne

            # Vérifie que le chemin se termine par .docx
            if not chemin.lower().endswith('.docx'):
                continue

            # Vérifie que le fichier existe
            if os.path.exists(convertir_en_style_linux(chemin)):
                chemins_valides.append(chemin)
            else:
                print(f"Fichier non trouvé : {chemin}")

    except FileNotFoundError:
        print(f"Erreur : Le fichier '{fichier_chemins}' n'a pas été trouvé.")
    except Exception as e:
        print(f"Une erreur s'est produite : {e}")

    return chemins_valides

# Exemple d'utilisation
chemins = lire_chemins_valides('chemins.txt')
print("Chemins valides trouvés :")
print(chemins)
```

