package lexer;

public class Word extends Token {
    public final String lexeme;

    public static final Word NOT = new Word("!", Tag.NOT);
    public static final Word GREATER_THAN = new Word(">", Tag.GREATER_THAN);
    public static final Word GREATER_EQUAL = new Word(">=", Tag.GREATER_EQUAL);
    public static final Word LESS_THAN = new Word("<", Tag.LESS_THAN);
    public static final Word LESS_EQUAL = new Word("<=", Tag.LESS_EQUAL);
    public static final Word DIF = new Word("!=", Tag.DIF);
    public static final Word EQUALS = new Word("==", Tag.EQUALS);
    public static final Word PLUS = new Word("+", Tag.PLUS);
    public static final Word SUBTRACT = new Word("-", Tag.SUBTRACT);
    public static final Word OR = new Word("||", Tag.OR);
    public static final Word MULT = new Word("*", Tag.MULT);
    public static final Word DIVIDE = new Word("/", Tag.DIVIDE);
    public static final Word AND = new Word("&&", Tag.AND);
    public static final Word ASSIGN = new Word("=", Tag.ASSIGN);
    public static final Word SEMICOLON = new Word(";", Tag.SEMICOLON);
    public static final Word COMMA = new Word(",", Tag.COMMA);
    public static final Word LEFT_PAREN = new Word("(", Tag.LEFT_PAREN);
    public static final Word RIGHT_PAREN = new Word(")", Tag.RIGHT_PAREN);
    public static final Word BRACKET_LEFT = new Word("{", Tag.BRACKET_LEFT);
    public static final Word BRACKET_RIGHT = new Word("}", Tag.BRACKET_RIGHT);
    public static final Word DOT = new Word(".", Tag.DOT);
    public static final Word DONE = new Word("-1", Tag.DONE);

    public Word(String lexeme, int tag) {
        super(tag);
        this.lexeme = lexeme;
    }
}
