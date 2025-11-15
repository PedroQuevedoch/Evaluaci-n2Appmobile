# 🚀 Guía de Configuración en un Nuevo Dispositivo

Esta guía te ayudará a configurar el proyecto en un nuevo PC/dispositivo sin problemas.

## 📋 Requisitos Previos

1. **Android Studio** instalado
2. **JDK** (Java Development Kit) - Android Studio incluye uno
3. **Conexión a Internet** (para descargar dependencias)

## 🔧 Pasos de Configuración

### Paso 1: Clonar o Copiar el Proyecto

1. Si usas Git:
   ```bash
   git clone <url-del-repositorio>
   cd DulzurasYasna
   ```

2. Si copias el proyecto manualmente:
   - Copia toda la carpeta del proyecto
   - Asegúrate de incluir todos los archivos

### Paso 2: Abrir el Proyecto en Android Studio

1. Abre **Android Studio**
2. Selecciona **"Open"** o **"Abrir"**
3. Navega a la carpeta del proyecto `DulzurasYasna`
4. Click en **"OK"**

### Paso 3: Configurar el SDK de Android

Android Studio automáticamente detectará que falta el archivo `local.properties` y lo generará.

**Si no se genera automáticamente:**

1. En Android Studio, ve a: **File > Project Structure** (o `Ctrl+Alt+Shift+S`)
2. En la pestaña **"SDK Location"**, verifica la ruta del SDK
3. Si necesitas cambiarla, hazlo aquí

**O manualmente:**

1. Crea un archivo llamado `local.properties` en la **raíz del proyecto** (mismo nivel que `build.gradle.kts`)
2. Agrega esta línea (ajusta la ruta según tu sistema):

   **Windows:**
   ```properties
   sdk.dir=C\:\\Users\\TU_USUARIO\\AppData\\Local\\Android\\Sdk
   ```

   **macOS:**
   ```properties
   sdk.dir=/Users/TU_USUARIO/Library/Android/sdk
   ```

   **Linux:**
   ```properties
   sdk.dir=/home/TU_USUARIO/Android/Sdk
   ```

3. Reemplaza `TU_USUARIO` con tu nombre de usuario

### Paso 4: Sincronizar el Proyecto

1. Android Studio detectará automáticamente que el proyecto necesita sincronización
2. Click en **"Sync Now"** o **"Sincronizar ahora"**
3. O manualmente: **File > Sync Project with Gradle Files**
4. Espera a que termine la sincronización (puede tomar varios minutos la primera vez)

### Paso 5: Configurar Firebase (IMPORTANTE)

El archivo `google-services.json` **NO** está en el repositorio por seguridad.

**Debes descargarlo manualmente:**

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Selecciona el proyecto: **bbdd-dulzurasyasna**
3. Ve a **Configuración del proyecto** (ícono de engranaje)
4. En **"Tus aplicaciones"**, busca la app Android
5. Click en **"Descargar google-services.json"**
6. **Copia** el archivo descargado a: `app/google-services.json`
   - 📍 **Ubicación exacta**: `app/google-services.json` (carpeta raíz del módulo app)

### Paso 6: Verificar Configuración

1. **Verifica que el SDK esté instalado:**
   - Ve a: **File > Settings > Appearance & Behavior > System Settings > Android SDK**
   - Asegúrate de que el SDK Platform esté instalado (API 36 o la que uses)

2. **Verifica que las dependencias se descargaron:**
   - Abre: `app/build.gradle.kts`
   - Si hay errores de dependencias, click en **"Sync Now"**

### Paso 7: Compilar y Ejecutar

1. **Limpia el proyecto:**
   - **Build > Clean Project**

2. **Reconstruye el proyecto:**
   - **Build > Rebuild Project**

3. **Ejecuta la aplicación:**
   - Conecta un dispositivo o inicia un emulador
   - Click en **Run ▶** o presiona `Shift + F10`

## 🔍 Solución de Problemas Comunes

### Error: "SDK location not found"

**Solución:**
1. Verifica que el archivo `local.properties` existe en la raíz del proyecto
2. Verifica que la ruta del SDK es correcta para tu sistema
3. En Android Studio: **File > Project Structure > SDK Location**

### Error: "google-services.json not found"

**Solución:**
1. Descarga el archivo desde Firebase Console (ver Paso 5)
2. Colócalo en `app/google-services.json`
3. Sincroniza el proyecto: **File > Sync Project with Gradle Files**

### Error: "Gradle sync failed"

**Solución:**
1. Verifica tu conexión a Internet
2. Intenta: **File > Invalidate Caches / Restart > Invalidate and Restart**
3. Limpia el proyecto: **Build > Clean Project**
4. Sincroniza nuevamente: **File > Sync Project with Gradle Files**

### Error: "SDK platform not found"

**Solución:**
1. Ve a: **File > Settings > Android SDK**
2. En la pestaña **"SDK Platforms"**, instala el SDK necesario (API 36)
3. En la pestaña **"SDK Tools"**, asegúrate de tener instalado:
   - Android SDK Build-Tools
   - Android SDK Platform-Tools
   - Android SDK Command-line Tools

### Error: "Package name mismatch"

**Solución:**
1. Verifica que el `applicationId` en `app/build.gradle.kts` sea: `com.example.dulzurasyasna`
2. Verifica que el `package_name` en `google-services.json` coincida
3. Si no coinciden, actualiza uno de los dos

## 📁 Archivos que NO deben estar en Git

Estos archivos son específicos de cada desarrollador y **NO** deben subirse al repositorio:

- ✅ `local.properties` - Ruta del SDK (específica de cada máquina)
- ✅ `.idea/` - Configuración de Android Studio (específica de cada IDE)
- ✅ `google-services.json` - Configuración de Firebase (contiene información sensible)
- ✅ `build/` - Archivos compilados (se generan automáticamente)
- ✅ `*.iml` - Archivos de configuración de IntelliJ/Android Studio

Todos estos archivos ya están en `.gitignore` y se generan automáticamente.

## ✅ Checklist de Verificación

Antes de comenzar a trabajar, verifica:

- [ ] Android Studio está instalado y actualizado
- [ ] El proyecto se abre sin errores
- [ ] El archivo `local.properties` existe (o se genera automáticamente)
- [ ] El archivo `google-services.json` está en `app/`
- [ ] La sincronización de Gradle se completó sin errores
- [ ] El proyecto compila correctamente (`Build > Rebuild Project`)
- [ ] La aplicación se ejecuta en un dispositivo/emulador

## 🎉 ¡Listo!

Una vez completados estos pasos, el proyecto debería funcionar en cualquier dispositivo. Si encuentras algún problema, consulta la sección "Solución de Problemas Comunes" o revisa los logs en Android Studio.

## 📚 Recursos Adicionales

- [Documentación oficial de Android Studio](https://developer.android.com/studio)
- [Guía de Firebase para Android](https://firebase.google.com/docs/android/setup)
- [Documentación de Gradle](https://docs.gradle.org/)

---

**Nota importante**: Cada vez que clones el proyecto en un nuevo dispositivo, solo necesitas seguir estos pasos. Los archivos específicos del sistema se generarán automáticamente.





