/*
 * @test
 * @summary Tests public Short(String) constructor parsing in radix 10
 * @run main ShortWitupTest
 */

public class ShortWitupTest {

    public static void main(String[] args) {
        check("123", (short) 123);
        check("-1", (short) -1);
        check("+5", (short) 5);
        check("-0", (short) 0);
        check("32767", Short.MAX_VALUE);
        check("-32768", Short.MIN_VALUE);

        expectNumberFormatException("32768");
        expectNumberFormatException("-32769");
        expectNumberFormatException("");
        expectNumberFormatException("abc");
        expectNumberFormatException("0x10");
        expectNumberFormatException("+");
        expectNumberFormatException("7F");
        expectNumberFormatException(null);
    }

    static void check(String s, short expected) {
        short actual = new Short(s).shortValue();
        if (actual != expected) {
            throw new RuntimeException("For input " + s + " expected " + expected + " but got " + actual);
        }
    }

    static void expectNumberFormatException(String s) {
        try {
            new Short(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for input: " + s);
    }
}
