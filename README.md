# Gestor de Tareas — Aplicación Fullstack

Aplicación web fullstack para la gestión de tareas: permite crear, listar, actualizar y eliminar tareas, cambiar su estado y filtrarlas. Construida con **Angular** en el frontend y **Spring Boot + PostgreSQL** en el backend, comunicándose mediante una **API REST**.

Este proyecto forma parte de mi portafolio como desarrollador orientado a **fullstack (Angular + Java)**.

---

## Tecnologías

**Frontend**
- Angular 19 (componentes standalone)
- TypeScript
- Signals (estado reactivo)
- HttpClient (consumo de la API REST)

**Backend**
- Java 17
- Spring Boot (Spring Web, Spring Data JPA, Validation)
- PostgreSQL
- Maven (con wrapper incluido)

---

## Estructura del proyecto

Este repositorio es un **monorepo** con el backend y el frontend en carpetas separadas:

```
gestor-tareas/
├── backend/    → API REST con Spring Boot
└── frontend/   → Aplicación Angular
```

---

## Funcionalidades

- Crear tareas con título, descripción y prioridad.
- Listar todas las tareas.
- Filtrar tareas por estado (Pendiente, En progreso, Completada).
- Cambiar el estado de una tarea.
- Eliminar tareas.
- Persistencia real en base de datos PostgreSQL.

---

## Arquitectura del backend

El backend sigue una arquitectura por capas, separando responsabilidades:

- **Entity** (`Tarea`) — modela la tabla de la base de datos.
- **Repository** (`TareaRepository`) — acceso a datos mediante Spring Data JPA.
- **Service** (`TareaService`) — lógica de negocio.
- **Controller** (`TareaController`) — expone los endpoints REST.

El manejo de errores se centraliza con una excepción propia (`RecursoNoEncontradoException`) que responde con `404 Not Found` cuando un recurso no existe.

### Endpoints

| Método | Ruta                          | Descripción                    |
|--------|-------------------------------|--------------------------------|
| GET    | `/api/tareas`                 | Lista todas las tareas         |
| GET    | `/api/tareas?estado=PENDIENTE`| Filtra las tareas por estado   |
| GET    | `/api/tareas/{id}`            | Obtiene una tarea por su id    |
| POST   | `/api/tareas`                 | Crea una nueva tarea           |
| PUT    | `/api/tareas/{id}`            | Actualiza una tarea existente  |
| DELETE | `/api/tareas/{id}`            | Elimina una tarea              |

**Estados posibles:** `PENDIENTE`, `EN_PROGRESO`, `COMPLETADA`
**Prioridades posibles:** `BAJA`, `MEDIA`, `ALTA`

---

## Cómo ejecutarlo localmente

Necesitas el **backend** y el **frontend** corriendo al mismo tiempo.

### Requisitos previos

- Java 17
- Node.js y Angular CLI
- PostgreSQL en ejecución, con una base de datos llamada `gestor_tareas`

### 1. Backend

La contraseña de la base de datos se lee de una variable de entorno para no exponerla en el código. Antes de ejecutar, define `DB_PASSWORD` con la contraseña de tu usuario de PostgreSQL:

```bash
# Windows (PowerShell)
$env:DB_PASSWORD="tu_contraseña"

# Linux / macOS
export DB_PASSWORD="tu_contraseña"
```

Luego, desde la carpeta `backend/`:

```bash
cd backend

# Windows
./mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

El backend quedará disponible en `http://localhost:8080`.

### 2. Frontend

En otra terminal, desde la carpeta `frontend/`:

```bash
cd frontend
npm install
ng serve
```

El frontend quedará disponible en `http://localhost:4200` y se conectará automáticamente al backend.

---

## Autor

**Andy Ibrahim García Rivera** — Egresado de Ingeniería en Computación
[LinkedIn](https://www.linkedin.com/in/garc%C3%ADa-rivera-andy-ibrahim-bb3523339) · [GitHub](https://github.com/AndyGarcia291003)