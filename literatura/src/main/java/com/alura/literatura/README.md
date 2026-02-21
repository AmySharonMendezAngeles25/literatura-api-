📚 **LITERATURA API**

Esta aplicacion desarrolllada en JAVA con Spring Boot que permite consultar libros desde una API externa,
almacenarlos en una base de datos PostgresSQL y generaestadisticas sobre autores y libros 
Este proyecto fue desarrollado como parte del programa ONE-Oracle Next Education.

🚀  **FUNCIONALIDADES**

✔ Buscar libro por título (consumiendo API Gutendex)

✔ Guardar libros y autores en base de datos

✔ Listar libros guardados

✔ Listar autores guardados

✔ Listar autores vivos en un año determinado

✔ Mostrar cantidad de libros por idioma

🛠 **TECNOLOGIAS UTILIZADAS**

* JAVA 21 
* Spring Boot
* Spring Data JPA
* Hibernate 
* PostgresSQL
* Maven
* API Gutendex
* IntelliJ IDEA

🗄 **BASE DE DATOS**

El proyecta utiliza PostgresSQL como centro de gestion de base de datos.

Las entidades principales son:

📖 **LIBRO**

* id
* titulo
* idioma
* numero de descargas 
* autor 

✍ **AUTOR**

* id 
* nombre 
* fecha de nacimiento 
* fecha de fallecimiento 
* lista de libros 

📊  **CONSULTAS IMPLEMENTADAS**

📌 Cantidad de libros por idioma

Se implementó una consulta que permite mostrar la cantidad de libros almacenados en la base de datos por idioma.

Ejemplo de salida:

🌎 Español (es): 13 libros

🌎 Inglés (en): 5 libros

📌 Autores vivos en determinado año

Se utiliza una consulta JPQL para obtener autores que estaban vivos en el año ingresado por el usuario:

@Query

("""SELECT a FROM Autor a

WHERE a.fechaNacimiento <= :anio

AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :anio)
""")

List<Autor> autoresVivosEnAnio(@Param("anio") Integer anio);

▶ **COMO EJECUTAR EL PROYECTO**

1. Clonar el repositorio:

git clone https://github.com/tuusuario/literatura.git

2. Configurar PostgreSQL en application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/literatura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update

3. Ejecutar la aplicacion desde IntelliJ o con:

mvn spring-boot:run

**MENU DE LA APLICACION**

1- Buscar libro por titulo
 
2- Mostrar libros guardados
 
3- Mostrar autores guardados

4- Mostrar autores vivos en un año 

5- Mostrar cantidad de libros por idioma

0- Salir 

**OBJETIVO DEL PROYECTO**

Practicar 

* Consumo de APIs externas 
* Persistencia con JPA 
* Relaciones entre entidades 
* Derived Queries
* JPQL
* Manejo de excepciones 
* Buenas practicas de organizacion 

**AUTORA**

PROYECTO DESARROLLADO POR **AMY SHARON**

PROGRAMA ONE-Oracle Next Education


