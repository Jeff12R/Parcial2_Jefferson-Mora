# CRM API — Spring Boot

API REST para gestión de clientes, contactos y oportunidades comerciales.
## Requisitos

- Java 21
- Maven 3.9+

## Ejecución

```bash
# Clonar / descomprimir el proyecto
cd crm-api

# Compilar y ejecutar
./mvnw spring-boot:run
```

El servidor inicia en `http://localhost:8080`.
## Documentación interactiva

- Swagger UI → `http://localhost:8080/swagger-ui.html`
- H2 Console → `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:crmdb`
  - User: `sa` / Password: *(vacío)*

---

## Usuarios precargados

| Email              | Contraseña      | Rol            |
|--------------------|-----------------|----------------|
| admin@crm.com      | Admin123*       | ROLE_ADMIN     |
| vendedor@crm.com   | Vendedor123*    | ROLE_VENDEDOR  |
| lector@crm.com     | Lector123*      | ROLE_LECTOR    |
## Autenticación

Todos los endpoints (excepto `/auth/**`) requieren un JWT en el header:

```
Authorization: Bearer <token>
```

Obtener token:
```http
POST /auth/login
Content-Type: application/json

{
  "email": "admin@crm.com",
  "password": "Admin123*"
}
```
## Evidencia de uso de IA

Este proyecto fue desarrollado con asistencia de IA (Claude - Anthropic) como herramienta de apoyo en:

- Generación de la estructura base del proyecto (packages, archivos base).
- Propuesta inicial de entidades JPA y sus relaciones.
- Esqueleto de servicios, repositorios y controladores.

**Partes revisadas y ajustadas manualmente:**
- Validación de que la restricción `DELETE solo ADMIN` quede correctamente en `SecurityConfig` y no en el controller.
- Verificación de que `mappedBy` esté en el lado correcto de cada relación `@OneToMany`.
- Confirmación de que `@EntityGraph` en `ClienteRepository` evita efectivamente el N+1.
- Ajuste de mensajes de error en español consistentes con el dominio del proyecto.

**Riesgos de copiar sin validar:**
- El `SecurityConfig` puede parecer correcto visualmente pero tener reglas que se anulan entre sí (el orden de `.requestMatchers` importa en Spring Security).
- Los `@EntityGraph` con nombres de atributo incorrectos fallan en silencio o con errores crípticos en tiempo de ejecución.
- Las relaciones JPA con `cascade = CascadeType.ALL` sin `orphanRemoval` pueden dejar registros huérfanos.

**Decisiones técnicas propias:**
- Usar `@Transactional(readOnly = true)` en métodos de consulta para optimización.
- Separar el `DataInitializer` con los tres roles para facilitar pruebas inmediatas.
- Nombrar los métodos de servicio con verbos distintos a los del bookstore base para diferenciar el código.
