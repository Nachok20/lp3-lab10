# Laboratorio 10 · Java Swing avanzado

Lenguajes de Programación III. Cada archivo Java contiene una aplicación de escritorio independiente. Las aplicaciones que reproducen audio o video buscan sus archivos en la carpeta `recursos` del proyecto.

| Parte de la guía | Clase principal | Qué muestra |
| --- | --- | --- |
| Marco teórico | `PersonaDemo` | Sincronización manual de un formulario con un objeto persona |
| Marco teórico | `GraficosSimples` | Figuras dibujadas en un panel Swing |
| Marco teórico | `Transformaciones` | Traslación, rotación, escala y sesgado con Graphics2D |
| Marco teórico | `Ventas` | Gráfico de barras con valores y etiquetas |
| Marco teórico | `AudioDemo` | Reproducción de un archivo WAV con Clip |
| Marco teórico | `VideoDemo` | Reproducción de un MP4 dentro de una ventana Swing mediante JavaFX |
| Actividad IV | `MetronomoApp` | Metrónomo con modelo de tempo, indicador gráfico y sonido |
| Ejercicio 1 | `ProductoApp` | Registro y actualización de los datos de un producto |
| Ejercicio 2 | `TemperaturasApp` | Registro de siete temperaturas y actualización del gráfico semanal |
| Ejercicio 3 | `EfectosApp` | Reproducción de aplausos, campana y explosión |
| Ejercicio 4 | `MusicaApp` | Reproducción, pausa y reanudación de una pista musical |

## Abrir y compilar

Use un JDK 21, una sesión gráfica de Linux y PowerShell en la terminal integrada de VS Code. Para escuchar los archivos WAV se requiere una salida de audio disponible para Java Sound. El ejemplo de video usa OpenJFX 23.0.2 para Linux x64.

Para reproducir la ruta de la práctica, abra PowerShell en una carpeta destinada a la unidad virtual y ejecute estos comandos. El directorio físico elegido contendrá `Users/Sebastian/Desktop/Lab10`.

```powershell
New-PSDrive -Name C -PSProvider FileSystem -Root (Get-Location).ProviderPath
New-Item -ItemType Directory -Force 'C:\Users\Sebastian\Desktop' | Out-Null
Set-Location 'C:\Users\Sebastian\Desktop'
git clone https://github.com/Nachok20/lp3-lab10.git Lab10
Set-Location Lab10
```

Si ya descargó el proyecto, abra su carpeta raíz en la terminal. Puede compilar y ejecutar cualquiera de las aplicaciones Swing del mismo modo que el ejercicio 1.

```powershell
New-Item -ItemType Directory -Force clases | Out-Null
javac -encoding UTF-8 -d clases ProductoApp.java
java -cp clases ProductoApp
```

Para compilar el proyecto completo, descargue los módulos de OpenJFX que usa el ejemplo de video. Ejecute desde la carpeta raíz los siguientes comandos de PowerShell.

```powershell
New-Item -ItemType Directory -Force lib, clases | Out-Null
$version = '23.0.2'
foreach ($modulo in 'base', 'graphics', 'media', 'swing') {
    $nombre = "javafx-$modulo"
    $jar = "$nombre-$version-linux.jar"
    $url = "https://repo.maven.apache.org/maven2/org/openjfx/$nombre/$version/$jar"
    Invoke-WebRequest $url -OutFile "lib/$jar"
}
$fuentes = (Get-ChildItem -File -Filter '*.java').Name
javac -encoding UTF-8 --module-path lib --add-modules javafx.swing,javafx.media -d clases $fuentes
java --module-path lib --add-modules javafx.swing,javafx.media -cp clases VideoDemo
```

Para ejecutar las demás clases ya compiladas, sustituya el nombre de la clase en el último argumento de `java -cp clases ProductoApp`. Mantenga la terminal en la carpeta raíz durante la ejecución. Los sonidos y el video se incluyen en `recursos`. El MP4 contiene una animación sin pista de audio.
