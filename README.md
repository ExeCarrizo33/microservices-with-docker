# Microservices with Docker

Este proyecto implementa una arquitectura basada en microservicios utilizando Spring Boot, Docker y Docker Compose para la orquestación. Cada microservicio cumple una función específica dentro del sistema, lo que permite escalabilidad, mantenimiento y modularidad.

## Servicios Incluidos

1. **API Gateway**
   - Maneja la comunicación entre los clientes y los microservicios internos.
   - Implementado con Spring Cloud Gateway.

2. **Discovery Server**
   - Servicio de descubrimiento basado en Eureka.
   - Permite que los microservicios se registren y encuentren entre ellos dinámicamente.

3. **Inventory Service**
   - Gestiona el inventario de productos.
   - Proporciona endpoints para consultar y actualizar el stock.

4. **Notification Service**
   - Envía notificaciones relacionadas con pedidos y otras actividades del sistema.

5. **Orders Service**
   - Gestiona la creación y el seguimiento de pedidos.
   - Se comunica con el `Inventory Service` para verificar disponibilidad de productos.

6. **Products Service**
   - Maneja la información de los productos disponibles en el sistema.

## Tecnologías Utilizadas

- **Spring Boot**: Framework principal para el desarrollo de cada microservicio.
- **Spring Cloud**: Para la implementación de patrones de microservicios como el API Gateway y el Discovery Server.
- **Docker**: Para contenerizar los servicios.
- **Docker Compose**: Para la orquestación de los contenedores.
- **Java**: Lenguaje de programación base del proyecto.
- **PostgreSQL**: Base de datos utilizada para los servicios de inventario, productos y autenticación con Keycloak.
- **MySQL**: Base de datos utilizada para el servicio de pedidos.
- **Keycloak**: Para la gestión de usuarios y autenticación.
- **Kafka**: Para la mensajería entre servicios.
- **Zookeeper**: Servicio requerido por Kafka para coordinar los brokers.
- **Zipkin**: Para el rastreo de solicitudes distribuidas (tracing).
- **Prometheus** y **Grafana**: Para el monitoreo y visualización de métricas del sistema.

## Estructura del Proyecto

```plaintext
microservices-with-docker/
├── api-gateway/         # Código del API Gateway
├── discovery-server/    # Código del servidor de descubrimiento
├── inventory-service/   # Código del servicio de inventario
├── notification-service/# Código del servicio de notificaciones
├── orders-service/      # Código del servicio de pedidos
├── products-service/    # Código del servicio de productos
├── docker-compose.yml   # Archivo de orquestación
├── pom.xml              # Configuración principal de Maven
└── README.md            # Documentación del proyecto
```

## Configuración y Ejecución

### Requisitos Previos

- **Docker** y **Docker Compose** instalados en tu máquina.
- JDK 17 o superior si deseas compilar localmente.

### Contenido del Docker Compose

El archivo `docker-compose.yml` define los servicios, redes y volúmenes necesarios para ejecutar el proyecto:

1. **Servicios**:
   - Bases de datos:
     - **PostgreSQL**: Utilizado para `Inventory Service`, `Products Service` y `Keycloak`.
     - **MySQL**: Utilizado para `Orders Service`.
   - **Keycloak**: Gestiona la autenticación y el control de acceso.
   - **Kafka**: Implementa la mensajería entre microservicios, coordinado por **Zookeeper**.
   - **Zipkin**: Para rastrear solicitudes distribuidas y depurar interacciones entre servicios.
   - **Prometheus** y **Grafana**: Monitoreo y visualización de métricas.

2. **Redes**:
   - Una red llamada `microservices-network` conecta todos los contenedores para permitir la comunicación entre ellos.

3. **Volúmenes**:
   - Se utilizan para almacenar datos persistentes, por ejemplo, configuraciones de Keycloak y datos de Prometheus y Grafana.

### Pasos para Ejecutar

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/ExeCarrizo33/microservices-with-docker.git
   cd microservices-with-docker
   ```

2. Construir y ejecutar los contenedores:

   ```bash
   docker-compose up --build
   ```

3. Verificar que los servicios estén corriendo accediendo al API Gateway en `http://localhost:8080` o consultando los logs de los contenedores.

### Endpoints Importantes

- **API Gateway**: `http://localhost:8080`
- **Discovery Server**: `http://localhost:8761`
- **Keycloak**: `http://localhost:8181`
- **Zipkin**: `http://localhost:9411`
- **Prometheus**: `http://localhost:9090`
- **Grafana**: `http://localhost:3000`
