import java.io.FileNotFoundException;
import java.io.FileReader;

import lexer.*;

public class Main {
    public static void main(String args[]) throws Exception {
        FileReader fileReader;

        try {
            fileReader = new FileReader("./test/case1.disney");
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }

        Lexer lexer = new Lexer(fileReader);

        int i = 0;
        System.out.println("Analise lexica: ");
        System.out.println("Fluxo de Tokens: ");
        while (!lexer.EOF) {
            Token t = lexer.scan();

            if (t instanceof Word) {
                Word w = (Word) t;
                System.out.println("Token " + (i + 1) + ": " + w.lexeme);
            } else if (t instanceof Int) {
                Int intValue = (Int) t;
                System.out.println("Token " + (i + 1) + ": " + intValue.value);
            } else if (t instanceof FloatValue) {
                FloatValue f = (FloatValue) t;
                System.out.println("Token " + (i + 1) + ": " + f.value);
            } else {
                System.out.println("Token " + (i + 1) + ": " + t.tag);
            }

            i++;
        }

        System.out.println("\nTabela de simbolos depois da analise lexica: ");

        lexer.getWords().entrySet().forEach(entrada -> {
            System.out.print(entrada.getKey() + " -> ");

            if (entrada.getValue().tag == 35) {
                System.out.println("Identificador");
            } else
                System.out.println("Palavra Reservada");
        });
    }
}