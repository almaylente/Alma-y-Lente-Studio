# Alma y Lente Studio — Android

Proyecto Android inicial de editor fotográfico, con interfaz oscura y acentos dorados inspirados en la referencia proporcionada.

## Incluye en esta primera versión
- Abrir imágenes desde la galería Android.
- Ajustes básicos de exposición, contraste, saturación y temperatura.
- Viñeta y estilos de color sencillos.
- Pincel de suavizado local básico con deshacer.
- Exportación JPG/PNG a `Pictures/Alma y Lente Studio`.
- Sin anuncios, cuentas, compras ni llamadas a servidores.

## Limitaciones transparentes
Esta primera versión es una base funcional, no equivale todavía a un editor profesional completo. El pincel de retoque es un suavizado sencillo, no una herramienta de eliminación inteligente de objetos. Capas/máscaras avanzadas, curvas, HSL, Dodge & Burn real, enfoque avanzado y edición RAW requieren desarrollo adicional.

## Cómo obtener el APK sin instalar Android Studio en tu computadora
1. Crea un repositorio nuevo en GitHub y sube todos los archivos de este proyecto.
2. Abre la pestaña **Actions** del repositorio.
3. Ejecuta **Build Android APK** con **Run workflow** (o espera al primer push).
4. Abre la ejecución terminada y descarga el artefacto `Alma-y-Lente-Studio-debug-apk`.
5. Descomprime el ZIP y encontrarás `app-debug.apk`.
6. En tu TECNO POVA 6, abre el APK y permite instalar aplicaciones desde esa fuente cuando Android lo solicite.

El APK de depuración se puede instalar para pruebas. Para distribuir una versión final firmada se debe generar y proteger una clave de firma propia.

## Requisitos
- Android 8.0 o superior (API 26).
- El flujo de compilación usa Java 17 y Android SDK 35.
