# Técnicas de Compilación — Trabajo Final 2024  

## Descripción  
Este proyecto corresponde al **Trabajo Final de la materia Técnicas de Compilación (Universidad Blas Pascal, 2024)**.  
El objetivo es desarrollar un programa en **Java** utilizando **ANTLR** que reciba como entrada un archivo de código fuente en **C** y produzca:  

- **Verificación gramatical** del código, reportando errores si los hubiera.  
- **Generación de código intermedio** en forma de **código de tres direcciones**.  
- **Optimización del código intermedio**, aplicando técnicas simples como propagación de constantes y eliminación de operaciones redundantes.  

## Funcionalidades  
El parser implementa las siguientes verificaciones:  
- Reconocimiento de bloques de código delimitados por llaves `{ }` y control de balance.  
- Verificación de operaciones aritméticas/lógicas y variables o números involucrados.  
- Validación del uso correcto de `;` para la terminación de instrucciones.  
- Balance de llaves, corchetes y paréntesis.  
- Construcción de la **tabla de símbolos**.  
- Reconocimiento de llamados a funciones de usuario.  

Si no se encuentran errores en la fase gramatical, el sistema procede a:  
1. Detectar variables y funciones **declaradas pero no utilizadas** y viceversa.  
2. Generar el **código intermedio** (tres direcciones).  
3. Optimizar dicho código mediante **propagación de constantes** y **eliminación de operaciones repetidas**.  

## Entradas y salidas  
- **Entrada:** archivo en lenguaje C.  
- **Salidas:**  
  1. Tabla de símbolos para todos los contextos.  
  2. Código intermedio en tres direcciones.  
  3. Código intermedio optimizado.  

## Tecnologías utilizadas  
- **Java 17**  
- **ANTLR**  
- **Maven**  
- **Git/GitHub**  
