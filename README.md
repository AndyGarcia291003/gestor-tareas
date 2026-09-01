# Gestor de Tareas — API REST

API REST para la gestión de tareas, desarrollada con **Spring Boot** y **PostgreSQL**. Permite crear, consultar, actualizar y eliminar tareas, además de filtrarlas por estado. Este proyecto forma parte de mi portafolio como desarrollador orientado a **fullstack (Angular + Java)**.

> El frontend en Angular que consume esta API se encuentra en desarrollo.

---

## Tecnologías

- **Java 17**
- **Spring Boot** (Spring Web, Spring Data JPA, Validation)
- **PostgreSQL**
- **Maven** (con wrapper incluido)

---

## Arquitectura

El proyecto sigue una arquitectura por capas, separando responsabilidades:

- **Entity** (`Tarea`) — modela la tabla de la base de datos.
- **Repository** (`TareaRepository`) — acceso a datos mediante Spring Data JPA.
- **Service** (`TareaService`) — lógica de negocio.
- **Controller** (`TareaController`) — expone los endpoints REST.

El manejo de errores se centraliza con una excepción propia (`RecursoNoEncontradoException`) que responde con `404 Not Found` cuando un recurso no existe.

---

## Endpoints

| Método | Ruta                          | Descripción                          |
|--------|-------------------------------|--------------------------------------|
| GET    | `/api/tareas`                 | Lista todas las tareas               |
| GET    | `/api/tareas?estado=PENDIENTE`| Filtra las tareas por estado         |
| GET    | `/api/tareas/{id}`            | Obtiene una tarea por su id          |
| POST   | `/api/tareas`                 | Crea una nueva tarea                 |
| PUT    | `/api/tareas/{id}`            | Actualiza una tarea existente        |
| DELETE | `/api/tareas/{id}`            | Elimina una tarea                    |

**Estados posibles:** `PENDIENTE`, `EN_PROGRESO`, `COMPLETADA`
**Prioridades posibles:** `BAJA`, `MEDIA`, `ALTA`

### Ejemplo — crear una tarea (POST `/api/tareas`)

```json
{
  "titulo": "Preparar entrega",
  "descripcion": "Terminar la documentación del proyecto",
  "prioridad": "ALTA"
}
```

La respuesta incluye el `id` y la `fechaCreacion` generados automáticamente, y el `estado` inicial `PENDIENTE`.

---

## Cómo ejecutarlo localmente

### Requisitos previos

- Java 17
- PostgreSQL en ejecución
- Una base de datos llamada `gestor_tareas`

### Configuración

La contraseña de la base de datos se lee de una variable de entorno para no exponerla en el código. Antes de ejecutar, define la variable `DB_PASSWORD` con la contraseña de tu usuario de PostgreSQL:

```bash
# En Windows (PowerShell)
$env:DB_PASSWORD="tu_contraseña"

# En Linux / macOS
export DB_PASSWORD="tu_contraseña"
```

El archivo `application.properties` ya está configurado para tomarla:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestor_tareas
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
```

### Ejecución

```bash
# Windows
./mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

La aplicación quedará disponible en `http://localhost:8080`.

---

## Autor

**Andy Ibrahim García Rivera** — Egresado de Ingeniería en Computación
[LinkedIn](https://www.linkedin.com/in/garc%C3%ADa-rivera-andy-ibrahim-bb3523339) · [GitHub](https://github.com/AndyGarcia291003)