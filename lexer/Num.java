package lexer;

public class Num extends Token {
    public final String value;

    public Num(String value, int tag) {
        super(tag);
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }

}
