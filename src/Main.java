import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        //generar();
        //generarLexer();
        checkParser();
    }
    public static void checkParser(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame AF1 = new Analizador();
                AF1.setSize(1000,600);
                AF1.setVisible(true);
            }
        });

    }

    public static void generarLexer() {
        String ruta = "C:\\Users\\luism\\IdeaProjects\\Lab02\\src\\entrada.jflex";
        File archivo = new File(ruta);
        JFlex.Main.generate(archivo);
    }

    public static void generar() throws IOException, Exception{
        String path0="C:\\Users\\luism\\IdeaProjects\\Lab02\\";
        String path=path0+"src\\";
        String rutaC=path+"entradaCup.jflex";
        String fileG="Syntactic.java";
        String[] rutaS={"-parser", "Syntactic", path+"Grammar.cup"};
        File archivo= new File(rutaC);
        JFlex.Main.generate(archivo);
        System.out.println("Fin Lexico");
        java_cup.Main.main(rutaS);
        System.out.println("Ruta---");
        Path rutaSym = Paths.get(path+"sym.java");
        if(Files.exists(rutaSym)){
            Files.delete(rutaSym);
        }
        Files.move(Paths.get(path0+"sym.java"), Paths.get(path+"sym.java"));
        Path rutaSin = Paths.get(path+fileG);
        if(Files.exists(rutaSin)){
            Files.delete(rutaSin);
        }
        Files.move(Paths.get(path0+fileG), Paths.get(path+fileG));
    }
}