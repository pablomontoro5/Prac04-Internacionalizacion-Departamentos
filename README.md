
# Práctica 4 – Internacionalización de Departamentos (IPO)

Aplicación de escritorio desarrollada en **Java (Swing)** para la gestión de departamentos, con soporte completo de **internacionalización**, **recursos gráficos por idioma**, **IDs persistentes** y **cambio de tema visual**.

Proyecto realizado para la asignatura **Interacción Persona–Ordenador (IPO).**.

##  Funcionalidad

La aplicación permite realizar las siguientes operaciones sobre departamentos:

- Alta de departamentos
- Baja de departamentos
- Consulta por ID
- Modificación de datos
- Listado completo de departamentos

Cada departamento dispone de un **ID único y persistente**, que no se renumera al eliminar registros.

---

##  Internacionalización

La aplicación incluye un sistema de internacionalización propio basado en:

- Fichero externo `idiomas.txt` con textos e imágenes por idioma
- Enum `Textos` para evitar números mágicos
- Clase `I18n` para la carga dinámica de recursos

Idiomas soportados:
- Español
- Inglés
- Francés
- Italiano
- Alemán
- Polaco

El cambio de idioma se realiza **en tiempo de ejecución**, actualizando textos, menús e imagen principal sin reiniciar la aplicación.

---

##  Recursos gráficos

Cada idioma dispone de:
- Bandera identificativa (`banderaXX.png`)
- Imagen principal de bienvenida (`campusXX.png`)

La imagen principal se escala automáticamente al ancho de la ventana manteniendo su proporción.

---

##  Interfaz de usuario

- Menú superior con acceso a todas las operaciones
- Selector de idioma con banderas
- Cambio de tema claro / oscuro (FlatLaf)
- Imagen de bienvenida dinámica por idioma

---

##  Tecnologías utilizadas

- Java 21
- Swing
- FlatLaf
- Git (control de versiones)
- IntelliJ IDEA

Las librerías externas se encuentran en el directorio `lib/`.

---

##  Estructura del proyecto

```

src/
├── app        (clase Main)
├── ui         (ventanas Swing)
├── model      (clases de dominio)
├── data       (gestión y persistencia de datos)
└── i18n       (internacionalización)
resources/
├── img        (imágenes por idioma)
└── idiomas.txt

```

---

##  Ejecución

1. Abrir el proyecto en IntelliJ IDEA
2. Asegurar que las librerías `.jar` están configuradas
3. Ejecutar la clase `Main`

---

##  Autor

- **Pablo Javier Montoro Bermúdez**
- Grado en Ingeniería Informática
- Universidad de Jaén

---

## Notas

Este repositorio contiene el código fuente completo de la práctica.  
La documentación detallada se encuentra en la memoria entregada junto al proyecto.

 [Acceder a la memoria completa en Google Docs](https://docs.google.com/document/d/1eJRbv2fu9FfUyOx_BToLVTUFN85HBZtTEei0gbVMehk/edit?tab=t.0#heading=h.vvg1az7cvq9k)




