# Operación fuego Quasar: Mercado Libre Challenge
## CESAR ANDRES NUNEZ TOBAR
#### cesarnt@gmail.com

Desarrollo de challenge técnica "Operación fuego Quasar".

## Stack Tecnológico
Aplicación backend desarrollada en:
- Spring Boot 3.2.5
- GCP
- Cloud Firestore
- JUNIT 5
- Docker

## Dependencias de la solución

| Dependencia                                       | Descripción                                                   | URL                                             |
|---------------------------------------------------|---------------------------------------------------------------|-------------------------------------------------|
| Spring Boot:3.2.5                                 | Base de la API Rest                                           | https://spring.io/projects/spring-boot          |
| com.google.cloud:google-cloud-firestore:3.7.0      | Para persistencia de mensajeria BD NoSql                      |                |
| org.projectlombok:lombok:1.18.32                  | Lombok                                                        | https://github.com/derjust/spring-data-dynamodb |
| javax.validation:validation-api:2.0.1.Final                | Transformación hacia y desde JSON                             |                   |
| com.lemmingapex.trilateration:trilateration:1.0.2 | Para la trilateración de la ubicación de emisores de mensajes | https://github.com/lemmingapex/trilateration    |

## Dependencia de Test
| Dependencia                      | Descripción                                      | URL                                            |
|----------------------------------|--------------------------------------------------|------------------------------------------------|
| org.mockito:mockito-inline:4.9.0 | Para la construccion de mocks estaticos en JUNIT | https://github.com/mockito/mockito |
| org.junit.jupiter:junit-jupiter:5.9.1 | Para la construccion de Test unitarios  |  |

## Versiones
La implementación presenta 3 versiones

| Tag   | Descripción   | URL   |
|-------|---------------|-------|
| 1.0   | Corresponde a la implementación del caso en su nivel 1 | https://github.com/12asec/challenge/tree/feature/nivel1 |
| 2.0   | Corresponde a la implementación del caso en su nivel 2 | https://github.com/12asec/challenge/tree/feature/nivel2 |
| 3.0   | Corresponde a la implementación del caso en su nivel 3 | https://github.com/12asec/challenge/tree/feature/nivel3 |

## Configuración de entorno
La solución requiere de 5 variables de entorno, las cuales deben existir en el contenedor que esta ejecutando la aplicación

| Variable       | Descripción                                                | Valor |
|----------------|------------------------------------------------------------|-------| 
| ENV | Entorno en el cual se esta ejecutando la solucion          | dev   |
| LOCATION_INIT | posiciones iniciales de satelites Kenobi, Skywalker y Sato | {"satelites":[{"name":"KENOBI","location":{"x":-500,"y":-200}},{"name":"SKYWALKER","location":{"x":100,"y":-100}},{"name":"SATO","location":{"x":500,"y":100}}]}   |
| STARS | Estrellas que se aceptan para procesamiento de request     |  Alderaan, Anobis, Shawken, Bespin, Mandalore  |
| COMMON_REPUBLIC| Corresponde a secreto consensuado entre emisor y receptor  |   l1b3rt4d    |
| SERVER_PORT| Puerto de ejecucion de la solucion                         |  8080   |

## Despliegue
Es posible desplegar la implementación en un entorno local de ejecución mediante docker, para lo cual debe ejecutar los siguientes comandos en su terminal ubicado en la raiz de la solución

Cambio de branch a probar
```sh
git checkout (feature/nivel)
```
Compilación de la imagen:
```sh
docker build -t challenge .
```
Levantamiento del contenedor:
```sh
docker run -p 8082:8080 -e COMMON_REPUBLIC="l1b3rt4d;ENV=dev" -e LOCATION_INIT='{"satelites":[{"name": "KENOBI","location": {"x": -500,"y": -200}},{"name":"SKYWALKER","location":{"x":100,"y":-100}},{"name":"SATO","location":{"x":500,"y":100}}]}' -e SERVER_PORT=8080 -e STARS="Alderaan, Anobis, Shawken, Bespin, Mandalore" challenge:latest
```

## Documentación de API

En el directorio docs se adjunta: fichero swagger (openapi) y colección POSTMAN
para pruebas

A continuación se describen los endpoints principales

| Endpoint                          | Descripción                                                                                          | Verbo Soportado |
|-----------------------------------|------------------------------------------------------------------------------------------------------|-----------------|
| /topsecret                        | Implementación de descifrado de mensajeria de resistencia en una sola request (Solicitud de Nivel 2) | POST            |
| /topsecret_split                  | Obtención de resultado de mensajeria persistido entre requests (Solicitud de Nivel 3)                | GET             |
| /topsecret_split/{nombreSatelite} | Persistencia de mensaje desde satelite (Solicitud de Nivel 3)                                        | POST            |

La API Rest del nivel 2 se encuentra desplegada para su consumo mediante POSTMAN o equivalentes en la siguiente ruta, desplegada mediante Cloud Run - GCP

https://challenge-u5rbmpvxzq-uk.a.run.app/topsecret/

## Contacto

Para consultas  [Cesarnt@gmail](mailto:cesarnt@gmail.com)

Fono: +56 9 6905 5350