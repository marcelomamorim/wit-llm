/*
 * @test
 * @summary Witup-generated checks for insert(int, double) observable through the public StringBuilder API
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, 1.5);
        check("1.5abc".equals(sb.toString()), "insert at offset 0");

        sb = new StringBuilder("abc");
        sb.insert(3, -2.0);
        check("abc-2.0".equals(sb.toString()), "insert at end offset");

        sb = new StringBuilder("ab");
        sb.insert(1, Double.NaN);
        check("aNaNb".equals(sb.toString()), "insert NaN");

        sb = new StringBuilder("x");
        sb.insert(1, -0.0);
        check("x-0.0".equals(sb.toString()), "insert negative zero");

        sb = new StringBuilder();
        sb.insert(0, 3.0);
        check("3.0".equals(sb.toString()), "insert into empty builder");

        assertSIOOBE(new StringBuilder("abc"), -1, 1.0);
        assertSIOOBE(new StringBuilder("abc"), 4, 1.0);
        assertSIOOBE(new StringBuilder(""), 1, 2.0);
    }

    static void assertSIOOBE(StringBuilder sb, int offset, double d) {
        try {
            sb.insert(offset, d);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
