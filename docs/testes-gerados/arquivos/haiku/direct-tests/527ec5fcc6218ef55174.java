/*
 * @test
 * @summary Tests observable behavior of Short.parseShort(String)
 * @run main ShortWitupTest
 */

public class ShortWitupTest {

    public static void main(String[] args) {
        check(Short.parseShort("0") == 0, "zero");
        check(Short.parseShort("123") == 123, "positive");
        check(Short.parseShort("-123") == -123, "negative");
        check(Short.parseShort("+5") == 5, "explicit plus sign");
        check(Short.parseShort("-32768") == Short.MIN_VALUE, "minimum bound");
        check(Short.parseShort("32767") == Short.MAX_VALUE, "maximum bound");

        expectNFE(null);
        expectNFE("");
        expectNFE("-");
        expectNFE("+");
        expectNFE("abc");
        expectNFE("12a");
        expectNFE(" 1");
        expectNFE("32768");
        expectNFE("-32769");
        expectNFE("99999999999");
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Failed: " + msg);
        }
    }

    static void expectNFE(String s) {
        try {
            Short.parseShort(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for: " + s);
    }
}
