/*
 * @test
 * @summary Tests the String(int[] codePoints, int offset, int count) constructor bounds checks and code point decoding
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        int[] cps = {0x41, 0x42, 0x43};

        check("ABC", new String(cps, 0, 3));
        check("BC", new String(cps, 1, 2));
        check("", new String(cps, 0, 0));
        check("", new String(cps, 3, 0));
        check("", new String(new int[0], 0, 0));

        int[] supp = {0x1F600};
        check(new String(Character.toChars(0x1F600)), new String(supp, 0, 1));

        expectSIOOBE(cps, -1, 1);
        expectSIOOBE(cps, 0, -1);
        expectSIOOBE(cps, 2, 2);
        expectSIOOBE(cps, 4, 0);
        expectSIOOBE(new int[0], 0, 1);

        boolean npe = false;
        try {
            new String((int[]) null, 0, 0);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null array");
        }
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but was [" + actual + "]");
        }
    }

    static void expectSIOOBE(int[] cps, int offset, int count) {
        try {
            new String(cps, offset, count);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset="
                + offset + ", count=" + count);
    }
}
