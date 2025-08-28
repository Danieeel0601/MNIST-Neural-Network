# MNIST-Neural-Network

Este proyecto implementa una red neuronal para reconocimiento de dígitos.

## Requisitos
- Java 22 o superior
- Maven (solo si deseas compilar desde el código fuente)

## Estructura
- Código fuente: `src/main/java`
- Datos para entrenamiento/modificación: `data/`
- Archivo principal para ejecución: `Neuronas.dat`
- JAR compilado: `target/Proyecto_Def_Equipo1-1.0-SNAPSHOT.jar`

## Instalación
Clona el repositorio:
```sh
git clone https://github.com/tu-usuario/MNIST-Neural-Network.git
cd MNIST-Neural-Network
```

### Ejecución rápida (sin Maven)
Asegúrate de tener Java instalado. Ejecuta:
```sh
java -cp target/Proyecto_Def_Equipo1-1.0-SNAPSHOT.jar Vista.Inicio
```
¡Dibuja bien tus números para que la red trabaje mejor! Es una red pequeña, puede fallar constantemente sus predicciones. 

### Compilar y ejecutar con Maven
```sh
mvn clean package
mvn exec:java -Dexec.mainClass="Vista.Inicio"
```
Si deseas entrenar la red neuronal con diferentes iteraciones o muestras, cambia el segundo comando por el siguiente después de compilar: 
```sh
mvn exec:java -Dexec.mainClass="Neuralnetwork.Backpropagation"
```

## Notas
- El archivo `Neuronas.dat` es necesario para la ejecución inicial del programa.
- La carpeta `data/` contiene archivos para modificar o reentrenar la red neuronal (opcional para usuarios avanzados).

## Licencia
[MIT](LICENSE)





