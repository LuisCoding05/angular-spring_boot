# TechFlix Hub - Plataforma de Entretenimiento

## 🎯 Descripción del Proyecto

**TechFlix Hub** es una plataforma web completa que combina gestión de contenido multimedia, comunidad de usuarios y seguimiento de actividad física. Los usuarios pueden descubrir películas, series y videojuegos, escribir reseñas, conectar con otros usuarios y registrar sus actividades físicas.

Este proyecto está siendo desarrollado con las últimas versiones de **Angular** y **Spring Boot**, y presenta una interfaz moderna y atractiva gracias a **Tailwind CSS**.

## 🚀 Funcionalidades Clave

### 🎬 Gestión de Contenidos

* **Catálogo de Videojuegos**: Integración con la [API de RAWG](https://rawg.io/apidocs) para acceder a una base de datos extensa de videojuegos.
* **Sistema de Reseñas y Valoraciones**: Permite a los usuarios puntuar el contenido del 1 al 10.
* **Listas Personalizadas**: Los usuarios pueden crear listas como "Por ver", "Favoritos" y "Completados".

### 👥 Comunidad y Social

* **Perfiles de Usuario**: Cada usuario tiene un perfil personalizable con avatar, biografía y estadísticas.
* **Sistema de Seguimiento**: Los usuarios pueden seguirse mutuamente.

### 🔐 Seguridad

* **Autenticación con JWT**: El backend está protegido con **Spring Security** y usa **JSON Web Tokens (JWT)** para autenticar usuarios.
* **Contraseñas Encriptadas**: Las contraseñas se encriptan antes de ser guardadas en la base de datos.

---

## 🛠️ Tecnologías y Dependencias

### Frontend

* **Angular 20+**
* **Tailwind CSS**
* **Angular Material**
* **NgRx**
* **Chart.js**

### Backend

* **Spring Boot 3.2+**
* **Spring Security 6**
* **Spring Data JPA**
* **PostgreSQL / H2**
* **OpenAPI 3 (Swagger)**

---

## 🏗️ Arquitectura Técnica

### Frontend (Angular)

La aplicación Angular está estructurada por módulos para facilitar su mantenimiento:

```
src/
├── app/
│   ├── core/             # Servicios, autenticación, interceptores, etc.
│   ├── shared/           # Componentes y modelos reutilizables
│   ├── features/         # Módulos principales: auth, juegos, perfil...
│   └── layout/           # Diseño general (header, footer, etc.)
└── assets/               # Recursos estáticos
```

### Backend (Spring Boot)

El backend sigue una arquitectura en capas:

```
src/main/java/com/techflixhub/
├── config/               # Configuración general
├── controller/           # Controladores REST
├── service/              # Lógica de negocio
├── repository/           # Acceso a datos
├── entity/               # Entidades de la base de datos
├── dto/                  # Objetos de transferencia
├── security/             # Seguridad y JWT
└── external/             # Conexiones con APIs externas (como RAWG)
```

---

## 🗄️ Modelo de Base de Datos

Principales entidades:

* **User**: Información del usuario
* **Game**: Información de videojuegos (RAWG)
* **Review**: Reseñas y puntuaciones
* **UserList**: Gestión de listas personalizadas

---

## 🧑‍💻 Cómo Ejecutar el Proyecto en Local (Guía para Principiantes)

### ✅ Requisitos Previos

Antes de empezar, asegúrate de tener instaladas estas herramientas:

* **Java 21+**
* **Node.js 20+**
* **Angular CLI**
  Instálalo con: `npm install -g @angular/cli`
* **Maven** (opcional, si no usas el wrapper `./mvnw`)
* **MySQL** (si vas a usar la base de datos real)
  Alternativamente puedes usar **H2**, que es en memoria y más fácil para pruebas.

---

### 🖥️ Instrucciones Paso a Paso

#### 1. Clona el repositorio

```bash
git clone https://github.com/tu-usuario/techflix-hub.git
cd techflix-hub
```

---

### 🔧 Backend (Spring Boot)

#### 2. Entra a la carpeta del backend

```bash
cd backend
```

#### 3. Configura la base de datos

Abre el archivo `src/main/resources/application.properties` o `application.yml`.

Ejemplo básico (usando MySQL):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/techflix
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.security.jwt.secret=unaClaveSecretaSuperSegura
```

> ⚠️ Si no tienes MySQL, puedes usar la base de datos **H2** (más simple para empezar). Solo activa el perfil `dev` en el archivo de configuración.

#### 4. Ejecuta el backend

Si usas Maven:

```bash
./mvnw spring-boot:run
```

O si tienes Maven instalado globalmente:

```bash
mvn spring-boot:run
```

> Si todo va bien, deberías ver un mensaje como:
> `Started TechflixHubApplication in XX seconds...`

---

### 💻 Frontend (Angular)

#### 5. Abre una nueva terminal y ve a la carpeta del frontend

```bash
cd ../angular
```

#### 6. Instala las dependencias

```bash
npm install
```

#### 7. Ejecuta el frontend

```bash
ng serve
```

Esto abrirá la app en tu navegador en:
👉 [http://localhost:4200](http://localhost:4200)

---

### 🧪 Comprobación

* Verifica que puedes registrarte y loguearte.
* Comprueba que puedes buscar juegos (usando la RAWG API).
* Crea una lista personalizada o prueba a dejar una reseña.

---

## 🤝 Cómo Contribuir

1. Haz un fork del repositorio.
2. Crea una rama para tu cambio:

   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. Haz tus cambios y haz commit:

   ```bash
   git commit -m "Añadir nueva funcionalidad"
   ```
4. Sube tu rama:

   ```bash
   git push origin feature/nueva-funcionalidad
   ```
5. Abre un Pull Request y descríbelo claramente.

---

## 📄 Licencia

Este proyecto está bajo la licencia **MIT**. Consulta el archivo [LICENSE](LICENSE) para más detalles.

