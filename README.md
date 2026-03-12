# 🏨 Hotel Reservation System - Microservices Architecture

Este proyecto es un sistema de gestión de reservas de hotel diseñado bajo una arquitectura de **microservicios** escalable y robusta. Implementa la gestión de inventario de habitaciones, procesos de reserva y seguridad centralizada.

---

## 🛠️ Stack Tecnológico

| Tecnología | Uso |
| :--- | :--- |
| **Java 21** | Lenguaje principal |
| **Spring Boot 4** | Framework base |
| **Spring Cloud OpenFeign** | Comunicación síncrona entre servicios |
| **Spring Security & OAuth2** | Seguridad y autorización (JWT) |
| **PostgreSQL** | Base de datos relacional (una por servicio) |
| **Jakarta Validation** | Validación de datos y reglas de negocio |
| **Maven** | Gestión de dependencias y módulos |

---

## 🏗️ Arquitectura del Proyecto

El sistema utiliza un enfoque de **Monorepo** para facilitar la gestión de la librería compartida y los servicios:



* **`common-exceptions`**: Librería compartida que estandariza las respuestas de error y centraliza los `GlobalExceptionHandler`.
* **`inventory-service`**: Microservicio encargado del catálogo de habitaciones, tipos de habitación y gestión de estados mediante Enums (`AVAILABLE`, `OCCUPIED`, `MAINTENANCE`).
* **`booking-service`**: Orquestador de reservas. Valida disponibilidad consumiendo el `inventory-service` y gestiona el ciclo de vida de la reserva.
* **`auth-service`**: Servidor de recursos y autorizador bajo el estándar OAuth2.

---

## 🚀Inicio rápido

### 1. Clonar el repositorio
Bash
git clone https://github.com/tu-usuario/hotel-reservation-system.git
cd hotel-reservation-system

### 2. Base de Datos
Crea tres bases de datos en PostgreSQL:

* db_inventory

* db_booking

* db_users

Configura las credenciales en los archivos src/main/resources/application.yml de cada microservicio.

### 3. Compilar y Ejecutar
Como el proyecto está configurado con un POM raíz, puedes compilar todo el sistema con un solo comando desde la carpeta principal:

mvn clean install
Luego, inicia los servicios que necesites:

Bash
Ejemplo para ejecutar Booking
cd booking-service
mvn spring-boot:run

## 💡 Retos Técnicos Superados
### 🛡️ Propagación de Tokens JWT
Se implementó un RequestInterceptor en Feign para capturar el token de seguridad de la petición original y propagarlo automáticamente en las llamadas entre microservicios, garantizando que la identidad del usuario se mantenga en todo el flujo.

### ⚠️ Manejo Global de Excepciones
Gracias a la librería common-exceptions, todos los servicios devuelven un formato de error estandarizado:
{
  "code": "INVALID_ARGUMENTS",
  "message": "La fecha de salida debe ser posterior a la de entrada",
  "timestamp": "2026-03-10T22:00:00"
}

### 🔢 Gestión de Enums y Persistencia
Implementación de valores por defecto para estados de habitación directamente en la lógica de JPA, asegurando que cada habitación nueva nazca como AVAILABLE.

## 📌 Endpoints Principales

* Inventory Service (Puerto 8085)
GET /inventory/api/v1/rooms/{id} - Detalle de habitación.

PATCH /inventory/api/v1/rooms/{id}/occupy - Marcar como ocupada.

* Booking Service (Puerto 8084)
POST /bookings/create - Crear reserva (Valida checkIn y checkOut).

GET /bookings/{id} - Detalle de reserva con datos agregados.

## ✉️ Contacto
José Silva - [LinkedIn](www.linkedin.com/in/josesilvap) - jsemnuel101@gm
