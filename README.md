# Paises-MesAPP-25001787

Aplicación Android nativa en Java que consulta [REST Countries](https://restcountries.com/) y muestra los nombres de los países junto a sus banderas.

Proyecto de la **Actividad No. 4: Listado de Países**, del curso **Introducción al desarrollo de aplicaciones móviles**, Universidad Galileo.

- **Estudiante:** Elmer Leonel De La Cruz Fuentes
- **Carné:** 25001787
- **Docente:** Ing. Francisco Antonio Retana Barco
- **Versión:** 1.1

## Funcionalidades

- Pantalla Home con nombre completo, carné y botón **Cargar**.
- Navegación al listado mediante un `Intent`.
- Consulta de todas las páginas del servicio REST Countries v5.
- Nombres en español cuando el servicio dispone de la traducción, ordenados alfabéticamente.
- Listado con `RecyclerView`: nombre a la izquierda y bandera a la derecha.
- Carga de banderas mediante Glide y un indicador cuando la imagen no está disponible.
- Estado de carga, mensajes de error y botón **Reintentar**.
- Tema claro con fondo blanco, texto oscuro y acentos turquesa, también cuando el teléfono utiliza modo oscuro.

## Requisitos

- Android Studio con soporte para Android Gradle Plugin 9.5.2.
- JDK 21 completo. Gradle utiliza el criterio definido en `gradle/gradle-daemon-jvm.properties`.
- Android SDK Platform 36 y herramientas del SDK.
- Teléfono o emulador con Android 7.0 (API 24) o superior.
- Conexión a Internet para descargar dependencias y consultar el servicio.

El proyecto incluye el wrapper de **Gradle 9.6.0**. No es necesario instalar Gradle por separado.

## Configuración

1. Clonar el repositorio y abrir su carpeta raíz desde Android Studio.
2. Instalar el SDK 36 desde el SDK Manager y sincronizar el proyecto. Android Studio configura la ruta local del SDK en `local.properties`.
3. El proyecto incluye una clave de REST Countries en `gradle.properties`, por decisión del propietario. Para usar otra clave, sustituir la propiedad `RESTCOUNTRIES_API_KEY` en ese archivo.

```properties
RESTCOUNTRIES_API_KEY=TU_CLAVE_DE_REST_COUNTRIES
```

`secrets.properties` y `local.properties` están excluidos de Git. Si se elimina la clave compartida de `gradle.properties`, se puede usar una variable de entorno `RESTCOUNTRIES_API_KEY` o copiar `secrets.properties.example` como `secrets.properties`. La prioridad es: propiedad de Gradle, variable de entorno y archivo local.

Sin una clave configurada se utiliza la clave pública de demostración del servicio; sus respuestas son de muestra y no equivalen al listado completo. Consulta la [documentación de REST Countries](https://restcountries.com/docs/countries) para obtener una clave y revisar los límites del servicio.

## Compilación y ejecución

En Windows, desde PowerShell en la carpeta del proyecto:

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:lintDebug
```

En macOS o Linux:

```sh
sh gradlew :app:assembleDebug :app:lintDebug
```

El APK de desarrollo se genera en:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Para ejecutarlo, seleccionar un dispositivo en Android Studio y pulsar **Run**, o instalarlo en un dispositivo conectado:

```powershell
.\gradlew.bat :app:installDebug
```

## Estructura

```text
app/
  build.gradle.kts               Configuración del módulo y dependencias
  src/main/
    AndroidManifest.xml         Permiso de Internet y actividades
    java/com/elmerfuentes/paisesmeapp/
      MainActivity.java         Pantalla Home y navegación
      PaisesActivity.java       Consulta, paginación y estados de carga
      Pais.java                 Modelo del país
      PaisAdapter.java          Presentación de nombres y banderas
      WindowInsetsHelper.java   Ajuste a las barras del sistema
    res/                        Diseños XML, colores, textos e iconos
gradle/                         Wrapper y catálogo de versiones
build.gradle.kts                Configuración general
settings.gradle.kts             Módulos y repositorios de dependencias
secrets.properties.example      Ejemplo de configuración sin credenciales
```

## Dependencias

Las dependencias se declaran en Gradle y se descargan durante la sincronización: AndroidX, Material Components, RecyclerView, Volley y Glide. No se incluyen sus archivos binarios en el repositorio. El JAR del wrapper de Gradle se conserva porque permite iniciar la compilación.

El repositorio contiene únicamente el código fuente, recursos, documentación y configuración del proyecto. Los APK, documentos académicos, emuladores, cachés, pruebas auxiliares y herramientas usadas durante la revisión permanecen fuera del control de versiones.
