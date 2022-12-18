import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Analizador extends JFrame {
    private JButton seleccionarButton;
    private JPanel panel1;
    private JTextArea textAreaCodigo;
    private JButton analizarButton;
    private JTextArea textAreaLexico;
    private JTextArea textAreaError;
    public Analizador() {
        super("Analizador");
        setContentPane(panel1);
        seleccionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seleccionarButtonActionPerformed(e);
            }
        });


        analizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                analizarButtonActionPerformed(e);
            }
        });
    }
    JFileChooser files=new JFileChooser();
    private void seleccionarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        File archivo;
        String texto;

        files.showOpenDialog(textAreaCodigo);
        archivo=new File(files.getSelectedFile().getAbsolutePath());
        try {
            texto=new String(Files.readAllBytes(archivo.toPath()));
            textAreaCodigo.setText(texto);

        }
        catch(FileNotFoundException ex){
            Logger.getLogger(Analizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(Analizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex){
            System.out.println("No selecciono archivo");
        }
    }

    private void analizarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String cadena=textAreaCodigo.getText();
        Syntactic sintactico = new Syntactic(new LexerCup(new StringReader(cadena)));
        try {
            sintactico.parse();
            textAreaError.setText("Analisis Exitoso");
            textAreaError.setForeground(Color.BLUE);
        }
        catch (Exception ex){
            java_cup.runtime.Symbol simbolo = sintactico.getS();
            textAreaError.setText("Error encontrado en linea " + (simbolo.right+1) +
                    " columna " + (simbolo.left+1) + " valor : " + (simbolo.value));
            textAreaError.setForeground(Color.RED);
        }


        Lexer lexer;
        String resultados;
        try{
            lexer = new Lexer(new StringReader(cadena));
            resultados = "";
            while(true){
                Tokens tokens = lexer.yylex();
                if(tokens == null){
                    resultados = resultados + "FIN";
                    textAreaLexico.setText(resultados);
                    return;
                }
                switch (tokens){
                    case ERROR: resultados = resultados + "NO_DEFINIDO\n";
                        break;
                    case Reservadas:
                    case Identificador:
                    case Numero:
                    case Igual:
                    case Suma:
                    case Resta:
                    case Multiplicacion:
                    case Division:
                    case Parentesis:
                    case Coma:
                        resultados = resultados + "{ "+ lexer.lexeme + " : " + tokens + " }\n";
                        break;
                    default:
                        resultados = resultados + " Token: " + tokens + " }\n";
                        break;
                }
            }
        } catch (FileNotFoundException ex){
        } catch (IOException ex){
        }
        catch (NullPointerException ex){
            System.out.println("No selecciono archivo");
        }
    }
}
