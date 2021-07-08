package lexer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer {

    public static int line = 1;
    private char ch = ' ';
    private FileReader file;
    private Hashtable<String, Word> words = new Hashtable<String, Word>();

    public Lexer(String fileName) throws Exception {
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }
        reserveAllDisneyReservedTokens();

        Token token = scan();
        while (!token.equals(Word.DONE)) {
            token = scan();
        }
        // conferir pq nao esta parando no EOF
        // int i = 0;
        // while (i < 85) {
        // token = scan();
        // i++;
        // }
    }

    public Token scan() throws IOException {

        for (;; readch()) {
            if (this.ch == ' ' || this.ch == '\t' || this.ch == '\r' || this.ch == '\b')
                continue;
            else if (this.ch == '\n')
                line++;
            else
                break;
        }

        switch (this.ch) {
            case '!':
                if (readch('=')) {
                    readch();
                    return Word.DIF;
                }
                readch();
                return Word.NOT;
            case '+':
                readch();
                return Word.PLUS;

            case '>':
                if (readch('=')) {
                    readch();
                    return Word.GREATER_EQUAL;
                }

                readch();
                return Word.GREATER_THAN;

            case '<':
                if (readch('=')) {
                    readch();
                    return Word.LESS_EQUAL;
                }

                readch();
                return Word.LESS_THAN;

            case '-':
                readch();
                return Word.SUBTRACT;

            case '|':
                if (readch('|')) {
                    readch();
                    return Word.OR;
                }

            case '/':
                readch();
                return Word.DIVIDE;

            case '*':
                readch();
                return Word.MULT;

            case '&':
                if (readch('&')) {
                    readch();
                    return Word.AND;
                }
                break;

            case '=':
                if (readch('=')) {
                    readch();
                    return Word.EQUALS;
                }
                readch();
                return Word.ASSIGN;

            case ',':
                readch();
                return Word.COMMA;

            case ';':
                readch();
                return Word.SEMICOLON;

            case '(':
                readch();
                return Word.LEFT_PAREN;

            case ')':
                readch();
                return Word.RIGHT_PAREN;

            case '{':
                readch();
                return Word.BRACKET_LEFT;

            case '}':
                readch();
                return Word.BRACKET_RIGHT;

            case '.':
                readch();
                return Word.DOT;

            case '"':
                readch();
                return Word.QUOTE;
        }

        if (Character.isDigit(this.ch)) {
            int value = 0;

            do {
                value = 10 * value + Character.digit(this.ch, 10);
                readch();
            } while (Character.isDigit(this.ch));
            return new Num(value);
        }

        // Identificadores
        if (Character.isLetter(this.ch)) {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(this.ch);
                readch();
            } while (Character.isLetterOrDigit(this.ch));

            String s = sb.toString();
            Word w = (Word) words.get(s);
            if (w != null)
                return w; // palavra já existe na HashTable
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }

        if (this.ch == -1) {
            return Word.DONE;
        }

        readch();
        return new Word(Character.toString(this.ch), Tag.NONE);
    }

    private void reserve(Word word) {
        words.put(word.getLexeme(), word);
    }

    private void reserveAllDisneyReservedTokens() {
        reserve(new Word("class", Tag.CLASS));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("float", Tag.FLOAT));
        reserve(new Word("if", Tag.IF));
        reserve(new Word("init", Tag.INIT));
        reserve(new Word("int", Tag.INT));
        reserve(new Word("read", Tag.READ));
        reserve(new Word("stop", Tag.STOP));
        reserve(new Word("string", Tag.STRING));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("write", Tag.WRITE));
    }

    private void readch() throws IOException {
        this.ch = (char) file.read();
        System.out.println(this.ch);
    }

    private boolean readch(char c) throws IOException {
        readch();
        if (this.ch != c)
            return false;
        this.ch = ' ';
        return true;
    }

}
