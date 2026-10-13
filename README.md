# Libreria

Es una API para una biblioteca. Se pueden guardar libros, socios y prestamos.

Cuando se presta un libro se resta un ejemplar. Cuando se devuelve, se suma otra vez.

La hice con Java 21, Spring Boot y MySQL. Spring Data JPA se encarga de guardar los datos en la base.

## Como correrla

Hay que tener MySQL prendido y Java 21.

```bash
export JAVA_HOME="/opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"
export DB_PASSWORD=tu_contrasena

cd libreria
./mvnw spring-boot:run
```

Queda en `http://localhost:1500`.

La base se llama `libreria` y se crea sola si MySQL lo permite. El usuario por defecto es `root`. Si se quiere otro usuario o otra url se usan `DB_USER` y `DB_URL`.

## Rutas

- Libros: `/libros`
- Socios: `/personas`
- Prestamos: `/prestamos`

En las tres se puede listar, ver uno por id, crear, actualizar y borrar.

Para devolver un libro se usa `PUT /prestamos/{id}/devolver`.

Si algo esta mal, la API manda un mensaje. Por ejemplo si ya no hay ejemplares o el socio no esta activo.

No hay login. Si se guarda la contrasena del socio, no se regresa en las respuestas.

## Ejemplos

Crear un libro:

```http
POST http://localhost:1500/libros
Content-Type: application/json

{
  "titulo": "Cien anos de soledad",
  "autor": "Gabriel Garcia Marquez",
  "editorial": "Sudamericana",
  "aPublicacion": "1967",
  "noEjemplares": 3
}
```

Crear un socio:

```http
POST http://localhost:1500/personas
Content-Type: application/json

{
  "nombre": "Ana",
  "apellido": "Lopez",
  "fechaNacimiento": "2000-05-12",
  "correo": "ana@correo.com",
  "telefono": "5512345678",
  "direccion": "Av. Reforma 100"
}
```

Pedir un libro prestado:

```http
POST http://localhost:1500/prestamos
Content-Type: application/json

{
  "persona": { "id": 1 },
  "libro": { "isbn": 1 }
}
```

La respuesta trae el prestamo con estado `Prestado` y el libro ya con un ejemplar menos.
