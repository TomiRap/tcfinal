package tc;

import java.util.*;

public class Escucha extends compiladorBaseListener {
    private final TablaSimbolos tablaSimbolos;
    private final List<String> errores = new ArrayList<>();


    public Escucha() {
        tablaSimbolos = new TablaSimbolos();
    }

    // Al entrar en un bloque, agregamos un nuevo contexto
    @Override
    public void enterBloque(compiladorParser.BloqueContext ctx) {
        tablaSimbolos.agregarContexto();
        System.out.println("\n Entrando a nuevo contexto ");
        //tablaSimbolos.imprimirTablaSimbolos();
    }

    // Al salir de un bloque, eliminamos el contexto actual
    @Override
    public void exitBloque(compiladorParser.BloqueContext ctx) {
        System.out.println("\n Saliendo del contexto ");
        // Verificar variables no usadas ANTES de eliminar el contexto
        for (TablaSimbolos.Simbolo simbolo : tablaSimbolos.getVariablesEnContextoActual()) {
            if (!simbolo.usada) {
                System.err.println("Warning en linea " + ctx.getStart().getLine() +
                                " La variable '" + simbolo.nombre + "' ha sido declarada pero no utilizada");
            }
        }
        tablaSimbolos.almacenarEstadoTabla(); // Guardamos el estado antes de eliminar el contexto
        tablaSimbolos.eliminarContexto();
    }

    // Declaracion de variables
    @Override
    public void enterDeclaracion(compiladorParser.DeclaracionContext ctx) {
        String tipo = ctx.tipo().getText();
        String nombre = ctx.ID().getText();
        boolean inicializada = (ctx.expr() != null);
    
        if (tablaSimbolos.existeEnContextoActual(nombre)) {
            errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                        " La variable '" + nombre + "' ya esta declarada el contexto");
            return;
        }
        
    
        if (ctx.expr() != null) {  // Verificar tipo si hay asignacion en la declaracion
            String tipoValor = obtenerTipoDeExpresion(ctx.expr());
            if (!esCompatible(tipo, tipoValor)) {
                errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                            " Asignacion incompatible: Se esperaba '" + tipo +
                            "', pero se asigno '" + tipoValor + "'.");
                return;
            }
        }
    
        tablaSimbolos.agregarVariable(nombre, tipo);
        TablaSimbolos.Simbolo variable = tablaSimbolos.buscarSimbolo(nombre);
        variable.inicializada = inicializada;
    }

    @Override
    public void enterAsignacion(compiladorParser.AsignacionContext ctx) {
        String nombre = ctx.ID().getText();
        TablaSimbolos.Simbolo variable = tablaSimbolos.buscarSimbolo(nombre);
        if (variable == null) {
            errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                        " La variable '" + nombre + "' no ha sido declarada.");
            return;
        }
    
        
    
        // Obtener el tipo esperado de la variable
        String tipoEsperado = variable.tipo;

        // Verificar el tipo del valor asignado
        if (ctx.expr() != null) {
            String tipoValor = obtenerTipoDeExpresion(ctx.expr());
            if (!esCompatible(tipoEsperado, tipoValor)) {
                errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                            " Asignacion incompatible: Se esperaba '" + tipoEsperado +
                            "', pero se asigno '" + tipoValor + "'.");
            }
        }

        variable.inicializada = true; // Marcar como inicializada
    }
    
    private String obtenerTipoDeExpresion(compiladorParser.ExprContext ctx) {
        if (ctx.NUMERO() != null) {
            return ctx.getText().contains(".") ? "float" : "int";
        } else if (ctx.STRING() != null) {
            return "string";
        } else if (ctx.ID() != null) {
            return tablaSimbolos.getTipoDeSimbolo(ctx.ID().getText());
        } else if (ctx.funcionCall() != null) {  
            String nombreFuncion = ctx.funcionCall().ID().getText();
            TablaSimbolos.Simbolo funcion = tablaSimbolos.buscarSimbolo(nombreFuncion);
    
            if (funcion != null && funcion.categoria.equals("funcion")) {
                return funcion.tipo;
            } else {
                errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                            " La funcion '" + nombreFuncion + "' no ha sido declarada antes de su uso.");
                return "desconocido";
            }
        } else if (ctx.op != null) {  
            String izquierda = obtenerTipoDeExpresion(ctx.expr(0));
            String derecha = obtenerTipoDeExpresion(ctx.expr(1));
    
            if (!esCompatible(izquierda, derecha)) {
                return "desconocido";
            }
            return izquierda.equals("float") || derecha.equals("float") ? "float" : "int";
        }
        return "desconocido";
    }
    
    

    
    private boolean esCompatible(String esperado, String real) {
        if (esperado.equals(real)) {
            return true;
        }
        return (esperado.equals("float") && real.equals("int")); // Permitir conversion implicita de int a float
    }

    
    @Override
    public void enterExpr(compiladorParser.ExprContext ctx) {
        if (ctx.ID() != null) {
            String nombre = ctx.ID().getText();
            TablaSimbolos.Simbolo variable = tablaSimbolos.buscarSimbolo(nombre);
    
            if (variable == null) {
                errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                            " La variable '" + nombre + "' no ha sido declarada.");
                return;
            }
    
            if (!variable.inicializada) {
                errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                            " La variable '" + nombre + "' no ha sido inicializada antes de su uso.");
            }
    
            // Marcar la variable como utilizada
            variable.usada = true;
        }
    }

    @Override
    public void enterFuncionDecl(compiladorParser.FuncionDeclContext ctx) {
        String tipo = ctx.tipo().getText();
        String nombre = ctx.ID().getText();
        List<String> parametros = new ArrayList<>();
    
        // REGISTRAR LA FUNCIoN **ANTES** DE AGREGAR EL CONTEXTO
        tablaSimbolos.agregarFuncion(nombre, tipo, parametros);
    
        // Agregar un nuevo contexto para la funcion
        tablaSimbolos.agregarContexto();
    
        if (ctx.parametros() != null) {
            for (var parametroCtx : ctx.parametros().parametro()) {
                String paramTipo = parametroCtx.tipo().getText();
                String paramNombre = parametroCtx.ID().getText();
                parametros.add(paramTipo);
    
                tablaSimbolos.agregarVariable(paramNombre, paramTipo);
                TablaSimbolos.Simbolo variable = tablaSimbolos.buscarSimbolo(paramNombre);
                variable.inicializada = true;
            }
        }
    
        // Actualizar la funcion con la lista de parametros
        tablaSimbolos.actualizarFuncion(nombre, parametros);
    }
    
    
    @Override
    public void exitFuncionDecl(compiladorParser.FuncionDeclContext ctx) {
        tablaSimbolos.eliminarContexto();
    } 

    @Override
    public void enterFuncionCall(compiladorParser.FuncionCallContext ctx) {
        String nombreFuncion = ctx.ID().getText();
        TablaSimbolos.Simbolo funcion = tablaSimbolos.buscarSimbolo(nombreFuncion);

        if (funcion == null || !funcion.categoria.equals("funcion")) {
            errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                        " → La funcion '" + nombreFuncion + "' no esta declarada.");
            return;
        }

        // Marcar los parametros como inicializados al llamar la funcion
        if (ctx.argumentos() != null) {
            List<String> parametrosEsperados = funcion.parametros;
            List<compiladorParser.ExprContext> argumentos = ctx.argumentos().expr();

            if (parametrosEsperados.size() != argumentos.size()) {
                errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                            " → Número incorrecto de argumentos en la llamada a '" + nombreFuncion + "'.");
                return;
            }

            for (int i = 0; i < parametrosEsperados.size(); i++) {
                String tipoEsperado = parametrosEsperados.get(i);
                String tipoArgumento = obtenerTipoDeExpresion(argumentos.get(i));

                if (!esCompatible(tipoEsperado, tipoArgumento)) {
                    errores.add("Error semantico en linea " + ctx.getStart().getLine() +
                                " → Tipo incorrecto en el argumento " + (i+1) + 
                                " de la llamada a '" + nombreFuncion + "'. Se esperaba '" + 
                                tipoEsperado + "', pero se recibio '" + tipoArgumento + "'.");
                }
            }
        }

        funcion.usada = true;
    }

    @Override
    public void exitPrograma(compiladorParser.ProgramaContext ctx) {
        for (TablaSimbolos.Simbolo funcion : tablaSimbolos.getFuncionesNoUsadas()) {
            System.err.println(" Warning en linea " + ctx.getStart().getLine() +
                               "  La funcion '" + funcion.nombre + "' fue declarada pero no utilizada.");
        }
        if (!errores.isEmpty()) {
            System.err.println("\n Errores encontrados:");
            for (String error : errores) {
                System.err.println(error);
            }
            System.exit(1);  // Salir con codigo de error
        }else{
            System.out.println("\n Resumen final de la Tabla de Simbolos:");
            tablaSimbolos.almacenarEstadoTabla(); // Guardamos el estado final antes de imprimir
            tablaSimbolos.imprimirHistorialTabla();
        }
    }
}