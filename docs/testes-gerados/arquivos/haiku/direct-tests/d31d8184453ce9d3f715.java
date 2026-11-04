/*
 * @test
 * @summary Verifies StringBuilder.append(char[], int, int) appends the requested slice, returns this, and rejects invalid input.
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testAppendSlice();
        testReturnsSameInstance();
        testZeroLengthAppendsNothing();
        testZeroLengthAtEndIsAllowed();
        testNullArrayThrowsNPE();
        testOutOfRangeThrowsIOOBE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAppendSlice() {
        char[] src = {'a', 'b', 'c', 'd', 'e'};
        StringBuilder sb = new StringBuilder();
        sb.append(src, 1, 3);
        check("bcd".equals(sb.toString()), "expected 'bcd' but was '" + sb + "'");

        StringBuilder sb2 = new StringBuilder("x");
        sb2.append(src, 0, 2);
        check("xab".equals(sb2.toString()), "expected 'xab' but was '" + sb2 + "'");
    }

    static void testReturnsSameInstance() {
        char[] src = {'q', 'r'};
        StringBuilder sb = new StringBuilder();
        StringBuilder result = sb.append(src, 0, 2);
        check(result == sb, "append(char[], int, int) must return this");
        check("qr".equals(result.toString()), "expected 'qr' but was '" + result + "'");
    }

    static void testZeroLengthAppendsNothing() {
        char[] src = {'a', 'b', 'c'};
        StringBuilder sb = new StringBuilder("keep");
        sb.append(src, 1, 0);
        check("keep".equals(sb.toString()), "zero-length append changed content to '" + sb + "'");
    }

    static void testZeroLengthAtEndIsAllowed() {
        char[] src = {'a', 'b', 'c'};
        StringBuilder sb = new StringBuilder("k");
        sb.append(src, 3, 0);
        check("k".equals(sb.toString()), "zero-length append at end changed content to '" + sb + "'");
    }

    static void testNullArrayThrowsNPE() {
        StringBuilder sb = new StringBuilder();
        boolean thrown = false;
        try {
            sb.append((char[]) null, 0, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null char[] must throw NullPointerException");
    }

    static void testOutOfRangeThrowsIOOBE() {
        char[] src = {'a', 'b', 'c', 'd', 'e'};
        StringBuilder sb = new StringBuilder();
        boolean thrown = false;
        try {
            sb.append(src, 3, 4);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "out-of-range slice must throw IndexOutOfBoundsException");
    }
}
