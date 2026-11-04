/*
 * @test
 * @summary Witup-derived checks for StringBuilder.insert(int, char) offset validation
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testValidInsertions();
        testReturnsSameInstance();
        testInvalidOffsetsThrow();
    }

    static void testValidInsertions() {
        StringBuilder sb = new StringBuilder("abc");
        sb.insert(0, 'x');
        check("xabc", sb.toString());

        sb = new StringBuilder("abc");
        sb.insert(3, 'z');
        check("abcz", sb.toString());

        sb = new StringBuilder("abc");
        sb.insert(1, 'y');
        check("aybc", sb.toString());

        sb = new StringBuilder();
        sb.insert(0, 'a');
        check("a", sb.toString());
    }

    static void testReturnsSameInstance() {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder r = sb.insert(1, 'q');
        if (r != sb) {
            throw new RuntimeException("insert did not return this");
        }
        check("aqb", sb.toString());
    }

    static void testInvalidOffsetsThrow() {
        expectSIOOBE(new StringBuilder("abc"), -1, 'x');
        expectSIOOBE(new StringBuilder("abc"), 4, 'x');
        expectSIOOBE(new StringBuilder(), 1, 'x');
    }

    static void expectSIOOBE(StringBuilder sb, int offset, char c) {
        try {
            sb.insert(offset, c);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for offset " + offset);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
