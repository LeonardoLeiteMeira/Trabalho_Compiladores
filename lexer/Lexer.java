package lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer {

    public static int line = 1;
    private char ch = ' ';
    private FileReader fileReader;
    private Hashtable<String, Token> words = new Hashtable();

    public Lexer(FileReader fileReader) throws Exception {
        this.fileReader = fileReader;

        reserve(new Word("class", Tag.CLASS));
        reserve(new Word("int", Tag.INT));
        reserve(new Word("string", Tag.STRING));
        reserve(new Word("float", Tag.FLOAT));
        reserve(new Word("init", Tag.INIT));
        reserve(new Word("stop", Tag.STOP));
        reserve(new Word("if", Tag.IF));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("read", Tag.READ));
        reserve(new Word("write", Tag.WRITE));

        Token token = scan();
        // while (!token.toString().equals(Word.DONE.toString())) {
        // System.out.println(token.toString());
        // token = scan();
        // }
        // conferir pq nao esta parando no EOF
        int i = 0;
        while (i < 82) {
            token = scan();
            i++;
        }
    }

    public Token scan() throws IOException {

        StringBuffer sb;

        for (;; readChar()) {
            if (this.ch == ' ' || this.ch == '\t' || this.ch == '\r' || this.ch == '\b')
                continue;
            else if (this.ch == '\n')
                line++;
            else
                break;
        }

        switch (this.ch) {
            case '!':
                if (readAhead('=')) {
                    return Word.DIF;
                }

                return Word.NOT;

            case '>':
                if (readAhead('=')) {
                    return Word.GREATER_EQUAL;
                }

                return Word.GREATER_THAN;

            case '<':
                if (readAhead('=')) {
                    return Word.LESS_EQUAL;
                }

                return Word.LESS_THAN;

            case '=':
                if (readAhead('=')) {
                    return Word.EQUALS;
                }

                return Word.ASSIGN;

            case '+':
                readChar();
                return Word.PLUS;

            case '-':
                readChar();
                return Word.SUBTRACT;

            case '|':
                if (readAhead('|')) {
                    return Word.OR;
                }

                throw new Error("Erro léxico na linha " + Lexer.line);

            case '*':
                readChar();
                return Word.MULT;

            // pensar bem comentarios
            case '/':
                sb = new StringBuffer();

                if (readAhead('/')) {
                    do {
                        sb.append(this.ch);
                        readChar();
                    } while (this.ch != '\n');

                    return new Word(Character.toString(this.ch), Tag.NONE);
                }

                if (readAhead('*')) {
                    do {
                        sb.append(this.ch);
                        readChar();
                    } while (this.ch != '*');

                    if (readAhead('/')) {
                        sb.append(this.ch);
                        readChar();
                    }

                    return new Word(Character.toString(this.ch), Tag.NONE);
                }

                return Word.DIVIDE;

            case '&':
                if (readAhead('&')) {
                    return Word.AND;
                }

                throw new Error("Erro léxico na linha " + Lexer.line);

            case ',':
                readChar();
                return Word.COMMA;

            case ';':
                readChar();
                return Word.SEMICOLON;

            case '(':
                readChar();
                return Word.LEFT_PAREN;

            case ')':
                readChar();
                return Word.RIGHT_PAREN;

            case '{':
                readChar();
                return Word.BRACKET_LEFT;

            case '}':
                readChar();
                return Word.BRACKET_RIGHT;

            case '.':
                readChar();
                return Word.DOT;

            // literais
            case '"':
                sb = new StringBuffer();
                do {
                    if (this.ch == '\n')
                        throw new Error("Erro léxico na linha " + Lexer.line);

                    sb.append(this.ch);
                    readChar();
                } while (this.ch != '"');

                sb.append(this.ch);
                readChar();

                String literal = sb.toString();

                return new Word(literal, Tag.LITERAL);
        }

        // numeros
        if (Character.isDigit(this.ch)) {
            Num t;
            int type = Tag.INT;

            switch (this.ch) {
                case '0':
                    t = (Num) words.get("0");

                    if (t != null)
                        return t; // palavra já existe na HashTable

                    t = new Num("0", Tag.INT);

                    words.put("0", t);
                    return t;

                default:
                    StringBuffer sb = new StringBuffer();
                    do {
                        sb.append(this.ch);

                        // logica de verificacao do ponto cagada
                        if (readAhead('.')) {
                            System.out.println(this.ch);
                            sb.append(this.ch);
                            readChar();

                            if (!Character.isDigit(this.ch))
                                throw new Error();

                            type = Tag.FLOAT;
                        }
                    } while (Character.isDigit(this.ch));

                    String s = sb.toString();
                    t = (Num) words.get(s);

                    if (t != null)
                        return t; // palavra já existe na HashTable

                    t = new Num(s, type);

                    words.put(s, t);
                    return t;
            }
        }

        // Identificadores
        if (Character.isLetter(this.ch)) {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(this.ch);
                readChar();
            } while (Character.isLetterOrDigit(this.ch) || this.ch == '_');

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

        readChar();
        return new Word(Character.toString(this.ch), Tag.NONE);
    }

    private void reserve(Word t) {
        words.put(t.lexeme, t);
    }

    private void readChar() throws IOException {
        int c = fileReader.read();

        this.ch = (char) c;
    }

    private boolean readAhead(char c) throws IOException {
        readChar();

        if (this.ch != c)
            return false;

        this.ch = ' ';
        return true;
    }
}
