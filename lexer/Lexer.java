package lexer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Lexer {

    public static int line = 1;
    private char ch = ' ';
    private FileReader file;
    private Hashtable<String, Token> words = new Hashtable<String, Token>();

    public Lexer(String fileName) throws Exception {
        try {
            file = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }
        reserveAllDisneyReservedTokens();

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

            // strings/literais
            case '"':
                StringBuffer sb = new StringBuffer();
                do {
                    if (this.ch != '\n')
                        sb.append(this.ch);

                    readch();
                } while (this.ch != '"');

                sb.append(this.ch);
                readch();

                String lexema = sb.toString();

                Token t = words.get(lexema);

                if (t != null)
                    return t; // palavra já existe na HashTable

                t = new Word(lexema, Tag.LITERAL);
                words.put(lexema, t);

                return t;
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
                        if (readch('.')) {
                            System.out.println(this.ch);
                            sb.append(this.ch);
                            readch();

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
                readch();
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

        readch();
        return new Word(Character.toString(this.ch), Tag.NONE);
    }

    private void reserve(Word word) {
        words.put(word.getLexeme(), word);
    }

    private void reserveAllDisneyReservedTokens() {
        reserve(Word.CLASS);
        reserve(Word.INT);
        reserve(Word.STRING);
        reserve(Word.FLOAT);
        reserve(Word.INIT);
        reserve(Word.STOP);
        reserve(Word.IF);
        reserve(Word.ELSE);
        reserve(Word.DO);
        reserve(Word.WHILE);
        reserve(Word.READ);
        reserve(Word.WRITE);
        reserve(Word.NOT);
        reserve(Word.GREATER_THAN);
        reserve(Word.GREATER_EQUAL);
        reserve(Word.LESS_THAN);
        reserve(Word.LESS_EQUAL);
        reserve(Word.DIF);
        reserve(Word.EQUALS);
        reserve(Word.PLUS);
        reserve(Word.SUBTRACT);
        reserve(Word.OR);
        reserve(Word.MULT);
        reserve(Word.DIVIDE);
        reserve(Word.AND);
        reserve(Word.ASSIGN);
        reserve(Word.SEMICOLON);
        reserve(Word.COMMA);
        reserve(Word.LEFT_PAREN);
        reserve(Word.RIGHT_PAREN);
        reserve(Word.BRACKET_LEFT);
        reserve(Word.BRACKET_RIGHT);
        reserve(Word.DOT);
        reserve(Word.DONE);
    }

    private void readch() throws IOException {
        int f = file.read();
        this.ch = (char) f;
    }

    private boolean readch(char c) throws IOException {
        readch();
        if (this.ch != c)
            return false;
        this.ch = ' ';
        return true;
    }

}
