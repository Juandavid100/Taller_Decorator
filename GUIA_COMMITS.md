# Guía de commits — Kevin Basante y Arley Riascos

El proyecto tiene **31 clases Java + 3 archivos de frontend**. Cada integrante sube aproximadamente la mitad:

| | Kevin Basante | Arley Riascos |
|---|---|---|
| Clases Java | 15 | 16 |
| Frontend | `index.html`, `styles.css` | `app.js` |
| Otros | `.gitignore`, `pom.xml`, `README.md`, `docs/`, esta guía | `run.sh`, `run.bat` |

### Archivos de Kevin
```
component/ArtisanPiece.java          decorator/PieceDecorator.java
component/WoodenPiece.java           decorator/MopaMopaLayerDecorator.java
component/WoodenBowl.java            decorator/GoldLeafDecorator.java
component/JewelryBox.java            decorator/SilverLeafDecorator.java
component/DecorativePlate.java       model/ResinColor.java
component/CarnivalMask.java          model/PieceType.java
Main.java                            model/DecorationType.java
ConsoleDemo.java
```

### Archivos de Arley
```
decorator/ProtectiveLacquerDecorator.java        service/WorkshopCatalog.java
decorator/ArtisanSignatureDecorator.java         service/QuoteService.java
decorator/AuthenticityCertificateDecorator.java  web/JsonSerializer.java
decorator/GiftPackagingDecorator.java            web/ApiHandler.java
service/DecorationRequest.java                   web/CatalogHandler.java
service/InvalidOrderException.java               web/QuoteHandler.java
service/Quote.java                               web/StaticFileHandler.java
service/QuoteStep.java                           web/WebServer.java
```
(Todas las rutas son relativas a `src/main/java/com/mopamopa/studio/`.)

---

## Paso 0: preparación (una sola vez)

**Cada uno** configura su identidad para que GitHub le atribuya sus commits:
```bash
git config --global user.name "Kevin Basante"        # Arley pone su nombre
git config --global user.email "correo-de-github@..."  # el correo de SU cuenta de GitHub
```

**Kevin:**
1. Crea un repositorio **vacío** en GitHub (sin README), por ejemplo `mopa-mopa-studio-decorator`.
2. En *Settings → Collaborators* agrega a **Arley**.
3. Clona el repositorio.

**Arley:** acepta la invitación (le llega por correo) y clona el repositorio.

**Ambos:** descomprimen el ZIP del proyecto en **otra carpeta, al lado del repo clonado**:
```
Documentos/
├── mopa-mopa-studio/               ← carpeta descargada (aquí está todo el código)
└── mopa-mopa-studio-decorator/     ← repositorio clonado (aquí se hacen los commits)
```

En cada commit se **copian solo los archivos de ese commit** desde la carpeta descargada al repo y se suben.

> **Regla de oro:** antes de cada commit, `git pull`; después de cada commit, `git push`. Si los dos van por turnos en el orden de abajo, no habrá conflictos.

Los comandos usan Git Bash (Windows) o la terminal (Linux/macOS), ejecutados **dentro del repo clonado**. Para acortar:
```bash
SRC=../mopa-mopa-studio
J=src/main/java/com/mopamopa/studio
```
(Hay que volver a definir esas dos variables cada vez que se abre una terminal nueva.)

---

## Orden de commits

Los mensajes de commit van en inglés, igual que el código.

### 1 · Kevin: configuración del proyecto
```bash
git pull
cp $SRC/.gitignore $SRC/pom.xml .
git add .gitignore pom.xml
git commit -m "chore: set up Maven project and gitignore"
git push
```

### 2 · Kevin: interfaz Component y clase base
```bash
git pull
mkdir -p $J/component
cp $SRC/$J/component/ArtisanPiece.java $SRC/$J/component/WoodenPiece.java $J/component/
git add $J/component
git commit -m "feat(component): add ArtisanPiece interface and WoodenPiece base class"
git push
```

### 3 · Kevin: componentes concretos
```bash
git pull
cp $SRC/$J/component/{WoodenBowl,JewelryBox,DecorativePlate,CarnivalMask}.java $J/component/
git add $J/component
git commit -m "feat(component): add concrete wooden pieces"
git push
```

### 4 · Arley: modelos de la cotización
```bash
git pull
mkdir -p $J/service
cp $SRC/$J/service/{DecorationRequest,InvalidOrderException,Quote,QuoteStep}.java $J/service/
git add $J/service
git commit -m "feat(service): add quote models and order exception"
git push
```

### 5 · Kevin: enums del catálogo
```bash
git pull
mkdir -p $J/model
cp $SRC/$J/model/*.java $J/model/
git add $J/model
git commit -m "feat(model): add resin colors, piece types and decoration types"
git push
```

### 6 · Kevin: decorador abstracto
```bash
git pull
mkdir -p $J/decorator
cp $SRC/$J/decorator/PieceDecorator.java $J/decorator/
git add $J/decorator
git commit -m "feat(decorator): add abstract PieceDecorator"
git push
```

### 7 · Kevin: decoradores de resina y metal
```bash
git pull
cp $SRC/$J/decorator/{MopaMopaLayerDecorator,GoldLeafDecorator,SilverLeafDecorator}.java $J/decorator/
git add $J/decorator
git commit -m "feat(decorator): add mopa-mopa resin, gold leaf and silver leaf decorators"
git push
```

### 8 · Arley: decoradores de acabado
```bash
git pull
cp $SRC/$J/decorator/{ProtectiveLacquerDecorator,ArtisanSignatureDecorator,AuthenticityCertificateDecorator,GiftPackagingDecorator}.java $J/decorator/
git add $J/decorator
git commit -m "feat(decorator): add lacquer, signature, certificate and gift packaging decorators"
git push
```

### 9 · Arley: catálogo y servicio de cotización
```bash
git pull
cp $SRC/$J/service/{WorkshopCatalog,QuoteService}.java $J/service/
git add $J/service
git commit -m "feat(service): build decorator chain and validate workshop rules"
git push
```

### 10 · Arley: API REST
```bash
git pull
mkdir -p $J/web
cp $SRC/$J/web/{JsonSerializer,ApiHandler,CatalogHandler,QuoteHandler}.java $J/web/
git add $J/web
git commit -m "feat(web): add JSON serializer and catalog/quote endpoints"
git push
```

### 11 · Arley: servidor web
```bash
git pull
cp $SRC/$J/web/{StaticFileHandler,WebServer}.java $J/web/
git add $J/web
git commit -m "feat(web): add embedded HTTP server and static file handler"
git push
```

### 12 · Kevin: punto de entrada y demo de consola
```bash
git pull
cp $SRC/$J/Main.java $SRC/$J/ConsoleDemo.java $J/
git add $J/Main.java $J/ConsoleDemo.java
git commit -m "feat: add application entry point and console demo"
git push
```

### 13 · Kevin: estructura y estilos del frontend
```bash
git pull
mkdir -p src/main/resources/static
cp $SRC/src/main/resources/static/{index.html,styles.css} src/main/resources/static/
git add src/main/resources/static
git commit -m "feat(frontend): add page layout and styles"
git push
```

### 14 · Arley: lógica del frontend
```bash
git pull
cp $SRC/src/main/resources/static/app.js src/main/resources/static/
git add src/main/resources/static/app.js
git commit -m "feat(frontend): add layer builder, live quote and decorator preview"
git push
```

### 15 · Arley: scripts de ejecución
```bash
git pull
cp $SRC/run.sh $SRC/run.bat .
git add run.sh run.bat
git commit -m "chore: add run scripts for Windows and Unix"
git push
```

### 16 · Kevin: documentación
```bash
git pull
mkdir -p docs
cp $SRC/README.md $SRC/GUIA_COMMITS.md .
cp $SRC/docs/screenshot.png docs/
git add README.md GUIA_COMMITS.md docs
git commit -m "docs: add README with UML diagram, usage guide and team split"
git push
```

Al terminar: **Kevin 9 commits, Arley 7 commits**, y el repositorio queda idéntico a la carpeta descargada. Para comprobarlo, ejecutar `./run.sh` (o `run.bat`) desde el repo y abrir http://localhost:8080.

---

### Problemas comunes

- **`rejected ... fetch first`** al hacer push: el compañero subió algo antes. Ejecutar `git pull` y luego `git push` de nuevo.
- **`{...}` no funciona** (CMD de Windows): usar **Git Bash**, o copiar los archivos a mano con el explorador y luego hacer `git add` y `git commit`.
- **El proyecto no compila entre commits:** es normal. Compila completo al llegar al commit 11.
