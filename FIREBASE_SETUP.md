# 🔥 Configuración de Firebase - Guía Paso a Paso

Esta guía te ayudará a conectar tu aplicación Android con Firebase Authentication y Firestore.

## 📋 Requisitos Previos

- Cuenta de Google con acceso a Firebase Console
- Android Studio instalado
- Proyecto Android configurado

## 🚀 Pasos para Configurar Firebase

### Paso 1: Crear Proyecto en Firebase Console

1. **Abrir Firebase Console**
   - Ve a: https://console.firebase.google.com/
   - Inicia sesión con tu cuenta de Google

2. **Crear Nuevo Proyecto**
   - Click en **"Agregar proyecto"** o **"Create a project"**
   - Ingresa un nombre para el proyecto (ej: "DulzurasYasna")
   - Click en **"Continuar"** o **"Continue"**
   - **Opcional**: Desactiva Google Analytics si no lo necesitas
   - Click en **"Crear proyecto"** o **"Create project"**
   - Espera a que se complete la creación (puede tomar unos minutos)

### Paso 2: Agregar Aplicación Android al Proyecto

1. **En la página principal del proyecto Firebase**
   - Busca el ícono de **Android** (🤖) o click en **"Agregar una app"** > **Android**

2. **Registrar la App**
   - **Nombre del paquete Android**: `com.example.dulzurasyasna`
     - ⚠️ **IMPORTANTE**: Este debe coincidir exactamente con el `applicationId` en `app/build.gradle.kts`
   - **Apodo de la app** (opcional): "DulzurasYasna"
   - **Certificado de depuración SHA-1** (opcional, para ahora puedes dejarlo vacío)
   - Click en **"Registrar app"** o **"Register app"**

### Paso 3: Descargar google-services.json

1. **Descargar el archivo de configuración**
   - Se mostrará una página con el botón **"Descargar google-services.json"**
   - Click en **"Descargar google-services.json"**

2. **Agregar el archivo al proyecto**
   - Abre Android Studio
   - En el proyecto, navega a: `app/` (carpeta raíz del módulo app)
   - **Copia** el archivo `google-services.json` descargado
   - **Pega** el archivo en: `app/google-services.json`
     - 📍 **Ubicación exacta**: `app/google-services.json` (al mismo nivel que `build.gradle.kts`)

3. **Verificar la ubicación**
   ```
   DulzurasYasna/
   ├── app/
   │   ├── build.gradle.kts
   │   ├── google-services.json  ← AQUÍ debe estar
   │   └── src/
   └── ...
   ```

### Paso 4: Habilitar Firebase Authentication

1. **En Firebase Console**
   - En el menú lateral izquierdo, ve a **"Authentication"** o **"Autenticación"**
   - Click en **"Comenzar"** o **"Get started"**

2. **Habilitar Email/Password**
   - Click en la pestaña **"Sign-in method"** o **"Método de inicio de sesión"**
   - Busca **"Correo electrónico/Contraseña"** o **"Email/Password"**
   - Click en **"Correo electrónico/Contraseña"**
   - **Habilita** el primer toggle (Email/Password)
   - Click en **"Guardar"** o **"Save"**

### Paso 5: Configurar Firestore Database (Opcional pero Recomendado)

1. **Crear Base de Datos Firestore**
   - En el menú lateral, ve a **"Firestore Database"** o **"Base de datos Firestore"**
   - Click en **"Crear base de datos"** o **"Create database"**

2. **Configurar Reglas de Seguridad**
   - Selecciona **"Comenzar en modo de prueba"** o **"Start in test mode"**
   - ⚠️ **Nota**: Esto permite lectura/escritura durante 30 días. Para producción, configura reglas de seguridad.
   - Selecciona una ubicación (elige la más cercana a ti)
   - Click en **"Habilitar"** o **"Enable"**

3. **Configurar Reglas de Seguridad Básicas** (Recomendado)
   - En la pestaña **"Reglas"** o **"Rules"**, reemplaza el contenido con:
   ```javascript
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /users/{userId} {
         // Permitir lectura/escritura solo al usuario autenticado
         allow read, write: if request.auth != null && request.auth.uid == userId;
       }
     }
   }
   ```
   - Click en **"Publicar"** o **"Publish"**

### Paso 6: Sincronizar el Proyecto en Android Studio

1. **Sincronizar Gradle**
   - En Android Studio, ve a: **File > Sync Project with Gradle Files**
   - O click en el ícono de sincronización en la barra de herramientas
   - Espera a que termine la sincronización

2. **Verificar que no haya errores**
   - Revisa la pestaña **"Build"** en la parte inferior
   - Debe mostrar **"BUILD SUCCESSFUL"**

### Paso 7: Probar la Configuración

1. **Ejecutar la aplicación**
   - Conecta un dispositivo o inicia un emulador
   - Click en **Run ▶** o presiona `Shift + F10`

2. **Probar Registro**
   - En la app, ve a la pantalla de registro
   - Crea un nuevo usuario con:
     - Nombre: "Test User"
     - Email: "test@example.com"
     - Username: "testuser"
     - Contraseña: "password123"
   - Click en **"Registrarse"**
   - Debe mostrar un indicador de carga y luego navegar a la pantalla principal

3. **Verificar en Firebase Console**
   - Ve a **Authentication > Users**
   - Debes ver el usuario recién creado con el email registrado
   - Ve a **Firestore Database > Data**
   - Debes ver una colección `users` con los datos del usuario (nombre, email, username)

4. **Probar Login**
   - Cierra la app y vuelve a abrirla
   - Intenta iniciar sesión con:
     - Username: "testuser"
     - Contraseña: "password123"
   - Debe iniciar sesión correctamente

## 🔍 Solución de Problemas

### Error: "google-services.json not found"
- **Solución**: Verifica que el archivo `google-services.json` esté en `app/google-services.json`
- Asegúrate de que el nombre del paquete en Firebase coincida con `applicationId` en `build.gradle.kts`

### Error: "FirebaseApp not initialized"
- **Solución**: 
  1. Verifica que el plugin `com.google.gms.google-services` esté en `app/build.gradle.kts`
  2. Sincroniza el proyecto: **File > Sync Project with Gradle Files**
  3. Limpia y reconstruye: **Build > Clean Project** y luego **Build > Rebuild Project**

### Error: "Email already in use"
- **Solución**: El email ya está registrado. Usa otro email o elimina el usuario desde Firebase Console

### Error: "Network error" o "Connection timeout"
- **Solución**: 
  1. Verifica tu conexión a internet
  2. Asegúrate de que Firebase esté habilitado en tu proyecto
  3. Verifica que las reglas de Firestore permitan lectura/escritura

### No se crean usuarios en Firestore
- **Solución**: 
  1. Verifica que Firestore esté habilitado en Firebase Console
  2. Verifica que las reglas de seguridad permitan escritura
  3. Revisa los logs en Android Studio para ver errores específicos

## 📱 Estructura de Datos en Firestore

Después de registrar usuarios, la estructura en Firestore será:

```
users/
  └── {userId}/
      ├── name: "Nombre del Usuario"
      ├── email: "usuario@example.com"
      ├── username: "nombreusuario"
      └── createdAt: Timestamp
```

## ✅ Checklist de Configuración

- [ ] Proyecto creado en Firebase Console
- [ ] Aplicación Android agregada al proyecto
- [ ] `google-services.json` descargado y colocado en `app/`
- [ ] Firebase Authentication habilitado con Email/Password
- [ ] Firestore Database creada y configurada
- [ ] Proyecto sincronizado en Android Studio
- [ ] Usuario de prueba creado exitosamente
- [ ] Login funciona correctamente

## 🎉 ¡Listo!

Una vez completados estos pasos, tu aplicación estará conectada con Firebase y podrás:
- ✅ Registrar nuevos usuarios
- ✅ Iniciar sesión con username y contraseña
- ✅ Validar usuarios únicos (username y email)
- ✅ Almacenar información adicional de usuarios en Firestore

## 📚 Recursos Adicionales

- [Documentación oficial de Firebase Authentication](https://firebase.google.com/docs/auth)
- [Documentación oficial de Firestore](https://firebase.google.com/docs/firestore)
- [Guía de configuración de Firebase para Android](https://firebase.google.com/docs/android/setup)

---

**Nota**: Si encuentras algún problema durante la configuración, revisa los logs en Android Studio (pestaña "Logcat") para ver mensajes de error específicos.

