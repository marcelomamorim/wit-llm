/*
 * @test
 * @summary Verifies observable behavior of insert(int offset, int i) through the public StringBuilder API
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder returned = sb.insert(1, 123);
        check("a123b", sb.toString());
        check(true, returned == sb);

        check("-5ab", new StringBuilder("ab").insert(0, -5).toString());
        check("ab7", new StringBuilder("ab").insert(2, 7).toString());
        check("0", new StringBuilder().insert(0, 0).toString());
        check("-2147483648", new StringBuilder().insert(0, Integer.MIN_VALUE).toString());
        check("2147483647", new StringBuilder().insert(0, Integer.MAX_VALUE).toString());

        checkOutOfBounds(-1);
        checkOutOfBounds(3);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void check(boolean expected, boolean actual) {
        if (expected != actual) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void checkOutOfBounds(int offset) {
        try {
            new StringBuilder("ab").insert(offset, 1);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("no StringIndexOutOfBoundsException for offset " + offset);
    }
}
