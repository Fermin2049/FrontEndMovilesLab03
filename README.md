# FrontEndMovilesLab03

Proyecto desarrollado como parte del curso de Desarrollo de Aplicaciones Móviles, usando el framework Android y arquitectura MVVM. Esta aplicación gestiona propiedades, contratos, inquilinos y pagos, proporcionando una experiencia intuitiva y funcional.

## Tabla de Contenidos

- [Introducción](#introducción)
- [Características](#características)
- [Arquitectura](#arquitectura)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Uso](#uso)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Introducción

Esta aplicación móvil para Android permite a los usuarios gestionar propiedades inmobiliarias, contratos de arrendamiento, inquilinos y pagos, proporcionando una solución completa para administrar estas áreas de forma centralizada. Se ha desarrollado utilizando Kotlin y sigue las mejores prácticas de arquitectura de Android, específicamente el patrón MVVM (Model-View-ViewModel).

## Características

- **Gestión de Propiedades**: Visualización, adición y detalles de las propiedades inmobiliarias.
- **Gestión de Inquilinos**: Consulta y detalle de los inquilinos asociados a las propiedades.
- **Contratos y Pagos**: Gestión de contratos de arrendamiento y pagos asociados.
- **Autenticación**: Funcionalidad de inicio de sesión y recuperación de contraseñas.
- **Animaciones y UI Personalizada**: Experiencia visual atractiva con animaciones personalizadas y un diseño de interfaz de usuario.

## Arquitectura

El proyecto sigue una arquitectura **MVVM (Model-View-ViewModel)**, que ayuda a organizar el código de manera modular y a mantener una separación clara entre la interfaz de usuario y la lógica de negocio. La estructura básica es la siguiente:

- **Modelos**: Representan los datos de la aplicación, como `Inmueble`, `Inquilino`, `Contrato`, etc.
- **Vistas**: Fragments y Activities para la interfaz gráfica, diseñadas en XML y ubicadas en `res/layout`.
- **ViewModels**: Gestionan la lógica y los datos asociados a las vistas. Algunos ejemplos incluyen `LoginViewModel`, `PropertyViewModel`, y `ContractViewModel`.

## Requisitos

- **Android Studio** (última versión recomendada)
- **Gradle** 7.0 o superior
- Dispositivo o emulador con **Android 5.0 (Lollipop)** o superior.

## Instalación

1. **Clona este repositorio**:

   ```bash
   git clone https://github.com/Fermin2049/FrontEndMovilesLab03.git
   ```

2. **Importa el proyecto en Android Studio**:
   - Abre Android Studio.
   - Selecciona "Open an existing Android Studio project".
   - Navega hasta la carpeta donde clonaste el repositorio.

3. **Instala las dependencias**:
   - Android Studio descargará automáticamente las dependencias al abrir el proyecto. Asegúrate de tener una conexión a Internet.

4. **Ejecuta la aplicación**:
   - Conecta un dispositivo Android o configura un emulador.
   - Haz clic en el botón "Run" en Android Studio.

## Estructura del Proyecto

Aquí tienes una vista general de la estructura del proyecto:

```plaintext
.
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/fermin2049/proyectofinallab3/
│   │   │   │   ├── activities/                # Actividades de la aplicación
│   │   │   │   ├── adapters/                  # Adaptadores de listas
│   │   │   │   ├── api/                       # Configuración de API y Retrofit
│   │   │   │   ├── models/                    # Modelos de datos
│   │   │   │   ├── ui/                        # Fragments y ViewModels
│   │   │   ├── res/                           # Recursos (layouts, drawables, animaciones)
│   │   ├── test/                              # Pruebas unitarias
├── build.gradle.kts                           # Configuración principal de Gradle
└── README.md
```

## Uso

### Funcionalidades Principales

1. **Inicio de Sesión**:
   - La aplicación cuenta con un sistema de inicio de sesión. Los usuarios pueden autenticarse y, en caso de olvidar la contraseña, hay una opción para recuperarla.

2. **Gestión de Propiedades**:
   - Los usuarios pueden agregar, editar y visualizar detalles de las propiedades. 

3. **Gestión de Contratos e Inquilinos**:
   - Permite gestionar contratos de alquiler, asignar inquilinos y ver los detalles de cada uno.

4. **Pagos**:
   - Funcionalidad para registrar y visualizar pagos realizados.

### Animaciones y Personalización de UI

El proyecto incluye varias animaciones (`res/anim`) y recursos visuales (`res/drawable`) que mejoran la experiencia del usuario. Las animaciones se aplican a transiciones entre vistas, botones y otros elementos interactivos.

## Contribuciones

¡Las contribuciones son bienvenidas! Sigue estos pasos para contribuir al proyecto:

1. Haz un fork de este repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y haz un commit (`git commit -m 'Agregar nueva funcionalidad'`).
4. Sube tus cambios a tu repositorio (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request en este repositorio.

## Licencia

Este proyecto está licenciado bajo la **Licencia MIT**. Consulta el archivo `LICENSE` para más detalles.

---

