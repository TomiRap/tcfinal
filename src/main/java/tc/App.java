package tc;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class App {
    private static final String RUTA_ARCHIVO = "C:\\Users\\torus\\Desktop\\tcfinal\\src\\main\\java\\tc\\input.txt";
    private static final String RUTA_CODIGO_OPTIMIZADO = "C:\\Users\\torus\\Desktop\\tcfinal\\src\\main\\java\\tc\\codigo_optimizado.txt";

    public static void main(String[] args) {
        try {
            procesarCompilacion();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error durante el análisis: " + e.getMessage());
        }
    }

    private static void procesarCompilacion() throws IOException {
        CharStream input = CharStreams.fromFileName(RUTA_ARCHIVO);
        compiladorLexer lexer = new compiladorLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        compiladorParser parser = new compiladorParser(tokens);

        configurarErrorListener(parser);
        ParseTree tree = ejecutarAnalisis(parser);
        ejecutarSintesis(tree);
    }

    private static void configurarErrorListener(compiladorParser parser) {
        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine, String msg, RecognitionException e) {
                String mensajeError = msg;
                if (msg.contains("missing ';'")) {
                    mensajeError = "Error sintáctico en línea " + line + ":" + charPositionInLine + " Falta un punto y coma";
                } else if (msg.contains("missing '('")) {
                    mensajeError = "Error sintáctico en línea " + line + ":" + charPositionInLine + " Falta apertura de paréntesis";
                } else if (msg.contains("extraneous input")) {
                    mensajeError = "Error sintáctico en línea " + line + ":" + charPositionInLine + " La declaración es incorrecta";
                } else if (msg.contains("no viable alternative at input")) {
                    mensajeError = "Error sintáctico en línea " + line + ":" + charPositionInLine + " Estructura inválida o falta apertura de paréntesis";
                }
                throw new RuntimeException(mensajeError);
            }
        });
    }

    private static ParseTree ejecutarAnalisis(compiladorParser parser) {
        ParseTree tree = parser.programa();
        ParseTreeWalker walker = new ParseTreeWalker();
        Escucha escucha = new Escucha();
        walker.walk(escucha, tree);
        return tree;
    }

    private static void ejecutarSintesis(ParseTree tree) {
        System.out.println("Inicio de generación de código intermedio");
        Caminante caminante = new Caminante();
        caminante.visit(tree);
        System.out.println("Finaliza el recorrido del árbol.");
        caminante.guardarCodigoIntermedio();
        List<String> codigoIntermedio = caminante.getCodigoIntermedio();

        System.out.println("Inicio de proceso de optimización");
        OptimizadorCodigo optimizador = new OptimizadorCodigo();
        List<String> codigoOptimizado = optimizador.optimizarCodigo(codigoIntermedio);
        optimizador.imprimirCodigoOptimizado();
        guardarCodigoOptimizado(codigoOptimizado);
        System.out.println("Finalización de Optimización");
        System.out.println("Ejecución completada sin errores");
    }

    private static void guardarCodigoOptimizado(List<String> codigoOptimizado) {
        try {
            Files.write(Path.of(RUTA_CODIGO_OPTIMIZADO), codigoOptimizado, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Código optimizado guardado en: " + RUTA_CODIGO_OPTIMIZADO);
        } catch (IOException e) {
            System.err.println("Error al guardar el código optimizado: " + e.getMessage());
        }
    }
}
