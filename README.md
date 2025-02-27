Proyecto BankAccountMVC
Este proyecto combina el patrón Modelo-Vista-Controlador (MVC) con la arquitectura hexagonal en Spring Boot para la creación de cuentas bancarias y la realización de transacciones.

¿Cómo ejecutar el proyecto en tu entorno local?
Requisitos previos
Para correr el proyecto, necesitas un IDE como IntelliJ IDEA (aunque puedes usar otro de tu preferencia).

Pasos para la instalación
Clonar el repositorio
Copia el siguiente enlace del repositorio en GitHub:
👉 https://github.com/brahianAlexisCardonaG/banckaccountMVC

Luego, abre una terminal en tu computadora y ejecuta:

git clone https://github.com/brahianAlexisCardonaG/banckaccountMVC
Abrir el proyecto en el IDE

Abre tu IDE y carga el proyecto.
Ubica el archivo pom.xml y actualiza las dependencias.
Compilar y preparar el proyecto
En la terminal, dentro de la carpeta del proyecto, ejecuta:

mvn clean  
mvn compile  
Esto descargará las dependencias necesarias y compilará el código.

Ejecutar la aplicación

Ubica el archivo principal del proyecto (generalmente, el que contiene el método main).
Corre la aplicación con:

mvn spring-boot:run
Guía en video
Si necesitas más detalles, aquí tienes un video explicativo:
🎥 https://drive.google.com/file/d/1MGZc0mPLhqE7TopfkKhWKVsz4P7XiKH7/view?usp=sharing

Tener en cuenta:

Existen 3 ramas: 

master: se utiliza BeanUtils.copyProperties para mappear entidades. 
change-beanutil-by-mapper: se actualizó para utilizar los Mappers de mapstruct.
created-test-unitaries-backaccount esta version tambien utiliza mapStruct, pero se agregaron pruebas unitarias.

El archivo JSON de Postman está incluido en el proyecto.
También cuenta con documentación de la API a través de Swagger.(http://localhost:3071/swagger-ui/index.html#/)
