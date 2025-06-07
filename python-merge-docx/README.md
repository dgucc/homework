`> python -m venv venv`  
`> venv\Script\activate.bat`   


> proxy workaround :  
(venv)> pip install python-docx --proxy http://userid:passwd@proxies.example.com:8080

```cmd
(venv)> python -m pip install --upgrade pip

(venv)> pip install python-docx
```

# python to exe
`(venv)> pyinstaller --onefile fusion_documents.py`

---

- Ne marche pas avec les images... : doc1.docx  
- Les couleurs (police et surlignage) ne sont pas respectées : doc2.docx et doc3.docx  


