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

---

## Notes
- Ne marche pas avec les images... : doc1.docx  
- Les couleurs (police et surlignage) ne sont pas respectées : doc2.docx et doc3.docx  
---

## TODO

To integrate :  

```python
import os
from pathlib import Path

def convertir_en_style_linux(chemin):
    path = Path(chemin)
    return str(path.as_posix())

def lire_chemins_valides(fichier_chemins):
    """
    Lit un fichier texte contenant des chemins vers des fichiers .docx,
    et retourne une liste des chemins valides (existants et avec extension .docx).
    """
    chemins_valides = []
```

