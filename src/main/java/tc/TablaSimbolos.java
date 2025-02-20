package tc;

import java.util.*;

public class TablaSimbolos {

    // Estructura para manejar contextos anidados
    private Stack<Map<String, Simbolo>> contextos;
    private List<String> historialTabla = new ArrayList<>();

    // Constructor
    public TablaSimbolos() {
        contextos = new Stack<>();
        // Agregamos un contexto global por defecto
        contextos.push(new HashMap<>());
    }

    // Crear un nuevo contexto
    public void agregarContexto() {
        contextos.push(new HashMap<>());
    }

    // Eliminar el contexto actual
    public void eliminarContexto() {
        if (!contextos.isEmpty()) {
            contextos.pop();
        } else {
            throw new RuntimeException("No se puede eliminar el contexto global.");
        }
    }

    // Agregar una variable a la tabla de símbolos
    public void agregarVariable(String nombre, String tipo) {
        if (buscarSimboloEnContextoActual(nombre) != null) {
            throw new RuntimeException("La variable '" + nombre + "' ya está declarada en el contexto actual.");
        }
        Simbolo simbolo = new Simbolo(nombre, tipo, "variable");
        contextos.peek().put(nombre, simbolo);
    }

    // Agregar una función a la tabla de símbolos
    public void agregarFuncion(String nombre, String tipo, List<String> parametros) {
        if (buscarSimboloEnContextoActual(nombre) != null) {
            throw new RuntimeException("La función '" + nombre + "' ya está declarada en el contexto actual.");
        }
        Simbolo simbolo = new Simbolo(nombre, tipo, "funcion", parametros);
        contextos.peek().put(nombre, simbolo);
    }

    // Buscar un símbolo en todos los contextos (comenzando por el más anidado)
    public Simbolo buscarSimbolo(String nombre) {
        for (int i = contextos.size() - 1; i >= 0; i--) {
            if (contextos.get(i).containsKey(nombre)) {
                return contextos.get(i).get(nombre);
            }
        }
        return null;
    }

    // Buscar un símbolo solo en el contexto actual
    public Simbolo buscarSimboloEnContextoActual(String nombre) {
        return contextos.peek().get(nombre);
    }

    public boolean existeEnContextoActual(String nombre) {
        return contextos.peek().containsKey(nombre);
    }

    public List<Simbolo> getVariablesEnContextoActual() {
        return new ArrayList<>(contextos.peek().values());
    }

    public String getTipoDeSimbolo(String nombre) {
        Simbolo simbolo = buscarSimbolo(nombre);
        return (simbolo != null) ? simbolo.tipo : null;
    }

    public List<Simbolo> getFuncionesNoUsadas() {
        List<Simbolo> funciones = new ArrayList<>();
        for (Map<String, Simbolo> contexto : contextos) {
            for (Simbolo simbolo : contexto.values()) {
                if (simbolo.categoria.equals("funcion") && !simbolo.usada && !simbolo.nombre.equals("main")) {
                    funciones.add(simbolo);
                }
            }
        }
        return funciones;
    }

    public void actualizarFuncion(String nombre, List<String> parametros) {
        for (Map<String, Simbolo> contexto : contextos) {
            if (contexto.containsKey(nombre)) {
                Simbolo funcion = contexto.get(nombre);
                if (funcion.categoria.equals("funcion")) {
                    funcion.parametros = parametros;
                }
                return;
            }
        }
    }

    // Clase para representar un símbolo (variable o función)
    public static class Simbolo {
        public String nombre;
        public String tipo;
        public String categoria; // "variable" o "funcion"
        public List<String> parametros;
        public boolean inicializada;
        public boolean usada;
        

        public Simbolo(String nombre, String tipo, String categoria) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.categoria = categoria;
            this.parametros = new ArrayList<>();
            this.inicializada = false;
            this.usada = false;
        }

        public Simbolo(String nombre, String tipo, String categoria, List<String> parametros) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.categoria = categoria;
            this.parametros = parametros;
            this.inicializada = false;
            this.usada = false;
        }

        @Override
        public String toString() {
            return "Simbolo{" +
                    "nombre='" + nombre + '\'' +
                    ", tipo='" + tipo + '\'' +
                    ", categoria='" + categoria + '\'' +
                    ", parametros=" + parametros +
                    ", usada =" + usada +
                    '}';
        }

        
    }

    public void almacenarEstadoTabla() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== Estado de la Tabla de Símbolos ==========\n");
    
        int nivel = contextos.size();
        for (int i = contextos.size() - 1; i >= 0; i--) {
            sb.append(" Contexto: ").append(nivel - i).append(" {\n");
            for (Simbolo simbolo : contextos.get(i).values()) {
                sb.append("   ").append(simbolo).append("\n");
            }
            sb.append("}\n");
        }
        sb.append("========================================\n");
    
        // Guardamos el estado en la lista en vez de imprimirlo de inmediato
        historialTabla.add(sb.toString());
    }
    
    
    // Método para imprimir toda la tabla de símbolos al final
    public void imprimirHistorialTabla() {
        for (String estado : historialTabla) {
            System.out.println(estado);
        }
    }
}