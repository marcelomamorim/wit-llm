/*
 * @test
 * @summary Verifies StringBuffer.substring(int,int) returns expected values and throws StringIndexOutOfBoundsException for invalid ranges
 * @run main StringBufferWitupTest
 */
public class StringBufferWitupTest {

    public static void main(String[] args) {
        testValidRanges();
        testInvalidRanges();
    }

    static void testValidRanges() {
        StringBuffer sb = new StringBuffer("hello");
        checkEquals("hello", sb.substring(0, 5));
        checkEquals("el", sb.substring(1, 3));
        checkEquals("", sb.substring(0, 0));
        checkEquals("", sb.substring(5, 5));
        checkEquals("o", sb.substring(4, 5));
        checkEquals("hello", sb.toString());
    }

    static void testInvalidRanges() {
        StringBuffer sb = new StringBuffer("hello");
        expectSIOOBE(sb, -1, 2);
        expectSIOOBE(sb, 3, 2);
        expectSIOOBE(sb, 0, 6);
        expectSIOOBE(sb, 6, 6);
        expectSIOOBE(sb, -1, -1);
        expectSIOOBE(new StringBuffer(), 0, 1);
    }

    static void expectSIOOBE(StringBuffer sb, int start, int end) {
        try {
            String result = sb.substring(start, end);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for start="
                    + start + ", end=" + end + " but got \"" + result + "\"");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void checkEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }
}
