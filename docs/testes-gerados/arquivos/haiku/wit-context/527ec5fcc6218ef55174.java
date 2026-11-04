/*
 * @test
 * @summary Witup-derived tests for Short.parseShort(String)
 * @run main ShortWitupTest
 */
public class ShortWitupTest {

    public static void main(String[] args) {
        checkValue("0", (short) 0);
        checkValue("123", (short) 123);
        checkValue("-123", (short) -123);
        checkValue("+5", (short) 5);
        checkValue("32767", Short.MAX_VALUE);
        checkValue("-32768", Short.MIN_VALUE);

        expectNfe(null);
        expectNfe("");
        expectNfe("-");
        expectNfe("+");
        expectNfe("32768");
        expectNfe("-32769");
        expectNfe("abc");
        expectNfe("1.0");
        expectNfe(" 1");
        expectNfe("1 ");
    }

    static void checkValue(String s, short expected) {
        short actual = Short.parseShort(s);
        if (actual != expected) {
            throw new RuntimeException("parseShort(\"" + s + "\") returned "
                    + actual + ", expected " + expected);
        }
    }

    static void expectNfe(String s) {
        try {
            Short.parseShort(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("expected NumberFormatException for input: " + s);
    }
}
