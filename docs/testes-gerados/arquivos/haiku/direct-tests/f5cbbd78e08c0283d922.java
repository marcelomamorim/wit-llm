/*
 * @test
 * @summary Tests observable behavior of Byte.valueOf(String)
 * @run main ByteWitupTest
 */

public class ByteWitupTest {
    static int failures = 0;

    static void check(boolean condition, String msg) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void expectNfe(String s) {
        try {
            Byte.valueOf(s);
            check(false, "expected NumberFormatException for: " + s);
        } catch (NumberFormatException e) {
            // expected
        }
    }

    public static void main(String[] args) {
        check(Byte.valueOf("0").byteValue() == 0, "zero");
        check(Byte.valueOf("127").byteValue() == 127, "max value");
        check(Byte.valueOf("-128").byteValue() == -128, "min value");
        check(Byte.valueOf("+5").byteValue() == 5, "leading plus sign");
        check(Byte.valueOf("-7").byteValue() == -7, "negative value");
        check(Byte.valueOf("42") == Byte.valueOf((byte) 42), "cached instance identity");

        expectNfe("");
        expectNfe("128");
        expectNfe("-129");
        expectNfe("abc");
        expectNfe(null);

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }
}
