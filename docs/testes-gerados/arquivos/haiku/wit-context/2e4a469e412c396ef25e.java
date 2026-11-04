/*
 * @test
 * @summary Verifies StringBuilder.insert(int, int) (reaching AbstractStringBuilder.insert(int, int)) for valid offsets and out-of-bounds offsets
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testValidInsertions();
        testInvalidOffsets();
    }

    static void testValidInsertions() {
        StringBuilder sb = new StringBuilder("abc");
        check(sb.insert(0, 12).toString().equals("12abc"), "insert at start");

        sb = new StringBuilder("ab");
        check(sb.insert(2, 7).toString().equals("ab7"), "insert at end");

        sb = new StringBuilder("ab");
        check(sb.insert(1, -5).toString().equals("a-5b"), "insert negative value in middle");

        sb = new StringBuilder();
        check(sb.insert(0, 0).toString().equals("0"), "insert into empty builder");
    }

    static void testInvalidOffsets() {
        expectSIOOBE(new StringBuilder("abc"), -1, 1, "negative offset");
        expectSIOOBE(new StringBuilder("abc"), 4, 1, "offset greater than length");
        expectSIOOBE(new StringBuilder(), 1, 0, "offset 1 on empty builder");
    }

    static void expectSIOOBE(StringBuilder sb, int offset, int value, String label) {
        try {
            sb.insert(offset, value);
        } catch (StringIndexOutOfBoundsException expected) {
            return;
        }
        throw new RuntimeException("Expected StringIndexOutOfBoundsException: " + label);
    }

    static void check(boolean condition, String label) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + label);
        }
    }
}
