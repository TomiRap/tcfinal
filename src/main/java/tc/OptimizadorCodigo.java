package tc;

import java.io.IOException;
import java.util.*;

public class OptimizadorCodigo {
    private final List<String> codigoOptimizado;
    private final Map<String, String> constantes;
    private final Map<String, String> aliasVariables;
    private final Set<String> variablesUtilizadas;

    public OptimizadorCodigo() {
        this.codigoOptimizado = new ArrayList<>();
        this.constantes = new HashMap<>();
        this.aliasVariables = new HashMap<>();
        this.variablesUtilizadas = new HashSet<>();
    }

    public List<String> optimizarCodigo(List<String> codigoIntermedio) {
        limpiarEstado();
        procesarInstrucciones(codigoIntermedio);
        return eliminarCodigoMuerto();
    }

    private void limpiarEstado() {
        codigoOptimizado.clear();
        constantes.clear();
        aliasVariables.clear();
        variablesUtilizadas.clear();
    }

    private void procesarInstrucciones(List<String> instrucciones) {
        for (String instruccion : instrucciones) {
            procesarInstruccion(instruccion);
        }
    }

    private void procesarInstruccion(String instruccion) {
        if (instruccion.contains("=")) {
            manejarAsignacion(instruccion);
        } else if (instruccion.startsWith("if")) {
            manejarCondicional(instruccion);
        } else if (instruccion.startsWith("goto")) {
            manejarSalto(instruccion);
        } else {
            codigoOptimizado.add(instruccion);
        }
    }

    private void manejarAsignacion(String instruccion) {
        String[] partes = instruccion.split("=");
        String destino = partes[0].trim();
        String origen = partes[1].trim();

        origen = resolverExpresion(origen);

        if (!destino.equals(origen)) {
            if (destino.startsWith("t")) {
                aliasVariables.put(destino, origen);
            } else {
                if (esNumero(origen)) {
                    constantes.put(destino, origen);
                }
                variablesUtilizadas.add(destino);
                codigoOptimizado.add(destino + " = " + origen);
            }
        }
    }

    private String resolverExpresion(String expresion) {
        if (constantes.containsKey(expresion)) {
            return constantes.get(expresion);
        }
        if (aliasVariables.containsKey(expresion)) {
            return aliasVariables.get(expresion);
        }
        if (esExpresionAritmetica(expresion)) {
            return String.valueOf(calcularExpresion(expresion));
        }
        return expresion;
    }

    private void manejarCondicional(String instruccion) {
        String[] elementos = instruccion.split(" ");
        if (elementos.length == 4 && constantes.containsKey(elementos[1])) {
            int valor = Integer.parseInt(constantes.get(elementos[1]));
            if (valor != 0) {
                codigoOptimizado.add("goto " + elementos[3]);
            }
        } else {
            codigoOptimizado.add(instruccion);
        }
    }

    private void manejarSalto(String instruccion) {
        if (codigoOptimizado.isEmpty() || 
            !codigoOptimizado.get(codigoOptimizado.size() - 1).startsWith("goto")) {
            codigoOptimizado.add(instruccion);
        }
    }

    private boolean esExpresionAritmetica(String expresion) {
        return expresion.matches("\\d+ [+\\-*/] \\d+");
    }

    private boolean esNumero(String valor) {
        return valor.matches("\\d+");
    }

    private int calcularExpresion(String expresion) {
        String[] elementos = expresion.split(" ");
        int a = Integer.parseInt(elementos[0]);
        int b = Integer.parseInt(elementos[2]);
        
        return switch (elementos[1]) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> b != 0 ? a / b : 0;
            default -> 0;
        };
    }

    private List<String> eliminarCodigoMuerto() {
        return codigoOptimizado.stream()
            .filter(instr -> !contieneAsignacionNoUsada(instr))
            .toList();
    }

    private boolean contieneAsignacionNoUsada(String instruccion) {
        if (!instruccion.contains(" = ")) {
            return false;
        }
        String variable = instruccion.split(" = ")[0].trim();
        return !variablesUtilizadas.contains(variable) && !instruccion.contains("main");
    }
    
    public void imprimirCodigoOptimizado() {
        System.out.println("\nCódigo Optimizado:");
        codigoOptimizado.forEach(System.out::println);        
    }
    
}