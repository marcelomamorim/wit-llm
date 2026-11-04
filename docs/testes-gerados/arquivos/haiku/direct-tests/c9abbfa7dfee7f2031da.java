/*
 * @test
 * @summary Verify insert(int, double) inserts String.valueOf(double) at the given offset and rejects invalid offsets
 * @run main AbstractStringBuilderWitupTest
 */
public class AbstractStringBuilderWitupTest {
    static int failures = 0;

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            failures++;
            System.err.println("Expected: " + expected + " but got: " + actual);
        }
    }

    static void checkOutOfBounds(int offset) {
        try {
            new StringBuilder("ab").insert(offset, 1.0);
            failures++;
            System.err.println("No exception for offset " + offset);
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    public static void main(String[] args) {
        check(new StringBuilder().insert(0, 1.5).toString(), "1.5");
        check(new StringBuilder("ab").insert(1, 2.0).toString(), "a2.0b");
        check(new StringBuilder("ab").insert(0, 2.0).toString(), "2.0ab");
        check(new StringBuilder("ab").insert(2, 2.0).toString(), "ab2.0");
        check(new StringBuilder("x").insert(0, 100.0).toString(), "100.0x");
        check(new StringBuilder().insert(0, -0.0).toString(), "-0.0");
        check(new StringBuilder().insert(0, Double.NaN).toString(), "NaN");
        check(new StringBuilder().insert(0, Double.POSITIVE_INFINITY).toString(), "Infinity");
        check(new StringBuilder().insert(0, 1e10).toString(), "1.0E10");

        checkOutOfBounds(-1);
        checkOutOfBounds(3);

        if (failures > 0) {
            throw new RuntimeException(failures + " failure(s)");
        }
    }
}
