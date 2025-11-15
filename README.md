# Dulzuras Yasna - Prototipo

Aplicación móvil Android para venta de pasteles desarrollada con Jetpack Compose y Firebase.

Checklist de rúbrica: [Carpeta con rúbrica](https://drive.google.com/drive/folders/1O4zE9rMX5gHnLI3fMdeKSIZtxK8-LBX0?usp=sharing)

## Conceptos Implementados

### Interfaz visual y navegación
- Arquitectura con Jetpack Compose y Navigation
- Pantallas: Login, Registro, Home, Detalle, Carrito, Checkout, Acerca de
- Navegación con rutas definidas y botones de retroceso

### Formularios validados
- Login y Registro con validación en tiempo real
- Mensajes de error visuales con animaciones
- Indicadores visuales y botones deshabilitados hasta validación completa

### Validaciones desde lógica
- ViewModels separados (LoginViewModel, SignUpViewModel)
- Validaciones de longitud mínima y formato
- Estado reactivo con StateFlow

### Animaciones
- Transiciones de navegación entre pantallas
- Animaciones en lista de productos y mensajes de error

### Estructura modular y persistencia
- Arquitectura MVVM
- DataStore para persistencia del carrito
- Organización por carpetas: ui/, data/, utils/, model/

### Recursos nativos
- Notificaciones al confirmar pedido en CheckoutScreen

## Cómo ejecutar

### Primera vez / Nuevo dispositivo

Si es la primera vez que abres el proyecto o estás en un nuevo dispositivo, sigue la guía completa:

[Ver Guía de Configuración en Nuevo Dispositivo](SETUP_NUEVO_DISPOSITIVO.md)

### Ejecución rápida

1. Abrir en Android Studio
   - File > Open > Selecciona la carpeta del proyecto

2. Configurar Firebase (requerido)
   - Descarga `google-services.json` desde Firebase Console
   - Colócalo en: `app/google-services.json`
   - Ver [FIREBASE_SETUP.md](FIREBASE_SETUP.md) para más detalles

3. Sincronizar Gradle
   - File > Sync Project with Gradle Files
   - Espera a que termine (primera vez puede tardar varios minutos)

4. Ejecutar la aplicación
   - Conecta un dispositivo o inicia un emulador
   - Click en Run o presiona Shift + F10
   - La app inicia en LoginScreen

## Estructura del proyecto

```
app/src/main/java/com/example/dulzurasyasna/
├── data/
│   ├── repository/
│   │   └── UserRepository.kt      # Firebase Auth + Firestore
│   ├── CartDataStore.kt           # Persistencia del carrito con DataStore
│   └── InMemoryProductRepository.kt
├── model/
│   └── Product.kt
├── ui/
│   ├── LoginViewModel.kt          # Validaciones de login con Firebase
│   ├── SignUpViewModel.kt         # Registro de usuarios con Firebase
│   ├── CartViewModel.kt           # Estado del carrito
│   ├── components/
│   │   └── BackgroundImage.kt     # Componente de fondo reutilizable
│   ├── navigation/
│   │   ├── AppNavGraph.kt         # Navegación con animaciones
│   │   └── NavRoutes.kt
│   └── screens/
│       ├── LoginScreen.kt         # Formulario validado con Firebase
│       ├── SignUpScreen.kt        # Registro de usuarios
│       ├── HomeScreen.kt          # Lista con animaciones
│       ├── DetailScreen.kt
│       ├── CartScreen.kt
│       ├── CheckoutScreen.kt      # Con notificaciones
│       └── AboutScreen.kt
├── utils/
│   └── NotificationUtils.kt       # Recurso nativo: Notificaciones
└── theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## Funcionalidades principales

- Autenticación: Registro y login con Firebase Authentication
- Registro: Formulario de registro con validación en tiempo real
- Login: Inicio de sesión con email o username
- Home: Lista de productos con búsqueda y animaciones
- Detalle: Información del producto con opción de agregar al carrito
- Carrito: Gestión de ítems con persistencia automática (DataStore)
- Checkout: Confirmación de pedido con notificación
- Navegación: Transiciones animadas entre pantallas

## Firebase

- Authentication: Email/Password para registro y login
- Firestore: Almacenamiento de datos adicionales de usuarios (opcional)
- Ver [FIREBASE_SETUP.md](FIREBASE_SETUP.md) para configuración

## Notas

- El carrito se guarda automáticamente en DataStore (persiste entre sesiones)
- Las notificaciones requieren permisos (se solicitan en Android 13+)
- Firebase requiere conexión a internet para funcionar
- Los productos son datos de ejemplo en memoria
- El archivo `google-services.json` debe descargarse desde Firebase Console
- El archivo `local.properties` se genera automáticamente (no debe subirse a Git)

## Documentación

- [SETUP_NUEVO_DISPOSITIVO.md](SETUP_NUEVO_DISPOSITIVO.md) - Guía para configurar el proyecto en un nuevo dispositivo
- [FIREBASE_SETUP.md](FIREBASE_SETUP.md) - Guía de configuración de Firebase
