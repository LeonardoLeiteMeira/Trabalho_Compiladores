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

    public Lexer(String fileName) throws FileNotFoundException{
        try{
            file = new FileReader (fileName);
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
            throw e;
        }
        reserveAllDisneyReservedTokens();
    }

    public Token scan() throws IOException {
        
        for (;; readch()) {
            if (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\b') continue;
            else if (ch == '\n') line++; 
            else break;
        }

        switch(ch) {
            case '!':
                if(readch('=')) return Word.DIF;
                return Word.NOT;
            case '+':
                return Word.PLUS;
                
            case '>':
                if(readch('=')) return Word.GREATER_EQUAL;
                return Word.GREATER_THAN;
                
            case '<':
                if(readch('=')) return Word.LESS_EQUAL;
                return Word.LESS_THAN;
                
            case '-':
                return Word.SUBTRACT;
                
            case '|':
                if(readch('|')) return Word.OR;
                
            case '/':
                return Word.DIVIDE;
                
            case '*':
                return Word.MULT;
                
            case '&':
                if(readch('&')) return Word.AND;
                break;
                
            case '=':
                if(readch('=')) return Word.EQUALS;
                return Word.ASSIGN;
                
            case ',':
                return Word.COMMA;          
                
            case ';':
                return Word.SEMICOLON;
                
            case '(':
                return Word.LEFT_PAREN;
                
            case ')':
                return Word.RIGHT_PAREN;                
                
            case '{':
                return Word.BRACKET_LEFT;
                
            case '}':
                return Word.BRACKET_RIGHT;
                
            case '.':
                return Word.DOT;
                
            case '"':
                return Word.QUOTE;
        }

        if (Character.isDigit(ch)) {
            int value = 0;

            do {
                value = 10*value + Character.digit(ch, 10);
                readch();
            } while(Character.isDigit(ch));
            return new Num(value);
        }

        //Identificadores
        if (Character.isLetter(ch)){
            StringBuffer sb = new StringBuffer();
            do{
                sb.append(ch);
                readch();
            }while(Character.isLetterOrDigit(ch));

            String s = sb.toString();
            Word w = (Word)words.get(s);
            if (w != null) return w; //palavra já existe na HashTable
            w = new Word (s, Tag.ID);
            words.put(s, w);
            return w;
        }

        //Caracteres não especificados
        Token t = new Token(ch);
        ch = ' ';
        return t;
    }


    private void reserve(Word word){
        words.put(word.getLexeme(), word);
    }


    private void reserveAllDisneyReservedTokens() {
        reserve(new Word ("class", Tag.CLASS));
        reserve(new Word ("do", Tag.DO));
        reserve(new Word ("else", Tag.ELSE));
        reserve(new Word ("float", Tag.FLOAT));
        reserve(new Word ("if", Tag.IF));
        reserve(new Word ("init", Tag.INIT));
        reserve(new Word ("int", Tag.INT));
        reserve(new Word ("read", Tag.READ));
        reserve(new Word ("stop", Tag.STOP));
        reserve(new Word ("string", Tag.STRING));
        reserve(new Word ("while", Tag.WHILE));
        reserve(new Word ("write", Tag.WRITE));
    }

    
    private void readch() throws IOException {
        ch = (char) file.read();
    }
    
    private boolean readch(char c) throws IOException {
        readch();
        if (ch != c) return false;
        ch = ' ';
        return true;
    }

}
