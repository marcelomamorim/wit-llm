/*
 * @test
 * @summary Witup-generated tests for Short.decode(String) range checks and parsing
 * @run main ShortWitupTest
 */
public class ShortWitupTest {
    public static void main(String[] args) {
        checkValue((short)127, "127");
        checkValue((short)-128, "-128");
        checkValue((short)32767, "32767");
        checkValue((short)-32768, "-32768");
        checkValue((short)63, "077");
        checkValue((short)255, "0xff");
        checkValue((short)32767, "0x7fff");
        checkValue((short)-32768, "#-8000");
        checkValue((short)5, "+5");

        checkRejected("32768");
        checkRejected("-32769");
        checkRejected("0x8000");
        checkRejected("-0x8001");
        checkRejected("");
        checkRejected("99999999999");
    }

    static void checkValue(short expected, String input) {
        Short result = Short.decode(input);
        if (result == null || result.shortValue() != expected) {
            throw new RuntimeException("Short.decode(\"" + input + "\") expected " + expected + " but got " + result);
        }
    }

    static void checkRejected(String input) {
        try {
            Short result = Short.decode(input);
            throw new RuntimeException("Expected NumberFormatException for input \"" + input + "\" but got " + result);
        } catch (NumberFormatException expected) {
            // expected
        }
    }
}
