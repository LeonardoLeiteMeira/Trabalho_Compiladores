package lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer {

    public static int line = 1;
    public boolean EOF = false;
    private char ch = ' ';
    private FileReader fileReader;
    private Hashtable<String, Token> words = new Hashtable<String, Token>();

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

            // lembrar de considerar EOF
            case '/':
                readChar();
                if (this.ch == '/') {
                    do {
                        readChar();
                    } while (this.ch != '\n');

                    return null;
                }

                if (this.ch == '*') {
                    while (true) {
                        readChar();

                        if (this.ch == '*') {
                            if (readAhead('/')) {
                                return null;
                            }
                        }

                    }
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
            switch (this.ch) {
                case '0':
                    return new Int(0);

                default:
                    double value = 0;
                    sb = new StringBuffer();
                    do {
                        value = 10 * value + Character.digit(this.ch, 10);
                        readChar();
                    } while (Character.isDigit(this.ch));

                    if (this.ch == '.') {
                        readChar();

                        if (!Character.isDigit(this.ch))
                            throw new Error("Erro léxico na linha " + Lexer.line);

                        int iter = 1;
                        do {
                            value = value + Math.pow(0.1, iter) * Character.digit(this.ch, 10);
                            readChar();
                            iter++;
                        } while (Character.isDigit(this.ch));

                        return new FloatValue((float) value);
                    }

                    return new Int((int) value);
            }
        }

        // Identificadores
        if (Character.isLetter(this.ch)) {
            sb = new StringBuffer();
            do {
                sb.append(this.ch);
                readChar();
            } while (Character.isLetterOrDigit(this.ch) || this.ch == '_');

            String id = sb.toString();
            Word w = (Word) words.get(id);

            if (w != null)
                return w; // palavra já existe na HashTable

            w = new Word(id, Tag.ID);

            words.put(id, w);
            return w;
        }

        Token t = new Token(this.ch);
        this.ch = ' ';
        return t;
    }

    private void reserve(Word t) {
        words.put(t.lexeme, t);
    }

    private void readChar() throws IOException {
        int c = fileReader.read();

        if (c == -1) {
            EOF = true;
        }

        this.ch = (char) c;
    }

    private boolean readAhead(char c) throws IOException {
        readChar();

        if (this.ch != c)
            return false;

        this.ch = ' ';
        return true;
    }

    public Hashtable<String, Token> getWords() {
        return words;
    }
}
