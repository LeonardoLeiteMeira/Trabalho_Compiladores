package lexer;

public class Word extends Token {
    private String lexeme;

    public static final Word CLASS = new Word("class", Tag.CLASS);
    public static final Word DO = new Word("do", Tag.DO);
    public static final Word ELSE = new Word("else", Tag.ELSE);
    public static final Word FLOAT = new Word("float", Tag.FLOAT);
    public static final Word IF = new Word("if", Tag.IF);
    public static final Word INIT = new Word("init", Tag.INIT);
    public static final Word INT = new Word("int", Tag.INT);
    public static final Word READ = new Word("read", Tag.READ);
    public static final Word STOP = new Word("stop", Tag.STOP);
    public static final Word STRING = new Word("string", Tag.STRING);
    public static final Word WHILE = new Word("while", Tag.WHILE);
    public static final Word WRITE = new Word("write", Tag.WRITE);
    public static final Word DIF = new Word("!=", Tag.DIF);
    public static final Word NOT = new Word("!=", Tag.NOT);
    public static final Word PLUS = new Word("!=", Tag.PLUS);
    public static final Word GREATER_EQUAL = new Word(">=", Tag.GREATER_EQUAL);
    public static final Word LESS_EQUAL = new Word("<=", Tag.LESS_EQUAL);
    public static final Word LESS_THAN = new Word("<", Tag.LESS_THAN);
    public static final Word GREATER_THAN = new Word(">", Tag.GREATER_THAN);
    public static final Word SUBTRACT = new Word("-", Tag.SUBTRACT);
    public static final Word OR = new Word("||", Tag.OR);
    public static final Word MULT = new Word("*", Tag.MULT);
    public static final Word DIVIDE = new Word("/", Tag.DIVIDE);
    public static final Word AND = new Word("&&", Tag.AND);
    public static final Word EQUALS = new Word("==", Tag.EQUALS);
    public static final Word ASSIGN = new Word("==", Tag.ASSIGN);
    public static final Word SEMICOLON = new Word(";", Tag.SEMICOLON);
    public static final Word COMMA = new Word(",", Tag.COMMA);
    public static final Word LEFT_PAREN = new Word("(", Tag.LEFT_PAREN);
    public static final Word RIGHT_PAREN = new Word("(", Tag.RIGHT_PAREN);
    public static final Word BRACKET_LEFT = new Word("{", Tag.BRACKET_LEFT);
    public static final Word BRACKET_RIGHT = new Word("}", Tag.BRACKET_RIGHT);
    public static final Word DOT = new Word(".", Tag.DOT);
    public static final Word QUOTE = new Word("\"", Tag.QUOTE);
    public static final Word ID = new Word("\"", Tag.ID);
    public static final Word DONE = new Word("-1", Tag.DONE);

    public Word(String lexeme, int tag) {
        super(tag);
        this.lexeme = lexeme;
    }

    public String toString() {
        return "" + lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }
}
