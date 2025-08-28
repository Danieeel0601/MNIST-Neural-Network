# Proyecto_Def_Equipo1

Este proyecto implementa una red neuronal para reconocimiento de d�gitos.

## Requisitos
- Java 8 o superior
- Maven (solo si deseas compilar desde el c�digo fuente)

## Estructura
- C�digo fuente: `src/main/java`
- Datos para entrenamiento/modificaci�n: `data/`
- Archivo principal para ejecuci�n: `Neuronas.dat`
- JAR compilado: `target/Proyecto_Def_Equipo1-1.0-SNAPSHOT.jar`

## Instalaci�n
Clona el repositorio:
```sh
git clone https://github.com/tu-usuario/Proyecto_Def_Equipo1.git
cd Proyecto_Def_Equipo1
```

### Ejecuci�n r�pida (sin Maven)
Aseg�rate de tener Java instalado. Ejecuta:
```sh
java -cp target/Proyecto_Def_Equipo1-1.0-SNAPSHOT.jar Vista.Inicio
```

### Compilar y ejecutar con Maven
```sh
mvn clean package
mvn exec:java -Dexec.mainClass="Vista.Inicio"
```

## Notas
- El archivo `Neuronas.dat` es necesario para la ejecuci�n inicial del programa.
- La carpeta `data/` contiene archivos para modificar o reentrenar la red neuronal (opcional para usuarios avanzados).

## Licencia
[MIT](LICENSE)
