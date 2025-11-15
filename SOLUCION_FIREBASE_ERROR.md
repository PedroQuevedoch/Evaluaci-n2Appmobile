# 🔧 Solución: Error "Unknown calling package name"

Este error ocurre cuando Firebase no puede verificar el package name de tu aplicación. Sigue estos pasos para resolverlo:

## ✅ Solución 1: Verificar y Agregar SHA-1 en Firebase Console

### Paso 1: Obtener el SHA-1 de tu aplicación

**En Windows (PowerShell):**

```powershell
cd C:\Users\nykon\AndroidStudioProjects\DulzurasYasna
.\gradlew.bat signingReport
```

**O usando keytool directamente:**

```powershell
keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
```

### Paso 2: Copiar el SHA-1

Busca la línea que dice:
```
SHA1: XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX
```

Copia todo el SHA-1 (incluyendo los dos puntos).

### Paso 3: Agregar SHA-1 en Firebase Console

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Selecciona tu proyecto: **bbdd-dulzurasyasna**
3. Ve a **Configuración del proyecto** (ícono de engranaje) > **Tus aplicaciones**
4. Click en tu app Android (com.example.dulzurasyasna)
5. Busca la sección **"Huellas digitales del certificado SHA"**
6. Click en **"Agregar huella digital"**
7. Pega el SHA-1 que copiaste
8. Click en **"Guardar"**

### Paso 4: Descargar el nuevo google-services.json

1. Después de agregar el SHA-1, descarga nuevamente el archivo `google-services.json`
2. Reemplaza el archivo existente en `app/google-services.json`
3. Sincroniza el proyecto en Android Studio

## ✅ Solución 2: Verificar Configuración del Proyecto

### Verificar que el plugin esté en el lugar correcto

El plugin `com.google.gms.google-services` debe estar:
- En el archivo **raíz** `build.gradle.kts` (project level) - ✅ Ya está
- En el archivo **app** `app/build.gradle.kts` (module level) - ✅ Ya está

### Verificar ubicación del google-services.json

El archivo debe estar en:
```
app/
  └── google-services.json  ← Debe estar aquí
```

**NO debe estar en:**
- `app/src/main/`
- `app/src/main/res/`
- Cualquier otra ubicación

## ✅ Solución 3: Limpiar y Reconstruir el Proyecto

1. **En Android Studio:**
   - Ve a: **Build > Clean Project**
   - Espera a que termine

2. **Invalidar cachés:**
   - Ve a: **File > Invalidate Caches / Restart...**
   - Selecciona: **Invalidate and Restart**
   - Espera a que Android Studio se reinicie

3. **Sincronizar Gradle:**
   - Ve a: **File > Sync Project with Gradle Files**
   - Espera a que termine

4. **Reconstruir:**
   - Ve a: **Build > Rebuild Project**
   - Espera a que termine

## ✅ Solución 4: Verificar Dispositivo/Emulador

### Si estás usando un emulador:

1. **Asegúrate de que el emulador tenga Google Play Services:**
   - Usa un emulador con Google Play (no AOSP)
   - Verifica que Google Play Services esté actualizado

2. **Reinicia el emulador:**
   - Cierra el emulador completamente
   - Inícialo nuevamente

### Si estás usando un dispositivo físico:

1. **Verifica que Google Play Services esté instalado:**
   - Abre Google Play Store
   - Busca "Google Play Services"
   - Actualiza si es necesario

2. **Verifica la conexión a internet:**
   - Asegúrate de que el dispositivo tenga conexión
   - Firebase requiere internet para funcionar

## ✅ Solución 5: Verificar Reglas de Firestore (si aplica)

Si estás usando Firestore, verifica que las reglas permitan acceso:

1. Ve a Firebase Console > Firestore Database > Reglas
2. Asegúrate de que las reglas permitan lectura/escritura para usuarios autenticados:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## 🔍 Verificación Final

Después de aplicar las soluciones:

1. **Verifica que el archivo google-services.json esté actualizado:**
   - Debe contener el SHA-1 que agregaste
   - El package_name debe ser: `com.example.dulzurasyasna`

2. **Ejecuta la aplicación nuevamente:**
   - Limpia el proyecto
   - Reconstruye
   - Ejecuta la app

3. **Revisa los logs:**
   - En Android Studio, abre la pestaña **Logcat**
   - Filtra por "Firebase" o "Google"
   - Busca mensajes de error específicos

## 📝 Notas Importantes

- **El SHA-1 es necesario para que Firebase verifique tu aplicación**
- **Cada vez que cambies de máquina o keystore, necesitarás agregar el nuevo SHA-1**
- **Para producción, necesitarás agregar el SHA-1 del keystore de release**

## 🆘 Si el problema persiste

1. **Verifica los logs completos en Logcat**
2. **Asegúrate de que Firebase Authentication esté habilitado**
3. **Verifica que el proyecto Firebase esté activo**
4. **Prueba crear un nuevo proyecto Firebase desde cero**

---

**Paso más importante:** Agregar el SHA-1 en Firebase Console es la solución más común para este error.





