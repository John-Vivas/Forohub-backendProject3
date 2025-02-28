# ForoHub - Proyecto de Foro Interactivo

ForoHub es una aplicación de foro interactivo desarrollada con Spring Boot 3. La aplicación permite a los usuarios crear, ver, actualizar y eliminar tópicos, así como interactuar con la API utilizando Insomnia. Además, la aplicación incluye autenticación y generación de tokens JWT para seguridad.

## Características

- **Registro de nuevo tópico**: Crear nuevos tópicos en el foro.
- **Mostrar todos los tópicos**: Ver una lista de todos los tópicos.
- **Detallar un tópico**: Ver información detallada de un tópico específico.
- **Actualizar un tópico**: Modificar la información de un tópico existente.
- **Eliminar un tópico**: Eliminar un tópico del foro.
- **Autenticación**: Autenticación con Spring Security y JWT.

## Dependencias

El proyecto utiliza las siguientes dependencias:

- Lombok
- Spring Web
- Spring Boot DevTools
- Spring Data JPA
- Flyway Migration
- MySQL Driver
- Validation
- Spring Security

## Diagrama de Base de Datos

La base de datos de ForoHub contiene las siguientes tablas e información:

- **Tópico**
  - `id`
  - `título`
  - `mensaje`
  - `fecha de creación`
  - `status`
  - `autor`
  - `curso`

## Configuración de Base de Datos

En el archivo `application.properties`, configura la base de datos MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forohub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
