package lexer;

public class FloatValue extends Token {
    public final float value;

    public FloatValue(float value) {
        super(Tag.FLOAT);
        this.value = value;
    }
}
