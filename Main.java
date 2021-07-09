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
    }
}