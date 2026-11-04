/*
 * @test
 * @summary Tests observable behavior of Short.valueOf(String)
 * @run main ShortWitupTest
 */

public class ShortWitupTest {

    public static void main(String[] args) {
        check(Short.valueOf("0").shortValue() == 0, "zero");
        check(Short.valueOf("123").shortValue() == 123, "positive");
        check(Short.valueOf("-123").shortValue() == -123, "negative");
        check(Short.valueOf("+5").shortValue() == 5, "explicit plus sign");
        check(Short.valueOf("-0").shortValue() == 0, "negative zero");
        check(Short.valueOf("32767").shortValue() == Short.MAX_VALUE, "MAX_VALUE bound");
        check(Short.valueOf("-32768").shortValue() == Short.MIN_VALUE, "MIN_VALUE bound");
        check(Short.valueOf("42").equals(Short.valueOf((short) 42)), "equals to valueOf(short)");

        expectNFE(null);
        expectNFE("");
        expectNFE("+");
        expectNFE("-");
        expectNFE("32768");
        expectNFE("-32769");
        expectNFE("1.0");
        expectNFE(" 1");
        expectNFE("abc");
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Failed: " + msg);
        }
    }

    static void expectNFE(String s) {
        try {
            Short.valueOf(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("NumberFormatException expected for: " + s);
    }
}
