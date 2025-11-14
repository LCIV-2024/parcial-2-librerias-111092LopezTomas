# 1) Etapa de build
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Selecciona un directorio dentro del contenedor
WORKDIR /app

# Copia los archivos pom.xml primero (cacheo de dependencias)
COPY pom.xml .

# Descarga dependencias sin compilar
RUN mvn dependency:go-offline -B

# Ahora copiamos el código fuente
COPY src ./src

# Compilamos y generamos el JAR
RUN mvn clean package -DskipTests


# 2) Etapa final (runtime)
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos el JAR generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto del Spring Boot
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
