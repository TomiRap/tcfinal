package tc;

import java.util.*;

public class Caminante extends compiladorBaseVisitor<String> {
    private final List<String> codigoIntermedio = new ArrayList<>();
    private int tempCount = 0;
    private int etiquetaCount = 0;
    private final Stack<String> contextoActual = new Stack<>();
    private final Map<String, Integer> contadorVariables = new HashMap<>();

    private String nuevaEtiqueta() {
        return "L" + etiquetaCount++;
    }

    private String nuevaTemporal() {
        return "t" + tempCount++;
    }

    public List<String> getCodigoIntermedio() {
        return new ArrayList<>(codigoIntermedio);
    }

    @Override
    public String visitPrograma(compiladorParser.ProgramaContext ctx) {
        inicializarGeneracion();
        Set<String> variablesGlobales = procesarDeclaracionesGlobales(ctx);
        procesarFunciones(ctx.funcionDecl());
        generarMain();
        procesarInstruccionesMain(ctx.instruccion(), variablesGlobales);
        return null;
    }

    private void inicializarGeneracion() {
        System.out.println("Iniciando generacion de codigo ");
        contextoActual.push("global");
    }

    private Set<String> procesarDeclaracionesGlobales(compiladorParser.ProgramaContext ctx) {
        Set<String> globales = new HashSet<>();
        ctx.instruccion().stream()
            .filter(inst -> inst.declaracion() != null)
            .forEach(inst -> {
                String var = inst.declaracion().ID().getText();
                globales.add(var);
                visit(inst.declaracion());
            });
        return globales;
    }

    private void procesarFunciones(List<compiladorParser.FuncionDeclContext> funciones) {
        funciones.forEach(this::visit);
    }

    private void generarMain() {
        if (!codigoIntermedio.contains("main:")) {
            codigoIntermedio.add("main:");
            System.out.println("Generando etiqueta main ");
        }
    }

    private void procesarInstruccionesMain(List<compiladorParser.InstruccionContext> instrucciones, Set<String> globales) {
        instrucciones.forEach(inst -> {
            if (inst.declaracion() != null) {
                String var = inst.declaracion().ID().getText();
                if (!globales.contains(var)) {
                    visit(inst);
                }
            } else {
                visit(inst);
            }
        });
    }

    @Override
    public String visitExpr(compiladorParser.ExprContext ctx) {
        if (ctx.op != null) {
            return procesarOperacion(ctx);
        }
        return obtenerValorTerminal(ctx);
    }

    private String procesarOperacion(compiladorParser.ExprContext ctx) {
        String izq = visit(ctx.expr(0));
        String der = visit(ctx.expr(1));
        String temp = nuevaTemporal();
        String operacion = temp + " = " + izq + " " + ctx.op.getText() + " " + der;
        codigoIntermedio.add(operacion);
        System.out.println("Codigo generado: " + operacion);
        return temp;
    }

    private String obtenerValorTerminal(compiladorParser.ExprContext ctx) {
        if (ctx.ID() != null) return ctx.ID().getText();
        if (ctx.NUMERO() != null) return ctx.NUMERO().getText();
        return null;
    }

    @Override
    public String visitFuncionCall(compiladorParser.FuncionCallContext ctx) {
        List<String> params = procesarParametros(ctx);
        return generarLlamadaFuncion(ctx.ID().getText(), params);
    }

    private List<String> procesarParametros(compiladorParser.FuncionCallContext ctx) {
        List<String> params = new ArrayList<>();
        if (ctx.argumentos() != null) {
            ctx.argumentos().expr().forEach(arg -> {
                String valor = visit(arg);
                params.add(valor);
                codigoIntermedio.add("param " + valor);
            });
        }
        return params;
    }

    private String generarLlamadaFuncion(String nombre, List<String> params) {
        String temp = nuevaTemporal();
        String llamada = temp + " = call " + nombre + ", " + params.size();
        codigoIntermedio.add(llamada);
        System.out.println(" Llamada generada: " + llamada);
        return temp;
    }

    @Override
    public String visitFuncionDecl(compiladorParser.FuncionDeclContext ctx) {
        String nombre = ctx.ID().getText();
        contextoActual.push(nombre);
        codigoIntermedio.add(nombre + ":");
        System.out.println(" Funcion generada: " + nombre);
        visit(ctx.bloque());
        contextoActual.pop();
        return null;
    }

    @Override
    public String visitAsignacion(compiladorParser.AsignacionContext ctx) {
        System.out.println("Generando codigo para asignacion ");
        String var = ctx.ID().getText();
        String valor = visit(ctx.expr());
        if (valor != null) {
            String asignacion = var + " = " + valor;
            codigoIntermedio.add(asignacion);
            System.out.println(" Asignacion generada: " + asignacion);
        }
        return null;
    }

    @Override
    public String visitDeclaracion(compiladorParser.DeclaracionContext ctx) {
        String var = ctx.ID().getText();
        String valor = procesarValorDeclaracion(ctx);
        String declaracion = var + " = " + valor;
        codigoIntermedio.add(declaracion);
        System.out.println(" Declaracion generada: " + declaracion);
        return null;
    }

    private String procesarValorDeclaracion(compiladorParser.DeclaracionContext ctx) {
        if (ctx.expr() != null) {
            if (ctx.expr().getChild(0) instanceof compiladorParser.FuncionCallContext) {
                return visitFuncionCall((compiladorParser.FuncionCallContext) ctx.expr().getChild(0));
            }
            return visit(ctx.expr());
        }
        return "null";
    }

    @Override
    public String visitReturnStmt(compiladorParser.ReturnStmtContext ctx) {
        String valor = visit(ctx.expr());
        String retorno = "return " + valor;
        codigoIntermedio.add(retorno);
        System.out.println(" Return generado: " + retorno);
        return valor;
    }

    @Override
    public String visitIfStmt(compiladorParser.IfStmtContext ctx) {
        return generarCodigoCondicional(ctx);
    }

    private String generarCodigoCondicional(compiladorParser.IfStmtContext ctx) {
        String cond = visit(ctx.expr());
        String etiqs = generarEtiquetasControl();
        generarBloquesControl(ctx, cond, etiqs);
        return null;
    }

    private String generarEtiquetasControl() {
        return nuevaEtiqueta() + ":" + nuevaEtiqueta() + ":" + nuevaEtiqueta();
    }

    private void generarBloquesControl(compiladorParser.IfStmtContext ctx, String cond, String etiquetas) {
        String[] etiq = etiquetas.split(":");
        codigoIntermedio.add("if " + cond + " goto " + etiq[0]);
        codigoIntermedio.add("goto " + etiq[1]);
        codigoIntermedio.add(etiq[0] + ":");
        visit(ctx.bloque(0));
        codigoIntermedio.add("goto " + etiq[2]);
        codigoIntermedio.add(etiq[1] + ":");
        if (ctx.bloque().size() > 1) visit(ctx.bloque(1));
        codigoIntermedio.add(etiq[2] + ":");
    }

    @Override
    public String visitWhileStmt(compiladorParser.WhileStmtContext ctx) {
        String inicio = nuevaEtiqueta();
        String fin = nuevaEtiqueta();
        generarBucleWhile(ctx, inicio, fin);
        return null;
    }

    private void generarBucleWhile(compiladorParser.WhileStmtContext ctx, String inicio, String fin) {
        codigoIntermedio.add(inicio + ":");
        String cond = visit(ctx.expr());
        codigoIntermedio.add("if not " + cond + " goto " + fin);
        visit(ctx.bloque());
        codigoIntermedio.add("goto " + inicio);
        codigoIntermedio.add(fin + ":");
    }

    @Override
    public String visitForStmt(compiladorParser.ForStmtContext ctx) {
        return generarBucleFor(ctx);
    }

    private String generarBucleFor(compiladorParser.ForStmtContext ctx) {
        String inicio = nuevaEtiqueta();
        String fin = nuevaEtiqueta();
        
        if (ctx.declaracion() != null) visit(ctx.declaracion());
        codigoIntermedio.add(inicio + ":");
        
        if (ctx.expr() != null) {
            String cond = visit(ctx.expr());
            codigoIntermedio.add("if not " + cond + " goto " + fin);
        }
        
        visit(ctx.bloque());
        if (ctx.incrementoDecremento() != null) visit(ctx.incrementoDecremento());
        codigoIntermedio.add("goto " + inicio);
        codigoIntermedio.add(fin + ":");
        return null;
    }

    @Override
    public String visitIncrementoDecremento(compiladorParser.IncrementoDecrementoContext ctx) {
        String var = ctx.ID().getText();
        boolean esIncremento = ctx.getChild(1).getText().equals("++");
        String temp = nuevaTemporal();
        codigoIntermedio.add(temp + " = " + var + (esIncremento ? " + 1" : " - 1"));
        codigoIntermedio.add(var + " = " + temp);
        return null;
    }

    public void guardarCodigoIntermedio() {
        if (codigoIntermedio.isEmpty()) {
            System.err.println("ERROR: No se ha generado codigo intermedio.");
            return;
        }
        System.out.println("\n Codigo intermedio generado:");
        codigoIntermedio.forEach(System.out::println);
    }
}