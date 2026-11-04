/*
 * @test
 * @summary Verifies subSequence bounds checking through the public StringBuilder API
 * @run main AbstractStringBuilderWitupTest
 */
public class AbstractStringBuilderWitupTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("abcdef");
        check("abcdef", sb.subSequence(0, 6).toString());
        check("bcd", sb.subSequence(1, 4).toString());
        check("", sb.subSequence(3, 3).toString());
        check("", sb.subSequence(6, 6).toString());
        check("", new StringBuilder().subSequence(0, 0).toString());
        expectSIOOBE(sb, -1, 3);
        expectSIOOBE(sb, 0, 7);
        expectSIOOBE(sb, 4, 2);
        expectSIOOBE(sb, 7, 7);
        expectSIOOBE(new StringBuilder(), 0, 1);
    }

    private static void expectSIOOBE(StringBuilder sb, int start, int end) {
        try {
            sb.subSequence(start, end);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for " + start + "," + end);
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }
}
