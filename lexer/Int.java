package lexer;

public class Int extends Token {
    public final int value;

    public Int(int value) {
        super(Tag.INT);
        this.value = value;
    }
}
